var rowAppNum = 8;
var mod = {
    init: function(boxs) {
        var self = this;
        $(boxs).each(function(i) {
            var box = $(boxs).eq(i);
            var status = box.attr("expand-data");
            var opts = box.find("[node-type='modOpt']");
            var opts_exp = box.find("[node-type='modOpt'][node-data='expand']");
            var tabs = box.find("[node-type='modTabItem']");
            var tabs_cont = box.find("[node-type='tabContent']");

            //加载默认的图标
            if (status == 1)
                opts_exp.attr("class", "hd_opts_item icon-arrow-right-3");

            box.find("[node-type='modExtOpt']").click(function() {
                opts_exp.triggerHandler("click");
            })

            //tab事件
            tabs.on("click", function() {
                tabs.removeClass("active");
                $(this).addClass("active");
                var type = $(this).attr("node-data");
                tabs_cont.hide();

                if (box.find("[node-type='tabContent'][node-data='" + type + "']").parents("[node-type='contentLeft']").find(".btn-opts").length > 0) {
                    if (type == "privateFile") {
                        box.find("[node-type='tabContent'][node-data='" + type + "']").parents("[node-type='contentLeft']").find(".btn-opts").show();
                    } else {
                        box.find("[node-type='tabContent'][node-data='" + type + "']").parents("[node-type='contentLeft']").find(".btn-opts").hide();
                    }
                }
                if (box.find("[node-type='tabContent'][node-data='" + type + "']").parents("[node-type='contentRight']").find(".btn-opts").length > 0) {
                    if (type == "favorite" || type == "note" || type == "privateFile") {
                        if (type == "privateFile") {
                            box.find("[node-type='tabContent'][node-data='" + type + "']").parents("[node-type='contentRight']").find(".btn-opts").hide();
                            box.find("[node-type='tabContent'][node-data='" + type + "']").parents("[node-type='contentRight']").find(".btn-upload").show();
                        } else {
                            box.find("[node-type='tabContent'][node-data='" + type + "']").parents("[node-type='contentRight']").find(".btn-opts").show();
                            box.find("[node-type='tabContent'][node-data='" + type + "']").parents("[node-type='contentRight']").find(".btn-upload").hide();
                        }
                    } else {
                        box.find("[node-type='tabContent'][node-data='" + type + "']").parents("[node-type='contentRight']").find(".btn-opts").hide();
                    }
                }
                box.find("[node-type='tabContent'][node-data='" + type + "']").show();
                $(window).trigger("resize");
            });

            //展开/收缩事件
            opts.on("click", function() {
                var op = $(this).attr("node-data");
                if (op == "expand")
                {
                    if (box.hasClass("mod_expand")) {
                        box.attr("expand-data", 0);
                        box.removeClass("mod_expand");
                        opts_exp.attr("class", "hd_opts_item  icon-arrow-down-2");
                    } else {
                        box.attr("expand-data", 1)
                        box.addClass("mod_expand");
                        opts_exp.attr("class", "hd_opts_item icon-arrow-right-3");
                    }
                } else if (op == "remove") {
                    $(this).tooltip('destroy');
                    box.fadeOut(500, function() {
                        box.remove();
                        self.updateSort();
                        self.autoHeight(true);
                    });
                } else if (op == "refresh") {
                    //alert("refresh");
                }
            });
        });

        //组件拖拽
        if ($("#app_list").length == 0) {
            $(".connectedSortable").sortable({
                handle: ".mod_box_hd_wrap",
                opacity: .6,
                revert: true,
                delay: 100,
                tolerance: "pointer",
                connectWith: ".connectedSortable",
                activate: function(event, ui) {
                    $('.ui-sortable-placeholder').height(ui.item.height()).width(ui.item.width() - 4);
                },
                stop: function(event, ui) {
                    if (desktopPos == '1')
                        self.updateSort();
                    self.autoHeight(true);
                }
            }).disableSelection();

            //初始化时自动化两边高度
            //this.autoHeight();
        }
        //标题点击弹窗
        $(document).on('click', '.mod_box ul[node-type="tabContent"] a[href]', function() {
          if($(this).parents(".mod_box").attr("node-data")!="eduarrangecourse.default")
            TUtil.openUrl(this.href);
            return false;
        });

        //刷新事件
        $(document).on('click', '.mod_box .hd_opts_item[node-data="refresh"]', function() {
            var self = $(this);
            var mod = self.parents('.mod_box').attr('node-data');
            var $ul = self.parents('.mod_box').find('ul');
            self.removeClass("icon-loop-3").addClass('loading').removeAttr("node-data");
            $.ajax({
                type: "POST",
                url: refreshUrl,
                data: {"mod": mod, "enableUserLines": enableUserLines},
                dataType: "json",
                success: function(data) {
                    var node = $ul.parents('.mod_box').find(".active").attr("node-data");

                    setTimeout(function() {
                        self.removeClass('loading').addClass('icon-loop-3').attr({"node-data":"refresh"});
                        //多标签
                        if ($.type(data) === "object") {
                            $ul.each(function() {
                                if ($(this).attr("node-data") != "calendar") {
                                    $(this).empty().append(data[$(this).attr("node-data")]);
                                } else {
                                    natDays = $.parseJSON(data['calendar']);
                                    function nationalDays(natDays) {
                                        var k;
                                        for (k in natDays) {
                                            if ((date.getFullYear() == (natDays[k]['start'][2]))) {
                                                if ((natDays[k]['start'][0] == (date.getMonth() + 1)) && ((date.getDate() == natDays[k]['start'][1]))) {
                                                    return [true, '', natDays[k]['event']];
                                                }
                                            }
                                        }
                                        return [false, ''];
                                    }
                                    $('#calendar').datepicker({beforeShowDay: nationalDays});
                                    $('#calendar').find('.ui-datepicker-prev').trigger('click');
                                    $('#calendar').find('.ui-datepicker-next').trigger('click');
                                }
                            });
                        } else {
                            if (mod.indexOf("calendar") < 0) {
                                $ul.empty().append(data[0]);
                            } else {
                                natDays = $.parseJSON(data[0]);
                                function nationalDays(natDays) {
                                    var k;
                                    for (k in natDays) {
                                        if ((date.getFullYear() == (natDays[k]['start'][2]))) {
                                            if ((natDays[k]['start'][0] == (date.getMonth() + 1)) && ((date.getDate() == natDays[k]['start'][1]))) {
                                                return [true, '', natDays[k]['event']];
                                            }
                                        }
                                    }
                                    return [false, ''];
                                }
                                $('#calendar').datepicker({beforeShowDay: nationalDays});
                                $('#calendar').find('.ui-datepicker-prev').trigger('click');
                                $('#calendar').find('.ui-datepicker-next').trigger('click');
                            }
                        }
                    }, 3000);
                }
            });
        });
    },
    autoHeight: function(a) {
        var rh = lh = 0;
        if (a)
        {
            var cr = $("div[node-type='contentRight']");
            var cl = $("div[node-type='contentLeft']");

            cr.find("div[node-type='modBox']").each(function() {
                rh += $(this).outerHeight(true);
            });

            cl.find("div[node-type='modBox']").each(function() {
                lh += $(this).outerHeight(true);
            });
            //console.log(rh + "-" + lh);
            if (rh > lh) {
                cl.height(rh);
                cr.height(rh);
            } else {
                cl.height(lh);
                cr.height(lh);
            }
        } else {
            rh = $("div[node-type='contentRight']").outerHeight(true);
            lh = $("div[node-type='contentLeft']").outerHeight(true);
            rh > lh ? $("div[node-type='contentLeft']").height(rh) : $("div[node-type='contentRight']").height(lh);
        }
    },
    updateSort: function() {
        var new_order_left = [];
        var new_order_right = [];
        $("div[node-type='contentRight'] div[node-type='modBox']").each(function() {
            new_order_right.push($(this).attr("node-id"));
        });
        $("div[node-type='contentLeft'] div[node-type='modBox']").each(function() {
            new_order_left.push($(this).attr("node-id"));
        });

        $.post(updateSortUrl, {'left': new_order_left, 'right': new_order_right});
    }
};
var overlay = {
    template: '<div class="overlayer fade" node-type="overLayer"></div>',
    init: function() {
        $('body').append(this.template);
        this.dom = $("[node-type='overLayer']");
    },
    show: function() {
        this.dom.show();
    },
    hide: function() {
        this.dom.hide();
    }
}
//判断有没有存在数组中
function inArray(e, o) {
    var flag = false;
    $.each(o, function(i, n) {
        if (typeof (o[i]) == 'array' || typeof (o[i]) == 'object') {
            if (inArray(e, o[i])) {
                flag = true;
                return false;
            }
        } else {
            if (e == o[i]) {
                flag = true;
                return false;
            }
        }
    });
    return flag;
}
function initTrash() {
    $("#trash").droppable({
        over: function() {
            $("#trash").addClass("hover");
        },
        out: function() {
            $("#trash").removeClass("hover");
        },
        drop: function(event, ui) {
            ui.draggable.addClass("remove").hide();
            delModule && delModule(ui.draggable.attr("index"));
            $(".ui-sortable-placeholder").animate({
                width: "0"
            }, "normal", function() {
            });
            $("#trash").removeClass("hover");
        }
    });
}
//lp 2013/7/15 6:36:38 应用盒子
var appbox = {
    base: {
        'screen_max_app_num': 32
    },
    init: function() {
        this.dom = $("[node-type='BoxRoot'][node-data='AppBox']");
        this.cateListDom = this.dom.find("[node-type='appCateList']");
        this.cateDetailDom = this.dom.find("[node-type='appCateDetail']");
        this.cateDetailDomWrap = this.dom.find("[node-type='appCateDetailWrap']");
        this.closeBtn = this.dom.find("[node-type='close']");
        this.screenListDom = $("[node-type='screenList']");
        var self = this;
        this.cateListDom.parent().niceScroll({cursorcolor: "#ccc"});
        //绑定关闭按钮
        this.closeBtn.bind('click', function() {
            self.dom.hide();
            overlay.hide();
            self.cateListDom.parent().niceScroll({cursorcolor: "#ccc"}).hide();
            self.cateDetailDomWrap.niceScroll({cursorcolor: "#ccc"}).hide();
        });

        //绑定一级菜单分类点击事件
        this.cateListDom.find("li").live("click", function() {
            self.changeCate($(this).attr("app_cate_id"));
        });

        //绑定点击应用事件
        this.cateDetailDom.find("li").live("click", function() {
            var appid = $(this).attr("app_id");
            var appname = $(this).attr("app_name");
            var appicon = $(this).attr("app_icon");
            var appsrc = $(this).attr("app_src");
            var appurl = $(this).attr("app_path");
            var appisopen = $(this).attr("is_open");
            if (getAppNums() > self.base.screen_max_app_num) {
                alert('当前屏幕应用不能超过' + self.base.screen_max_app_num + '个！');
                return false;
            }
            addApp({
                "func_id": appid,
                "id": appid,
                "name": appname,
                "icon": appicon,
                "src": appsrc,
                "url": appurl,
                "isopen": appisopen
            }, slideBox.getCursor());
            var flag = serializeSlide();

            if (flag) {
                $(this).fadeOut(($.browser.msie ? 1 : 300), function() {
                    $(this).remove();
                });

                $('#add_success').animate({
                    opacity: 'show'
                }, "slow", "swing", function() {
                    $(this).animate({
                        opacity: 'hide'
                    })
                }).text("应用添加成功！");
                $(window).trigger("resize");
            } else {
                $('#add_failed').animate({
                    opacity: 'show'
                }, "slow", "swing", function() {
                    $(this).animate({
                        opacity: 'hide'
                    })
                });
            }
        });
    },
    show: function() {
        var self = this;

        //已经初始化过了
        if (this.isInit)
        {
            //重新点击最后点过的值
            if (this.lastCate) {
                this.changeCate(this.lastCate);
            }

        } else {

            //一级菜单
            $.each(appcate || [], function(i, n) {
                if (!self.lastCate)
                    self.lastCate = appcate[i][3];
                self.cateListDom.append('<li app_cate_id=' + appcate[i][3] + '><a href="javascript:;">' + appcate[i][0] + '</a></li>');
            });

            this.isInit = true;
        }
        this.refixDialogPos();
        overlay.show();
        this.dom.show();
        self.cateListDom.parent().niceScroll({cursorcolor: "#ccc"}).show();
        self.cateListDom.parent().niceScroll({cursorcolor: "#ccc"}).resize();
        self.cateDetailDomWrap.niceScroll({cursorcolor: "#ccc"}).show();
        self.cateDetailDomWrap.niceScroll({cursorcolor: "#ccc"}).resize();
        self.screenListDom.parent().niceScroll({cursorcolor: "#ccc"}).hide();
    },
    changeCate: function(i) {
        var self = this;
        var data = appcatedata[i];
        //样式
        this.cateListDom.find("li").removeClass("active");
        this.cateListDom.find("li[app_cate_id=" + i + "]").addClass("active");

        self.cateDetailDom.empty();
        $.each(data || [], function(k, n) {
            if (!inArray(parseInt(data[k]), funcIdObj)) {
                //            self.cateDetailDom.append('<li app_id=' + data[k] + ' app_name=' + funcarray[data[k]]['name'] + ' app_icon=' + funcarray[data[k]]['icon'] +'><a href="javascript:;" title="' + funcarray[data[k]]['name'] +'"><div class="img '+ funcarray[data[k]]['icon'] +'"></div><span>' + funcarray[data[k]]['name'] +'</span></a></li>');
                self.cateDetailDom.append('<li app_id=' + data[k] + ' app_name=' + funcarray[data[k]]['name'] + ' app_icon=' + funcarray[data[k]]['icon'] + ' app_src=' + funcarray[data[k]]['src'] + '><a href="javascript:;" title="' + funcarray[data[k]]['name'] + '"><div class="img"><img src="' + funcarray[data[k]]['src'] + '"></img></div><span>' + funcarray[data[k]]['name'] + '</span></a></li>');
            }
        });
        self.cateDetailDomWrap.niceScroll({cursorcolor: "#ccc"}).show();
        self.cateDetailDomWrap.niceScroll({cursorcolor: "#ccc"}).resize();

        //记住最后点击的值
        this.lastCate = i;
    },
    refixDialogPos: function() {
        var dialog = this.dom;
        height = dialog.height();
        width = dialog.width();
        var wWidth = (window.innerWidth || (window.document.documentElement.clientWidth || window.document.body.clientWidth));
        var hHeight = (window.innerHeight || (window.document.documentElement.clientHeight || window.document.body.clientHeight));
        var top = left = 0;
        var bst = document.body.scrollTop || document.documentElement.scrollTop;
        top = Math.round((hHeight - height) / 2 + bst) + "px";
        mleft = "-" + Math.round(width / 2) + "px";
        top = top < 0 ? top = 0 : top;
        dialog.css({
            "top": top,
            "left": "50%",
            "margin-left": mleft
        });
        return this;
    }
};
//盒子设置按钮
var appBoxSet = {
    init: function() {
        this.appDom = $("[node-type='changeTypeDom'][node-data='app']");
        this.screenDom = $("[node-type='changeTypeDom'][node-data='screen']");
        this.dom = $("[node-type='BoxRoot'][node-data='AppBox']");
        this.cateListDom = this.dom.find("[node-type='appCateList']");
        this.cateDetailDomWrap = this.dom.find("[node-type='appCateDetailWrap']");
        this.screenListDom = $("[node-type='screenList']");
        var self = this;

        $("[node-action='changeType']").live("click", function() {
            var type = $(this).attr("node-type");
            $("[node-action='changeType']").removeClass('active');
            $(this).addClass('active');
            self.changeType(type);
        });

        //默认打开app
        this.lastType = 'app';
    },
    changeType: function(t) {
        if (t == "app")
        {
            $("[node-type='changeTypeDom']").hide();
            this.appDom.show();
            appbox.show();
            this.lastType = 'app';
            this.screenListDom.parent().niceScroll({cursorcolor: "#ccc"}).hide();
        } else {
            $("[node-type='changeTypeDom']").hide();
            this.screenDom.show();
            this.cateListDom.parent().niceScroll({cursorcolor: "#ccc"}).hide();
            this.cateDetailDomWrap.niceScroll({cursorcolor: "#ccc"}).hide();
            this.screenListDom.parent().niceScroll({cursorcolor: "#ccc"}).show();
            this.screenListDom.parent().niceScroll({cursorcolor: "#ccc"}).resize();
            this.lastType = 'screen';
        }
    },
    run: function() {
        $("[node-action='changeType'][node-type='" + this.lastType + "']").trigger('click');
    }
};
//lp 获取当前屏幕应用的个数
function getAppNums(index) {
    var index = (index == "" || typeof (index) == "undefined") ? slideBox.getCursor() : index;
    var num = $(".screen:eq(" + index + ") ul li.block").size();
    return num;
}
//添加桌面应用 e {"func_id": ,"id": ,"name":} index 为要添加应用的屏幕索引
function addApp(e, index) {
    var s = slideBox.getScreen(index);
    if (!s.find(".ul_section").length) {
        s.append("<div class='ul_section'></div>");
        s = s.find(".ul_section");
    }
    if (s)
    {
        var ul = s.find("ul");
        if (!ul.length) {
            ul = $("<ul></ul>");
            s.append(ul);
            ul.sortable({
                revert: true,
                //delay: 200,
                distance: 10, //延迟拖拽事件(鼠标移动十像素),便于操作性
                tolerance: 'pointer', //通过鼠标的位置计算拖动的位置*重要属性*
                connectWith: ".screen ul",
                scroll: false,
                stop: function(e, ui) {
                    setTimeout(function() {
                        $("#trash").hide();
                        ui.item.click(openUrl);
                        serializeSlide();
                    }, 0);
                },
                start: function(e, ui) {
                    $("#trash").show();
                    ui.item.unbind("click");
                }
            });
        }
        addModule(e, s.find("ul"));
    }
}
function addModule(e, el) {
    el = $(el);
    var _id = e.id;
    var li = $("<li class=\"block\"></li>");
    var img = $("<div class='img'><p></p></div>");
    //   img.find("p").append("<div class='icon " + e.icon +"'></div>");
    img.find("p").append("<img src='" + e.src + "'></img>");
    var divT = $("<div class=\"count\"></div>");
    li.attr({
        "id": "block_" + e.id,
        "title": e.name,
        "index": e.id
    });
    divT.attr("id", "count_" + e.id);
    if (0 < e.count && e.count <= 9) {//个数
        divT.addClass("count" + e.count);
    } else if (e.count > 9) {
        divT.addClass("count10");
    }
    var span = $("<span class='app_name ellipsis'></span>").text(e.name);
    li.append(img.append(divT)).append(span);
    el.append(li);
}
function delModule(el) {
    var pObj = $("#inner_app_list .screen ul li.block");
    pObj.each(function() {
        var index = $(this).attr("index");
        if (el == index) {
            $(this).remove();
            var flag = serializeSlide();
        }
    });
}
function getAppMargin() {
    var clientSize = $(document.body).outerWidth(true);
    var appsize = 120 * rowAppNum;
    if (clientSize > appsize) {
        var _margin = Math.floor((clientSize - appsize - 70 * 2) / 16);
    } else {
        var _margin = 0;
    }
    return _margin;
}
function initMenus()
{
    var modules = [];
    var screen_count = 0;
    for (var i = 0; i < funcIdObj.length; i++)
    {
        var item_array = funcIdObj[i];
        if (item_array == "")
            continue;

        var items = [];
        var item_count = 0;
        for (var j = 0; j < item_array.length; j++)
        {
            var func_id = item_array[j];
            if (func_id == "" || !funcarray[func_id])
                continue;

            items[item_count++] = {
                id: funcarray[func_id]['id'],
                name: funcarray[func_id]['name'],
                icon: funcarray[func_id]['icon'],
                src: funcarray[func_id]['src'], //add test
                url: funcarray[func_id]['url'],
                isopen: funcarray[func_id]['isopen'],
                count: 0
            };
        }
        modules[screen_count++] = {
            items: items
        };
    }

    return modules;
}
function initModules(modules, el) {
    window.slideBox = $("#inner_app_list").slideBox({
        count: modules.length,
        cancel: isTouchDevice() ? "" : ".block",
        obstacle: "200",
        speed: "slow",
        touchDevice: isTouchDevice(),
        control: "#control .control-c"
    });
    el = $(el);
    var count = 0;
    $.each(modules || [], function(i, e) {
        slideBox.getScreen(i).append("<div class='ul_section'></div>")
        var ul = $("<ul></ul>");
        slideBox.getScreen(i).find(".ul_section").append(ul)
        $.each(e.items || [], function(j, e) {
            addModule(e, ul);
        });
        i++;
    });
}
//屏幕设置
var screenBox = {
    init: function() {
        this.screenListDom = $("[node-type='screenList']");

        //生成虚拟屏幕
        var _len = slideBox.getCount();
        for (var i = 0; i < _len; i++)
            this.screenListDom.append('<li class="minscreenceil" index=' + i + '>' + (i + 1) + '</li>');
        this.screenListDom.append("<li id='btnAddScreen' class='no-draggable-holder icon-plus' title='添加屏幕'></li>");
        this.addBtnDom = this.screenListDom.find("#btnAddScreen");

        var self = this;
        this.screenListDom.parent().niceScroll({cursorcolor: "#ccc"}).show();
        this.screenListDom.parent().niceScroll({cursorcolor: "#ccc"}).resize();
        //调整虚拟屏幕位置事件
        this.screenListDom.sortable({
            cursor: 'move',
            tolerance: 'pointer',
            cancel: '#btnAddScreen',
            stop: function() {
                var arrScreen = new Array();
                $(this).find("li").each(function() {
                    arrScreen.push($(this).attr("index"));
                });
                slideBox.sortScreen(arrScreen);
                $(this).find("li").each(function(i) {
                    $(this).attr("index", i);
                });
                var flag = serializeSlide();
                if (flag)
                    alert('桌面顺序已设置成功！');
            }
        });

        //添加屏幕
        this.addBtnDom.bind("click", function() {
            slideBox.addScreen();
            slideBox.scroll(slideBox.getCount() - 1);
            var _max = 0;
            self.screenListDom.find(".minscreenceil").each(function() {
                _max = _max > parseInt($(this).attr("index")) ? _max : parseInt($(this).attr("index"));
            });
            $(this).before("<li class='minscreenceil' index='" + (_max + 1) + "'>" + (_max + 2) + "</li>");
            var flag = serializeSlide();
            $('#add_success').animate({
                opacity: 'show'
            }, "slow", "swing", function() {
                $(this).animate({
                    opacity: 'hide'
                })
            }).text("屏幕添加成功！");
        });

        //鼠标滑过屏幕样式
        this.screenListDom.find(".minscreenceil").live({
            'mouseenter': function() {
                $(this).css({
                    "font-size": "60px"
                });
                if ($('span.closebtn', this).length <= 0)
                    $(this).append("<span class='closebtn' title='移除此屏'></span>");
                $('span.closebtn', this).show();
            },
            'mouseleave': function() {
                $(this).css({
                    "font-size": ""
                });
                $('span.closebtn', this).hide();
            }
        });

        //删除屏幕
        this.screenListDom.find(".minscreenceil span").live("click", function() {
            var self1 = $(this);
            confirm('删除桌面，将删除桌面全部应用模块，确定要删除吗？', function(ret) {
                if (ret == true) {
                    var currentDom = self1.parent("li");
                    slideBox.removeScreen(currentDom.index("li.minscreenceil"));
                    var flag = serializeSlide();
                    if (flag)
                    {
                        $('#add_success').animate({
                            opacity: 'show'
                        }, "slow", "swing", function() {
                            $(this).animate({
                                opacity: 'hide'
                            })
                        }).text("桌面删除成功！");
                        currentDom.remove();
                        self.reSortMinScreen();
                    }
                }
            });
        });

    },
    reSortMinScreen: function() {
        this.screenListDom.find(".minscreenceil").each(function(i) {
            $(this).text(i + 1);
            $(this).attr("index", i);
        });
    }
}
function initBlock()
{
    $('#app_list .screen ul li.block').live("click", openUrl);
}
function openUrl()
{
    var id = this.id.substr(6);
    var _d = funcarray[id];
    window.parent.P && window.top.P.headTaskBar.createTab(id, _d.name, _d.url, _d.isopen);
}
$(function() {
    mod.init($(".mod_box"));
    $(window).resize(function() {
        var height = $(window).height();
        var c_height = $("#app_list").length > 0 ? height - $("#app_list").outerHeight(true) : height;
        $("#app_list .screen ul").each(function() {
            var a_w = $(this).find("li").outerWidth(true);
            $(this).width(a_w * ($(this).find("li").length) + 40);
            $(this).parent().niceScroll({cursorcolor: "#ccc", "cursorwidth": "8px"}).resize();
        });
        $(".content_main,.content_main_wrap").height(c_height);
        $(".content_main").niceScroll({styler: "fb", cursorcolor: "#ccc", "cursorwidth": "8px"});
        if ($("#app_list").length > 0) {
            var ul_height = 0;
            $(".content_main_minheight  ul:visible").each(function() {
                ul_height = $(this).outerHeight(true) > ul_height ? $(this).outerHeight(true) : ul_height;
            });
            var ul_height = ul_height + 20;
            var h_height = $(".content_main_minheight  .mod_box_hd").outerHeight(true);
            var min_height = c_height - 20 > 300 ? c_height - 20 : "300";
            $(".content_main_minheight  .mod_box_wrap").height((ul_height + h_height) - 20 > min_height ? ul_height + h_height - 20 : min_height);
            var btnUpload = $(".content_main_minheight").find(".btn-upload");
            btnUpload.css("right", parseInt(btnUpload.parents(".content_main_minheight").find(".mod_box").css("margin-right")) + 20 + "px");
        }
    });
    $(window).trigger("resize");


    $('.mod_hd_title').click(function() {
        var app = $(this).attr('app');
        if (app && window.parent.P) {
            var $a = $(window.parent.document).find('.header_sub_main a[data-id=' + app + ']');
            $a && $a.click();
        }
    });
    initBlock();
    //序列化桌面上的图标,并且更新
    function serializeSlide() {
        var s = {};

        $(".screen").each(function(i, e) {
            s[i] = new Array();
            $(this).find("li").each(function(j, el) {
                if (!$(el).attr("index"))
                    return true;
                s[i].push($(el).attr("index"));
            });
        });

        var flag = false;
        $.ajax({
            type: 'POST',
            async: false,
            data: {
                "app_id": s
            },
            url: changeSortUrl,
            success: function(r) {
                data = $.parseJSON(r);
                if (data.code == "ok") {
                    $.notify({type: 'success', message: {text: data.tips, icon: 'icon-checkmark'}}).show();
                } else {
                    $.notify({type: 'error', message: {text: data.tips, icon: 'icon-info'}}).show();
                }
            }
        });
    }

    function isTouchDevice() {
        try {
            document.createEvent("TouchEvent");
            return true;
        } catch (e) {
            return false;
        }
    }

    if (showType == "mix") {
        getCounts();
        initModules(initMenus());
        //遮罩层
        overlay.init();
        appbox.init();
        appBoxSet.init();

        //垃圾箱
        initTrash();
        //显示屏幕设置
        screenBox.init();
    }
    function getCounts()
    {
        $.ajax({
            type: 'GET',
            dataType: 'json',
            url: queryCount,
            success: function(data) {
                $.each(data || [], function(i, n) {
                    var count = Math.min(10, data[i]);
                    var className = count > 0 ? ('count count' + count) : 'count';
                    if ($('#count_' + i).length > 0)
                        $('#count_' + i).attr('class', className);
                });
                window.setTimeout(getCounts, queryCountTime * 1000);
            },
            error: function(request, textStatus, errorThrown) {
                window.setTimeout(getCounts, queryCountTime * 1000);
            }
        });
    }


    //初始化屏幕
    $(".screen ul").sortable({
        revert: true,
        //delay: 200,
        distance: 10, //延迟拖拽事件(鼠标移动十像素),便于操作性
        tolerance: 'pointer', //通过鼠标的位置计算拖动的位置*重要属性*
        connectWith: ".screen ul",
        scroll: false,
        stop: function(e, ui) {
            setTimeout(function() {
                $("#trash").hide();
                ui.item.click(openUrl);
                serializeSlide();
            }, 0);
        },
        start: function(e, ui) {
            $("#trash").show();
            ui.item.unbind("click");
        }
    });
    //初始化屏幕
    $("#favorite_list").live("mouseenter", function() {
        $("#favorite_list").sortable({
            revert: true,
            //delay: 200,
            distance: 10, //延迟拖拽事件(鼠标移动十像素),便于操作性
            tolerance: 'pointer', //通过鼠标的位置计算拖动的位置*重要属性*
            connectWith: "#favorite_list",
            scroll: false,
            stop: function(e, ui) {
                setTimeout(function() {
                    ui.item.click(openUrl);
                    changeOrder();
                }, 0);
            },
            start: function(e, ui) {
                ui.item.unbind("click");
            }
        });
    });
    //lp 绑定“界面设置”事件
    $("#openAppBox").click(function() {

        //控制操作区域
        //  refixminScreenbtn();

        //显示应用盒子
        appbox.show();

        //应用设置和屏幕设置的操作
        appBoxSet.run();

    });
    $("#control .control-c").live("click", function() {
        var self = $(this);
        $(window).trigger("resize");
        $("#app_list .screen ul").each(function(i) {
            $(this).parent().niceScroll({cursorcolor: "#ccc", "cursorwidth": "8px"}).show();
            $(this).parent().niceScroll({cursorcolor: "#ccc", "cursorwidth": "8px"}).resize();
        });
    });
    if ($('#FileFolderPrivate-form').length > 0) {
        $('#FileFolderPrivate-form').fileupload({
            dataType: 'json',
            autoUpload: true,
            stop: function(e) {
                var that = $(this).data('fileupload');
                that._transition($(this).find('.fileupload-progress')).done(
                        function() {
                            $(this).find('.progress')
                                    .attr('aria-valuenow', '0')
                                    .find('.bar').css('width', '0%');
                            $(this).find('.progress-extended').html('&nbsp;');
                            that._trigger('stopped', e);
                        }
                );
                $("div[node-type='contentLeft']").find("[node-data='refresh']").trigger("click");
            },
            change: function(e, data) {
                $("#upload .fileupload-buttonbar .fileinput-button").addClass("move-bottom");
                $("#upload .btn-add").addClass("hide-button");
                $("#upload .message-info").hide();
                $("#upload .fileupload-buttonbar .start").show();
                $("#upload .fileupload-buttonbar .start").addClass("move-right");
            }
        });
    }
});
//序列化桌面上的图标,并且更新
function serializeSlide() {
    var s = {};
    $(window).trigger("resize");
    $("#inner_app_list .screen").each(function(i, e) {
        s[i] = new Array();
        $(this).find("li.block").each(function(j, el) {
            if (!$(el).attr("index"))
                return true;
            s[i].push($(el).attr("index"));
        });
    });

    var flag = false;
    $.ajax({
        type: 'POST',
        async: false,
        data: {
            "app_id": s
        },
        url: changeSortUrl,
        success: function(r) {
            data = $.parseJSON(r);
            if (data.code == "ok") {
                flag = true;

                //重新赋值给funcIdObj
                funcIdObj = s;
            }
        }
    });
    return flag;
}
//更新网站排序号
function changeOrder() {
    $(window).trigger("resize");
    var s = new Array();
    $("#favorite_list a").each(function(i, e) {
        s.push($(this).attr("index"));
    });
    var flag = false;
    $.ajax({
        type: 'POST',
        async: false,
        data: {
            "ids[]": s
        },
        url: changeFavoriteSortUrl,
        success: function(r) {
            data = $.parseJSON(r);
            if (data.code == "ok") {
                flag = true;
                $.notify({type: 'success', message: {text: data.tips, icon: 'icon-checkmark'}}).show();
            }
        }
    });
    return flag;
}
var privateFile = {
    showUpload: function() {
        $.ajax({
            url: uploadInfoUrl,
            data: {'type': 'isover'},
            type: "POST",
            dataType: "html",
            success: function(data) {
                if (data) {
                    $('#FileFolderPrivate-form .folder_id').remove();
                    $('#FileFolderPrivate-form').append('<input type="hidden" value="' + rootId + '" class="folder_id" id="folder_id" name="folder_id"/>');
                    $("#upload").modal({
                        "backdrop": "static",
                        "show": true
                    });
                    privateFile.changeUpload();
                } else {
                    $.notify({type: 'danger', message: {text: '您的个人文件柜已超过容量限制(' + folderCapacity + ' MB)，请清除您的无用文件！', icon: 'icon-error'}}).show();
                }
            }
        });
    },
    changeUpload: function() {
        if ($("#upload table").find("tr").length > 0) {
            $("#upload .fileupload-buttonbar .fileinput-button").addClass("move-right");
            $("#upload .btn-add").addClass("hide-button");
            $("#upload .message-info").hide();
            $("#upload .fileupload-buttonbar .start").addClass("move-bottom");
            $("#upload .btn-add").addClass("hide-button");
            $("#upload .fileupload-buttonbar .start").show();
        } else {
            $("#upload .fileupload-buttonbar .fileinput-button").removeClass("move-bottom");
            $("#upload .message-info").show();
            $("#upload .btn-add").removeClass("hide-button");
            $("#upload .fileupload-buttonbar .start").hide();
            $("#upload .fileupload-buttonbar .start").removeClass("move-right");
        }
    }
}
//新建网址
function createFavorite() {
    _width = $(window).width() * 0.7;
    _height = $(window).height() * 0.9;
    _top = ($(window).height() - _height) / 2;
    _left = ($(window).width() - _width) / 2;
    var type = $("[node-type='contentRight'] .mod_hd_tabs").find(".active").attr("node-data");
    if (type == "favorite") {
        window.open(createUrl, '创建网站', 'height=' + _height + ',width=' + _width + ',top=' + _top + ',left=' + _left + ',toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no')
    } else {
        window.open(createNoteUrl, '创建便签', 'height=' + _height + ',width=' + _width + ',top=' + _top + ',left=' + _left + ',toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no')
    }
//
}
//刷新右侧
function  refresh() {
    $("div[node-type='contentRight']").find("[node-data='refresh']").trigger("click");
}

//刷新待办公文
function  updateDocument() {
    $('.mod_box[node-data="document.default"] .hd_opts_item[node-data="refresh"]').trigger("click");
}