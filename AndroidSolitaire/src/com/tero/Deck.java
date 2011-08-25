package com.tero;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Deck {

	public int mX;
	public int mY;
	public int mWidth;
	public int mHeight;
	public int mZ;

	protected Rect mRect;
	protected Paint paint = new Paint();

	public Deck(int z, Resources res, int x, int y, int width, int height) {
		mZ = z;
		mX = x;
		mY = y;
		mWidth = width;
		mHeight = height;
		
		paint.setAntiAlias(true);
		paint.setColor(0xFFFFFFFF); // black
	}

	public void doDraw(Canvas canvas) {
		// TODO: draw deck background rectangle
		canvas.drawRect(mRect, paint);
		// TODO: draw deck cards

	}

	public void addCard(Card newCard) {
		// TODO:
	}
	
	public void removeCard(Card removeThis) {
		// TODO:
	}

	public Card getCardFromPos(int x, int y) {
		Card c = null;
		// TODO: return most upper card from this deck in this position
		return c;
	}

	public boolean isUnderTouch(int x, int y) {
		if (mRect.contains(x, y))
			return true;
		else
			return false;
	}

}
