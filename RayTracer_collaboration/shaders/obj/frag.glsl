#version 420 core
#define NUMBOUNCES 1000
#define PI 3.14159
#define EPSILON 1e-5
#define INFINITY 1e5

out vec4 outColor;

in vec3 fragNormal;
in vec3 fragPosition;
uniform vec3 sphereColor;

uniform vec4 light_pos;
uniform vec3 eye_pos;
uniform vec3 lightColor;

uniform vec2 resolution;
uniform vec3 cameraU;
uniform vec3 cameraV;
uniform vec3 cameraW;
uniform float fov;

uniform samplerCube cubeMapTex;

uniform float ambient_intensity;

struct Material{
      vec3 color;
      float metallic;
      float roughness;
      int mat_type;
};

struct Sphere{
      float radius;
      vec3 center;
      Material mat;
};

struct Hit{
    float d;
    vec3 point;
    vec3 normal;
};

struct Ray{
    vec3 direction;
    vec3 origin;
    float tMin, tMax;
};

struct AABB {
      vec3 minP;
      vec3 maxP;
};



// Taken from professor's GitHub
Ray getRay(vec2 pixel)
{
      Ray ray;
      ray.origin = eye_pos;
      float height = 2.*tan(fov/2.);
      float aspect = resolution.x/resolution.y;
      float width = height * aspect;
      vec2 windowDim = vec2(width, height);
      vec2 pixelSize = windowDim / resolution;
      vec2 delta = -0.5 * windowDim + pixel * pixelSize;
      ray.direction = -cameraW + cameraV * delta.y + cameraU * delta.x;
      ray.tMin = 0.;
      ray.tMax = INFINITY;
      return ray;
}

float compute_gaf(Sphere sphere, vec3 N, vec3 V, vec3 L)
{
      float alpha = pow(sphere.mat.roughness, 2);
      float k = (alpha) / 2;

      float Gv = clamp(dot(V, N), 0., 1.) / (clamp(dot(N, V), 0., 1.) * (1 - k) + k);
      float Gl = clamp(dot(L, N), 0., 1.) / (clamp(dot(L, N), 0., 1.) * (1 - k) + k);

      return Gv * Gl;
}

float compute_mdf(Sphere sphere, vec3 N, vec3 H)
{
      float alpha = pow(sphere.mat.roughness, 2);
      return pow(alpha, 2) / (PI * pow((pow(max(dot(H, N), 0), 2) * (pow(alpha, 2) - 1) + 1), 2));
}

Hit compute_sip(Sphere sphere, Ray ray)
{
      Hit hit = Hit(-1.0, vec3(0.0), vec3(0));
      vec3 magnitude = ray.origin - sphere.center;
      float a = dot(ray.direction, ray.direction);
      float b = 2 * dot(ray.direction, ray.origin - sphere.center);
      float c = dot(magnitude, magnitude) - pow(sphere.radius, 2);
      float delta = pow(b, 2) - (4.0 * a * c);
      if (delta > 0)
      {
            float t1 = (-b + sqrt(delta)) / (2.0 * a);
            float t2 = (-b - sqrt(delta)) / (2.0 * a);
            ray.tMin = t1;
            ray.tMax = t2;
            hit.d = min(t1, t2);
            hit.point = ray.origin + hit.d * ray.direction;
            hit.normal = normalize(hit.point - sphere.center);
      }

      return hit;
}

Sphere compute_nio(Sphere sphere, Ray ray)
{
      Hit hit_sphere = compute_sip(sphere, ray);
      Sphere nearest_object = sphere;

      if (hit_sphere.d > 0 && hit_sphere.d < INFINITY)
      {
            nearest_object = sphere;
      }
      else
      {
            nearest_object.radius = 0.0;
            nearest_object.mat.color = vec3(0.0);
      }

      return nearest_object;
}

vec3 computeDiffuse(Sphere sphere, vec3 N, vec3 L, vec3 F)
{
      vec3 ks = F;
      vec3 Kd = 1 - ks;

      return Kd * (1 - sphere.mat.metallic) * sphere.mat.color * max(dot(N, L), 0);
}

