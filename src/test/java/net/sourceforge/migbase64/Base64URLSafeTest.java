package net.sourceforge.migbase64;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Test;

public final class Base64URLSafeTest
{
    public static final String HEX = "0123456789abcdef";

    public static void assertBase64Encode(String expected, String hexstr,
            boolean padding)
    {
        Assert.assertEquals(expected, Base64URLSafe.encodeToString
                (Base64Test.fromString(hexstr), false, padding));
    }

    @Test
    public void encodeToStringWithPadding()
    {
        assertBase64Encode("AAAA", "000000", true);
        assertBase64Encode("AAA=", "0000",   true);
        assertBase64Encode("AA==", "00",     true);

        assertBase64Encode("____", "ffffff", true);
        assertBase64Encode("__8=", "ffff",   true);
        assertBase64Encode("_w==", "ff",     true);
    }

    @Test
    public void encodeToStringWithoutPadding()
    {
        assertBase64Encode("AAAA", "000000", false);
        assertBase64Encode("AAA",  "0000",   false);
        assertBase64Encode("AA",   "00",     false);

        assertBase64Encode("____", "ffffff", false);
        assertBase64Encode("__8",  "ffff",   false);
        assertBase64Encode("_w",   "ff",     false);
    }

}
