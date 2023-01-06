# Android VpnService Demo

## 简介

这是一个Android的VPNService类的使用Demo，将劫持设备所有App传输数据并代为请求和接收处理。

没有什么自己的东西，只是用自己风格的Kotlin代码将其它几个项目抄了一遍，改了一些设计和增加一些可读性。

但没有完全使用kotlin，因为其它大佬的代码还是很优秀的，直接使用了。

## 原理

1. 建立Android Vpn Service，获取所有对外发送网络数据包。
2. 解析数据包，获取TCP/UDP类型，及请求来源和请求目标
3. 建立绕行socket，请求目标
4. 获取返回结果，传递给请求来源

## 设备要求

Android 5.0(21) 及更高版本

## 技术概要

1. Kotlin
2. jetpack-compose
3. BIO
4. NIO

## 可以用来做什么？

1. 可以修改设备的网络传输数据，屏蔽广告什么的
2. 可以用来计算准确的网络速度和延迟
3. 可以让数据包经由其它协议承载
4. 可以做弱网测试
5. 可以用来抓包
6. 可以用来做负载均衡
7. 可以定向流量
8. 可以加密数据提升网络安全
9. 可以多网络接口加速或高可用
10. 可以通过USB连接接入网络
11. 自己想更多的可能……

本项目不包含任何业务逻辑，以上功能此项目均未实现，请自己参考本项目实现或改造此项目。

## 性能

在小米note3上持续速度>40MiB/s，手机很烫（必然的，完全软件处理，跳过了硬件加速）

CPU使用20~50%之间蹦跶

内存在190MB以下，但你能看到日志中发生大量GC，暂无思路优化。

## 参考和引用内容

1. [ToyVpn](https://android.googlesource.com/platform/development/+/master/samples/ToyVpn)
2. [VpnService](https://developer.android.google.cn/guide/topics/connectivity/vpn#lifecycle)
3. [android-vpnservice-example](https://github.com/mightofcode/android-vpnservice-example)

说实话我抄了逻辑，但也没完全抄懂，建议也自己抄一遍，最好先掌握计算机网络
