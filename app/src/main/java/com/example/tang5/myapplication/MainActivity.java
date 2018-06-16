package com.example.tang5.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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
						Toast.makeText(getBaseContext(), new StringBuilder()
								.append(year)
								.append("-")
								.append(month)
								.append("-")
								.append(day)
								.append(" ")
								.append(period)
								.toString(), Toast.LENGTH_SHORT).show();
					}
				});

				wheelView.setSureListener(new FourParametersTimeWheelView.SureListener() {
					@Override
					public void onSure() {
						Toast.makeText(getBaseContext(), "确认", Toast.LENGTH_SHORT).show();
					}
				});

				wheelView.setCancelListener(new FourParametersTimeWheelView.CancelListener() {
					@Override
					public void onCancel() {
						Toast.makeText(getBaseContext(), "取消", Toast.LENGTH_SHORT).show();
					}
				});
				wheelView.show(getSupportFragmentManager(),"1");
			}
		});

	}

}
