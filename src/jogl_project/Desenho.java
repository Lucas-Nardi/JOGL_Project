package jogl_project;

import com.jogamp.nativewindow.util.Point;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;

public class Desenho {

    private ArrayList<Point> ctrlP;
    public static GL2 gl;
    private Polinomio poly;    
    private double red;
    private double green;
    private double blue;
    
    public Desenho(ArrayList<Point> PontosDeControle) {
        this.ctrlP = PontosDeControle;
        poly = new Polinomio();
        red = 1;
        green = 1;
        blue = 0;
    }

    public GL2 getGl() {
        return gl;
    }

    public void setGl(GL2 gl) {
        this.gl = gl;
    }

    public void Draw(double qtdPontos) {

        double t, t1;
        double px1, py1, px2, py2;

        for (int i = 0; i < qtdPontos; i++) {
            gl.glColor3d(red, green, blue);                    // QUAL É A COR DA CURVA
            gl.glLineWidth(3);                        // GROSSURA DA LINHA
            t = i / qtdPontos;
            t1 = (i + 1) / qtdPontos;
            poly.pegarPonto(ctrlP, t); // PEGAR O A POSICAO X e Y DO PONTO 1;
            px1 = poly.getResultX();
            py1 = poly.getResultY();
            poly.pegarPonto(ctrlP, t1); // // PEGAR O A POSICAO X e Y DO PONTO 2;
            px2 = poly.getResultX();
            py2 = poly.getResultY();
            gl.glBegin(GL2.GL_LINES); // Começa a desemnhas quadrilateros
                gl.glVertex2d(px1, py1);
                gl.glVertex2d(px2, py2);
            gl.glEnd();
        }
    }

    public void setRed(double red) {
    
       this.red = red;
    }

    public void setGreen(double green) {        

        this.green = green/255;        
    }

    public void setBlue(double blue) {        
        
        this.blue = blue/255;
    }   

    public Polinomio getPoly() {
        return poly;
    }

    public void setPoly(Polinomio poly) {
        this.poly = poly;
    }

}
