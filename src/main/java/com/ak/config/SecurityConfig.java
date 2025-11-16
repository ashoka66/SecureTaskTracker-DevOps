package com.ak.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


//concept used :Spring Security + InMemory Authentication
//Protects / dash board route and allows/login


@Configuration
public class SecurityConfig {
	
	//Define In memory users for simplicity
	@Bean //TL updated method access member public to private 
	private InMemoryUserDetailsManager userDetailsService() {
		
		UserDetails user = User.withUsername("admin").password("password").roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}
	
	
	
	
	//Define Security filterChain (authorization rules)
	@Bean //TL updated method access member public to private 
	private SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/login")
				                                .permitAll()
				                                .anyRequest().authenticated())
		   .formLogin(form->form
				    .loginPage("/login")
				    .defaultSuccessUrl("/dashboard",true)
				    .permitAll())
		   .logout(logout->logout.permitAll());
				       	                                
		   return http.build();                                    
		
	}
	
	
	//No password encoding for demo (never use in production)
	@Bean
	private static NoOpPasswordEncoder passwordEncoder() {
		return(NoOpPasswordEncoder)  NoOpPasswordEncoder.getInstance();
	}
	

}
