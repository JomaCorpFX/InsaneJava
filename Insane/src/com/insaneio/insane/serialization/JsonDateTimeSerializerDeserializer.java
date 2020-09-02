package com.insaneio.insane.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.insaneio.insane.string.Strings;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author Joma Espinoza Bone on 08/07/2016.
 */
public class JsonDateTimeSerializerDeserializer implements JsonSerializer<DateTime>, JsonDeserializer<DateTime>
{

    private Boolean useDotNetFormat;

    private JsonDateTimeSerializerDeserializer()
    {

    }

    public JsonDateTimeSerializerDeserializer(Boolean isInDotNetFormat)
    {
        this();
        JsonDateTimeSerializerDeserializerX(isInDotNetFormat);
    }

    @Override
    public JsonElement serialize(DateTime t, Type type, JsonSerializationContext jsc)
    {
        return serializeX(t, type, jsc);
    }

    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        return deserializeX(json, typeOfT, context);
    }

    private JsonElement serializeX(DateTime t, Type type, JsonSerializationContext jsc)
    {
        t = t.toDateTime(DateTimeZone.UTC);
        return useDotNetFormat ? new JsonPrimitive(Strings.format(X.get("iaV8acUL-zL6lo7fhQFVcNKWq2YadAa_XC2ocYhwYTE,", true)/* "/Date({0})/" */, t.getMillis())) : new JsonPrimitive(t.toString(ISODateTimeFormat.dateTime()));
    }

    private void JsonDateTimeSerializerDeserializerX(Boolean isInDotNetFormat)
    {
        useDotNetFormat = isInDotNetFormat;
    }

    private DateTime deserializeX(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        String jsonString = json.getAsJsonPrimitive().getAsString();
        String Regexp = X.get("-QiAFVYxkys5a82WJX3tD2Dy2dJsP72GgGkxPdQ2--_ErZbcy2rDXwDaHMxT7xR00Eu5TStoCeOx1-wjPg_S0A,,", true)/* "\\/Date\\((\\-?\\d*?)([\\+\\-]\\d{4,4})?\\)\\/" */;
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
