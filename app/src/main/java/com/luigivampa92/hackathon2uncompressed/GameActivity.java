package com.luigivampa92.hackathon2uncompressed;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class GameActivity extends Activity implements View.OnClickListener {

    public static final String LVL = "lvl";
    String lvlName = null;
    SharedPreferences sharedPreferences;
    private GameView mGameView;
    private TerrainView backgroundView;
    private boolean mPaused;
    private Button mLeftButton;
    private Button mRightButton;
    private Button mPauseButton;
    private GameLoop animator;
    private BackgorundLoop backgorundAnimator;
    private final int FPS = 50;


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mGameView.mLeftPressed = true;
                mGameView.mRightPressed = false;
                return true;

            case MotionEvent.ACTION_UP:
                mGameView.mRightPressed = true;
                mGameView.mLeftPressed = false;
                return true;

            case MotionEvent.ACTION_MOVE:

                return true;
        }
        return true;
    }

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_game);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        lvlName = getIntent().getStringExtra(GameActivity.LVL);
        mGameView = (GameView) findViewById(R.id.gameView);
        mGameView.setBackgroundColor(Color.TRANSPARENT);
        backgroundView = findViewById(R.id.background);
        animator = new GameLoop(mGameView, FPS);
        backgorundAnimator = new BackgorundLoop(backgroundView, FPS);

//        mGameView.mLivesTextView = (TextView) findViewById(R.id.livesLabel);
//        mGameView.mScoreTextView = (TextView) findViewById(R.id.scoreLabel);
        mGameView.mGameLoop = animator;

//        mPauseButton = (Button) findViewById(R.id.pauseButton);
//        mPauseButton.setOnClickListener(this);
//
       /* mLeftButton = (Button) findViewById(R.id.leftButton);
        mLeftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mGameView.mLeftPressed = true;
                        return true;
                    case MotionEvent.ACTION_UP:
                        mGameView.mLeftPressed = false;
                        return true;
                }
                return false;
            }
        });

        mRightButton = (Button) findViewById(R.id.rightButton);
        mRightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mGameView.mRightPressed = true;
                        return true;
                    case MotionEvent.ACTION_UP:
                        mGameView.mRightPressed = false;
                        return true;
                }
                return false;
            }
        });
*/
/*        mLeftButton = (Button) findViewById(R.id.leftButton);
        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mPaused) {
                    mGameView.moveCharacter(-50);
                    mGameView.mCharacterPosition -= 50;
                    mGameView.invalidate();
                }
            }
        });

        mRightButton = (Button) findViewById(R.id.rightButton);
        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mPaused) {
                    mGameView.moveCharacter(50);
                    mGameView.mCharacterPosition += 50;
                    mGameView.invalidate();
                }
            }
        });*/

    }

    public void onResume() {
        super.onResume();
        loadPreferences();
        if (!animator.isRunning()) {
            animator.start();
        }
        if (!backgorundAnimator.isRunning()) {
            backgorundAnimator.start();
        }
    }

    @Override
    public void onClick(View view) {
        // need to respond to clicks, not touches??
//        switch(view.getId()) {
//            case R.id.pauseButton :
//                Log.d("GameView", "onClick called on mPauseButton.");
//                Log.d("GameView", "animator is running: " + animator.isRunning());
//                if (animator.isRunning()) {
//                    mPaused = true;
//                    animator.stop();
//                } else {
//                    mPaused = false;
//                    animator.start();
//                }
//                break;
//        }
    }

    public void onPause() {
        super.onPause();
        if (animator.isRunning()) {
            animator.stop();
        }
        if (backgorundAnimator.isRunning()) {
            backgorundAnimator.stop();
        }
    }

    public void onStop() {
        super.onStop();
        if (animator.isRunning()) {
            animator.stop();
        }
        if (backgorundAnimator.isRunning()) {
            backgorundAnimator.stop();
        }
    }

    public void loadPreferences() {
        if (lvlName.equals("normalGame")) {
            Constants.speed = Integer.parseInt(sharedPreferences.getString("normalSpeed", ""));
            String s = sharedPreferences.getString("normalColor", "");
            Constants.aircraftColor = getColor(s);
        }
        if (lvlName.equals("hardGame")) {
            Constants.speed = Integer.parseInt(sharedPreferences.getString("hardSpeed", ""));
            String s = sharedPreferences.getString("hardColor", "");
            Constants.aircraftColor = getColor(s);
        }
    }

    private int getColor(String s) {
        switch(s) {
            case "red":
                return Color.argb(128, 200, 50, 100);
            case "blue":
                return Color.argb(128, 50, 100, 200);
            case "orange":
                return Color.argb(128, 200, 100, 50);
            case "green":
                return Color.argb(128, 50, 200, 100);
        }
        return Color.argb(128, 200, 50, 100);
    }
}

