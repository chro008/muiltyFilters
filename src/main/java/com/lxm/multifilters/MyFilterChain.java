package com.lxm.multifilters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFilterChain implements FilterChain{
	

	private MyFilterInvocation invocation;
	private Filter[] additionalFilters;
	private int currentPosition = 0;
	
	public MyFilterChain(MyFilterInvocation filterInvocation,
			Filter[] additionalFilters) {
		this.invocation = filterInvocation;
		this.additionalFilters = additionalFilters;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		
		if (currentPosition == additionalFilters.length) {
			System.out.println(invocation.getRequestUrl()
						+ " reached end of additional filter chain; proceeding with original chain");
			invocation.getChain().doFilter(request, response);
		} else {
			currentPosition++;
			System.out.println(invocation.getRequestUrl() + " at position "
						+ currentPosition + " of " + additionalFilters.length
						+ " in additional filter chain; firing Filter: '"
						+ additionalFilters[currentPosition - 1] + "'");
			additionalFilters[currentPosition - 1].doFilter(request, response,this);
		}
		
	}

}
