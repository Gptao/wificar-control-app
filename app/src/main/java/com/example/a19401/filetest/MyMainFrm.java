package com.example.a19401.filetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MyMainFrm extends Activity {

	EditText CameraIP,ControlIP,Port;
	Button Button_go;
	String videoUrl,controlUrl,port;
	public static String CameraIp;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mymainfrm);	
		
		CameraIP = (EditText) findViewById(R.id.editIP);
		ControlIP = (EditText) findViewById(R.id.ip);
		Port = (EditText) findViewById(R.id.port);
		
		Button_go = (Button) findViewById(R.id.button_go);
		
		videoUrl = CameraIP.getText().toString();
		controlUrl = ControlIP.getText().toString();
		port = Port.getText().toString();
		
		Button_go.requestFocusFromTouch();

        
		Button_go.setOnClickListener(new Button.OnClickListener()
		{
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Intent intent = new Intent();
    			intent.putExtra("CameraIp", videoUrl);//通过键值对的方式在不同的应用程序或者Activity之间传递信息
    			intent.putExtra("ControlUrl", controlUrl);
    			intent.putExtra("Port", port);
    			
    			intent.putExtra("Is_Scale", true);
    			intent.setClass(MyMainFrm.this, MyVideo.class);
    			MyMainFrm.this.startActivity(intent);
    			finish();// 用于结束一个MyMainFrm的生命周期
	            System.exit(0);
    		}
    	});
    
	}
	private long exitTime = 0;
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {  
                 if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
                 {  
                           
                         if((System.currentTimeMillis()-exitTime) > 2000)
                         {  
                          //Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
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

        

   

}


