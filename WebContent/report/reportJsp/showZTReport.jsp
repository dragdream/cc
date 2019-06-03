
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.runqian.report4.cache.CacheManager"%>
<%@page import="com.runqian.report4.usermodel.IReport"%>
<%@page import="com.runqian.report4.usermodel.Engine"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@page import="com.tianee.webframe.util.db.TeeDataSource"%>
<%@page import="org.apache.commons.dbutils.DbUtils"%>
<%@page import="com.runqian.report4.util.ReportUtils"%>
<%@page import="com.runqian.report4.model.ReportDefine"%>
<%@page import="com.runqian.report4.view.ReportConfigModel"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.tianee.webframe.util.db.TeeDbUtility"%>
<%@page import="java.util.*"%>
<%@page import="com.runqian.report4.usermodel.Context"%>


<%@ taglib uri="/WEB-INF/runqianReport4.tld" prefix="report"%>
<%@ page session="true" import="java.lang.StringBuffer"%>

<%
    request.setCharacterEncoding("utf-8");
	String reportId = request.getParameter("reportId");
	String reportFile = request.getParameter("reportFile");
	String paramFile = request.getParameter("paramFile");
	String reportTitle = request.getParameter("reportTitle");
	// 增加多语言处理 zhoukai 20160511
	String closeName = request.getParameter("closeName");
	if (reportTitle!=null) reportTitle = new String(reportTitle.getBytes("iso-8859-1"), "utf-8");
	if (closeName == null) {
		closeName = "close";
	}
	String userID = request.getParameter("userID");
	String deptID = request.getParameter("deptID");

%>

<html>
<head>
<title>报表详情</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<LINK rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body oncontextmenu="window.event.returnValue=false">
<!--增加关闭按钮 zhoukai 20160511-->
<!--暂时去掉关闭按钮 zhoukai 20160511-->
<!--<div class="button_div"><input type="button" class="listbutton" onclick="window.close()" value="<%=closeName%>"/></div>-->
<table width=100% id="reportTable"><tr>
        <td >
        <%
	        Context context = new Context();
	        Connection conn = null;
	        List<Connection> connList = new ArrayList();
	        List<String> connIdList = new ArrayList();
	        try{
	        	 
	        	 conn = TeeDbUtility.getConnection();
	        	 DbUtils dbUtils = new DbUtils(conn);
	        	 List<Map> list = dbUtils.queryToMapList("select * from bis_data_source");
	        	 for(Map data:list){
	        		 Connection tmpConn = null;
	        		 try{
	        			 if("1".equals(data.get("DATA_SOURCE")+"")){//系统数据源
	        				 tmpConn = TeeDbUtility.getConnection();
		        		 }else{//外部数据源
		        			 TeeDataSource dataSource = new TeeDataSource();
		        			 dataSource.setDriverClass(TeeStringUtil.getString(data.get("DRIVER_CLASS")));
		        			 dataSource.setPassWord(TeeStringUtil.getString(data.get("DRIVER_PWD")));
		        			 dataSource.setUrl(TeeStringUtil.getString(data.get("DRIVER_URL")));
		        			 dataSource.setUserName(TeeStringUtil.getString(data.get("DRIVER_USERNAME")));
		        			 tmpConn = TeeDbUtility.getConnection(dataSource);
		        		 }
	        			 connList.add(tmpConn);
	        			 connIdList.add(TeeStringUtil.getString(data.get("SID")));
	        		 }catch(Exception ex){
	        			 ex.printStackTrace();
	        		 }
	        	 }
	        	 
	        	 for(int i=0;i<connList.size();i++){
	        		 context.setConnection(connIdList.get(i), connList.get(i));
	        	 }
	        	 
	 			 request.setAttribute("myContext", context);
	 			 
	 			Map reportData = dbUtils.queryToMap("select * from quiee_report where sid="+reportId);
	 			byte raqFile[] = (byte[])reportData.get("raq_file");
	 			byte raqParamFile[] = (byte[])reportData.get("raq_param_file");
	 			
	 			
	 			
	 			
	 			
	 			//关闭系统数据源
	 			TeeDbUtility.closeConn(conn);
	 			
	 			CacheManager manager = CacheManager.getInstance();
	 			manager.deleteReportEntry("reportDefine"+reportId);
	 			manager.deleteReportEntry("reportDefineParam"+reportId);
	 			
	 			if (raqParamFile != null) {
	 				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(raqParamFile);
	 				ReportDefine rd = (ReportDefine)ReportUtils.read(byteArrayInputStream);
	 				String name = "reportDefineParam"+reportId;
		 			request.setAttribute("reportDefineParam"+reportId,rd);
	 			%>
	 				<report:param name="paramFile" 
					beanName="<%=name %>" 
					srcType="defineBean" 
					contextName="myContext" 
					/>	
	        	<%
				}
	 			if(raqFile!=null){
	 				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(raqFile);
	 				ReportDefine rd = (ReportDefine)ReportUtils.read(byteArrayInputStream);
	 				
	 				String name = "reportDefine"+reportId;
		 			request.setAttribute(name,rd);
		 			
	 				%>
	 				<report:html name="reportFile" 
						srcType="defineBean" 
						beanName="<%=name %>" 
						contextName="myContext"	
						useCache="no" 
						timeout="1" 
						width="-1" 
						height="-1"
						needScroll="yes"
						submit=""
						funcBarLocation="top"
						needSaveAsExcel="yes"
						excelLabel="<img src=../images/excel.gif border=no >"	
						/>	
	 				<%
	 			}
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        }finally{
	        	for(int i=0;i<connList.size();i++){
	        		TeeDbUtility.closeConn(connList.get(i));
	        	 }
	        }
		%>
        </td>
       </tr>
       </table>
</body>
</html>
