import numpy as np
import cv2
from scipy import ndimage
from scipy import linalg
from scipy.linalg import sqrtm
import matplotlib.pyplot as plt
import pdb 
import math
from PIL import Image

def binarization(img, threshold):
    #determine if image values are greater than threshold and multiply by 255
    img = np.array(img)
    img_update = (img > threshold) * 255
    return img_update


def otsu(img):
    img = np.array(img)
    #get the number of pixels
    pixels = img.shape[0] * img.shape[1]
    weight = 1.0/pixels

    #get histogram values
    hist, bin = np.histogram(img, np.array(range(0, 257)))

    #initialize variables
    thresh = 0
    opt_value = 0

    arr = np.arange(256)

    #iterate through threshold
    for t in bin[1:-1]:
        #background and forground values
        b = np.sum(hist[:t])
        f = np.sum(hist[t:])
        wb = b * weight
        wf = f * weight

        #mean background and forground values
        mwb = np.sum(arr[:t] * hist[:t]) / float(b)
        mwf = np.sum(arr[t:] * hist[t:]) / float(f)

        #define value
        value = wb * wf * (mwb - mwf) ** 2

        if value > opt_value:
            thresh = t
            opt_value = value
        
    #do the binarization
    result = img.copy()
    result[img > thresh] = 255
    result[img < thresh] = 0
    return result
    

if __name__ == '__main__':
    #define the threshold values
    threshold1 = 40
    threshold2 = 150
    threshold3 = 225
    rows = 4
    columns = 3

    #input images for simple binarization
    img1s = (Image.open('image.png').convert('L'))
    img2s = (Image.open('image2.jpg').convert('L'))
    img3s = (Image.open('IMG_5134.jpg').convert('L'))

    #input images for otsu
    img1o = (Image.open('image.png').convert('L'))
    img2o = (Image.open('image2.jpg').convert('L'))
    img3o = (Image.open('IMG_5134.jpg').convert('L'))

    #plot histograms
    hist1s = img1s.histogram()
    hist2s = img2s.histogram()
    hist3s = img3s.histogram()


    #define figure for histogram
    figHist = plt.figure(figsize = (12, 5))
    figHist.add_subplot(1, columns, 1)
    plt.hist(hist1s, bins=256, range=(0, 256), color='gray')
    plt.title("histogram 1")

    figHist.add_subplot(1, columns, 2)
    plt.hist(hist2s, bins=256, range=(0, 256), color='gray')
    plt.title("histogram 2")

    figHist.add_subplot(1, columns, 3)
    plt.hist(hist3s, bins=256, range=(0, 256), color='gray')
    plt.title("histogram 3")
    plt.show()


    #do the binarization for each image
    img1s1 = binarization(img1s, threshold1)
    img2s1 = binarization(img2s, threshold1)
    img3s1 = binarization(img3s, threshold1)

    #define figure for images
    fig = plt.figure(figsize = (10, 7))
    fig.add_subplot(rows, columns, 1)
    plt.imshow(img1s1, cmap='gray')
    plt.title('image 1 threshold: 40')

    fig.add_subplot(rows, columns, 2)
    plt.imshow(img2s1, cmap='gray')
    plt.title('image 2 threshold: 40')

    fig.add_subplot(rows, columns, 3)
    plt.imshow(img3s1, cmap='gray')
    plt.title('image 3 threshold: 40')

    #do the binarization for each image
    img1s2 = binarization(img1s, threshold2)
    img2s2 = binarization(img2s, threshold2)
    img3s2 = binarization(img3s, threshold2)

    #define figure for images
    fig.add_subplot(rows, columns, 4)
    plt.imshow(img1s2, cmap='gray')
    plt.title('image 1 threshold: 150')

    fig.add_subplot(rows, columns, 5)
    plt.imshow(img2s2, cmap='gray')
    plt.title('image 2 threshold: 150')

    fig.add_subplot(rows, columns, 6)
    plt.imshow(img3s2, cmap='gray')
    plt.title('image 3 threshold: 150')

    #do the binarization for each image
    img1s3 = binarization(img1s, threshold3)
    img2s3 = binarization(img2s, threshold3)
    img3s3 = binarization(img3s, threshold3)

    #define figure for images
    fig.add_subplot(rows, columns, 7)
    plt.imshow(img1s3, cmap='gray')
    plt.title('image 1 threshold: 225')

    fig.add_subplot(rows, columns, 8)
    plt.imshow(img2s3, cmap='gray')
    plt.title('image 2 threshold: 225')

    fig.add_subplot(rows, columns, 9)
    plt.imshow(img3s3, cmap='gray')
    plt.title('image 3 threshold: 225')
    
    #do the otsu thresholding
    img1o = otsu(img1o)
    img2o = otsu(img2o)
    img3o = otsu(img3o)

    #define figure for images
    fig.add_subplot(rows, columns, 10)
    plt.imshow(img1o, cmap='gray')
    plt.title('image 1 otsu')

    fig.add_subplot(rows, columns, 11)
    plt.imshow(img2o, cmap='gray')
    plt.title('image 2 otsu')

    fig.add_subplot(rows, columns, 12)
    plt.imshow(img3o, cmap='gray')
    plt.title('image 3 otsu')
    plt.show()

