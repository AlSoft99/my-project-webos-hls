package com.cn;

import com.util.ProgressActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MycadillacActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btn1 = (Button)findViewById(R.id.login);
        btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent1 = new Intent(MycadillacActivity.this,ProgressActivity.class);
				MycadillacActivity.this.startActivityForResult(intent1, 10);
				new Thread(new MycadillacActivity.Test()).start();
			}
		});
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//String name = data.getExtras().get("name").toString();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	class Test implements Runnable{
		public void run() {
			try {
				Thread.sleep(2000);
				MycadillacActivity.this.finishActivity(10);
				Intent i = new Intent(MycadillacActivity.this,IndexActivity.class);
				MycadillacActivity.this.startActivity(i);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}