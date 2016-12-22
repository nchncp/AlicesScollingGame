import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Game
{
    private Grid grid;
    private Color color;
    private Cell cell;
    private int userRow;
    private int msElapsed;
    private int timesGet;
    private int timesAvoid;
    String name = JOptionPane.showInputDialog("Your name");
    private int row = 10;
    private int col = 15;
    private int bonus;
    //private int random = 5 + (int)(Math.random() * ((14 - 5) + 1));
    //(int)((Math.random()*5)+1);
    private int life = 10;

    public Game()
    {
        grid = new Grid(row, col);
        userRow = 0;
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        updateTitle();
        grid.setImage(new Location(userRow, 0), "alice.png");
    }

    public void play()
    {
        
        while (!isGameOver())
        {
            grid.pause(100);
            handleKeyPress();
            if (msElapsed % 300 == 0)
            {
                scrollLeft();
                populateRightEdge();
            }
            updateTitle();
            msElapsed += 100;
        }

        if(isGameOver()){
            JFrame frame = new JFrame();
            String message = "Restart?";
            int answer = JOptionPane.showConfirmDialog(frame, message);
            if(answer==JOptionPane.YES_OPTION){
                clear();
                play();
            }else if(answer==JOptionPane.NO_OPTION){
                System.exit(0);
            }else{
                System.exit(0);
            }
        }
    }

    //keyboard use
    public void handleKeyPress()
    {
        int key = grid.checkLastKeyPressed();
        if(key==38 && userRow>=1){
            grid.setImage(new Location(userRow, 0), null);
            handleCollision(new Location(userRow-1, 0));
            grid.setImage(new Location(--userRow, 0), "alice.png");

        }else if(key==40 && userRow<=8){
            grid.setImage(new Location(userRow, 0), null);
            handleCollision(new Location(userRow+1, 0));
            grid.setImage(new Location(++userRow, 0), "alice.png");

        }
    }

    //randim make population
    public void populateRightEdge()
    {      
        int gLocate = (int)(Math.random()*10);
        int aLocate = (int)(Math.random()*10);
        int bLocate = (int)(Math.random()*10);

        //check same random
        while(aLocate==gLocate||aLocate==bLocate||gLocate==bLocate){
            if(aLocate==gLocate||aLocate==bLocate){
                if(gLocate==bLocate){
                    aLocate = (int)(Math.random()*10);
                    gLocate = (int)(Math.random()*10);
                }else{
                    aLocate = (int)(Math.random()*10);
                }
            }else if(gLocate==bLocate){
                gLocate = (int)(Math.random()*10);
            }
        }

        //lacate img by random row
        grid.setImage(new Location(gLocate, col-1), "mushgang.png");
        grid.setImage(new Location(aLocate, col-1), "trash.png");
        grid.setImage(new Location(bLocate, col-1), "bottle.png");
    }

    //scolling from right to left
    public void scrollLeft()
    {
        for(int i=0; i<=14; i++){
            for(int j=0; j<=9; j++){
                if(i!=14){
                    if(grid.getImage(new Location(j, i)) != "alice.png" ){
                        grid.setImage(new Location(j, i), grid.getImage(new Location(j, i+1)));
                    }else{
                        handleCollision(new Location(j, i));
                    }
                    grid.setImage(new Location(j, i+1), null);
                }
            }
        }
    }

    //collect score
    public void handleCollision(Location loc)
    {
        /*grid.getImage(new Location(x,y));
        grid.getImage(loc);

        loc.getRow();
        loc.getCol();*/

        if(grid.getImage(new Location(loc.getRow(), 0)) == "mushgang.png"){
            ++timesGet;
            //System.out.println("get " + timesGet);
        }else if(grid.getImage(new Location(loc.getRow(), 0)) == "trash.png"){
            ++timesAvoid;
            //System.out.println("avoid " + timesAvoid);
        }else if(grid.getImage(new Location(loc.getRow(), 0)) == "bottle.png"){
            timesGet += 10;
        }

        if(grid.getImage(new Location(loc.getRow(), loc.getCol()+1)) == "mushgang.png"){
            ++timesGet;
            //System.out.println("get " + timesGet);
        }else if(grid.getImage(new Location(loc.getRow(), loc.getCol()+1)) == "trash.png"){
            ++timesAvoid;
            //System.out.println("avoid " + timesAvoid);
        }else if(grid.getImage(new Location(loc.getRow(), loc.getCol()+1)) == "bottle.png"){
            timesGet += 10;
        }
    }

    //get score
    public int getScore()
    {
        return timesGet-timesAvoid;
    }

    //show the score at the title
    public void updateTitle()
    {
        if(timesAvoid==9){
            grid.setTitle(name + " got " + getScore() + " scores and left " + (life-timesAvoid) + " life.");
        }else{
            grid.setTitle(name + " got " + getScore() + " scores and left " + (life-timesAvoid) + " lifes.");
        }
    }

    //game over situation
    public boolean isGameOver()
    {
        if(timesAvoid == 10){
            return true;
        }else{
            return false;
        }
    }

    //play game
    public static void test()
    {
        Game game = new Game();
        game.play();
    }

    public static void main(String[] args)
    {
        test();
    }

    //clear screen
    public void clear()
    {
        for(int i=0; i<=14; i++){
            for(int j=0; j<=9; j++){
                if(i!=14){
                    if(grid.getImage(new Location(j, i)) != null ){
                        grid.setImage(new Location(j, i), grid.getImage(new Location(j, i+1)));
                    }else{
                        handleCollision(new Location(j, i));
                    }
                    grid.setImage(new Location(j, i+1), null);
                }
            }
        }
        grid.setImage(new Location(userRow, 0), "alice.png");
        timesGet = 0;
        timesAvoid = 0;
        life=10;
    }
}