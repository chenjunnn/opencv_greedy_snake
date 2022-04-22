import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class GreedySnakeApp {
    private static VideoCapture cap;
    private static HttpStreamServer http_ss;
    private static ChessboardDetector detector;
    private static Snake snake;
    private static Food food;
    private static boolean initailized = false;

    public static void main(String[] args) throws Exception {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // opencv open camera
        cap = new VideoCapture(0);

        // init http server
        Mat frame = new Mat();
        http_ss = new HttpStreamServer(frame);
        new Thread(http_ss).start();

        // init detector
        detector = new ChessboardDetector();

        // init snake
        final var RADIUS = 10;
        snake = new Snake(RADIUS);

        // init food
        food = new Food();

        // show camera frame
        while (cap.read(frame)) {
            Point chessboardCenter = new Point();
            Boolean found = detector.detectCenter(frame, chessboardCenter);
            if (found) {
                if (!initailized) {
                    // init snake body
                    // head
                    snake.points.addFirst(chessboardCenter);
                    // second point
                    chessboardCenter.x -= RADIUS;
                    snake.points.addFirst(chessboardCenter);
                    // third point
                    chessboardCenter.x -= RADIUS;
                    snake.points.addFirst(chessboardCenter);

                    initailized = true;
                    continue;
                }

                if (snake.canMoveTo(chessboardCenter)) {
                    snake.moveTo(chessboardCenter);
                }

                if (snake.canEat(food.position)) {
                    snake.eat(food.position);
                    // generate new food
                    food = new Food();
                }
            }

            // Draw snake
            for (var point : snake.points) {
                Imgproc.circle(frame, point, RADIUS, new Scalar(0, 255, 0), -1);
            }

            // Draw food
            Imgproc.circle(frame, food.position, RADIUS, new Scalar(0, 0, 255), -1);

            // flip image
            Core.flip(frame, frame, 1);

            // send frame to http server
            http_ss.imag = frame.clone();
        }
    }
}
