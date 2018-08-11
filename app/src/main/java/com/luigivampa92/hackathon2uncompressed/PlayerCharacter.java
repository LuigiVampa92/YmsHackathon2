package com.luigivampa92.hackathon2uncompressed;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class PlayerCharacter {

//    private Bitmap character;
    private int x;
    private int y;
    private int size;
    private Paint paint;

    public PlayerCharacter(int x, int size) {
//        character = bmp;
        this.x = x;
        this.size = size;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(12);
    }

    public void update(int x) {
        this.x += x;
    }

    public void draw(Canvas c) {
//        c.drawBitmap(character, x, c.getHeight() - size, p);
        c.drawOval(x - size /2, y - size / 2, x + size / 2, y + size /2, paint);
    }

    public int getWidth() {
        return size;
    }

    public int getHeight() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

