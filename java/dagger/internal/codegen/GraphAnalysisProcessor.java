package dagger.internal.codegen;

import dagger.Module;
import dagger.Provides;
import dagger.Provides.Type;
import dagger.internal.Binding;
import dagger.internal.Binding.InvalidBindingException;
import dagger.internal.BindingsGroup;
import dagger.internal.Linker;
import dagger.internal.Linker.ErrorHandler;
import dagger.internal.ProblemDetector;
import dagger.internal.ProvidesBinding;
import dagger.internal.SetBinding;
import dagger.internal.codegen.Util.CodeGenerationIncompleteException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileManager.Location;
import javax.tools.StandardLocation;
import net.sqlcipher.database.SQLiteDatabase;

@SupportedAnnotationTypes({"dagger.Module"})
public final class GraphAnalysisProcessor extends AbstractProcessor {
    private static final Set<String> ERROR_NAMES_TO_PROPAGATE = new LinkedHashSet(Arrays.asList(new String[]{"com.sun.tools.javac.code.Symbol$CompletionFailure"}));
    private final Set<String> delayedModuleNames = new LinkedHashSet();

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        if (env.processingOver()) {
            Set<Element> modules = new LinkedHashSet();
            for (String moduleName : this.delayedModuleNames) {
                modules.add(elements().getTypeElement(moduleName));
            }
            for (Element element : modules) {
                try {
                    Map<String, Object> annotation = Util.getAnnotation(Module.class, element);
                    TypeElement moduleType = (TypeElement) element;
                    if (annotation == null) {
                        error("Missing @Module annotation.", moduleType);
                    } else {
                        if (annotation.get("complete").equals(Boolean.TRUE)) {
                            try {
                                Map<String, Binding<?>> bindings = processCompleteModule(moduleType, false);
                                new ProblemDetector().detectCircularDependencies(bindings.values());
                                try {
                                    writeDotFile(moduleType, bindings);
                                } catch (IOException e) {
                                    StringWriter sw = new StringWriter();
                                    e.printStackTrace(new PrintWriter(sw));
                                    this.processingEnv.getMessager().printMessage(Kind.WARNING, "Graph visualization failed. Please report this as a bug.\n\n" + sw, moduleType);
                                }
                            } catch (ModuleValidationException e2) {
                                error("Graph validation failed: " + e2.getMessage(), e2.source);
                            } catch (InvalidBindingException e3) {
                                error("Graph validation failed: " + e3.getMessage(), elements().getTypeElement(e3.type));
                            } catch (RuntimeException e4) {
                                if (ERROR_NAMES_TO_PROPAGATE.contains(e4.getClass().getName())) {
                                    throw e4;
                                }
                                error("Unknown error " + e4.getClass().getName() + " thrown by javac in graph validation: " + e4.getMessage(), moduleType);
                            }
                        }
                        if (annotation.get("library").equals(Boolean.FALSE)) {
                            try {
                                new ProblemDetector().detectUnusedBinding(processCompleteModule(moduleType, true).values());
                            } catch (IllegalStateException e5) {
                                error("Graph validation failed: " + e5.getMessage(), moduleType);
                            }
                        }
                    }
                } catch (CodeGenerationIncompleteException e6) {
                }
            }
            return false;
        }
        for (Element e7 : env.getElementsAnnotatedWith(Module.class)) {
            if (e7 instanceof TypeElement) {
                this.delayedModuleNames.add(((TypeElement) e7).getQualifiedName().toString());
            } else {
                error("@Module applies to a type, " + e7.getSimpleName() + " is a " + e7.getKind(), e7);
            }
        }
        return false;
    }

    private void error(String message, Element element) {
        this.processingEnv.getMessager().printMessage(Kind.ERROR, message, element);
    }

    private Map<String, Binding<?>> processCompleteModule(TypeElement rootModule, boolean ignoreCompletenessErrors) {
        ErrorHandler errorHandler;
        Map<String, TypeElement> allModules = new LinkedHashMap();
        collectIncludesRecursively(rootModule, allModules, new LinkedList());
        ArrayList<GraphAnalysisStaticInjection> staticInjections = new ArrayList();
        if (ignoreCompletenessErrors) {
            errorHandler = ErrorHandler.NULL;
        } else {
            GraphAnalysisErrorHandler graphAnalysisErrorHandler = new GraphAnalysisErrorHandler(this.processingEnv, rootModule.getQualifiedName().toString());
        }
        Linker linker = new Linker(null, new GraphAnalysisLoader(this.processingEnv), errorHandler);
        synchronized (linker) {
            BindingsGroup baseBindings = new 1(this);
            2 2 = new 2(this);
            for (TypeElement module : allModules.values()) {
                BindingsGroup addTo;
                Map<String, Object> annotation = Util.getAnnotation(Module.class, module);
                boolean overrides = ((Boolean) annotation.get("overrides")).booleanValue();
                boolean library = ((Boolean) annotation.get("library")).booleanValue();
                if (overrides) {
                    addTo = 2;
                } else {
                    addTo = baseBindings;
                }
                Set<String> injectsProvisionKeys = new LinkedHashSet();
                for (Object injectableTypeObject : (Object[]) annotation.get("injects")) {
                    TypeMirror injectableType = (TypeMirror) injectableTypeObject;
                    String providerKey = GeneratorKeys.get(injectableType);
                    injectsProvisionKeys.add(providerKey);
                    linker.requestBinding(Util.isInterface(injectableType) ? providerKey : GeneratorKeys.rawMembersKey(injectableType), module.getQualifiedName().toString(), getClass().getClassLoader(), false, true);
                }
                for (Object staticInjection : (Object[]) annotation.get("staticInjections")) {
                    staticInjections.add(new GraphAnalysisStaticInjection(this.processingEnv.getTypeUtils().asElement((TypeMirror) staticInjection)));
                }
                for (Element enclosed : module.getEnclosedElements()) {
                    Provides provides = (Provides) enclosed.getAnnotation(Provides.class);
                    if (provides != null) {
                        ExecutableElement providerMethod = (ExecutableElement) enclosed;
                        String key = GeneratorKeys.get(providerMethod);
                        ProvidesBinding<?> binding = new ProviderMethodBinding(key, providerMethod, library);
                        Binding<?> previous = addTo.get(key);
                        if (!(previous == null || ((provides.type() == Type.SET || provides.type() == Type.SET_VALUES) && (previous instanceof SetBinding)))) {
                            String message = "Duplicate bindings for " + key;
                            if (overrides) {
                                message = message + " in override module(s) - cannot override an override";
                            }
                            error(message + ":\n    " + previous.requiredBy + "\n    " + binding.requiredBy, providerMethod);
                        }
                        switch (3.$SwitchMap$dagger$Provides$Type[provides.type().ordinal()]) {
                            case SQLiteDatabase.OPEN_READONLY /*1*/:
                                if (injectsProvisionKeys.contains(binding.provideKey)) {
                                    binding.setDependedOn(true);
                                }
                                try {
                                    addTo.contributeProvidesBinding(key, binding);
                                    break;
                                } catch (IllegalStateException ise) {
                                    throw new ModuleValidationException(ise.getMessage(), providerMethod);
                                }
                            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                                SetBinding.add(addTo, GeneratorKeys.getSetKey(providerMethod), binding);
                                break;
                            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                                SetBinding.add(addTo, key, binding);
                                break;
                            default:
                                throw new AssertionError("Unknown @Provides type " + provides.type());
                        }
                    }
                }
            }
            linker.installBindings(baseBindings);
            linker.installBindings(2);
            Iterator it = staticInjections.iterator();
            while (it.hasNext()) {
                ((GraphAnalysisStaticInjection) it.next()).attach(linker);
            }
        }
        return linker.linkAll();
    }

    private Elements elements() {
        return this.processingEnv.getElementUtils();
    }

    void collectIncludesRecursively(TypeElement module, Map<String, TypeElement> result, Deque<String> path) {
        Map<String, Object> annotation = Util.getAnnotation(Module.class, module);
        if (annotation == null) {
            throw new ModuleValidationException("No @Module on " + module, module);
        }
        String name = module.getQualifiedName().toString();
        if (path.contains(name)) {
            StringBuilder message = new StringBuilder("Module Inclusion Cycle: ");
            if (path.size() == 1) {
                message.append(name).append(" includes itself directly.");
            } else {
                String includer = name;
                int i = 0;
                while (path.size() > 0) {
                    String current = includer;
                    includer = (String) path.pop();
                    message.append("\n").append(i).append(". ").append(current).append(" included by ").append(includer);
                    i++;
                }
                message.append("\n0. ").append(name);
            }
            throw new ModuleValidationException(message.toString(), module);
        }
        result.put(name, module);
        Types types = this.processingEnv.getTypeUtils();
        List<Object> seedModules = new ArrayList();
        seedModules.addAll(Arrays.asList((Object[]) annotation.get("includes")));
        if (!annotation.get("addsTo").equals(Void.class)) {
            seedModules.add(annotation.get("addsTo"));
        }
        for (Object include : seedModules) {
            if (include instanceof TypeMirror) {
                TypeElement includedModule = (TypeElement) types.asElement((TypeMirror) include);
                path.push(name);
                collectIncludesRecursively(includedModule, result, path);
                path.pop();
            } else {
                this.processingEnv.getMessager().printMessage(Kind.WARNING, "Unexpected value for include: " + include + " in " + module, module);
            }
        }
    }

    void writeDotFile(TypeElement module, Map<String, Binding<?>> bindings) throws IOException {
        Location location = StandardLocation.SOURCE_OUTPUT;
        String path = Util.getPackage(module).getQualifiedName().toString();
        String file = module.getQualifiedName().toString().substring(path.length() + 1) + ".dot";
        GraphVizWriter dotWriter = new GraphVizWriter(this.processingEnv.getFiler().createResource(location, path, file, new Element[]{module}).openWriter());
        new GraphVisualizer().write(bindings, dotWriter);
        dotWriter.close();
    }
}
