from sys import argv
import json
import random
image = argv[1]
data = {}


data['value'] = str(random.randrange(1000000))
data['serialNumber'] = str(random.randrange(1000000))
data['photoPath'] = str(image)
print(json.dumps(data))
exit