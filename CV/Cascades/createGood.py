import os
from PIL import Image
 
dir_name = "C:\\Users\\Alisher\\Desktop\\CVMeters\Good\\"
d = "Good\\"
images = os.listdir(dir_name)

f = open('Good.dat','w')
print(images)

i = 0
for _id in range(len(images)):
    full_path = dir_name + images[_id]
    im = Image.open(full_path)
    (width, height) = im.size
    info = d+images[_id] + "  1  0 0 " + str(width) + " " + str(height) +'\n'
    print(info)
    f.write(info)
f.close() 
