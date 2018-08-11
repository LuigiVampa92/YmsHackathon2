package com.luigivampa92.hackathon2uncompressed;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class PlayerCharacter {

    private int x;
    private int y;
    private int size;
    private Paint paint;

    public PlayerCharacter(int x, int size) {
        this.x = x;
        this.size = size;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(Constants.aircraftColor);
        paint.setStrokeWidth(12);
    }

    public void update(int x) {
        this.x += x;
    }

    public void draw(Canvas c) {
        int tailGap = size / 10;
        int winglength = size / 2;
        int wingSide = size / 4;
        int wingPeak = size / 20;
        int bodySize = size / 10;
        int bodyLen = size - size / 4;

        Path path = new Path();
        path.moveTo(x + size / 2, y + tailGap);

        path.lineTo(x + size, y);
        path.lineTo(x + size, y + winglength);
        path.lineTo(x + size - wingPeak, y + winglength + wingPeak);
        path.lineTo(x + size - wingPeak * 2, y + winglength);
        path.lineTo(x + size - wingPeak * 2, y + wingSide);
        path.lineTo(x + size / 2 + bodySize, y + wingSide + tailGap);

        path.lineTo(x + size / 2 + bodySize, y + bodyLen);
        path.lineTo(x + size / 2, y + size);
        path.lineTo(x + size / 2 - bodySize, y + bodyLen);

        path.lineTo(x + size / 2 - bodySize, y + wingSide + tailGap);
        path.lineTo(x + wingPeak * 2, y + wingSide);
        path.lineTo(x + wingPeak * 2, y + winglength);
        path.lineTo(x + wingPeak, y + winglength + wingPeak);
        path.lineTo(x, y + winglength);

        path.lineTo(x , y);

        path.lineTo(x + size / 2, y + tailGap); // back to start

        c.drawPath(path, paint);
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

