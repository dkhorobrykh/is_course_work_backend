package ru.itmo.is.course_work.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itmo.is.course_work.exception.CustomException;
import ru.itmo.is.course_work.exception.ErrorDetails;
import ru.itmo.is.course_work.exception.ExceptionEnum;

import java.io.IOException;
import java.time.Clock;
import java.util.Objects;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException ex) {
            setErrorResponse(ex, request, response);
        }
    }

    public void setErrorResponse(CustomException ex, HttpServletRequest request, HttpServletResponse response) {
        ExceptionEnum exConstant = ex.getExceptionEnum();
        response.setStatus(ex.getExceptionEnum().getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        ResponseEntity<ErrorDetails> responseEntity = new ResponseEntity<>(new ErrorDetails(Clock.systemDefaultZone().instant(),
                exConstant, new ServletWebRequest(request).getDescription(false)), exConstant.getHttpStatus());
        try {
            String json = Objects.requireNonNull(responseEntity.getBody()).toJSON();
            response.getWriter().write(json);
        } catch (IOException ignored) {
        }
    }
}
