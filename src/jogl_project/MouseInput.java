package jogl_project;

import com.jogamp.nativewindow.util.Point;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import static jogl_project.EventListener.qtdPonto;
import static jogl_project.EventListener.pontosOriginais;

public class MouseInput implements MouseListener {

    private int x;   // QUAL É A POSICAO X DO MOUSE
    private int y;   // QUAL É A POSICAO Y DO MOUSE
    private int quePonto = 0;  // QUE PONTO SO VETOR DE PONTOS EU VOU ARRASTAR
    public static boolean criarCurva = false;    
    public static boolean pegou = false;
   

    @Override

    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent me) {
        Point p;
        if (KeyBoardInput.mexerPonto == false && Jogl_Project.leuArquivo == false) {  // DESENHAR TODOS OS PONTOS DA CURVA 
           
            if(EventListener.qtdPonto < Jogl_Project.tamanho ){
                
                p = new Point(x,y);
                EventListener.pontosOriginais.add(p);
                qtdPonto++;
            }
            if(qtdPonto == Jogl_Project.tamanho){
                criarCurva = true;
            }
            
        } else { // ESTOU PEGANDO O PONTO PARA ARRASTAR
            
            if (pegou == false) {
                for (int j = 0; j < EventListener.pontosOriginais.size(); j++) {
                    p = pontosOriginais.get(j);                    

                    if ((p.getX() >= (x - 22) && p.getX() <= (x + 22)) && (p.getY() >= (y - 22) && p.getY() <= (y + 22))) {
                        pegou = true;
                        quePonto = j;  // QUE PONTO DO VETOR (pontosOriginais) EU VOU ARRASTAR
                    }
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        
        x = me.getX();
        y = me.getY();        
        
        if (KeyBoardInput.mexerPonto == true && pegou == true) {          
            
            pontosOriginais.get(quePonto).setX(x); // ALTERO O X DO PONTO QUE EU PEGUEI COM AS COORDENADAS DO MOUSE
            pontosOriginais.get(quePonto).setY(y);  // ALTERO O Y DO PONTO QUE EU PEGUEI COM AS COORDENADAS DO MOUSE
        }

    }

    @Override
    public void mouseDragged(MouseEvent me) {

    }

    @Override
    public void mouseWheelMoved(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }    
    
}
