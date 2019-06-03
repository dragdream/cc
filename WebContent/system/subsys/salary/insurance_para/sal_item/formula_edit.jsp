<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
    
<%
	int accountId = TeeStringUtil.getInteger(request.getParameter("accountId"), 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf8"/>


<TITLE>公式编辑</TITLE>

<style >
.inputClass {
   padding:2px 10px;
}
</style>
<script type="" src="<%=contextPath %>/system/subsys/salary/js/account.js"></script>
<script language="javascript">
var parent_window =xparent;
$.extend($.fn,{
	
	 //获取文本框内光标位置
    getSelectionStart: function() {
      var e = this[0];
      if (e.selectionStart) {
        return e.selectionStart;
      } else if (document.selection) {
        e.focus();
        var r=document.selection.createRange();
        var sr = r.duplicate();
        sr.moveToElementText(e);
        sr.setEndPoint('EndToEnd', r);
        return sr.text.length - r.text.length;
      }
     
      return 0;
    },
    getSelectionEnd: function() {
      var e = this[0];
      if (e.selectionEnd) {
        return e.selectionEnd;
      } else if (document.selection) {
        e.focus();
        var r=document.selection.createRange();
        var sr = r.duplicate();
        sr.moveToElementText(e);
        sr.setEndPoint('EndToEnd', r);
        return sr.text.length;
      }
     
      return 0;
    },
 
    //自动插入默认字符串
    insertString: function(str) {
      $(this).each(function() {
          var tb = $(this);
          tb.focus();
          if (document.selection){
              var r = document.selection.createRange();
              document.selection.empty();
              r.text = str;
              r.collapse();
              r.select();
          } else {
              var newstart = tb.get(0).selectionStart+str.length;
              tb.val(tb.val().substr(0,tb.get(0).selectionStart) +
          str + tb.val().substring(tb.get(0).selectionEnd));
              tb.get(0).selectionStart = newstart;
              tb.get(0).selectionEnd = newstart;
          }
      });
     
      return this;
    },
    setSelection: function(startIndex,len) {
      $(this).each(function(){
        if (this.setSelectionRange){
          this.setSelectionRange(startIndex, startIndex + len); 
        } else if (document.selection) {
          var range = this.createTextRange(); 
          range.collapse(true); 
          range.moveStart('character', startIndex); 
          range.moveEnd('character', len); 
          range.select();
        } else {
          this.selectionStart = startIndex;
          this.selectionEnd = startIndex + len;
        }
      });
     
      return this;
    },
    getSelection: function() {
      var elem = this[0];
   
        var sel = '';
        if (document.selection){
            var r = document.selection.createRange();
            document.selection.empty();
            sel = r.text;
        } else {
            var start = elem.selectionStart;
            var end = elem.selectionEnd;
        var content = $(elem).is(':input') ? $(elem).val() : $(elem).text();
            sel = content.substring(start, end);
        }
        return sel;
    }
  });
	
function getCursortPosition (ctrl) {//获取光标位置函数
	var CaretPos = 0; // IE Support 
	if (document.selection) { 
		ctrl.focus ();  
		var Sel = document.selection.createRange (); 
		Sel.moveStart ('character', 
				-ctrl.value.length);
		CaretPos = Sel.text.length; 
	} // Firefox support 
	else if (ctrl.selectionStart || ctrl.selectionStart == '0') 
		CaretPos = ctrl.selectionStart;
	
	return (CaretPos);
}  
		
function setCaretPosition(ctrl, pos){//设置光标位置函数 
	if(ctrl.setSelectionRange) { 
		ctrl.focus(); ctrl.setSelectionRange(pos,pos); 
	} else if (ctrl.createTextRange) {  
		var range = ctrl.createTextRange(); 
		range.collapse(true); 
		range.moveEnd('character', pos); 
		range.moveStart('character', pos);
		range.select(); 
			
	} 	
} 

	
//将LIST内容插入到光标指定位置
function insertAtCaret (textEl, text)
{
	var caretPos = getCursortPosition(textEl);
    if (caretPos)
    {
        var caretPos = textEl.caretPos;
        $(textEl).insertString(text);
        //caretPos.text = text;//caretPos.text.charAt(caretPos.text.length - 1) == ' ' ? text + ' ' : text;
    }
    else
    {
        textEl.value += text;
    }

    textEl.focus();
}

//编辑公式
function funEditFormula ()
{
    if (document.formMain.selectList.selectedIndex < 0)
        return;

    var strValue = document.formMain.selectList.item(document.formMain.selectList.selectedIndex).value;
    var aryData = strValue.split (";");

    if (aryData [2] == "IN")
    {
        alert ("您所选择的字段是输入项，不是公式，不能编辑！");
        document.formMain.selectList.focus ();
        return;
    }

    if (!confirm ("确定要编辑该公式吗？"))
        return;

    var strFormulaCont = "";
    for (var i = 0; i < aryDataFormula.length; i ++)
    {
        if (aryDataFormula [i]["PAYPROID"] == aryData [0])
        {
            strFormulaCont = aryDataFormula [i]["CONTENT"];
            break;
        }
    }

    document.formMain.textFormula.value = strFormulaCont;
    document.formMain.btnFormulaID.value = aryData [0];
    document.formMain.btnFormulaName.value = document.formMain.selectList.item(document.formMain.selectList.selectedIndex).text;

    document.formMain.FormulaName.value = "公式编辑：" + document.formMain.btnFormulaName.value + "";
}

//公式校验
function funCheck ()
{
//     if (document.formMain.textFormula.value == "")
//     {
//         alert ("请输入公式内容！");
//         document.formMain.textFormula.focus ();
//         return;
//     }
    
//     if(document.formMain.textFormula.value.indexOf('if') < 0  ){
//     	reVal = /([^<\(\+\-\*\/]|[\+\-\*\/]{2,})(\[|\(|<)/;

//     	    if (reVal.test(document.formMain.textFormula.value))
//     	    {
//     	        //alert (RegExp.$1 +"1\n"+RegExp.$2);
//     	        alert ("公式不正确，请检查括号及+-*/是否匹配！");
//     	        return;
//     	    }
//     	    reVal = /(\]|\)|>)([^>\)\+\-\*\/]|[\+\-\*\/]{2,})/;

//     	    if (reVal.test(document.formMain.textFormula.value))
//     	    {
//     	        //alert (RegExp.$1 +"2\n"+RegExp.$2);
//     	        alert ("公式不正确，请检查括号及+-*/是否匹配！");
//     	        return;
//     	    }
//     }
    

    //reVal = /(([^\(\+\-\*\/]|[\+\-\*\/]{2,})(\[|\())|((\]|\))([^\)\+\-\*\/]|[\+\-\*\/]{2,}))/;
    //if (reVal.test(document.formMain.textFormula.value))
    //{
    //    alert ("公式不正确，请检查括号及+-*/是否匹配！");
    //    return;
    //}

}

//公式保存
function SaveInfo ()
{
//     if (document.formMain.textFormula.value == "")
//     {
//         alert ("请输入公式内容！");
//         document.formMain.textFormula.focus ();
//         return;
//     }
//     if(document.formMain.textFormula.value.indexOf('if') < 0  ){
//     	  reVal = /([^<\(\+\-\*\/]|[\+\-\*\/]{2,})(\[|\(|<)/;
//    		  if (reVal.test(document.formMain.textFormula.value))
//  		    {
//  		        //alert (RegExp.$1 +"1\n"+RegExp.$2);

//  		        alert ("公式不正确，请检查括号及+-*/是否匹配！");
//  		        return;
//  		    }
//  		    reVal = /(\]|\)|>)([^>\)\+\-\*\/]|[\+\-\*\/]{2,})/;
//  		    if (reVal.test(document.formMain.textFormula.value))
//  		    {
//  		        //alert (RegExp.$1 +"2\n"+RegExp.$2);
//  		        alert ("公式不正确，请检查括号及+-*/是否匹配！");
//  		        return;
//  		    }
//     }

  

    if(checkAndParse() == false){
    	alert ("公式不正确，请检查输入的字段是否有效！");
    	return 
    }
   var s1=document.formMain.textFormula.value;
     var re;
   //alert(s1);
   //re=/\[([^$,\]])*/gi;
   //var r=s1.replace(re, "[");
   //alert(r);
  /*  parent_window.form1.formula.value=r;
   parent_window.form1.formulaName.value = document.formMain.textFormula.value;
   */
   window.close(); 
}

function getString(){
	var str = "sdsdds[sd]sdsd,[[sd3]";
	var regex = /\[(.*?)\]/g;
	var v = str.match(regex);
	for(var i =0 ;i <v.length ; i++){
		var ttt = v[i];
		var ttt =  ttt.replace(/\[/g , '').replace(/\]/g , '');
		//v =  v.replace("]" , '');
		alert(ttt);
	}
	
}
/**
 * 检查及转换
 */
function checkAndParse(){
	var s1=document.formMain.textFormula.value;
	var regex = /\[(.*?)\]/g;
	var v = s1.match(regex);
	if(v ){
		for(var i =0 ;i <v.length ; i++){
			var itemName = v[i];
			
			var itemName =  itemName.replace(/\[/g , '').replace(/\]/g , '');;
			var itemColumn = getItemColumnByIteName(itemName);
			if(itemColumn == ''){
				return false;
			}
			//alert(itemName +":"+ itemColumn) 
			s1 = s1.replace( "[" +itemName + "]" , "[" + itemColumn +"]" );
		}
	}
	//alert(s1)
	
	parent_window.form1.formula.value=s1;
	parent_window.form1.formulaName.value = document.formMain.textFormula.value;
	return true;
}
function getItemColumnByIteName(itemName){
	for(var i = 0 ; i < itemPrcs.length ;  i ++){
		var item = itemPrcs[i];
		if(itemName == item.itemName){//存在
			return item.itemColumn;
		}
	}
	return "";
}
var itemPrcs ;
function SetValue()
{
	//获取自定义项目 ---包含保险参数
	var accountId =  <%=accountId%>;
	var url = contextPath + "/teeSalItemController/getFormulaItemByAccountId.action";
	var json = tools.requestJsonRs(url,{accountId:accountId});
	if(json.rtState){
		var prcs = json.rtData;
		itemPrcs = prcs;
		var options = "";
		for ( var i = 0; i < prcs.length; i++) {
			options = options + "<option value='" + prcs[i].sid + "'>" + prcs[i].itemName + "</option>";
		}
		$("#selectList").append(options);
	}else{
		alert(json.rtMsg);
		return;
	}

	
     if(accountId != "")
    {
        document.all("FormulaID").value=parent_window.form1.formula.value;
        document.all("textFormula").value=parent_window.form1.formulaName.value;
    }

}

</script>

</HEAD>

<BODY class="panel" onload="SetValue()" style="padding:10px;">

<form method="POST" action="" name="formMain" style="width:550px">
<input type="hidden" name="FormulaID" value="">

  <table  border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
      <td colspan="5">
        <table border="0" cellpadding="0" cellspacing="0" width="500">
          <tr class="TableHeader">
            <td align="left" style="font-weight: bold;">
               <script language="javascript">
                if(xparent.form1.itemName.value!="")
                document.write(xparent.form1.itemName.value+"=")
               </script>

            </td>
          </tr>
          <tr class="TableData">
          <td>
         <textarea name="textFormula" cols="85" rows="5" class="BigTextarea" ></textarea>
         </td>
         </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td width="59%" colspan="3">
        <br><b>参与可计算的工资项目：</b></td>
        <td width="38%" colspan="2">
        <br><b>参与可计算的函数：</b></td>
    </tr>
    <tr>
      <td width="12%">
        <select name="selectList" id="selectList" size="15" class="BigSelect" onclick="insertAtCaret(document.formMain.textFormula,  '[' + $(this).find('option:selected').text() + ']');">
        
        </select>
       </td>
      <td width="16%">
        <table width="159">
           <tr valign="middle">
            <td width="35" >
              <input type="button" name="btnAdd" value=" 1 " class="btn btn-primary btn-xs inputClass" onClick="insertAtCaret(document.formMain.textFormula, '1');">
            </td>
            <td width="35" >
              <input type="button" name="btnDec" value=" 2 " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '2');">
            </td>
            <td width="35" >
              <input type="button" name="btnMul" value=" 3 " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '3');">
            </td>
            <td width="36" >
              <input type="button" name="btnAdd" value=" + " class="btn btn-primary btn-xs inputClass" onClick="insertAtCaret(document.formMain.textFormula, '+');">
            </td>
          </tr>
            <tr valign="middle">
            <td width="35" >
              <input type="button" name="btnAdd" value=" 4 " class="btn btn-primary btn-xs inputClass" onClick="insertAtCaret(document.formMain.textFormula, '4');">
            </td>
            <td width="35" >
              <input type="button" name="btnDec" value=" 5 " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '5');">
            </td>
            <td width="35" >
              <input type="button" name="btnMul" value=" 6 " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '6');">
            </td>
             <td width="36" >
              <input type="button" name="btnDec" value=" - " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '-');">
            </td>
          </tr>

           <tr valign="middle">
            <td width="35" >
              <input type="button" name="btnAdd" value=" 7 " class="btn btn-primary btn-xs inputClass" onClick="insertAtCaret(document.formMain.textFormula, '7');">
            </td>
            <td width="35" >
              <input type="button" name="btnDec" value=" 8 " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '8');">
            </td>
            <td width="35" >
              <input type="button" name="btnMul" value=" 9 " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '9');">
            </td>
            <td width="36" >
              <input type="button" name="btnMul" value=" * " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '*');">
            </td>
          </tr>

          <tr valign="middle">
             <td width="35" >
              <input type="button" name="btnDiv" value=" 0 " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '0');">
            </td>
             <td width="35" >
              <input type="button" name="btnDiv" value=" . " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '.');">
            </td>
             <td width="35" >
              <input type="button" name="btnLeft" value=" = " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '=');">
            </td>
            <td width="36" >
              <input type="button" name="btnDiv" value=" / " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '/');">
            </td>

          </tr>
           <tr valign="middle">
           <td width="35" >
              <input type="button" name="btnLeft" value=" < " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '<');">
            </td>
            <td width="35">
              <input type="button" name="btnRight" value=" > " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '>');">
            </td>
             <td width="35" >
              <input type="button" name="btnLeft" value=" ( " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, '(');">
            </td>
            <td width="36">
              <input type="button" name="btnRight" value=" ) " class="btn btn-primary btn-xs inputClass" onclick="insertAtCaret(document.formMain.textFormula, ')');">
            </td>
            </tr>
          <tr>
            <td colspan="2">
              <input type="button" name="btnCheck" value=" 校验 " class="btn btn-success btn-xs inputClass" onClick="funCheck()">

            </td>
            <td colspan="2" align="right">
              &nbsp;<input type="button" name="btnClean" value=" 清空 " class="btn btn-success btn-xs inputClass" onClick='javascript:document.formMain.textFormula.value="";'>
 &nbsp;
            </td>
           <tr/>
           <tr>
             <td colspan="4" align="center">
             
             <input type="button" name="btnCheck" value=" 确定 " class="btn btn-primary btn-xs inputClass" onClick="SaveInfo()">

            </td>
          
          </tr>
        </table>
      </td>
      <td width="1%" >　</td>
      <td width="24%" >
      
       		
       		
       	  <select name="selectList" size="15" class="BigSelect" onclick="insertAtCaret(document.formMain.textFormula,  $(this).val());">
              <option value="if(){}">if 条件语句</option>
                <option value="else{}">else 否则</option>
              <option value="Abs()">Abs 绝对值</option>
              <option value="Avg()">Avg 平均值</option>
              <option value="PFax()">PFax 计算个人所得税</option>
          </select>
      </td>
    </tr>
  </table>
</form>
</BODY>
</HTML>
