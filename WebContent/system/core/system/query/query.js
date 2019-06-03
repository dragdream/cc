/**
 * 查看公告信息
 * @param state- 状态  0-未读  1- 已读
 */
function jBoxNotifyDetail(id  )
{
	var url = contextPath + "/system/core/base/notify/person/readNotify.jsp?id=" + id ;
	top.$.jBox("iframe:"+url,
			{title:"查看公告通知详情",width:800,height:400,buttons: {"关闭":true}});
}
/**
 * 查看通讯录详情
 * @param id
 */
function jBoxAddressDetail(id  )
{
	var url = contextPath + "/system/core/base/address/public/address/addressDetail.jsp?id=" + id ;
	top.$.jBox("iframe:"+url,
			{title:"查看通讯信息详情",width:800,height:400,buttons: {"关闭":true}});
}

/**
 * 
 * @param runId
 * @param flowId
 * @param frpSid
 */
function jBoxForwardDetail(runId,flowId,frpSid  )
{
	var para = "runId="+runId+"&view=31";
	var url = contextPath + "/system/core/workflow/flowrun/print/index.jsp?" + para ;
	window.openFullWindow(url,"查看流程信息");
	/*top.$.jBox("iframe:"+url,
			{title:"查看流程信息详情",width:1000,height:550,buttons: {"关闭":true}});*/
}


