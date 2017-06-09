package com.opensource.limxtop.validation;

import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.SparseArray;

/**
 * Created by limiaoxin on 12/21/15.
 * Email limxtop@gmail.com
 * Description:
 * an input filter which accept formatted decimal only
 */

public class DecimalFormatInputFilter extends DigitsKeyListener {

    private static SparseArray<DecimalFormatInputFilter> instances = new SparseArray<>(4);
    private static final int DECIMAL_SHIFT = 4;// 4 bit for decimal is enough to represent 15 bit decimal.

    private int integer;
    private int decimal;

    private static final String DECIMAL_POINT = ".";

    public DecimalFormatInputFilter(int integer, int decimal) {
        this.integer = integer;
        this.decimal = decimal;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        String original = dest.toString();
        String result = dest.toString() + source.toString();
        if (result.equals(DECIMAL_POINT)) {
            return "0.";
        } else if (result.toString().indexOf(DECIMAL_POINT) == -1) {
            // no decimal point placed yet
            if (result.length() > integer) {
                return "";
            }
        } else {
            int pointPosition;
            if (original.indexOf(DECIMAL_POINT) == -1) {
                pointPosition = result.indexOf(DECIMAL_POINT);
            } else {
                pointPosition = original.indexOf(DECIMAL_POINT);
            }
            if (dstart <= pointPosition) {
                // cursor is at left of decimal point.
                String beforeDot = original.substring(0, pointPosition);
                if (beforeDot.length() < integer) {
                    return source;
                } else {
                    if (source.toString().equalsIgnoreCase(DECIMAL_POINT)) {
                        return source;
                    } else {
                        return "";
                    }

                }
            } else {
                // cursor is at right of decimal point.
                result = result.substring(result.indexOf(DECIMAL_POINT) + 1);
                if (result.length() > decimal) {
                    return "";
                }
            }
        }

        return super.filter(source, start, end, dest, dstart, dend);
    }

    /**
     * @param integer  the number of integer
     * @param decimal the number of decimal
     * @return
     */
    public static DecimalFormatInputFilter getInstance(int integer, int decimal) {
        int key = integer << DECIMAL_SHIFT | decimal;
        DecimalFormatInputFilter instance = instances.get(key);
        if (null == instance) {
            instance = new DecimalFormatInputFilter(integer, decimal);
            instances.put(key, instance);
        }

        return instance;
    }

}
