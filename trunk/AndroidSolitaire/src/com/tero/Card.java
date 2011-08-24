package com.tero;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Card {

	public int mX;
    public int mY;	
	public int mWidth;
	public int mHeight;
	public boolean mVisible = true;
	
	private Bitmap mBitmap;
	private Rect mRect;
	private int mOldX;
	private int mOldY;
	
    
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
	
	public void storePosition(int x, int y)
	{
        mOldX = x;
        mOldY = y;
	}
	
	public void cancelMove()
	{
		setPos(mOldX,mOldY);
	}
	
	public boolean isUnderTouch(int x, int y)
	{
		Log.v("rec", mRect.toShortString());		
		Log.v("x",Integer.toString(x));
		Log.v("y",Integer.toString(y));
		
		if (mRect.contains(x,y))
			return true;
		else
			return true; // TODO
	}
	
}
