package com.luigivampa92.hackathon2uncompressed;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class Obstacle {
    private static final int SPEED = 5;

    private int x;
    private int y;
    private int resetY;
    private int size;

    private boolean isDonut;
    private boolean collided;
    private ArrayList<Integer> lanes;

    private Paint paintCoin;
    private Paint paintCoinSide;
    private Paint paintCoinText;
    private Paint paintStone;
    private Paint paintStoneDirt;

    public Obstacle(int[] coordinates, boolean isDonut, int canvasHeight,
                    int size, ArrayList<Integer> possibleLanes) {
        collided = false;

        this.x = coordinates[0];
        this.y = coordinates[1];

        this.isDonut = isDonut;
        this.resetY = canvasHeight;
        this.size = size;

        this.lanes = new ArrayList<Integer>();
        for (int i = 0; i < possibleLanes.size(); i++) {
            lanes.add(possibleLanes.get(i));
        }

        createPaints();
    }

    private void createPaints() {
        paintCoin = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCoin.setColor(0xffffce42);
        paintCoin.setStrokeWidth(12);

        paintCoinSide = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCoinSide.setColor(0xffffff00);
        paintCoinSide.setStrokeWidth(12);

        paintCoinText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCoinText.setColor(0xffaa9f10);
        paintCoinText.setTextSize(size / 2);
        paintCoinText.setStrokeWidth(12);

        paintStone = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStone.setColor(0xff555555);
        paintStone.setStrokeWidth(12);

        paintStoneDirt = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStoneDirt.setColor(0xcc222222);
        paintStoneDirt.setStrokeWidth(12);
    }

    public void update() {
        if (collided || y <= -getHeight()) {
            this.y = resetY;
            collided = false;

            int rand = (int) (Math.random() * 3);
            this.x = this.lanes.get(rand);
        }
        this.y -= 2 * SPEED; // todo
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getCollided() {
        return collided;
    }

    public void setCollided(boolean c) {
        collided = c;
    }

    public int getWidth() {
        return size;
    }

    public int getHeight() {
        return size;
    }

    public boolean isDonut() {
        return isDonut;
    }

    public void draw(Canvas c, Paint p) {
        if (isDonut) {
            drawCoin(c);
        }
        else {
            drawAsteroid(c);
        }
    }

    private void drawCoin(Canvas c) {
        int coinside = size / 8;
        c.drawOval(x - size / 2, y - size / 2, x + size / 2, y + size / 2, paintCoin);
        c.drawOval(x - size / 2 + coinside, y - size / 2 + coinside, x + size / 2 - coinside, y + size / 2 - coinside, paintCoinSide);

        int textg = size / 2 - size / 6;
        c.drawText("\u20bd",
                (int) (x - textg - ((paintCoinText.descent() + paintCoinText.ascent()) / 2)),
                (int) (y - ((paintCoinText.descent() + paintCoinText.ascent()) / 2)),
                paintCoinText
        );
    }

    private void drawAsteroid(Canvas c) {
        c.drawOval(x - size / 2, y - size / 2, x + size / 2, y + size / 2, paintStone);
        c.drawOval(x - size / 4, y - size / 4, x + size / 2 - size / 8, y + size / 2 - size / 4, paintStoneDirt);
    }
}


// луна если что =)
//         c.drawOval(x - size / 4, y - size / 4, x + size / 2, y + size / 2, paintStoneDirt);
