package cn.fxmes.barcodeserver;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableCaching
@EnableAutoConfiguration
@ComponentScan(basePackages = "cn.fxmes.barcodeserver")
@ImportResource("classpath*:spring-*.xml")
public class Application {
	private static Logger log = Logger.getLogger(Application.class);
	
	public static void main(String[] args) {
		log.info("MES Scanner Application Startup.");
		SpringApplication.run(Application.class, args);
	}
}
