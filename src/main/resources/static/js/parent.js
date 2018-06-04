
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
    $('.zhezhao').css('display', 'none');
    $('#removeBi').fadeOut();
}
function toLogin(){
    location.href = "../loin.html";
}

