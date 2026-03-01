package com.example.focusflip;

import android.os.Handler;
import android.os.Looper;
import java.util.Locale;

// Timer that counts upward from 0 using Handler and Runnable
public class FocusTimer {

    public interface TimerCallback {
        void onTick(String formattedTime);
    }

    private int elapsedSeconds = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final TimerCallback callback;
    private boolean isRunning = false;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            elapsedSeconds++;
            callback.onTick(getFormattedTime());
            handler.postDelayed(this, 1000);
        }
    };

    public FocusTimer(TimerCallback callback) {
        this.callback = callback;
    }

    public void start() {
        if (isRunning) return;
        isRunning = true;
        handler.postDelayed(runnable, 1000);
    }

    public void pause() {
        isRunning = false;
        handler.removeCallbacks(runnable);
    }

    public void reset() {
        pause();
        elapsedSeconds = 0;
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    public String getFormattedTime() {
        int min = elapsedSeconds / 60;
        int sec = elapsedSeconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", min, sec);
    }
}
