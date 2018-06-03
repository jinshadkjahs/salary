$(function () {
    loadingShow();
    loadDateData();
    loadDepartData();
    loadSalaryTypeData();
    queryPage(1);
});

function  loadDateData() {
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
        var ss = ""+i;
        if(i<10){
            ss = "0"+i;
        }
        $('#salaryMonth').append("<option value='" + ss + "'>" + i + "月</option>");
    }
}

function  loadDepartData() {
    $.ajax({
        url: "../../salary/getDeparts",
        type: "get",
        dataType: "json",
        async: false,
        data: {},
        success: function(data){
            $("#departId").empty();
            $("#departId").append("<option value=''>--请选择--</option>");
            if(data.code == "0000"){
                var departs = data.data;
                for(var i=0; i< departs.length; i++){
                    $("#departId").append("<option value='"+departs[i].departid+"'>"+departs[i].departName+"</option>");
                }
            }
        }
    });
}

function  loadSalaryTypeData() {
    $.ajax({
        url: "../../salary/getEmpTypes",
        type: "get",
        dataType: "json",
        async: false,
        data: {},
        success: function(data){
            $("#empType").empty();
            if(data.code == "0000"){
                var empTypes = data.data;
                $("#empType").append("<option value=''>--请选择--</option>");
                for(var i=0; i<empTypes.length; i++){
                    $("#empType").append("<option value='"+empTypes[i].empType+"'>"+empTypes[i].typeName+"</option>");
                }
            }
        }
    });
}

function queryPage(pageNum) {
    loadingShow();
    $("#pageN").val(pageNum);
    if($("#salaryYear").val()!="" && $("#salaryMonth").val()!=""){
        $("#salaryDate").val($("#salaryYear").val()+"-"+$("#salaryMonth").val());
    }
    var queryPam = $("#salarySearchForm").serialize();
    $.getJSON("../../salary/getSalarys",queryPam,function (data) {
        if (data.code == "0000"){
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
            for (var i=0;i<listData.length; i++){
                var salaryList = listData[i].salaryList;
                $("#dataTab").append("<tr>" +
                                         "<td>"+(i+1)+"</td>" +
                                "        <td>"+listData[i].empId+"</td>" +
                                "        <td>"+listData[i].departIdStr+"</td>" +
                                "        <td>"+listData[i].empName+"</td>" +
                                "        <td>"+listData[i].empTypeStr+"</td>" +
                                "        <td>"+salaryList[0].salaryDate.substring(0,7)+"</td>" +
                                "        <td>"+listData[i].baseSalary+"</td>" +
                                "        <td>"+listData[i].baseSalary+"</td>" +
                                "        <td><a href='#'><img src=\"../../static/img/read.png\" alt=\"查看\" title=\"查看\"/></a>\n" +
                    "                    <a href='#'><img src=\"../../static/img/xiugai.png\" alt=\"修改\" title=\"修改\"/></a>\n" +
                    "                    <a href='#' class='removeBill'><img src=\"../../static/img/schu.png\" alt=\"删除\" title=\"删除\"/></a></td>" +
                                     "</tr>");
            }
            if(listData.length<10){
                for(var i = listData.length;i<10;i++){
                    $("#dataTab").append("<tr style='height: 42px'>" +
                        "                <td></td>" +
                        "                <td></td>" +
                        "                <td></td>" +
                        "                <td></td>" +
                        "                <td></td>" +
                        "                <td></td>" +
                        "                <td></td>"+
                        "                <td></td>"+
                        "                <td></td>"+
                        "            </tr>");
                }
            }
            $("#pageInfo").html(getPageHtml(data.data.pageNum, data.data.pages, "queryPageClick"));
        } else {
            toLogin(data.code);
        }
        loadingHide();
    });
}

function queryPageClick(obj) {
    queryPage($(obj).attr("forPage"));
}