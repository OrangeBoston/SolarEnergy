package com.orangeboston.solarenergy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.orangeboston.solarenergy.utils.VivoView;

public class ElectricDischargeActivity extends AppCompatActivity {

    private VivoView vivoView;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_discharge);
        init();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void init() {
        vivoView = (VivoView) findViewById(R.id.vivo);
        vivoView.startAnimation();
        setLight(this, 255);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    @Override
    public void onDetachedFromWindow() {
        vivoView.stopAnimation();
        super.onDetachedFromWindow();
    }

    //Activity被销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销监听器
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }

    //感应器事件监听器
    private SensorEventListener listener = new SensorEventListener() {

        //当感应器精度发生变化
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        //当传感器监测到的数值发生变化时
        @Override
        public void onSensorChanged(SensorEvent event) {
            // values数组中第一个值就是当前的光照强度
            float value = event.values[0];

            if (value <= 20000) {
                finish();
            } else {
                Toast.makeText(ElectricDischargeActivity.this,"快速放电服务已启动⚡",Toast.LENGTH_SHORT).show();
            }

        }

    };

    private void setLight(Activity context, int brightness) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        Log.d("light", lp.toString());
        context.getWindow().setAttributes(lp);
    }
}
