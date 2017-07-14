package marvell.android.lollipop;


import marvell.android.testsuit.TConstant;
import marvell.android.util.Configure;
import marvell.android.util.LogUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ConfigureActivity extends Activity implements OnClickListener {

	Button saveButton;
	Button cancleButton;
	Context context;
	EditText sim1NumberEditView;
	EditText sim2NumberEditView;
	CheckBox enableAutoAnswerCheckBox;
	CheckBox enableAutoResetCheckBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configure);

		Intent intent = this.getIntent();
		context = this.getApplicationContext();

		saveButton = (Button) findViewById(R.id.saveConfigurationButton);
		saveButton.setOnClickListener(this);
		cancleButton = (Button) findViewById(R.id.cancelConfigurationButton);
		cancleButton.setOnClickListener(this);

		sim1NumberEditView = (EditText) findViewById(R.id.sim1NumberEditText);
		sim1NumberEditView.setText(intent.getStringExtra("sim1Number"));
		sim1NumberEditView.setOnClickListener(this);

		sim2NumberEditView = (EditText) findViewById(R.id.sim2NumberEditText);
		sim2NumberEditView.setText(intent.getStringExtra("sim2Number"));
		sim2NumberEditView.setOnClickListener(this);

		enableAutoAnswerCheckBox = (CheckBox) findViewById(R.id.enableAutoAnswerCheckBox);
		enableAutoAnswerCheckBox.setOnClickListener(this);
		enableAutoAnswerCheckBox.setChecked(intent.getBooleanExtra(
				"enableAutoAnswer", false));

		enableAutoResetCheckBox = (CheckBox) findViewById(R.id.enableAutoResetCheckBox);
		enableAutoResetCheckBox.setOnClickListener(this);
		enableAutoResetCheckBox.setChecked(intent.getBooleanExtra(
				"enableAutoReset", false));

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.saveConfigurationButton:

			LogUtil.d("saveConfigurationButton");

			Configure.saveConfiguration(context, enableAutoAnswerCheckBox
					.isChecked(), sim1NumberEditView.getText().toString(),
					sim2NumberEditView.getText().toString(),
					enableAutoResetCheckBox.isChecked());

			nextActivity(TConstant.mainActivity);

			break;
		case R.id.cancelConfigurationButton:

			LogUtil.d("cancelConfigurationButton");

			nextActivity(TConstant.mainActivity);
			break;
		case R.id.enableAutoAnswerCheckBox:
			LogUtil.d("enableAutoAnswerCheckBox");
			if (enableAutoAnswerCheckBox.isChecked()) {
				LogUtil.d("selected");
			} else {

				LogUtil.d("not selected");

			}

			break;

		case R.id.sim1NumberEditText:
			LogUtil.d("sim1NumberEditText");
			sim1NumberEditView.setText("");
			break;
		case R.id.sim2NumberEditText:
			LogUtil.d("sim2NumberEditText");
			sim2NumberEditView.setText("");
			break;

		}

	}

	private void nextActivity(int nextActivityNumber) {

		Intent intent = new Intent();

		switch (nextActivityNumber) {

		case TConstant.mainActivity:

			intent.setClass(this, MainActivity.class);

			break;

		}

		startActivity(intent);

	}

	@Override
	public void onBackPressed() {

		LogUtil.d("onBackPressed");

	}

}
