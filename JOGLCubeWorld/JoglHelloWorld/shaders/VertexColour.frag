#version 330

out vec4 outputcolor;

in vec3 fragColour;

void main(){
    outputcolor = vec4(fragColour, 1.0);
}