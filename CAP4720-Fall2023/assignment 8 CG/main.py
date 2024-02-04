# Import necessary libraries
import pygame as pg
from OpenGL.GL import *
import numpy as np
import pyrr

from objLoaderV4 import ObjLoader
import shaderLoaderV3
from guiV3 import SimpleGUI


# Initialize pygame
pg.init()

# Set up OpenGL context version
pg.display.gl_set_attribute(pg.GL_CONTEXT_MAJOR_VERSION, 3)
pg.display.gl_set_attribute(pg.GL_CONTEXT_MINOR_VERSION, 3)


# Create a window for graphics using OpenGL
width = 900
height = 480
pg.display.set_mode((width, height), pg.OPENGL | pg.DOUBLEBUF)

#color set
glClearColor(0.3, 0.4, 0.5, 1.0)

# Todo: Enable depth testing here using glEnable()
glEnable(GL_DEPTH_TEST)

# Todo: Part 3: Write shaders (vertex and fragment shaders) and compile them here
#shader = shaderLoaderV3.ShaderProgram("shaders/vert.glsl", "shaders/frag.glsl")
#glUseProgram(shader)

shaderProgram = shaderLoaderV3.ShaderProgram("shaders/vert.glsl", "shaders/frag.glsl")
shader = shaderProgram.shader

ambient_intensity = 0.25

# Todo: Part 1: Read the 3D model
# Lets setup our scene geometry.

obj = ObjLoader("objects/dragon.obj")
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
aspect = (width)/height

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

#Do the final rendering. In the rendering loop, do the following:
    # - Clear the color buffer and depth buffer before drawing each frame using glClear()
    # - Use the shader program using glUseProgram()
    # - Bind the VAO using glBindVertexArray()
    # - Draw the triangle using glDrawArrays()

#set the material color to red
material_color = (1.0, 0.1, 0.1)

gui = SimpleGUI("Assignment 8")
slide = gui.add_slider("camera Y angle", -180, 180, 0)
slide1 = gui.add_slider("camera X angle", -90, 90, 0)
slide2 = gui.add_slider("fov", 25, 120, 45)

light_pos = np.array([2, 2, 2, None], dtype=np.float32)

near = 0.1
far = 10

eye = (0, 0, 2)
target = (0, 0, 0)
up = (0, 1, 0)
#set up translation and scaling vectors
scaling = pyrr.matrix44.create_from_scale([scale, scale, scale])

translation = pyrr.matrix44.create_from_translation([-center])

material_color_picker = gui.add_color_picker("material color", initial_color=material_color)
light_type_radio_picker = gui.add_radio_buttons("light type", options_dict={"point":1, "directional":0}, initial_option="point")
shading_radio_picker = gui.add_radio_buttons("shading type", options_dict={"Diffuse":1, "Toon shading":0}, initial_option="Toon shading")
silhouette_check_box = gui.add_checkbox("silhouette", initial_state=True)


# Run a loop to keep the program running
draw = True
while draw:
    for event in pg.event.get():
        if event.type == pg.QUIT:
            draw = False

    # Clear color buffer and depth buffer before drawing each frame
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    #define sliders
    cameraY = slide.get_value()
    cameraX = slide1.get_value()
    fov = slide2.get_value()

    materialColor = material_color_picker.get_color()
    light_pos[3] = light_type_radio_picker.get_value()
    shading = shading_radio_picker.get_value()
    silhouette = silhouette_check_box.get_value()
    
    #rotation matrices
    yRotation = pyrr.matrix44.create_from_y_rotation(np.deg2rad(cameraY))
    xRotation = pyrr.matrix44.create_from_x_rotation(np.deg2rad(cameraX))
    rotation = pyrr.matrix44.multiply(xRotation, yRotation)

    model_matrix = pyrr.matrix44.multiply(translation, scaling)

    #projection matrix
    projection_matrix = pyrr.matrix44.create_perspective_projection_matrix(fov, aspect, near, far)
    #update the view matrix
    rotated_eye = pyrr.matrix44.apply_to_vector(rotation, eye)
    view_matrix = pyrr.matrix44.create_look_at(rotated_eye, target, up)
    
    #variables to be sent to the shader
    shaderProgram["model_matrix"] = model_matrix
    shaderProgram["view_matrix"] = view_matrix
    shaderProgram["projection_matrix"] = projection_matrix
    shaderProgram["eye_pos"] = rotated_eye
    shaderProgram["materialColor"] = materialColor
    shaderProgram["light_pos"] = light_pos
    shaderProgram["shading"] = int(shading)
    shaderProgram["silhouette"] = silhouette

    #draw object 2
    #glUseProgram(shader)
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