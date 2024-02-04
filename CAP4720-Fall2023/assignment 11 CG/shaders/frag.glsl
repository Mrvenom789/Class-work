#version 330 core

in vec3 fragNormal;
in vec3 fragPosition;
in vec4 fragPosLightSpace;

out vec4 outColor;

uniform vec3 materialColor;
uniform vec3 light_pos;
uniform vec3 eye_pos;
//uniform vec3 specular_color;
uniform float roughness;
uniform float ambient_intensity;
//uniform float K_s;
//uniform int Object_id;
uniform int material_type;
uniform float metallic;

vec3 computeFresnel(vec3 V, vec3 H){
    vec3 F0;
    //select iron
    if(material_type == 4){
        F0 = vec3(0.56, 0.57, 0.58);
    }
    //select copper
    else if(material_type == 3){
        F0 = vec3(0.95, 0.64, 0.54);
    }
    //select gold
    else if(material_type == 2){
        F0 = vec3(1.00, 0.71, 0.29);
    }
    //select aluminum
    else if(material_type == 1){
        F0 = vec3(0.91, 0.92, 0.92);
    }
    //select silver
    else{
        F0 = vec3(0.95, 0.93, 0.88);
    }
    vec3 F0_dielectric = vec3(0.04);
    F0 = mix(F0_dielectric, F0, metallic);
    float VdotH = clamp(dot(V,H), 0, 1);
    vec3 F = F0 + (1-F0) * pow((1 - VdotH), 5);
    return F;
}
float computeMDF(vec3 N, vec3 H){
    float alpha = pow(roughness, 2);
    float alpha2 = pow(alpha, 2);
    float dotpow = pow(dot(N,H), 2);
    float D = alpha2 / (3.14 * pow(dotpow * ((alpha2 - 1) + 1), 2));
    return D;
}

float computeGAF(vec3 N, vec3 V, vec3 L){
    float alpha = pow(roughness, 2);
    float k = alpha / 2;
    float gv = dot(N, V) / (dot(N, V) * (1 - k) + k);
    float gl = dot(N, L) / (dot(N, L) * (1 - k) + k);
    float G = gv * gl;
    return G;
}

void main(){
    vec3 norm = normalize(fragNormal);
    //vec3 light_dir;
    vec3 view_dir = normalize(eye_pos - fragPosition);
    vec3 fragPos3D = fragPosLightSpace.xyz / fragPosLightSpace.w;
    fragPos3D = (fragPos3D + 1.0) / 2.0;
    vec3 L = normalize(light_pos - fragPosition);
    vec3 half_vector = normalize(L + view_dir);
    vec3 color_ambient_light = ambient_intensity * materialColor;
    vec3 F = computeFresnel(view_dir, half_vector);

    float D = computeMDF(norm, half_vector);

    float G = computeGAF(norm, view_dir, L);

    vec3 kd = 1 - F;
    vec3 diffuseColor = kd * metallic * materialColor * clamp(dot(norm, L), 0, 1);

    vec3 lightColor = vec3(1.0);
    
    vec3 microfacet = (F * D * G);

    vec3 specularColor = microfacet * lightColor;

    //outColor = vec4(F, 1.0);
    outColor = vec4(color_ambient_light + diffuseColor + specularColor, 1.0);
}