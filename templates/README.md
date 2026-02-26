# 项目模板使用说明

## 模板类型

### Maven 原型模板
位于 `maven-archetype/` 目录，包含以下模板：

- **java-basic-archetype**：Java 基础项目模板
- **spring-boot-archetype**：Spring Boot 项目模板
- **web-app-archetype**：Web 应用项目模板
- **database-archetype**：数据库项目模板

## 使用方法

### 使用 Maven 命令创建项目

1. **安装原型模板**：
   ```bash
   cd templates/maven-archetype/<archetype-name>
   mvn install
   ```

2. **使用原型创建项目**：
   ```bash
   mvn archetype:generate \
     -DgroupId=com.example \
     -DartifactId=my-project \
     -DarchetypeGroupId=com.demos.java \
     -DarchetypeArtifactId=<archetype-name> \
     -DarchetypeVersion=1.0.0 \
     -DinteractiveMode=false
   ```

### 使用工具脚本创建项目

1. **使用 create-project.sh 脚本**：
   ```bash
   ./tools/create-project.sh <category> <project-name>
   ```

   例如：
   ```bash
   ./tools/create-project.sh core java-basics-demo
   ```

## 模板结构

### Java 基础项目模板
```
project/
├── src/
│   ├── main/java/       # 主源代码
│   └── test/java/       # 测试代码
└── pom.xml              # Maven配置
```

### Spring Boot 项目模板
```
project/
├── src/
│   ├── main/java/       # 主源代码
│   ├── main/resources/  # 资源文件
│   └── test/java/       # 测试代码
└── pom.xml              # Maven配置
```

### Web 应用项目模板
```
project/
├── src/
│   ├── main/java/       # 主源代码
│   ├── main/resources/  # 资源文件
│   ├── main/webapp/     # Web资源
│   └── test/java/       # 测试代码
└── pom.xml              # Maven配置
```

### 数据库项目模板
```
project/
├── src/
│   ├── main/java/       # 主源代码
│   ├── main/resources/  # 资源文件
│   └── test/java/       # 测试代码
└── pom.xml              # Maven配置
```

## 自定义模板

1. **创建新模板**：
   ```bash
   mvn archetype:create-from-project
   ```

2. **修改模板**：
   编辑生成的模板文件，调整结构和内容

3. **安装模板**：
   ```bash
   cd target/generated-sources/archetype
   mvn install
   ```