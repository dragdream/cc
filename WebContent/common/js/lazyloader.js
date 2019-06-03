(function(){
	/*
	{
		url:'',
		placeHolder:'',
		contentHolder:'',
		queryParam:{},
		pageSize:15,
		rowRender:function(){}
		
	}
	*/
	window.lazyLoader=function(settings){
		var page = 1;//页号
		var placeHolder = $("#"+settings.placeHolder);
		var contentHolder = $("#"+settings.contentHolder);
		var cur = this;
		if(!settings.pageSize){
			settings.pageSize = 10;
		}
		
		this.getTotal = function(){
			var json = tools.requestJsonRs(settings.url,{page:1,rows:1});
			return json.total;
		}
		
		this.load = load = function(beforeLoaded){
			var params = {rows:settings.pageSize,page:page++};
			if(settings.queryParam){
				for(var key in settings.queryParam){
					params[key] = settings.queryParam[key];
				}
			}
			$.ajax({ 
				url: settings.url,
				data:params,
				async:true,
				type:"POST",
				dataType:"html",
				success: function(json){
					if(beforeLoaded){
						beforeLoaded();
					}
					json = eval("("+json+")");
					var rows = json.rows;
					if(rows.length!=0){
						for(var i=0;i<rows.length;i++){
							contentHolder.append(settings.rowRender(rows[i]));
						}
					}
					settings.onLoadSuccess();
					//无数据回调函数
					if(rows.length==0 || settings.pageSize>=json.total){
						if(settings.onNoData){
							settings.onNoData();
						}
					}
		      	}
			});
		}
		
		this.reload=function(params){
			settings.queryParam = params;
			try{
				contentHolder.find("[id!="+settings.placeHolder+"]").each(function(i,obj){
					$(obj).remove();
				});
			}catch(e){
				
			}
			
			page = 1;
			
			load(function(){
				contentHolder.html("");
			});
		}
		
		//注册更多按钮的事件
		placeHolder.click(function(){
			cur.load();
		});
		
		this.load();//加载
	}
	
})();