#version 330 core

in vec3 fragNormal;

out vec4 outColor;

void main(){
    vec3 norm = (normalize(fragNormal) + 1.0) / 2;
    outColor = vec4(norm, 1.0);
}