package br.com.usermanager.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.usermanager.repository.UserRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService autenticationService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticationService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/api/user").hasRole("ADMINISTRATOR")		
		.antMatchers(HttpMethod.GET, "/api/user/*").hasAnyRole("ADMINISTRATOR","COMOON")		
		.antMatchers(HttpMethod.DELETE, "/api/user/*").hasRole("ADMINISTRATOR")
		.antMatchers(HttpMethod.PUT, "/api/user/*").hasRole("ADMINISTRATOR")
		.antMatchers(HttpMethod.GET, "/api/user/list").hasRole("ADMINISTRATOR")
		.antMatchers(HttpMethod.GET, "/api/user/byName").hasRole("ADMINISTRATOR")
		.antMatchers(HttpMethod.GET, "/api/user/byStation").hasRole("ADMINISTRATOR")
		.antMatchers(HttpMethod.GET, "/api/user/byProfile").hasRole("ADMINISTRATOR")
		.antMatchers(HttpMethod.GET, "/api/user/byStatus").hasRole("ADMINISTRATOR")
		.antMatchers(HttpMethod.PUT, "/api/user/inative/*").hasRole("ADMINISTRATOR")
		.antMatchers(HttpMethod.GET, "/api/user/femalesBiggerThenEighteen").hasRole("ADMINISTRATOR")
		.antMatchers(HttpMethod.GET, "/api/user/cpfThatStartsWithZero").hasRole("ADMINISTRATOR")
		.antMatchers(HttpMethod.POST, "api/station").hasRole("ADMINISTRATOR")
		.antMatchers(HttpMethod.POST, "api/profile").hasRole("ADMINISTRATOR")
		.antMatchers(HttpMethod.POST, "/api/auth").permitAll()
		.antMatchers("/h2-console/**").permitAll()
		.anyRequest().authenticated()
		.and().csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterBefore(new AuthTokenFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
        .ignoring()
        .antMatchers("/h2-console/**")
        .antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
	}
}
