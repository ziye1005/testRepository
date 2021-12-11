import json
import urllib
import urllib.request
# import Py4Js

import pandas as pd


def youdao_translate(content):
    '''实现有道翻译的接口'''
    youdao_url = 'http://fanyi.youdao.com/translate?smartresult=dict&smartresult=rule'
    data = {}

    data['i'] = content
    data['from'] = 'AUTO'
    data['to'] = 'AUTO'
    data['smartresult'] = 'dict'
    data['client'] = 'fanyideskweb'
    data['salt'] = '1525141473246'
    data['sign'] = '47ee728a4465ef98ac06510bf67f3023'
    data['doctype'] = 'json'
    data['version'] = '2.1'
    data['keyfrom'] = 'fanyi.web'
    data['action'] = 'FY_BY_CLICKBUTTION'
    data['typoResult'] = 'false'
    data = urllib.parse.urlencode(data).encode('utf-8')

    youdao_response = urllib.request.urlopen(youdao_url, data)
    youdao_html = youdao_response.read().decode('utf-8')
    target = json.loads(youdao_html)

    trans = target['translateResult']
    ret = ''
    for i in range(len(trans)):
        line = ''
        for j in range(len(trans[i])):
            line = trans[i][j]['tgt']
        ret += line + '\n'

    return ret


def read_txt(path):
    """实现TXT文档的读取，一次将内容全部取出"""
    content = ''
    with open(path, encoding='utf-8') as f:
        content = f.read()
    # print(content)
    return content


def write_txt(path, content):
    """实现TXT文档的写方法"""
    print(content)
    with open(path, 'w') as f:
        f.write(content)


def baidu_translate(content, type=1):
    '''实现百度翻译'''
    baidu_url = 'http://fanyi.baidu.com/basetrans'
    data = {}

    data['from'] = 'en'
    data['to'] = 'zh'
    data['query'] = content
    data['transtype'] = 'translang'
    data['simple_means_flag'] = '3'
    data['sign'] = '94582.365127'
    data['token'] = 'ec980ef090b173ebdff2eea5ffd9a778'
    data = urllib.parse.urlencode(data).encode('utf-8')

    headers = {
        "User-Agent": "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Mobile Safari/537.36"}
    baidu_re = urllib.request.Request(baidu_url, data, headers)
    baidu_response = urllib.request.urlopen(baidu_re)
    baidu_html = baidu_response.read().decode('utf-8')
    target2 = json.loads(baidu_html)

    trans = target2['trans']
    ret = ''
    for i in range(len(trans)):
        ret += trans[i]['dst'] + '\n'

    return ret


# def google_translate(content):
#     '''实现谷歌的翻译'''
#     js = Py4Js()
#     tk = js.getTk(content)
#
#     if len(content) > 4891:
#         print("翻译的长度超过限制！！！")
#         return
#
#     param = {'tk': tk, 'q': content}
#
#     result = requests.get("""http://translate.google.cn/translate_a/single?client=t&sl=en
#         &tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss
#         &dt=t&ie=UTF-8&oe=UTF-8&clearbtn=1&otf=1&pc=1&srcrom=0&ssel=0&tsel=0&kc=2""", params=param)
#
#     # 返回的结果为Json，解析为一个嵌套列表
#     trans = result.json()[0]
#     ret = ''
#     for i in range(len(trans)):
#         line = trans[i][0]
#         if line != None:
#             ret += trans[i][0]
#
#     return ret


if __name__ == '__main__':
    path1 = "read.txt"
    path2 = "translateYoudao.txt"
    path3 = "read.csv"
    path4 = "translateBaidu.txt"
    content = read_txt(path1)
    content2 = youdao_translate(content)
    # content3 = baidu_translate(content)
    write_txt(path2, content2)
    # write_txt(path4, content3)
    # dataCsv = pd.read_csv(path3, sep=',', header='infer', encoding='utf-8')
