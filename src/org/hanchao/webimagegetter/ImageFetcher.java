package org.hanchao.webimagegetter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageFetcher extends ImageResizer {

//	private String IMAGE_TEMP_DIR;

	public ImageFetcher(){
		super();
		System.out.println("new ImageFetcher...............");
	}

	// //////////////////////////先加载到内存 后加载到本地//////////////////////
	@Override
	protected Bitmap getBitmap(String url) {
//		System.out.println("ImageFetcher getBitmap()..................");
		return getBitmapFromUrl(url);
	}
	
	@Override
	protected Bitmap getBitmapFromLocalDir(String filePath) {
		if (!isEmpty(filePath) && !isEmpty(Globe.IMAGE_TEMP_DIR)) {
			File tmpDir = new File(Globe.IMAGE_TEMP_DIR);
			if (tmpDir.exists() && tmpDir.isDirectory()) {
				File file = new File(filePath);

				if (!isEmpty(filePath) && file != null && file.exists()) {
//					System.out.println("getBitmapFromLocalDir..." + filePath);
					return BitmapFactory.decodeFile(filePath);
				}
			} else {
				tmpDir.mkdirs();
			}
		}
		return null;
	}

	@Override
	protected void saveBitmapToLocalDir(Bitmap map, String url , String type) {
		if (map != null && !isEmpty(url)&& !isEmpty(Globe.IMAGE_TEMP_DIR)) {
			File tmpDir = new File(Globe.IMAGE_TEMP_DIR);
			if (!tmpDir.exists() || !tmpDir.isDirectory()) {
				tmpDir.mkdirs();
			}

//			String fileName = getLocalFilePathFromUrl(url);
			
			if (url != null && url.length() > 0) {
				File imageFile = new File(url);
//				System.out.println("saveBitmapToLocalDir : " + url);
				imageFile.delete();

				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(imageFile);
					if (url.contains(".png")) {
						map.compress(Bitmap.CompressFormat.PNG, 100, fos);
					} else {
						map.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					}
//					 System.out.println("save to 本地..........."+url);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException ioe) {
							// do nothing
						}
					}
				}
			}
		}
	}

	public Bitmap getBitmapFromUrl(String url) {
		Bitmap bitmap;
		bitmap = getBitmapFromLocalDir(getLocalFilePathFromUrl(url));
		if (bitmap != null) {
			return bitmap;
		}

		URL m;
		InputStream i = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
			bitmap = BitmapFactory.decodeStream(i);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(url.contains(".png")){
			saveBitmapToLocalDir(bitmap, getLocalFilePathFromUrl(url),".png");
		}else{
			// 将图片保存到本地SD卡
			saveBitmapToLocalDir(bitmap, getLocalFilePathFromUrl(url),".jpg");
		}
		
		return bitmap;
	}

	
	// ///////////////////////////////加载图片生成本地文件返回文件路径/////////////////////////////////
	
	@Override
	protected String downloadBitmap(String urlString) {
		String fileName = getLocalFilePathFromUrl(urlString);

		HttpURLConnection urlConnection = null;
		BufferedOutputStream out = null;

		try {
			File tmpDir = new File(Globe.IMAGE_TEMP_DIR);
			if (!tmpDir.exists() || !tmpDir.isDirectory()) {
				tmpDir.mkdirs();
			}
			final URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			final InputStream in = new BufferedInputStream(
					urlConnection.getInputStream(), 8 * 1024);
			out = new BufferedOutputStream(new FileOutputStream(fileName), 8 * 1024);

			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}

//			System.out.println("downloadBitmap....");

			return fileName;
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			if (out != null) {
				try {
					out.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
}
