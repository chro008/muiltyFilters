package com.lxm.multifilters;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyMultiFilters implements Filter{
	
	private Filter[] filters;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		filters = getAllDefinedFilters();
		for (int i = 0; i < filters.length; i++) {
			if (filters[i] != null) {
				System.out.println("Initializing Filter defined in ApplicationContext: '" + filters[i].toString() + "'");
				filters[i].init(filterConfig);
			}
		}
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		MyFilterInvocation invocation = new MyFilterInvocation(request, response, chain);

		if (filters.length == 0) {
			System.out.println(invocation.getRequestUrl() + " has an empty filter list");
			chain.doFilter(request, response);
			return;
		}

		MyFilterChain filterCharin = new MyFilterChain(invocation,filters);
		filterCharin.doFilter(invocation.getRequest(), invocation.getResponse());
	}

	@Override
	public void destroy() {
		for (int i = 0; i < filters.length; i++) {
			if (filters[i] != null) {
				System.out.println("Destroying Filter defined in ApplicationContext: '"
							+ filters[i].toString() + "'");
				filters[i].destroy();
			}
		}
	}
	
	/**
	* 初始化单点登录需要的过滤器
	* @return
	*/
	private Filter[] getAllDefinedFilters() {
		Set<Filter> list = new LinkedHashSet<>();
		list.add(new Filter1());
		list.add(new Filter2());
		list.add(new Filter3());
		return (Filter[]) list.toArray(new Filter[0]);
	}

}
