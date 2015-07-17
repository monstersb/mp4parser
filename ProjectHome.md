<h1>MOVED TO GITHUB</h1>
<h1>ONLY FOR REFERENCE</h1>
<h1><a href='https://github.com/sannies/mp4parser'>MP4Parser @ Github</a></h1>

<table width='100%'>
<tr>
<td align='left'><a href='http://mp4parser.googlecode.com/files/isoviewer-1.0-RC-26.jnlp'><img src='http://mp4parser.googlecode.com/files/webstart.png' /></a></td>
<td>

</td>

<td>
<a href='http://stackoverflow.com/users/829133/sebastian-annies'>
<img src='http://stackoverflow.com/users/flair/829133.png?theme=clean' alt='profile for Sebastian Annies at Stack Overflow, Q&amp;A for professional and enthusiast programmers' title='profile for Sebastian Annies at Stack Overflow, Q&amp;A for professional and enthusiast programmers' width='208' height='58' />
</a>

</td>
<td align='right'><a href='https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=RYAMZH9C2XXHU'><img src='https://www.paypalobjects.com/en_US/DE/i/btn/btn_donateCC_LG.gif' /></a></td>
</tr>
</table>


# The MP4 Parser Project #

Find examples here: https://mp4parser.googlecode.com/svn/trunk/examples/src/main/java/com/googlecode/mp4parser/

## ISO Parser ##

The isoparser API can read and write the MP4 file structure. It is a low level tool dealing with the so called boxes but it is as well as dealing with structure like tracks and movies. For examples see [Examples](Examples.md).

