# taotao
项目名称：**taotao商城**

## 完成的功能：
1. 商品类型的添加；
2. 商品的添加；（图片的上传）
3. 商品规格参数的功能的添加；
4. 添加缓存功能；
5. 添加solr搜索功能；
6. 单点登录系统的添加；
7. 订单模块的添加；


## 各个子模块的作用：
1. taotaocommon:存放公共组件或工具类和公共jar包；

2. taotaomanager:项目后台的代码模块；（后台管理系统）
    1. taotaomanagerpojo:后台系统的pojo对象；
    2. taotaomanagermapper:存放mybatis的mapper类以及sql配置文件;
    3. taotaomanagerservice:存放业务层的代码；
    4. taotaomanagerweb:存放controller的代码和页面代码；
    
3. taotaorest:(服务提供模块)
    1. 向各个模块提供REST服务；
    
4. taotaoportal:门户代码模块；（前台系统）
    1. 购物车的代码放在这个里面；
    2. sso的拦截器代码；
    【注】本模块依赖于taotaorest来实现业务；

5. taotaosso：单点登录模块；
6. taotaoorder:订单模块;
  