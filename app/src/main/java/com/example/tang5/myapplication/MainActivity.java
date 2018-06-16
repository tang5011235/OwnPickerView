package com.example.tang5.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
		final PeriodTimeWheelView wheelView = new PeriodTimeWheelView();

		mTvDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				wheelView.setDateListener(new FourParametersTimeWheelView.DateListener() {
					@Override
					public void onDateSelected(int year, int month, int day, String period) {

					}
				});
				wheelView.show(getSupportFragmentManager(),"1");

			}
		});

	}

}
