package com.example.a06.trabalhoandroid;

import android.speech.tts.TextToSpeech;
import org.w3c.dom.Text;

public class TtsData {

    private String data;
    private boolean init;
    private TextToSpeech tts;

    public TtsData(TextToSpeech tts)
    {
        this.tts = tts;
    }

    public void setData(String data) {
        this.data = data;
        if(init)
            tts.speak(data, TextToSpeech.QUEUE_FLUSH, null);
    }

    public String getData() {
        return data;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public boolean isInit() {
        return init;
    }

    public void setTts(TextToSpeech tts) {
        this.tts = tts;
    }

}
