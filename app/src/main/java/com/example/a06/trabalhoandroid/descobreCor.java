package com.example.a06.trabalhoandroid;

import android.graphics.Color;

public class descobreCor {

    static String getNome(int cor)
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
}
