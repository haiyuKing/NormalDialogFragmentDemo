package com.why.project.normaldialogfragmentdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.why.project.normaldialogfragmentdemo.dialog.PreviewDialog;

public class MainActivity extends AppCompatActivity {

	private Button btn_open_full;
	private Button btn_open;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
		initEvents();
	}

	private void initViews() {
		btn_open_full = findViewById(R.id.btn_open_full);
		btn_open = findViewById(R.id.btn_open);
	}

	private void initEvents() {
		btn_open_full.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//打开预览界面
				Bundle bundle = new Bundle();
				PreviewDialog previewDialog = PreviewDialog.getInstance(MainActivity.this,bundle);
				previewDialog.show(getSupportFragmentManager(),PreviewDialog.TAG_FULLSCREEN);
			}
		});

		btn_open.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//打开预览界面
				Bundle bundle = new Bundle();
				PreviewDialog previewDialog = PreviewDialog.getInstance(MainActivity.this,bundle);
				previewDialog.show(getSupportFragmentManager(),PreviewDialog.TAG_BELOWSTATUEBAR);
			}
		});
	}
}
