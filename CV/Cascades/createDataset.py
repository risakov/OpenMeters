import tkinter as tk
from PIL import Image, ImageTk
from tkinter.filedialog import askopenfilename
import keyboard

class Coords:
    def __init__(self, x1, y1, x2, y2):
        self.x1 = x1
        self.y1 = y1
        self.x2 = x2
        self.y2 = y2


class ExampleApp(tk.Tk):
    def __init__(self):
        tk.Tk.__init__(self)
        self.canvas = tk.Canvas(self, width=512, height=512, cursor="cross")
   
        keyboard.add_hotkey('Ctrl + s', self.new_rect)
        keyboard.add_hotkey('Ctrl + r', self.new_image)
        keyboard.add_hotkey('Ctrl + q', self.end)

        self.canvas.bind("<ButtonPress-1>", self.on_button_press)
        self.canvas.bind("<B1-Motion>", self.on_move_press)
        self.canvas.bind("<ButtonRelease-1>", self.on_button_release)
        self.canvas.pack(side="top", fill="both", expand=True)
        self.rect = None

        self.rects = []
        self.res_good = []
        
        self.id = 0
        self.x1 = None
        self.y1 = None
        self.x2 = None
        self.y2 = None

        self._draw_image()
    

    def new_rect(self):
        p = Coords(self.x1, self.y1, self.x2, self.y2)
        cropped = self.im.crop((p.x1, p.y1, p.x2, p.y2))
        filename = './Numbers/' + str(len(self.rects)) + '.bmp'
        cropped.save(filename)
        self.rects.append(p)
        print(filename)

    def new_image(self):
        filename = "Good/" + str(self.id) + ".bmp  " + str(len(self.rects)) + "  "
        for p in self.rects:
            filename += str(p.x1) + " " + str(p.y1) + " " + str(p.x2) + " " + str(p.y2) + ' '
        self.res_good.append(filename)
        self.id += 1
        self._draw_image()
        print(filename)

    def end(self):
        f = open('./Numbers/Good.dat','w')
        f.writelines(self.res_good)
        f.close()
        print("Its Goood!")

    def _draw_image(self):
        self.imrect = None
        filename = self._get_filename()
        self.im = Image.open(filename)
        self.tk_im = ImageTk.PhotoImage(self.im)
        self.imrect = self.canvas.create_image(0, 0, anchor="nw", image=self.tk_im)
    
    def _get_filename(self):
        return askopenfilename(defaultextension='.bmp',
                  filetypes=[('All files','*.*'),
                             ('BMP pictures', '*.bmp'),
                             ('PNG pictures','*.png'),
                             ('JPEG pictures','*.jpg')])

    def on_button_press(self, event):
        # save mouse drag start position
        self.x1 = event.x
        self.y1 = event.y

        #one rectangle
        if not self.rect:
            self.rect = self.canvas.create_rectangle(self.x1, self.y1, 1, 1, )

    def on_move_press(self, event):
        self.x2, self.y2 = (event.x, event.y)

        # expand rectangle as you drag the mouse
        self.canvas.coords(self.rect, self.x1, self.y1, self.x2, self.y2)


    def on_button_release(self, event):
        pass


if __name__ == "__main__":
    app = ExampleApp()
    app.mainloop()

'''
import Tkinter as tk
from PIL import Image, ImageTk


class ExampleApp(tk.Tk):
    def __init__(self):
        tk.Tk.__init__(self)
        self.x = self.y = 0
        self.canvas = tk.Canvas(self, width=512, height=512, cursor="cross")
        self.canvas.pack(side="top", fill="both", expand=True)
        self.canvas.bind("<ButtonPress-1>", self.on_button_press)
        self.canvas.bind("<B1-Motion>", self.on_move_press)
        self.canvas.bind("<ButtonRelease-1>", self.on_button_release)

        self.rect = None

        self.start_x = None
        self.start_y = None

        self._draw_image()

    def _draw_image(self):
        self.im = Image.open('1.jpg')
        self.tk_im = ImageTk.PhotoImage(self.im)
        self.canvas.create_image(0, 0, anchor="nw", image=self.tk_im)

    def on_button_press(self, event):
        # save mouse drag start position
        self.start_x = event.x
        self.start_y = event.y

        #one rectangle
        if not self.rect:
            self.rect = self.canvas.create_rectangle(self.x, self.y, 1, 1, )

    def on_move_press(self, event):
        curX, curY = (event.x, event.y)

        # expand rectangle as you drag the mouse
        self.canvas.coords(self.rect, self.start_x, self.start_y, curX, curY)

    def on_button_release(self, event):
        pass


if __name__ == "__main__":
    app = ExampleApp()
    app.mainloop()
'''