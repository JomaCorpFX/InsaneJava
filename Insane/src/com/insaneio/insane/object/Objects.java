package com.insaneio.insane.object;

/**
 * Created by Joma Espinoza Bone on 22/07/2016.
 */
public class Objects {

    public static <T> T coalesce(T value, T replaceValue)
    {
        return value == null ? replaceValue : value;
    }
    
}
