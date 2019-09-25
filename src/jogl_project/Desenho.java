package jogl_project;

import com.jogamp.nativewindow.util.Point;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;

public class Desenho {

    private ArrayList<Point> ctrlP;
    private GL2 gl;
    private Polinomio poly;
    private int queTransformacao;
    private int qtdTransformacoes = 0;

    public Desenho(ArrayList<Point> PontosDeControle) {
        this.ctrlP = PontosDeControle;
        poly = new Polinomio();
        queTransformacao = 0;
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

        if (queTransformacao == 1) {  // TRANSFORMAÇÃO ROTAÇÃO
            
            gl.glPushMatrix();   // SALVAR OS PONTOS ANTES DA TRANSLAÇÃO
            gl.glTranslated(45, 0, 1);
            queTransformacao = 0;          // SÓ FAZ A TRANSLAÇÃO UMA VEZ
            qtdTransformacoes++;  // QUANTAS TRANSFORMAÇÕES EU FIZ
            
        }else if (queTransformacao == 2) { // TRANSFORMAÇÃO ESCALONAR
            
            gl.glPushMatrix();   // SALVAR OS PONTOS ANTES DE ESCALONAR
            gl.glScaled(1, 0.6, 1);
            queTransformacao = 0;          // SÓ ALTERA A ESCALA UMA VEZ
            qtdTransformacoes++;  // QUANTAS TRANSFORMAÇÕES EU FIZ
            
        }else if (queTransformacao == 3) { // TRANSFORMAÇÃO ROTAÇÃO
            
            gl.glPushMatrix();   // SALVAR OS PONTOS ANTES DA ROTAÇÃO
            gl.glRotated(45, 0, 0, 1);
            queTransformacao = 0;          // SÓ ROTACIONAR UMA VEZ
            qtdTransformacoes++;  // QUANTAS TRANSFORMAÇÕES EU FIZ
            
        }else if (queTransformacao == -1) { // RETORNAR OS PONTOS ORIGINAIS SEM TRANSFORMAÇÕES
            
            for(int i = 0; i < qtdTransformacoes; i++){
                gl.glPopMatrix();
            }
        }

        for (int i = 0; i < qtdPontos; i++) {
            gl.glColor3d(1, 1, 0);                    // QUAL É A COR DA CURVA
            gl.glLineWidth(3);                        // GROSSURA DA LINHA
            t = i / qtdPontos;
            t1 = (i + 1) / qtdPontos;
            poly.pegarPonto(ctrlP, t); // PEGAR O A POSICAO X e Y DO PONTO 1;
            px1 = poly.getResultX();
            py1 = poly.getResultY();
            poly.pegarPonto(ctrlP, t1); // // PEGAR O A POSICAO X e Y DO PONTO 2;
            px2 = poly.getResultX();
            py2 = poly.getResultY();
            gl.glBegin(GL2.GL_POINTS); // Começa a desemnhas quadrilateros
            gl.glVertex2d(px1, py1);
            gl.glVertex2d(px2, py2);
            gl.glEnd();
        }
    }

    public int getQueTransformacao() {
        return queTransformacao;
    }

    public void setQueTransformacao(int transformei) {
        this.queTransformacao = transformei;
    }
    

    public Polinomio getPoly() {
        return poly;
    }

    public void setPoly(Polinomio poly) {
        this.poly = poly;
    }

}
