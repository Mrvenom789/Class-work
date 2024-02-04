#version 420 core

// todo: define all the input variables to the fragment shader
in vec3 fragNormal;
in vec3 fragPosition;
in vec2 fragUV;
in vec4 fragPosLightSpace;

// todo: define all the uniforms


layout (binding=0) uniform sampler2D depthTex;  // depth texture bound to texture unit 0
out vec4 outColor;

void main(){
    // todo: fill in the fragment shader
    vec3 fragPos3D = fragPosLightSpace.xyz / fragPosLightSpace.w;
    fragPos3D = (fragPos3D + 1.0) / 2.0;
    vec3 L;
    if(fragPosLightSpace.w == 0.0){
        L = normalize(fragPosLightSpace.xyz);
    }
    else{
        L = normalize(fragPosLightSpace.xyz - fragPosition);
    }
    float z_current = fragPos3D.z;
    float z_depthTex = texture(depthTex, fragPos3D.xy).r;
    float bias = max(0.0005 * (1.0 - dot(fragNormal, L)), 0.0001);
    //float bias = 0.0005f;
    if(z_current - bias > z_depthTex){
        //in shadow
        outColor = vec4(fragPos3D, 1.0);
    }
    else{
        //not in shadow
        outColor = vec4(fragPos3D, 1.0);
    }
}