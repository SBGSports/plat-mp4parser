package org.mp4parser;

import org.junit.Assert;
import org.junit.Test;
import org.mp4parser.boxes.iso14496.part12.SampleTableBox;
import org.mp4parser.boxes.iso14496.part12.SyncSampleBox;

import java.io.FileInputStream;
import java.util.List;

public class SyncSampleBoxTest {
    @Test
    public void shouldGetSyncSamples() throws Exception {
        IsoFile isoFile = new IsoFile(new FileInputStream(this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile() + "/Tiny Sample - NEW - Metaxed.mp4").getChannel());
        List<SampleTableBox> sampleTableBoxes = org.mp4parser.tools.Path.getPaths(isoFile, "moov/trak/mdia/minf/stbl");
        final SyncSampleBox syncSampleBox = sampleTableBoxes.get(0).getSyncSampleBox();
        Assert.assertEquals(3, syncSampleBox.getSampleNumber().length);
    }
}
