# Import necessary libraries
import pygame as pg
from OpenGL.GL import *
import numpy as np
import pyrr

from objLoaderV4 import ObjLoader
import shaderLoader
from guiV1 import SimpleGUI


# Initialize pygame
pg.init()

# Set up OpenGL context version
pg.display.gl_set_attribute(pg.GL_CONTEXT_MAJOR_VERSION, 3)
pg.display.gl_set_attribute(pg.GL_CONTEXT_MINOR_VERSION, 3)


# Create a window for graphics using OpenGL
width = 1280
height = 480
pg.display.set_mode((width, height), pg.OPENGL | pg.DOUBLEBUF)

#color set
#glClearColor(0.3, 0.4, 0.5, 1.0)

# Todo: Enable depth testing here using glEnable()
glEnable(GL_DEPTH_TEST)
#enable scissor test
glEnable(GL_SCISSOR_TEST)

# Todo: Part 3: Write shaders (vertex and fragment shaders) and compile them here
shader = shaderLoader.compile_shader("shaders/vert.glsl", "shaders/frag.glsl")
glUseProgram(shader)

#set up shader 2
shader2 = shaderLoader.compile_shader("shaders/vert.glsl", "shaders/frag2.glsl")
glUseProgram(shader2)


# Todo: Part 1: Read the 3D model
# Lets setup our scene geometry.

obj = ObjLoader("objects/raymanModel.obj")
vertices = np.array(obj.vertices, dtype="float32")
center = obj.center
dia = obj.dia
#object 2
obj2 = ObjLoader("objects/dragon.obj")
vertices2 = np.array(obj2.vertices, dtype="float32")
center2 = obj2.center
dia2 = obj2.dia

size_position = 3
size_texture = 2
size_normal = 3
stride = (size_position + size_texture + size_normal) * 4
offset_position = 0
offset_texture = size_position * 4
offset_normal = (size_position + size_texture) * 4
n_vertices = len(obj.vertices) // (size_position + size_texture + size_normal)
n_vertices2 = len(obj2.vertices) // (size_position + size_texture + size_normal)

scale = (2.0 / dia)
aspect = (width/2)/height

scale2 = (2.0 / dia2)

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


#Configure uniform variables for object 1
scale_loc = glGetUniformLocation(shader, "scale")
glUniform1f(scale_loc, scale)

center_loc = glGetUniformLocation(shader, "center")
glUniform3fv(center_loc, 1, center)

aspect_loc = glGetUniformLocation(shader, "aspect")
glUniform1f(aspect_loc, aspect)

#object 2 vao, vbo, etc.
vao2 = glGenVertexArrays(1)
glBindVertexArray(vao2)
vbo2 = glGenBuffers(1)
glBindBuffer(GL_ARRAY_BUFFER, vbo2)
glBufferData(GL_ARRAY_BUFFER,
             size = obj2.vertices.nbytes,
             data = obj2.vertices, 
             usage = GL_STATIC_DRAW)

position_loc2 = glGetAttribLocation(shader2, "position")
glVertexAttribPointer(index = position_loc2,
                      size = size_position,
                      type = GL_FLOAT,
                      normalized = GL_FALSE,
                      stride = stride,
                      pointer = ctypes.c_void_p(offset_position))

glEnableVertexAttribArray(position_loc2)

normal_loc2 = glGetAttribLocation(shader2, "normal")
glVertexAttribPointer(index = normal_loc2,
                      size = size_normal,
                      type = GL_FLOAT,
                      normalized = GL_FALSE,
                      stride = stride,
                      pointer = ctypes.c_void_p(offset_normal))

glEnableVertexAttribArray(normal_loc2)

# Todo: Part 5: Configure uniform variables.
scale_loc2 = glGetUniformLocation(shader2, "scale")
glUniform1f(scale_loc2, scale2)

center_loc2 = glGetUniformLocation(shader2, "center")
glUniform3fv(center_loc2, 1, center2)

aspect_loc2 = glGetUniformLocation(shader2, "aspect")
glUniform1f(aspect_loc2, aspect)
# Todo: Part 6: Do the final rendering. In the rendering loop, do the following:
    # - Clear the color buffer and depth buffer before drawing each frame using glClear()
    # - Use the shader program using glUseProgram()
    # - Bind the VAO using glBindVertexArray()
    # - Draw the triangle using glDrawArrays()



