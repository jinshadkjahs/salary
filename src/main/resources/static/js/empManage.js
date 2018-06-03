

var queryEmp = function (pageNum) {

    var empNo = $("#empNo").val();
    var empName = $("#empName").val();

    $.post("../../emp/queryEmpInfo", {'empNo':empNo,'empName':empName,'pageNum':pageNum},function(data){
        var reVal = data.data;
        console.log(reVal);
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
       console.log(reData);
       if(reData.length>0){
           for(var i=0; i<reData.length; i++){
               html += '    <tr>\n' +
                   '                <td>'+reData[i].empId+'</td>\n' +
                   '                <td>'+reData[i].empName+'</td>\n' +
                   '                <td>'+reData[i].empPhone+'</td>\n' +
                   '                <td>'+reData[i].empCardNum+'</td>\n' +
                   '                <td>'+reData[i].empType+'</td>\n' +
                   '                <td>'+reData[i].baseSalary+'</td>\n' +
                   '                <td>\n' +
                   '                    <a href="billView.html"><img src="../static/img/read.png" alt="查看" title="查看" height="24" width="24"/></a>\n' +
                   '                    <a href="billUpdate.html"><img src="../static/img/xiugai.png" alt="修改" title="修改" height="24" width="24"/></a>\n' +
                   '                    <a href="#" class="removeBill"><img src="../static/img/schu.png" alt="删除" title="删除" height="24" width="24"/></a>\n' +
                   '                </td>\n' +
                   '            </tr>';

           }
       }
       $("#employee").html(htmlHead+html);
       $("#empPage").html(getPageHtml(reVal.pageNum,reVal.pages,"pageClick"));
       },'json');
}

function pageClick(obj) {
    queryEmp(parseInt($(obj).attr("forPage")));
}
