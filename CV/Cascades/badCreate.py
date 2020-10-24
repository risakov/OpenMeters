import os
from PIL import Image

d = "Bad\\"

f = open('Bad.dat','w')
files = []
i = 0
for _id in range(473):
    files.append(d+str(_id)+".bmp\n")
f.writelines(files)
f.close() 
