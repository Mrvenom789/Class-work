# Import necessary libraries
import pygame as pg
from OpenGL.GL import *
import numpy as np
import pyrr

from objLoaderV4 import ObjLoader
import shaderLoaderV3
from guiV3 import SimpleGUI

def load_cube(filename):
    #generate cube id
    cube_texture = glGenTextures(1)

    #bind the texture
    glBindTexture(GL_TEXTURE_CUBE_MAP, cube_texture)

    # Define texture parameters
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE)
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_NEAREST)
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

    # Define the faces of the cubemap
    faces = [GL_TEXTURE_CUBE_MAP_POSITIVE_X, GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
             GL_TEXTURE_CUBE_MAP_POSITIVE_Y, GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
             GL_TEXTURE_CUBE_MAP_POSITIVE_Z, GL_TEXTURE_CUBE_MAP_NEGATIVE_Z]
    
    for i in range(6):
        cube_data, cube_width, cube_height = load_image(filename[i], format="RGB", flip=False)
        glTexImage2D(faces[i], 0, GL_RGB, cube_width, cube_height, 0, GL_RGB, GL_UNSIGNED_BYTE, cube_data)
    
    #generate mipmap
    glGenerateMipmap(GL_TEXTURE_CUBE_MAP)

    # Unbind the texture
    glBindTexture(GL_TEXTURE_CUBE_MAP, 0)

    return cube_texture



def load_image(filename, format="RGB", flip=False):
    img = pg.image.load(filename)
    img_data = pg.image.tobytes(img, format, flip)
    w, h = img.get_size()
    return img_data, w, h

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

shaderProgramSky = shaderLoaderV3.ShaderProgram("shaderskybox/vertsky.glsl", "shaderskybox/fragsky.glsl")
shaderSky = shaderProgramSky.shader


ambient_intensity = 0.25

# Todo: Part 1: Read the 3D model
# Lets setup our scene geometry.

obj = ObjLoader("stormtrooper/stormtrooper.obj")
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
position_loc = 0
glBindAttribLocation(shader, position_loc, "position")
glVertexAttribPointer(index = position_loc,
                      size = size_position,
                      type = GL_FLOAT,
                      normalized = GL_FALSE,
                      stride = stride,
                      pointer = ctypes.c_void_p(offset_position))

glEnableVertexAttribArray(position_loc)

texture_loc = 1
glBindAttribLocation(shader, texture_loc, "uv")
glVertexAttribPointer(index = texture_loc,
                      size = size_texture,
                      type = GL_FLOAT,
                      normalized = GL_FALSE,
                      stride = stride,
                      pointer = ctypes.c_void_p(offset_texture))

glEnableVertexAttribArray(texture_loc)

normal_loc = 2
glBindAttribLocation(shader, normal_loc, "normal")
glVertexAttribPointer(index = normal_loc,
                      size = size_normal,
                      type = GL_FLOAT,
                      normalized = GL_FALSE,
                      stride = stride,
                      pointer = ctypes.c_void_p(offset_normal))

glEnableVertexAttribArray(normal_loc)


# Define the vertices of the quad.
quad_vertices = (
            # Position
            -1, -1,
             1, -1,
             1,  1,
             1,  1,
            -1,  1,
            -1, -1
)
vertices = np.array(quad_vertices, dtype=np.float32)

size_position = 2       # x, y, z
stride = size_position * 4
offset_position = 0
quad_n_vertices = len(vertices) // size_position  # number of vertices

# Create VA0 and VBO
vao_quad = glGenVertexArrays(1)
glBindVertexArray(vao_quad)            # Bind the VAO. That is, make it the active one.
vbo_quad = glGenBuffers(1)                  # Generate one buffer and store its ID.
glBindBuffer(GL_ARRAY_BUFFER, vbo_quad)     # Bind the buffer. That is, make it the active one.
glBufferData(GL_ARRAY_BUFFER, vertices.nbytes, vertices, GL_STATIC_DRAW)   # Upload the data to the GPU.

#position location for skybox
position_loc = 0
glBindAttribLocation(shader, position_loc, "position")
glVertexAttribPointer(index = position_loc,
                      size = size_position,
                      type = GL_FLOAT,
                      normalized = GL_FALSE,
                      stride = stride,
                      pointer = ctypes.c_void_p(offset_position))

glEnableVertexAttribArray(position_loc)


#set the material color to red
material_color = (1.0, 0.1, 0.1)

#load image
img_data, img_width, img_height = load_image("stormtrooper/stormtrooper.jpg", flip=True)
texture_id = glGenTextures(1)
glBindTexture(GL_TEXTURE_2D, texture_id)
glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

# Upload the image data to the GPU
glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, img_width, img_height, 0, GL_RGB, GL_UNSIGNED_BYTE, img_data)
shaderProgram["tex2D"] = 0

cubemap_images = ["skybox/right.png", "skybox/left.png", "skybox/top.png", "skybox/bottom.png", "skybox/front.png", "skybox/back.png"]
cubemap_id = load_cube(cubemap_images)
shaderProgram["cubeMapTex"] = 1

shaderProgramSky["cubeMapTexSky"] = 0

gui = SimpleGUI("Assignment 9")
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

#choose texture
texture_picker = gui.add_radio_buttons("Texture type", options_dict={"environment mapping":2, "2D texture":1, "Mix":0}, initial_option="environment mapping")

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
    tex = texture_picker.get_value()
    
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

    #view matrix without the translation
    view_wo_translation = view_matrix.copy()
    view_wo_translation[3][:3] = [0,0,0]

    #compute inverse of new view matrix
    inverse_view = pyrr.matrix44.inverse(pyrr.matrix44.multiply(view_wo_translation, projection_matrix))
    
    #variables to be sent to the shader
    shaderProgram["model_matrix"] = model_matrix
    shaderProgram["view_matrix"] = view_matrix
    shaderProgram["projection_matrix"] = projection_matrix
    shaderProgram["eye_pos"] = rotated_eye
    shaderProgram["tex_pick"] = int(tex)

    glUseProgram(shader)

    #bind texture
    glActiveTexture(GL_TEXTURE0)
    glBindTexture(GL_TEXTURE_2D, texture_id)

    #bind cubemap textures
    glActiveTexture(GL_TEXTURE1)
    glBindTexture(GL_TEXTURE_CUBE_MAP, cubemap_id)

    glActiveTexture(GL_TEXTURE0)
    glBindTexture(GL_TEXTURE_CUBE_MAP, cubemap_id)

    #draw object
    glBindVertexArray(vao)
    glDrawArrays(GL_TRIANGLES,
                 0,
                 n_vertices)
    
    #draw skybox
    glDepthFunc(GL_LEQUAL)
    glUseProgram(shaderSky)
    shaderProgramSky["invViewProjectionMatrix"] = inverse_view
    glBindVertexArray(vao_quad)
    glDrawArrays(GL_TRIANGLES,
                 0,
                 quad_n_vertices)  
    glDepthFunc(GL_LESS)      

    
    # Refresh the display to show what's been drawn
    pg.display.flip()


# Cleanup

glDeleteVertexArrays(1, [vao, vao_quad])
glDeleteBuffers(1, [vbo, vbo_quad])
glDeleteProgram(shader)
glDeleteProgram(shaderSky)

pg.quit()   # Close the graphics window
quit()      # Exit the program