var loaderUrl = ""; //定义combobox loader加载数据路径
var globalTabsTtileArray = {};//全局tab页签标题数组

function EasyuiTabSwitchBugFunc(title,index){
    if(!globalTabsTtileArray[title]){
        var src = $("#GLOBAL_TAB_"+title).attr("src");
        $("#GLOBAL_TAB_"+title).attr("src",src);
        globalTabsTtileArray[title] = true;
    }
}

/**
 * 动态加载tabs 
 * @param id 标签ID
 * @param title 标签页面板的名称
 * @param url 指向的页面路径
 * @returns
 */
function addTab(id, title, url){
    if ($('#'+ id).tabs('exists', title)){
        var content = '<iframe id="GLOBAL_TAB_'+title+'" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        enableTab(id,title);
        $('#'+ id).tabs('select', title);
        var selTab = $('#'+ id).tabs('getSelected');
        $('#'+ id).tabs('update', {
            tab: selTab,
            options: {
                content:content
            }
        });
    } else {
        var content = '<iframe id="GLOBAL_TAB_'+title+'" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        $('#'+ id).tabs('add',{
            title:title,
            content:content,
            selected: false
        });
    }
}

/**
 * 指定特定的tabs被选择
 * @param id tab标签ID
 * @param index tab标签ID
 * @returns
 */
function setTab(id,index){
    enableTab(id,index);//启用指定的标签页面板
    $('#'+ id).tabs('select',index); //选中指定的标签页面板
}
/**
 * 启用指定的标签页面板
 * @param id tab标签ID
 * @param index tab的索引
 * @returns
 */
function enableTab(id,index){
    $('#'+ id).tabs('enableTab', index); //启用指定的标签页面板
}

/**
 * 禁用指定的tabs
 * @param id tab标签ID
 * @param index tab的索引
 * @returns
 */
function disabledTab(id,index){
    $('#'+ id).tabs('disableTab', index); //禁用指定的标签页面板
}

/**
 * iframe嵌套的tabs 点击保存(下一步)解锁下一个页签
 * @param id tab标签的ID
 * @returns
 */
function controlTabs(id){
    var tab = parent.$('#'+ id).tabs('getSelected');
    var index = parent.$('#'+ id).tabs('getTabIndex',tab);
    parent.enableTab(id,index + 1);
    parent.setTab(id,index + 1);
}

/**
 * 刷新下拉框样式
 * @param id select标签ID
 * @returns
 */
function initCommonCaseSelectRefresh(id) {
    var json = [{}];
    json.unshift({codeNo: -1, codeName: "请选择"});
    $('#'+ id).combobox({
        data: json,
        valueField: 'codeNo',
        textField: 'codeName',
        panelHeight:"200",
        onLoadSuccess:function(){
            $('#'+ id).combobox('setValue',-1);
        }
    });
}

/**
 * 加载非代码值的select 无值默认显示“请选择”
 * @param id select标签ID
 * @param json 传递的json数组，只有codeNo,codeName两个属性
 * @param value 默认选中代码值
 * @returns
 */
function initCommonCaseSelectJson(id, json, value) {
    if(json != null && json != '' && json != 'null'){
        var panelHeight = 'auto';
        if(json.length > 6){
            panelHeight = '200';
        }
        $('#'+ id).combobox({
            data: json,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: panelHeight,
            prompt : '请选择',
            onLoadSuccess:function(){
                var jsonArr = [];//存放所有的key
                for(var i=0; i<json.length; i++){
                    jsonArr.push(json[i].codeNo)
                }
                if(jsonArr.indexOf(value) == -1){
                    //value参数不存在于代码值中
                    $('#'+id).combobox('setValue',null);
                }else{
                    //value参数存在于代码值中
                    $('#'+id).combobox('setValue',value);
                }
            },
            onHidePanel: function() {
                var valueField = $(this).combobox("options").textField;
                var val = $(this).combobox("getText"); //当前combobox的值
                var allData = $(this).combobox("getData");  //获取combobox所有数据
                var result = true;     //为true说明输入的值在下拉框数据中不存在
                for (var i = 0; i < allData.length; i++) {
                    if (val == allData[i][valueField]) {
                        result = false;
                    }
                }
                if(result){//不存在清除
                    $(this).combobox('clear');
                }
            }
        });
    }
}

