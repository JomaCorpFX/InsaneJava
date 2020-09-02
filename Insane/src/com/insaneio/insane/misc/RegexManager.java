package com.insaneio.insane.misc;


import com.insaneio.insane.string.Strings;

import java.util.regex.Pattern;

/**
 * @author Joma Espinoza Bone on 13/10/2015.
 */
public class RegexManager
{

    public static Boolean isValidEmail(String Email)
    {
        if (Strings.isNullOrWhiteSpace(Email))
        {
            return false;
        }

        return regExpMatches(Email, X.get("gdta3W9ewqi4uSbVjNFIsp5rtCpnQCutxsDx_nTkTlA0CZcEPoN1RgYukm9uUvyvWkSwKl2jfzMP80f09UlzF2ArdI0NeS716e1-2bAV9FGVUU7BvkeH3QLzROwuAb6Ux7F7fGNImMWYbwkiCa5-vw,,", true)/* "^[_a-zA-Z0-9-]+(\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*(\.[a-zA-Z]{2,4})$" */);
    }


    public static Boolean regExpMatches(String data, String pattern)
    {
        Pattern p = Pattern.compile(pattern);
        return p.matcher(data).matches();
    }


    public static Boolean isValidPhoneNumber(String Phone)
    {
        if (Strings.isNullOrWhiteSpace(Phone))
        {
            return false;
        }
        return regExpMatches(Phone, X.get("jdfsN1Hjm6a3vL-pvd2DdXTiCQM2PCTXKQSDt-XMCi_tg60d_7zjdpi43jzhpfGJ", true)/* "^[+0-9]{0,1}(\d{6,})$" */);
    }


    public static Boolean isValidPhoneNumber(String Phone, int NumDigit)
    {
        if (NumDigit < 6)
        {
            throw new IllegalArgumentException("Número de digitos debe ser mayor o igual a 6.");
        }
        String Pattern = X.get("A_k9uZOMTaDLDLkFgXHyNwYjj4KDEgA7okF8pLsO211ucIe2JtBMdRnUQBWGT32N", true)/* "^[+0-9]{0,1}(\d{6," */ + String.valueOf(NumDigit-1) + X.get("1eQOeUCvsjil3zVJnkRWxcIAXfUUfBupLEYSFfcopwU,", true)/* "})$" */;
        if (Strings.isNullOrWhiteSpace(Phone))
        {
            return false;
        }
        return regExpMatches(Phone, Pattern);
    }


    public static Boolean isValidStrongPassword(String Password)
    {
        //Código obtenido de http://web.ontuts.com/snippets/10-expresiones-regulares-imprescindibles-en-desarrollo-web/
        if (Strings.isNullOrWhiteSpace(Password))
        {
            return false;
        }
        return regExpMatches(Password, X.get("qjf7IlADRDEY6Y2RQa0NvrRBJPb0tzqGWdFA6rJcffIAEBCKnvN9eU_phbMMR4mB9UIyZiA9FuFOUbQqLQofqcI6alIXeiAw8tuave7v_WB6qyFkl0s3TvnNtgp47T6G", true)/* "(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" */);
    }
}
