package com.example.a06.trabalhoandroid;

public class ComplexMath {

    public static void real2Complex(float complexData[], float data[], int lenght)
    {
        for(int i=0;i<lenght/2;i++)
        {
            complexData[i*2] = data[i];
            complexData[i*2+1] = (float) 0.0;
        }
    }

    public static void complexMag(int result[], float data[])
    {
        for(int i=0; i < result.length;i++)
            result[i] = (int)Math.sqrt(data[i*2]*data[i*2] + data[i*2+1]*data[i*2+1]);
    }

    public static void complexMult(float complexData[], float complexN1[],float complexN2[])
    {
        for(int i=0; i<complexData.length/2; i++) {
            complexData[i*2] = (complexN1[i*2] * complexN2[i*2]) - (complexN1[i*2+1] * complexN2[i*2+1]);
            complexData[i*2+1] = complexN1[i*2] * complexN2[i*2+1] + complexN1[i*2+1] * complexN2[i*2];
        }
    }


}
