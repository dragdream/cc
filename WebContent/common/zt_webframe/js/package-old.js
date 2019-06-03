	(function($){
		$.fn.datagrid=function(config){
			var datagridTable = this;
			$(datagridTable).css({"margin":"0 20px"});
			//先渲染指定field和title的表头
			var tableHeader = ["<thead><tr>"];
			for(var i=0;i<config.columns.length;i++){
				tableHeader.push("<td>"+config.columns[i]["title"]+"</td>");
			}
			tableHeader.push("</tr></thead>");

			//将表头加入到table中
			datagridTable.append(tableHeader.join(""));//把数组里的字符串拼接在一起

			//ajax请求后台数据
			$.ajax({
				type:"post",
				dataType:"json",
				url:config.url,
				data:config.queryParams,
				success:function(returnData){//这里的returnData是formatter传过来的数据
					var bodyHtml = [];
						bodyHtml.push("<tbody>");
					for(var i=0;i<returnData.rows.length;i++){
						bodyHtml.push("<tr>");
						for(var j=0;j<config.columns.length;j++){
							bodyHtml.push("<td>");
							if(config.columns[j].formatter){
								//这里是调用formatter后的返回值来push
								bodyHtml.push(config.columns[j].formatter(returnData.rows[i][config.columns[j].field],returnData.rows[i],i));
							}else{
								bodyHtml.push(returnData.rows[i][config.columns[j].field]);
							}
							bodyHtml.push("</td>");
						}
						bodyHtml.push("</tr>");
					}
						bodyHtml.push("</tbody>");

					$(datagridTable).append(bodyHtml.join(""));					//如果成功的话，执行onLoadSuccess
					if(config.onLoadSuccess){
							config.onLoadSuccess();
					}

					//分页
					if(config.multiSelect){
						var allCheckbox = "<td style=\"text-align: center;width: 35px;padding: 10px;\"><input class = \"allCheck\" style = \"margin:0 auto;\" type=\"checkbox\"></td>";
						var multiCheck = "<td style=\"text-align: center;width: 35px;padding: 10px;\"><input class = \"multiCheck\" style = \"margin:0 auto;\" type=\"checkbox\"></td>";

						$('thead tr').prepend(allCheckbox);
						$('tbody tr').prepend(multiCheck);

						$(".allCheck").change(function(){
							if($(this).prop("checked")){
								$(".multiCheck").attr("checked",true);
							}else{
								$(".multiCheck").attr("checked",false);
							}
					});

				};
			},
				error:function(data){
					alert('获取数据失败！')
				}
			});

		}
		//tab页签
		$.fn.tab=function(data,tab){
			var tabContainer = this;

		}

		//列宽手动调整
		$.fn.tableresize = function () {
		   var _document = $("body");
		   $(this).each(function () {
		     if (!$.tableresize) {
		       $.tableresize = {};
		     }
		     var _table = $(this);
		     //设定ID
		     var id = _table.attr("id") || "tableresize_" + (Math.random() * 100000).toFixed(0).toString();
		     var tr = _table.find("tr").first(), ths = tr.children(), _firstth = ths.first();
		     //设定临时变量存放对象
		    var cobjs = $.tableresize[id] = {};
		     cobjs._currentObj = null, cobjs._currentLeft = null;
		     ths.mousemove(function (e) {
		       var _this = $(this);
		       var left = _this.offset().left,
		           top = _this.offset().top,
		           width = _this.width(),
		           height = _this.height(),
		           right = left + width,
		           bottom = top + height,
		           clientX = e.clientX,
		           clientY = e.clientY;
		       var leftside = !_firstth.is(_this) && Math.abs(left - clientX) <= 5,
		           rightside = Math.abs(right - clientX) <= 5;
		       if (cobjs._currentLeft||clientY>top&&clientY<bottom&&(leftside||rightside)){
		         _document.css("cursor", "e-resize");
		         if (!cobjs._currentLeft) {
		           if (leftside) {
		             cobjs._currentObj = _this.prev();
		           }
		           else {
		             cobjs._currentObj = _this;
		           }
		         }
		       }
		       else {
		         cobjs._currentObj = null;
		       }
		     });
		     ths.mouseout(function (e) {
		       if (!cobjs._currentLeft) {
		         cobjs._currentObj = null;
		         _document.css("cursor", "auto");
		       }
		     });
		     _document.mousedown(function (e) {
		       if (cobjs._currentObj) {
		         cobjs._currentLeft = e.clientX;
		       }
		       else {
		         cobjs._currentLeft = null;
		       }
		     });
		     _document.mouseup(function (e) {
		       if (cobjs._currentLeft) {
		         cobjs._currentObj.width(cobjs._currentObj.width() + (e.clientX - cobjs._currentLeft));
		       }
		       cobjs._currentObj = null;
		       cobjs._currentLeft = null;
		       _document.css("cursor", "auto");
		     });
		   });
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
		    _html += '<a id="win_ico">X</a><div id="win_msg">' + '<img id = "win_img" src="imgs/question.png" />&nbsp' + msg + '</div><div id="win_btnbox">';
		    _html += '<input id="win_check" type="checkbox" /> 不再提示';
		    if (type == "alert") {
		        _html += '<input id="win_btn_ok" type="button" value="确定" />';
		    }
		    if (type == "confirm") {
		        _html += '<input id="win_btn_ok" type="button" value="确定" />';
		        _html += '<input id="win_btn_no" type="button" value="取消" />';
		    }
		    _html += '</div></div>';
		    //必须先将_html添加到body，再设置Css样式
		    $("body").append(_html); GenerateCss();
		}
		//生成Css
		var GenerateCss = function () {
		    $("#win_box").css({ width: '100%', height: '100%', zIndex: '99999', position: 'fixed',
		        filter: 'Alpha(opacity=60)', backgroundColor: 'black', top: '0', left: '0', opacity: '0.6'
		    });
		    $("#win_con").css({ zIndex: '999999', width: '350px', position: 'fixed',
		        backgroundColor: '#fff',
		    });
		    $("#win_tit").css({ width:'320px',height:'10px',display: 'block', fontSize: '14px', color: '#fff', padding: '10px 15px',backgroundColor: '#6a9ee8',borderBottom: '3px solid #009BFE',
		    });
		    $("#win_msg").css({ padding: '20px', lineHeight: '20px',fontSize: '13px'
		    });
		    $("#win_img").css({ marginRight:'10px'
		    });
		    $("#win_ico").css({ display: 'block', position: 'absolute', right: '10px', top: '9px',
		        border: 'none', width: '18px', height: '18px', textAlign: 'center',
		        lineHeight: '16px', cursor: 'pointer', fontFamily: '微软雅黑',color:"#fff"
		    });
		    $("#win_btnbox").css({ height:"20px" ,padding:"9px 0" ,textAlign: 'center',fontSize:"12px",backgroundColor:"#ebebeb"});
		    $("#win_check").css({backgroundColor:"#fff",verticalAlign: 'top'})
		    $("#win_btn_ok,#win_btn_no").css({ width: '60px', height: '20px', color: '#000'});
		    $("#win_btn_ok").css({ backgroundColor: '#fff', border:"1px solid #6a9ee8",marginLeft:"80px"});
		    $("#win_btn_no").css({ backgroundColor: '#fff',border:"1px solid #959595", marginLeft: '20px' });
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





	})(jQuery);

//表格的排序功能
	function TableSorter(table)
	{
	    this.Table = this.$(table);
	    if(this.Table.rows.length <= 1)
	    {
	        return;
	    }
	    var args = [];
	    if(arguments.length > 1)
	    {
	        for(var x = 1; x < arguments.length; x++)
	        {
	            args.push(arguments[x]);
	        }
	    }
	    this.Init(args);
	}

	TableSorter.prototype = {
	    $ : function(element)//简写document.getElementById
	    {
	        return document.getElementById(element);
	    },
	    Init : function(args)//初始化表格的信息和操作
	    {
	        this.Rows = [];
	        this.Header = [];
	        this.ViewState = [];
	        this.LastSorted = null;
	        this.NormalCss = "NormalCss";
	        this.SortAscCss = "SortAscCss";
	        this.SortDescCss = "SortDescCss";
	        for(var x = 0; x < this.Table.rows.length; x++)
	        {
	            this.Rows.push(this.Table.rows[x]);
	        }
	        this.Header = this.Rows.shift().cells;
	        for(var x = 0; x < (args.length ? args.length : this.Header.length); x++)
	        {
	            var rowIndex = args.length ? args[x] : x;
	            if(rowIndex >= this.Header.length)
	            {
	                continue;
	            }
	            this.ViewState[rowIndex] = false;
	            this.Header[rowIndex].style.cursor = "pointer";
	            this.Header[rowIndex].onclick = this.GetFunction(this, "Sort", rowIndex);
	        }
	    },
	    GetFunction : function(variable,method,param)//取得指定对象的指定方法.
	    {
	        return function()
	        {
	            variable[method](param);
	        }
	    },
	    Sort : function(column)//执行排序.
	    {
	        if(this.LastSorted)
	        {
	            this.LastSorted.className = this.NormalCss;
	        }
	        var SortAsNumber = true;
	        for(var x = 0; x < this.Rows.length && SortAsNumber; x++)
	        {
	            SortAsNumber = this.IsNumeric(this.Rows[x].cells[column].innerHTML);
	        }
	        this.Rows.sort(
	        function(row1, row2)
	        {
	            var result;
	            var value1,value2;
	            value1 = row1.cells[column].innerHTML;
	            value2 = row2.cells[column].innerHTML;
	            if(value1 == value2)
	            {
	                return 0;
	            }
	            if(SortAsNumber)
	            {
	                result = parseFloat(value1) > parseFloat(value2);
	            }
	            else
	            {
	                result = value1 > value2;
	            }
	            result = result ? 1 : -1;
	            return result;
	        })
	        if(this.ViewState[column])
	        {
	            this.Rows.reverse();
	            this.ViewState[column] = false;
	            this.Header[column].className = this.SortDescCss;
	        }
	        else
	        {
	            this.ViewState[column] = true;
	            this.Header[column].className = this.SortAscCss;
	        }
	        this.LastSorted = this.Header[column];
	        var frag = document.createDocumentFragment();
	        for(var x = 0; x < this.Rows.length; x++)
	        {
	            frag.appendChild(this.Rows[x]);
	        }
	        this.Table.tBodies[0].appendChild(frag);
	        this.OnSorted(this.Header[column], this.ViewState[column]);
	    },
	    IsNumeric : function(num)//验证是否是数字类型.
	    {
	        return /^\d+(\.\d+)?$/.test(num);
	    },
	    OnSorted : function(cell, IsAsc)//排序完后执行的方法.cell:执行排序列的表头单元格,IsAsc:是否为升序排序.
	    {
	        return;
	    }
	}


//分页功能
	;(
		function($){
			$.extend({
					"easypage":function(options){
						options = $.extend({//参数设置
							contentclass:"contentlist",//要显示的内容的class
							navigateid:"navigatediv",//导航按钮所在的dom的id
							everycount:"5",//每页显示多少个
							navigatecount:"5"//导航按钮一次显示多少个
							},
							options);
						var currentpage = 0;//当前页
						var contents = $("."+options.contentclass);//要显示的内容
						var contentcount = contents.length;//得到内容的个数
						var pagecount = Math.ceil(contentcount/options.everycount);//计算出页数
						//拼接导航按钮
						var navigatehtml = "<div id='pagefirst' class='pagefirst'><a href='javascript:void(0)'>首页</a></div><div id='pagepre' class='pagepre'><a href='javascript:void(0)'>上一页</a></div>";
						for(var i = 1;i <= pagecount;i++){
								navigatehtml+='<div class="pagenavigate"><a href="javascript:void(0)">'+i+'</a></div>';
						}
						navigatehtml+="<div id='pagenext' class='pagenext'><a href='javascript:void(0)'>下一页</a></div><div id='pagelast' class='pagelast'><a href='javascript:void(0)'>尾页</a></div>";

						//加载导航按钮
						$("#"+options.navigateid).html(navigatehtml);

						//得到所有按钮
						var navigates = $(".pagenavigate");

						//隐藏所有的导航按钮
						$.extend({
							"hidenavigates":function(){
								navigates.each(function(){
									$(this).hide();
								})
							}
						});

						//显示导航按钮
						$.extend({
							"shownavigate":function(currentnavigate){
								$.hidenavigates();
								var begin = currentnavigate>=options.navigatecount?currentnavigate-parseInt(options.navigatecount):0;
								if(begin>navigates.length-2*options.navigatecount){
									begin = navigates.length-2*options.navigatecount;
								}
								for(var i = begin;i < currentnavigate+parseInt(options.navigatecount);i++){
									$(navigates[i]).show();
								}
							}
						});

						//高亮显示某个按钮
						$.extend({
							"lightnavigate":function(currentnavigate){
								currentnavigate.addClass("pagenavigateon");
							}
						});

						//移除所有高亮按钮
						$.extend({
							"removelight":function(){
								$(".pagenavigateon").each(
									function(){
										$(this).removeClass("pagenavigateon");
									}
								)
							 }
						});

						//显示某页的内容
						$.extend({
							"showPage":function(page){
								contents.each(
									function(contentindex){
										if(contentindex>=page*options.everycount && contentindex < (page+1)*options.everycount){
										$(this).show();
										}else{
										$(this).hide();
										}
									}
								);
							}
						});

						//隐藏前进后退按钮
						$.extend({
							"hidePreNext":function(page){
									if(page==pagecount-1){
										$("#pagenext").hide();
										$("#pagelast").hide();
										$("#pagepre").show();
										$("#pagefirst").show();
									}else if(page==0){
										$("#pagepre").hide();
										$("#pagefirst").hide();
										$("#pagenext").show();
										$("#pagelast").show();
									}else{
										$("#pagenext").show();
										$("#pagepre").show();
										$("#pagefirst").show();
										$("#pagelast").show();
									}
							}
						});
						//显示指定的导航按钮
						$.shownavigate(0);
						//隐藏所有的内容
						$.showPage(0);
						//开始时隐藏后退按钮
						$.hidePreNext(0);
						//高亮显示第一个按钮
						if(pagecount>0){
								$.lightnavigate($(navigates.get(0)));
						}
						//点击导航按钮
						$(".pagenavigate").each(
							function(myindex){
								$(this).click(
									function(){
										$.showPage(myindex);
										$.removelight();
										$.lightnavigate($(this));
										currentpage = myindex;
										$.hidePreNext(currentpage);
										var na = Math.floor((currentpage+1)/options.navigatecount)*options.navigatecount;
										$.shownavigate(na);
									}
								);
							}
						);
						//点击后退按钮
						$("#pagepre").click(
							function(){
								--currentpage<=0 && (currentpage=0);
								$.showPage(currentpage);
								$.removelight();
								$.lightnavigate($(navigates.get(currentpage)));
								$.hidePreNext(currentpage);
								var na = Math.floor(currentpage/options.navigatecount)*options.navigatecount;
								$.shownavigate(na);
							}
						);
						//点击前进按钮
						$("#pagenext").click(
							function(){
								++currentpage>=pagecount-1 && (currentpage=pagecount-1);
								$.showPage(currentpage);
								$.removelight();
								$.lightnavigate($(navigates.get(currentpage)));
								$.hidePreNext(currentpage);

								var na = Math.floor((currentpage+1)/options.navigatecount)*options.navigatecount;
								$.shownavigate(na);
							}
						);
						//点击首页按钮
						$("#pagefirst").click(
							function(){
								currentpage=0;
								$.showPage(currentpage);
								$.removelight();
								$.lightnavigate($(navigates.get(currentpage)));
								$.hidePreNext(currentpage);

								var na = Math.floor((currentpage+1)/options.navigatecount)*options.navigatecount;
								$.shownavigate(na);
							}
						);
					 //点击尾页按钮
					 $("#pagelast").click(
					 		function(){
					 			currentpage=pagecount-1;
					 			$.showPage(currentpage);
								$.removelight();
								$.lightnavigate($(navigates.get(currentpage)));
								$.hidePreNext(currentpage);

								var na = Math.floor((currentpage+1)/options.navigatecount)*options.navigatecount;
								$.shownavigate(na);
					 		}
					 );
					}
			});
		}
	)(jQuery)