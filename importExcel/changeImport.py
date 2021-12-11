import math

import pandas as pd
# 在线将json转为xlsx之后，输出为需要导入数据的格式，BatchInsert
# path1 = "test.xlsx"
# path1 = "targetFinal.csv"
# path1 = "../translate/resources/equipRes2.csv"
# path1 = "environment.xlsx"
path1 = "../translate/resources/structureRes.csv"
path2 = "structureImport.csv"
df1 = pd.read_csv(path1)
str_t = ''
for i in range(df1.shape[0]):
    str1 = "values(\""
    for j in range(df1.shape[1]):
        a = str(df1.iloc[i].iat[j])
        if a == 'nan':
            a = ''
        str1 = str1 + a
        if j != df1.shape[1] - 1:
            str1 = str1 + "\",\""
        else:
            str1 = str1 + "\"),\n"
    print(str1)
    str_t = str_t + str1
    str_tt = str_t
    str_tt.replace("\"nan\"", "\"\"")
    with open(path2, "w", encoding='utf-8') as f:
        f.write(str_tt)
        f.close()
    # list.insert(str)
