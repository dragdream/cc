var suppotPictureFileType = ["bmp", "jpg" , "gif", "png"];//html支持浏览器浏览类型图片类型
var suppotExcleFileType  = ["xls", "xlsx"];//excle格式文件
var suppotWordFileType  = ["doc", "docx"];//worfd格式文件
var suppotCsvFileType  = ["csv"];//csv格式文件

/**
 * 检查是否是html支持浏览类型
 * @param fileId
 * @returns {Boolean}
 */
function checkSuppotPictureFile(fileId){

    var fileObj = $("#" + fileId)[0];
    var fileName = fileObj.value;
    if(!fileObj || fileName == ''){
        $.MsgBox.Alert_auto("请选择文件!");
        fileObj.focus();
        return false;
    }
     //lastIndexOf如果没有搜索到则返回为-1
    if(fileName.lastIndexOf(".")!=-1)
    {
     var fileType = (fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)).toLowerCase();
         for(var i =0;i<suppotPictureFileType.length;i++){
             if(suppotPictureFileType[i]==fileType){
                 return true;
             }else{
                 continue;
             }
         }
         $.MsgBox.Alert_auto("文件只支持" + suppotPictureFileType.toString());
         return false;
    }else {
        $.MsgBox.Alert_auto("文件只支持" + suppotPictureFileType.toString());
    }
}

/**
 * 检查是否为excle文件
 * @param fileId
 * @returns {Boolean}
 */
function checkSuppotExcleFile(fileId){

    var fileObj = $("#" + fileId)[0];
    var fileName = fileObj.value;
    if(!fileObj ||  fileName == ''){
        $.MsgBox.Alert_auto("请选择文件!");
        fileObj.focus();
        return false;
    }
     //lastIndexOf如果没有搜索到则返回为-1
    if(fileName.lastIndexOf(".")!=-1)
    {
     var fileType = (fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)).toLowerCase();
         for(var i =0;i<suppotExcleFileType.length;i++){
             if(suppotExcleFileType[i]==fileType){
                 return true;
             }else{
                 continue;
             }
         }
         $.MsgBox.Alert_auto("文件只支持" + suppotExcleFileType.toString());
         return false;
    }else {
        $.MsgBox.Alert_auto("文件只支持" + suppotExcleFileType.toString());
    }
}


/**
 * 检查是否为word文件
 * @param fileId
 * @returns {Boolean}
 */
function checkSuppotWordFile(fileId){

    var fileObj = $("#" + fileId)[0];
    var fileName = fileObj.value;
    if(!fileObj ||  fileName == ''){
        $.MsgBox.Alert_auto("请选择文件!");
        fileObj.focus();
        return false;
    }
     //lastIndexOf如果没有搜索到则返回为-1
    if(fileName.lastIndexOf(".")!=-1)
    {
     var fileType = (fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)).toLowerCase();
         for(var i =0;i<suppotWordFileType.length;i++){
             if(suppotWordFileType[i]==fileType){
                 return true;
             }else{
                 continue;
             }
         }
         $.MsgBox.Alert_auto("文件只支持" + suppotWordFileType.toString());
         return false;
    }else {
        $.MsgBox.Alert_auto("文件只支持" + suppotWordFileType.toString());
    }
}

/**
 * 检查是否为csv文件
 * @param fileId
 * @returns {Boolean}
 */
function checkSuppotCsvFile(fileId){

    var fileObj = $("#" + fileId)[0];
    var fileName = fileObj.value;
    if(!fileObj ||  fileName == ''){
        $.MsgBox.Alert_auto("请选择文件!");
        fileObj.focus();
        return false;
    }
     //lastIndexOf如果没有搜索到则返回为-1
    if(fileName.lastIndexOf(".")!=-1)
    {
     var fileType = (fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)).toLowerCase();
         for(var i =0;i<suppotCsvFileType.length;i++){
             if(suppotCsvFileType[i]==fileType){
                 return true;
             }else{
                 continue;
             }
         }
         $.MsgBox.Alert_auto("文件只支持" + suppotCsvFileType.toString());
         return false;
    }else {
        $.MsgBox.Alert_auto("文件只支持" + suppotCsvFileType.toString());
    }
}


/**
 * 打开模态窗口,不能改变窗口大小
 */
function dialog(actionUrl, width, height) {
  var locX = (screen.width - width) / 2;
  var locY = (screen.height - height) / 2;
  var attrs = null;

  attrs = "status:no;directories:no;";
  attrs += "dialogWidth:" + width + "px;";
  attrs += "dialogHeight:" + height + "px;";
  attrs += "dialogLeft:" + locX + "px;";
  attrs += "dialogTop:" + locY + "px;";

  var IM_OA;
  try{
      IM_OA = window.external.IM_OA;
  }catch(e){}

  if(window.showModelDialog || IM_OA){
        dialogChangesize(actionUrl, width, height);
    }else{
        openWindow(actionUrl,"", width, height);
    }
//  return window.showModalDialog(actionUrl, self, attrs);
}

/**
 * 打开模态窗口,不能改变窗口大小
 */
