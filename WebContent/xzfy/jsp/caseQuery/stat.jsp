<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>统计分析</title>
	<link rel="stylesheet" type="text/css" href="/xzfy/css/caseQuery.css" />
	<script type="text/javascript" src="/xzfy/js/common/common.js"></script>
	<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
	<style> 
		.firstTh{ text-align:center} 
		.shuli{ margin:0 auto;width:20px;line-height:24px;border:1px solid #333;text-align:center} 
	</style> 
</head>

<body style="padding-left: 10px;padding-right: 10px" onload="doInit();">

   <div id="toolbar" class = " clearfix" style="margin-top: 5px">
	    <div class="fl" style="position:static">
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/jhrw/icon_任务中心.png">
		    <span class="title" id="title"></span>
	    </div>
	    <span class="basic_border_grey" style="margin-top: 10px"></span>
	    
	    <div class="setHeight">
	        <form id="form1" style="">
	            <input type="hidden" id="orgId" value="" >
	            <table class="none_table" width="100%" height="65">
	           
	 				<tr id="accsearchTr1">
	 				
	 					<td class="TableData TableBG">复议机关：
	 					    <select name="moreselAge" id="addNew">   
						        <option value="1" selected>默认机关名称</option>   
						        <option value="2">下级机关名称</option>    
						      </select>   
	 					    <input type="checkbox" name="" value="" />本级及下级
	 					</td>
	 				
	 					<td class="TableData TableBG">开始时间：
	 					
	 					 	<input type="text" name="beginTime" id="beginTime" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
	 					 		class="Wdate BigInput" style="width:172px;height: 20px"/>
						</td>
						
						<td class="TableData TableBG">结束时间：

	 						<input type="text" name="endTime" id="endTime" readonly onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\',{d:0});}',dateFmt:'yyyy-MM-dd'})" 
							    class="Wdate BigInput" style="width:172px;height: 20px"/>
	 					</td>
	 					
	 					<td class="TableData TableBG"></td>
	 					
 						<td class="TableData"><button class="btn-win-white" type="button" onclick="search()">生成统计图</button></td>
	 				</tr>
				</table>
			</form>
		</div>
		<span class="basic_border"></span>
		
   	</div>
   	<br>
   	<div id="tablediv" align="center" style="display:none">
     <table border="1" cellspacing="0" cellpadding="0" id=table>
        <tbody align="center">
        	<tr>
        		<th rowspan="2" style="vertical-align:bottom;text-align:center">行政管理类型</th>
        		<th colspan="2" class="firstTh">本期新收</th>
        		<th colspan="2" class="firstTh">申请人类型</th>
        		<th colspan="4" class="firstTh">被申请人类型</th>
        		<th colspan="4" class="firstTh">受理类型</th>
        		<th colspan="9" class="firstTh">行政复议事项</th>
        		<th colspan="9" class="firstTh">结案类型</th>
        		<th colspan="2" class="firstTh">行政<br>赔偿</th>
        	</tr>
        	<tr>
        		<th class="shuli">复议</th>
        		<th class="shuli">复议前置</th>
        		<th class="shuli">公民</th>
        		<th class="shuli">法人或其他组织</th>
        		<th class="shuli">乡镇政府</th>
        		<th class="shuli">县级政府及部门</th>
        		<th class="shuli">市级政府及部门</th>
        		<th class="shuli">省级政府及部门</th>
        		<th class="shuli">受理</th>
        		<th class="shuli">不予受理</th>
        		<th class="shuli">告知</th>
        		<th class="shuli">转达</th>
        		<th class="shuli">行政处罚</th>
        		<th class="shuli">行政强制</th>
        		<th class="shuli">行政征收</th>
        		<th class="shuli">行政许可</th>
        		<th class="shuli">行政确权</th>
        		<th class="shuli">干预经营自主权</th>
        		<th class="shuli">信息公开</th>
        		<th class="shuli">行政不作为</th>
        		<th class="shuli">其他</th>
        		<th class="shuli">驳回</th>
        		<th class="shuli">维持</th>
        		<th class="shuli">确认违法</th>
        		<th class="shuli">撤销</th>
        		<th class="shuli">变更</th>
        		<th class="shuli">责令履行</th>
        		<th class="shuli">调解</th>
        		<th class="shuli">终止</th>
        		<th class="shuli">总计</th>
        		<th class="shuli">件数</th>
        		<th>赔<br/>偿<br/>数<br/>额<br/>（<br/>元<br/>）</th>
        	</tr>
            <tr>
                <th>公安</th>
                <td> &nbsp;</td> 
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td>2</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> 9</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> 6</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> 3</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td>            
            </tr>
            <tr>
                <th>国家安全</th>
                <td> &nbsp;</td> 
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td>            
            </tr>
            <tr>
                <th>劳动和社会保障</th>
                <td> &nbsp;</td> 
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td>            
            </tr>
            <tr>
                <th>司法行政</th>
                <td> &nbsp;</td> 
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td>            
            </tr>
            <tr>
                <th>民政</th>
                <td> &nbsp;</td> 
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td>            
            </tr>
            <tr>
                <th>农林环资</th>
                <td> &nbsp;</td> 
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td>            
            </tr>
            <tr>
                <th>城建</th>
                <td> &nbsp;</td> 
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td>10</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td>            
            </tr>
            <tr>
                <th>工交商事</th>
                <td> &nbsp;</td> 
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> 6</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td>4</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td>            
            </tr>
            <tr>
                <th>财政金融</th>
                <td> &nbsp;</td> 
                <td> &nbsp;</td>
                <td> 3</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> 3</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> 2</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td>            
            </tr>
            <tr>
                <th>教科文卫</th>
                <td> &nbsp;</td> 
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td>
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td> 
                <td> &nbsp;</td>            
            </tr>
        </tbody>
    </table>
    </div>
    <iframe id="exportIframe" style="display:none"></iframe>
    <!--  
    <script type="text/javascript" src="/xzfy/js/caseRegister/register.js"></script>
    -->
    <script type="text/javascript" src="/xzfy/js/base/juicer-min.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseList/stat.js"></script>
   <!--  
    <script type="text/javascript" src="/xzfy/js/caseTrial/trial.js"></script>
    -->
    
</body>
</html>