package com.insaneio.insane.cryptography;

import com.insaneio.insane.string.Strings;

/**
 * @author Joma Espinoza Bone on 10/10/2014.
 */
public enum HashAlgorithm
{

    /**
     * Valor para el algoritmo SHA512.
     */
    SHA512("SHA-512"),

    /**
     * Valor para el algoritmo SHA256.
     */
    SHA256("SHA-256"),

    /**
     * Valor para el algoritmo SHA384.
     */
    SHA384("SHA-384"),

    /**
     * Valor para el algoritmo SHA1.
     */
    SHA1("SHA-1"),//SHA-1
    /**
     * Valor para el algoritmo MD5.
     */
    MD5("MD5");


    private String Value = Strings.EMPTY;


    HashAlgorithm(String Value) {
        this.Value = Value;
    }


    @Override
    public String toString() {
        return Value;
    }

}
