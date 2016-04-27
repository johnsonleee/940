/**
 * Created by Administrator on 2016/4/27.
 */
var indexmodule=angular.module("indexmodule",[]);
var tgsystem=angular.module("tgsystem",[]);
indexmodule.controller("freeCtrl",function($scope){
   /* $scope.frees=[
        {title:"完全自然人工浏览，不同地区IP，真实流量、安全可靠",id:"1"},
        {title:"百万人同时在线，深度访问，增加店铺收藏，提高排名",id:"2"},
        {title:"专业的关键词优化，提高点击率，有点击才会有销量",id:"3"},
        {title:"轻松为您打造店铺爆款商品，从而引爆全店销量",id:"4"}
    ]*/
});
var home={
    /*免费*/
    free:function(){
        return{
            restrict:"E",
            templateUrl:'template/free.html',
            replace:false,
            link:function($scope,$elem,$attrs){
                console.log($attrs);
               /* $elem.append(1);*/
                $scope.frees=[
                    {title:"完全自然人工浏览，不同地区IP，真实流量、安全可靠",id:"1"},
                    {title:"百万人同时在线，深度访问，增加店铺收藏，提高排名",id:"2"},
                    {title:"专业的关键词优化，提高点击率，有点击才会有销量",id:"3"},
                    {title:"轻松为您打造店铺爆款商品，从而引爆全店销量",id:"4"}
                ]
            }
        }
    },
    /*视频*/
    videoList:function(){
        return{
            restrict:'E',
            templateUrl:'template/videolist.html',
            replace:false,
            link:function($scope,$element,$arrts){
                console.log($arrts);
                $scope.arr = [
                    {
                        id:"1",
                        className:"video-spacing",
                        title: "注册帐号，完善资料，绑定手机证书",
                        url: "14651978969257574452"//1
                    },
                    {
                        id:"2",
                        title: "如何发布推广任务(PC端)",
                        url: "14651978969257569568"//2
                    },
                    {
                        id:"3",
                        className:"video-spacing",
                        title: "如何发布推广任务(手机端)",
                        url: "14651978969257570292"//3
                    },
                    {
                        id:"4",
                        title: "如何发布商品链接任务",
                        url: "14651978969257572725"//4
                    },
                    {
                        id:"5",
                        className:"video-spacing",
                        title: "如何发布店铺链接任务",
                        url: "14651978969257572440"//5
                    },
                    {
                        id:"6",
                        title: "如何查关键词排名",
                        url: "14651978969257568679"//6
                    }
                        ]
            }
        }
    },
    /*推广流程系统*/
    stepShow:function(){
            return{
                restrict:"E",
                templateUrl:"template/tgSystem.html",
                replace:false,
                link:function(){

                }
            }
    }
}
indexmodule.directive("freeList",home.free);
indexmodule.directive("videoList",home.videoList);