
var item_id_multiple = "";
var item_id_multiple_name = "";


var id_field_array = new Array();//选中数组  (目前包括 Id和name属性)[{id:1,name:'aaa'} ,{id:2,name:'bbb'} ]
var to_id_field_value = "";//选中Id字符串，以逗号分隔
var to_name_field_value = "";//选中name字符串，以逗号分隔

/**
 * 初始化选择
 */
function load_init()
{

   //顶部标签点击事件
   jQuery('#navMenu > a').click(function(){
      jQuery(this).siblings().removeClass('active');
      jQuery(this).addClass('active');
      jQuery('.main-block').hide();
      
      var block = jQuery(this).attr('block');
      jQuery('#block_' + block).show();
      init_item(block);
      if(block == "selected" || jQuery('#' + block + '_item').text() == "" && block != "query")
      {
         var url = item_url + '?ACTION=' + block + '&' + query_string;
         if(block == "selected")
            url += '&TO_ID_STR=' + encodeURIComponent(to_id_field.value);
         
         get_items(url, block, '0');
      }
   });
   
   //搜索框的输入事件
   jQuery('#keyword').keyup(function(event){
      if(this.value == "")
      {
         jQuery('#search_clear').hide();
         return;
      }
      
      jQuery('#search_clear').show();
      if(jQuery('#query_item:visible').length <= 0)
         jQuery('#navMenu > a[block="query"]').triggerHandler('click');

      var url = item_url + '?ACTION=query&' + query_string + '&KEYWORD=' + this.value;
      get_items(url, 'query', '0');
   });
   
   //清除按钮的点击事件
   jQuery('#search_clear').click(function(event){
      jQuery('#keyword').val('');
      jQuery(this).hide();
      jQuery('#keyword').focus();
   });
   
   //左侧角色、分组等点击、鼠标hover事件
   jQuery('div.panel-group .list-group-item').live('click', function(){
      jQuery(this).siblings().removeClass('active');
	   //jQuery('div.panel-group .list-group-item').removeClass('active');
     // if(jQuery(this).attr('class').indexOf('active') < 0)
     // {
         jQuery(this).addClass('active');
         get_new_items( jQuery(this).attr('block'), jQuery(this).attr('item_id'),jQuery(this).attr('item_name'));
     // }
   });
   /**
   jQuery('div.left .list-group-item').live('mouseenter', function(){
      jQuery(this).addClass('hover');
   });
   jQuery('div.left .list-group-item').live('mouseleave', function(){
      jQuery(this).removeClass('hover');
   });
   */
 /*  //右侧用户列表点击、鼠标hover事件
   jQuery('div.right .list-group-item').bind('click', function(){
      if(single_select)
         jQuery(this).siblings().removeClass('active');
      
      jQuery(this).toggleClass('active');
      if(jQuery(this).attr('class').indexOf('active') < 0){
          remove_item(this.getAttribute("item_id"), this.getAttribute("item_name"));
      }
      else{
    	  add_item(this.getAttribute("item_id"), this.getAttribute("item_name"));
      }
       
   });*/
   /**
   jQuery('div.right .block-right-item').live('mouseenter', function(){
      jQuery(this).addClass('hover');
   });
   jQuery('div.right .block-right-item').live('mouseleave', function(){
      jQuery(this).removeClass('hover');
   });
   */
   //右侧全部添加点击、鼠标hover事件
   jQuery('#addAll').live('click', function(){
       jQuery(this).siblings('.list-group-item').each(function(){
           add_item(this.getAttribute("item_id"), this.getAttribute("item_name"));
           jQuery(this).addClass('active');
       });
   });
   /**
   jQuery('div.right .block-right-add').live('mouseenter', function(){
      jQuery(this).addClass('add-hover');
   });
   jQuery('div.right .block-right-add').live('mouseleave', function(){
      jQuery(this).removeClass('add-hover');
   });
   */
   //右侧全部删除点击、鼠标hover事件
   jQuery('#removeAll').live('click', function(){
       jQuery(this).siblings('.list-group-item').each(function(){
          remove_item(this.getAttribute("item_id"), this.getAttribute("item_name"));
          jQuery(this).removeClass('active');
       });
   });
   /**
   jQuery('div.right .block-right-remove').live('mouseenter', function(){
      jQuery(this).addClass('remove-hover');
   });
   jQuery('div.right .block-right-remove').live('mouseleave', function(){
      jQuery(this).removeClass('remove-hover');
   });
   */
  
   
   //右侧全体部门点击、鼠标hover事件
   jQuery('div.right .block-right-alldept').live('click', function(){
      to_id_field.value = jQuery(this).attr('item_id');
      to_name_field.value = jQuery(this).attr('item_name');
      close_window();
   });
   jQuery('div.right .block-right-alldept').live('mouseenter', function(){
      jQuery(this).addClass('alldept-hover');
   });
   jQuery('div.right .block-right-alldept').live('mouseleave', function(){
      jQuery(this).removeClass('alldept-hover');
   });
   
   //右侧返回点击、鼠标hover事件
   jQuery('div.right .block-right-goback').live('click', function(){
      var block = jQuery(this).attr('block');
      var item_id = jQuery(this).attr('item_id');
      jQuery('#' + block + '_item').children().hide();
      jQuery('#' + block + '_item_' + item_id).show();
   });
   jQuery('div.right .block-right-goback').live('mouseenter', function(){
      jQuery(this).addClass('goback-hover');
   });
   jQuery('div.right .block-right-goback').live('mouseleave', function(){
      jQuery(this).removeClass('goback-hover');
   });
   
   jQuery('input.BigButtonA').hover(
      function(){this.className = 'BigButtonAHover';},
      function(){this.className = 'BigButtonA';}
   );
}

