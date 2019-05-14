package curves;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 *
 *  Author Emil Walewski
 *
 **/


public class Window extends JPanel{

    public static void main(String[] args){

        JFrame window = new JFrame();
        window.setContentPane(new Window());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(new Dimension(500,500));
        window.setVisible(true);
    }

    public Window(){

        DrawCurves draw = new DrawCurves();

        setLayout(new BorderLayout(3,3));
        add(draw, BorderLayout.CENTER);


    }//end of window constructor

    class DrawCurves extends JPanel implements MouseListener{

        int clickCounter = 0;
        int[] xPos = new int[4];
        int[] yPos = new int[4];
        Shape[] line = new Shape[100];
        int lineCounter = 0;
        double funX, funY;
        boolean isset = false;

        DrawCurves(){
            addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {


            if (clickCounter <= 3) {
                xPos[clickCounter] = e.getX();
                yPos[clickCounter] = e.getY();
                //System.out.println(xPos[counter] + "  " + yPos[counter]);
                addPoint(new DrawPoint());
                clickCounter++;
                isset = true;
            }
            else{
                setCurves();
            }
            //place function here
        }

        public void  setCurves(){

            double[] dx = new double[13];
            double[] dy = new double[13];
            int i = 0;
            int j = 0;

            for (double t = 0; t <= 1; t += 0.1) {
                funX = xPos[0] * (Math.pow((1 - t), 3)) + 3 * xPos[1] * t * (Math.pow((1 - t), 2)) + 3 * xPos[2] * Math.pow(t, 2) * (1 - t) + xPos[3] * Math.pow(t, 3);
                funY = yPos[0] * (Math.pow((1 - t), 3)) + 3 * yPos[1] * t * (Math.pow((1 - t), 2)) + 3 * yPos[2] * Math.pow(t, 2) * (1 - t) + yPos[3] * Math.pow(t, 3);
                //DrawPoint dp = new DrawPoint();
                //dp.setPoints((funX,funY);

                //nowa sekcja

                dx[j] = funX;
                dy[j] = funY;
                j++;
                DrawLine dl = new DrawLine();
                if (t == 0) {
                    dl.setPoints((double)xPos[0],(double) yPos[0],dx[i],dy[i]);
                }
                else{

                    dl.setPoints(dx[i],dy[i],dx[i+1],dy[i+1]);
                    i++;
                }

                //koniec sekcji
                addCurv(dl);
            }
        }

        public void paint(Graphics g){

            super.paint(g);

            if (isset) {
                for (int i = 0; i < lineCounter; i++) {
                    Shape d = line[i];
                    d.draw(g);
                }
            }


        }

        public void addCurv(Shape shape){

            line[lineCounter] = shape;
            lineCounter++;
            repaint();

        }

        public void addPoint(Shape shape){

            shape.setPoints(xPos[clickCounter],yPos[clickCounter]);
            line[lineCounter] = shape;
            lineCounter++;
            repaint();
            System.out.println("dodano");
        }

        //------------------------------------------------------------
        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
        //------------------------------------------------------------
    }// end of Drawcurves class

    static abstract class Shape {

        double x, y;
        double x2,y2;

        public void setPoints(double x, double y){
            this.x = x;
            this.y = y;
        }


        public abstract void draw(Graphics g);

    }

    static class DrawLine extends Shape{


        public void setPoints(double x, double y, double x2, double y2){
            this.x = x;
            this.y = y;
            this.x2 = x2;
            this.y2 = y2;
        }

        public void draw(Graphics g){

                g.drawLine((int)x,(int)y,(int)x2,(int)y2);
        }

    }

    static class DrawPoint extends Shape{

        public void setPoints(double x, double y){
            this.x = x;
            this.y = y;
        }

        public void draw(Graphics g){

            g.drawOval((int)x,(int)y,5,5);
        }

    }
}
