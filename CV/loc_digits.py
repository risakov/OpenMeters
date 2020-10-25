from detecto.core import Model
from detecto.utils import read_image
import ocr
import cv2

model = Model.load('localization_model.pth', ['digits'])
image = read_image('16.png')
top_preds = model.predict_top(image)
res = top_preds[1].numpy()[0]

x1, y1 = int(res[0]), int(res[1])
w, h = int(res[2]), int(res[3])
w -= x1
h -= y1
crop = image[y1:y1+h, x1:x1+w]
cv2.rectangle(image, (x1,y1), (x1+w,y1+h), (0,255,0), 5)
cv2.imshow("image", image)
cv2.imshow("crop", crop)
cv2.imwrite('im.png', crop)
im, res = ocr.get_output_image('im.png')
cv2.imshow('ocr', im)
print(res)

cv2.waitKey(0)