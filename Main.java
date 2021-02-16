import java.awt.Color;
import java.awt.Graphics;

/**
 * The type Main.
 */
public class Main {
    /**
     * The constant HEIGHT.
     */
    final static int HEIGHT = 600;
    /**
     * The constant WIDTH.
     */
    final static int WIDTH = 800;
    /**
     * The constant SIZE.
     */
    final static int SIZE = 10;
    /**
     * The constant FPS.
     */
    final static int FPS = 60;

    /**
     * The constant NUMBER_OF_PATHS.
     */
    final static int NUMBER_OF_PATHS = 5;
    /**
     * The constant SCREEN_COLOR.
     */
    final static Color SCREEN_COLOR = Color.WHITE;
    /**
     * The constant IS_SHOWING_CELLS.
     */
    final static boolean IS_SHOWING_CELLS = true;
    /**
     * The constant IS_SHOWING_LINES.
     */
    final static boolean IS_SHOWING_LINES = false;
    /**
     * The constant IS_RESTART_AUTO.
     */
    final static boolean IS_RESTART_AUTO = false;
    /**
     * The constant maze.
     */
    private static Maze maze;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        maze = new Maze();
        Display display = new Display(WIDTH, HEIGHT, "Generated Maze");
        display.run();
    }

    /**
     * Render maze.
     *
     * @param g the g
     */
    public static void renderMaze(Graphics g) {
        maze.render(g);
    }

    /**
     * Update game.
     */
    public static void updateGame() {
        maze.update();
    }


}
