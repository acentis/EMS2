package com.td.mace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	PersistentTokenRepository tokenRepository;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.authorizeRequests()
				.antMatchers("/Workpackage", "/workpackageslist")
				.access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
				.antMatchers("/newworkpackage/**", "/delete-workpackage-*")
				.access("hasRole('ADMIN')").antMatchers("/edit-workpackage-*")
				.access("hasRole('ADMIN') or hasRole('DBA')").and().formLogin()
				.loginPage("/login").loginProcessingUrl("/login")
				.usernameParameter("ssoId").passwordParameter("password").and()
				.rememberMe().rememberMeParameter("remember-me")
				.tokenRepository(tokenRepository).tokenValiditySeconds(86400)
				.and().csrf().and().exceptionHandling()
				.accessDeniedPage("/Access_Denied");

		http.authorizeRequests()
				.antMatchers("/Project", "/projectslist")
				.access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
				.antMatchers("/newproject/**", "/delete-project-*")
				.access("hasRole('ADMIN')").antMatchers("/edit-project-*")
				.access("hasRole('ADMIN') or hasRole('DBA')").and().formLogin()
				.loginPage("/login").loginProcessingUrl("/login")
				.usernameParameter("ssoId").passwordParameter("password").and()
				.rememberMe().rememberMeParameter("remember-me")
				.tokenRepository(tokenRepository).tokenValiditySeconds(86400)
				.and().csrf().and().exceptionHandling()
				.accessDeniedPage("/Access_Denied");

		http.authorizeRequests()
				.antMatchers("/", "/list").permitAll()
				.access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
				.antMatchers("/newuser/**", "/delete-user-*")
				.access("hasRole('ADMIN')").antMatchers("/edit-user-*")
				.access("hasRole('ADMIN') or hasRole('DBA')").and().formLogin()
				.loginPage("/login").loginProcessingUrl("/login")
				.usernameParameter("ssoId").passwordParameter("password").and()
				.rememberMe().rememberMeParameter("remember-me")
				.tokenRepository(tokenRepository).tokenValiditySeconds(86400)
				.and().csrf().and().exceptionHandling()
				.accessDeniedPage("/Access_Denied");*/
		http.authorizeRequests()
				.antMatchers("/newproject/**", "/delete-project-*","/edit-project-*").access("hasRole('ADMIN')")		
				//.antMatchers("/newuser/**", "/delete-user-*","/edit-user-*").access("hasRole('ADMIN')")
				.antMatchers("/newworkpackage/**", "/delete-workpackage-*","/edit-workpackage-*").access("hasRole('ADMIN')")
				.antMatchers("/newuserAttendance/**", "/delete-userAttendance-*").access("hasRole('ADMIN')")
				.antMatchers("/", "/list").permitAll()
				.antMatchers("/Project", "/projectslist").permitAll()
				.antMatchers("/Workpackage", "/workpackageslist").permitAll()
				.antMatchers("/UserAttendance", "/userAttendanceslist").permitAll()
				.antMatchers("/UserAttendance", "/monthlyReport").permitAll()
				.antMatchers("/edit-userAttendance-*").permitAll()
				.antMatchers("/work-package").permitAll()
				.and().formLogin()
				.loginPage("/login").loginProcessingUrl("/login")
				.usernameParameter("ssoId").passwordParameter("password").and()
				.rememberMe().rememberMeParameter("remember-me")
				.tokenRepository(tokenRepository).tokenValiditySeconds(86400)
				.and().csrf().and().exceptionHandling()
				.accessDeniedPage("/Access_Denied");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
		PersistentTokenBasedRememberMeServices tokenBasedservice = new PersistentTokenBasedRememberMeServices(
				"remember-me", userDetailsService, tokenRepository);
		return tokenBasedservice;
	}

	@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}

}