/**
 * 加载非代码值的select，无值默认显示“全部”
 * @param id select标签ID
 * @param json 传递的json数组，只有codeNo,codeName两个属性
 * @param value 默认选中代码值
 * @returns
 */
function initCommonCaseSelectIndexJson(id, json, value) {
    if(json != null && json != '' && json != 'null'){
        var panelHeight = 'auto';
        if(json.length > 6){
            panelHeight = '200';
        }
        $('#'+ id).combobox({
            data: json,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: panelHeight,
            prompt : '全部',
            onLoadSuccess:function(){
                var jsonArr = [];//存放所有的key
                for(var i=0; i<json.length; i++){
                    jsonArr.push(json[i].codeNo)
                }
                if(jsonArr.indexOf(value) == -1){
                    //value参数不存在于代码值中
                    $('#'+id).combobox('setValue',null);
                }else{
                    //value参数存在于代码值中
                    $('#'+id).combobox('setValue',value);
                }
            },
            onHidePanel: function() {
                var valueField = $(this).combobox("options").textField;
                var val = $(this).combobox("getText"); //当前combobox的值
                var allData = $(this).combobox("getData");  //获取combobox所有数据
                var result = true;     //为true说明输入的值在下拉框数据中不存在
                for (var i = 0; i < allData.length; i++) {
                    if (val == allData[i][valueField]) {
                        result = false;
                    }
                }
                if(result){//不存在清除
                    $(this).combobox('clear');
                }
            }
        });
    }
}


/**
 * 刷新下拉框样式 无 “请选择”
 * @param code 字典代码值
 * @param id select标签ID
 * @param value 设置默认选中的属性值
 * @returns
 */
function initCommonCaseSelectCodeFirst(code, id, value) {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: code});
    if(json.rtState) {
        var panelHeight = 'auto';
        if(json.rtData.length > 6){
            panelHeight = '200';
        }
        $('#'+ id).combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: panelHeight,
            onLoadSuccess:function(){
                var jsonArr = [];//存放所有的key
                for(var i=0; i<json.rtData.length; i++){
                    jsonArr.push(json.rtData[i].codeNo)
                }
                if(jsonArr.indexOf(value) == -1){
                    //value参数不存在于代码值中
                    $('#'+id).combobox('setValue',null);
                }else{
                    //value参数存在于代码值中
                    $('#'+id).combobox('setValue',value);
                }
            },
            onHidePanel: function() {
                var valueField = $(this).combobox("options").valueField;
                var val = $(this).combobox("getValue"); //当前combobox的值
                var allData = $(this).combobox("getData");  //获取combobox所有数据
                var result = true;     //为true说明输入的值在下拉框数据中不存在
                for (var i = 0; i < allData.length; i++) {
                    if (val == allData[i][valueField]) {
                        result = false;
                    }
                }
                if(result){//不存在清除
                    $(this).combobox('clear');
                }
            }
        });
    }
}

/**
 * 初始化select标签 带有“请选择”
 * @param code 字典代码值
 * @param id select标签ID
 * @param value 默认选中的属性值,若没有默认选中值，则传递 null
 * @returns
 */
