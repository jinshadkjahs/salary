

var queryEmp = function (pageNum) {
    loadingShow();
    var empNo = $("#empNo").val();
    var empName = $("#empName").val();
    var empType = $("#empType").val();

    $.post("../../emp/queryEmpInfo", {'empNo':empNo,'empName':empName,'empType':empType,'pageNum':pageNum},function(data){
        var reVal = data.data;
       var reData = reVal.list;
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
       if(data.code==='0000'){
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
                   '                    <a onclick="motifyEmp(\''+reData[i].empId+'\')"><img src="../static/img/xiugai.png" alt="修改" title="修改" /></a>\n' +
                   '                    <a onclick="delEmp(this)" class="removeBill"><img src="../static/img/schu.png" alt="删除" title="删除"/></a>\n' +
                   '                </td>\n' +
                   '            </tr>';

           }
       }

        if (reData.length < 10) {
            for (var i = reData.length; i < 10; i++) {
                html+='<tr style=\'height: 40.8px\'>\n' +
                    '                <td></td>\n' +
                    '                <td></td>\n' +
                    '                <td></td>\n'+
                    '                <td></td>\n' +
                    '                <td></td>\n' +
                    '                <td></td>\n' +
                    '                <td>\n' +
                    '                    <a><img style="display: none;" src="../static/img/xiugai.png" alt="修改" title="修改" /></a>\n' +
                    '                    <a><img style="display: none;" src="../static/img/schu.png" alt="删除" title="删除"/></a>\n' +
                    '                </td>\n' +
                    '            </tr>';
            }
        }
       var lastHtml = htmlHead+html;
       console.log(lastHtml);
       $("#employee").html(lastHtml);
       $("#empPage").html(getPageHtml(reVal.pageNum,reVal.pages,"pageClick"));
       loadingHide();
       },'json');

}

function pageClick(obj) {
    queryEmp(parseInt($(obj).attr("forPage")));
}

function motifyEmp(empId) {
    window.location.href="../../emp/intoModifyEmp/"+empId;
}



function employeeModifySave(obj) {
    var isOk = inputCheck(obj);
    if(isOk) {
        confirmShow("确认提交吗", function () {


            loadingShow();
            $.ajax({
                url: "../../emp/modifyEmp?" + $("#employeeUpdateForm").serialize(),
                type: "put",
                dataType: "json",
                async: false,
                data: {},
                success: function (data) {
                    confirmHide();
                    loadingHide();
                    if (data.code == "0000") {
                        alertShow(data.message);
                        location.href = "/employee/billList.html";
                    } else {
                        alertShow(data.message);
                    }
                }
            });
        });
    }
}
//表单校验
var inputCheck = function (obj) {
    var isOk=true;
    var empPhone = $('#empPhone').val();
    var empCardNum = $('#empCardNum').val();
    console.log(empPhone);
    console.log(empCardNum);
    var idCardRegex = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    var mobileRegex = /^(((1[3456789][0-9]{1})|(15[0-9]{1}))+\d{8})$/;
    if(!idCardRegex.test(empCardNum) && empCardNum!=='' && empCardNum!= undefined){
        //身份证号填写有误！
        $('#empCardNum').val("身份证编码填写有误!");
        $('#empCardNum').css({color:'red'});
        isOk = false;
    }
    if(!mobileRegex.test(empPhone) && empPhone!=='' && empPhone!=undefined){
        $('#empPhone').val("手机号码填写有误!");
        $('#empPhone').css({color:'red'});
        isOk = false;
    }
    return isOk;
}
//员工修改页面加载员工信息
function loadEmployeeData(empId) {
    $.ajax({
        url: "../../emp/getEmp?empId=" + empId,
        type: "get",
        dataType: "json",
        async: false,
        data: {},
        success: function (data) {
            if (data.code == "0000") {
                var empData = data.data;
                console.log(empData);
                $("#empId").val(empData.empId);
                $("#empName").val(empData.empName);
                $("#empPhone").val(empData.empPhone);
                $("#empCardNum").val(empData.empCardNum);
                var empType = empData.empType;
                console.log(empType);
                $("#empType").val(empData.empType==="0"?"正式员工":"合同员工");
                $("#baseSalary").val(empData.baseSalary);
            }else{
                alertShow(data.message);
            }
        }
    });
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
    modelShow("importEmployee","../../employee/importImp.html","导入",400,300);
}

var importEmp = function () {
    var file = parent.$("#importEmp").val();
    var fileName = file.substring(file.lastIndexOf(".")+1);
    parent.$("#showTishi").text("");
    if(!file && file == ""){
        return;
    }
    if(fileName != "xlsx" && fileName != "xls"){
        parent.$("#showTishi").text("文件格式必须是xlsx或者xls！");
        return;
    }
    loadingShow();
    parent.$.ajaxFileUpload
    (
        {
            url: '../../emp/importEmp', //用于文件上传的服务器端请求地址
            secureuri: false,           //一般设置为false
            fileElementId: "importEmp", //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值
            //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
            dataType: 'json',//返回值类型 一般设置为json
            enctype:'multipart/form-data',//注意一定要有该参数
            complete: function () {//只要完成即执行，最后执行
            },
            success: function (data, status)  //服务器成功响应处理函数
            {
                loadingHide();
                if(data.code == "0000"){
                    var importData = data.data;
                    debugger;
                    console.log(importData);
                    modelHide("importEmployee");
                    if(importData.allImport){
                        alertShow("导入完成，共导入"+importData.successNums+"条!");
                    }else if(!importData.allImport){
                        var modifyNums = importData.failNums;
                        if(modifyNums>=0){
                            alertShow("导入完成，导入成功"+importData.successNums+"条！"+"已修改"+importData.failNums+"条:"+importData.failEmpNo+"");
                        }
                    }

                    location.href = "/employee/billList.html";

                }else if(data.code == "1008"){
                    parent.$("#showTishi").text("没有选择文件！")
                }
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                loadingHide();
                modelHide("importEmployee");
                alertShow("员工数据导入失败："+e);
            }
        }
    )
}

function checkData(){
    var fileName = $("#importEmp").val();
    var suffix = fileName.substr(fileName.lastIndexOf(".") + 1);
    console.log(fileName);
    if("" == fileName){
        parent.$("#showTishi").text("请选择需要导入的Excel文件！");
        return false;
    }
    if("xls" != suffix && "xlsx" != suffix ){
        parent.$("#showTishi").text("请选择Excel格式的文件导入！");
        return false;
    }
    return true;
}

var exportEmpModel=function (importType) {
    //正式工模板
    if(importType==='0'){


    }
    //合同工模板
    if(importType==='1'){

    }

}