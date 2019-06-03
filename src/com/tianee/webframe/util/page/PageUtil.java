package com.tianee.webframe.util.page;


/**
 * 分页工具
 * 
 * @author CXT
 */
public class PageUtil {

  public static int PAGE_SIZE = 10;

  public static int[] init(int total,int pageNo) {
	    int firstResult = (pageNo-1)*PAGE_SIZE;
	    int totalPages = 1;
	    if ((total % PAGE_SIZE) == 0) { 
	    	 totalPages = total / PAGE_SIZE; 
	    	 } else { 
	    	 totalPages = total / PAGE_SIZE + 1; 
	     } 
	    int maxResults = PAGE_SIZE;
	    if(pageNo>=totalPages){
	    	maxResults = total-PAGE_SIZE*(totalPages-1);
	    }
	    if(total == 0){
	    	maxResults = total;
	    }
	    int lastResults = firstResult + maxResults;
	    return new int[] {firstResult, lastResults};
	  }

}
