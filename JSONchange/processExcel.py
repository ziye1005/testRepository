import csv
import pandas as pd
path = "E:/研一汇总/弹药毁伤项目/文档/相关文件/2021.10/导入导出/导出/规则目标/台军飞机挂载.csv"
path2 = "E:/研一汇总/弹药毁伤项目/文档/相关文件/2021.10/导入导出/导出/规则目标/台军飞机挂载out.csv"

# with open(path, 'r', encoding="UTF-8") as f:
#     reader = csv.reader(f)
#     data = list(reader)
#     print(data)
#
# for i in range(1, len(data)):
#     # for j in range(0, len(data[0])):
#     #     if data[i][j] =="" or "":
#     if data[i][3] == "挂载方案的作用":
#         data.drop([i])
# print(data)
#
# with open("path2", 'w', encoding="UTF-8", newline='') as csvFile:
#     writer = csv.writer(csvFile)
#     for row in data:
#         writer.writerow(row)


data = pd.read_csv(path)
for i in range(1, data.shape[0]):
    # for j in range(0,data.shape[1]):
    if data.iloc[i].iat[3] == "挂载方案的作用":
        data.drop([i], axis=0)  # 删除一行
print(data)
data.to_csv(path2, index=False, sep=',')

