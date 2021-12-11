import datetime
import time
import random

import pandas
from pandas import DataFrame

# 27所excel表格产生随机数的数据
# 产生随机字符串
def ranstr(num):
    H = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'

    salt = ''
    for i in range(num):
        salt += random.choice(H)

    return salt


# 产生随机数日期
def random_data():
    end_time = datetime.datetime.now()
    start_time = datetime.datetime.now() + datetime.timedelta(days=-7)  # 当前时间减去3分钟

    a1 = tuple(start_time.timetuple()[0:9])  # 设置开始日期时间元组（2020-04-11 16:30:21）
    a2 = tuple(end_time.timetuple()[0:9])  # 设置结束日期时间元组（2020-04-11 16:33:21）

    start = time.mktime(a1)  # 生成开始时间戳
    end = time.mktime(a2)  # 生成结束时间戳

    # 随机生成日期字符串
    for i in range(random.randint(1, 10)):
        t = random.randint(start, end)  # 在开始和结束时间戳中随机取出一个
        date_touple = time.localtime(t)  # 将时间戳生成时间元组
        date = time.strftime("%Y-%m-%d %H:%M:%S", date_touple)  # 将时间元组转成格式化字符串（2020-04-11 16:33:21）
        # print(date)
    return date


path_input = "resources/test.xlsx"
path_output = "resources/output_random_data.csv"
data = pandas.read_excel(path_input)
data = data.dropna(subset=["字段中文名称"], axis=0)
data['数据类型'] = data['数据类型'].str.upper()
data['字段英文名称'] = data['字段英文名称'].str.upper()
list_tt = []
header = []
for i in range(data.shape[0]):
    header.append(str(data.iloc[i].at['字段英文名称']))
print(header)
for gene_round in range(15):
    list_t = []
    str1 = ""
    for i in range(data.shape[0]):
        len1 = data.shape[0]
        a = str(data.iloc[i].at['字段中文名称'])
        c = str(data.iloc[i].at['数据类型'])
        if "VARCHAR" in c:
            str1 = ranstr(4)  # 产生10位随机字符串
            list_t.append(str1)
            # print(i, "Varchar2:" + str1)
        elif "NUMBER" in c:  # 产生数字，小数默认是两位小数，10-20'
            if c.strip() == "NUMBER":
                str1 = random.randint(1, 20)
            else:
                temp = c.split("(")[1].split(")")[0]
                ttflag = temp.isdigit()
                if temp.isdigit():
                        str1 = random.randint(1, 10 * (int(temp)) - 1)
                else:
                    str1 = round(random.uniform(10, 20), 2)
            list_t.append(str1)
            # print(i, "Number:", str1)
        elif "DATE" in c:
            str1 = random_data()
            list_t.append(str1)
    print(list_t)
    list_tt.append(list_t)
print(list_tt)
data_out = DataFrame(list_tt)
# print(data_out)
data_out.to_csv(path_output, sep=',', encoding="gbk", index=None, header=header)
