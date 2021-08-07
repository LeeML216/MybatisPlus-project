var table=layui.table;
//第一个实例
var tableIns = table.render({
    elem:'#customerList'
    ,url:'/customer/list'// 数据接口
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
        {field:'realName',title:'真实姓名'}
        ,{field:'sex',title:'性别'}
        ,{field:'age',title:'年龄'}
        ,{field:'phone',title:'电话号码'}
        ,{field:'createTime',title:'创建时间'}
        ,{title:'操作',toolbar:'#barDemo'}
    ]]
})

/**
 * 按条件查询
 */
function query(){
    tableIns.reload({
        where:{
            realName:$("#realName").val()
            ,phone:$("#phone").val()
        },page:{
            curr:1 // 重新从第一页开始
        }
    })
}

/**
 * 进入新增页面
 */
function toAdd(){
    openLayer('/customer/toAdd','新增客户');
    // 渲染radio
    layui.form.render();
    mySubmit('addSubmit','POST');
}


//工具条事件
table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data; //获得当前行数据
    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

    let customerId = data.customerId;

    if(layEvent === 'detail'){ //查看
        openLayer('/customer/toDetail/'+customerId,'客户详情');
    } else if(layEvent === 'del'){ //删除
        layer.confirm('确认删除该行信息？', function(index){
            layer.close(index);
            myDelete("/customer/"+customerId);
        });
    } else if(layEvent === 'edit'){ //编辑
        openLayer('/customer/toUpdate/'+customerId,'编辑客户信息');
        layui.form.render();
        mySubmit('updateSubmit','PUT');
    }
});


