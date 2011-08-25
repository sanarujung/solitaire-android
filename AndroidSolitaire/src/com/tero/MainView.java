
// Tero Paananen 2011
// tepaanan@gmail.com
// FINLAND

package com.tero;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View {

	private Paint mCanvasPaint;

	private Bitmap mCacheBitmap;
	private boolean mUseCache = false;

	private Rect mScreenSize = new Rect();

	private Rect mCardSize = new Rect();
	private int cardXCap;
	private int cardYCap;

	private int mCardCap;
	private int mCardTopMargin;

//	private ArrayList<Card> mCards = new ArrayList<Card>();

	private ArrayList<Deck> mSourceDecks = new ArrayList<Deck>();
	
	private Card mActiveCard;

	public MainView(Context context) {
		super(context);

		mCanvasPaint = new Paint();
		mCanvasPaint.setColor(0xFF228B22); // Green background
		mCanvasPaint.setAntiAlias(false);
		mCanvasPaint.setFilterBitmap(false);

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		// Store current screen size
		mScreenSize.set(0, 0, w, h);

		// TODO: calculate card and decks positions
		int cw = w / 11;
		mCardSize.set(0, 0, cw, (int) (cw * 1.5));
		Log.v("card size", mCardSize.toString());

		int freeSize = w - cw * 7;
		mCardCap = freeSize / (6+4*2);
		mCardTopMargin = mCardSize.height() / 5;
		
		int cy = (int)(mScreenSize.height()*0.4);

		// TODO: Create cards...
		// TODO: Suffle card list by Random

		// Create source decks
		// Add cards to the source decks
		if (mSourceDecks.size()==0) {
			Deck deck = new Deck(mCardCap*4, cy, mCardSize.width(), mCardSize.height());
			Card c = new Card(0, getResources(), 0, 0, mCardSize.width(), mCardSize.height(), R.raw.clubace);
			deck.addCard(c, false);
			mSourceDecks.add(deck);
			
			deck = new Deck(mCardCap*5+mCardSize.width(), cy, mCardSize.width(), mCardSize.height());
			for(int i=0;i<2;i++) {
				c = new Card(0, getResources(), 0, 0, mCardSize.width(), mCardSize.height(), R.raw.clubace);
				deck.addCard(c, false);
			}
			mSourceDecks.add(deck);
			
			deck = new Deck(mCardCap*6+mCardSize.width()*2, cy, mCardSize.width(), mCardSize.height());
			for(int i=0;i<3;i++) {
				c = new Card(0, getResources(), 0, 0, mCardSize.width(), mCardSize.height(), R.raw.clubace);
				deck.addCard(c, false);
			}
			mSourceDecks.add(deck);
			
			deck = new Deck(mCardCap*7+mCardSize.width()*3, cy, mCardSize.width(), mCardSize.height());
			for(int i=0;i<4;i++) {
				c = new Card(0, getResources(), 0, 0, mCardSize.width(), mCardSize.height(), R.raw.clubace);
				deck.addCard(c, false);
			}
			mSourceDecks.add(deck);
			
			deck = new Deck(mCardCap*8+mCardSize.width()*4, cy, mCardSize.width(), mCardSize.height());
			for(int i=0;i<5;i++) {
				c = new Card(0, getResources(), 0, 0, mCardSize.width(), mCardSize.height(), R.raw.clubace);
				deck.addCard(c, false);
			}
			mSourceDecks.add(deck);
			
			deck = new Deck(mCardCap*9+mCardSize.width()*5, cy, mCardSize.width(), mCardSize.height());
			for(int i=0;i<6;i++) {
				c = new Card(0, getResources(), 0, 0, mCardSize.width(), mCardSize.height(), R.raw.clubace);
				deck.addCard(c, false);
			}
			mSourceDecks.add(deck);
			
			deck = new Deck(mCardCap*10+mCardSize.width()*6, cy, mCardSize.width(), mCardSize.height());
			for(int i=0;i<7;i++) {
				c = new Card(0, getResources(), 0, 0, mCardSize.width(), mCardSize.height(), R.raw.clubace);
				deck.addCard(c, false);
			}
			mSourceDecks.add(deck);

			
			
		}
		
		/*
		for (int i=0;i<8;i++) {
			Card c = new Card(z++,getResources(), mCardCap*4, cy+mCardTopMargin*i, 
					mCardSize.width(), mCardSize.height(), R.raw.clubace);
			mCards.add(c);
		}

		Card c = new Card(z++,getResources(), mCardCap*5+mCardSize.width(), cy, 
				mCardSize.width(), mCardSize.height(), R.raw.clubace);
		mCards.add(c);

		c = new Card(z++,getResources(), mCardCap*6+mCardSize.width()*2, cy, 
				mCardSize.width(), mCardSize.height(), R.raw.clubace);
		mCards.add(c);

		c = new Card(z++,getResources(), mCardCap*7+mCardSize.width()*3, cy, 
				mCardSize.width(), mCardSize.height(), R.raw.clubace);
		mCards.add(c);
	
		c = new Card(z++,getResources(), mCardCap*8+mCardSize.width()*4, cy, 
				mCardSize.width(), mCardSize.height(), R.raw.clubace);
		mCards.add(c);
	
		c = new Card(z++,getResources(), mCardCap*9+mCardSize.width()*5, cy, 
				mCardSize.width(), mCardSize.height(), R.raw.clubace);
		mCards.add(c);

		c = new Card(z++,getResources(), mCardCap*10+mCardSize.width()*6, cy, 
				mCardSize.width(), mCardSize.height(), R.raw.clubace);
		mCards.add(c);
		*/

		
		
	}

	private void enableCache(boolean enabled) {
		if(enabled && mUseCache!=enabled) {
			mActiveCard.mVisible = false;
			setDrawingCacheEnabled(true);
			//buildDrawingCache();
			mCacheBitmap = Bitmap.createBitmap(getDrawingCache()); 
			mActiveCard.mVisible = true;
		} else if(!enabled && mUseCache!=enabled) {
			setDrawingCacheEnabled(false);
			mCacheBitmap = null;
		}
		mUseCache = enabled;
	}

	@Override
	public void onDraw(Canvas canvas) {

		// Cache?
		if (mUseCache) {
			// Yes
			canvas.drawBitmap(mCacheBitmap, 0, 0, null);
		} else {
			// No
			mCanvasPaint.setStyle(Style.FILL);
			canvas.drawRect(mScreenSize, mCanvasPaint);
			// Draw decks
			for (Deck deck : mSourceDecks) {
				deck.doDraw(canvas);
			}
		}

		// Draw active card last
		if (mActiveCard!=null) {
			mActiveCard.doDraw(canvas);
		}
		
		/*
		canvas.drawBitmap(
				mBmpPlayer1,
				new Rect(0, 0, mBmpPlayer1.getWidth(), mBmpPlayer1.getHeight()),
				new Rect(mCardTl.x, mCardTl.y, mCardSize.width(), mCardSize.height()), 
				mBmpPaint);
				*/

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			mActiveCard = null;
			// Search card under touch
			for (Deck deck : mSourceDecks) {
				if (deck.isUnderTouch(x, y)) {
					mActiveCard = deck.getCardFromPos(x, y);
					break;
				} 
				
				/*
				if(card.isUnderTouch(x, y))
				{
					// Take most upper card (z order)
					if(mActiveCard!=null && mActiveCard.mZ < card.mZ) {
						mActiveCard = card;
					} else if (mActiveCard==null) {
						mActiveCard = card;
					}
				}
				*/
			}
			// Card founds?
			if (mActiveCard!=null) {
				cardXCap = x - mActiveCard.mRect.left;
				cardYCap = y - mActiveCard.mRect.top;
				mActiveCard.storePosition(x - cardXCap, y - cardYCap);
				enableCache(true);
				invalidate();
			}
			
			// Log.v("", "down");
			return true;

		} else if (action == MotionEvent.ACTION_MOVE) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			if (mActiveCard!=null) {
				mActiveCard.setPos(x - cardXCap, y - cardYCap);
				invalidate();
			}
			return true;

		} else if (action == MotionEvent.ACTION_UP) {
			enableCache(false);
			if(mActiveCard!=null)
				mActiveCard.cancelMove();
			mActiveCard = null;
			invalidate();
			return true;
		}
		return false;

	}


	// http://developer.android.com/guide/topics/graphics/2d-graphics.html
	// http://www.higherpass.com/Android/Tutorials/Working-With-Images-In-Android/3/
	// http://www.droidnova.com/playing-with-graphics-in-android-part-i,147.html
/*
	private Bitmap scaleImage(Bitmap src, int newWidth) {
		int width = src.getWidth();
		int height = src.getHeight();

		float scaleWidth = ((float) newWidth) / width;
		float ratio = ((float) src.getWidth()) / newWidth;
		int newHeight = (int) (height / ratio);
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
	}
*/
	
}
