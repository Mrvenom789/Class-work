#version 420 core

// Attributes
layout (location = 0) in vec3 position;    // we can also use layout to specify the location of the attribute
layout (location = 1) in vec2 uv;
layout (location = 2) in vec3 normal;


// todo: define all the out variables
out vec3 fragNormal;
out vec3 fragPosition;
out vec2 fragUV;
out vec4 fragPosLightSpace;

// todo: define all the uniforms
uniform mat4 modelMatrix;
uniform mat4 lightviewMatrix;
uniform mat4 lightprojectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;



void main(){
    vec4 world_pos = modelMatrix * vec4(position, 1.0);
    fragPosition = world_pos.xyz;
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(position, 1.0);
    fragPosLightSpace = lightprojectionMatrix * lightviewMatrix * modelMatrix * vec4(position, 1.0);

    fragUV = uv;
    mat4 normal_matrix = transpose(inverse(modelMatrix));
    vec3 new_normal = (normal_matrix * vec4(normal, 0)).xyz;
    fragNormal = normalize(new_normal);
}
