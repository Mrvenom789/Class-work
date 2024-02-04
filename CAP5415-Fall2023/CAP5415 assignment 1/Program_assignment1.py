import numpy as np
import cv2
from scipy import ndimage
from scipy import linalg
from scipy.linalg import sqrtm
import matplotlib.pyplot as plt
import pdb 
import math

def convolution(image, kernel, axis):
    '''
    Performs convolution along x and y axis, based on kernel size.
    Assumes input image is 1 channel (Grayscale)
    Inputs: 
      image: H x W x C shape numpy array (C=1)
      kernel: K_H x K_W shape numpy array (for example, 3x1 for 1 dimensional filter for y-component)
    Returns:
      H x W x C Image convolved with kernel
    '''
    # Define the image kernel axes
    imageH, imageW = len(image), len(image[0])
    kernelL = len(kernel)

    # Calculate the padding needed
    padL = kernelL // 2
    padR = kernelL - padL - 1
    padU = kernelL // 2
    padD = kernelL - padU - 1
    padW = padL + padR
    padH = padU + padD

    # Define the output dimensions based on the axis
    if axis:
        outputH = imageH
        outputW = imageW - 1
    else:
        outputW = imageW
        outputH = imageH - 1

    if axis:
        padded_image = np.zeros((imageH, imageW + padW*2))
        padded_image[:, padW:-padW] = image

        output = np.zeros((imageH, imageW))
        # Convolve along rows (x-axis)
        for i in range(outputH):
            for j in range(outputW):
                #convolution
                convolve = np.sum(padded_image[i, j:j+kernelL] * kernel)

                # Store the output
                output[i][j] = convolve
    else:
        padded_image = np.zeros((imageH + padH*2, imageW))
        padded_image[padH:-padH, :] = image

        output = np.zeros((imageH, imageW))
        # Convolve along columns (y-axis)
        for j in range(outputW):
            for i in range(outputH):
                #convolution
                convolve = np.sum(padded_image[i:i+kernelL, j] * kernel.reshape(kernelL))

                # Store the output
                output[i][j] = convolve

    return output

def gaussian_kernel(size, sigma):
    '''
    Creates Gaussian Kernel (1 Dimensional)
    Inputs: 
      size: width of the filter
      sigma: standard deviation
    Returns a 1xN shape 1D gaussian kernel
    '''
    #define the size of the matrix and center
    matrix = np.zeros(size)
    center = size // 2

    #create Gaussian kernel
    for x in range(size):
        matrix[x] = (1 / (np.sqrt(2 * np.pi) * sigma)) * np.exp(-((x-center) ** 2) / (2 * sigma ** 2))
    
    #normalize
    matrix /= np.sum(matrix)

    #return statement
    return matrix

def gaussian_first_derivative_kernel(size, sigma):
    '''
    Creates 1st derviative gaussian Kernel (1 Dimensional)
    Inputs: 
      size: width of the filter
      sigma: standard deviation
    Returns a 1xN shape 1D 1st derivative gaussian kernel
    '''
    center = size // 2
    x = np.arange(-center, center + 1)
    #calculate the derivative
    gaussian = np.exp(-x**2 / (2*sigma**2))
    derivative = -x / (sigma**2) * gaussian
    return derivative


def non_max_supression(det, phase):
    '''
    Performs non-maxima supression for given magnitude and orientation.
    Returns output with nms applied. Also return a colored image based on gradient direction for maximum value.
    '''
    # Define the quantized gradient directions (e.g., 0°, 45°, 90°, 135°)
    quantized_direction = (np.round(phase / 45.0) * 45.0) % 180.0

    height, width = det.shape
    result = np.zeros_like(det, dtype=np.uint8)  # Initialize the result image
    colored_image = np.zeros((height, width, 3), dtype=np.uint8)  # Initialize colored image

    for y in range(1, height - 1):
        for x in range(1, width - 1):
            angle = quantized_direction[y, x]

            # Define neighbor positions based on the angle
            if angle == 0:
                neighbors = [(y, x - 1), (y, x + 1)]
            elif angle == 45:
                neighbors = [(y - 1, x - 1), (y + 1, x + 1)]
            elif angle == 90:
                neighbors = [(y - 1, x), (y + 1, x)]
            elif angle == 135:
                neighbors = [(y - 1, x + 1), (y + 1, x - 1)]

            neighbor_magnitudes = [det[ny, nx] for ny, nx in neighbors]
            neighbor_magnitudes.append(det[y, x])

            # Check if the magnitude at the current pixel is the maximum among neighbors
            if det[y, x] == max(neighbor_magnitudes):
                result[y, x] = det[y, x]  # Preserve the maximum magnitude
                # Create a colored image based on gradient direction (for visualization)
                if angle == 0:
                    colored_image[y, x] = [255, 0, 0]  # Red
                elif angle == 45:
                    colored_image[y, x] = [0, 255, 0]  # Green
                elif angle == 90:
                    colored_image[y, x] = [0, 0, 255]  # Blue
                elif angle == 135:
                    colored_image[y, x] = [255, 255, 0]  # Yellow

    return result, colored_image
    
            
    

