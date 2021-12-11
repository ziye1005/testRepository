import cx_Oracle
# python连接oracle数据库，实现模糊条件查询
# 第一步：链接数据库，用户名/密码@localhost:1521/orcl
conn = cx_Oracle.connect("scott/120308@localhost:1521/orcl")
# 第二步：创建一个游标对象
cur = conn.cursor()

# 第三步：执行sql语句
# 1、准备sql语句
sql = "SELECT * FROM scott.TARGETZSZF where ID like '%chaJcem4Yz%' "
# 2、执行sql语句
res = cur.execute(sql)
# 第四步：提取sql语句查找的内容
data = cur.fetchall()
# print(data)
for i in data:
    list1 = list(i)
    # print(list1)
    des = cur.description
    # print("表的描述:", des)
    t = ",".join([item[0] for item in des])
    print(type(t))
    watch_head = t.split(',')
    print(watch_head)
    dict1 = dict(zip(watch_head, list1))
