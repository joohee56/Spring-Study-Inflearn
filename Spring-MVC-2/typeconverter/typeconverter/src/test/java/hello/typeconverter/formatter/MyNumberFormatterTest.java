package hello.typeconverter.formatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;

import java.text.ParseException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

public class MyNumberFormatterTest {

    MyNumberFormatter formatter = new MyNumberFormatter();

    @Test
    void parse() throws ParseException {
        Number result = formatter.parse("1,000", Locale.KOREA);
        assertThat(result).isEqualTo(1000L); //Long 타입 주의
    }

    @Test
    void print() {
        String result = formatter.print(1000, Locale.KOREA);
        assertThat(result).isEqualTo("1,000");
    }
}
