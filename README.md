# Android-Router
A simple and easy to use Android Router

## Features
 - 支持动态注入路由
 - 支持注解方式注入路由
 - 支持Bundle传参
 - 支持添加拦截器
 - 自动生成module调用方法（ Thanks for [Obo](https://github.com/OboBear)）
 
## Installation
### Gradle Dependency
#####   Add the library to your project build.gradle
```gradle
compile 'com.joybar.router:librouter:1.0.5'
compile 'com.joybar.router:compiler:1.0.5'
```


## Sample Usage

### Step1

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


### Step2

####  直接启动Activity

方式一：

```java
Router.getRouterService()
		.buildRule(new Rule("user", "user_main"))
		.navigate(context);
```

方式二  **（推荐 ）**：（RouteTable$$moduleuser 为自动生成的类,此类按照moduleuser的开发者指定的规则自动生成，对于其他使用者来说，只需关注RouteTable$$moduleuser中的调用方法即可）

```java
RouteTable$$moduleuser
		.GoToUser_main()
		.navigate(context);	
```
#### startActivityForResult方式启动Activity
```java
    Router
        .with(MainActivity.this)
        .buildRule(new Rule("user", "user_main"))
        .goForResult(requestCode);
```

#### Activity传参并添加拦截器


```java
Bundle bundle = new Bundle();
bundle.putInt("id", 10000);
Router.with(MainActivity.this)
        .buildRule(new Rule("shop", "shop_main"))
        .withExtra(bundle)
        .addInterceptor(new TestInterceptor())
        .withInterceptorCallback(new InterceptorCallback() {
            @Override
            public void onIntercept(Object result) {
                Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onContinue() {
                Toast.makeText(MainActivity.this, "continue", Toast.LENGTH_LONG).show();

            }
        })
        .go();
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
        