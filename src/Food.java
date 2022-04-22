import org.opencv.core.Point;

public class Food {
    public Point position;

    private static final int IMG_WIDTH = 640;
    private static final int IMG_HEIGHT = 480;

    public Food() {
        position = new Point(
            (int) (Math.random() * IMG_WIDTH),
            (int) (Math.random() * IMG_HEIGHT)
        );
    }
}
