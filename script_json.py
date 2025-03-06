import json
from pathlib import Path

cnt = 0
file_list = []
#output_file = open("out.txt", 'w');

def json_reader(path, outfile):
    f = open(path, 'r')
    data = json.load(f)
    print(data['textures'])
    f.close()
    return data

for path in Path('./src').rglob('*.json'):
    print('SEARCHING FOR FILES...')
    print(f'JSON_READER: {path.name}')
    file_list.append(path)
    cnt += 1

print(f'Number of file found: [{cnt}]')
print(file_list)
for p in file_list:
    json_reader(p, 'out.txt')