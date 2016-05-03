var home={
    freeList:function(){
        return{
            restrict:"E",
            templateUrl:"template/viewList.html",
            replace:true,
            controller:'MainCtrl',
           /* scope:{
                getContent:'&'
            },
            controller:function($scope){
                $scope.user=$scope.getContent();    //调用无参函数
            },*/
            link:function($scope,$element,$attr){
                /* console.log($attr);*/
                if($attr+1 % 2 == 0){
                    $element.classname="free-cont1";
                }else{
                    $element.classname="free-cont2";
                }
            }
        }
    },
    shopList:function(){
        return{
            restrict:"E",
            templateUrl:"template/itemList.html",
            replace:true,
            controller:'shopUrl',
            link:function($scope,$element){
                if(2 % 2 == 0){
                    $element.classname="free-cont1";
                }else{
                    $element.classname="free-cont2";
                }
            }
        }
    },
    sucList:function(){
        return{
            restrict:"E",
            templateUrl:"template/itemList.html",
            replace:true,
            controller:'secUrl',
            link:function($scope,$element){
                if(2 % 2 == 0){
                    $element.classname="free-cont1";
                }else{
                    $element.classname="free-cont2";
                }
            }
        }
    },
    teachList:function() {
        return{
            restrict: "E",
            templateUrl: "template/teachList.html",
            replace: true,
            controller:'teachUrl',
            link: function ($scope, $element) {
                if (2 % 2 == 0) {
                    $element.classname = "free-cont1";
                } else {
                    $element.classname = "free-cont2";
                }
            }
        }
    },
    articleDetail:function(){
        return{
            restrict:"E",
            templateUrl:"template/itemList.html",
            replace:true,
            controller:'MainCtrl',
            link:function($scope,$element){
                if(2 % 2 == 0){
                    $element.classname="free-cont1";
                }else{
                    $element.classname="free-cont2";
                }

            }
        }
    }
};
indexApp.directive("freeList",home.freeList);
indexApp.directive("shopList",home.shopList);
indexApp.directive("sucList",home.sucList);
indexApp.directive("teachList",home.teachList);
indexApp.directive("articleDetail",home.articleDetail);