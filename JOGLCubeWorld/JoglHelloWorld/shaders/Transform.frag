#version 330

out vec4 outputcolor;
in vec3 fragColour;
in vec3 Normal;

uniform float u_transperancy;
uniform vec4 u_Color;
uniform vec3 u_diffuse;
uniform vec3 u_ambient;
uniform vec3 u_specular;


void main(){
    outputcolor = vec4(fragColour, 1.0);
}