function initCommonCaseSelectCodeHaveSelect(code, id, value) {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: code});
    if(json.rtState) {
        var panelHeight = 'auto';
        if(json.rtData.length > 6){
            panelHeight = '200';
        }
        $('#'+ id).combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: panelHeight,
            prompt : '请选择',
            onLoadSuccess:function(){
                var jsonArr = [];//存放所有的key
                for(var i=0; i<json.rtData.length; i++){
                    jsonArr.push(json.rtData[i].codeNo)
                }
                if(jsonArr.indexOf(value) == -1){
                    //value参数不存在于代码值中
                    $('#'+id).combobox('setValue',null);
                }else{
                    //value参数存在于代码值中
                    $('#'+id).combobox('setValue',value);
                }
            },
            onHidePanel: function() {
                var valueField = $(this).combobox("options").valueField;
                var val = $(this).combobox("getValue"); //当前combobox的值
                var allData = $(this).combobox("getData");  //获取combobox所有数据
                var result = true;     //为true说明输入的值在下拉框数据中不存在
                for (var i = 0; i < allData.length; i++) {
                    if (val == allData[i][valueField]) {
                        result = false;
                    }
                }
                if(result){//不存在清除
                    $(this).combobox('clear');
                }
            }
        });
    }
}

/**
 * 初始化select标签 带有“请选择”,多选
 * @param code 字典代码值
 * @param id select标签ID
 * @param value 默认选中的属性值,若没有默认选中值，则传递 null
 * @returns
 */
function initCommonCaseMultipleSelectCodeHaveSelect(code, id, value) {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: code});
    if(json.rtState) {
        var panelHeight = 'auto';
        if(json.rtData.length > 6){
            panelHeight = '200';
        }
        $('#'+ id).combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 0,
            multiple:true,
            editable:false,
            prompt : '请选择',
            onSelect: function(record){
                $.tabIndex = $('#'+ id).index(this);
                $('#'+ id).eq($.tabIndex).textbox("setValue", record.id);
            },
            onHidePanel: function() {
                var valueField = $(this).combobox("options").valueField;
                var val = $(this).combobox("getValues"); //当前combobox的值
                var allData = $(this).combobox("getData");  //获取combobox所有数据
                var unSelect = [allData.length]
                var currentValue=val.toString().split(",");//把选中的值及输入值分割为数组
                for(var j=0;j<currentValue.length;j++){//循环选中的值和com中所有值进行比对，不存在的利用unselect清除
                    var result = true;     //为true说明输入的值在下拉框数据中不存在
                    for (var i = 0; i < allData.length; i++) {
                        if (currentValue[j] == allData[i][valueField]) {
                            result = false;
                        }
                    }
                    if(result){//仅仅清除不存在的值
                        $(this).combobox('unselect', currentValue[j]);
                    }
                }
            }
        });
    }
}

/**
 * 把Json数据绑定到控件

  json : json对象
  filters : 过滤不需要绑定的控件
 */
function bindJsonObj2Easyui(json, formId, filters) {
    var form = document.getElementById(formId);
    for (var property in json) {
        
        if (filters) {
            if(filters.indexOf(",") > 0) {
                var filterArray = filters.split(",");
                if (filterArray.contains(property)) {
                  continue;
                }
            } else {
                if(filters == property) {
                    continue;
                }
            }
        }
        
        var value = json[property];
        
        var cntrlArray = document.getElementsByName(property);    
        var cntrlCnt = cntrlArray.length;
        if (!cntrlArray || cntrlCnt < 1) {
            if (document.getElementById(property)) {
                cntrlArray = [document.getElementById(property)];
                cntrlCnt = 1;
            }else {
                continue;
            }
        }
        if (cntrlCnt == 1) {
            var cntrl = cntrlArray[0];
            if(form[property] != null) {
                var $this = form[property][0] == null ? form[property] : form[property][0];
                if($this != null && $this != '') {
                    if($this.className != null && $this.className != '') {
                        if($this.className.indexOf("easyui-textbox") != -1) {
                            $('#' + $this.id).textbox('setValue', value);
                        } else if($this.className.indexOf("easyui-datebox") != -1) {
                            $('#' + $this.id).datebox('setValue', value);
                        } else if($this.className.indexOf("easyui-datetimebox") != -1) {
                            $('#' + $this.id).datebox('setValue', value);
                        } else if($this.className.indexOf("easyui-datetimebox") != -1) {
                            $('#' + $this.id).datebox('setValue', value);
                        } else if($this.className.indexOf("easyui-radiobutton") != -1) {
                            $("input[name='" + $this.name + "']").iRadiobutton('setValue', value);
                        } else if($this.className.indexOf("easyui-combobox") != -1) {
                            if (value != null && value != '') {
                                var value = value + "";
                                var vals = value.split(',');
                                if (vals.length > 1) {
                                    $('#' + $this.id).combobox('setValues', vals);
                                } else {
                                    $('#' + $this.id).combobox('setValue', value);
                                }
                            }
                        } else if($this.className.indexOf("easyui-datebox")) {
                            
                        }
                    } else {
                        if($this.id != null && $this.id != '') {
                            $('#' + $this.id).val(value);
                        }
                    }
                }
            }
        }else {
            for (var i = 0; i < cntrlCnt; i++) {
                var cntrl = cntrlArray[i];
                if (cntrl.value == value) {
                    cntrl.checked = true;
                }else {
                    cntrl.checked = false;
                }
            }
        }
    }
}
/**
 * contains 方法
 */
