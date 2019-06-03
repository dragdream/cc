<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String inputName = request.getParameter("inputName");
	String hiddenUserId = request.getParameter("hiddenUserId");
	String single = request.getParameter("single");
	String callback = request.getParameter("callback");
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
			.mui-checkbox input[type=checkbox], .mui-radio input[type=radio]{
				top: 18px;
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
				top: 15px;
				background-image: url(images/move.png);
				background-repeat: no-repeat;
				background-size: 30px;
				background-position: center;
			}
			.removeItem{
				background-color: #f00;
				color: #fff;
				width: 20px;
				height: 20px;
				position: absolute;
				top: 22px;
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
			    margin-left: 37px;
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
				/*padding-left: 35px;*/
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
				top:5px;
				left:5px;
			}
			.organizationWrap .mui-input-group .mui-input-row{
				height: 40px;
			}
			.organizationWrap .mui-input-row{
				height:40px;
			}
			.organizationWrap .groupBody .mui-checkbox.mui-left label, .mui-radio.mui-left label{
				padding:0;
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
				font: 20px;
				margin-top: 20px;
			}
			.organizationWrap .mui-checkbox.mui-left label, .mui-radio.mui-left label{
				padding-left:0px;
			}
			.mui-checkbox.mui-left label, .mui-radio.mui-left label{
				line-height: 35px;
			}
		</style>
	</head>
	<body>
		<header id="header" class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title">组织架构</h1>
		</header>
		<div class="footerBar" style="cursor:pointer">
			<ul class="footerList" style="cursor:pointer">
				<li class="allPageBtn selectAll">全选</li>
				<li>
					<a  class="mui-control-item" href="#item3mobile"><button class="selectedPerson">已选(<span class="selectedNum">0</span>)</button></a>
				</li>
				<li style="cursor:pointer">
					<input class="confirmBtn" type="button" value="确定" style="cursor:pointer">
				</li>
			</ul>
		</div>
		<div class="mui-content mui-slider">
			<div class="mui-slider-group ">
				<!-- 组织架构 -->
				<div id="item5mobile" class="mui-slider-item mui-control-content mui-active">
					<div id="scroll5" class="mui-scroll-wrapper">
						<div class="mui-scroll">
							<div class="hide mui-loading">
								<div class="mui-spinner">
								</div>
							</div>
							<div class="organizationWrap"></div>
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
			var callback = "<%= callback %>";
			
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
				leftbtn.addEventListener("tap",function(){
					setTimeout(function(){
						$('#selectDepartIframe', window.parent.document).hide();
					},300);
				});
				var text = document.createTextNode('取消');
				leftbtn.appendChild(text);

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
				getOrgDepAll();
			})(mui);
			$(function(){
				selectedCallback(SELECTED);
				/*点击空白弹出框消失*/
				$(document).on("tap",function(){
					$(".allPageList").hide();
				})
				/*常用组和组织机构  点击切换子菜单*/
				$(".organizationWrap").on("tap",".groupHeader",function(){
					$(this).siblings('.groupBody').slideToggle("fast");
					var showNext = $(this).children(".organizationToggle");//下拉图标
					if(showNext.length){
						showNext.toggleClass('opened');
					}
				});
				/*已选人员*/
				$(".selectedPerson").on("tap",function(){
					$("#header").find(".mui-title").text("已选");
				});
				/*已选移除人员*/
				$(".selectedPersonWrap").on("tap",".removeItem",function(){
					var that = $(this);
					var uuid = that.closest('.mui-input-row').attr("uuid");
					SELECTED.splice($.inArray(uuid,SELECTED),1);
					that.closest('.mui-input-row').remove();
					if($(".selectedPersonWrap").find('.mui-input-row').length == 0){
						$(".selectedPersonWrap").find(".tip").show();
					}
					var curSelected = $(".selectedNum").text();
					$(".leftCheckbox[dpid="+uuid+"]").removeAttr('checked');
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
						var uuid = $(selected[i]).attr("uuid");
						var userName = $(selected[i]).find('.name1').text();
						uuidArr.push(uuid);
						userNamesArr.push(userName);
					}
					
					//获取原先的值
					var oNames = $('#'+INPUTNAME, window.parent.document).val();
					var oIds = $('#'+HIDDENUSERID, window.parent.document).val();
					
					if(oIds==""){
						oIds = [];
					}else{
						oIds = oIds.split(",");
					}
					if(oNames==""){
						oNames = [];
					}else{
						oNames = oNames.split(",");
					}
					
					
					for(var i in oIds){//遍历旧的ids
						var exists = false;
						for(var i1 in uuidArr){//遍历新的ids
							if(uuidArr[i1]==oIds[i]){//如果在新的里面找不到旧的 说明旧的是删除了
								exists = true;
								break;
							}
						}
						if(!exists){
							if(callback!="undefined" && callback!="null" && callback!=""){
								eval("parent."+callback+"(\""+oIds[i]+"\",\""+oNames[i]+"\",\"REMOVE_ITEM\")");
							}
						}
					}
					
					//遍历新的ids
					for(var i in uuidArr){//遍历旧的ids
						var exists = false;
						for(var i1 in oIds){//遍历新的ids
							if(uuidArr[i]==oIds[i1]){//如果在旧的里面找不到新的 说明新的是添加的
								exists = true;
								break;
							}
						}
						if(!exists){
							if(callback!="undefined" && callback!="null" && callback!=""){
								eval("parent."+callback+"(\""+uuidArr[i]+"\",\""+userNamesArr[i]+"\",\"ADD_ITEM\")");
							}
						}
					}
					
					$('#'+HIDDENUSERID, window.parent.document).val(uuidArr.join(","));
					$('#'+INPUTNAME, window.parent.document).val(userNamesArr.join(","));
					setTimeout(function(){
						$('#selectDepartIframe', window.parent.document).hide();
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
				    if(index === 0){
						$("#header").find(".mui-title").text("组织架构");
					}else if(index === 1){
						$("#header").find(".mui-title").text("已选");
					}
				});

				$(document).on("change","input.leftCheckbox",function(){
					var item = {};
					item.id = $(this).attr("dpId");
					item.name = $(this).attr("dpName");

					if($(this).prop("checked") ){//选中添加
						if(single == "true"){
							$(".selectedPersonWrap").find(".removeItem").trigger('tap');
						}
						renderSelectedPerson(item);
						$(".mui-input-row[uuid="+item.id+"]").find(".leftCheckbox").prop("checked","checked");
						if($(".organizationWrap .leftCheckbox").length == $(".organizationWrap .leftCheckbox:checked").length){
							$(".selectAll").text("取消全选");
							$(".selectAll").addClass('cancelSelectAll');
						}
					}else{//取消选中
						$(".selectedPersonWrap .mui-input-row[uuid="+item.id+"]").remove();//移除已选组里的人员
						SELECTED.splice($.inArray(item.id,SELECTED),1);
						$(".mui-input-row[uuid="+item.id+"]").find(".leftCheckbox").removeAttr('checked');
						setSelectedNumber();
						$(".selectAll").text("全选");
						$(".selectAll").removeClass('cancelSelectAll');
					}
				});

				$(".selectAll").on("tap",function(){
					if(!$(this).hasClass('cancelSelectAll')){
						$(".organizationWrap").find(".leftCheckbox").removeAttr('checked');
						$(".organizationWrap").find(".leftCheckbox").trigger('click');
						$(this).text("取消全选");
						$(this).addClass('cancelSelectAll');
					}else{
						$(".organizationWrap").find(".leftCheckbox").removeAttr('checked');
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
			 	if($(".selectedPersonWrap .mui-input-row[uuid="+item.id+"]").length > 0){
			 		return;
			 	}
			 	var html = [];
			 	html.push('<div uuid="' + item.id + '" class="mui-input-row mui-checkbox mui-left"><div class="removeItem">-</div><label for="AllPeople-'+item.id+'"><div class="name">');
				html.push('<div class="name1">'+item.name+'</div>');
				html.push('</div></label>');
				html.push('<div class="movePosition"></div>');
				html.push('</div>');
				$(".selectedPersonWrap").find(".tip").hide();
				$(".selectedPersonWrap").find(".mui-input-group").append(html.join(""));
				if(!($.inArray(item.id,SELECTED) > -1 )){
					SELECTED.push(parseInt(item.id));
				}
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
			 * 获取组织架构所有的部门目录
			 */
			function getOrgDepAll(){
				if($(".organizationWrap").children().length > 0){
					return;
				}
				$.ajax({
					url: "/orgSelectManager/getSelectDeptTreeAll.action",
					type: "post",
					data:{

					},
					dataType:"json",
					success:function(data, textStatus){
						if(data.rtState){
							var rtData = data.rtData;
							var treeData = toTreeData(rtData,"id","pId","child");
							var html = renderOrgDep(treeData)
							$(".organizationWrap").html(html);
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
			 * 一维数组转化为树
			 */
			var toTreeData = function (jsonData, id, pid, child) {
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
		     *  递归渲染组织架构的部门结构
		     *
		     * @param      {Array}  treeData  The tree data
		     * @return     {Array}   { description_of_the_return_value }
		     *
		     */
		    function renderOrgDep(treeData){
		    	var html = [];
		    	for(var i = 0,l = treeData.length;i<l;i++){
		    		html.push('<div class="group mui-checkbox mui-left">');
	    				html.push('<input dpName="'+treeData[i].name+'" dpId="'+treeData[i].id+'" '+ ($.inArray(treeData[i].id, SELECTED) > -1 ? 'checked="checked"' : "") +' class="leftCheckbox" id="OrgDep-'+treeData[i].id+'" name="checkbox" value="depart"  type="checkbox" >');
		    			html.push('<div gpId="' + treeData[i].id +'" class="organizationHeader groupHeader organization-G'+treeData[i].id+'">');
		    			if( "undefined" == typeof(treeData[i].child) ){
		    				html.push('<label for="OrgDep-'+treeData[i].id+'">');
		    			}
			    				html.push('<div class="organizationName">' + treeData[i].name + '</div>');
			    		if( "undefined" != typeof(treeData[i].child) ){
		    				html.push('<span class="organizationToggle">></span>');
		    			}

	    				if( "undefined" == typeof(treeData[i].child) ){
	    					html.push('</label>');
	    				}
		    			html.push('</div>');
		    			html.push('<div class="groupBody">');
		    			if( "undefined" != typeof(treeData[i].child) ){
		    				html.push(renderOrgDep(treeData[i].child));
		    			}
		    			html.push('</div>');
		    		html.push('</div>');
		    	}
		    	return html.join("");
		    }

		    /**
		     * 已选人员回填
		     */
		    function selectedCallback(selected){
		    	selected = selected.length ? selected : [];
		    	var selectName = $("#"+INPUTNAME,parent.document).val();
		    	var selectNameArr = selectName ? selectName.split(",") : [];

		    	var l = Math.max(selected.length,selectNameArr.length);
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