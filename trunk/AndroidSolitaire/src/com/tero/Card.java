package com.tero;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Card {

	public int mX;
    public int mY;	
	public int mWidth;
	public int mHeight;
	public boolean mVisible = true;
	public int mZ;
	
	private Bitmap mBitmap;
	private Rect mRect;
	private int mOldX;
	private int mOldY;
	
	private Paint paint = new Paint();
	
    
	public Card(int z, Resources res, int x, int y, int width, int height, int bmpResId) {
        mBitmap = BitmapFactory.decodeResource(res, bmpResId);
        mZ = z;
        mX = x;
        mY = y;
        mRect = new Rect(x,y,x+width,y+height);
        mHeight = height;
        mWidth = width;
        
        // For painting
        // TODO: not in use
        paint.setAntiAlias(false);
        paint.setFilterBitmap(true);
        paint.setDither(true); 
        
        // Load and scale bitmap
        Options options = new Options();
        options.inScaled = false;     
        options.inDither = true;     
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;         

        Bitmap tmp = BitmapFactory.decodeResource(res,bmpResId,options);
        mBitmap = Bitmap.createScaledBitmap(tmp, mWidth, mHeight, true);
        tmp = null;
    }
	
	
	public void doDraw(Canvas canvas) {
        if (mVisible) {
        	canvas.drawBitmap(mBitmap, mX, mY, null);
        }
    }	
	
	public void setPos(int x, int y)
	{
        mX = x;
        mY = y;
        mRect.set(x, y, x+mRect.width(), y+mRect.height());
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
		if (mRect.contains(x,y))
			return true;
		else
			return false;
	}
	
}
