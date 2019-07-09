<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>faq</title>


    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
    <!-- easyui的图标支持 -->
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
    <!-- jQuery支持 -->
    <script type="text/javascript" src="easyui/jquery.min.js"></script>
    <!-- Easyui的功能支持(基于jQuery，必需在jQuery后面)-->
    <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="easyui/jquery.easyui.mobile.js"></script>
    <!-- Easyui的国际化支持(中文) -->
    <script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
    <!-- 对jQuery又做了很多功能扩展 -->
    <script type="text/javascript" src="easyui/plugin/jquery.jdirk.js"></script>

    <!-- 引入验证的控件 -->
    <link href="/easyui/plugin/validatebox/jeasyui.extensions.validatebox.css" rel="stylesheet" />
    <script src="/easyui/plugin/validatebox/jeasyui.extensions.validatebox.rules.js"></script>
    <!-- 引入自己的js -->
    <script src="js/model/employee.js"></script>
    <script>
        Date.prototype.format = function (format) {
            var o = {
                "M+": this.getMonth() + 1, // month
                "d+": this.getDate(), // day
                "h+": this.getHours(), // hour
                "m+": this.getMinutes(), // minute
                "s+": this.getSeconds(), // second
                "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
                "S": this.getMilliseconds()
                // millisecond
            }
            if (/(y+)/.test(format))
                format = format.replace(RegExp.$1, (this.getFullYear() + "")
                    .substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(format))
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            return format;
        }
        function formatDatebox(value) {
            if (value == null || value == '') {
                return '';
            }
            var dt;
            if (value instanceof Date) {
                dt = value;
            } else {
                dt = new Date(value);
            }

            return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)
        }



    </script>
    <style type="text/css">

    </style>
</head>
<body>
<!-- 数据展示 -->

<table id="dataGrid" class="easyui-datagrid" style="width:400px;height:250px"
       data-options="
       url:'/employee/search',
       fitColumns:true,
       singleSelect:true,
       fit:true,
       toolbar:'#tb',
       pageSize:20,
       rownumbers:true,
       pagination:true">
    <thead>
    <tr>
        <th data-options="field:'name',width:100">姓名</th>
        <th data-options="field:'schoolName',width:100">毕业学校</th>
        <th data-options="field:'degree',width:100">学历</th>
        <th data-options="field:'companyName',width:100,align:'right'">就职公司</th>
        <th data-options="field:'date',width:100,align:'right',formatter:formatDatebox">入职时间</th>
        <th data-options="field:'address',width:100,align:'right'">公司地址</th>
        <th data-options="field:'salary',width:100,align:'right'">薪资</th>
        <th data-options="field:'fl',width:100,align:'right'">福利待遇</th>
        <th data-options="field:'year_end_bonus',width:100,align:'right'">年终奖</th>
        <th data-options="field:'elses',width:100,align:'right'">社保公积金</th>
        <th data-options="field:'salary_detail',width:100,align:'right'">薪资明细</th>
        <th data-options="field:'salary_total',width:100,align:'right'">薪资总额</th>
    </tr>
    </thead>
