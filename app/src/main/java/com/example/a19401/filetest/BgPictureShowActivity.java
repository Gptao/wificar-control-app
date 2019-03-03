package com.example.a19401.filetest;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class BgPictureShowActivity extends Activity implements
AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {
	/*
     *
     * 这个类用于显示保存下来的图片，通过gallery控件，将SD卡指定路径中的图片
     * 一一读出，并显示在屏幕上。
     *
     * */
private List<String> ImageList;
private String[]list;
private ImageSwitcher mSwitcher;  //图片切换控件
public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pictureshow);

			ImageList=getSD();
			list = ImageList.toArray(new String[ImageList.size()]);

			mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
			mSwitcher.setFactory(this);

			mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
			android.R.anim.fade_in));

			mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
			android.R.anim.fade_out));
			mSwitcher.setOnClickListener(new OnClickListener(){


			public void onClick(View v) {
				//Toast.makeText(BgPictureShowActivity.this, "点击了", Toast.LENGTH_SHORT).show();

			}

		});

		Gallery g = (Gallery) findViewById(R.id.mygallery);
		g.setAdapter(new ImageAdapter(this,ImageList));
		g.setOnItemSelectedListener(this);
		g.setOnItemClickListener(new OnItemClickListener()
		{
		public void onItemClick(AdapterView<?> parent,
                                View v, int position, long id)
		{
			Toast.makeText(BgPictureShowActivity.this, "第"+(position+1)+"张图片", Toast.LENGTH_SHORT).show();
		}
		});
		}

		private List<String> getSD()
		{

			List<String> it=new ArrayList<String>();
			File f1 = new File("/storage/emulated/0/vp");
			//如果存在  
			if (f1.isDirectory()) {
				System.out.println("该文件夹存在！");
				//将文件夹下面的所有文件 组成数组  
				File lists[] = f1.listFiles();
				if(lists!=null)
				{ System.out.println("文件夹下面有" + lists.length + "个文件");
					//遍历，取出所有的文件名称  
					for (int i = 0; i < lists.length; i++) {
						System.out.println("文件名 ：" + lists[i].getName());
					}
						for(int i=0;i<lists.length;i++)
						{
							File file=lists[i];
							if(getImageFile(file.getPath()))
								it.add(file.getPath());
						}

				}
				else{Log.d(TAG,"list is empty");}
			} else {
//如果不存在该文件夹，则输出  
				System.out.println("该文件夹不存在！");
				f1.mkdir();
			}
			return it;
		}

		private boolean getImageFile(String fName)
		{
			boolean re;
			String end=fName.substring(fName.lastIndexOf(".")+1,
			fName.length()).toLowerCase();
			if(end.equals("jpg")||end.equals("gif")||end.equals("png")
			||end.equals("jpeg")||end.equals("bmp"))
			{
				re=true;
			}
			else
			{
				re=false;
			}
			return re;
		}


		public class ImageAdapter extends BaseAdapter
		{

		int mGalleryItemBackground;
		private Context mContext;
		private List<String> lis;


		public ImageAdapter(Context c, List<String> li)
		{
		mContext = c;
		lis=li;

		TypedArray a = obtainStyledAttributes(R.styleable.Gallery);

		mGalleryItemBackground = a.getResourceId(
		R.styleable.Gallery_android_galleryItemBackground, 0);

		a.recycle();
		}


		public int getCount()
		{
		return lis.size();
		}

		public Object getItem(int position)
		{
		return position;
		}


		public long getItemId(int position)
		{
		return position;
		}


		public View getView(int position, View convertView,
                            ViewGroup parent)
		{

		ImageView i = new ImageView(mContext);

		Bitmap bm = BitmapFactory.decodeFile(lis.get(position).toString());
		i.setImageBitmap(bm);

		i.setScaleType(ImageView.ScaleType.FIT_XY);

		i.setLayoutParams(new LayoutParams(480, 320));

		i.setBackgroundResource(mGalleryItemBackground);

		return i;
		}
		}


		public void onItemSelected(AdapterView<?> parent, View view, int position,
                                   long id) {
			String photoURL=list[position];
			Log.i("A", String.valueOf(position));

			mSwitcher.setImageURI(Uri.parse(photoURL));

		}

		public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

		}

		public View makeView() {
			ImageView i = new ImageView(this);
			i.setBackgroundColor(0xFF000000);
			i.setScaleType(ImageView.ScaleType.FIT_CENTER);
			i.setLayoutParams(new ImageSwitcher.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			return i;
		}
}