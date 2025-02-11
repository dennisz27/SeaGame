//Basic Game Application
// Basic Object, Image, Movement
// Threaded

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

//*******************************************************************************

public class BasicGameApp implements Runnable {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too
    Character fish;
    Character shark;
    Character hook;

    boolean fishvsshark = false;
    boolean hookvsfish = false;
    boolean hookvsshark = false;

    Image backgroundPic;

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() { // BasicGameApp constructor

        setUpGraphics();


        backgroundPic = Toolkit.getDefaultToolkit().getImage("bg.jpg");

        //variable and objects
        //create (construct) the objects needed for the game
        fish = new Character(100,100,3,3,50,50);
        fish.name = "Fish";
        fish.pic = Toolkit.getDefaultToolkit().getImage("fish.png");

        shark = new Character(200, 400,3,0,200,200);
        shark.name = "Shark";
        shark.pic = Toolkit.getDefaultToolkit().getImage("shark.png");

        hook = new Character(0,0,3,3,50,50);
        hook.name = "Hook";
        hook.pic = Toolkit.getDefaultToolkit().getImage("hook.png");
    } // end BasicGameApp constructor


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever.
        while (true) {
            moveThings();  //move all the game objects
            collide();
            shark.wrap();
            fish.move();
            hook.move();
            render();  // paint the graphics
            pause(10); // sleep for 10 ms
        }
    }

    public void moveThings() {
        shark.wrap();
      //  shark.printInfo();
        fish.move();

        hook.move();
        //fish.printInfo();
        //call the move() code for each object
    }

    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw the images
        Color myColor = new Color(3, 120, 180);
        g.setColor(myColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);
        g.drawImage(shark.pic, shark.xpos, shark.ypos, shark.width, shark.height, null);
        g.drawImage(fish.pic, fish.xpos, fish.ypos, fish.width, fish.height, null);
        g.drawImage(hook.pic, hook.xpos, hook.ypos, hook.width, hook.height, null);
        g.setColor(Color.MAGENTA);
        g.drawRect(fish.hitbox.x, fish.hitbox.y, fish.hitbox.width, fish.hitbox.height);
        g.setColor(Color.MAGENTA);
        g.drawRect(shark.hitbox.x, shark.hitbox.y, shark.hitbox.width, shark.hitbox.height);
        g.setColor(Color.MAGENTA);
        g.drawRect(hook.hitbox.x, hook.hitbox.y, hook.hitbox.width, hook.hitbox.height);


        g.dispose();
        bufferStrategy.show();
    }

    public void collide() {
        if(shark.hitbox.intersects(fish.hitbox) == true && fishvsshark == false){
            fishvsshark = true;
            fish.dx = -fish.dx;
            fish.dy = -fish.dy;
        }
        if (shark.hitbox.intersects(fish.hitbox) == false) {
            fishvsshark = false;
        }
        if(hook.hitbox.intersects(fish.hitbox) == true && hookvsfish == false){
            hookvsfish = true;
            fish.dx = -fish.dx;
            fish.dy = -fish.dy;
        }
        if (hook.hitbox.intersects(fish.hitbox) == false) {
            hookvsfish = false;
        }

        if (hook.hitbox.intersects(shark.hitbox) == true && hookvsshark == false) {
            hook.width = hook.width + 5;
            hook.height = hook.height + 5;
        }
        if (hook.hitbox.intersects(shark.hitbox) == false) {
            hookvsshark = false;
        }
    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

}
