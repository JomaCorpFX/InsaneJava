package com.insaneio.insane.object;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Joma Espinoza Bone on 18/08/2016.
 */
public class Arrays
{

    public static <T> T[] concat(T[]... arrays)
    {
        ArrayList<T> ret = new ArrayList<>();
        for (T[] value : arrays)
        {
            Collections.addAll(ret, value);
        }
        return ret.toArray(arrays[0].clone());
    }

    public static Byte[] toByteArray(byte[] sourceArray)
    {
        Byte[] arr = new Byte[sourceArray.length];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = sourceArray[i];
        }
        return arr;
    }

    public static Byte[] toByteArray(int[] sourceArray)
    {
        Byte[] arr = new Byte[sourceArray.length];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = (byte) sourceArray[i];
        }
        return arr;
    }

    public static Byte[] toByteArray(Integer[] sourceArray)
    {
        Byte[] arr = new Byte[sourceArray.length];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = sourceArray[i].byteValue();
        }
        return arr;
    }

    public static Integer[] toIntegerArray(int[] sourceArray)
    {
        Integer[] arr = new Integer[sourceArray.length];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = sourceArray[i];
        }
        return arr;
    }

    public static Integer[] toIntegerArray(byte[] sourceArray)
    {
        Integer[] arr = new Integer[sourceArray.length];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = new Integer(sourceArray[i]);
        }
        return arr;
    }

    public static Integer[] toIntegerArray(Byte[] sourceArray)
    {
        Integer[] arr = new Integer[sourceArray.length];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = sourceArray[i].intValue();
        }
        return arr;
    }

    public static byte[] toPrimitiveByteArray(Byte[] sourceArray)
    {
        byte[] arr = new byte[sourceArray.length];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = sourceArray[i];
        }
        return arr;
    }

    public static byte[] toPrimitiveByteArray(int[] sourceArray)
    {
        byte[] arr = new byte[sourceArray.length];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = (byte) sourceArray[i];
        }
        return arr;
    }

    public static byte[] toPrimitiveByteArray(Integer[] sourceArray)
    {
        byte[] arr = new byte[sourceArray.length];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = sourceArray[i].byteValue();
        }
        return arr;
    }

    public static int[] toPrimitiveIntegerArray(Integer[] sourceArray)
    {
        int[] arr = new int[sourceArray.length];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = sourceArray[i];
        }
        return arr;
    }

    public static int[] toPrimitiveIntegerArray(byte[] sourceArray)
    {
        int[] arr = new int[sourceArray.length];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = sourceArray[i];
        }
        return arr;
    }

    public static int[] toPrimitiveIntegerArray(Byte[] sourceArray)
    {
        int[] arr = new int[sourceArray.length];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = sourceArray[i];
        }
        return arr;
    }

}
