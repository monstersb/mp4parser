# ISO Parser Quickstart #

The isoparser can read and write the MP4 file structure. It is a low level tool dealing with the so called boxes.

Let's quickly start with some example
```
FileChannel fc = new FileInputStream("file.mp4").getChannel();
IsoFile isoFile = new IsoFile(fc);
List<Box> boxes = isoFile.getBoxes();
```
```
FileChannel fc = new FileInputStream("file.mp4").getChannel();
IsoFile isoFile = new IsoFile(fc);
MovieBox moov = isoFile.getBoxes(MovieBox.class).get(0);
```
gives you the Movie Box.

Now you can navigate through the file structure.

Hint: The iTunes metadata is located here:
```
MovieBox moov = isoFile.getBoxes(MovieBox.class).get(0);
UserDataBox udta = moov.getBoxes(UserDataBox.class).get(0);
System.out.println(udta.getBoxes())
```

## Working on Movie/Track Level ##

In the last weeks I added an API that handles MP4 files on a much higher level. Be aware all of that is work in progress and _will_ change without further notice (but I'll help updating).

### Muxing MP4 ###

The example can be found in SVN under examples/src/main/java/com/googlecode/mp4parser/MuxExample.java

```
Movie countVideo = new MovieCreator().build(Channels.newChannel(MuxExample.class.getResourceAsStream("/count-video.mp4")));
Movie countAudioDeutsch = new MovieCreator().build(Channels.newChannel(MuxExample.class.getResourceAsStream("/count-deutsch-audio.mp4")));
Movie countAudioEnglish = new MovieCreator().build(Channels.newChannel(MuxExample.class.getResourceAsStream("/count-english-audio.mp4")));

Track audioTrackDeutsch = countAudioDeutsch.getTracks().get(0);
audioTrackDeutsch.getTrackMetaData().setLanguage("deu");
Track audioTrackEnglish = countAudioEnglish.getTracks().get(0);
audioTrackEnglish.getTrackMetaData().setLanguage("eng");

countVideo.addTrack(audioTrackDeutsch);
countVideo.addTrack(audioTrackEnglish);


IsoFile out = new DefaultMp4Builder().build(countVideo);
FileOutputStream fos = new FileOutputStream(new File("output.mp4"));
out.getBox(fos.getChannel());
fos.close();
```

This snippet open 3 MP4 files, adds the tracks of the latter two to the first and writes the result into a file.

### Adding Time Text (subtitles) ###
The example can be found in SVN under examples/src/main/java/com/googlecode/mp4parser/SubTitleExample.java

```
Movie countVideo = new MovieCreator().build(Channels.newChannel(SubTitleExample.class.getResourceAsStream("/count-video.mp4")));

TextTrackImpl subTitleEng = new TextTrackImpl();
subTitleEng.getTrackMetaData().setLanguage("eng");


subTitleEng.getSubs().add(new TextTrackImpl.Line(5000, 6000, "Five"));
subTitleEng.getSubs().add(new TextTrackImpl.Line(8000, 9000, "Four"));
subTitleEng.getSubs().add(new TextTrackImpl.Line(12000, 13000, "Three"));
subTitleEng.getSubs().add(new TextTrackImpl.Line(16000, 17000, "Two"));
subTitleEng.getSubs().add(new TextTrackImpl.Line(20000, 21000, "one"));

countVideo.addTrack(subTitleEng);

TextTrackImpl subTitleDeu = SrtParser.parse(SubTitleExample.class.getResourceAsStream("/count-subs-deutsch.srt"));
subTitleDeu.getTrackMetaData().setLanguage("deu");
countVideo.addTrack(subTitleDeu);

IsoFile out = new DefaultMp4Builder().build(countVideo);
FileOutputStream fos = new FileOutputStream(new File("output.mp4"));
out.getBox(new IsoOutputStream(fos));
fos.close();

```

### More Examples ###

  * [Append Tracks](AppendTracks.md)