vec3 computePBR(Sphere sphere, Ray ray, Hit hit)
{
      vec3 N = normalize(hit.normal);
      vec3 L = normalize(light_pos.xyz - hit.point);
      vec3 V = normalize(eye_pos - hit.point);
      vec3 H = normalize(L + V);

      vec3 F0_metal;
      vec3 F0_dielectric = vec3(.04, .04, .04);

      // Metals
      if (sphere.mat.mat_type == 1)
            F0_metal = vec3(1.0, 1.0, 1.0);
      else if (sphere.mat.mat_type == 2)
            F0_metal = vec3(1.0, 1.0, 1.0);
      else if (sphere.mat.mat_type == 3)
            F0_metal = vec3(1.0, 1.0, 1.0);
      else if (sphere.mat.mat_type == 4)
            F0_metal = vec3(1.0, 1.0, 1.0);
      else if (sphere.mat.mat_type == 5)
            F0_metal = vec3(1.0, 1.0, 1.0);

      vec3 material_color = F0_metal;
      vec3 F0 = mix(F0_dielectric, F0_metal, sphere.mat.metallic);

      vec3 F = F0 + (1 - F0) * pow((1 - clamp(dot(H, V), 0, 1)), 5);

      float G = compute_gaf(sphere, N, V, L);

      float D = compute_mdf(sphere, N, H);

      vec3 microfacet = F * D * G;

      vec3 diffuseColor = computeDiffuse(sphere, N, L, F);

      vec3 ambientColor = ambient_intensity * sphere.mat.color;

      vec3 specularColor = microfacet * lightColor;

      return vec3(ambientColor + specularColor + diffuseColor);
}

vec3 pColor(Sphere sphere, vec2 pixel)
{
      float reflection = 0.9;
      Ray ray = getRay(pixel);

      Sphere closest_sphere = compute_nio(sphere, ray);

      Hit hit_sphere;

      Material hit_material;

      vec3 color = vec3(0.0);
      vec3 final_color = vec3(0.0);
      float shadow_factor = 0.8;
      float distance_to_light = -1.0;
      bool is_shadowed;

      // Number of bounces
      for (int i = 0; i < 10; i++)
      {
            closest_sphere = compute_nio(sphere, ray);
            Hit closest_object = Hit(INFINITY, vec3(0.0), vec3(0.0));
            Hit hit_ground = compute_sip(sphere, ray);
            Hit hit_sphere_obj = compute_sip(closest_sphere, ray);

            // Set the closest object hit to the sphere
            if (hit_ground.d > 0.0)
            {
                  closest_object = hit_ground;
                  hit_material = sphere.mat;
            }

            // Slight delta added to hit point to avoid the sphere from hitting itself on the next raycast
            vec3 shifted_point = closest_object.point + closest_object.normal * 0.0001;
            if (closest_object.d == INFINITY)
            {
                  color = texture(cubeMapTex, reflect(ray.direction, closest_object.normal)).rgb;
                  final_color += color * shadow_factor * reflection;
                  break;
            }

            // Sphere lighting and color
            if (closest_object.d == hit_sphere_obj.d)
            {
                  vec3 intersection_to_light = normalize(light_pos.xyz - shifted_point);
                  float intersection_to_light_distance = length(light_pos.xyz - closest_object.point);
                  Ray light_check;
                  light_check.origin = shifted_point;
                  light_check.direction = intersection_to_light;
                  Hit min_distance = compute_sip(closest_sphere, light_check);
                  
                  if (min_distance.d > 0)
                  {
                        distance_to_light = min_distance.d;
                        is_shadowed = true;
                  }
                  
                  vec3 intersection_to_camera = normalize(eye_pos - closest_object.point);
                  vec3 H = normalize(intersection_to_light + intersection_to_camera);

                  color = computePBR(closest_sphere, ray, closest_object);
            }

            // Adding the color
            final_color += color * reflection;
            reflection *= (1 - hit_material.roughness);

            // Setting up the next ray
            ray.origin = shifted_point;
            ray.direction = reflect(ray.direction, closest_object.normal);
      }

      return final_color;
}

void main()
{
      Sphere sphere;
      sphere.radius = 0.5;
      sphere.center = vec3(0.0, 0.0, 0.0);
      sphere.mat.color = sphereColor;  // Red color
      sphere.mat.metallic = 0.8;
      sphere.mat.roughness = 0.2;
      sphere.mat.mat_type = 1;

      Ray ray = getRay(gl_FragCoord.xy);
      Sphere closest_sphere = compute_nio(sphere, ray);
      Hit hit = compute_sip(closest_sphere, ray);

      outColor = vec4(pColor(sphere, gl_FragCoord.xy), 1.0);
}
