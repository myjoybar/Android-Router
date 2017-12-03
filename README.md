# Android-Router
A simple and easy to use Android Router

## Features
 - 支持动态注入路由
 - 支持注解方式注入路由
 - 支持Bundle传参
 - 支持添加拦截器
 - 支持module单独作为Application编译
 - 支持主app的Application在各个module内调用
 - 自动生成module调用方法（ Thanks for [Obo](https://github.com/OboBear)）
 -  moduleEventBus实现module之间通信
 
## Installation
### Gradle Dependency
#####   Add the library to your project build.gradle
```gradle
compile 'com.joybar.router:librouter:1.0.5'
compile 'com.joybar.router:compiler:1.0.5'
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

- 在此添加需要生成路由引导模块的module,运行Builder#main，
- 在routertable路径下生成RouterTable$$moduleuser和RouterTable$$moduleshop以及相关的调用方法

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
example:

```java
  ...
  public static AutoRouter GoToShop_receive_param(String name, Integer id) {
    // This class was generated automatically 2017-12-03 20:19:46
    // module=shop,path=shop_receive_param
    AutoRouter autoRouterBuilder =  new AutoRouter("shop", "shop_receive_param");
    autoRouterBuilder.withString("name", name);
    autoRouterBuilder.withInt("id", id);
    return autoRouterBuilder;
  }
  ...

```
### Step3（使用）

#### 1.   直接启动Activity

方式一：

```java
Router.create()
		.buildRule(new Rule("shop", "shop_main"))
		.navigate(context);
```

方式二  **（推荐 ）**

- RouteTable$$moduleuser为自动生成的类
- 此类按照moduleuser的开发者指定的规则生成
- 对于使用者来说，只需关注RouteTable$$moduleuser中的对外公开的方法即可

```java
RouterTable$$moduleshop
        .GoToShop_main()
        .navigate(context);
```
#### 2. startActivityForResult方式启动Activity
```java
                RouterTable$$moduleshop.GoToShop_finish_with_result()
                        .navigate(MainActivity
                        .this, 2);;
                        
                // OR
//                Router.create()
//                        .buildRule(new Rule("shop", "shop_finish_with_result"))
//                        .navigate(MainActivity
//                        .this, 2);
```

#### 3.  Activity之间参数传递

```java
                RouterTable$$moduleshop
                        .GoToShop_receive_param("obo", 23)
                        .navigate(context);

                // OR
//                final Bundle bundle = new Bundle();
//                bundle.putInt("id", 123);
//                bundle.putString("name", "obo");
//                Router.create()
//                        .buildRule(new Rule("shop", "shop_main"))
//                        .withExtra(bundle)
//                        .navigate(context);
```

#### 4.  为Router 添加拦截器

```java
  Router.create()
                .buildRule(new Rule("shop", "shop_main"))
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
        