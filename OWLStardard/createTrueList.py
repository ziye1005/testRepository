path_input = "owlRes.txt"
path_output = "owl_tree.txt"

result1 = dict()
with open(path_input, 'r', encoding='utf-8') as fr:
    for text in fr.readlines():
        str1 = text.strip().split(",", 2)
        result1.update({str1[0]: str1[1]})
for key 