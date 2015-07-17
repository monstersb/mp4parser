# Introduction #

The default deployment for MS Smooth Streaming requires an IIS. With some tricks we can use a default Apache (or even this SVN) without any configuration hassle.


# Preparing the content #

Let's start with content that is publicy available. Download the good old [Big Buck Bunny](http://www.bigbuckbunny.org/index.php/download/).

I use the version big\_buck\_bunny\_720p\_surround.avi in my example (and I took most of the example from the [Codeshop](http://smoothstreaming.code-shop.com/trac/wiki/Smooth-Streaming-Encoding-FFmpeg) guys).



## Creating the qualities ##

video:

This probably not the best way to encode the video but I'm a container guy - not an encoder guy - plus I cannot really use the work done by my co-worker Christian without asking I'm just using the most simple way of creating the files.

This creates the first pass log file.
```
x264 -p 1 -B 400 big_buck_bunny_720p_surround.avi statsfile
```

These commands use the first pass statistics to make sure that the I-frames are all on the same position.
```
x264 -p 2 -B 100 big_buck_bunny_720p_surround.avi -o big_buck_bunny_100.mp4
x264 -p 2 -B 200 big_buck_bunny_720p_surround.avi -o big_buck_bunny_200.mp4
x264 -p 2 -B 400 big_buck_bunny_720p_surround.avi -o big_buck_bunny_400.mp4
x264 -p 2 -B 800 big_buck_bunny_720p_surround.avi -o big_buck_bunny_800.mp4
x264 -p 2 -B 1200 big_buck_bunny_720p_surround.avi -o big_buck_bunny_1200.mp4
```

audio:

```
ffmpeg -i big_buck_bunny_720p_surround.avi -vn -acodec aac -ac 2  -strict experimental  big_buck_bunny_audio_2.mp4
```

for audio I only use one quality. The player and the fragmenter tool allow more than one audio quality.


# Fragmenting the content #

Download the smooth-streaming-fragmenter from the [download area](http://code.google.com/p/mp4parser/downloads/list).

start the fragmentation with:
```
java -jar smooth-streaming-fragmenter-[version].jar big_buck_bunny*.mp4
```

the result will be in the newly created 'smooth' directory. You could set the output directory via -o command line option.


# Streaming from google code's SVN #

I now commit all files in the smooth directory to http://mp4parser.googlecode.com/svn/smooth/big_buck_bunny/ and create an HTML page at http://mp4parser.googlecode.com/svn/smooth/gcode.html  - Smooth Streaming from SVN! Isn't that cool?