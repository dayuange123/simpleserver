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
	/**
	 * 根据URL以及提交方式解析出对应的Mapping
	 * 
	 * @param request
	 * @param uri
	 * @return
	 */
	static MyRequestMapping get(final HttpRequest request, final String uri) {
		MyRequestMapping mapping = null;
		return mapping;
	}
	
	/**
	 * 通配URL遍历
	 * @param uri
	 * @param mapping
	 * @return
	 */
	private static MyRequestMapping get(String uri, Map<String, MyRequestMapping> mapping) {
		for (Entry<String, MyRequestMapping> item : mapping.entrySet())
			if (uri.matches(item.getKey()))
				return item.getValue();

		return null;
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