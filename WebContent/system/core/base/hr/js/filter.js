/**
 * 筛选方式详情
 */
var  filterItemType = [{itemId:"一" ,itemName:"初选"},{itemId:"二" ,itemName:"复选"},{itemId:"三" ,itemName:"决选"}
   ,{itemId:"四" ,itemName:"加试"},{itemId:"五" ,itemName:"加试"},{itemId:"六" ,itemName:"加试"}
   ,{itemId:"七" ,itemName:"加试"},{itemId:"八" ,itemName:"加试"},{itemId:"九" ,itemName:"加试"}];

/**
 * 查询办理详情
 * @param sid
 */
function handleDetail(sid){
	var  url = contextPath + "/system/core/base/hr/filter/handleDetail.jsp?sid=" + sid;
	bsWindow(url ,"招聘筛选办理详情",{width:"1000",height:"400",buttons:
		[
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		}else if(v=="关闭"){
			return true;
		}
	}});

}