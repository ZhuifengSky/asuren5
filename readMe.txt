个人项目
   cn.service ---前端接口服务代码
   com.main   ---管理后台代码
   
   jsp  ---后台项目视图展示
   html ---前台页面展示
   
可以利用Eclipse插件生成也可以使用mvn命令
mvm命令如下：mvn mybatis-generator:generate

##做顶部导航菜单学习
 对于公共的页面之前一直认为是抽出一个公共页面，然后iframe嵌入，
 但是遇到很多子页面高度适配的问题，今天学习到可以将公共的html转成js然后在页面中直接引入js即可
 http://tool.chinaz.com/Tools/Html_Js.aspx  附上转工具页