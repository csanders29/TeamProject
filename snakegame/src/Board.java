
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener
{
    public static int WindowSize = 300;
    public static int UnitSize = 5;
    public static int Units = 500;

    private int RandPos = 60;
    private int Delay = 120;
    private int booting=60;

    public static int[] x = new int[Units];
    public static int[] y = new int[Units];

    public static int snakeSize;
    private int fruitX;
    private int fruitY;
    private int fruitT;

    public static int playGame = 0;
    private Timer timer;

    public Board()
    {
        initBoard();
    }

    private void initBoard()
    {
        addKeyListener(new MyAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(WindowSize,WindowSize));
        startGame();

    }

    private void startGame() {
        snakeSize = 4;
        for(int i = 0; i<snakeSize; i++){
            x[i] = 25-i*10;
            y[i] = 25;
        }
        newFruit();
        timer = new Timer(Delay, this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    private  void score(Graphics g){
        g.setColor(Color.white);
        g.setFont( new Font("Verdana",Font.BOLD, 35));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+(snakeSize-4), (WindowSize- metrics1.stringWidth("Score: "+(snakeSize-4)))/2, g.getFont().getSize());

    }

    private void draw(Graphics g) {

        if(playGame<50) //star page
        {   g.setColor(Color.white);
            g.setFont( new Font("Verdana",Font.BOLD, 10));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            String line1="Welcome to Snake game";
            String line2="you use the direction keys to move the snake";
            String line3="different type of fruit gives you different POWERS!!";
            String line4="red fruit make you slower";
            String line5="blue fruit make you faster";
            String line6="yellow fruit make you nothing!!!";
            String line7="make by Rocky, China, Jessica";
            g.drawString( line1, (WindowSize- metrics1.stringWidth(line1))/2, g.getFont().getSize()+0);
            g.drawString( line2, (WindowSize- metrics1.stringWidth(line2))/2, g.getFont().getSize()+30);
            g.drawString( line3, (WindowSize- metrics1.stringWidth(line3))/2, g.getFont().getSize()+50);
            g.drawString( line4, (WindowSize- metrics1.stringWidth(line4))/2, g.getFont().getSize()+70);
            g.drawString( line5, (WindowSize- metrics1.stringWidth(line5))/2, g.getFont().getSize()+90);
            g.drawString( line6, (WindowSize- metrics1.stringWidth(line6))/2, g.getFont().getSize()+110);
            g.drawString( line7, (WindowSize- metrics1.stringWidth(line7))/2, g.getFont().getSize()+130);
            playGame++;
        }
        else if(playGame==50){
            //draw fruit
            score(g);
            if(fruitT==0)
                g.setColor(Color.yellow);//normal fruit
            else if(fruitT==1)
                g.setColor(Color.red);//slow fruit
            else
                g.setColor(new Color(164, 206, 251));//speed fruit
            g.fillRect(fruitX,fruitY,UnitSize,UnitSize);

            //draw snake:
            for(int i = 0;i<snakeSize; i++){
                //head is green
                if(i==0){
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i],y[i],UnitSize,UnitSize);
                }
                //body is light green
                else{
                    g.setColor(new Color(205, 250, 137));
                    g.fillRect(x[i], y[i], UnitSize, UnitSize);

                }
            }
          //  Toolkit.getDefaultToolkit().sync();

        }
        else {
            score(g);
            endGame(g);
        }
    }

    private void endGame(Graphics g) {

        String gameover = "GAME OVER";
        Font textFont = new Font("Verdana", Font.BOLD, 40);
        FontMetrics metr = getFontMetrics(textFont);

        g.setColor(Color.WHITE);
        g.setFont(textFont);
        g.drawString(gameover, (WindowSize - metr.stringWidth(gameover))/2, WindowSize/2);

    }

    private void checkFruit(){
        //is the fruit eaten
        if ((x[0] == fruitX) && (y[0]==fruitY)){
            if(fruitT==1)
                timer.setDelay(Delay+booting);//slow
            else if(fruitT==2&&0<(Delay-booting))
                timer.setDelay(Delay-booting);//faster
            snakeSize++;
            newFruit();
        }
    }

    private void newFruit() {
        fruitX = (int) (Math.random() * RandPos)*(UnitSize);
        fruitY = (int) (Math.random() * RandPos)*(UnitSize);
        fruitT = (int) (Math.random() * RandPos)%3;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(playGame==50){
            checkFruit();
            Snake.snakeDie();
            Snake.move();
        }
        repaint();
    }

    private class MyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){

            int key = e.getKeyCode();

            if((key == KeyEvent.VK_LEFT )&&(!Snake.Direct.equals("R")))
                Snake.Direct = "L";

            if((key == KeyEvent.VK_RIGHT )&&(!Snake.Direct.equals("L")))
                Snake.Direct = "R";

            if((key == KeyEvent.VK_UP )&&(!Snake.Direct.equals("D")))
                Snake.Direct = "U";

            if((key == KeyEvent.VK_DOWN )&&(!Snake.Direct.equals("U")))
                Snake.Direct = "D";
            }
    }


}
