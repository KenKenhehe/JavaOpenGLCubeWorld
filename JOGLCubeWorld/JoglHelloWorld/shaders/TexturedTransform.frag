#version 330

out vec4 outputcolor;
in vec3 fragColour;
in vec2 TexCoord;

uniform float u_transperancy;
uniform vec4 u_Color;

uniform vec3 u_diffuse;
uniform vec3 u_ambient;
uniform vec3 u_specular;
uniform sampler2D u_Texture;
void main(){
    //vec4 texColor = texture(u_Texture, TexCoord);
    outputcolor = texture(u_Texture, TexCoord);
}