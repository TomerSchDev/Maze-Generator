import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cell {
    private int y;
    private int x;
    private int size;
    private boolean isVisited;
    private Line[] walls;
    private int[] drawWall;
    private Color visited;
    private Color usingNow;
    private boolean isUsingNow;


    public Cell(int row, int column, int size) {
        this.y = row;
        this.x = column;
        this.size = size;
        this.isVisited = false;
        this.isUsingNow = false;
        Line up = new Line(this.x, this.y, this.x + size, this.y, java.awt.Color.BLACK);
        Line right = new Line(this.x + size, this.y, this.x + size, this.y + size, java.awt.Color.BLACK);
        Line down = new Line(this.x, this.y + size, this.x + size, this.y + size, java.awt.Color.BLACK);
        Line left = new Line(this.x, this.y, this.x, this.y + size, java.awt.Color.BLACK);
        this.walls = new Line[] {up, right, down, left};
        this.drawWall = new int[] {
                0, 0, 0, 0
        };

    }

    public void drawOn(Graphics g) {
        if (isVisited&&!isUsingNow) {
            g.setColor(visited);
            g.fillRect(x, y, size, size);
        }
        for (int i = 0; i < 4; i++) {
            if (drawWall[i] == 1) {
                walls[i].drawOn(g);
            }
        }
    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }

    public void setVisited(Color color) {
        this.visited = color;
    }

    public void setUsingNow(Color color) {
        this.usingNow = color;
    }

    public int howManyOpenWalls() {
        int sum = 0;
        for (int i = 0; i < drawWall.length; i++) {
            sum += drawWall[i];
        }
        return sum;
    }

    public Cell findPath(Cell[] neighbors) {
        List<Cell> neighborsNotListed = new ArrayList<>();
        for (Cell neighbor : neighbors) {
            if (neighbor != null && !neighbor.isVisited && neighbor.howManyOpenWalls() < 3) {
                neighborsNotListed.add(neighbor);
            }
        }
        if (neighborsNotListed.isEmpty()) {
            return null;
        }
        for (int i = 0; i < 4; i++) {
            if (drawWall[i] != 0) {
                continue;
            }
            drawWall[i] = 1;
        }
        Random random = new Random();
        int nextCell = random.nextInt(neighborsNotListed.size());
        Cell next = neighborsNotListed.get(nextCell);
        for (int i = 0; i < neighbors.length; i++) {
            if (next.equals(neighbors[i])) {
                drawWall[i] = 2;
                int[] nextWall = new int[] {0, 0, 0, 0};
                nextWall[(i + 2) % 4] = 2;
                next.setDrawWall(nextWall);
            }
        }
        return next;
    }

    public Cell setVisited(boolean visited) {
        isVisited = visited;
        return this;
    }

    public Cell setDrawWall(int[] drawWall) {
        this.drawWall = drawWall;
        return this;
    }

    public void fillOn(Graphics g) {
        g.setColor(usingNow);
        g.fillRect(x, y, size, size);
    }

    public void setUsingNow(boolean b) {
        this.isUsingNow = b;
    }
}
