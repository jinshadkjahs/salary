
//loading显示
function loadingShow() {
    $('#loading').show();
}

//loading隐藏
function loadingHide() {
    $('#loading').hide();
}

//对话框调用
function confirmShow(confirmTishi, yesfn, nofn){
    $("#confirmTishi").text(confirmTishi);
    $('.zhezhao').css('display', 'block');
    $('#removeBi').fadeIn();
    var left = (document.body.offsetWidth - 400)/2;
    var top = (document.body.offsetHeight - 190)/3;
    document.getElementById("removeBi").style.top = top+"px";
    document.getElementById("removeBi").style.left = left+"px";
    document.getElementById('confirmyes').onclick = yesfn;
    if(nofn){
        document.getElementById('confirmno').onclick = nofn;
    }else {
        document.getElementById('confirmno').onclick = confirmHide;
    }
}

//对话框隐藏
function confirmHide(){
    document.getElementById('confirmyes').onclick = function () {  };
    $("#confirmTishi").text("");
    $('.zhezhao').css('display', 'none');
    $('#removeBi').fadeOut();
}

//显示弹出框
function alertShow(alertTishi, title){
    if(title){
        $("#alertTitle").text(title);
    }else {
        $("#alertTitle").text("提示");
    }
    $("#alertTishi").text(alertTishi);
    var left = (document.body.offsetWidth - 400)/2;
    var top = (document.body.offsetHeight - 190)/3;
    document.getElementById("alertBi").style.top = top+"px";
    document.getElementById("alertBi").style.left = left+"px";
    $('.zhezhao').css('display', 'block');
    $('#alertBi').fadeIn();
}

function alertHide(){
    $("#alertTishi").text("");
    $('.zhezhao').css('display', 'none');
    $('#alertBi').fadeOut();
}

function modelShow(id, url,modelTitle, width, height){
    $.ajax({
        url: "/user/getLoginUser",
        type: "get",
        dataType: "json",
        async: false,
        data: {},
        success: function (data) {
        }
    });
    $("#modelTitle").text(modelTitle);
    var left = (document.body.offsetWidth - width)/2;
    var top = (document.body.offsetHeight - height)/3;
    document.getElementById("modelBi").style.width = width+"px";
    document.getElementById("modelBi").style.height = height+"px";
    document.getElementById("modelBi").style.top = top+"px";
    document.getElementById("modelBi").style.left = left+"px";
    document.getElementById("modelMain").style.height = (height-78)+"px";
    $("#modelMain").load(url);
    $('.zhezhao').css('display', 'block');
    $('#modelBi').fadeIn();
}
function modelHide(id){
    $("#modelTitle").text("");
    $('.zhezhao').css('display', 'none');
    $('#modelBi').fadeOut();
}

function toLogin(){
    location.href = "../login.html";
}

(function($){
    //首先备份下jquery的ajax方法
    var _ajax=$.ajax;

    //重写jquery的ajax方法
    $.ajax=function(opt){
        //备份opt中error和success方法
        var fn = {
            error:function(XMLHttpRequest, textStatus, errorThrown){},
            success:function(data, textStatus){}
        }
        if(opt.error){
            fn.error=opt.error;
        }
        if(opt.success){
            fn.success=opt.success;
        }

        //扩展增强处理
        var _opt = $.extend(opt,{
            error:function(XMLHttpRequest, textStatus, errorThrown){
                //错误方法增强处理
                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },
            success:function(data, textStatus){
                //成功回调方法增强处理
                if(data.code == "1003"){
                    toLogin();
                }
                if(data.code == "2222"){
                    alertShow("没有权限访问！");
                    return;
                }
                fn.success(data, textStatus);
            },
            beforeSend:function(XHR){
                //提交前回调方法
                // $('body').append("<div id='ajaxInfo' style=''>正在加载,请稍后...</div>");
            },
            complete:function(XHR, TS){
                //请求完成后回调函数 (请求成功或失败之后均调用)。
                // $("#ajaxInfo").remove();;
            }
        });
        return _ajax(_opt);
    };
})(jQuery);
