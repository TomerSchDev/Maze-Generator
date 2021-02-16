import java.awt.*;

public class Rectangle {
    private int x;
    private int y;
    private int size;

    public Rectangle(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }
    public void drawOn(biuoop.DrawSurface drawSurface,Color color){
        drawSurface.setColor(color);
        drawSurface.fillRectangle(x,y,size,size);
    }
}
