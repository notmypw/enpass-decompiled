package dagger.internal;

import dagger.internal.Binding.InvalidBindingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

public final class Linker {
    private static final Object UNINITIALIZED = new Object();
    private boolean attachSuccess = true;
    private final Linker base;
    private final Map<String, Binding<?>> bindings = new HashMap();
    private final ErrorHandler errorHandler;
    private final List<String> errors = new ArrayList();
    private volatile Map<String, Binding<?>> linkedBindings = null;
    private final Loader plugin;
    private final Queue<Binding<?>> toLink = new ArrayQueue();

    public Linker(Linker base, Loader plugin, ErrorHandler errorHandler) {
        if (plugin == null) {
            throw new NullPointerException("plugin");
        } else if (errorHandler == null) {
            throw new NullPointerException("errorHandler");
        } else {
            this.base = base;
            this.plugin = plugin;
            this.errorHandler = errorHandler;
        }
    }

    public void installBindings(BindingsGroup toInstall) {
        if (this.linkedBindings != null) {
            throw new IllegalStateException("Cannot install further bindings after calling linkAll().");
        }
        for (Entry<String, ? extends Binding<?>> entry : toInstall.entrySet()) {
            this.bindings.put(entry.getKey(), scope((Binding) entry.getValue()));
        }
    }

    public Map<String, Binding<?>> linkAll() {
        assertLockHeld();
        if (this.linkedBindings != null) {
            return this.linkedBindings;
        }
        for (Binding<?> binding : this.bindings.values()) {
            if (!binding.isLinked()) {
                this.toLink.add(binding);
            }
        }
        linkRequested();
        this.linkedBindings = Collections.unmodifiableMap(this.bindings);
        return this.linkedBindings;
    }

    public Map<String, Binding<?>> fullyLinkedBindings() {
        return this.linkedBindings;
    }

    public void linkRequested() {
        assertLockHeld();
        while (true) {
            Binding<?> binding = (Binding) this.toLink.poll();
            if (binding == null) {
                try {
                    break;
                } finally {
                    this.errors.clear();
                }
            } else if (binding instanceof DeferredBinding) {
                DeferredBinding deferred = (DeferredBinding) binding;
                String key = deferred.deferredKey;
                boolean mustHaveInjections = deferred.mustHaveInjections;
                if (this.bindings.containsKey(key)) {
                    continue;
                } else {
                    try {
                        Binding<?> resolvedBinding = createBinding(key, binding.requiredBy, deferred.classLoader, mustHaveInjections);
                        resolvedBinding.setLibrary(binding.library());
                        resolvedBinding.setDependedOn(binding.dependedOn());
                        if (key.equals(resolvedBinding.provideKey) || key.equals(resolvedBinding.membersKey)) {
                            Binding<?> scopedBinding = scope(resolvedBinding);
                            this.toLink.add(scopedBinding);
                            putBinding(scopedBinding);
                        } else {
                            throw new IllegalStateException("Unable to create binding for " + key);
                        }
                    } catch (InvalidBindingException e) {
                        addError(e.type + " " + e.getMessage() + " required by " + binding.requiredBy);
                        this.bindings.put(key, Binding.UNRESOLVED);
                    } catch (UnsupportedOperationException e2) {
                        addError("Unsupported: " + e2.getMessage() + " required by " + binding.requiredBy);
                        this.bindings.put(key, Binding.UNRESOLVED);
                    } catch (IllegalArgumentException e3) {
                        addError(e3.getMessage() + " required by " + binding.requiredBy);
                        this.bindings.put(key, Binding.UNRESOLVED);
                    } catch (RuntimeException e4) {
                        throw e4;
                    } catch (Exception e5) {
                        throw new RuntimeException(e5);
                    }
                }
            } else {
                this.attachSuccess = true;
                binding.attach(this);
                if (this.attachSuccess) {
                    binding.setLinked();
                } else {
                    this.toLink.add(binding);
                }
            }
        }
        this.errorHandler.handleErrors(this.errors);
    }

