package org.mp4parser.tools;

import org.mp4parser.UnprocessableInputException;

public final class MemoryUtils {
    private static final long MEGABYTE = 1024L * 1024L;
    private static final long MEMORY_ALLOCATION_LIMIT_BYTES = MEGABYTE * 10;

    private MemoryUtils() { }

    /*
     * Verify contentSize does not exceed memory allocation limt.
     * Throws UnprocessableInputException if it does.
     */
    public static void verifyAvailableMemory(long contentSize) {
        if (contentSize > MEMORY_ALLOCATION_LIMIT_BYTES) {
            // likely bad header parsing has lead to unreasonably high memory allocation requests.
            throw new UnprocessableInputException("Cannot allocate " + contentSize + " memory. Memory allocation limit: " + MEMORY_ALLOCATION_LIMIT_BYTES);
        }
    }
}
