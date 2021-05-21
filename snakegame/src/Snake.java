import javax.swing.*;
import java.awt.EventQueue;


public class Snake extends JFrame {

    public Snake(){
        initSnake();
    }

    private void initSnake() {
        add(new Board());

        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static String Direct = "D";

    static void move(){
        for(int i = Board.snakeSize;i>0; i--){
            Board.x[i] = Board.x[(i-1)];
            Board.y[i] = Board.y[i-1];
        }
        switch (Direct){
            case "U":
                Board.y[0] -= Board.UnitSize;
                break;
            case "D":
                Board.y[0] += Board.UnitSize;
                break;
            case "L":
                Board.x[0] -= Board.UnitSize;
                break;
            case "R":
                Board.x[0] += Board.UnitSize;
                break;
        }
    }
    static void snakeDie(){
        for(int i = Board.snakeSize; i>0;i--){
            if ((i>3)&&(Board.x[0]==Board.x[i])&&(Board.y[0]==Board.y[i])){
                Board.playGame = 3;
            }
        }
        if (Board.y[0] > Board.WindowSize || Board.y[0] < 0 || Board.x[0]>Board.WindowSize || Board.x[0]<0) {
            Board.playGame = 3;
        }

    }
    public static void main (String[] args){
        EventQueue.invokeLater(()->{
            JFrame frame = new Snake();
            frame.setVisible(true);
        });
    }
}
