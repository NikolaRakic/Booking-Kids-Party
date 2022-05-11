package com.diplomski.bookingkidsparty.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.diplomski.bookingkidsparty.app.service.impl.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("UserServiceImpl")
	private UserServiceImpl jwtUserDetailsService;

	@Autowired
	private TokenUtils tokenUtils;

	// Handler za vracanje 401 kada klijent sa neodogovarajucim korisnickim imenom i
	// lozinkom pokusa da pristupi resursu
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	// Registrujemo authentication manager koji ce da uradi autentifikaciju
	// korisnika za nas
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Definisemo uputstvo za authentication managera koji servis da koristi da
	// izvuce podatke o korisniku koji zeli da se autentifikuje,
	// kao i kroz koji enkoder da provuce lozinku koju je dobio od klijenta u
	// zahtevu da bi adekvatan hash koji dobije kao rezultat bcrypt algoritma
	// uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain
	// lozinka)
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	// Definisemo prava pristupa odredjenim URL-ovima
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST
				// aplikacija
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				// sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

				// svim korisnicima dopusti da pristupe putanjama /auth/**
				.authorizeRequests().antMatchers("/auth/login").permitAll()
				.antMatchers("/swagger-ui/**", "/v3/api-docs", "/api/v1/KidsParty/v3/api-docs").permitAll()
				.antMatchers("/auth/parent/signup")
				.permitAll()

				// za svaki drugi zahtev korisnik mora biti autentifikovan
				.anyRequest().authenticated().and()

				// umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT
				// tokena umesto cistih korisnickog imena i lozinke (koje radi
				// BasicAuthenticationFilter)
				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
						BasicAuthenticationFilter.class);

		// zbog jednostavnosti primera
		http.cors().and().csrf().disable();

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
		web.ignoring().antMatchers(HttpMethod.GET, "/serviceProvider/type/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/serviceOffers");
		web.ignoring().antMatchers(HttpMethod.GET, "/serviceProvider/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/rating**");
		web.ignoring().antMatchers(HttpMethod.GET, "/rating/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/photos/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/v3/api-docs");
		web.ignoring().antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**", "/api/v1/KidsParty/v3/api-docs");

	}
}
