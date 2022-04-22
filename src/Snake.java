import java.util.ArrayDeque;
import org.opencv.core.Point;

public class Snake {

    private final int RADIUS;
    public Point head;
    public ArrayDeque<Point> points;

    public Snake(int radius) {
        RADIUS = radius;
        head = new Point();
        points = new ArrayDeque<Point>();
    }

    public boolean canMoveTo(Point point) {
        var diff = calDist2Head(point);
        return diff >= 2 * RADIUS;
    }

    public void moveTo(Point point) {
        head = point;
        points.addFirst(point.clone());
        points.removeLast();
    }

    public void eatPoint(Point point) {
        head = point;
        points.addFirst(point.clone());
    }

    public boolean eatSelf() {
        for (var point : points) {
            if (point == head) {
                continue;
            }

            if (calDist2Head(point) < RADIUS) {
                return true;
            }
        }

        return false;
    }

    private double calDist2Head(Point point) {
        var diff_x = head.x - point.x;
        var diff_y = head.y - point.y;
        var diff = Math.sqrt(diff_x * diff_x + diff_y * diff_y);

        return diff;
    }
}
