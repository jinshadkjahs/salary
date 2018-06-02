
//loading显示
function loadingShow() {
    $('#loading').show();
}

//loading隐藏
function loadingHide() {
    $('#loading').hide();
}

//对话框调用
function confirmShow(confirmTishi, functionName, args){
    $("#confirmTishi").text(confirmTishi);
    $('.zhezhao').css('display', 'block');
    $('#removeBi').fadeIn();
    $("#confirmyes").attr("onclick","iframe.window."+functionName+"("+args+")");
}

//对话框隐藏
function confirmHide(){
    $("#confirmyes").attr("onclick","");
    $('.zhezhao').css('display', 'none');
    $('#removeBi').fadeOut();
}

$(function () {
    $('#confirmno').click(function () {
        $('.zhezhao').css('display', 'none');
        $('#removeBi').fadeOut();
    });

});