function load_init_item(){
	 
	  //右侧用户列表点击、鼠标hover事件
	   jQuery('div.right .list-group-item').bind('click', function(){
	      if(single_select)
	         jQuery(this).siblings().removeClass('active');
	      
	      jQuery(this).toggleClass('active');
	      if(jQuery(this).attr('class').indexOf('active') < 0){
	          remove_item(this.getAttribute("item_id"), this.getAttribute("item_name"));
	      }
	      else{
	    	  add_item(this.getAttribute("item_id"), this.getAttribute("item_name"));
	      }
	       
	   }); 
}

function close_window()
{
   if(typeof(window.external) == 'undefined' || typeof(window.external.OA_SMS) == 'undefined' || !window.external.OA_SMS("", "", "GET_VERSION") || window.external.OA_SMS("", "", "GET_VERSION") < '20110223')
   {
      window.open('','_self','');
      CloseWindow();
   }
   else
   {
      window.external.OA_SMS("", "", "CLOSE_WINDOW")
   }
}

function trigger_callback(type, args ){
	//alert(args)

    var parent_window = jQuery.browser.msie ? parent.dialogArguments : parent.opener;

    if(typeof parent_window == 'object' && typeof parent_window[type] == 'function'){
        parent_window[type].apply(this, args );
    }
}

/**
 * 选中项
 * @param item_id   Id
 * @param item_name  name
 * @param extend  扩展字段---比如人员在线状态
 */
