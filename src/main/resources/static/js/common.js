/**
 * Created by Administrator on 2017/6/2.
 */
//校验IP格式
function isIP(ip)
{
    var reSpaceCheck = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
    if (reSpaceCheck.test(ip))
    {
        ip.match(reSpaceCheck);
        if (RegExp.$1<=255&&RegExp.$1>=0
            &&RegExp.$2<=255&&RegExp.$2>=0
            &&RegExp.$3<=255&&RegExp.$3>=0
            &&RegExp.$4<=255&&RegExp.$4>=0)
        {
            return true;
        }else
        {
            return false;
        }
    }else
    {
        return false;
    }
}

//时间格式
Date.prototype.Format = function (fmt) {
    var Milliseconds=this.getMilliseconds();
    if(this.getMilliseconds()<10){
        Milliseconds='00'+this.getMilliseconds();
    }else if(this.getMilliseconds()<100){
        Milliseconds='0'+this.getMilliseconds();
    }
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S":  Milliseconds //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}