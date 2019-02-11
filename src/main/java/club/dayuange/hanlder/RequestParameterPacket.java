package club.dayuange.hanlder;

import club.dayuange.entry.MyRequestMapping;
import club.dayuange.exection.AnalysisMethodExection;
import club.dayuange.mypacket.request.DefultRequest;
import club.dayuange.mypacket.request.SimpleRequest;
import club.dayuange.mypacket.response.SimpleResponse;
import club.dayuange.scanner.MainStartup;
import club.dayuange.utils.CheckAccess;
import club.dayuange.utils.LoggerInitialization;
import club.dayuange.utils.StringUtils;
import com.sun.org.apache.regexp.internal.RE;
import io.netty.handler.codec.http.FullHttpRequest;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.logging.log4j.Logger;

import javax.jws.Oneway;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 对请求方法 参数的获取。以及方法的调用。
 */
public class RequestParameterPacket {
    static Logger logger = LoggerInitialization.logger;
    static AnalysisMethodExection exection = new AnalysisMethodExection("服务器错误 500");
    static Map<Class, Object> instance = new HashMap<>();

    public static Object callRequestMethod(String uri, FullHttpRequest fullHttpRequest, DefultRequest request1, ClassLoader c1, SimpleResponse response) throws Exception {

        MyRequestMapping mapping = MainStartup.map.get(uri);
        Class<?> clazz = mapping.getClazz();
        Method method = mapping.getMethod();
        String clazzName = clazz.getName();
        LinkedHashMap<Class, String> ptn = getParameterTypeAndName(clazzName, method);
        CheckAccess.checkMapNuLL(ptn, exection);
        Object o = instance.get(clazz);
        if (o == null) {
            o = clazz.newInstance();
            instance.put(clazz, o);
        }
        if (ptn.size() == 0) {
            //直接调用
            return method.invoke(o);
        }
        //获取参数
        Object[] objects = getAllParamter(ptn, request1, response);
//        for (Object object : objects) {
//            System.out.println(object);
//        }
        return method.invoke(o, objects);
    }

    private static Object[] getAllParamter(LinkedHashMap<Class, String> ptn, DefultRequest request1, SimpleResponse response) throws IllegalAccessException, InstantiationException {
        Object[] objects = new Object[ptn.size()];
        int index = 0;

        for (Map.Entry<Class, String> classStringEntry : ptn.entrySet()) {
            //是否匹配到对应的 参数
            Class aClass = classStringEntry.getKey();
            String value = classStringEntry.getValue();
            if (aClass.isAssignableFrom(SimpleRequest.class)) {
                objects[index++] = request1;
            } else if (aClass.isAssignableFrom(SimpleResponse.class)) {
                objects[index++] = response;
            }
            //如果是基本类型或者是包装类型。或者是string类型
            else if (setParIfIsEffective(aClass, request1.getParameter(value), index, objects)) {
                index++;
            }
            //剩下的就是其他的类型。
            else {
                Field[] fields = aClass.getDeclaredFields();
                Object obj = aClass.newInstance();
                //遍历所有的属性，将合适的值初始化。如果对象里面还是对象类型 忽略。
                for (Field field : fields) {
                    String parameter = request1.getParameter(field.getName());
                    if (parameter != null) {
                        field.setAccessible(true);
                        // System.out.println(field.getType());
                        if (field.getType().isPrimitive() || StringUtils.isPacketType(field.getType())) {
                            Object returnObj = ConvertUtils.convert(parameter, field.getType());
                            if (!returnObj.toString().equals(parameter)) {
                                throw new ClassCastException("string to " + returnObj.getClass() + "\t" +
                                        "check your par:" + field.getName());
                            }
                            // System.out.println(returnObj);
                            field.set(obj, returnObj);
                        }
                    }
                }
                objects[index++] = obj;
            }
        }
        return objects;
    }

    //将对应字符串转化为指定的 类型aClass  这个aClass 为基本类型或者包装类型 或者String
    private static boolean setParIfIsEffective(Class aClass, String parameter, int index, Object[] objects) {
        if (aClass.isPrimitive() || StringUtils.isPacketType(aClass)) {
            if (parameter != null) {
                Object returnObj = ConvertUtils.convert(parameter, aClass);
                if (!returnObj.toString().equals(parameter)) {
                    throw new ClassCastException("string to " + returnObj.getClass() + "\t" + aClass);
                }
                objects[index] = returnObj;
                return true;
            }
            if (aClass.isPrimitive()) {
                Object defultValue = StringUtils.getDefultValue(aClass);
                objects[index] = defultValue;
            } else {
                objects[index] = null;
            }
            return true;
        } else
            return false;
    }

    //对方法参数类型和 名字的解析
    private static LinkedHashMap<Class, String> getParameterTypeAndName(String clazzName, Method method) {
        try {
            //获取要操作的类对象
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.get(clazzName);
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
            LinkedHashMap<Class, String> map = new LinkedHashMap<>();
            for (int i = 0; i < count; i++) {
                map.put(paramTypes[i], attr.variableName(i + pos));
                /**
                 * mark
                 */
                logger.info(paramTypes[i] + "\t" + attr.variableName(i + pos));
            }
            return map;
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}