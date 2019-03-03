package com.example.a19401.filetest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn=(Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File f1 = new File("/storage/emulated/0/vp");
//                //如果存在  
//                if (f1.isDirectory()) {
//                    System.out.println("已经存在该文件夹！");
//
//                    //将文件夹下面的所有文件 组成数组  
//                    File lists[] = f1.listFiles();
//                    if(lists!=null)
//                    { System.out.println("文件夹下面有" + lists.length + "个文件");
//                    //遍历，取出所有的文件名称  
//                    for (int i = 0; i < lists.length; i++) {
//                        System.out.println("文件名 ：" + lists[i].getName());
//                    }}
//                } else {
////如果不存在该文件夹，则输出  
//                    System.out.println("该文件夹不存在！");
//                    f1.mkdir();
//                }
//
//                //在该文件夹下面创建 新的文件  
//                File f2 = new File("/storage/emulated/0/vp/jj.png");
//                //如果存在在文件  
//                if (f2.exists()) {
//                    System.out.println("已经存在该文件");
//                } else {
//                    try {
//                        f2.createNewFile();
//                    } catch (IOException e) {
//// TODO Auto-generated catch block  
//                        e.printStackTrace();
//                    }
//                }
               // saveBitmap();
                getSD();
            }
        });


        }
    private void getSD()
    {
        File f1 = new File("/storage/emulated/0/vp");
        if (!f1.exists()) {
            f1.mkdirs();
        }
        else Log.d("vp","存在目录!");
        File lis[] = f1.listFiles();
        if(lis!=null)
        { System.out.println("文件夹下面有" + lis.length + "个文件");
            //遍历，取出所有的文件名称  
            for (int i = 0; i < lis.length; i++) System.out.println("文件名 ：" + lis[i].getName());
        }
        File f2 = new File("/storage/emulated/0/vp/test.png");
        if (f2.exists()) {
            System.out.println("已经存在该文件");
        } else
            try {
                f2.createNewFile();
            } catch (IOException e) {
                Log.d("error","IOException");

            }
    }
    public void saveBitmap() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("/storage/emulated/0/DCIM/Screenshots/test.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap mBitmap= BitmapFactory.decodeStream(fis);
        Log.e(TAG, "保存图片");
        File f = new File("/storage/emulated/0/vp/test.png");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }}