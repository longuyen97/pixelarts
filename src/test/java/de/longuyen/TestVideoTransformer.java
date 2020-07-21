package de.longuyen;

import org.junit.jupiter.api.Test;
import ws.schild.jave.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public class TestVideoTransformer {
    @Test
    void test() throws EncoderException, URISyntaxException {
        File source = new File(TestVideoTransformer.class.getResource("/take_on_me.mp4").toURI());
        File target = new File("target/target.mp4");

        /* Step 2. Set Audio Attrributes for conversion*/
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("aac");
// here 64kbit/s is 64000
        audio.setBitRate(64000);
        audio.setChannels(2);
        audio.setSamplingRate(44100);

        /* Step 3. Set Video Attributes for conversion*/
        VideoAttributes video = new VideoAttributes();
        video.setCodec("h264");
        video.setX264Profile(VideoAttributes.X264_PROFILE.BASELINE);
// Here 160 kbps video is 160000
        video.setBitRate(160000);
// More the frames more quality and size, but keep it low based on devices like mobile
        video.setFrameRate(15);
        video.setSize(new VideoSize(400, 300));

        /* Step 4. Set Encoding Attributes*/
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp4");
        attrs.setAudioAttributes(audio);
        attrs.setVideoAttributes(video);

        /* Step 5. Do the Encoding*/
        Encoder encoder = new Encoder();
        encoder.encode(List.of(new MultimediaObject(source)), target, attrs);
    }
}
