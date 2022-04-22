import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class GreedySnakeApp {
    private static Snake snake;
    private static VideoCapture cap;
    private static HttpStreamServer http_ss;
    private static ChessboardDetector detector;
    private static boolean initailized = false;

    public static void main(String[] args) throws Exception {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        final var RADIUS = 10;

        // init snake
        snake = new Snake(RADIUS);

        // opencv open camera
        cap = new VideoCapture(0);

        // init http server
        Mat frame = new Mat();
        http_ss = new HttpStreamServer(frame);
        new Thread(http_ss).start();

        // init detector
        detector = new ChessboardDetector();

        // show camera frame
        while (cap.read(frame)) {
            Point chessboardCenter = new Point();
            Boolean found = detector.detectCenter(frame, chessboardCenter);
            if (found) {
                if (!initailized) {
                    snake.points.addFirst(chessboardCenter.clone());
                    snake.points.addFirst(chessboardCenter.clone());
                    initailized = true;
                    continue;
                }

                if (snake.canMoveTo(chessboardCenter)) {
                    snake.moveTo(chessboardCenter);
                }
            }

            // Draw snake
            for (var point : snake.points) {
                Imgproc.circle(frame, point, RADIUS, new Scalar(0, 255, 0), -1);
            }
            http_ss.imag = frame.clone();
        }
    }
}
