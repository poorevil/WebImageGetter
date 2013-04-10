package org.hanchao.webimagegetter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class ImageResizer extends ImageWorker{
	
    public ImageResizer(){
    	super();
    	System.out.println("new ImageResizer...............");
    }

    public Bitmap decodeSampledBitmapFromFile(String filename,
            int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);
        
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        
//        System.out.println("inSampleSize : " + options.inSampleSize);
        
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }
    
    public int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }

            final float totalPixels = width * height;
            
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

	@Override
	protected Bitmap getBitmap(String url,int width, int height) {
		Bitmap map;
		
		//获取本地已压缩的图片
		String localPath = getLocalFilePathFromUrl(url);
		String imgType;
		if(url.contains(".png")){
			imgType = ".png";
		}else{
			imgType = ".jpg";
		}
		
		String localScalePath = getResavePath(localPath,width,height,imgType);
		
//		System.out.println("localScalePath : "+localScalePath);
		
		map = getBitmapFromLocal(localScalePath);
		if(map != null && !map.isRecycled()){
//			System.out.println("获取已压缩图�?.............");
			return map;
		}
		
		//获取本地未压缩的图片
		map = getBitmapFromLocal(localPath);
		if(map != null && !map.isRecycled()){
//			System.out.println("获取未压缩图�?.............");
			reSaveBitmapToLocal(localScalePath,map,width,height,imgType);
			return map;
		}
		
		//网络加载图片
		String fileName = getDownloadFileString(url);
		if(!isEmpty(fileName)){
//			System.out.println("下载图片..............");
			map = decodeSampledBitmapFromFile(fileName,width,height);
			if(map != null && !map.isRecycled()){ //将压缩后的图片保存到本地
				reSaveBitmapToLocal(localScalePath,map,width,height,imgType);
			}
		}
		return map;
	}

	private String getDownloadFileString(String url){
		return downloadBitmap(url);
	}
	
	private Bitmap getBitmapFromLocal(String localPath) {
		return getBitmapFromLocalDir(localPath);
	}
	
	private Bitmap reSaveBitmapToLocal(String fileName,Bitmap map,int width,int height,String type) {
		if(map!=null && !isEmpty(fileName) && width != 0){
			saveBitmapToLocalDir(map,fileName,type);
		}
		return null;
	}
}
