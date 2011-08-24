package com.tero;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Card {

	private Bitmap mBitmap;
	public int mX;
    public int mY;	
	public int mWidth;
	public int mHeight;
	
	private Rect mRect;
	public boolean mVisible = true;
    
	public Card(Resources res, int x, int y, int width, int height, int bmpResId) {
        mBitmap = BitmapFactory.decodeResource(res, bmpResId);
        mX = x;
        mY = y;
        mRect = new Rect(x,y,width,height);
        mHeight = height;
        mWidth = width;
        // Load and scale bitmap
        Bitmap tmp = BitmapFactory.decodeResource(res, bmpResId);
        mBitmap = Bitmap.createScaledBitmap(tmp, mWidth, mHeight, true);        
    }
	
	
	public void doDraw(Canvas canvas) {
        if (mVisible)
        	canvas.drawBitmap(mBitmap, mX, mY, null);
    }	
	
	public void setPos(int x, int y)
	{
        mX = x;
        mY = y;
        mRect.set(x, y, mRect.width(), mRect.height());
	}
	
	public boolean isUnderTouch(int x, int y)
	{
		if (mRect.contains(x,y))
			return true;
		else
			return true;
	}
	
}
