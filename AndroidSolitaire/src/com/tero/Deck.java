
// Tero Paananen 2011
// tepaanan@gmail.com
// FINLAND

package com.tero;

import java.util.ArrayList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Deck {

	public int mX;
	public int mY;
	public int mWidth;
	public int mHeight;
	protected Rect mBackgroundRect;
	protected int mDeckCardsInternalZ = 0;
	public int mCardTopCap = 10;

	protected Rect mRect;
	protected Paint paint = new Paint();
	protected ArrayList<Card> mCards = new ArrayList<Card>();

	public enum DeckType {
		ESource,
		ETarget,
		EWaste1,
		EWaste2
	}
	public DeckType mDeckType;

	
	public Deck(DeckType type, int x, int y, int width, int height) {
		mDeckType = type;
		mX = x;
		mY = y;
		mWidth = width;
		mHeight = height;
        mRect = new Rect(x,y,x+width,y+height);
		mBackgroundRect = new Rect(mRect);
		paint.setAntiAlias(false);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		paint.setColor(Color.WHITE);
		mCardTopCap = height / 5;
	}

	public void doDraw(Canvas canvas) {
		// Draw deck background rectangle
        Rect tmp = new Rect(mBackgroundRect);
		tmp.inset(3, 3);
		canvas.drawRect(tmp, paint);

		// Draw cards
		for (Card card : mCards) {
			card.doDraw(canvas);
		}
	}

	public void addCard(Deck fromDeck, Card newCard, boolean justOnTopOfOthers) {
	
		// Add card to new deck
		addCard(newCard, justOnTopOfOthers);
		
		// Remove card from old deck
		//Log.v("count", Integer.toString(fromDeck.mCards.size()));
		fromDeck.removeCard(newCard);

		// Null old deck parent card reference
		if(fromDeck.mCards.size()>0) {
			Card c = fromDeck.mCards.get(fromDeck.mCards.size()-1);
			c.mParentCard = null;
		}
	}
	
	public void addCard(Card newCard, boolean justOnTopOfOthers) {
		
		newCard.setDeck(this);
		
		if (justOnTopOfOthers) {
			// All cards are just top of other cards
	        // Set card new position
	        newCard.mRect = this.mRect;
		} else {
			// There is cap on top of all cards
			// Change deck rectangle height
			mHeight = newCard.mHeight + mCardTopCap*mCards.size();
	        mRect = new Rect(mX,mY,mX+mWidth,mY+mHeight);

	        // Set card new position
	        newCard.mRect.set(mX, 
					mY + mCards.size()*mCardTopCap, 
					mX+mWidth, 
					mY + mCards.size()*mCardTopCap + newCard.mHeight);
		}
        
		// Give new z to this most upper card in this deck
        mDeckCardsInternalZ++;
        newCard.mZ = mDeckCardsInternalZ;

        // Set new card to be pervious card parent
        if (!mCards.isEmpty()) {
        	mCards.get(mCards.size()-1).mParentCard = newCard;
        }
        
        // Add card
        mCards.add(newCard);
	}
	
	public void removeCard(Card removeThis) {
		mCards.remove(removeThis);
	}

	public Card getCardFromPos(int x, int y) {
		Card c = null;
		// Return most upper card from this deck in this position
		for (Card card : mCards) {
			if(card.mRect.contains(x,y)) {
				if (c!=null && c.mZ < card.mZ)
					c = card;
				else if(c==null)
					c = card;
			}
		}

		// If card has parent, do not give it
		if(c != null && c.mParentCard!=null) 
			return null;
		
		// Turn card if needed
		if (c != null && (mDeckType == Deck.DeckType.ESource || mDeckType == Deck.DeckType.EWaste1
				|| mDeckType == Deck.DeckType.EWaste2)) // TODO: Waste2 only, no Waste1
			c.mTurned = true;
		
		return c;
	}

	public boolean isUnderTouch(int x, int y) {
		if (mRect.contains(x, y))
			return true;
		else
			return false;
	}

}
