#version 330 core

in vec3 fragNormal;
in vec3 fragPosition;

out vec4 outColor;

uniform vec3 materialColor;
uniform vec4 light_pos;
uniform vec3 eye_pos;
uniform vec3 specular_color;
uniform int shininess;
uniform float ambient_intensity;
uniform float K_s;
uniform int Object_id;

vec3 computeDifuse(vec3 n, vec3 l){
    return materialColor * clamp(dot(l, n), 0.0, 1.0);
}
vec3 computeSpecular(vec3 norm, vec3 half_vector){
    return specular_color * pow(clamp(dot(norm, half_vector), 0.0, 1.0), shininess);
}

void main(){
    vec3 norm = normalize(fragNormal);
    vec3 light_dir;
    vec3 view_dir = normalize(eye_pos - fragPosition);
    if(light_pos.w == 0.0){
        light_dir = normalize(light_pos.xyz);
    }
    else{
        light_dir = normalize(light_pos.xyz - fragPosition);
    }
    vec3 half_vector = normalize(light_dir + view_dir);
    //computes difuse reflection
    vec3 color_difuse_reflection = computeDifuse(norm, light_dir);
    //computes the specular reflection
    vec3 color_specular_reflection = computeSpecular(norm, half_vector);
    vec3 color_ambient_light = ambient_intensity * materialColor;
    //computes ambient color
    vec3 color = color_ambient_light + color_difuse_reflection + K_s * color_specular_reflection;
    if(Object_id == 1){
        outColor = vec4(color_difuse_reflection, 1.0);
    }
    else if(Object_id == 2){
        outColor = vec4(color_specular_reflection, 1.0);
    }
    else if (Object_id == 3){
        outColor = vec4(color, 1.0);
    }
    
}