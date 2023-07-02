package com.example.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseApplication {

	public static void main(String[] args) {
//		Package securityPackage = Authentication.class.getPackage();
//		String version = securityPackage.getImplementationVersion();
//		System.out.println("Spring Security Version: " + version);


		SpringApplication.run(CourseApplication.class, args);
	}

}
