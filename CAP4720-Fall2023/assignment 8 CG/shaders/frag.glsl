#version 330 core

in vec3 fragNormal;
in vec3 fragPosition;

out vec4 outColor;

uniform vec3 materialColor;
uniform vec4 light_pos;
uniform vec3 eye_pos;
uniform int shading;
uniform bool silhouette;

vec3 computeDifuse(vec3 n, vec3 l){
    return materialColor * clamp(dot(l, n), 0.0, 1.0);
}
void main(){
    vec3 norm = normalize(fragNormal);
    vec3 light_dir;
    vec3 view_dir = normalize(eye_pos - fragPosition);
    //sets light direction
    if(light_pos.w == 0.0){
        light_dir = normalize(light_pos.xyz);
    }
    else{
        light_dir = normalize(light_pos.xyz - fragPosition);
    }
    float diffIntensity = dot(light_dir, fragNormal);
    //computes difuse reflection
    if(shading == 1){
        vec3 color_difuse_reflection = computeDifuse(norm, light_dir);
        outColor = vec4(color_difuse_reflection, 1.0);
    }
    //computes the toon shading
    else{
        float intensity;
        float intenseClamp = clamp(diffIntensity, 0, 1);
        int n = 5;
        intensity = ceil(intenseClamp * n) / n;
        float step = sqrt(intensity) * n;
        intensity = (floor(step) + smoothstep(0.48, 0.52, fract(step))) / n;
        intensity = intensity * intensity;
        
        //multiply the intensity and material color
        vec3 final = intensity * materialColor;
        //if silhouette is active
        if(silhouette == true){
            if(dot(fragNormal, view_dir) < 0.2){
                final =vec3(0.0, 0.0, 0.0);
                view_dir = normalize(eye_pos - fragPosition);
                outColor = vec4(final, 1.0);
            }
        }
        outColor = vec4(final, 1.0);
    }
    
}