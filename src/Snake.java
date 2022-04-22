import java.util.ArrayDeque;
import org.opencv.core.Point;

public class Snake {

    private final int RADIUS;
    public ArrayDeque<Point> points;

    public Snake(int radius) {
        RADIUS = radius;
        points = new ArrayDeque<Point>();
    }

    public boolean canMoveTo(Point point) {
        var diff = calDist2Head(point);
        return diff >= RADIUS;
    }

    public boolean canEat(Point point) {
        var diff = calDist2Head(point);
        return diff <= 2 * RADIUS;
    }

    public void moveTo(Point point) {
        points.addFirst(point.clone());
        points.removeLast();
    }

    public void eat(Point point) {
        points.addLast(points.getLast().clone());
    }

    public void eatSelf() {
        boolean eaten = false;
        var iter = points.iterator();
        // skip head
        iter.next();
        while (iter.hasNext()) {
            var point = iter.next();
            if (!eaten) {
                if (calDist2Head(point) < RADIUS / 2) {
                    eaten = true;
                    iter.remove();
                }
            } else {
                iter.remove();
            }
        }
    }

    private double calDist2Head(Point point) {
        var diff_x = points.getFirst().x - point.x;
        var diff_y = points.getFirst().y - point.y;
        var diff = Math.sqrt(diff_x * diff_x + diff_y * diff_y);

        return diff;
    }
}
