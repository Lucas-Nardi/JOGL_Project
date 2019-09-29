package jogl_project;

import com.jogamp.nativewindow.util.Point;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Jogl_Project {                 // FALTA PREENCHER O VETOR QUE TEM TODAS AS TRANSFORMAÇÕES DO ARQUIVO OU PEDIR PRO USUARIO DIGITAR QUAIS TRANSFORMACOES ELE QUER PARA A SUA CURVA

    private static GLWindow window;
    public static int screenWidth = 700;
    public static int screenHeight = 500;
    public static float unitWide = 10;
    public static ArrayList<Point> pontos = new ArrayList<>();
    public static Scanner in = new Scanner(System.in);
    public static int tamanho;
    public static boolean leuArquivo = false;
    public static String nomeDoArquvio;
    public static double[][] transformacoes = new double [45][3];
    public static int qtdTransformacoes = 0;

    public static void LerArquivo(String nome) throws IOException {

        File arq = new File(nome + ".obj");
        FileReader fileR = null;
        BufferedReader bufferR = null;
        String readLine = "";
        String coordenadas[] = null;
        Point p;

        if (arq.exists()) {
            fileR = new FileReader(arq);
            bufferR = new BufferedReader(fileR);
            while (readLine != null) {
                readLine = bufferR.readLine();
                if (readLine == null) {
                    break;
                }
                                
                if (readLine.charAt(0) == 'v') { // PEGAR OS VERTICES DO ARQUIVO (PONTOS DA CURVA)

                    coordenadas = readLine.split("\t");
                    p = new Point();

                    p.setX(Integer.parseInt(coordenadas[1]));
                    p.setY(Integer.parseInt(coordenadas[2]));
                    pontos.add(p);
                
                }else if (readLine.charAt(0) == 't') { // PEGAR A TRANSFORMAÇÃO TRANSLAÇÃO 

                    coordenadas = readLine.split("\t");
                    transformacoes[qtdTransformacoes][0] =  1; // IDENTIFICA QUE É UMA TRANSFORMACAO DE TRANSLAÇÃO
                    transformacoes[qtdTransformacoes][1] = Double.parseDouble(coordenadas[1]); // TRANSLAÇÃO EM TORNO DO EIXO X
                    transformacoes[qtdTransformacoes][2] = Double.parseDouble(coordenadas[2]); // TRANSLAÇÃO EM TORNO DO EIXO Y
                    qtdTransformacoes++;
                
                }else if (readLine.charAt(0) == 's') { // PEGAR A TRANSFORMAÇÃO DE ESCALONAMENTO
  
                    coordenadas = readLine.split("\t");
                    transformacoes[qtdTransformacoes][0] =  2; // IDENTIFICA QUE É UMA TRANSFORMACAO DE ESCALONAMENTO
                    transformacoes[qtdTransformacoes][1] = Double.parseDouble(coordenadas[1]); // ESCALONAMENTO EM TORNO DO EIXO X
                    transformacoes[qtdTransformacoes][2] = Double.parseDouble(coordenadas[2]); // ESCALONAMENTO EM TORNO DO EIXO Y
                    qtdTransformacoes++;                
                
                } else if (readLine.charAt(0) == 'r') { // PEGAR A TRANSFORMAÇÃO DE ROTAÇÃO

                    coordenadas = readLine.split("\t");
                    transformacoes[qtdTransformacoes][0] =  3; // IDENTIFICA QUE É UMA TRANSFORMACAO DE ROTACAO
                    transformacoes[qtdTransformacoes][1] = Double.parseDouble(coordenadas[1]); // PEGA O ANGULO DA ROTAÇÃO
                    transformacoes[qtdTransformacoes][2] = 0 ; //ROTAÇÃO SÖ PRECIDA DO ANGULO
                    qtdTransformacoes++;
                }
            }
            tamanho = pontos.size();
            bufferR.close();
            fileR.close();

        } else {
            leuArquivo = false;
            arq.createNewFile();
            System.out.println("Arquivo nao encontrado.");
            System.out.println("Por isso esse arquivo " + nome + ".obj será aonde você salvará a curva que voce criará.");
            System.out.println("Quantos pontos voce deseja que sua curva possua?");
            tamanho = in.nextInt();
        }

    }

    public static void init() {
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        window = GLWindow.create(caps);
        window.setSize(screenWidth, screenHeight);
        window.setTitle("TESTE");
        window.setResizable(true);
        window.addGLEventListener(new EventListener());
        window.addKeyListener(new KeyBoardInput());
        window.addMouseListener(new MouseInput());
        FPSAnimator animator = new FPSAnimator(window, 60); // FAZER O LOOP a 60 FPF
        animator.start();
        window.setVisible(true);
    }

    public static int getWindowWidth() {
        return window.getWidth();
    }

    public static int getWindowHeight() {
        return window.getHeight();
    }

    public static GLWindow getWindow() {
        return window;
    }

    public static void main(String[] args) throws IOException {
        int resposta;

        do {
            System.out.println("Se quiser Ler o Arquivo digite 1");
            System.out.println("Se quiser plotar seu próprios pontos digite 2");
            resposta = in.nextInt();
            if (resposta < 1 || resposta > 2) {
                System.out.println("Numero errado, digite novamente.");
            }
        } while (resposta < 1 || resposta > 2);

        if (resposta == 1) {
            System.out.print("Nome do arquivo: ");
            nomeDoArquvio = in.next();
            leuArquivo = true;
            LerArquivo(nomeDoArquvio);

        } else {
            System.out.println("Quantos pontos voce deseja que sua curva possua?");
            tamanho = in.nextInt();
            System.out.print("Nome do arquivo que salvará esses pontos: ");
            nomeDoArquvio = in.next();
        }
        init();
    }
}
