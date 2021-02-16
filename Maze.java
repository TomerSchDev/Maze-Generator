import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Maze {
    final private int ROWS = Math.round(Main.HEIGHT / Main.SIZE);
    final private int COLUMNS = Math.round(Main.WIDTH / Main.SIZE);
    private Cell[][] cells;
    private Stack<Cell>[] visitedCells;
    private Cell[] checkingCells;
    private Color[] visitedColors;
    private Color[] usingColor;
    private List<Line>[] connectingLines;
    public boolean isGameFinished;

    public Maze() {
        this.cells = new Cell[ROWS][COLUMNS];
        this.visitedCells = new Stack[Main.NUMBER_OF_PATHS];
        this.checkingCells = new Cell[Main.NUMBER_OF_PATHS];
        this.visitedColors = new Color[Main.NUMBER_OF_PATHS];
        this.usingColor = new Color[Main.NUMBER_OF_PATHS];
        this.connectingLines = new List[Main.NUMBER_OF_PATHS];
        this.isGameFinished = false;
        initMaze();

    }

    private void initMaze() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                cells[i][j] = new Cell((i * Main.SIZE), (j * Main.SIZE), Main.SIZE);
            }
        }
        Random rnd = new Random();
        for (int i = 0; i < checkingCells.length; i++) {
            checkingCells[i] = cells[rnd.nextInt(ROWS)][rnd.nextInt(COLUMNS)];
            checkingCells[i].setVisited(true);
            int r = rnd.nextInt(155) + 100;
            int g = rnd.nextInt(155) + 100;
            int b = rnd.nextInt(155) + 100;
            visitedColors[i] = new Color(r, g, b);
            usingColor[i] = new Color(r - 100, g - 100, b - 100);
            visitedCells[i] = new Stack<>();
            connectingLines[i] = new ArrayList<>();
        }
    }

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

    public void render(Graphics g) {
        this.isGameFinished = true;
        if (Main.IS_SHOWING_CELLS) {
            for (int i = 0; i < Main.NUMBER_OF_PATHS; i++) {
                if (checkingCells[i] == null) {
                    continue;
                }
                isGameFinished = false;
                checkingCells[i].setVisited(visitedColors[i]);
                checkingCells[i].setUsingNow(usingColor[i]);
                if (Main.IS_SHOWING_CELLS) {
                    checkingCells[i].fillOn(g);
                }
                if (Main.IS_SHOWING_LINES) {
                    for (int j = 0; j < connectingLines[i].size(); j++) {
                        connectingLines[i].get(j).drawOn(g);
                    }
                }
            }
            for (Cell[] rowCell : cells) {
                for (Cell c : rowCell) {
                    c.drawOn(g);
                }
            }
        }
    }

    public void update() {
        for (int i = 0; i < Main.NUMBER_OF_PATHS; i++) {
            if (checkingCells[i] != null) {
                Cell nextCell = checkingCells[i].findPath(neighbors(checkingCells[i]));
                int size = Main.SIZE;
                if (nextCell != null) {
                    Line move = new Line(checkingCells[i].getX() + size / 2, checkingCells[i].getY() + size / 2,
                                         nextCell.getX() + size / 2, nextCell.getY() + size / 2, visitedColors[i]
                    );
                    nextCell.setVisited(true);
                    nextCell.setUsingNow(true);;
                    checkingCells[i].setUsingNow(false);
                    checkingCells[i] = nextCell;
                    connectingLines[i].add(move);
                    visitedCells[i].push(checkingCells[i]);
                } else {
                    if (!visitedCells[i].isEmpty()) {
                        checkingCells[i] = visitedCells[i].pop();
                    } else {
                        checkingCells[i] = null;
                    }
                }
            }
        }
        if (this.isGameFinished && Main.IS_RESTART_AUTO) {
            this.initMaze();
        }
    }
}
