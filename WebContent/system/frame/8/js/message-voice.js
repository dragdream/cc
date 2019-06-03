var voiceMsg = {
    "init": function() {
        $(document).delegate(".vmPanel[action-type='play']", "click", function(e)
        {

            e.stopPropagation();

            if (typeof ($(this).attr("status")) == "undefined")
                var status = "stop";
            else
                var status = $(this).attr("status");

            if (status == "stop")
            {
                $(this).attr("status", "palying");
                voiceMsg.play($(this));
                $(this).attr("title", "点击停止播放");
            } else {
                $(this).attr("status", "stop");
                voiceMsg.stop();
                $(this).attr("title", "点击播放");
            }
            return;
        });

        $(document).delegate(".vmPanel[action-type='download']", "click", function() {
            voiceMsg.download($(this));
        });
    },
    "play": function(handler)
    {
        var voice_data = handler.attr("action-data");
        this.stop();
        handler.find("[un='voiceStatus']").attr("class", "icoVoicePlaying");
        if (typeof (window.external.PlayVoiceMsg) == "undefined") {
           alert("网页端不能播放，请在精灵端查看");
        } else {
            window.external.PlayVoiceMsg(voice_data);
        }
    },
    "stop": function()
    {
        if (typeof (window.external.StopVoiceMsg) == "undefined") {
           alert("网页端不能播放，请在精灵端查看");
        } else {
            window.external.StopVoiceMsg("");
        }
    },
    "download": function(handler) {
        var voice_data = handler.attr("action-data-url");
        window.location = voice_data;
    }
};
$(document).ready(function() {
    voiceMsg.init();
});