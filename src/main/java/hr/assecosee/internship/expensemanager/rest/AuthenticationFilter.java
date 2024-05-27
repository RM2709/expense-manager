package hr.assecosee.internship.expensemanager.rest;

import hr.assecosee.internship.expensemanager.ExpenseManagerApplication;
import hr.assecosee.internship.expensemanager.core.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.naming.AuthenticationException;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    private static final Logger logger = LogManager.getLogger(ExpenseManagerApplication.class);

    @Autowired
    public AuthenticationFilter(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().split("/")[2].equals("status") || request.getRequestURI().split("/")[2].equals("auth")){
            filterChain.doFilter(request, response);
            return;
        }
        final String authorizationHeader = request.getHeader("Authorization");
        try {
            if(authenticationService.authorize(authorizationHeader)){
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            logger.error("User authorization failed. Error message: " + e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, new AuthenticationException(e.getMessage()));
        }
    }
}
