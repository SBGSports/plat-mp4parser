package org.mp4parser;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class AbstractBoxParserTest {

    @Test(expected = UnprocessableInputException.class)
    public void shouldThrowUnprocessableInputExceptionForBoxSizeZero() throws IOException {
        // 8 bytes is meant to represent the header.
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[8]);
        ReadableByteChannel channel = Channels.newChannel(inputStream);
        new TestClass().parseBox(channel, "parent7676");
    }

    private static class TestClass extends AbstractBoxParser {
        @Override
        public ParsableBox createBox(String type, byte[] userType, String parent) {
            return null;
        }
    }
}
