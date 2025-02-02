import tkinter as tk
from PIL import Image, ImageTk
from tkinter.filedialog import askopenfilename
import keyboard
import csv
import random
import os
class Coords:
    def __init__(self, x1, y1, x2, y2):
        self.x1 = x1
        self.y1 = y1
        self.x2 = x2
        self.y2 = y2


class LabelsForImage(tk.Tk):
    def __init__(self):
        tk.Tk.__init__(self)
        self.canvas = tk.Canvas(self, width=512, height=512, cursor="cross")
        self.files = os.listdir("./Meters/")
   
        keyboard.add_hotkey('Ctrl + r', self.new_image)
        keyboard.add_hotkey('Ctrl + q', self.end)
        keyboard.add_hotkey('Ctrl + x', self.next_image)

        self.canvas.bind("<ButtonPress-1>", self.on_button_press)
        self.canvas.bind("<B1-Motion>", self.on_move_press)
        self.canvas.bind("<ButtonRelease-1>", self.on_button_release)
        self.canvas.pack(side="top", fill="both", expand=True)
        self.rect = None
        self.image_id = 0

        self.rects = []
        self.res_good = []
        
        self.id = 0
        self.x1 = None
        self.y1 = None
        self.x2 = None
        self.y2 = None

        self.id_train = 0
        self.id_valid = 0
        self.path_train ="./train/"
        self.path_valid = "./valid/"

        self.train = []
        self.valid = []

        self._draw_image()

    def new_image(self):
        (width, height) = self.im.size
        file = self.filename.replace("C:/Users/Alisher/Desktop/OpenHackCV/CV/Meters/", "n")
        info = {"filename": "n" + file,
            "width":width, "height":height,
            "class":"numbers",
            "xmin" : self.x1, "ymin":self.y1, "xmax":self.x2, "ymax":self.y2, "image_id": file.replace(".png",  "")}
        self.image_id += 1
        if random.random() > 0.2:
            self.train.append(info)
            self.im.save(self.path_train +"n" + file)
        else:
            self.im.save(self.path_valid +"n"+ file)
            self.valid.append(info)
        self._draw_image()
        print('ok')

    def save_csv(self, filename, data):
        columns=[ "filename", "width", "height", "class", "xmin", "ymin", "xmax", "ymax", "image_id"]
        with open(filename, "w", newline="") as file:
            writer = csv.DictWriter(file, fieldnames=columns)
            writer.writeheader()
            writer.writerows(data)
    def next_image(self):
        self._draw_image()

    def end(self):
        self.save_csv("train.csv", self.train)
        self.save_csv("valid.csv", self.valid)
        print("Its Goood!")

    def _draw_image(self):
        self.imrect = None
        self.filename = self.files[self.id]
        self.id += 1
        self.im = Image.open("./Meters/" + self.filename)
        self.im = self.im.resize((400, 400), Image.ANTIALIAS)
        self.tk_im = ImageTk.PhotoImage(self.im)
        self.imrect = self.canvas.create_image(0, 0, anchor="nw", image=self.tk_im)
    
    def _get_filename(self):
        return askopenfilename(defaultextension='.bmp',
                  filetypes=[('All files','*.*'),
                             ('BMP pictures', '*.bmp'),
                             ('PNG pictures','*.png'),
                             ('JPEG pictures','*.jpg')])

    def on_button_press(self, event):
        self.x1 = event.x
        self.y1 = event.y
        if not self.rect:
            self.rect = self.canvas.create_rectangle(self.x1, self.y1, 1, 1, )

    def on_move_press(self, event):
        self.x2, self.y2 = (event.x, event.y)
        self.canvas.coords(self.rect, self.x1, self.y1, self.x2, self.y2)
    
    def on_button_release(self, event):
        pass



if __name__ == "__main__":
    app = LabelsForImage()
    app.mainloop()
