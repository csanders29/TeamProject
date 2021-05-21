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
    private int RandPos = 9;
    private int Delay = 120;

    public static int[] x = new int[Units];
    public static int[] y = new int[Units];

    public static int snakeSize;
    private int fruitX;
    private int fruitY;

    public static boolean playGame = true;
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

    private void draw(Graphics g) {
        if(playGame){
            //draw fruit
            g.setColor(Color.red);
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
                    g.setColor(new Color(119, 255, 73));
                    g.fillRect(x[i], y[i], UnitSize, UnitSize);

                }
            }
            Toolkit.getDefaultToolkit().sync();

        }
        else {
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
            snakeSize++;
            newFruit();
        }
    }

    private void newFruit() {
        fruitX = (int) (Math.random() * RandPos)*(UnitSize);
        fruitY = (int) (Math.random() * RandPos)*(UnitSize);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(playGame){
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