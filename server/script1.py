from sys import argv
import json
image = argv[1]
data = {}


data['value'] = '123'
data['serialNumber'] = '1231'
data['photoPath'] = str(image)
print(json.dumps(data))
exit