function add_item(item_id, item_name , extend)
{
	//var toIdFiledName  = to_id_field.name;//文档框名称
	var callBackFunc =   "ORG_SELECT_ADD_BACH_FUNC";//回调函数
	
	
	 arguments.length = 5;
     var callBackParaObj ;
     if(callBackPara && callBackPara != ''){
    	 callBackParaObj = tools.string2JsonObj(callBackPara);
     }

	if(objSelectType &&objSelectType == '1'){//当为多选框时
		getMultiple();
		/*to_id_field_value = item_id_multiple;
		to_name_field_value = item_id_multiple_name;*/
	}else if(objSelectType == '2'){//DIV
		
	}

	//var to_id_field_value = to_id_field.value;
   if(!item_id || !item_name)
      return;
   
   var item = {id:item_id , name:item_name};
   if(single_select)//单选
   {
	  id_field_array.length = 0;//清空数组
	
	  id_field_array.push(item);//添加数组
      to_id_field.value = item_id;
      to_name_field.value = item_name;
	  arguments[2] =  to_id_field.value;
	  arguments[3] = to_name_field.value;
	  arguments[4] = callBackParaObj;
      trigger_callback(callBackFunc, arguments  );
      close_window();
      
      if(xparent.ChangeInputSize){
  		xparent.ChangeInputSize();
  	}
      return;
   }

   //添加 数组 页面文本属性
   var isAddItem = true;
   for(var i =0 ; i<id_field_array.length ; i++){//循环所选项
	   var itemTemp = id_field_array[i];
	   if(itemTemp.id == item_id){//如果相等
		   isAddItem = false;
		   break;
	   }
   }
   if(isAddItem){
	   id_field_array.push(item);//添加对象
	   
	   if(to_id_field.value == ""){// 等于空，直接赋值
		   to_id_field_value = item_id;
		   to_name_field_value = item_name;
	   }else{
		   if(to_id_field.value.substring(to_id_field.value.length - 1 , to_id_field.value.length) == ','){
			   to_id_field_value = to_id_field.value + item_id ;
			   to_name_field_value = to_name_field.value + item_name;
		   }else{
			   to_id_field_value = to_id_field.value + "," + item_id ;
			   to_name_field_value = to_name_field.value + "," + item_name;
		   }
	   }
   }
  
   to_id_field.value = to_id_field_value;
   to_name_field.value = to_name_field_value;

   /**
    * 判断是否存在此方法，转为多选人员，第三栏处理
    */
   if( window.dataSelectLoadInit ){  
	    dataSelectLoadInit(id_field_array);  
	 }   
   
   if(xparent.ChangeInputSize){
		xparent.ChangeInputSize();
	}
   
  // arguments.push(to_id_field.value );
  // arguments.push(to_name_field.value );


   arguments[2] =  to_id_field.value;
   arguments[3] = to_name_field.value;
   arguments[4] = callBackParaObj;
   trigger_callback(callBackFunc, arguments );;
}
/**
 * 删除所选节点
 * @param item_id
 * @param item_name
 * @param type 1-处理多选人员 其它-不处理
 */
function remove_item(item_id, item_name,type)
{
	var callBackFunc =   "ORG_SELECT_REMOVE_BACH_FUNC";//回调函数
	
	var callBackParaObj ;
    if(callBackPara && callBackPara != ''){
        callBackParaObj = tools.string2JsonObj(callBackPara);
    }
	
    arguments.length = 5;//设置动态四个参数
    if(!item_id || !item_name){
	   return ;
    }
    item_id = "" + item_id;//处理int类型
    if(single_select)//单选模式
    {        
    	
	   arguments[2] =  to_id_field.value;
	   arguments[3] = to_name_field.value;
	   arguments[4] = callBackParaObj;
       trigger_callback(callBackFunc, arguments , to_id_field.value , to_name_field.value );
       //全部清空
       clear_item();
       if(xparent.ChangeInputSize){
			xparent.ChangeInputSize();
		}
       return;
   }
   if(objSelectType &&objSelectType == '1'){//当为多选框时
		getMultiple();
		to_id_field_value = item_id_multiple;
		to_name_field_value = item_id_multiple_name;
	}
   
   //删除数据  数组、页面属性文本框值（id、name）

   to_id_field_value = "";//先清空数据
   to_name_field_value = "";//先清空数据
   var id_field_array_new = new Array();//传新数组
   for(var i =0 ; i<id_field_array.length ; i++){//循环所选项
	   var item = id_field_array[i];
	   if(item.id != item_id){//如果不相等
		   id_field_array_new.push(item);
		   to_id_field_value = to_id_field_value + item.id  + ",";
		   to_name_field_value = to_name_field_value + item.name  + ",";
	   }
   }
   id_field_array = id_field_array_new;
   
   to_id_field.value = to_id_field_value;
   to_name_field.value = to_name_field_value;
   
   if(xparent.ChangeInputSize){
		xparent.ChangeInputSize();
	}
   
   //处理多选人员。点击删除
   if(type && type == '1'){
	   //清空中间选人选中
 	  jQuery(".list-group-item[item_id='" +item_id + "']").each(function(){
 		  jQuery(this).removeClass('active');
 	  });
 	 
   }
   
   /**
    * 判断是否需要处理多选人员（已选三栏）
    */
   if( window.dataSelectLoadInit ){  
	   //删除右边已选人员数据
	 	 jQuery("#select_item_0 .list-group-item[item_id='" +item_id + "']").each(function(){
			  jQuery(this).remove();
		  });
	    //dataSelectLoadInit(id_field_array);
   }  
   
   arguments[2] =  to_id_field.value;
   arguments[3] = to_name_field.value;
   arguments[4] = callBackParaObj;
   trigger_callback(callBackFunc, arguments  );//回调函数

}

