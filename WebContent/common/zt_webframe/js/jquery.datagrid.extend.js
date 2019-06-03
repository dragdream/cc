var EASYUI_DATAGRID_NONE_DATA_TIP = $.extend({},$.fn.datagrid.defaults.view,{
    onAfterRender:function(target){
        $.fn.datagrid.defaults.view.onAfterRender.call(this,target);
        var opts = $(target).datagrid('options');
        var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
        vc.children('div.msg-info').remove();
        if (!$(target).datagrid('getRows').length){
            var d = $('<div class="msg-info"></div>').html(opts.emptyMsg || '暂无符合条件的数据！').appendTo(vc);
            d.css({
                marginTop:70,
                width:'300px',
                textAlign:'center',
                fontColor:'#999999',
                border:'1px solid #e6e6e6',
                borderRadius:'8px',
                padding:'70px 50px 15px',
                background: 'url(/common/zt_webframe/imgs/common_img/info.png) no-repeat center 13px'
            });            
        }
    }
});