	(function($){
		var itemTotle,rtData,$check,pageSize ;
		var options ;
		var pageTotle;//页码数为向上取整数
		var checkLog = true;

		$.fn.datagrid=function(config,param){
			var datagridTable = this;
			var selectionLog = true;



			if(typeof(config) == "string"){
				switch (config){
					case "getData":
						return rtData;
					case "reload":
						for(var i in param){
							config.queryParams[i] = param[i];
						};
						startRender();return;
					case "getSelections":

							return getSelections();
					default:alert("提示\n初始化失败:未定义的指令！");
				}
			}

			var pageList = config.pageList;//可以选择显示的条数
			 pageSize = config.pageList[0];//当前的默认的条数

			config.queryParams = {};
			config.queryParams.page = 1;
			config.queryParams.rows = pageSize;

			if(typeof(config) == "object"){
				options = config;//这里把config全部复制了
				startRender();
			}
			//获取选中的行数
			//getSelections(options);

			function getSelections(){
					$check = $(".multiCheck");
					var arr = [];
					for(var i=0;i<$check.length;i++){
							if($($check[i]).prop("checked")){
								arr.push(rtData.rows[i]);
						  }
					}
					return arr;
			};

			//对列排序
			function sortAndOrder(){
				var orderWay = ["asc","desc"];//0 升序 1降序
				$(datagridTable).delegate(".headtd","click",function(){
					var $span = $(this).find("span");
						options.queryParams.sort = $(this).attr("field");
						if($span.hasClass("caret-up")){//默认第一次点击是升序
							options.queryParams.order =  orderWay[0];
							$span.removeClass("caret-up");
							$span.addClass("caret-down");
						}else{
							options.queryParams.order =  orderWay[1];
							$span.removeClass("caret-down");
							$span.addClass("caret-up");
						}
						renderTable(options);
				});
			}


			//添加页码栏
			function addPageBlock(){
				if(checkLog){
				var navigatehtml = ["<div class = 'dg-page'>"];
				navigatehtml.push("<select class=\"dg-pageList\">");
				for(var i=0;i<options.pageList.length;i++){
					navigatehtml.push("<option value=\"" + options.pageList[i] + "\">" + options.pageList[i] + "</option>");
				};
				navigatehtml.push("</select>");
				navigatehtml.push("<button disabled class=\"dg-firstPage\"><a style=\"color:#aaa;cursor:default;\">首页</a></button>"+
												"<button disabled class=\"dg-prePage\"><a style=\"color:#aaa;cursor:default;\">上一页</a></button>"+
												"<span class=\"dg-current\">"+
													"第<input class = 'dg-currentPage jump2page' value='1' type=\"text\">页/共<span class = 'dg-totalPage'></span>页"+
												"</span>"+
												"<button class=\"dg-nextPage\"><a>下一页</a></button>"+
												"<button class=\"dg-lastPage\"><a>尾页</a></button>"+
												"<button class=\"dg-jump\"><a>跳转</a></button>");
				navigatehtml.push("</div>");
				$(datagridTable).after(navigatehtml.join(""));
				}

			};

			//写入页码总数
			function setTotalPage(){
				$(".dg-totalPage").text(pageTotle);
			}
			function setCurrentPage(page){
				$(".dg-currentPage").val(page);
			}
			//获取页面的pageSize
			function changePagesize(){
				$(".dg-pageList").on("change",function(){
					var val = $(".dg-pageList").val();
					options.queryParams.rows = val;
					pageSize = val;
					renderTable(options);
					setTotalPage();
				})

			}
			//设置容器外部间距
			$(datagridTable).css({"margin":"0 auto"});
			$(datagridTable).closest("body").css({"box-sizing": "border-box","padding":"0 20px","overflow":"hidden"});

			//先渲染指定field和title的表头
			var tableHeader = ["<thead><tr>"];
			for(var i=0;i<config.columns.length;i++){
				if(config.columns[i].sortable){
					tableHeader.push("<td class=\"headtd\" field=\"" + config.columns[i].field + "\" style=\"width:" + config.columns[i].width + "px\">"+config.columns[i]["title"]+ "<span style=\"margin-top:15px;margin-left:5px;\"></span>" +"</td>");
						sortAndOrder();
				}else{
					tableHeader.push("<td field=\"" + config.columns[i].field + "\" style=\"width:" + config.columns[i].width + "px\">"+config.columns[i]["title"]+"</td>");
				}
			}
			tableHeader.push("<td style=\"display:hidden;width:auto;\"></td>");
			tableHeader.push("</tr></thead>");
			tableHeader.push("<tbody>");
			tableHeader.push("</tbody>");
			//将表头加入到table中

			$(datagridTable).append(tableHeader.join(""));

			//ajax请求后台数据并渲染
			function renderTable(opt){
				$.ajax({
				type:"post",
				dataType:"json",
				url:opt.url,
				data:opt.queryParams,
				success:function(returnData){//这里的returnData是formatter传过来的数据
					var bodyHtml = [];
					rtData = returnData;
					itemTotle = returnData.total;
					pageTotle = Math.ceil(itemTotle/pageSize);
					setTotalPage();
					for(var i=0;i<returnData.rows.length;i++){
						bodyHtml.push("<tr>");
						for(var j=0;j<opt.columns.length;j++){
							bodyHtml.push("<td>");
							if(opt.columns[j].formatter){
								//这里是调用formatter后的返回值来push
								bodyHtml.push(opt.columns[j].formatter(returnData.rows[i][opt.columns[j].field],returnData.rows[i],i));
							}else{
								bodyHtml.push(returnData.rows[i][opt.columns[j].field]);
							}
							bodyHtml.push("</td>");
						}
						bodyHtml.push("<td style=\"display:hidden;width:auto;\"></td>");
						bodyHtml.push("</tr>");
					}
					$(datagridTable).find("tbody").html("");
					$(datagridTable).find("tbody").append(bodyHtml.join(""));
					//如果成功的话，执行onLoadSuccess
					if(opt.onLoadSuccess){
						datagridTable.tableresize();
						opt.onLoadSuccess();
							//$(datagridTable).tableresize();
					}
					tableHeight();
					//是否有多选框
					if(opt.multiSelect){
						var allCheckbox = "<td style=\"text-align: center;width: 14px;height:14px;padding: 10px;\"><input class = \"allCheck\" style = \"margin:0 auto;\" type=\"checkbox\"></td>";
						var multiCheck = "<td style=\"text-align: center;width: 14px;height:14px;padding: 10px;\"><input class = \"multiCheck\" style = \"margin:0 auto;\" type=\"checkbox\"></td>";
						//判断是否添过
						if(checkLog){
							$('thead tr').prepend(allCheckbox);
							checkLog = false;
						}
						$('tbody tr').prepend(multiCheck);
						$check = $(".multiCheck");
						//getSelections(options);
						$(".allCheck").on("change",function(){
							if($(this).prop("checked")){
								$(".multiCheck").prop("checked",true);
							}else{
								$(".multiCheck").prop("checked",false);
							}
						})
					};

					//是否可排序
					if(opt.sortable){
						sortAndOrder(options);
					}


				},
					error:function(data){
						alert('获取数据失败！请重试！');
					}
				});
			}
			function tableHeight(){
				datagridTable.wrap('<div class="tableWrap" style="overflow:auto"></div>');
					var clientHeight = $(window).height();
					$(".tableWrap").height(clientHeight - 120);
				$(window).resize(function(){
					datagridTable.wrap('<div class="tableWrap" style="overflow:auto"></div>');
					var clientHeight = $(window).height();
					$(".tableWrap").height(clientHeight - 120);
				})
			}

			//点击页码获取数据
			function changePage(){
				var val = $(".dg-currentPage").val();

				$('.dg-firstPage').on('click',function(){
					options.queryParams.page = 1;
					if(pageTotle>1){
							$(".dg-nextPage a").css({"color":"#0f92d8","cursor":"pointer"});
							$(".dg-nextPage").removeAttr("disabled");
						}else{
							$(".dg-nextPage a").css({"color":"#aaa","cursor":"default",});
							$(".dg-nextPage").attr("disabled","disabled");
						}
					$(this).find("a").css({"color":"#aaa","cursor":"default",});
					$(this).attr("disabled","disabled");
					$(".dg-prePage a").css({"color":"#aaa","cursor":"default",});
					$(".dg-prePage").attr("disabled","disabled");
					renderTable(options);
					setCurrentPage(1);
				});

				$(".dg-nextPage").on("click",function(){
					++options.queryParams.page>=pageTotle && (options.queryParams.page=pageTotle);//判断是否是最后一页
					if(options.queryParams.page==pageTotle){
						if(pageTotle != 1){
							$(".dg-prePage").find("a").css({"color":"#0f92d8","cursor":"pointer",});
							$(".dg-prePage").removeAttr("disabled");
							$(".dg-firstPage").find("a").css({"color":"#0f92d8","cursor":"pointer",});
							$(".dg-firstPage").removeAttr("disabled");
						}
						$(".dg-lastPage").find("a").css({"color":"#aaa","cursor":"default",});
						$(this).find("a").css({"color":"#aaa","cursor":"default",});
						$(this).attr("disabled","disabled");
						$(".dg-lastPage").attr("disabled","disabled");
					}else{
						$(".dg-firstPage a").css({"color":"#0f92d8","cursor":"pointer"});
						$(".dg-prePage a").css({"color":"#0f92d8","cursor":"pointer"});
						// $(".dg-nextPage").removeAttr("disabled");
						$(".dg-prePage").removeAttr("disabled");
						$(".dg-firstPage").removeAttr("disabled");
					}
					setCurrentPage(options.queryParams.page);
					renderTable(options);

				});


				$(".dg-prePage").on('click',function(){
					--options.queryParams.page<=0 && (options.queryParams.page=1);//判断是否是第一页
					if(pageTotle != 1){
						$(".dg-nextPage").find("a").css({"color":"#0f92d8","cursor":"pointer",});
						$(".dg-nextPage").removeAttr("disabled");
						$(".dg-lastPage").find("a").css({"color":"#0f92d8","cursor":"pointer",});
						$(".dg-lastPage").removeAttr("disabled");
					}
					if(options.queryParams.page==1){
						$(".dg-firstPage a").css({"color":"#aaa","cursor":"default"});
						$(this).find("a").css({"color":"#aaa","cursor":"default",});
						$(this).attr("disabled","disabled");
						$(".dg-firstPage").attr("disabled","disabled");
					}else{
						$(".dg-lastPage a").css({"color":"#0f92d8","cursor":"pointer"});
						$(".dg-lastPage").removeAttr("disabled");
						$(".dg-nextPage a").css({"color":"#0f92d8","cursor":"pointer"});
						$(this).removeAttr("disabled");
						$(".dg-nextPage").removeAttr("disabled");
					}
					setCurrentPage(options.queryParams.page);
					renderTable(options);
				});

				$(".dg-lastPage").on("click",function(){
						if(pageTotle>1){
							$(".dg-prePage a").css({"color":"#0f92d8","cursor":"pointer"});
							$(".dg-prePage").removeAttr("disabled");
						}else{
							$(".dg-prePage a").css({"color":"#aaa","cursor":"default",});
							$(".dg-prePage").attr("disabled","disabled");
						}
						$(this).find("a").css({"color":"#aaa","cursor":"default",});
						$(this).attr("disabled","disabled");
						$(".dg-nextPage a").css({"color":"#aaa","cursor":"default",});
						$(".dg-nextPage").attr("disabled","disabled");

					options.queryParams.page = pageTotle;
					setCurrentPage(options.queryParams.page);
					renderTable(options);
				})

			};

			//跳转到某页
			function jump2page(){
				$(".dg-jump").on("click",function(){
					var jump = $(".jump2page").val();
					jump*=1;
					if(isNaN(jump)){
						$.MsgBox.Alert("提示","请输入一个数字！");
					}else{
						if(jump>pageTotle){
							$.MsgBox.Alert("提示","您输入的页码超过最大页数！");
						}else if(jump<=0){
							$.MsgBox.Alert("提示","您输入的页码小于1！");
						}else{
							options.queryParams.page = jump;
							renderTable(options);
							if(jump==1){
								$(".dg-firstPage a").css({"color":"#aaa","cursor":"default",});
								$(".dg-firstPage").attr("disabled","disabled");
								$(".dg-prePage").find("a").css({"color":"#aaa","cursor":"default",});
								$(".dg-prePage").attr("disabled","disabled");
								$(".dg-nextPage a").css({"color":"#0f92d8","cursor":"pointer"});
								$(".dg-nextPage").removeAttr("disabled");
							}else if(jump==pageTotle){
								$(".dg-lastPage").find("a").css({"color":"#aaa","cursor":"default",});
								$(".dg-lastPage").attr("disabled","disabled");
								$(".dg-nextPage").find("a").css({"color":"#aaa","cursor":"default",});
								$(".dg-nextPage").attr("disabled","disabled");
							}
						}
					}



				})
			};

			function startRender(){
				renderTable(options);
				addPageBlock();
				changePage();
				changePagesize();
				jump2page();
			};
			return datagridTable;
		}


		//列宽手动调整
		$.fn.tableresize = function () {
			var tTD; //用来存储当前更改宽度的Table Cell,避免快速移动鼠标的问题
			var table = document.getElementById(this.attr("id"));
			for (j = 0; j < table.rows[0].cells.length; j++) {
			    table.rows[0].cells[j].onmousedown = function () {
			      //记录单元格
			        tTD = this;
			       if (event.offsetX > tTD.offsetWidth - 10) {
			            tTD.mouseDown = true;
			          	tTD.oldX = event.x;
			            tTD.oldWidth = tTD.offsetWidth;
			        }

			        //记录Table宽度
			        //table = tTD; while (table.tagName != ‘TABLE') table = table.parentElement;
			       //tTD.tableWidth = table.offsetWidth;
			        };
			   table.rows[0].cells[j].onmouseup = function () {
			        //结束宽度调整
			        if (tTD == undefined) {tTD = this;}
			        tTD.mouseDown = false;
			        tTD.style.cursor = 'default';
			        this.style.border = 'none';
			        };
			    table.rows[0].cells[j].onmousemove = function () {
			       //更改鼠标样式
			       if (event.offsetX > this.offsetWidth - 10){
			          this.style.cursor = 'col-resize';
			         // this.style.border = '1px dashed #aaa';
			       }else{
			       		this.style.cursor = 'default';
			       }
			        //取出暂存的Table Cell
			       if (tTD == undefined) {tTD = this};
			       //调整宽度
			        if (tTD.mouseDown != null && tTD.mouseDown == true) {
			           tTD.style.cursor = 'default';
			            if (tTD.oldWidth + (event.x - tTD.oldX)>0)
			                tTD.width = tTD.oldWidth + (event.x - tTD.oldX);
			           //调整列宽
			           tTD.style.width = tTD.width;
			           tTD.style.cursor = 'col-resize';
			        	this.style.border = 'none';

			           //调整该列中的每个Cell
			           // table = tTD;
			           // while (table.tagName != 'TABLE') {table = table.parentElement};
			           // for (j = 0; j < table.rows.length; j++) {
			           //     table.rows[j].cells[tTD.cellIndex].width = tTD.width;
			           //     }
			          //调整整个表
			         //table.width = tTD.tableWidth + (tTD.offsetWidth – tTD.oldWidth);
			           //table.style.width = table.width;
			           }
			       };
			   }

		};



		//弹窗设计
		$.MsgBox = {
		    Alert: function (title, msg) {
		        GenerateHtml("alert", title, msg);
		        btnOk();  //alert只是弹出消息，因此没必要用到回调函数callback
		        btnNo();  //取消按钮事件
		    },
		    Confirm: function (title, msg, callback) {
		        GenerateHtml("confirm", title, msg);
		        btnOk(callback);
		        btnNo();  //取消按钮事件
		    }
		}
		//生成Html
		var GenerateHtml = function (type, title, msg) {
		    var _html = "";
		    _html += '<div id="win_box"></div><div id="win_con"><span id="win_tit">' + title + '</span>';
		    _html += '<a id="win_ico">X</a><div id="win_msg">' ;
		    if(type == "confirm"){
		    	_html += '<img id = "win_img" src="/common/zt_webframe/imgs/common_img/question.png" />&nbsp' + msg + '</div><div id="win_btnbox" class="clearfix"> ';

		    }else{
		    	_html += '<img id = "win_img" src="/common/zt_webframe/imgs/common_img/tip.png" />&nbsp' + msg + '</div><div id="win_btnbox" class="clearfix"> ';
		    }
		    _html += '<input id="win_check" type="checkbox" /> 不再提示';
		    if (type == "alert") {
		        _html += '<input id="win_btn_ok" type="button" class="btn-alert-blue" value="确定" />';
		    }
		    if (type == "confirm") {
		        _html += '<input id="win_btn_ok" class = "btn-win-white" type="button" value="确定" />';
		        _html += '<input id="win_btn_no" class = "btn-win-white" type="button" value="取消" />';
		    }
		    _html += '</div></div>';
		    //必须先将_html添加到body，再设置Css样式
		    $("body").append(_html); GenerateCss();
		}
		//生成Css
		var GenerateCss = function () {
		    var _width = document.documentElement.clientWidth;  //屏幕宽
		    var _height = document.documentElement.clientHeight; //屏幕高
		    var boxWidth = $("#win_con").width();
		    var boxHeight = $("#win_con").height();
		    //让提示框居中
		    $("#win_con").css({ top: (_height - boxHeight) / 2 + "px", left: (_width - boxWidth) / 2 + "px" });
		}
		//确定按钮事件
		var btnOk = function (callback) {
		    $("#win_btn_ok").click(function () {
		        $("#win_box,#win_con").remove();
		        if (typeof (callback) == 'function') {
		            callback();
		        }
		    });
		}
		//取消按钮事件
		var btnNo = function () {
		    $("#win_btn_no,#win_ico").click(function () {
		        $("#win_box,#win_con").remove();
		    });
		}


		$.fn.modal=function(){
			var modal = this;
			var zhezhao = '<div id="win_box"></div>';
			$("body").append(zhezhao);
			var cookie,modalName;
			var _width = document.documentElement.clientWidth;  //屏幕宽
		    var _height = document.documentElement.clientHeight; //屏幕高
		    //让提示框居中

		    console.log($(".modal-"+ modalName).css("top"))
			var modalClass = $(modal).attr("class");
			if(modalClass.indexOf(" ") > 0 ){
				var name = modalClass.split(" ");
				for(var i=0;i<name.length;i++){
					console.log(name[i]);
					console.log(name[i].substring(0,10));
					if(name[i].substring(0,10)=="modal-menu"){
						cookie = name[i];
						console.log(cookie);
						break;
					}
				}
				modalName = cookie.substring(11);
				$(".modal-"+ modalName).slideToggle(100);
			}else{
				modalName = modalClass.substring(11);
				$(".modal-"+ modalName).fadeToggle(100);
			}
			var boxWidth = $(".modal-"+ modalName).width();
		    var boxHeight = $(".modal-"+ modalName).height();
		    $(".modal-"+ modalName).css({ top: (_height - boxHeight) / 2 + "px", left: (_width - boxWidth) / 2 + "px" });
			$(".modal-"+ modalName).on("click",".modal-win-close,.modal-btn-close,.modal-save",function(){
				$("#win_box").remove();
				$(".modal-"+ modalName).hide();
			})

		}



	})(jQuery);


	//组件包
	/*下拉菜单*/
(function(){
	$(function(){
			var btnGroups = $(".btn-group");
			var btnMenu = $(".btn-menu");
			var btnContent = $(".btn-content");
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
					$($(this).parents()[i]).find(".btn-content").siblings(".btn-menu").removeClass("open");
				}
				if(bool){
					$(this).addClass("open");
				}
				if($(this).hasClass("open")){
					$(this).siblings(".btn-content").hide(50);
					$(this).removeClass("open");
				}else{
					$(this).siblings(".btn-content").slideDown(50);
					$(this).addClass("open");
				}
				e.stopPropagation();
			});
			btnContent.on("click",function(){
				$(this).hide();
				bool = false;
			});
			$("html").on("click",function(e){
					$(".btn-content").hide();
					$(".btn-menu").removeClass("open");
			})
		})
})()
