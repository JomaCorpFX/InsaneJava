package com.insaneio.insane.serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Joma Espinoza Bone on 18/07/2016.
 */
public class JsonEnumTypeAdapter extends TypeAdapter<Enum>
{

    private Boolean useDotNetFormat;
    private Enum[] constants;

    private JsonEnumTypeAdapter()
    {

    }

    public JsonEnumTypeAdapter(Boolean useDotNetFormat, Enum[] constants)
    {
        this();
        JsonEnumTypeAdapterX(useDotNetFormat, constants);
    }

    @Override
    public void write(JsonWriter out, Enum value) throws IOException
    {
        writeX(out, value);
    }

    @Override
    public Enum read(JsonReader in) throws IOException
    {
        return readX(in);
    }

    private void JsonEnumTypeAdapterX(Boolean isDotNetFormat, Enum[] enumConstants)
    {
        useDotNetFormat = isDotNetFormat;
        constants = enumConstants;
    }

    private void writeX(JsonWriter out, Enum value) throws IOException
    {
        if (value == null)
        {
            out.nullValue();
            return;
        }
        if (useDotNetFormat)
        {
            out.value(value.ordinal());
        }
        else
        {
            out.value(value.toString());
        }
    }

    private Enum readX(JsonReader in) throws IOException
    {
        if (in.peek() == JsonToken.NULL)
        {
            in.nextNull();
            return null;
        }
        if (in.peek() == JsonToken.NUMBER)
        {
            int number = in.nextInt();
            for (Enum value : constants)
            {
                if (value.ordinal() == number)
                {
                    return value;
                }
            }
        }
        else
        {
            String name = in.nextString();
            for (Enum value : constants)
            {
                if (value.name().equals(name))
                {
                    return value;
                }
            }
        }
        return null;
    }
}
