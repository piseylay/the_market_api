package com.market_api.the_market_api.config

import com.market_api.the_market_api.security.JwtAuthenticationEntryPoint
import com.market_api.the_market_api.security.JwtRequestFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class WebSecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtRequestFilter: JwtRequestFilter
) {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    @Throws(java.lang.Exception::class)
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity -> web.ignoring().antMatchers(
            //"/**",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/resources/**"
        ) }
      }

    @Throws(Exception::class)
    @Bean
    fun filterChain(httpSecurity: HttpSecurity) : SecurityFilterChain{
        /**
         * We don't need CSRF for this example
         */
        httpSecurity.csrf().disable()
            .cors().configurationSource(corsConfigurationSource(httpSecurity))
            .and()
            /** dont authenticate this particular request */
            .authorizeRequests().antMatchers(
                "/**/authenticate",
            ).permitAll()
            /** all other requests need to be authenticated */
            .anyRequest().authenticated().and()
            /**
             * make sure we use stateless session; session won't be used to
             * store user's state. */
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        /** Add a filter to validate the tokens with every request */

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
        return httpSecurity.build()
    }

    @Bean
    fun corsConfigurationSource(httpSecurity: HttpSecurity): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:4200","http://localhost:4300")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE","OPTIONS")
        configuration.allowCredentials = true
        //the below three lines will add the relevant CORS response headers
        configuration.addAllowedOrigin("*")
        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
