package us.devoxx.dev.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import us.devoxx.dev.security.jwt.JwtTokenVerifier;
import us.devoxx.dev.security.jwt.JwtUsernameAndPasswordAuthenticationFiler;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtTokenVerifier jwtTokenVerifier ;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AppUserService   appUserService ;
    public  SecurityConfig (BCryptPasswordEncoder bCryptPasswordEncoder ,   AppUserService  appUserService, JwtTokenVerifier jwtTokenVerifier){
        this.bCryptPasswordEncoder =  bCryptPasswordEncoder ;
        this.appUserService  =  appUserService ;
         this.jwtTokenVerifier =  jwtTokenVerifier ;
    }
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity  http) throws Exception {

        return  http.csrf(csrf -> csrf.disable())
                .authenticationProvider(authenticationProvider())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests().requestMatchers("/private").authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFiler(authenticationManager() ) )
                .addFilterBefore( jwtTokenVerifier  ,  JwtUsernameAndPasswordAuthenticationFiler.class)
                .build();

    }


    @Bean
    public AuthenticationManager  authenticationManager(){
        return   new ProviderManager(authenticationProvider());
    }
    @Bean
   AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return   provider ;
    }
}
