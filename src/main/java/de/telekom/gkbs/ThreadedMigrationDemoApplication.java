package de.telekom.gkbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.telekom.gkbs.service.MultiThreadMigrator;
import de.telekom.gkbs.service.OneThreadMigrator;

@SpringBootApplication
public class ThreadedMigrationDemoApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(ThreadedMigrationDemoApplication.class, args);
		context.getBean(OneThreadMigrator.class).migrateCarts();
		context.getBean(MultiThreadMigrator.class).migrateCarts();
		System.exit(0);
	}

}
