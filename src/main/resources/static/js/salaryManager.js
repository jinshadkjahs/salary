var salaryTypeList;

function loadDateData() {
    var myDate = new Date();
    var startyear = 1946;
    var endyear = myDate.getFullYear();
    $("#salaryYear").empty();
    $("#salaryYear").append("<option value=''>--请选择--</option>");
    for (i = endyear; i > startyear; i--) {
        $('#salaryYear').append("<option value='" + i + "'>" + i + "年</option>");
    }
    $("#salaryMonth").empty();
    $("#salaryMonth").append("<option value=''>--请选择--</option>");
    for (i = 1; i <= 12; i++) {
        var ss = "" + i;
        if (i < 10) {
            ss = "0" + i;
        }
        $('#salaryMonth').append("<option value='" + ss + "'>" + i + "月</option>");
    }
    var time = $(window.parent.document).find("#time").text().substring(0, 7).split("年");
    var year = time[0];
    // var monval = "" + (parseInt(time[1]));
    var monval = time[1];
    // if (time[1] == "1") {
    //     year = "" + (parseInt(time[0]) - 1);
    //     monval = 12;
    // } else {
    //     if ((parseInt(time[1]) - 1) < 10) {
    //         monval = "0" + (parseInt(time[1]) - 1);
    //     }
    // }
    $("#salaryMonth").val(monval);
    $("#salaryYear").val(year);
}

function loadDepartData() {
    $.ajax({
        url: "../../salary/getDeparts",
        type: "get",
        dataType: "json",
        async: false,
        data: {},
        success: function (data) {
            $("#departId").empty();
            $("#departId").append("<option value=''>--请选择--</option>");
            if (data.code == "0000") {
                var departs = data.data;
                for (var i = 0; i < departs.length; i++) {
                    $("#departId").append("<option value='" + departs[i].departid + "'>" + departs[i].departName + "</option>");
                }
            }
        }
    });
}

function loadEmpTypeData() {
    $.ajax({
        url: "../../salary/getEmpTypes",
        type: "get",
        dataType: "json",
        async: false,
        data: {},
        success: function (data) {
            $("#empType").empty();
            if (data.code == "0000") {
                var empTypes = data.data;
                $("#empType").append("<option value=''>--请选择--</option>");
                for (var i = 0; i < empTypes.length; i++) {
                    $("#empType").append("<option value='" + empTypes[i].empType + "'>" + empTypes[i].typeName + "</option>");
                }
            }
        }
    });
}

