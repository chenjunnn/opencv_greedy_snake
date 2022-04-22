import org.opencv.calib3d.Calib3d;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class ChessboardDetector {

    public boolean detectCenter(Mat frame, Point center) {
        // convert to gray
        Mat gray = new Mat();
        Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

        // find chessboard corners
        MatOfPoint2f corners = new MatOfPoint2f();
        boolean found = Calib3d.findChessboardCorners(gray, new Size(3, 3), corners);

        if (found) {
            // calculate center
            for (var corner : corners.toList()) {
                center.x += corner.x;
                center.y += corner.y;
            }

            center.x /= corners.toList().size();
            center.y /= corners.toList().size();
        }

        return found;
    }
}
