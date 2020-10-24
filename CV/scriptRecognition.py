from sys import argv
import json
from DetectDigits import get_output_image
import cv2
import os

filename = argv[1]
data = {}
cascade = cv2.CascadeClassifier(".\\Cascades\\cascade.xml")
img = cv2.imread(filename)
img = cv2.cvtColor(img, cv2.IMREAD_GRAYSCALE)
numbers = cascade.detectMultiScale(img, 1.1, 3)
(x,y,w,h) = numbers[0]
img = img[y:y+h, x:x+w]
temp_path = "res.png"
cv2.imwrite(temp_path, img)
image, res = get_output_image(temp_path)
for i in range(len(res)):
    data[i] = str(res[i])

print(json.dumps(data))
os.remove(temp_path)
exit