package com.example.demo.Security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired 
	private DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passEncoder = passwordEncoder();
	   System.out.println("**************************");
		System.out.println(passEncoder.encode("1234"));
		System.out.println("*************************");
	   
		auth.jdbcAuthentication().dataSource(dataSource)
	    .usersByUsernameQuery("select username as princibal , password  as Credentials,'true' as enabled"
	    		+ " from users where username=?")
	    .authoritiesByUsernameQuery("select username as princibal , role as role  "
	    		+ "from users_roles where username=?")
	    .passwordEncoder(passEncoder)
	    .rolePrefix("ROLE_");
		
//		auth.inMemoryAuthentication().withUser("user").password(passEncoder.encode("1234")).roles("USER");
//		auth.inMemoryAuthentication().withUser("admin").password(passEncoder.encode("1234")).roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Redirect to Different Pages after Login with Spring Security :
//		******http.formLogin().loginPage("/login").defaultSuccessUrl("/product/index");
		
		//**http.formLogin();

		// get form login like alert
		//** http.httpBasic();
		
		// any action you need authentication :
		http.authorizeRequests()
		.antMatchers("/save**/**", "/admin**/**", "/form**/**").hasRole("ADMIN")
		.antMatchers("/product**/**").hasRole("USER")
		.and()
		.formLogin().loginPage("/login").defaultSuccessUrl("/product/index")
		.and()
		.csrf().disable()
		.exceptionHandling().accessDeniedPage("/403")
		.and()
		.logout().logoutSuccessUrl("/");
		
		
		//accessible app without auth but when git any operation should be auth :
//      *****  http.authorizeRequests().antMatchers("/product**/**").hasRole("USER");
        
//		*****http.authorizeHttpRequests().anyRequest().authenticated();
		
		// for disable token should be add function disable() :
//       ***** http.csrf().disable();
		
//		******http.csrf();
		
		// for get page 403 about error 403 (not authorized")
//		******http.exceptionHandling().accessDeniedPage("/403");
		
		//choose where you are go when click logout :
//		******http.logout().logoutSuccessUrl("/");
	}

	
}
