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
        var now_time = now_year + '-0' + now_month;
        addTable(now_time);
    }
    else {
        var time_sel = year + '-0' + month;
        addTable(time_sel);
    }
    loadingHide();
}

//得到员工信息，选择展示页面
function addTable(salaryDate) {
    $.post("../../staff/findSalary", {'salaryDate': salaryDate}, function (data) {
        var reVal = data.data;
        // if (reVal.emp_type = 0) {
        Generatdata(reVal,salaryDate);
        // } else {
        //     GeneratHTdata(reVal,salaryDate);
        // }
    }, 'json');
}


function Generatdata(result,salaryDate) {
    var html = '';
    var htmlHead = '<tr class="firstTr">' +
        '<th width="10%" colspan="5">'+salaryDate+'  山西医科大学第六医院职工工资明细表</th>' +
        '</tr>';
    html += '<tr>' +
        '<th width="20%">姓名</th>' +
        '<th width="20%" colspan="2">'+result.salaryInfo.employee.empName+'</th>' +
        '<th width="20%">职工编号</th>' +
        '<th width="20%">'+result.salaryInfo.employee.empId+'</th>' +
        '</tr><tr>' +
        '<th width="20%">身份号</th>' +
        '<th width="20%" colspan="4">'+result.salaryInfo.employee.empCardNum+'</th></tr>';
    for (var k = 0, length = result.salaryInfo.salaryTypeEmpList.length; k < length; k++) {
        html +=
            '<tr>' +
            '<th width="20%">'+result.salaryInfo.salaryTypeEmpList[k].salaryTypeObj.salaryName+'</th>' +
            '<th width="20%" colspan="2">'+result.salaryInfo.salaryTypeEmpList[k].money/10000+'</th>';
        if(k+1<length){
            html +=
                '<th width="20%">'+result.salaryInfo.salaryTypeEmpList[k+1].salaryTypeObj.salaryName+'</th>' +
                '<th width="20%">'+result.salaryInfo.salaryTypeEmpList[k+1].money/10000+'</th>';
        }
        html += '</tr>';
        k++;
    }
    html +=   '<tr><th width="20%">应领工资</th>' +
        '<th width="20%" colspan="4">'+result.salaryInfo.grossPay/10000+'</th>' +
        '</tr><tr>' +
        '<th width="20%">其他奖明细</th>' +
        '<th width="20%" colspan="4"></th>' +
        '</tr>'+'<tr>';
    html +='<th width="20%">序号</th>' +
        '<th width="20%">金额</th>' +
        '<th width="20%" colspan="2">内容</th>' +
        '<th width="20%">归口管理部门</th>' +
        '</tr>';
    for(var i=0;i<result.bonusInfo.length;i++){
        html +='<tr><th width="20%">'+result.bonusInfo[i].id+'</th>' +
            '<th width="20%">'+result.bonusInfo[i].money+'</th>' +
            '<th width="20%" colspan="2">'+result.bonusInfo[i].cont+'</th>' +
            '<th width="20%">'+result.bonusInfo[i].manageDepart+'</th>' +
            '</tr>';
    }
    $("#usertable").html(htmlHead + html);
}

