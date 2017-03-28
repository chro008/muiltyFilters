package com.lxm.multifilters;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyFilterInvocation {
	private FilterChain chain;
	private ServletRequest request;
	private ServletResponse response;
	
	
	public MyFilterInvocation(ServletRequest request, ServletResponse response,
			FilterChain chain) {
		if ((request == null) || (response == null) || (chain == null)) {
			throw new IllegalArgumentException(
					"Cannot pass null values to constructor");
		}
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException(
					"Can only process HttpServletRequest");
		}
		if (!(response instanceof HttpServletResponse)) {
			throw new IllegalArgumentException(
					"Can only process HttpServletResponse");
		}
		this.request = request;
		this.response = response;
		this.chain = chain;
	}

	public FilterChain getChain() {
		return chain;
	}
	
	public ServletRequest getRequest() {
		return request;
	}
	
	public ServletResponse getResponse() {
		return response;
	}
	
	public HttpServletRequest getHttpRequest() {
		return (HttpServletRequest) request;
	}

	public HttpServletResponse getHttpResponse() {
		return (HttpServletResponse) response;
	}
	
	public String getRequestUrl() {
		return getHttpRequest().getRequestURL().toString();
	}

	public String toString() {
		return "MyFilterInvocation: URL: " + getRequestUrl();
	}
}
