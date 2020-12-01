package com.lexsoft.project.beer.logging.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class CorrelationIdFilter implements Filter {

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
                throws IOException, ServletException {

            final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String currentCorrId = httpServletRequest.getHeader(RequestCorrelation.CORRELATION_ID);

            if (!currentRequestIsAsyncDispatcher(httpServletRequest)) {
                currentCorrId = Optional.ofNullable(currentCorrId)
                        .orElse(UUID.randomUUID().toString());
                RequestCorrelation.setId(currentCorrId);
            }
            filterChain.doFilter(httpServletRequest, servletResponse);
        }

        private boolean currentRequestIsAsyncDispatcher(HttpServletRequest httpServletRequest) {
            return httpServletRequest.getDispatcherType().equals(DispatcherType.ASYNC);
        }
}