function dialog0(actionUrl, width, height) {
  var locX = (screen.width - width) / 2;
  var locY = (screen.height - height) / 2;
  var attrs = null;

  attrs = "status:no;directories:no;";
  attrs += "dialogWidth:" + width + "px;";
  attrs += "dialogHeight:" + height + "px;";
  attrs += "dialogLeft:" + locX + "px;";
  attrs += "dialogTop:" + locY + "px;";

  var IM_OA;
  try{
      IM_OA = window.external.IM_OA;
  }catch(e){}

  if(window.showModelDialog || IM_OA){
        dialogChangesize(actionUrl, width, height);
    }else{
        openWindow(actionUrl,"", width, height);
    }
//  return window.showModalDialog(actionUrl, self, attrs);
}

/**
 * 打开模态窗口,能改变窗口大小


 */
function dialogChangesize(actionUrl, width, height) {
  var locX = (screen.width - width) / 2;
  var locY = (screen.height - height) / 2;
  var attrs = null;

  attrs = "status:no;directories:yes;scroll:yes;Resizable=yes;";
  attrs += "dialogWidth:" + width + "px;";
  attrs += "dialogHeight:" + height + "px;";
  attrs += "dialogLeft:" + locX + "px;";
  attrs += "dialogTop:" + locY + "px;";

  var IM_OA;
  try{
      IM_OA = window.external.IM_OA;
  }catch(e){}

  if(window.showModelDialog || IM_OA){
      window.showModalDialog(actionUrl, self, attrs);
    }else{
        openWindow(actionUrl,"", width, height);
    }

}


/**
 * 打开窗口,能改变窗口大小


 */
