package com.opensource.limxtop.validation;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by limiaoxin on 12/21/15.
 * Email limxtop@gmail.com
 * Description: Accept letter including lowercase and uppercase only for input.
 */
public class LetterInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        for (int i = start; i < end; i++) {
            if (!Character.isLetter(source.charAt(i))) {
                return "";
            }
        }
        return null;// keep original
    }

}
