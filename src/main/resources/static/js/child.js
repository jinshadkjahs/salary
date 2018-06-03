function loadingShow() {
    parent.loadingShow();
}

//loading隐藏
function loadingHide() {
    parent.loadingHide();
}

//对话框调用 提示信息，方法名，传入参数
function confirmShow(confirmTishi, functionName, args){
    parent.confirmShow(confirmTishi, functionName, args);
}

//对话框隐藏
function confirmHide(){
    parent.confirmHide();
}

function toLogin(code) {
    if(data.code == "1111" || data.code == "1003"){
        parent.toLogin();
    }
}