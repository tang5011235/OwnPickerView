package com.example.tang5.myapplication;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.fl_container)
	FrameLayout mFlContainer;
	@BindView(R.id.tv_date)
	TextView mTvDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);


		View inflate = LayoutInflater.from(this).inflate(R.layout.view_four, mFlContainer, false);
		FourParametersTimeWheelView wheelView = new FourParametersTimeWheelView(inflate);
		wheelView.setDateListener(new FourParametersTimeWheelView.DateListener() {
			@Override
			public void onDateSelected(int year, int month, int day, String period) {
				mTvDate.setText(new StringBuilder()
						.append(year)
						.append("-")
						.append(month)
						.append("-")
						.append(day)
						.append(" ")
						.append(period)
						.toString()
				);
			}
		});
		wheelView.setDate();


		AlertDialog dialog = new AlertDialog.Builder(this,R.style.MyDialog)
				.setView(inflate)
				.create();
		dialog.getWindow()
				.setGravity(Gravity.BOTTOM);
		View decorView = dialog.getWindow().getDecorView();
		decorView.setPadding(0,0,0,0);
		ViewGroup.LayoutParams layoutParams = dialog.getWindow().getAttributes();
		layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
		layoutParams.height= ViewGroup.LayoutParams.WRAP_CONTENT;
		dialog.show();

	}

}
