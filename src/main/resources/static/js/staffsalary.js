/**
 * Created by Yvan on 2018/06/01.
 */
//时间list生成
$(function () {
    var myDate = new Date();
    var endyear = myDate.getFullYear();
    for (i = endyear; i >= 1946; i--) {
        $('#year').append("<option value='" + i + "'>" + i + "年</option>");
    }
    for (j = 1; j <= 12; j++) {
        $('#month').append("<option value='" + j + "'>" + j + "月</option>");
    }
    //查询工资
    search();

    $("#next").click(function(){
        window.location.href = "../../salary/intoPrint/" + $(this).attr("salaryId");
    })
});

//查询员工工资
function search() {
    loadingShow();
    var year = $('#year').val();
    var month = $('#month').val();
    if (year == null || month == null || year == "" || month == "") {
        var myDate = new Date();
        var now_year = myDate.getFullYear();
        var now_month = myDate.getMonth() + 1;
        $("#year").val(now_year);
        $("#month").val(now_month);
        if(now_month < 10){
            var now_time = now_year + '-0' + now_month;
            addTable(now_time);
        }else{
            var now_time = now_year + '-' + now_month;
            addTable(now_time);
        }
    }
    else{
        if(month < 10){
            var time_sel = year + '-0' + month;
            addTable(time_sel);
        }else{
            var time_sel = year + '-' + month;
            addTable(time_sel);
        }
    }
    loadingHide();
}

//得到员工信息，选择展示页面
function addTable(salaryDate) {

    $.post("../../staff/findSalary", {'salaryDate': salaryDate}, function (data) {
        if(data.code == "1011"){
            $("#usertable").html("<tr><th>本月暂无工资信息！</th>></tr>");
        }
        if(data.code != "0000"){
            return;
        }
        var reVal = data.data;
        // if (reVal.emp_type = 0) {
        if (reVal.emp_type == 0){
            $("#next").siblings().hide();
            $("#next").show();
        }else {
            $("#next").hide();
            $("#next").siblings().show();
        }
        Generatdata(reVal, salaryDate);
        $("#next").attr("salaryId", reVal.salaryInfo.salaryId);
        // } else {
        //     GeneratHTdata(reVal,salaryDate);
        // }
    }, 'json');
}


function Generatdata(result, salaryDate) {
    var html = '';
    if (result.emp_type == 0) {
        var htmlHead = '<tr class="firstTr">' +
            '<th width="10%" colspan="5" style="size:40px; height:60px;"><img src="../../static/img/logo.jpeg" width="50px" height="50px" align="center" style="padding-left: 20px;">&nbsp;&nbsp;&nbsp;' + salaryDate + ' 山西医科大学第六医院职工工资明细表</th>' +
            '</tr>';
    }else{
        var htmlHead = '<tr class="firstTr">' +
            '<th width="10%" colspan="5" style="size:40px; height:60px;"><img src="../../static/img/logo.jpeg" width="50px" height="50px" align="center" style="padding-left: 20px;">&nbsp;&nbsp;&nbsp;' + salaryDate + ' 山西医科大学第六医院合同制职工工资明细表</th>' +
            '</tr>';
    }
    html += '<tr>' +
        '<th width="20%">姓名</th>' +
        '<th width="20%" colspan="2">' + result.salaryInfo.employee.empName + '</th>' +
        '<th width="20%">职工编号</th>' +
        '<th width="20%">' + result.salaryInfo.employee.empId + '</th>' +
        '</tr><tr>' +
        '<th width="20%">身份证号</th>' +
        '<th width="20%" colspan="4">' + (result.salaryInfo.employee.empCardNum == null?'':result.salaryInfo.employee.empCardNum) + '</th></tr>';
    for (var k = 0, length = result.salaryInfo.salaryTypeEmpList.length; k < length; k++) {
        html +=
            '<tr>' +
            '<th width="20%">' + result.salaryInfo.salaryTypeEmpList[k].salaryTypeObj.salaryName + '</th>' +
            '<th width="20%" colspan="2">' + result.salaryInfo.salaryTypeEmpList[k].money / 10000 + '</th>';
        if (k + 1 < length) {
            html +=
                '<th width="20%">' + result.salaryInfo.salaryTypeEmpList[k + 1].salaryTypeObj.salaryName + '</th>' +
                '<th width="20%">' + result.salaryInfo.salaryTypeEmpList[k + 1].money / 10000 + '</th>';
        }
        html += '</tr>';
        k++;
    }
    if (result.emp_type == 0) {
        html += '<tr><th width="20%">应领工资</th>' +
            '<th width="20%" colspan="2">' + result.salaryInfo.grossPay / 10000 + '</th>' +
            '<th width="20%">实领工资</th>' +
            '<th width="20%">' + result.salaryInfo.netPayroll / 10000 + '</th>'+
            '</tr><tr><th></th></tr><tr>' +
            '<th width="20%" style="height: 30px;">其他奖明细</th>' +
            '<th width="20%" colspan="4"></th>' +
            '</tr>' + '<tr>';
    }else{
        html += '<tr><th width="20%">应领工资</th>' +
            '<th width="20%" colspan="4">' + result.salaryInfo.grossPay / 10000 + '</th>' +
            '</tr><tr><th></th></tr><tr>' +
            '<th width="20%" style="height: 30px;">其他奖明细</th>' +
            '<th width="20%" colspan="4"></th>' +
            '</tr>' + '<tr>';
    }
    if(result.bonusInfo.length == 0){
        html +='<th width="20%" colspan="5">本月无其他奖明细</th></tr>'
    }else {
        html += '<th width="20%">序号</th>' +
            '<th width="20%">金额</th>' +
            '<th width="20%" colspan="2">内容</th>' +
            '<th width="20%">归口管理部门</th>' +
            '</tr>';
        for (var i = 0; i < result.bonusInfo.length; i++) {
             a = i+1;
            html += '<tr><th width="20%">' + a + '</th>' +
                '<th width="20%">' + result.bonusInfo[i].money + '</th>' +
                '<th width="20%" colspan="2">' + result.bonusInfo[i].cont + '</th>' +
                '<th width="20%">' + result.bonusInfo[i].manageDepart + '</th>' +
                '</tr>';
        }
    }

    if (result.emp_type == 0) {
        html += '<tr><th></th></tr><tr style="height: 40px;"><th width="20%">组成规则</th>' +
            '<th width="20%" colspan="4">' +
            '应领工资=应领A卡+B卡\n</br>' +
            '实领工资=应领工资-个税-养老-医疗-失业-公积金-扣款</br>' +
            '其他奖包括预算奖，具体见科室薪酬发放构成表</th></br>' +
            '</tr>';
        $("#usertable").html(htmlHead + html);
    } else {
        html += '<tr><th></th></tr><tr style="height: 40px;"><th width="20%">组成规则</th>' +
            '<th width="20%" colspan="4">' +
            '其他奖包括预算奖，具体见科室薪酬发放构成表</th></br>' +
            '</tr>';
        $("#usertable").html(htmlHead + html);
    }
}
