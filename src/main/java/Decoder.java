import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.util.Arrays;

public class Decoder implements IDecoder {
    @NotNull
    @Override
    public byte[] decode(@NotNull byte[] src, @NotNull Charset srcCharset) {
        if (srcCharset.equals(SupportedCharsets.CP866.charset)) {
            return fromCP866ToCP1251(src);
        }
        if (srcCharset.equals(SupportedCharsets.ISO88595.charset)) {
            return fromISO88595ToCP1251(src);
        }
        if (srcCharset.equals(SupportedCharsets.CP1251.charset)) {
            return CoderKt.fromCP1251ToUTF8(src);
        }
        throw new IllegalArgumentException("Unsupported charset: " + srcCharset.toString());
    }

    @NotNull
    private byte[] fromCP866ToCP1251(@NotNull byte[] src) {
        byte[] result = Arrays.copyOf(src, src.length);
        for (int i = 0; i < src.length; i++) {
            if (i == 0) result[i] -= 36;
            if (i == 1) result[i] -= 86;

            if (result[i] > -129 && result[i] < -80) {
                result[i] += 64;
            } else if (result[i] > -33 && result[i] < -16) {
                result[i] += 16;
            }
        }
        return result;
    }

    @NotNull
    private byte[] fromISO88595ToCP1251(@NotNull byte[] src) {
        byte[] result = Arrays.copyOf(src, src.length);
        for (int i = 0; i < src.length; i++) {
            if (result[i] >= -80 && result[i] <= -17) {
                result[i] += 0x10;
            }
        }
        return result;
    }

    @Contract("null -> fail")
    @NotNull
    public static byte[] getBytes(String src) {
        if (src == null) throw new IllegalArgumentException("Source must not be null");
        return src.getBytes();
    }
}
