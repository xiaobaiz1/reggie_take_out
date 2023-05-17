package com.dbb.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//
@Slf4j
@SpringBootApplication
@ServletComponentScan	//用于扫描 @WebFilter 过滤器注解
@EnableTransactionManagement	//启动事务注解
public class ReggieApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReggieApplication.class, args);
		log.info("启动成功");

	}

}
