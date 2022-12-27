package us.devoxx.dev.security.jwt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;

@Component
public class JwtTokenVerifier  extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader =  request.getHeader("Authorization");
        if ("/login".equals(request.getServletPath())) {
            filterChain.doFilter(request , response);
         } else {
            if (authorizationHeader != null    && authorizationHeader.startsWith("Bearer ")) {
                //  je dois  verifier la validité de token
                String  token =  authorizationHeader.replace("Bearer ",  "");
              try {
                  String  key  =  "secret key"  ;
                  Algorithm algorithm =  Algorithm.HMAC256(key.getBytes());
                  JWTVerifier  verifier = JWT.require(algorithm).build();
                  DecodedJWT decodedJWT = verifier.verify(token);

                  String  username  =  decodedJWT.getSubject() ;
                  String  []   roles   = decodedJWT.getClaim("roles").asArray(String.class);
                  Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                  stream(roles).forEach(role -> {
                      authorities.add(new SimpleGrantedAuthority(role));
                  });
                  System.out.println("token  bien  vérifié ");
                  UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                  SecurityContextHolder.getContext().setAuthentication(authentication);

                  filterChain.doFilter(request, response);

              } catch (  Exception  exp ){
                  response.setHeader("error", exp.getMessage());
                  response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                  response.getWriter().println(" BAD TOEKn  ");
              }
            }else {
              filterChain.doFilter(request, response);
            }

        }

    }



}
