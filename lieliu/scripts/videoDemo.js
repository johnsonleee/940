(function() {
    // 等价于html.style.fontSize = windowWidth / 640 * 100 + 'px';
    document.addEventListener('DOMContentLoaded', function() {
        var html = document.documentElement;
        var windowWidth = html.clientWidth;
        // if (windowWidth >= 540) {
        //     windowWidth = 540;
        // }
        html.style.fontSize = windowWidth / 6.4 + 'px';
    }, false);
})();

$(function() {
    // 不能加高度，要不然中间区域跟随不到底部：style="padding-bottom: 1.13rem;"
    // if ($("header").length > 0) {
    //     $("header").prev("div").css({
    //         "padding-top": "1.08rem"
    //     });
    // }

    // 返回top顶部
    // $('#scroll').click(function() {
    //     $("html,body").animate({
    //         scrollTop: 0
    //     }, 200);
    // });

    // 数字
    var CountUpOptions = {
        useEasing: false,
        useGrouping: true,
        separator: ',',
        prefixdecimal: '.',
        prefix: '',
        suffix: ''
    };
    $("html, body").scrollTop(0);
    var demo = new CountUp("myTargetElement", 0, 758360, 0, 2.5, CountUpOptions);

    var rollSet = $('#myTargetElement');
    var offset = rollSet.offset();
    $(window).scroll(function() {
        var scrollTop = $(window).scrollTop();
        var scrollWindow = $(window).height();
        var rollLen = rollSet.find(".sectiondemo");

        if (offset.top + -90 < scrollTop) {
            demo.start();
        }
    });

    // 下载猎流
    var platformht = $(".platform").outerHeight(true);
    $(".download").hide();
    $(document).scroll(function(){
        var scrTop = $(document).scrollTop();
        console.log(platformht);
        if (scrTop >= platformht) {
            $(".download").fadeIn();
        }else{
            $(".download").fadeOut();
        }
    });

    // 右侧栏
    $('.slide-menu').on('click', function() {
        var test = $('.wrapperhove' + 'rtree').height();
        $('.slide-mask').css('height', test).show();
        $('.slide-wrapper').css('height', test).addClass('moved');
    });
    $('.slide-mask').on('click', function() {
        $('.slide-mask').hide();
        $('.slide-wrapper').removeClass('moved');
    });

    // 视频
    var option = {
        "auto_play": "0",
        "file_id": "14651978969257574452",
        "app_id": "1251672867",
        "width": 1920,
        "height": 1080
    };
    /*调用播放器进行播放*/
    new qcVideo.Player(
        /*代码中的id_video_container将会作为播放器放置的容器使用,可自行替换*/
        "id_video_container",
        option
    );


});

// 手机输入 www.940.com 跳转 手机网页：m.940.com
// function isPhone() {
//     for (var a = navigator.userAgent, b = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"], c = !0, d = 0; d < b.length; d++)
//         if (a.indexOf(b[d]) > 0) {
//             c = !1;
//             break;
//         }
//     return c;
// }
// isPhone() || (window.location.href = "http://m.940.com/");