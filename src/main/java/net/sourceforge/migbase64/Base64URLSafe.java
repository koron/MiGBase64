package net.sourceforge.migbase64;

public class Base64URLSafe
{
    private static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toCharArray();

    public final static char[] encodeToChar(byte[] sArr)
    {
        return encodeToChar(sArr, false, false);
    }

    public final static char[] encodeToChar(byte[] sArr, boolean lineSep,
            boolean padding)
    {
        // Check special case
        int sLen = sArr != null ? sArr.length : 0;
        if (sLen == 0) {
            return new char[0];
        }

        int eLen = (sLen / 3) * 3;              // Length of even 24-bits.
        int cCnt = ((sLen - 1) / 3 + 1) << 2;   // Returned character count
        if (!padding) {
            cCnt -= (eLen + 3 - sLen) % 3;
        }

        // Length of returned array.
        int dLen = cCnt;
        if (lineSep) {
            dLen += (cCnt - 1) / 76 << 1;
        }
        char[] dArr = new char[dLen];

        // Encode even 24-bits
        int d = 0;
        for (int s = 0, cc = 0; s < eLen;) {
            // Copy next three bytes into lower 24 bits of int, paying
            // attention to sign.
            int i = (sArr[s++] & 0xff) << 16 | (sArr[s++] & 0xff) << 8
                | (sArr[s++] & 0xff);

            // Encode the int into four chars
            dArr[d++] = CA[(i >>> 18) & 0x3f];
            dArr[d++] = CA[(i >>> 12) & 0x3f];
            dArr[d++] = CA[(i >>> 6) & 0x3f];
            dArr[d++] = CA[i & 0x3f];

            // Add optional line separator
            if (lineSep && ++cc == 19 && d < dLen - 2) {
                dArr[d++] = '\r';
                dArr[d++] = '\n';
                cc = 0;
            }
        }

        // Pad and encode last bits if source isn't even 24 bits.
        int left = sLen - eLen; // 0 - 2.
        if (left > 0) {
            // Prepare the int
            int i = ((sArr[eLen] & 0xff) << 10) |
                (left == 2 ? ((sArr[sLen - 1] & 0xff) << 2) : 0);

            // Set last four chars
            dArr[d++] = CA[i >> 12];
            dArr[d++] = CA[(i >>> 6) & 0x3f];
            if (left == 2) {
                dArr[d++] = CA[i & 0x3f];
            } else if (padding) {
                dArr[d++] = '=';
            }
            if (padding) {
                dArr[d++] = '=';
            }
        }
        return dArr;
    }

    public final static String encodeToString(byte[] sArr)
    {
        return new String(encodeToChar(sArr));
    }

    public final static String encodeToString(byte[] sArr, boolean lineSep,
            boolean padding)
    {
        return new String(encodeToChar(sArr, lineSep, padding));
    }
}
