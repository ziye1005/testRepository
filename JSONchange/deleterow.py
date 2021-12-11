# 导入pandas
import pandas as pd

# 导入数据
excel_name1 = 'E:/研一汇总/弹药毁伤项目/文档/相关文件/2021.10/导入导出/export台军水面舰艇.xls'
excel_name2 = 'E:/研一汇总/弹药毁伤项目/文档/相关文件/2021.10/导入导出/export台军水面舰艇2.xls'
excel = pd.read_excel(excel_name1)

for i in range(excel.shape[0]):
    # .shape()用于获取数据形状
    if i % 2 == 1:  # 删除奇数行，从0开始的
        excel = excel.drop([i], axis=0)  # .drop()方法可以用于删除行或者列

print(excel)

excel.to_excel(excel_name2, encoding='utf-8', index=0)
