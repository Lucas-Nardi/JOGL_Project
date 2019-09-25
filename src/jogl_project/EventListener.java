package jogl_project;

import com.jogamp.nativewindow.util.Point;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import java.util.ArrayList;

public class EventListener implements GLEventListener {

    public static Desenho curvas;             // VETOR QUE GUARDA TODAS AS CURVAS
    public static int qtdPonto;                   // QUANTOS PONTO EU TENHO NA TELA
    public static ArrayList<Point> TodosPontos = Jogl_Project.pontos;    // VETOR QUE GUARDA TODOS OS PONTOS QUE TEM NAS CURVAS
    public static int queDesenho = 0;                    // PEGA UMA CURVA DO VETOR DE CURVAS
    String poly;
    GLUT glut = new GLUT();
    boolean tenhoDesenho = false;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 1);    // COR DO BACKGROUD
        if (Jogl_Project.leuArquivo) {
            qtdPonto = TodosPontos.size();
        } else {
            qtdPonto = 0;
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glPointSize(5);
        String[] fonts = {"BitMap 9 by 15", "BitMap 8 by 13", // FONTE DO TEXTO QUE TERÄ O POLINOMIO

            "Times Roman 10 Point ", "Times Roman 24 Point ",
            "Helvetica 10 Point ", "Helvetica 12 Point ", "Helvetica 18 Point "};

        if (Jogl_Project.leuArquivo) { // JA TENHO O MEU VETOR DE PONTO PREENCHIDOS POIS PREECHI LENDO O ARQUIVO

            if (tenhoDesenho == false) {
                curvas = new Desenho(TodosPontos);
                curvas.setGl(gl);
                tenhoDesenho = true;
            }
            curvas.Draw(100); // QUANTOS PONTO TEM EM CADA CURVA + DESENHAR A CURVA
            pontosDeControle(gl); // EXIBI OS PONTOS DE CONTROLE

        } else {  // NAO TENHO O MEU VETOR DE PONTO PREENCHIDOS POIS, NAO LI O ARQUIVO

            if (qtdPonto < Jogl_Project.tamanho) {  // SO DESENHA OS PONTOS DE CONTROLE DEPOIS QUE EU COLOCAR O NUMERO DE PONTOS QUE O USUARIO PEDIU

                pontosDeControle(gl); // EXIBI OS PONTOS DE CONTROLE

            } else if (qtdPonto == Jogl_Project.tamanho) {  // PEGUEI OS PONTOS DO ARQUIVO  || PREENCHI TOSDOS OS PONTOS NECESSARIOS PARA CRIAR A CURVA

                if (MouseInput.criarCurva == true) {  // PERMITE DESENHA AS CURVAS POIS TENHO TODOS OS PONTOS QUE O USUARIO PEDIU

                    curvas = new Desenho(TodosPontos);
                    curvas.setGl(gl);
                    MouseInput.criarCurva = false;
                }
                curvas.Draw(100);  // DESENHA A PRIMEIRA CURVA
                pontosDeControle(gl); // EXIBI OS PONTOS DE CONTROLE

            }
        }
        // OBS FALTA CRIAR O POLINOMIO EM FORMA DE UMA STRING PARA APARECER NA TELA 
        
        if (KeyBoardInput.aparecerPolinomios) {  // APARECER O POLINOMIO 
            gl.glClearColor(1, 0, 0, 1);  // COR DO TEXTO  //            

            poly = "MEU POLINOMIO";
            gl.glRasterPos2d(80, 50); // POSIÇÃO X E Y QUE O TEXTO TERÁ
            glut.glutBitmapString(7, poly); //PLOTAR O DESENHO ONDE 7 É A FONTE E POLY É A STRING A SER MOSTRADA
            
            /*
                BITMAP_8_BY_13   3
                BITMAP_9_BY_15   2
                BITMAP_HELVETICA_10   6
                BITMAP_HELVETICA_12   7
                BITMAP_HELVETICA_18   8
                BITMAP_TIMES_ROMAN_10 4
                BITMAP_TIMES_ROMAN_24 5            
            */
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {  // MODO DE AJUSTE DE TELA

        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, 700, 500, 0, -1, 1);  // Pense que sua tela tenha 4 quadrantes onde
        gl.glMatrixMode(GL2.GL_MODELVIEW);           // O eixo do x tenha -320 --> 0 e  0 --> 320
        // E o eixo y tenha -180 --> 0 e 0 --> 180
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {

    }

    public void pontosDeControle(GL2 gl) {

        // EXIBIR OS PONTOS DE CONTROLE
        for (int i = 0; i < TodosPontos.size(); i++) {
            gl.glColor3d(1, 1, 1);// COR DOS PONTOS DE CONTROLE
            gl.glBegin(GL2.GL_POINTS);
            gl.glVertex2d(TodosPontos.get(i).getX(), TodosPontos.get(i).getY());
            gl.glEnd();
        }

    }
}
