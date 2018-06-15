package com.example.tang5.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author：thf on 2018/6/14 0014 17:13
 * <p>
 * 邮箱：tang5011235@163.com
 * <p>
 * name:OwnPickerView
 * <p>
 * version:
 * @description:
 */
public class PeriodTimeWheelView extends DialogFragment {

	private View mRootView;


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MyDialog);
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

		mRootView = LayoutInflater.from(getContext()).inflate(R.layout.view_four, (ViewGroup) (getActivity().findViewById(android.R.id.content)), false);
		FourParametersTimeWheelView wheelView = new FourParametersTimeWheelView(mRootView);
		wheelView.setDate();

		Dialog dialog = new Dialog(getActivity(),R.style.MyDialog);

		Window window = dialog.getWindow();
		WindowManager.LayoutParams wlp = window.getAttributes();
		wlp.gravity = Gravity.BOTTOM;
		window.setAttributes(wlp);
		dialog.getWindow()
				.setGravity(Gravity.BOTTOM);
		View decorView = dialog.getWindow().getDecorView();
		decorView.setPadding(0,0,0,0);


		dialog.setContentView(mRootView);


		return dialog;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
