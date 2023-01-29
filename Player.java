import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class Player {

    // image that represents the player's position on the board
    private BufferedImage headUpImage;
    private BufferedImage headRightImage;
    private BufferedImage headDownImage;
    private BufferedImage headLeftImage;
    // current position of the player on the board grid
    private Point pos;
    // keep track of the player's length
    private int length;
    //direction 0=up 1=right 2=down 3=left
    public int direction;
    private int speed;
    private int maxSpeed;
    private int directionUpdate;
    List<Point> posList = new ArrayList<Point>();
    
    public Player() {
        // load the assets
        loadImage();

        // initialize the state
        pos = new Point(2, 2);
        posList.add(pos);
        length = 1;
        direction = 1;
        directionUpdate = 1;
        speed = 20;
        maxSpeed=speed;
    }

    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            headUpImage = ImageIO.read(new File("images/head_up.png"));
            headRightImage = ImageIO.read(new File("images/head_right.png"));
            headDownImage = ImageIO.read(new File("images/head_down.png"));
            headLeftImage = ImageIO.read(new File("images/head_left.png"));

        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but 
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        for(Point snake:posList){
        
        if(directionUpdate==0){
        g.drawImage(
            headUpImage, 
            snake.x * Board.TILE_SIZE, 
            snake.y * Board.TILE_SIZE, 
            observer
        );
        }else if(directionUpdate==1){
            g.drawImage(
            headRightImage, 
            snake.x * Board.TILE_SIZE, 
            snake.y * Board.TILE_SIZE, 
            observer
        );
        }else if(directionUpdate==2){
            g.drawImage(
            headDownImage, 
            snake.x * Board.TILE_SIZE, 
            snake.y * Board.TILE_SIZE, 
            observer
        );
        }else if(directionUpdate==3){
            g.drawImage(
            headLeftImage, 
            snake.x * Board.TILE_SIZE, 
            snake.y * Board.TILE_SIZE, 
            observer
        );
        }else{System.out.println("Direction isnt 0,1,2,3 ERROR");}
    }
    }
    
    public void keyPressed(KeyEvent e) {
        // every keyboard get has a certain code. get the value of that code from the
        // keyboard event so that we can compare it to KeyEvent constants
        int key = e.getKeyCode();
        
        // depending on which arrow key was pressed, we're going to move the player by
        // one whole tile for this input
        if (key == KeyEvent.VK_UP) {
            // pos.translate(0, -1);
            direction = 0;
        }
        if (key == KeyEvent.VK_RIGHT) {
            // pos.translate(1, 0);
            direction = 1;
        }
        if (key == KeyEvent.VK_DOWN) {
            // pos.translate(0, 1);
            direction=2;
        }
        if (key == KeyEvent.VK_LEFT) {
            // pos.translate(-1, 0);
            direction=3;
        }
    }

    public void tick() {
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the player.
        if(speed==0){
            //called every time the snake moves
            System.out.println("x: "+pos.getX());
            System.out.println("Y: "+pos.getY());
            
            if(direction==0){
                pos.translate(0,-1);
            }else if(direction==1){
                pos.translate(1,0);
            }else if(direction==2){
                pos.translate(0,1);
            }else if(direction==3){
                pos.translate(-1,0);
            }

            directionUpdate=direction;
            speed=maxSpeed;

            posList.add(new Point(pos.x,pos.y));

            if(posList.size()>length){
                posList.remove(0);
            }


        }else{
            speed--;
        }
        
        for(Point fruit:posList){
            System.out.println(fruit);
        }
        System.out.println("break");
        
        

        // prevent the player from moving off the edge of the board sideways
        if (pos.x < 0) {
            pos.x = 0;
        } else if (pos.x >= Board.COLUMNS) {
            pos.x = Board.COLUMNS - 1;
        }
        // prevent the player from moving off the edge of the board vertically
        if (pos.y < 0) {
            pos.y = 0;
        } else if (pos.y >= Board.ROWS) {
            pos.y = Board.ROWS - 1;
        }
    }

    public String getScore() {
        return String.valueOf(length);
    }

    public void addScore(int amount) {
        length += amount;
    }

    public Point getPos() {
        return pos;
    }

    public int getDirection(){
        return direction;
    }

}