If you have any questions please ask at the [mp4parser discussion group](http://groups.google.com/group/mp4parser-discussion). I don't do private support.

**Do NOT copy the sources into your project. Use a released version. Use Maven. Make exemptions only if you really have to. Keep your upgrade path open. Benefit from my bugfixes and performance tweaks.**

## ISO Viewer ##

The ISO Viewer has been moved to Github.com. See: https://github.com/sannies/isoviewer

## Maven Repository ##
The isoparser artifact is deployed to maven central. Using mp4parser is just a dependency away:

```
<dependency>
  <groupId>com.googlecode.mp4parser</groupId> 
  <artifactId>isoparser</artifactId> 
  <version>1.0.1</version> 
</dependency> 
```


Snapshots are deployed to the Sonatype OSS Nexus: https://oss.sonatype.org/content/repositories/snapshots/

Some releases are not published to central. They will stay inside publicly accessible staging repositories until someone asks me to publish it to central.


## Using without Maven ##

I do not publish artifacts here. I only publish them to either maven-central.
You can access a release in Maven central  browsing to:

http://repo1.maven.org/maven2/com/googlecode/mp4parser/isoparser/

The isoparser library depends on aspectj-rt.jar, you may download it from maven central:

http://repo1.maven.org/maven2/org/aspectj/aspectjrt/1.7.3/

In case of any questions please post publicly on the mp4parser-discussion google group. I will not give any private support (donations can of course convince me to do otherwise).

### Credits ###

YourKit supports this project with a free open source license. Thank you, YourKit!

YourKit is kindly supporting open source projects with its full-featured Java Profiler.
YourKit, LLC is the creator of innovative and intelligent tools for profiling
Java and .NET applications. Take a look at YourKit's leading software products:

[YourKit Java Profiler](http://www.yourkit.com/java/profiler/index.jsp) and [YourKit .NET Profiler](http://www.yourkit.com/.net/profiler/index.jsp)

### Changes/Releases ###
10.Oct.13: isoparser-1.0-RC-27

**Fallback to system class loader if current thread doesn't have class loader (Android oddity)** Introduced DataSource to enable usage without underlying file

30.Aug.13: isoparser-1.0-RC-26

https://oss.sonatype.org/content/repositories/comgooglecodemp4parser-1005

Improved mem-consumption. Thanks for the patch Anton!

16.Jul.13: isoparser-1.0-RC-25


  * Speed up H264TrackImpl
  * Allow AppendTrack with 14496-2 streams
  * SampleEntry implements Box
  * IsoViewer now always has same version number as isoparser.
  * Major changes: read no longer possible from ReadableByteChannel
  * Added Matrix object to handling uvw-transformation at TrackHeaderBox and MovieHeaderBox more easily
  * Added H264TrackImpl constructor that allows to set timescale and ticksperframe
  * H264TrackImpl now uses FileChannel
  * Audio track volume now 1.0 by default
  * All date fields' types are now Java Date and no longer Macintosh epoch
  * The Movie object now preserves orientation


10.Apr.13: isoparser-1.0-RC-23

  * Repaired a weird Android issue where multiple rewind() calls on a MappedMemoryBuffer caused an inconsistent internal state of the buffer.
  * Added a very basic new IsoViewer on JavaFX 2.2 base
  * Added a BoxReplacer helper to replace certain boxes in a file with ease

Repository: https://oss.sonatype.org/content/repositories/comgooglecodemp4parser-781/


18.Mar.13: isoparser-1.0-RC-22

Many small changes. Huge performance leap. Massively reduced the number of memory allocations. Releases 19, 20, 21 are just steps on the way.

https://oss.sonatype.org/content/repositories/comgooglecodemp4parser-919/

21.Dec.12: isoparser-1.0-RC-18

[Staging Repo](https://oss.sonatype.org/content/repositories/comgooglecodemp4parser-137/)

  * Various bug fixes: SampleGroupDescriptionBox, support for large files (with timestamps > int), AvcConfigurationBox initial values,
  * Added a bunch of box names in isoviewer
  * As always a few minor performance tweaks
  * Added tapt, clef, prof & enof box
  * Boxes are now parsed completely on-demand.
  * IsoFile itself does no longer skips through whole file. You may now use the isoparser to extract metadata from multi-gigabyte files without even parsing through the whole file.

31.Aug.12: isoparser-1.0-RC-15

Published to central

  * DTS muxer fixes
  * make language for audio tracks configurable

17.Aug.12: isoparser-1.0-RC-14

Not available anymore

  * Small change in SyncSampleIntersectionFinderImpl so that it AGAIN throws an exception if not common sync samples are found
  * Minor IsoViewer usability stuff
  * Added IsoFile(File) constructor for convenience
  * Added ability to set language when importing raw audio tracks

8.Aug.12: isoparser-1.0-RC-12

Not available anymore

  * Use memory mapping earlier. Now box > 100Kb are mapped and not only boxes > 1Mb to prevent OOME with big fragmented MP4 files
  * Implemented Sample Group Description Box
  * Updated License Headers
  * Added TaptAtom without knowing what it is but its a container box and now you can look into it
  * Made IsoViewer display names of well-known UUID boxes
  * improved and tidied up examples.
  * Fixed bug in 'pssh' box - it's now read and written correctly
  * Correctly pssh box - it's a full box and no simple box.

19.Jul.12: isoparser-1.0-RC-11

Not available anymore

  * Added boxes: gmin, text (quciktime text), gmhd, mehd,
  * Added SilenceTrack to produce AAC silence
  * Made AppendTrack ignore minor difference in AAC Decoder Config Descriptor

12.Jun.12: isoparser-1.0-RC-10 (not publicy available)

  * Allow setting of minimum fragment duration with SyncSampleIntersectionFinderImpl
  * Added Protection System Specific Header Box (pssh) from common encryption
  * Deal with virtual memory restrictions on 32 bit system (mdat)
  * read default IV size from 'tenc' when parsing 'senc'

25.May.12: isoparser-1.0-RC-9 (not publicy available)

  * isoparser's SampleList now knows about sample size defaults in Track Extends Box (trex)
  * improved 2s intersection finder (prevent crash when segment is too short)

22.May.12: isoparser-1.0-RC-8 (not publicy available)

  * isoviewer: h264 track: show nal types,
  * isoviewer: show array sizes
  * added segment type box
  * reproduce AvcConfigurationBox even if reserved bytes are not set to 1

10.May.12: isoparser-1.0-RC-7 (not publicy available)


  * DefaultMp4Builder & FragmentedMp4Builder: make tkhd & mvhd version 1 to support long values
  * Smooth Streaming: Multiple video frame rates in one asset
  * Smooth Streaming: Multiple audo sample rates in one asset
  * Smooth Streaming: support for mono, stereo and 5.1
  * Fragmented H264 Tracks: Main/High Profile now works correctly. (ctts was wrong)
  * H264TrackImpl: Ability to mux raw H264 tracks into MP4
  * I changed the versioning scheme of the isoviewer. It is now always the version number of the isoparser plus one. isoparser-1.0-RC-7 -> isoviewer-2.0-RC-7


20.Apr.12: isoparser-1.0-RC-2 released

  * added tmcd time code sample entry (apple stuff)
  * Enhanced AvcConfigurationBox to support latest spec revision
  * Added Ac3TrackImpl & Ec3TrackImpl -> Ability to mux (E)AC3 into Mp4
  * corrected sample duration calculation in FragmentedMp4Builder
  * Track/Movie level API now supports Sub Sample Information Boix


24.Mar.12: isoparser-1.0-RC-1 released

This release is the single most important release the isoparser has ever seen. The crappy IsoBufferWrapper interface and IsoOutputStream are vanished. All I/O is done via Buffers and Channels (Java NIO). With the help of aspect4j a file is first parsed just coarsely (box length and type)  and whenever you access a box it is parsed on demand. Boxes larger than 1 MB are not read into memory they are mapped into the memory.
All in all the read/write performance has - depending on the measurement - been improved by the factor of 5.

A cool new example is a Microsoft Smooth Streaming example! You can create and stream Microsoft Smooth Streaming streams without any MS tool. You just need the isoparser and an HTTP server of your choice. Anyone interested or is it just an intellectual bauble?

11.Mar.12: isoviewer-2.0-SNAPSHOT uploaded

I just uploaded a new version of the isoviewer. It is based on the new [API](https://groups.google.com/forum/?fromgroups#!topic/mp4parser-discussion/1ZAwWInV5rY).  It is marked as 2.0-SNAPSHOT in the header, the old one (1.2-SNAPSHOT) is still there since it is really brand new.
The performance should be much better. Try it and tell me.

6.Mar.12: isoparser-1.0-beta-6 released

  * sync samples were wrong when using append track
  * add sampleInfoSize calulation for correct saiz usage
  * getter for hdlr box
  * added toString and getters on several classes
  * made usable on android
  * some work with amf0 tracks (flash stuff)
  * scannotation has been removed from the dependencies

9.Jan.12: isoparser-1.0-beta-5 released

  * Change: BoxContainer#getClass now also returns subclasses
  * Added MS TfrfBox
  * Added MS Tfxd box
  * Added TrackEncryptionBox ('tenc' & 'uuid' variant)
  * Added SampleEncryptionBox

22.Nov.11: Iso Viewer 1.2 released

No substantial change over 1.2-SNAPSHOT. Using latest beta-4 release of isoparser as base.

21.Nov.11: isoparser-1.0-beta-4 released

It was time for a release. I added a lot of stuff without providing a release of the new features.

Changes:

  * Added ItemDataBox, PiffSampleEncryptionBox ('uuid' )
  * Added ability to keep uuid box to class references in isoparser-default.properties
  * Added TrickPlayBox ('trik') and ESDescriptorBox ('esds')
  * Added AVCNalUnitStorageBox ('avcn') and BaseLocationBox ('bloc')
  * Added AssetInformationBox ('ainf')
  * Added SampleAuxiliaryInformationOffsetsBox ('saio') and SampleAuxiliaryInformationSizesBox ('saiz')
  * Tweaked memory consumption especially when parsing a file with raw audio
  * Added XmlBox ('xml ')
  * Added CompositionShiftLeastGreatestAtom ('cslg')
  * Made IsoBufferWrapper an interface and added various implementations
  * Made MetaBox sensible to the different definitions (apple handles it as full box, iso as box (or the other way round - don't remember))


---

4.Nov.11: new isoviewer snapshot.

  * Application now restores its size, position, last opened file and last opened boxes
  * Application has an icon and shows opened file in title
  * ESDescriptors are now parsed and shown (isoparser feature)
  * ftyp shows subtypes (again)
  * Due to annotation parsing this release is around 1.2MB. I don't think this kind of annotation processing is necessary. I think I will remove it and rather list all required classes.
  * Tweaked memory consumption for tracks with fixed sample size (e.g. raw audio)