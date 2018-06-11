//拼接分页
function getPageHtml(pageNum, pageToal, functionName) {
    if(!pageToal || pageToal == 0){
        pageToal = 1;
    }
    if(!pageNum || pageNum == 0){
        pageNum = 1;
    }
    var firstPage = "onclick='"+functionName+"(this)'";
    var lastPage = "onclick='"+functionName+"(this)'";
    var prevPageNumber = pageNum-1;
    var nextPageNumber = pageNum+1;
    if(pageNum == 1){
        firstPage = "class='disabled'";
        prevPageNumber = 1;
    }
    if(pageNum == pageToal){
        lastPage = "class='disabled'";
        nextPageNumber = pageToal;
    }
    var pageHtml = "<link rel='stylesheet' href='../../static/css/page.css'>";
    pageHtml += "    <div>"+
                        "<div class='pagination'>";
    pageHtml +=             "<span "+firstPage+" forPage = '1' title='首页'>首页</span>" +
                            "<span "+firstPage+" title='上一页' forPage = '"+prevPageNumber+"'>上一页</span>";
    if(pageToal > 9){
        if(pageNum<4 || pageNum>pageToal-3){
            for(var i=1; i<=4; i++){
                if(pageNum == i){
                    pageHtml += "<span class='current' forPage = '"+i+"'>"+i+"</span>";
                }else {
                    pageHtml += "<span onclick='"+functionName+"(this)' forPage = '"+i+"'>"+i+"</span>";
                }
            }
            pageHtml += "   <span class='disabled'>...</span>";
            for(var i=pageToal-3; i<=pageToal; i++){
                if(pageNum == i){
                    pageHtml += "<span class='current' forPage = '"+i+"'>"+i+"</span>";
                }else {
                    pageHtml += "<span onclick='"+functionName+"(this)' forPage = '"+i+"'>"+i+"</span>";
                }
            }
        }else {
            pageHtml += "   <span onclick='"+functionName+"(this)' forPage = '"+1+"'>"+1+"</span>";
            pageHtml += "   <span class='disabled'>...</span>";
            pageHtml += "   <span onclick='"+functionName+"(this)' forPage = '"+(pageNum-2)+"'>"+(pageNum-2)+"</span>";
            pageHtml += "   <span onclick='"+functionName+"(this)' forPage = '"+(pageNum-1)+"'>"+(pageNum-1)+"</span>";
            pageHtml += "   <span class='current' forPage = '"+pageNum+"'>"+pageNum+"</span>";
            pageHtml += "   <span onclick='"+functionName+"(this)' forPage = '"+(pageNum+1)+"'>"+(pageNum+1)+"</span>";
            pageHtml += "   <span onclick='"+functionName+"(this)' forPage = '"+(pageNum+2)+"'>"+(pageNum+2)+"</span>";
            pageHtml += "   <span class='disabled'>...</span>";
            pageHtml += "   <span onclick='"+functionName+"(this)' forPage = '"+pageToal+"'>"+pageToal+"</span>";
        }
    }else {
        for(var i=1; i<=pageToal; i++){
            if(pageNum == i){
                pageHtml += "<span class='current' forPage = '"+i+"'>"+i+"</span>";
            }else {
                pageHtml += "<span onclick='"+functionName+"(this)' forPage = '"+i+"'>"+i+"</span>";
            }
        }
    }

    pageHtml +=             "<span "+lastPage+" title='下一页' forPage = '"+nextPageNumber+"'>下一页</span>" +
                            "<span "+lastPage+" title='尾页' forPage = '"+pageToal+"'>尾页</span>";

    pageHtml += "       </div>"+
                    "</div>";
    return pageHtml;
}