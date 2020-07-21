public class StreamingClient extends Application implements GrabberListener

    public static void main(String[] args)
    {
        launch(args);
    }

    private Stage primaryStage;
    private ImageView imageView;

    private SimplePlayer simplePlayer;

    @Override
    public void start(Stage stage) throws Exception
    {
        String source = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov"; // the video is weird for 1 minute then becomes stable

        primaryStage = stage;
        imageView = new ImageView();

        StackPane root = new StackPane();

        root.getChildren().add(imageView);
        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());

        Scene scene = new Scene(root, 640, 480);

        primaryStage.setTitle("Streaming Player");
        primaryStage.setScene(scene);
        primaryStage.show();

        simplePlayer = new SimplePlayer(source, this);
    }

    @Override
    public void onMediaGrabbed(int width, int height)
    {
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
    }

    @Override
    public void onImageProcessed(Image image)
    {
        LogHelper.e(TAG, "image: " + image);

        Platform.runLater(() -> {
            imageView.setImage(image);
        });
    }

    @Override
    public void onPlaying() {}

    @Override
    public void onGainControl(FloatControl gainControl) {}

    @Override
    public void stop() throws Exception
    {
        simplePlayer.stop();
    }
}