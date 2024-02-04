#version 330 core

in vec3 position;
in vec3 normal;

out vec3 fragNormal;

uniform float scale;
uniform float aspect;
uniform vec3 center;
uniform mat4 model_matrix;

void main() {
    vec4 pos = model_matrix * vec4(position, 1.0);
    pos.x /= aspect;
    //pos.z *= -1;
    gl_Position = pos;
    mat4 normal_matrix = transpose(inverse(model_matrix));
    vec3 new_normal = (normal_matrix * vec4(normal, 0)).xyz;
    fragNormal = normalize(new_normal);
}