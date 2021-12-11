# IntentionAnalysis
#### 初始化

1. mysql创建数据库，名称为intention，执行intention.sql
2. 启动springboot，输入http://localhost:8080/user/select，查询到数据即运行成功

#### 说明

1. mysql配置在resources/application.yml，可以设置数据库路径、用户名、密码
2. 各组在mysql中建立需要的表，定期上传构建的.sql文件
3. pom.xml中添加需要的依赖包
4. mapper提供了两种sql语句书写途径
5. controller-service-mapper三层结构应一一对应
6. 具体算法应封装在algorithm/_文件夹中，各组之间互不干扰
7. 各组应新建分支在自己分支上进行开发，定期合并到主分支

#### UDP接口

1. 目前UDP连接处于测试阶段，可以先使用controller的HTTP接口
2. UdpPortListener提供8081端口的监听，用于接收数据，当springboot启动时自动开启服务
3. UdpSender和UdpServer有问题，可测试使用