    private void assertLockHeld() {
        if (!Thread.holdsLock(this)) {
            throw new AssertionError();
        }
    }

    private Binding<?> createBinding(String key, Object requiredBy, ClassLoader classLoader, boolean mustHaveInjections) {
        String builtInBindingsKey = Keys.getBuiltInBindingsKey(key);
        if (builtInBindingsKey != null) {
            return new BuiltInBinding(key, requiredBy, classLoader, builtInBindingsKey);
        }
        String lazyKey = Keys.getLazyKey(key);
        if (lazyKey != null) {
            return new LazyBinding(key, requiredBy, classLoader, lazyKey);
        }
        String className = Keys.getClassName(key);
        if (className == null || Keys.isAnnotated(key)) {
            throw new IllegalArgumentException(key);
        }
        Binding<?> binding = this.plugin.getAtInjectBinding(key, className, classLoader, mustHaveInjections);
        if (binding != null) {
            return binding;
        }
        throw new InvalidBindingException(className, "could not be bound with key " + key);
    }

    @Deprecated
    public Binding<?> requestBinding(String key, Object requiredBy) {
        return requestBinding(key, requiredBy, getClass().getClassLoader(), true, true);
    }

    public Binding<?> requestBinding(String key, Object requiredBy, ClassLoader classLoader) {
        return requestBinding(key, requiredBy, classLoader, true, true);
    }

    @Deprecated
    public Binding<?> requestBinding(String key, Object requiredBy, boolean mustHaveInjections, boolean library) {
        return requestBinding(key, requiredBy, getClass().getClassLoader(), mustHaveInjections, library);
    }

    public Binding<?> requestBinding(String key, Object requiredBy, ClassLoader classLoader, boolean mustHaveInjections, boolean library) {
        assertLockHeld();
        Binding<?> binding = null;
        for (Linker linker = this; linker != null; linker = linker.base) {
            binding = (Binding) linker.bindings.get(key);
            if (binding != null) {
                if (!(linker == this || binding.isLinked())) {
                    throw new AssertionError();
                }
                if (binding != null) {
                    Binding<?> deferredBinding = new DeferredBinding(key, classLoader, requiredBy, mustHaveInjections, null);
                    deferredBinding.setLibrary(library);
                    deferredBinding.setDependedOn(true);
                    this.toLink.add(deferredBinding);
                    this.attachSuccess = false;
                    return null;
                }
                if (!binding.isLinked()) {
                    this.toLink.add(binding);
                }
                binding.setLibrary(library);
                binding.setDependedOn(true);
                return binding;
            }
        }
        if (binding != null) {
            if (binding.isLinked()) {
                this.toLink.add(binding);
            }
            binding.setLibrary(library);
            binding.setDependedOn(true);
            return binding;
        }
        Binding<?> deferredBinding2 = new DeferredBinding(key, classLoader, requiredBy, mustHaveInjections, null);
        deferredBinding2.setLibrary(library);
        deferredBinding2.setDependedOn(true);
        this.toLink.add(deferredBinding2);
        this.attachSuccess = false;
        return null;
    }

    private <T> void putBinding(Binding<T> binding) {
        if (binding.provideKey != null) {
            putIfAbsent(this.bindings, binding.provideKey, binding);
        }
        if (binding.membersKey != null) {
            putIfAbsent(this.bindings, binding.membersKey, binding);
        }
    }

    static <T> Binding<T> scope(Binding<T> binding) {
        return (!binding.isSingleton() || (binding instanceof SingletonBinding)) ? binding : new SingletonBinding(binding, null);
    }

    private <K, V> void putIfAbsent(Map<K, V> map, K key, V value) {
        V replaced = map.put(key, value);
        if (replaced != null) {
            map.put(key, replaced);
        }
    }

    private void addError(String message) {
        this.errors.add(message);
    }
}
