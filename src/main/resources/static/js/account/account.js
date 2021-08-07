layui.laydate.render({
    elem:'#createTimeRange'
    ,range:true
    // 2020-11-12 - 2020-11-20
});

var table=layui.table;
//第一个实例
var tableIns = table.render({
    id:'test'
    ,elem:'#accountList'
    ,url:'/account/list'// 数据接口
    ,page: true // 开启分页
    ,parseData:function (res){
        return{
            "code": res.code,//接口
            "msg":res.msg,//提示文本
            "count":res.data.count,//数据长度
            "data":res.data.records//数据列表
        };
    }
    ,cols:[[ //  表头
        {field:'username',title:'用户名'}
        ,{field:'realName',title:'真实姓名'}
        ,{field:'roleName',title:'角色名称'}
        ,{field:'sex',title:'性别'}
        ,{field:'createTime',title:'创建时间'}
        ,{title:'操作',toolbar:'#barDemo'}
    ]]
})

/**
 * 查询方法
 */
function query(){
    tableIns.reload({
        where:{
            realName:$("#realName").val()
            ,email:$("#email").val()
            ,createTimeRange:$("#createTimeRange").val()
        },page:{
            curr:1 // 重新从第一页开始
        }
    })
}

/**
 * 进入新增页面
 */
function toAdd(){
    openLayer('/account/toAdd','新增账号');
    // 渲染radio
    layui.form.render();
    mySubmit('addSubmit','POST');
}

//工具条事件
table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data; //获得当前行数据
    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

    let accountId = data.accountId;

    if(layEvent === 'detail'){ //查看
        openLayer('/account/toDetail/'+accountId,'账号详情');
    } else if(layEvent === 'del'){ //删除
        layer.confirm('确认删除该行信息？', function(index){
            layer.close(index);
            myDelete("/account/"+accountId);
        });
    } else if(layEvent === 'edit'){ //编辑
        openLayer('/account/toUpdate/'+accountId,'编辑账号信息');
        layui.form.render();
        mySubmit('updateSubmit','PUT');
    }
});

layui.form.verify({
    checkUsername: function (value) {//value：表单的值、item：表单的DOM对象
        let error = null;
        let url = '/account/' + value;
        let accountId=$("input[name='accountId']").val();
        if(typeof(accountId)!='undefined'){
            url+='/'+accountId;
        }

        $.ajax({
            url: url
            , async: false
            , type: 'GET'
            , success: function (res) {
                if (res.code == 0) {
                    if (res.data > 0) {
                        error = "用户名已存在！";
                    }
                } else {
                    error = "用户名检测出错！";
                }
            }, error: function () {
                error = "用户名检测出错！";
            }
        });
        if (error != null) {
            return error;
        }
    }
});




