package com.javarush.task.task37.task3709.connectors;

import com.javarush.task.task37.task3709.security.SecurityChecker;
import com.javarush.task.task37.task3709.security.SecurityCheckerImpl;

public class SecurityProxyConnector implements Connector {

	SecurityChecker checker;
	SimpleConnector connector;

	public SecurityProxyConnector(String resourceString) {
		this.checker = new SecurityCheckerImpl();
		this.connector = new SimpleConnector(resourceString);
	}

	@Override
	public void connect() {
		if (checker.performSecurityCheck()) {
			connector.connect();
		}
	}
}
