package com.insaneio.insane.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Joma Espinoza Bone.
 */
public class JsonSerializerDeserializer
{

    private JsonSerializerDeserializer()
    {

    }

    public static String serialize(Object instance, Boolean useDotNetFormat)
    {
        return serializeX(instance, useDotNetFormat);
    }
    
    public static String serialize(Object instance)
    {
        return serializeX(instance);
    }

    public static <T> T deserialize(String json, Class<T> resultType)
    {
        return deserializeX(json, resultType);
    }


    private static String serializeX(Object instance, Boolean useDotNetFormat)
    {
        GsonBuilder builder = new GsonBuilder();
        //builder.registerTypeAdapter(DateTime.class, new DateTimeSerializerDeserializer(useDotNetFormat));
        //builder.registerTypeAdapter(DateTime.class, new DateTimeAdapter(useDotNetFormat));
        builder.registerTypeAdapterFactory(new JsonTypeAdapterFactory(useDotNetFormat));
        Gson handler = builder.create();
        if (useDotNetFormat)
        {
            return (handler.toJson(instance)).replace(X.get("mtsy2sYWxQo6y8NqAnc89ooBx534ZLFQ92FROvw3QhI,", true)/* "/" */, X.get("jLWpS4TrdXMfDH-iRLcldpSejKi71Rs9Mh4dtt8-4mE,", true)/* "\\/" */);
        }
        else
        {
            return handler.toJson(instance);
        }
    }

    private static String serializeX(Object instance)
    {
        return serializeX(instance, false);
    }

    private static <T> T deserializeX(String json, Class<T> resultType)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new JsonTypeAdapterFactory(false));
        Gson handler = builder.create();
        return handler.fromJson(json, resultType);
    }
}
