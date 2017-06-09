package com.opensource.limxtop.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DialerKeyListener;
import android.widget.EditText;

import com.opensource.limxtop.R;
import com.opensource.limxtop.validation.DecimalFormatInputFilter;
import com.opensource.limxtop.validation.LetterInputFilter;
import com.opensource.limxtop.validation.LetterOrDigitInputFilter;
import com.opensource.limxtop.validation.LicensePlateNumberInputFilter;
import com.opensource.limxtop.validation.LowercaseInputFilter;
import com.opensource.limxtop.validation.UppercaseInputFilter;

/**
 * Created by limxtop on 6/8/17.
 */

public class ValidateDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
    }

    @Override
    protected void onResume() {
        super.onResume();

        EditText idText = (EditText) findViewById(R.id.id_field);
        idText.setFilters(new InputFilter[] { new LetterOrDigitInputFilter(),
                new InputFilter.AllCaps(), new InputFilter.LengthFilter(8) });

        EditText phoneEdit = (EditText) findViewById(R.id.phone_field);
        phoneEdit.setKeyListener(DialerKeyListener.getInstance());

        EditText licenseEdit = (EditText) findViewById(R.id.license_field);
        licenseEdit.setFilters(new InputFilter[] { LicensePlateNumberInputFilter.getInstance()});

        EditText letter = (EditText) findViewById(R.id.letter_field);
        letter.setFilters(new InputFilter[] { new LetterInputFilter() });

        EditText upperText = (EditText) findViewById(R.id.uppercase_field);
        upperText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        upperText.setFilters(new InputFilter[] { new UppercaseInputFilter() });

        EditText lowerText = (EditText) findViewById(R.id.lowercase_field);
        lowerText.setInputType(InputType.TYPE_CLASS_TEXT);
        lowerText.setFilters(new InputFilter[] { new LowercaseInputFilter() });

        EditText lenghEdit = (EditText) findViewById(R.id.length_field);
        lenghEdit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(5) });

        EditText num2dot2Text = (EditText) findViewById(R.id.num2i2d_field);
        num2dot2Text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        num2dot2Text.setFilters(new InputFilter[] {DecimalFormatInputFilter.getInstance(2, 2) });

        EditText num4dot2Text = (EditText) findViewById(R.id.num4i2d_field);
        num4dot2Text.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        num4dot2Text.setFilters(new InputFilter[] { DecimalFormatInputFilter.getInstance(4, 2) });
    }




}