function clear_item()
{
   to_id_field.value = '';
   to_name_field.value = '';
   jQuery('.block-right:visible .block-right-item').removeClass('active');
   trigger_callback('clear', arguments);
}

/**
 * 初始化
 * @param block
 */
function init_item(block)
{
	 to_id_field_value = to_id_field.value;
	 to_name_field_value = to_name_field.value;
	if(objSelectType &&objSelectType == '1'){//当为多选框时
		getMultiple();
		to_id_field_value = item_id_multiple;
		to_name_field_value = item_id_multiple_name;
	}
   jQuery('#' + block + '_item .list-group-item').each(function(){
      var item_id = this.getAttribute("item_id");
      if(("," + to_id_field_value + ",").indexOf("," + item_id + ",") >= 0 )  { 
    	  jQuery(this).addClass('active');
      }else{
    	  jQuery(this).removeClass('active'); 
      }
        
   });
   trigger_callback('init', arguments);
}


/**
 * 获取select Multiple 
 * @param obj
 * @returns {Array}
 */
var item_id_multiple_array = new Array();
var item_id_multiple_name_array = new Array();
function getMultiple(){    
	var objSelect = to_id_field;
	var arr = [];    
	item_id_multiple = "";
	item_id_multiple_name = "";
	for(var i=0;i<objSelect.options.length;i++){
		//if(objSelect.options[i].selected == true){//选中
			item_id_multiple = item_id_multiple + objSelect.options[i].value + ",";
			item_id_multiple_name = item_id_multiple_name + objSelect.options[i].innerHTML + ",";

		//}
		
		//arr.push({id:options[obj.selectedIndex].value,name:[obj.selectedIndex].name});       
		//options[obj.selectedIndex].selected =false;       
	}  
	return arr;   
} 



/**
 * 添加或者删除
 * 判断是否已存在
 * @param objSelect
 * @param objItemValue
 * @param optType 操作类型 0-添加 1-删除
 * @returns {Boolean}
 */
function addOrDelSelectIsExitItem(objSelect,objItemValue,objItemName,optType)   
{   
   var isExit = false;   
   for(var i=0;i<objSelect.options.length;i++)   
   {   
         if(objSelect.options[i].value == objItemValue)   
        {   
             isExit = true;  
             if(optType == '0'){
            	 objSelect.options[i].selected = true;
             }else if(optType == '1'){
            	 //不选中objSelect.options[i].selected = false;
            	objSelect.options.remove(i);
             }
 
             break;   
         }   
         
    }
   if(optType == '0' && !isExit){
	   $(objSelect).append("<option value='"+objItemValue+ "' selected= 'true'>" + objItemName + "</optin>");

   }
     return isExit;   
} 
function clearData(idStr,idNameStr,objSelectType){
	to_id_field = document.getElementById( idStr);
	if(objSelectType && objSelectType == '1'){
		jQuery(to_id_field).empty();

	}else{
		to_id_field.value = "";
		jQuery("#" + idNameStr).val("");
	}

}

