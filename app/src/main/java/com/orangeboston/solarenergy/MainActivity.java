package com.orangeboston.solarenergy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //感应器管理器
    private SensorManager sensorManager;
    //光线亮度
    private TextView light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        light = new TextView(this);
//        setContentView(light);

        //获得感应器服务
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //获得光线感应器
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        //注册监听器
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            showDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        //创建dialog构造器
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        //设置title
        normalDialog.setTitle(getString(R.string.action_about));
        //设置icon
        normalDialog.setIcon(R.drawable.ic_launcher_round);
        //设置内容
        normalDialog.setMessage(getString(R.string.action_about_detail));
        //设置按钮
        normalDialog.setPositiveButton(getString(R.string.done)
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        //创建并显示
        normalDialog.create().show();
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
            light = findViewById(R.id.tv_light);
//            light.setText("当前亮度 " + value + " lx(勒克斯)");
            if (value >= 20000) {
                Intent intent = new Intent(MainActivity.this, ElectricDischargeActivity.class);
                startActivity(intent);
            } else {
                light.setText("请将设备正面朝上\n\n放置在阳光下进行放电");
            }

        }

    };

}
