package com.example.a19401.filetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MyVideo extends Activity implements VerticalColorSeekBar.OnStateChangeListener {
    private Button TakePhotos;//拍摄按钮
    private Button ViewPhotos;//查看按钮
    private ImageView BtnForward, BtnBackward, BtnLeft, BtnRight, BtnStop;
    private Button SD,GW;
    private VerticalColorSeekBar V1, V2;
    private Spinner myspinner;
    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    float x1;
    float x2;
    float y1;
    float y2;
    int sumx=50,sumy=50;
    URL videoUrl;
    public static String CameraIp;
    public static String CtrlIp;
    public static String CtrlPort;
    MySurfaceView r;
    private Socket socket;
    OutputStream socketWriter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.myvideo);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        r = (MySurfaceView) findViewById(R.id.mySurfaceViewVideo);
        TakePhotos = (Button) findViewById(R.id.TakePhoto);
        ViewPhotos = (Button) findViewById(R.id.ViewPhoto);
        SD=(Button) findViewById(R.id.sd);
        GW=(Button)findViewById(R.id.gw);
        myspinner = (Spinner) findViewById(R.id.spinner);
        BtnForward = (ImageView) findViewById(R.id.button_forward);
        BtnBackward = (ImageView) findViewById(R.id.button_backward);
        BtnLeft = (ImageView) findViewById(R.id.button_left);
        BtnRight = (ImageView) findViewById(R.id.button_right);
        BtnStop = (ImageView) findViewById(R.id.button_stop);
        V1 = (VerticalColorSeekBar) findViewById(R.id.vtseekbar1);
        V2 = (VerticalColorSeekBar) findViewById(R.id.vtseekbar2);
        initEvents();
        list.add("手动模式");
        list.add("红外跟随");
        list.add("红外巡线");
        list.add("红外避障");
        list.add("超声波雷达避障");
        list.add("超声波距离显示");
        list.add("温湿度显示");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        myspinner.setAdapter(adapter);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        myspinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
               switch (arg2) {
                   case 1:
                       try
                   {
                       socketWriter.write(new byte[]{(byte)0xff,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0xff});
                       socketWriter.flush();
                   }
                   catch (Exception e)
                   {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
                   }
                   break;
                   case 2:
                       try
                       {
                           socketWriter.write(new byte[]{(byte)0xff,(byte)0x13,(byte)0x02,(byte)0x00,(byte)0xff});
                           socketWriter.flush();
                       }
                       catch (Exception e)
                       {
                           // TODO Auto-generated catch block
                           e.printStackTrace();
                       }
                       break;
                   case 3:
                       try
                       {
                           socketWriter.write(new byte[]{(byte)0xff,(byte)0x13,(byte)0x03,(byte)0x00,(byte)0xff});
                           socketWriter.flush();
                       }
                       catch (Exception e)
                       {
                           // TODO Auto-generated catch block
                           e.printStackTrace();
                       }
                       break;
                   case 4:
                       try
                       {
                           socketWriter.write(new byte[]{(byte)0xff,(byte)0x13,(byte)0x04,(byte)0x00,(byte)0xff});
                           socketWriter.flush();
                       }
                       catch (Exception e)
                       {
                           // TODO Auto-generated catch block
                           e.printStackTrace();
                       }
                       break;
                   case 5:
                       try
                       {
                           socketWriter.write(new byte[]{(byte)0xff,(byte)0x13,(byte)0x05,(byte)0x00,(byte)0xff});
                           socketWriter.flush();
                       }
                       catch (Exception e)
                       {
                           // TODO Auto-generated catch block
                           e.printStackTrace();
                       }
                       break;
                   case 6:
                       try
                       {
                           socketWriter.write(new byte[]{(byte)0xff,(byte)0x13,(byte)0x06,(byte)0x00,(byte)0xff});
                           socketWriter.flush();
                       }
                       catch (Exception e)
                       {
                           // TODO Auto-generated catch block
                           e.printStackTrace();
                       }
                       break;
                   case 7:
                       try
                       {
                           socketWriter.write(new byte[]{(byte)0xff,(byte)0x13,(byte)0x00,(byte)0x00,(byte)0xff});
                           socketWriter.flush();
                       }
                       catch (Exception e)
                       {
                           // TODO Auto-generated catch block
                           e.printStackTrace();
                       }
                       break;
               }
                /* 将mySpinner 显示*/
                arg0.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> arg0) {

                arg0.setVisibility(View.VISIBLE);
            }
        });
        /*下拉菜单弹出的内容选项触屏事件处理*/
        myspinner.setOnTouchListener(new Spinner.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                /**
                 *
                 */
                return false;
            }
        });
        /*下拉菜单弹出的内容选项焦点改变事件处理*/
        myspinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

            }
        });
        Intent intent = getIntent();
        CameraIp = intent.getStringExtra("CameraIp");
		CtrlIp= intent.getStringExtra("ControlUrl");		
		CtrlPort=intent.getStringExtra("Port");		
		Log.d("wifirobot", "control is :++++"+CtrlIp);
		Log.d("wifirobot", "CtrlPort is :++++"+CtrlPort);
		r.GetCameraIP(CameraIp);
		InitSocket();
        SD.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    socketWriter.write(new byte[]{(byte)0xff,(byte)0x32,(byte)0x01,(byte)0x00,(byte)0xff});
                    socketWriter.flush();
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Toast.makeText(MyVideo.this,"方向已经锁定",Toast.LENGTH_SHORT).show();
            }
        });
        GW.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    socketWriter.write(new byte[]{(byte)0xff,(byte)0x33,(byte)0x01,(byte)0x00,(byte)0xff});
                    socketWriter.flush();
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Toast.makeText(MyVideo.this,"舵机已经归位",Toast.LENGTH_SHORT).show();
            }
        });
		BtnForward.setOnClickListener(new OnClickListener(){   //前进按钮

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				  try 
			        {
			             socketWriter.write(new byte[]{(byte)0xff,(byte)0x00,(byte)0x01,(byte)0x00,(byte)0xff});
			             socketWriter.flush();
					} 
			         catch (Exception e)
			        {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		
		BtnBackward.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try 
		        {
		             socketWriter.write(new byte[]{(byte)0xff,(byte)0x00,(byte)0x02,(byte)0x00,(byte)0xff});
		             socketWriter.flush();
				} 
		         catch (Exception e)
		        {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		BtnRight.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try 
		        {
		             socketWriter.write(new byte[]{(byte)0xff,(byte)0x00,(byte)0x03,(byte)0x00,(byte)0xff});
		             socketWriter.flush();
				} 
		         catch (Exception e)
		        {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		});
		BtnLeft.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try 
		        {
		             socketWriter.write(new byte[]{(byte)0xff,(byte)0x00,(byte)0x04,(byte)0x00,(byte)0xff});
		             socketWriter.flush();
				} 
		         catch (Exception e)
		        {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		BtnStop.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try 
		        {
		             socketWriter.write(new byte[]{(byte)0xff,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0xff});
		             socketWriter.flush();
				} 
		         catch (Exception e)
		        {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		});
		TakePhotos.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
                if(TakePhotos.getText().equals("拍照"))
                TakePhotos.setText("停止");
                else
                    TakePhotos.setText("拍照");
				// TODO Auto-generated method stub
				if(null!=Constant.handler)
				{
				 Message message = new Message();
		            message.what = 1;      
		            Constant.handler.sendMessage(message);
				}
			}
			
		});
		
		ViewPhotos.setOnClickListener(new OnClickListener()
		{

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MyVideo.this, BgPictureShowActivity.class);
				MyVideo.this.startActivity(intent);

			}
			
		});
		
	}
    @Override
    public void OnStateChangeListener(View view, float progress) {

    }
    @Override
    public void onStopTrackingTouch(View view, float progress) {

        int viewId = view.getId();
        switch (viewId) {
            case R.id.vtseekbar1:
                if (progress < 0) {
                    progress = 0;
                }
                if(progress > 100) {
                    progress = 100;
                }
                try
                {
                    int temp=(int)progress;
                    String str1=String.valueOf(temp);
                    Toast.makeText(this,"左轮速度为"+str1,Toast.LENGTH_SHORT).show();
                    socketWriter.write(new byte[]{(byte)0xff,(byte)0x02,(byte)0x01,(byte)temp,(byte)0xff});
                    socketWriter.flush();
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                break;

            case R.id.vtseekbar2:
                if (progress < 0) {
                    progress = 0;
                }
                if(progress > 100) {
                    progress = 100;
                }
                try
                {
                    int temp=(int)progress;
                    String str1=String.valueOf(temp);
                    Toast.makeText(this,"右轮速度为"+str1,Toast.LENGTH_SHORT).show();
                    socketWriter.write(new byte[]{(byte)0xff,(byte)0x02,(byte)0x02,(byte)temp,(byte)0xff});
                    socketWriter.flush();
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                break;
        }
    }
	private void initEvents() {

		V1.setOnStateChangeListener(this);
        V2.setOnStateChangeListener(this);
        V1.setProgress(15);
        V2.setProgress(15);
	}

  public void InitSocket()
  {
	  
			 try {
				socket = new Socket(InetAddress.getByName(CtrlIp), Integer.parseInt(CtrlPort));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try {
				socketWriter= socket.getOutputStream();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  }
		
	public void onDestroy() 
	{
		super.onDestroy();
	
	}

	private long exitTime = 0;
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {  
		 if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		 {  
		           
		         if((System.currentTimeMillis()-exitTime) > 2500)
				 {  
					 Toast.makeText(getApplicationContext(), "发生错误", Toast.LENGTH_SHORT).show();
		        	 exitTime = System.currentTimeMillis();
				 }  
		         else  
		         {  
		             finish();  
		             System.exit(0);
		         }  
		                   
		         return true;  
		 }  
		 return super.onKeyDown(keyCode, event);  
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();

            //int x=(int)(x1>x2?(x1-x2):(x2-x1));
            //int y=(int)(y1>y2?(y1-y2):(y2-y1));
            int x = (int) ((x1 - x2)/20);
            int y = (int) ((y1 - y2)/20);
            if (y1 - y2 > 50 || y2 - y1 > 50) {
                try {
                    sumy+=y;
                    if(sumy<0)sumy=0;
                    if(sumy>100)sumy=100;
                    socketWriter.write(new byte[]{(byte) 0xff, (byte) 0x01, (byte) 0x02, (byte) sumy, (byte) 0xff});
                    socketWriter.flush();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
               // Toast.makeText(MyVideo.this, "上下", Toast.LENGTH_SHORT).show();

            }
            if (x1 - x2 > 50 || x2 - x1 > 50) {
                try {
                    sumx+=x;
                    if(sumx<0)sumx=0;
                    if(sumx>100)sumx=100;
                    socketWriter.write(new byte[]{(byte) 0xff, (byte) 0x01, (byte) 0x01, (byte) sumx, (byte) 0xff});
                    socketWriter.flush();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //Toast.makeText(MyVideo.this, "左右", Toast.LENGTH_SHORT).show();

            }
        }
            return super.onTouchEvent(event);
        }
}


