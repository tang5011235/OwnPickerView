package com.example.tang5.myapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		 mRootView = LayoutInflater.from(getContext()).inflate(R.layout.view_four, (ViewGroup) (getActivity().findViewById(android.R.id.content)), false);
		builder.setView(mRootView);
		return builder.create();
	}
}
