import pandas as pd

# 根据已有的子类父类名称填充父类的id和英文名称
path = "resources/structure.csv"
path_out = "resources/structureRes.csv"
data = pd.read_csv(path, sep=",", encoding="utf-8")
print(data.head())
mapCsv = dict()
mapLoss = dict()
print(data.shape)
data.drop_duplicates(subset="材料类别名称", keep='first', inplace=True)
# 根据类别名称去重
print(data.shape)
for i in range(data.shape[0]):
    temp = data.iloc[i].iat[4]
    try:
        temp = str(temp)
    except:
        pass
    if temp in mapCsv.keys():
        a = mapCsv[temp][0]
        b = mapCsv[temp][1]
        data.iloc[i, 3] = a
        data.iloc[i, 5] = b
    else:
        rowIndex = 0
        flag = 0
        for j in range(data.shape[0]):
            if data.iloc[j].iat[1] == temp:
                rowIndex = j
                flag = 1
                break
        if flag == 1:
            mapCsv.update({data.iloc[rowIndex].iat[1]: [data.iloc[rowIndex].iat[0], data.iloc[rowIndex].iat[2]]})
            temp2 = data.iloc[rowIndex].iat[1]
            data.iloc[i, 3] = mapCsv[temp2][0]
            data.iloc[i, 5] = mapCsv[temp2][1]
        else:
            mapLoss.update({temp: i})
            # print(i, temp)
for ii in range(5):
    for jj in range(data.shape[1]):
        print(data.iloc[ii].iat[jj], end=",")
    print()
print(mapCsv)
print(mapLoss)
data.to_csv(path_out, encoding="utf-8", sep=",",index=None)
