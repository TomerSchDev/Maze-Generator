import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * The type Maze.
 */
public class Maze {
    /**
     * The Rows.
     */
    final private int ROWS = Math.round(Main.HEIGHT / Main.SIZE);
    /**
     * The Columns.
     */
    final private int COLUMNS = Math.round(Main.WIDTH / Main.SIZE);
    /**
     * The Cells.
     */
    private Cell[][] cells;
    /**
     * The Visited cells.
     */
    private Stack<Cell>[] visitedCells;
    /**
     * The Checking cells.
     */
    private Cell[] checkingCells;
    /**
     * The Visited colors.
     */
    private Color[] visitedColors;
    /**
     * The Using color.
     */
    private Color[] usingColor;
    /**
     * The Connecting lines.
     */
    private List<Line>[] connectingLines;

    /**
     * Instantiates a new Maze.
     */
    public Maze() {
        this.cells = new Cell[ROWS][COLUMNS];
        this.visitedCells = new Stack[Main.NUMBER_OF_PATHS];
        this.checkingCells = new Cell[Main.NUMBER_OF_PATHS];
        this.visitedColors = new Color[Main.NUMBER_OF_PATHS];
        this.usingColor = new Color[Main.NUMBER_OF_PATHS];
        this.connectingLines = new List[Main.NUMBER_OF_PATHS];
        initMaze();

    }

    /**
     * Init maze.
     */
    private void initMaze() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                cells[i][j] = new Cell((i * Main.SIZE), (j * Main.SIZE), Main.SIZE);
            }
        }
        Random rnd = new Random();
        for (int i = 0; i < checkingCells.length; i++) {
            checkingCells[i] = cells[rnd.nextInt(ROWS)][rnd.nextInt(COLUMNS)];
            checkingCells[i].setState(1);
            int r = rnd.nextInt(155) + 100;
            int g = rnd.nextInt(155) + 100;
            int b = rnd.nextInt(155) + 100;
            visitedColors[i] = new Color(r, g, b);
            usingColor[i] = new Color(r - 100, g - 100, b - 100);
            visitedCells[i] = new Stack<>();
            connectingLines[i] = new ArrayList<>();
            checkingCells[i].setColors(visitedColors[i], usingColor[i]);
        }
    }

    /**
     * Neighbors cell [ ].
     *
     * @param cell the cell
     *
     * @return the cell [ ]
     */
    public Cell[] neighbors(Cell cell) {
        int row = 0;
        int column = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (cell.equals(this.cells[i][j])) {
                    row = i;
                    column = j;
                    break;
                }
            }
        }
        int upRow = row - 1;
        int rightColumn = column + 1;
        int downRow = row + 1;
        int leftColumn = column - 1;
        Cell[] cells = new Cell[4];
        if (upRow < 0) {
            cells[0] = null;
        } else {
            cells[0] = this.cells[upRow][column];
        }
        if (rightColumn >= COLUMNS) {
            cells[1] = null;
        } else {
            cells[1] = this.cells[row][rightColumn];
        }
        if (downRow >= ROWS) {
            cells[2] = null;
        } else {
            cells[2] = this.cells[downRow][column];
        }
        if (leftColumn < 0) {
            cells[3] = null;
        } else {
            cells[3] = this.cells[row][leftColumn];
        }
        return cells;

    }

    /**
     * Render.
     *
     * @param g the g
     */
    public void render(Graphics g) {
        if (Main.IS_SHOWING_LINES) {
            for (int i = 0; i < Main.NUMBER_OF_PATHS; i++) {
                for (int j = 0; j < connectingLines[i].size(); j++) {
                    connectingLines[i].get(j).drawLine(g);
                }
            }
        }
        if (Main.IS_SHOWING_CELLS) {
            for (Cell[] rowCell : cells) {
                for (Cell c : rowCell) {
                    if (c.getState() != 0) {
                        c.renderCell(g);
                    }
                }
            }
        }
    }

    /**
     * Update.
     */
    public void update() {
        boolean isGameFinished = true;
        for (int i = 0; i < Main.NUMBER_OF_PATHS; i++) {
            if (checkingCells[i] == null) {
                continue;
            }
            isGameFinished = false;
            checkingCells[i].setState(2);
            Cell nextCell = checkingCells[i].findPath(neighbors(checkingCells[i]));
            int size = Main.SIZE;
            if (nextCell != null) {
                Line move = new Line(checkingCells[i].getX() + size / 2, checkingCells[i].getY() + size / 2,
                                     nextCell.getX() + size / 2, nextCell.getY() + size / 2, visitedColors[i]
                );
                nextCell.setState(1);
                nextCell.setColors(this.visitedColors[i], this.usingColor[i]);
                checkingCells[i] = nextCell;
                connectingLines[i].add(move);
                visitedCells[i].push(checkingCells[i]);
            } else {
                if (!visitedCells[i].isEmpty()) {
                    checkingCells[i] = visitedCells[i].pop();
                    checkingCells[i].setState(1);
                } else {
                    checkingCells[i] = null;
                }
            }

        }
        if (isGameFinished && Main.IS_RESTART_AUTO) {
            this.initMaze();
        }
    }
}
