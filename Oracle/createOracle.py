import pandas as pd


# 27所excel表格转为oracle建表和加注释的形式
def write_txt(path, content):
    """实现TXT文档的写方法"""
    print(content)
    with open(path, 'w', encoding='gb18030') as f:
        f.write(content)


path0 = "resources/test.xlsx"
path2 = "resources/output_commend.txt"
path3 = "resources/output_comment.txt"
# data = pd.read_csv(path1, encoding="utf-8")
data = pd.read_excel(path0)
table_name = "scott.ZSZF2"

str1 = "create table " + table_name + "(\n"
str2 = ""
data = data.dropna(subset=["字段中文名称"], axis=0)
for i in range(data.shape[0]):

    a = str(data.iloc[i].at['字段中文名称'])
    b = str(data.iloc[i].at['字段英文名称'])
    c = str(data.iloc[i].at['数据类型'])
    d = str(data.iloc[i].at['约束'])
    e = str(data.iloc[i].at['备注'])
    if d == 'nan':
        d = ''
    else:
        d = "," + str(data.iloc[i].at['约束'])
    if e == 'nan':
        e = ''
    else:
        e = "," + str(data.iloc[i].at['备注'])
    str1 = str1 + b + " " + c + ",\n"
    str2 = str2 + "COMMENT ON COLUMN " + table_name + "." + b + " IS " + "\'" + a + e + "\'" + ";\n"
str1 = str1 + "primary key(ID));"
write_txt(path2, str1)
write_txt(path3, str2)
