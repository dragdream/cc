<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>菜单分类设置</title>

</head>
<body style="min-height:400px">
<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big"><img src="<%=imgPath%>/menu/system.gif" HEIGHT="20" align="absmiddle"><span class="big3">&nbsp;菜单设置</span>
    </td>
  </tr>
</table>

<table class="" align="center">
  <tr>
    <td class="msg info">
      <h4 class="title">菜单定义简明指南</h4>
      <div class="content" style="font-size:12pt">
1、通过灵活定义菜单，可以挂接外部的B/S、C/S或单机版系统。<br><br>
2、菜单定义实际并不难，如果存在疑问，中安科技咨询软件开发商获得详尽的帮助。<br><br>
3、菜单定义的关键是理解菜单编号的含义：作为排序之用，在同一父菜单下的平级菜单，该代码不能重复,唯一性标识，菜单项代码为三位数字。<br><br>
4、同一层次的菜单项代码建议保留一定间隔，以方便日后在中间插入菜单项。<br><br>
5、新增的菜单项，只有进行菜单权限设置后才能看到。
</div>
    </td>
  </tr>
</table>
</body>
</html>