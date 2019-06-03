<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>复议事项情况统计</title>
	<link rel="stylesheet" type="text/css" href="/xzfy/css/caseQuery.css" />
	<script type="text/javascript" src="/xzfy/js/common/common.js"></script>
	<script type="text/javascript" src="/xzfy/js/base/echarts.min.js"></script>
	<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
	<style> 
	.table_center{
		border-collapse:collapse;
	}
	.tab_center td{
		vertical-align:middle;
		text-align:center
	}
	.btn-win-white  {width:110px !important;}
    </style> 
</head>

<body style="padding-left: 10px;padding-right: 10px" onload="">
<div id="toolbar" class = " clearfix" style="margin-top: 5px">
	    <div class="fl" style="position:static">
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/jhrw/icon_任务中心.png">
		    <span class="title" id="title">复议事项情况统计</span>
	    </div>
	    <span class="basic_border_grey" style="margin-top: 10px"></span>
	    
	    <div class="setHeight">
	        <form id="form1" style="">
	            <input type="hidden" id="orgId" value="" >
	            <table class="none_table" width="100%" height="65">
	           
	 				<tr id="accsearchTr1">
	 					<td colspan="2">复议机关：
	 					    <select name="moreselAge" id="addNew">   
						        <option value="1" selected>默认机关名称</option>   
						        <option value="2">下级机关名称</option>    
						      </select>   
	 					    <input type="checkbox" name="" value="" />本级及下级
	 					</td>
	 					<td colspan="2">开始时间：
	 					 	<input type="text" name="beginTime" id="beginTime" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
	 					 		class="Wdate BigInput" style="width:172px;height: 20px"/>
						</td>
						<td colspan="2">结束时间：
	 						<input type="text" name="endTime" id="endTime" readonly onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\',{d:0});}',dateFmt:'yyyy-MM-dd'})" 
							    class="Wdate BigInput" style="width:172px;height: 20px"/>
	 					</td>
 						<td class="TableData"><button class="btn-win-white" type="button" onclick="">生成统计图</button></td>
	 				</tr>
	 				<tr id="accsearchTr1">
	 					<td>
	 						统计维度：
	 						<button class="btn-win-white" type="button" onclick="switchCharts(optionSQFS)">申请方式</button>
	 					</td>
	 					<td>
	 						<button class="btn-win-white" type="button" onclick="switchCharts(optionFYSX)">复议事项</button>
	 					</td>
						<td>
							<button class="btn-win-white" type="button" onclick="switchCharts(optionXZGLLB_bar)">行政管理类别排行</button>
	 					</td>
	 					<td>
	 						<button class="btn-win-white" type="button" onclick="switchCharts(optionSLLX)">受理类型</button>
	 					</td>
	 					<td>
							<button class="btn-win-white" type="button" onclick="switchCharts(optionJALX)">结案类型</button>
	 					</td>
	 					<td>
	 						<button class="btn-win-white" type="button" onclick="switchCharts(optionJCYY)">纠错原因分析</button>
	 					</td>
	 					<td>
							<button class="btn-win-white" type="button" onclick="switchCharts(optionAJZT)">案件状态统计</button>
	 					</td>
	 				</tr>
				</table>
			</form>
		</div>
		<span class="basic_border"></span>
		
   	</div>
   	<br>
   	<div id="tablediv_sqr" align="center" style="display:none;">
   	<span class="title">同一申请人申请次数分析<br></span>
     <table border="1" cellspacing="0" cellpadding="0" class="table_center" id="table">
     	<thead>
     		<tr>
     			<th>序号</th>
     			<th>申请人姓名</th>
     			<th>性别</th>
     			<th>申请人类型</th>
     			<th>申请次数</th>
     			<th>申请次数排名</th>
     		</tr>
     	</thead>
     	<tbody>
     		<tr>
     			<td>1</td>
     			<td>陈莹莹</td>
     			<td>女</td>
     			<td>公民</td>
     			<td><a href='/xzfy/jsp/caseQuery/applyCntDetail.jsp' target="_blank">10</a></td>
     			<td>1</td>
     		</tr>
     		<tr>
     			<td>2</td>
     			<td>陈莹</td>
     			<td>女</td>
     			<td>公民</td>
     			<td><a href='/xzfy/jsp/caseQuery/applyCntDetail.jsp' target="_blank">10</a></td>
     			<td>2</td>
     		</tr>
     		<tr>
     			<td>3</td>
     			<td>陈莹</td>
     			<td>女</td>
     			<td>公民</td>
     			<td><a href='/xzfy/jsp/caseQuery/applyCntDetail.jsp' target="_blank">7</a></td>
     			<td>2</td>
     		</tr>
     		<tr>
     			<td>3</td>
     			<td>陈莹</td>
     			<td>女</td>
     			<td>公民</td>
     			<td><a href='/xzfy/jsp/caseQuery/applyCntDetail.jsp' target="_blank">7</a></td>
     			<td>2</td>
     		</tr>
     		<tr>
     			<td>5</td>
     			<td>陈莹</td>
     			<td>女</td>
     			<td>公民</td>
     			<td><a href='/xzfy/jsp/caseQuery/applyCntDetail.jsp' target="_blank">7</a></td>
     			<td>5</td>
     		</tr>
     	</tbody>
     	<tfoot>
     	</tfoot>
     </table>
    </div>
    <div id="tablediv_bsqr" align="center" style="display:none;">
    <span class="title">同一被申请人复议案件数量分析<br></span>
     <table border="1" cellspacing="0" cellpadding="0" class="table_center" id="table">
     	<thead>
     		<tr>
     			<th>序号</th>
     			<th>被申请人姓名</th>
     			<th>被申请人种类</th>
     			<th>被复议次数</th>
     			<th>被复议次数排名</th>
     		</tr>
     	</thead>
     	<tbody>
     		<tr>
     			<td>1</td>
     			<td>深圳市人民政府</td>
     			<td>市级政府</td>
     			<td><a href='/xzfy/jsp/caseQuery/aplyedReconCntDetail.jsp' target="_blank">10</a></td>
     			<td>1</td>
     		</tr>
     		<tr>
     			<td>2</td>
     			<td>深圳市人民政府</td>
     			<td>市级政府</td>
     			<td><a href='/xzfy/jsp/caseQuery/aplyedReconCntDetail.jsp' target="_blank">10</a></td>
     			<td>2</td>
     		</tr>
     		<tr>
     			<td>3</td>
     			<td>深圳市人民政府</td>
     			<td>市级政府</td>
     			<td><a href='/xzfy/jsp/caseQuery/aplyedReconCntDetail.jsp' target="_blank">10</a></td>
     			<td>2</td>
     		</tr>
     		<tr>
     			<td>4</td>
     			<td>深圳市人民政府</td>
     			<td>市级政府</td>
     			<td><a href='/xzfy/jsp/caseQuery/aplyedReconCntDetail.jsp' target="_blank">10</a></td>
     			<td>2</td>
     		</tr>
     	</tbody>
     	<tfoot>
     	</tfoot>
     </table>
    </div>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	<div align="center" >
    <div id="main"style="width: 800px;height:400px;"></div>
    </div>
    <script type="text/javascript">
       
    </script>
    <script type="text/javascript" src="/xzfy/js/base/juicer-min.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseList/reconMattersStat.js"></script>
</body>
</html>