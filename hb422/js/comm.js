/**
 * Created by Administrator on 2016/3/10.
 */
/*tab*/
;(function () {
    // 等价于html.style.fontSize = windowWidth / 640 * 100 + 'px';
   /* document.addEventListener('DOMContentLoaded', function () {
        var html = document.documentElement;
        var windowWidth = html.clientWidth;
        html.style.fontSize = windowWidth / 6.4 + 'px';
        if(windowWidth>1000){
            html.style.fontSize = 100 + 'px';
        }
    }, false);*/
    if ($(".footer-bottom").length > 0) {
        $(".footer-bottom").prev("div").css({
            "padding-bottom": "65px"
        });
    }
})();

/*返回顶部*/
window.onload = function(){
    var oTop = document.getElementById("top-animation");
    function topDb(){
        var scrTop = document.documentElement.scrollTop || document.body.scrollTop;
      if(oTop){
          if(scrTop<=15){
              oTop.style.display ="none";
          }else{
              oTop.style.display ="block";
          }
      }
    };
    topDb();
    window.onscroll = function(){
        topDb();
    };
      if(oTop){
          oTop.onclick = function(){
              document.documentElement.scrollTop = document.body.scrollTop =0;
          };
      }
};
$(function(){
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
        $(this).addClass("active").siblings().removeClass("active");
    });
    /*tabs切换*/
    $(".ui-nav-bar a").click(function () {
        var index=$(this).index();
        $(".ui-tabs-content .tabs-item").eq(index).show().siblings().hide();
    });
/*    function clickstop(e){
        e.stopPropagation();
    };*/
});
