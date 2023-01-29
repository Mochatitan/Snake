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

    private BufferedImage bodyVerticalImage;
    private BufferedImage bodyHorizontalImage;
    // current position of the player on the board grid
    private Point pos;
    // keep track of the player's length
    private int length;
    
    public String direction;
    private String directionUpdate;

    private int speed;
    private int maxSpeed;
  

    List<Point> posList = new ArrayList<Point>();
    List<String> directionList = new ArrayList<String>();
    public Player() {
        // load the assets
        loadImage();

        // initialize the state
        pos = new Point(2, 2);
        // posList.add(pos.x,pos.y);
        length = 1;
        direction = "Right";
        directionList.add("Right");
        directionUpdate = "Right";
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
            bodyHorizontalImage = ImageIO.read(new File("images/body_horizontal.png"));
            bodyVerticalImage = ImageIO.read(new File("images/body_vertical.png"));

        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but 
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        for(int i = 0; i < posList.size(); i++){
        
        if(directionList.get(i)=="Up"){
        g.drawImage(
            headUpImage, 
            posList.get(i).x * Board.TILE_SIZE, 
            posList.get(i).y * Board.TILE_SIZE, 
            observer
        );
        }else if(directionList.get(i)=="Right"){
            g.drawImage(
            headRightImage, 
            posList.get(i).x * Board.TILE_SIZE, 
            posList.get(i).y * Board.TILE_SIZE, 
            observer
        );
        }else if(directionList.get(i)=="Down"){
            g.drawImage(
            headDownImage, 
            posList.get(i).x * Board.TILE_SIZE, 
            posList.get(i).y * Board.TILE_SIZE, 
            observer
        );
        }else if(directionList.get(i)=="Left"){
            g.drawImage(
            headLeftImage, 
            posList.get(i).x * Board.TILE_SIZE, 
            posList.get(i).y * Board.TILE_SIZE, 
            observer
        );
        }else{System.out.println("Direction ERROR: direction= "+direction+". directionList(i)= "+directionList.get(i));}
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
            direction = "Up";
        }
        if (key == KeyEvent.VK_RIGHT) {
            // pos.translate(1, 0);
            direction = "Right";
        }
        if (key == KeyEvent.VK_DOWN) {
            // pos.translate(0, 1);
            direction="Down";
        }
        if (key == KeyEvent.VK_LEFT) {
            // pos.translate(-1, 0);
            direction="Left";
        }
    }

    public void tick() {
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the player.
        if(speed==0){
            //called every time the snake moves
            System.out.println("x: "+pos.getX());
            System.out.println("Y: "+pos.getY());
            
            if(direction=="Up"){
                pos.translate(0,-1);
            }else if(direction=="Right"){
                pos.translate(1,0);
            }else if(direction=="Down"){
                pos.translate(0,1);
            }else if(direction=="Left"){
                pos.translate(-1,0);
            }

            directionUpdate=direction;
            speed=maxSpeed;

            posList.add(new Point(pos.x,pos.y));
            directionList.add(new String(direction));

            if(posList.size()>length){
                posList.remove(0);
            }
            if(directionList.size()>length){
                directionList.remove(0);
            }
            for(String vegetable:directionList){
            System.out.println(vegetable);
        }
        System.out.println("break");


        }else{
            speed--;
        }
        
        
        
        
        

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

    public String getDirection(){
        return direction;
    }

}
