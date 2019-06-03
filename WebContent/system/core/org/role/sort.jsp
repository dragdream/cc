<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
		String uuid = request.getParameter("uuid");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp"%>
<title>调整角色顺序</title>
	
<script type="text/javascript" charset="UTF-8">
/**
 * 上调
 */
	function func_up()
	{
	  var select1 = document.getElementById('select1');
	  sel_count=0;
	  for (i=select1.options.length-1; i>=0; i--)
	  {
	    if(select1.options[i].selected)
	       sel_count++;
	  }
	  if(sel_count==0)
	  {
	     alert("调整顺序时，请选择其中一项！");
	     return;
	  }
	  else if(sel_count>1)
	  {
	     alert("调整顺序时，只能选择其中一项！");
	     return;
	  }
	
	  i=select1.selectedIndex;//获取选中的索引

	  if(i!=0)
	  {
	    var cur_no=select1.options[i].getAttribute('no');//获取选中的对象NO属性值
	    var my_option = document.createElement("OPTION");
	    my_option.text=select1.options[i].text;
	    my_option.innerHTML = select1.options[i].text;
	    my_option.value=select1.options[i].value;
	    my_option.setAttribute('no',select1.options[i-1].getAttribute('no'));
	    select1.remove(i);	
		select1.insertBefore(my_option,select1.options[i-1]);
	    select1.options[i-1].selected=true;
	    select1.options[i].setAttribute('no',cur_no);
	  }
	}
	/**
	**下调
	*/
	function func_down()
	{
	  var select1 = document.getElementById('select1');
	  sel_count=0;
	  for (i=select1.options.length-1; i>=0; i--)
	  {
	    if(select1.options[i].selected)
	       sel_count++;
	  }
	
	  if(sel_count==0)
	  {
	     alert("调整顺序时，请选择其中一项！");
	     return;
	  }
	  else if(sel_count>1)
	  {
	     alert("调整顺序时，只能选择其中一项！");
	     return;
	  }
	
	  i=select1.selectedIndex;
	
	  if(i!=select1.options.length-1)
	  {
	    var cur_no=select1.options[i].getAttribute('no');
	    var my_option = document.createElement("OPTION");
	    my_option.text=select1.options[i].text;
	    my_option.innerHTML = select1.options[i].text;
	    my_option.value=select1.options[i].value;
	    my_option.setAttribute('no',select1.options[i+1].getAttribute('no'));
	    select1.remove(i);	
		select1.insertBefore(my_option,select1.options[i+1]);
	    select1.options[i+1].selected=true;
	    select1.options[i].setAttribute('no',cur_no);
	  }
	}
	/**
	*保存
	*/
	
	function mysubmit()
	{
	   var select1 = document.getElementById('select1');
	   priv_str=no_str="";
	   for (i=0; i< select1.options.length; i++)
	   {
	      priv_str+=select1.options[i].value+",";
	      no_str+=select1.options[i].getAttribute('no')+",";
	   }
	   
	    var url = "<%=contextPath %>/userRoleController/updateRoleSort.action";
		var para =  {sidIds:priv_str , sorts : no_str } ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.jBox.tip("保存成功！" , 'info' , {timeout:2000});
		}else{
			
			alert(jsonRs.rtMsg);
		}
	}
	
	function doInit(){
		var url =    "<%=contextPath%>/userRoleController/selectUserPrivList.action";
		var para = {};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			var dataList = jsonRs.rtData;
			var temp = "";
			for(var i = 0 ; i<dataList.length ;i++){
				 temp = temp + ' <option value="'+ dataList[i].uuid + '" no="' + dataList[i].roleNo  +  '">'+  dataList[i].roleName +'</option>';
			}
			$("#select1").append(temp);
		}else{
			alert(jsonRs.rtMsg);
		}
	}
	
	/*  <option value="39" no="1">院长2</option>

     <option value="44" no="3">党委书记</option>

     <option value="51" no="4">院领导</option> */


</script>
</head>

<body class="bodycolor" onload="doInit()" topmargin="5">

<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big"><span class="Big3"> 调整角色序号高低</span>
    </td>
  </tr>
</table>
<br>

<table class="TableBlock" width="400px" align="center">
  <tr class="TableHeader">
    <td align="center"><b>角色</b></td>
    <td align="center">排序</td>
  </tr>
  <tr class="TableData">
    <td valign="top" align="center">
    <select id="select1" name="select1" class="BigSelect" MULTIPLE style="width:300px;height:320px">
      
    </select>
    </td>
    <td align="center">
    
   		 <input type='button' class='btn btn-info btn-xs' value='↑' style="width:30px;height:40px;" onClick='func_up();'>
           <br><br>
        <input type='button' class='btn btn-info btn-xs' value='↓'  style="width:30px;height:40px;" onClick='func_down();'>
		    	

    </td>
  </tr>
  <tr class="TableControl">
    <td align="center" valign="top" colspan="4">
      <input type="button" class="btn btn-primary" value="保存" onclick="mysubmit();">&nbsp;&nbsp;
    </td>
  </tr>

</table>
</body>
</html>
