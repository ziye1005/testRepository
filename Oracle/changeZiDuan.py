# 将test.xlsx中的表格转化为Java model的形式
import pandas as pd


def write_txt(path, content):
    """实现TXT文档的写方法"""
    print(content)
    with open(path, 'w', encoding='gb18030') as f:
        f.write(content)


path0 = "resources/test.xlsx"
path2 = "resources/output_java.txt"
# data = pd.read_csv(path1, encoding="utf-8")
data = pd.read_excel(path0)
data['数据类型'] = data['数据类型'].str.upper()
str1 = ""
str2 = ""
data = data.dropna(subset=["字段中文名称"], axis=0)
for i in range(data.shape[0]):

    en_name = str(data.iloc[i].at['字段英文名称'])
    data_type = str(data.iloc[i].at['数据类型'])
    flag = 0
    if "VARCHAR" in data_type:
        change_type = "String"
    elif "DATE" in data_type:
        change_type = "Date"
    else:
        if "," in data_type:
            change_type = "Double"
        elif "NUMBER" in data_type:
            try:
                flag = data_type.split("(")[1].split(")")[0].isdigit()
                change_type = "BigDecimal"
            except:
                pass
        else:
            # pass
            print(i, end=" ")
            print(en_name)
    str1 = str1 + change_type + " " + en_name + ";\n"

write_txt(path2, str1)
