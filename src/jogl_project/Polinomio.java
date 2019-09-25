package jogl_project;

import com.jogamp.nativewindow.util.Point;
import java.util.ArrayList;

public class Polinomio {

    private double resultX;
    private double resultY;

    public void pegarPonto(ArrayList <Point> p, double t) {
        
        double resultado = 0;
        double resultado2 = 0;
        int n = p.size()-1;        
        
        for(int i=0; i < p.size(); i++){
            
            resultado = resultado + Math.pow((1-t), (n - i)) * Math.pow(t, i) * p.get(i).getX(); // CALCULA O VALOR DE X
            
            resultado2 = resultado2 + Math.pow((1-t), (n - i)) * Math.pow(t, i) * p.get(i).getY(); // CALCULA O VALOR DE Y
        }
        resultX = resultado;
        resultY = resultado2;
    }

    public double getResultX() {
        return resultX;
    }

    public double getResultY() {
        return resultY;
    }    
    
}
