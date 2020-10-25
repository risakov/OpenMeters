from imutils import contours
import numpy as np
import argparse
import imutils
import cv2
import operator
import os
from PIL import Image
import pytesseract
from keras.preprocessing.image import ImageDataGenerator
from keras.models import Sequential
from keras.layers import Convolution2D, MaxPooling2D
from keras.layers import Flatten, Dense
import sys
import pandas as pd
import datetime
import time
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.base import MIMEBase
from email import encoders
################################################
image = cv2.imread('16.png')
image111=cv2.resize(image,(300,400))
image = imutils.resize(image, width=300)
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# apply a tophat (whitehat) morphological operator to find light
# regions against a dark background (i.e., the credit card numbers)
rectKernel = cv2.getStructuringElement(cv2.MORPH_RECT, (9, 3))
sqKernel = cv2.getStructuringElement(cv2.MORPH_RECT, (5, 5))
tophat = cv2.morphologyEx(gray, cv2.MORPH_TOPHAT, rectKernel)

# compute the Scharr gradient of the tophat image, then scale
# the rest back into the range [0, 255]
gradX = cv2.Sobel(tophat, ddepth=cv2.CV_32F, dx=1, dy=0,
	ksize=-1)
gradX = np.absolute(gradX)
(minVal, maxVal) = (np.min(gradX), np.max(gradX))
gradX = (255 * ((gradX - minVal) / (maxVal - minVal)))
gradX = gradX.astype("uint8")

# apply a closing operation using the rectangular kernel to help
# cloes gaps in between digits, then apply
# Otsu's thresholding method to binarize the image
gradX = cv2.morphologyEx(gradX, cv2.MORPH_CLOSE, rectKernel)
cv2.imwrite('closing.jpeg',gradX)
thresh = cv2.threshold(gradX, 0, 255,
	cv2.THRESH_BINARY | cv2.THRESH_OTSU)[1]

# apply a second closing operation to the binary image, again
# to help close gaps between number 
thresh = cv2.morphologyEx(thresh, cv2.MORPH_CLOSE, sqKernel)
kernel = np.ones((5,5),np.uint8)
opening = cv2.morphologyEx(thresh, cv2.MORPH_OPEN, kernel)

thresh = cv2.morphologyEx(opening, cv2.MORPH_CLOSE, rectKernel)
cv2.imwrite('finalclosing.jpeg',thresh)

#Finding contours
cnts=cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL,
	cv2.CHAIN_APPROX_SIMPLE)
cnts = cnts[0] if imutils.is_cv2() else cnts[1]
locs = []

# loop over the contours
for (i, c) in enumerate(cnts):
    # compute the bounding box of the contour, then use the
    # bounding box coordinates to derive the aspect ratio
    (x, y, w, h) = cv2.boundingRect(c)
    ar = w / float(h)
    if ar >4 and ar < 8:
        # append the bounding box region of the digits group
	# to our locations list
        if (w >50 and w < 100) and (h > 15 and h < 30):
            # append the bounding box region of the digits group
	    # to our locations list
            locs.append((x, y, w, h))
            #print(w,h,ar)

#drawing rectangle around the detected region            
for (i,(x,y,w,h)) in enumerate(locs):
        cv2.rectangle(image, (x - 5, y - 5),(x+w + 5,y + h + 5), (0, 0, 255), 2)

#cv2.imshow('check',image)
cv2.imwrite('detected.jpeg',image)