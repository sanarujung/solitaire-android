// Tero Paananen 2011
// tepaanan@gmail.com
// FINLAND

package com.tero;

import java.util.ArrayList;
import java.util.Random;

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

	private ArrayList<Deck> mSourceDecks = new ArrayList<Deck>();
	private ArrayList<Deck> mTargetDecks = new ArrayList<Deck>();
	private ArrayList<Card> mCards = new ArrayList<Card>();
	private Deck mWasteDeck;
	private Deck mWasteDeck2;

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

		// Calculate card and decks sizes and positions
		int cw = w / 11;
		mCardSize.set(0, 0, cw, (int) (cw * 1.5));
		Log.v("card size", mCardSize.toString());

		int freeSize = w - cw * 7;
		mCardCap = freeSize / (6 + 4 * 2);

		int cy = (int) (mScreenSize.height() * 0.35);

		// Create all cards
		mCards.add(new Card(1, Card.CardLand.EClub, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.club1));
		mCards.add(new Card(2, Card.CardLand.EClub, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.club2));
		mCards.add(new Card(3, Card.CardLand.EClub, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.club3));
		mCards.add(new Card(4, Card.CardLand.EClub, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.club4));
		mCards.add(new Card(5, Card.CardLand.EClub, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.club5));
		mCards.add(new Card(6, Card.CardLand.EClub, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.club6));
		mCards.add(new Card(7, Card.CardLand.EClub, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.club7));
		mCards.add(new Card(8, Card.CardLand.EClub, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.club8));
		mCards.add(new Card(9, Card.CardLand.EClub, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.club9));
		mCards.add(new Card(10, Card.CardLand.EClub, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.club10));
		mCards.add(new Card(11, Card.CardLand.EClub, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.club11));
		mCards.add(new Card(12, Card.CardLand.EClub, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.club12));
		mCards.add(new Card(13, Card.CardLand.EClub, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.club13));

		mCards.add(new Card(1, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.diamond1));
		mCards.add(new Card(2, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.diamond2));
		mCards.add(new Card(3, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.diamond3));
		mCards.add(new Card(4, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.diamond4));
		mCards.add(new Card(5, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.diamond5));
		mCards.add(new Card(6, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.diamond6));
		mCards.add(new Card(7, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.diamond7));
		mCards.add(new Card(8, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.diamond8));
		mCards.add(new Card(9, Card.CardLand.EDiamond, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.diamond9));
		mCards.add(new Card(10, Card.CardLand.EDiamond, 0, getResources(), 0,
				0, mCardSize.width(), mCardSize.height(), R.raw.diamond10));
		mCards.add(new Card(11, Card.CardLand.EDiamond, 0, getResources(), 0,
				0, mCardSize.width(), mCardSize.height(), R.raw.diamond11));
		mCards.add(new Card(12, Card.CardLand.EDiamond, 0, getResources(), 0,
				0, mCardSize.width(), mCardSize.height(), R.raw.diamond12));
		mCards.add(new Card(13, Card.CardLand.EDiamond, 0, getResources(), 0,
				0, mCardSize.width(), mCardSize.height(), R.raw.diamond13));

		mCards.add(new Card(1, Card.CardLand.EHeart, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.heart1));
		mCards.add(new Card(2, Card.CardLand.EHeart, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.heart2));
		mCards.add(new Card(3, Card.CardLand.EHeart, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.heart3));
		mCards.add(new Card(4, Card.CardLand.EHeart, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.heart4));
		mCards.add(new Card(5, Card.CardLand.EHeart, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.heart5));
		mCards.add(new Card(6, Card.CardLand.EHeart, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.heart6));
		mCards.add(new Card(7, Card.CardLand.EHeart, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.heart7));
		mCards.add(new Card(8, Card.CardLand.EHeart, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.heart8));
		mCards.add(new Card(9, Card.CardLand.EHeart, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.heart9));
		mCards.add(new Card(10, Card.CardLand.EHeart, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.heart10));
		mCards.add(new Card(11, Card.CardLand.EHeart, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.heart11));
		mCards.add(new Card(12, Card.CardLand.EHeart, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.heart12));
		mCards.add(new Card(13, Card.CardLand.EHeart, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.heart13));

		mCards.add(new Card(1, Card.CardLand.ESpade, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.spade1));
		mCards.add(new Card(2, Card.CardLand.ESpade, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.spade2));
		mCards.add(new Card(3, Card.CardLand.ESpade, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.spade3));
		mCards.add(new Card(4, Card.CardLand.ESpade, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.spade4));
		mCards.add(new Card(5, Card.CardLand.ESpade, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.spade5));
		mCards.add(new Card(6, Card.CardLand.ESpade, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.spade6));
		mCards.add(new Card(7, Card.CardLand.ESpade, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.spade7));
		mCards.add(new Card(8, Card.CardLand.ESpade, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.spade8));
		mCards.add(new Card(9, Card.CardLand.ESpade, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.spade9));
		mCards.add(new Card(10, Card.CardLand.ESpade, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.spade10));
		mCards.add(new Card(11, Card.CardLand.ESpade, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.spade11));
		mCards.add(new Card(12, Card.CardLand.ESpade, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.spade12));
		mCards.add(new Card(13, Card.CardLand.ESpade, 0, getResources(), 0, 0,
				mCardSize.width(), mCardSize.height(), R.raw.spade13));

		// Create source decks (random)
		// Add cards to the source decks
		Random random = new Random();
		if (mSourceDecks.size() == 0) {
			Deck deck = new Deck(Deck.DeckType.ESource, mCardCap * 4, cy,
					mCardSize.width(), mCardSize.height());
			Card c = mCards.remove(random.nextInt(mCards.size()));
			c.mTurned = true;
			deck.addCard(c, false);
			mSourceDecks.add(deck);

			deck = new Deck(Deck.DeckType.ESource, mCardCap * 5
					+ mCardSize.width(), cy, mCardSize.width(),
					mCardSize.height());
			for (int i = 0; i < 2; i++) {
				c = mCards.remove(random.nextInt(mCards.size()));
				deck.addCard(c, false);
			}
			c.mTurned = true;
			mSourceDecks.add(deck);

			deck = new Deck(Deck.DeckType.ESource, mCardCap * 6
					+ mCardSize.width() * 2, cy, mCardSize.width(),
					mCardSize.height());
			for (int i = 0; i < 3; i++) {
				c = mCards.remove(random.nextInt(mCards.size()));
				deck.addCard(c, false);
			}
			c.mTurned = true;
			mSourceDecks.add(deck);

			deck = new Deck(Deck.DeckType.ESource, mCardCap * 7
					+ mCardSize.width() * 3, cy, mCardSize.width(),
					mCardSize.height());
			for (int i = 0; i < 4; i++) {
				c = mCards.remove(random.nextInt(mCards.size()));
				deck.addCard(c, false);
			}
			c.mTurned = true;
			mSourceDecks.add(deck);

			deck = new Deck(Deck.DeckType.ESource, mCardCap * 8
					+ mCardSize.width() * 4, cy, mCardSize.width(),
					mCardSize.height());
			for (int i = 0; i < 5; i++) {
				c = mCards.remove(random.nextInt(mCards.size()));
				deck.addCard(c, false);
			}
			c.mTurned = true;
			mSourceDecks.add(deck);

			deck = new Deck(Deck.DeckType.ESource, mCardCap * 9
					+ mCardSize.width() * 5, cy, mCardSize.width(),
					mCardSize.height());
			for (int i = 0; i < 6; i++) {
				c = mCards.remove(random.nextInt(mCards.size()));
				deck.addCard(c, false);
			}
			c.mTurned = true;
			mSourceDecks.add(deck);

			deck = new Deck(Deck.DeckType.ESource, mCardCap * 10
					+ mCardSize.width() * 6, cy, mCardSize.width(),
					mCardSize.height());
			for (int i = 0; i < 7; i++) {
				c = mCards.remove(random.nextInt(mCards.size()));
				deck.addCard(c, false);
			}
			c.mTurned = true;
			mSourceDecks.add(deck);

			// Create target decks
			deck = new Deck(Deck.DeckType.ETarget, mCardCap * 7
					+ mCardSize.width() * 3, mCardCap, mCardSize.width(),
					mCardSize.height());
			mTargetDecks.add(deck);

			deck = new Deck(Deck.DeckType.ETarget, mCardCap * 8
					+ mCardSize.width() * 4, mCardCap, mCardSize.width(),
					mCardSize.height());
			mTargetDecks.add(deck);

			deck = new Deck(Deck.DeckType.ETarget, mCardCap * 9
					+ mCardSize.width() * 5, mCardCap, mCardSize.width(),
					mCardSize.height());
			mTargetDecks.add(deck);

			deck = new Deck(Deck.DeckType.ETarget, mCardCap * 10
					+ mCardSize.width() * 6, mCardCap, mCardSize.width(),
					mCardSize.height());
			mTargetDecks.add(deck);

			// Waste decks
			mWasteDeck = new Deck(Deck.DeckType.EWaste1, mCardCap * 4,
					mCardCap, mCardSize.width(), mCardSize.height());
			while (!mCards.isEmpty()) {
				c = mCards.remove(random.nextInt(mCards.size()));
				mWasteDeck.addCard(c, true);
			}
			mWasteDeck2 = new Deck(Deck.DeckType.EWaste2, mCardCap * 5
					+ mCardSize.width(), mCardCap, mCardSize.width(),
					mCardSize.height());

		}

	}

	private void enableCache(boolean enabled) {
		if (enabled && mUseCache != enabled) {
			mActiveCard.mVisible = false;
			setDrawingCacheEnabled(true);
			// buildDrawingCache();
			mCacheBitmap = Bitmap.createBitmap(getDrawingCache());
			mActiveCard.mVisible = true;
		} else if (!enabled && mUseCache != enabled) {
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
			for (Deck deck : mTargetDecks) {
				deck.doDraw(canvas);
			}
			mWasteDeck.doDraw(canvas);
			mWasteDeck2.doDraw(canvas);
		}

		// Draw active card last
		if (mActiveCard != null) {
			mActiveCard.doDraw(canvas);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			mActiveCard = null;

			// Search card under source decks
			Deck deck = getDeckUnderTouch(x, y);
			mActiveCard = deck.getCardFromPos(x, y);

			// Card founds?
			if (mActiveCard != null) {
				cardXCap = x - mActiveCard.mRect.left;
				cardYCap = y - mActiveCard.mRect.top;
				mActiveCard.storePosition();
				enableCache(true);
				invalidate();
			}

			// Log.v("", "down");
			return true;

		} else if (action == MotionEvent.ACTION_MOVE) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			if (mActiveCard != null) {
				if (mActiveCard.mOwnerDeck.mDeckType != Deck.DeckType.EWaste1) {
					mActiveCard.setPos(x - cardXCap, y - cardYCap);
					invalidate();
				}
			}
			return true;

		} else if (action == MotionEvent.ACTION_UP) {
			int x = (int) event.getX();
			int y = (int) event.getY();

			enableCache(false);
			handleCardMove(x, y);
			invalidate();
			return true;
		}
		return false;

	}

	private Deck getDeckUnderTouch(int x, int y) {
		Deck ret = null;
		for (Deck deck : mSourceDecks) {
			if (deck.isUnderTouch(x, y))
				return deck;
		}
		for (Deck deck : mTargetDecks) {
			if (deck.isUnderTouch(x, y))
				return deck;
		}
		if (mWasteDeck.isUnderTouch(x, y)) {
			return mWasteDeck;
		}
		if (mWasteDeck2.isUnderTouch(x, y)) {
			return mWasteDeck2;
		}
		return ret;
	}

	private void handleCardMove(int x, int y) {
		if (mActiveCard != null) {
			Deck fromDeck = mActiveCard.mOwnerDeck;
			Deck toDeck = getDeckUnderTouch(x, y);
			boolean topOfOtherCards = true;

			if (fromDeck.mDeckType == Deck.DeckType.EWaste1) {
				// Copy waste cards from Waste1 to Waste2
				mWasteDeck2.addCard(fromDeck, mActiveCard, true);
			} else {
				// Handle card move
				if (toDeck!=null) {
					if (toDeck.mDeckType == Deck.DeckType.ESource) {
						topOfOtherCards = false; // Drawed exactly top of other cards in the deck
					}
					// Accept card move or not?
					if (acceptCardMove(fromDeck, toDeck, mActiveCard)) {
						toDeck.addCard(fromDeck, mActiveCard, topOfOtherCards);
					} else {
						mActiveCard.cancelMove();
					}
				} else {
					mActiveCard.cancelMove();
				}
			}
		}
		mActiveCard = null;
	}

	private boolean acceptCardMove(Deck from, Deck to, Card card) {
		Card topOfThisCard=null;
		if (to.mCards.size()>0) {
			topOfThisCard = to.mCards.get(to.mCards.size()-1);
		}

		// Check deck issuess
		if (topOfThisCard!=null && topOfThisCard.mTurned==false) {
			return false;
		}
		
		if (from.mDeckType != Deck.DeckType.ESource && from.mDeckType != Deck.DeckType.EWaste2)
			return false;

		if (to.mDeckType != Deck.DeckType.ESource && to.mDeckType != Deck.DeckType.ETarget)
			return false;

		// Card decks must differ
		if (from == to)
			return false;

		// Check cars issuess
		if(to.mCards.size()>0) {
			if (to.mDeckType == Deck.DeckType.ESource) {
				// Card can be top of one step greater card and different color
                // Card can not be same color
                if (card.mCardLand == topOfThisCard.mCardLand || topOfThisCard.mCardValue != card.mCardValue + 1 || card.mBlack == topOfThisCard.mBlack)
                    return false;
			} else if (to.mDeckType == Deck.DeckType.ETarget) {
                // Cards must be in ascending order and same suite in 2 target deck
                if (topOfThisCard.mCardValue + 1 != card.mCardValue || topOfThisCard.mCardLand != card.mCardLand) 
                    return false;
			}
		} else {
            // Moving top of empty deck

            // If there is no cards in the deck, then the first one must be King card in source decks 1
            if (to.mCards.size() == 0 &&
            		card.mCardValue != 13 && to.mDeckType == Deck.DeckType.ESource)
                return false;

            // Ace card must be the first card in foundation
            if (to.mDeckType == Deck.DeckType.ETarget && to.mCards.size() == 0 && card.mCardValue != 1)
                return false;
		}

		return true;
	}

	
}
