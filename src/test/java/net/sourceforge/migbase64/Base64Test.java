package net.sourceforge.migbase64;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Test;

public final class Base64Test
{
    public static final String HEX = "0123456789abcdef";

    public static byte toByte(char c1, char c2)
    {
        return (byte)((HEX.indexOf(c1) << 4) + HEX.indexOf(c2));
    }

    public static byte[] fromString(String s)
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = 0; i + 1 < s.length(); i += 2) {
            out.write(toByte(s.charAt(i), s.charAt(i + 1)));
        }
        return out.toByteArray();
    }

    public static void assertBase64Encode(String expected, String hexstr)
    {
        Assert.assertEquals(expected,
                Base64.encodeToString(fromString(hexstr), false));
    }

    @Test
    public void encodeToString()
    {
        assertBase64Encode("AAAA", "000000");
        assertBase64Encode("AAA=", "0000");
        assertBase64Encode("AA==", "00");

        assertBase64Encode("////", "ffffff");
        assertBase64Encode("//8=", "ffff");
        assertBase64Encode("/w==", "ff");
    }

}