Array.prototype.contains = function(val){
    for (var i = 0; i < this.length; i++){
        if (this[i] == val){
            return true;
        }
    }
    return false;
};
/**
 * validatebox绑定(reduce)，取消验证(remove)
 * 用法 $('#id').validatebox('reduce') 绑定
 *     $('#id').validatebox('remove') 取消绑定
 *     
 *     $("#id").validatebox({required:true}) //必填
 */
$.extend($.fn.validatebox.methods, {
    remove: function (jq, newposition) {
        return jq.each(function () {
            $(this).removeClass("validatebox-text validatebox-invalid easyui-validatebox").unbind('focus').unbind('blur');
        });
    },

    reduce: function (jq, newposition) {
        return jq.each(function () {
            var opt = $(this).data().validatebox.options;
            $(this).addClass("validatebox-text validatebox-invalid easyui-validatebox").validatebox(opt);
        });
    }
});




/**
 * combobox，多选下拉框，输入关键字，搜索加载数据
 * @param id 标签ID
 * @param url 请求数据路径，数据返回格式 JSONObject
 * @returns
 */
function initMultipleSelect(id, url){
    loaderUrl = url;
    $('#'+ id).combobox({
        loader: loader, // loader 自定义方法
        mode: 'remote',
        valueField: 'id',
        textField: 'name',
        multiple:true,
        editable:true,
        separator:' ',
        panelHeight: '200',
        prompt : '请输入关键字搜索并选择',
        onSelect: function(record){
            $.tabIndex = $('#'+ id).index(this);
            $('#'+ id).eq($.tabIndex).textbox("setValue", record.id);
        },
        onHidePanel: function() {
            var valueField = $(this).combobox("options").valueField;
            var val = $(this).combobox("getValues"); //当前combobox的值
            var allData = $(this).combobox("getData");  //获取combobox所有数据
            var unSelect = [allData.length]
            var currentValue=val.toString().split(",");//把选中的值及输入值分割为数组
            for(var j=0;j<currentValue.length;j++){//循环选中的值和com中所有值进行比对，不存在的利用unselect清除
                var result = true;     //为true说明输入的值在下拉框数据中不存在
                for (var i = 0; i < allData.length; i++) {
                    if (currentValue[j] == allData[i][valueField]) {
                        result = false;
                    }
                }
                if(result){//仅仅清除不存在的值
                    $(this).combobox('unselect', currentValue[j]);
                }
            }
        }
    });
}

/**
 * combobox，单选下拉框，输入关键字，搜索时加载数据
 * @param id 标签ID
 * @param url 请求数据路径，数据返回格式 JSONObject
 * loaderUrl 全局变量
 * @returns
 */
