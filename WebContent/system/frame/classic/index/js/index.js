/**
 * Created by 陈学飞 on 2018/8/21 0021.
 */
$(document).ready(function(){
    
    jQuery(".slideBox").slide({mainCell:".bd ul",autoPlay:true});

    $(".dianji li").click(function() {
        // $(".chuxian div").eq($(this).index()).addClass("").siblings().removeClass('');
        $(".chuxian div").hide().eq($(this).index()).show();

    });
});