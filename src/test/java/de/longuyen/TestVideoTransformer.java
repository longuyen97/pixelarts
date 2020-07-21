package de.longuyen;

import org.junit.jupiter.api.Test;
import ws.schild.jave.*;

import java.io.File;

public class TestVideoTransformer {
    @Test
    void test() throws EncoderException {
        File source = new File("source.avi");
        File target = new File("target.wav");
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("pcm_s16le");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("wav");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        encoder.encode(new MultimediaObject(source), target, attrs);
    }
}
