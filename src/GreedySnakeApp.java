import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class GreedySnakeApp {
    private static VideoCapture cap;
    private static HttpStreamServer httpStreamService;
    private static ChessboardDetector detector;

    public static void main(String[] args) throws Exception {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // opencv open camera
        cap = new VideoCapture(0);

        // init http server
        Mat frame = new Mat();
        httpStreamService = new HttpStreamServer(frame);
        new Thread(httpStreamService).start();

        // init detector
        detector = new ChessboardDetector();

        // show camera frame
        while (cap.read(frame)) {
            Point center = new Point();
            Boolean found = detector.detectCenter(frame, center);
            if (found) {
                System.out.println("center: " + center);
                Imgproc.circle(frame, center, 5, new Scalar(0, 255, 0), -1);
            }

            httpStreamService.imag = frame;
        }
    }
}
