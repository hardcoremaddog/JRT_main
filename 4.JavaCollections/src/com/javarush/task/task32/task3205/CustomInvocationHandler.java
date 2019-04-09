package com.javarush.task.task32.task3205;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CustomInvocationHandler implements InvocationHandler {

	private SomeInterfaceWithMethods origInterface;


	public CustomInvocationHandler(SomeInterfaceWithMethods origInterface) {
		this.origInterface = origInterface;

	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.println(method.getName() + " in");
		Object obj = method.invoke(origInterface, args);


		System.out.println(method.getName() + " out");
		return obj;
	}
}
