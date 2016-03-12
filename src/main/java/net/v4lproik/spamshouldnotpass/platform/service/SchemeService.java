package net.v4lproik.spamshouldnotpass.platform.service;

import com.google.common.collect.Lists;
import javassist.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SchemeService {

    private static Logger log = Logger.getLogger(SchemeService.class);

    private final String ALLOW_CHAR_VARIABLES = "^\\w+$";
    private final List<String> types = Lists.newArrayList("java.lang.String", "java.lang.Integer", "java.lang.Boolean");

    public boolean isSchemeValid(Map<String, List<String>> map){

        Map<String, List<String>> collect = map.entrySet()
                .parallelStream()
                .filter(x -> types.contains(x.getKey()) && this.matches(x.getValue()).equals(x.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (map.equals(collect)){
            return true;
        }

        return false;
    }

    public Map<Class<?>, List<String>> transformProperties(Map<String, List<String>> map) throws ClassNotFoundException {
        boolean valid = false;

        Map<Class<?>, List<String>> collect = map.entrySet()
                .parallelStream()
                .collect(Collectors.toMap(e -> {
                    try {
                        return Class.forName(e.getKey());
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    return null;
                }, e -> e.getValue()));

        return collect;
    }

    public static Class generate(String className, Map<Class<?>, List<String>>  properties) throws NotFoundException,
            CannotCompileException {

        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass(className);

        for (Map.Entry<Class<?>, List<String>> entry : properties.entrySet()) {

            for (String variableName:entry.getValue()){

                cc.addField(new CtField(resolveCtClass(entry.getKey()), variableName, cc));

                cc.addMethod(generateGetter(cc, variableName, entry.getKey()));

                cc.addMethod(generateSetter(cc, variableName, entry.getKey()));
            }
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

    private List<String> matches(List<String> arr){
        List<String> allMatches = new ArrayList<String>();
        for (String str:arr){
           if (str.matches(ALLOW_CHAR_VARIABLES)) allMatches.add(str);
        }
        return allMatches;
    }

}
