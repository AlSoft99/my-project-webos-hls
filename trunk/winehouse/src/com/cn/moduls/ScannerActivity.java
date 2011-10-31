package com.cn.moduls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.R;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.util.PlanarYUVLuminanceSource;

public class ScannerActivity extends Activity {
	/** Called when the activity is first created. */      
    private SurfaceView sfvCamera;      
    private SFHCamera sfhCamera;      
    private ImageView imgView;      
    private View centerView;      
    private TextView txtScanResult;      
    private Timer mTimer;      
    private MyTimerTask mTimerTask;      
    // 按照标准HVGA      
    final static int width = 480;      
    final static int height = 320;      
    int dstLeft, dstTop, dstWidth, dstHeight;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.moduls_scanner);
		this.setTitle("Android条码/二维码识别Demo-----hellogv");      
        imgView = (ImageView) this.findViewById(R.id.ImageView01);      
        centerView = (View) this.findViewById(R.id.centerView);      
        sfvCamera = (SurfaceView) this.findViewById(R.id.sfvCamera);      
        sfhCamera = new SFHCamera(sfvCamera.getHolder(), width, height,      
                previewCallback);      
        txtScanResult=(TextView)this.findViewById(R.id.txtScanResult);      
        // 初始化定时器      
        mTimer = new Timer();      
        mTimerTask = new MyTimerTask();      
        mTimer.schedule(mTimerTask, 0, 2000);      
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alph);    
        ((View)findViewById(R.id.animationLine)).startAnimation(animation); 
	}
	
	class MyTimerTask extends TimerTask {      
        @Override      
        public void run() {      
            if (dstLeft == 0) {//只赋值一次      
                dstLeft = centerView.getLeft() * width      
                        / getWindowManager().getDefaultDisplay().getWidth();      
                dstTop = centerView.getTop() * height      
                        / getWindowManager().getDefaultDisplay().getHeight();      
                dstWidth = ((centerView.getRight() - centerView.getLeft())* width      
                        / getWindowManager().getDefaultDisplay().getWidth());      
                dstHeight = (centerView.getBottom() - centerView.getTop())* height      
                        / getWindowManager().getDefaultDisplay().getHeight();      
                System.out.println("dstLeft:"+dstLeft+"   dstTop:"+dstTop+"   dstWidth:"+dstWidth+"   dstHeight:"+dstHeight);  
                sfhCamera.AutoFocusAndPreviewCallback();  
            }      
              
        }      
    }      
    /**   
     *  自动对焦后输出图片   
     */      
    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {      
        @Override      
        public void onPreviewFrame(byte[] data, Camera camera) {     
            //取得指定范围的帧的数据      
            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(data, width, height, dstLeft, dstTop, dstWidth, dstHeight,false);      
            //取得灰度图      
            Bitmap mBitmap = source.renderCroppedGreyscaleBitmap();      
            //显示灰度图      
            imgView.setImageBitmap(mBitmap);      
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));      
            MultiFormatReader reader = new MultiFormatReader();      
            try {      
                saveImage(mBitmap);  
                Hashtable hints = new Hashtable();    
                hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");    
                Result result = reader.decode(bitmap,hints);      
                String strResult = "BarcodeFormat:"      
                        + result.getBarcodeFormat().toString() + "  text:"      
                        + result.getText();      
                txtScanResult.setText(strResult);      
            } catch (Exception e) {      
                txtScanResult.setText("Scanning");     
                sfhCamera.AutoFocusAndPreviewCallback();  
            }      
        }      
    };     
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){    
            sfhCamera.closeCamera();  
        }  
        return super.onKeyDown(keyCode, event);  
    }  
    @Override  
    protected void onResume() {  
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {  
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);  
        }  
        super.onResume();  
    }  
      
    private void saveImage(Bitmap mBitmap){  
        File file=new File("/sdcard/feng.png");   
        try {   
            FileOutputStream out=new FileOutputStream(file);   
            if(mBitmap.compress(Bitmap.CompressFormat.PNG, 50, out)){   
                out.flush();   
                out.close();   
            }   
        } catch (FileNotFoundException e) {   
            e.printStackTrace();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
    }
}
