# 使用 JDK 17 作为基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 设置时区
ENV TZ=Asia/Shanghai

# 创建配置文件和日志目录
RUN mkdir -p /app/config /app/logs

# 复制 JAR 文件
COPY target/*.jar app.jar

# 复制配置文件到配置目录
COPY src/main/resources/config.json /app/config/

# 设置容器启动命令
ENTRYPOINT ["java", \
    "-jar", \
    "/app/app.jar"] 