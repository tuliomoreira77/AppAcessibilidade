package com.example.a06.trabalhoandroid.processamentoDeImagens;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public class TessOCR {
    private final TessBaseAPI mTess;
    Fetch fetch;

    public TessOCR(Context context, String language) {
        mTess = new TessBaseAPI();
        String datapath = context.getFilesDir() + "/tesseract/";
        String completeDatapath = datapath + "tessdata/";
        File folder = new File(datapath);
        File folder2 = new File(completeDatapath);
        File archive = new File(completeDatapath+language+".traineddata");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
            success = folder2.mkdirs();
            downloadFiles(context,completeDatapath,language);
        } else {
            if(!folder2.exists())
                success = folder2.mkdirs();
            else {
                if(!archive.exists())
                    downloadFiles(context, completeDatapath, language);
                else
                    mTess.init(datapath, language);
            }
        }
    }

    public void downloadFiles(Context context,String datapath, String language)
    {
        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(context)
                .setDownloadConcurrentLimit(3)
                .build();
        fetch = Fetch.Impl.getInstance(fetchConfiguration);

        String url = "https://github.com/tesseract-ocr/tessdata/raw/3.04.00/por.traineddata";

        final Request request = new Request(url, datapath+language+".traineddata");
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");
        FetchListener fetchListener = new FetchListener() {
            @Override
            public void onWaitingNetwork(@NotNull Download download) {

            }

            @Override
            public void onStarted(@NotNull Download download, @NotNull List<? extends DownloadBlock> list, int i) {

            }

            @Override
            public void onError(@NotNull Download download, @NotNull Error error, @Nullable Throwable throwable) {

            }

            @Override
            public void onDownloadBlockUpdated(@NotNull Download download, @NotNull DownloadBlock downloadBlock, int i) {

            }

            @Override
            public void onAdded(@NotNull Download download) {

            }

            @Override
            public void onQueued(@NotNull Download download, boolean waitingOnNetwork) {

            }

            @Override
            public void onCompleted(@NotNull Download download) {
                System.out.println("Acabei o Download");
                Toast.makeText(context, "Terminei", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(@NotNull Download download, long etaInMilliSeconds, long downloadedBytesPerSecond) {

            }

            @Override
            public void onPaused(@NotNull Download download) {

            }

            @Override
            public void onResumed(@NotNull Download download) {

            }

            @Override
            public void onCancelled(@NotNull Download download) {

            }

            @Override
            public void onRemoved(@NotNull Download download) {

            }

            @Override
            public void onDeleted(@NotNull Download download) {

            }
        };

        fetch.addListener(fetchListener);
        fetch.enqueue(request, updatedRequest -> {

        }, error -> {

        });

    }

    public String getOCRResult(Bitmap bitmap) {
        mTess.setImage(bitmap);
        try{
            return mTess.getUTF8Text();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void onDestroy() {
        if (mTess != null) mTess.end();
    }
}