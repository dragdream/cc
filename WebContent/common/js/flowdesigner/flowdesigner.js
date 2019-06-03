/**
 * 流程设计器
 */
(function(){
	Raphael.fn.arrowLine = function(points,color){
		var path = "";
		for(var i=0;i<points.length;i++){
			var axis = points[i];
			if(i==0){
				path+="M"+axis[0]+","+axis[1];
			}else{
				path+="L"+axis[0]+","+axis[1];
			}
		}
		if(points.length>=2){
			var x1 = points[points.length-1][0];
			var y1 = points[points.length-1][1];
			var x2 = points[points.length-2][0];
			var y2 = points[points.length-2][1];
			
			var v_x = x1-x2;//向量x
			var v_y = y1-y2;//向量y
			var d = (v_y)/(v_x);//直线斜率
			var d1 = (-1)/d;//垂直线斜率
			
			var a = 7;
			var innerArrow = this.path("M"+x1+","+y1+"L"+(x1-a/Math.sqrt(3))+","+(y1-a)+"L"+(x1+a/Math.sqrt(3))+","+(y1-a)+"L"+x1+","+y1);
			innerArrow.attr("fill",color);
			//document.title = v_x+" "+v_y+" "+(90+Math.abs(Math.atan( d)*180/Math.PI));
			if(v_x>0 && v_y>0){//第一象限
				innerArrow.rotate(-(90-Math.abs(Math.atan(d)*180/Math.PI)),x1,y1);
			}else if(v_x<0 && v_y>0){//第二象限
				innerArrow.rotate((90-Math.abs(Math.atan(d)*180/Math.PI)),x1,y1);
			}else if(v_x<0 && v_y<0){//第三象限
				innerArrow.rotate((90+Math.abs(Math.atan(d)*180/Math.PI)),x1,y1);
			}else if(v_x>0 && v_y<0){//第四象限
				innerArrow.rotate(-(90+Math.abs(Math.atan(d)*180/Math.PI)),x1,y1);
			}else if(v_x>0 && v_y==0){//x轴正方向
				innerArrow.rotate(-90,x1,y1);
			}else if(v_x<0 && v_y==0){//x轴负方向
				innerArrow.rotate(90,x1,y1);
			}else if(v_x==0 && v_y>0){//y轴正方向
				//innerArrow.rotate(180,x1,y1);
			}else if(v_x==0 && v_y<0){//y轴负方向
				innerArrow.rotate(180,x1,y1);
			}
		}
		var arrowLine = this.path(path);
		arrowLine.attr("stroke",color);
		return arrowLine;
	}

	var dragObj;
	var startDrag=false;
	window.FlowDesigner = function(paper){
		var container = $(paper.canvas).parent();//设置容器
		var nodes = new Array();//节点集合
		var movedListener;//全局移动监听器

		$(container).mousemove(function(event){
			var ctn = $(this);
			if(startDrag){
				if(dragObj){
					var offsetX = parseInt(dragObj.attr("offsetX"));
					var offsetY = parseInt(dragObj.attr("offsetY"));
					var left = event.pageX-$(container).position().left-offsetX;
					var top = event.pageY-$(container).position().top-offsetY;
					/**
					if(left<0){
						left = 0;
					}
					if(left+parseInt(dragObj.width())>parseInt(ctn.width())){
						left = parseInt(ctn.width())-parseInt(dragObj.width());
					}
					if(top<0){
						top = 0;
					}
					if(top+parseInt(dragObj.height())>parseInt(ctn.height())){
						top = parseInt(ctn.height())-parseInt(dragObj.height());
					}
					**/
					dragObj.css({left:left,top:top});

					if(movedListener){
						movedListener(nodes,event);
					}
				}
			}
		});

		//绘制开始节点
		this.drawStart = function(x,y,id){
			var html = "<div node start class='flow-start' id='"+id+"'></div>";
			var node = $(html).appendTo($(container)).css({left:x,top:y});
			addNodeExtraMethod(node);
			addMouseListener(node);
			nodes.push(node);
			return node;
		}

		//绘制结束节点
		this.drawEnd = function(x,y,id){
			var html = "<div node end class='flow-end' id='"+id+"'></div>";
			var node = $(html).appendTo($(container)).css({left:x,top:y});
			addNodeExtraMethod(node);
			addMouseListener(node);
			nodes.push(node);
			return node;
		}

		//绘制普通节点
		this.drawSimpleNode = function(x,y,id,title,content){
			var html = "<table node simple class='flow-simple-node-frame' id='"+id+"'>";
			html+="<tr class='flow-simple-node-title'><td>"+title+"</td></tr>";
			html+="<tr class='flow-simple-node-content'><td>"+content+"</td></tr>";
			html+="</table>";
			var node = $(html).appendTo($(container)).css({left:x,top:y});
			addNodeExtraMethod(node);
			addMouseListener(node);
			nodes.push(node);
			return node;
		}
		
		//绘制聚合节点
		this.drawAggregationNode = function(x,y,id,title,content){
			var html = "<table node aggregation class='flow-aggregation-node-frame' id='"+id+"'>";
			html+="<tr class='flow-aggregation-node-title'><td>"+title+"</td></tr>";
			html+="<tr class='flow-aggregation-node-content'><td>"+content+"</td></tr>";
			html+="</table>";
			var node = $(html).appendTo($(container)).css({left:x,top:y});
			addNodeExtraMethod(node);
			addMouseListener(node);
			nodes.push(node);
			return node;
		}
		
		//绘制并发节点
		this.drawParallelNode = function(x,y,id,title,content){
			var html = "<table node parallel class='flow-parallel-node-frame' id='"+id+"'>";
			html+="<tr class='flow-parallel-node-title'><td>"+title+"</td></tr>";
			html+="<tr class='flow-parallel-node-content'><td>"+content+"</td></tr>";
			html+="</table>";
			var node = $(html).appendTo($(container)).css({left:x,top:y});
			addNodeExtraMethod(node);
			addMouseListener(node);
			nodes.push(node);
			return node;
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
		
		//添加节点移动监听事件
		var addMouseListener=function(node){
			node.mousedown(function(event){
				node.attr("offsetX",event.pageX-$(container).position().left-node.position().left);
				node.attr("offsetY",event.pageY-$(container).position().top-node.position().top);
				dragObj = node;
				startDrag = true;
			}).mouseup(function(){
				dragObj=undefined;
				startDrag = false;
			});
		}
		
		//添加节点附加方法
		var addNodeExtraMethod = function(node){
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

		this.setMovedListener=function(listener){
			movedListener = listener;
		}
		
		/**
		 * 连接线
		 * @param from
		 * @param to
		 * @return
		 */
		this.lineTo=function(from,to){
			var f_left = from.position().left;
			var f_top = from.position().top;
			var f_height = from.height();
			var f_width = from.width();
			var f_cx = f_left+f_width/2;
			var f_cy = f_top+f_height/2;

			var t_left = to.position().left;
			var t_top = to.position().top;
			var t_height = to.height();
			var t_width = to.width();
			var t_cx = t_left+t_width/2;
			var t_cy = t_top+t_height/2;
			
			//左侧竖线函数
			var x1=t_left;
			//右侧竖线函数
			var x2=t_left+t_width;
			//上侧横线函数
			var y1=t_top;
			//下侧横线函数
			var y2=t_top+t_height;
			
			var A = t_cy-f_cy;
			var B = t_cx-f_cx;
			var C = f_cy*B-f_cx*A;
			
			var axisLeft = [x1,(x1*A+C)/B];//左侧竖线焦点
			var axisRight = [x2,(x2*A+C)/B];//右侧竖线焦点
			var axisTop = [(y1*B-C)/A,y1];//上侧横线焦点
			var axisBottom = [(y2*B-C)/A,y2];//下侧横线焦点
			
			var axisArray = new Array();
			if(axisLeft[1]>=t_top && axisLeft[1]<=t_top+t_height){
				axisArray.push(axisLeft);
			}
			if(axisRight[1]>=t_top && axisRight[1]<=t_top+t_height){
				axisArray.push(axisRight);
			}
			if(axisTop[0]>=t_left && axisTop[0]<=t_left+t_width){
				axisArray.push(axisTop);
			}
			if(axisBottom[0]>=t_left && axisBottom[0]<=t_left+t_width){
				axisArray.push(axisBottom);
			}
			
			var minDistanceAxis;//最小距离焦点
			var minDistance=100000;//最小距离
			for(var i=0;i<axisArray.length;i++){
				var axis = axisArray[i];
				var tmpDistance = Math.sqrt(Math.pow((axis[0]-f_cx), 2)+Math.pow((axis[1]-f_cy),2));
				if(i==0){
					minDistanceAxis = axis;
					minDistance = tmpDistance;
				}else{
					if(minDistance>tmpDistance){
						minDistance = tmpDistance;
						minDistanceAxis = axis;
					}
				}
			}
			
			paper.arrowLine([[f_cx,f_cy],minDistanceAxis],"#434343");
			
		}
		
		//绘制连线
		this.doLineTo=function(){
			for(var i=0;i<nodes.length;i++){
				var curNode = nodes[i];
				var nextNodes = curNode.getNextNodes();
				for(var j=0;j<nextNodes.length;j++){
					this.lineTo(curNode,nextNodes[j]);
				}
			}
		}
		
		//重绘
		this.repaint = function(){
			paper.clear();
		}
	}
	
	
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