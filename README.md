# Android-Router
A simple and easy to use Android Router

## Features
 - 支持动态注入路由
 - 支持注解方式注入路由
 - 支持Bundle传参
 - 支持module之间传大容量的数据
 - 支持添加拦截器
 - 支持module单独作为Application编译
 - 支持主app的Application在各个module内调用
 - 路由引导模块：自动生成module的调用方法
 - moduleEventBus：实现module之间通信
 
## Installation
### Gradle Dependency
#####   Add the library to your project build.gradle
```gradle
compile 'com.joybar.router:librouter:1.0.7'
compile 'com.joybar.router:compiler:1.0.7' //注解处理器来在编译期通过读取@RouterRegister()注解并解析,
compile 'com.joybar.router:routerguider:1.0.7'//路由引导模块，自动生module的调用方法
```




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
RouterInject.inject("com.joybar.moduleuser.MainActivity");
RouterInject.inject("com.joybar.moduleshop.MainActivity");
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
        CodeMaker.autoGenerateModuleMethodName("moduleshop");
        CodeMaker.autoGenerateModuleMethodName("moduleuser");
        System.out.println("=============end build=============");
    }

}
```
#### 3.在routertable路径下生成RouterTable$$Moduleuser和RouterTable$$Moduleshop以及相关的调用方法

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
  Router.create()
                .buildRule(new Rule("shop", "main"))
                .addInterceptor(new TestInterceptor()).withInterceptorCallback(new InterceptorCallback() {
            @Override
            public void onIntercept(Object result) {
                Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onContinue() {
                Toast.makeText(context, "continue", Toast.LENGTH_LONG).show();

            }
        }).navigate(context);
```
#### 5.  ModuleEventBus：modlue之间简单通信


##### Step1 在Activity#onCreate注册
```java
ModuleEventBus.getInstance().register(this);
```
##### Step2 在Activity#onDestroy反注册
```java
ModuleEventBus.getInstance().unregister(this);
```
##### Step3 发送数据
```java
ModuleEventBus.getInstance().post(new ShopInfo("KFC", "Hangzhou Xihu"));

```
##### Step4 使用注解，接收数据
```java
@ModuleEvent()
public void testReceiveModuleEventBusData(ShopInfo shopInfo) {
    Toast.makeText(this, "I am user main activity,receive data from shop,msg=" + shopInfo.toString(), Toast
            .LENGTH_LONG).show();
}
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
        