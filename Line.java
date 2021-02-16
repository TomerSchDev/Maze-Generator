import java.awt.Color;
import java.awt.Graphics;

/**
 * The type Line.
 */
public class Line {
    /**
     * The X 1.
     */
    private int x1;
    /**
     * The X 2.
     */
    private int x2;
    /**
     * The Y 1.
     */
    private int y1;
    /**
     * The Y 2.
     */
    private int y2;
    /**
     * The Color of the line.
     */
    private Color color;

    /**
     * Instantiates a new Line.
     *
     * @param x1    the x 1
     * @param y1    the y 1
     * @param x2    the x 2
     * @param y2    the y 2
     * @param color the color
     */
    public Line(int x1, int y1, int x2, int y2, Color color) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
    }

    /**
     * Draw line.
     *
     * @param g the graphics that draw the line
     */
    public void drawLine(Graphics g) {
        g.setColor(color);
        g.drawLine(this.x1, this.y1, this.x2, this.y2);
    }
}
