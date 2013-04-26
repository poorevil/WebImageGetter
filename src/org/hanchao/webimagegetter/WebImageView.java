package org.hanchao.webimagegetter;

import java.util.concurrent.Future;

import org.hanchao.webimagegetter.ImageWorker.LoadImgCallable;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WebImageView extends ImageView implements LoadImgCallable{

	String imageUrl;
	Future<Bitmap> future;
	Handler handler = new Handler();
	
	int defaultImageResourceId;//默认图片资源id
	
	Runnable r;
	
	public WebImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置图片网络地址
	 * @param url
	 */
	public void setImageUrl(String url,int defaultImageResourceId){
		
		this.defaultImageResourceId = defaultImageResourceId;
		
		/*
		 * 可以通过setImageUrl(null)来停止该图片的下载
		 */
		if(url == null || !url.startsWith("http")){
			
			this.imageUrl = null;
			stopCurrentFuture();
			
			return ;
		}
		
		/*
		 * 判断url是否相等
		 */
		if (!url.equals(this.imageUrl)) {
			
			this.imageUrl = url;
			stopCurrentFuture();//缓存中已经存在，则尽可能关掉正在运行的线程
			
			if(ImageWorker.getInstance().isBitmapExist(url)){
				
				this.setImageBitmap(ImageWorker.getInstance().getBitmapFromMemCache(this.imageUrl));
				
			}else{
				
				setImageResource(this.defaultImageResourceId);
				
				future = ImageWorker.getInstance().getBitmapFromUrl(this.getContext()
						, 0, this.imageUrl, this);
				
			}
		}
	}
	
	/**
	 * 停止当前图片加载线程
	 */
	private void stopCurrentFuture(){
		
		/*
		 * 停掉当前handler任务
		 */
		if(r!=null){
			handler.removeCallbacks(r);
			r = null;
		}
		
		if (future != null) {
			try {
				future.cancel(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setImage(final Bitmap bitmap,String url) {

		if(url.equals(this.imageUrl) && bitmap!=null&&!bitmap.isRecycled()){
			
			handler.post(r = new Runnable() {
				@Override
				public void run() {
					setImageBitmap(bitmap);
				}
			});
		}
		
		this.future = null;
	}
	
}
