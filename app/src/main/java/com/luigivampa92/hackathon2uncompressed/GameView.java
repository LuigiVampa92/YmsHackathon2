package com.luigivampa92.hackathon2uncompressed;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameView extends View {

    private static final String TAG = "GameView";
    private int mScore;
    private int mLives;

    private Canvas canvas;

    private PlayerCharacter mCharacter;
    private ArrayList<Obstacle> mObstacles;
    private int chaSize;
    private int obsSize;

    private boolean mFirstDraw = true;
    public boolean mLeftPressed;
    public boolean mRightPressed;

    public TextView mLivesTextView;
    //    public TextView mScoreTextView;
    public GameLoop mGameLoop;

    private ExecutorService soundExecutor = Executors.newSingleThreadExecutor();

    private static final int LINE_SIZE = 12;
    private static final int NUM_LANES = 3;
    private ArrayList<Integer> lanes;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // Called during the first onDraw call
    public void initialize() {
        // Initialize variables
        mScore = 0;
        mLives = 5;
        chaSize = getWidth() / NUM_LANES - NUM_LANES * LINE_SIZE;
        obsSize = chaSize / 2;

        // Initialize ArrayList of possible Obstacle positions at each lane
        int incr = getWidth() / 6;
        lanes = new ArrayList<Integer>();
        for (int i = 0; i < NUM_LANES; i++) {
            lanes.add((int) ((incr + (incr * i * 2)) - obsSize / 2));
        }

        // Initializing Character
//        Bitmap mCharacterBmp = BitmapFactory.decodeResource(getResources(), R.drawable.character1);
//        mCharacterBmp = Bitmap.createScaledBitmap(mCharacterBmp, chaSize, chaSize, false);
        mCharacter = new PlayerCharacter(getWidth() / 2 - chaSize / 2, chaSize);

        // Initializing Obstacles
        mObstacles = new ArrayList<Obstacle>();
//        Bitmap mObstacleBmp = BitmapFactory.decodeResource(getResources(), R.drawable.broccoli);
//        mObstacleBmp = Bitmap.createScaledBitmap(mObstacleBmp, chaSize/3, chaSize/3, false);
        mObstacles.add(new Obstacle(getCoordinates(false), false, getHeight(), obsSize, lanes));
        mObstacles.add(new Obstacle(getCoordinates(false), false, getHeight(), obsSize, lanes));
        mObstacles.add(new Obstacle(getCoordinates(false), false, getHeight(), obsSize, lanes));

        // Initializing Donuts
//        Bitmap mDonutBmp = BitmapFactory.decodeResource(getResources(), R.drawable.donut1);
//        mDonutBmp = Bitmap.createScaledBitmap(mDonutBmp, obsSize, obsSize, false);
        mObstacles.add(new Obstacle(getCoordinates(true), true, getHeight(), obsSize, lanes));

//        Bitmap mDonutBmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.donut9);
//        mDonutBmp2 = Bitmap.createScaledBitmap(mDonutBmp2, obsSize, obsSize, false);
        mObstacles.add(new Obstacle(getCoordinates(true), true, getHeight(), obsSize, lanes));

//        Bitmap mDonutBmp3 = BitmapFactory.decodeResource(getResources(), R.drawable.donut8);
//        mDonutBmp3 = Bitmap.createScaledBitmap(mDonutBmp3, obsSize, obsSize, false);
        mObstacles.add(new Obstacle(getCoordinates(true), true, getHeight(), obsSize, lanes));
    }

    // Check collisions between Character and Obstacles
    private boolean checkCollisions(Obstacle obs, PlayerCharacter cha) {
        int chaLeft = cha.getX();
        int chaRight = chaLeft + cha.getWidth();
        int obsLeft = obs.getX();
        int obsRight = obsLeft + obs.getWidth();

        if ((chaLeft < obsLeft && chaRight > obsRight) || (chaLeft >= obsLeft && chaLeft < obsRight)
                || (chaRight <= obsRight && chaRight > obsLeft)) {
            if (cha.getHeight() >= obs.getY()) {
                obs.setCollided(true);
                if (!obs.isDonut()) {
                    mLives--;

                   //showScore(String.valueOf(mLives),26);
                    //mLivesTextView.setText(String.valueOf("Lives:" + mLives));
                    soundExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            playSound(2000, 44100);
                        }
                    });
                }
                if (obs.isDonut()) {
                    mScore++;
                   // showScore(String.valueOf(mScore),26);
                    soundExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            playSound(400, 44100);
                        }
                    });
                }
                return true;
            }
        }
        return false;
    }

    // Check collisions between obstacles and donuts
    private boolean checkCollisions(int y, Obstacle other) {
        int top = y;
        int bottom = top + obsSize;
        int otherTop = other.getY();
        int otherBottom = otherTop + other.getHeight();

        if ((bottom >= otherTop && bottom < otherBottom) || (top >= otherTop && top < otherBottom)
                || (top < otherTop && bottom > otherBottom)) {
            return true;
        }
        return false;
    }

    // Grab x and y coordinates for an Obstacle
    private int[] getCoordinates(boolean isDonut) { // todo
        int[] coordinates = new int[2];
        int x = lanes.get((int) (Math.random() * NUM_LANES));

        // Randomly get a y-location for the obstacles
        int offset = chaSize + obsSize;
        int range = getHeight();
        int y = (int) (Math.random() * range) - offset;
        y = checkSafe(0, mObstacles.size(), x, y, range, offset, isDonut);

        coordinates[0] = x;
        coordinates[1] = y;

        // Return coordinates for the new obstacle

        Log.d("YMSH_DEBUG", String.format("[ x = %d , y = %d ]", x, y));
        return coordinates;
    }

    // Check to ensure no objects overlap each other
    private int checkSafe(int start, int end, int x, int y, int range, int offset, boolean isDonut) {
        int tempY = y;

        for (int i = start; i < end; i++) {
            Obstacle other = mObstacles.get(i);
            int otherX = other.getX();
            boolean otherIsDonut = other.isDonut();

            // Check that new obstacle will not overlap with any of the already created obstacles
            if (x == otherX && checkCollisions(tempY, other)) {
                Log.d(TAG, i + " THIS X: " + x + " THIS Y: " + tempY + " ++ obs X: " + otherX + " obs Y: " + other.getY());
            }

            // Check that 2 broccoli obstacles are not directly besides each other
            if (!isDonut && !otherIsDonut && (((int) Math.abs(x - otherX)) == (lanes.get(1) - lanes.get(0)))) {
                tempY = checkSafe(0, i, x, (int) (Math.random() * range) - offset, range, offset, isDonut);
            }
        }

        return tempY;
    }

    private void gameOver() {
       mGameLoop.gameOver = true;
       mGameLoop.stop();



        // TODO save to high scores list
    }

    public void moveCharacter(int distance) {
        mCharacter.update(distance);
    }


    public void showText(String text, int fontSize, int x, int y) {

        Paint fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setTextSize(fontSize);
        fontPaint.setStyle(Paint.Style.STROKE);
        fontPaint.setColor(Color.WHITE);


        // ширина текста
        float width = fontPaint.measureText(text);

        // посимвольная ширина
        float[] widths = new float[text.length()];
        fontPaint.getTextWidths(text, widths);

        // вывод текста
        canvas.drawText(text, x, y, fontPaint);
    }


    public void onDraw(Canvas canvas) {
        this.canvas = canvas;

        showText("Score: " + mScore, 50, 50, 50);
        showText("Lives: " + mLives, 50, 50, 100);


        if(mLives == 0){
            showText("Game Over", 50, canvas.getWidth()/2-60, canvas.getHeight()/2);
        }
        // Move character
        if (mLeftPressed && !(mCharacter.getX() < 1)) {
//            Log.d(TAG, "insde mLeft X-COOR " + mCharacter.getX());
            moveCharacter(-20);
        }
        if (mRightPressed && (mCharacter.getX() < canvas.getWidth() - mCharacter.getWidth())) {
//            Log.d(TAG, "insde mRight Y-COOR " + mCharacter.getX());
            moveCharacter(20);
        }


//        mScoreTextView.setText(String.valueOf("Score: " + mScore));
        if (mFirstDraw) {
            initialize();
//           mLivesTextView.setText(String.valueOf("Lives:" + mLives));
//            mScoreTextView.setText(String.valueOf("Score: " + mScore));
            mFirstDraw = false;
        }

        // Check if character has hit any obstacles
        for (int i = 0; i < mObstacles.size(); i++) {
            Obstacle obs = mObstacles.get(i);
//            Log.d(TAG, i+ " obs coord" + obs.getX() + "     " + obs.getY() + "-" + (obs.getY() + obs.getHeight()));
            if (!obs.getCollided() && checkCollisions(obs, mCharacter)) {
                if (mLives <= 0) {
                    gameOver();
                }
            }
        }

        // Update both the obstacles and donuts
        for (int i = 0; i < mObstacles.size(); i++) {
            Obstacle obs = mObstacles.get(i);
            obs.update();
        }

        // Set paint object attributes
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(LINE_SIZE);

        // Draw the obstacles and donuts
        for (int i = 0; i < mObstacles.size(); i++) {
            Obstacle obs = mObstacles.get(i);
            obs.draw(canvas, paint);
        }

        // Draw road lines
//        canvas.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight(), paint);
//        canvas.drawLine(getWidth() / 3 * 2, 0, getWidth() / 3 * 2, getHeight(), paint);

        // Draw character
//        mCharacter.draw(canvas, paint);
        mCharacter.draw(canvas);
    }

    private void playSound(double frequency, int duration) {
        // AudioTrack definition
        int mBufferSize = AudioTrack.getMinBufferSize(44100,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT);

        AudioTrack mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                mBufferSize, AudioTrack.MODE_STREAM);

        // Sine wave
        double[] mSound = new double[4410];
        short[] mBuffer = new short[duration];
        for (int i = 0; i < mSound.length; i++) {
            mSound[i] = Math.sin((2.0 * Math.PI * i / (44100 / frequency)));
            mBuffer[i] = (short) (mSound[i] * Short.MAX_VALUE);
        }

        mAudioTrack.setStereoVolume(AudioTrack.getMaxVolume(), AudioTrack.getMaxVolume());
        mAudioTrack.play();

        mAudioTrack.write(mBuffer, 0, mSound.length);
        mAudioTrack.stop();
        mAudioTrack.release();

    }
}
