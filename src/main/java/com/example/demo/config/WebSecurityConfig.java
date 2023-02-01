package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	/*
	@Bean 
	public PasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(); 
	}
	
	@Bean
    public InMemoryUserDetailsManager userDetailsService() {
		UserDetails user1 = User.withUsername("guest1")
	        .password(passwordEncoder().encode("123"))
	        .roles("USER")
	        .build();
        UserDetails user2 = User.withUsername("guest2")
            .password(passwordEncoder().encode("123"))
            .roles("USER")
            .build();
        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder().encode("123"))
            .roles("ADMIN")
            .build();
        return
        	new InMemoryUserDetailsManager(user1, user2, admin);
    }
	*/
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
    	.csrf().disable()
    	/*
    	.authorizeHttpRequests()
    	.requestMatchers("/", "/securityLogin").permitAll()
		.anyRequest().authenticated()
    	.and()
		.formLogin()
			.loginPage("/securityLogin")
			.loginProcessingUrl("/login")
			.usernameParameter("userId")
			.passwordParameter("password")
			.defaultSuccessUrl("/board/list")
			.failureForwardUrl("/board/error")
		.and()
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
			.logoutSuccessUrl("/")
		*/
		;
        return http.build();
    }
    /*
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
    	return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }
    */
}
