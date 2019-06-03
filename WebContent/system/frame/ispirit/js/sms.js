(function() {
  var defaultOpt = {
    type: "classic",
    smsCallCount: 3,
    smsVoiceSpace: 3,
    smsAlertTimer: null,
    //smsAlert: false,
    smsRefresh: 10,
    smsOn: "0",
    callSound: 2,
    userId: 0
  };
  function Text2Object(data) {
	  try {
	    var func = new Function("return " + data);
	    return func();
	  } catch (ex) {
	    return '数据错误：' + data;
	  }
	}
  window.sms = {
	
    initialize: function(opt) {
      $.extend(this, defaultOpt, opt);
      this.checkShortMsrg();

    },
    //从session轮询短信列表，检查是否有需要提示的短信
    checkShortMsrg: function() {
        var url = contextPath + "/sms/checkSessionSmsList.action";
        var para =  {} ;
		var smsList = tools.requestJsonRs(url,para);
		if(smsList!=null){
			if (smsList > 0) {
		          //显示短信提示按钮
		        sms.showSmsButton();
		          //showShortMsrg();
	        } else if (smsList < 0){
		          //空闲强制自动离线
		        doLogout();
	        }
			sms.shortMsrgTimer = setTimeout(function() {sms.checkShortMsrg();}, sms.smsRefresh * 1000);
		}
	},

    /**
     * 展示内部短信窗口
     */
    showShortMsrg: function () {
		$("#smsFlash").hide();
		var url = contextPath+"/system/core/sms/smsbox.jsp";
		top.$.jBox.open("iframe:"+url,"消息提醒",650,420);
//      var left = 5;
//      var top = $(document).height() - 360 - 5;
//	  var mytop = (screen.availHeight - 500) / 2 -15;
//	  var myleft = (screen.availWidth - 540) / 2;
//      if (!sms.smsWindow) {
//        var cfg = {
//          'title' : 'Tenee办公自动化智能平台',
//          'draggable': true,
//          resizable: {},
//          tbar: [{
//            id: 'close',
//            preventDefault: true,
//            handler: function(e, t, p) {
//              p.hide();
//            }
//          }],
//          lazyContainer: true,
//          showEffect: function(speed, callback) {
//            var self = this;
//            this.expand(speed, callback);
//          },
//          hideEffect: function() {
//            this.collapse.apply(this, arguments)
//          },
//          
//          cls: 'window-group window-active',
//          'type': 'iframe',
//          'width': 530,
//          'icon': imgPath + '/mainframe/smsIcon.png',
//          'height': 430,
//          'top': mytop,
//          'left':myleft,
//          'src': contextPath + '/system/frame/ispirit/smsBox.jsp',
//          'listeners': {
//              'hide': {
//            	 
//                after: function(e, t) {
//                  sms.smsAlert = false;
//                  //隐藏短信窗口,设置iframe指向空
//                  t.setSrc('about:blank');
//                  sms.checkShortMsrg();
//                }
//              },
//              show: {
//                after: function(e, t) {
//                  sms.count = 0;
//                  clearTimeout(sms.shortMsrgTimer);
//                }
//              }
//            }
//        };
//
//          cfg.modal = true;
//      
//        sms.smsWindow = new Tee.Window(cfg);
//
//      }
//      else {
//        sms.smsWindow.setSrc(contextPath + '/system/frame/ispirit/smsBox.jsp');
//      }
//      sms.smsWindow.show();
//      sms.hideSmsButton();
    },
    /**
     * 显示左下状态栏短信按钮
     * @return
     */
    showSmsButton: function () {
    	//alert(sms.smsOn);
    	$('#smsFlash').show();
      if (sms.smsOn == '0') {
        if (!sms.smsAlert) {
          //sms.smsAlert = true;
          //sms.count = sms.smsCallCount;
          //Tee.getCmp('smsBtn') && Tee.getCmp('smsBtn').setStatus('active');
          $('#smsFlash').css({
            'display': 'block'
          });
          clearTimeout(sms.smsAlertTimer);
          //sms.smsCallSound();
        }
      }
      else {
        if (sms.callSound >= 0) {
          sms.playSound(contextPath + '/system/frame/ispirit/audio/' + sms.callSound + '.wav');
        }
        else {
          sms.playSound(contextPath + '/system/frame/ispirit/wav/' + sms.userId + '.swf');
        }
      }
    },
    /**
     * 关闭内部短信窗口
     */
    hideShortMsrg: function () {
      if (sms.smsWindow){
        sms.smsWindow.hide();
      }
    },
    /**
     * 隐藏左下状态栏短信按钮
     * @return
     */
    hideSmsButton: function (){
      Tee.getCmp('smsBtn') && Tee.getCmp('smsBtn').setStatus('default');
      $('#smsFlash').css({
        'display': 'none'
      });
    },
    /**
     * 播放flash音频
     * @param name
     * @return
     */
    playSound: function (name) {
      if ($.browser.msie) {
        if (!$("bgsound").length) {
          var bgsound = $("<bgsound></bgsound>");
          $("head").append(bgsound);
        }
        $("bgsound").attr("src", name);
      }
      else {
        if (!$('#soundPlayer').length) {
          $("body").append('<div id="soundPlayer" style="visibility: hidden"><audio src="' + name + '" autoplay><embed loop="false" autostart="true" src="' + name + '"></embed></audio></div>');
        }
        else if ($('#soundPlayer audio').length) {
          $('#soundPlayer audio')[0].play();
        }
        else {
          $('#soundPlayer').remove();
          sms.playSound(name);
        }
      }
    },
    /**
     * 播放短信提示音
     * @return
     */
    smsCallSound: function () {
      if (sms.count < 1) {
        return;
      }
      if (sms.count == 1) {
        if (sms.smsAlertTimer) {
          clearTimeout(sms.smsAlertTimer);
        }
      }else if (sms.count > 1) {
        if (sms.smsAlertTimer) {
          clearTimeout(sms.smsAlertTimer);
        }
        sms.smsAlertTimer = setTimeout(function() {
          sms.smsCallSound();
        }, sms.smsVoiceSpace * 60 * 1000);
      }
      if (sms.callSound >= 0) {
        sms.playSound(contextPath + '/system/frame/ispirit/audio/' + sms.callSound + '.wav');
      }
      else {
        sms.playSound(contextPath + '/system/frame/ispirit/wav/' + sms.userId + '.swf');
      }
      sms.count--;
    }
  };
}) ();