function openWindow(actionUrl, title, width, height) {
  var winParam = "menubar=0,toolbar=0,status=0";
  winParam += ",scrollbars=1,resizable=1";
  var availWidth = window.screen.availWidth;
  var availHeight = window.screen.availHeight;
  if($.browser.webkit){
      availHeight = parseInt(availHeight)-60;
  }

  if (!width) {
    width = 800;
  }
  if (!height) {
    height = 600;
  }
  var left = (availWidth - width) / 2;
  var top = (availHeight - 100 - height) / 2;
  winParam += ",width=" + width;
  winParam += ",height=" + height;
  winParam += ",top=" + top;
  winParam += ",left=" + left;
  winParam += ",location=no";

  try{
      if(window.external && window.external.IM_OA){
        window.external.IM_OpenNavigation(title,actionUrl.replace(contextPath,""),width,height);
        return;
      }
  }catch(e){

  }


  return window.open(actionUrl, new Date().getTime()+"", winParam);
}

 function jBoxWindow(url,title,opts){
     if(!opts){
         opts = {};
     }
     var width = opts.width||$(top.document).width()-100;
     var height = opts.height||$(top.document).height()-100;
     top.$.jBox.open("iframe:"+url,title,width,height,{top:"5%",submit:function(v,h,f){
         var iframe = $(h).find("iframe:first");
         var cw = iframe[0].contentWindow;
         if(opts.submit){
             return opts.submit(v,h,f,cw);
         }
         return true;
     }});
 }

 /**
  * bsWindow("http://www.baidu.com","标题",{buttons:[{name:"关闭",classStyle:"1"},{name:"开始",classStyle:"2"}]});
  * @param url
  * @param title
  * @param modelId 自定义ID
  * @param opts
  * @return
  */
 var GLOBAL_BSWINDOW_CLOSED_FUNC;
 
 function bsWindow(con,title,opts,modelId,type){
     /*var date = new Date();*/
     /*var c_t = date.getTime();*/
    var timestamp = Date.parse(new Date()); 
    var c_t = "";
    if (typeof(modelId)=='undefined') {
        c_t =timestamp/1000;
    }else if(modelId != null && modelId != ''){
        c_t = modelId;
    }else {
        c_t =timestamp/1000;
    }
     opts.height+="";
	 opts.width+="";
	 if(opts.height.indexOf("px")==-1 && opts.height.indexOf("%")==-1){
		 opts.height+="px";
	 }
	 if(opts.width.indexOf("px")==-1 && opts.width.indexOf("%")==-1){
		 opts.width+="px";
	 }
	 
		createIframe(con,title,opts,type);
		iframeCss(con,title,opts,type);
		iframeBind(con,title,opts,type);
		function createIframe(con,title,opts,type){
			var _iframeDiv = "<span class='bsWindow'><div class='zhezhao"+c_t+" zhezhao'></div><div id='box' class='iframeDiv"+c_t+"' style='width:"+opts.width+";border-radius: 5px;cursor: move;'>";
			_iframeDiv+="<div style='border-top-left-radius: 4px;border-top-right-radius: 4px;height:35px;line-height: 35px;background: #5b8fdb;color: #fff;padding: 0 12px;"+"' class='iframeTitle'>";
			_iframeDiv+="<span style='font-size:15px;' class='if_title fl'>"+title+"</span>";
			_iframeDiv+= "<span id='win_ico' style='line-height:15px;font-size:27px;' class='fr iframeDiv_close"+c_t+"'>×</span>";
			_iframeDiv+="</div>";
			_iframeDiv+="<div class='iframeContent' style='width:"+opts.width+";height:"+opts.height+";padding:0 10px;padding: 0 10px 0 10px;background-color:#fff;box-sizing: border-box;'>";
			if(typeof(type)=='undefined'){
				_iframeDiv+="<iframe id='_iframe_id"+c_t+"' class='_iframe_id"+c_t+"' frameborder='0' style='width:100%;height:100%;'></iframe>";
			}else if(type=="html"){
				_iframeDiv+=con;
			}
			_iframeDiv+="</div>";
			_iframeDiv+="<div class='iframeBtns fr clearfix' style='border-bottom-right-radius: 4px;border-bottom-left-radius: 4px;text-align: center;width:100%;padding: 8px 0;background-color:#fff;float: right;'>";
            if(opts.buttons){
            	var l=opts.buttons.length - 1;
    			for(i=l;i>=0;i--){
    				_iframeDiv+="<button class='"+opts.buttons[i].classStyle+" btn"+c_t+" fr' style='margin-right:14px;'>"+opts.buttons[i].name+"</button>";
    			};
            }else{
            	_iframeDiv+="<button class='btn-alert-blue btn"+c_t+" fr' style='margin-right:14px;'>确定</button>";
            }
			_iframeDiv+="</div>";
			_iframeDiv+="</div></span>";
			$("body").append(_iframeDiv);
			$("#_iframe_id"+c_t).attr("src",con);
			iframeCss(con,title,opts,type);
		}
		  $(function () {
		        //创建小方块的jquery对象
		        var $box = $("#box");
		        //创建小方块的鼠标点按下事件
		        $box.mousedown(function (event) {
		            //获取鼠标按下的时候左侧偏移量和上侧偏移量
		            var old_left = event.pageX;//左侧偏移量
		            var old_top = event.pageY;//竖直偏移量

		            //获取鼠标的位置
		            var old_position_left = $(this).position().left;
		            var old_position_top = $(this).position().top;

		            //鼠标移动
		            $(document).mousemove(function (event) {
		                var new_left = event.pageX;//新的鼠标左侧偏移量
		                var new_top = event.pageY;//新的鼠标竖直方向上的偏移量

		                //计算发生改变的偏移量是多少
		                var chang_x = new_left - old_left;
		                var change_y = new_top - old_top;

		                //计算出现在的位置是多少

		                var new_position_left = old_position_left + chang_x;
		                var new_position_top = old_position_top + change_y;
		                //加上边界限制
		                if(new_position_top<0){//当上边的偏移量小于0的时候，就是上边的临界点，就让新的位置为0
		                    new_position_top=0;
		                }
		                //如果向下的偏移量大于文档对象的高度减去自身的高度，就让它等于这个高度
		                if(new_position_top>$(document).height()-$box.height()){
		                    new_position_top=$(document).height()-$box.height();
		                }
		                //右限制
		                if(new_position_left>$(document).width()-$box.width()){
		                    new_position_left=$(document).width()-$box.width();
		                }
		                if(new_position_left<0){//左边的偏移量小于0的时候设置 左边的位置为0
		                    new_position_left=0;
		                }

		                $box.css({
		                    left: new_position_left + 'px',
		                    top: new_position_top + 'px'
		                })
		            });
		            $box.mouseup(function(){
		                $(document).off("mousemove");
		            })
		        });
		    })
		function iframeBind(con,title,opts,type){
			$(".iframeDiv_close"+c_t).click(function(){
				$(".iframeDiv"+c_t).hide();
				$(".iframeDIv"+c_t).find("iframe").remove();
				$(".iframeDiv"+c_t).remove();
				$(".zhezhao"+c_t).remove();
			});
			$(".btn"+c_t).click(function(){
				if(opts.submit){
					if(opts.submit($(this).html(),$("._iframe_id"+c_t),$(".iframeContent"),$(".bsWindow"))){
						$(".iframeDiv"+c_t).hide();
						$(".iframeDIv"+c_t).find("iframe").remove();
						$(".iframeDiv"+c_t).remove();
						$(".zhezhao"+c_t).remove();
						
					}
				}
			});
			$(".iframeDiv_close"+c_t).click(function(){
				$(".iframeDiv"+c_t).hide();
				$(".iframeDIv"+c_t).find("iframe").remove();
				$(".iframeDiv"+c_t).remove();
				$(".zhezhao"+c_t).remove();
				
			});
			
			
		};
		function iframeCss(){
			var _width = document.documentElement.clientWidth;  //屏幕宽
		    var _height = document.documentElement.clientHeight; //屏幕高
		    var scrTop = $(window).scrollTop();
		    var boxWidth = $(".iframeDiv"+c_t).width();
		    var boxHeight = $(".iframeDiv"+c_t).height();
			$(".zhezhao"+c_t).css("z-index",c_t);
			/*$(".iframeDiv"+c_t).css("zIndex",(date.getTime()+1));*/
			$(".iframeDiv"+c_t).css("zIndex",(c_t+1));
		    $(".iframeDiv"+c_t).css({ "position":"absolute","background-color":"#fff",top: ((_height - boxHeight) / 2 + scrTop) + "px", left: (_width - boxWidth) / 2 + "px" });
		};
		
		GLOBAL_BSWINDOW_CLOSED_FUNC = function() {
            $(".iframeDiv"+c_t).hide();
            $(".iframeDIv"+c_t).find("iframe").remove();
            $(".iframeDiv"+c_t).remove();
            $(".zhezhao"+c_t).remove();
            
        }

    }
 
 function bsWindow2(con,title,opts,modelId,type){
     /*var date = new Date();*/
     /*var c_t = date.getTime();*/
    var timestamp = Date.parse(new Date()); 
    var c_t = "";
    if (typeof(modelId)=='undefined') {
        c_t =timestamp/1000;
    }else if(modelId != null && modelId != ''){
        c_t = modelId;
    }else {
        c_t =timestamp/1000;
    }
     opts.height+="";
	 opts.width+="";
	 if(opts.height.indexOf("px")==-1 && opts.height.indexOf("%")==-1){
		 opts.height+="px";
	 }
	 if(opts.width.indexOf("px")==-1 && opts.width.indexOf("%")==-1){
		 opts.width+="px";
	 }
	 
		createIframe(con,title,opts,type);
		iframeCss(con,title,opts,type);
		iframeBind(con,title,opts,type);
		function createIframe(con,title,opts,type){
			var _iframeDiv = "<span class='bsWindow'><div class='zhezhao"+c_t+" zhezhao'></div><div id='box' class='iframeDiv"+c_t+"' style='width:"+opts.width+";border-radius: 5px;cursor: move;'>";
			_iframeDiv+="<div style='border-top-left-radius: 4px;border-top-right-radius: 4px;height:35px;line-height: 35px;background: #5b8fdb;color: #fff;padding: 0 12px;"+"' class='iframeTitle'>";
			_iframeDiv+="<span style='font-size:15px;' class='if_title fl'>"+title+"</span>";
			_iframeDiv+= "<span id='win_ico' style='line-height:15px;font-size:27px;' class='fr iframeDiv_close"+c_t+"'>×</span>";
			_iframeDiv+="</div>";
			_iframeDiv+="<div class='iframeContent' style='width:"+opts.width+";height:"+opts.height+";background-color:#fff;box-sizing: border-box;'>";
			if(typeof(type)=='undefined'){
				_iframeDiv+="<iframe id='_iframe_id"+c_t+"' class='_iframe_id"+c_t+"' frameborder='0' style='width:100%;height:100%;'></iframe>";
			}else if(type=="html"){
				_iframeDiv+=con;
			}
			_iframeDiv+="</div>";
			/*_iframeDiv+="<div class='iframeBtns fr clearfix' style='border-bottom-right-radius: 4px;border-bottom-left-radius: 4px;text-align: center;width:100%;padding: 8px 0;background-color:#fff;float: right;'>";
            if(opts.buttons){
            	var l=opts.buttons.length - 1;
    			for(i=l;i>=0;i--){
    				_iframeDiv+="<button class='"+opts.buttons[i].classStyle+" btn"+c_t+" fr' style='margin-right:14px;'>"+opts.buttons[i].name+"</button>";
    			};
            }else{
            	_iframeDiv+="";
            }
			_iframeDiv+="</div>";*/
			_iframeDiv+="</div></span>";
			$("body").append(_iframeDiv);
			$("#_iframe_id"+c_t).attr("src",con);
			iframeCss(con,title,opts,type);
		}
		  $(function () {
		        //创建小方块的jquery对象
		        var $box = $("#box");
		        //创建小方块的鼠标点按下事件
		        $box.mousedown(function (event) {
		            //获取鼠标按下的时候左侧偏移量和上侧偏移量
		            var old_left = event.pageX;//左侧偏移量
		            var old_top = event.pageY;//竖直偏移量

		            //获取鼠标的位置
		            var old_position_left = $(this).position().left;
		            var old_position_top = $(this).position().top;

		            //鼠标移动
		            $(document).mousemove(function (event) {
		                var new_left = event.pageX;//新的鼠标左侧偏移量
		                var new_top = event.pageY;//新的鼠标竖直方向上的偏移量

		                //计算发生改变的偏移量是多少
		                var chang_x = new_left - old_left;
		                var change_y = new_top - old_top;

		                //计算出现在的位置是多少

		                var new_position_left = old_position_left + chang_x;
		                var new_position_top = old_position_top + change_y;
		                //加上边界限制
		                if(new_position_top<0){//当上边的偏移量小于0的时候，就是上边的临界点，就让新的位置为0
		                    new_position_top=0;
		                }
		                //如果向下的偏移量大于文档对象的高度减去自身的高度，就让它等于这个高度
		                if(new_position_top>$(document).height()-$box.height()){
		                    new_position_top=$(document).height()-$box.height();
		                }
		                //右限制
		                if(new_position_left>$(document).width()-$box.width()){
		                    new_position_left=$(document).width()-$box.width();
		                }
		                if(new_position_left<0){//左边的偏移量小于0的时候设置 左边的位置为0
		                    new_position_left=0;
		                }

		                $box.css({
		                    left: new_position_left + 'px',
		                    top: new_position_top + 'px'
		                })
		            });
		            $box.mouseup(function(){
		                $(document).off("mousemove");
		            })
		        });
		    })
		function iframeBind(con,title,opts,type){
			$(".iframeDiv_close"+c_t).click(function(){
				$(".iframeDiv"+c_t).hide();
				$(".iframeDIv"+c_t).find("iframe").remove();
				$(".iframeDiv"+c_t).remove();
				$(".zhezhao"+c_t).remove();
			});
			$(".btn"+c_t).click(function(){
				if(opts.submit){
					if(opts.submit($(this).html(),$("._iframe_id"+c_t),$(".iframeContent"),$(".bsWindow"))){
						$(".iframeDiv"+c_t).hide();
						$(".iframeDIv"+c_t).find("iframe").remove();
						$(".iframeDiv"+c_t).remove();
						$(".zhezhao"+c_t).remove();
						
					}
				}
				if(opts.next){
					//alert("111");
					console.log($(this).html());
					console.log($("._iframe_id"+c_t));
					console.log($(".iframeContent"));
					console.log($(".bsWindow"));
					if(opts.next($(this).html(),$("._iframe_id"+c_t),$(".iframeContent"),$(".bsWindow"))){
						/*$(".iframeDiv"+c_t).hide();
						$(".iframeDIv"+c_t).find("iframe").remove();
						$(".iframeDiv"+c_t).remove();
						$(".zhezhao"+c_t).remove();*/
						alert("333");
						
					}
				}
			});
			$(".iframeDiv_close"+c_t).click(function(){
				$(".iframeDiv"+c_t).hide();
				$(".iframeDIv"+c_t).find("iframe").remove();
				$(".iframeDiv"+c_t).remove();
				$(".zhezhao"+c_t).remove();
				
			});
			
			
		};
		function iframeCss(){
			var _width = document.documentElement.clientWidth;  //屏幕宽
		    var _height = document.documentElement.clientHeight; //屏幕高
		    var scrTop = $(window).scrollTop();
		    var boxWidth = $(".iframeDiv"+c_t).width();
		    var boxHeight = $(".iframeDiv"+c_t).height();
			$(".zhezhao"+c_t).css("z-index",c_t);
			/*$(".iframeDiv"+c_t).css("zIndex",(date.getTime()+1));*/
			$(".iframeDiv"+c_t).css("zIndex",(c_t+1));
		    $(".iframeDiv"+c_t).css({ "position":"absolute","background-color":"#fff",top: ((_height - boxHeight) / 2 + scrTop) + "px", left: (_width - boxWidth) / 2 + "px" });
		};
		
		GLOBAL_BSWINDOW_CLOSED_FUNC = function() {
            $(".iframeDiv"+c_t).hide();
            $(".iframeDIv"+c_t).find("iframe").remove();
            $(".iframeDiv"+c_t).remove();
            $(".zhezhao"+c_t).remove();
            
        }

    }
 /**
  * 全屏幕打开窗口
  * @param actionUrl
  * @param title
  * @returns
  */
 function openFullWindow(actionUrl, title) {
     var fulls = "left=0,screenX=0,top=0,screenY=0,scrollbars=1";    //定义弹出窗口的参数
      if (window.screen) {
         var ah = screen.availHeight - 30;
         var aw = screen.availWidth - 10;
         fulls += ",height=" + ah;
         fulls += ",innerHeight=" + ah;
         fulls += ",width=" + aw;
         fulls += ",innerWidth=" + aw;
         fulls += ",resizable"
     } else {
         fulls += ",resizable"; // 对于不支持screen属性的浏览器，可以手工进行最大化。 manually
     }

      try{
          if(window.external && window.external.IM_OA){
            window.external.IM_OpenNavigation(title,actionUrl.replace(contextPath,""),0,0);
            return;
          }
      }catch(e){

      }

      return window.open(actionUrl, new Date().getTime()+"", fulls);
    }

 function openFullDialog(actionUrl, title) {
      var winParam = "menubar=0,toolbar=0,status=0";
      winParam += ",scrollbars=1,resizable=1";
      var left = 0;
      var top = 0;
      var width = window.screen.availWidth;
      var height = window.screen.availHeight;
      if($.browser.webkit){
          height = parseInt(height)-60;
      }
      height = parseInt(height)-80;
      var width = window.screen.availWidth;
      var height = window.screen.availHeight;
      var attrs = null;

      attrs = "status:no;directories:yes;scroll:yes;Resizable=no;";
      attrs += "dialogWidth:" + width + "px;";
      attrs += "dialogHeight:" + height + "px;";
      attrs += "dialogLeft:" + left + "px;";
      attrs += "dialogTop:" + top + "px;";

      var IM_OA;
      try{
          IM_OA = window.external.IM_OA;
      }catch(e){}

      if(window.showModelDialog || IM_OA){
            dialogChangesize(actionUrl, width, height);
        }else{
            openWindow(actionUrl,"", width, height);
        }
    }

 /**
  * Select 生成 option方便方法


  */
 function setOptions(el,aOption,value){
        for(var i = 0 ;i<aOption.length;i++){
            var _option = document.createElement("option");
        _option.value = aOption[i].value;
        if (aOption[i].value == value) {
                  _option.selected = true;
            }
        _option.appendChild(document.createTextNode(aOption[i].text ));
        el.appendChild(_option);
    }

}

 /**
  * 判断是否是数据类型，包括小数点


  */
 function isNumber(aValue) {
   if (aValue == null) {
     return false;
   }
   var reg = /^([+-]?)\d*\.?\d+$/;
   return reg.test(aValue);
 }
