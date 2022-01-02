package com.calculator.incometax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.calculator.incometax.model.Donation;
import com.calculator.incometax.model.SRS;
import com.calculator.incometax.model.Tax;
import com.calculator.incometax.properties.LoginProperties;
import com.calculator.incometax.repository.DonationRepository;
import com.calculator.incometax.repository.SRSRepository;
import com.calculator.incometax.repository.TaxRepository;
import com.vaadin.flow.component.dependency.NpmPackage;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@EnableConfigurationProperties(LoginProperties.class)
@NpmPackage(value = "lumo-css-framework", version = "^4.0.10")
public class IncometaxApplication {

	private static final Logger log = LoggerFactory.getLogger(IncometaxApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(IncometaxApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(TaxRepository taxRepository, DonationRepository donationRepository, SRSRepository srsRepository) {
		return (args) -> {
			// init incometax rate
			taxRepository.save(new Tax("0", "20000", "0.0"));
			taxRepository.save(new Tax("20000", "30000", "2.0"));
			taxRepository.save(new Tax("30000", "40000", "3.5"));
			taxRepository.save(new Tax("40000", "80000", "7.0"));
			taxRepository.save(new Tax("80000", "120000", "11.5"));
			taxRepository.save(new Tax("120000", "160000", "15.0"));
			taxRepository.save(new Tax("160000", "200000", "18.0"));
			taxRepository.save(new Tax("200000", "240000", "19.0"));
			taxRepository.save(new Tax("240000", "280000", "19.5"));
			taxRepository.save(new Tax("280000", "320000", "20.0"));
			taxRepository.save(new Tax("320000", "10000000", "22.0"));
			
			donationRepository.save(new Donation("2.5"));

			srsRepository.save(new SRS("1.0"));
			
			// fetch all taxes
			log.info("Tax found with findAll():");
			log.info("-------------------------------");
			for (Tax tax : taxRepository.findAll()) {
				log.info(tax.toString());
			}
			log.info("");

			// fetch all donations
			log.info("Donation found with findAll():");
			log.info("-------------------------------");
			for (Donation donation : donationRepository.findAll()) {
				log.info(donation.toString());
			}
			log.info("");

			// fetch all srses
			log.info("SRS found with findAll():");
			log.info("-------------------------------");
			for (SRS srs : srsRepository.findAll()) {
				log.info(srs.toString());
			}
			log.info("");
		};
	}
	
}
