package us.devoxx.dev.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AppUserService   appUserService ;
    public  SecurityConfig (BCryptPasswordEncoder bCryptPasswordEncoder ,   AppUserService  appUserService){
        this.bCryptPasswordEncoder =  bCryptPasswordEncoder ;
        this.appUserService  =  appUserService ;
    }
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity  http) throws Exception {

        return  http.csrf(csrf -> csrf.disable())
                .authenticationProvider(authenticationProvider())
                .authorizeRequests().requestMatchers("/private").authenticated()
                .anyRequest().permitAll()
                .and().formLogin()
                .and().build();

    }
    @Bean
   AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return   provider ;
    }
}
