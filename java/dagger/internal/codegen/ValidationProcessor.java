package dagger.internal.codegen;

import dagger.Module;
import dagger.Provides;
import dagger.internal.codegen.Util.CodeGenerationIncompleteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import net.sqlcipher.database.SQLiteDatabase;

@SupportedAnnotationTypes({"*"})
public final class ValidationProcessor extends AbstractProcessor {
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        List<Element> allElements = new ArrayList();
        Map<Element, Element> parametersToTheirMethods = new LinkedHashMap();
        getAllElements(env, allElements, parametersToTheirMethods);
        for (Element element : allElements) {
            try {
                validateProvides(element);
                validateScoping(element);
                validateQualifiers(element, parametersToTheirMethods);
            } catch (CodeGenerationIncompleteException e) {
            }
        }
        return false;
    }

    private void validateProvides(Element element) {
        if (element.getAnnotation(Provides.class) != null && Util.getAnnotation(Module.class, element.getEnclosingElement()) == null) {
            error("@Provides methods must be declared in modules: " + Util.elementToString(element), element);
        }
    }

    private void validateQualifiers(Element element, Map<Element, Element> parametersToTheirMethods) {
        boolean suppressWarnings = element.getAnnotation(SuppressWarnings.class) != null && Arrays.asList(((SuppressWarnings) element.getAnnotation(SuppressWarnings.class)).value()).contains("qualifiers");
        int numberOfQualifiersOnElement = 0;
        for (AnnotationMirror annotation : element.getAnnotationMirrors()) {
            if (annotation.getAnnotationType().asElement().getAnnotation(Qualifier.class) != null) {
                switch (1.$SwitchMap$javax$lang$model$element$ElementKind[element.getKind().ordinal()]) {
                    case SQLiteDatabase.OPEN_READONLY /*1*/:
                        numberOfQualifiersOnElement++;
                        if (element.getAnnotation(Inject.class) == null && !suppressWarnings) {
                            warning("Dagger will ignore qualifier annotations on fields that are not annotated with @Inject: " + Util.elementToString(element), element);
                            break;
                        }
                    case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                        numberOfQualifiersOnElement++;
                        if (!(isProvidesMethod(element) || suppressWarnings)) {
                            warning("Dagger will ignore qualifier annotations on methods that are not @Provides methods: " + Util.elementToString(element), element);
                            break;
                        }
                    case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                        numberOfQualifiersOnElement++;
                        if (!(isInjectableConstructorParameter(element, parametersToTheirMethods) || isProvidesMethodParameter(element, parametersToTheirMethods) || suppressWarnings)) {
                            warning("Dagger will ignore qualifier annotations on parameters that are not @Inject constructor parameters or @Provides method parameters: " + Util.elementToString(element), element);
                            break;
                        }
                    default:
                        error("Qualifier annotations are only allowed on fields, methods, and parameters: " + Util.elementToString(element), element);
                        break;
                }
            }
        }
        if (numberOfQualifiersOnElement > 1) {
            error("Only one qualifier annotation is allowed per element: " + Util.elementToString(element), element);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void validateScoping(javax.lang.model.element.Element r8) {
        /*
        r7 = this;
        r5 = 1;
        r4 = java.lang.SuppressWarnings.class;
        r4 = r8.getAnnotation(r4);
        if (r4 == 0) goto L_0x0071;
    L_0x0009:
        r4 = java.lang.SuppressWarnings.class;
        r4 = r8.getAnnotation(r4);
        r4 = (java.lang.SuppressWarnings) r4;
        r4 = r4.value();
        r4 = java.util.Arrays.asList(r4);
        r6 = "scoping";
        r4 = r4.contains(r6);
        if (r4 == 0) goto L_0x0071;
    L_0x0021:
        r3 = r5;
    L_0x0022:
        r2 = 0;
        r4 = r8.getAnnotationMirrors();
        r1 = r4.iterator();
    L_0x002b:
        r4 = r1.hasNext();
        if (r4 == 0) goto L_0x00a7;
    L_0x0031:
        r0 = r1.next();
        r0 = (javax.lang.model.element.AnnotationMirror) r0;
        r4 = r0.getAnnotationType();
        r4 = r4.asElement();
        r6 = javax.inject.Scope.class;
        r4 = r4.getAnnotation(r6);
        if (r4 == 0) goto L_0x002b;
    L_0x0047:
        r4 = dagger.internal.codegen.ValidationProcessor.1.$SwitchMap$javax$lang$model$element$ElementKind;
        r6 = r8.getKind();
        r6 = r6.ordinal();
        r4 = r4[r6];
        switch(r4) {
            case 2: goto L_0x0073;
            case 3: goto L_0x0056;
            case 4: goto L_0x0098;
            default: goto L_0x0056;
        };
    L_0x0056:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r6 = "Scoping annotations are only allowed on concrete types and @Provides methods: ";
        r4 = r4.append(r6);
        r6 = dagger.internal.codegen.Util.elementToString(r8);
        r4 = r4.append(r6);
        r4 = r4.toString();
        r7.error(r4, r8);
        goto L_0x002b;
    L_0x0071:
        r3 = 0;
        goto L_0x0022;
    L_0x0073:
        r2 = r2 + 1;
        r4 = r7.isProvidesMethod(r8);
        if (r4 != 0) goto L_0x002b;
    L_0x007b:
        if (r3 != 0) goto L_0x002b;
    L_0x007d:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r6 = "Dagger will ignore scoping annotations on methods that are not @Provides methods: ";
        r4 = r4.append(r6);
        r6 = dagger.internal.codegen.Util.elementToString(r8);
        r4 = r4.append(r6);
        r4 = r4.toString();
        r7.warning(r4, r8);
        goto L_0x002b;
    L_0x0098:
        r4 = r8.getModifiers();
        r6 = javax.lang.model.element.Modifier.ABSTRACT;
        r4 = r4.contains(r6);
        if (r4 != 0) goto L_0x0056;
    L_0x00a4:
        r2 = r2 + 1;
        goto L_0x002b;
    L_0x00a7:
        if (r2 <= r5) goto L_0x00c3;
    L_0x00a9:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Only one scoping annotation is allowed per element: ";
        r4 = r4.append(r5);
        r5 = dagger.internal.codegen.Util.elementToString(r8);
        r4 = r4.append(r5);
        r4 = r4.toString();
        r7.error(r4, r8);
    L_0x00c3:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: dagger.internal.codegen.ValidationProcessor.validateScoping(javax.lang.model.element.Element):void");
    }

    private void getAllElements(RoundEnvironment env, List<Element> result, Map<Element, Element> parametersToTheirMethods) {
        for (Element element : env.getRootElements()) {
            addAllEnclosed(element, result, parametersToTheirMethods);
        }
    }

    private void addAllEnclosed(Element element, List<Element> result, Map<Element, Element> parametersToTheirMethods) {
        result.add(element);
        for (Element enclosed : element.getEnclosedElements()) {
            addAllEnclosed(enclosed, result, parametersToTheirMethods);
            if (enclosed.getKind() == ElementKind.METHOD || enclosed.getKind() == ElementKind.CONSTRUCTOR) {
                for (Element parameter : ((ExecutableElement) enclosed).getParameters()) {
                    result.add(parameter);
                    parametersToTheirMethods.put(parameter, enclosed);
                }
            }
        }
    }

    private boolean isProvidesMethod(Element element) {
        return element.getKind() == ElementKind.METHOD && element.getAnnotation(Provides.class) != null;
    }

    private boolean isProvidesMethodParameter(Element parameter, Map<Element, Element> parametersToTheirMethods) {
        return ((Element) parametersToTheirMethods.get(parameter)).getAnnotation(Provides.class) != null;
    }

    private boolean isInjectableConstructorParameter(Element parameter, Map<Element, Element> parametersToTheirMethods) {
        return ((Element) parametersToTheirMethods.get(parameter)).getKind() == ElementKind.CONSTRUCTOR && ((Element) parametersToTheirMethods.get(parameter)).getAnnotation(Inject.class) != null;
    }

    private void error(String msg, Element element) {
        this.processingEnv.getMessager().printMessage(Kind.ERROR, msg, element);
    }

    private void warning(String msg, Element element) {
        this.processingEnv.getMessager().printMessage(Kind.WARNING, msg, element);
    }
}
