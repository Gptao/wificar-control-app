package com.example.a19401.filetest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MySurfaceView extends SurfaceView implements Callback {
    private SurfaceHolder sfh;
    private Canvas canvas;
    URL videoUrl;
    private String urlstr;
    HttpURLConnection conn;
    Bitmap bmp;
    private Paint p;
    InputStream inputstream = null;
    private Bitmap mBitmap;
    private static int mScreenWidth;
    private static int mScreenHeight;
    public boolean Is_Scale = false;
    public boolean take_photo=false;
    private boolean isThreadRunning = true;
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Constant.context = context;
        initialize();
        p = new Paint();
        p.setAntiAlias(true);
        sfh = this.getHolder();
        sfh.addCallback(this);
        this.setKeepScreenOn(true);
        setFocusable(true);
        this.getWidth();
        this.getHeight();
        Constant.handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        if(null !=mBitmap)
                        {
                           take_photo=!take_photo;
                        }
                        else
                        {
                            Toast.makeText(Constant.context, "拍照失败，未能获取摄像头图像",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                super.handleMessage(msg);
            }

        };

    }

    private void initialize() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        this.setKeepScreenOn(true);// 保持屏幕常亮
    }
    public void saveMyBitmap(String bitName, Bitmap mBitmap) {
        File f1 = new File("/storage/emulated/0/vp");
        if (!f1.exists()) {
            f1.mkdirs();
            Log.d("vp","不存在目录!");
        }
        else Log.d("vp","存在目录!");
        File lis[] = f1.listFiles();
        if(lis!=null)
        { System.out.println("文件夹下面有" + lis.length + "个文件");
            //遍历，取出所有的文件名称??
            for (int i = 0; i < lis.length; i++) System.out.println("文件名?：" + lis[i].getName());
        }
        File f2 = new File("/storage/emulated/0/vp/"+bitName+System.currentTimeMillis()+".png");
        if (f2.exists()) {
            System.out.println("已经存在该文件");
        } else
            try {
                f2.createNewFile();
            } catch (IOException e) {
                Log.d("error","IOException");

            }
        OutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("error","FileNotFound");
        }

        Log.d("MySurface", "bitmap is:" + mBitmap + "fout is:" + fOut);
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class Takephoto extends Thread {
        public Takephoto(){}
        @Override
        protected Object clone() throws CloneNotSupportedException {
            // TODO Auto-generated method stub
            return super.clone();
        }
        public void run() {
            while(true)
                while(take_photo)
                {
                        saveMyBitmap("img", mBitmap);
                    try {
                        Thread.sleep(50);
                        Log.d("test","take photo complete");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
    class DrawVideo extends Thread {
        public DrawVideo() {
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            // TODO Auto-generated method stub
            return super.clone();
        }

        public void run() {
            Paint pt = new Paint();
            pt.setAntiAlias(true);
            pt.setColor(Color.GREEN);
            pt.setTextSize(20);
            pt.setStrokeWidth(1);

            int bufSize = 512 * 1024; // 视频图片缓冲
            byte[] jpg_buf = new byte[bufSize]; // buffer to read jpg

            int readSize = 4096; // 每次最大获取的流
            byte[] buffer = new byte[readSize]; // buffer to read stream

            while (isThreadRunning) {
                long Time = 0;
                long Span = 0;
                int fps = 0;
                String str_fps = "0 fps";

                URL url = null;
                HttpURLConnection urlConn = null;

                try {
                    url = new URL(urlstr);
                    urlConn = (HttpURLConnection) url.openConnection(); // 使用HTTPURLConnetion打开连接

                    Time = System.currentTimeMillis();

                    int read = 0;
                    int status = 0;
                    int jpg_count = 0; // jpg数据下标

                    while (true) {
                        read = urlConn.getInputStream().read(buffer, 0,
                                readSize);

                        if (read > 0) {

                            for (int i = 0; i < read; i++) {
                                switch (status) {
                                    // Content-Length:
                                    case 0:
                                        if (buffer[i] == (byte) 'C')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 1:
                                        if (buffer[i] == (byte) 'o')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 2:
                                        if (buffer[i] == (byte) 'n')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 3:
                                        if (buffer[i] == (byte) 't')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 4:
                                        if (buffer[i] == (byte) 'e')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 5:
                                        if (buffer[i] == (byte) 'n')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 6:
                                        if (buffer[i] == (byte) 't')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 7:
                                        if (buffer[i] == (byte) '-')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 8:
                                        if (buffer[i] == (byte) 'L')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 9:
                                        if (buffer[i] == (byte) 'e')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 10:
                                        if (buffer[i] == (byte) 'n')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 11:
                                        if (buffer[i] == (byte) 'g')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 12:
                                        if (buffer[i] == (byte) 't')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 13:
                                        if (buffer[i] == (byte) 'h')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 14:
                                        if (buffer[i] == (byte) ':')
                                            status++;
                                        else
                                            status = 0;
                                        break;
                                    case 15:
                                        if (buffer[i] == (byte) 0xFF)
                                            status++;
                                        jpg_count = 0;
                                        jpg_buf[jpg_count++] = (byte) buffer[i];
                                        break;
                                    case 16:
                                        if (buffer[i] == (byte) 0xD8) {
                                            status++;
                                            jpg_buf[jpg_count++] = (byte) buffer[i];
                                        } else {
                                            if (buffer[i] != (byte) 0xFF)
                                                status = 15;

                                        }
                                        break;
                                    case 17:
                                        jpg_buf[jpg_count++] = (byte) buffer[i];
                                        if (buffer[i] == (byte) 0xFF)
                                            status++;
                                        if (jpg_count >= bufSize)
                                            status = 0;
                                        break;
                                    case 18:
                                        jpg_buf[jpg_count++] = (byte) buffer[i];
                                        if (buffer[i] == (byte) 0xD9) {
                                            status = 0;
                                            // jpg接收完成

                                            fps++;
                                            Span = System.currentTimeMillis()
                                                    - Time;
                                            if (Span > 1000L) {
                                                Time = System.currentTimeMillis();
                                                str_fps = String.valueOf(fps)
                                                        + " fps";
                                                fps = 0;
                                            }
                                            // 显示图像
                                            //if (null != canvas)
                                            {
                                                canvas = sfh.lockCanvas();
                                                canvas.drawColor(Color.BLACK);

                                                Bitmap bmp = BitmapFactory.decodeStream(new ByteArrayInputStream(jpg_buf));

                                                int width = mScreenWidth;
                                                int height = mScreenHeight;

                                                if(null!=bmp)
                                                {
                                                    float rate_width = (float) mScreenWidth/ (float) bmp.getWidth();
                                                    float rate_height = (float) mScreenHeight/ (float) bmp.getHeight();
                                                }

//											if (Is_Scale) {
//												if (rate_width > rate_height)
//													width = (int) ((float) bmp
//															.getWidth() * rate_height);
//												if (rate_width < rate_height)
//													height = (int) ((float) bmp
//															.getHeight() * rate_width);
//
//											}

                                                //Log.d("bmp","bmp"+bmp);
                                                //Log.d("width","width"+width);
                                                // Log.d("height","height"+height);
                                                if(null!=bmp)
                                                {
                                                    mBitmap = Bitmap
                                                            .createScaledBitmap(bmp,
                                                                    width, height,
                                                                    false);

                                                    canvas.drawBitmap(
                                                            mBitmap,
                                                            (mScreenWidth - width) / 2,
                                                            (mScreenHeight - height) / 2,
                                                            null);

                                                    canvas.drawText(str_fps, 2, 22, pt);
                                                }
                                                sfh.unlockCanvasAndPost(canvas);// 画完一副图像，解锁画布
                                            }
                                        } else {
                                            if (buffer[i] != (byte) 0xFF)
                                                status = 17;
                                        }
                                        break;
                                    default:
                                        status = 0;
                                        break;

                                }
                            }
                        }
                    }
                } catch (IOException ex) {
                    urlConn.disconnect();
                    ex.printStackTrace();
                }
            }

        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        isThreadRunning=false;
        try
        {
            Thread.sleep(300);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void GetCameraIP(String p) {
        urlstr = p;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        isThreadRunning=true;
        take_photo=false;
        new DrawVideo().start();
        new Takephoto().start();
    }
}