package com.kokoroguruma.testservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MyService extends Service {

	MediaPlayer mediaPlayer;
	String musicUrl;

	public MyService() {
	}

	@Override
	public void onCreate() {
		Log.d("MyService: ", "onCreate(): ");
		super.onCreate();
	}



	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("MyService: ", "onStartCommand(): ");

		// startServiceの開始

		// ネットラジオ（終わらない～）
		musicUrl = "http://listen.kibo.fm:8000/kibofm";

		this.mediaPlayer = new MediaPlayer();
		try {
			this.mediaPlayer.setDataSource(this.musicUrl);
			this.mediaPlayer.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}




		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.d("MyService: ", "onDestroy(): ");

		this.mediaPlayer.stop();
		this.mediaPlayer = null;

		super.onDestroy();
	}

	// Binder で、自身の複製を送る。
	public class ServiceBinder extends Binder {
		public MyService getService() {
			return MyService.this;
		}
	}
	// Binderの生成
	private IBinder iBinder = new ServiceBinder();

	// 必須、オーバーライドメソッド。ここに一度アクセスしてくる。
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("MyService: ", "onBind(): ");
		// iBinderに含まれる getService() を使うことで、このクラス内のPublicメソッドにアクセス出来る。
		return iBinder;
	}





	public void playMediaPlayer() {
		Log.d("MyService: ", "playMediaPlayer(): ");

		this.mediaPlayer.start();

	}


	public void stopMediaPlayer() {
		Log.d("MyService: ", "stopMediaPlayer(): ");
		this.mediaPlayer.pause(); // ここではネットラジオなので一時停止。
	}



}
