package dagger.internal.codegen;

import com.github.clans.fab.BuildConfig;
import com.squareup.javawriter.JavaWriter;
import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import dagger.Provides.Type;
import dagger.internal.Binding;
import dagger.internal.BindingsGroup;
import dagger.internal.Linker;
import dagger.internal.ModuleAdapter;
import dagger.internal.ProvidesBinding;
import dagger.internal.SetBinding;
import dagger.internal.codegen.Util.CodeGenerationIncompleteException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import net.sqlcipher.database.SQLiteDatabase;

@SupportedAnnotationTypes({"*"})
public final class ModuleAdapterProcessor extends AbstractProcessor {
    private static final String BINDINGS_MAP = JavaWriter.type(BindingsGroup.class, new String[0]);
    private static final List<String> INVALID_RETURN_TYPES = Arrays.asList(new String[]{Provider.class.getCanonicalName(), Lazy.class.getCanonicalName()});
    private final LinkedHashMap<String, List<ExecutableElement>> remainingTypes = new LinkedHashMap();

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        this.remainingTypes.putAll(providerMethodsByClass(env));
        Iterator<String> i = this.remainingTypes.keySet().iterator();
        while (i.hasNext()) {
            String typeName = (String) i.next();
            TypeElement type = this.processingEnv.getElementUtils().getTypeElement(typeName);
            List<ExecutableElement> providesTypes = (List) this.remainingTypes.get(typeName);
            try {
                Map<String, Object> parsedAnnotation = Util.getAnnotation(Module.class, type);
                StringWriter stringWriter = new StringWriter();
                String adapterName = Util.adapterName(type, "$$ModuleAdapter");
                generateModuleAdapter(stringWriter, adapterName, type, parsedAnnotation, providesTypes);
                Writer sourceWriter = this.processingEnv.getFiler().createSourceFile(adapterName, new Element[]{type}).openWriter();
                sourceWriter.append(stringWriter.getBuffer());
                sourceWriter.close();
            } catch (CodeGenerationIncompleteException e) {
            } catch (IOException e2) {
                error("Code gen failed: " + e2, type);
            }
            i.remove();
        }
        if (env.processingOver() && this.remainingTypes.size() > 0) {
            this.processingEnv.getMessager().printMessage(Kind.ERROR, "Could not find types required by provides methods for " + this.remainingTypes.keySet());
        }
        return false;
    }

    private void error(String msg, Element element) {
        this.processingEnv.getMessager().printMessage(Kind.ERROR, msg, element);
    }

    private Map<String, List<ExecutableElement>> providerMethodsByClass(RoundEnvironment env) {
        Elements elementUtils = this.processingEnv.getElementUtils();
        Types types = this.processingEnv.getTypeUtils();
        Map<String, List<ExecutableElement>> result = new HashMap();
        for (Element providerMethod : findProvidesMethods(env)) {
            switch (1.$SwitchMap$javax$lang$model$element$ElementKind[providerMethod.getEnclosingElement().getKind().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    TypeElement type = (TypeElement) providerMethod.getEnclosingElement();
                    Set<Modifier> typeModifiers = type.getModifiers();
                    if (!typeModifiers.contains(Modifier.PRIVATE) && !typeModifiers.contains(Modifier.ABSTRACT)) {
                        Set<Modifier> methodModifiers = providerMethod.getModifiers();
                        if (!methodModifiers.contains(Modifier.PRIVATE) && !methodModifiers.contains(Modifier.ABSTRACT) && !methodModifiers.contains(Modifier.STATIC)) {
                            ExecutableElement providerMethodAsExecutable = (ExecutableElement) providerMethod;
                            if (!providerMethodAsExecutable.getThrownTypes().isEmpty()) {
                                error("@Provides methods must not have a throws clause: " + type.getQualifiedName() + "." + providerMethod, providerMethod);
                                break;
                            }
                            TypeMirror returnType = types.erasure(providerMethodAsExecutable.getReturnType());
                            if (!returnType.getKind().equals(TypeKind.ERROR)) {
                                for (String invalidTypeName : INVALID_RETURN_TYPES) {
                                    TypeElement invalidTypeElement = elementUtils.getTypeElement(invalidTypeName);
                                    if (invalidTypeElement != null && types.isSameType(returnType, types.erasure(invalidTypeElement.asType()))) {
                                        error(String.format("@Provides method must not return %s directly: %s.%s", new Object[]{invalidTypeElement, type.getQualifiedName(), providerMethod}), providerMethod);
                                        break;
                                    }
                                }
                            }
                            List<ExecutableElement> methods = (List) result.get(type.getQualifiedName().toString());
                            if (methods == null) {
                                methods = new ArrayList();
                                result.put(type.getQualifiedName().toString(), methods);
                            }
                            methods.add(providerMethodAsExecutable);
                            break;
                        }
                        error("@Provides methods must not be private, abstract or static: " + type.getQualifiedName() + "." + providerMethod, providerMethod);
                        break;
                    }
                    error("Classes declaring @Provides methods must not be private or abstract: " + type.getQualifiedName(), type);
                    break;
                    break;
                default:
                    error("Unexpected @Provides on " + Util.elementToString(providerMethod), providerMethod);
                    break;
            }
        }
        TypeMirror objectType = elementUtils.getTypeElement("java.lang.Object").asType();
        for (Element module : env.getElementsAnnotatedWith(Module.class)) {
            if (module.getKind().equals(ElementKind.CLASS)) {
                TypeElement moduleType = (TypeElement) module;
                if (!moduleType.getSuperclass().equals(objectType)) {
                    error("Modules must not extend from other classes: " + Util.elementToString(module), module);
                }
                String moduleName = moduleType.getQualifiedName().toString();
                if (!result.containsKey(moduleName)) {
                    result.put(moduleName, new ArrayList());
                }
            } else {
                error("Modules must be classes: " + Util.elementToString(module), module);
            }
        }
        return result;
    }

    private Set<? extends Element> findProvidesMethods(RoundEnvironment env) {
        Set<Element> result = new LinkedHashSet();
        result.addAll(env.getElementsAnnotatedWith(Provides.class));
        return result;
    }

    private void generateModuleAdapter(Writer ioWriter, String adapterName, TypeElement type, Map<String, Object> module, List<ExecutableElement> providerMethods) throws IOException {
        if (module == null) {
            error(type + " has @Provides methods but no @Module annotation", type);
            return;
        }
        Object[] staticInjections = (Object[]) module.get("staticInjections");
        Object[] injects = (Object[]) module.get("injects");
        Object[] includes = (Object[]) module.get("includes");
        boolean overrides = ((Boolean) module.get("overrides")).booleanValue();
        boolean complete = ((Boolean) module.get("complete")).booleanValue();
        boolean library = ((Boolean) module.get("library")).booleanValue();
        JavaWriter writer = new JavaWriter(ioWriter);
        boolean multibindings = checkForMultibindings(providerMethods);
        boolean providerMethodDependencies = checkForDependencies(providerMethods);
        writer.emitSingleLineComment("Code generated by dagger-compiler.  Do not edit.", new Object[0]);
        writer.emitPackage(Util.getPackage(type).getQualifiedName().toString());
        writer.emitImports(findImports(multibindings, !providerMethods.isEmpty(), providerMethodDependencies));
        String typeName = type.getQualifiedName().toString();
        writer.emitEmptyLine();
        writer.emitJavadoc("A manager of modules and provides adapters allowing for proper linking and\ninstance provision of types served by {@code @Provides} methods.", new Object[0]);
        writer.beginType(adapterName, "class", EnumSet.of(Modifier.PUBLIC, Modifier.FINAL), JavaWriter.type(ModuleAdapter.class, new String[]{typeName}), new String[0]);
        StringBuilder injectsField = new StringBuilder().append("{ ");
        for (TypeMirror typeMirror : injects) {
            injectsField.append(JavaWriter.stringLiteral(Util.isInterface(typeMirror) ? GeneratorKeys.get(typeMirror) : GeneratorKeys.rawMembersKey(typeMirror))).append(", ");
        }
        injectsField.append("}");
        writer.emitField("String[]", "INJECTS", EnumSet.of(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL), injectsField.toString());
        StringBuilder staticInjectionsField = new StringBuilder().append("{ ");
        for (Object staticInjection : staticInjections) {
            staticInjectionsField.append(Util.typeToString((TypeMirror) staticInjection)).append(".class, ");
        }
        staticInjectionsField.append("}");
        writer.emitField("Class<?>[]", "STATIC_INJECTIONS", EnumSet.of(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL), staticInjectionsField.toString());
        StringBuilder includesField = new StringBuilder().append("{ ");
        for (Object include : includes) {
            if (include instanceof TypeMirror) {
                includesField.append(Util.typeToString((TypeMirror) include)).append(".class, ");
            } else {
                this.processingEnv.getMessager().printMessage(Kind.WARNING, "Unexpected value: " + include + " in includes of " + type, type);
            }
        }
        includesField.append("}");
        writer.emitField("Class<?>[]", "INCLUDES", EnumSet.of(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL), includesField.toString());
        writer.emitEmptyLine();
        writer.beginMethod(null, adapterName, EnumSet.of(Modifier.PUBLIC), new String[0]);
        writer.emitStatement("super(%s.class, INJECTS, STATIC_INJECTIONS, %s /*overrides*/, INCLUDES, %s /*complete*/, %s /*library*/)", new Object[]{typeName, Boolean.valueOf(overrides), Boolean.valueOf(complete), Boolean.valueOf(library)});
        writer.endMethod();
        ExecutableElement noArgsConstructor = Util.getNoArgsConstructor(type);
        if (noArgsConstructor != null && Util.isCallableConstructor(noArgsConstructor)) {
            writer.emitEmptyLine();
            writer.emitAnnotation(Override.class);
            writer.beginMethod(typeName, "newModule", EnumSet.of(Modifier.PUBLIC), new String[0]);
            writer.emitStatement("return new %s()", new Object[]{typeName});
            writer.endMethod();
        }
        Map<ExecutableElement, String> methodToClassName = new LinkedHashMap();
        Map<String, AtomicInteger> methodNameToNextId = new LinkedHashMap();
        if (!providerMethods.isEmpty()) {
            writer.emitEmptyLine();
            writer.emitJavadoc("Used internally obtain dependency information, such as for cyclical\ngraph detection.", new Object[0]);
            writer.emitAnnotation(Override.class);
            writer.beginMethod("void", "getBindings", EnumSet.of(Modifier.PUBLIC), new String[]{BINDINGS_MAP, "bindings", typeName, "module"});
            for (ExecutableElement providerMethod : providerMethods) {
                Provides provides = (Provides) providerMethod.getAnnotation(Provides.class);
                switch (1.$SwitchMap$dagger$Provides$Type[provides.type().ordinal()]) {
                    case SQLiteDatabase.OPEN_READONLY /*1*/:
                        writer.emitStatement("bindings.contributeProvidesBinding(%s, new %s(module))", new Object[]{JavaWriter.stringLiteral(GeneratorKeys.get(providerMethod)), bindingClassName(providerMethod, methodToClassName, methodNameToNextId)});
                        break;
                    case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                        writer.emitStatement("SetBinding.add(bindings, %s, new %s(module))", new Object[]{JavaWriter.stringLiteral(GeneratorKeys.getSetKey(providerMethod)), bindingClassName(providerMethod, methodToClassName, methodNameToNextId)});
                        break;
                    case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                        writer.emitStatement("SetBinding.add(bindings, %s, new %s(module))", new Object[]{JavaWriter.stringLiteral(GeneratorKeys.get(providerMethod)), bindingClassName(providerMethod, methodToClassName, methodNameToNextId)});
                        break;
                    default:
                        throw new AssertionError("Unknown @Provides type " + provides.type());
                }
            }
            writer.endMethod();
        }
        for (ExecutableElement providerMethod2 : providerMethods) {
            generateProvidesAdapter(writer, providerMethod2, methodToClassName, methodNameToNextId, library);
        }
        writer.endType();
        writer.close();
    }

    private Set<String> findImports(boolean multibindings, boolean providers, boolean dependencies) {
        Set<String> imports = new LinkedHashSet();
        imports.add(ModuleAdapter.class.getCanonicalName());
        if (providers) {
            imports.add(BindingsGroup.class.getCanonicalName());
            imports.add(Provider.class.getCanonicalName());
            imports.add(ProvidesBinding.class.getCanonicalName());
        }
        if (dependencies) {
            imports.add(Linker.class.getCanonicalName());
            imports.add(Set.class.getCanonicalName());
            imports.add(Binding.class.getCanonicalName());
        }
        if (multibindings) {
            imports.add(SetBinding.class.getCanonicalName());
        }
        return imports;
    }

    private boolean checkForDependencies(List<ExecutableElement> providerMethods) {
        for (ExecutableElement element : providerMethods) {
            if (!element.getParameters().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkForMultibindings(List<ExecutableElement> providerMethods) {
        for (ExecutableElement element : providerMethods) {
            Type providesType = ((Provides) element.getAnnotation(Provides.class)).type();
            if (providesType != Type.SET) {
                if (providesType == Type.SET_VALUES) {
                }
            }
            return true;
        }
        return false;
    }

    private String bindingClassName(ExecutableElement providerMethod, Map<ExecutableElement, String> methodToClassName, Map<String, AtomicInteger> methodNameToNextId) {
        String className = (String) methodToClassName.get(providerMethod);
        if (className != null) {
            return className;
        }
        String methodName = providerMethod.getSimpleName().toString();
        String suffix = BuildConfig.FLAVOR;
        AtomicInteger id = (AtomicInteger) methodNameToNextId.get(methodName);
        if (id == null) {
            methodNameToNextId.put(methodName, new AtomicInteger(2));
        } else {
            suffix = id.toString();
            id.incrementAndGet();
        }
        className = (Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1)) + "ProvidesAdapter" + suffix;
        methodToClassName.put(providerMethod, className);
        return className;
    }

    private void generateProvidesAdapter(JavaWriter writer, ExecutableElement providerMethod, Map<ExecutableElement, String> methodToClassName, Map<String, AtomicInteger> methodNameToNextId, boolean library) throws IOException {
        String methodName = providerMethod.getSimpleName().toString();
        String moduleType = Util.typeToString(providerMethod.getEnclosingElement().asType());
        String className = bindingClassName(providerMethod, methodToClassName, methodNameToNextId);
        String returnType = Util.typeToString(providerMethod.getReturnType());
        List<? extends VariableElement> parameters = providerMethod.getParameters();
        boolean dependent = !parameters.isEmpty();
        writer.emitEmptyLine();
        writer.emitJavadoc(AdapterJavadocs.bindingTypeDocs(returnType, false, false, dependent), new Object[0]);
        Set of = EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);
        String type = JavaWriter.type(ProvidesBinding.class, new String[]{returnType});
        String[] strArr = new String[1];
        strArr[0] = JavaWriter.type(Provider.class, new String[]{returnType});
        writer.beginType(className, "class", of, type, strArr);
        writer.emitField(moduleType, "module", EnumSet.of(Modifier.PRIVATE, Modifier.FINAL));
        Iterator i$ = parameters.iterator();
        while (i$.hasNext()) {
            writer.emitField(JavaWriter.type(Binding.class, new String[]{Util.typeToString(((Element) i$.next()).asType())}), parameterName((Element) i$.next()), EnumSet.of(Modifier.PRIVATE));
        }
        writer.emitEmptyLine();
        writer.beginMethod(null, className, EnumSet.of(Modifier.PUBLIC), new String[]{moduleType, "module"});
        boolean singleton = providerMethod.getAnnotation(Singleton.class) != null;
        String str = "super(%s, %s, %s, %s)";
        Object[] objArr = new Object[4];
        objArr[0] = JavaWriter.stringLiteral(GeneratorKeys.get(providerMethod));
        objArr[1] = singleton ? "IS_SINGLETON" : "NOT_SINGLETON";
        objArr[2] = JavaWriter.stringLiteral(moduleType);
        objArr[3] = JavaWriter.stringLiteral(methodName);
        writer.emitStatement(str, objArr);
        writer.emitStatement("this.module = module", new Object[0]);
        writer.emitStatement("setLibrary(%s)", new Object[]{Boolean.valueOf(library)});
        writer.endMethod();
        if (dependent) {
            writer.emitEmptyLine();
            writer.emitJavadoc("Used internally to link bindings/providers together at run time\naccording to their dependency graph.", new Object[0]);
            writer.emitAnnotation(Override.class);
            writer.emitAnnotation(SuppressWarnings.class, JavaWriter.stringLiteral("unchecked"));
            writer.beginMethod("void", "attach", EnumSet.of(Modifier.PUBLIC), new String[]{Linker.class.getCanonicalName(), "linker"});
            for (VariableElement parameter : parameters) {
                String parameterKey = GeneratorKeys.get(parameter);
                Object[] objArr2 = new Object[4];
                objArr2[0] = parameterName(parameter);
                objArr2[1] = writer.compressType(JavaWriter.type(Binding.class, new String[]{Util.typeToString(parameter.asType())}));
                objArr2[2] = JavaWriter.stringLiteral(parameterKey);
                objArr2[3] = writer.compressType(moduleType);
                writer.emitStatement("%s = (%s) linker.requestBinding(%s, %s.class, getClass().getClassLoader())", objArr2);
            }
            writer.endMethod();
            writer.emitEmptyLine();
            writer.emitJavadoc("Used internally obtain dependency information, such as for cyclical\ngraph detection.", new Object[0]);
            writer.emitAnnotation(Override.class);
            String setOfBindings = JavaWriter.type(Set.class, new String[]{"Binding<?>"});
            writer.beginMethod("void", "getDependencies", EnumSet.of(Modifier.PUBLIC), new String[]{setOfBindings, "getBindings", setOfBindings, "injectMembersBindings"});
            i$ = parameters.iterator();
            while (i$.hasNext()) {
                writer.emitStatement("getBindings.add(%s)", new Object[]{parameterName((Element) i$.next())});
            }
            writer.endMethod();
        }
        writer.emitEmptyLine();
        writer.emitJavadoc("Returns the fully provisioned instance satisfying the contract for\n{@code Provider<%s>}.", new Object[]{returnType});
        writer.emitAnnotation(Override.class);
        writer.beginMethod(returnType, "get", EnumSet.of(Modifier.PUBLIC), new String[0]);
        StringBuilder args = new StringBuilder();
        boolean first = true;
        i$ = parameters.iterator();
        while (i$.hasNext()) {
            Element parameter2 = (Element) i$.next();
            if (first) {
                first = false;
            } else {
                args.append(", ");
            }
            args.append(String.format("%s.get()", new Object[]{parameterName(parameter2)}));
        }
        writer.emitStatement("return module.%s(%s)", new Object[]{methodName, args.toString()});
        writer.endMethod();
        writer.endType();
    }

    private String parameterName(Element parameter) {
        if (parameter.getSimpleName().contentEquals("module")) {
            return "parameter_" + parameter.getSimpleName().toString();
        }
        return parameter.getSimpleName().toString();
    }
}
