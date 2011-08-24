package com.tero;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Card {

	private Bitmap mBitmap;
	public int mX;
    public int mY;	
	public int mWidth;
	public int mHeight;
    
	public Card(Resources res, int x, int y, int width, int height, int bmpResId) {
        mBitmap = BitmapFactory.decodeResource(res, bmpResId);
        mX = x;
        mY = y;
        mHeight = height;
        mWidth = width;
        // Load and scale bitmap
        Bitmap tmp = BitmapFactory.decodeResource(res, bmpResId);
        mBitmap = Bitmap.createScaledBitmap(tmp, mWidth, mHeight, true);        
    }
	
	
	public void doDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mX, mY, null);
    }	
	
	public void setPos(int x, int y)
	{
        mX = x;
        mY = y;
	}
	
	public boolean isUnderTouch(int x, int y)
	{
		// TODO:
		return true;
	}
	
}
