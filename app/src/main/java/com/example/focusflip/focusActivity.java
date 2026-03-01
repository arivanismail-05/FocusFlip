package com.example.focusflip;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.focusflip.json.JsonStorageHelper;
import com.example.focusflip.json.StudySession;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class focusActivity extends AppCompatActivity
        implements FocusSensor.SensorCallback, FocusTimer.TimerCallback {

    // UI
    private TextView timerDisplay, statusText, lostFocusMinutes, congratsDetails;
    private MaterialCardView popupLostFocus, popupCongrats, popupExit;

    // Components
    private FocusSensor focusSensor;
    private FocusTimer focusTimer;

    // State
    private boolean isSessionStarted = false;
    private boolean isSessionComplete = false;
    private static final int SESSION_DURATION_SECONDS = 5; // Change to 1500 for 25 minutes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

        // Find views
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        timerDisplay = findViewById(R.id.timer_display);
        statusText = findViewById(R.id.status_text);
        lostFocusMinutes = findViewById(R.id.lost_focus_minutes);
        congratsDetails = findViewById(R.id.congrats_details);
        popupLostFocus = findViewById(R.id.popup_lost_focus);
        popupCongrats = findViewById(R.id.popup_congrats);
        popupExit = findViewById(R.id.popup_exit);

        // Init components
        focusTimer = new FocusTimer(this);
        focusSensor = new FocusSensor(this, this);

        // Toolbar back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBackPress();
            }
        });

        // Lost focus popup buttons
        findViewById(R.id.btn_save_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentSession();
                resetEverything();
            }
        });
        findViewById(R.id.btn_discard_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetEverything();
            }
        });

        // Congrats popup buttons
        findViewById(R.id.btn_save_session).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentSession();
                resetEverything();
            }
        });
        findViewById(R.id.btn_discard_session).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetEverything();
            }
        });

        // Exit popup buttons
        findViewById(R.id.btn_yes_leave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_no_stay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupExit.setVisibility(View.GONE);
            }
        });
    }

    // === SENSOR CALLBACKS ===

    @Override
    public void onFaceDown() {
        // If session is complete do nothing
        if (isSessionComplete) return;

        // If lost focus popup is showing, hide it and resume timer
        if (isSessionStarted && popupLostFocus.getVisibility() == View.VISIBLE) {
            hideAllPopups();
            statusText.setText("Focusing...");
            focusTimer.start();
            return;
        }

        // First time face-down, start the session
        if (!isSessionStarted) {
            isSessionStarted = true;
            statusText.setText("Focusing...");
            timerDisplay.setText("00:00");
            playStartSound();
            focusTimer.start();
        }
    }

    @Override
    public void onFaceUp() {
        // Do nothing if session has not started
        if (!isSessionStarted) return;
        // Do nothing if session is already complete
        if (isSessionComplete) return;

        // Session is active, pause and show lost focus popup
        focusTimer.pause();
        showLostFocusPopup();
    }

    // === TIMER CALLBACK ===

    @Override
    public void onTick(String formattedTime) {
        timerDisplay.setText(formattedTime);

        // Check if session duration reached, stop timer FIRST
        if (focusTimer.getElapsedSeconds() >= SESSION_DURATION_SECONDS) {
            focusTimer.pause();
            completeSession();
        }
    }

    // === SESSION LOGIC ===

    // Called when session duration is reached
    private void completeSession() {
        // If already complete do nothing
        if (isSessionComplete) return;

        // Mark complete first to prevent re-entry
        isSessionComplete = true;
        // Stop timer again in case it is still running
        focusTimer.pause();
        playFinishSound();
        showCongratsPopup();
    }

    // Save the current session to JSON
    private void saveCurrentSession() {
        String date = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        String username = getSharedPreferences("register", MODE_PRIVATE).getString("name", "User");
        int minutes = focusTimer.getElapsedSeconds() / 60;

        StudySession session = new StudySession(date, minutes, username);
        JsonStorageHelper.saveSession(this, session);
        Toast.makeText(this, "Session Saved!", Toast.LENGTH_SHORT).show();
    }

    // Reset everything back to initial state
    private void resetEverything() {
        focusTimer.reset();
        isSessionStarted = false;
        isSessionComplete = false;
        timerDisplay.setText("00:00");
        statusText.setText("Place your phone face-down to start");
        hideAllPopups();
    }

    // === POPUP HELPERS ===

    private void showLostFocusPopup() {
        hideAllPopups();
        int totalSeconds = focusTimer.getElapsedSeconds();
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        if (minutes > 0) {
            lostFocusMinutes.setText("You focused for " + minutes + "m " + seconds + "s");
        } else {
            lostFocusMinutes.setText("You focused for " + seconds + " seconds");
        }
        popupLostFocus.setVisibility(View.VISIBLE);
    }

    private void showCongratsPopup() {
        hideAllPopups();
        String date = new SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault()).format(new Date());
        String username = getSharedPreferences("register", MODE_PRIVATE).getString("name", "User");
        congratsDetails.setText("Date: " + date + "\nUser: " + username);
        popupCongrats.setVisibility(View.VISIBLE);
    }

    private void hideAllPopups() {
        popupLostFocus.setVisibility(View.GONE);
        popupCongrats.setVisibility(View.GONE);
        popupExit.setVisibility(View.GONE);
    }

    // === BACK BUTTON ===

    private void handleBackPress() {
        if (isSessionStarted && !isSessionComplete) {
            focusTimer.pause();
            hideAllPopups();
            popupExit.setVisibility(View.VISIBLE);
        } else {
            finish();
        }
    }

    // === SOUND ===

    // Short beep when session starts
    private void playStartSound() {
        try {
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 80);
            toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 300);
            // Release after tone finishes playing
            new Handler(Looper.getMainLooper()).postDelayed(toneGen::release, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Short beep when session finishes
    private void playFinishSound() {
        try {
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 80);
            toneGen.startTone(ToneGenerator.TONE_PROP_BEEP2, 300);
            // Release after tone finishes playing
            new Handler(Looper.getMainLooper()).postDelayed(toneGen::release, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // === LIFECYCLE ===

    @Override
    protected void onResume() {
        super.onResume();
        focusSensor.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        focusSensor.unregister();
    }
}
