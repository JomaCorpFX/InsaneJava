package com.insaneio.insane.cryptography;

import com.insaneio.insane.string.Strings;

/**
 * @author Joma Espinoza Bone on 14/10/2014.
 */
public class RsaKeyPair
{

    private String PublicKey;
    private String PrivateKey;

    public RsaKeyPair()
    {
    }

    public RsaKeyPair(String PublicStringKey, String PrivateStringKey)
    {
        this.PublicKey = PublicStringKey;
        this.PrivateKey = PrivateStringKey;
    }

    public String getPublicKey()
    {
        return PublicKey;
    }

    public void setPublicKey(String PublicStringKey)
    {
        this.PublicKey = PublicStringKey;
    }

    public String getPrivateKey()
    {
        return PrivateKey;
    }

    public void setPrivateKey(String PrivateStringKey)
    {
        this.PrivateKey = PrivateStringKey;
    }
    
    @Override
    public String toString()
    {
        return Strings.format("Public{0}{1}{0}Private{0}{2}", Strings.NEW_LINE, PublicKey, PrivateKey);
    }
}
