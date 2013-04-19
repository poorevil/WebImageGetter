package org.hanchao.webimagegetter.sample;

import org.hanchao.webimagegetter.WebImageView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends Activity{

	private ListView listview;
	
	private LayoutInflater mInflater;
	
	private Handler handler = new Handler();
	
	private boolean isLoadListViewImg = true; //图片加载开关
	
	private String[] imageUrls = {"http://img04.taobaocdn.com/imgextra/i4/15147020533848542/T14x8KXCFgXXXXXXXX_!!262525147-0-pix.jpg_100x100.jpg"
								,"http://img02.taobaocdn.com/imgextra/i2/16431020525278137/T1HQXKXAlfXXXXXXXX_!!737496431-0-pix.jpg_100x100.jpg"
								,"http://img04.taobaocdn.com/imgextra/i4/18906033677824352/T18fXLXtXdXXXXXXXX_!!297648906-0-pix.jpg_100x100.jpg"
								,"http://img04.taobaocdn.com/imgextra/i4/16431022448947970/T17dxMXr4bXXXXXXXX_!!737496431-0-pix.jpg_100x100.jpg"
								,"http://img04.taobaocdn.com/imgextra/i4/16431020519882797/T1kntLXuFcXXXXXXXX_!!737496431-0-pix.jpg_100x100.jpg"
								,"http://img03.taobaocdn.com/imgextra/i3/14701020493989789/T1QRBMXqtXXXXXXXXX_!!140264701-0-pix.jpg_100x100.jpg"
								,"http://img01.taobaocdn.com/imgextra/i1/10759020463476531/T1unNLXrNXXXXXXXXX_!!892240759-0-pix.jpg_100x100.jpg"
								,"http://img04.taobaocdn.com/imgextra/i4/18565022403003849/T12TtJXxtgXXXXXXXX_!!23328565-0-pix.jpg_100x100.jpg"
								,"http://img03.taobaocdn.com/imgextra/i3/16431020522061662/T1Nu0KXEpgXXXXXXXX_!!737496431-0-pix.jpg_100x100.jpg"
								,"http://img01.taobaocdn.com/imgextra/i1/18805033671896491/T1tnNvXsBhXXXXXXXX_!!472868805-0-pix.jpg_100x100.jpg"
								,"http://img03.taobaocdn.com/imgextra/i3/18805020508648687/T17CFwXrlhXXXXXXXX_!!472868805-0-pix.jpg_100x100.jpg"
								,"http://img02.taobaocdn.com/imgextra/i2/15614020520450281/T1H3NLXs8dXXXXXXXX_!!75105614-0-pix.jpg_100x100.jpg"
								,"http://img01.taobaocdn.com/imgextra/i1/10277020493885030/T1rWhLXrpdXXXXXXXX_!!168180277-0-pix.jpg_100x100.jpg"
								,"http://img04.taobaocdn.com/imgextra/i4/16431022448947970/T17dxMXr4bXXXXXXXX_!!737496431-0-pix.jpg_100x100.jpg"
								,"http://img04.taobaocdn.com/imgextra/i4/16431020519882797/T1kntLXuFcXXXXXXXX_!!737496431-0-pix.jpg_100x100.jpg"
								,"http://img03.taobaocdn.com/imgextra/i3/14701020493989789/T1QRBMXqtXXXXXXXXX_!!140264701-0-pix.jpg_100x100.jpg"
								,"http://img01.taobaocdn.com/imgextra/i1/10759020463476531/T1unNLXrNXXXXXXXXX_!!892240759-0-pix.jpg_100x100.jpg"
								,"http://img04.taobaocdn.com/imgextra/i4/18565022403003849/T12TtJXxtgXXXXXXXX_!!23328565-0-pix.jpg_100x100.jpg"
								,"http://img03.taobaocdn.com/imgextra/i3/16431020522061662/T1Nu0KXEpgXXXXXXXX_!!737496431-0-pix.jpg_100x100.jpg"
								,"http://img01.taobaocdn.com/imgextra/i1/18805033671896491/T1tnNvXsBhXXXXXXXX_!!472868805-0-pix.jpg_100x100.jpg"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		initView();
	}
	
	private void initView(){
		
		mInflater = LayoutInflater.from(MainActivity.this);
		
		listview = (ListView)findViewById(R.id.list);
		listview.setAdapter(new ListAdapter());
		
//		listview.setOnScrollListener(new AbsListView.OnScrollListener() {
//			@Override
//			public void onScroll(AbsListView view,
//					int firstVisibleItem, int visibleItemCount,
//					int totalItemCount) {
//			}
//
//			@Override
//			public void onScrollStateChanged(AbsListView view,
//					int scrollState) {
//				if(scrollState == SCROLL_STATE_FLING){ //滑动时停止加载图片
//					isLoadListViewImg = false;
//				}
//				else {
//					isLoadListViewImg = true;
//					new WebImageGetterForChartListView(0).start();
//				}
//			}
//		});
//		
//		new WebImageGetterForChartListView(500).start();
		
	}
	
	class ListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView==null){
				
				convertView = mInflater.inflate(R.layout.cellview_layout, null);
				
			}
			