/**
 * 判断是否是整数


 */
function isInteger(aValue) {
  if (aValue == null) {
    return false;
  }
  var reg = /^[-+]?[1-9][0-9]*$/;
  return reg.test(aValue);
}


/**
 * 判断是否是整数+ 0


 */
function isIntegeOrZero(aValue) {
  if (aValue == null) {
    return false;
  }
  var reg = /^\d+$/;
  return reg.test(aValue);
}
/**
 * 判断是否是正整数
 */
function isPositivInteger(aValue) {
  if (aValue == null) {
    return false;
  }
  var reg = /^[1-9][0-9]*$/;
  return reg.test(aValue);
}

/**
 * 判断日期是否有效
 */
function isValidDate(year, month, day) {
  // alert(year + " " + month + " " + day);
  return isValidDateStr(year + "-" + month + "-" + day);
}

/**
 * 判断是否为合法的日期串


 *
 * @str 日期串


 */
function isValidDateStr(str) {
  if (!str) {
    return;
  }
  var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
  if (r == null) {
    return false;
  }
  if (parseInt(r[1]) > 9999 || parseInt(r[1]) < 1753) {
    return false;
  }
  var d = new Date(r[1], r[3]-1, r[4]);
  return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]);
}

/**
 * 判断是否为合法的时间串


 * @param str
 * @return
 */
