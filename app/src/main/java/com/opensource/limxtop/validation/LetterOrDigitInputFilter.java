package com.opensource.limxtop.validation;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by limiaoxin on 12/21/15.
 * Email limxtop@gmail.com
 * Description: Accept letter or digit only for input.
 */
public class LetterOrDigitInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        for (int i = start; i < end; i++) {
            if (!Character.isLetterOrDigit(source.charAt(i))) {
                return "";
            }
        }
        return null;// keep original
    }

}