//
// function GeneratZSdata(result,salaryDate) {
//     var html = '';
//     var htmlHead = '<tr class="firstTr">\n' +
//         '<th width="10%" colspan="5">****年**月医疗公司职工工资明细表</th>\n' +
//         '</tr>';
//     html += '<tr>\n' +
//         '                        <th width="20%">姓名</th>\n' +
//         '                        <th width="20%" colspan="2">张三</th>\n' +
//         '                        <th width="20%">职工编号</th>\n' +
//         '                        <th width="20%">HT789378939</th>\n' +
//         '                    </tr>'+'<tr>\n' +
//         '                        <th width="20%">身份号</th>\n' +
//         '                        <th width="20%" colspan="2">149902198909097811</th>\n' +
//         '                        <th width="20%">养老保险好</th>\n' +
//         '                        <th width="20%">9378939</th>\n' +
//         '                    </tr>'+'<tr>\n' +
//         '                        <th width="20%" rowspan="4">固定资产</th>\n' +
//         '                        <th width="20%">工龄工资</th>\n' +
//         '                        <th width="20%">300.00</th>\n' +
//         '                        <th width="20%">保留工资</th>\n' +
//         '                        <th width="20%">120</th>\n' +
//         '                    </tr>'+'<tr>\n' +
//         '                        <th width="20%">各种补贴</th>\n' +
//         '                        <th width="20%">300.00</th>\n' +
//         '                        <th width="20%">回民</th>\n' +
//         '                        <th width="20%">120</th>\n' +
//         '                    </tr>'+'<tr>\n' +
//         '                        <th width="20%">房补</th>\n' +
//         '                        <th width="20%">300.00</th>\n' +
//         '                        <th width="20%">专项奖</th>\n' +
//         '                        <th width="20%">120</th>\n' +
//         '                    </tr>'+'<tr>\n' +
//         '                        <th width="20%">书报费</th>\n' +
//         '                        <th width="20%">300.00</th>\n' +
//         '                        <th width="20%">卫生费</th>\n' +
//         '                        <th width="20%">120</th>\n' +
//         '                    </tr>'+'<tr>\n' +
//         '                        <th width="20%" rowspan="2">浮动工资</th>\n' +
//         '                        <th width="20%">岗薪工资</th>\n' +
//         '                        <th width="20%">300.00</th>\n' +
//         '                        <th width="20%">绩效工资</th>\n' +
//         '                        <th width="20%">120</th>\n' +
//         '                    </tr>'+'<tr>\n' +
//         '                        <th width="20%">节日加班</th>\n' +
//         '                        <th width="20%">300.00</th>\n' +
//         '                        <th width="20%">其他奖</th>\n' +
//         '                        <th width="20%">120</th>\n' +
//         '                    </tr>'+'<tr>\n' +
//         '                        <th width="20%">其他</th>\n' +
//         '                        <th width="20%">补发</th>\n' +
//         '                        <th width="20%">300.00</th>\n' +
//         '                        <th width="20%">扣款</th>\n' +
//         '                        <th width="20%">120</th>\n' +
//         '                    </tr>'+' <tr>\n' +
//         '                        <th width="20%" rowspan="3">缴费部分</th>\n' +
//         '                        <th width="20%">个税</th>\n' +
//         '                        <th width="20%">300.00</th>\n' +
//         '                        <th width="20%">养老</th>\n' +
//         '                        <th width="20%">120</th>\n' +
//         '                    </tr>'+'<tr>\n' +
//         '                        <th width="20%">医疗</th>\n' +
//         '                        <th width="20%">300.00</th>\n' +
//         '                        <th width="20%">失业</th>\n' +
//         '                        <th width="20%">120</th>\n' +
//         '                    </tr>'+'<tr>\n' +
//         '                        <th width="20%">公积金</th>\n' +
//         '                        <th width="20%">300.00</th>\n' +
//         '                        <th width="20%">其他</th>\n' +
//         '                        <th width="20%">120</th>\n' +
//         '                    </tr>'+'<tr>\n' +
//         '                        <th width="20%">应领工资</th>\n' +
//         '                        <th width="20%" colspan="2">3000.00</th>\n' +
//         '                        <th width="20%">实领工资</th>\n' +
//         '                        <th width="20%">3000.00</th>\n' +
//         '                    </tr>'+'<tr>\n' +
//         '                        <th width="20%">其他奖明细</th>\n' +
//         '                        <th width="20%" colspan="4"></th>\n' +
//         '                    </tr>'+' <tr>\n' +
//         '                        <th width="20%">序号</th>\n' +
//         '                        <th width="20%">金额</th>\n' +
//         '                        <th width="20%" colspan="2">内容</th>\n' +
//         '                        <th width="20%">归口管理部门</th>\n' +
//         '                    </tr>';
//     $("#usertable").html(htmlHead + html);
// }