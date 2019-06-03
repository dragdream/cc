<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String inputName = request.getParameter("inputName");
	String hiddenUserId = request.getParameter("hiddenUserId");
	String single = request.getParameter("single");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>常用人</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">

		<link rel="stylesheet" href="css/mui.css">
		<style>
			html,
			body {
				background-color: #ffffff;
			}
			*{
				margin:0;
				padding:0;
				font-family: "Microsoft Yahei";
			}
			li{
				list-style: none;
			}
			p{
				margin-bottom: 0;
			}
			.hide{
				display: none;
			}
			.sortable-ghost {
				opacity: .2;
			}
			/*.sortable-drag {
				background: #daf4ff;
			}*/
			.mui-content {
    			background-color: #fff;
				padding-bottom: 44px;
				width: 100vw;
				height: 100vh;
    		}
			.footerBar{
				position: fixed;
				bottom: 0;
				left: 0;
				right: 0;
				height:44px;
				z-index: 2;
				background: #e1e1e1;
			}
			.mui-input-group .mui-input-row{
				height: auto;
			}
			.name{
				float: left;
				margin-left: 10px;
			}
			.depart{
				margin-left: 20px;
				float: left;
			}
			.name1{
				font-size: 14px;
				max-width: 7em;
			    white-space: nowrap;
			    text-overflow: ellipsis;
			    overflow: hidden;
			    line-height: 30px;
			}
			.name2{
				font-size: 14px;
				color: #999;
				max-width: 7em;
			    white-space: nowrap;
			    text-overflow: ellipsis;
			    overflow: hidden;
			}
			.depart1{
				font-size: 14px;
				color: #666666;
				margin-bottom: 10px;
			}
			.depart2{
				font-size: 14px;
				color: #999999;
			}
			.roleWrap .mui-checkbox input[type=checkbox], .mui-radio input[type=radio]{
				top: 5px;
			}
			.mui-card{
				margin: 0;
				box-shadow: none;
				/*border-bottom: 1px solid #eaeaea;*/
			}
			.mui-content>.mui-card:first-child {
			    margin-top: 0;
			    margin-bottom: 0;
			}
			.allPageBtn{
				background-image: url(images/selectAll.png);
				background-repeat: no-repeat;
				background-position: left center;
				padding-left: 25px;
				line-height: 25px;
				background-size: 22px;
			}
			.allPageList{
				position: fixed;
				bottom: 50px;
				left: 20px;
				padding:5px;
				background-color: #fff;
				border-radius: 5px;
				border:1px solid #aeaeae;
			}
			.allPageList:before{
				content:"";
				border-top: 12px solid #aeaeae;
				border-left: 12px solid transparent;
				border-right: 12px solid transparent;
				position: absolute;
				position: absolute;
				bottom: -12px;
				left: 35px;
			}
			.allPageList:after{
				content:"";
				border-top: 12px solid #fff;
				border-left: 12px solid transparent;
				border-right: 12px solid transparent;
				position: absolute;
				bottom: -11px;
				left: 35px;
			}
			.allPageList li{
				line-height: 35px;
				border-top: 1px solid #AEAEAE;
				text-align: center;
			}
			.allPageList li a{
				display: block;
				padding:0 10px;
			}
			.allPageList li:first-child{
				border-top:none;
			}
			.footerList{
				display: flex;
				justify-content:space-around;
				padding:5px 0;
			}
			.footerList li{
				float: left;
				line-height: 34px;
    			font-size: 16px;
			}
			button.selectedPerson{
				background-color: #e1e1e1;
				border:1px solid #aeaeae;
				background-image: url(images/selected.png);
				background-repeat: no-repeat;
				background-position: 5px center;
				padding-left: 35px;
				background-size: 25px;
			}
			input[type=button].confirmBtn{
				background-color: #e1e1e1;
				border:1px solid #aeaeae;
				background-image: url(images/confirm_btn.png);
				background-repeat: no-repeat;
				background-position: 5px center;
				padding-left: 35px;
				background-size: 25px;
			}
			.searchBar{
				padding:5px 0;
				background-color: #d9d8d8;
			}
			input[type='text'].searchInput{
				width: 90%;
			    display: block;
			    margin: 0 auto;
			    padding: 0;
			    border-radius: 7px;
			    height: 30px;
			    font-size: 16px;
			    padding-left: 35px;
			    background-image: url(images/search.png);
			    background-position: 10px center;
			    background-repeat: no-repeat;
			    background-size: 20px;
			}
			.mui-input-row{
				position: relative;
			}
			.movePosition{
				width:30px;
				height: 30px;
				position: absolute;
				right: 10px;
				top: 10px;
				background-image: url(images/move.png);
				background-repeat: no-repeat;
				background-size: 23px;
				background-position: center;
			}
			.removeItem{
				background-color: #f00;
				color: #fff;
				width: 20px;
				height: 20px;
				position: absolute;
				top: 15px;
				left: 30px;
				border-radius: 50%;
				text-align: center;
				line-height: 20px;
				font-size: 35px;
			}
			.frequentGroupWrap{
				padding:10px;
			}
			.allPersonPageWrap{
				position: relative;
				height: 100%;
			}
			.searchBar{
				position: absolute;
				top: 0;
				left: 0;
				right: 0;
				height:40px;
				z-index: 1;
			}
			.searchContent{
				padding-top: 40px;
				width: 100%;
				height: 100%;
				overflow: auto;
			}
			.groupHeader{
			    overflow: hidden;
			    margin:5px 0;
			}
			.GroupToggle{
				font-size: 20px;
			    font-weight: bold;
			    color: #017afd;
			    line-height: 25px;
			    float: left;
			}
			.group{
				margin:5px 0;
			}
			.groupName{
				margin-left: 20px;
			    line-height: 30px;
			}
			.groupBody {
				margin-left: 15px;
			}
			.groupBody .mui-checkbox.mui-left label, .mui-radio.mui-left label{
				padding-left:45px;
			}

			.organizationWrap{
				padding:10px;
			}
			.organizationHeader{
			    position: relative;
			}
			.selectedCount{
				display: inline-block;
				color: #969696;
				position: absolute;
				right: 35px;
				top: 5px;
				font-size: 14px;
			}
			.organizationName{
				font-size: 16px;
				padding-left: 35px;
			    background-image: url(images/orgTitle.png);
			    background-repeat: no-repeat;
			    background-position: 5px center;
			    background-size: 25px;
			    line-height: 30px;
				font-size: 14px;
				margin: 5px 0;
			}
			.organizationToggle{
				background-repeat: no-repeat;
				background-position: center;
				position: absolute;
				top: 5px;
				right: 15px;
				color: #969696;
				transition: all 0.5s;
			}
			.organizationToggle.opened{
				transform: rotateZ(90deg);
			}
			.organizationToggle.open{
				background-image: url(images/right.png);
			}
			.organizationToggle.close{
				background-image: url(images/down.png);
			}
			.organizationWrap .mui-checkbox input[type=checkbox], .mui-radio input[type=radio]{
				top:0px;
			}
			.organizationWrap .mui-input-group .mui-input-row{
				height: 40px;
			}
			.organizationWrap .mui-input-row{
				height:40px;
			}
			.organizationWrap .groupBody .mui-checkbox.mui-left label, .mui-radio.mui-left label{
				padding:0;
				padding-left: 45px;
			}
			.organizationWrap .groupBody .name,.organizationWrap .groupBody .depart{
				line-height: 30px;
			}

			.mui-scroll-wrapper{
				overflow: auto;
			}
			.mui-slider .mui-slider-group
			{
				width: 100%;
				height: 100%;
			}
			.mui-card .sortable-ghost {
				opacity: 0.4;
				background-color: #F4E2C9;
			}
			.selectedPersonWrap .movePosition{
			    -webkit-touch-callout:none;  /*系统默认菜单被禁用*/
			    -webkit-user-select:none; /*webkit浏览器*/
			    -khtml-user-select:none; /*早期浏览器*/
			    -moz-user-select:none;/*火狐*/
			    -ms-user-select:none; /*IE10*/
			    user-select:none;
			    -webkit-touch-callout: none; /* IOS 禁止长按链接与图片弹出菜单  */
			}
			.groupBody{
				display: none;
			}
			.getMore{
				line-height: 30px;
				text-align: center;
			}
			.tip{
				line-height: 25px;
				text-align: center;
				font-size: 14px;
				margin-top: 20px;
			}
			.roleWrap{
				padding-left: 15px;
			}
			.roleWrap .mui-input-row label{
				padding: 5px 15px;
			}
			.roleWrap .tip{
				line-height: 25px;
				text-align: center;
				font-size: 14px;
				margin-top:0;
			}
		</style>
	</head>

	<body>
		<header id="header" class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">按照角色选择</h1>
		</header>
		<div class="footerBar">
			<ul class="footerList">
				<li class="allPageBtn selectAll">全选</li>
				<li>
					<a  class="mui-control-item" href="#item3mobile"><button class="selectedPerson">已选(<span class="selectedNum">0</span>)</button></a>
				</li>
				<li>
					<input class="confirmBtn" type="button" value="确定">
				</li>
			</ul>
		</div>
		<div class="mui-content mui-slider">
			<div class="mui-slider-group ">
				<!-- 常用组 -->
				<div id="item4mobile" class="mui-slider-item mui-control-content">
					<div id="scroll4" class="mui-scroll-wrapper">
						<div class="mui-scroll">
							<div class="hide mui-loading">
								<div class="mui-spinner">
								</div>
							</div>
							<div class="roleWrap" id="roleWrap"></div>
						</div>
					</div>

				</div>
				<!-- 已选-->
				<div id="item3mobile" class="mui-slider-item mui-control-content">
					<div id="scroll3" class="mui-scroll-wrapper">
						<div class="mui-scroll">
							<div class="hide mui-loading">
								<div class="mui-spinner">
								</div>
							</div>
							<div class="selectedPersonWrap">
								<div class="mui-card">
									<form class="mui-input-group">
										<p class="tip">无选择项</p>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
		<script src="https://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
		<script src="js/mui.min.js"></script>
		<script type="text/javascript" src="js/Sortable.min.js"></script>
		<script type="text/javascript">
			var INPUTNAME = "<%= inputName %>";
			var HIDDENUSERID = "<%= hiddenUserId %>";
			if(INPUTNAME==HIDDENUSERID){
				$('#'+HIDDENUSERID, window.parent.document).val("");
				$('#'+INPUTNAME, window.parent.document).val("");
			}
			var selectedId = $("#"+HIDDENUSERID,parent.document).val();
			var SELECTED = selectedId ? selectedId.split(",") : [];
			var single = "<%= single %>";
			if(single === "true"){
				$(".selectAll").remove();
			}
			mui.init({
				swipeBack: false
			});
			creatNav();
			function creatNav(){
				var header = document.getElementById("header");
				 //左侧图标+文字按钮
				var leftbtn = document.createElement('button');
				leftbtn.className = 'mui-btn mui-btn-blue mui-btn-link mui-btn-nav mui-pull-left';
				var span = document.createElement('span');
				span.className = 'mui-icon mui-icon-left-nav';
				leftbtn.appendChild(span);
				var text = document.createTextNode('取消');
				leftbtn.appendChild(text);
				leftbtn.addEventListener("tap",function(){
					setTimeout(function(){
						$('#selectRoleIframe', window.parent.document).hide();
					},300);
				});

				remove('.mui-pull-left');
				header.appendChild(leftbtn);
			}

			 //删除原先存在的导航栏节点
			function remove(selector) {
				var elem = header.querySelector(selector);
				if (elem) {
					header.removeChild(elem);
				}
			}
			(function($) {
				/*左右滚动显示滚动条*/
				$('.mui-scroll-wrapper').scroll({
					indicators: false //是否显示滚动条
				});
			})(mui);
			$(function(){
				renderRoleWrap();

				selectedCallback(SELECTED);

				$(".roleWrap").on("tap",".groupHeader",function(){
					$(this).siblings('.groupBody').slideToggle("fast");
					var showIcon = $(this).children(".GroupToggle");//下拉图标
					if(showIcon.hasClass('open')){
						showIcon.removeClass('open');
						showIcon.text("+");
					}else{
						showIcon.addClass('open');
						showIcon.text("-");
					}
				});
				/*已选人员*/
				$(".selectedPerson").on("tap",function(){
					$("#header").find(".mui-title").text("已选");
				});

				/*已选移除人员*/
				$(".selectedPersonWrap").on("tap",".removeItem",function(){
					var that = $(this);
					var roleId = that.closest('.mui-input-row').attr("roleId");
					SELECTED.splice($.inArray(roleId,SELECTED),1);
					that.closest('.mui-input-row').remove();
					if($(".selectedPersonWrap").find('.mui-input-row').length == 0){
						$(".selectedPersonWrap").find(".tip").show();
					}
					var curSelected = $(".selectedNum").text();
					$(".mui-input-row[roleId="+roleId+"]").find(".leftCheckbox").removeAttr('checked');
					setSelectedNumber();
					$(".selectAll").text("全选");
					$(".selectAll").removeClass('cancelSelectAll');
				});
				/*点击确定执行回调*/
				$(".confirmBtn").on("tap",function(){
					var selected = $(".selectedPersonWrap").find(".mui-input-row");
					var uuidArr = [];
					var userNamesArr = [];
					for(var i=0,l=selected.length;i<l;i++){
						var roleId = $(selected[i]).attr("roleId");
						var userName = $(selected[i]).find('.name1').text();
						uuidArr.push(roleId);
						userNamesArr.push(userName);
					}
					$('#'+HIDDENUSERID, window.parent.document).val(uuidArr.join(","));
					$('#'+INPUTNAME, window.parent.document).val(userNamesArr.join(","));
					setTimeout(function(){
						$('#selectRoleIframe', window.parent.document).hide();
					},300);
				});
				/* 拖动排序*/
				var el = $('.selectedPersonWrap .mui-input-group')[0];
				new Sortable(el, {
				    group: { name: "mui-input-row", pull: true, put: true},
				    store: null, //   @see Store
				    handle: ".movePosition", // 点击目标元素约束开始
				    draggable: ".mui-input-row",   // 指定那些选项需要排序
				    ghostClass: "sortable-ghost",
				    chosenClass: "sortable-chosen",
				    animation: 150,
				    onStart: function (/**Event*/evt) { // 拖拽
				        var itemEl = evt.item;
				        $(".selectedPersonWrap")[0].style.overflow = "hidden"
				    },

				    onEnd: function (/**Event*/evt) { // 拖拽
				        var itemEl = evt.item;
				    },

				    onAdd: function (/**Event*/evt){
				        var itemEl = evt.item;
				    },

				    onUpdate: function (/**Event*/evt){
				        var itemEl = evt.item; // 当前拖拽的html元素
				    },

				    onRemove: function (/**Event*/evt){
				        var itemEl = evt.item;
				    }
				});
				/*长按禁止显示系统菜单*/
				$(".selectedPersonWrap").on("touchstart ",".movePosition",function(e){
					e.preventDefault();
				});
				/*滑动切换标题*/
				document.querySelector('.mui-slider').addEventListener('slide', function(event) {
				  //注意slideNumber是从0开始的；
				  var index = event.detail.slideNumber;
				 //  if(index === 0){
					// 	$("#header").find(".mui-title").text("常用人");
					// }else
				    if(index === 0){
						$("#header").find(".mui-title").text("按照角色选择");
					}else if(index === 1){
						$("#header").find(".mui-title").text("已选");
					}
				});

				$(document).on("change","input.leftCheckbox",function(){
					var item = {};
					item.id = $(this).attr("roleId");
					item.name = $(this).attr("roleName");

					if($(this).prop("checked") ){//选中添加
						if(single == "true"){
							$(".selectedPersonWrap").find(".removeItem").trigger('tap');
						}
						renderSelectedPerson(item);

						$(".mui-input-row[roleId="+item.id+"]").find(".leftCheckbox").prop("checked","checked");
						if($(".roleWrap .leftCheckbox").length == $(".organizationWrap .leftCheckbox:checked").length){
							$(".selectAll").text("取消全选");
							$(".selectAll").addClass('cancelSelectAll');
						}
					}else{//取消选中
						$(".selectedPersonWrap .mui-input-row[roleId="+item.id+"]").remove();//移除已选组里的人员
						SELECTED.splice($.inArray(item.id,SELECTED),1);
						$(".mui-input-row[roleId="+item.id+"]").find(".leftCheckbox").removeAttr('checked');
						setSelectedNumber();
						$(".selectAll").text("全选");
						$(".selectAll").removeClass('cancelSelectAll');
					}
				});
				/*全选操作和取消全选*/
				$(".selectAll").on("tap",function(){
					if(!$(this).hasClass('cancelSelectAll')){
						$(".roleWrap").find(".leftCheckbox").removeAttr('checked');
						$(".roleWrap").find(".leftCheckbox").trigger('click');
						$(this).text("取消全选");
						$(this).addClass('cancelSelectAll');
					}else{
						$(".roleWrap").find(".leftCheckbox").removeAttr('checked');
						$(".selectedPersonWrap").find(".removeItem").trigger('tap');
						$(this).text("全选");
						$(this).removeClass('cancelSelectAll');
					}
				});

			})

			/**
			 * 渲染已选
			 */
			 function renderSelectedPerson(item) {
			 	if($(".selectedPersonWrap .mui-input-row[roleId="+item.id+"]").length > 0){
			 		return;
			 	}
			 	var html = [];
			 	html.push('<div roleId="' + item.id + '" class="mui-input-row mui-checkbox mui-left"><div class="removeItem">-</div><label for="AllPeople-'+item.id+'"><div class="name">');
				html.push('<div class="name1">'+item.name+'</div>');
				html.push('</div></label>');
				html.push('<div class="movePosition"></div>');
				html.push('</div>');
				$(".selectedPersonWrap").find(".tip").hide();
				$(".selectedPersonWrap").find(".mui-input-group").append(html.join(""));
				SELECTED.push(parseInt(item.id));
				setSelectedNumber();
			 }

			 /**
			  * 获取选中人员的个数
			  */
			 function setSelectedNumber(){
			 	var len = $(".selectedPersonWrap").find(".mui-input-row").length;
			 	$(".selectedNum").text(len);
			 	return len;
			 }
			/**
			 * 获取常用组的所有组框架
			 * @return {[type]} [description]
			 */
			function renderRoleWrap(){
				if($("#frequentGroupWrap").children().length){
					return;
				}
				$.ajax({
					url: "/orgSelectManager/selectUserPrivTree.action",
					type: "post",
					data:{

					},
					dataType:"json",
					success:function(data, textStatus){
						if(data.rtState){
							var rtData = data.rtData;
							var treeData = toTreeData(rtData,"id","pId","child");
							$("#roleWrap").html(renderRole(treeData));
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						if(textStatus){
							console.log(textStatus);
						}
					}
				})
			}
			/**
			 * 渲染角色
			 */
			function renderRole(rtData) {
				var html = [];
				for(var i = 0,l = rtData.length;i<l;i++){
					var isOrg = rtData[i].id.indexOf("org") == 0 ? true : false;
					var isDept = rtData[i].id.indexOf("dept") == 0 ? true : false;
					if(isOrg || isDept){
						html.push('<div class="group">');
							html.push('<div gpId="' + rtData[i].id +'" class="groupHeader FrequentGroup-G'+rtData[i].id+'">');
								html.push('<p class="GroupToggle">+</p>');
								html.push('<p class="groupName">' + rtData[i].name + '</p>');
							html.push('</div>');
							html.push('<div class="groupBody">');
							if("undefined" !== typeof(rtData[i].child)){
								html.push(renderRole(rtData[i].child));
							}else{
								html.push('<p class="tip">暂无角色</p>')
							}
							html.push('</div>');
						html.push('</div>');
					}else{
						html.push('<div roleId="' + rtData[i].id + '" class="mui-input-row mui-checkbox mui-left"><label for="role-'+rtData[i].id+'"><div class="name">');
						html.push('<div class="name1">'+rtData[i].name+'</div>');
						html.push('</div></label>');
						html.push('<input '+ ($.inArray(rtData[i].id, SELECTED) > -1 ? 'checked="checked"' : "") +' class="leftCheckbox" roleId="'+rtData[i].id+'" RoleName="'+rtData[i].name+'"   id="role-'+rtData[i].id+'" name="role" type="checkbox" >');
						html.push('</div>');
					}

				}
				return html.join("");
			}
			/**
			 * 一维数组转化为树
			 */
			var toTreeData = function (jsonData, id, pid, child) {
		        id = id ? id : "id";
		        pid = pid ? pid : "pId";
		        child = child ? child : "child";
				var tree = [];
		        var hash = {};
		        var newTree = [];
		        for (var i = 0; i < jsonData.length; i++) {
		            hash[jsonData[i][id]] = jsonData[i];
		        }
		        for (var j = 0; j < jsonData.length; j++) {
		            var aVal = jsonData[j],
		                hashVP = hash[aVal[pid]];
		            if (hashVP) {
		                !hashVP[child] && (hashVP[child] = []);
		                hashVP[child].push(aVal);
		            } else {
		                tree.push(aVal);
		            }
		        }
		        return tree;
		    };

		    /**
		     * 已选人员回填
		     */
		    function selectedCallback(selected){
		    	selected = selected.length ? selected : [];
		    	var selectName = $("#"+INPUTNAME,parent.document).val();
		    	var selectNameArr = selectName ? selectName.split(",") : [];

		    	var l = Math.min(selected.length,selectNameArr.length);
		    	for(var i=0;i<l;i++){
		    		var callbackItem = {};
		    		callbackItem.id = selected[i];
		    		callbackItem.name = selectNameArr[i];
		    		renderSelectedPerson(callbackItem);
		    	}
		    }
		</script>

	</body>

</html>