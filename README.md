# Android-Router
A simple and easy to use Android Router

## Features
 - 支持动态注入路由
 - 支持注解方式注入路由
 - 支持Bundle传参
 - 支持添加拦截器
 
## Installation
### Gradle Dependency
#####   Add the library to your project build.gradle
```gradle
compile 'com.joybar.librouter:librouter:1.0.0'
```


## Sample Usage

### Step1

动态注入路由

```java
RouterManager.registerRouter("user", "user_main", com.joybar.moduleuser.MainActivity.class);
RouterManager.registerRouter("shop", "shop_main", com.joybar.moduleshop.MainActivity.class);

```
OR

注解方式注入路由

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
InjectComponentUtil.inject("com.joybar.moduleuser.MainActivity");
InjectComponentUtil.inject("com.joybar.moduleshop.MainActivity");
```


### Step2

#### 直接启动Activity


```java
RouterManager
        .with(MainActivity.this)
        .buildRule(new Rule("user", "user_main"))
        .go();
```
#### stratActivityForResult方式启动Activity
```java
RouterManager
        .with(MainActivity.this)
        .buildRule(new Rule("user", "user_main"))
        .goForResult(requestCode);
```

#### Activity传参并添加拦截器


```java
Bundle bundle = new Bundle();
bundle.putInt("id", 10000);
RouterManager.with(MainActivity.this)
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
        