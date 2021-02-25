package org.mp4parser.tools;

import org.junit.Test;
import org.mp4parser.UnprocessableInputException;

public class MemoryUtilsTest {
    @Test(expected = UnprocessableInputException.class)
    public void shouldThrowUnprocessableInputExceptionOnUnsupportedByteLengths() {
        MemoryUtils.verifyAvailableMemory(Long.MAX_VALUE);
    }
}
