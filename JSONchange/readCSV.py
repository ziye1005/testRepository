from py2neo import Graph, Node, Relationship, NodeMatcher, RelationshipMatcher
# encoding:utf-8
import csv

graph = Graph("http://localhost:7474/", username="neo4j", password="120308")
graph.delete_all()
# file = open("C://Users/ASUS/Desktop/hs.csv", 'r', encoding='utf-8')
# for line in file.readlines():
#     data=list(line)
#     print(data)

with open("E:/研一汇总/弹药毁伤项目/文档/相关文件/2021.10/导入导出/RC-135型飞机.csv", 'r', encoding="UTF-8") as f:
    reader = csv.reader(f)
    data = list(reader)
    # print(data)

for i in range(1, len(data)):
    properties = {}
    type = ''
    for j in range(0, len(data[0])):
        if data[i][j] != "0":
            properties[data[0][j]] = data[i][j].replace('\n', '').replace('?', '')
            if data[0][j] == '类型':
                type = data[i][j]
    node = Node(type, **properties)
    graph.create(node)
    print(properties)
