package tech.devinhouse.pharmacymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PharmacymanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmacymanagementApplication.class, args);
    }

}
