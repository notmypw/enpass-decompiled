package com.google.api.client.http;

import com.github.clans.fab.BuildConfig;
import com.google.api.client.repackaged.com.google.common.base.Splitter;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Types;
import com.google.api.client.util.escape.CharEscapers;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

public class UriTemplate {
    private static final String COMPOSITE_NON_EXPLODE_JOINER = ",";
    static final Map<Character, CompositeOutput> COMPOSITE_PREFIXES = new HashMap();

    private enum CompositeOutput {
        PLUS(Character.valueOf('+'), BuildConfig.FLAVOR, UriTemplate.COMPOSITE_NON_EXPLODE_JOINER, false, true),
        HASH(Character.valueOf('#'), "#", UriTemplate.COMPOSITE_NON_EXPLODE_JOINER, false, true),
        DOT(Character.valueOf('.'), ".", ".", false, false),
        FORWARD_SLASH(Character.valueOf('/'), "/", "/", false, false),
        SEMI_COLON(Character.valueOf(';'), ";", ";", true, false),
        QUERY(Character.valueOf('?'), "?", "&", true, false),
        AMP(Character.valueOf('&'), "&", "&", true, false),
        SIMPLE(null, BuildConfig.FLAVOR, UriTemplate.COMPOSITE_NON_EXPLODE_JOINER, false, false);
        
        private final String explodeJoiner;
        private final String outputPrefix;
        private final Character propertyPrefix;
        private final boolean requiresVarAssignment;
        private final boolean reservedExpansion;

        private CompositeOutput(Character propertyPrefix, String outputPrefix, String explodeJoiner, boolean requiresVarAssignment, boolean reservedExpansion) {
            this.propertyPrefix = propertyPrefix;
            this.outputPrefix = (String) Preconditions.checkNotNull(outputPrefix);
            this.explodeJoiner = (String) Preconditions.checkNotNull(explodeJoiner);
            this.requiresVarAssignment = requiresVarAssignment;
            this.reservedExpansion = reservedExpansion;
            if (propertyPrefix != null) {
                UriTemplate.COMPOSITE_PREFIXES.put(propertyPrefix, this);
            }
        }

        String getOutputPrefix() {
            return this.outputPrefix;
        }

        String getExplodeJoiner() {
            return this.explodeJoiner;
        }

        boolean requiresVarAssignment() {
            return this.requiresVarAssignment;
        }

        int getVarNameStartIndex() {
            return this.propertyPrefix == null ? 0 : 1;
        }

        String getEncodedValue(String value) {
            if (this.reservedExpansion) {
                return CharEscapers.escapeUriPath(value);
            }
            return CharEscapers.escapeUri(value);
        }

        boolean getReservedExpansion() {
            return this.reservedExpansion;
        }
    }

    static {
        CompositeOutput.values();
    }

    static CompositeOutput getCompositeOutput(String propertyName) {
        CompositeOutput compositeOutput = (CompositeOutput) COMPOSITE_PREFIXES.get(Character.valueOf(propertyName.charAt(0)));
        return compositeOutput == null ? CompositeOutput.SIMPLE : compositeOutput;
    }

    private static Map<String, Object> getMap(Object obj) {
        Map<String, Object> map = new LinkedHashMap();
        for (Entry<String, Object> entry : Data.mapOf(obj).entrySet()) {
            Object value = entry.getValue();
            if (!(value == null || Data.isNull(value))) {
                map.put(entry.getKey(), value);
            }
        }
        return map;
    }

    public static String expand(String baseUrl, String uriTemplate, Object parameters, boolean addUnusedParamsAsQueryParams) {
        String pathUri;
        if (uriTemplate.startsWith("/")) {
            GenericUrl url = new GenericUrl(baseUrl);
            url.setRawPath(null);
            pathUri = url.build() + uriTemplate;
        } else if (uriTemplate.startsWith("http://") || uriTemplate.startsWith("https://")) {
            pathUri = uriTemplate;
        } else {
            pathUri = baseUrl + uriTemplate;
        }
        return expand(pathUri, parameters, addUnusedParamsAsQueryParams);
    }

