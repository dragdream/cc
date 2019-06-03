<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/header/header.jsp" %>
<title>系统界面设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/colorPicker/css/colorPicker.css">
<style type="text/css">

</style>
<script type="text/javascript" src="<%=contextPath %>/common/colorPicker/colorPicker.js"></script>


<script type="text/javascript">
$(function(){
	 $('#colorpickerHolder').ColorPicker({flat: true}); 
	
	$('#colorpickerHolder2').ColorPicker({
		flat: true,
		color: '#00ff00',
		onSubmit: function(hsb, hex, rgb ,el) {
			//alert(hsb+ ":"+  hex);
			$('#colorSelector2 div').css('backgroundColor', '#' + hex);
	
		}
	});
	$('#colorpickerHolder2>div').css('position', 'absolute');
	var widt = false;
 	$('#colorSelector2').bind('click', function() {
		$('#colorpickerHolder2').stop().animate({height: widt ? 0 : 173}, 500);
		widt = !widt;
	})  
	
	
	
	$('#colorpickerField1, #colorpickerField2, #colorpickerField3').ColorPicker({
		onSubmit: function(hsb, hex, rgb, el) {
			$(el).val(hex);
			$(el).ColorPickerHide();
			alert("bb");
		},
		onBeforeShow: function () {
			$(this).ColorPickerSetColor(this.value);
			alert("ccc")
		}
	})
	.bind('keyup', function(){
		alert("ddd")
		$(this).ColorPickerSetColor(this.value);
		
	});
	
	
	
	
	$('#colorSelector').ColorPicker({
		color: '#0000ff',
		onShow: function (colpkr) {
			$(colpkr).fadeIn(500);
			return false;
		},
		onHide: function (colpkr) {
			$(colpkr).fadeOut(500);
			return false;
		},
		onChange: function (hsb, hex, rgb) {
			//$('#colorSelector div').css('backgroundColor', '#' + hex);
		},onSubmit: function(hsb, hex, rgb, el) {
			$(el).val(hex);
			$(el).ColorPickerHide();
			$('#colorSelector div').css('backgroundColor', '#' + hex);
		}
	});
	

});
</script>
</head>
<body>
    <div class="pagedemo">
        <h1>颜色选择器 - jQuery插件</h1>
        <div class="tabsContent">
            <div class="tab">
                <h2>介绍</h2>
                <p>一个简单的组件来选择色彩在你选择的颜色一样Adobe Photoshop</p>
                <h3>特征</h3>
                <ul>
                    <li>在页面元素平面模式</li>
                    <li>颜色选择功能强大的控件</li>
					<li>容易定制通过改变一些图像的外观</li>
					<li>可视选择器颜色变化</li>
                </ul>
                <h3>例子</h3>
                <p>平面模式</p>
                <p id="colorpickerHolder">
                </p>
                <pre>
$('#colorpickerHolder').ColorPicker({flat: true});
                </pre>
                <p>自定义皮肤，并使用平面模式显示在一个自定义的颜色选择器部件。.</p>
				<div id="customWidget">
					<div id="colorSelector2"><div style="background-color: #00ff00"></div></div>
	                <div id="colorpickerHolder2">
	                </div>
				</div>

				<p>附加到一个文本字段，并使用回调函数来更新字段的值的颜色，并提交颜色设置在该领域的值。</p>
				<p><input type="text" maxlength="6" size="6" id="colorpickerField1" value="00ff00" /></p>
				<p><input type="text" maxlength="6" size="6" id="colorpickerField3" value="0000ff" /></p>
				<p><input type="text" maxlength="6" size="6" id="colorpickerField2" value="ff0000" /></p>
				<pre>$('#colorpickerField1, #colorpickerField2, #colorpickerField3').ColorPicker({
	onSubmit: function(hsb, hex, rgb, el) {
		$(el).val(hex);
		$(el).ColorPickerHide();
	},
	onBeforeShow: function () {
		$(this).ColorPickerSetColor(this.value);
	}
})
.bind('keyup', function(){
	$(this).ColorPickerSetColor(this.value);
});
</pre>
				<p>使用回调的实现预览的颜色和添加颜色动画。</p>
				<p>
					<div id="colorSelector"><div style="background-color: #0000ff"></div></div>
					
					<div id="colorpickerHolder2">
	                </div>
				</p>
				<pre>
$('#colorSelector').ColorPicker({
	color: '#0000ff',
	onShow: function (colpkr) {
		$(colpkr).fadeIn(500);
		return false;
	},
	onHide: function (colpkr) {
		$(colpkr).fadeOut(500);
		return false;
	},
	onChange: function (hsb, hex, rgb) {
		$('#colorSelector div').css('backgroundColor', '#' + hex);
	}
});
</pre>
            </div>
        </div>
    </div>
</body>
</html>