package com.self.mianshi.blackfilter;

import com.self.mianshi.utils.NetUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter(urlPatterns = "/*",filterName = "blackFilter")
@Slf4j
public class BlackFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String ipAddress = NetUtils.getIpAddress((HttpServletRequest) servletRequest);
        if (BlackIpUtils.isBlackIp(ipAddress)){
            servletResponse.setContentType("text/json;charset=UTF-8");
            servletResponse.getWriter().write("{\"errorCode\":-1,\"errorMsg\":\"黑名单禁止访问\"}");
            return;
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LoginFilter.init");
    }
}
