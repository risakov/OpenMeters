import sys
import cv2 as cv
import numpy as np
from tkinter.filedialog import askopenfilename
import imutils
from imutils import contours

cascade = cv.CascadeClassifier("C:\\Users\\Alisher\\Desktop\\OpenHackCV\\CV\\Cascades\\cascade.xml")
def get_filename():
    return askopenfilename(defaultextension='.bmp',
            filetypes=[('All files','*.*'),
                    ('BMP pictures', '*.bmp'),
                    ('PNG pictures','*.png'),
                    ('JPEG pictures','*.jpg')])
for i in range(10):
    img = cv.imread(get_filename())
    numbers = cascade.detectMultiScale(img, 1.2, 5)

    for (x,y,w,h) in numbers:
        plates_rec = cv.rectangle(img, (x,y), (x+w, y+h), (0,255,0), 1)        


    cv.imshow('img', img)
    cv.imwrite(str(i)+'.bmp', img)
    cv.waitKey(0)

'''
def detect_numbers(img):
    numbers = img
    rect = cascade.detectMultiScale(numbers,scaleFactor=1.2,minNeighbors=5)
    for (x,y,w,h) in rect:
        cv.rectangle(numbers,(x,y),(x+w,y+h),(0,255,0),10)
    return numbers

img = cv.imread(get_filename())
number = detect_numbers(img)

cv.imwrite("good.bmp", number)
'''