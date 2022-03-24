package se.iths.corkdork;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class CorkDorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(CorkDorkApplication.class, args);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ModelMapper createModelMapper(){
        return new ModelMapper();
    }

}
