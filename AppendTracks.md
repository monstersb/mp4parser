# Appending one Video to another #

## The most simple way - same encoder settings ##
```
MovieCreator mc = new MovieCreator();
Movie video = mc.build(Channels.newChannel(AppendExample.class.getResourceAsStream("/count-video.mp4")));
Movie audio = mc.build(Channels.newChannel(AppendExample.class.getResourceAsStream("/count-english-audio.mp4")));


List<Track> videoTracks = video.getTracks();
video.setTracks(new LinkedList<Track>());

List<Track> audioTracks = audio.getTracks();


for (Track videoTrack : videoTracks) {
   video.addTrack(new AppendTrack(videoTrack, videoTrack));
}
for (Track audioTrack : audioTracks) {
   video.addTrack(new AppendTrack(audioTrack, audioTrack));
}

IsoFile out = new DefaultMp4Builder().build(video);
FileOutputStream fos = new FileOutputStream(new File(String.format("output.mp4")));
out.getBox(fos.getChannel());
fos.close();
```

The AppendTrack initialization will fail if the Sample Description Boxes of the track arguments differ.

## Different encoder settings - separate tracks (not implemented) ##

Another way to append two videos is to keep the media in separate tracks. All tracks will have different start times (via edit list box)  timed in a way that track 'B' start it the moment track 'A' ends.

This will only work with quicktime since edit list boxes are not well supported in typical players.

## Different encoder settings - special case H264 (not implemented) ##

the avcC Box contains to important decoder configurations:

  * Sequence Parameter Set (SPS)
  * Picture Parameter Set (PPS)

Each entry in each category contains an ID. Each GOP (??) references one of those entries. Two H264 tracks could be appended if their SPS & PPS contain different ID. Each reference can then stay as is and all SPS & PPS can be merged. A problem occurs if an ID is used twice with different settings. In the 'same id - different setting' case all samples referencing that particular SPS or PPS must be rewritten with a new ID (reference the newly created SPS entry with the new ID).