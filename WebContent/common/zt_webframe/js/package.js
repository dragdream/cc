(function($){
	// datagride相关js

	//弹窗设计
	$.MsgBox = {

		Alert: function (title, msg ,callback) {
			inputBlur();
			GenerateHtml("alert", title, msg);
			btnOk(callback);
			btnNo();  //取消按钮事件
		},
		newAlert: function (title, msg ,callback) {
			inputBlur();
			newGenerateHtml("alert", title, msg);
			btnOk(callback);
			btnNo();  //取消按钮事件
		},
		Confirm: function (title, msg, callback,cancelCallback) {
			checkIsRepead();
			GenerateHtml("confirm", title, msg);
			btnOk(callback);
			btnNo(cancelCallback);  //取消按钮事件
		},
		newConfirm: function (title, msg, callback,cancelCallback) {
			checkIsRepead();
			newGenerateHtml("confirm", title, msg);
			btnOk(callback);
			btnNo(cancelCallback);  //取消按钮事件
		},
		Alert_auto:function(msg,callback){
			GenerateHtml_auto(msg);
			GenerateCss_auto();
			auto_hide(callback);
		},
		Loading:function(){
			var maskid = "loading_tip_mask_"+new Date().getTime();
			$("<div id='"+maskid+"' class='loading_tip_mask'><img class='loading_tip_pic' src='/common/zt_webframe/css/images/loading_mask.gif' /></div>").appendTo($("body"));
		},
		CloseLoading:function(){
			$(".loading_tip_mask").remove();
		}
	};
	//生成的毫秒数作为图层的index值
	//出现弹框时页面上的所有输入框失去焦点
	var inputBlur = function(){
		$("input,textarea").blur();
	};

	//生成Html
	var GenerateHtml = function (type, title, msg) {
		var _html = "";
		_html += '<div id="win_box"></div><div id="win_con"><span id="win_tit">' + title + '</span>';
		_html += '<a id="win_ico" style="font-size: 27px;">×</a><div id="win_msg">' ;
		if(type == "confirm"){
			_html += '<img id = "win_img" src="/common/zt_webframe/imgs/common_img/question.png" />&nbsp' + msg + '</div><div id="win_btnbox" class="clearfix"> ';

		}else if(type == "notice"){
			_html += '<img id = "win_img" src="/common/zt_webframe/imgs/common_img/notice.png" />&nbsp' + msg + '</div><div id="win_btnbox" class="clearfix"> ';

		}else{
			_html += '<img id = "win_img" src="/common/zt_webframe/imgs/common_img/tip.png" />&nbsp' + msg + '</div><div id="win_btnbox" class="clearfix"> ';
		}
		// _html += '<input id="win_check" type="checkbox" /> 不再提示';
		if (type == "alert") {
			_html += '<input id="win_btn_ok" type="button" class="btn-win-white" value="确定" />';
		};
		if (type == "confirm") {
			_html += '<input id="win_btn_no" class = "btn-win-white" type="button" value="取消" />';
			_html += '<input id="win_btn_ok" class = "btn-win-white" type="button" value="确定" />';
		}
		_html += '</div></div>';
		//必须先将_html添加到body，再设置Css样式
		$("body").append(_html);GenerateCss();
	};
	//生成Html
	var newGenerateHtml = function (type, title, msg) {
		var _html = "";
		_html += '<div id="win_box"></div><div id="win_con"><span id="win_tit" style="height:30px;width:100%;">' + title + '</span>';
		_html += '<a id="win_ico" style="font-size: 27px;">×</a><div id="win_msg">' ;
		if(type == "confirm"){
			_html += '<img id = "win_img" src="/common/zt_webframe/imgs/common_img/question.png" />&nbsp' + msg + '</div><div id="win_btnbox" class="clearfix" style="height: 40px;"> ';

		}else{
			_html += '<img id = "win_img" src="/common/zt_webframe/imgs/common_img/tip.png" />&nbsp' + msg + '</div><div id="win_btnbox" class="clearfix" style="height: 40px;"> ';
		}
		// _html += '<input id="win_check" type="checkbox" /> 不再提示';
		if (type == "alert") {
			_html += '<input id="win_btn_ok" type="button" class="btn-win-white" value="确定" />';
		};
		if (type == "confirm") {
			_html += '<input id="win_btn_no" class = "btn-win-white" type="button" value="取消" />';
			_html += '<input id="win_btn_ok" class = "btn-win-white" type="button" value="确定" />';
		}
		_html += '</div></div>';
		//必须先将_html添加到body，再设置Css样式
		$("body").append(_html);
		GenerateCss();
	};
	
	


	//生成Css
	var GenerateCss = function () {
		var scrTop = $(window).scrollTop();
		var _width = window.document.documentElement.clientWidth;  //屏幕宽
		var _height = window.document.documentElement.clientHeight; //屏幕高
		var boxWidth = $("#win_con").width();
		var boxHeight = $("#win_con").height();
		/*var date = new Date();*/
		//让提示框居中
		$("#win_con").css({ top: ((_height - boxHeight) / 2 - 20 ) +"px", left: (_width - boxWidth) / 2 + "px" });
		/*var date = new Date();*/
		var timestamp = Date.parse(new Date());
		var zIndex = timestamp/1000;
		$("#win_box").css("zIndex",zIndex);
		$("#win_con").css("zIndex",(zIndex+1));
	};


	var GenerateHtml_auto = function(msg){
		var _html = "";
		_html += '<div class="box_auto" style="display:table;box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);border-left:4px solid #2DA9FA;border-radius:5px;min-height: 50px;width: 400px; "> <div style="display:table-cell;vertical-align: middle;width:40px;height:40px;"><div style="width:40px;height:40px;border-radius: 50%;background: #50bfff;margin:5px 10px 10px 10px; text-align:center;"><img src="/common/zt_webframe/imgs/common_img/notice.png" alt="" style="width: 20px ;height: 20px;margin-top:10px"></div></div><span style="display:table-cell;width:322px;vertical-align: middle;text-align:left;">'+  msg +'</span></div>';
		$("body").append(_html);
	};
	var GenerateCss_auto = function(){
		var timestamp = Date.parse(new Date());
		var zIndex = timestamp/1000;
		$(".box_auto").css({position:"fixed",backgroundColor:"#fff",color:"black",fontSize:"14px",zIndex:zIndex});
		var scrTop = $(window).scrollTop();
		var _width = document.documentElement.clientWidth;  //屏幕宽
		var _height = document.documentElement.clientHeight; //屏幕高
		var boxWidth = $(".box_auto").width();
		var boxHeight = $(".box_auto").height();
		_top = (_height - boxHeight) / 2;
		$(".box_auto").css({ top: ((_height - boxHeight)  / 2) + "px", left: (_width - boxWidth) / 2 + "px" });
	};
	

	//确定按钮事件
	var btnOk = function (callback) {
		$("#win_btn_ok").click(function () {
			if($("#win_check").prop("checked") && window._showBox != false){
				window._showBox = false;
			}
			$("#win_box,#win_con").remove();
			if (typeof (callback) == 'function') {
				var cal_result = callback();
				return cal_result;
			}
		});
	};
	//取消按钮事件
	var btnNo = function (cancelCallback) {
		$("#win_btn_no,#win_ico").click(function () {
			$("#win_box,#win_con").remove();
			if(cancelCallback){
				cancelCallback();
			}
		});
	}
	//检查是否点击不在提示
	var checkIsRepead = function (callback){

		if(!window._showBox){
			btnOk(callback);
			return;
		}
	};
	var auto_hide = function(callback){
		$(".box_auto").show();
		setTimeout(function(){animate(callback);},4000);
	};
	function animate(callback){
		$(".box_auto").animate({top:((_top-80 )+"px"),opacity:0},1000,"",function(){
			$(".box_auto").remove();
			if (typeof (callback) == 'function') {
				callback();
			}
		});

	};
	/*模态框*/
	$.fn.modal=function(order){
		var modal = this;
		var cookie,modalName;
		/*var date = new Date();*/
		var timestamp = Date.parse(new Date());
		var zIndex = timestamp/1000;
		var zhezhao = '<div id="modal-box"></div>';
		var modalClass = $(modal).attr("class");
		var scrTop = $(window).scrollTop();
		var _width = document.documentElement.clientWidth;  //屏幕宽
		var _height = document.documentElement.clientHeight; //屏幕高
		//console.log(order);
		//console.log(typeof(order));
		//alert(1);
		if(typeof(order)=="undefined"){
			addZhezhao();
			addModal();
		}else if (order=="show"){
			addZhezhao();
			showModal();
			modalCss();
		}else if(order=="hide"){
			hideModal();
		};
		function addZhezhao (){
			$("body").prepend(zhezhao);
		};
		function addModal(){

			if(modalClass.indexOf(" ") > 0 ){
				var name = modalClass.split(" ");
				for(var i=0;i<name.length;i++){
					if(name[i].substring(0,10)=="modal-menu"){
						cookie = name[i];
						break;
					}
				}
				modalName = cookie.substring(11);
				$(".modal-"+ modalName).fadeIn(100);
			}else{
				modalName = modalClass.substring(11);
				$(".modal-"+ modalName).fadeIn(100);
			};
			var boxWidth = $(".modal-"+ modalName).width();
			var boxHeight = $(".modal-"+ modalName).height();

			/*$("#modal-box").css("zIndex",date.getTime());
			$(".modal-"+ modalName).css("zIndex",(date.getTime()+10));*/
			$("#modal-box").css("zIndex",zIndex);
			$(".modal-"+ modalName).css("zIndex",zIndex);
			$(".modal-"+ modalName).css({position:"fixed", top: ((_height - boxHeight) / 2) +"px", left: (_width - boxWidth) / 2 + "px" });
			$(".modal-"+ modalName).on("click",".modal-win-close,.modal-btn-close",function(){
				$("#modal-box").remove();
				$(".modal-"+ modalName).find("input[type=text]:not([noclear]),textarea:not([noclear])").html("").val("");
				$(".modal-"+ modalName).hide();
			});
		};
		function showModal(){
			//console.log(modal);
			$(modal).fadeIn(100);
		};
		function hideModal(){
			$("#modal-box").remove();
			$(modal).hide();
		};
		function modalCss() {
			var boxWidth = $(modal).width();
			var boxHeight = $(modal).height();
			$("#modal-box").css("zIndex",zIndex);
			$(modal).css("zIndex",(zIndex+1));
			$(modal).css({position:"fixed", top: ((_height - boxHeight) / 2) +"px", left: (_width - boxWidth) / 2 + "px" });
			$(modal).on("click",".modal-win-close,.modal-btn-close",function(){
				$("#modal-box").remove();
				$(modal).find("input[type=text],textarea").html("").val("");
				$(modal).hide();
			});
		};

	};
	$(".modal-confirm").on("click",function(){
		var onclick = $(this).attr("onclick");
		if(onclick == "undefined"){
			$("#modal-box").remove();//移除黑色背景
			$(".modal-win-close").click();//移除模态框
		}
	});
	//确认的按钮写在onclick的函数里
	// $("#modal-box").remove();//移除黑色背景
	// $(".modal-win-close").click();//移除模态框

	//tab分页，包括添加分页标签和切换iframe内容
	$.addTab=function(c1,c2,opts){
		var _c1 = $("#"+c1);
		var _c2 = $("#"+c2);

		var _iframe = _c2.find("iframe:first");
		if(_iframe.length==0){
			_c2.append("<iframe style='height:100%;width:100%;' frameborder=0></iframe>");
		}

		if(opts instanceof Array){
			var li = [];
			var hasActive,cookie,order;
			for(var i=0;i<opts.length;i++){
				renderTab(_c1,_c2,opts[i]);
				cookie = renderTabContent(opts[i],i);
				if(cookie){
					hasActive=true;
				}
			}
			_c1.append(li.join(""));
			if(!hasActive){
				_c2.find("iframe").attr("src",opts[0].url);
				_c1.find("li:first").addClass("select");
			}else{
				//console.log(_c1.find("li"));
				_c1.find("li").eq(order).addClass("select");
			}

			bindClick(opts);
			setDistance();

		}
		;function renderTab(c1,c2,opt){//这里是opt
			if(!opt){
				return;
			}
			var title = opt.title;
			var url = opt.url;
			li.push("<li style=\"float:left;margin-right:20px;line-height:25px;cursor: pointer;\">"+title+"</li>");
		}
		;function bindClick(opts){
			//_c2.find("iframe").attr("src",opts[0].url);

			_c1.find("li").on("click",function(){
				var index = $(this).index();
				// $(this).css({"border-bottom":"2px solid #379ff7","color":"#379ff7"}).siblings().css({"border-bottom":"none","color":"#000"});
				$(this).addClass("select").siblings().removeClass("select");
				_c2.find("iframe").attr("src",opts[index].url);
			})
		}
		;function renderTabContent(opt,i){
			//alert(opt.active);
			if(!opt){
				return;
			}
			if(opt.active){
				_c2.find("iframe").attr("src",opt.url);
				order = i;
				return true;
			}else{
				return false;
			}
		}
		;function setDistance(){
			_c2.css({"position":"absolute","top":"56px","left":"0px","right":"0px","bottom":"0"});
		}
	}

})(jQuery);
//组件包
/*下拉菜单*/
(function(){
	$(function(){
		var btnGroups = $(".btn-group");
		var btnMenu = $(".btn-menu");
		var btnContent = $(".btn-content");
		var btnContent_up = $(".btn-content-up");
		var srcTarget;
		btnMenu.on("click",function(e){
			var bool = true;
			if($(this).hasClass("open")){
				bool = true;
			}else{
				bool = false;
			};
			for( var i = 0,j = $(this).parents().length;i<j;i++){
				$($(this).parents()[i]).find(".btn-content").hide();
				$($(this).parents()[i]).find(".btn-content-up").hide();
				$($(this).parents()[i]).find(".btn-content").siblings(".btn-menu").removeClass("open");
				$($(this).parents()[i]).find(".btn-content-up").siblings(".btn-menu").removeClass("open");
			}
			if(bool){
				$(this).addClass("open");
			}
			if($(this).hasClass("open")){
				$(this).siblings(".btn-content").hide(50);
				$(this).siblings(".btn-content-up").hide(50);
				$(this).removeClass("open");
			}else{
				$(this).siblings(".btn-content").slideDown(50);
				$(this).siblings(".btn-content-up").slideDown(50);
				$(this).addClass("open");
			}
			e.stopPropagation();
		});
		btnContent.on("click",function(){
			$(this).hide();
			bool = false;
		});
		btnContent_up.on("click",function(){
			$(this).hide();
			bool = false;
		});
		$("html").on("click",function(e){
			$(".btn-content").hide();
			$(".btn-content-up").hide();
			$(".btn-menu").removeClass("open");
		})
	});
})();
/*option切换 按钮切换  不是tab*/
(function(){
	$(function(){
		$("body").on("click",".option_buttons button,.option_buttons input[type=button]",function(){
			$(".option_buttons button,.option_buttons input[type=button]").removeClass("btn_active");
			$(this).addClass("btn_active");
		});
	});

})();