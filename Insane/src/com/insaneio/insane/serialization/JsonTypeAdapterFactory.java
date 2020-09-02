package com.insaneio.insane.serialization;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;

/**
 * Created by Joma Espinoza Bone on 17/07/2016.
 */
public class JsonTypeAdapterFactory implements TypeAdapterFactory
{

    private Boolean useDotNetFormat;

    private JsonTypeAdapterFactory()
    {

    }

    public JsonTypeAdapterFactory(Boolean useDotNetFormat)
    {
        this();
        JsonTypeAdapterFactoryX(useDotNetFormat);
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type)
    {
        return createX(gson, type);
    }

    private void JsonTypeAdapterFactoryX(Boolean isDotNetFormat)
    {
        useDotNetFormat = isDotNetFormat;
    }

    private <T> TypeAdapter<T> createX(Gson gson, TypeToken<T> type)
    {
        Class<? super T> rawType = type.getRawType();
        if (rawType == DateTime.class)
        {
            return (TypeAdapter<T>) new JsonDateTimeTypeAdapter(useDotNetFormat);
        }
        if (rawType.isEnum())
        {
            T[] constants = (T[]) rawType.getEnumConstants();
            return (TypeAdapter<T>) new JsonEnumTypeAdapter(useDotNetFormat, (Enum[]) constants);
        }
        return null;
    }


}
