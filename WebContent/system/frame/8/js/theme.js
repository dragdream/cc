var P =
{
    Dom: {
        h: $('#header'),
        ht: $('#header #taskbar'),
        hn: $('#header #navbar'),
        f: $('#footer'),
        hb: $('#hero_bar'),
        c: $('#container'),
        cc: $('#container #c_content'),
        cci: $('#container #c_content iframe'),
        cf: $('#container #c_funcarea'),
        co: $('#container #c_orglist'),
        cow: $('#container #c_orglist #c_orglist_wrapper'),
        cowo: $("div[node-type='orglist']", $('#container #c_orglist #c_orglist_wrapper')),
        cowr: $("div[node-type='recentlist']", $('#container #c_orglist #c_orglist_wrapper'))
    },
    //存储基本信息
    Base: {
        onlineStatus: {
            '0': {'desc': '在线', 'className': 'T_online_status_online'},
            '1': {'desc': '离开', 'className': 'T_online_status_away'},
            '2': {'desc': '忙碌', 'className': 'T_online_status_busy'},
            '3': {'desc': '请勿打扰', 'className': 'T_online_status_donttrouble'},
            '4': {'desc': '隐身', 'className': 'T_online_status_invisible'},
            '5': {'desc': '离线', 'className': 'T_online_status_offline'}
        },
        recentMsgNum: 40,
        highlightCss: 'T_highlight',
        title: {
            'message': '新微讯',
            'notification': '新提醒',
            'msg&ntf': '新微讯和提醒'
        }
    },
    Status: {
        sidePanelColse: $.cookie('PsidePanelColse') == "true" ? $.cookie('PsidePanelColse') : "false",
        headNavInside: "false",
        skin: $CONFIG['defaultSkin']
    },
    Timer: {
        mon: {
            'message': {name: null, time: 1000},
            'notification': {name: null, time: 1000}
        },
        browserTitle: {name: null, time: 1000},
        taskBarSpeed: 300,
        notificationBox: {name: null, time: 3000},
        fbox: {
            show: {name: null, time: 500},
            trigger: {name: null, time: 500}
        },
        skinBox: {
            show: {name: null, time: 500},
            trigger: {name: null, time: 500}
        },
        recentlist: {
            show: {name: null, time: 500},
            trigger: {name: null, time: 500}
        },
        personBox: {
            show: {name: null, time: 500},
            trigger: {name: null, time: 500}
        },
        timeBox: {
            show: {name: null, time: 500},
            trigger: {name: null, time: 500}
        },
        logoutBox: {
            show: {name: null, time: 500},
            trigger: {name: null, time: 500}
        }
    },
    cookieExpires: {
        'PsidePanelColse': null
    },
    AppCate: {},
    App: {},
    init: function() {
        //加载进度条
        if ($CONFIG['enable_loading'] == 1)
            this.preLoad.start();

        //初始化头部标签
        this.headTaskBar.init();
		
        //sidePanel初始化
        this.sidePanel.init();

        //初始化菜单
        this.headNavBar.init();

        //初始化应用提醒功能区
        this.sideNotification.init();

        //初始化风格切换
        this.switchSkins.init();

        //初始注销
        this.logout.init();

        //通知中心延时加载
        setTimeout($.proxy(function() {
            this.notification.init();
        }, this), 1000)

        //初始化通知中心
        this.personBox.init();

        //设置总体布局
        this.resizeLayout();

        //浏览器标题闪烁
        this.browserTitle.init();
        
        //tab右键
        this.closeTab();

    },
    //tab右键
    closeTab:function(){
       var task = this.Dom.ht.find('[node-type=taskBarContent]');
       task.find("li").live("mouseenter",function(){
         var self = $(this);
          self.contextmenu({
             target: '#node-menu',
             before: function(e, element) {
                 if(self.find(".close").length>0){
                    $("#cm_close_self").removeClass("disabled");
                 }else{
                    $("#cm_close_self").addClass("disabled"); 
                 }
                 if(self.nextAll().find(".close").length>0){
                    $("#cm_close_right").removeClass("disabled"); 
                 }else{
                    $("#cm_close_right").addClass("disabled"); 
                 }
                 if(self.prevAll().find(".close").length>0){
                    $("#cm_close_left").removeClass("disabled");
                 }else{
                    $("#cm_close_left").addClass("disabled");
                 }
                 if(self.siblings().find(".close").length>0){
                    $("#cm_close_other").removeClass("disabled"); 
                 }else{
                    $("#cm_close_other").addClass("disabled"); 
                 }
                 if((self.siblings().find(".close").length>0) || (self.find(".close").length>0)){
                    $("#cm_close_all").removeClass("disabled"); 
                 }else{
                    $("#cm_close_all").addClass("disabled"); 
                 }
                 return true;
             },
             onItem: function(e, element) {
                    var item = $(element).parent();
                    var activeLi = task.find("li.active").attr("action-data");
                    switch (item.attr('id')) {
                        case 'cm_close_self':
                            if(!item.hasClass("disabled"))
                              self.find(".close").trigger("click");
                            break;
                        case 'cm_close_other':
                            if(!item.hasClass("disabled"))
                              self.siblings().find(".close").trigger("click");
                            break;
                        case 'cm_close_right':
                           if(!item.hasClass("disabled"))
                              self.nextAll().find(".close").trigger("click");
                            break;
                        case 'cm_close_left':
                           if(!item.hasClass("disabled"))
                            self.prevAll().find(".close").trigger("click");
                            break;
                        case 'cm_close_all':
                           if(!item.hasClass("disabled")){
                               self.siblings().find(".close").trigger("click");
                               self.find(".close").trigger("click");
                            }
                            break;
                        case 'cm_refresh':
                            var actionData = self.attr("action-data");
                            if (actionData == "desktop" || actionData == "edu_teacher" || actionData == "edu_leader") {
                              var iframeEle = $("#c_content").find("[action-data='" + actionData + "_iframe']");
                              var eleSrc = iframeEle.attr("src");
                              iframeEle.attr("src", eleSrc);
                            } else {
                              var iframeEle = $("#c_content").find("[action-data='tabs_" + actionData + "_iframe']");
                              var eleSrc = iframeEle.attr("src");
                              iframeEle.attr("src", eleSrc);
                            }
                            break;
                        case 'cm_new_open':
                            var actionData = self.attr("action-data");
                            var eleSrc;
                            if (actionData == "desktop" || actionData == "edu_teacher" || actionData == "edu_leader") {
                               eleSrc = $("#c_content").find("[action-data='" + actionData + "_iframe']").attr("src");
                             } else {
                               eleSrc = $("#c_content").find("[action-data='tabs_" + actionData + "_iframe']").attr("src");
                             }
                             window.open(eleSrc, "_blank");
                             break;
                    }
                    if(task.find("li[action-data='"+activeLi+"']").length>0){
                        task.find("li[action-data='"+activeLi+"']").trigger("click");
                    }
                }
       });
       });
        
    },
    //设置宽度与高度
    resizeLayout: function() {
        var wWidth = $(window).width();
        var wHeight = $(window).height();
        var nHeight = this.Dom.h.outerHeight(true);
        var hHeight = this.Dom.hb.length > 0 ? this.Dom.hb.outerHeight(true) : 0;
        var fHeight = this.Dom.f.outerHeight(true);
        var ccPadding = parseInt(this.Dom.cc.css("paddingLeft")) + parseInt(this.Dom.cc.css("paddingRight"));

        //设置左侧提醒栏区域
        var cHeight = wHeight - nHeight - fHeight - hHeight;
        this.Dom.cf.height(wHeight-150);

        //设置主体内容区域
        this.Dom.cc.height(wHeight-170);
        if (this.Dom.co.length > 0) {
            //设置右侧人员组织列表区域
            var cfHeight = wHeight - this.Dom.co.offset().top - fHeight - hHeight;
            this.Dom.co.height(cfHeight);
            this.Dom.cow.height(cfHeight);

            //设置组织架构高度
            var recentHeight = this.Dom.cow.find(".body").outerHeight(true)  - this.Dom.cow.find("[node-type='tab']").outerHeight(true);
            var orgHeight = recentHeight - this.Dom.cow.find("#search_input").outerHeight(true);
            var recentList = this.Dom.cow.find("[node-data='recent'] .recentlist");
            var orgTree =  this.Dom.cow.find("[node-type='orgTreeNode']");
            var groupList = this.Dom.cow.find("[node-data='group'] .grouplist");
            orgTree.height(orgHeight);
            this.Dom.cow.find("[node-type='orgTreeRSNodeScroll']").height(orgHeight - this.Dom.cow.find("[node-type='orgTreeRS']").outerHeight(true) - 16);
            groupList.height(recentHeight);
            recentList.height(recentHeight);
        } else {
            this.Dom.cc.css({"margin-right": "0"});
        }
        //设置标签任务栏的宽度
        var htWidth = wWidth - this.Dom.co.outerWidth(true) - this.Dom.cf.outerWidth(true);
        var htcWidth = htWidth - 40;
        this.Dom.ht = $("#taskbar");
        this.Dom.ht.outerWidth(htWidth);
        this.Dom.ht.find('[node-type=taskBarContent]').outerWidth(htcWidth);
        this.headTaskBar.resize();

    },
    //顶部应用导航
    headNavBar: {
        navTagName: 'li',
        navNode: 'appParentPanel',
        navClassName: 'active',
        appMenuClassName: 'active',
        appPanelNodeType: 'appSubPanel',
        appMenuPanelNodeType: 'appMenuPanel',
        appNodeType: 'appSubNode',
        appMenuNodeType: 'appMenuSubNode',
        appMenuListNodeType: 'appMenuList',
        subMenuOn: false,
        init: function() {
            //生成nav数据
            this.buildApp(this.bindEvt());
        },
        bindEvt: function() {
        	P.Dom.hn = $("#navbar");
            var navs = P.Dom.hn.find('[node-type="' + this.navNode + '"] ' + this.navTagName);
            var navPanel = P.Dom.hn.find('[node-type="' + this.navNode + '"]');
            var appPanel = P.Dom.hn.find('[node-type="' + this.appPanelNodeType + '"]');
            var menu = this.menu = P.Dom.hn.find('.header_menu');
            var menuList = this.menuList = P.Dom.hn.find('[node-type="' + this.appMenuListNodeType + '"]');
            var menus = this.menus = this.menuList.find(this.navTagName);
            var subMenuPanel = this.subMenuPanel = P.Dom.hn.find('[node-type="' + this.appMenuPanelNodeType + '"]');
            var self = this;
            
            //nav切换
            navs.on({
                'mouseenter': function() {
                    P.Status.headNavInside = "true";
                    var n = $(this).attr("node-data");
                    navs.removeClass(self.navClassName);
                    $(this).addClass(self.navClassName);
                    appPanel.show();
                    P.Dom.hn.find('[node-type="' + self.appNodeType + '"]').hide();
                    self.moduleToPanel(n).show();
                },
                'mouseleave': function() {
                    P.Status.headNavInside = "false";
                    setTimeout(function() {
                        if (P.Status.headNavInside == "false")
                            self.hidePanel();
                    }, 500);
                }
            });

            //初始化更多菜单的位置
            if (menu.offset()) {
                menuList.css({
                    'left': (menu.offset().left - P.Dom.hn.offset().left) + "px",
                    'top': menu.outerHeight(true) + "px"
                });
            }

            menu.find('.menu').on({
                'mouseenter': function() {
                    menuList.show();
                },
                'mouseleave': function() {
                    var that = $(this);
                    setTimeout(function() {
                        if (!self.subMenuOn) {
                            menuList.hide();
                        }
                    }, 200);
                }
            });

            menuList.on({
                'mouseenter': function() {
                    self.subMenuOn = true;
                },
                'mouseleave': function() {
                    var that = $(this);
                    self.subMenuOn = false;
                    setTimeout(function() {
                        if (!self.subMenuOn) {
                            self.hideMenu();
                        }
                    }, 500);
                }
            })

            //menu切换
            menus.on({
                'mouseenter': function() {
                    self.subMenuOn = true;
                    menus.removeClass(self.appMenuClassName);
                    $(this).addClass(self.appMenuClassName);

                    var curPanel = subMenuPanel.find("[node-type='" + self.appNodeType + "'][node-data='" + $(this).attr("node-data") + "']");
                    self.subMenuPanel.find("[node-type='" + self.appNodeType + "']").hide();
                    curPanel.show();

                    var offset = $(this).offset();
                    var top = offset.top - (subMenuPanel.outerHeight(true) - $(this).outerHeight(true)) / 2;

                    self.subMenuPanel.css({
                        'top': top > navs.outerHeight(true) ? top : navs.outerHeight(true),
                        'right': $(window).width() - offset.left + 5
                    }).show();

                },
                'mouseleave': function() {
                    self.subMenuOn = false;
                    setTimeout(function() {
                        if (!self.subMenuOn) {
                            self.hideMenu();
                        }
                    }, 500);
                }
            });

            subMenuPanel.on({
                'mouseenter': function() {
                    self.subMenuOn = true;
                },
                'mouseleave': function() {
                    self.subMenuOn = false;
                    setTimeout(function() {
                        if (!self.subMenuOn) {
                            self.hideMenu();
                        }
                    }, 500);
                }
            });

            //panel二级应用切换显示
            appPanel.on({
                'mouseenter': function() {
                    P.Status.headNavInside = "true";
                },
                'mouseleave': function() {
                    P.Status.headNavInside = "false";
                    self.hidePanel();
                }
            });
        },
        moduleToPanel: function(m) {
            return P.Dom.hn.find("[node-type='" + this.appNodeType + "'][node-data='" + m + "']");
        },
        buildApp: function(fn) {
            var self = this;
            var Html = '';
            var navs = P.Dom.hn.find('[node-type="' + this.navNode + '"] ' + this.navTagName);
            var subMenuPanel = P.Dom.hn.find('[node-type="' + this.appMenuPanelNodeType + '"]');
            P.Dom.hn.find('[node-type="' + self.appPanelNodeType + '"]').width(P.Dom.hn.find('.header_right_top').width());
        },
        openApp: function(o) {
            var openType = $(o).attr("data-open");
            var id = $(o).attr("data-id");
            var name = $(o).find("span").text();
            var url = $(o).attr("data-url");
            if (openType == 0) {
                P.headTaskBar.createTab(id, name, url);
            } else {
                window.open(url);
            }
            this.hidePanel();
        },
        hidePanel: function() {
            P.Dom.hn.find('[node-type="' + this.navNode + '"] ' + this.navTagName).removeClass(this.navClassName);
            P.Dom.hn.find('[node-type="' + this.appPanelNodeType + '"]').hide();
        },
        hideMenu: function() {
            this.menuList.hide();
            this.menus.removeClass(this.appMenuClassName);
            this.subMenuPanel.hide();
        }
    },
    //头部多标签任务栏
    headTaskBar: {
        tabTagName: 'li',
        tabCloseTagName: 'a.close',
        tabEvtType: 'click',
        tabCloseEvtType: 'dblclick',
        tabHoverClass: 'active',
        tabCloseClass: 'uncloseable',
        panelHoverClass: 'active',
        panelTagName: '.tabs_panel',
        taskBarLScrollNodeType: 'taskBarLScroll',
        taskBarRScrollNodeType: 'taskBarRScroll',
        taskBarContentNodeType: 'taskBarContent',
        tabHtml: '<li><a href="javascript:;" hidefocus="hidefocus" class="tab"></a><a href="javascript:;" hidefocus="hidefocus" class="close"></a></li>',
        iframeHtml: '<div class="tabs_panel"><iframe allowtransparency="true" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" scrolling="auto"></iframe></div>',
        scrollIncrement: 100,
        init: function() {
        	P.Dom.ht = $("#taskbar");
            this.taskBarLScroll = P.Dom.ht.find("[node-type='" + this.taskBarLScrollNodeType + "']");
            this.taskBarRScroll = P.Dom.ht.find("[node-type='" + this.taskBarRScrollNodeType + "']");
            this.taskBarContent = P.Dom.ht.find("[node-type='" + this.taskBarContentNodeType + "']");
            this.taskBar = P.Dom.ht.find("[node-type='" + this.taskBarNodeType + "']");
            var self = this;

            //tab切换
            P.Dom.ht.delegate(this.tabTagName, this.tabEvtType, function() {
                self.open($(this).attr('action-data'));
                self.scrollTabVisible(this);
            });

            //tab双击关闭
            P.Dom.ht.delegate(this.tabTagName + ':not(".' + this.tabCloseClass + '")', this.tabCloseEvtType, function() {
                self.close($(this).attr('action-data'));
                return false;
            });

            //tab关闭
            P.Dom.ht.delegate(this.tabTagName + ' ' + this.tabCloseTagName, this.tabEvtType, function(e) {
                e.stopPropagation();
                self.close($(this).parent().attr('action-data'));
                return false;
            });

            this.bindEvt();

        },
        close: function(m) {

            //todo 增加最后一个tab判断

            //移除掉关闭的菜单
            var o = this.moduleToTab(m);
            var next = o.next();
            var prev = o.prev();
            var module = o.attr('action-data');

            //如果有下一个则打开下一个标签
            if (next.length > 0) {
                this.open(next.attr('action-data'));
            } else if (prev.length > 0) {
                this.open(prev.attr('action-data'));
            } else {
                return;
            }

            this.moduleToIframe(module).remove();
            o.remove();
            this.resize();
        },
        open: function(m) {
            this.taskBarContent.find(this.tabTagName).removeClass(this.tabHoverClass);
            this.moduleToTab(m).addClass(this.tabHoverClass);

            P.Dom.cc.find(this.panelTagName).removeClass(this.panelHoverClass);
            this.moduleToIframe(m).addClass(this.panelHoverClass);
        },
        moduleToIframe: function(m) {
            return P.Dom.cc.find("[action-data='tabs_" + m + "']");
        },
        moduleToTab: function(m) {
            return P.Dom.ht.find(this.tabTagName + "[action-data='" + m + "']");
        },
        createTab: function(m, t, u, o, a, c) {
        	this.taskBarContent = $(".taskbar_content");
            P.Dom.cc = $("#c_content");
            
            //m:模块id t:标题 u:打开的链接 o:新窗口打开 a:是否立即激活(true false 默认为true) c:是否允许关闭(true false 默认true)
            var o = arguments[3] == 1 ? true : false;
            var a = arguments[4] != false ? true : false;
            var c = (arguments[5] != true && typeof (arguments[4]) != 'undefined') ? false : true;
            var tabObj = $(this.tabHtml);
            var ifboxObj = $(this.iframeHtml);
            var iframeObj = ifboxObj.find('iframe');

            if (o)
            {
                _width = $(window).width() * 0.6;
                _height = $(window).height() * 0.6;
                _top = ($(window).height() - _height) / 2;
                _left = ($(window).width() - _width) / 2;

                var re = /^http[s]?:\/\//i;
                var re2 = /portal\/default\/route/i;
                if(re.test(u) || re2.test(u)){
                    window.open(u, t, 'height=' + _height + ',width=' + _width + ',top=' + _top + ',left=' + _left + ',toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no')
                } else {
                    window.open(u, t, 'height=' + _height + ',width=' + _width + ',top=' + _top + ',left=' + _left + ',toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no')
                }
                return;
            }
            
            //如果tab已经存在则直接切换过去，不刷新url地址
            if (this.tabExists(m)) {
                this.open(m);
                return;
            }

            //设置tab模块id, 标题, 是否允许关闭
            tabObj.attr('action-data', m);
            tabObj.find('a.tab').html(t);
            if (!c)
                tabObj.addClass('uncloseable').attr('closeable', 'false');

            //设置Iframe外层wrapper属性
            ifboxObj.attr('action-data', 'tabs_' + m);
            
            
            
            //添加tab标签
            this.taskBarContent.append(tabObj);
            P.Dom.cc.append(ifboxObj);

            //是否立即激活
            if (a == true)
                tabObj.trigger('click');//this.open(m);

            //设置iframe链接
            iframeObj.attr({
                'src': u,
                'name': 'tabs_' + m + '_iframe',
                'action-data': 'tabs_' + m + '_iframe'
            });
            if ($.browser.msie && 8 === parseInt($.browser.version)) {
                if (iframeObj.get(0).attachEvent) {
                    iframeObj.get(0).attachEvent("onload", function() {
                        TUtil.ie8FixIcon();
                    });
                } else {
                    iframeObj.get(0).addEventListener("onload", function() {
                        TUtil.ie8FixIcon();
                    }, false);
                }
            }
            this.resize();
        },
        tabExists: function(m) {
            return this.taskBarContent.find(this.tabTagName + "[action-data='" + m + "']").length > 0;
        },
        scrollTabVisible: function(o) {
            var tabsOffsetLeft = $(o).offset().left;
            var tabsWidth = $(o).outerWidth();
            var containerOffsetLeft = this.taskBarContent.offset().left;
            var containerWidth = this.taskBarContent.outerWidth();
            var containerScrollLeft = this.taskBarContent.scrollLeft();
            if (tabsOffsetLeft > containerOffsetLeft && tabsOffsetLeft + tabsWidth > containerOffsetLeft + containerWidth) //要选中的标签的左侧可见，右侧不可见
            {
                var scrollTo = (tabsOffsetLeft + tabsWidth) - (containerOffsetLeft + containerWidth) + containerScrollLeft;
                this.taskBarContent.animate({scrollLeft: scrollTo}, P.Timer.taskBarSpeed);
            }
            else if (tabsOffsetLeft < containerOffsetLeft) //要选中的标签的右侧可见，左侧不可见
            {
                var scrollTo = containerScrollLeft - (containerOffsetLeft - tabsOffsetLeft);
                this.taskBarContent.animate({scrollLeft: scrollTo}, P.Timer.taskBarSpeed);
            }
        },
        bindEvt: function() {
            var self = this;

            this.taskBarLScroll.click(function() {
                var scrollTo = self.taskBarContent.scrollLeft() - self.scrollIncrement;
                if (scrollTo < self.scrollIncrement)//如果不够一个tab宽度，则滚动到头部
                    scrollTo = 0;
                self.taskBarContent.animate({scrollLeft: scrollTo}, P.Timer.taskBarSpeed);
            });


            this.taskBarRScroll.click(function() {
                var scrollTo = self.taskBarContent.scrollLeft() + self.scrollIncrement;
                if (scrollTo + self.scrollIncrement > self.taskBarContent.prop('scrollWidth'))
                    scrollTo = self.taskBarContent.prop('scrollWidth');
                self.taskBarContent.animate({scrollLeft: scrollTo}, P.Timer.taskBarSpeed);
            });

            //鼠标滚轮事件
            this.taskBarContent.mousewheel(function(event, delta, deltaX, deltaY) {
                if (delta < 0)
                    self.taskBarLScroll.trigger('click');
                else
                    self.taskBarRScroll.trigger('click');
                return false;
            });
        },
        resize: function() {
            if (this.taskBarContent.outerWidth(true) < this.taskBarContent.prop('scrollWidth'))
            {
                this.taskBarLScroll.show();
                this.taskBarRScroll.show();
            }
            else
            {
                this.taskBarLScroll.hide();
                this.taskBarRScroll.hide();
            }
        }
    },
    //侧边栏Panel
    sidePanel: {
        tabEvtType: 'click',
        tabHoverClass: 'active',
        panelEvtType: 'click',
        panelCollapsedClass: 'collapsed',
        contentExpandedClass: 'expanded',
        panelBodyClass: '.body',
        init: function() {
            var self = this;
            var o = P.Dom.co;
            var p = P.Dom.co.find("div[node-type='panel']");
            var t = P.Dom.co.find("a[node-type='tab']");
            var oe = P.Dom.co.find("a[node-type='leftExpBtn']");

            //读取cookie设置侧边栏状态
            if (P.Status.sidePanelColse == "true") {
                P.Dom.co.addClass(self.panelCollapsedClass);
                P.Dom.cc.addClass(self.contentExpandedClass);
                P.Dom.cowr.show();
            } else {
                P.Dom.cowo.show();
            }

            //tab切换
            t.bind(this.tabEvtType, function() {
                t.removeClass(self.tabHoverClass);
                $(this).addClass(self.tabHoverClass);
                self.showPanel(p, $(this));
            });

            //关闭/展开
            oe.bind(this.panelEvtType, function(e) {
                e.stopPropagation();
                P.Status.sidePanelColse == "true" ? self.expand() : self.collapse();
                //设置标签任务栏的宽度
                var wWidth = $(window).width();
                var htWidth = wWidth - P.Dom.co.outerWidth(true) - P.Dom.cf.outerWidth(true);
                var htcWidth = htWidth - 40;
                P.Dom.ht.outerWidth(htWidth);
                P.Dom.ht.find('[node-type=taskBarContent]').outerWidth(htcWidth);
                return false;
            });

            //初始化右侧最近联系人列表
            //P.sideRecentList.init();
        },
        showPanel: function(p, i) {
            p.hide();
            this.tabToPanel(p, i).show();
        },
        tabToPanel: function(p, i) {
            var which = i.attr('node-data');
            var _target = null;
            p.each(function() {
                if ($(this).attr('node-data') == which) {
                    _target = $(this);
                    return false;
                }
            });
            var recentList = $("[node-data='recent'] .recentlist");
            var orgTree =  $("[node-type='orgTreeNode']");
            var groupList = $("[node-data='group'] .grouplist");
            if (which == "recent") {
               recentList.height($(".body").height() - $(".tabs").outerHeight(true));
               recentList.niceScroll({cursorcolor: "#ccc"}).show().resize();
               orgTree.niceScroll({cursorcolor: "#ccc"}).hide();
               groupList.niceScroll({cursorcolor: "#ccc"}).hide();
            }else if(which == "group"){
               groupList.height($(".body").height() - $(".tabs").outerHeight(true));
               groupList.niceScroll({cursorcolor: "#ccc"}).show().resize();
               recentList.niceScroll({cursorcolor: "#ccc"}).hide();
               orgTree.niceScroll({cursorcolor: "#ccc"}).hide();
            }else{
               orgTree.height($(".body").height() - $(".tabs").outerHeight(true) - $("#search_input").outerHeight(true));
               orgTree.niceScroll({cursorcolor: "#ccc"}).show().resize();
               recentList.niceScroll({cursorcolor: "#ccc"}).hide();
               groupList.niceScroll({cursorcolor: "#ccc"}).hide();
            }
            return _target;

        },
        expand: function() {
            var self = this;
            P.Dom.co.removeClass(self.panelCollapsedClass);
            P.Dom.cc.removeClass(self.contentExpandedClass);
            P.Dom.cowo.show();
            P.Dom.cowr.hide();
            P.Status.sidePanelColse = 'false';
            $.cookie('PsidePanelColse', 'false', {expires: P.cookieExpires.PsidePanelColse, path: '/'});
            //P.webIm.refresh();
        },
        collapse: function() {
            var self = this;
            P.Dom.co.addClass(this.panelCollapsedClass);
            P.Dom.cc.addClass(this.contentExpandedClass);
            P.Dom.cowo.hide();
            P.Dom.cowr.show();
            P.Status.sidePanelColse = 'true';
            $.cookie('PsidePanelColse', 'true', {expires: P.cookieExpires.PsidePanelColse, path: '/'});
            //P.webIm.refresh();
        }
    },
    
    //左侧提醒与功能区
    sideNotification: {
        nodeType: 'notification',
        boxNodeType: 'fBox',
        nodeClass: 'btn-danger',
        moduleNode: {
            reminder: 'reminder',
            msg: 'msg'
        },
        init: function() {
            var ops = P.Dom.cf.find("a[node-type='" + this.nodeType + "']");
            ops.click(
                function() {
                	$("#smsbox").show();
                }
            );
        }
    },
    //增加皮肤切换功能
    switchSkins: {
        nodeType: 'switchSkin',
        boxNodeType: 'skinBox',
        wins: [],
        init: function() {
            var self = this;
            var ops = P.Dom.cf.find("a[node-type='" + this.nodeType + "']");
            ops.hover(
                function() {
                    var that = $(this);
                    P.Timer.skinBox.trigger.name = setTimeout(function() {
                            clearTimeout(P.Timer.skinBox.show.name);
                            P.popBox.init(
                                {
                                    self: that,
                                    holder: P.Dom.cf
                                },
                                {
                                    direction: 'left',
                                    nodeDom: self.boxNodeType,
                                    title: that.attr('node-tips'),
                                    classname: 'skinbox',
                                    closeAble: 'true',
                                    content: self.build()
                                }
                            );
                        },
                        P.Timer.skinBox.trigger.time
                    );
                },
                function() {
                    clearTimeout(P.Timer.skinBox.trigger.name);
                    P.Timer.skinBox.show.name = P.popBox.closeTimer(self.boxNodeType, P.Timer.skinBox.show.time);
                }
            );
        },
        build: function() {
            var self = this;
            var $dom = $('<div id="skin_wrap" class="clearfix"></div>');
            $.each($CONFIG['skins'], function(k, v) {
				$dom.append('<a href="javascript:;" class="skin_item skin_' + k + '" node-type="skinItem" node-data="' + k + '"><span class="skinbg"></span><span class="skintxt">' + v + '</span></a>');
            });
            $dom.find("a[node-type='skinItem']").on("click", function() {
                var skin = $(this).attr('node-data');
				var imgPath="";
				if(skin =="0"||skin =="1"||skin =="2"){	
					//更换框架的颜色
					$("#skin_8").attr("href","./skin/"+skin+"/style.css");
					//更换消息提醒框的标题背景颜色
					$("#sms_box_id").attr("class","title"+skin);
					//更换按钮图标
					imgPath=contextPath+"/system/frame/8/images/icons/"+skin+"/";
				}else{
					//更换框架的颜色
					$("#skin_8").attr("href","./skin/0/style.css");
					//更换消息提醒框的标题背景颜色
					$("#sms_box_id").attr("class","title0");
					//更换按钮图标
					imgPath=contextPath+"/system/frame/8/images/icons/0/";
					skin = "0";
				}
				setCookie("skin_new" , skin, 365);
				if(skin =="0"){
					setCookie("skinChange" , "orange", 365);
				}else if(skin =="1"){
					setCookie("skinChange" , "blue", 365);
				}else if(skin =="2"){
					setCookie("skinChange" , "green", 365);
				}
				$(".iconsDiv").each(function(i,e){
					var icon = $(e).attr("icon_name");
					var style = "url(\""+imgPath+icon+"\") no-repeat  center";
					$(this).css("background",style);
				});
				
            });
            return $dom;
        }
    },
   
    //增加注销功能
    logout: {
        nodeType: 'logout',
        boxNodeType: 'logoutBox',
        init: function() {
            var self = this;
            var ops = P.Dom.cf.find("a[node-type='" + self.nodeType + "']");
            ops.click(
                function(e)
                {
                    var that = $(this);
                    P.popBox.init(
                        {
                            self: that,
                            holder: P.Dom.cf
                        },
                        {
                            direction: 'left',
                            nodeDom: self.boxNodeType,
                            title: that.attr('node-tips'),
                            classname: 'logoutbox',
                            content: self.build(),
                            blurclose: 'true',
                            closeAble: 'true'
                        }
                    );
                    return false;
                }
            );
        },
        build: function() {
            var $dom = $('<div id="logout_wrap" class="clearfix"></div>');
            $dom.append('<div node-type="logoutIcon" class="logout_icon"></div>');
            var $logoutInfo = $('<div class="logout_info"></div>');
            $dom.append($logoutInfo);
            $logoutInfo.append('<div class="login_username">您好，' + $CONFIG['loginUser'] + '！</div><div class="logout_text">' + $CONFIG['logoutText'] + '</div><div class="logout_tip">确定要注销吗？</div>');
            $dom.append('<div class="logout_button"><a  href=' + $CONFIG['url_logout'] + ' class="btn btn-danger logout_item">确定</a><a href="javascript:;" class="btn logout_item" node-type="close">取消</a></div>');
            return $dom;
        },
        hide: function() {
            $("[node-type='BoxRoot'][node-data='" + this.boxNodeType + "']").hide();
        }
    },
    //通用盒子组件
    popBox: {
        closeAble: false,
        ajaxType: 'json',
        direction: 'left',
        container: 'body',
        errTips: '加载错误请重试',
        blurclose: false,
        template: '<div class="T_layer" node-type="BoxRoot"><div class="bg"><table cellspacing="0" cellpadding="0" border="0"><tbody><tr><td><div class="content" node-type="content"><div class="content_inner clearfix" node-type="inner"></div></div></td></tr></tbody></table><div node-type="layerArr" class="arrow"></div></div></div>',
        init: function(el, opts) {
            this.moveOut = false;
            this.nodeDom = opts.nodeDom;
            this.content = opts.content;
            this.title = opts.title;
            this.classname = opts.classname;
            this.closeAble = opts.closeAble;
            this.ajaxType = opts.ajaxType ? opts.ajaxType : this.ajaxType;
            this.ajaxUrl = opts.ajaxUrl;
            this.ajaxData = opts.ajaxData || {};
            this.dataFormat = opts.dataFormat;
            this.callback = opts.callback || $.noop();
            this.blurclose = opts.blurclose;
            this.direction = opts.direction ? opts.direction : this.direction;
            this.errTips = opts.errTips ? opts.errTips : this.errTips;
            this.boxStyle = opts.boxStyle || $.noop(),
                this.el = el;
            this.container = opts.container ? opts.container : this.container;

            this.box = this.exists() ? $(this.container).find("div[node-type='BoxRoot'][node-data='" + this.nodeDom + "']") : $(this.template);

            if (this.nodeDom) {
                this.box.attr({
                    "node-data": this.nodeDom
                });
            }

            //设定样式来确定默认大小
            if (this.classname) {
                this.box.addClass(this.classname);
            }

            //标题
            this.box.find("[node-type='content']").find("[node-type='title']").remove();
            if (this.title) {
                this.box.find("[node-type='content']").prepend('<div class="title" node-type="title"><span node-type="title_content">' + this.title + '</span></div>');
            }

            //是否允许关闭
            this.box.find("[node-type='content']").find("[node-type='close']").remove();
            if (this.closeAble == "true") {
                this.box.find("[node-type='content']").prepend('<a href="javascript:void(0);" class="T_close" title="关闭" node-type="close"></a>');
            }

            //加载到dom结构
            this.append();

            //设置内容
            this.setContent();

            //绑定事件
            this.bindEvt();

            //设置位置
            this.setPos();

            this.show(this.nodeDom);
        },
        setContent: function() {
            var self = this;
            var _content = '';
            var selfContentDom = $(this.container).find("div[node-type='BoxRoot'][node-data='" + this.nodeDom + "']").find("div[node-type='inner']");

            //ajax动态获取数据
            if (this.ajaxUrl)
            {
                //loading
                $.ajax({
                    type: "POST",
                    url: this.ajaxUrl,
                    data: this.ajaxData,
                    //async: false,
                    dataType: this.ajaxType,
                    beforeSend: function() {
                        selfContentDom.html(P.loading.build("false", {
                            'paddingTop': '15px',
                            'paddingBottom': '15px'
                        }));
                    },
                    success: function(data) {

                        //如果在请求过程中已经移除则，中断
                        if (self.moveOut) {
                            return;
                        }

                        _content = self.ajaxType == "json" ? self.dataFormat(data) : data;

                        selfContentDom.html(_content);

                        //增加成功之后的回调函数
                        if (this.callback) {
                            this.callback();
                        }

                        //填充内容后重新设置位置
                        self.setPos();
                    },
                    error: function() {
                        selfContentDom.html(self.errTips);
                    }
                });
            } else {
                selfContentDom.html(this.content);
                self.setPos();
            }
        },
        setPos: function() {
            //display:none的情况下无法获取到offset
            this.box.show();
            var el = this.el;
            var holderPos = el.holder.offset();
            var selfPos = el.self.offset();
            var arrSize = this.box.find("div[node-type='layerArr']");
            var arrSize_top = arrSize_plus = 0;

            //修正内容增高，外层高度计算错误的问题
            var boxContentHeight = this.box.find(".bg").outerHeight(true);
            if (this.direction == "left" || this.direction == "right")
            {
                //如果到达最底部，则设置最小边距
                if ((selfPos.top + boxContentHeight + P.Dom.f.outerHeight(true) + 5) > $(window).height())
                {
                    this.box.css({
                        "bottom": P.Dom.f.outerHeight(true) + 5 + "px",
                        "top": "auto"
                    });

                    //超过边界值则加上对应的差值
                    arrSize_plus = selfPos.top - this.box.offset().top;
                } else {
                    this.box.css({
                        "top": selfPos.top + "px",
                        "bottom": "auto"
                    });
                }

                if (this.direction == "left")
                {
                    arrSize.css('left', 'none').addClass('arrow_l');
                    this.box.css("left", el.holder.outerWidth(true) + 5 + "px");
                } else {
                    arrSize.css('right', 'none').addClass('arrow_r');
                    this.box.css("right", el.holder.outerWidth(true) + 12 + "px");
                }

                arrSize_top = el.self.height() / 2 - arrSize.height() / 2 + arrSize_plus;

                //如果为负数则设置默认top值
                arrSize.css("top", (arrSize_top > 0 ? arrSize_top : 5) + 'px');

            } else if ((this.direction == "top") || (this.direction == "bottom")) {
                this.box.find("div[node-type='layerArr']").addClass((this.direction == "top") ? 'arrow_t' : 'arrow_b');
                //如果到达最右侧，则设置最小边距
                if ((selfPos.left + this.box.outerWidth(true)) > $(window).width())
                {
                    this.box.css({
                        "right": "5px",
                        "left": "none"
                    });
                    arrSize.css({
                        //"right" : (el.self.width()/2 - arrSize.width()/2 - 5 ) + 'px',
                        "right": $(window).width() - selfPos.left - el.self.outerWidth(true) / 2 - arrSize.width() / 2,
                        "left": "none"
                    });
                } else {
                    this.box.css("left", selfPos.left + "px");
                }

                this.box.css("top", (this.direction == "bottom") ? (selfPos.top - this.box.outerHeight(true) - 5) + "px" : el.self.outerHeight(true) + selfPos.top + 5 + "px");
            }
        },
        bindEvt: function() {
            var self = this;

            //设置鼠标进入事件
            self.box.bind({
                "mouseenter": function() {
                    $(this).attr('status', 'on');
                }
            });

            //设置鼠标离开事件
            if (!self.blurclose) {
                self.box.bind({
                    "mouseleave": function() {
                        $(this).attr('status', 'off').hide();
                    }
                });
            } else {
                self.box.unbind('mouseleave');
            }

            self.box.find("[node-type='close']").click(function() {
                self.box.hide();
            });
        },
        closeTimer: function(n, t) {
            var self = this;
            this.moveOut = true;    //增加移除状态判断
            return setTimeout(function() {
                    if (self.status(n) != 'on')
                        self.hide(n);
                },
                t);
        },
        append: function() {
            if (!this.exists())
                $(this.container).append(this.box);
        },
        exists: function() {
            return $(this.container).find("div[node-type='BoxRoot'][node-data='" + this.nodeDom + "']").length > 0;
        },
        hide: function(n) {
            $(this.container).find("div[node-type='BoxRoot'][node-data='" + n + "']").hide();
        },
        show: function(n) {
            $(this.container).find("div[node-type='BoxRoot'][node-data='" + n + "']").attr('status', 'off').css('visibility', 'visible').show();
        },
        status: function(n) {
            return $(this.container).find("div[node-type='BoxRoot'][node-data='" + n + "']").attr('status');
        }
    },
    //通知中心
   notification: {
        lastQueryTime: 0,
        dom: $("[node-type='BoxRoot'][node-data='notificationBox']"),
        closeNodeType: 'close',
        readAllNodeType: 'readAll',
        listNodeType: 'nocBoxList',
        tipsNodeType: 'nocBoxTips',
        noDataHtml: '<div class="tips_content">暂无新提醒，<span node-type="tips_count"></span>秒钟之后自动关闭</div>',
        template: '<li><p class="noc_item_info"><span class="noc_item_time" node-type="nocItemTime"></span><span class="noc_item_name" node-type="nocItemName"></span><span class="noc_item_type" node-type="nocItemType"></span></p><p class="noc_item_content" node-type="nocItemContent"></p></li>',
        init: function() {
            this.list = $("[node-type='" + this.listNodeType + "']");
            this.tips = $("[node-type='" + this.tipsNodeType + "']");

            this.closeBtn = this.dom.find("[node-type='" + this.closeNodeType + "']");
            this.readAllBtn = this.dom.find("[node-type='" + this.readAllNodeType + "']");

            this.closeBtn.click($.proxy(function() {
                this.close();
            }, this));

            this.readAllBtn.click($.proxy(function() {
                this.readAll();
            }, this));

            $("[node-type='notificationBtn']").click(function() {
                P.notification.refresh(true);
            });

            var self = this;
            this.list.find("ul").delegate("li", "click", function() {
                var self_li = $(this);
                TUtil.openUrl(self_li.attr('data-url'), self_li.attr('data-type-id'));
                $.ajax({
                    type: "POST",
                    url: $CONFIG['url_cancelNotification'],
                    data: {
                        'selectdel[]': new Array(self_li.attr('data-noti-id'))
                    },
                    success: function(data) {
                        if (data == "ok") {
                            if (self_li.parent().children().length == 1) {
                                self.autoClose();
                            }
                            self_li.remove();
                        } else {
                            return;
                        }
                    }
                });
            });
            this.refresh();
        },
        readAll: function() {
            var self = this;
            var arr_ids = new Array();
            this.list.find("ul li").each(function() {
                arr_ids.push($(this).attr('data-noti-id'));
            });
            $.ajax({
                type: "POST",
                url: $CONFIG['url_cancelNotification'],
                data: {'selectdel[]': arr_ids},
                beforeSend: function() {
                    self.loading();
                },
                success: function(data) {
                    if (data == "ok") {
                        self.autoClose();
                    } else {
                        this.tips.empty().html('读取错误').show();
                        return;
                    }
                }
            });
        },
        loading: function() {
            this.list.hide();
            this.tips.empty().html(P.loading.build()).show();
        },
        autoClose: function() {
            this.list.hide().find("ul").empty();
            var tipsCount = $(this.noDataHtml);
            this.tipsCount = tipsCount.find("[node-type='tips_count']");
            this.tips.empty().html(tipsCount);
            this.tipsCount.text(P.Timer.notificationBox.time / 1000);

            this.tips.show();
            clearInterval(P.Timer.notificationBox.name);
            P.Timer.notificationBox.name = setInterval($.proxy(function() {
                this.timerClose()
            }, this), 1000);
        },
        timerClose: function() {
            var time = this.tipsCount.text();
            if (time > 1) {
                this.tipsCount.text(time - 1);
            } else {
                this.close();
            }
        },
        close: function() {
            this.dom.hide();
            this.list.niceScroll({cursorcolor: "#ccc"}).hide();
            P.overLayer.hide();
            clearInterval(P.Timer.notificationBox.name);
            P.Timer.notificationBox.name = null;

            //关闭时开启定时查询
            P.mon.start('notification');
            return;
        },
        show: function(data) {
            var self = this;
            P.overLayer.show();
            this.dom.show();
            P.mon.close('notification');
            if (typeof (data) == 'object') {
                self.tips.hide();
                var $first = self.list.find("ul>li:first");
                $.each(data, function (i, n) {
                    var $tpl = $(self.template);
                    $tpl.attr({
                        'data-noti-id': data[i].id,
                        'data-url': data[i].url,
                        'data-type-id': data[i].notification_type
                    });
                    $tpl.find("[node-type='nocItemTime']").html(data[i].remind_time);
                    $tpl.find("[node-type='nocItemName']").html(data[i].from_name);
                    $tpl.find("[node-type='nocItemType']").html(data[i].notification_type_text);
                    $tpl.find("[node-type='nocItemContent']").html(data[i].content);
                    if($first.length == 0) {
                        self.list.find("ul").append($tpl);
                    } else {
                        if (i == 0) {
                            self.list.find("ul").prepend($tpl);
                        } else {
                            $tpl.before($first);
                        }
                    }

                });
                self.list.show();
                self.list.niceScroll({cursorcolor: "#ccc"}).show();
            } else {
                if(self.list.find("ul>li").length == 0)
                    self.autoClose();
            }
        },
        refresh: function(force) {
            var self = this;
            var now = new Date().getTime();
            if(self.sms() || force) {
                $.getJSON($CONFIG['url_newNotification'], {
                    'now': now,
                    'beginTime': self.lastQueryTime
                }, function (data) {
                    //手工刷新并显示
                    if (force == true) {
                        self.show(data.data);
                    } else {
                        if (data.code == 'ok') {
                            self.show(data.data);
                            P.browserTitle.blink('notification');
                            $('#sys_sound').html($CONFIG['sys_sound']);
                            setTimeout("$('#sys_sound').html('');", 5000);
                        } else {
                            P.Timer.mon.notification.name && P.browserTitle.reset();
                        }
                    }
                });
                self.lastQueryTime = now;
            }
        },
        sms: function () {
            var smsUrl = $CONFIG['url_sms'];
            var flag = false;
            $.ajax({
                type: 'GET',
                url: smsUrl,
                async: false,
                data: {'now': new Date().getTime()},
                success: function(data){
                    if(data.substr(0, 1) == '1') {
                        flag = true;
                    }
                }
            });
            return flag;
        }
    },
    //个人信息面板
    personBox: {
        boxNodeType: 'personBox',
        avatarNodeType: 'avatarImg',
        statusNodeType: 'avatarStatus',
        myInfoPanelNodeType: 'myInfoPanel',
        init: function() {
            this.avatarDom = P.Dom.cf.find("[node-type='" + this.avatarNodeType + "']");
            this.statusDom = P.Dom.cf.find("[node-type='" + this.statusNodeType + "']");
            var status = this.statusDom.attr("node-data");

            //渲染用户在线状态
            this.statusDom.addClass(P.Base.onlineStatus[status].className);
            var self = this;

            this.avatarDom.hover(
                function() {
                    var that = $(this);
                    P.Timer.personBox.trigger.name = setTimeout(function() {
                            clearTimeout(P.Timer.personBox.show.name);
                            P.popBox.init(
                                {
                                    self: that,
                                    holder: P.Dom.cf
                                },
                                {
                                    direction: 'left',
                                    nodeDom: self.boxNodeType,
                                    classname: 'recentbox',
                                    content: self.buildInfoPanel()
                                }
                            );
                        },
                        P.Timer.personBox.trigger.time
                    );
                },
                function() {
                    clearTimeout(P.Timer.personBox.trigger.name);
                    P.Timer.personBox.show.name = P.popBox.closeTimer(self.boxNodeType, P.Timer.personBox.show.time);
                }
            );
        },
        buildInfoPanel: function() {
            if (!this.info)
                this.info = P.Dom.cf.find("[node-type='" + this.myInfoPanelNodeType + "']");
            return $(this.info).css("display", "block");
        }
    },
    overLayer: {
        html: '<div class="home_overlay modal-backdrop fade" node-type="homeOverLayer"></div>',
        init: function() {
            if ($("[node-type=homeOverLayer]").length == 0) {
                $("body").append(this.html);
            }
            this.dom = $("[node-type=homeOverLayer]");
            return this;
        },
        show: function(style) {
            this.init();
            if (style) {
                this.dom.css(style);
            }
            this.dom.addClass('in').show();
        },
        hide: function() {
            this.dom && this.dom.removeClass('in').fadeOut()
        }
    },
    searchPerson: {
        tipsText: {
            hasdata: '查询到<span class="T_redtxt"></span>个相关结果',
            empty: '没有找到与<span class="T_redtxt"></span>相关的结果'
        },
        template: ' <li><div class="person_list_funcs"><a href="javascript:;" class="td-icon icon-bubble-10" node-type="chat" title="微讯" rel="tooltip" data-placement="bottom"></a><a href="javascript:;" class="td-icon icon-envelop" node-type="email" title="邮件" rel="tooltip" data-placement="bottom"></a></div><div class="T_fwb" node-type="userName"></div></li>',
        init: function() {
            var self = this;
            this.parent = P.Dom.cow.find("[node-type='panel'][node-data='org']");
            this.search_input = this.parent.find("#search_input");
            this.org_list_wrapper = this.parent.find("[node-type='orgTreeNode']");
            this.search_list_wrapper = this.parent.find("[node-type='orgTreeSearchNode']");
            this.search_result_tips = this.search_list_wrapper.find("[node-type='orgTreeRS']");
            this.search_list_content = this.parent.find("[node-type='orgTreeRSNodeScroll']");
            this.search_list = this.search_list_wrapper.find("[node-type='orgTreeRSNode']");

            //初始化搜索的结果区域
            this.search_list_content.niceScroll({cursorcolor: "#ccc"});

        },
        search: function() {
            var self = this;
            var str = this.search_input.val();
            if (str == "") {
                this.reset();
                return;
            }
        },
        build: function(data) {
            var self = this;
            $.each(data, function(i, n) {
                var $template = $(self.template);
                $template.attr({
                    'node-data-id': data[i].uid,
                    'node-data-name': data[i].user_name,
                    'node-data-avatar': data[i].user_avatar
                });
                if (data[i].user_name)
                    $template.find("[node-type='userName']").text(data[i].user_name + '（' + data[i].gender + '）');

                if (data[i].dept_name)
                    $template.append('<span>部门：' + data[i].dept_name + '</span>');

                if (data[i].role_name)
                    $template.append('<span>角色：' + data[i].role_name + '</span>');

                if (data[i].mobile)
                    $template.append('<span>手机：' + data[i].mobile + '</span>');

                if (data[i].tel_dept)
                    $template.append('<span>工作电话：' + data[i].tel_dept + '</span>');

                if (data[i].email)
                    $template.append('<span>Email：' + data[i].email + '</span>');

                if (data[i].qq)
                    $template.append('<span>QQ：' + data[i].qq + '</span>');

                self.search_list.append($template);
            });
            this.search_list_content.show();
            return this;
        },
        nodata: function(str) {
            this.showRs().search_result_tips.html(this.tipsText.empty).show().find("span").text(str);
            return this;
        },
        tips: function(data) {
            this.showRs().search_result_tips.html(this.tipsText.hasdata).show().find("span").text(data.length);
            return this;
        },
        showRs: function() {
            this.org_list_wrapper.hide();
            this.search_list_wrapper.show();
            return this;
        },
        reset: function() {
            this.search_result_tips.hide();
            this.search_list_content.hide();
            this.search_list_wrapper.hide();
            this.org_list_wrapper.show();

        }
    },
    headClock: {
        nodeType: 'timeHolder',
        boxNodeType: 'timeBox',
        clockPanelNodeType: 'clockPanel',
        timePanelNodeType: 'realTimePanel',
        init: function() {
            var self = this;
            this.clockDom = P.Dom.hn.find("[node-type='" + self.nodeType + "']");
            this.realTimeDom = P.Dom.hn.find("[node-type='" + this.clockPanelNodeType + "'] [node-type='" + this.timePanelNodeType + "']");
            this.realTime(this.realTimeDom);
            this.clockDom.hover(
                function() {
                    var that = $(this);
                    P.Timer.timeBox.trigger.name = setTimeout(function() {
                            clearTimeout(P.Timer.timeBox.show.name);
                            P.popBox.init(
                                {
                                    self: that,
                                    holder: P.Dom.hn
                                },
                                {
                                    direction: 'top',
                                    nodeDom: self.boxNodeType,
                                    classname: 'timebox',
                                    content: self.build()
                                }
                            );
                        },
                        P.Timer.timeBox.trigger.time
                    );
                },
                function() {
                    clearTimeout(P.Timer.timeBox.trigger.name);
                    P.Timer.timeBox.show.name = P.popBox.closeTimer(self.boxNodeType, P.Timer.timeBox.show.time);
                }
            );
        },
        build: function() {
            if (!this.info)
                this.info = P.Dom.hn.find("[node-type='" + this.clockPanelNodeType + "']");
            return $(this.info).css("display", "block");
        },
        realTime: function(ev) {
            var Y, M, D, W, H, I, S;
            function fillZero(v) {
                if (v < 10) {
                    v = '0' + v;
                }
                return v;
            }
            (function() {
                var d = new Date();
                H = fillZero(d.getHours());
                I = fillZero(d.getMinutes());
                ev.html(H + ':' + I);
                setTimeout(arguments.callee, 1000);
            })();
        }
    },
    onlineStatus: {
        nodeType: 'avatarStatus',
        boxNodeType: 'avatarStatusBox',
        template: '<div class="avatar_status_panel"><ul class="reset"></ul></div>',
        init: function() {
            var self = this;
            this.statusDom = P.Dom.cf.find("[node-type='" + self.nodeType + "']");
            var status = this.statusDom.attr("node-data");
            this.statusDom.addClass(P.Base.onlineStatus[status].className);
            this.statusDom.attr("title", P.Base.onlineStatus[status].desc);
            this.statusDom.click(
                function(e)
                {
                    var that = $(this);
                    P.popBox.init(
                        {
                            self: that,
                            holder: P.Dom.cf
                        },
                        {
                            direction: 'left',
                            nodeDom: self.boxNodeType,
                            classname: 'statusbox',
                            content: self.build(),
                            blurclose: true
                        }
                    );
                    return false;
                }
            );
        },
        build: function() {
            var self = this;
            var $tpl = $(this.template);
            $.each(P.Base.onlineStatus, function(i, n) {
                if (i != 5)
                    $tpl.find("ul").append('<li node-data-status="' + i + '" node-data-class="' + P.Base.onlineStatus[i].className + '" node-data-desc="' + P.Base.onlineStatus[i].desc + '"><span class="' + P.Base.onlineStatus[i].className + '"></span>' + P.Base.onlineStatus[i].desc + '</li>')
            });

            $tpl.find("li").bind('click', function() {
                var status = $(this).attr('node-data-status');
                var classname = $(this).attr('node-data-class');
                var desc = $(this).attr('node-data-desc');
                self.changeStatus(status);
                self.statusDom.attr({
                    'class': 'avatar_status T_online_status ' + classname,
                    'node-data': status,
                    'title': desc
                });
            });

            return $tpl;
        },
        hide: function() {
            $("[node-type='BoxRoot'][node-data='" + this.boxNodeType + "']").hide();
        },
        changeStatus: function(s, callback) {
            $.post($CONFIG['url_updateStatus'], {status: s}, function(data) {
                var b = $.parseJSON(data);
                if (b.code && b.code == 'ok') {
                    $.isFunction(callback)
                    callback();
                }
            });
        }
    },
    browserTitle: {
        init: function() {
            this.orgTitle = document.title;
        },
        blink: function(type) {
            var flag = 0;
            this.typeText = P.Base.title[type];
            this.blankText = '';
            var word = this.typeText.length;
            for (i = 0; i < word; i++) {
                this.blankText += '　';
            }
            if (!P.Timer.browserTitle.name)
            {
                P.Timer.browserTitle.name = setInterval($.proxy(function() {
                    flag ^= 1;
                    document.title = flag == 0 ? "【" + this.blankText + "】" + this.orgTitle : "【" + this.typeText + "】" + this.orgTitle;
                }, this), P.Timer.browserTitle.time);
            }

        },
        reset: function() {
            clearInterval(P.Timer.browserTitle.name);
            P.Timer.browserTitle.name = null;
            document.title = this.orgTitle;
            return this;
        }
    },
    mon: {
        init: function() {
            this.start('message');
            this.start('notification');
        },
        message: function() {
            $.getJSON($CONFIG['url_newMessage'], {'now': new Date().getTime()}, function(data) {
                if (data.code == 'ok') {
                    P.webIm.notify(data.data, data.count);
                    P.browserTitle.blink('message');
                } else {
                    P.Timer.mon.message.name && P.browserTitle.reset();
                }
            });
        },
        notification: function() {
            P.notification.refresh();
        },
        start: function(t) {
            var self = this;
            P.Timer.mon[t].time = $CONFIG['timer_' + t] * 1000;
            eval('P.Timer.mon[t].name = setInterval(self.' + t + ', P.Timer.mon[t].time)');
        },
        close: function(t) {
            clearInterval(P.Timer.mon[t].name);
            P.Timer.mon[t].name = null;
        }
    },
    util: {
        arrayDel: function(a, n) {
            if (n < 0)
                return a;
            else
                return a.slice(0, n).concat(a.slice(n + 1, a.length));
        },
        highlight: function(dom) {
            var css = P.Base.highlightCss;
            var t = 0;
            var n = null;
            dom.removeClass(css);
            var n = setInterval(function() {
                if (t == 5) {
                    clearInterval(n);
                    n = null;
                    return;
                }
                if (!dom.hasClass(css))
                    dom.addClass(css);
                else
                    dom.removeClass(css);
                t++;
            }, 500)
        }
    },
    preLoad: {
        overlayId: 'preOverlay',
        loaderId: 'preLoader',
        progress: $('#preload .progress'),
        top: '40%',
        width: 400,
        init: function() {
            var overlay = $('#' + this.overlayId);
            var loader = $('<div></div>')
                .attr('id', this.preLoader)
                .css({width: this.width})
                .appendTo(overlay);

            var posWidth = $(window).width() - $(loader).width();
            $(loader).css({
                position: 'absolute',
                top: this.top,
                left: Math.round((50 / $(window).width()) * posWidth) + '%'
            });

            var text = $('<div></div>')
                .addClass('text')
                .css("margin-left","150px")
                .html("正在初始化资源")
                .appendTo(loader);

            var progress = $('<div></div>')
                .addClass('progress progress-striped active')
                .appendTo(loader);

            var bar = $('<div></div>')
                .addClass('bar')
                .css({
                    width: '0',
                    height: '100%',
                    transition: "width 0.1s ease"
                })
                .appendTo(progress);
        },
        start: function() {
            this.init();
            var self = this;
            var $overlay = $("#preOverlay");
            var $text = $('.text', $overlay);
            var $bar = $('.bar', $overlay);
            var $progress = $('.progress', $overlay);
            var width = 0;
            var text = '';
            var checkStatus = {image: false, sidePanel: false, app: false};
            var progress = setInterval(function() {
                if ($bar.width() == self.width) {
                    clearInterval(progress);
                    $progress.removeClass('active');
                    $overlay.fadeOut(200, function() {
                        $overlay.remove();
                    });
                } else {
                    if (checkStatus.image == false && preLoadStatus.image == true) {
                        width = width + self.width / 4;
                        text = "图片加载完成";
                        checkStatus.image = true;
                    }
                    else if (checkStatus.sidePanel == false && preLoadStatus.sidePanel == true) {
                        width = width + self.width / 4;
                        text = "布局加载完成";
                        checkStatus.sidePanel = true;
                    }
                    else if (checkStatus.app == false && preLoadStatus.app == true) {
                        width = width + self.width / 4;
                        text = "菜单加载完成";
                        checkStatus.app = true;
                    } else {
                        width = width + self.width / 10;
                    }

                    if (width > self.width)
                        width = self.width;
					$bar.width(width);
                    $text.html(text).css("margin-left","150px");
                }
                $bar.text(parseInt(width / 4) + "%");
            }, 200);
        }
    }
};