import org.opencv.core.Point;

public class Food {
    public Point position;

    private static final int IMG_WIDTH = 640;
    private static final int IMG_HEIGHT = 480;

    private static final int LEFT_BOUND = 150;
    private static final int RIGHT_BOUND = IMG_WIDTH - 150;
    private static final int TOP_BOUND = 150;
    private static final int BOTTOM_BOUND = IMG_HEIGHT - 150;

    public Food() {
        position = new Point(
            (int) (Math.random() * (RIGHT_BOUND - LEFT_BOUND)) + LEFT_BOUND,
            (int) (Math.random() * (BOTTOM_BOUND - TOP_BOUND)) + TOP_BOUND
        );
    }
}
