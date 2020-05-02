package com.finkicommunity.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finkicommunity.domain.request.user.LoginRequest;
import com.finkicommunity.domain.response.UserLoginResponse;
import com.google.gson.Gson;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequest credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            System.out.println(credentials);
            // Create login token
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    credentials.username,
                    credentials.password,
                    new ArrayList<>());

            // Authenticate user
            Authentication auth = authenticationManager.authenticate(authenticationToken);

            return auth;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Grab principal
        UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();

        // Create JWT Token
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));


        // Add token in response
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        // userResponse.expiresIn = expires;
        // userResponse.idToken = token;
        // GrantedAuthority[] grantedAuthorities;
//        userResponse.role = Role.valueOf(principal.getAuthorities()
//                .stream()
//                .findFirst()
//                .map(
//                        authority ->  authority.getAuthority().replace("ROLE_", "")
//                )
//                .orElse("USER")
//        );
//        userResponse.errorMessage = "";
//        userResponse.valid = true;
        userLoginResponse.username = principal.getUsername();
        userLoginResponse.jwtToken = token;

        String userResponseJsonString = new Gson().toJson(userLoginResponse);

        out.print(userResponseJsonString);
        out.flush();
    }
}
