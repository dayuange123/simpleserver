package test;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import javassist.tools.reflect.Sample;
import org.junit.Test;

import java.lang.reflect.Method;
import java.time.temporal.ValueRange;

public class TestSsist {
    @Test
    public void test3() {
        try {
            //获取要操作的类对象
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.get("test.Sample1");
            //获取所有方法
            Method[] methods = Sample1.class.getDeclaredMethods();
            for (Method method : methods) {
                //获取方法的参数个数
                int count = method.getParameterCount();
                //参数类型
                Class<?>[] paramTypes = method.getParameterTypes();
                CtClass[] ctParams = new CtClass[count];
                for (int i = 0; i < count; i++) {
                    ctParams[i] = pool.getCtClass(paramTypes[i].getName());
                }
                CtMethod ctMethod = ctClass.getDeclaredMethod(method.getName(), ctParams);
                //得到该方法信息类
                MethodInfo methodInfo = ctMethod.getMethodInfo();
                //获取属性变量相关
                CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
                //获取方法本地变量信息，包括方法声明和方法体内的变量
                //需注意，若方法为非静态方法，则第一个变量名为this
                LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
                int pos = Modifier.isStatic(method.getModifiers()) ? 0 : 1;
                for (int i = 0; i < count; i++) {
                    System.out.println(paramTypes[i] + "\t" + attr.variableName(i + pos));
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Sample1 {
    private String name;

    private void end(int aa, Sample1 sample1) {

    }

    public void start(String aa, int a) {
        int i = 0;
        String abc = "abc";
        System.out.println(i);
        System.out.println(aa);
        System.out.println(abc);
    }
}
