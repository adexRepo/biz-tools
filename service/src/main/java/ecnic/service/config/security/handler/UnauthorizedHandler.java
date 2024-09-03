package ecnic.service.config.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint, Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setCharacterEncoding("utf-8");
    response.setHeader("Content-type","text/plain;charset=utf-8");
    response.getWriter().println("Unauthorized User!");
  }
}
