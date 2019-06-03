package com.tianee.webframe.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.tianee.webframe.util.str.TeeStringUtil;


/**
 * 参数处理验过滤器（针对ajax、表单等请求） 1.获取请求参数； 2.对获取到的请求参数进行处理（解密、字符串替、请求参数分类截取等等）； 3.把处理后的参数放回到请求列表里面
 * 
 * @author zhaoheng
 *
 */
public class XssFilter implements Filter {

    private static final Logger log = Logger.getLogger(XssFilter.class);


    @Override
    public void destroy() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        log.info("过滤器2执行开始");
        String qUri = ((HttpServletRequest) request).getRequestURI();
		if (qUri.endsWith("/")) {
			qUri += "index.jsp";
		}

        //通过地址对特定的请求进行处理，如果不需要可以不用，如果不用，就会对使用的请求进行过滤
        if (qUri.endsWith(".jsp")) {
            XssHttpServletRequestWrapper requestWrapper1 = new XssHttpServletRequestWrapper(
                    (HttpServletRequest) request);
            
            Map parameterMap=requestWrapper1.getParameterMap();
            for (Object key : parameterMap.keySet()) {
            	String k=TeeStringUtil.getString(key);
            	
                String v = requestWrapper1.getParameter(k);
                // 2.把处理后的参数放回去
                requestWrapper1.setParameter(k,v.replace("'","").replace("\"", "").replace("<", "").replace(">", ""));
			}
            // 3.放行，把我们的requestWrapper1放到方法当中
            chain.doFilter(requestWrapper1, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
