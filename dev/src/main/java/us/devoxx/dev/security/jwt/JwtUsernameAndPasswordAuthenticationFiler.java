package us.devoxx.dev.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtUsernameAndPasswordAuthenticationFiler extends UsernamePasswordAuthenticationFilter {
    private  static final Logger LOG = LogManager.getLogger();
      private AuthenticationManager  authenticationManager ;
      public JwtUsernameAndPasswordAuthenticationFiler ( AuthenticationManager  authenticationManager  ){
          this.authenticationManager =  authenticationManager  ;

      }


      @Override
      public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
           var  username =  request.getParameter("username");
           var passsword  = request.getParameter("password");
           if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(passsword) ) {
               JwtUsernameAndPasswordAuthenticationFiler.LOG
                       .debug("--> JwtAuthenticationFilter.attemptAuthentication(email, password) as Json in Body");
               String body  = null ;
               try {
                   body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                   var mapper = new ObjectMapper();
                   var login  = mapper.readValue(body ,  Login.class);
                   username =  login.getEmail();
                   passsword =  login.getPassword() ;
               } catch (IOException lExp) {
                   JwtUsernameAndPasswordAuthenticationFiler.LOG.error(
                           "--> JwtAuthenticationFilter.attemptAuthentication - Error, your JSon is not right!, found {}, should be something like {\"email\":\"toto@gmail.com\",\"password\":\"bonjour\"}. DO NOT use simple quote!",
                           body, lExp);
               }

           } else {
               JwtUsernameAndPasswordAuthenticationFiler.LOG
                       .debug("--> JwtAuthenticationFilter.attemptAuthentication(email, password) as parameter");

           }
          JwtUsernameAndPasswordAuthenticationFiler.LOG.debug("--> JwtAuthenticationFilter.attemptAuthentication({}, [PROTECTED])",
                  username);

           Authentication  authentication =  new UsernamePasswordAuthenticationToken(username , passsword );
           var  result  =  this.authenticationManager.authenticate(authentication) ;
          System.out.println( "username   =  " + username   +  "password   =  " + passsword  +  "  authentication  " +  result.getPrincipal()   + "  <  " + result.getCredentials()  );
          return   result ;
      }



      @Override
      public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
            String  key  =  "secret key"  ;
            Algorithm  algorithm =  Algorithm.HMAC256(key.getBytes());

            String jwtAccessToken  = JWT.create()
                    .withSubject(authResult.getName())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 *1000))
                    .withIssuer(request.getRequestURI().toString())
                    .withClaim("roles" , authResult.getAuthorities().stream().map(GrantedAuthority :: getAuthority).collect(Collectors.toList()))
                    .sign(algorithm);


            String refreshToken  =  JWT .create()
                    .withSubject(authResult.getName())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .sign(algorithm);


          System.out.println("this is  jwtaccessToken  =  " + jwtAccessToken);
          System.out.println("htis is refresh  token  = "  +  refreshToken  );



          response.addHeader("Authorization", "Bearer " + jwtAccessToken);
          response.setStatus(200);
          response.getWriter().println(" vous estes bien  authentifié  !!!! "+ authResult.getName()  );

      }


} //  end of  class





