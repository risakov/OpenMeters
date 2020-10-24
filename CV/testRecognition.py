
import torchvision.transforms as transforms
from DigitRecognition import recognition
from   PIL import Image
import torch
import cv2

filename = "C:\\Users\\Alisher\\Desktop\\CVMeters\\Numbers\\6.bmp"

image = cv2.imread(filename)
print(recognition(image))

cv2.waitKey(0)