</table>
<!-- 高级查询与按钮 -->
<div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <c:if test="${Account.dept== '就业部'}">
        <a href="#" data-method="add" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
        </c:if>
        <c:if test="${Account.dept== '就业部'}">
        <a href="#" data-method="edit" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
        </c:if>
        <c:if test="${Account.dept== '就业部'}">
        <a href="#" data-method="delete" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
        </c:if>
        <c:if test="${Account.jurisdiction== 'manage'}">
        <a href="#" data-method="skip" class="easyui-linkbutton" iconCls="icon-remove" plain="true">账号管理</a>

        </c:if>
        <a href="#" data-method="exitLogin" class="easyui-linkbutton" iconCls="icon-remove" plain="true">退出登录</a>
        <a href="#" data-method="updatePasswordDialogOpen" class="easyui-linkbutton" iconCls="icon-remove" plain="true">修改密码</a>
        <c:if test="${Account.dept== '就业部'}">
        <form  id="userForm" name="userForm2"  enctype="multipart/form-data" method="post">

            <div id="newUpload2">
                <input id="file" type="file" name="file" multiple="true" accept=".xls,.xlsx">
            </div>
            <div> <input type="button" onclick="importExcel();" value="导入Excel" ></div>
            <div> <input type="button" onclick="exportExcel();" value="导出Excel" ></div>
        </form>
        </c:if>
        <form id="searchForm" name="userForm2"  enctype="multipart/form-data" method="post">

           <div>姓名：<input  type="text" name="name" >&nbsp&nbsp&nbsp&nbsp毕业学校：
               <input  type="text" name="schoolName" >&nbsp&nbsp&nbsp&nbsp公司：<input  type="text" name="companyName" >&nbsp&nbsp&nbsp&nbsp薪资范围:<input  type="text" name="minSalary" >&nbsp---&nbsp<input  type="text" name="maxSalary" >
               &nbsp<a href="#"  class="easyui-linkbutton" data-method="search" iconCls="icon-save">查询</a></div>

        </form>

    </div>
  <%--  <div>
        <form id="searchForm" action="">
            用户名: <input name="username" class="easyui-textbox" style="width:80px">
            邮件: <input name="email" class="easyui-textbox" style="width:80px">
            部门:<input name="departmentId" class="easyui-combobox" name="dept"
                      panelHeight="auto"
                      data-options="valueField:'id',textField:'name',url:'/util/dept'" />

            <a href="#" class="easyui-linkbutton" data-method="search" iconCls="icon-search">查询</a>
        </form>
    </div>--%>
</div>
<!-- 添加与修改的弹出框 -->
<div id="employeeDialog" class="easyui-dialog" title="功能操作" style="width:280px;padding: 10px;"
     data-options="iconCls:'icon-save',modal:true,closed:true">
    <!-- 表单准备 -->
    <form id="editForm" action="" method="post">
        <input id="employeeId" type="hidden" name="id" />
        <table>
            <tr>
                <td>姓名:</td>
                <%--<td><input name="username" class="f1 easyui-validatebox" validType="checkName" data-options="required:true"></input></td>--%>
                <td><input name="name" class="f1 easyui-validatebox"  data-options="required:true"></input></td>
            </tr>
            <tr data-save="true">
                <td>毕业学校:</td>
                <td><input id="schoolName"  name="schoolName"  class="f1 easyui-validatebox" data-options="required:true"></input></td>
            </tr>
            <tr data-save="true">
                <td>学历:</td>
               <%-- <td><input name="degree" type="password" class="f1 easyui-validatebox" validType="equals['password','id']" data-options="required:true"></input></td>--%>
                <td><input name="degree"  class="f1 easyui-validatebox"  data-options="required:true"></input></td>
            </tr>
            </tr>
            <tr>
                <td>就职公司:</td>
             <%--   <td><input name="companyName" class="f1 easyui-validatebox" data-options="validType:'email'"></input></td>--%>
                <td><input name="companyName" class="f1 easyui-validatebox" ></input></td>
            </tr>
            <tr>
      <%--          <td>入职时间:</td>
           &lt;%&ndash;     <td><input name="date" class="f1 easyui-validatebox" data-options="validType:'integerRange[8,80]'"></input></td>&ndash;%&gt;
                <td><input name="date" class="easyui-datebox" ></input></td>--%>
            </tr>
            <tr>
                <td>公司地址:</td>
                <td><input name="address" class="f1 easyui-validatebox" ></input></td>
             <%-- <--
                    <input name="department.id" class="easyui-combobox" name="dept"
                           panelHeight="auto"
                           data-options="valueField:'id',textField:'name',url:'/util/dept',required:true" />
                </td>&ndash;%&gt;--%>
            </tr>
            <tr data-save="true">
                <td>入职时间:</td>
                <td><input type="text" id="date" name="date" class="easyui-datebox" data-options=""></input></td>
            </tr>
            <tr data-save="true">
                <td>薪资:</td>
                <td><input id="salary"  name="salary"  class="f1 easyui-validatebox" data-options="required:true"></input></td>
            </tr>
            <tr data-save="true">
                <td>福利待遇:</td>
                <td><input name="fl"  class="f1 easyui-validatebox"  data-options="required:true"></input></td>
            </tr>
            <tr>
                <td>福利明细:</td>
                <td><input name="flmx" class="f1 easyui-validatebox" ></input></td>
            </tr>
            <tr>
                <td>年终奖:</td>
                <td><input name="year_end_bonus" class="f1 easyui-validatebox" ></input></td>
            </tr>
            <tr>
                <td>社保公积金:</td>
                <td><input name="elses" class="f1 easyui-validatebox" ></input></td>
            </tr>
        </table>
        <div style="text-align:center;padding:5px">
            <a href="#"  class="easyui-linkbutton" data-method="save" iconCls="icon-save">确定</a>
            <a href="#"  class="easyui-linkbutton" onclick="$('#employeeDialog').dialog('close')" iconCls="icon-cancel">取消</a>
        </div>
    </form>
