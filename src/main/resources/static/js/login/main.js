/**
 * 打开选项卡 进入相应模块主页
 * @param url
 * @param name
 * @param id
 * */
function showTab(url,name,id){

   let length=$("li[lay-id="+id+"]").length;
   let element=layui.element;
   if(length==0){
       let fullUrl="/"+url;
       // iframe 可以隔离 同一个页面中id不重复即可
       // 不用iframe的话整个project都是单页面的
       let height=$(window).height()-185;
       let content='<iframe style="width: 100%;height: '+height+'px" src="'+fullUrl+'" frameborder="0" scrolling="no">'
       element.tabAdd('menu',{
           title:name,
           content:content,
           id:id
       });
   }
       // menu对应main.js的lay-filter名称
       element.tabChange("menu",id);
}
