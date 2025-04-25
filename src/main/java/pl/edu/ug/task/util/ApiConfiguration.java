package pl.edu.ug.task.util;

import jakarta.servlet.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class ApiConfiguration implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getServletContext().getContextPath().startsWith("/api")) {
            response.setContentType("application/xml");
        }
        chain.doFilter(request, response);
    }
}
