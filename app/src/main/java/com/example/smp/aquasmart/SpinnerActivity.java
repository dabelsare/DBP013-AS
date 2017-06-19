package com.example.smp.aquasmart;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by Manjiri on 5/27/2017.
 */
public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
/*        Spinner spinner = (Spinner) findViewById(R.id.jar_type_spinner);
        spinner.setOnItemSelectedListener(this);*/
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}