

var queryEmp = function () {

    var empNo = $("#empNo").val();
    var empName = $("#empName").val();

    $.post("../../emp/queryEmpInfo", {'empNo':empNo,'empName':empName},function(data){
       var reData = data.data;
       var html = '';
       console.log(reData);
       if(reData.length>0){
           for(var i=0; i<reData.length; i++){
               html += '    <tr>\n' +
                   '                <td>'+reData[i].empid+'</td>\n' +
                   '                <td>'+reData[i].emp_name+'</td>\n' +
                   '                <td>'+reData[i].emp_phone+'</td>\n' +
                   '                <td>'+reData[i].emp_card_num+'</td>\n' +
                   '                <td>'+reData[i].emp_type+'</td>\n' +
                   '                <td>'+reData[i].base_salary+'</td>\n' +
                   '                <td>\n' +
                   '                    <a href="billView.html"><img src="../static/img/view.png" alt="查看" title="查看" height="24" width="24"/></a>\n' +
                   '                    <a href="billUpdate.html"><img src="../static/img/modify.png" alt="修改" title="修改" height="24" width="24"/></a>\n' +
                   '                    <a href="#" class="removeBill"><img src="../static/img/del.png" alt="删除" title="删除" height="24" width="24"/></a>\n' +
                   '                </td>\n' +
                   '            </tr>';

           }
       }
       $("#employee").html(html);
       },'json');
}