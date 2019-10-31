package inft3032;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    public boolean[] keys = new boolean[4];

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("KeyTyped");
    }

    //0 left
    //1 right
    //2 up
    //3 down
    public void keyPressed(KeyEvent e){
        int value = e.getKeyCode();
        if(value == KeyEvent.VK_A){
            keys[0] = true;
            System.out.println(value);
        }
        if(value == KeyEvent.VK_D){
            keys[1] = true;
            System.out.println(value);
        }
        if(value == KeyEvent.VK_W){
            keys[2] = true;
            System.out.println(value);
        }
        if(value == KeyEvent.VK_S){
            keys[3] = true;
            System.out.println(value);
        }
    }
    public void keyReleased(KeyEvent e){
        int value = e.getKeyCode();
        if(value == KeyEvent.VK_A){
            keys[0] = false;
        }
        if(value == KeyEvent.VK_D){
            keys[1] = false;
        }
        if(value == KeyEvent.VK_W){
            keys[2] = false;
        }
        if(value == KeyEvent.VK_S){
            keys[3] = false;
        }
    }
}
