# 导入pandas
import pandas as pd

# 导入数据
excel_name1 = "E:/研一汇总/弹药毁伤项目/文档/相关文件/2021.10/导入导出/导出/规则目标/Excel/台军飞机挂载.xlsx"
excel_name2 = "E:/研一汇总/弹药毁伤项目/文档/相关文件/2021.10/导入导出/导出/规则目标/Excel/台军飞机挂载exl2.xlsx"
excel = pd.read_excel(excel_name1)

for i in range(1, excel.shape[0]):
    # .shape()用于获取数据形状
    if excel.iloc[i].iat[3] == "挂载方案的作用":
        excel.drop([i], axis=0)  # 删除一行

print(excel)

excel.to_excel(excel_name2, encoding='utf-8', index=0)
