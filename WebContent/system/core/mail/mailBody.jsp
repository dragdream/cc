<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<%@ include file="/system/core/mail/loading/loading.jsp" %>
	<%@ include file="/header/upload.jsp"%>

	<title>mail</title>
	<link href="<%=cssPath%>/bootstrap.min.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/system/core/mail/style/skin1/pagination.css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style_without_tab.css"/>
		<!-- 引入respond.js解决IE8显示问题 -->
	<script type="text/javascript" src="<%=contextPath%>/common/bootstrap/js/respond.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=contextPath%>/common/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" charset="UTF-8">
		var editor ;
		function doInit(){
			$("[title]").tooltips();
			editor= new UE.ui.Editor({
			      toolbars : [ [  'source', '|', 'undo', 'redo', '|', 'bold',
			                     'italic', 'underline', 'fontborder', 'strikethrough', 'superscript',
			                     'subscript', 'removeformat', 'formatmatch', 'autotypeset',
			                     'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor',
			                     'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc',
			                     '|', 'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
			                     'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
			                     'directionalityltr', 'directionalityrtl', 'indent', '|', 'print',
			                     'preview', 'searchreplace', 'help' ] ]
			,elementPathEnabled : false,				            
			//初始化全屏
            fullscreen : true,
            //关闭字数统计
            wordCount:false
			               });
			editor.render("content");
			editor.ready(function() {
				parent.window.setContent();
				//editor.setContent("测试");
			    editor.setHeight(500);
		    });
			//alert($('#sub-tabs', parent.document).html()); 
			//var text = $('#sub-tabs', parent.document).html();
			
		}
		function sendMail(){
			//if(checkMail()){
				var url = "<%=contextPath %>/mail/addOrUpdateMail.action";
				var para =  tools.formToJson($("#form1")) ;
				para["model"] = "EMAIL";
				$("#form1").doUpload({
					url:url,
					success:function(json){
						alert("保存成功");
						window.location.reload();
					},
					post_params:para
				});
			//}
		}
	</script>
	<style type="text/css">

	</style>
</head>
<body  style="overflow-x:hidden;" onload="doInit();">
<form role="form" id= "form1" name = "form1" enctype="multipart/form-data" method="post">
	<table  align="center" class="TableBlock" style="width: 100%;height:auto;">
		<TR class="TableContent">
			<TD id="SIGN_INFO_FEEDBACK">
				<DIV>
					<textarea style="height:100%" id="content" placeholder="邮件内容..." name="content" ></textarea>
					<input id="subject" name="subject" type="hidden"/>
					 <script type="text/javascript">
					 /*
				        var editor = UE.getEditor('content',{
				            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
				            toolbars:[
				            [ 'source', '|', 'undo', 'redo', '|',
				                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
				                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
				                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
				                'emotion',
				                'print', 'preview', 'searchreplace', 'help']
				       	    ],
				            //focus时自动清空初始化时的内容
				            autoClearinitialContent:true,
				            //关闭字数统计
				            wordCount:false,
				            //初始化全屏
				            fullscreen : true,
				            //关闭elementPath
				            elementPathEnabled:false,
				            //默认的编辑区域高度
				            initialFrameHeight:500,
				            //默认的编辑区域宽度
				            //initialFrameWidth:100
				            //更多其他参数，请参考ueditor.config.js中的配置项
				        });
					 */
 							</script>
				</DIV>
			</TD>
		</TR>
	</table>
</form>

</body>

</html>
