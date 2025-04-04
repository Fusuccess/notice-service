## 消息推送服务 (Java版)

本项目是一个基于Java语言开发的消息推送服务,实现消息的实时通知。


## 🛠️ 技术栈
- Java 17
- Maven 3.8.4
- Log4j 2.17.1
- Lombok 1.18.28
- Git 2.37.1


## 📝 功能特性
- 支持多种推送目标的配置
- 支持灵活的消息内容和推送策略
- 支持日志记录和错误处理


## 📋 功能列表
- [x] 钉钉推送


## 📦 环境安装
```shell
# 1. 克隆项目
git clone https://github.com/fusuccess/notice-service.git
# 2. 进入项目目录
cd notice-service
# 3. 编译项目
mvn clean package
# 4. 运行项目
java -jar target/notice-service-1.0.0.jar
```


## 🐣运行示例

| 推送类型 | 执行步骤 | 执行结果 |
|------|------|------|
| 钉钉   |      |      |


## 📝计划任务
### 功能列表
- [ ] 邮箱推送
- [ ] 短信推送

### 运行方式
- [ ] 定时任务
- [ ] docker部署


## 🌲代码结构树
```shell
.
|____resources
| |____properties.json  #全局配置文件
| |____log4j.properties  #日志配置文件
|____java
| |____com
| | |____fusuccess
| | | |____config
| | | | |____AppConfig.java #应用配置类
| | | |____module
| | | | |____notice
| | | | | |____impl
| | | | | | |____dingtalk
| | | | | | | |____DingTalkImpl.java #钉钉推送
| | | | | | |____sms
| | | | | | |____email
| | | | | | | |____EmailImpl.java   #邮箱推送
| | | | | |____config
| | | | | | |____DingTalkConfig.java #钉钉推送配置实体
| | | | | | |____EmailConfig.java #邮箱推送配置实体
| | | | | | |____NoticeConfig.java #消息推送配置实体
| | | | | |____strategy
| | | | | | |____NoticeClient.java  #消息推送客户端接口
| | | | | | |____NoticeStrategy.java #消息推送策略接口
| | | |____untils
| | | | |____DateUtils.java #日期工具类
| | | |____common
| | | | |____ConfigLoader.java #全局配置加载器
| | | |____Main.java
```