package jogl_project;

import com.jogamp.nativewindow.util.Point;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import java.util.ArrayList;
import static jogl_project.Jogl_Project.transformacoes;
import static jogl_project.KeyBoardInput.percorrerTrans;
import static jogl_project.KeyBoardInput.poly;
import static jogl_project.KeyBoardInput.transformei;

public class EventListener implements GLEventListener {

    public static Desenho curvaTransformada;             // É A CURVA ORIGINAL QUE SOFRERA TRANSFORMAÇÕES SE EXISTIREM
    public static Desenho curvaAntesDaTrans;             // CURVA AS DA TRANSFORMAÇÃO
    public static int qtdPonto;                   // QUANTOS PONTO EU TENHO NA TELA
    public static ArrayList<Point> pontosOriginais = Jogl_Project.pontos;    // PONTOS DA CURVA PRINCIPAL    
    public static int queDesenho = 0;                    // PEGA UMA CURVA DO VETOR DE CURVAS
    GLUT glut = new GLUT();
    boolean tenhoDesenho = false;
    int percorrerPontos = 0;
    boolean criei2Telas = false;
    int i = 0;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 1);    // COR DO BACKGROUD
        if (Jogl_Project.leuArquivo) {
            qtdPonto = pontosOriginais.size();
        } else {
            qtdPonto = 0;
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glPointSize(5);

        if (Jogl_Project.leuArquivo) { // JA TENHO O MEU VETOR DE PONTO PREENCHIDOS POIS PREECHI LENDO O ARQUIVO

            if (tenhoDesenho == false) {
                curvaTransformada = new Desenho(pontosOriginais);
                curvaTransformada.setGl(gl);
                tenhoDesenho = true;
                curvaAntesDaTrans = new Desenho(pontosOriginais);
                curvaAntesDaTrans.setGl(gl);
                curvaAntesDaTrans.setRed(0);
                curvaAntesDaTrans.setGreen(255);
                curvaAntesDaTrans.setBlue(0);
            }

        } else {  // NAO TENHO O MEU VETOR DE PONTO PREENCHIDOS POIS, NAO LI O ARQUIVO

            if (qtdPonto < Jogl_Project.tamanho) {  // SO DESENHA A CURVA DEPOIS QUE EU COLOCAR O NUMERO DE PONTOS QUE O USUARIO PEDIU

                pontosDeControle(gl); // EXIBI OS PONTOS DE CONTROLE

            } else {  // PEGUEI OS PONTOS DO ARQUIVO  || PREENCHI TOSDOS OS PONTOS NECESSARIOS PARA CRIAR A CURVA

                if (MouseInput.criarCurva == true) {  // PERMITE DESENHA AS CURVAS POIS TENHO TODOS OS PONTOS QUE O USUARIO PEDIU

                    curvaTransformada = new Desenho(pontosOriginais);
                    curvaTransformada.setGl(gl);
                    MouseInput.criarCurva = false;
                    curvaAntesDaTrans = new Desenho(pontosOriginais);
                    curvaAntesDaTrans.setRed(0);
                    curvaAntesDaTrans.setGreen(255);
                    curvaAntesDaTrans.setBlue(0);
                    tenhoDesenho = true;
                }
            }
        }
       
        if (KeyBoardInput.transformei && tenhoDesenho == true) { // TENHO TRANSFORMAÇÕES LOGO, TENHO QUE MOSTRAR 2 CURVAS UMA ANTES E A OUTRA DEPOIS
        
            if (percorrerTrans - 1 >= 1) { // SEGUNDA TRANSFORMAÇÃO
                gl.glPushMatrix();
                for (int i = 0; i < percorrerTrans - 1; i++) { // PEGAR TODAS AS TRANSFORMAÇÕES ANTERIORES

                    if (transformacoes[i][0] == 1) { // TRANSLAÇÃO

                        gl.glTranslated(transformacoes[i][1], transformacoes[i][2], 1);

                    } else if (transformacoes[i][0] == 2) { // ESCALONAMENTO

                        gl.glScaled(transformacoes[i][1], transformacoes[i][2], 1);

                    } else { // ROTAÇÃO

                        gl.glRotated(transformacoes[i][1], 0, 0, 1);
                    }
                }

                gl.glViewport(0, 0, Jogl_Project.getWindowWidth() / 2, Jogl_Project.getWindowHeight()); // TELA DA ESQUERDA ANTES DA TRANSFORMAÇÃO
                gl.glColor3d(1, 0, 0);         // COR DO TEXTO
                gl.glRasterPos2d(25, 30); // POSIÇÃO X E Y QUE O TEXTO TERÁ
                glut.glutBitmapString(5, "Antes da Transformação " + percorrerTrans); //PLOTAR O DESENHO ONDE 9 É A FONTE E "Antes da transformação é o texto"    
                curvaAntesDaTrans.Draw(100); // DESENHA A CURVA    

                gl.glViewport(Jogl_Project.getWindowWidth() / 2, 0, Jogl_Project.getWindowWidth() / 2, Jogl_Project.getWindowHeight()); // DA DIREITA
                gl.glColor3d(1, 0, 0);
                gl.glRasterPos2d(25, 30);
                glut.glutBitmapString(5, poly);

                if (transformacoes[percorrerTrans - 1][0] == 1) { // TRANSLAÇÃO

                    gl.glTranslated(transformacoes[percorrerTrans - 1][1], transformacoes[percorrerTrans - 1][2], 1);

                } else if (transformacoes[percorrerTrans - 1][0] == 2) { // ESCALONAMENTO

                    gl.glScaled(transformacoes[percorrerTrans - 1][1], transformacoes[percorrerTrans - 1][2], 1);

                } else { // ROTAÇÃO

                    gl.glRotated(transformacoes[percorrerTrans - 1][1], 0, 0, 1);
                }

                curvaTransformada.Draw(100);
                gl.glLoadIdentity();
                gl.glPopMatrix();

            } else { // PRIMEIRA TRANSFORMAÇÃO

                gl.glPushMatrix();
                gl.glViewport(0, 0, Jogl_Project.getWindowWidth() / 2, Jogl_Project.getWindowHeight() - 70); // TELA DA ESQUERDA
                gl.glColor3d(1, 0, 0);         // COR DO TEXTO
                gl.glRasterPos2d(25, 30); // POSIÇÃO X E Y QUE O TEXTO TERÁ
                glut.glutBitmapString(5, "Antes da Transformação "); //PLOTAR O DESENHO ONDE 9 É A FONTE E "Antes da transformação é o texto"    
                curvaAntesDaTrans.Draw(100); // DESENHA A CURVA    

                gl.glViewport(Jogl_Project.getWindowWidth() / 2, 0, Jogl_Project.getWindowWidth() / 2, Jogl_Project.getWindowHeight() - 70); // DA DIREITA
                gl.glColor3d(1, 0, 0);
                gl.glRasterPos2d(25, 30);
                glut.glutBitmapString(5, poly);

                if (transformacoes[percorrerTrans - 1][0] == 1) { // TRANSLAÇÃO

                    gl.glTranslated(transformacoes[percorrerTrans - 1][1], transformacoes[percorrerTrans - 1][2], 1);

                } else if (transformacoes[percorrerTrans - 1][0] == 2) { // ESCALONAMENTO

                    gl.glScaled(transformacoes[percorrerTrans - 1][1], transformacoes[percorrerTrans - 1][2], 1);

                } else { // ROTAÇÃO

                    gl.glRotated(transformacoes[percorrerTrans - 1][1], 0, 0, 1);
                }
                curvaTransformada.Draw(100);
                gl.glLoadIdentity();
                gl.glPopMatrix();

            }

        } else if(KeyBoardInput.transformei == false && tenhoDesenho == true){ // VOLTAR PARA UMA CURVA 

            if (criei2Telas && KeyBoardInput.transformei == false) {               

                gl.glLoadIdentity();
                gl.glPopMatrix();
                criei2Telas = false;
            } else {
                criei2Telas = true;
                gl.glPushMatrix();
            }
            curvaTransformada.Draw(100); // QUANTOS PONTO TEM EM CADA CURVA + DESENHAR A CURVA
            pontosDeControle(gl);
        }

        if (KeyBoardInput.aparecerPolinomios && transformei == false) {  // APARECER O POLINOMIO 

            // CRIA UMA LINHA ENTRE O PONTO DE CONTROLE
            for (int i = 0; i < pontosOriginais.size() - 1; i++) {
                gl.glColor3d(0, 0, 1);// COR DOS PONTOS DE CONTROLE
                gl.glBegin(GL2.GL_LINES);
                gl.glVertex2d(pontosOriginais.get(i).getX(), pontosOriginais.get(i).getY());
                gl.glVertex2d(pontosOriginais.get(i + 1).getX(), pontosOriginais.get(i + 1).getY());
                gl.glEnd();
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {  // MODO DE AJUSTE DE TELA

        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, Jogl_Project.getWindowWidth(), Jogl_Project.getWindowHeight(), 0, -1, 1);  // Pense que sua tela tenha 4 quadrantes onde
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {

    }

    public void pontosDeControle(GL2 gl) {

        // EXIBIR OS PONTOS DE CONTROLE
        for (int i = 0; i < pontosOriginais.size(); i++) {
            gl.glColor3d(1, 1, 1);// COR DOS PONTOS DE CONTROLE
            gl.glBegin(GL2.GL_POINTS);
            gl.glVertex2d(pontosOriginais.get(i).getX(), pontosOriginais.get(i).getY());
            gl.glEnd();
        }

    }
}
