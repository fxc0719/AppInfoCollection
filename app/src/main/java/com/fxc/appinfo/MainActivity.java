package com.fxc.appinfo;

import android.app.ActivityManager;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

	TextView textView;
	ActivityManager manager;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			textView.setText((String) msg.obj);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.tv);
		new Thread(new Runnable() {
			@Override
			public void run() {
				AppInfoUtils utils = AppInfoUtils.newInstance(MainActivity.this);
				StringBuffer sb = new StringBuffer();
				while (true) {
					sb.append(utils.getTopAppPkgName());
					sb.append("\n");
					sb.append(utils.getTopActivityName());
					sb.append("\n");
					sb.append(utils.getForegroundPid());
					sb.append("\n");
					sb.append(utils.getTopAppMem());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Message message = new Message();
					message.obj = sb.toString();
					mHandler.sendMessage(message);
					Log.d("asd", sb.toString());
					sb.delete(0, sb.length());
				}
			}
		}).start();
	}
}