function queryPage(pageNum) {
    loadingShow();
    $("#pageN").val(pageNum);
    $("#salaryDate").val($("#salaryYear").val() + "-" + $("#salaryMonth").val());

    var queryPam = $("#salarySearchForm").serialize();
    $.getJSON("../../salary/getSalarys", queryPam, function (data) {
        if (data.code == "0000") {
            $("#dataTab").empty();
            $("#dataTab").append("<tr class=\"firstTr\">\n" +
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
            var listData = data.data.list;
            for (var i = 0; i < listData.length; i++) {
                $("#dataTab").append("<tr>" +
                    "<td>" + (i + 1) + "</td>" +
                    "        <td>" + listData[i].empId + "</td>" +
                    "        <td>" + listData[i].departId + "</td>" +
                    "        <td>" + listData[i].empName + "</td>" +
                    "        <td>" + listData[i].empType + "</td>" +
                    "        <td>" + listData[i].salaryDate + "</td>" +
                    "        <td>" + (listData[i].grossPay) / 10000 + "</td>" +
                    "        <td>" + (listData[i].netPayroll) / 10000 + "</td>" +
                    "        <td><a href='#' onclick='showSalary(\"" + listData[i].salaryId + "\")'><img src=\"../../static/img/read.png\" alt=\"查看\" title=\"查看\"/></a>\n" +
                    "                    <a href='#' onclick='updateSalary(\"" + listData[i].salaryId + "\")'><img src=\"../../static/img/xiugai.png\" alt=\"修改\" title=\"修改\"/></a>\n" +
                    "                    <a href='#' onclick='removeSalary(\"" + listData[i].salaryId + "\")'><img src=\"../../static/img/schu.png\" alt=\"删除\" title=\"删除\"/></a></td>" +
                    "</tr>");
            }
            if (listData.length < 10) {
                for (var i = listData.length; i < 10; i++) {
                    $("#dataTab").append("<tr style='height: 42px'>" +
                        "                <td></td>" +
                        "                <td></td>" +
                        "                <td></td>" +
                        "                <td></td>" +
                        "                <td></td>" +
                        "                <td></td>" +
                        "                <td></td>" +
                        "                <td></td>" +
                        "                <td></td>" +
                        "            </tr>");
                }
            }
            $("#pageInfo").html(getPageHtml(data.data.pageNum, data.data.pages, "queryPageClick"));
        }
        loadingHide();
    });
}

function queryPageClick(obj) {
    queryPage($(obj).attr("forPage"));
}

function showSalary(salaryId) {
    window.location.href = "../../salary/intoShow/" + salaryId;
}

function updateSalary(salaryId) {
    window.location.href = "../../salary/intoUpdate/" + salaryId;
}

function removeSalary(salaryId) {
    confirmShow("确认删除这条工资信息吗", function () {
        loadingShow();
        $.ajax({
            url: "../../salary/deleteSalary?salaryId=" + salaryId,
            type: "DELETE",
            dataType: "json",
            async: false,
            data: {},
            success: function (data) {
                if (data.code == "0000") {
                    confirmHide();
                    alertShow("删除成功！");
                    queryPage($(".current").attr("forPage"));
                } else {
                    confirmHide();
                    alertShow("删除失败！");
                }
            }
        });
        loadingHide();
    })
}

function loadSalaryTypeData() {
    $.ajax({
        url: "../../salary/getSalaryTypes",
        type: "get",
        dataType: "json",
        async: false,
        data: {},
        success: function (data) {
            if (data.code == "0000") {
                salaryTypeList = data.data;
            }
        }
    });

}

function loadSalaryTypeTab(empType) {
    $("#salaryInput").empty();
    if (salaryTypeList) {
        var list;
        if ("0" == empType) {
            list = salaryTypeList.formal;
        } else {
            list = salaryTypeList.pact;
        }
        var jiaTr = "";
        var jiaNum = 1;
        var jianTr = "";
        var jianNum = 1;
        var tr = "";
        var num = 1;
        for (var i = 0; i < list.length; i++) {
            if (list[i].type == "0") {
                if (jiaNum % 2 == 0) {
                    jiaTr += "<td class='titletd'>" + list[i].salaryName + ":</td><td><input onkeyup=\"value=value.replace(/[^\\d{1,}\\.\\d{1,}|\\d{1,}]/g,'')\" maxlength='15' id='salaryType_" + list[i].salaryType + "' name='salaryType_" + list[i].salaryType + "' value='0' operate='" + list[i].type + "'></td></tr>";
                } else {
                    jiaTr += "<tr><td  class='titletd'>" + list[i].salaryName + ":</td><td><input onkeyup=\"value=value.replace(/[^\\d{1,}\\.\\d{1,}|\\d{1,}]/g,'')\" maxlength='15' id='salaryType_" + list[i].salaryType + "' value='0' name='salaryType_" + list[i].salaryType + "'  operate='" + list[i].type + "'></td>";
                }
                jiaNum++;

            } else if (list[i].type == "1") {
                if (jianNum % 2 == 0) {
                    jianTr += "<td class='titletd'>" + list[i].salaryName + ":</td><td><input onkeyup=\"value=value.replace(/[^\\d{1,}\\.\\d{1,}|\\d{1,}]/g,'')\" maxlength='15' id='salaryType_" + list[i].salaryType + "' value='0' name='salaryType_" + list[i].salaryType + "'  operate='" + list[i].type + "'></td></tr>";
                } else {
                    jianTr += "<tr><td class='titletd'>" + list[i].salaryName + ":</td><td><input onkeyup=\"value=value.replace(/[^\\d{1,}\\.\\d{1,}|\\d{1,}]/g,'')\" maxlength='15' id='salaryType_" + list[i].salaryType + "' value='0'  name='salaryType_" + list[i].salaryType + "'  operate='" + list[i].type + "'></td>";
                }
                jianNum++;
            } else {
                if (num % 2 == 0) {
                    tr += "<td class='titletd'>" + list[i].salaryName + ":</td><td><input onkeyup=\"value=value.replace(/[^\\d{1,}\\.\\d{1,}|\\d{1,}]/g,'')\" maxlength='15' id='salaryType_" + list[i].salaryType + "'  value='0'  name='salaryType_" + list[i].salaryType + "'  operate='" + list[i].type + "'></td></tr>";
                } else {
                    tr += "<tr><td class='titletd'>" + list[i].salaryName + ":</td><td><input onkeyup=\"value=value.replace(/[^\\d{1,}\\.\\d{1,}|\\d{1,}]/g,'')\" maxlength='15' id='salaryType_" + list[i].salaryType + "' value='0' name='salaryType_" + list[i].salaryType + "'  operate='" + list[i].type + "'></td>";
                }
                num++;
            }
        }
        if (jiaNum != 0 && jiaNum % 2 == 0) {
            jiaTr += "</tr>";
        }
        if (jianNum != 0 && jianNum % 2 == 0) {
            jianTr += "</tr>";
        }
        if (num != 0 && num % 2 == 0) {
            tr += "</tr>";
        }

        if (jiaNum != 1) $("#salaryInput").append("<tr><td><div class=\"search\" style='height: auto'><table style=\"width: 100%\">" + jiaTr + "</table><a id='addBuns' onclick='getBunsTab(this);addBunsTab(bunsNum+1)'>添加奖金详情</a></div></td></tr>");
        if (jianNum != 1) $("#salaryInput").append("<tr><td><div class=\"search\" style='height: auto'><table style=\"width: 100%\">" + jianTr + "</table></div></td></tr>");
        if (num != 1) $("#salaryInput").append("<tr><td><div class=\"search\" style='height: auto'><table style=\"width: 100%\">" + tr + "</table></td></div></tr>");
        $("#salaryInput").append("<tr><td><div class=\"search\" style='height: auto'><table style=\"width: 100%\"><tr><td class='titletd'>应领工资:</td><td><input onkeyup=\"value=value.replace(/[^\\d{1,}\\.\\d{1,}|\\d{1,}]/g,'')\" maxlength='15' name='grossPay_dob' id='grossPay_dob'  value='0' ></td><td class='titletd'>实领工资:</td><td><input onkeyup=\"value=value.replace(/[^\\d{1,}\\.\\d{1,}|\\d{1,}]/g,'')\" maxlength='15' name='netPayroll_dob' id='netPayroll_dob' value='0'></td></tr></table></div></td></tr>");
    }
}

function loadEmployees(obj) {
    var departid = $("#departId").val();
    var empName = $(obj).val();
    $("#emps").empty();
    $("#empId").val("");
    if (departid != "" && empName != "") {
        $.ajax({
            url: "../../salary/getEmployees",
            type: "get",
            dataType: "json",
            async: true,
            data: {"departId": departid, "empName": empName},
            success: function (data) {
                if (data.code == "0000") {
                    var emps = data.data;
                    for (var i = 0; i < emps.length; i++) {
                        $("#emps").append(" <li empid='" + emps[i].empId + "' empname='" + emps[i].empName + "' emptype='" + emps[i].empType + "' onclick='empliClick(this)'>" + emps[i].empName + "    " + emps[i].empPhone + "  " + emps[i].waltzDate + "</li>");
                    }
                    $("#emps").parent().show();
                }
            }
        });
    }
}

function empliClick(obj) {
    $("#empId").val($(obj).attr("empid"));
    $("#empName").val($(obj).attr("empname"));
    $("#emps").parent().hide();
    loadSalaryTypeTab($(obj).attr("emptype"));
}


function salarySave() {
    $("#salaryDate").val($("#salaryYear").val() + "-" + $("#salaryMonth").val());
    var bunsTr = $("#bunsTab tr")||[];
    var bunsIds = "";
    for(var i=0; i<bunsTr.length; i++){
        if($(bunsTr[i]).attr("num") && $(bunsTr[i]).attr("num")!= ""){
            bunsIds += $(bunsTr[i]).attr("num")+",";
        }
    }
    $("#bunsIds").val(bunsIds);
    var input = $("input[name!='salaryId'][name!='bunsIds']");
    for(var i=0; i<input.length; i++){
        if(!($(input[i]).val()) || $(input[i]).val()== ""){
            alertShow("输入框存在为空！");
            console.log($(input[i]).attr("name"));
            return;
        }
    }
    confirmShow("确认提交吗", function () {
        loadingShow();
        $.post("../../salary/addSalary", $("#salaryAddForm").serialize(), function (data) {
            confirmHide();
            loadingHide();
            if (data.code == "0000") {
                alertShow("添加成功！");
                window.location = "list.html";
            } else if (data.code == "1007") {
                alertShow("该员工此月工资信息已存在！");
            } else {
                alertShow("添加失败！");
            }

        }, 'json');
    })
}

function salaryUpdateSave() {
    var bunsTr = $("#bunsTab tr")||[];
    var bunsIds = "";
    for(var i=0; i<bunsTr.length; i++){
        if($(bunsTr[i]).attr("num") && $(bunsTr[i]).attr("num")!= ""){
            bunsIds += $(bunsTr[i]).attr("num")+",";
        }
    }
    $("#bunsIds").val(bunsIds);
    var input = $("input[name!='bunsIds']");
    for(var i=0; i<input.length; i++){
        if(!($(input[i]).val()) || $(input[i]).val()== ""){
            alertShow("输入框存在为空！");
            console.log($(input[i]).attr("name"));
            return;
        }
    }
    confirmShow("确认提交吗", function () {
        loadingShow();
        $.ajax({
            url: "../../salary/updateSalary?" + $("#salaryUpdateForm").serialize(),
            type: "put",
            dataType: "json",
            async: false,
            data: {},
            success: function (data) {
                confirmHide();
                loadingHide();
                if (data.code == "0000") {
                    alertShow("修改成功！");
                    location.href = "/salarymanager/list.html";
                } else {
                    alertShow("修改失败！");
                }

            }
        });
    })
}

function loadSalaryData(salaryId) {
    $.ajax({
        url: "../../salary/getSalary?salaryId=" + salaryId,
        type: "get",
        dataType: "json",
        async: false,
        data: {},
        success: function (data) {
            if (data.code == "0000") {
                var salary = data.data;
                loadSalaryTypeTab(salary.employee.empType);
                $("#salaryId").val(salary.salaryId);
                $("#departId").val(salary.employee.departIdStr);
                $("#empName").val(salary.employee.empName);
                $("#salaryDate").val(salary.salaryDate);
                $("#salaryId").val(salary.salaryId);
                $("#empId").val(salary.empId)
                $("#grossPay_dob").val((salary.grossPay) / 10000);
                $("#netPayroll_dob").val((salary.netPayroll) / 10000);
                for (var i = 0; i < salary.salaryTypeEmpList.length; i++) {
                    $("[name='salaryType_" + salary.salaryTypeEmpList[i].salaryType + "']").val((salary.salaryTypeEmpList[i].money) / 10000);
                }
                if(data.data.bonusInfos){
                    getBunsTab(document.getElementById("addBuns"));
                    var bonusInfos = data.data.bonusInfos;
                    for(var i=0; i<bonusInfos.length; i++){
                        addBunsTab(i,bonusInfos[i].money, bonusInfos[i].cont, bonusInfos[i].manageDepart);
                    }
                    bunsNum = bonusInfos.length;
                }
            }
        }
    });
}

function intoImport() {
    modelShow("importSalary", "/salarymanager/import.html", "导入", 400, 300);
}

function fileImport() {
    parent.$("#salaryDate").val(parent.$("#salaryYear").val() + "-" + parent.$("#salaryMonth").val());
    var file = parent.$("#salaryfile").val();
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
            url: '../../salary/batchimport?salaryDate='+parent.$("#salaryDate").val(), //用于文件上传的服务器端请求地址
            secureuri: false,           //一般设置为false
            fileElementId: "salaryfile", //文件上传控件的id属性  <input type="file" id="file" name="file" /> 注意，这里一定要有name值
            //$("form").serialize(),表单序列化。指把所有元素的ID，NAME 等全部发过去
            dataType: 'json',//返回值类型 一般设置为json
            enctype:'multipart/form-data',//注意一定要有该参数
            complete: function () {//只要完成即执行，最后执行
            },
            success: function (data, status)  //服务器成功响应处理函数
            {
                loadingHide()
                if(data.code == "0000"){
                    modelHide("importSalary");
                    alertShow("共导入"+data.data+"条数据!");
                    location.href = "/salarymanager/list.html";
                }else if(data.code == "1008"){
                    parent.$("#showTishi").text("没有选择文件！")
                }else if(data.code == "1009"){
                    parent.$("#showTishi").text("文件格式必须是xlsx或者xls！");
                }else if(data.code == "1005"){
                    var errData = data.data;
                    if(errData[0] == 1){
                        parent.$("#showTishi").text("文件模板不对！");
                    }else if(errData[0] == 2) {
                        parent.$("#showTishi").text("文件导入出错！位置第"+errData[1]+"行 第"+errData[2]+"列");
                    }else {
                        parent.$("#showTishi").text("导入失败！");
                    }

                }else if(data.code == "1001"){
                    parent.$("#showTishi").text("请选择时间！");
                }else if(data.code == "1003"){
                    location.href = "/login.html";

                }
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                // alert(e);
                loadingHide();
            }
        }
    )
}


