package com.luigivampa92.hackathon2uncompressed;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class GameActivity extends Activity implements View.OnClickListener {

    private GameView mGameView;
    private TerrainView backgroundView;
    private boolean mPaused;
    private Button mLeftButton;
    private Button mRightButton;
    private Button mPauseButton;
    private GameLoop animator;
    private BackgorundLoop backgorundAnimator;
    private final int FPS = 50;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_game);

        mGameView = (GameView) findViewById(R.id.gameView);
        mGameView.setBackgroundColor(Color.TRANSPARENT);
        backgroundView = findViewById(R.id.background);
        animator = new GameLoop(mGameView, FPS);
        backgorundAnimator = new BackgorundLoop(backgroundView, FPS);
        Log.d("GameView", "gv object: " + mGameView);

//        mGameView.mLivesTextView = (TextView) findViewById(R.id.livesLabel);
//        mGameView.mScoreTextView = (TextView) findViewById(R.id.scoreLabel);
        mGameView.mGameLoop = animator;

//        mPauseButton = (Button) findViewById(R.id.pauseButton);
//        mPauseButton.setOnClickListener(this);
//
        mLeftButton = (Button) findViewById(R.id.leftButton);
        mLeftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch(event.getAction()) {
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
                switch(event.getAction()) {
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
}