</div>
<!-- 修改密码 -->
<div id="updatePasswordDialog" class="easyui-dialog" title="功能操作" style="width:280px;padding: 10px;"
     data-options="iconCls:'icon-save',modal:true,closed:true">
    <!-- 表单准备 -->
    <form id="editForm1" action="" method="post">

        <table>
            <tr>

                <td><input name="id" class="f1 easyui-validatebox" validType="checkName" data-options="" value="${Account.id}" type="hidden"></input></td>
            </tr>
            <tr>
                <td>账号:</td>
                <td><input name="num" class="f1 easyui-validatebox" validType="checkName" data-options="" value="${Account.num}" readonly="readonly"></input></td>
            </tr>
            <tr data-save="true">
                <td>密码:</td>
                <td><input id="password" type="password" name="password"  class="f1 easyui-validatebox" data-options="required:true"></input></td>
            </tr>

        </table>
        <div style="text-align:center;padding:5px">
            <a href="#"  class="easyui-linkbutton" data-method="updatePassword" iconCls="icon-save">确定</a>
            <a href="#"  class="easyui-linkbutton" onclick="$('#employeeDialog').dialog('close')" iconCls="icon-cancel">取消</a>
        </div>
    </form>
</div>
</body>
<script>



    // 分页数据的操作
    function pagerFilter(data) {
        if (typeof data.length == 'number' && typeof data.splice == 'function') {   // is array
            data = {
                total: data.length,
                rows: data
            }
        }
        var dg = $(this);
        var opts = dg.datagrid('options');
        var pager = dg.datagrid('getPager');
        pager.pagination({
            onSelectPage: function (pageNum, pageSize) {
                opts.pageNumber = pageNum;
                opts.pageSize = pageSize;
                pager.pagination('refresh', {
                    pageNumber: pageNum,
                    pageSize: pageSize
                });
                dg.datagrid('loadData', data);
            }
        });
        if (!data.originalRows) {
            data.originalRows = (data.rows);
        }
        var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = (data.originalRows.slice(start, end));
        return data;
    }

    function importExcel(){
        $("#userForm").form('submit', {
            type:"post",  //提交方式
            url:"/employee/importExcel", //请求url
            success:function(data)
            { //提交成功的回调函数
                var data =eval('(' + data + ')');
                $.messager.alert({  title:'提示',
                    msg: data.resultMsg,
                    icon:'info',
                });
                $('#dataGrid').datagrid("reload");
            }
        });
    }
    function exportExcel(){
        $("#searchForm").form('submit', {
            type:"post",  //提交方式
            url:"/employee/exportExcel", //请求url
            success:function(data) {
                //提交成功的回调函数
                var data =eval('(' + data + ')');
                $.messager.alert({  title:'提示',
                    msg: data.resultMsg,
                    icon:'info',
                });
            }
        });
    }



</script>
</html>