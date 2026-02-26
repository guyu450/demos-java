# Java Maven 项目集合

这是一个用于存放Java相关Maven项目的集合，包含各种Java技术的示例代码和demo。

## 目录结构

```
demos-java/
├── .git/                    # Git 版本控制目录
├── .github/                 # GitHub 相关配置
│   ├── workflows/           # CI/CD 工作流
│   │   └── maven.yml        # Maven 构建工作流
├── .gitignore               # Git 忽略文件配置
├── README.md                # 项目集合总说明
├── docs/                    # 文档目录
│   ├── guides/              # 使用指南
│   └── best-practices.md    # 最佳实践
├── projects/                # 项目集合
│   ├── core/                # 核心功能示例
│   │   ├── java-basics/     # Java 基础示例
│   │   ├── oop-concepts/    # 面向对象概念
│   │   └── collections/     # 集合框架示例
│   ├── spring/              # Spring 相关示例
│   │   ├── spring-core/     # Spring 核心
│   │   ├── spring-boot/     # Spring Boot 示例
│   │   └── spring-security/ # Spring Security 示例
│   ├── web/                 # Web 相关示例
│   │   ├── servlet/         # Servlet 示例
│   │   ├── jsp/             # JSP 示例
│   │   └── rest-api/        # REST API 示例
│   ├── database/            # 数据库相关示例
│   │   ├── jdbc/            # JDBC 示例
│   │   ├── jpa/             # JPA 示例
│   │   └── mybatis/         # MyBatis 示例
│   └── advanced/            # 高级主题
│       ├── concurrency/     # 并发编程
│       ├── design-patterns/ # 设计模式
│       └── performance/     # 性能优化
├── templates/               # 项目模板
│   ├── maven-archetype/     # Maven 原型模板
│   └── README.md            # 模板使用说明
└── tools/                   # 工具脚本
    ├── create-project.sh    # 创建项目脚本
    └── build-all.sh         # 构建所有项目脚本
```

## 项目分类

- **core/**: 包含Java核心功能的示例，如基础语法、面向对象编程、集合框架等
- **spring/**: 包含Spring框架相关的示例，如Spring Core、Spring Boot、Spring Security等
- **web/**: 包含Web开发相关的示例，如Servlet、JSP、REST API等
- **database/**: 包含数据库相关的示例，如JDBC、JPA、MyBatis等
- **advanced/**: 包含高级主题的示例，如并发编程、设计模式、性能优化等

## 如何使用

1. 克隆仓库：
   ```bash
   git clone <repository-url>
   cd demos-java
   ```

2. 构建项目：
   ```bash
   # 构建单个项目
   cd projects/<category>/<project-name>
   mvn clean install

   # 构建所有项目
   ./tools/build-all.sh
   ```

3. 创建新项目：
   ```bash
   ./tools/create-project.sh <category> <project-name>
   ```

## 贡献指南

1. Fork 仓库
2. 创建功能分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

## 许可证

MIT License