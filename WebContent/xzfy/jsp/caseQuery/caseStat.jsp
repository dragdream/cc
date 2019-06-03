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
		.firstColTh{vertical-align:center;text-align:center}
		.shuli{ margin:0 auto;width:20px;line-height:24px;border:1px solid #333;text-align:center}
		.btn-win-white  {width:110px !important;} 
	</style> 
</head>

<body style="padding-left: 10px;padding-right: 10px" onload="doInit();">

   <div id="toolbar" class = " clearfix" style="margin-top: 5px">
	    <div class="fl" style="position:static">
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/jhrw/icon_任务中心.png">
		    <span class="title" id="title">案件统计表</span>
	    </div>
	    <span class="basic_border_grey" style="margin-top: 10px"></span>
	    
	    <div class="setHeight">
	        <form id="form1" style="">
	            <input type="hidden" id="orgId" value="" >
	            <table class="none_table" width="100%" height="65">
	           
	 				<tr id="accsearchTr1">
	 				
	 					<td>复议机关：
	 					    <select name="moreselAge" id="addNew">   
						        <option value="1" selected>默认机关名称</option>   
						        <option value="2">下级机关名称</option>    
						      </select>   
	 					    <input type="checkbox" name="" value="" />本级及下级
	 					</td>
	 				
	 					<td>开始时间：
	 					
	 					 	<input type="text" name="beginTime" id="beginTime" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
	 					 		class="Wdate BigInput" style="width:172px;height: 20px"/>
						</td>
						
						<td>结束时间：

	 						<input type="text" name="endTime" id="endTime" readonly onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\',{d:0});}',dateFmt:'yyyy-MM-dd'})" 
							    class="Wdate BigInput" style="width:172px;height: 20px"/>
	 					</td>
	 					
	 					<td></td>
	 					
 						<td class="TableData"><button class="btn-win-white" type="button" onclick="search()">生成统计图</button></td>
	 				</tr>
				</table>
			</form>
		</div>
		<span class="basic_border"></span>
		
   	</div>
   	<br>
   	<div align="center" >
   	<span class="title">行政复议案件统计表<br></span>
   	<span id="dateTxt"></span>
   	</div>
   	<div id="tablediv" align="center" >
     <table border="1" cellspacing="0" cellpadding="0" id="table">
     	<thead>
        	<tr>
        		<th rowspan="3" class="firstColTh">行政管理类型</th>
        		<th rowspan="3" class="shuli">上期结转</th>
        		<th rowspan="3" class="shuli">本期新收</th>
        		<th rowspan="3" class="shuli">受理</th>
        		<th rowspan="3" class="shuli">申请人总数</th>
        		<th colspan="9" class="firstTh">被申请人</th>
        		<th colspan="8" class="firstTh">复议机关</th>
        		<th colspan="10" class="firstTh">申请复议事项</th>
        		<th colspan="10" class="firstTh">已审结</th>
        		<th rowspan="3" class="shuli">未审结</th>
        		<th rowspan="3" class="shuli">对规范性文件附带审查</th>
        		<th colspan="2" class="firstTh">行政<br>赔偿</th>
        		<th colspan="2" class="firstTh">行政复议意见书</th>
        	</tr>
        	<tr>
        		<th rowspan="2" class="shuli">乡镇政府</th>
        		<th rowspan="2" class="shuli">县级政府的部门</th>
        		<th rowspan="2" class="shuli">县级政府</th>
        		<th rowspan="2" class="shuli">市<br>（<br>地<br>）<br>级<br>政<br>府<br>的<br>部<br>门</th>
        		<th rowspan="2" class="shuli">市<br>（<br>地<br>）<br>级<br>政<br>府</th>
        		<th rowspan="2" class="shuli">省级政府的部门</th>
        		<th rowspan="2" class="shuli">省级政府</th>
        		<th rowspan="2" class="shuli">国务院部门</th>
        		<th rowspan="2" class="shuli">其他</th>
        		<th rowspan="2" class="shuli">县级政府的部门</th>
        		<th rowspan="2" class="shuli">县级政府</th>
        		<th rowspan="2" class="shuli">市<br>（<br>地<br>）<br>级<br>政<br>府<br>的<br>部<br>门</th>
        		<th rowspan="2" class="shuli">市<br>（<br>地<br>）<br>级<br>政<br>府</th>
        		<th rowspan="2" class="shuli">省级政府的部门</th>
        		<th rowspan="2" class="shuli">省级政府</th>
        		<th rowspan="2" class="shuli">国务院部门</th>
        		<th rowspan="2" class="shuli">国务院</th>
        		<th rowspan="2" class="shuli">行政处罚</th>
        		<th rowspan="2" class="shuli">行政强制</th>
        		<th rowspan="2" class="shuli">行政征收</th>
        		<th rowspan="2" class="shuli">行政许可</th>
        		<th rowspan="2" class="shuli">行政确权</th>
        		<th rowspan="2" class="shuli">行政确认</th>
        		<th rowspan="2" class="shuli">信息公开</th>
        		<th rowspan="2" class="shuli">举报投诉处理</th>
        		<th rowspan="2" class="shuli">行政不作为</th>
        		<th rowspan="2" class="shuli">其他</th>
        		<th rowspan="2" class="shuli">总计</th>
        		<th rowspan="2" class="shuli">驳回</th>
        		<th rowspan="2" class="shuli">维持</th>
        		<th rowspan="2" class="shuli">确认违法</th>
        		<th rowspan="2" class="shuli">撤销</th>
        		<th rowspan="2" class="shuli">变更</th>
        		<th rowspan="2" class="shuli">责令履行</th>
        		<th colspan="2">终止</th>
        		<th rowspan="2" rowspan="2" class="shuli">其他</th>
        		<th rowspan="2" class="shuli">件数</th>
        		<th rowspan="2">赔<br/>偿<br/>数<br/>额<br/>（<br/>元<br/>）</th>
        		<th rowspan="2">制发数</th>
        		<th rowspan="2">落实数</th>
        	</tr>
            <tr>
            	<th class="shuli">调<br>解<br>（<br>和<br>解<br>）</th>
        		<th class="shuli">其他</th>
            </tr>
     	</thead>
        <tbody id="tbody">
           <!--  <tr>
                <th class="firstColTh">公安</th>
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td>2</td>
                <td> </td>
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 9</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 6</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 3</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>    
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>  
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td> </td>          
            </tr>
            <tr>
                <th class="firstColTh">国家安全</th>
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td>2</td>
                <td> </td>
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 9</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 6</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 3</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>    
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>  
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td> </td>          
            </tr>
            <tr>
                <th class="firstColTh">劳动和社会保障</th>
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td>2</td>
                <td> </td>
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 9</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 6</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 3</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>    
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>  
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td> </td>          
            </tr>
            <tr>
                <th class="firstColTh">水利</th>
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td>2</td>
                <td> </td>
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 9</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 6</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>    
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 4</td> 
                <td> </td>  
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td> </td>          
            </tr>
            <tr>
                <th class="firstColTh">城乡规划</th>
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td>2</td>
                <td> </td>
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 9</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 6</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>    
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 4</td> 
                <td> </td>  
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td> </td>          
            </tr>
            <tr>
                <th class="firstColTh">工商</th>
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td>2</td>
                <td> </td>
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 9</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 6</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>    
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 4</td> 
                <td> </td>  
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td> </td>          
            </tr>
            <tr>
                <th class="firstColTh">安监</th>
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td>2</td>
                <td> </td>
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 9</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 6</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>    
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 4</td> 
                <td> </td>  
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td> </td>          
            </tr>
            <tr>
                <th class="firstColTh">商务</th>
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td>2</td>
                <td> </td>
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 9</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 6</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>    
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 4</td> 
                <td> </td>  
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td> </td>          
            </tr>
            <tr>
                <th class="firstColTh">物价</th>
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td>2</td>
                <td> </td>
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 9</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 6</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>    
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 4</td> 
                <td> </td>  
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td> </td>          
            </tr>
            <tr>
                <th class="firstColTh">能源</th>
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td>2</td>
                <td> </td>
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 9</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 6</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>    
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 4</td> 
                <td> </td>  
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td> </td>          
            </tr>
            <tr>
                <th class="firstColTh">交通运输</th>
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td></td>
                <td> </td>
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 9</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 6</td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>    
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> 4</td> 
                <td> </td>  
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td> 
                <td> </td>
                <td> </td>
                <td> </td>
                <td> </td>          
            </tr> -->
            
        </tbody>
        <tfoot id="tfoot">
        	<tr  id="count">
                      
            </tr>
        </tfoot>
    </table>
    </div>
   
    <script type="text/javascript" src="/xzfy/js/base/juicer-min.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseList/stat.js"></script>
  
</body>
</html>