/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.iff.marketplace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class MyWebSecurity extends WebSecurityConfigurerAdapter {
    
    
    @Autowired
    private PessoaDetailsService service;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
        
        
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/apirest/**")
                .and()
                .authorizeRequests()
                .antMatchers("/apirest/**").hasRole("ADMIN")
                .antMatchers("/profile/addproduto/**").hasRole("VENDEDOR")
                .antMatchers("/profile/meusprodutos/**").hasRole("VENDEDOR")
                .antMatchers("/produtos/comprar/**").hasRole("USUARIO")
                .antMatchers("/profile/edit/usuario").hasRole("USUARIO")
                .antMatchers("/profile/edit/vendedor").hasRole("VENDEDOR")
                .antMatchers("/produtos/id/deletar").hasAnyRole("VENDEDOR", "ADMIN")
                .and()
                .httpBasic()
                .and()
                .formLogin().usernameParameter("user")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll().logoutSuccessUrl("/");
                
    }
    
    
}
