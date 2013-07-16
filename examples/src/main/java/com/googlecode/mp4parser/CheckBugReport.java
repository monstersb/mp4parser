package com.googlecode.mp4parser;

import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.TimeToSampleBox;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.H264TrackImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sannies on 14.07.13.
 */
public class CheckBugReport {
    public static void main(String[] args) {

    }

    public void buildMP4(String inputMP4File, String h264File, String outputMP4file, ArrayList<Long> timeStamps) throws IOException {

        Movie movie = MovieCreator.build(new FileInputStream(inputMP4File).getChannel());
        Track audioTrack = movie.getTrackByTrackId(2);

        H264TrackImpl h264Track = new H264TrackImpl(new FileInputStream(h264File).getChannel());

        List<TimeToSampleBox.Entry> ttsbEntries = h264Track.getDecodingTimeEntries();
        ttsbEntries.clear();

        //SKIPPING FIRST AND LAST SAMPLES - LOOKS LIKE THEY ARE NOT REAL SAMPLES
        //COUNTING DELTAS, THAT'S WHY STARTING FROM 2
        for (int i = 2; i < timeStamps.size(); i++)
        {
            long deltaMicroSecs = timeStamps.get(i) - timeStamps.get(i - 1);
            double deltaMilliSecs = (deltaMicroSecs / 1000.0);
            int deltaKhz = (int) (deltaMilliSecs * 90.0);

            TimeToSampleBox.Entry entry = new TimeToSampleBox.Entry(1, deltaKhz);
            ttsbEntries.add(entry);
        }

        Movie m = new Movie();
        m.addTrack(h264Track);
        m.addTrack(audioTrack);

        Container out = new DefaultMp4Builder().build(m);

        FileOutputStream fos = new FileOutputStream(new File(outputMP4file));
        out.writeContainer(fos.getChannel());
        fos.close();
    }

}