function isValidTimeStr(str) {
  if (!str) {
    return;
  }
  var r = str.match(/^(\d{1,2}):(\d{1,2}):(\d{1,2})$/);
  if (r == null) {
    return false;
  }
  if (parseInt(r[1]) > 59 || parseInt(r[1]) < 0) {
    return false;
  }
  if (parseInt(r[2]) > 59 || parseInt(r[2]) < 0) {
    return false;
  }
  if (parseInt(r[3]) > 59 || parseInt(r[3]) < 0) {
    return false;
  }
  return true;
}

/**
 * 判断是否为合法的日期时间串


 * @param str
 * @return
 */
function isValidateTimeStr(str) {
  if (!str) {
    return;
  }
  var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})\s(\d{1,2}):(\d{1,2}):(\d{1,2})$/);
  if (r == null) {
    return false;
  }
  if (parseInt(r[1]) > 9999 || parseInt(r[1]) < 1753) {
    return false;
  }
  var d = new Date(r[1], r[3]-1, r[4]);
  if (!(d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4])) {
    return false;
  }
  if (parseInt(r[5]) > 59 || parseInt(r[5]) < 0) {
    return false;
  }
  if (parseInt(r[6]) > 59 || parseInt(r[6]) < 0) {
    return false;
  }
  if (parseInt(r[7]) > 59 || parseInt(r[7]) < 0) {
    return false;
  }
  return true;
}


