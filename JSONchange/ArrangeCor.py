# 实现列合并
from xlutils.copy import copy
import xlrd
import csv

pathExportExcel = "E:/研一汇总/弹药毁伤项目/文档/相关文件/2021.10/导入导出/RC-135型飞机.xls"
pathExportCSV = "E:/研一汇总/弹药毁伤项目/文档/相关文件/2021.10/导入导出/RC-135型飞机.csv"


def write_csv(path, data_row):
    with open(path, 'a+') as f:
        csv_write = csv.writer(f)
        csv_write.writerow(data_row)


# 追加的方法往excel里面写
def write_excel_xls_append(path, value):
    index = len(value)  # 获取需要写入数据的行数
    workbook = xlrd.open_workbook(path)  # 打开工作簿
    sheets = workbook.sheet_names()  # 获取工作簿中的所有表格
    worksheet = workbook.sheet_by_name(sheets[0])  # 获取工作簿中所有表格中的的第一个表格
    rows_old = worksheet.nrows  # 获取表格中已存在的数据的行数
    new_workbook = copy(workbook)  # 将xlrd对象拷贝转化为xlwt对象
    new_worksheet = new_workbook.get_sheet(0)  # 获取转化后工作簿中的第一个表格
    for i in range(0, index):
        for j in range(0, len(value[i])):
            new_worksheet.write(i + rows_old, j, value[i][j])  # 追加写入数据，注意是从i+rows_old行开始写入
    new_workbook.save(path)  # 保存工作簿
    print("xls格式表格【追加】写入数据成功！")


pathData = "E:/研一汇总/弹药毁伤项目/文档/相关文件/2021.10/导入导出/data.xls"

book = xlrd.open_workbook(pathData)
sheet1 = book.sheets()[0]
nrows = sheet1.nrows
ncols = sheet1.ncols

col1_values = sheet1.col_values(0)
col2_values = sheet1.col_values(1)
col3_values = sheet1.col_values(2)
col4_values = sheet1.col_values(3)

for i in range(len(col1_values)):
    col3_values.append(col1_values[i])  # 属性
    col4_values.append(col2_values[i])  # 名称
print(col3_values)
print(col4_values)

list = []

list.append(col3_values)
list.append(col4_values)

# write_excel_xls_append(pathExportExcel, list)
write_csv(pathExportCSV, list)


# list转dataframe
# df = pd.DataFrame(list)

# # 保存到本地excel
# df.to_excel("E:/研一汇总/弹药毁伤项目/文档/相关文件/2021.10/导入导出/exportExcel.xls", index=False, startrow=20)

# # json转化
# bJson = json.dumps(list, ensure_ascii=False)
# with open("E:/研一汇总/弹药毁伤项目/文档/相关文件/2021.10/导入导出/demo.json", "w") as f:
#     json.dump(bJson, f)
