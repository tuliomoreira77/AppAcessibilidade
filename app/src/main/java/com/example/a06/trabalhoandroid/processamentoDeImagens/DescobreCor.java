package com.example.a06.trabalhoandroid.processamentoDeImagens;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.a06.trabalhoandroid.dados.Cor;

public class DescobreCor {

    public static int normalize(int cor)
    {
        float hsv[] = new float[3];
        int hue,sat,val;
        Color.colorToHSV(cor,hsv);
        hue = (int) hsv[0];
        sat = (int) (hsv[1]*100);
        val = (int) (hsv[2]*100);
        String nomeCor = "Cor não detectável";

        if(sat <= 10 && val > 75){
            return Color.WHITE;
        } else if(sat < 10 && val >= 20){
            return Color.GRAY;
        } else if(val< 30){
            return Color.BLACK;
        } else if(hue <= 10){
            return Color.RED;
        } else if(hue <= 40){
            return Color.RED;
        } else if(hue <= 70){
            return Color.YELLOW;
        } else if(hue <= 160){
            return Color.GREEN;
        } else if(hue <= 210){
            return Color.CYAN;
        } else if(hue <= 250){
            return Color.BLUE;
        } else if(hue <= 285){
            return Color.MAGENTA;
        } else if(hue <= 340){
            return Color.MAGENTA;
        } else if(hue <= 360){
            return Color.RED;
        }
        return Color.TRANSPARENT;
    }

    public static Cor getByValue(int cor)
    {
        float hsv[] = new float[3];
        int hue,sat,val;
        Color.colorToHSV(cor,hsv);
        hue = (int) hsv[0];
        sat = (int) (hsv[1]*100);
        val = (int) (hsv[2]*100);

        if(sat <= 5 && val > 90){
            return new Cor("Branco",0);
        } else if(sat < 15 && val>= 10){
            return new Cor("Cinza",1);
        } else if(val< 20){
            return new Cor("Preto", 2);
        } else if(hue <= 10){
            return new Cor("Vermelho",3);
        } else if(hue <= 40){
            return new Cor( "Laranja", 4);
        } else if(hue <= 70){
            return new Cor("Amarelo",5);
        } else if(hue <= 160){
            return new Cor("Verde",6);
        } else if(hue <= 210){
            return new Cor("Azul Claro",7);
        } else if(hue <= 250){
            return new Cor("Azul Escuro",8);
        } else if(hue <= 285){
            return new Cor("Roxo",9);
        } else if(hue <= 340){
            return new Cor("Rosa",10);
        } else if(hue <= 360){
            return new Cor("Vermelho",11);
        }

        return new Cor("Vazio", -1);
    }

    public static Cor getById(int id)
    {
        if(id == 0){
            return new Cor("Branco",0);
        } else if(id == 1){
            return new Cor("Cinza",1);
        } else if(id == 2){
            return new Cor("Preto", 2);
        } else if(id == 3){
            return new Cor("Vermelho",3);
        } else if(id == 4){
            return new Cor( "Laranja", 4);
        } else if(id == 5){
            return new Cor("Amarelo",5);
        } else if(id == 6){
            return new Cor("Verde",6);
        } else if(id == 7){
            return new Cor("Azul Claro",7);
        } else if(id == 8){
            return new Cor("Azul Escuro",8);
        } else if(id == 9){
            return new Cor("Roxo",9);
        } else if(id == 10){
            return new Cor("Rosa",10);
        } else if(id == 11){
            return new Cor("Vermelho",11);
        }

        return new Cor("Vazio", -1);
    }


    public static Cor contaPixels(Bitmap image, int tamanho)
    {
        int inicioX = image.getWidth()/2 - tamanho/2;
        int inicioY = image.getHeight()/2 - tamanho/2;
        int id[] = new int[DescobreCor.numCores()];
        int idMax = 0;

        for(int i=0;i<DescobreCor.numCores();i++)
            id[i] = 0;

        for(int i=0;i<tamanho;i++)
        {
            for(int j=0;j<tamanho;j++)
            {
                int cor = DescobreCor.getByValue(image.getPixel(inicioX+i,inicioY+j)).getId();
                id[cor]++;
            }
        }
        for(int i=0; i < numCores()-1; i++)
        {
            if(id[i] < id[i+1])
                idMax = i+1;
        }

        return DescobreCor.getById(idMax);
    }

    public static Bitmap normalizaImagem(Bitmap imagem)
    {
        Bitmap imgNor = imagem;
        for(int i=0; i < imgNor.getWidth(); i++)
        {
            for(int j=0; j < imgNor.getHeight(); j++)
            {
                int pixelNormalized = DescobreCor.normalize(imagem.getPixel(i,j));
                imgNor.setPixel(i,j,pixelNormalized);
            }
        }
        return imgNor;
    }

    public static Bitmap blur(Bitmap imagem)
    {
        ConvolutionMatrix blur = new ConvolutionMatrix(3);
        float[] filter = {(float)(1.0/16),(float)(1.0/8),(float)(1.0/16),(float)(1.0/8),(float)(1.0/4),(float)(1.0/8),(float)(1.0/16),(float)(1.0/8),(float)(1.0/16)};
        Bitmap imgBlur = ConvolutionMatrix.fastConvolution(imagem,filter);
        return imgBlur;
    }

    public static Bitmap edge(Bitmap imagem)
    {
        return null;
    }

    static int numCores()
    {
        return 12;
    }
}
