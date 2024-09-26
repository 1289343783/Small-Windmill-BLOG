#  **[Small-Windmill-BLOG](https://gitee.com/wh578/small-windmill-blog)** 个人博客

Small-Windmill-BLOG是本人创办的个人博客平台，主要用于记录我在学习道路上的点点滴滴。在这里，我会分享一些学习笔记、心得体会以及编程实践，涵盖技术知识、项目经验和个人感悟。这个博客旨在帮助自己梳理知识体系。 

#### 项目介绍

项目技术构建使用前后端分离Springboot+vue3构建

**前端**： Vue 3 + TypeScript + Vite构建 

**后端**：springBoot+mybatis-plus+redis+Sa-token+hutool+swagger2构建

### 运行项目

使用git拉取代码

```bash
git clone https://gitee.com/guchengwuyue/yshopmall.git
```

### 前端启动

#### 安装依赖

```bash
$ pnpm i
```

####  项目启动

```bash
$ pnpm dev
```

####  使用环境

- **vue3**
- **TypeScript**
- **vite**
- **Pinia**
- **vueRouter**
- **element-plus **
- **tailwindcss**
- **vueuse工具**
- **pnpm 版本 8.6.10**
- **node-js 版本 v18.17.0**

### 后端启动

####  基本环境（必备）

- JDK：8+

- Redis 3.0+

- Maven 3.0+

- MYSQL 8+

#### 运行项目

2 . idea打开项目Maven加载依赖

3 .导入mysql数据，运行项目下的wu-blog.sql

4 .在blog模块下的resources文件夹修改application.yml

- 修改mysql账号和密码

- 修改redis密码

- 运行BlogApplication,java