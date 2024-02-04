# Import necessary libraries
import pygame as pg
from OpenGL.GL import *
import numpy as np

from objLoaderV2 import ObjLoader
import shaderLoader
from guiV1 import SimpleGUI


# Initialize pygame
pg.init()

# Set up OpenGL context version
pg.display.gl_set_attribute(pg.GL_CONTEXT_MAJOR_VERSION, 3)
pg.display.gl_set_attribute(pg.GL_CONTEXT_MINOR_VERSION, 3)


# Create a window for graphics using OpenGL
width = 640
height = 480
pg.display.set_mode((width, height), pg.OPENGL | pg.DOUBLEBUF)


glClearColor(0.3, 0.4, 0.5, 1.0)
# Todo: Enable depth testing here using glEnable()
glEnable(GL_DEPTH_TEST)


# Todo: Part 3: Write shaders (vertex and fragment shaders) and compile them here
shader = shaderLoader.compile_shader("shaders/vert.glsl", "shaders/frag.glsl")
glUseProgram(shader)


# Todo: Part 1: Read the 3D model
# Lets setup our scene geometry.

obj = ObjLoader("objects/raymanModel.obj")
vertices = np.array(obj.vertices, dtype="float32")
center = obj.center
dia = obj.dia

size_position = 3
size_texture = 2
size_normal = 3
stride = (size_position + size_texture + size_normal) * 4
offset_position = 0
offset_texture = size_position * 4
offset_normal = (size_position + size_texture) * 4
n_vertices = len(obj.vertices) // (size_position + size_texture + size_normal)

scale = (2.0 / dia)
aspect = width/height

# Todo: Part 2: Upload the model data to the GPU. Create a VAO and VBO for the model data.
#vertex array object
vao = glGenVertexArrays(1)
glBindVertexArray(vao)

#vertex buffer object
vbo = glGenBuffers(1)
glBindBuffer(GL_ARRAY_BUFFER, vbo)
glBufferData(GL_ARRAY_BUFFER,
             size = obj.vertices.nbytes,
             data = obj.vertices, 
             usage = GL_STATIC_DRAW)

# Todo: Part 4: Configure vertex attributes using the variables defined in Part 1
position_loc = glGetAttribLocation(shader, "position")
glVertexAttribPointer(index = position_loc,
                      size = size_position,
                      type = GL_FLOAT,
                      normalized = GL_FALSE,
                      stride = stride,
                      pointer = ctypes.c_void_p(offset_position))

glEnableVertexAttribArray(position_loc)

normal_loc = glGetAttribLocation(shader, "normal")
glVertexAttribPointer(index = normal_loc,
                      size = size_normal,
                      type = GL_FLOAT,
                      normalized = GL_FALSE,
                      stride = stride,
                      pointer = ctypes.c_void_p(offset_normal))

glEnableVertexAttribArray(normal_loc)


# Todo: Part 5: Configure uniform variables.
scale_loc = glGetUniformLocation(shader, "scale")
glUniform1f(scale_loc, scale)

center_loc = glGetUniformLocation(shader, "center")
glUniform3fv(center_loc, 1, center)

aspect_loc = glGetUniformLocation(shader, "aspect")
glUniform1f(aspect_loc, aspect)

# Todo: Part 6: Do the final rendering. In the rendering loop, do the following:
    # - Clear the color buffer and depth buffer before drawing each frame using glClear()
    # - Use the shader program using glUseProgram()
    # - Bind the VAO using glBindVertexArray()
    # - Draw the triangle using glDrawArrays()


# Run a loop to keep the program running

slideName = SimpleGUI("Assignment 3")
slide = slideName.add_slider("Scale", 0.5 * scale, 2.0 * scale, scale)

draw = True
while draw:
    scale = slide.get_value()
    glUniform1f(scale_loc, scale)
    for event in pg.event.get():
        if event.type == pg.QUIT:
            draw = False

    # Clear color buffer and depth buffer before drawing each frame
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    glUseProgram(shader)
    glBindVertexArray(vao)
    glDrawArrays(GL_TRIANGLES,
                 0,
                 n_vertices)


    # Refresh the display to show what's been drawn
    pg.display.flip()


# Cleanup
glDeleteVertexArrays(1, [vao])
glDeleteBuffers(1, [vbo])
glDeleteProgram(shader)

pg.quit()   # Close the graphics window
quit()      # Exit the program