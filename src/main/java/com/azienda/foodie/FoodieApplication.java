package com.azienda.foodie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FoodieApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(FoodieApplication.class, args);
		context.getBean("BeanAdmin");
	}

}
