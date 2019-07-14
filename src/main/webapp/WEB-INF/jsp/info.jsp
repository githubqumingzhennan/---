<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>学生信息管理系统</title>


    <link rel="stylesheet" type="text/css" href="../../easyui/themes/default/easyui.css">
    <!-- easyui的图标支持 -->
    <link rel="stylesheet" type="text/css" href="../../easyui/themes/icon.css">
    <!-- jQuery支持 -->
    <script type="text/javascript" src="../../easyui/jquery.min.js"></script>
    <!-- Easyui的功能支持(基于jQuery，必需在jQuery后面)-->
    <script type="text/javascript" src="../../easyui/jquery.easyui.min.js"></script>
    <!-- Easyui的国际化支持(中文) -->
    <script type="text/javascript" src="../../easyui/locale/easyui-lang-zh_CN.js"></script>
    <!-- 对jQuery又做了很多功能扩展 -->
    <script type="text/javascript" src="../../easyui/plugin/jquery.jdirk.js"></script>

    <!-- 引入验证的控件 -->
    <link href="/easyui/plugin/validatebox/jeasyui.extensions.validatebox.css" rel="stylesheet" />
    <script src="/easyui/plugin/validatebox/jeasyui.extensions.validatebox.rules.js"></script>
    <!-- 引入自己的js -->
    <script src="../../js/model/info.js"></script>

    <!-- 引入自己的js -->

    <script>
        function form2Json(id) {

            var arr = $("#" + id).serializeArray()
            var jsonStr = "";

            jsonStr += '{';
            for (var i = 0; i < arr.length; i++) {
                jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",'
                console.debug(arr[i].value+"%%%%%%%%%%")
            }
            jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
            jsonStr += '}'

            var json = JSON.parse(jsonStr)
            return json
        }
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
    <script src="js/model/employee.js"></script>
</head>
<body>
<!-- 数据展示 -->

<table id="dataGrid" class="easyui-datagrid" style="width:400px;height:250px"
       data-options="
       url:'/info/search',
       fitColumns:true,
       singleSelect:true,
       fit:true,
       toolbar:'#tb',
       pageSize:20,
       rownumbers:true,
       pagination:true">
    <thead>
    <tr>
        <th data-options="field:'classes',width:100">班级</th>
        <th data-options="field:'name',width:100">姓名</th>
        <th data-options="field:'sex',width:100">性别</th>
        <th data-options="field:'age',width:100">年龄</th>
        <th data-options="field:'phoneNumber',width:100,align:'right'">联系方式</th>
        <th data-options="field:'schoolName',width:100,align:'right'">毕业学校</th>
        <th data-options="field:'major',width:100,align:'right'">专业</th>
        <th data-options="field:'degree',width:100,align:'right'">学历</th>
        <th data-options="field:'graduateTime',width:100,align:'right'">毕业时间</th>
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
        <c:if test="${Account.dept== '就业部'}">
            <a href="#" data-method="messagePage" class="easyui-linkbutton" iconCls="icon-remove" plain="true">毕业学生信息表管理</a>
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
                <input  type="text" name="schoolName" >&nbsp&nbsp&nbsp&nbsp联系方式：<input  type="text" name="phoneNumber" >&nbsp&nbsp&nbsp&nbsp专业:<input  type="text" name="major" >
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
            <tr>
                <td>年龄:</td>
                <%--   <td><input name="companyName" class="f1 easyui-validatebox" data-options="validType:'email'"></input></td>--%>
                <td><input name="age" class="f1 easyui-validatebox" ></input></td>
            </tr>
            <tr>
                <td>性别:</td>
                <%--   <td><input name="companyName" class="f1 easyui-validatebox" data-options="validType:'email'"></input></td>--%>
                <td><input name="sex" class="f1 easyui-validatebox" ></input></td>
            </tr>
            <tr>
                <td>联系方式:</td>
                <%--   <td><input name="companyName" class="f1 easyui-validatebox" data-options="validType:'email'"></input></td>--%>
                <td><input name="phoneNumber" class="f1 easyui-validatebox" ></input></td>
            </tr>
            <tr>
                <td>班级:</td>
                <%--<td><input name="username" class="f1 easyui-validatebox" validType="checkName" data-options="required:true"></input></td>--%>
                <td><input name="classes" class="f1 easyui-validatebox"  data-options=""></input></td>
            </tr>

            <tr data-save="true">
                <td>毕业学校:</td>
                <td><input id="schoolName"  name="schoolName"  class="f1 easyui-validatebox" data-options=""></input></td>
            </tr>
            <tr data-save="true">
                <td>专业:</td>
                <%-- <td><input name="degree" type="password" class="f1 easyui-validatebox" validType="equals['password','id']" data-options="required:true"></input></td>--%>
                <td><input name="major"  class="f1 easyui-validatebox"  data-options=""></input></td>
            </tr>
            <tr data-save="true">
                <td>学历:</td>
                <td><input id="degree"  name="degree"  class="f1 easyui-validatebox" data-options=""></input></td>
            </tr>
            <tr data-save="true">
                <td>毕业时间:</td>
                <td><input id="graduateTime"  name="graduateTime"  class="f1 easyui-validatebox" data-options=""></input></td>
            </tr>
            <tr>
                <%--          <td>入职时间:</td>
                     &lt;%&ndash;     <td><input name="date" class="f1 easyui-validatebox" data-options="validType:'integerRange[8,80]'"></input></td>&ndash;%&gt;
                          <td><input name="date" class="easyui-datebox" ></input></td>--%>
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
            url:"/info/importExcel", //请求url
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
            url:"/info/exportExcel", //请求url
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