function initSingleSelect(id, url){
    loaderUrl = url;
    $('#'+ id).combobox({
        loader: loader, // loader 自定义方法
        mode: 'remote',
        valueField: 'id',
        textField: 'name',
        editable:true,
        separator:' ',
        panelHeight: '200',
        prompt : '请输入关键字搜索并选择',
        onSelect: function(record){
            $.tabIndex = $('#'+ id).index(this);
            $('#'+ id).eq($.tabIndex).textbox("setValue", record.id);
        },
        onHidePanel: function() {
            var valueField = $(this).combobox("options").valueField;
            var val = $(this).combobox("getValue"); //当前combobox的值
            var allData = $(this).combobox("getData");  //获取combobox所有数据
            var result = true;     //为true说明输入的值在下拉框数据中不存在
            for (var i = 0; i < allData.length; i++) {
                if (val == allData[i][valueField]) {
                    result = false;
                }
            }
            if(result){//不存在清除
                $(this).combobox('clear');
            }
        }
    });
}

/**
 * 单选输入搜索combobox回显方法
 * @param id combobox标签ID
 * @param url 请求回显数据路径
 * @param params 参数 回显数据ID
 * @returns
 */
function initSingleselectCallBack(id, url, params){
    var singleJson = tools.requestJsonRs(url,params);
    if(singleJson.rtState){
        var flag = true;//仅让onLoadSuccess加载一次，完成修改时初始化赋值
        $('#'+id).combobox({
            valueField: 'id',
            textField: 'name',
            mode: 'remote',
            editable:true,
            onSelect: function(record){
                $.tabIndex = $('#'+id).index(this);
                $('#'+id).eq($.tabIndex).textbox("setValue", record.id);
                flag = false;
            },
            onLoadSuccess: function(){
                if (flag){
                    $('#'+id).combobox("setValue", singleJson.rtData.id);
                }
            },
            loader: function(param, success, error){
                if(param.q == null || param.q == ''){
                    param.q = singleJson.rtData.name;
                }
                var q = param.q;
                if (q == ''){
                    return false;
                }
                $.ajax({
                    url: loaderUrl,
                    dataType: 'json',
                    data: {
                        q: q
                    },
                    success: function(data){
                        var items = $.map(data.data, function(item, index){
                            return {
                                id: item.id,
                                name: item.name
                            }
                            
                        });
                        success(items);
                    },
                    error: function(){
                        error.apply(this, arguments);
                    }
                });
            }
        });
    }
}

/**
 * 自定义方法
 * param, success, error combobox中loader内部参数，无需手动传参
 * loaderUrl 自定义请求数据路径，返回数据格式JSONObject
 * 在 combobox loader 属性处引用
 */
var loader = function(param, success, error){
    var q = param.q || '';
    if (q == ''){
        return false;
    }
    var url = loaderUrl; //
    $.ajax({
        url: url,
        dataType: 'json',
        data: {
            q: q
        },
        success: function(data){
            var items = $.map(data.data, function(item, index){
                return {
                    id: item.id,
                    name: item.name
                }
            });
            if(items != null && items.length > 0){
                success(items);
            }
        },
        error: function(){
            error.apply(this, arguments);
        }
    });
}

/**
 * 首页查询条件获取 默认选中第一个值
 * @param id 标签ID
 * @param url 加载初始化数据连接
 * @param loaderUrl 动态加载查询连接
 * @param params 参数
 * @returns
 */
