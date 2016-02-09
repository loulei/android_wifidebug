package com.example.wifidebug;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wifidebug.ShellUtil.CommandResult;

/*stop adbd
 setprop service.adb.tcp.port 5555
 start adbd*/

public class MainActivity extends Activity {

	private Button btn_start;
	private TextView tv_result;

	private String[] cmds = new String[] { "stop adbd", "setprop service.adb.tcp.port 5555", "start adbd" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_start = (Button) findViewById(R.id.btn_start);
		tv_result = (TextView) findViewById(R.id.tv_result);

		btn_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommandResult commandResult = ShellUtil.execCommand(cmds, true, true);
				tv_result.setText("adb connect " + getIp() + ":5555");
				// System.out.println(commandResult.responseMsg);
			}
		});
	}

	private String getIp() {
		WifiManager wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifiMan.getConnectionInfo();
		String mac = info.getMacAddress();// 获得本机的MAC地址
		String ssid = info.getSSID();// 获得本机所链接的WIFI名称

		int ipAddress = info.getIpAddress();
		String ipString = "";// 本机在WIFI状态下路由分配给的IP地址

		// 获得IP地址的方法一：
		if (ipAddress != 0) {
			ipString = ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "." + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
		}
		return ipString;
	}
}
