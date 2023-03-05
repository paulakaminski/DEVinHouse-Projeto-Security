package tech.devinhouse.pharmacymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tech.devinhouse.pharmacymanagement.security.JwtTokenProvider;

@SpringBootApplication
@EnableFeignClients
public class PharmacymanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmacymanagementApplication.class, args);
        System.out.println("Colaborador: " + new JwtTokenProvider().generateToken("colaborador@usuario.com"));
        System.out.println("Gerente: " + new JwtTokenProvider().generateToken("gerente@usuario.com"));
        System.out.println("Admin: " + new JwtTokenProvider().generateToken("admin@usuario.com"));
    }

}
