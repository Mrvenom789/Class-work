# Import necessary libraries
import pygame as pg
from OpenGL.GL import *
import numpy as np
import math

# Initialize pygame
pg.init()

# Set up OpenGL context version
pg.display.gl_set_attribute(pg.GL_CONTEXT_MAJOR_VERSION, 3)
pg.display.gl_set_attribute(pg.GL_CONTEXT_MINOR_VERSION, 3)

# Create a window for graphics using OpenGL
width = 640
height = 480
pg.display.set_mode((width, height), pg.OPENGL | pg.DOUBLEBUF)

# Todo: Part 1
# Set the background color (clear color)
# glClearColor takes 4 arguments: red, green, blue, alpha. Each argument is a float between 0 and 1.
glClearColor(1.0, 0.0, 0.0, 1.0)


# Todo: Part 2
class ObjLoader:
    def __init__(self, file):
        '''
        This Objloader class loads a mesh from an obj file.
        The mesh is made up of vertices.
        Each vertex is generally defined by the following attributes
             - position coordinates (v)
             - texture coordinates (vt)
             - normal coordinates (vn)

        There are other attributes that can be defined for a vertex,
        but we will not use them for now.

        Note: Sometimes, the obj file only contains position coordinates (v).

        If the obj file contains information for all three (v, vt, vn),
        then each vertex is made up of 8 floats:
                    3 for position coordinates  v = (x,y,z),
                    2 for texture coordinates   vt = (u,v),
                    3 for normals               vn = (xn,yn,zn)

        Important member variables to note:

        self.vertices:
            a one dimensional array of floats in the form:
            vertices = [ x,y,z, u,v, xn,yn,zn,    x,y,z, u,v, xn,yn,zn,   ...]
                        ------  ---   ------     ------  ---   ------
                        |  v     vt     vn |     | v     vt     vn  |
                        -------------------      -------------------    ...
                              vertex 1                vertex 2

        self.v:
            a list of vertex position coordinates
            v = [ [x,y,z], [x,y,z], [x,y,z], ...]

        self.vt:
            a list of vertex texture coordinates
            vt = [ [u,v], [u,v], [u,v], ...]

        self.vn:
            a list of vertex normal coordinates
            vn = [ [xn,yn,zn], [xn,yn,zn], [xn,yn,zn], ...]

        :param file:    full path to the obj file
        '''

        self.vertices = []      # 1D array of floats
        self.v = []             # list of vertex position coordinates
        self.vt = []            # list of vertex texture coordinates
        self.vn = []            # list of vertex normal coordinates

        self.load_mesh(file)


    def load_mesh(self, filename):
        '''
        Load a mesh from an obj file.
        :param filename:
        :return:
        '''

        vertices = []

        with open(filename, "r") as file:
            for line in file:
                words = line.split(" ")
                if words[0] == "v":
                    self.v.append(list(map(float, words[1:4])))
                elif words[0] == "vt":
                    self.vt.append(list(map(float, words[1:3])))
                elif words[0] == "vn":
                    self.vn.append(list(map(float, words[1:4])))
                elif words[0] == "f":
                    n_triangle = len(words) - 3

                    for i in range(n_triangle):
                        self.add_vertex(words[1], self.v, self.vt, self.vn, vertices)
                        self.add_vertex(words[2 + i], self.v, self.vt, self.vn, vertices)
                        self.add_vertex(words[3 + i], self.v, self.vt, self.vn, vertices)

        self.vertices = np.array(vertices, dtype=np.float32)
        self.v = np.array(self.v, dtype=np.float32)
        self.vt = np.array(self.vt, dtype=np.float32)
        self.vn = np.array(self.vn, dtype=np.float32)

    def add_vertex(self, corner_description: str,
                   v, vt,
                   vn, vertices) -> None:
        '''
        Add a vertex to the list of positions.
        :param corner_description:
        :param v:   list of vertex position coordinates
        :param vt:  list of vertex texture coordinates
        :param vn:  list of vertex normal coordinates
        :param vertices:
        :return:
        '''

        v_vt_vn = corner_description.split("/")
        v_vt_vn = list(map(int, v_vt_vn))

        if len(v_vt_vn) == 1:
            vertices.extend(v[int(v_vt_vn[0]) - 1])
        elif len(v_vt_vn) == 2:
            if len(vn) == 0:
                vertices.extend(v[int(v_vt_vn[0]) - 1])     # add vertex coordinates to the list
                vertices.extend(vt[int(v_vt_vn[1]) - 1])    # add texture coordinates to the list
            elif len(vt) == 0:
                vertices.extend(v[int(v_vt_vn[0]) - 1])     # add vertex coordinates to the list
                vertices.extend(vn[int(v_vt_vn[1]) - 1])    # add normal coordinates to the list
        elif len(v_vt_vn) == 3:
            vertices.extend(v[int(v_vt_vn[0]) - 1])         # add vertex coordinates to the list
            vertices.extend(vt[int(v_vt_vn[1]) - 1])        # add texture coordinates to the list
            vertices.extend(vn[int(v_vt_vn[2]) - 1])        # add normal coordinates to the list
    
obj = ObjLoader("objects/raymanModel.obj")

positions = obj.v
print("Dimension of v: ", obj.v.shape)

texture_coordinates = obj.vt
print("Dimension of vt: ", obj.vt.shape)

normal_coordinates = obj.vn
print("Dimension of vn: ", obj.vn.shape)

vertices = obj.vertices         # 1D array of vertices (position, texture, normal)
print("Dimension of vertices: ", obj.vertices.shape)


# Todo: Part 3

#calculate maximum and minimum values
x_max, y_max, z_max = np.amax(positions, axis=0)
x_min, y_min, z_min = np.amin(positions, axis=0)

max_val = [x_max, y_max, z_max]
min_val = [x_min, y_min, z_min]

#print values
print("Min: ", min_val)
print("Max: ", max_val)

#calculate middle values
x_mid = (x_max + x_min) / 2
y_mid = (y_max + y_min) / 2
z_mid = (z_max + z_min) / 2

mid_val = [x_mid, y_mid, z_mid]

print("Center: ", mid_val)

#calculate distance 
distance = math.sqrt((x_max - x_min)**2 + (y_max - y_min)**2 + (z_max - z_min)**2)
print("Distance: ", distance)

# Todo: Part 4
vbo = glGenBuffers(1)
glBindBuffer(GL_ARRAY_BUFFER, vbo)
glBufferData(GL_ARRAY_BUFFER, size=vertices.nbytes, data=vertices, usage=GL_STATIC_DRAW)

# Run a loop to keep the program running
draw = True
while draw:
    for event in pg.event.get():
        if event.type == pg.QUIT:
            draw = False

    # Clear the screen (or clear the color buffer), and set it to the background color chosen earlier
    glClear(GL_COLOR_BUFFER_BIT)

    # Refresh the display to show what's been drawn
    pg.display.flip()


pg.quit()   # Close the graphics window
quit()      # Exit the program