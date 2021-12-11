
import json
filepath = 'dataJson'
str_t = ""
for i in range(1, 4):
    path = filepath + "\\" + str(i) + ".json"
    json_file = open(path, "r", encoding='utf-8')
    item_list = json.load(json_file)
    str1 = str(item_list)
    str1 = str1 + ",\n"
    str_t = str_t + str1
print(str_t)
