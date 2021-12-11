import os.path

import pandas as pd
import numpy as np
# 将师妹整理的数据与原数据表合并
path1 = "E:\研一汇总\弹药毁伤项目\文档\本体构建\关系表\word关系表\总体关系表\数据导入\merge0.xlsx"
path2 = "E:\研一汇总\弹药毁伤项目\文档\本体构建\关系表\word关系表\总体关系表\数据导入\弹药参数表数据导入.xlsx"
path3 = "E:\研一汇总\弹药毁伤项目\文档\本体构建\关系表\word关系表\总体关系表\数据导入"
# ['地雷参数表', '子母弹参数表', '导弹参数表', '枪射弹药参数表', '榴弹参数表', '火箭弹参数表', '灵巧弹药参数表',
# '破甲弹参数表', '碎甲弹参数表', '穿甲弹参数表', '航空炸弹参数表', '迫击炮弹参数表', '预制破片弹参数表', '特种弹药参数表', '其他弹药参数表']
sheet_name = "航空炸弹参数表"
path3 = path3+"\\"+sheet_name+'.xlsx'
print(path3)
df1 = pd.read_excel(path1)  # 需要合并到这个列名
df2 = pd.read_excel(path2, sheet_name=sheet_name)
head1 = list(df1)
head2 = list(df2)

for i in head2:
    if i in head1:
        df1.loc[:, i] = df2.loc[:, i]
    elif i =="弹长":
        df1.loc[:, "弹长(弹丸长度)"] = df2.loc[:, i]
    elif i =="全弹长度":
        # 全弹长度(全弹长)
        df1.loc[:, "全弹长度(全弹长)"] = df2.loc[:, i]
    else:
        df1.insert(df1.shape[1], i, df2.loc[:, i])
for i in range(df1.shape[0]):
    for j in range(df1.shape[1]):
        df1.iloc[i, j] = str(df1.iloc[i, j]).replace('~', '-')
# df1.fillna('0', inplace=True)
df1[df1 == 'nan'] = ''
# writer = pd.ExcelWriter(path3)
with pd.ExcelWriter(path3) as writer:
    for name, data in df1.items():
        df1.to_excel(writer, sheet_name=sheet_name)
# df1.to_excel(writer, sheet_name=sheet_name, encoding='utf-8')
# a = df2.columns


# # data3 = pd.concat([data1, data2], axis=1)
# data3 = pd.concat([data1, data2], axis=1, join='outer')
# # data3 = pd.merge(data1, data2)
# data3.to_excel(path3, encoding='utf-8', index=0)
# excel.to_excel(excel_name2, encoding='utf-8', index=0)
