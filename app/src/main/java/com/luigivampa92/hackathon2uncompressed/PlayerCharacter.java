package com.luigivampa92.hackathon2uncompressed;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class PlayerCharacter {
    private Bitmap character;
    private int x;
    private int y;
    private int size;

    public PlayerCharacter(Bitmap bmp, int x, int size) {
        character = bmp;
        this.x = x;
        this.size = size;
    }

    public void update(int x) {
        this.x += x;
    }

    public void draw(Canvas c, Paint p) {
        c.drawBitmap(character, x, 0, p);
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

