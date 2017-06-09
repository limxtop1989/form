package com.opensource.limxtop.util;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.opensource.limxtop.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Saint on 1/7/16.
 * Email limiaoxin@souche.com
 * Description:
 */
public class FormUtils {

    public static void updateViewFromVO(View root, Object VO, IViewToField[] viewToFields) throws NoSuchFieldException, IllegalAccessException {
        ViewGroup labelField;
        EditText field;
        Object value;
        if (null == VO) {
            return;
        }
        for (IViewToField viewToFieldAdapter : viewToFields) {
            labelField = (ViewGroup) root.findViewById(viewToFieldAdapter.getResID());
            field = (EditText) labelField.findViewById(R.id.field);
            value = getFieldValue(VO, getDeclaredField(VO, viewToFieldAdapter.getFieldName()));
            if (null == value) {
                continue;
            }
            if (EditType.TEXT.equals(viewToFieldAdapter.getEditType())) {
                field.setText(value.toString());
            } else {
                field.setTag(value.toString());
            }
        }
    }
    /**
     * get value from views in form and fill to VO's fields respectively
     * @param root
     * @param VO
     * @param viewToFields
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static void fillVOFromView(View root, Object VO, IViewToField[] viewToFields) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        ViewGroup labelField;
        String fieldValue = null;
        for (IViewToField viewToFieldAdapter : viewToFields) {
            labelField = (ViewGroup) root.findViewById(viewToFieldAdapter.getResID());
            if (viewToFieldAdapter.getEditType().equals(EditType.TEXT)) {
                fieldValue = ((EditText) labelField.findViewById(R.id.field)).getText().toString();
            } else if (viewToFieldAdapter.getEditType().equals(EditType.TAG)) {
                Object object = labelField.findViewById(R.id.field).getTag();
                fieldValue = (null == object ? null : object.toString());
            }
            if (TextUtils.isEmpty(fieldValue)) {
                fieldValue = null;
            }
//            if (fieldValue != null && !TextUtils.isEmpty(fieldValue.toString())) {
                FormUtils.setFieldValue(VO, viewToFieldAdapter.getFieldName(), fieldValue);
//            }
        }
    }

    public static void setFieldValue(Object object, String fieldName, String newValue) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Field field = getDeclaredField(object, fieldName);
        /**
         * if we declare a field by "private Integer id;" then the value of fieldType is Integer.class
         */
        Class fieldType = field.getType();
        Object value = null;
//        if (clazz == Integer.class) {
//            value = Integer.valueOf(newValue);
//        } else if (clazz == Long.class) {
//            value = Long.valueOf(newValue);
//        } else if (clazz == Float.class) {
//            value = Float.valueOf(newValue);
//        } else if (clazz == Double.class) {
//            value = Double.valueOf(newValue);
//        }
        // use reflection to initial object is perfect rather than the judgement codes above.
        if (!TextUtils.isEmpty(newValue)) {
            /**
             * the class type of newValue is String always, so we pass String.class as second parameter.
             * the value pass to setFieldValue must be a instance of fieldType exactly.
             */
            value = newInstance(fieldType, new Class[] { String.class }, new Object[] { newValue });
        }

        //  what if newValue is null which means that the user clear the input ?
        setFieldValue(object, field, value);
    }

    private static <T> T newInstance(Class<T> clazz, Class<?>[] paramClazzes, Object[] params) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return clazz.getConstructor(paramClazzes).newInstance(params);
    }

    private static Field getDeclaredField(Object object, String fieldName) throws NoSuchFieldException {
        Field field;
        Class<?> clazz = object.getClass();
        do {
            field = clazz.getDeclaredField(fieldName);
        } while (null == field &  null != (clazz = clazz.getSuperclass()));

        if (null == field) {
            throw new NoSuchFieldException();
        }

        return field;
    }

    private static void setFieldValue(Object object, Field field, Object newValue) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(object, newValue);
    }

    private static Object getFieldValue(Object object, Field field) throws  IllegalAccessException {
        field.setAccessible(true);
        return field.get(object);
    }
}
