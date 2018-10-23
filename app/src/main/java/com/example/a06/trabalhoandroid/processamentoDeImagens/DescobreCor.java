package com.example.a06.trabalhoandroid.processamentoDeImagens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;

import com.example.a06.trabalhoandroid.dados.Cor;

public class DescobreCor {

    public static int normalizaCor(int cor)
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


    public static Cor contaPixels(Bitmap image)
    {
        int id[] = new int[DescobreCor.numCores()];
        int idMax = 0;

        int width = image.getWidth();
        int height = image.getHeight();
        int lenghtReal = width*height;

        int[] imgInt = new int[lenghtReal];
        image.getPixels(imgInt,0,width,0,0,width,height);

        for(int i=0;i<DescobreCor.numCores();i++)
            id[i] = 0;

        for(int i=0;i<lenghtReal;i++)
        {
            int cor = DescobreCor.getByValue(imgInt[i]).getId();
            id[cor]++;
        }
        for(int i=0; i < numCores(); i++)
        {
            if(id[idMax] < id[i])
                idMax = i;
        }

        return DescobreCor.getById(idMax);
    }

    public static Bitmap normalize(Bitmap image)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        int lenghtReal = width*height;

        int[] imgInt = new int[lenghtReal];
        image.getPixels(imgInt,0,width,0,0,width,height);

        int[] imgRed = new int[lenghtReal];
        int[] imgBlue = new int[lenghtReal];
        int[] imgGreen = new int[lenghtReal];

        for(int i=0;i<lenghtReal;i++) {
            imgRed[i] = Color.red(imgInt[i]);
            imgGreen[i] = Color.green(imgInt[i]);
            imgBlue[i] = Color.blue(imgInt[i]);
        }

        for(int i=0;i<lenghtReal;i++) {

            imgRed[i] = 255*imgRed[i]/(imgRed[i] + imgBlue[i] + imgGreen[i]);
            imgGreen[i] = 255*imgGreen[i]/(imgRed[i] + imgBlue[i] + imgGreen[i]);
            imgBlue[i] = 255*imgBlue[i]/(imgRed[i] + imgBlue[i] + imgGreen[i]);

            imgInt[i] = 0xFF000000 | imgRed[i] << 16 | imgGreen[i] << 8 | imgBlue[i];
        }

        Bitmap imgFinal = Bitmap.createBitmap(width, height, image.getConfig());
        imgFinal.setPixels(imgInt,0,width,0,0,width,height);
        return imgFinal;
    }

    public static Bitmap blur(Bitmap image)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        int lenghtReal = width*height;

        int[] imgInt = new int[lenghtReal];
        image.getPixels(imgInt,0,width,0,0,width,height);

        int[] imgRed = new int[lenghtReal];
        int[] imgBlue = new int[lenghtReal];
        int[] imgGreen = new int[lenghtReal];

        for(int i=0;i<lenghtReal;i++) {
            imgRed[i] = Color.red(imgInt[i]);
            imgGreen[i] = Color.green(imgInt[i]);
            imgBlue[i] = Color.blue(imgInt[i]);
        }
        ConvolutionMatrix.blur(imgRed,width,height);
        ConvolutionMatrix.blur(imgGreen,width,height);
        ConvolutionMatrix.blur(imgBlue,width,height);

        for(int i=0;i<lenghtReal;i++) {

            imgInt[i] = 0xFF000000 | imgRed[i] << 16 | imgGreen[i] << 8 | imgBlue[i];
        }

        Bitmap imgFinal = Bitmap.createBitmap(width, height, image.getConfig());
        imgFinal.setPixels(imgInt,0,width,0,0,width,height);
        return imgFinal;
    }

    public static Bitmap edge(Bitmap image)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        int lenghtReal = width*height;

        int[] imgInt = new int[lenghtReal];

        image.getPixels(imgInt,0,width,0,0,width,height);
        ConvolutionMatrix.fastEdge(imgInt,width,height);
        for(int i=0;i<lenghtReal;i++) {
            int dataInt = imgInt[i];

            if(dataInt < 100)
                imgInt[i] = 0x00000000 | dataInt << 16 | dataInt << 8 | dataInt;
            else
                imgInt[i] = 0xFF000000 | 255 << 16 | 255 << 8 | 255;
        }

        Bitmap imgEdg = Bitmap.createBitmap(width, height, image.getConfig());
        imgEdg.setPixels(imgInt,0,width,0,0,width,height);
        return imgEdg;
    }

    static int numCores()
    {
        return 12;
    }
}
