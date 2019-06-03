<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>帮助文档</title>
</head>
<body style="padding:10px">
	<fieldset>
		<legend>
			<h5 style="font-size:14px;font-weight:bold;font-family:微软雅黑">帮助文档</h5>
		</legend>
	</fieldset>
	<p>
		第1步：登录网址<a href="https://qy.weixin.qq.com/" target="_blank">https://qy.weixin.qq.com/</a>，进行企业微信号的注册。
	</p>
	<p>
		<img src="1.png" style="width:800px"/>
	</p>
	<p>
		第2步：注册后，通过微信扫描二维码，登录到企业微信中。 
	</p>
	<p>
		<img src="2.png" style="width:800px"/>
	</p>
	<p>
	第3步：按照下图点击步骤所示，先点击“我的企业”，然后点击右侧菜单“权限管理”，再点击“管理组”，最后点击“+”，创建一个管理组
		
	</p>
	<p>
		<img src="3.png" style="width:800px"/>
	</p>
	<p>
		第4步：在创建的管理组中，添加好管理员、通讯录权限、应用权限，并记录下Secret，在我的企业--企业信息中，记录下CorpID。
	</p>
	<p>
		<img src="4.png" style="width:800px"/>
	</p>
	<p>
		<img src="4-1.png" style="width:800px"/>
	</p>
	<p>
		<img src="4-2.png" style="width:800px"/>
	</p>
	<p>
		第5步：将上述得到的CorpID和Secret复制到下图的位置，保存后点击界面中显示出的“测试链接”按钮，提示“连接成功”。
	</p>
	<p>
		<img src="5.png" style="width:800px"/>
	</p>
	<p>
	第6步：连接成功后点击【组织机构同步】中的【同步组织机构】按钮。初始化操作成功后，OA中的组织机构就已同步到微信企业号平台【通讯录】中。
	</p>
	<p>
		<img src="6.png" style="width:800px"/>
	</p>
	<p>
	第7步：连接成功后点击【同步用户】中的【同步所有用户】、【同步所选用户】、【删除所选用户】按钮进行同步。操作成功后，OA中的用户就已同步到微信企业号平台【通讯录】中。
	</p>
	<p>
		<img src="7.png" style="width:800px"/>
	</p>
</body>
</html>