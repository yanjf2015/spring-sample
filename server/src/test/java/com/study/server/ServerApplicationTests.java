package com.study.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Map;

@SpringBootTest
class ServerApplicationTests {

	@Autowired
	private ApplicationContext context;
	@Test
	void contextLoads() {
		Map<String, Object> beanMap = context.getBeansWithAnnotation(RestController.class);
		for (Map.Entry<String, Object> bean : beanMap.entrySet()) {
			System.out.println(bean.getValue().getClass());
			Method[] methods = ReflectionUtils.getDeclaredMethods(bean.getValue().getClass());
			for (Method method : methods) {
				System.out.println(method.getName());
				Parameter[] parameters = method.getParameters();
				for (Parameter parameter : parameters) {
					System.out.println(parameter.getName() + ":"  + parameter.getParameterizedType().getTypeName());
				}
			}
		}
		System.out.println(context.getApplicationName());
	}

}
