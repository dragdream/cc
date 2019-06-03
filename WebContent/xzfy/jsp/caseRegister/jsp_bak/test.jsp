<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<link href="css/bootstrap.min.css" rel="stylesheet"> 
<link href="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet"> 

<style>
body{
	background:#fff;
	padding:5px 10px 0 10px; 
}
.fyform .row .col-md-4,.col-md-6,.col-md-8,.col-md-2,.col-md-10{padding:0;margin:0 0 10px 0;}
.fyleft, .fyright ,.fycenter, .fybottom{padding:0}
.inputlable{text-align:right;height:30px;line-height:30px;font-weight: normal;}
.form-control{height:30px;border-radius:0px;}
.radio-group{text-align:left;}
.radio-group span{    
	display: inline-block;
    height: 28px;
    line-height: 28px;
    padding: 0 10px;
	border: 1px solid;}
.inputlable:after{content:"*";color:red;}

</style>
</head>
<body>

<div class="container-fluid fyform">
	<div class="row">
	     <div class="col-md-6 fyleft">
	         <label class="inputlable col-md-4 control-label">案件编号：</label>
		     <div class="col-md-8">
		         <select class="form-control">
				 <option>1</option>
				 <option>2</option>
				 <option>3</option>
				 <option>4</option>
				 <option>5</option>
				 </select>
		     </div>

			 <label class="inputlable col-md-4 control-label">申请日期：</label>
		     <div class="col-md-8">
				 <div class='input-group date' id='datetime1'>
                 <input type='text' class="form-control" />
                 <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                 </span>
                 </div>
		     </div>

			 <label class="inputlable col-md-4 control-label">申请事项：</label>
		     <div class="col-md-8">
		         <input type="text" class="form-control" id="" placeholder="" />
		     </div>

			 <label class="inputlable col-md-4 control-label">具体行政行为文号：</label>
		     <div class="col-md-8">
		         <select class="form-control">
				 <option>1</option>
				 <option>2</option>
				 <option>3</option>
				 <option>4</option>
				 <option>5</option>
				 </select>
		     </div>

			 <label class="inputlable col-md-4 control-label">收到处罚决定书日期：</label>
		     <div class="col-md-8">
				 <div class='input-group date' id='datetime2'>
                 <input type='text' class="form-control" />
                 <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                 </span>
                 </div>
		     </div>

	     </div>
	     <div class="col-md-6 fyright">
	         <label class="inputlable col-md-4 control-label">行政类别管理：</label>
		     <div class="col-md-8">
		         <input type="text" class="form-control" id="" placeholder="" />
		     </div>

			 <label class="inputlable col-md-4 control-label">具体行政行为名称：</label>
		     <div class="col-md-8">
		         <select class="form-control">
				 <option>1</option>
				 <option>2</option>
				 <option>3</option>
				 <option>4</option>
				 <option>5</option>
				 </select>
		     </div>

			 <label class="inputlable col-md-4 control-label">具体行政行为日期：</label>
		     <div class="col-md-8">
		         <select class="form-control">
				 <option>1</option>
				 <option>2</option>
				 <option>3</option>
				 <option>4</option>
				 <option>5</option>
				 </select>
		     </div>

			 <label class="inputlable col-md-4 control-label">得知该具体行为途径：</label>
		     <div class="col-md-8">
		         <input type="text" class="form-control" id="" placeholder="" />
		     </div>
	     </div>
	</div>

	<div class="row">
	     <div class="col-md-12 fycenter">
	         <label class="inputlable col-md-2 control-label">具体行政行为：</label>
		     <div class="col-md-10">
		         <textarea class="form-control" rows="3"></textarea>
			 </div>

			 <label class="inputlable col-md-2 control-label">行政复议请求：</label>
		     <div class="col-md-10">
		         <textarea class="form-control" rows="3"></textarea>
			 </div>

			 <label class="inputlable col-md-2 control-label"></label>
		     <div class="col-md-10 radio-group">
		         <span><input type="radio" name="optradio" />   是否行政复议前置</span>
				 <span><input type="radio" name="optradio" />   是否行政不作为</span>
				 <span><input type="radio" name="optradio" />   是否申请赔偿</span>
				 <span><input type="radio" name="optradio" />   是否申请举行听证会</span>
				 <span><input type="radio" name="optradio" />   是否申请附带对规范性文件的审查</span>
			 </div>
		 </div>	 
    </div>

	<div class="row">
	     <div class="col-md-12 fybottom">
			 <div class="col-md-6">
			 <label class="inputlable col-md-4 control-label">赔偿请求类型：</label>
		     <div class="col-md-8">
		         <input type="text" class="form-control" id="" placeholder="">
		     </div>
			 </div>
			 <div class="col-md-6 bottomright">
			 <label class="inputlable col-md-4 control-label">规范性文件名称：</label>
		     <div class="col-md-8">
		         <input type="text" class="form-control" id="" placeholder="">
		     </div>
			 </div>
		 </div>	 
    </div>



</div>


<script>
        $(function () {
            $("#datetime1").datetimepicker({
                format: 'YYYY-MM-DD',
                locale: moment.locale('zh-cn')
            });
			$("#datetime1").children(".dropdown-menu").css("z-index","9999");
			 $("#datetime2").datetimepicker({
                format: 'YYYY-MM-DD',
                maxView:5,
                locale: moment.locale('zh-cn')
            });
        });

		
    </script>


      <script src="../../js/base/jquery.1.11.3.min.js"></script>
      <script src="../../js/base/bootstrap.min.js"></script>
	  <script src="https://cdn.bootcss.com/moment.js/2.18.1/moment-with-locales.min.js"></script>  
	  <script src="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>

</body>
</html>