function getSelectSearchIndex(id,url,loaderUrl, params){
    var singleJson = tools.requestJsonRs(url);
    if(singleJson.rtState){
        var flag = true;//仅让onLoadSuccess加载一次，完成修改时初始化赋值
        $('#'+id).combobox({
            data: singleJson.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            mode: 'remote',
            editable:true,
            panelHeight: '200',
            prompt : '全部',
            onSelect: function(record){
                $.tabIndex = $('#'+id).index(this);
                $('#'+id).eq($.tabIndex).textbox("setValue", record.id);
                flag = false;
            },
            onLoadSuccess: function(){
                if (flag){
                    $('#'+id).combobox("setValue", singleJson.rtData[0].codeNo);
                }
            },
            onHidePanel: function() {
                var valueField = $(this).combobox("options").valueField;
                var val = $(this).combobox("getValue"); //当前combobox的值
                var allData = $(this).combobox("getData");  //获取combobox所有数据
                var result = true;     //为true说明输入的值在下拉框数据中不存在
                for (var i = 0; i < allData.length; i++) {
                    if (val == allData[i][valueField]) {
                        result = false;
                    }
                }
                if(result){//不存在清除
                    $(this).combobox('clear');
                }
            },
            loader: function(param, success, error){
                if(param.q == null || param.q == ''){
                    param.q = singleJson.rtData[0].codeName;
                }
                var q = param.q;
                if (q == ''){
                    return false;
                }
                $.ajax({
                    url: loaderUrl,
                    dataType: 'json',
                    data: {
                        subName: q,
                        menuNames: params.menuNames
                    },
                    success: function(data){
                        if(data.rtData != null && data.rtData.length > 0){
                            var items = $.map(data.rtData, function(item, index){
                                return {
                                    codeNo: item.codeNo,
                                    codeName: item.codeName
                                }
                                
                            });
                            success(items);
                        }
                    },
                    error: function(){
                        error.apply(this, arguments);
                    }
                });
            }
        });
    }
}

/**
 * 首页查询条件获取 默认不选中
 * @param id 标签ID
 * @param loaderUrl 动态加载查询连接
 * @param params 参数
 * @returns
 */
function getSelectSearch(id,loaderUrl, params){
    $('#'+id).combobox({
        valueField: 'codeNo',
        textField: 'codeName',
        mode: 'remote',
        editable:true,
        panelHeight: '200',
        prompt : '全部',
        onSelect: function(record){
            $.tabIndex = $('#'+id).index(this);
            $('#'+id).eq($.tabIndex).textbox("setValue", record.id);
            flag = false;
        },
        onHidePanel: function() {
            var valueField = $(this).combobox("options").valueField;
            var val = $(this).combobox("getValue"); //当前combobox的值
            var allData = $(this).combobox("getData");  //获取combobox所有数据
            var result = true;     //为true说明输入的值在下拉框数据中不存在
            for (var i = 0; i < allData.length; i++) {
                if (val == allData[i][valueField]) {
                    result = false;
                }
            }
            if(result){//不存在清除
                $(this).combobox('clear');
            }
        },
        loader: function(param, success, error){
            var q = param.q;
            if (q == ''){
                return false;
            }
            $.ajax({
                url: loaderUrl,
                dataType: 'json',
                data: {
                    subName: q,
                    menuNames: params.menuNames
                },
                success: function(data){
                    if(data.rtData != null && data.rtData.length > 0){
                        var items = $.map(data.rtData, function(item, index){
                            return {
                                codeNo: item.codeNo,
                                codeName: item.codeName
                            }
                            
                        });
                        success(items);
                    }
                },
                error: function(){
                    error.apply(this, arguments);
                }
            });
        }
    });
}

