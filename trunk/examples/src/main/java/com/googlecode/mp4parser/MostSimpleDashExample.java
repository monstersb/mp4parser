package com.googlecode.mp4parser;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.TrackBox;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Mp4TrackImpl;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sannies
 * Date: 11.05.13
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */
public class MostSimpleDashExample {


    public static void main(String[] args) throws IOException {
        String basePath = GetDuration.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "/dash/";

        Movie m = new Movie();

        ArrayList<ByteBuffer> samples = new ArrayList<ByteBuffer>();
        List<Mp4TrackImpl> videoTracks = new ArrayList<Mp4TrackImpl>();
        for (int i = 1; i < 2; i++) {
            IsoFile baseIsoFile = new IsoFile(basePath + "redbull_100kbit_dash.mp4");
            IsoFile fragment = new IsoFile(basePath + "redbull_10sec" + i + ".m4s");
            for (Box box : fragment.getBoxes()) {
                baseIsoFile.addBox(box);
            }
            videoTracks.add(new Mp4TrackImpl(baseIsoFile.getMovieBox().getBoxes(TrackBox.class).get(0)));
        }

        m.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));


        DefaultMp4Builder builder = new DefaultMp4Builder();
        IsoFile stdMp4 = builder.build(m);
        FileOutputStream fos = new FileOutputStream("out.mp4");
        stdMp4.getBox(fos.getChannel());
        fos.close();
    }
}
