import java.awt.*;

public class Character {

    //declare variables

    public String name;
    public Image pic;
    public int xpos;
    public int ypos;
    public int dx = 3;
    public int dy = 3;
    public int width = 50;
    public int height = 50;
    public boolean isAlive;
    public Rectangle hitbox;

    public Character() {
        hitbox = new Rectangle();
    }

    public Character(int paramXpos, int paramYpos, int paramDx, int paramDy, int paramWidth, int paramHeight){
        xpos = paramXpos;
        ypos = paramYpos;
        dx = paramDx;
        dy = paramDy;
        width = paramWidth;
        height = paramHeight;
        hitbox = new Rectangle(xpos, ypos, width, height);
    }

    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (ypos >= 700 - width  || ypos <= 0) {
            dy = -dy;
        }

        if (xpos >= 1000 - width || xpos <= 0) {
            dx = -dx;
        }
        hitbox = new Rectangle(xpos, ypos, width, height);
    }


    public void wrap(){
        xpos = xpos + dx;
        ypos = ypos + dy;

        if(xpos >= 1000){
            xpos = -width;
        }

        if(xpos == 0){
            xpos = 1000-width;
        }

        if(ypos >= 700){
            ypos = -height;
        }

        if(ypos == 0){
            ypos = 700-height;
        }
        hitbox = new Rectangle(xpos, ypos, width, height);
    }
    public void printInfo () {
        System.out.println(name + " is at (" + xpos + ", " + ypos + ")");
    }
}