$.extend($.fn.validatebox.defaults.rules, {
    number : {
        validator : function(value, param) {
            return /^[0-9]*$/.test(value);
        },
        message : "请输入数字"
    },
    decimal : {
        validator : function(value, param) {
            return /^[0-9]*(.[0-9]*)$/.test(value);
        },
        message : "请输入数字"
    },
    money : {
    	validator : function(value, param) {
            return /^([1-9]\d{0,9}|0)([.]?|(\.\d{1,2})?)$/.test(value);
        },
        message : "请输入有效金额"
    },
    chinese : {
        validator : function(value, param) {
            var reg = /^[\u4e00-\u9fa5]+$/i;
            return reg.test(value);
        },
        message : "请输入中文"
    },
    checkLength: {
        validator: function(value, param){
            return param[0] >= get_length(value);
        },
        message: '请输入最大{0}位字符'
    },
    date:{
        validator : function(value){
            if (!value) {
                return;
            }
            var r = value.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
            if (r == null) {
                return false;
            }
            if (parseInt(r[1]) > 9999 || parseInt(r[1]) < 1) {
                return false;
            }
            var d = new Date(r[1], r[3]-1, r[4]);
            return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]);
        },
        message : '请输入正确格式时间yyyy-MM-dd'
    },
    specialCharacter: {
        validator: function(value, param){
            var reg = new RegExp("[`~!@#$^&*()=|{}':;'\\[\\]<>~！@#￥……&*（）——|{}【】‘；：”“'、？]");
            return !reg.test(value);
        },
        message: '不允许输入特殊字符'
    },
    idCard : {// 验证身份证
        validator : function(value) {
        	//return /^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$/.test(value);
        	return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/i.test(value);
        },
        message : '身份证号码长度不正确'
    },
    organizationCode: {
        validator : function (value) {
            var val = value.toUpperCase();
            return creditCodeValidate(value);
        },
        message : '统一社会信用代码格式不正确'
    },
    orgCode: {
        validator : function (value) {
            var val = value.toUpperCase();
            return !orgcodeValidate(value);
        },
        message : '组织机构代码格式不正确'
    },
    busCode: {
        validator : function (value) {
            var val = value.toUpperCase();
            return !busCodeValidate(value);
        },
        message : '营业执照注册号格式不正确'
    },
    phone : {
        validator : function(value) {
            // return /((\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14})/.test(value);
        	return /^1(3|4|5|7|8)\d{9}$/.test(value) || /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(value);
        },
        message : '联系方式格式不正确'
    },
    radio: {
        validator: function (value, param) {
            var frm = param[0], groupname = param[1], ok = false;
            $('input[name="' + groupname + '"]', document[frm]).each(function () { //查找表单中所有此名称的radio
                if (this.checked) { ok = true; return false; }
            });
            return ok;
        },
        message: '需要选择一项！'
    },
    checkbox: {
        validator: function (value, param) {
            var frm = param[0], groupname = param[1], checkNum = 0;
            $('input[name="' + groupname + '"]', document[frm]).each(function () { //查找表单中所有此名称的checkbox
                if (this.checked) checkNum++;
            });
            return checkNum > 0;
        },
        message: '请选择分期执行！'
    }
});

/**
 * 校验组织机构代码 （返回false校验成功，放回true校验失败）
 * @param value
 * @returns
 */
function orgcodeValidate(value) {
    if (value != "") {
        var values = value.split("-");
        var ws = [ 3, 7, 9, 10, 5, 8, 4, 2 ];
        var str = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
        var reg = /^([0-9A-Z]){8}$/;
        if (!reg.test(values[0])) {
            return true
        }
        var sum = 0;
        for (var i = 0; i < 8; i++) {
            sum += str.indexOf(values[0].charAt(i)) * ws[i];
        }
        var C9 = 11 - (sum % 11);
        var YC9 = values[1] + '';
        if (C9 == 11) {
            C9 = '0';
        } else if (C9 == 10) {
            C9 = 'X';
        } else {
            C9 = C9 + '';
        }
        return YC9 != C9;
    }
}

/**
 * 校验统一社会信用代码（返回true校验成功，返回false校验失败）
 * @param Code
 * @returns
 */
