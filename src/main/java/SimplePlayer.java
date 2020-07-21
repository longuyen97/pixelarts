import javafx.animation.AnimationTimer;

public class SimplePlayer {
    private static volatile Thread playThread;
    private SourceDataLine soundLine;

    private int counter;

    public SimplePlayer(String source) {
        if (source.isEmpty()) return;

        counter = 0;

        playThread = new Thread(() -> {
            try {
                FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(source);
                grabber.start();

                grabberListener.onMediaGrabbed(grabber.getImageWidth(), grabber.getImageHeight());

                if (grabber.getSampleRate() > 0 && grabber.getAudioChannels() > 0) {
                    AudioFormat audioFormat = new AudioFormat(grabber.getSampleRate(), 16, grabber.getAudioChannels(), true, true);

                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                    soundLine = (SourceDataLine) AudioSystem.getLine(info);
                    soundLine.open(audioFormat);
                    soundLine.start();
                }

                Java2DFrameConverter converter = new Java2DFrameConverter();

                while (!Thread.interrupted()) {
                    Frame frame = grabber.grab();
                    if (frame == null) {
                        break;
                    }
                    if (frame.image != null) {

                        Image image = SwingFXUtils.toFXImage(converter.convert(frame), null);
                        Platform.runLater(() -> {
                            grabberListener.onImageProcessed(image);
                        });
                    } else if (frame.samples != null) {
                        ShortBuffer channelSamplesFloatBuffer = (ShortBuffer) frame.samples[0];
                        channelSamplesFloatBuffer.rewind();

                        ByteBuffer outBuffer = ByteBuffer.allocate(channelSamplesFloatBuffer.capacity() * 2);

                        for (int i = 0; i < channelSamplesFloatBuffer.capacity(); i++) {
                            short val = channelSamplesFloatBuffer.get(i);
                            outBuffer.putShort(val);
                        }
                    }
                }
                grabber.stop();
                grabber.release();
            } catch (Exception exception) {
                System.exit(1);
            }
        });
        playThread.start();
    }

    public void stop() {
        playThread.interrupt();
    }
}