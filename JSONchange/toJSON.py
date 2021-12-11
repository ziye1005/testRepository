import json

import xmltodict
def load_json(xml_path):
    #获取xml文件
    xml_file = open(xml_path, encoding='gb18030', errors='ignore')
    #读取xml文件内容
    xml_str = xml_file.read()
    #将读取的xml内容转为json
    json = xmltodict.parse(xml_str)
    return json
load_json("台军-飞机.xml")
# print(json)
with open("result.txt", "w") as fp:
    fp.write(json.dumps(json))