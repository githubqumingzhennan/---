<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <script src="../../js/model/account.js"></script>

</head>
<body>
<!-- 数据展示 -->
<table id="dataGrid" class="easyui-datagrid" style="width:400px;height:250px"
       data-options="
       url:'/employee/AccountPage',
       fitColumns:true,
       singleSelect:true,
       fit:true,
       toolbar:'#tb',
       rownumbers:true
       ">
    <thead>
    <tr>
        <th data-options="field:'id',width:100">id</th>

        <th data-options="field:'name',width:100">姓名</th>
        <th data-options="field:'num',width:100">账号</th>
        <th data-options="field:'dept',width:100">部门</th>

        <%--   <th data-options="field:'companyName',width:100,align:'right'">就职公司</th>
           <th data-options="field:'date',width:100,align:'right'">入职时间</th>
           <th data-options="field:'address',width:100,align:'right',formatter:deptFormat">公司地址</th>
           <th data-options="field:'salary',width:100,align:'right'">薪资</th>
           <th data-options="field:'fl',width:100,align:'right'">福利待遇</th>
           <th data-options="field:'year_end_bonus',width:100,align:'right',formatter:deptFormat">年终奖</th>
           <th data-options="field:'elses',width:100,align:'right'">其他</th>--%>
    </tr>
    </thead>
</table>
<!-- 高级查询与按钮 -->
<div id="tb" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <a href="#" data-method="add" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
        <a href="#" data-method="delete" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
        <a href="#" data-method="back" class="easyui-linkbutton" iconCls="icon-add" plain="true">返回</a>
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
        <input id="AccountId" type="hidden" name="id" />
        <table>
            <tr>
                <td>姓名:</td>
                <td><input name="name" class="f1 easyui-validatebox" validType="checkName" data-options="required:true"></input></td>
            </tr>
            <tr data-save="true">
                <td>账号:</td>
                <td><input id="num" type="text" name="num"  class="f1 easyui-validatebox" data-options="required:true"></input></td>
            </tr>

            <tr>
                <td>部门:</td>
                <td><input name="dept" class="f1 easyui-validatebox" data-options=""></input>
               <%-- <td><input name="dept" class="f1 easyui-validatebox" data-options="validType:'email'"></input></td>--%>
            </tr>

        </table>
        <div style="text-align:center;padding:5px">
            <a href="#"  class="easyui-linkbutton" data-method="save" iconCls="icon-save">确定</a>
            <a href="#"  class="easyui-linkbutton" onclick="$('#employeeDialog').dialog('close')" iconCls="icon-cancel">取消</a>
        </div>
    </form>
</div>
</body>
<script>
    function loadFile(){
        $("#userForm").form('submit', {
            type:"post",  //提交方式
            url:"/employee/upload", //请求url
            success:function(data)
            { //提交成功的回调函数
                var data =eval('(' + data + ')');
                $.messager.alert({  title:'提示',
                    msg: data.resultMsg,
                    icon:'info',
                });
            }
        });
    }
    function getPath(v, row, index) {
        /*return  '<img src=" '+"123.png"+ ' " 播放 />'*/
        /*<video src="movie.ogg" controls="controls">*/
        /*  return '<a href='+'"www.baidu.com"'+'>'+"123"+'</a>'*/
        // return '<audio src='+row.path+" controls"+' />'
        return '<a href='+row.path+'>'+row.name+'</a>'



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

    function formatDatebox(v, row, index) {
        console.debug(1111)
        if (row.createTime == null || row.createTime == '') {
            return '';
        }
        var dt;
        if (row.createTime instanceof Date) {
            dt = row.createTime;
        } else {
            dt = new Date(row.createTime);
        }

        return dt.format("yyyy-MM-dd"); //扩展的Date的format方法(上述插件实现)
    }


</script>
</html>
