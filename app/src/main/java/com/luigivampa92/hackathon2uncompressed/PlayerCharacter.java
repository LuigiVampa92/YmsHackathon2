package com.luigivampa92.hackathon2uncompressed;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class PlayerCharacter {

    private Bitmap character;
    private int x;
    private int y;
    private int size;

    public PlayerCharacter(Bitmap bmp, int x, int y, int size) {
        character = bmp;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void update(int y) {
        this.y += y;
    }

    public void draw(Canvas c, Paint p) {
        c.drawBitmap(character, x, y, p);
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

