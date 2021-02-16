import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Cell.
 */
public class Cell {
    /**
     * The Y.
     */
    private int y;
    /**
     * The X.
     */
    private int x;
    /**
     * The Size.
     */
    private int size;
    /**
     * The Walls.
     */
    private Line[] walls;
    /**
     * The Draw wall.
     */
    private int[] drawWall;
    /**
     * The Visited color.
     */
    private Color visitedColor;
    /**
     * The Using now color.
     */
    private Color usingNowColor;
    /**
     * The State.
     */
    private int state;


    /**
     * Instantiates a new Cell.
     *
     * @param row    the row
     * @param column the column
     * @param size   the size
     */
    public Cell(int row, int column, int size) {
        this.y = row;
        this.x = column;
        this.size = size;
        Line up = new Line(this.x, this.y, this.x + size, this.y, java.awt.Color.BLACK);
        Line right = new Line(this.x + size, this.y, this.x + size, this.y + size, java.awt.Color.BLACK);
        Line down = new Line(this.x, this.y + size, this.x + size, this.y + size, java.awt.Color.BLACK);
        Line left = new Line(this.x, this.y, this.x, this.y + size, java.awt.Color.BLACK);
        this.walls = new Line[] {up, right, down, left};
        this.drawWall = new int[] {
                0, 0, 0, 0
        };
        this.state = 0;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public int getState() {
        return this.state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     *
     * @return the state
     */
    public Cell setState(int state) {
        this.state = state;
        return this;
    }

    /**
     * Draw on.
     *
     * @param g the g
     */
    public void renderCell(Graphics g) {
        switch (this.state) {
            case (2):
                g.setColor(visitedColor);
                break;
            case (1):
                g.setColor(usingNowColor);
                break;
            default:
                g.setColor(Main.SCREEN_COLOR);
                break;
        }
        g.fillRect(this.x, this.y, this.size, this.size);
        for (int i = 0; i < 4; i++) {
            if (drawWall[i] == 1) {
                walls[i].drawLine(g);
            }
        }
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        int y = this.y;
        return y;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        int x = this.x;
        return x;
    }

    /**
     * Sets colors.
     *
     * @param visitedColor  the visited color
     * @param usingNowColor the using now color
     */
    public void setColors(Color visitedColor, Color usingNowColor) {
        this.visitedColor = visitedColor;
        this.usingNowColor = usingNowColor;
    }

    /**
     * How many open walls int.
     *
     * @return the int
     */
    public int howManyOpenWalls() {
        int sum = 0;
        for (int i = 0; i < drawWall.length; i++) {
            sum += drawWall[i];
        }
        return sum;
    }

    /**
     * Find path cell.
     *
     * this method decide for each cell with path to choose
     * @param neighbors the neighbors
     *
     * @return the cell
     */
    public Cell findPath(Cell[] neighbors) {
        List<Cell> neighborsNotListed = new ArrayList<>();
        for (Cell neighbor : neighbors) {//gets all the neighbors that he can chose
            if (neighbor != null && neighbor.state == 0 && neighbor.howManyOpenWalls() < 3) {
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

    /**
     * Sets draw wall.
     *
     * @param drawWall the draw wall
     *
     * @return the draw wall
     */
    public Cell setDrawWall(int[] drawWall) {
        this.drawWall = drawWall;
        return this;
    }

}
