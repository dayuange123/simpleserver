package club.dayuange.entry;

import club.dayuange.annotation.PageEngine;
import club.dayuange.annotation.RequestMethod;
import io.netty.handler.codec.http.HttpRequest;


import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Map.Entry;


/**
 * URL映射实体
 */
public class MyRequestMapping {
	/**
	 * 解析URL对应的类
	 */
	final Class<?> clazz;
	/**
	 * 对应的方法
	 */
	final Method method;

	final RequestMethod requestMethod;
	/**
	 * 该方法的参数名
	 */
	final String value;
	/**
	 * 模板引擎
	 */
	final PageEngine engine;

	public MyRequestMapping(final Class<?> clazz, final Method method, final PageEngine engine,
							final String value,RequestMethod requestMethod) {
		this.clazz = clazz;
		this.method = method;
		this.engine = engine;
		this.value = value;
		this.requestMethod=requestMethod;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Method getMethod() {
		return method;
	}

	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	public String getValue() {
		return value;
	}

	public PageEngine getEngine() {
		return engine;
	}

	@Override
	public String toString() {
		return "MyRequestMapping{" +
				"clazz=" + clazz +
				", method=" + method +
				", requestMethod=" + requestMethod +
				", value='" + value + '\'' +
				", engine=" + engine +
				'}';
	}
}