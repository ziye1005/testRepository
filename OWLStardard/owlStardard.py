path = "resources/hs_13.txt"
path2 = "owlRes.txt"

result = dict()
# owl 文件按照子类父类找出来
with open(path, 'r', encoding='utf-8') as fr:
    flag = 0
    key = ""
    value = ""
    for text in fr.readlines():
        strSplit = text.split()

        if text.split():  # 空行不进入
            if "<owl:Class" in strSplit:
                key = strSplit[len(strSplit) - 1].split("#")[-1].split("\"")[0]
                # flag = 1
            elif "<rdfs:subClassOf" in strSplit:
                value = strSplit[len(strSplit) - 1].split("#")[-1].split("\"")[0]
                # if flag == 1:
                temp = ""
                try:
                    temp = result[key]
                except:
                    pass
                if len(temp) > 0:
                    value = temp + "," + value
                result.update({key: value})

print(result)

with open(path2, 'w', encoding="utf-8") as fd:
    for item in result.items():
        str1 = item[0]
        str2 = item[1]
        fd.write(str1 + "," + str2 + "\r")  # key value在一行
    # fd.write("\r")       # 换行