slideName = SimpleGUI("Assignment 4")
slide = slideName.add_slider("camera Y angle", -180, 180, 0)
slide1 = slideName.add_slider("camera X angle", -90, 90, 0)
slide2 = slideName.add_slider("fov", 25, 120, 45)

near = 0.1
far = 10

eye = (0, 0, 2)
target = (0, 0, 0)
up = (0, 1, 0)

translation = pyrr.matrix44.create_from_translation([-center])
scaling = pyrr.matrix44.create_from_scale([scale, scale, scale])

translation2 = pyrr.matrix44.create_from_translation([-center2])
scaling2 = pyrr.matrix44.create_from_scale([scale2, scale2, scale2])

# Run a loop to keep the program running
draw = True
while draw:
    #define sliders
    cameraY = slide.get_value()
    cameraX = slide1.get_value()
    fov = slide2.get_value()

    #rotation matrices
    yRotation = pyrr.matrix44.create_from_y_rotation(np.deg2rad(cameraY))
    xRotation = pyrr.matrix44.create_from_x_rotation(np.deg2rad(cameraX))
    rotation = pyrr.matrix44.multiply(xRotation, yRotation)

    model_matrix = pyrr.matrix44.multiply(translation, scaling)
    model_matrix2 = pyrr.matrix44.multiply(translation2, scaling2)

    #projection matrix
    projection_matrix = pyrr.matrix44.create_perspective_projection_matrix(fov, aspect, near, far)
    #update the view matrix
    rotated_eye = pyrr.matrix44.apply_to_vector(rotation, eye)
    view_matrix = pyrr.matrix44.create_look_at(rotated_eye, target, up)

    #matrix locations
    model_loc = glGetUniformLocation(shader, "model_matrix")
    view_loc = glGetUniformLocation(shader, "view_matrix")
    projection_loc = glGetUniformLocation(shader, "projection_matrix")

    #object 1
    glUniformMatrix4fv(model_loc, 1, GL_FALSE, model_matrix)
    glUniformMatrix4fv(view_loc, 1, GL_FALSE, view_matrix)
    glUniformMatrix4fv(projection_loc, 1, GL_FALSE, projection_matrix)

    #viewport 1
    glViewport(0, 0, 640, height)
    glScissor(0, 0, 640, height)
    glClearColor(0.6, 0.2, 0.9, 0.5)
    # Clear color buffer and depth buffer before drawing each frame
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    
    

    for event in pg.event.get():
        if event.type == pg.QUIT:
            draw = False


    #object 1 draaw object
    glUseProgram(shader)
    glBindVertexArray(vao)
    glDrawArrays(GL_TRIANGLES,
                 0,
                 n_vertices)
    
    
    #matrix locations for object 2
    model_loc2 = glGetUniformLocation(shader2, "model_matrix")
    view_loc2 = glGetUniformLocation(shader2, "view_matrix")
    projection_loc2 = glGetUniformLocation(shader2, "projection_matrix")

    #object 2
    glUniformMatrix4fv(model_loc2, 1, GL_FALSE, model_matrix2)
    glUniformMatrix4fv(view_loc2, 1, GL_FALSE, view_matrix)
    glUniformMatrix4fv(projection_loc2, 1, GL_FALSE, projection_matrix)

    #viewport 2
    glViewport(640, 0, 640, height)
    glScissor(640, 0, 640, height)
    glClearColor(0.2, 0.8, 0.5, 0.4)
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    for event in pg.event.get():
        if event.type == pg.QUIT:
            draw = False

    #object 2 draw object
    glUseProgram(shader2)
    glBindVertexArray(vao2)
    glDrawArrays(GL_TRIANGLES,
                 0,
                 n_vertices2)


    # Refresh the display to show what's been drawn
    pg.display.flip()


# Cleanup
glDeleteVertexArrays(1, [vao])
glDeleteBuffers(1, [vbo])
glDeleteProgram(shader)

glDeleteVertexArrays(1, [vao2])
glDeleteBuffers(1, [vbo2])
glDeleteProgram(shader2)

pg.quit()   # Close the graphics window
quit()      # Exit the program