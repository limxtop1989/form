package com.opensource.limxtop.validation;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by limiaoxin on 1/5/16.
 * Email limxtop@gmail.com
 * Description: Accept license number only for input.
 */
public class LicensePlateNumberInputFilter implements InputFilter {

    private static final List<String> LICENSE_PLATE_ABBREVIATION = Arrays.asList("京", "津", "渝",
            "沪", "冀", "晋", "辽", "吉", "黑", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘",
            "粤", "琼", "川", "贵", "云", "陕", "秦", "甘", "陇", "青", "台", "蒙", "桂", "宁", "新",
            "藏", "澳", "军", "海", "航", "警");

    private static final int LICENSE_PLATE_NUMBER_LENGTH = 7;

    private static LicensePlateNumberInputFilter instance = new LicensePlateNumberInputFilter();

    private LicensePlateNumberInputFilter() {

    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//        Saint: source=闽, start=0, end=1, dest=, dstart=0, dend=0
//        Saint: source=A, start=0, end=1, dest=闽, dstart=1, dend=1
//        Saint: source=6, start=0, end=1, dest=闽A, dstart=2, dend=2
//        Saint: source=4, start=0, end=1, dest=闽A6, dstart=3, dend=3
//        Saint: source=9, start=0, end=1, dest=闽A64, dstart=4, dend=4
//        Saint: source=7, start=0, end=1, dest=闽A649, dstart=5, dend=5
//        Saint: source=J, start=0, end=1, dest=闽A6497, dstart=6, dend=6
//        Log.d("Saint", "source=" + source + ", start=" + start + ", end=" + end + ", dest=" + dest + ", dstart=" + dstart + ", dend=" + dend);
        /**
         * replace the range <code>dstart &hellip; dend</code> of <code>dest</code> with the new text from the range
         * <code>start &hellip; end</code> of <code>source</code> to be result is correct.
         */
        StringBuilder replaceResult = new StringBuilder(7);
        replaceResult.append(dest.subSequence(0, dstart));
        replaceResult.append(source.subSequence(start, end));
        replaceResult.append(dest.subSequence(dend, dest.length()));

        Log.d("Saint", "replaceResult=" + replaceResult.toString());

        boolean isAccepted = true;
        int length = replaceResult.length();

        if (length >= 1) {
            if (!isLicensePlateAbbreviation(replaceResult.charAt(0))) {
                isAccepted = false;
            }
        }

        if (length >= 2 && isAccepted) {
            if (!isCapital(replaceResult.charAt(1))) {
                isAccepted = false;
            }
        }

        if (length > LICENSE_PLATE_NUMBER_LENGTH) {
            isAccepted = false;
        }

        if (length > 2 && isAccepted) {
            for (int i = 2; i < length; i++) {
                if (!isCapital(replaceResult.charAt(i)) && !isDigital(replaceResult.charAt(i))) {
                    isAccepted = false;
                }
            }
        }

        if (isAccepted) {
            return null;
        } else {
            return "";
        }
    }

    public static LicensePlateNumberInputFilter getInstance() {
        return instance;
    }

    private boolean isLicensePlateAbbreviation(char text) {
        return LICENSE_PLATE_ABBREVIATION.contains(String.valueOf(text));
    }

    private boolean isCapital(char text) {
        return Character.isUpperCase(text);
    }

    private boolean isDigital(char text) {
        return Character.isDigit(text);
    }

}
