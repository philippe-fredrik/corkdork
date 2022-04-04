package se.iths.corkdork.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] urls = {
            "/users/**",
            "/users/signup",
            "/wines",
            "/roles",
            "/countries/**",
            "/grapes/**",
            "/publish"};

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf().ignoringAntMatchers(urls)
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/users/signup").permitAll()
                .antMatchers("/users/**","/roles/**").hasRole("ADMIN")
                .antMatchers("/wines", "/grapes/**", "/countries/**").hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .and().build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
