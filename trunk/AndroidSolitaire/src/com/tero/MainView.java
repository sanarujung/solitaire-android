package com.tero;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View {

	private Paint mCanvasPaint;
	private Paint mBmpPaint;

	private Bitmap mBmpPlayer1;
	//private Bitmap mBmpPlayer2;

	private Bitmap mCacheBitmap;
	private Canvas mCacheCanvas;
	private boolean mUseCache = false;

	private Rect mScreenSize = new Rect();
	private Rect mCardSize = new Rect();
	private int cardXCap;
	private int cardYCap;

	private Point mCardTl = new Point();
	private int mCardCap;

	public MainView(Context context) {
		super(context);

		mBmpPaint = new Paint();
		mBmpPaint.setAntiAlias(false);
		mBmpPaint.setFilterBitmap(false);

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

		int cw = w / 12;
		mCardSize.set(0, 0, cw, (int) (cw * 1.5));

		int freeSize = w - cw * 7;

		mCardCap = freeSize / 8;
		mCardTl.set(mCardCap, 20);

//		mBmpPlayer1 = getResBitmap(R.drawable.clubace, mCardSize.width(),
//				mCardSize.height());
		
		mBmpPlayer1 = getResBitmap(R.raw.clubace, mCardSize.width(),
				mCardSize.height());

		mCacheBitmap = Bitmap.createBitmap(mScreenSize.width(),
				mScreenSize.height(), Bitmap.Config.ARGB_8888);

		mCacheCanvas = new Canvas();
		mCacheCanvas.setBitmap(mCacheBitmap);
	}

	private void setToCache() {
		
		mCacheCanvas.drawRect(mScreenSize, mCanvasPaint);
		mCacheCanvas.drawBitmap(mBmpPlayer1, mCardTl.x, mCardTl.y, mBmpPaint);
		mUseCache = true;
	}

	@Override
	public void onDraw(Canvas canvas) {

		// Cache?
		if (mUseCache) {
			// Yes
			canvas.drawBitmap(mCacheBitmap, 0, 0, mBmpPaint);
		} else {
			mCanvasPaint.setStyle(Style.FILL);
			canvas.drawRect(mScreenSize, mCanvasPaint);
		}

		if (mBmpPlayer1 != null) {
			/*
			canvas.drawBitmap(
					mBmpPlayer1,
					new Rect(mCardTl.x, mCardTl.y, mBmpPlayer1.getWidth(),
							mBmpPlayer1.getHeight()),
					new Rect(mCardTl.x, mCardTl.y, mCardSize.width(), mCardSize
							.height()), mBmpPaint);
*/
			canvas.drawBitmap(mBmpPlayer1, mCardTl.x, mCardTl.y, mBmpPaint);
			// canvas.drawBitmap(mBmpPlayer2, 50, 50, mBmpPaint);

		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();

		if (action == MotionEvent.ACTION_DOWN) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			cardXCap = x - mCardTl.x;
			cardYCap = y - mCardTl.y;
			setToCache();
			invalidate();

			// Log.v("", "down");
			return true;

		} else if (action == MotionEvent.ACTION_MOVE) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			mCardTl.set(x - cardXCap, y - cardYCap);
			invalidate();
			return true;

		} else if (action == MotionEvent.ACTION_UP) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			//mCardTl.set(x - cardXCap, y - cardYCap);
			mUseCache = false;
			invalidate();
			return true;

		}
		return false;

	}

	private Bitmap getResBitmap(int bmpResId, int width, int height) {
		Options opts = new Options();
		opts.inDither = true;

		Resources res = getResources();
		Bitmap b = BitmapFactory.decodeResource(res, bmpResId, opts);
		//Bitmap bmp = Bitmap.createScaledBitmap(b, width, height, true);
		//return bmp;

		Bitmap bmp = scaleImage(b,width);
		return bmp;

		//BitmapDrawable drawable = (BitmapDrawable) res.getDrawable(bmpResId);
		//return drawable;
		 
		//return b;
	}

	// http://developer.android.com/guide/topics/graphics/2d-graphics.html
	
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

}
