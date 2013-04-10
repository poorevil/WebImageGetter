package org.hanchao.webimagegetter;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;

public class ImageCache {
	
//	private int DEFAULT_MEM_CACHE_SIZE = 1024 * 1024 * 1;
	
	public ImageCache(){
//		DEFAULT_MEM_CACHE_SIZE = cacheSize;
//		System.out.println("new ImageCache...............");
		mMemoryCache = getMemoryCache();
	}
	
	private LruCache<String, Bitmap> mMemoryCache;
	
	public void addBitmapToCache(String data, Bitmap bitmap) {
		if (data == null || bitmap == null) {
			return;
		}
		// Add to memory cache
		if (mMemoryCache != null && mMemoryCache.get(data) == null) {
//			System.out.println("addBitmapToCache : " + data);
			mMemoryCache.put(data, bitmap);
		}
	}

	public Bitmap getBitmapFromMemCache(String data) {
		if (mMemoryCache != null) {
			final Bitmap memBitmap = mMemoryCache.get(data);
			if (memBitmap != null) {
//				System.out.println("getBitmapFromMemCache : " + data);
				return memBitmap;
			}
		}
		return null;
	}

	public void clearCaches() {
		System.out.println("mMemoryCache.evictAll()................");
		mMemoryCache.evictAll();
	}
	
	public LruCache<String, Bitmap> getMemoryCache() {
		if (mMemoryCache == null) {
			System.out.println("new mMemoryCache............." + Globe.DEFAULT_MEM_CACHE_SIZE);
			return mMemoryCache = new LruCache<String, Bitmap>(
					Globe.DEFAULT_MEM_CACHE_SIZE) {
				/**
				 * Measure item size in bytes rather than units which is more
				 * practical for a bitmap cache
				 */
//				@SuppressLint("NewApi")
				@Override
				protected int sizeOf(String key, Bitmap bitmap) {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
						return bitmap.getByteCount();
					}
					// Pre HC-MR1
					return bitmap.getRowBytes() * bitmap.getHeight();
				}
			};
		}
		return mMemoryCache;
	}
}