function creditCodeValidate(code) {
    var patrn = /^[0-9A-Z]+$/;
    // 18位校验及大写校验
    if ((code.length != 18) || (patrn.test(code) == false)) {
        console.info("不是有效的统一社会信用编码！");
        return false;
    } else {
        var anCode;// 统一社会信用代码的每一个值
        var anCodeValue;// 统一社会信用代码每一个值的权重
        var total = 0;
        var weightedfactors = [ 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28 ];// 加权因子
        var str = '0123456789ABCDEFGHJKLMNPQRTUWXY';
        // 不用I、O、S、V、Z
        for (var i = 0; i < code.length - 1; i++) {
            anCode = code.substring(i, i + 1);
            anCodeValue = str.indexOf(anCode);
            total = total + anCodeValue * weightedfactors[i];
            // 权重与加权因子相乘之和
        }
        var logiccheckcode = 31 - total % 31;
        if (logiccheckcode == 31) {
            logiccheckcode = 0;
        }
        var str2 = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,J,K,L,M,N,P,Q,R,T,U,W,X,Y";
        var array_Str = str2.split(',');
        logiccheckcode = array_Str[logiccheckcode];

        var checkcode = code.substring(17, 18);
        if (logiccheckcode != checkcode) {
            console.info("不是有效的统一社会信用编码！");
            return false;
        } else {
            console.info("yes");
        }
        return true;
    }
}

/**
 * 旧，营业执照号校验（返回true校验成功，返回false校验失败）
 * @param busCode
 * @returns
 */
function busCodeValidate(busCode){
    var ret = false;
    if(busCode.length==15){
        var sum=0;
        var s=[];
        var p=[];
        var a=[];
        var m=10;
        p[0]=m;
        for(var i=0;i<busCode.length;i++){
            a[i]=parseInt(busCode.substring(i,i+1),m);
            s[i]=(p[i]%(m+1))+a[i];
            if(0==s[i]%m){
                p[i+1]=10*2;
            }else{
                p[i+1]=(s[i]%m)*2;
            }
        }
        if(1==(s[14]%m)){
            // 营业执照编号正确!
            // alert(“营业执照编号正确!”);
            ret=true;
        }else{
            // 营业执照编号错误!
            ret=false;
            // alert(“营业执照编号错误!”);
        }
    }
    return ret;
}
 
/**
 * 通过身份证，提取出生日期，年龄，性别
 * 
 * @param UUserCard
 *            身份证
 * @param num
 *            获取类型
 * @returns
 */
function IdCard(UUserCard, num) {
    if (num == 1) {
        //获取出生日期
        birth = UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14);
        return birth;
    }
    if (num == 2) {
        //获取性别
        if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
            //男
            return "男";
        } else {
            //女
            return "女";
        }
    }
    if (num == 3) {
        //获取性别
        if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
            //男
            return "1";
        } else {
            //女
            return "2";
        }
    }
    if (num == 4) {
        //获取年龄
        var myDate = new Date();
        var month = myDate.getMonth() + 1;
        var day = myDate.getDate();
        var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1;
        if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) {
            age++;
        }
        return age;
    }
}

function dateValidate(beginId, endId){
	$("#"+beginId).datebox().datebox('calendar').calendar({
        validator: function(date){
        	var endDate = $("#"+endId).val();
	        if(!endDate){
	            return true;
	        }
	        var r2 = endDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	        var d2 = new Date(r2[1], r2[3]-1, r2[4]);
        	return date<=d2;
        }
    });
	$("#"+endId).datebox().datebox('calendar').calendar({
        validator: function(date){
        	var endDate = $("#"+beginId).val();
	        if(!endDate){
	            return true;
	        }
	        var r2 = endDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	        var d2 = new Date(r2[1], r2[3]-1, r2[4]);
        	return date>=d2;
        }
    });
}

function dateSysdateValidate(id){
	$("#"+id).datebox().datebox('calendar').calendar({
        validator: function(date){
	        var d2 = new Date();
        	return date<=d2;
        }
    });
}

function longToTime(time){
    if(time != null && time != ""){
        var d = new Date(parseFloat(time));
        var y = d.getFullYear();
        var m = d.getMonth() + 1;
        var da = d.getDate();
        var time1 = y + "-" + (m<10?"0":"")+ m + "-" + (da<10?"0":"") + da;
        return time1;
    }else{
        return "";
    }
}