/**
 * 是否是有效的文件文件名  特殊字符串
 * @param str
 * @returns 存在特殊字符返回true
 */
function isValidateFilePath(str){
  var re=/["\/\\:*?"<>|]/;
  return str.match(re);
}


 /**
  * 对Date的扩展，将 Date 转化为指定格式的String
  * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符
  * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
  * eg:
  * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
  * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04
  * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04
  * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04
  * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
  */
 Date.prototype.pattern=function(fmt) {
     var o = {
     "M+" : this.getMonth()+1, //月份
     "d+" : this.getDate(), //日
     "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
     "H+" : this.getHours(), //小时
     "m+" : this.getMinutes(), //分
     "s+" : this.getSeconds(), //秒
     "q+" : Math.floor((this.getMonth()+3)/3), //季度
     "S" : this.getMilliseconds() //毫秒
     };
     var week = {
     "0" : "/u65e5",
     "1" : "/u4e00",
     "2" : "/u4e8c",
     "3" : "/u4e09",
     "4" : "/u56db",
     "5" : "/u4e94",
     "6" : "/u516d"
     };
     if(/(y+)/.test(fmt)){
         fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
     }
     if(/(E+)/.test(fmt)){
         fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
     }
     for(var k in o){
         if(new RegExp("("+ k +")").test(fmt)){
             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
         }
     }
     return fmt;
}


/**
 * 转换日期字符串
 * @param time 时间戳  --时间为空则为系统当前时间
 * @param format  日期转换格式
 */
function getFormatDateStr(time , format){
    var timeStr = "";
    if(time){
        var timeDate =new Date(parseInt(time,10));
        timeStr = new Date(timeDate).pattern(format);
    }else{
        timeStr = new Date(new Date()).pattern(format);
    }
    return timeStr;
}

/**
 * 转换日期字符串
 * @param time 时间戳
 * @param format  日期转换格式
 */
function getFormatDateTimeStr(time , format){
    var timeStr = "";
    if(time){
        var timeDate =new Date(parseInt(time,10));
        timeStr = new Date(timeDate).pattern(format);
    }else{
        //timeStr = new Date(new Date()).pattern(format);
    }
    return timeStr;
}

/**
 * 将时间戳转换为字符串 --- 多少天多少小时多少分钟多少秒
 * @param milliSeconds
 * @return
 */
function getTimeMiliBirdDesc(milliSeconds){
    var days = Math.floor(milliSeconds/(60*60*24));
    var milliSeconds = milliSeconds-(days*24*60*60);
    var hours = Math.floor(milliSeconds/(60*60));
    var milliSeconds = milliSeconds-(hours*60*60);
    var minutes = Math.floor(milliSeconds/60);
    var milliSeconds = milliSeconds-(minutes*60);
    var  seconds = Math.floor(milliSeconds);
    var sb = "";
    if(days != 0){
        sb += days + "天";
    }
    if(hours != 0){
        sb += hours+ "小时";
    }
    if(minutes != 0){
        sb += minutes+"分钟";
    }
    if(seconds != 0){
        sb += seconds+"秒";
    }
    return sb;
}


