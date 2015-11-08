package net.v4lproik.spamshouldnotpass.platform.service.api;

import com.google.common.collect.Lists;
import javassist.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SchemeService {

    private static Logger log = Logger.getLogger(SchemeService.class);

    private final String ALLOW_CHAR_VARIABLES = "^\\w+$";
    private final List<String> types = Lists.newArrayList("String");

    public boolean isSchemeValid(Map<String, String> map){

        Map<String, String> collect = map.entrySet()
                .parallelStream()
                .filter(x -> types.contains(x.getKey()) && x.getValue().matches(ALLOW_CHAR_VARIABLES))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (map.equals(collect)){
            return true;
        }

        return false;
    }

    public Map<String, Class<?>> transformProperties(Map<String, String> map) throws ClassNotFoundException {
        boolean valid = false;

        Map<String, Class<?>> collect = map.entrySet()
                .parallelStream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    try {
                        return Class.forName(e.getValue());
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    return null;
                }));

        return collect;
    }

    public static Class generate(String className, Map<String, Class<?>>  properties) throws NotFoundException,
            CannotCompileException {

        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass(className);

        for (Map.Entry<String, Class<?>> entry : properties.entrySet()) {

            cc.addField(new CtField(resolveCtClass(entry.getValue()), entry.getKey(), cc));

            cc.addMethod(generateGetter(cc, entry.getKey(), entry.getValue()));

            cc.addMethod(generateSetter(cc, entry.getKey(), entry.getValue()));
        }

        return cc.toClass();
    }

    private static CtMethod generateGetter(CtClass declaringClass, String fieldName, Class fieldClass)
            throws CannotCompileException {

        String getterName = "get" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);

        StringBuffer sb = new StringBuffer();
        sb.append("public ").append(fieldClass.getName()).append(" ")
                .append(getterName).append("(){").append("return this.")
                .append(fieldName).append(";").append("}");
        return CtMethod.make(sb.toString(), declaringClass);
    }

    private static CtMethod generateSetter(CtClass declaringClass, String fieldName, Class fieldClass)
            throws CannotCompileException {

        String setterName = "set" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);

        StringBuffer sb = new StringBuffer();
        sb.append("public void ").append(setterName).append("(")
                .append(fieldClass.getName()).append(" ").append(fieldName)
                .append(")").append("{").append("this.").append(fieldName)
                .append("=").append(fieldName).append(";").append("}");
        return CtMethod.make(sb.toString(), declaringClass);
    }

    private static CtClass resolveCtClass(Class clazz) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        return pool.get(clazz.getName());
    }

}
