package com.insaneio.insane.serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.insaneio.insane.string.Strings;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Created by Joma Espinoza Bone on 17/07/2016.
 */
public class JsonDateTimeTypeAdapter extends TypeAdapter<DateTime>
{
    Boolean useDotNetFormat;

    private JsonDateTimeTypeAdapter()
    {

    }

    public JsonDateTimeTypeAdapter(Boolean useDotNetFormat)
    {
        this();
        JsonDateTimeTypeAdapterX(useDotNetFormat);
    }
    

   
    @Override
    public void write(JsonWriter out, DateTime value) throws IOException
    {
        writeX( out,  value);
    }
    

    @Override
    public DateTime read(JsonReader in) throws IOException
    {
        return readX(in);
    }


    private void JsonDateTimeTypeAdapterX(Boolean isDotNetFormat)
    {
        useDotNetFormat = isDotNetFormat;
    }
    private void writeX(JsonWriter out, DateTime value) throws IOException
    {
        if (value == null)
        {
            out.nullValue();
            return;
        }
        value = value.toDateTime(DateTimeZone.UTC);
        String json = useDotNetFormat ? Strings.format(X.get("mQH3lAo1B-iyxbiCAit_r2vUbPlxlMbBet9lWRWNdys,", true)/* "/Date({0})/" */, value.getMillis()) : value.toString(ISODateTimeFormat.dateTime());
        out.value(json);
    }
    private DateTime readX(JsonReader in) throws IOException
    {
        if (in.peek() == JsonToken.NULL)
        {
            in.nextNull();
            return null;
        }
        String jsonString = in.nextString();
        String Regexp = X.get("VFVfxj5JqYHSpS-jq1rcWwgaJKoaX5eyANaIamjTOXq2x--TQLqFXBrgNNiYTiItUOa8aqOJji_GK66TgILXvA,,", true)/* "\\/Date\\((\\-?\\d*?)([\\+\\-]\\d{4,4})?\\)\\/" */;
        Pattern MyPattern = Pattern.compile(Regexp);
        Matcher MyMatcher = MyPattern.matcher(jsonString);
        if (MyMatcher.matches())
        {
            Long time = new Long(MyMatcher.group(1));
            return new DateTime(time, DateTimeZone.UTC);
        }
        else
        {
            return DateTime.parse(jsonString.substring(0, jsonString.length())).toDateTime(DateTimeZone.UTC);
        }

    }
}
