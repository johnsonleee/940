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
    if ($("footer").length > 0) {
        $("footer").prev("div").css({
            "padding-bottom": "1.13rem"
        });
    }

    // 左侧栏菜单
    var slideout = new Slideout({
        'panel': document.getElementById('main'),
        'menu': document.getElementById('menu'),
        'padding': 5,
        'tolerance': 70,
        'touch': false //禁用触摸滑动
    });
    document.querySelector('.js-slideout-toggle').addEventListener('click', function() {
        slideout.toggle();
    });
    document.querySelector('.menu').addEventListener('click', function(eve) {
        if (eve.target.nodeName === 'A') {
            slideout.close();
        }
    });

    function aload(t) {
        "use strict";
        t = t || window.document.querySelectorAll("[data-aload]"), void 0 === t.length && (t = [t]);
        var a, e = 0,
            r = t.length;
        for (e; r > e; e += 1) a = t[e], a["LINK" !== a.tagName ? "src" : "href"] = a.getAttribute("data-aload"), a.removeAttribute("data-aload");
        return t;
    }

    // tab切换
    // $('.free-tabPanel ul.free-ul li').click(function() {
    //     $('.free-tabPanel ul.free-ul li').removeClass('free-hit');
    //     $(this).addClass('free-hit');
    //     $('.free-panes>div:eq(' + $(this).index() + ')').show().siblings().hide();
    // });

    //弹出'申请职位框'按钮弹窗
    // $('.reg').on('click', function() {
    //     layer.open({
    //         type: 2, //iframe层
    //         move: false, //禁止拖拽
    //         //scrollbar: false,      //默认允许浏览器滚动
    //         area: ['6rem', '6rem'],
    //         shadeClose: false, //点击遮罩关闭
    //         title: '修改登录密码',
    //         content: '../user/passw.html',
    //         // skin: 'layui-layer-rim'      //加上边框,直接在layer里面的css样式改
    //     });
    // });

    // user
    var storage = window.localStorage;
    var json_data = JSON.parse(storage.getItem("member"));
    console.log(json_data.u);

    function formatData(r) {
        var a = new Array();
        var c = Object.prototype.toString.call(r);
        r = /String/.test(c) ? eval('(' + r + ')') : r;
        var d = Object.prototype.toString.call(r);
        // console.log(d);   // [object Object]
        a = /Array/.test(d) ? r : a[0] = r;
        return a;
    }
    var listdata = $.param({
        user_name: json_data.u,
        password: json_data.password
    });

    // 绑数据
    $.ajax({
        type: 'get',
        url: 'http://940.com/userInfo',
        dataType: 'jsonp',
        processData: false,
        data: listdata,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        success: function(data) {
            console.log(data.s);
            var data = formatData(data);
            // 性别
            var list = $('.free-pane');
            var sex = '';
            list.html("");
            if (data.s==0) {
                sex = "<div class='fl'>" +
                    "<i data-s='1' class='selected'></i>" +
                    "<strong class='icon_nan'>男</strong>" +
                    "</div>" +
                    "<div class='fl'>" +
                    "<i data-s='2'></i>" +
                    "<strong class='icon_nv'>女</strong>" +
                    "</div>";
            }else if (data.s==1) {
                sex = "<div class='fl'>" +
                    "<i data-s='1' class='selected'></i>" +
                    "<strong class='icon_nan'>男</strong>" +
                    "</div>" +
                    "<div class='fl'>" +
                    "<i data-s='2'></i>" +
                    "<strong class='icon_nv'>女</strong>" +
                    "</div>";
            } else if (data.s==2) {
                sex = "<div class='fl'>" +
                    "<i data-s='1'></i>" +
                    "<strong class='icon_nan'>男</strong>" +
                    "</div>" +
                    "<div class='fl'>" +
                    "<i data-s='2' class='selected'></i>" +
                    "<strong class='icon_nv'>女</strong>" +
                    "</div>";
            }
            // 头像+信息（登录信息）
            var pesonal = '';
            pesonal += "<div class='users'>" +
                "<div class='users-personal'>" +
                "<dl>" +
                "<dt><img src='" + data.im + "'></dt>" +
                "<dd>" +
                "<i>学员：" + data.r + "</i>" +
                "</dd>" +
                "<dd>" +
                "<span>登录邮箱：" + data.u + "</span>" +
                "<span class='right membero'>编号：" + data.o + "</span>" +
                "</dd>" +
                "<dd>" +
                "<div class='acc_rank' id='acc_data'>" +
                "<span>安全等级：</span>" +
                "<span class='a_integ b_integ'>" +
                "<i class='yellows' style='width: 70%;'></i>" +
                "</span>" +
                "<font>中</font>" +
                "</div>" +
                "</dd>" +
                "<dd>" +
                "<div class='acc_rank' id='acc_data'>" +
                "<span>信息完整度：</span>" +
                "<span class='a_integ'>" +
                "<i style='width: 70%;'></i>" +
                "</span>" +
                "<font>70%</font>" +
                "</div>" +
                "</dd>" +
                "<dd>" +
                "<span>上次登录时间：" + data.v + "</span>" +
                "</dd>" +
                "</dl>" +
                "</div>" +
                "</div>" +
                // 编辑个人资料
                "<div class='profile'>" +
                "<div class='users-edit'>" +
                "<div class='title'>" +
                "<span>编辑个人资料</span>" +
                "<i></i>" +
                "</div>" +
                "</div>" +
                "<div class='users-cont'>" +
                "<ul>" +
                // 登录邮箱
                "<li>" +
                "<span>登录邮箱：</span>" +
                "<div class='item modify_pass'>" +
                "<i class='yahei'>" + data.u + "</i>" +
                "<a href='###' class='reg'>（修改登录密码）</a>" +
                "</div>" +
                "</li>" +
                // 真实姓名
                "<li class='li_name'>" +
                "<span>真实姓名：</span>" +
                "<div class='item'>" +
                "<i class='modify-name'>" + data.r + "</i>" +
                "<a data-v='name' href='javascript:' id='txtiname'>（修改）</a>" +
                "<input id='txtIname' type='text' class='xg_txt' style='display: none;'>" +
                "</div>" +
                "</li>" +
                // 性别
                "<li class='li_sex'>" +
                "<span>性&nbsp&nbsp别：</span>" +
                "<div class='item sex_inp'>" +
                sex +
                "</div>" +
                "</li>" +
                // 手机
                "<li class='li_phone'>" +
                "<span>手&nbsp&nbsp机：</span>" +
                "<div class='item un_phone' style='display: none;'>" +
                "<i class='yahei'>加载中...</i>" +
                "<a href='javascript:'>（解绑）</a>" +
                "</div>" +
                "<div class='item bind_phone' style=''>" +
                "<i style='color: #999;'>" + data.z + "</i>" +
                // "<i style='color: #999;'>还未绑定手机</i>" +
                "<a href='javascript:' class='bind'>（立即绑定）</a>" +
                "</div>" +
                "</li>" +
                // QQ
                "<li class='li_qq'>" +
                "<span>QQ：</span>" +
                "<div class='item revise_inp'>" +
                "<i class='modify-qq'>" + data.q + "</i>" +
                "<a data-v='qq' href='javascript:' id='txtiqq'>（修改）</a>" +
                "<input id='txtIqq' type='text' class='xg_txt' style='display: none;'>" +
                "</div>" +
                "</li>" +
                // YY
                "<li class='li_yy'>" +
                "<span>YY：</span>" +
                "<div class='item revise_inp'>" +
                "<i class='modify-yy'>" + data.yy + "</i>" +
                "<a data-v='yy' href='javascript:' id='txtiyy'>（修改）</a>" +
                "<input id='txtIyy' type='text' class='xg_txt' style='display: none;'>" +
                "</div>" +
                "</li>" +
                // 所在地
                "<li class='li_area'>" +
                "<span>所在地：</span>" +
                "<div class='item selectList'>" +
                "<i class='modify-city'>" + data.a + "</i>" +
                "<a data-v='szd' href='javascript:' id='txticity'>（修改）</a>" +
                "<div id='txtIcity' class='city-layer'>" +
                "<div class='select_ui'>" +
                "<div class='select_arrow'></div>" +
                "<div class='select_text_ui' style='min-width: 2.5em;'>北京</div>" +
                "<select class='province' required='true' name='birthday_year'>" +
                "undefined" +
                "<option value='北京'>北京</option>" +
                "<option value='天津'>天津</option>" +
                "<option value='河北'>河北</option>" +
                "<option value='山西'>山西</option>" +
                "<option value='内蒙古'>内蒙古</option>" +
                "<option value='辽宁'>辽宁</option>" +
                "<option value='吉林'>吉林</option>" +
                "<option value='黑龙江'>黑龙江</option>" +
                "<option value='上海'>上海</option>" +
                "<option value='江苏'>江苏</option>" +
                "<option value='浙江'>浙江</option>" +
                "<option value='安徽'>安徽</option>" +
                "<option value='福建'>福建</option>" +
                "<option value='江西'>江西</option>" +
                "<option value='山东'>山东</option>" +
                "<option value='河南'>河南</option>" +
                "<option value='湖北'>湖北</option>" +
                "<option value='湖南'>湖南</option>" +
                "<option value='广东'>广东</option>" +
                "<option value='广西'>广西</option>" +
                "<option value='海南'>海南</option>" +
                "<option value='重庆'>重庆</option>" +
                "<option value='四川'>四川</option>" +
                "<option value='贵州'>贵州</option>" +
                "<option value='云南'>云南</option>" +
                "<option value='西藏'>西藏</option>" +
                "<option value='陕西'>陕西</option>" +
                "<option value='甘肃'>甘肃</option>" +
                "<option value='青海'>青海</option>" +
                "<option value='宁夏'>宁夏</option>" +
                "<option value='新疆'>新疆</option>" +
                "<option value='香港'>香港</option>" +
                "<option value='澳门'>澳门</option>" +
                "</select>" +
                "</div>" +
                "<div class='select_ui'>" +
                "<div class='select_arrow'></div>" +
                "<div class='select_text_ui' style='min-width: 2.5em;'>西城</div>" +
                "<select class='city' required='true' name='birthday_year'>" +
                "<option value='西城'>西城</option>" +
                "<option value='东城'>东城</option>" +
                "<option value='崇文'>崇文</option>" +
                "<option value='宣武'>宣武</option>" +
                "<option value='朝阳'>朝阳</option>" +
                "<option value='海淀'>海淀</option>" +
                "<option value='丰台'>丰台</option>" +
                "<option value='石景山'>石景山</option>" +
                "<option value='门头沟'>门头沟</option>" +
                "<option value='房山'>房山</option>" +
                "<option value='通州'>通州</option>" +
                "<option value='顺义'>顺义</option>" +
                "<option value='大兴'>大兴</option>" +
                "<option value='昌平'>昌平</option>" +
                "<option value='平谷'>平谷</option>" +
                "<option value='怀柔'>怀柔</option>" +
                "<option value='密云'>密云</option>" +
                "<option value='延庆'>延庆</option>" +
                "</select>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</li>" +
                // 点此更换图片
                "<li class='li_url por_li'>" +
                "<div class='upload'>" +
                "<dl>" +
                "<dt id='fileList'>" +
                "<img src='" + data.im + "'>" +
                "</dt>" +
                "<dd>"+
                "<form id='imgurlform'   target='form'  enctype='multipart/form-data' method='post' action='http://www.940.com/upload?id_good='"+data.o+"?&client=1'>" +
                " <input type='hidden' name='id_good' value='"+data.o+"'><input type='file' name='file1' id='filename' onchange='handleFiles(this)'><br/>" +
                " <input type='submit' id='imgsubmit' value='提交'>" +
                " </form>"+
                "</dd>"+
                //"<form id='uploadForm' target='form' method='post' enctype='multipart/form-data' action='http://940.com/upload'>"+
               // "<dd>" +
                //"<p>上传支持JPG、PNG、BMP格式，</p>" +
               // "<p>不超过150KB最佳尺寸为145x145像素。</p>" +
              //  "</dd>" +
               // "<dd class='ipt'>" +
                //"<a href='###' onchange='handleFiles(this)'>点此更换图片</a>" +
              // "<input onchange='javascript:setImagePreview();' id='id_good' value='1000473'>"+
                //"<input type='file' id='fileElem' multiple accept='image/*' onchange='handleFiles(this)'>" +
               // "</dd>" +
                "</form>"+
                "<iframe id='iframe' name='form' style='display:none;'></iframe>"+
                "</dl>" +
                "</div>" +
                "</li>" +
                "</ul>" +
                "</div>" +
                "</div>" +
                "<div class='preserved'>" +
                "<button type='submit' class='btn-long btn-purple' id='savesubmit'>保<i></i>存</button>" +
                "</div>";

            list.append(pesonal);
        }
    });
    var ajaxPane1 = $('#imgurlform');
    ajaxPane1.bind('submit', function(event) {
        ajaxPane1.attr('action','http://m.940.com/upload?id_good='+$(".membero").html().replace(/[^0-9]/ig,""))+"&client=1";
        alert(1);
        event.preventDefault();
        var imgurl='http://m.940.com/upload?id_good='+$(".membero").html().replace(/[^0-9]/ig,"");
        var formData = new FormData($("#imgurlform")[0]);
        $.ajax({
            url: imgurl,
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            dataType:'json',
            // jsonp: 'callback',
            contentType: false,
            processData: false,
            success: function (data) {
                alert(data.tips);
            },
            error: function (data) {
                alert(data.tips);
            }
        });
        return false;
    });
    // 修改-提交
    var ajaxPane = $('.free-pane');
    ajaxPane.bind('submit', function(event) {
        event.preventDefault();
        var r = /<[^>]*>/g;
        document.domain = "m.940.com";
        var forprofile=$(window.frames['form'].document.body).html();
       /* var forprofile=$("#iframe").text();*/
       var  jsonimg=$.parseJSON(forprofile);
       console.log(jsonimg.file_url);
        console.log(forprofile);
        var modify = $.param({
            user_name: json_data.u,
            qq: $(".modify-qq").text(),
            yy: $(".modify-yy").text(),
            gender: $("i.selected").attr("data-s"),
            profile:jsonimg.file_url,
            address: $thisdress,
            realname:function(){
                return  $(".modify-name").val()?0:$(".modify-name").val();
            },
            password: json_data.password
        });
        $.ajax({
            async: false,
            type: "post",
            url: 'http://www.940.com/saveUserInfo',
            dataType: 'json',
            // jsonp: 'callback',
            data: modify,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success: function(data) {
                data = formatData(data);
                console.log(modify);
                var item = data.sududa;
                console.log(item.tips);
                if (item.status == 1) {
                    alert(item.tips);
                } else if (item.status == -9) {
                    alert(item.tips);
                } else {
                    alert("item.tips");
                }
            }
        });

    });

    // // 修改-提交
    // var ajaxPane = $('.free-pane');
    // ajaxPane.bind('submit', function(event) {
    //     event.preventDefault();
    //     var modify = $.param({
    //         user_name: json_data.u,
    //         qq: $(".modify-qq").text(),
    //         yy: $(".modify-yy").text(),
    //         gender: $("i.selected").attr("data-s"),
    //         profile: $("#fileList img").attr("src"),
    //         address: $(".modify-city").text(),
    //         /* realname: $(".modify-name").text(),*/
    //         realname:function(){
    //             return  $(".modify-name").val()?0:$(".modify-name").val();
    //         },
    //         password: json_data.password
    //     });
    //     $.ajax({
    //         async: false,
    //         type: "get",
    //         url: 'http://940.com/saveUserInfo',
    //         dataType: 'jsonp',
    //         jsonp: 'callback',
    //         data: modify,
    //         contentType: "application/x-www-form-urlencoded; charset=utf-8",
    //         success: function(data) {
    //             data = formatData(data);
    //             console.log(modify);

    //             var item = data.sududa;
    //             console.log(item.tips);
    //             if (item.status == 1) {
    //                 alert(item.tips);
    //             } else if (item.status == -9) {
    //                 alert(item.tips);
    //             } else {
    //                 alert("item.tips");
    //             }
    //         }
    //     });

    // });


});