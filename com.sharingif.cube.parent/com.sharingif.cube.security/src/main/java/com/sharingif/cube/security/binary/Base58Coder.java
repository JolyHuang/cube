package com.sharingif.cube.security.binary;

import java.math.BigInteger;
import java.util.Arrays;

import com.sharingif.cube.security.confidentiality.encrypt.digest.SHA256Encryptor;

/**
 * Base58Coder
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/6/29 下午6:35
 */
public class Base58Coder {


    private static final SHA256Encryptor SHA256_ENCRYPTOR = new SHA256Encryptor();
    private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final BigInteger BASE = BigInteger.valueOf(58);

    public String encode(byte[] input)
    {
        // This could be a lot more efficient.
        BigInteger bi = new BigInteger(1, input);
        StringBuffer s = new StringBuffer();
        while (bi.compareTo(BASE) >= 0)
        {
            BigInteger mod = bi.mod(BASE);
            s.insert(0, ALPHABET.charAt(mod.intValue()));
            bi = bi.subtract(mod).divide(BASE);
        }
        s.insert(0, ALPHABET.charAt(bi.intValue()));
        // Convert leading zeros too.
        for (byte anInput : input)
        {
            if (anInput == 0)
                s.insert(0, ALPHABET.charAt(0));
            else
                break;
        }
        return s.toString();
    }

    public byte[] decode(String input)
    {
        byte[] bytes = decodeToBigInteger(input).toByteArray();
        // We may have got one more byte than we wanted, if the high bit of the next-to-last byte was not zero. This
        // is because BigIntegers are represented with twos-compliment notation, thus if the high bit of the last
        // byte happens to be 1 another 8 zero bits will be added to ensure the number parses as positive. Detect
        // that case here and chop it off.
        boolean stripSignByte = bytes.length > 1 && bytes[0] == 0 && bytes[1] < 0;
        // Count the leading zeros, if any.
        int leadingZeros = 0;
        for (int i = 0; input.charAt(i) == ALPHABET.charAt(0); i++)
        {
            leadingZeros++;
        }
        // Now cut/pad correctly. Java 6 has a convenience for this, but Android can't use it.
        byte[] tmp = new byte[bytes.length - (stripSignByte ? 1 : 0) + leadingZeros];
        System.arraycopy(bytes, stripSignByte ? 1 : 0, tmp, leadingZeros, tmp.length - leadingZeros);
        return tmp;
    }

    protected static BigInteger decodeToBigInteger(String input)
    {
        BigInteger bi = BigInteger.valueOf(0);

        // Work backwards through the string.
        for (int i = input.length() - 1; i >= 0; i--)
        {
            int alphaIndex = ALPHABET.indexOf(input.charAt(i));
            if (alphaIndex == -1)
            {
                throw new IllegalArgumentException("In Base58.decodeToBigInteger(), Illegal character " + input.charAt(i) + " at index " + i + ". Throwing new IlleglArgumentException.");
            }
            bi = bi.add(BigInteger.valueOf(alphaIndex).multiply(BASE.pow(input.length() - 1 - i)));
        }

        return bi;
    }

    public String encodeChecked(byte[] b) {
        byte[] cs = SHA256_ENCRYPTOR.encrypt(b);
        byte[] extended = new byte[b.length + 4];
        System.arraycopy(b, 0, extended, 0, b.length);
        System.arraycopy(cs, 0, extended, b.length, 4);
        return encode(extended);
    }

    /**
     * Decodes the given base58 string into the original data bytes, using the checksum in the
     * last 4 bytes of the decoded data to verify that the rest are correct. The checksum is
     * removed from the returned data.
     *
     * @param input the base58-encoded string to decode (which should include the checksum)
     * @return the original data bytes less the last 4 bytes (the checksum).
     */
    public byte[] decodeChecked(String input) {
        byte[] decoded = decode(input);
        if (decoded.length < 4)
            throw new RuntimeException("Input too short");
        byte[] data = Arrays.copyOfRange(decoded, 0, decoded.length - 4);
        byte[] checksum = Arrays.copyOfRange(decoded, decoded.length - 4, decoded.length);
        byte[] actualChecksum = Arrays.copyOfRange(SHA256_ENCRYPTOR.encrypt(data), 0, 4);
        if (!Arrays.equals(checksum, actualChecksum))
            throw new RuntimeException("Checksum does not validate");
        return data;
    }

    /**
     * Divides a number, represented as an array of bytes each containing a single digit
     * in the specified base, by the given divisor. The given number is modified in-place
     * to contain the quotient, and the return value is the remainder.
     *
     * @param number     the number to divide
     * @param firstDigit the index within the array of the first non-zero digit
     *                   (this is used for optimization by skipping the leading zeros)
     * @param base       the base in which the number's digits are represented (up to 256)
     * @param divisor    the number to divide by (up to 256)
     * @return the remainder of the division operation
     */
    private byte divmod(byte[] number, int firstDigit, int base, int divisor) {
        // this is just long division which accounts for the base of the input digits
        int remainder = 0;
        for (int i = firstDigit; i < number.length; i++) {
            int digit = (int) number[i] & 0xFF;
            int temp = remainder * base + digit;
            number[i] = (byte) (temp / divisor);
            remainder = temp % divisor;
        }
        return (byte) remainder;
    }

}
