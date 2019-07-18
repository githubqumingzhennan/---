﻿//单独修改提示
$.fn.validatebox.extensions.rules.integerRange.message = "年龄必需在 {0} 与 {1} 之间";
//自定义验证功能
// extend the 'equals' rule
$.extend($.fn.validatebox.defaults.rules, {
    // 你为这个验证取的名称
    checkName: {
        /**
         * @param value : 当前文本框中的值
         * @param param : 参数(数组)
         */
        validator: function(value,param){
            //拿到相应的id值
            var id = $("#employeeId").val();
            //Ajax -> 异步 【改成同步的功能】
            // 这种方式返回的数据都是字符串
            var result = $.ajax({
                url: "/employee/checkName",
                data:{username:value,id:id},
                async: false //同步功能
            }).responseText;
            //返回boolean确定验证成功还是失败
            return result == "true";
        },
        //提示信息
        message: ''
    }
});


function imgFormat(value,row,index) {
    return "<img width='50px' src='"+value+"' alt='没有头像'>"
}
function deptFormat(value,row,index) {

    return value?value.name:"";
}

$(function () {
    //把所有会多次用到的组件放到最上面
    var datagrid = $('#dataGrid'); //数据展示
    var employeeDialog = $('#employeeDialog'); //弹框
    var updatePasswordDialog=$('#updatePasswordDialog');
    var searchForm = $('#searchForm'); //查询表单
    var editForm = $('#editForm'); //添加与修改表单
    var editForm1 = $('#editForm1'); //添加与修改表单
    //为所有有data-method的组件添加事件
    $("*[data-method]").on("click",function(){
        // var method = $(this).data("method");
        // itsource[method]();
        itsource[$(this).data("method")]()
    })

    //防止污染
    window.itsource = {
        exitLogin:function () {
            window.location.href="/employee/exitLogin"
        },
        accountManage:function () {
            window.location.href="/employee/toAccountPage";
        },
        messagePage:function(){
            window.location.href="/employee/toStudentMessagePage";
        },
        skip:function () {
            window.location.href="/employee/test";
        },
        add:function(){
            //1.清空form中的数据
            editForm.form("clear");
            //2.添加的时候显示密码
            /*$("*[data-save]").show();
            $("*[data-save] input").validatebox("enable");*/
            //3.打开面板(绝对居中)
            employeeDialog.dialog("center").dialog('open').dialog("setTitle","添加数据");

        },
        updatePasswordDialogOpen:function () {
            updatePasswordDialog.dialog("center").dialog('open').dialog("setTitle","修改密码");
        },
        updatePassword:function () {
            var url = "/employee/updatePassword";
            //拿到表单中的id


            //提交表单的功能
            editForm1.form('submit', {
                url: url,
                onSubmit: function(){
                    //提交之前执行的功能，如果返回false，它就不会再执行了

                },
                //easyui提交后表单后 返回的值只是一个字符串
                success: function(result){
                    //把它变成一个Json数据 eval("("+jsonStr+")") /JSON.parse(jsonStr)
                    //console.debug(result);
                    result = JSON.parse(result);
                    if(result.resultMsg){
                        //成功之后的处理

                        //关闭弹出框
                        updatePasswordDialog.dialog('close');
                    }else{
                        $.messager.alert('提示','修改失败！',"info");
                    }
                }
            });
        },
        edit:function () {
            //记住 ：选择一条数据才能进行修改
            var row =  datagrid.datagrid("getSelected");
            if(row){
                //1.清空form中的数据
                editForm.form("clear");
                //2.添加的时候显示密码
                /* $("*[data-save]").hide();
                 $("*[data-save] input").validatebox("disable");*/
                //3.打开面板(绝对居中)
                employeeDialog.dialog("center").dialog('open').dialog("setTitle","修改数据");
                //4.数据进行回显
                //  name="email",name=age  row{email:"xx",age:45}
                //  name="department.id"  row{department:{id:4}}
                // row["department.id"]   row.department.id
                var row1= Object.assign({}, row)
                var time=row1.date;
                row1.date=new Date(time).format("yyyy-MM-dd");
                console.debug(row1);

                editForm.form("load",row1);
                //$('#date').datebox('setValue',"2018-6-19");
            }else{
                $.messager.alert('提示','请选择一条数据再进行修改！',"warning");
            }

        },
        //代表完成保存功能
        save:function () {
            //添加和修改的路径要分开
            var url = "/info/save";
            //拿到表单中的id
            var id = $("#employeeId").val();

            //提交表单的功能
            editForm.form('submit', {
                url: url,
                onSubmit: function(){
                    //提交之前执行的功能，如果返回false，它就不会再执行了
                    //return $(this).form('validate');
                },
                //easyui提交后表单后 返回的值只是一个字符串
                success: function(result){
                    //把它变成一个Json数据 eval("("+jsonStr+")") /JSON.parse(jsonStr)
                    //console.debug(result);
                    result = JSON.parse(result);
                    console.debug(result.resultMsg);
                    if(result.resultMsg){
                        //成功之后的处理
                        $('#dataGrid').datagrid('reload');
                        //关闭弹出框
                        employeeDialog.dialog('close');
                    }else{
                        $.messager.alert('提示','添加失败了！',"info");
                    }
                }
            });
        },
        //删除功能
        delete:function(){
            //1.判断是否已经选中了一条数据
            var row =  datagrid.datagrid("getSelected");
            if(row){
                //2.提示是否真的要删除这条数据呢？
                $.messager.confirm('确认', '你确定要<span style="color:red;font-size: 20px;">狠心</span>删除我嘛？', function(r){
                    if (r){
                        //3.直接进行相应的删除
                        $.get("/info/delete",{id:row.id},function (result) {
                            console.debug(result.resultMsg);
                            if(result.resultMsg){
                                //删除成功，刷新页面
                                // console.debug("faq");
                                $('#dataGrid').datagrid("reload");
                            }else {
                                //删除失败，给出提示信息
                                $.messager.alert('提示','删除失败！<br /> 原因是'+result.msg,"info");
                            }
                        })
                    }
                });
            }else{
                $.messager.alert('提示','请选择一条数据再进行删除！',"warning");
            }
        },
        search1:function(){
            //获取过滤的参数值
            var params = searchForm.serializeObject();
            //刷新grid
            datagrid.datagrid('load',params);
        },
        search:function () {
            $('#dataGrid').datagrid({ queryParams: form2Json("searchForm") });
            var page = $("#dataGrid").datagrid('options').pageNumber;
            var rows = $("#dataGrid").datagrid('options').pageSize;
            //添加和修改的路径要分开
            console.debug(1111)
            var url = "/info/search";
            //拿到表单中的id
            //var id = $("#employeeId").val();
            /*    if(id){
                    url = "/employee/update?cmd=update"
                }*/
            //提交表单的功能
            searchForm.form('submit', {
                url: url,

                onSubmit: function(param){
                    param.page=page;
                    param.rows=rows;
                    //param.test='张';
                    //提交之前执行的功能，如果返回false，它就不会再执行了
                    return $(this).form('validate');
                },
                //easyui提交后表单后 返回的值只是一个字符串
                success: function(result){
                    //把它变成一个Json数据 eval("("+jsonStr+")") /JSON.parse(jsonStr)
                    //console.debug(result);
                    result1 = JSON.parse(result);

                    //成功之后的处理
                    $('#dataGrid').datagrid('loadData', result1);
                    // datagrid.loadData(result1);//这种方式该如何使用

                }
            });
        }
    };
})