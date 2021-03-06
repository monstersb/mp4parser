package com.coremedia.iso.boxes.rtp;

import com.coremedia.iso.BoxFactory;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoInputStream;
import com.coremedia.iso.IsoOutputStream;
import com.coremedia.iso.boxes.Box;

import java.io.IOException;

/**
 * The largest relative transmission time, in milliseconds.
 */
public class LargestRelativeTransmissionTimeBox extends Box {
  public static final String TYPE = "tmax";

  long time;

  public LargestRelativeTransmissionTimeBox() {
    super(IsoFile.fourCCtoBytes(TYPE));
  }

  protected long getContentSize() {
    return 4;
  }

  public void parse(IsoInputStream in, long size, BoxFactory boxFactory, Box lastMovieFragmentBox) throws IOException {
    time = in.readUInt32();
  }

  public String getDisplayName() {
    return "Largest Relative Transmission Time";
  }

  protected void getContent(IsoOutputStream os) throws IOException {
    os.writeUInt32(time);
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }
}
