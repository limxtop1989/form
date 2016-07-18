package com.opensource.limxtop.util;


/**
 * Created by Saint on 1/11/16.
 * Email limiaoxin@souche.com
 * Description:
 * define the map information about the field in Value Object from which the value fetch to View to where the value set.
 * every module define its own implementation to avoid naming conflict.
 */
public interface IViewToField {

    /**
     *
     * @return the id of View which has the value you want to get or set into field in Value Object.
     */
    int getResID();

    /**
     *
     * @return the field name in Value Object which you want to set or get value coming from the View
     */
    String getFieldName();

    /**
     * define the edit type such as TAG or TEXT, which is mean the value fetch from View is get from the tag or text field respectively.
     * @return EditType
     */
    EditType getEditType();
}
