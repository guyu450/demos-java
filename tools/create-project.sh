#!/bin/bash

# 检查参数
if [ $# -ne 2 ]; then
    echo "Usage: ./create-project.sh <category> <project-name>"
    echo "Example: ./create-project.sh core java-basics-demo"
    exit 1
 fi

CATEGORY=$1
PROJECT_NAME=$2
PROJECT_DIR="projects/$CATEGORY/$PROJECT_NAME"

# 检查分类目录是否存在
if [ ! -d "projects/$CATEGORY" ]; then
    echo "Error: Category '$CATEGORY' does not exist"
    echo "Available categories: core, spring, web, database, advanced"
    exit 1
fi

# 检查项目是否已存在
if [ -d "$PROJECT_DIR" ]; then
    echo "Error: Project '$PROJECT_NAME' already exists in category '$CATEGORY'"
    exit 1
fi

# 创建项目目录
mkdir -p "$PROJECT_DIR/src/main/java" "$PROJECT_DIR/src/test/java"

# 创建pom.xml文件
cat > "$PROJECT_DIR/pom.xml" << EOF
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.demos.java</groupId>
    <artifactId>$PROJECT_NAME</artifactId>
    <version>1.0.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- JUnit 5 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.1.2</version>
            </plugin>
        </plugins>
    </build>
</project>
EOF

# 创建主类
mkdir -p "$PROJECT_DIR/src/main/java/com/demos/java/$CATEGORY"
cat > "$PROJECT_DIR/src/main/java/com/demos/java/$CATEGORY/${PROJECT_NAME^}.java" << EOF
package com.demos.java.$CATEGORY;

/**
 * $PROJECT_NAME
 */
public class ${PROJECT_NAME^} {
    public static void main(String[] args) {
        System.out.println("Hello from $PROJECT_NAME!");
    }
}
EOF

# 创建测试类
mkdir -p "$PROJECT_DIR/src/test/java/com/demos/java/$CATEGORY"
cat > "$PROJECT_DIR/src/test/java/com/demos/java/$CATEGORY/${PROJECT_NAME^}Test.java" << EOF
package com.demos.java.$CATEGORY;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * $PROJECT_NAME Test
 */
public class ${PROJECT_NAME^}Test {
    @Test
    public void testExample() {
        assertTrue(true);
    }
}
EOF

echo "Project '$PROJECT_NAME' created successfully in category '$CATEGORY'"
echo "Location: $PROJECT_DIR"
echo ""
echo "To build the project:"
echo "cd $PROJECT_DIR && mvn clean install"
echo ""
echo "To run the project:"
echo "cd $PROJECT_DIR && mvn exec:java -Dexec.mainClass=com.demos.java.$CATEGORY.${PROJECT_NAME^}"
