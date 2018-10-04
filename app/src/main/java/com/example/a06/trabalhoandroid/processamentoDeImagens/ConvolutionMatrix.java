package com.example.a06.trabalhoandroid.processamentoDeImagens;

import android.graphics.Bitmap;
import android.graphics.Color;

import org.jtransforms.fft.FloatFFT_2D;

public class ConvolutionMatrix
{
    public static final int SIZE = 3;

    public double[][] Matrix;
    public double Factor = 1;
    public double Offset = 1;

    public ConvolutionMatrix(int size) {
        Matrix = new double[size][size];
    }

    public void setAll(double value) {
        for (int x = 0; x < SIZE; ++x) {
            for (int y = 0; y < SIZE; ++y) {
                Matrix[x][y] = value;
            }
        }
    }

    public void applyConfig(double[][] config) {
        for(int x = 0; x < SIZE; ++x) {
            for(int y = 0; y < SIZE; ++y) {
                Matrix[x][y] = config[x][y];
            }
        }
    }

    public static Bitmap fastEdge(Bitmap image)
    {
        int width = image.getWidth();
        int height = image.getHeight();
        int lenghtReal = width*height;
        int lenghComp = lenghtReal*2;
        int[] imgInt = new int[lenghtReal];

        float[] dataComplex = new float[width*2*height];
        float[] kernelComplex = new float[width*2*height];
        float[] result = new float[width*2*height];

        int[] Gx = new int[lenghtReal];
        int[] Gy = new int[lenghtReal];

        float[] kernelX = {-1,0,1,-1,0,2,-1,0,1};
        float[] kernelY = {1,2,1,0,0,0,-1,-2,-1};

        float[] kernelBig = new float[width*2*height];
        float[] data = new float[lenghtReal];

        FloatFFT_2D floatFFT2D = new FloatFFT_2D(width,height);

        image.getPixels(imgInt,0,width,0,0,width,height);

        for(int i=0;i<lenghtReal;i++)
            data[i] = (float)(0.299 * Color.red(imgInt[i]) + 0.587 * Color.green(imgInt[i]) + 0.114 * Color.blue(imgInt[i]));
        //data[i] = Color.red(imgInt[i]);

        ComplexMath.createKernel(kernelX,kernelX.length,kernelBig, height, width);

        ComplexMath.real2Complex(kernelComplex,kernelBig,lenghComp);
        ComplexMath.real2Complex(dataComplex,data,lenghComp);

        floatFFT2D.complexForward(dataComplex);
        floatFFT2D.complexForward(kernelComplex);
        ComplexMath.complexMult(result,dataComplex,kernelComplex);

        floatFFT2D.complexInverse(result,true);
        //ComplexMath.complexMag(Gx,result);

        ComplexMath.createKernel(kernelY,kernelY.length,kernelBig, height, width);

        ComplexMath.real2Complex(kernelComplex,kernelBig,lenghComp);
        floatFFT2D.complexForward(kernelComplex);
        ComplexMath.complexMult(result,dataComplex,kernelComplex);
        floatFFT2D.complexInverse(result,true);
        //ComplexMath.complexMag(Gy,result);

        ComplexMath.vectorMag(imgInt,Gx,Gy);

        for(int i=0;i<lenghtReal;i++) {
            int dataInt = imgInt[i];

            if(dataInt < 100)
                dataInt = 0;
            else
                dataInt = 255;
            
            imgInt[i] = 0xFF000000 | dataInt << 16 | dataInt << 8 | dataInt;
        }

        Bitmap imgFinal = Bitmap.createBitmap(width, height, image.getConfig());
        imgFinal.setPixels(imgInt,0,width,0,0,width,height);
        return imgFinal;
    }

    public static Bitmap fastConvolution(Bitmap image, float kernel[])
    {
        int width = image.getWidth();
        int height = image.getHeight();
        int lenghtReal = width*height;
        int lenghComp = lenghtReal*2;
        int[] imgInt = new int[lenghtReal];

        float[] dataComplex = new float[width*2*height];
        float[] result = new float[width*2*height];

        float[] kernelBig = new float[width*2*height];
        float[] data = new float[lenghtReal];

        FloatFFT_2D floatFFT2D = new FloatFFT_2D(width,height);

        image.getPixels(imgInt,0,width,0,0,width,height);

        for(int i=0;i<lenghtReal;i++)
            data[i] = (float)(0.299 * Color.red(imgInt[i]) + 0.587 * Color.green(imgInt[i]) + 0.114 * Color.blue(imgInt[i]));
        //data[i] = Color.red(imgInt[i]);

        ComplexMath.createKernel(kernel,kernel.length,kernelBig, height, width);

        float[] kernelComplex = new float[width*2*height];

        ComplexMath.real2Complex(kernelComplex,kernelBig,lenghComp);
        ComplexMath.real2Complex(dataComplex,data,lenghComp);

        floatFFT2D.complexForward(dataComplex);
        floatFFT2D.complexForward(kernelComplex);
        ComplexMath.complexMult(result,dataComplex,kernelComplex);

        floatFFT2D.complexInverse(result,true);

        Bitmap imgFinal = Bitmap.createBitmap(width, height, image.getConfig());

        //for(int i=0; i< lenghtReal;i++)
        //imgInt[i] = (int)dataComplex[i*2];
        //for(int i=0;i<lenghtReal;i++)
        //imgInt[i] = (int) result[i*2];
        ComplexMath.complexMag(imgInt,result);
        for(int i=0;i<lenghtReal;i++) {
            int dataInt = imgInt[i];

            imgInt[i] = 0xFF000000 | dataInt << 16 | dataInt << 8 | dataInt;
        }

        imgFinal.setPixels(imgInt,0,width,0,0,width,height);
        return imgFinal;
    }

    public static Bitmap computeConvolution3x3(Bitmap src, ConvolutionMatrix matrix) {
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B;
        int sumR, sumG, sumB;
        int[][] pixels = new int[SIZE][SIZE];

        for(int y = 0; y < height - 2; ++y) {
            for(int x = 0; x < width - 2; ++x) {

                // get pixel matrix
                for(int i = 0; i < SIZE; ++i) {
                    for(int j = 0; j < SIZE; ++j) {
                        pixels[i][j] = src.getPixel(x + i, y + j);
                    }
                }

                // get alpha of center pixel
                A = Color.alpha(pixels[1][1]);

                // init color sum
                sumR = sumG = sumB = 0;

                // get sum of RGB on matrix
                for(int i = 0; i < SIZE; ++i) {
                    for(int j = 0; j < SIZE; ++j) {
                        sumR += (Color.red(pixels[i][j]) * matrix.Matrix[i][j]);
                        sumG += (Color.green(pixels[i][j]) * matrix.Matrix[i][j]);
                        sumB += (Color.blue(pixels[i][j]) * matrix.Matrix[i][j]);
                    }
                }

                // get final Red
                R = (int)(sumR / matrix.Factor + matrix.Offset);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                // get final Green
                G = (int)(sumG / matrix.Factor + matrix.Offset);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                // get final Blue
                B = (int)(sumB / matrix.Factor + matrix.Offset);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // apply new pixel
                result.setPixel(x + 1, y + 1, Color.argb(A, R, G, B));
            }
        }

        // final image
        return result;
    }
}