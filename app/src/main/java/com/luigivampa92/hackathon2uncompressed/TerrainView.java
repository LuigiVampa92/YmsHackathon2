package com.luigivampa92.hackathon2uncompressed;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import org.jetbrains.annotations.Nullable;

public class TerrainView extends View
{
	public TerrainView(Context context)
	{
		super(context);
	}

	public TerrainView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
	}

	public TerrainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public TerrainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		drawTerrain(canvas, 0xff7C07A9, 10.0, 1.0 , 1300, 100);
		drawTerrain(canvas, 0xff65247F, 10.0, 1.2 , 700, 100);
		drawTerrain(canvas, 0xff50026E, 10.0, 1.1777 , 300, 100);
	}

	private void drawTerrain(Canvas canvas, int color, double displace, double roughness, int h, int period) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getDisplay().getMetrics(displayMetrics);

		Paint blockPaint;
		blockPaint = new Paint();
		blockPaint.setColor(0x687C07A9);
		blockPaint.setStyle(Paint.Style.FILL);
		blockPaint.setAntiAlias(true);

		double[] list = terrain(new Double(displayMetrics.widthPixels) + 1000, new Double(displayMetrics.heightPixels) - h, displace, roughness);

		Path fillPath = new Path();
		fillPath.moveTo(0, 0);
		fillPath.lineTo(displayMetrics.widthPixels, 0);

		for (int i = 0; i < list.length - period; i += period) {
			fillPath.lineTo((float) list[i], i); // Final point
		}
		fillPath.lineTo(0, displayMetrics.heightPixels); // First point
		fillPath.lineTo(0, 0); // Same origin point
		canvas.drawPath(fillPath, blockPaint);
		blockPaint.setStyle(Paint.Style.FILL);
	}

	private double[] terrain(Double width, Double height, Double displace, Double roughness) {
		Integer power = Double.valueOf( Math.pow(2.0, Math.ceil(Math.log(width) / (Math.log(2.0)))) ).intValue();
		double[] points = new double[power+1];
		points[0] = (Double.valueOf(height / 2 + (Math.random() * displace * 2) - displace));
		points[power] = (Double.valueOf(height / 2 + (Math.random() * displace * 2) - displace));
		displace = displace * roughness;

		// Increase the number of segments
		for (int i = 1; i < power; i *= 2){
			// Iterate through each segment calculating the center point
			for (int j = (power / i) / 2; j < power; j += power / i){
				points[j] = ((points[j - (power / i) / 2] + points[j + (power / i) / 2]) / 2);
				points[j] += (Math.random()*displace*2) - displace;
			}
			// reduce our random range
			displace = displace * roughness;
		}
		return points;
	}

}
