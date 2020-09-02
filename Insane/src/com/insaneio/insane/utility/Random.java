package com.insaneio.insane.utility;

import java.security.SecureRandom;

/**
 * Created by Joma Espinoza Bone on 18/08/2016.
 */
public class Random {

    public static int generate(int min, int max)
    {
        return generateX(min, max);
    }

    private static int generateX(int min, int max)
    {
        SecureRandom rnd = new SecureRandom();
        return min + rnd.nextInt(max - min + 1);
    }

}
