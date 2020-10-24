from sys import argv
import json
from DetectDigits import get_output_image
import cv2
import os

path = argv[1]
image_name = argv[2]
filename = path +'\\'+ image_name
data = {}
cascade = cv2.CascadeClassifier(path + "\\Cascades\\cascade.xml")
img = cv2.imread(filename)
img = cv2.cvtColor(img, cv2.IMREAD_GRAYSCALE)
numbers = cascade.detectMultiScale(img, 1.1, 3)
(x,y,w,h) = numbers[0]
img = img[y:y+h, x:x+w]
temp_path = "res.png"
cv2.imwrite(temp_path, img)
image, res = get_output_image(temp_path, path+'\\digit_classifier.h5')
for i in range(len(res)):
    data[i] = str(res[i])

cv2.imwrite(argv[1] + '\\rec_'+  argv[2], image)
cv2.imwrite(argv[1] + '\\loc_'+  argv[2], cv2.rectangle(cv2.imread(filename), (x,y), (x+w, y+h), (0,255,0), 5))
print(json.dumps(data))
#os.remove(temp_path)
exit