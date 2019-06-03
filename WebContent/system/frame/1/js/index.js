$(function() {
  var bodyLayout = $('body').layout({
    'listeners': {
      'resize': function(){
      }
    },
    north: {
      'fxSpeen': 'slow',
      size: 'auto'
    },
    south: {
      size: 'auto'
    },
    west: {
      'fxSpeed': 'slow',
      size: 'auto'
    },
    center: {
      
    }
  });
  
  initMenu(".left-menu > div");
});

/**
 * 显示桌面模块
 * @param src   以javascript:开头则直接运行javascript:后边的内容
 *              带有openFlag=1的url在新窗口中打开(通过参数openWidth=?/openHeight=?设置新窗口宽高)
 * @return
 */
function dispParts(src, openFlag){
  if (src){
    if (/javascript:/.exec(src)){
      //当路径为javascript:则src为可执行的函数

      try {
        eval(src);
      } catch (e) {
        
      }
    }
    else{
      //使用?或者&分割URL
      var srcList = src.split(/[?&]/);
      var url = '';
      var paras = '';
      
      if (srcList.length > 1){
        $.each(srcList, function(i, e){
          if (e == 'openFlag=1'){
            openFlag = 1;
          }
          else if (/^openHeight=:/.exec(e)){
            paras += e.replace('openHeight','height') + ',';
          }
          else if (/^openWidth=:/.exec(e)){
            paras += e.replace('openWidth','width') + ',';
          }
          else if (url.indexOf('?') > 0){
            url += e + '&';
          }
          else{
            url += e + '?';
          }
        });
      }
      else{
        url = src;
      }
      
      if (openFlag == 1){
        //当openFlag=1时在新窗口中打开链接
        window.open(encodeURI(url),'',paras);
      }
      else{
        //在工作区打开连接
        $('#desktop').hide();
        $('#workspace').show();
        window['workspace'].location = encodeURI(url);
      }
    }
  }
}

/**
 * 初始化主菜单
 * @param el
 * @return
 */
function initMenu(el) {
  var data = [{
    seqId:84,
    expand:0,
    id:"02",
    text:"个人事务",
    icon:"mytable.gif",
    leaf:0,
    children: [{
      text: "内部邮件",
      leaf: true,
      url: contextPath + "/system/frame/1/test/index.jsp"
    }, {
      text: "公告通知",
      leaf: true,
      url: contextPath + "/fis/funcs/basecode/system/"
    }, {
        text: "消息管理",
        leaf: true,
        url: contextPath + "/fis/funcs/basecode/system/"
    }, {
        text: "新闻",
        leaf: true,
        url: contextPath + "/fis/funcs/basecode/system/"
   }, {
	    text: "个人考勤",
	    leaf: true,
	    url: contextPath + "/fis/funcs/basecode/system/"
   }, {
        text: "日程安排",
        leaf: true,
        url: contextPath + "/fis/funcs/basecode/system/"
   }, {
         text: "通讯簿",
         leaf: true,
         url: contextPath + "/fis/funcs/basecode/system/"
  }, {
        text: "控制面板",
        leaf: true,
        url: contextPath + "/fis/funcs/basecode/system/"
     }],
    openFlag:""
  },{
	    seqId:84,
	    expand:0,
	    id:"02",
	    text:"工作流",
	    icon:"mytable.gif",
	    leaf:0,
	    children: [{
	      text: "我的工作",
	      leaf: true,
	      url: contextPath + "/funcs/email"
	    }, {
	      text: "工作查询",
	      leaf: true,
	      url: contextPath + "/funcs/richeng"
	    }],
	    openFlag:""
	  },{
	    seqId:84,
	    expand:0,
	    id:"02",
	    text:"行政事务",
	    icon:"mytable.gif",
	    leaf:0,
	    children: [{
	      text: "人员管理",
	      leaf: true,
	      url: contextPath + "/funcs/email"
	    }, {
	      text: "档案管理",
	      leaf: true,
	      url: contextPath + "/funcs/richeng"
	    }],
	    openFlag:""
		  },{
		    seqId:71,
		    expand:0,
		    id:"04",
		    text:"系统设置",
		    icon:"workflow.gif",
		    leaf:0,
		    url:"",
		    openFlag:"",
		    children: [{
		        seqId:84,
		        expand:0,
		        id:"02",
		        text:"组织机构",
		        icon:"mytable.gif",
		        leaf:0,
		        children: [{
		          text: "单位管理",
		          leaf: true,
		          url: contextPath + "/system/core/org/index.jsp"
		        }, {
		          text: "部门管理",
		          leaf: true,
		          url: contextPath + "/system/core/dept/index.jsp"
		        },{
		            text: "用户管理",
		            leaf: true,
		            url: contextPath + "/system/core/person/index.jsp"
		         },{
		              text: "角色管理",
		              leaf: true,
		              url: contextPath + "/system/core/org/manageRole.jsp"
		           }]
		    }, {
		      text: "其他设置",
		      leaf: true,
		      url: contextPath + "/fis/funcs//"
		    }],
		    openFlag:""
  }];
  
  new T9.Menu({
    id: "menu",
    classes: ['menu-lv1', 'menu-lv2', 'menu-lv3'],
    data: data,
    openUrl: function (node) {
      dispParts(node.url, node.openFlag);
    },
    el: el,
    expandType: 0,
    //isLazyLoad: true,
    lazyLoadData: function (menu) {
      return {};
    },
    liClass: [null, 'menu-close', null],
    selClass: 'menu-selected',
    expClass: ['menu-selected', 'menu-expand']
  });
}