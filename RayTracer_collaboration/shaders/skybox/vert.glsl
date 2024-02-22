#version 430 core

layout (location = 0) in vec2 position;

out vec2 positionOut;

void main()
{
    positionOut = position;
    gl_Position = vec4(position, 1.0, 1.0);
}