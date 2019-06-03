//显示页签的标识符
var pageFlag = 0;

function doInit() {
    showpage(pageFlag);
}

$(document).ready(function() {
    $('.rp-btn-left').hide();
    $('#copy').hide();
});

function back() {
    $('#forward').attr("disabled", false);
    $('#forward').css("color", '#fff');
    pageFlag--;
    if (pageFlag <= 0) {
        $('#back').attr("disabled", true);
        $("#back").css('color', '#A9A9A9');
        showpage(0);
    } else {
        $('#back').attr("disabled", false);
        $("#forward").css('color', '#3379b7');
        showpage(pageFlag);
    }
}

function forward() {
    $('#back').attr("disabled", false);
    $('#back').css("color", '#fff');
    pageFlag++;
    if (pageFlag >= 3) {
        $('#forward').attr("disabled", true);
        $("#forward").css('color', '#A9A9A9');
        showpage(3);
    } else {
        $('#forward').attr("disabled", false);
        $("#forward").css('color', '#3379b7');
        showpage(pageFlag);
    }
}

function save() {

}

function showpage(index) {
    pageFlag = index;
    if (pageFlag == 0) {
        $('#back').attr("disabled", true);
        $('#forward').attr("disabled", false);
        $("#back").css('color', '#A9A9A9');
        $('#forward').css("color", '#fff');
    } else if (pageFlag == 3) {
        $('#back').attr("disabled", false);
        $('#forward').attr("disabled", true);
        $('#back').css("color", '#fff');
        $("#forward").css('color', '#A9A9A9');
        $('#copy').show();
    } else {
        $('#back').attr("disabled", false);
        $('#forward').attr("disabled", false);
        $('#back').css("color", '#fff');
        $('#forward').css("color", '#fff');
        $('#copy').hide();
    }
    if (pageFlag == 0 || pageFlag == 1) {
        $('.rp-btn-left').hide();
    }
    if (pageFlag == 2 || pageFlag == 3) {
        $('.rp-btn-left').show();
    }
    $(".content").hide();
    $(".content").eq(pageFlag).show();
}