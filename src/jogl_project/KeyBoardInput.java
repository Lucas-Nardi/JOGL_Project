package jogl_project;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static jogl_project.Jogl_Project.transformacoes;
import static jogl_project.EventListener.pontosOriginais;
import static jogl_project.Jogl_Project.qtdTransformacoes;

public class KeyBoardInput implements KeyListener {

    public static boolean mexerPonto = false;         // MEXE OS PONTOS DE CONTROLE DA CURVA
    private GLWindow window = Jogl_Project.getWindow();
    public static boolean aparecerPolinomios = false;
    public static int sumirFrase = 0;               // FAZ APARECER OU SUMIR POLINOMIO 
    public static int percorrerTrans = 0;              // PERCORRE O VETOR DE TRANSFORMAÇÃO
    public static boolean transformei = false;
    public static String poly = "Depois da transformação";
    

    public void EscreverArquivo() throws IOException {

        File arq = new File(Jogl_Project.nomeDoArquvio + ".obj");
        FileWriter fileW = null;
        BufferedWriter bufferW = null;
        int i = 0;
        String x, y, vertice, transformacao = null, etapa, parametro1, parametro2;

        if (!arq.exists()) { // ARQUIVO NAO EXISTE POR ISSO CRIO ELE
            arq.createNewFile();
        }
        fileW = new FileWriter(arq);
        bufferW = new BufferedWriter(fileW);

        bufferW.write("#--------------------------------------------------------------------------");
        bufferW.newLine();
        bufferW.write("# " + Jogl_Project.nomeDoArquvio + ".obj");
        bufferW.newLine();
        bufferW.write("# Pontos de controle para uma curva de grau " + (Jogl_Project.pontos.size() - 1));
        bufferW.newLine();
        bufferW.write("#--------------------------------------------------------------------------");
        bufferW.newLine();
        bufferW.write("# Lista de vértices:");
        bufferW.newLine();
        bufferW.write("#---------------------------------------------------------------------------");
        bufferW.newLine();
        bufferW.flush();

        for (i = 0; i < pontosOriginais.size(); i++) {

            x = Integer.toString(pontosOriginais.get(i).getX());
            y = Integer.toString(pontosOriginais.get(i).getY());
            System.out.println("Ponto " + i + " X: " + x + ",Y: " + y);
            vertice = "v\t" + x + "\t" + y;
            bufferW.write(vertice);
            bufferW.newLine();
            bufferW.flush();
        }
        bufferW.write("#---------------------------------------------------------------------------");
        bufferW.newLine();
        bufferW.write("# Transformações em 2D:");
        bufferW.newLine();
        bufferW.write("#---------------------------------------------------------------------------");
        bufferW.newLine();
        bufferW.flush();
        for (i = 0; i < qtdTransformacoes; i++) {

            parametro1 = Double.toString(transformacoes[i][1]); // PEGA O 
            parametro2 = Double.toString(transformacoes[i][2]);

            if (transformacoes[i][0] == 1) { // TRANSLAÇÃO

                transformacao = "t\t" + parametro1 + "\t" + parametro2 + "\t#Translação";

            } else if (transformacoes[i][0] == 2) { // ESCALONAMENTO

                transformacao = "s\t" + parametro1 + "\t" + parametro2 + "\t#Escalonamento";

            } else if (transformacoes[i][0] == 3) { // ROTAÇÃO 

                transformacao = "r\t" + parametro1 + "\t#Rotação de " + parametro1 + " graus";

            }
            bufferW.write(transformacao);
            bufferW.newLine();
            bufferW.flush();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_P) {  // MOSTRAR OS PONTOS DE CONTROLE COM UMA LINHA LIGANDO ELES

            if (sumirFrase == 0) {
                aparecerPolinomios = true;
                sumirFrase = 1;
            } else {
                aparecerPolinomios = false;
                sumirFrase = 0;
            }

        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) { // PODER MOVER OS PONTOS

            mexerPonto = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_Q || e.getKeyCode() == KeyEvent.VK_ESCAPE) { // FECHAR O PROGRAMA

            try {
                EscreverArquivo();
            } catch (IOException ex) {

            }
            window.getAnimator().stop();
            window.destroy();
        }
        if (e.getKeyCode() == KeyEvent.VK_F) { // DEIXAR FULL SCREEN
            window.setFullscreen(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_M) { // TIRAR O FULL SCREEN
            window.setFullscreen(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) { // COMEÇA AS TRANSFORMAÇÕES 
            
            if (percorrerTrans < qtdTransformacoes) {
                
                transformei = true;
                
                if(transformacoes[percorrerTrans][0] == 1){ // TRANSLAÇÃO TEXTO
                    
                    poly = "Transformcação de Translação no eixo x " + transformacoes[percorrerTrans][1] + " e no eixo y " + transformacoes[percorrerTrans][2];
                    
                }else if(transformacoes[percorrerTrans][0] == 2){ // ESCALONAMENTO TEXTO
                    
                    poly = "Transformcação de Scalonamento no eixo x " + transformacoes[percorrerTrans][1] + " e no eixo y " + transformacoes[percorrerTrans][2];
                   
                }else{ // ROTAÇÃO TEXTO
                    
                    poly = "Transformcação de Rotação de " + transformacoes[percorrerTrans][1] + " graus";
                }
                percorrerTrans++;  
            } else { // JÁ FIZ TODAS AS TRANSFORMAÇÕES LOGO, PRECISO VOLTAR PARA A CURVA PARA A POSIÇÃO ORIGINAL
                transformei = false;
                percorrerTrans = 0;               
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_CONTROL) { // PODER MOVER OS PONTOS

            mexerPonto = false;
            MouseInput.pegou = false;
        }
    }

}
