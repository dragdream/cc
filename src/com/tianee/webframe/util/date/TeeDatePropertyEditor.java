package com.tianee.webframe.util.date;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TeeDatePropertyEditor  extends PropertyEditorSupport {

	private String format;   
	@Override  
	public void setAsText(String text) throws IllegalArgumentException  {   
	              
	    SimpleDateFormat sdf=new SimpleDateFormat(format);   
	    try  {   
	         Date date=sdf.parse(text);   
	         this.setValue(date);  //把转换后的值传过去   
	    } catch (Exception e) {   
	         e.printStackTrace();   
	    }   
	   }   
	  public String getFormat()   {   
	       return format;   
	  }   
	  public void setFormat(String format) {   
	       this.format = format;   
	  }   	
}
