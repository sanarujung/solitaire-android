// Tero Paananen 2011
// tepaanan@gmail.com
// FINLAND

package com.tero;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

//
public class Card {

	public int mWidth;
	public int mHeight;
	private boolean mVisible = true;
	public boolean mTurned = false;
	public int mZ;
	public Rect mRect;

	public Card mParentCard = null;
	public Deck mOwnerDeck = null;

	public boolean mBlack;

	private Bitmap mBitmap;
	private Bitmap mBackBitmap;
	private int mOldX;
	private int mOldY;
	private int mX;
	private int mY;

	public enum CardLand {
		EClub, EDiamond, ESpade, EHeart
	}

	public CardLand mCardLand;
	public int mCardValue;

	public Card(int cardValue, CardLand cardLand, int z, Resources res, int x,
			int y, int width, int height, int bmpResId) {
		mCardValue = cardValue;
		mCardLand = cardLand;

		if (mCardLand == Card.CardLand.ESpade
				|| mCardLand == Card.CardLand.EClub)
			mBlack = true;
		else
			mBlack = false;

		mZ = z;
		mX = x;
		mY = y;
		mRect = new Rect(x, y, x + width, y + height);
		mHeight = height;
		mWidth = width;

		// Load and scale bitmap
		Options options = new Options();
		options.inScaled = false;
		options.inDither = true;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// Bitmaps
		Bitmap tmp = BitmapFactory.decodeResource(res, R.raw.cardback);
		mBackBitmap = Bitmap.createScaledBitmap(tmp, mWidth, mHeight, true);
		tmp = BitmapFactory.decodeResource(res, bmpResId, options);
		mBitmap = Bitmap.createScaledBitmap(tmp, mWidth, mHeight, true);
		tmp = null;
	}

	public void setDeck(Deck d) {
		mOwnerDeck = d;
	}

	public boolean isVisible() {
		return mVisible;
	}
	
	public void setVisible(boolean visible) {
		mVisible = visible;

		// Visible also parent card?
		if (mParentCard != null && mOwnerDeck != null
				&& mOwnerDeck.mDeckType == Deck.DeckType.ESource) {
			mParentCard.setVisible(visible);
		}
	}

	public void doDraw(Canvas canvas) {
		if (mVisible) {
			if (mTurned)
				canvas.drawBitmap(mBitmap, mRect.left, mRect.top, null);
			else
				canvas.drawBitmap(mBackBitmap, mRect.left, mRect.top, null);

			// Draw also parent card?
			if (mParentCard != null && mOwnerDeck != null
					&& mOwnerDeck.mDeckType == Deck.DeckType.ESource) {
				mParentCard.doDraw(canvas);
			}
		}
	}

	public void setPos(int x, int y, boolean parentCardAlso) {
		mX = x;
		mY = y;
		mRect.set(x, y, x + mRect.width(), y + mRect.height());

		// Move also parent card?
		if (parentCardAlso && mParentCard != null && mOwnerDeck != null
				&& mOwnerDeck.mDeckType == Deck.DeckType.ESource) {
			mParentCard.setPos(x, y + mOwnerDeck.mCardTopCap, true);
		}
	}

	public void storePosition() {
		mOldX = mRect.left;
		mOldY = mRect.top;
	}

	public void cancelMove(boolean parentCardAlso) {
		setPos(mOldX, mOldY, parentCardAlso);
	}

	public boolean isUnderTouch(int x, int y) {
		if (mRect.contains(x, y))
			return true;
		else
			return false;
	}

}
