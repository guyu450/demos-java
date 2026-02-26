#!/bin/bash

# 构建所有Java Maven项目

echo "Building all Java Maven projects..."
echo "=================================="

# 遍历所有项目目录
find projects -name "pom.xml" | sort | while read pom;
do
    project_dir=$(dirname "$pom")
    project_name=$(basename "$project_dir")
    category=$(basename "$(dirname "$project_dir")")
    
    echo ""
    echo "Building $category/$project_name..."
    echo "-----------------------------"
    
    # 构建项目
    (cd "$project_dir" && mvn clean install)
    
    if [ $? -eq 0 ]; then
        echo "✓ $category/$project_name built successfully"
    else
        echo "✗ $category/$project_name build failed"
        build_failed=true
    fi
done

echo ""
echo "=================================="

if [ -z "$build_failed" ]; then
    echo "All projects built successfully!"
else
    echo "Some projects failed to build."
    exit 1
fi
