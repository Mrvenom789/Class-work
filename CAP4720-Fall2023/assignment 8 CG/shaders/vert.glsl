#version 330 core

in vec3 position;
in vec3 normal;

out vec3 fragNormal;
out vec3 fragPosition;

//uniform float scale;
//uniform vec3 center;
//uniform float aspect;
uniform mat4 model_matrix;
uniform mat4 view_matrix;
uniform mat4 projection_matrix;

void main() {
    vec4 world_pos = model_matrix * vec4(position, 1.0);
    fragPosition = world_pos.xyz;
    gl_Position = projection_matrix * view_matrix * world_pos;

    //vec4 pos = projection_matrix * view_matrix * model_matrix * vec4(position, 1.0);
    //pos.x /= aspect;
    //gl_Position = pos;
    mat4 normal_matrix = transpose(inverse(model_matrix));
    vec3 new_normal = (normal_matrix * vec4(normal, 0)).xyz;
    fragNormal = normalize(new_normal);
}