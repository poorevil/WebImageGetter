package org.hanchao.webimagegetter;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.Context;
import android.graphics.Bitmap;

public abstract class ImageWorker {

	private ImageCache imageCache;

	private static ImageWorker instance;
	private ExecutorService threadPool;// 线程池

	private Map<Context, Queue<WeakReference<Future<Bitmap>>>> requestMap;
	private int requestTaskSizePerContext = 10;// 单个context请求队列的最大数量

	public ImageWorker() {
		//初始化线程池 缓存 多线程任务队列
		threadPool = Executors.newFixedThreadPool(4);
		imageCache = new ImageCache();
		requestMap = new WeakHashMap<Context, Queue<WeakReference<Future<Bitmap>>>>();
//		IMAGE_TEMP_DIR = Environment.getExternalStorageDirectory()+ File.separator + "testTemp";
		
		System.out.println("new ImageWorker...............");
	}

	public static ImageWorker getInstance() {
		if (instance == null) {
			instance = new ImageFetcher();
		}
		return instance;
	}

	public ExecutorService getThreadPool() {
		if (threadPool == null) {
			return threadPool = Executors.newFixedThreadPool(4);
		}
		return threadPool;
	}

	public Future<Bitmap> getBitmapFromUrl(Context context, int position, String url,
			LoadImgCallable callable){
		return sendGetBitmapRequest(context,position,url,callable,0,0);
	}
	
	public Future<Bitmap> getBitmapFromUrl(Context context, int position, String url,
			LoadImgCallable callable , int size){
		return sendGetBitmapRequest(context,position,url,callable,size,size);
	}
	
	public Future<Bitmap> getBitmapFromUrl(Context context, int position, String url,
			LoadImgCallable callable,int width,int height) {
		return sendGetBitmapRequest(context,position,url,callable,width,height);
	}
	
