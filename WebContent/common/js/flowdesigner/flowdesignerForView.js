function Designer(container){
	var designer = $("#"+container);
	var nodes = new Array();//节点集合
	
	var fillColor = "#5c96bc";	
	jsPlumb.Defaults.Connector = [ "Straight", { curviness:20 } ];
	jsPlumb.Defaults.DragOptions = { cursor: "pointer", zIndex:0 };
	jsPlumb.Defaults.PaintStyle = { strokeStyle:fillColor, lineWidth:1.5 };
	jsPlumb.Defaults.EndpointStyle = { radius:1, fillStyle:fillColor };
	jsPlumb.Defaults.ConnectionOverlays = [[ "Arrow", { 
		location:-3,
		id:"arrow",
        length:10,
        foldback:0.5
	} ],[ "Label", { location:0.2,id:"label", cssClass:"aLabel" }]];
    
    var anchors = [[ "Perimeter", { anchorCount:25, shape:"Rectangle"}]];
	var aConnection = {	
		anchor:anchors
	};		
	
	//添加节点附加方法
	var addNodeExtraMethod = function(node){
		jsPlumb.draggable(node);
		node[0].nextnodes = new Array();
		node[0].prevnodes = new Array();
		node.addNextNode=function(n){
			var hasNext=false;
			var hasPrev=false;
			var obj = $(this);
			var nextnodes = obj[0].nextnodes;
			var prevnodes = n[0].prevnodes;
			for(var i=0;i<nextnodes.length;i++){
				if(nextnodes[i].attr("id")==n.attr("id")){
					hasNext = true;
					break;
				}
			}
			for(var i=0;i<prevnodes.length;i++){
				if(prevnodes[i].attr("id")==obj.attr("id")){
					hasPrev = true;
					break;
				}
			}
			if(!hasNext){
				nextnodes.push(n);
			}
			if(!hasPrev){
				prevnodes.push(obj);
			}
			
		};
		node.getNextNodes=function(){
			var obj = $(this);
			var nextnodes = obj[0].nextnodes;
			return nextnodes;
		}
		node.getPrevNodes=function(){
			var obj = $(this);
			var prevnodes = obj[0].prevnodes;
			return prevnodes;
		}
	}
	
	/**
	 * 绘制开始节点
	 */
	this.drawStart=function(x,y,info){
		var node = $("<div node start noWrap class=\"node start_node\" style='background:"+info.background+"'><img src=\""+contextPath+"/common/images/workflow/start_node.png\" /> ("+info.sortNo+")<br/>"+info.prcsName+"</div>");
		if(info.curFlowPrcsId == info.sid && !info.isEnd){
			node = $("<div node start noWrap class=\"node start_node\" style='background:#ffb3b3;'><img src=\""+contextPath+"/common/images/workflow/start_node.png\" /> ("+info.sortNo+")<br/>"+info.prcsName+"</div>");
		}
		node.attr("id",info.sid).attr("title",info.title);
		node.data("data",info);
		node.css({left:x,top:y});
		node.appendTo(designer);
		addNodeExtraMethod(node);
		nodes.push(node);
		return node;
	}
	
	this.drawEnd=function(x,y,info){
		var node = $("<div node end noWrap class=\"node end_node\" style='background:"+info.background+"'><img src=\""+contextPath+"/common/images/workflow/end_node.png\" /><br/>"+info.prcsName+"</div>");
		if(info.isEnd){
			node = $("<div node end noWrap class=\"node end_node\" style='background:#ffb3b3;'><img src=\""+contextPath+"/common/images/workflow/end_node.png\" /><br/>"+info.prcsName+"</div>");
		}
		node.attr("id",info.sid).attr("title",info.title);
		node.data("data",info);
		node.css({left:x,top:y});
		node.appendTo(designer);
		addNodeExtraMethod(node);
		nodes.push(node);
		return node;
	}
	
	this.drawSimpleNode=function(x,y,info){
		var node = $("<div node simple noWrap class=\"node simple_node\" style='background:"+info.background+"'><img src=\""+contextPath+"/common/images/workflow/simple_node.png\" /> ("+info.sortNo+")<br/>"+info.prcsName+"</div>");
		if(info.curFlowPrcsId == info.sid && !info.isEnd){
			node = $("<div node simple noWrap class=\"node simple_node\" style='background:#ffb3b3;'><img src=\""+contextPath+"/common/images/workflow/simple_node.png\" /> ("+info.sortNo+")<br/>"+info.prcsName+"</div>");
		}
		node.attr("id",info.sid).attr("title",info.title);
		node.data("data",info);
		node.css({left:x,top:y});
		node.appendTo(designer);
		addNodeExtraMethod(node);
		nodes.push(node);
		return node;
	}
	
	this.drawSoftNode=function(x,y,info){
		var node = $("<div node simple noWrap class=\"node soft_node\" style='background:"+info.background+"'><img src=\""+contextPath+"/common/images/workflow/soft_node.png\" /> ("+info.sortNo+")<br/>"+info.prcsName+"</div>");
		if(info.curFlowPrcsId == info.sid && !info.isEnd){
			node = $("<div node simple noWrap class=\"node soft_node\" style='background:#ffb3b3;'><img src=\""+contextPath+"/common/images/workflow/soft_node.png\" /> ("+info.sortNo+")<br/>"+info.prcsName+"</div>");
		}
		node.attr("id",info.sid).attr("title",info.title);
		node.data("data",info);
		node.css({left:x,top:y});
		node.appendTo(designer);
		addNodeExtraMethod(node);
		nodes.push(node);
		return node;
	}
	
	this.drawAggregationNode = function(x,y,info){
		var node = $("<div node aggregation noWrap class=\"node aggregation_node\" style='background:"+info.background+"'><img src=\""+contextPath+"/common/images/workflow/aggregation_node.png\" /> ("+info.sortNo+")<br/>"+info.prcsName+"</div>");
		if(info.curFlowPrcsId == info.sid && !info.isEnd){
			node =$("<div node aggregation noWrap class=\"node aggregation_node\" style='background:#ffb3b3;'><img src=\""+contextPath+"/common/images/workflow/aggregation_node.png\" /> ("+info.sortNo+")<br/>"+info.prcsName+"</div>");
		}
		node.attr("id",info.sid).attr("title",info.title);
		node.data("data",info);
		node.css({left:x,top:y});
		node.appendTo(designer);
		addNodeExtraMethod(node);
		nodes.push(node);
		return node;
	}
	
	this.drawParallelNode = function(x,y,info){
		var node = $("<div  node parallel noWrap class=\"node parallel_node\" style='background:"+info.background+"'><img src=\""+contextPath+"/common/images/workflow/parallel_node.png\" /> ("+info.sortNo+")<br/>"+info.prcsName+"</div>");
		if(info.curFlowPrcsId == info.sid && !info.isEnd){
			 $("<div  node parallel noWrap class=\"node parallel_node\" style='background:#ffb3b3;'><img src=\""+contextPath+"/common/images/workflow/parallel_node.png\" /> ("+info.sortNo+")<br/>"+info.prcsName+"</div>");
		}
		node.attr("id",info.sid).attr("title",info.title);
		node.data("data",info);
		node.css({left:x,top:y});
		node.appendTo(designer);
		addNodeExtraMethod(node);
		nodes.push(node);
		return node;
	}
	
	this.drawChildNode = function(x,y,info){
		
		var node = $("<div  node child noWrap class=\"node child_node\" style='background:"+info.background+"'><img src=\""+contextPath+"/common/images/workflow/child_node.png\" /> ("+info.sortNo+")<br/>"+info.prcsName+"</div>");
		if(info.curFlowPrcsId == info.sid && !info.isEnd){
			$("<div  node child noWrap class=\"node child_node\" style='background:#ffb3b3;'><img src=\""+contextPath+"/common/images/workflow/child_node.png\" /> ("+info.sortNo+")<br/>"+info.prcsName+"</div>");
		}
		node.attr("id",info.sid).attr("title",info.title);
		node.data("data",info);
		node.css({left:x,top:y});
		node.appendTo(designer);
		addNodeExtraMethod(node);
		nodes.push(node);
		return node;
	}
	
	this.doLineTo=function(){
		for(var i=0;i<nodes.length;i++){
			var curNode = nodes[i];
			var nextNodes = curNode.getNextNodes();
			var info = curNode.data("data");
			if(!info){
				continue;
			}
			
			var conditionModel = new Array();
			if(info.conditionModel){
				conditionModel = eval("("+info.conditionModel+")");
			}
			
			for(var j=0;j<nextNodes.length;j++){
				var connect = jsPlumb.connect({source:curNode, target:nextNodes[j]},aConnection);
				//查找标签元素
				var label = "";
				for(var h=0;h<conditionModel.length;h++){
					try{
						if((conditionModel[h].prcsTo+"")==(""+nextNodes[j].data("data").prcsId)){
							label = conditionModel[h].label;
							break;
						}
					}catch(e){
						break;
					}
				}
				if(!label){
					label = "";
				}
				//connect.getOverlay("label").setLabel(label);
			}
		}
	}
	
	//移除节点（同时也移除连线规则）
	this.removeNode=function(node){
		var id = node.attr("id");
		
		//删除所有节点关联该子节点的关系
		for(var i=0;i<nodes.length;i++){
			var tmp = nodes[i];
			var nextnodes = tmp[0].nextnodes;
			for(var j=0;j<nextnodes.length;j++){
				if(nextnodes[j].attr("id")==id){
					tmp[0].nextnodes.splice(j,1);
					break;
				}
			}
			if(tmp.attr("id")==id){
				nodes.splice(i,1);
			}
		}
		
		//删除所有节点关联该该节点的父节点关系
		
		for(var i=0;i<nodes.length;i++){
			var tmp = nodes[i];
			var prevnodes = tmp[0].prevnodes;
			for(var j=0;j<prevnodes.length;j++){
				if(prevnodes[j].attr("id")==id){
					tmp[0].prevnodes.splice(j,1);
					break;
				}
			}
		}
		
		node.remove();
	}
}
(function(){
	$.fn.addNextNode=function(n){
		var hasNext=false;
		var hasPrev=false;
		var obj = $(this);
		var nextnodes = obj[0].nextnodes;
		var prevnodes = n[0].prevnodes;
		for(var i=0;i<nextnodes.length;i++){
			if(nextnodes[i].attr("id")==n.attr("id")){
				hasNext = true;
				break;
			}
		}
		for(var i=0;i<prevnodes.length;i++){
			if(prevnodes[i].attr("id")==obj.attr("id")){
				hasPrev = true;
				break;
			}
		}
		if(!hasNext){
			nextnodes.push(n);
		}
		if(!hasPrev){
			prevnodes.push(obj);
		}
	}
	
	$.fn.getNextNodes=function(n){
		var obj = $(this);
		var nextnodes = obj[0].nextnodes;
		return nextnodes;
	}
	
	$.fn.getPrevNodes=function(){
		var obj = $(this);
		var prevnodes = obj[0].prevnodes;
		return prevnodes;
	}
})();