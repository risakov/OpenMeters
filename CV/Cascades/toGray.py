import os
import cv2 as cv
dir_name = "C:\\Users\\Alisher\\Desktop\\CVMeters\Bad\\"
images = os.listdir(dir_name)
dir_res = "C:\\Users\\Alisher\\Desktop\\CVMeters\\bd\\"
for image in images:
    img = cv.imread(dir_name + image)
    gray = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
    cv.imwrite(dir_res + image, gray)
