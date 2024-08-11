/*package com.tripleS.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}*/
package com.tripleS.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {

		//이거 빼세요
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "local");

		SpringApplication.run(ServerApplication.class, args);
	}
}

