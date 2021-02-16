import java.awt.Color;
import java.awt.Graphics;

public class Main {
    final static int HEIGHT = 600;
    final static int WIDTH = 800;
    final static int SIZE = 10;
    final static int FPS = 120;

    final static int NUMBER_OF_PATHS = 5;
    final static Color SCREEN_COLOR = Color.RED;
    final static boolean IS_SHOWING_CELLS = true;
    final static boolean IS_SHOWING_LINES = false;
    final static boolean IS_RESTART_AUTO = true;
    private static Maze maze;

    public static void main(String[] args) {
        maze = new Maze();
        Display display = new Display(WIDTH, HEIGHT, "Generated Maze");
        display.run();
    }

    public static void renderMaze(Graphics g) {
        maze.render(g);
    }

    public static void updateGame() {
        maze.update();
    }


}
