package com.souza.gamesAPI.config.security;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.souza.gamesAPI.repository.UsuarioRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	 @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 
		 auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	 
	 @Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
		 .antMatchers(HttpMethod.POST, "/registro").permitAll()
		 .antMatchers(HttpMethod.GET, "/jogos").permitAll()
		 .antMatchers(HttpMethod.GET, "/categorias").permitAll()
		 .antMatchers(HttpMethod.POST, "/auth").permitAll()
		 .antMatchers(HttpMethod.DELETE, "/jogos/*").hasRole("ADM")
		 .antMatchers(HttpMethod.POST, "/jogos").hasRole("ADM")
		 .antMatchers(HttpMethod.GET, "/jogos/*").permitAll()
		 .antMatchers(HttpMethod.DELETE, "/categorias/*").hasRole("ADM")
		 .antMatchers(HttpMethod.POST, "/categorias").hasRole("ADM")
		 .antMatchers(HttpMethod.GET, "/categorias/*").permitAll()
		 .anyRequest().authenticated()
		 .and().csrf().disable()
		 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		 .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
		 
	}
	 
	 @Override
	 public void configure(WebSecurity web) throws Exception {
	     web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**","/configuration/**", "/swagger-resources/**");
	 }
	 
	 public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
	
}
