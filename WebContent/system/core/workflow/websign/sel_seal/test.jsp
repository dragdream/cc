<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%
	String host = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ;
%>
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript">


</script>

</head>

<body  topmargin="5" onload="">

<center>
<span id="SIGN_INFO_POS" name="SIGN_INFO_POS"> 印章显示区</span>
</center>

<input type="button" onclick="WebSignAddSeal()" value="测试!"/>
<div id="SIGN_INFO1" style="color: gray"  >印章显示区11</div>
<%@ include file="/system/core/workflow/websign/ver.jsp" %>
<script>
var host = '<%=host%>';
var sign_str = "";
var sign_check={};
var sign_arr = [];
var sealForm = 2; 

/**
 * 
 * @param item
 * @param callback - 回掉函数
 * @return
 */
function show_seal(item,callback)
{
  var URL = contextPath + "/system/core/workflow/websign/sel_seal/index.jsp?item=" + item +"&callback=" + callback;
  dialog(URL,  470, 400);
  //showModalWindow(URL,'选择印章' , "seal" ,300,350 , false);
}

function addSeal(item,seal_id) {
  var DWebSignSeal = document.getElementById("DWebSignSeal");
  try {
    if(DWebSignSeal.FindSeal(item+"_seal",2)!="") {
  	alert("您已经签章，请先删除已有签章！");
      return;
    }
    var str = SetStore(item);
    DWebSignSeal.SetPosition(0,0,"SIGN_POS_"+item);
    if (sealForm == 1 ) {
      DWebSignSeal.addSeal("", item+"_seal");
    } else {
      
      if(typeof seal_id == "undefined") {
        show_seal(item,"addSeal");
      } else {
    	  var URL = "http://"+ host +"?id=" + seal_id; 
    	  DWebSignSeal.AddSeal(URL, item+"_seal");
      }
    }
    DWebSignSeal.SetSealSignData(item+"_seal",str); 
    DWebSignSeal.SetMenuItem(item+"_seal",261);
  } catch (err) {
    //alert('websign控件没有加载成功！')
  }
}
function handWrite(item  , color) {
  var DWebSignSeal = document.getElementById("DWebSignSeal");
  try {
    if(DWebSignSeal.FindSeal(item+"_hw",2)!="") {
      alert("您已经签章，请先删除已有签章！");
      return;
    }
    var fontColor = 255;
    if(typeof(color) == "string" && color)
    {
      fontColor = parseInt(color);
    }
    var str=SetStore(item);
    DWebSignSeal.SetPosition(0,0,"SIGN_POS_"+item);
    DWebSignSeal.HandWrite(0,fontColor,item+"_hw");
  
    DWebSignSeal.SetSealSignData(item+"_hw",str);
    DWebSignSeal.SetMenuItem(item+"_hw",261);
  } catch (err) {
    //alert('websign控件没有加载成功！')
  }
}


function GetDataStr(item) {
  if(typeof item == 'undefined') {
    return; 
  }
  var str="";
  var separator = "::";  // 分隔符


  var TO_VAL = sign_check[item];
  if(TO_VAL!="") {
    var item_array = TO_VAL.split(",");
    for (i=0; i < item_array.length; i++) {
      var MyValue="";
      if (item_array[i] && $(item_array[i])){
        var obj =  $(item_array[i]);
        if(obj.type=="checkbox"){
          if(obj.checked==true) {
            MyValue="on";
          } else {
            MyValue="";
          }
        } else {
          MyValue=obj.value;
        }
        str += obj.name + "separator" + MyValue + "\n";
      }
    }
  }
  return str;
}

function SetStore(item)
{ 
  var DWebSignSeal = document.getElementById("DWebSignSeal");
  try {
	var str= GetDataStr(item);
    DWebSignSeal.SetSignData("-");
    DWebSignSeal.SetSignData("+DATA:" + str);
	return str;
  } catch (err) {
    //alert('websign控件没有加载成功！')
  }
}

function LoadSignData() {
  var DWebSignSeal = document.getElementById("DWebSignSeal");
  try {
    for(var i=0;i<sign_arr.length;i++) {
      if(sign_arr[i] && $(sign_arr[i])){
        DWebSignSeal.SetStoreData($(sign_arr[i]).value);
      }
    }
    DWebSignSeal.ShowWebSeals();
   
    var str= "";
    var strObjectName ;
    strObjectName = DWebSignSeal.FindSeal("",0);
    while(strObjectName  != "")
    {
      if(strObjectName.indexOf("_hw")>0)
         item_arr = strObjectName.split("_hw");
      else if(strObjectName.indexOf("_seal")>0)
         item_arr = strObjectName.split("_seal");
      if(item_arr)
      {
        str = GetDataStr(item_arr[0]);
        DWebSignSeal.SetSealSignData(strObjectName,str);
        var tmp = "4";
        if (opFlag == 1) {
          tmp = '261';
        }
        DWebSignSeal.SetMenuItem(strObjectName , tmp);
      }
      strObjectName = DWebSignSeal.FindSeal(strObjectName,0);
    }
    //加载完成标识
    SignLoadFlag = true;
  } catch (err) {
    alert('websign控件没有加载成功！')
  }
}

