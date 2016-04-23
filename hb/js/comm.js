/**
 * Created by Administrator on 2016/3/10.

 */
/*   (function() {
 // windowWidth / 640 * 100 + 'px';
 document.addEventListener('DOMContentLoaded', function() {
 var html = document.documentElement;
 var windowWidth = html.clientWidth;
 // if (windowWidth >= 540) {
 //     windowWidth = 540;
 // }
 if(windowWidth>736){
 html.style.fontSize = 100 + 'px';
 }else if(windowWidth<736){
 html.style.fontSize = windowWidth / 6.4 + 'px';
 }
 }, false);
 })();*/
/*tab*/
;(function () {
    if ($(".footer-bottom").length > 0) {
        $(".footer-bottom").prev("div").css({
            "padding-bottom": "67px"
        });
    }
})();


$(function(){
    /*返回顶部*/
    $("#top-animation").css("display","none");
    var $isTop=$("#top-animation");
    $(window).scroll(function(){
        //top
        if($isTop){
            var sc=$(window).scrollTop();

            /*头部固定*/
            var headHeiht=$(".nav-header").height()+50;
            console.log(headHeiht);
            if(sc>headHeiht){
                $(".nav-header").addClass("topFixed");
            }else{
                $(".nav-header").removeClass("topFixed");
            }
            if(sc>0){
                $isTop.css("display","block");
            }else{
                $isTop.css("display","none");
            }
        }
    });
    if($isTop){
        $isTop.click(function(){
            $('body,html').animate({scrollTop:0},500);
        });
    }



    /*导航弹出*/
    $(".ui-btn-layer").click(function () {
        var navHeight=$(".nav-header").height()+'px';
        $("#jq_menu").toggleClass("jq_more_open").css({"top":navHeight});
        $(".ui-arrow-down").toggleClass("ui-arrow-up");
    });
    /*点击弹出框内容改变层消失*/
    $(".ui-menu-nav li").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
        var layerChild=$(".ui-btn-layer").children();
        $(".ui-btn-layer").html($(this).text()).append(layerChild);
        jqHs();
    });
    function jqHs(){
        $("#jq_menu").removeClass("jq_more_open");
        $(".ui-arrow-down").toggleClass("ui-arrow-up");
        /*clickstop();*/
    };
    $(".jq_mark").click(function () {
        jqHs();
    });
    /*圆圈选中效果*/
    $(".ui-list-mian span").click(function () {
        $(this).toggleClass("active");
    });
    $(".ui-nav-bar a").click(function () {
        //$(this).toggleClass("active").siblings().removeClass("active");
        $(this).addClass("active").siblings().removeClass("active");
    });

    /*    function clickstop(e){
     e.stopPropagation();
     };*/
});
;(function ($) {
    $.extend({
        loading: function (options) {
            options = $.extend({
                loadId:'loader',
                loadBg:"#6A7273",
                loadSh:true,
                time:3000
            }, options);
            var loadBox = '<div class="loader" id="loader">\
                        <div class="loader-inner line-spin-fade-loader">\
                        <div></div>\
                        <div></div>\
                        <div></div>\
                        <div></div>\
                        <div></div>\
                        <div></div>\
                        <div></div>\
                        <div></div>\
                        </div>\
                        </div>';


            function remove(){
                $('#'+options.loadId).remove();
            }
            if ($('#'+options.loadId).length ==0) {
                if(options.loadSh=='show'){
                    $("body").append(loadBox);
                }
            }else if(options.loadSh=='hide'){
                remove();
            }

            if (options.loadBg!=undefined) {
                $('#'+options.loadId).css('background',options.loadBg);
            }
        }
    });
})(jQuery);