function getBunsTab(obj) {
    var html ="<div class=\"search\" style=\"height: auto\">\n" +
        "                        <table id='bunsTab' style=\"width: 100%\"><tr>\n" +
        "                            <th class=\"titletd\" style='width: 30%'>金额</th>\n" +
        "                            <th style='width: 30%'>内容</th>\n" +
        "                            <th style='width: 30%;' class=\"titletd\">归口管理部门</th>\n" +
        "                            <th  class=\"titletd\">操作</th>\n" +
        "                        </tr>\n" +
        "                    </table>\n" +
        "                    </div>";
    $(obj).parent().parent().parent().after("<tr><td>"+html+"</td></tr>");
    $(obj).attr("onclick","addBunsTab(bunsNum+1)");
}

function addBunsTab(i, money, cont, manageDepart){
    bunsNum = i;
    if(money || cont || manageDepart){

        $("#bunsTab").append("<tr num='"+i+"'>" +
            "                                    <td class=\"titletd\"><input style='width: 70%'  onkeyup=\"value=value.replace(/[^\\d{1,}\\.\\d{1,}|\\d{1,}]/g,'')\" maxlength='15' name='money_"+i+"' value='"+money+"'/></td>" +
            "                                    <td ><input style='width: 70%'  name='cont_"+i+"' value='"+cont+"' maxlength='50' /></td>" +
            "                                    <td  class=\"titletd\"><input style='width: 70%' maxlength='50'  name='manageDepart_"+i+"' value='"+manageDepart+"'/></td>" +
            "                                    <td  class=\"titletd\"><button style='width: 30px;height: 25px' onclick='removeBouns(this)'>-</button></td>" +
            "                                </tr>");

    }else {
        $("#bunsTab").append("<tr num='"+i+"'>" +
            "                                    <td class=\"titletd\"><input style='width: 70%' onkeyup=\"value=value.replace(/[^\\d{1,}\\.\\d{1,}|\\d{1,}]/g,'')\" maxlength='15' name='money_"+i+"' /></td>" +
            "                                    <td ><input style='width: 70%'  name='cont_"+i+"' maxlength='50' /></td>" +
            "                                    <td  class=\"titletd\"><input style='width: 70%'maxlength='50'  name='manageDepart_"+i+"' /></td>" +
            "                                    <td  class=\"titletd\"><button style='width: 30px;height: 25px' onclick='removeBouns(this)'>-</button></td>" +
            "                                </tr>");
    }
}

function removeBouns(obj) {
    $(obj).parent().parent().remove();
    if($("#bunsTab tr").length == 1){
        $("#bunsTab").remove();
    }
}