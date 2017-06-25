package com.bjss.baskettest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bjss.baskettest.basket.api.BasketService;
import com.bjss.baskettest.basket.exceptions.ProductNotFoundException;
import com.bjss.baskettest.offer.OfferDetails;
import com.bjss.baskettest.offer.OfferFactory;

/**
 * Entry class for the application, this is a spring boot application that
 * uses a command line runner to drive the application once the spring
 * container has loaded the application context and completed bean creation.
 */
@SpringBootApplication
public class PriceBasket implements CommandLineRunner {

	@Autowired
	private BasketService basketService;
	
	@Autowired
	private OfferFactory offers;
	
	@Autowired
	ConsoleOutput outputter;
	
	public static void main(String[] args) {
		try {
			SpringApplication app = new SpringApplication(PriceBasket.class);
			app.setBannerMode(Banner.Mode.OFF);
		    app.run(args);
		} catch(Exception ex) {
            System.out.println("Problem loading application");			
		}
	}

	/**
	 * Run method called after spring framework loaded. Processes the
	 * command line args:
	 * 		Add products to basket if found
	 * 		Apply any discounts that are applicable
	 * 		Output totals to screen
	 */
	@Override
	public void run(String... args) throws Exception {
        if (args.length > 0) {
            Arrays.asList(args).forEach(arg -> {
				try {
					basketService.addToBasket(arg);
				} catch (ProductNotFoundException e) {
					System.out.println(e.getMessage());
			        System.exit(0);		
				}
			});
            
            List<OfferDetails> offerDetails = offers.getLeadOffer().process(basketService.getBasket());
            
            outputter.output(basketService.getBasket(), offerDetails);;
        } else {
            System.out.println("No items to add to basket");
        }

        System.exit(0);		
	}
}
