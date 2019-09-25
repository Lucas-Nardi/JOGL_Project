package jogl_project;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static jogl_project.EventListener.TodosPontos;
import static jogl_project.EventListener.curvas;
import static jogl_project.Jogl_Project.transformacoes;

public class KeyBoardInput implements KeyListener {

    public static boolean mexerPonto = false;         // MEXE OS PONTOS DE CONTROLE DA CURVA
    private GLWindow window = Jogl_Project.getWindow();        
    public static boolean aparecerPolinomios = false;
    public static int sumirFrase = 0;               // FAZ APARECER OU SUMIR POLINOMIO 
    private int percorrerTrans = 0;              // PERCORRE O VETOR DE TRANSFORMAÇÃO
    
     public  void LerArquivo() throws IOException{

        File arq = new File(Jogl_Project.nomeDoArquvio + ".obj");
        FileWriter fileW = null;
        BufferedWriter bufferW = null;
        int i = 0;
        String x,y,s;       
         
        if (arq.exists()) {
            fileW = new FileWriter(arq);
            bufferW = new BufferedWriter(fileW);
            for(i=0; i < TodosPontos.size(); i++){
                
                x = Integer.toString(TodosPontos.get(i).getX());
                y = Integer.toString(TodosPontos.get(i).getY());
                s = x+","+y;
                bufferW.write(s);
                bufferW.newLine();
                bufferW.flush();
            }            

        } else {
            arq.createNewFile();
            fileW = new FileWriter(arq);
            bufferW = new BufferedWriter(fileW);
            for(i=0; i < TodosPontos.size(); i++){
                
                x = Integer.toString(TodosPontos.get(i).getX());
                y = Integer.toString(TodosPontos.get(i).getY());
                s = x+","+y;
                bufferW.write(s);
                bufferW.newLine();
                bufferW.flush();
            }            
        }

    }        
    
    
    
    
    
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_P) {
            
            if(sumirFrase == 0){
                aparecerPolinomios = true;
                sumirFrase = 1;
            }else{
                aparecerPolinomios = false;
                sumirFrase = 0;
            }
            
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) { // PODER MOVER OS PONTOS

            mexerPonto = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_Q || e.getKeyCode() == KeyEvent.VK_ESCAPE) { 
            
            try {                        
                LerArquivo();
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
        if (e.getKeyCode() == KeyEvent.VK_N) { // COMEÇA UM CURVA DO ZERO
            
            EventListener.qtdPonto = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) { // COMEÇA AS TRANSFORMAÇÕES
            
            
            /* FALTA PREENCHER O VETOR DE TRANSFORMACAO
               E SETAR AS VARIAVEIS DAS TRANSFORMAÇÕES OU SEJA,
               PARA A ROTAÇÃO POR EXEMPLO, VOCE COLOCA QUE       curvas.setQueTransformacao(3)
               AI ELE VAI FAZER UMA ROTACAO JA ESTABELECIDA DE 45 GRAUS QUE TEM NA CLASSE DESENHA.
               PRECISAMOS TIRAR ESSA ROTACAO JÁ ESTABELECIDA
            */
            int queTransformacao =     transformacoes[percorrerTrans];            
            curvas.setQueTransformacao(queTransformacao);  // PERCORRER O VETOR DE TRANSFORMAÇÕES
            percorrerTrans++;
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
