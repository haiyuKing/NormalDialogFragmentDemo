package com.why.project.normaldialogfragmentdemo.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.why.project.normaldialogfragmentdemo.R;

/**
 * Created by HaiyuKing
 * Used
 */

public class PreviewDialog extends BaseDialogFragment{
	private static final String TAG = PreviewDialog.class.getSimpleName();

	public static final String TAG_FULLSCREEN = "fullScreen";//全屏
	public static final String TAG_BELOWSTATUEBAR = "belowStatusBar";//状态栏下方

	/**View实例*/
	private View myView;
	/**context实例*/
	private Context mContext;
	/**标记：用来代表是从哪个界面打开的这个对话框*/
	private String mTag;

	private WebView mWebView;

	public static PreviewDialog getInstance(Context mContext, Bundle bundle)
	{
		PreviewDialog previewDialog = new PreviewDialog();
		previewDialog.mContext = mContext;
		return previewDialog;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_NoTitleBar_Fullscreen);//全屏（在状态栏底下）
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));//设置背景为透明，并且没有标题
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		//设置窗体全屏
		getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		myView = inflater.inflate(R.layout.dialog_preview, container, false);
		/*this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener()
		{
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event){
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					LogUtil.w(TAG, "onKey");
					dismiss();
					return true; // return true是中断事件，那么下面的就接受不到按键信息了
				}else
				{
					return false; //在return false的时候 才会事件继续向下传递。
				}
			}
		});*/
		return myView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//初始化控件以及设置
		initView();
		//初始化数据
		initDatas();
		initEvents();
	}

	/**
	 * 设置宽度和高度值，以及打开的动画效果
	 */
	@Override
	public void onStart() {
		super.onStart();

		if(mTag.equals(TAG_FULLSCREEN)){//全屏显示
			//设置对话框的宽高，必须在onStart中
			Window window = this.getDialog().getWindow();
			window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);//全屏（盖住状态栏）
			window.setGravity(Gravity.BOTTOM);//设置在底部
			//打开的动画效果--从底部向上
			window.setWindowAnimations(R.style.bottomsheetdialog_animation);
		}else{
			//从我的场景列表界面中设置按钮打开的
			//设置对话框的宽高，必须在onStart中
			DisplayMetrics metrics = new DisplayMetrics();
			this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
			Window window = this.getDialog().getWindow();
			if(getStatusBarHeight(mContext) <= 96){
				window.setLayout(metrics.widthPixels, metrics.heightPixels - getStatusBarHeight(mContext));
			}else{
				window.setLayout(metrics.widthPixels, this.getDialog().getWindow().getAttributes().height);//适配红米6pro
			}
			window.setGravity(Gravity.BOTTOM);//设置在底部
			//打开的动画效果--从底部向上
			window.setWindowAnimations(R.style.bottomsheetdialog_animation);
		}
	}

	/**获取状态栏的高度*/
	private int getStatusBarHeight(Context context) {
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}
	@Override
	public void onDestroy()
	{
		//销毁webview控件
		mWebView.removeAllViews();
		mWebView.destroy();
		super.onDestroy();
	}

	/**实例化控件*/
	private void initView() {
		mWebView = myView.findViewById(R.id.web_view);
		mWebView.setWebViewClient(new WebViewClient() {
			/**
			 * 重写此方法表明点击网页内的链接由自己处理，而不是新开Android的系统browser中响应该链接。
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView webView, String url) {
				//webView.loadUrl(url);
				return false;
			}
		});
	}
	/**
	 * 初始化数据：tag标记、标题
	 */
	private void initDatas() {
		mTag = this.getTag();

		mWebView.loadUrl("http://www.baidu.com");
	}

	/**
	 * 初始化监听事件
	 */
	private void initEvents() {

	}

	@Override
	public void dismiss() {
		super.dismiss();
	}


}
