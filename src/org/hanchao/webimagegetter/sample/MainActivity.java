package org.hanchao.webimagegetter.sample;

import org.hanchao.webimagegetter.WebImageView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity extends Activity{

	private ListView listview;
	
	private LayoutInflater mInflater;
	
	private String[] imageUrls = {"http://img04.taobaocdn.com/imgextra/i4/15147020533848542/T14x8KXCFgXXXXXXXX_!!262525147-0-pix.jpg_100x100.jpg"
								,"http://img02.taobaocdn.com/imgextra/i2/16431020525278137/T1HQXKXAlfXXXXXXXX_!!737496431-0-pix.jpg_100x100.jpg"
								,"http://img04.taobaocdn.com/imgextra/i4/18906033677824352/T18fXLXtXdXXXXXXXX_!!297648906-0-pix.jpg_100x100.jpg"
								,"http://img04.taobaocdn.com/imgextra/i4/16431022448947970/T17dxMXr4bXXXXXXXX_!!737496431-0-pix.jpg_100x100.jpg"
								,"http://img04.taobaocdn.com/imgextra/i4/16431020519882797/T1kntLXuFcXXXXXXXX_!!737496431-0-pix.jpg_100x100.jpg"
								,"http://img03.taobaocdn.com/imgextra/i3/14701020493989789/T1QRBMXqtXXXXXXXXX_!!140264701-0-pix.jpg_100x100.jpg"
								,"http://img01.taobaocdn.com/imgextra/i1/10759020463476531/T1unNLXrNXXXXXXXXX_!!892240759-0-pix.jpg_100x100.jpg"
								,"http://img04.taobaocdn.com/imgextra/i4/18565022403003849/T12TtJXxtgXXXXXXXX_!!23328565-0-pix.jpg_100x100.jpg"
								,"http://img03.taobaocdn.com/imgextra/i3/16431020522061662/T1Nu0KXEpgXXXXXXXX_!!737496431-0-pix.jpg_100x100.jpg"
								,"http://img04.taobaocdn.com/imgextra/i4/19982034310616186/T1vxd_XvXfXXXXXXXX_!!22279982-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19982023499155678/T1bsR.XANcXXXXXXXX_!!22279982-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19982021583248550/T1ce8.XxXcXXXXXXXX_!!22279982-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19982021566220561/T1Lt0.XANbXXXXXXXX_!!22279982-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19982021564510676/T1utx.XrXcXXXXXXXX_!!22279982-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19982021564068565/T1jfN.XyBbXXXXXXXX_!!22279982-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19982021555753683/T1OAV.XyBaXXXXXXXX_!!22279982-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19982021554673648/T1fg49XxlgXXXXXXXX_!!22279982-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19982021553907234/T1F88.XylaXXXXXXXX_!!22279982-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19982021553647457/T1Tdp.XAxbXXXXXXXX_!!22279982-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19982021213554939/T18T02XrpbXXXXXXXX_!!22279982-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19982021213460520/T1P2h1XyNfXXXXXXXX_!!22279982-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19950033899460223/T1dZhZXtheXXXXXXXX_!!672289950-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19950033867508415/T11KFZXwNcXXXXXXXX_!!672289950-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19950023861843942/T1R8CdXu8hXXXXXXXX_!!672289950-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19950021937783419/T1ob_3Xm4lXXXXXXXX_!!672289950-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19950021128496770/T1WwB0XChcXXXXXXXX_!!672289950-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19950021126414523/T1N5BZXrFeXXXXXXXX_!!672289950-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19950021119219347/T1CsR0XrXdXXXXXXXX_!!672289950-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19950021116043253/T1JRN1XsRXXXXXXXXX_!!672289950-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19950021084557725/T10RNZXstbXXXXXXXX_!!672289950-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19946021577014434/T1kX4_XrtfXXXXXXXX_!!409009946-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19937021580998519/T1Sq9XXD4aXXXXXXXX_!!90109937-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19937021574275165/T12dd.XpJdXXXXXXXX_!!90109937-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19905021957622967/T1wjieXCBgXXXXXXXX_!!733549905-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19905021949865549/T1GImhXEJbXXXXXXXX_!!733549905-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19905021581093363/T1WzV_Xv4eXXXXXXXX_!!733549905-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19905021212706591/T1ok02XrhcXXXXXXXX_!!733549905-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19905021205241642/T1AF43XAdbXXXXXXXX_!!733549905-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19881021582688509/T1J_t_Xr4dXXXXXXXX_!!15099881-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19881021579377466/T1dEJ.XqNbXXXXXXXX_!!15099881-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19880023130579714/T1lW82XrNfXXXXXXXX_!!408979880-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19857023499179514/T1qIl.XvpcXXXXXXXX_!!134639857-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19857021581574745/T1M.09XuFgXXXXXXXX_!!134639857-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19857021580189282/T1i.d_XDtdXXXXXXXX_!!134639857-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19857021575954279/T12EGXXppXXXXXXXXX_!!134639857-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19857021573435286/T1.SiXXvxXXXXXXXXX_!!134639857-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19857021573279011/T1HSV.XvdbXXXXXXXX_!!134639857-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19819021194518412/T1pal3XApaXXXXXXXX_!!88819819-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19819021191645913/T16uN2XEpcXXXXXXXX_!!88819819-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19772034014188226/T1Xnh3XqRcXXXXXXXX_!!711209772-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19772023360539980/T1Lsp8XvJbXXXXXXXX_!!711209772-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19772021246901375/T111h3XsdcXXXXXXXX_!!711209772-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19725021560425410/T1Ljx9Xz0fXXXXXXXX_!!515339725-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706034515976463/T1v2GiXrXXXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706033985568178/T1f.B3XzpXXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706033914436391/T1AS41XtdaXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706033914296160/T1qnd1XshaXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706023880955519/T1m2SiXxlXXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706023879907733/T1oYagXpxeXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706023873827522/T1yVmhXz8bXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706022535995674/T184lOXyhXXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706022535291913/T1Q2pMXqXfXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706022535059605/T1NHhMXqRhXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021970116803/T1e0KgXsFeXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021964806741/T1xk9gXpldXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021963121074/T11E1eXz0gXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021962749327/T1pe5iXzJXXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021962081359/T1KKWgXpdeXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021962009047/T1Rw9fXDXgXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021961457436/T1FjCiXp8XXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021959050362/T1RdKiXARXXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021957577907/T1I99hXylaXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021955597193/T1cWChXqBcXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021955493270/T1.jShXAVaXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021950727376/T19VmhXt0cXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021950509538/T1CGWhXDpbXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021210846140/T1tzN1XAlfXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021209174946/T1MzR2XwJbXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021202186080/T1hdX2XzFdXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021143964997/T1tF81XABcXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021143832551/T1vpF1Xp4cXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021140109236/T1KFN0XuteXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021140041172/T1qNX1XtFbXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021135735270/T1A0t0XpteXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021135679119/T1CVR0XqpeXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021135523439/T1ONR0XARdXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021135490312/T16JB0XAlcXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706021132657521/T1ktx2XzhXXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020624846628/T1G8dNXqhcXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020621478584/T1sSBNXutbXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020620185239/T1__pMXCNeXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020618680921/T1tjNMXAxeXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020618545287/T11JhMXx4gXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020618340926/T1o6XNXxRcXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020618153017/T1O40OXxFXXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020613295402/T1OU0MXBJdXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020613163406/T1K7xNXxBcXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020612463056/T1fhFMXD0fXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020611573978/T1koFOXpFXXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020610462232/T1RaxNXvVdXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020609342067/T1hClMXzReXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19706020607541519/T1WnFNXrBbXXXXXXXX_!!87639706-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19651023499539621/T1Xd4.XqVdXXXXXXXX_!!1098189651-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19651021955718761/T1YCagXypcXXXXXXXX_!!1098189651-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19651021955714893/T1KRWgXp8cXXXXXXXX_!!1098189651-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19651021952061664/T1PLygXApeXXXXXXXX_!!1098189651-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19651021951243369/T16UifXCVdXXXXXXXX_!!1098189651-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19651021949055434/T1Xc9hXyRbXXXXXXXX_!!1098189651-0-pix.jpg_200x200.jpg",
								"http://img04.taobaocdn.com/imgextra/i4/19651021583692977/T147J.XCBbXXXXXXXX_!!1098189651-0-pix.jpg_200x200.jpg"};
	
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
			
			WebImageView image = (WebImageView)convertView.findViewById(R.id.image);
			image.setImageUrl(imageUrls[position],R.drawable.ic_launcher);
			
			return convertView;
		}
		
	}
	
}
