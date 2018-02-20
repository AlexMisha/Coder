import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;

public enum SupportedCharsets {
    CP866(Charset.forName("CP866")),
    ISO88595(Charset.forName("ISO-8859-5")),
    CP1251(Charset.forName("windows-1251"));

    Charset charset;

    SupportedCharsets(@NotNull Charset charset) {
        this.charset = charset;
    }
}
