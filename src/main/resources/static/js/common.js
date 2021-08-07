
/**
 * 公共弹出层
 * @param url
 * @param title
 */
function openLayer(url,title){
    // 如果不做同步设置 在表单未提交时就对页面做了渲染
    $.ajaxSettings.async=false;
    // 通过ajax方法请求弹出页面内容
    // 在不跳转的情况下实现新增页面
    $.get(url,function (res){
        layer.open({
            type:1 // 代表是页面层
            ,title:title
            ,area:['800px','500px']
            ,content:res
        });
    });
    $.ajaxSettings.async=true;
}

/**
 * 监听提交事件
 * @param filter
 * @param type
 * @param func
 */
function mySubmit(filter,type,func){
    layui.form.on('submit('+filter+')',function (data){
        if(typeof(func)!='undefined'){
            func(data.field);
        }
        $.ajax({
            url:data.form.action
            ,async:false
            ,type:type
            ,contentType:'application/json;charset=utf-8'
            ,data:JSON.stringify(data.field)
            ,success:function (res){
                if(res.code==0){
                    layer.closeAll();
                    query();
                }else{
                    layer.alert(res.msg);
                }
            }
        });
        return false;// 阻止表单跳转
    });
}

/**
 * 公共删除方法
 * @param url
 */
function myDelete(url){
    $.ajax({
        url:url
        ,async:false
        ,type:'DELETE'
        ,success:function (res){
            if(res.code==0){
                query();
            }else{
                layer.alert(res.msg);
            }
        }
    });
}
