import random
import hashlib
import time
import urllib.parse
import json
import http.client


def baidu_translation(content):
    appid = '20211202001016623'  # 填写你的appid
    secretKey = 'Pd1oEPhK1_GvhteQ5mlA'  # 填写你的密钥
    httpClient = None
    myurl = '/api/trans/vip/translate'
    q = content
    fromLang = 'en'
    toLang = 'zh'
    salt = random.randint(32768, 65536)
    sign = appid + q + str(salt) + secretKey
    sign = hashlib.md5(sign.encode()).hexdigest()
    myurl = myurl + '?appid=' + appid + '&q=' + urllib.parse.quote(
        q) + '&from=' + fromLang + '&to=' + toLang + '&salt=' + str(salt) + '&sign=' + sign

    try:
        httpClient = http.client.HTTPConnection('api.fanyi.baidu.com')
        httpClient.request('GET', myurl)

        # response是HTTPResponse对象
        response = httpClient.getresponse()
        jsonResponse = response.read().decode("utf-8")  # 获得返回的结果，结果为json格式
        js = json.loads(jsonResponse)  # 将json格式的结果转换字典结构
        # print(js)
        dst = str(js["trans_result"][0]["dst"])  # 取得翻译后的文本结果
        return (dst)  # 打印结果
    except Exception as e:
        print(e)
    finally:
        if httpClient:
            httpClient.close()


# print(baidu_translation('My name is John.'))
input_list = []
input_str = ' '
input_path = "resources/readBaidu.txt"
output_path = "resources/translateBaidu.txt"
with open(input_path, 'r', encoding='utf-8') as f:
    # for line in f:
    #     input_list.append(line)
    input_list = f.read().splitlines()
    input_str = input_str.join(input_list)   #list拼成string
    # input_list = input_list + str1
print(input_str)
f.close()

with open(output_path, 'w', encoding='utf-8') as f:
    # for line in input_list:
    #     temp = baidu_translation(line)
    #     time.sleep(1)   # 需要等待
    #     print(line, temp)
    #     f.write(temp + '\n')
    temp = baidu_translation(input_str)
    f.write(temp)
f.close()
