#version 330

out vec4 outputcolor;
in vec3 fragColour;
in vec3 Normal;
in vec3 fragPos;

uniform float u_transperancy;
uniform vec4 u_Color;
uniform vec3 u_diffuse;
uniform vec3 u_ambient;
uniform vec3 u_specular;
uniform vec3 viewPos;

uniform vec3 lightPos;
uniform vec3 lightColour;
uniform float constantAtten;
uniform float linearAtten;
uniform float quadraticAtten;

void main(){
    float ambientStrenth = .9f;
    vec3 Ambient = ambientStrenth * u_ambient;

    vec3 surfaceToLight = lightPos - fragPos;
    float d = length(surfaceToLight);
    float attenuation = 1.0 / (constantAtten + d * linearAtten + d * d * quadraticAtten);
    vec3 lightDir = normalize(surfaceToLight);
    float diff = max(dot(Normal, lightDir), 0.0);
    vec3 diffuse = u_diffuse * max(vec3(0.0, 0.0, 0.0), (lightColour * dot(Normal, surfaceToLight)) * attenuation);
//    vec3 norm = normalize(Normal);
//    vec3 lightDir = normalize(lightPos - fragPos);
//    float diff = max(dot(norm, lightDir), 0.0);
//    vec3 diffuse = diff * lightColour;

    float specularStrength = 0.5;
    vec3 viewDir = normalize(viewPos - fragPos);
    vec3 reflectDir = reflect(-lightDir, Normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
    vec3 specular = specularStrength * spec * lightColour;

    vec3 result = (Ambient + diffuse + specular) * fragColour;
    outputcolor = vec4(result, 1.0);
}