    public static String expand(String pathUri, Object parameters, boolean addUnusedParamsAsQueryParams) {
        Map<String, Object> variableMap = getMap(parameters);
        StringBuilder pathBuf = new StringBuilder();
        int cur = 0;
        int length = pathUri.length();
        while (cur < length) {
            int next = pathUri.indexOf(123, cur);
            if (next != -1) {
                pathBuf.append(pathUri.substring(cur, next));
                int close = pathUri.indexOf(125, next + 2);
                cur = close + 1;
                String templates = pathUri.substring(next + 1, close);
                CompositeOutput compositeOutput = getCompositeOutput(templates);
                ListIterator<String> templateIterator = Splitter.on(',').splitToList(templates).listIterator();
                boolean isFirstParameter = true;
                while (templateIterator.hasNext()) {
                    String template = (String) templateIterator.next();
                    boolean containsExplodeModifier = template.endsWith("*");
                    int varNameStartIndex = templateIterator.nextIndex() == 1 ? compositeOutput.getVarNameStartIndex() : 0;
                    int varNameEndIndex = template.length();
                    if (containsExplodeModifier) {
                        varNameEndIndex--;
                    }
                    String varName = template.substring(varNameStartIndex, varNameEndIndex);
                    String value = variableMap.remove(varName);
                    if (value != null) {
                        Object value2;
                        if (isFirstParameter) {
                            pathBuf.append(compositeOutput.getOutputPrefix());
                            isFirstParameter = false;
                        } else {
                            pathBuf.append(compositeOutput.getExplodeJoiner());
                        }
                        if (value instanceof Iterator) {
                            value2 = getListPropertyValue(varName, (Iterator) value, containsExplodeModifier, compositeOutput);
                        } else if ((value instanceof Iterable) || value.getClass().isArray()) {
                            value2 = getListPropertyValue(varName, Types.iterableOf(value).iterator(), containsExplodeModifier, compositeOutput);
                        } else if (value.getClass().isEnum()) {
                            if (FieldInfo.of((Enum) value).getName() != null) {
                                if (compositeOutput.requiresVarAssignment()) {
                                    value2 = String.format("%s=%s", new Object[]{varName, value});
                                }
                                value2 = CharEscapers.escapeUriPath(value2.toString());
                            }
                        } else if (Data.isValueOfPrimitiveType(value)) {
                            if (compositeOutput.requiresVarAssignment()) {
                                value = String.format("%s=%s", new Object[]{varName, value});
                            }
                            if (compositeOutput.getReservedExpansion()) {
                                value2 = CharEscapers.escapeUriPathWithoutReserved(value.toString());
                            } else {
                                value2 = CharEscapers.escapeUriPath(value.toString());
                            }
                        } else {
                            value2 = getMapPropertyValue(varName, getMap(value), containsExplodeModifier, compositeOutput);
                        }
                        pathBuf.append(value2);
                    }
                }
            } else if (cur == 0 && !addUnusedParamsAsQueryParams) {
                return pathUri;
            } else {
                pathBuf.append(pathUri.substring(cur));
                if (addUnusedParamsAsQueryParams) {
                    GenericUrl.addQueryParams(variableMap.entrySet(), pathBuf);
                }
                return pathBuf.toString();
            }
        }
        if (addUnusedParamsAsQueryParams) {
            GenericUrl.addQueryParams(variableMap.entrySet(), pathBuf);
        }
        return pathBuf.toString();
    }

    private static String getListPropertyValue(String varName, Iterator<?> iterator, boolean containsExplodeModifier, CompositeOutput compositeOutput) {
        if (!iterator.hasNext()) {
            return BuildConfig.FLAVOR;
        }
        String joiner;
        StringBuilder retBuf = new StringBuilder();
        if (containsExplodeModifier) {
            joiner = compositeOutput.getExplodeJoiner();
        } else {
            joiner = COMPOSITE_NON_EXPLODE_JOINER;
            if (compositeOutput.requiresVarAssignment()) {
                retBuf.append(CharEscapers.escapeUriPath(varName));
                retBuf.append("=");
            }
        }
        while (iterator.hasNext()) {
            if (containsExplodeModifier && compositeOutput.requiresVarAssignment()) {
                retBuf.append(CharEscapers.escapeUriPath(varName));
                retBuf.append("=");
            }
            retBuf.append(compositeOutput.getEncodedValue(iterator.next().toString()));
            if (iterator.hasNext()) {
                retBuf.append(joiner);
            }
        }
        return retBuf.toString();
    }

    private static String getMapPropertyValue(String varName, Map<String, Object> map, boolean containsExplodeModifier, CompositeOutput compositeOutput) {
        if (map.isEmpty()) {
            return BuildConfig.FLAVOR;
        }
        String joiner;
        String mapElementsJoiner;
        StringBuilder retBuf = new StringBuilder();
        if (containsExplodeModifier) {
            joiner = compositeOutput.getExplodeJoiner();
            mapElementsJoiner = "=";
        } else {
            joiner = COMPOSITE_NON_EXPLODE_JOINER;
            mapElementsJoiner = COMPOSITE_NON_EXPLODE_JOINER;
            if (compositeOutput.requiresVarAssignment()) {
                retBuf.append(CharEscapers.escapeUriPath(varName));
                retBuf.append("=");
            }
        }
        Iterator<Entry<String, Object>> mapIterator = map.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Entry<String, Object> entry = (Entry) mapIterator.next();
            String encodedKey = compositeOutput.getEncodedValue((String) entry.getKey());
            String encodedValue = compositeOutput.getEncodedValue(entry.getValue().toString());
            retBuf.append(encodedKey);
            retBuf.append(mapElementsJoiner);
            retBuf.append(encodedValue);
            if (mapIterator.hasNext()) {
                retBuf.append(joiner);
            }
        }
        return retBuf.toString();
    }
}
