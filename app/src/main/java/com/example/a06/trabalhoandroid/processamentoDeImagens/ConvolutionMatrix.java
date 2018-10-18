package com.example.a06.trabalhoandroid.processamentoDeImagens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import org.jtransforms.fft.FloatFFT_2D;

public class ConvolutionMatrix
{
    public static void fastEdge(int imgInt[],int width,int height)
    {
        int lenghtReal = width*height;
        int lenghComp = lenghtReal*2;
        float[] dataComplex = new float[width*2*height];
        float[] kernelComplex = new float[width*2*height];
        float[] result = new float[width*2*height];

        int[] Gx = new int[lenghtReal];
        int[] Gy = new int[lenghtReal];

        float[] kernelX = {-1,0,1,-2,0,2,-1,0,1};
        float[] kernelY = {1,2,1,0,0,0,-1,-2,-1};

        float[] kernelBig = new float[width*2*height];
        float[] data = new float[lenghtReal];

        FloatFFT_2D floatFFT2D = new FloatFFT_2D(width,height);

        for(int i=0;i<lenghtReal;i++)
            data[i] = (float)(0.299 * Color.red(imgInt[i]) + 0.587 * Color.green(imgInt[i])
                        + 0.114 * Color.blue(imgInt[i]));
        //data[i] = Color.red(imgInt[i]);

        ComplexMath.createKernel(kernelX,kernelX.length,kernelBig, height, width);

        ComplexMath.real2Complex(kernelComplex,kernelBig,lenghComp);
        ComplexMath.real2Complex(dataComplex,data,lenghComp);

        floatFFT2D.complexForward(dataComplex);
        floatFFT2D.complexForward(kernelComplex);
        ComplexMath.complexMult(result,dataComplex,kernelComplex);

        floatFFT2D.complexInverse(result,true);
        ComplexMath.complexMag(Gx,result);

        ComplexMath.createKernel(kernelY,kernelY.length,kernelBig, height, width);

        ComplexMath.real2Complex(kernelComplex,kernelBig,lenghComp);
        floatFFT2D.complexForward(kernelComplex);
        ComplexMath.complexMult(result,dataComplex,kernelComplex);
        floatFFT2D.complexInverse(result,true);
        ComplexMath.complexMag(Gy,result);

        ComplexMath.vectorMag(imgInt,Gx,Gy);
    }

    public static void blur(int imgInt[], int width,int height)
    {
        float[] filter = {(float)(1.0/16),(float)(1.0/8),(float)(1.0/16),
                (float)(1.0/8), (float)(1.0/4),(float)(1.0/8),
                (float)(1.0/16),(float)(1.0/8),(float)(1.0/16)};
        ConvolutionMatrix.fastConvolution(imgInt,width,height,filter);
    }


    public static void fastConvolution(int imgInt[], int width,int height, float kernel[])
    {
        int lenghtReal = width*height;
        int lenghComp = lenghtReal*2;

        float[] dataComplex = new float[width*2*height];
        float[] result = new float[width*2*height];

        float[] kernelBig = new float[width*2*height];
        float[] data = new float[lenghtReal];

        FloatFFT_2D floatFFT2D = new FloatFFT_2D(width,height);

        /*for(int i=0;i<lenghtReal;i++)
            data[i] = (float)(0.299 * Color.red(imgInt[i]) + 0.587 * Color.green(imgInt[i])
                    + 0.114 * Color.blue(imgInt[i]));*/
        //data[i] = Color.red(imgInt[i]);

        ComplexMath.createKernel(kernel,kernel.length,kernelBig, height, width);

        float[] kernelComplex = new float[width*2*height];

        ComplexMath.real2Complex(kernelComplex,kernelBig,lenghComp);
        ComplexMath.real2Complex(dataComplex,data,lenghComp);

        floatFFT2D.complexForward(dataComplex);
        floatFFT2D.complexForward(kernelComplex);
        ComplexMath.complexMult(result,dataComplex,kernelComplex);
        floatFFT2D.complexInverse(result,true);

        //for(int i=0; i< lenghtReal;i++)
        //imgInt[i] = (int)dataComplex[i*2];
        //for(int i=0;i<lenghtReal;i++)
        //imgInt[i] = (int) result[i*2];
        ComplexMath.complexMag(imgInt,result);
    }
}