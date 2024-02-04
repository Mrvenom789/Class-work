import time
import torch
import torch.nn as nn
import torch.nn.functional as F

class ConvNet(nn.Module):
    def __init__(self, mode):
        super(ConvNet, self).__init__()
        
        # Define various layers here, such as in the tutorial example
        # self.conv1 = nn.Conv2D(...)
        self.conv1 = nn.Conv2d(in_channels = 1, out_channels = 40, kernel_size = 5, stride=1)
        self.maxpool = nn.MaxPool2d(2, 2)
        self.conv2 = nn.Conv2d(in_channels = 40, out_channels = 49, kernel_size = 5, stride=1)
        self.maxpool = nn.MaxPool2d(2, 2)
        self.flat = nn.Flatten()
        #define sizes
        self.input_size = 28*28
        self.hidden_size = 100
        self.output = 10
        #fully connected layers and output
        self.fc1 = nn.Linear(self.input_size, self.hidden_size)
        self.fc2 = nn.Linear(self.hidden_size, self.hidden_size)
        self.dropout = nn.Dropout(p=0.5)
        self.output = nn.Linear(self.hidden_size, self.output)
        # This will select the forward pass function based on mode for the ConvNet.
        # Based on the question, you have 5 modes available for step 1 to 5.
        # During creation of each ConvNet model, you will assign one of the valid mode.
        # This will fix the forward function (and the network graph) for the entire training/testing
        if mode == 1:
            self.forward = self.model_1
        elif mode == 2:
            self.forward = self.model_2
        elif mode == 3:
            self.forward = self.model_3
        elif mode == 4:
            self.forward = self.model_4
        elif mode == 5:
            self.forward = self.model_5
        else: 
            print("Invalid mode ", mode, "selected. Select between 1-5")
            exit(0)
        
        
    # Baseline model. step 1
    def model_1(self, X):
        # ======================================================================
        # One fully connected layer.
        #
        # ----------------- YOUR CODE HERE ----------------------
        X = self.flat(X)
        X = torch.sigmoid(self.fc1(X))
        X = self.output(X)
        return X
        
        # Uncomment the following return stmt once method implementation is done.
        # return  fcl
        # Delete line return NotImplementedError() once method is implemented.

    # Use two convolutional layers.
    def model_2(self, X):
        # ======================================================================
        # Two convolutional layers + one fully connnected layer.
        #
        # ----------------- YOUR CODE HERE ----------------------
        X = self.conv1(X)
        X = self.maxpool(X)
        X = self.conv2(X)
        X = self.maxpool(X)
        X = self.flat(X)
        X = torch.sigmoid(self.fc1(X))
        X = self.output(X)
        return X
        # Uncomment the following return stmt once method implementation is done.
        # return  fcl
        # Delete line return NotImplementedError() once method is implemented.

    # Replace sigmoid with ReLU.
    def model_3(self, X):
        # ======================================================================
        # Two convolutional layers + one fully connected layer, with ReLU.
        #
        # ----------------- YOUR CODE HERE ----------------------
        X = self.conv1(X)
        X = self.maxpool(X)
        X = self.conv2(X)
        X = self.maxpool(X)
        X = self.flat(X)
        X = torch.relu(self.fc1(X))
        X = self.output(X)
        return X
        # Uncomment the following return stmt once method implementation is done.
        # return  fcl
        # Delete line return NotImplementedError() once method is implemented.
        

    # Add one extra fully connected layer.
    def model_4(self, X):
        # ======================================================================
        # Two convolutional layers + two fully connected layers, with ReLU.
        #
        # ----------------- YOUR CODE HERE ----------------------
        X = self.conv1(X)
        X = self.maxpool(X)
        X = self.conv2(X)
        X = self.maxpool(X)
        X = self.flat(X)
        X = torch.relu(self.fc1(X))
        X = torch.relu(self.fc2(X))
        X = self.output(X)
        return X

        # Uncomment the following return stmt once method implementation is done.
        # return  fcl
        # Delete line return NotImplementedError() once method is implemented.

    # Use Dropout now.
    def model_5(self, X):
        # ======================================================================
        # Two convolutional layers + two fully connected layers, with ReLU.
        # and  + Dropout.
        #
        # ----------------- YOUR CODE HERE ----------------------
        X = self.conv1(X)
        X = self.maxpool(X)
        X = self.conv2(X)
        X = self.maxpool(X)
        X = self.flat(X)
        X = torch.relu(self.fc1(X))
        X = torch.relu(self.fc2(X))
        X = self.dropout(X)
        X = self.output(X)
        return X

        # Uncomment the following return stmt once method implementation is done.
        # return  fcl
        # Delete line return NotImplementedError() once method is implemented.
        #return NotImplementedError()
    
    
