package com.wpay.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@ServletComponentScan
@SpringBootApplication
public class WpayCommonApplication {

	public static void main(String[] args) {
		SpringApplication.run(WpayCommonApplication.class, args);
	}

}
