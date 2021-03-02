package org.mp4parser.boxes.iso14496.part12;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class MediaDataBoxTest {

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotSupportGetBox() throws IOException {
        final MediaDataBox mediaDataBox = new MediaDataBox();
        mediaDataBox.getBox(null);
    }

    @Test
    public void shouldSkipOverByteChannelContents() throws IOException {
        int inputSize = 1024;
        final int contentSize = 50;
        byte[] input = new byte[inputSize];
        try (
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final ReadableByteChannel readableByteChannel = Channels.newChannel(new ByteArrayInputStream(input));
                final WritableByteChannel writableByteChannel = Channels.newChannel(baos);
        ) {
            final MediaDataBox mediaDataBox = new MediaDataBox();
            mediaDataBox.parse(readableByteChannel, ByteBuffer.allocate(100), contentSize, null);

            copy(readableByteChannel, writableByteChannel);

            // should have been read 50 bytes in .parse(). should have 1024 - 50 remaining.
            Assert.assertEquals(inputSize - contentSize, baos.size());
        }
    }

    public static void copy(ReadableByteChannel in, WritableByteChannel out) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(32 * 1024);
        while (in.read(buffer) != -1 || buffer.position() > 0) {
            buffer.flip();
            out.write(buffer);
            buffer.compact();
        }
    }
}
