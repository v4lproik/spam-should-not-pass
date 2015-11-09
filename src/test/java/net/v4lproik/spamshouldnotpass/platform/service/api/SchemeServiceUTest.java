package net.v4lproik.spamshouldnotpass.platform.service.api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SchemeServiceUTest {

    private SchemeService service;

    @Before
    public void setUp(){
        service = new SchemeService();
    }

    @Test
    public void test_isValid() throws Exception{
        service.isSchemeValid(Maps.newHashMap(ImmutableMap.of("String", "variable")));
    }

    @Test
    public void generateClass_withValidData_shouldGenerateClass() throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        final String className = "net.v4lproik.spamshouldnotpass.platform.service.api.entities.Pojo$Generated";

        Map<Class<?>, List<String>> props = new HashMap<Class<?>, List<String>>();
        props.put(Class.forName("java.lang.String"), Lists.newArrayList("documentId", "object", "content"));

        Class<?> clazz = SchemeService.generate(
                "net.v4lproik.spamshouldnotpass.platform.service.api.entities.Pojo$Generated", props);

        Object obj = clazz.newInstance();

        System.out.println("Clazz: " + clazz);
        System.out.println("Object: " + obj);
        System.out.println("Serializable? " + (obj instanceof Serializable));

        for (final Method method : clazz.getDeclaredMethods()) {
            System.out.println(method);
        }

        // set property "bar"
        clazz.getMethod("setDocumentId", String.class).invoke(obj, "documentId");
        clazz.getMethod("setObject", String.class).invoke(obj, "object");
        clazz.getMethod("setContent", String.class).invoke(obj, "content");

//        // get property "bar"
//        String result = (String) clazz.getMethod("getBar").invoke(obj);
//        System.out.println("Value for bar: " + result);

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext(obj);


        Boolean resultRule = false;
        try{
            resultRule = parser.parseExpression("documentId == 'documentIdx'").getValue(context, Boolean.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        assertEquals(resultRule, false);
    }
}