package com.kokoroguruma.testservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	Intent serviceIntent;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		// 通常のStartServiceで一度開始。向こうで音声準備もする。
		serviceIntent = new Intent(this, MyService.class);
		startService(serviceIntent);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();


	}

	public void startButton(View view) {

		// コネクション（と、その機能）の準備
		ServiceConnection serviceConnection = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {

				// サービスのオブジェクトを引っ張ってきて、直接メソッドを操作
				MyService musicPlayService = ((MyService.ServiceBinder) service).getService();
				musicPlayService.playMediaPlayer(); // 音声再生。
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {

			}
		};

		// バインドで操作
		bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

	}


	public void stopButton(View view) {

		// コネクション（と、その機能）の準備
		ServiceConnection serviceConnection = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {

				// サービスのオブジェクトを引っ張ってきて、直接メソッドを操作
				MyService musicPlayService = ((MyService.ServiceBinder) service).getService();
				musicPlayService.stopMediaPlayer(); // 音声停止。
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {

			}
		};

		// バインドで操作
		bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

	}



}
