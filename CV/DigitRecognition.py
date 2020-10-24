import torch.nn as nn
import torch
import torchvision.transforms as transforms
from   PIL import Image
import cv2

def calc_out(in_layers, stride, padding, kernel_size, pool_stride):
    return int((1+(in_layers - kernel_size + (2*padding))/stride)/pool_stride)

class Net(nn.Module):
    def __init__(self):
        super(Net, self).__init__()

        inputs      = [1,32,64,64]
        kernel_size = [5,5,3]
        stride      = [1,1,1]
        pool_stride = [2,2,2]
        layers = []

        self.out   = 28
        self.depth = inputs[-1]
        for i in range(len(kernel_size)):
            padding = int(kernel_size[i]/2)

            self.out = calc_out(self.out, stride[i], padding,
                                kernel_size[i], pool_stride[i])

            layers.append(nn.Conv2d(inputs[i], inputs[i+1], kernel_size[i], 
                                       stride=stride[i], padding=padding))
            layers.append(nn.ReLU())
            
            layers.append(nn.Conv2d(inputs[i+1], inputs[i+1], kernel_size[i], 
                                       stride=stride[i], padding=padding))
            layers.append(nn.ReLU())

            layers.append(nn.MaxPool2d(pool_stride[i],pool_stride[i]))
            layers.append(nn.Dropout(p=0.2))

        self.cnn_layers = nn.Sequential(*layers)
        
        print(self.depth*self.out*self.out)
        
        layers2 = []
        layers2.append(nn.Dropout(p=0.2))
        layers2.append(nn.Linear(self.depth*self.out*self.out, 512))
        layers2.append(nn.Dropout(p=0.2))
        layers2.append(nn.Linear(512, 256))
        layers2.append(nn.Dropout(p=0.2))
        layers2.append(nn.Linear(256, 256))
        layers2.append(nn.Dropout(p=0.2))
        layers2.append(nn.Linear(256, 10))

        self.fc_layers = nn.Sequential(*layers2)

    def forward(self, x):
        x = self.cnn_layers(x)
        x = x.view(-1, self.depth*self.out*self.out)
        x = self.fc_layers(x)
        return x

def recognition(image):
    model = Net()
    model.load_state_dict(torch.load('saved_model.json'))
    model.eval()
    transform = transforms.Compose([
            transforms.ToPILImage(),
            transforms.Resize((28,28)),
            transforms.ToTensor(),
            transforms.Normalize((0.5,), (0.5,))
        ])
    image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

    thresh = cv2.threshold(image, 0, 255,
        cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU) [1]
    kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (1, 5))
    thresh = cv2.morphologyEx(thresh, cv2.MORPH_OPEN, kernel)
    im = transform(thresh).type('torch.FloatTensor')
    output = model(im.unsqueeze(0))
    return output

