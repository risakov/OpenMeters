from sys import argv
import json
#from DetectDigits import get_output_image
import cv2
import os
import ocr

from detecto.core import Model
from detecto.utils import read_image

model = Model.load('localization_model.pth', ['digits'])

path = argv[1]
image_name = argv[2]
filename = path +'\\'+ image_name
data = {}

#cascade = cv2.CascadeClassifier(path + "\\Cascades\\cascade.xml")
#img = cv2.imread(filename)
img = read_image(filename)
top_preds = model.predict_top(img)
res = top_preds[1].numpy()[0]
#numbers = cascade.detectMultiScale(img, 1.1, 3)

x, y = int(res[0]), int(res[1])
w = int(res[2])
h = int(res[3])
w = w - x
h = h - y
img = img[y:y+h, x:x+w]
temp_path = "res.png"
cv2.imwrite(temp_path, img)

good_list = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
image, res = ocr.get_output_image(temp_path)
for i in range(len(res)):
    if res[i] not in good_list:
        res[i] = '0'
    data[str(i)] = str(res[i])

full_data = {"serialNumber" : "", "value" : data}
print(json.dumps(full_data))
os.remove(temp_path)
exit