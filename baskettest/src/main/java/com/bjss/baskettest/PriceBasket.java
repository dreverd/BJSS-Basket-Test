package com.bjss.baskettest;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PriceBasket implements CommandLineRunner {

	public static void main(String[] args) {
		try {
			SpringApplication app = new SpringApplication(PriceBasket.class);
			app.setBannerMode(Banner.Mode.OFF);
		    app.run(args);
		} catch(Exception ex) {
            System.out.println("Problem loading application");			
		}
	}

	@Override
	public void run(String... args) throws Exception {
        if (args.length > 0) {
            System.out.println("Adding to basket: " + args);
        } else {
            System.out.println("No items to add to basket");
        }

        System.exit(0);		
	}
}
