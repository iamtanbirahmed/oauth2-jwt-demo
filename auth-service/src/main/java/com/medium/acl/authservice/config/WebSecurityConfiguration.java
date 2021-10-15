package com.medium.acl.authservice.config;


import com.medium.acl.authservice.component.JwtRequestFilter;
import com.medium.acl.authservice.service.ParticipantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ParticipantService participantService;
    private final JwtRequestFilter jwtRequestFilter;

    public WebSecurityConfiguration(ParticipantService participantService, JwtRequestFilter jwtRequestFilter) {
        this.participantService = participantService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(participantService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        //TODO: Should not disable in the production. Need to check how to properly configure this;
        httpSecurity
                .csrf()
                .and()
                .cors().disable();

        // Authorization configuration
        //TODO: Authorization has to be dynamic and pulled from Database
        httpSecurity
                .authorizeRequests()
                .antMatchers("/v2/**", "/api/v1.0.1/jwt/authenticate")
                .permitAll()
                .anyRequest()
                .authenticated();

        // Session management
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // TODO: Need to use encrypted password in production
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("password")
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password("password")
//                .roles("ADMIN");
//
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .withDefaultSchema()
//                .withUser(
//                        User.withUsername("user")
//                                .password("password")
//                                .roles("USER"))
//                .withUser(
//                        User.withUsername("admin")
//                                .password("password")
//                                .roles("ADMIN")
//                );
//
//        auth.jdbcAuthentication()
//                .dataSource(dataSource);

//         auth.jdbcAuthentication()
//                    .dataSource(dataSource)
//                .usersByUsernameQuery(
//                        "select username, password, enabled" +
//                                "from users" +
//                                "where username = ?"
//    )
//                .authoritiesByUsernameQuery(
//                        "select username, authority" +
//                                "from authorities" +
//                                "where username = ?"
//
//    );
//
//    }
}
