/*  
 * Copyright 2008 CoreMedia AG, Hamburg
 *
 * Licensed under the Apache License, Version 2.0 (the License); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an AS IS BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package org.mp4parser.boxes.iso14496.part12;

import org.mp4parser.BoxParser;
import org.mp4parser.ParsableBox;
import org.mp4parser.support.DoNotParseDetail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * <h1>4cc = "{@value #TYPE}"</h1>
 * This box contains the media data. In video tracks, this box would contain video frames. A presentation may
 * contain zero or more Media Data Boxes. The actual media data follows the type field; its structure is described
 * by the metadata (see {@link SampleTableBox}).<br>
 * In large presentations, it may be desirable to have more data in this box than a 32-bit size would permit. In this
 * case, the large variant of the size field is used.<br>
 * There may be any number of these boxes in the file (including zero, if all the media data is in other files). The
 * metadata refers to media data by its absolute offset within the file (see {@link StaticChunkOffsetBox});
 * so Media Data Box headers and free space may easily be skipped, and files without any box structure may
 * also be referenced and used.
 */
public final class MediaDataBox implements ParsableBox {
    public static final String TYPE = "mdat";
    long mdatSize;

    public MediaDataBox() {
    }

    public String getType() {
        return TYPE;
    }


    public void getBox(WritableByteChannel writableByteChannel) throws IOException {
        throw new UnsupportedOperationException("MediaDataBox.getBox is no longer supported. This library fork only supports parsing.");
    }

    public long getSize() {
        return mdatSize;
    }

    /**
     * The original impl of the mdat parse would write the contents to a temp file and referred directly in getBox() for large video scenarios.
     * This solution does not work when your system is disk constrained.
     *
     * The use-case for video analysing does not need to access or analyse the mdat box, so the calculated contentSize bytes are
     * instead just skipped over.
     */
    @DoNotParseDetail
    public void parse(ReadableByteChannel dataSource, ByteBuffer header, long contentSize, BoxParser boxParser) throws IOException {
        // we do not want to process any bytes of the mdat box. Just read and dump them as we go.
        mdatSize = header.limit() + contentSize;

        // just read and drop bytes for contentSize (bytes).
        skip(dataSource, contentSize);
    }

    // https://commons.apache.org/proper/commons-io/javadocs/api-2.5/org/apache/commons/io/IOUtils.html#skip(java.nio.channels.ReadableByteChannel,%20long)
    public static long skip(ReadableByteChannel input, long toSkip) throws IOException {
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        } else {
            ByteBuffer skipByteBuffer = ByteBuffer.allocate((int)Math.min(toSkip, 2048L));

            long remain;
            int n;
            for(remain = toSkip; remain > 0L; remain -= (long)n) {
                skipByteBuffer.position(0);
                skipByteBuffer.limit((int)Math.min(remain, 2048L));
                n = input.read(skipByteBuffer);
                if (n == -1) {
                    break;
                }
            }

            return toSkip - remain;
        }
    }

}
