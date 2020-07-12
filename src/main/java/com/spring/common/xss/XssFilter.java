package com.spring.common.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS过滤器
 * @author  团子
 * @since V1.0
 * @date 2018/4/10 10:15
 */
public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}
	@Override
	public void destroy() {}

}