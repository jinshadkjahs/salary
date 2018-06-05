/**
 * Created by Yvan on 2018/06/01.
 */
function addTable(salaryDate) {
    $.post("../../staff/findSalary", {'salaryDate':salaryDate},function(data){
        alert(000);
        var reVal = data.data;

        alert(reVal);

        $("#providerTable").append("<tr class=\"firstTr\">\n" +
            "        <th width=\"10%\">序号</th>\n" +
            "        <th width=\"10%\">职工编号</th>\n" +
            "        <th width=\"10%\">科室</th>\n" +
            "        <th width=\"10%\">姓名</th>\n" +
            "        <th width=\"10%\">类别</th>\n" +
            "        <th width=\"10%\">日期</th>\n" +
            "        <th width=\"10%\">应领工资</th>\n" +
            "        <th width=\"10%\">实领工资</th>\n" +
            "        <th width=\"15%\">操作</th>\n" +
            "    </tr>");
    },'json');
}

//查询员工工资
function search() {
    loadingShow();
    var year = $('#year').val();
    var month = $('#month').val();
    if (year == null || month == null || year == "" || month == "") {
        var myDate = new Date();
        var now_year = myDate.getFullYear();
        var now_month = myDate.getMonth()+1;

        var now_time = now_year + '-0' + now_month;
        alert(now_time);
        addTable(now_time);
    }
    else {
        var time_sel = year + '-0' + month;
        addTable(time_sel);
    }
    loadingHide();
}

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
    alert(1);
    search();
    alert(2);
});

//展示奖金
function ShowbBonus() {
    alert("展示奖金页");
}

//打印工资信息
function Printing() {
    alert("下载打印 PDF 文件！");
}
