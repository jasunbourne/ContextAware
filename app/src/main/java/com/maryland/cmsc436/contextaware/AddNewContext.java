package com.maryland.cmsc436.contextaware;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.maryland.cmsc436.contextaware.ContextSettings.ActiveStatus;
import com.maryland.cmsc436.contextaware.ContextSettings.Ringer;


public class AddNewContext extends Activity {

    private static final String TAG = "ContextAware";

    private RadioGroup ringerRadioGroup;
    private RadioGroup activeRadioGroup;
    private EditText TitleText;
    private RadioButton DefaultActiveButton;
    private RadioButton DefaultRingerButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_context);

        TitleText = (EditText) findViewById(R.id.title);
        DefaultActiveButton = (RadioButton) findViewById(R.id.yes);
        DefaultRingerButton = (RadioButton) findViewById(R.id.vibrate);
        ringerRadioGroup = (RadioGroup) findViewById(R.id.ringerGroup);
        activeRadioGroup = (RadioGroup) findViewById(R.id.statusGroup);


        // OnClickListener for the Cancel Button,
        final Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Entered cancelButton.OnClickListener.onClick()");
                finish();
            }
        });

        // OnClickListener for the Reset Button
        final Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Entered resetButton.OnClickListener.onClick()");
                ringerRadioGroup.check(R.id.vibrate); // selects the ringer to the default "Vibrate"
                activeRadioGroup.check(R.id.yes); // selects the active status to the default "Yes"
                TitleText.setText(""); // clears the "Title" field to the default empty string
            }
        });

        // OnClickListener for the Submit Button
        final Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Entered submitButton.OnClickListener.onClick()");

                // Save the active/inactive status recorded by the user
                ActiveStatus currentStatus = getStatus();

                // Save the title string recorded by the user
                String currentTitle = TitleText.getText().toString();

                // Save the ringer setting recorded by the user
                Ringer currentRinger = getRinger();

                // package all of the info we just recorded into an intent
                Intent recordedData = new Intent();
                ContextSettings.packageIntent(recordedData,currentTitle,currentRinger,currentStatus);

                // setResult sets the resultCode to be RESULT_OK, which is what we want
                setResult(RESULT_OK,recordedData);

                // finish the activity since the user hit the submit button
                finish();
            }
        });
    }

    // This method will get the ringer status that is currently set for this context
    private Ringer getRinger() {
        switch (ringerRadioGroup.getCheckedRadioButtonId()) {
            case R.id.silent: {
                return Ringer.SILENT;
            }
            case R.id.loud: {
                return Ringer.LOUD;
            }
            // The default case is the ringer is set to vibrate
            default: {
                return Ringer.VIBRATE;
            }
        }
    }

    // This method will get the current status set by the user (either active or inactive)
    private ActiveStatus getStatus() {
        switch (activeRadioGroup.getCheckedRadioButtonId()) {
            case R.id.no: {
                return ActiveStatus.NO;
            }
            // The default case is yes (AKA activated)
            default: {
                return ActiveStatus.YES;
            }
        }
    }
}