function WebSign_Submit(){ 
  var DWebSignSeal = document.getElementById("DWebSignSeal");
  try {
    var sign_val;
    for(var i=0;i<sign_arr.length;i++)
    {
      if(sign_arr[i]!="")
      {
        var oldstr="";
        var objName_hw = DWebSignSeal.FindSeal(sign_arr[i]+"_hw",2);
        var objName_seal = DWebSignSeal.FindSeal(sign_arr[i]+"_seal",2);
        //保存兼容老数据，老数据存在本次可写的第一个字段里。


        if(i==0)
        {
          var strObjectName = DWebSignSeal.FindSeal("",0);
          while(strObjectName !="")
          {
            if(strObjectName.indexOf("_hw")<0 && strObjectName.indexOf("_seal")<0 && strObjectName.indexOf("SIGN_INFO")<0)
               oldstr += strObjectName+";";
            strObjectName = DWebSignSeal.FindSeal(strObjectName,0);
          }
  
          if(objName_hw=="" && objName_seal=="" && oldstr=="")
            sign_val="";
          else
            sign_val=DWebSignSeal.GetStoreDataEx(oldstr+sign_arr[i]+"_hw"+";"+sign_arr[i]+"_seal");
        }
        else
        {
          if(objName_hw=="" && objName_seal=="")
            sign_val="";
          else
            sign_val=DWebSignSeal.GetStoreDataEx(sign_arr[i]+"_hw"+";"+sign_arr[i]+"_seal");
        }
        $(sign_arr[i]).value=sign_val ;
      }
    }
  } catch (err) {
    //alert('websign控件没有加载成功！')
  }
}

function WebSignAddSeal(item,seal_id) {
	  var DWebSignSeal = document.getElementById("DWebSignSeal");
	  try {
	    DWebSignSeal.SetCurrUser(1 + "["+ 11 +"]");
	    DWebSignSeal.SetSignData("-");
	    DWebSignSeal.SetSignData("+DATA:中国兵器工业信息中心");
	    DWebSignSeal.SetPosition(10,10,"SIGN_INFO_POS");
	    if (sealForm == 1 )  {
	      DWebSignSeal.addSeal("", "SIGN_INFO");
	    } else {
	      if(typeof seal_id=="undefined") {
	        show_seal(item,"WebSignAddSeal");
	      } else {
	        var URL = "http://"+ host +"/sealManage/stampPriv.action?sid=" + seal_id;
	        var obj_name = DWebSignSeal.AddSeal(URL, "SIGN_INFO");
	       
	      }
	    }
	    //DWebSignSeal.SetMenuItem(obj_name,0x8000);
	   // DWebSignSeal.SetMenuItem(obj_name,0x100);
	   // DWebSignSeal.SetMenuItem(obj_name,5);
	  } catch (err) {
	    alert('websign控件没有加载成功！')
	  }
	}
function signSubmit() {
	  var DWebSignSeal=document.getElementById("DWebSignSeal");
	  var sing_info_str="";
	  try {
	   var strObjectName = DWebSignSeal.FindSeal("",0);
	   while(strObjectName)  {
	      //判断是属于会签意见签章 并且会签名称不能是宏标记里调用显示的，即新添加的手写或签章

	      if(strObjectName.indexOf("SIGN_INFO")>=0 && sign_info_object.indexOf(strObjectName+",")<0) {
	        sing_info_str += strObjectName+";";
	      }
	      strObjectName = DWebSignSeal.FindSeal(strObjectName,0);
	    }
	    if(sing_info_str!="") {
	      $('signData').value=DWebSignSeal.GetStoreDataEx(sing_info_str);
	    } else {
	      $('signData').value="";
	    }
	  } catch (err) {
	    //alert('websign控件没有加载成功！')
	  }
	  return sing_info_str;
	}

function WebSignHandWritePop() {
	alert(22);
	  var DWebSignSeal=document.getElementById("DWebSignSeal");
	  try {
	    DWebSignSeal.SetCurrUser("1" + "["+ "shenhua" +"]");
	    DWebSignSeal.SetSignData("-");
	    DWebSignSeal.SetSignData("+DATA:中国兵器工业信息中心");
	    DWebSignSeal.SetPosition(0,-30,"SIGN_INFO1");
	    var obj_name=DWebSignSeal.HandWritePop(0,255,0,0,0,"SIGN_INFO");
	    DWebSignSeal.SetMenuItem(obj_name,5);
	  } catch (err) {
	   // alert('websign控件没有加载成功！')
	  }
	}
</script>
</body>
</html>