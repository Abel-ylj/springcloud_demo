## SpringCloud

>SpringCloud是整套web组件，版本名用伦敦地铁站名来命名
>
>配置管理，服务发现，智能路由，负载均衡，熔断器，控制总线，集群状态
>
>Eureka: 注册中心
>
>Gateway： 服务网关， zuul已经不再维护
>
>Ribbon： 负载均衡
>
>Feign: 服务调用
>
>Hystrix: 服务熔断降级

![image-20210125153856776](https://yljnote.oss-cn-hangzhou.aliyuncs.com/2021-01-25-073857.png)

## 注册中心(Eureka)

1. 服务注册和发现： 服务上线时就注册到eureka server，定时拉取服务列表，可以配置多个中心(高可用)

2. 服务状态监管: 客户端定时发送心跳续约服务，若超过阈值，中心会将服务剔除，若是正常下线的服务会发送消息给中心，中心立马清除记录。 eureka中心还有个服务保护功能，当1个服务故障下线时，中心会统计其15min内的续约率，来决定是否剔除。

3. 服务容灾(高可用)： 搭建多个eureka，互相注册为客户端，互相发现

    

### 注册中心高可用配置

1. 源码中yml 配置成如下形式，表示可以从启动参数中获取配置值
2. 多个eureka实例互相注册，如下，10086注册到10087，10087注册到10086
3. 客户端服务 配置多个eureka中心，若10086中心挂了，会自动找10087，因为两个中心互相注册了，所以数据也是一致的

![](https://yljnote.oss-cn-hangzhou.aliyuncs.com/2021-01-24-100936.png)



## 代理请求(Feign)

>动态代理 了restTemplate进行子服务请求，该组件集成了 
>
>1.自保护功能Hystrix(服务降级熔断)，
>
>2.软路由(ribbon，被访问的服务角度看是负载均衡)， 
>
>3.日志
>
>4.请求压缩
>
>实现了代码分离，不用在业务逻辑中调用子服务时， 直接对restTemplate对象进行操作，
>
>如传入url，封装返回实体等。将这一块代码都分离出去。单独管理

## 负载均衡 (Ribbon)

>跟nginx不同，ribbon是对从eureka server获取到的服务列表中，根据算法来均衡请求。
>
>请求不会集中到一个中心压力点。轮训，随机， 权重算法等。。。

- 原理： 在请求发送时，Ribbon负载均衡拦截器拦截，查找本地请求的服务的列表，根据算法选取一个，再发送。
- 源码： 从eureka-starter依赖中传递过来了ribbon依赖，里面有和spring.factories文件，会被@EnableAutoConfiguration递归查找到，其中有个LoadBalancerAutoConfiguration,完成环境侦测和自动配置
- ![image-20210124193220163](https://yljnote.oss-cn-hangzhou.aliyuncs.com/2021-01-24-113220.png)

## 服务响应超时降级&熔断降级(Hystrix)

- 背景： 前端工程获取到请求后，会开启一个线程来处理，当这个处理任务依赖于远程服务，那么就要等待RPC服务调用结束，才会完成该次请求。 若RPC服务因为某种原因无法响应，那么该请求的资源就无法释放掉。

    当这种请求数量达到阈值后，服务器就会奔溃。

- 响应超时降级：单次请求响应时间查过设定值时，自动响应 故障语(对不起。。网络飞了)，

    熔断降级：多次请求的响应动态统计数据 给到 breaker，会根据情况，来切断服务，半开服务，全开服务。比较智能

- <img src="https://yljnote.oss-cn-hangzhou.aliyuncs.com/2021-01-24-114916.png" alt="image-20210124194915755" style="zoom:50%;" />

- <img src="https://yljnote.oss-cn-hangzhou.aliyuncs.com/2021-01-24-121836.png" alt="image-20210124201836022" style="zoom: 50%;" />

## Http请求工具

- httpClient
- okHttp
- JDK原生URLConnection
- Spring封装的工具 restTemplate







## 网关(Gateway)

> 基本功能：安全，监控/埋点， 限流等，提供了有效且统一的API路由管理方式
>
> ​	一整套微服务提供的综合服务，对外只暴露Gateway！！！

- Spring Cloud Gateway基于Filter链提供功能，通过这些过滤器可以将请求转发到对应的微服务。
- Gateway是加载整个微服务最前沿的防火墙和代理器，隐藏微服务IP，
- 核心： 过滤和路由

### 过滤

1. 局部过滤器

    ```sh
    都实现GatewayFilterFactory接口
    1.通过spring.cloud.gateway.routes.filters配置在具体的路由项下。
    2.spring.cloud.gateway.default-filters对所有请求过滤
    ```

2. 全局过滤器

    ```sh
    实现GlobalFilter接口，不需要在配置文件中配置，作用在所有的请求上
    ```

### 路由

1. filter项

## 配置中心服务&消息总线

- 微服务监听mq，触发yml更新事件
- 用户修改git上的配置后，向配置中心发送触发请求，
- 配置中心完成刷新逻辑(1.从git中重新获取yml  2. 发送消息给MQ)----->监听的服务都完成刷新

![image-20210125144544833](https://yljnote.oss-cn-hangzhou.aliyuncs.com/2021-01-25-064545.png)