//			ImageView image = (ImageView)convertView.findViewById(R.id.image);
//			convertView.setTag(imageUrls[position]+position);
//			if(ImageWorker.getInstance().isBitmapExist(imageUrls[position])){
//				image.setImageBitmap(ImageWorker.getInstance().getBitmapFromMemCache(imageUrls[position]));
//			}else{
//				image.setImageResource(R.drawable.ic_launcher);
//			}
			
			WebImageView image = (WebImageView)convertView.findViewById(R.id.image);
			image.setImageUrl(imageUrls[position]);
			
			
			return convertView;
		}
		
	}
	
//	public class WebImageGetterForChartListView extends Thread implements LoadImgCallable{
//
//		private int sleepTime;
//
//		public WebImageGetterForChartListView(int sleepTime) {
//			this.sleepTime = sleepTime;
//		}
//		
//		@Override
//		public void run() {
//			
//			if(sleepTime>0){
//				try{
//					Thread.sleep(sleepTime);
//				}catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			
//			try {
//				if (imageUrls != null && imageUrls.length > 0 && listview != null) {
//					int beginOffSet = listview.getFirstVisiblePosition();
//					int endOffSet = listview.getLastVisiblePosition();
//					
////					System.out.println("beginOffSet : " + beginOffSet + " endOffSet : " + endOffSet);
//					
//					for (; beginOffSet <= endOffSet; beginOffSet++) {
//						
//						if(!isLoadListViewImg || !(beginOffSet < imageUrls.length)){
//							break;
//						}
//						if (!this.isInterrupted()) {
//							
//							ImageWorker.getInstance().getBitmapFromUrl(MainActivity.this
//									, beginOffSet, imageUrls[beginOffSet], this);
//							
//							if (ImageWorker.getInstance().isBitmapNull(imageUrls[beginOffSet])){
//								ImageWorker.getInstance().getBitmapFromUrl(MainActivity.this
//										, beginOffSet, imageUrls[beginOffSet], this);
//								
//							}else{
//								setViewImage(beginOffSet,imageUrls[beginOffSet]);
//							}
//							
//						} else {
//							return;
//						}
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				return;
//			}
//		}
//		
//		@Override
//		public void setViewImage(final int position, final String url) {
//			
//			if(ImageWorker.getInstance().isBitmapExist(url)){
//				
//				final Bitmap map = ImageWorker.getInstance().getBitmapFromMemCache(url);
//				
//				handler.post(new Runnable() {
//					@Override
//					public void run() {
//						if(imageUrls != null && imageUrls.length > 0 && position <= imageUrls.length-1){
//							
//							View convertView = (View)listview.findViewWithTag(imageUrls[position]+position);
//							
//							if(convertView==null)
//								return;
//							
//							ImageView icon = (ImageView)convertView.findViewById(R.id.image);
//							
//							if(icon != null && isLoadListViewImg){
//								if(map != null && !map.isRecycled()){
//									icon.setImageBitmap(map);
//								}
//							}
//						}
//					}
//				});
//			}
//		}
//	}
	
}