	private Future<Bitmap> sendGetBitmapRequest(Context context, int position, String url,
			LoadImgCallable callable,int width,int height){
		Queue<WeakReference<Future<Bitmap>>> requestList = requestMap
				.get(context);
		if (requestList == null) {
			requestList = new LinkedList<WeakReference<Future<Bitmap>>>();
			requestMap.put(context, requestList);
		}

		/*
		 * 若当前context的请求队列大于requestTaskSizePerContext， 则出队最前面的请求
		 */
		if (requestList.size() >= requestTaskSizePerContext) {
			WeakReference<Future<Bitmap>> futureWR = requestList.poll();
			if (futureWR != null && futureWR.get() != null) {
				try {
					futureWR.get().cancel(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (threadPool == null) {
			threadPool = getThreadPool();
			if (imageCache == null) {
				imageCache = new ImageCache();
			}
		}

		Future request = threadPool.submit(new GetImageLoad(position, url,callable,height,height));
		requestList.add(new WeakReference<Future<Bitmap>>(request));
		
		return request;
	}

	public class GetImageLoad implements Runnable {
		private int position;
		private String url;
		private LoadImgCallable callabe;
		private int width;
		private int height;

		public GetImageLoad(int position, String url, LoadImgCallable callables,int width ,int height) {
			this.position = position;
			this.url = url;
			this.callabe = callables;
			this.width = width;
			this.height = height;
		}

		@Override
		public void run() {
			Bitmap map = imageCache.getBitmapFromMemCache(url);
			if (map != null && !map.isRecycled()) {
				// System.out.println("WebImageGetterForMingRenGridView get from 内存..........");
//				callabe.setViewImage(position, url);
				callabe.setImage(map);
				return;
			}

			Bitmap img;
			if(width != 0){
//				System.out.println("get bitmap scale...........");
				img = getBitmap(url,width,height);
//				String reWriteUrl = getLocalFilePathFromUrl(url);
//				if(url.contains(".png")){
//					url = getResavePath(reWriteUrl,width,height,".png");
//				}else{
//					url = getResavePath(reWriteUrl,width,height,".jpg");
//				}
				url = getScaleImageCacheKey(url,width,height);
			}else{
//				System.out.println("get bitmap not scale...........");
				img = getBitmap(url);
			}

			if(img != null && !img.isRecycled()){
				imageCache.addBitmapToCache(url, img);

				// 回调activity 设置imageView
//				callabe.setViewImage(position, url);
				callabe.setImage(img);
			}
		}
	}
	
	///////////////////////////////////////获取内存缓存部分////////////////////////////////
	public boolean isBitmapExist(String url) {
		Bitmap map = imageCache.getBitmapFromMemCache(url);
		if (map != null && !map.isRecycled()) {
			return true;
		}
		return false;
	}
	
	public boolean isBitmapExist(String url,int width,int height) {
		Bitmap map = imageCache.getBitmapFromMemCache(getScaleImageCacheKey(url,width,height));
		if (map != null && !map.isRecycled()) {
			return true;
		}
		return false;
	}
	
	public Bitmap getBitmapFromMemCache(String data) {
		return imageCache.getBitmapFromMemCache(data);
	}
	
	public Bitmap getBitmapFromMemCache(String data,int width,int height) {
		return imageCache.getBitmapFromMemCache(getScaleImageCacheKey(data,width,height));
	}

	public boolean isBitmapNull(String url) {
		Bitmap map = imageCache.getBitmapFromMemCache(url);
		if (map == null || map.isRecycled()) {
			return true;
		}
		return false;
	}
	
	public boolean isBitmapNull(String data,int width,int height) {
		Bitmap map = imageCache.getBitmapFromMemCache(getScaleImageCacheKey(data,width,height));
		if (map == null || map.isRecycled()) {
			return true;
		}
		return false;
	}
	
	private String getScaleImageCacheKey(String url,int width,int height){
		String reWriteUrl = getLocalFilePathFromUrl(url);
		if(url.contains(".png")){
			url = getResavePath(reWriteUrl,width,height,".png");
		}else{
			url = getResavePath(reWriteUrl,width,height,".jpg");
		}
		return url;
	}
	
	public void clearCaches(){
		imageCache.clearCaches();
	}
	
	//activity页面回调接口  setImageView
	public interface LoadImgCallable {
//		public void setViewImage(int position, String url);
		public void setImage(Bitmap bitmap);
	}
	
	//线程池任务停止部分
	public void cancelRequests(Context context, boolean mayInterruptIfRunning) {
		Queue<WeakReference<Future<Bitmap>>> requestList = requestMap
				.get(context);
		if (requestList != null) {
			for (WeakReference<Future<Bitmap>> requestRef : requestList) {
				Future<?> request = requestRef.get();
				if (request != null) {
					System.out.println("request.cancel(mayInterruptIfRunning)...............");
					request.cancel(mayInterruptIfRunning);
				}
			}
		}
		requestMap.remove(context);
	}

//	protected String IMAGE_TEMP_DIR;
	
	protected abstract Bitmap getBitmap(String url);
	
	protected abstract Bitmap getBitmap(String url,int width,int height);
	
	protected abstract String downloadBitmap(String url);
	
	protected abstract void saveBitmapToLocalDir(Bitmap map, String url , String type);
	
	protected abstract Bitmap getBitmapFromLocalDir(String url);
	
	protected String getLocalFilePathFromUrl(String url) {
		if (url != null && url.trim().length() > 0 ) {
			String fileName = url.hashCode()+"";
			if (fileName != null && fileName.length() > 0) {
				return Globe.IMAGE_TEMP_DIR+File.separator+ fileName;
			}
		}
		return null;
	}
	
	protected String getResavePath(String fileName,int width,int height,String type){
		StringBuffer sb = new StringBuffer(fileName);
		
		if(".png".equals(type)){
			sb.append("_"+width+"x"+height);
		}else{
			sb.append("_"+width+"x"+height);
		}
		
		return sb.toString();
	}
	
	protected boolean isEmpty(String url) {
		if (url == null || "".equals(url) || "null".equals(url)) {
			return true;
		}
		return false;
	}
}
