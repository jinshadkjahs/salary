
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
    var top = (document.body.offsetHeight - 190)/2;
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
    var top = (document.body.offsetHeight - 190)/2;
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
    $("#modelTitle").text(modelTitle);
    var left = (document.body.offsetWidth - width)/2;
    var top = (document.body.offsetHeight - height)/2;
    document.getElementById("modelBi").style.width = width+"px";
    document.getElementById("modelBi").style.height = height+"px";
    document.getElementById("modelBi").style.top = top+"px";
    document.getElementById("modelBi").style.left = left+"px";
    document.getElementById("modelMain").style.height = (height-113)+"px";
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
    location.href = "../loin.html";
}


