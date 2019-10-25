package com.zzr;

import com.zzr.config.GlobalConfigInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZkConfigApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ZkConfigApplication.class);
		application.addInitializers(new GlobalConfigInit());
		application.run(args);
	}

}
