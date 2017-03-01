package com.example.leo.uninstallanimation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.readystatesoftware.systembartint.SystemBarTintManager;

public class MainActivity extends AppCompatActivity {

    private View measure_view;
    private int width;
    private int height;
    private String TAG = "times";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        measure_view = findViewById(R.id.measure_view);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);

        tintManager.setTintColor(Color.RED);
    }

    //解决在oncreate onstart onresume中无法获得view的宽高方法1
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            width = measure_view.getMeasuredWidth();
            height = measure_view.getMeasuredHeight();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        //方法2 post
        measure_view.post(new Runnable() {
            @Override
            public void run() {
                width = measure_view.getMeasuredWidth();
                height = measure_view.getMeasuredHeight();
            }
        });


        //方法3 ViewTreeObserver
        ViewTreeObserver observer = measure_view.getViewTreeObserver();
        //当view树当状态发生改变或者view树内部的可见性发生改变时 OnGlobalLayoutListener将被毁回调
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //移除监听
                measure_view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = measure_view.getMeasuredWidth();
                int height = measure_view.getMeasuredHeight();
            }
        });
    }


    public void drawShape() {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setStrokeWidth(2f);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        Log.i(TAG, "width: " + width + " height: " + height);
        canvas.drawLine(0, 0, width, height, paint);
    }


}