/**
 * 点击角色、分组执行事件函数
 */
function get_new_items( block, item_id,item_name){
	//alert(block+","+item_id+","+item_name);
	if(block == 'role'){//角色
		getPersonByRole(item_id,item_name);
	}else if(block == 'group'){
		getPersonByUserGroup(item_id,item_name);
	}
}
/**
 *  根据角色获取相关人员
 * @param roleId
 * @param roleName
 */
function getPersonByRole(roleId,roleName){

	var url = contextPath +  "/orgSelectManager/getSelectUserByRoleWorkFlow.action";
	if(!userFilter){
		userFilter = "0";
	}
	var para = {roleId:roleId,userFilter:userFilter,moduleId:moduleId,privNoFlag:privNoFlag,prcsId:prcsId,frpSid:frpSid};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		dataLoad(dataList,roleName,'1');
	}else{
		alert(jsonRs.rtMsg);
	}

}
/**
 *  根据自定义组获取相关人员
 * @param roleId
 * @param roleName
 */
function getPersonByUserGroup(id,name){
	var url = contextPath + "/orgSelectManager/getPersonByUserGroupWorkFlow.action";
	if(!userFilter){
		userFilter = "0";
	}
	var para = {userGroupId:id,userFilter:userFilter,moduleId:moduleId,privNoFlag:privNoFlag,prcsId:prcsId,frpSid:frpSid};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		
		dataLoad(dataList,name ,'1');
	}else{
		alert(jsonRs.rtMsg);
	}

}


/***
 * 加载数据 
 * datList: 人员列表
 *  type ：判断是否需要初始化右侧项目 1-不需要 其他-需要
 */
function dataLoad(dataList,name , type){
	 $("a").remove(".right .list-group-item");
	 $("a").remove(".list-group-item-header");
	 $("div").remove(".block-right-item,.emptyClass");
	 if(dataList.length > 0){
		 $("#dept_item_0").append("<a class='list-group-item-header' style='padding:8px 15px;cursor:pointer'>" + name + "</a>");
		 for(var i = 0; i < dataList.length; i++){
			var roleId = dataList[i].uuid;
			var name = dataList[i].userName;
			var userOnlineStatus  = dataList[i].userOnlineStatus;
			var userOnlineStatusDesc = "";
			if(userOnlineStatus && userOnlineStatus == '1'){//在线
				userOnlineStatusDesc = "<font color='red'>&nbsp(在线)</font>";
			}
			if(roleId != ''){
				 $("#dept_item_0").append("<a class='list-group-item'  style='text-align:center;cursor:pointer'  item_id='"+roleId+ "' item_name='"+name+"'><h6 class='list-group-item-heading'>"+ name + userOnlineStatusDesc +"</h6></a>");
			}
		 } 
	 }else{
		if(!name){
			name = "已选人员";
		}
		$("#dept_item_0").append("<a class='list-group-item-header'  style='padding:8px 15px;;cursor:pointer'>" + name + "</a>"
			+"<div align='center' class='emptyClass' style='padding-top:5px;'><h6>无相关人员！</h6></div>");
	 }
	 if(type != '1'){
		load_init(); 
	} 
	load_init_item();//初始化右侧项目
    //默认加载角色选中状态
    init_item('dept');
}
/***
 * 初始化和点击已选人员 ---加载数据 
 * datList: 人员列表
 * type 1-多选人员处理 其它-不处理
 */
