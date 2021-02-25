package org.mp4parser.tools;

import org.junit.Test;
import org.mp4parser.UnprocessableInputException;

public class CastUtilsTest {
    @Test(expected = UnprocessableInputException.class)
    public void shouldThrowUnprocessableInputExceptionWhenByteLengthGreaterThanIntegerMaxValue() {
        long input = Integer.MAX_VALUE;
        CastUtils.l2i(++input);
    }
}
