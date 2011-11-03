package com.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

public class Util {
	public static Dialog dialogOneBtn(Context context,String title,String content){  
		AlertDialog.Builder bul = new AlertDialog.Builder(context);  
        bul.setTitle(title);  
        bul.setMessage(content);  
        bul.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                
            }  
        });  
        return bul.create();  
    }
}