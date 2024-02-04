#version 330 core

in vec3 position;
in vec3 normal;

out vec3 fragNormal;

uniform float scale;
uniform float aspect;
uniform vec3 center;

void main() {
    vec3 pos = position-center;
    pos.x /= aspect;
    pos.z *= -1;
    gl_Position = vec4(pos * scale, 1.0);
    fragNormal = normalize(normal);
}