from PIL import Image
import pytesseract
import cv2
import os
import numpy as np


def get_grayscale(image): return cv2.cvtColor(image, cv2.COLOR_BGR2GRAY) # noise removal
def remove_noise(image): return cv2.medianBlur(image,5) #thresholding
def thresholding(image): return cv2.threshold(image, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)[1] #dilation
def dilate(image): kernel = np.ones((5,5),np.uint8); return cv2.dilate(image, kernel, iterations = 1) #erosion
def erode(image): kernel = np.ones((5,5),np.uint8); return cv2.erode(image, kernel, iterations = 1) #opening - erosion followed by dilation
def opening(image): kernel = np.ones((5,5),np.uint8); return cv2.morphologyEx(image, cv2.MORPH_OPEN, kernel) #canny edge detection
def canny(image): return cv2.Canny(image, 100, 200) #skew correction
def deskew(image): 
    coords = np.column_stack(np.where(image > 0))
    angle = cv2.minAreaRect(coords)[-1]
    if angle < -45: 
        angle = -(90 + angle) 
    else: 
        angle = -angle 
    (h, w) = image.shape[:2]
    center = (w // 2, h // 2) 
    M = cv2.getRotationMatrix2D(center, angle, 1.0) 
    rotated = cv2.warpAffine(image, M, (w, h), 
        flags=cv2.INTER_CUBIC, borderMode=cv2.BORDER_REPLICATE) 
    return rotated #template matching
def match_template(image, template): return cv2.matchTemplate(image, template, cv2.TM_CCOEFF_NORMED)

def predict(filename):
    image = cv2.imread(filename)
    gray = get_grayscale(image)
    thresh = remove_noise(gray)

    filename = "{}.png".format(os.getpid())
    cv2.imwrite(filename, thresh)

    pytesseract.pytesseract.tesseract_cmd = r'C:\\Program Files\\Tesseract-OCR\\tesseract.exe'
    custom_config = r'--oem 3 --psm 6 outputbase digits -c tessedit_char_whitelist=-0123456789'
    text = pytesseract.image_to_string(Image.open(filename), lang="digits_comma", config=custom_config)
    os.remove(filename)
    return text


import cv2
import numpy as np        
import matplotlib.pyplot as plt
from scipy import ndimage
import math
import imutils


def put_label(t_img,label,x,y):
    font = cv2.FONT_HERSHEY_SIMPLEX
    l_x = int(x) - 10
    l_y = int(y) + 10
    cv2.rectangle(t_img,(l_x,l_y+5),(l_x+35,l_y-35),(0,255,0),-1) 
    cv2.putText(t_img,str(label),(l_x,l_y), font,1.5,(255,0,0),1,cv2.LINE_AA)
    return t_img

def image_refiner(gray):
    org_size = 22
    img_size = 28
    rows,cols = gray.shape
    
    if rows > cols:
        factor = org_size/rows
        rows = org_size
        cols = int(round(cols*factor))        
    else:
        factor = org_size/cols
        cols = org_size
        rows = int(round(rows*factor))
    gray = cv2.resize(gray, (cols, rows))

    colsPadding = (int(math.ceil((img_size-cols)/2.0)),int(math.floor((img_size-cols)/2.0)))
    rowsPadding = (int(math.ceil((img_size-rows)/2.0)),int(math.floor((img_size-rows)/2.0)))
    gray = np.lib.pad(gray,(rowsPadding,colsPadding),'constant')
    return gray

def sort_contours(cnts, method="left-to-right"):
    # initialize the reverse flag and sort index
    reverse = False
    i = 0

    # handle if we need to sort in reverse
    if method == "right-to-left" or method == "bottom-to-top":
        reverse = True

    # handle if we are sorting against the y-coordinate rather than
    # the x-coordinate of the bounding box
    if method == "top-to-bottom" or method == "bottom-to-top":
        i = 1

    # construct the list of bounding boxes and sort them from top to
    # bottom
    boundingBoxes = [cv2.boundingRect(c) for c in cnts]
    (cnts, boundingBoxes) = zip(*sorted(zip(cnts, boundingBoxes),
        key=lambda b:b[1][i], reverse=reverse))

    # return the list of sorted contours and bounding boxes
    return (cnts, boundingBoxes)

def get_output_image(path):
    img = cv2.imread(path,2)
    img_org =  cv2.imread(path)

    ret,thresh = cv2.threshold(img,127,255,0)
    contours,hierarchy = cv2.findContours(thresh, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_SIMPLE)
    res = []
    cnt_dict = dict()
    for j,cnt in enumerate(contours):
        epsilon = 0.01*cv2.arcLength(cnt,True)
        approx = cv2.approxPolyDP(cnt,epsilon,True)
        hull = cv2.convexHull(cnt)
        k = cv2.isContourConvex(cnt)
        x,y,w,h = cv2.boundingRect(cnt)
        if(hierarchy[0][j][3]!=-1 and w>10 and h>10):
            cv2.rectangle(img_org,(x,y),(x+w,y+h),(0,255,0),2)
            roi = img[y:y+h, x:x+w]
            roi = cv2.bitwise_not(roi)
            #th,fnl = cv2.threshold(roi,127,255,cv2.THRESH_BINARY)
            cv2.imwrite('temp.png', roi)
            pred = predict('temp.png')[0]
            res.append(pred)
            (x,y),radius = cv2.minEnclosingCircle(cnt)

    return img_org, res