/**
 * 将时间戳转换为字符串 --- 多少天多少小时多少分
 * @param milliSeconds
 * @return
 */
function getTimeMilisecondDesc(milliSeconds){
    if(!milliSeconds || milliSeconds == ''){
        return "";
    }
    var days = Math.floor(milliSeconds/(60*60*24));
    var milliSeconds = milliSeconds-(days*24*60*60);
    var hours = Math.floor(milliSeconds/(60*60));
    var milliSeconds = milliSeconds-(hours*60*60);
    var minutes = Math.floor(milliSeconds/60);
    var milliSeconds = milliSeconds-(minutes*60);

    var sb = "";
    if(days != 0){
        sb += days + "天";
    }
    if(hours != 0){
        sb += hours+ "小时";
    }
    if(minutes != 0){
        sb += minutes+"分钟";
    }
    /*if(seconds != 0){
        sb += seconds+"秒";
    }*/
    return sb;
}

 /**
  * 处理frameset,左边是菜单,右边是功能区,菜单不随着功能区滚动的问题
  * @param menuId
  * @param mainId
  * @param minHeight
  * @param url
  * @return
  */
 function doFramesetScroll(menuId, mainId, minHeight, url) {
   try {
     var main = document.getElementById(mainId);
     var menu = document.getElementById(menuId);
  //   menu.scrolling = "no";
     menu.contentWindow.document.body.style.height = "auto";
     menu.contentWindow.document.attachEvent && menu.contentWindow.document.attachEvent("onmousewheel", function(e) {
       var top = (main.contentWindow.document.documentElement || main.contentWindow.document.body).scrollTop;
       if (e.wheelDelta >= 120) {
         main.contentWindow.scrollTo(0, top - 50);
       }
       else {
         main.contentWindow.scrollTo(0, top + 50);
       }
     });
     main.onload = main.onreadystatechange = function() {
       if (!this.readyState || this.readyState == "complete") {
         menu.contentWindow.scrollTo(0, 0);
         main.contentWindow.document.body.style.minHeight = parseInt(minHeight-160) + "px";
         if (main.contentWindow.attachEvent) {
           main.contentWindow.attachEvent("onscroll", function(e) {
             menu.contentWindow.scrollTo(0, main.contentWindow.document.documentElement.scrollTop);
             return false;
           });
         }
         else if (main.contentWindow.addEventListener) {
           main.contentWindow.addEventListener("scroll", function(e) {
             menu.contentWindow.scrollTo(0, main.contentWindow.document.documentElement.scrollTop);
             return false;
           }, false);
         }
       }
     };
     main.src = url;
   } catch (e) {

   } finally {

   }
}

 /**
  *读取cookie
  *@param name 名称
  */
function getCookie(name){
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
    if (arr != null){
        return unescape(arr[2]);
    }else{
        return null;
    }
    return null;
}

/**
 * 设置cookie
 * @param name 名称
 * @param value 值
 * @param Days  时间
 */
function setCookie(name , value , Days){
    var exp  = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);//天数
    document.cookie = name + "="+ escape (value) + ";path=/;expires=" + exp.toGMTString();
}



/**
 * 消息提示
 * @param msrg
 * @param cntrlId 绑定消息的控件
 * @param type  消息类型[info|error||warning|forbidden|stop|blank] 默认为info
 * @param width 宽度
 * @return
 */
function messageMsg(msrg, cntrlId,type ,width ) {
    /*if(!width){
        width = 320;
    }
    var msrgDom = "<table class=\"MessageBox\" align=\"center\"  style=\"width:" + width + "px\">";
    if(!type){
        type = "info";
    }
    msrgDom += " <tr>  <td class=\"msg " + type + "\">";
    msrgDom +=  "<div class=\"\" style=\"\">" + msrg + "</div>"
      + " </td> </tr> </table>";
    $("#" + cntrlId).html(msrgDom);*/
    if(!width){
        width = 320;
    }
    if(!type){
        type = "info";
    };
    var msrgDom = "<div class=\"MessageBox msg-" + type + "\" align=\"center\"  style=\"width:" + width + "px;border-radius:8px;\">";


    msrgDom += "<p>"+msrg+"</p></div>";
    $("#" + cntrlId).html(msrgDom);

}

function startLoading(container){
    var loadingPic = $("<center><img style='margin-top:40px;' src='"+systemImagePath+"/other/loading1.gif' /></center>");
    $(container).append(loadingPic);
}

function endLoading(container){
    $(container).html("");
}

/**
 * 将form的属性 设置清空
 * form : 表单对象
 */
function setFormClearValue(form){
    $(form).find("input[name][type=text]").each(function(i,obj){
        $(obj).val('');
    });
    $(form).find("input[name][type=hidden]").each(function(i,obj){
        $(obj).val('');
    });
    $(form).find("textarea[name]").each(function(i,obj){
         $(obj).val('');
    });
    $(form).find("select[name]").each(function(i,obj){
        $(obj).val('');
    });
    $(form).find("input[name][type=checkbox]").each(function(i,obj){
        if($(obj).attr("checked")){
            //$(obj).attr("checked" , false);
        }else{
            //json[$(obj).attr("name")] = 0;
        }
    });
    $(form).find("input[name][type=radio][checked]").each(function(i,obj){
        //$(obj).attr("checked" , false);
    });

    $(form).find("input[name][type=password]").each(function(i,obj){
        $(obj).val();
    });
}

