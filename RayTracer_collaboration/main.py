# Import necessary libraries
import pygame as pg
from OpenGL.GL import *
import numpy as np

from guiV3 import SimpleGUI
from objLoaderV4 import ObjLoader
import shaderLoaderV3
import pyrr
from utils import load_image


# Loads textures
def load_cubemap_texture(filenames):
    # Generate a texture ID
    texture_id = glGenTextures(1)

    # Bind the texture as a cubemap
    glBindTexture(GL_TEXTURE_CUBE_MAP, texture_id)

    # Define texture parameters
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE)
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE)
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE)
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER,
                    GL_LINEAR_MIPMAP_NEAREST)
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

    # Define the faces of the cubemap
    faces = [GL_TEXTURE_CUBE_MAP_POSITIVE_X, GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
             GL_TEXTURE_CUBE_MAP_POSITIVE_Y, GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
             GL_TEXTURE_CUBE_MAP_POSITIVE_Z, GL_TEXTURE_CUBE_MAP_NEGATIVE_Z]

    # Load and bind images to the corresponding faces
    for i in range(6):
        img_data, img_w, img_h = load_image(
            filenames[i], format="RGB", flip=False)
        glTexImage2D(faces[i], 0, GL_RGB, img_w, img_h, 0,
                     GL_RGB, GL_UNSIGNED_BYTE, img_data)

    # Generate mipmaps
    glGenerateMipmap(GL_TEXTURE_CUBE_MAP)

    # Unbind the texture
    glBindTexture(GL_TEXTURE_CUBE_MAP, 0)

    return texture_id

# Does all the vao/vbo related stuff in a function
# Can use this to bind all the buffers for any object drawn in the scene
# Call this one time for each object i.e. a single sphere


def build_buffers(object):
    vao = glGenVertexArrays(1)
    glBindVertexArray(vao)
    vbo = glGenBuffers(1)
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    glBufferData(GL_ARRAY_BUFFER, object.vertices.nbytes,
                 object.vertices, GL_STATIC_DRAW)

    position_loc = 0
    tex_loc = 1
    normal_loc = 2

    glVertexAttribPointer(position_loc, object.size_position, GL_FLOAT,
                          GL_FALSE, object.stride, ctypes.c_void_p(object.offset_position))
    glVertexAttribPointer(tex_loc, object.size_position, GL_FLOAT,
                          GL_FALSE, object.stride, ctypes.c_void_p(object.offset_texture))
    glVertexAttribPointer(normal_loc, object.size_position, GL_FLOAT,
                          GL_FALSE, object.stride, ctypes.c_void_p(object.offset_normal))

    glEnableVertexAttribArray(position_loc)
    glEnableVertexAttribArray(tex_loc)
    glEnableVertexAttribArray(normal_loc)

    return vao, vbo, object.n_vertices


# Initialize pygame
pg.init()

# Set up OpenGL context version
pg.display.gl_set_attribute(pg.GL_CONTEXT_MAJOR_VERSION, 3)
pg.display.gl_set_attribute(pg.GL_CONTEXT_MINOR_VERSION, 3)
pg.display.gl_set_attribute(pg.GL_STENCIL_SIZE, 8)

#Create a window for graphics using OpenGL
width = 1200 
height = 800 

screen_center = [width / 2, height / 2]
focus = True

pg.display.set_mode((width, height), pg.OPENGL | pg.DOUBLEBUF)


glClearColor(0.3, 0.4, 0.5, 1.0)
glEnable(GL_DEPTH_TEST)


#Write shaders
shaderProgram_skybox = shaderLoaderV3.ShaderProgram(
    "shaders/skybox/vert.glsl", "shaders/skybox/frag.glsl")
shaderProgram_obj = shaderLoaderV3.ShaderProgram(
    "shaders/obj/vert.glsl", "shaders/obj/frag.glsl")

#set up camera variables
eye = np.array([0, 0, 3], dtype=np.float32)
target = (0, 0, 0)
camera_forward = np.array([0, 0, -1], dtype=np.float32)
up = np.array([0, 1, 0], dtype=np.float32)

yaw = -90.0
pitch = 0

fov = 45
aspect = width/height
near = 0.1
far = 100

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

quad_n_vertices = len(vertices) // 2

vao_quad = glGenVertexArrays(1)
glBindVertexArray(vao_quad)
vbo_quad = glGenBuffers(1)
glBindBuffer(GL_ARRAY_BUFFER, vbo_quad)
glBufferData(GL_ARRAY_BUFFER, vertices.nbytes, vertices, GL_STATIC_DRAW)

position_loc = 0
glBindAttribLocation(shaderProgram_skybox.shader, position_loc, "position")
glVertexAttribPointer(position_loc, 2, GL_FLOAT,
                      GL_FALSE, 8, ctypes.c_void_p(0))
glEnableVertexAttribArray(position_loc)

cube_map_images = ['images/skybox1/right.png', 'images/skybox1/left.png',
                   'images/skybox1/top.png', 'images/skybox1/bottom.png',
                   'images/skybox1/front.png', 'images/skybox1/back.png']