function dataLoadInit(dataList ,type){
	 $("a").remove(".list-group-item-header");
	 $("a").remove(".right .list-group-item");
	 $("div").remove(".block-right-item,.emptyClass");
	 if(dataList.length> 0 ){
		 $("#dept_item_0").append("<a class='list-group-item-header'  style='padding:8px 15px;;cursor:pointer'>选择人员</a>");
		 for(var i = 0; i < dataList.length; i++){
			 var item = dataList[i];
			 var itemId = item.id;
			 var name = item.name;
			 if(itemId != ''){
				   $("#dept_item_0").append("<a class='list-group-item active'  style='text-align:center;;cursor:pointer'  item_id='"+itemId+ "' item_name='"+name+"'><h6 class='list-group-item-heading'>"+ name+"</h6></a>");
		  	 }
		 }  
	 }else{
			$("#dept_item_0").append("<a class='list-group-item-header'  style='padding:8px 15px;;cursor:pointer'>已选人员</a>"
					+"<div align='center' class='emptyClass' style='padding-top:5px;'><h6>无相关人员！</h6></div>");
	 }
	 
	 //处理多选人员。点击删除
     if(type && type == '1'){
    	 dataSelectLoadInit(dataList); 
     }
	
	load_init();  
    //默认加载角色选中状态
    init_item('dept');
}







/***
 * 获取角色
 */ 
var isRoleClick = false;
function getRole(){ 
	if(!isRoleClick){
		var url = contextPath +  "/userRoleController/selectUserPrivListWorkFlow.action";
		var para = {prcsId:prcsId,frpSid:frpSid};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
		  	var dataList = jsonRs.rtData;
		  	if(dataList.length > 0){
		  		for(var i=0;i<dataList.length;i++){
		  			$("#roleList").append( "<a block='role' class='list-group-item' item_id=" + dataList[i].uuid+ " item_name=" + dataList[i].roleName + " style=';cursor:pointer'><h6 class='list-group-item-heading'>" + dataList[i].roleName + "</h6></a>");
		  		}
		  	}else{
		  		alert("没有相关角色，请与系统管理员联系!");
		  	}
		 }else{
			 $("#defaultRole").append("暂无可选择人员的角色！");
		 }	
		isRoleClick = true;
	}

}

/***
 * 获取自定义组   包括公共和个人自定义
 */
 var isUserGroupClick = false;
function getGroup(){ 
	if(!isUserGroupClick){
		var url = contextPath + "/userGroup/getPublicAndPersonalUserGroupWorkFlow.action";
		var para = {prcsId:prcsId,frpSid:frpSid};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
		  	var dataList = jsonRs.rtData;
		  	if(dataList.length > 0){
		  		$("#defaultGroup").hide();
		  		for(var i=0;i<dataList.length;i++){
		  			var data = dataList[i];
		  			var isPublic = "";
		  			if(data.userId == '0' || data.userId == ''){
		  				isPublic = " <font color='red'>&nbsp;(公共)</font>";
		  			}
		  			$("#userGroupList").append( "<a class='list-group-item' item_id=" + dataList[i].uuid+ " block='group' item_name=" + dataList[i].groupName + " style=';cursor:pointer'><h6 class='list-group-item-heading'>" + dataList[i].groupName + isPublic + "</h6></a>");
		  		}
		  	}else{
		  		//alert("没有设置自动组，请与系统管理员联系!");
		  		$("#defaultGroup").append("暂无可选择人员的自定义组！");
		  	}
		 }else{
		 	alert(jsonRs.rtMsg);
		 }	
		isUserGroupClick = true;
	}

}

/***
 * 获取在线用户人员
 */
function getUserOnline(){ 
	var url = contextPath +  "/orgSelectManager/getUserOnlineWorkFlow.action";
	if(!userFilter){
		userFilter = "0";
	}
	var para = {userFilter:userFilter,moduleId:moduleId,privNoFlag:privNoFlag};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		dataLoad(dataList,"在线人员");
	}else{
		alert(jsonRs.rtMsg);
	}
}