def DFS(img):
    '''
    If pixel is linked to a strong pixel in a local window, make it strong as well.
    Called iteratively to make all strong-linked pixels strong.
    '''
    
    for i in range (1, int(img.shape[0] - 1)):
        for j in range(1, int(img.shape[1] - 1)):
            #makes other pixels strong
            if(img[i, j] == 1):
                to_max = max(img[i-1, j-1], img[i-1, j], img[i-1, j+1], img[i, j-1], img[i, j], img[i, j+1], img[i+1, j-1], img[i+1, j], img[i+1, j+1])

                if(to_max == 2):
                    img[i, j] == 2
    
def CalculateMagnitude(x, y):
    #sets up magnitude array
    magnitude = np.zeros(np.shape(x))
    #calculates magnitude at each point
    for i in range(len(x)):
        for j in range(len(y)):
            magnitude[i][j] = np.sqrt(x[i][j]**2 + y[i][j]**2)
    
    return magnitude

def calculateOrientation(x, y):
    #sets up orientation array
    orient = np.zeros(np.shape(x))
    #calculates orientation at each point
    for i in range(len(x)):
        for j in range(len(y)):
            orient[i][j] = math.atan2(I_yy[i][j], I_xx[i][j])
    
    return orient
                    
def hysteresis_thresholding(img, low_ratio, high_ratio):
    #assigning variables for low and high values
    difference = np.max(img) - np.min(img)
    low = np.min(img) + low_ratio * difference
    high = np.max(img) + high_ratio * difference
    #temporary image variable
    temp = np.copy(img)

    #assigning values
    for i in range (1, int(img.shape[0] - 1)):
        for j in range(1, int(img.shape[1] - 1)):
            #strong
            if(img[i, j] > high):
                temp[i, j] = 2
            #weak
            elif(img[i, j] < low):
                temp[i, j] = 0
            #average
            else:
                temp[i, j] = 1
    temp_strong = np.sum(img == 2)
    while(1):
        DFS(temp)
        if(temp_strong == np.sum(temp == 2)):
            break
        temp_strong = np.sum(temp == 2)
    #remove weak points
    for i in range (1, int(img.shape[0] - 1)):
        for j in range(1, int(img.shape[1] - 1)):
            if(temp[i, j] == 1):
                temp[i, j] = 0
    
    temp = temp / np.max(temp)
    return temp
if __name__ == '__main__':
    # Initialize values
    # You can choose any sigma values like 1, 0.5, 1.5, etc
    #sigma = 0.5 seems to work best
    sigma = 1.5
    size = 9
    rows = 3
    columns = 3
    low_ratio = 0.5
    high_ratio = 0.75
    # Read the image in grayscale mode using opencv
    img = cv2.imread('299091.jpg')
    grayscale = np.dot(img[..., :3],[0.2989, 0.5870, 0.1140])
    I = np.array(grayscale)

    ## above there is no need to do grayscale conversion, image is already in grayscale
    fig = plt.figure(figsize = (10, 7))

    # Create a gaussian kernel 1XN matrix
    G = gaussian_kernel(size, sigma)

    # Convolution of G and I
    I_x = convolution(I, G, True)
    I_y = convolution(I, G, False)

    #display the next images
    fig.add_subplot(rows, columns, 1)
    plt.imshow(I_x, cmap='gray')
    plt.title("convolution x")

    fig.add_subplot(rows, columns, 2)
    plt.imshow(I_y, cmap='gray')
    plt.title("convolution y")
    
    # Get the First Derivative Kernel
    G_x = gaussian_first_derivative_kernel(size, sigma)
    G_y = G_x.reshape(-1, 1)

    # Derivative of Gaussian Convolution
    I_xx = convolution(I_x, G_x, True)
    I_yy = convolution(I_y, G_y, False)
    
    # Convert derivative result to 0-255 for display.
    # Need to scale from 0-1 to 0-255.
    abs_grad_x = (( (I_xx - np.min(I_xx)) / (np.max(I_xx) - np.min(I_xx)) ) * 255.).astype(np.uint8)  
    abs_grad_y = (( (I_yy - np.min(I_yy)) / (np.max(I_yy) - np.min(I_yy)) ) * 255.).astype(np.uint8)

    #display the next images
    fig.add_subplot(rows, columns, 3)
    plt.imshow(abs_grad_x, cmap='gray')
    plt.title("derivative convolution x")

    
    fig.add_subplot(rows, columns, 4)
    plt.imshow(abs_grad_y, cmap='gray')
    plt.title("derivativeyconvolution y")
    
    # Compute magnitude
    magnitude = CalculateMagnitude(I_xx, I_yy)
    # Compute orientation
    orient = calculateOrientation(I_xx, I_yy)
    

    # Compute non-max suppression
    nms, color = non_max_supression(magnitude, orient)
    
    fig.add_subplot(rows, columns, 5)
    plt.imshow(nms, cmap='gray')
    plt.title("non maximum suppression")
    
    #Compute thresholding and then hysteresis
    hysteresis = hysteresis_thresholding(I, low_ratio, high_ratio)
    fig.add_subplot(rows, columns, 6)
    plt.imshow(hysteresis, cmap='gray')
    plt.title("Hysteresis Threshold")
    plt.show()