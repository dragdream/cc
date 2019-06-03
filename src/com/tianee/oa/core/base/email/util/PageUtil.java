package com.tianee.oa.core.base.email.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 分页工具
 * 
 * @author cxt
 */
public class PageUtil {

  public static int PAGE_SIZE = 20;
  //public static int PAGE_SIZE1 = 30;

  public static int[] init(Page<?> page, HttpServletRequest request) {
	    int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(request.getParameter("p"), "1"));
	    page.setPageNo(pageNumber);
	    int pageSize = Integer.parseInt(StringUtils.defaultIfBlank(request.getParameter("ps"), String.valueOf(PAGE_SIZE)));
	    page.setPageSize(pageSize);
	    int firstResult = page.getFirst() - 1;
	    int maxResults = page.getPageSize();
	    return new int[] {firstResult, maxResults};
  }
  /*
  public static int[] init(Page<?> page, HttpServletRequest request,int total) {
	    int pageNumber = Integer.parseInt(StringUtils.defaultIfBlank(request.getParameter("p"), "1"));
	    page.setPageNo(pageNumber);
	    int totalPages = 1;
	    int firstResult = (pageNumber-1)*PAGE_SIZE1;
	    if ((total % PAGE_SIZE1) == 0) { 
	    	 totalPages = total / PAGE_SIZE1; 
	    	 } else { 
	    	 totalPages = total / PAGE_SIZE1 + 1; 
	     } 
	    int maxResults = PAGE_SIZE1;
	    if(pageNumber>=totalPages){
	    	maxResults = total-PAGE_SIZE1*(totalPages-1);
	    }
	    if(total == 0){
	    	maxResults = total;
	    }
	    int lastResults = firstResult + maxResults;
	    return new int[] {firstResult, lastResults};
	  }
	*/
}
