import sys
import cv2 as cv
import numpy as np
from tkinter.filedialog import askopenfilename
import imutils
from imutils import contours

cascade = cv.CascadeClassifier("cascade.xml")

gray = cv.cvtColor(img, 0)
cv.waitKey(0)
numbers = cascade.detectMultiScale(gray, 1.05, 4)

for (x,y,w,h) in numbers:
    plates_rec = cv.rectangle(img, (x,y), (x+w, y+h), (0,255,0), 1)        

    gray_plates = gray[y:y+h, x:x+w]
    color_plates = img[y:y+h, x:x+w]

    height, width, chanel = gray_plates.shape
    print(height, width)

cv.imshow('img', img)
cv.imwrite("good.bmp", img)
cv.waitKey(0)

def detect_numbers(img):
    numbers = img
    rect = cascade.detectMultiScale(numbers,scaleFactor=1.2,minNeighbors=5)
    for (x,y,w,h) in rect:
        cv.rectangle(numbers,(x,y),(x+w,y+h),(0,255,0),10)
    return numbers

img = cv.imread(get_filename())
number = detect_numbers(img)

cv.imwrite("good.bmp", number)
