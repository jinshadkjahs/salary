

var queryEmp = function (pageNum) {
    loadingShow();
    var empNo = $("#empNo").val();
    var empName = $("#empName").val();
    var empType = $("#empType").val();

    $.post("../../emp/queryEmpInfo", {'empNo':empNo,'empName':empName,'pageNum':pageNum,'empType':empType},function(data){
        var reVal = data.data;
       var reData = reVal.list;

       console.log(data);
       var html = '';
       var htmlHead = '<tr class="firstTr">\n' +
           '                <th width="10%">员工编号</th>\n' +
           '                <th width="20%">员工姓名</th>\n' +
           '                <th width="10%">手机号</th>\n' +
           '                <th width="10%">身份证编号</th>\n' +
           '                <th width="10%">员工类别</th>\n' +
           '                <th width="10%">基本工资</th>\n' +
           '                <th width="10%">操作</th>\n' +
           '            </tr>';
       if(reData.length>0){
           for(var i=0; i<reData.length; i++){
               var empPhone = reData[i].empPhone;
               var empCardNum = reData[i].empCardNum;
               var empType = reData[i].empType;
               var empTypeDesc = "";
               if(empType === '0'){
                   empTypeDesc = "正式员工";
               }
               if(empType === '1'){
                   empTypeDesc = "合同员工";
               }
               if(empPhone==null){
                   empPhone="";
               }
               if(empCardNum==null){
                   empCardNum="";
               }
               html += '    <tr>\n' +
                   '                <td id="empId'+i+'">'+reData[i].empId+'</td>\n' +
                   '                <td>'+reData[i].empName+'</td>\n' +
                   '                <td>'+empPhone+'</td>\n' +
                   '                <td>'+empCardNum+'</td>\n' +
                   '                <td>'+empTypeDesc+'</td>\n' +
                   '                <td>'+reData[i].baseSalary+'</td>\n' +
                   '                <td>\n' +
                   '                    <a href="billView.html"><img src="../static/img/read.png" alt="查看" title="查看" /></a>\n' +
                   '                    <a href="billUpdate.html"><img src="../static/img/xiugai.png" alt="修改" title="修改" /></a>\n' +
                   '                    <a onclick="delEmp(this)" class="removeBill"><img src="../static/img/schu.png" alt="删除" title="删除"/></a>\n' +
                   '                </td>\n' +
                   '            </tr>';

           }
       }
       $("#employee").html(htmlHead+html);
       $("#empPage").html(getPageHtml(reVal.pageNum,reVal.pages,"pageClick"));
       loadingHide();
       },'json');

}

function pageClick(obj) {
    queryEmp(parseInt($(obj).attr("forPage")));
}

var delEmp = function(obj){
    var txt=  "确定删除该员工信息？";
    confirmShow(txt,function (){
        loadingShow();
        confirmHide();
        var empTr = getRowObj(obj);
        var empId = $(empTr).children().eq(0).text();
        $.post('../../emp/deleteEmp',{'empId':empId},function (data) {
            if(data.code === '0000'){
                empTr.parentNode.removeChild(empTr);
                alertShow(data.message);
            }else if(data.code === '1006'){
                alertShow(data.message);
            }
            loadingHide();
        },'json');

    })
}

//获取行对象
function getRowObj(obj){
    var i = 0;
    while(obj.tagName.toLowerCase() != "tr"){
        obj = obj.parentNode;
        if(obj.tagName.toLowerCase() == "table")
            return null;
    }
    return obj;
}

function openImportEmp(){
    modelShow("str","../../employee/importImp.html","员工导入",600,400);
}

var importEmp = function () {

   if(checkData()){
       $.ajax({
           url: "../../emp/importEmp",
           type: 'POST',
           cache: false,
           data: new FormData($("#imoprtForm")[0]),
           processData: false,
           contentType: false,
           success: function (result) {
           },
           error: function (err) {
           }
       });
   }

}

function checkData(){
    var fileName = $("#importEmp").val();
    var suffix = fileName.substr(fileName.lastIndexOf(".") + 1);
    alert(suffix);
    if("" == fileName){
        alert("选择需要导入的Excel文件！");
        return false;
    }
    if("xls" != suffix && "xlsx" != suffix ){
        alert("选择Excel格式的文件导入！");
        return false;
    }
    return true;
}