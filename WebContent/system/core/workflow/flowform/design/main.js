// polyfill for trim()
if (!String.prototype.trim) {
  String.prototype.trim = function () {
    return this.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
  };
}

(function() {
    // some globals
    var formId = "";
    var versionFormId = "";

    if(location.search && location.search.substring(1).split('=')[0] == "version_form_id")
    {
         versionFormId =  location.search.substring(1).split('=')[1];
    }else
    {
         formId = location.search ? location.search.substring(1).split('=')[1] : '';
    }

    var debug = false;
    var hasModified = false;
    var hasEmptyGroup = false;
    var hasIllegeChar = false;
    window.formData = null;
    window.idHash = {};
    
    // when dom is ready
    $(function() {
    	var url = contextPath+"/flowForm/getFormItemsByFormGroup.action";
		var json = tools.requestJsonRs(url,{formId:formId2});
    	var data=json.rtData;
    	console.log(data);
	//	var data= [{"type":"group","name":"测试组","fields":[]},{"type":"group","name":"组1","fields":[{"name":"\u586b\u8868\u65e5\u671f","type":"macro","id":"DATA_243"},{"name":"\u59d3\u540d","type":"text","id":"DATA_244"},{"name":"\u90e8\u95e8","type":"macro","id":"DATA_245"},{"name":"\u804c\u52a1","type":"macro","id":"DATA_246"},{"name":"\u51fa\u751f\u65e5\u671f","type":"calendar","id":"OTHER_212"},{"name":"\u7c4d\u8d2f","type":"text","id":"DATA_247"},{"name":"\u5b66\u5386","type":"select","id":"DATA_248"},{"name":"\u8eab\u4efd\u8bc1","type":"text","id":"DATA_249"},{"name":"\u5165\u804c","type":"calendar","id":"OTHER_216"},{"name":"\u5de5\u53f7","type":"text","id":"DATA_250"},{"name":"\u624b\u673a","type":"text","id":"DATA_251"},{"name":"\u90ae\u7bb1","type":"text","id":"DATA_252"},{"name":"\u662f\u5426\u6709\u7167\u7247","type":"radio","id":"DATA_253"},{"name":"\u662f\u5426\u6709\u6863\u6848\u8868","type":"radio","id":"DATA_254"},{"name":"\u662f\u5426\u6709\u8eab\u4efd\u8bc1","type":"radio","id":"DATA_255"},{"name":"\u662f\u5426\u6709\u5b66\u4f4d\u8bc1","type":"radio","id":"DATA_256"},{"name":"\u5176\u4ed6\u8bc1\u4ef6","type":"text","id":"DATA_257"},{"name":"\u5206\u914d\u7535\u8bdd","type":"text","id":"DATA_258"},{"name":"\u4eba\u4e8b\u90e8\u610f\u89c1","type":"textarea","id":"DATA_259"},{"name":"\u4eba\u4e8b\u90e8\u7ecf\u529e\u4eba","type":"macro","id":"DATA_260"},{"name":"\u5206\u914d\u7535\u5b50\u90ae\u4ef6","type":"text","id":"DATA_261"},{"name":"\u5f00\u8bbeOA\u8d26\u53f7","type":"text","id":"DATA_262"},{"name":"\u5206\u914d\u7535\u8111","type":"select","id":"DATA_263"},{"name":"\u7cfb\u7edf\u610f\u89c1","type":"textarea","id":"DATA_264"},{"name":"\u7cfb\u7edf\u7ecf\u529e\u4eba","type":"macro","id":"DATA_265"},{"name":"\u6307\u7eb9\u8003\u52e4","type":"radio","id":"DATA_266"},{"name":"\u57fa\u672c\u529e\u516c\u7528\u54c1\u767b\u8bb0","type":"radio","id":"DATA_267"},{"name":"\u884c\u653f\u90e8\u610f\u89c1","type":"textarea","id":"DATA_268"},{"name":"\u884c\u653f\u7ecf\u529e\u4eba","type":"macro","id":"DATA_269"},{"name":"\u90e8\u95e8\u610f\u89c1","type":"textarea","id":"DATA_270"},{"name":"\u90e8\u95e8\u7ecf\u529e\u4eba","type":"macro","id":"DATA_271"},{"name":"\u603b\u7ecf\u7406\u610f\u89c1","type":"textarea","id":"DATA_272"},{"name":"\u603b\u7ecf\u7406\u7ecf\u529e\u4eba","type":"macro","id":"DATA_273"},{"name":"\u65b0\u5458\u5de5\u5efa\u8bae","type":"textarea","id":"DATA_274"},{"name":"\u65b0\u5458\u5de5\u7ecf\u529e\u4eba","type":"macro","id":"DATA_275"}]}];
    	//data=[{"type":"group","name":"dd","fields":[{"name":"ee","type":"macro","id":"DATA_243"}]}];
    	//console.info('getdata',data);
        //typeof data === 'string' ? data = JSON.parse(data) : data = data
        //如果数据不为空
        if (data !== null) {
            try {
                window.formdata = data;
            } catch (e) {
                debug && alert(e.message);
                return false;
            }
            
            
            //移除placeholder
            $('.mbui_group_list').find('.mbui_panel_form_tips').remove();
            
            $.each(data, function(index, item) {
            	var groupId=item.groupId;
                //定义每个组的容器和相应id
                var el = $('<div class="mbui_cells_group"><div class="mbui_cells_title"><input type="hidden"  value="'+groupId+'"/><span>组容器</span><img src="../img/xiala.png" onclick="toggleExtension(this);" /></div><div class="mbui_cells"></div><div class="mbui_cell_layer mbui_cell_group_layer" title="双击编辑组容器名称"><div class="mbui_cell_layer_del group_layer_del"></div></div> </div>');
                var groundId = generateId('td_' + item.type);
                
                //子控件容器
                var subContainer = el.find('.mbui_cells');
                //设置组容器名称
                el.find('.mbui_cells_title span').text(item.name);
                //添加到设计器中，并让它接收控件拖拽
                $('.mbui_group_list').append(el);
                //绑事件
                el.attr('data-group-id', groundId).click(function(e) {
                    if (!$(e.target).hasClass('mbui_cell_layer_del')) {
                        setCellActive(this);
                        //高亮被点击的控件	
                    }
                });
                el.dblclick(function(e) {
                    if (!$(e.target).hasClass('mbui_cell_layer_del') && !$(e.target).hasClass('rename-group')) {
                        var groupName = $(this).find('.mbui_cells_title span').html()
                        $('<input class="rename-group" type="text" placeholder=" 请输入组容器名称..." value="' +groupName+ '" />').appendTo(this).select()
                    }
                });
                
                // el.hover(function(e) {
                //     var offset = $(this).offset()
                //     var width = $(this).width()
                //     var tH = offset.top - 10
                //     var tW = offset.left + width/2 - 130/2
                //     $('<div class="my-tooltip" style="top:'+tH+'px;left:'+tW+'px;">双击编辑组容器名称</div>').prependTo('body')
                //     setTimeout(function() {
                //         $('.my-tooltip').remove()
                //     }, 2000);
                // })
                
                makeGroupSortable();
                
                
                //遍历出子控件
                $.each(item.fields, function(index, item) {
                	var itemId=item.itemId;
                    //根据拖拽控件类型去取设计器模板，并存在本地变量el中
                    var el = $($('#mbui_cell_' + item.type).html());
                    var ctrlId = generateId('td_' + item.type);
                    var html="<input type='hidden' value='"+itemId+"'/>"
                    $(el).append(html);
                    // 储存id
                    window.idHash[ctrlId] = item.id;
                    //设置名称
                    item.name && el.find('.mbui_label').text(item.name);
                    //说明控件
                    if (item.type === "desc") {
                        el.find('.mbui_label').text(item.value);
                    }
                    //设置placeholder
                    if (el.find('.mbui_input').length > 0) {
                        el.find('.mbui_input').attr('placeholder', item.placeholder);
                    }
                    if (el.find('.mbui_textarea').length > 0) {
                        el.find('.mbui_textarea').attr('placeholder', item.placeholder);
                    }
                   // console.log(el);
                    //添加到组容器中
                    subContainer.append(el);
                    //帮事件
                    el.attr('data-ctrl-id', ctrlId).click(function(e) {
                        if (!$(e.target).hasClass('mbui_cell_layer_del')) {
                            //高亮被点击的控件
                            setCellActive(this);
                            $('.rename-group').blur()
                            return false;
                        }
                    });
                    el.attr('data-ctrl-id', ctrlId).dblclick(function(e) {
                        if (!$(e.target).hasClass('mbui_cell_layer_del')) {
                            return false;
                        }
                    });
                });
            });
        }
        
        
		$(".mbui_group_list").sortable({
			opacity: 0.8,
			axis: "y",
		  	items: ".mbui_cells_group",
		  	placeholder: "magic_placeholder",
		  	receive: function(event,ui){

		  	},
		  	stop: function(){
		  		//$('.mbui_cells_group').removeClass('fold');
		  		$('.mbui_cells_title img').attr('src','../img/shouqi.png');
		  		hasModified = true;

		  	},
		  	start: function(){
		  		//$('.mbui_cells_group').addClass('fold');
		  		$('.mbui_cells_title img').attr('src','../img/xiala.png');
		  	}
		})
		
		// Event handles
		// Add group container
		$('.group_add_btn').click(function(e) {
		    //定义每个组的容器和相应id
            var el = $('<div class="mbui_cells_group"><div class="mbui_cells_title"><input type="hidden" value="0"/><span>组容器</span><img src="../img/shouqi.png" onclick="toggleExtension(this);" /></div><div class="mbui_cells"></div><div class="mbui_cell_layer mbui_cell_group_layer" title="双击编辑组容器名称"><div class="mbui_cell_layer_del group_layer_del"></div></div> </div>');
            var groundId = generateId('td_group');

            //设置组容器名称
            el.find('.mbui_cells_title span').text('组容器');
            //添加到设计器中，并让它接收控件拖拽
            el.prependTo('.mbui_group_list');
            //绑事件
            el.attr('data-group-id', groundId).click(function(e) {
                if (!$(e.target).hasClass('mbui_cell_layer_del')) {
                    //加载设置模板
                    setCellActive(this);
                    //高亮被点击的控件	
                }
            });
            el.attr('data-group-id', groundId).dblclick(function(e) {
                if (!$(e.target).hasClass('mbui_cell_layer_del') && !$(e.target).hasClass('rename-group')) {
                    var groupName = $(this).find('.mbui_cells_title span').html()
                    $('<input class="rename-group" type="text" placeholder=" 请输入组容器名称..." value="' +groupName+ '" />').appendTo(this).select()
                }
            });
            makeGroupSortable();
		})
		
		// Delete groups only if their contain no fields
		$('.mbui_panel_form').on('click','.group_layer_del',function(e) {
		    var sortableDiv = $(this).parent().prev()
		    if(sortableDiv.find('.mbui_cell').length > 0) {
		        alert('该组容器不为空，无法删除。')
		    } else {
		        $(this).parents('.mbui_cells_group').remove()
		    }
		})
		
		
		$('.mbui_panel_form').on('click','.mbui_cells_title',function(e) {
		    //debugger
		})
		
		// Modify the group title
		$('.mbui_panel_form').on('blur', '.rename-group', function() {
		    var el = $(this)
		    var newName = el.val().trim()
		    if(newName === '') {
		        newName = '组容器'
		    }
		    if(/[\<\>\'\"\/\\]/.test(newName)) {
		        alert('组容器名称不能包含<，>，' + "'"  +'，"，/，\\等非法字符')
		        hasIllegeChar = true
		        return false
		    }
		    hasIllegeChar = false
		    el.prevAll('.mbui_cells_title').find('span').html(newName)
		    el.remove()
		})
		$('.mbui_panel_form').on('click', '.rename-group', function() {
		    return false
		})
		$('.mbui_panel_form').on('keypress', '.rename-group', function(e) {
		    if(e.keyCode === 13) {
		        $('.rename-group').blur()
		    }
		})
		
		$(document).click(function() {
		    $('.rename-group').blur()
		})
		
		
		$('.mbui_header_save').click(function(e) {
		    var dataJson = scanForm()
		    
		    if(hasEmptyGroup) {
		        alert('保存失败，组容器不能为空。')
		        return false;
		    }
		    if(hasIllegeChar) {
		        alert('保存失败，组容器名称不能包含非法字符。')
		        return false;
		    }

//		    if(formId != "")
//			{
//                $.post('/general/system/approve_center/flow_form/pda/submit.php',
//                    {FORM_ID:formId, result:JSON2.stringify(dataJson)},
//                    function(data) {
//                        if(data == 1)
//                        {
//                            alert("保存成功");
//                        }
//                        debug && console.log(arguments)
//                    })
//			}else if(versionFormId != "")
//			{
//                $.post('/general/system/approve_center/flow_form/pda/submit.php',
//                    {version_form_id:versionFormId, result:JSON2.stringify(dataJson)},
//                    function(data) {
//                        if(data == 1)
//                        {
//                            alert("保存成功");
//                        }
//                        debug && console.log(arguments)
//                    })
//			}

		    //debug && console.log(dataJson)
		    
		    saveItemGroup();
		})
		 $(".mbui_cells_group").addClass("fold");
    })


    function generateId(originalId){
		return originalId + '_' + new Date().getTime() + Math.floor(Math.random()*1000);
	}
	
	function setCellActive(self){
		$('.mbui_cell_layer').removeClass("active");
		$(self).children('.mbui_cell_layer').addClass("active");
	}
	
	function makeGroupSortable(){
	   // return false
		$(".mbui_panel_form .mbui_cells").
    		sortable({
    			opacity: 0.8,
    			items: ".mbui_cell",
    			placeholder: "magic_placeholder",
    			connectWith: ".mbui_cells",
    			receive: function(event,ui){

    				hasModified = true;
    				//剥离helper内联样式
        			if(ui.helper){
        				ui.helper.removeAttr('style');
        				//if(ui.helper === ui.item)
        					//return false;
        			} else {
        				ui.item.removeAttr('style');
        				//return false;
        			}
    			},
    			stop: function(event,ui){
    				ui.item.removeAttr("style");
    				hasModified = true;
    			}
    		});
	}
	
	//扫描设计器
	function scanForm(){
		var srcStr = $('.mbui_group_list').html(),
			matchedId = srcStr.match(/td_(\w)+_(\d)+/g) || [],
			data = [],
			lastGroup = {};

		$.each(matchedId,function(index,item){
			if(item.indexOf('group') !== -1){
				var obj = {};
				$.extend(obj,{type:'group'});
				obj.name = $('[data-group-id="'+ item +'"]').find('.mbui_cells_title span').text();
				obj.fields = [];
				data.push(obj);
			} else {
				lastGroup = data[data.length-1];
				lastGroup.fields.push(idHash[item]);
			}
		  
		});

		hasEmptyGroup = false;
		//不能有空容器
		$.each(data,function(index,item){
			if(item.fields.length === 0){
				hasEmptyGroup = true;
			}
		});

		return data;
	}

})()


