package in.watch.auth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


//There are multiple ways to filer=>you can implement Filter interface
//here we are filtering using it for every request
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String autHeader = request.getHeader("Authorization");
        
        //if its null or does not start with Bearer
        if (autHeader == null || !autHeader.startsWith("Bearer ")) {
            //calling next filter
            filterChain.doFilter(request, response);
            //return the function
            return;
        }

        //extracting token => after extracting token you need the data inside JWT token
        String jwtToken = autHeader.split(" ")[1];

        //username is generally used when talking about security. So username refer userEmail
        String userEmail = jwtService.extractUsername(jwtToken);

        //checking if email is not null and user is not authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //here we have to check user in db
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        filterChain.doFilter(request, response);

    }
}
