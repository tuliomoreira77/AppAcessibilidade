package com.example.a06.trabalhoandroid;

import android.graphics.Bitmap;
import android.graphics.Color;

public class DescobreCor {

    public static String getNome(int cor)
    {
        float hsv[] = new float[3];
        int hue,sat,val;
        Color.colorToHSV(cor,hsv);
        hue = (int) hsv[0];
        sat = (int) (hsv[1]*100);
        val = (int) (hsv[2]*100);
        String nomeCor = "Cor não detectável";

        if(sat <= 5 && val > 90){
            return "Branco";
        } else if(sat < 15){
            return "Cinza";
        } else if(val<10){
            return "Preto";
        } else if(hue <= 10){
            return "Vermelho";
        } else if(hue <= 40){
            return "Laranja";
        } else if(hue <= 70){
            return "Amarelo";
        } else if(hue <= 160){
            return "Verde";
        } else if(hue <= 210){
            return "Azul Claro";
        } else if(hue <= 250){
            return "Azul Escuro";
        } else if(hue <= 285){
            return "Roxo";
        } else if(hue <= 340){
            return "Rosa";
        } else if(hue <= 360){
            return "Vermelho";
        }
        return nomeCor;
    }

    public static String getNomePorId(int cor)
    {
        if(cor == 1){
            return "Branco";
        } else if(cor == 2){
            return "Cinza";
        } else if(cor == 3){
            return "Preto";
        } else if(cor == 4){
            return "Vermelho";
        } else if(cor == 5){
            return "Laranja";
        } else if(cor == 6){
            return "Amarelo";
        } else if(cor == 7){
            return "Verde";
        } else if(cor == 8){
            return "Azul Claro";
        } else if(cor == 9){
            return "Azul Escuro";
        } else if(cor == 10){
            return "Roxo";
        } else if(cor == 11){
            return "Rosa";
        } else if(cor == 12){
            return "Vermelho";
        }
        return "Cor não detectável";
    }

    static int getId(int cor)
    {
        float hsv[] = new float[3];
        int hue,sat,val;
        Color.colorToHSV(cor,hsv);
        hue = (int) hsv[0];
        sat = (int) (hsv[1]*100);
        val = (int) (hsv[2]*100);
        String nomeCor = "Cor não detectável";

        if(sat <= 5 && val > 90){
            return 1;
        } else if(sat < 15){
            return 2;
        } else if(val<10){
            return 3;
        } else if(hue <= 10){
            return 4;
        } else if(hue <= 40){
            return 5;
        } else if(hue <= 70){
            return 6;
        } else if(hue <= 160){
            return 7;
        } else if(hue <= 210){
            return 8;
        } else if(hue <= 250){
            return 9;
        } else if(hue <= 285){
            return 10;
        } else if(hue <= 340){
            return 12;
        } else if(hue <= 360){
            return 13;
        }
        return 0;
    }

    public static int contaPixels(Bitmap image, int tamanho)
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
                int cor = getId(image.getPixel(inicioX+i,inicioY+j));
                id[cor]++;
            }
        }
        for(int i=0; i < numCores()-1; i++)
        {
            if(id[i] < id[i+1])
                idMax = i+1;
        }

        return idMax;
    }

    static int numCores()
    {
        return 12;
    }
}
