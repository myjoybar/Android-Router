# Android-Router
一个使用简单方便的Android router框架，
一个完整的Android模块化开发示例

 ![image](https://github.com/myjoybar/Android-Router/blob/master/screenshots/screenshot.gif)

 [Blog地址](https://blog.csdn.net/yalinfendou/article/details/78822749)
## Features
 - 支持动态注入路由
 - 支持注解方式注入路由
 - 支持Bundle传参
 - 支持module之间传大容量的数据
 - 支持添加拦截器
 - 支持module单独作为Application编译
 - 支持module之间同步或者异步服务调用
 - 支持主app的Application在各个module内调用
 - 路由引导模块：自动生成module的调用方法 （ Thank for [Obo](https://github.com/OboBear)）


 
## Installation
### Gradle Dependency
#####   Add the library to your project build.gradle
```gradle
    compile 'com.joybar.router:librouter:1.1.7'
    compile 'com.joybar.router:compiler:1.1.7'
    compile 'com.joybar.router:annotation:1.1.7'
```

## Structure
- app： 一个空壳，本身不实现任何业务逻辑，最终打包成完整的release APK 
- moduleshop：实现shop相关的业务逻辑，可单独编译成APK
- moduleuser：实现user相关的业务逻辑，可单独编译成APK，和其它module通过router通信
- librouter：router实现的核心类


 ![image](https://github.com/myjoybar/Android-Router/blob/master/screenshots/screenshot1.png)
 
## Sample Usage

### Step1（初始化）

方式一：动态注入路由

```java
Router.registerRouters(new Router.RouterTable() {
    @Override
    public List<Rule> buildRuleList() {
        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(new Rule("user", "user_main", com.joybar.moduleuser.MainActivity.class));
        ruleList.add(new Rule("shop", "shop_main", com.joybar.moduleshop.MainActivity.class));
        return ruleList;
    }
});

```

方式二：注解方式注入路由

```java
@RouterRegister(module = "user", path = "user_main")
public class MainActivity extends AppCompatActivity {
...
}

```
```java
@RouterRegister(module = "shop", path = "shop_main")
public class MainActivity extends AppCompatActivity {
...
}

```
并在Application或者初始页面初始化注解路由器

```java
//参数为module name
RouterInject.registerModule("shop");
RouterInject.registerModule("user");
```

### Step2（生成路由引导模块，可选）

#### 1. 添加注解（注解@RegisterLaunch只会读取方法参数，而launch方法本身可供模块内部调用）

```java
    @RegisterLaunch
    public static void launch(Context context,String address){
        Intent intent = new Intent(context,ReceiveParamActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("address",address);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @RegisterLaunch
    public static void launch(Context context,String name,int id){
        Intent intent = new Intent(context,ReceiveParamActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putString("name",name);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

```

####  2. 在此添加需要生成路由引导模块的module,运行Builder#main

```java
public class Builder {

    public static void main(String[] args) {
        System.out.println("=============start build=============");
        CodeMaker.autoGenerateModuleMethodName("moduleshop","/baselib/src/main/java","com.joy.baselib.guider.routertable");
        System.out.println("=============end build=============");
    }

}
```
#### 3.在参数指定的路径下生成RouterTable$$Moduleuser和RouterTable$$Moduleshop以及相关的调用方法

RouterTable$$Moduleshop 为自动生成的类,其方法可供其他Module调用

```java
public final class RouterTable$$Moduleshop {
  public static RouterGuider launchFinishWithResult() {
    // This class was generated automatically 2017-12-11 18:23:03
    // module=shop,path=finish_with_result
    RouterGuider routerGuider =  new RouterGuider("shop", "finish_with_result");
    return routerGuider;
  }

  public static RouterGuider launchMain() {
    // This class was generated automatically 2017-12-11 18:23:03
    // module=shop,path=main
    RouterGuider routerGuider =  new RouterGuider("shop", "main");
    return routerGuider;
  }

  public static RouterGuider launchPostModuleData() {
    // This class was generated automatically 2017-12-11 18:23:03
    // module=shop,path=post_module_data
    RouterGuider routerGuider =  new RouterGuider("shop", "post_module_data");
    return routerGuider;
  }

  public static RouterGuider launchReceiveParam(String name, Integer id) {
    // This class was generated automatically 2017-12-11 18:23:03
    // module=shop,path=receive_param
    RouterGuider routerGuider =  new RouterGuider("shop", "receive_param");
    routerGuider.withString("name", name);
    routerGuider.withInt("id", id);
    return routerGuider;
  }

  public static RouterGuider launchReceiveParam(String address) {
    // This class was generated automatically 2017-12-11 18:23:03
    // module=shop,path=receive_param
    RouterGuider routerGuider =  new RouterGuider("shop", "receive_param");
    routerGuider.withString("address", address);
    return routerGuider;
  }
}

```
### Step3（使用）

#### 1.   直接启动Activity

方式一：

```java
Router.create()
		.buildRule(new Rule("shop", "main"))
		.navigate(context);
```

方式二  **（推荐 ）**

- RouteTable$$Moduleshop为自动生成的类
- 此类按照moduleuser开发者指定的规则生成
- 对于使用者来说，只需关注RouteTable$$moduleuser中的对外公开的方法即可

```java
RouterTable$$Moduleshop
        .launchMain()
        .navigate(context);
```
#### 2. startActivityForResult方式启动Activity
```java
                RouterTable$$Moduleshop.launchFinishWithResult()
                        .navigate(MainActivity
                        .this, 2);;
                        
                // OR
//                Router.create()
//                        .buildRule(new Rule("shop", "finish_with_result"))
//                        .navigate(MainActivity
//                        .this, 2);
```

#### 3.  Activity之间参数传递

```java
                RouterTable$$Moduleshop
                        .launchReceiveParam("obo", 23)
                        .navigate(context);

                // OR
//                final Bundle bundle = new Bundle();
//                bundle.putInt("id", 123);
//                bundle.putString("name", "obo");
//                Router.create()
//                        .buildRule(new Rule("shop", "receive_param"))
//                        .withExtra(bundle)
//                        .navigate(context);
```

#### 4.  为Router 添加拦截器

```java
     RouterTable$$Moduleshop.launchMain()
             .withRouteInterceptor(new TestInterceptor(mContext))
             .withCallBack(new InterceptorCallback() {
                 @Override
                 public void onIntercept(Object result) {
                     Toast.makeText(mContext, result.toString(), Toast.LENGTH_LONG).show();
                 }

                 @Override
                 public void onContinue() {
                     Toast.makeText(mContext, "continue", Toast.LENGTH_LONG).show();
                 }
             })
             .navigate(mContext);
```
#### 5.  modlue之间同步或者异步调用服务


##### Step1 在module创建service类并实现IBaseService
```java
public class ShopService implements IBaseService {
	private static final String SERVICE_CMD_TEST = "cmd_test";
	private static final String SERVICE_CMD_LOGIN = "cmd_login";

	@Override
	public Object execute(String cmd, Object... args) {
		Object obj[] = args;
		String msg = "";
		if (obj != null && obj.length != 0) {
			msg = Arrays.toString(obj);
		}
		if (SERVICE_CMD_TEST.equals(cmd)) {
			return "成功同步调用 Shop service " + SERVICE_CMD_TEST + "服务，返回值：" + msg;
		}
		return null;
	}

	@Override
	public void executeAsync(String cmd, IServiceCallBack iServiceCallBack, Object... args) {

		if (SERVICE_CMD_LOGIN.equals(cmd)) {

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if ("Tom".equals(args[0]) && "123456".equals(args[1])) {
				iServiceCallBack.onSuccess("登录成功");
			} else {
				iServiceCallBack.onFailure(new RouterServiceException("用户名或者密码错误"));
			}

		}
	}

	@Override
	public String moduleServiceName() {

		return "RSShopService";
	}
}
```
##### Step2 在module对应的AndroidManifest文件中注册meta信息
```java
<meta-data
        android:name="RSShopService"
        android:value="com.joybar.moduleshop.ShopService"/>
```
##### Step3 在Application中初始化
```java
RouterServiceManager.getInstance().init(this);// 注册组件之间服务路由

```
##### Step4 在User module中使用
```java
//调用同步服务
IBaseService service = RouterServiceManager.getInstance().getService("RSShopService");
String result = (String) service.execute("cmd_test","ABCDE");
Toast.makeText(mContext, "User module " + result,Toast.LENGTH_LONG).show();


//调用异步服务
IBaseService service = RouterServiceManager.getInstance().getService("RSShopService");
String userName = "Tom";
String pwd = "123456";
service.executeAsync("cmd_login", new IServiceCallBack() {

	@Override
	public void onSuccess(Object result) {

		Toast.makeText(mContext, result.toString(), Toast.LENGTH_LONG).show();

	}

	@Override
	public void onFailure(RouterServiceException routerServiceException) {
		Toast.makeText(mContext, routerServiceException.getMessage(), Toast.LENGTH_LONG).show();
	}
}, userName, pwd);
				
				
```


#### 6.  其它

#####  1.单独编译module：
step1.  config.gradle

```java
 isDebugType = false   //主App工程
 project.ext.set("is_debug_type",  "false") //module 工程
```
step2.  执行 ./gradlew :moduleshop:assembleRelease 或者./gradlew :moduleshop:assembleDebug

#####  2.为保证各个module能在主App的relase版本共用Application，以及每个module的application在debug和release状态下调用方法不变，请实现ApplicationService 接口


## License

    Copyright 2017 MyJoybar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.    
        