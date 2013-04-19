package org.hanchao.webimagegetter;

import java.util.concurrent.Future;

import org.hanchao.webimagegetter.ImageWorker.LoadImgCallable;
import org.hanchao.webimagegetter.sample.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

public class WebImageView extends ImageView implements LoadImgCallable{

	public WebImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}


	String imageUrl;
	
	Future<Bitmap> future;
	
	Handler handler = new Handler();
	
	

	public void setImageUrl(String url){
		
		if(ImageWorker.getInstance().isBitmapExist(url)){
			
			this.imageUrl = url;
			
			this.setImageBitmap(ImageWorker.getInstance().getBitmapFromMemCache(this.imageUrl));
			
			this.future = null;
			
		}else{
			
			setImageResource(R.drawable.ic_launcher);
			
			//TODO:考虑url与this.imageUrl相等的情况
			if (future != null) {
				try {
					future.cancel(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			this.imageUrl = url;
			
			ImageWorker.getInstance().getBitmapFromUrl(this.getContext()
					, 0, this.imageUrl, this);
		}
	}


	@Override
	public void setImage(final Bitmap bitmap) {
		// TODO Auto-generated method stub
		
		if(bitmap!=null&&!bitmap.isRecycled()){
//			this.setImage(bitmap);
			
			handler.post(new Runnable() {
				@Override
				public void run() {
					
					setImageBitmap(bitmap);
						
				}
			});
		}
		
		this.future = null;
		
	}
	
}