skybox_id = load_cubemap_texture(cube_map_images)

shaderProgram_skybox['cubeMapTex'] = 0

#define color and light
material_color = (1.0, 0.1, 0.1)
light_pos = np.array([-10, 10, -10, None], dtype=np.float32)

#define the object
obj = ObjLoader("objects/square.obj")
vao_obj, vbo_obj, n_vertices_obj = build_buffers(obj)

scaling_mat = pyrr.matrix44.create_from_scale(pyrr.Vector3([0.5, 0.5, 0.5]))
model_mat = scaling_mat

gui = SimpleGUI("settings")

#Create gui variables
fov_slider = gui.add_slider("fov", 25, 90, 90, resolution=1)
light_rot_check = gui.add_slider("Light Movement", -5, 5, 0.1, resolution=0.1)
ambient_intensity_slider = gui.add_slider(
    "Ambient Intensity", 0, 1, 0.1, resolution=0.1)
material_color = gui.add_color_picker(
    "material color", initial_color=(0.5, 0.5, 0.5))
slide = gui.add_slider("camera Y angle", -180, 180, 0)
slide1 = gui.add_slider("camera X angle", -90, 90, 0)

# Run a loop to keep the program running
draw = True
while draw:
    for event in pg.event.get():
        if event.type == pg.QUIT:
            draw = False

    # Clear color buffer and depth buffer before drawing each frame
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    # Rotates camera
    cameraY = slide.get_value()
    cameraX = slide1.get_value()

    # rotation matrices
    yRotation = pyrr.matrix44.create_from_y_rotation(np.deg2rad(cameraY))
    xRotation = pyrr.matrix44.create_from_x_rotation(np.deg2rad(cameraX))
    rotation = pyrr.matrix44.multiply(xRotation, yRotation)

    rotated_eye = pyrr.matrix44.apply_to_vector(rotation, eye)
    view_mat = pyrr.matrix44.create_look_at(rotated_eye, target, up)
    projection_mat = pyrr.matrix44.create_perspective_projection_matrix(
        fov_slider.get_value(), aspect, near,  far)

    view_mat_without_translation = view_mat.copy()
    view_mat_without_translation[3][:3] = [0, 0, 0]

    inverseViewProjection_mat = pyrr.matrix44.inverse(
        pyrr.matrix44.multiply(view_mat_without_translation, projection_mat))

    # Rotates light around scene
    light_pos[0] = 20 * light_rot_check.get_value()
    light_pos[2] = 20 * light_rot_check.get_value()

    # Set uniforms
    shaderProgram_obj["model_matrix"] = model_mat
    shaderProgram_obj["light_pos"] = light_pos
    shaderProgram_obj["eye_pos"] = eye
    shaderProgram_obj["fov"] = np.deg2rad(fov_slider.get_value())

    #create minimum and maximum bounds
    shaderProgram_obj["minBound"] = (-0.5, -0.5, -0.5)
    shaderProgram_obj["maxBound"] = (0.5, 0.5, 0.5)

    shaderProgram_obj["cameraU"] = pyrr.Vector3(
        [view_mat[0][0], view_mat[1][0], view_mat[2][0]])
    shaderProgram_obj["cameraV"] = pyrr.Vector3(
        [view_mat[0][1], view_mat[1][1], view_mat[2][1]])
    shaderProgram_obj["cameraW"] = pyrr.Vector3(
        [view_mat[0][2], view_mat[1][2], view_mat[2][2]])

    shaderProgram_obj["resolution"] = np.array(
        [width, height], dtype=np.float32)

    shaderProgram_obj["ambient_intensity"] = ambient_intensity_slider.get_value(
    )
    shaderProgram_obj["lightColor"] = [1.0, 1.0, 1.0]
    shaderProgram_obj["sphereColor"] = material_color.get_color()

    glActiveTexture(GL_TEXTURE0)
    glBindTexture(GL_TEXTURE_CUBE_MAP, skybox_id)

    glUseProgram(shaderProgram_obj.shader)
    glBindVertexArray(vao_obj)
    glDrawArrays(GL_TRIANGLES, 0, obj.n_vertices)      # draw the object

    glDepthFunc(GL_LEQUAL)
    glUseProgram(shaderProgram_skybox.shader)
    shaderProgram_skybox["inViewProjectionMatrix"] = inverseViewProjection_mat
    glBindVertexArray(vao_quad)
    glDrawArrays(GL_TRIANGLES, 0, quad_n_vertices)

    glDepthFunc(GL_LESS)

    # Refresh the display to show what's been drawn
    pg.display.flip()


# Cleanup
glDeleteVertexArrays(1, [vao_obj, vao_quad])
glDeleteBuffers(1, [vbo_obj, vbo_obj])
glDeleteProgram(shaderProgram_skybox.shader)
glDeleteProgram(shaderProgram_obj.shader)

pg.quit()   # Close the graphics window
quit()      # Exit the program
