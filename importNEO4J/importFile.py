import csv

# 连接neo4j数据库，输入地址、用户名、密码
from py2neo import Graph, Node, Relationship, NodeMatcher, RelationshipMatcher

graph = Graph("http://127.0.0.1:7474", username="neo4j", password="120308")
# 清除原有全部节点
# graph.delete_all()

path = "E:/研一汇总/弹药毁伤项目/文档/相关文件/2021.10/导入导出/导出/印军装备/印军装备CSV/export印度水面舰艇.csv"
with open(path, 'r', encoding="UTF-8") as f:
    reader = csv.reader(f)
    data = list(reader)
    # print(data)

for i in range(1, len(data)):
    properties = {}
    dataType = '印度武器'
    #dataType = "武器挂载"       # 台军飞机挂载
    #dataType = "台军飞机"   # 台军武器2
    #dataType = "印度飞机"  # 印度飞机挂载
    for j in range(0, len(data[0])):
        if data[i][j] != "0":
            properties[data[0][j]] = data[i][j].replace('\n', '').replace('?', '')
            # if data[0][j] == '挂载方案':
            #     dataType = data[i][j]
    #   双乘号： 变量中可迭代对象的元素拆解出来，作为独立的参数第传给函数
    node = Node(dataType, **properties)
    graph.create(node)
    print(dataType)
print(properties)

