#version 330 core

in vec3 fragNormal;

out vec4 outColor;

void main(){
    vec3 norm = abs(normalize(fragNormal));
    outColor = vec4(norm, 1.0);
}