/*---------------------------------------------------------------
 *jquery扩展，加载技术文件和css文件  zhp 2013-12-01
 *主要是动态引入 文件  不需要引入的页面 可以不引入
 *demo 例子
 *$.includePath = "sss";
 *$.include(['css/css.css',
 *          'js/test.js',
 *           ]);
 * $.includePath = ""; //注意这里 要把这个全局变量 设置还原
 ---------------------------------------------------------------*/
$.extend({
   includePath: '',
   include: function(file)
   {
       var files = typeof file == "string" ? [file] : file;
       for (var i = 0; i < files.length; i++)
       {
           var name = files[i].replace(/^\s|\s$/g, "");
           var att = name.split('.');
           var ext = att[att.length - 1].toLowerCase();
           var isCSS = ext == "css";
           var tag = isCSS ? "link" : "script";
           var attr = isCSS ? " type='text/css' rel='stylesheet' " : " language='javascript' type='text/javascript' ";
           var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'";
           if ($(tag + "[" + link + "]").length == 0) document.write("<" + tag + attr + link + "></" + tag + ">");
       }
   }
});

/*----------------------------------------------------------------------------------
 * jbox 文件引入 zhp 2013-12-01 |************在需要的页面拷贝 此段代码 即可************|
 *在jquery 引入之后执行
 * $.includePath = '<%=contextPath1%>/common/jbox-v2.3/jBox/';
 * $.include(['jquery.jBox-2.3.min.js',
 *          'i18n/jquery.jBox-zh-CN.js',
 *          'Skins/Default/jbox.css'
 * ]);
 *
---------------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------------
 * 模板格式化（正则替换）
---------------------------------------------------------------------------------*/
 var FormatModel = function(str,model){
        for(var k in model){
            var re = new RegExp("{"+k+"}","g");
            str = str.replace(re,model[k]);
        }
        return str;
    }

$.fn.group=function(){
    var curr = $(this);
    curr.children().each(function(i,obj){
        $(obj).click(function(){
            curr.children().each(function(i1,obj1){
                $(obj1).removeClass("active");
            });
            $(this).addClass("active");
        });
    });
}



/**
 * 添加Tab页
 *tabName：标签名称
 *tabURl 路径
 */
function addNewTabs(tabName, tabUrl){
    if(!$('#mainTabs').tabs('exists',tabName)){//判断是否存在路径
         $('#mainTabs').tabs('add',{
                title:  tabName,
                content: '<iframe width="100%" height="100%" frameborder="0"  src="' + tabUrl + '" style="width:100%;height:100%;"></iframe>',
                closable: true

            });
     }else{
         $('#mainTabs').tabs('select',tabName);
     }
}

function createNewWork(flowId,initData){
    var url = contextPath+"/flowRun/createNewWork.action";
    var param = {};
    if(initData){
        for(var key in initData){
            param[key] = initData[key];
        }
    }
    param["fType"] = flowId;
    var json = tools.requestJsonRs(url,{fType:flowId});
    if(json.rtState){
        window.openFullWindow(contextPath+"/system/core/workflow/flowrun/prcs/index.jsp?runId="+json.rtData.runId+"&frpSid="+json.rtData.frpSid+"&flowId="+flowId+"&isNew=1","流程办理");
    }else{
        $.MsgBox.Alert_auto(json.rtMsg);
    }
}

//是否开启业务引擎
function isOpenBisEngine(type){
    var json = tools.requestJsonRs(contextPath+"/bisConfig/isOpenBisEngine.action",{type:type});
    return json.rtData;
}

//通过post打开窗体
function openPostWindow(url,param,target){
    var form = $("#_SYS_HIDDEN_FORM");
    if(form.length==0){
        form = $("<form id='_SYS_HIDDEN_FORM'></form>");
        $("body").append(form);
    }
    form.attr("method","post").attr("target",target).attr("action",url).html("");

    for(var key in param){
        var input = $("<input type='hidden' name='"+key+"'/>");
        input.attr("value",param[key]);
        form.append(input);
    }
    form.submit();
}

function openSeniorSearch(params){
    var url = '/common/search/advSearch.jsp';
//    var data = {
//        name:"advSearch",
//        value:value
//    };
//    var name = '高级查询';
//    openPostWindow(url,data,name);
    window.TMP_SENIOR_SEARCH_PARAMS = params;
    dialog(url,860,300);
}

String.prototype.EndWith=function(s){
      if(s==null||s==""||this.length==0||s.length>this.length)
         return false;
      if(this.substring(this.length-s.length)==s)
         return true;
      else
         return false;
      return true;
     }

     String.prototype.StartWith=function(s){
      if(s==null||s==""||this.length==0||s.length>this.length)
       return false;
      if(this.substr(0,s.length)==s)
         return true;
      else
         return false;
      return true;
     }

     /**
      * 统一关闭窗体方法
      */
     function CloseWindow(){
         try{
             if(window.external && window.external.IM_OA){
                 window.external.IM_Close();
             }else{
                window.close();
             }
         }catch(e){
             window.close();
         }
     }
     
     /**
      * 打开相关数据
      * id = 业务主键
      * type = 数据类型 1：工作流
      */
     function OpenRelateData(id,type){
         if(type==1){
             openFullWindow(contextPath+"/workflow/view.action?runId="+id,"流程查看");
         }
     }