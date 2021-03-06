import java.util.Random;

public class Maze {
    private int n;                 
    private boolean[][] north;    
    private boolean[][] east;
    private boolean[][] south;
    private boolean[][] west;
    private boolean[][] visited; 
    private boolean done = false;

    public Maze(int n) {
        this.n = n;
        StdDraw.setCanvasSize(1300,1300);
        StdDraw.setXscale(0, n+2);
        StdDraw.setYscale(0, n+2);
        init();
        generate();
    }

    private void init() {

        visited = new boolean[n+2][n+2];
        for (int x = 0; x < n+2; x++) {
            visited[x][0] = true;
            visited[x][n+1] = true;
        }
        for (int y = 0; y < n+2; y++) {
            visited[0][y] = true;
            visited[n+1][y] = true;
        }

        north = new boolean[n+2][n+2];
        east  = new boolean[n+2][n+2];
        south = new boolean[n+2][n+2];
        west  = new boolean[n+2][n+2];
        for (int x = 0; x < n+2; x++) {
            for (int y = 0; y < n+2; y++) {
                north[x][y] = true;
                east[x][y]  = true;
                south[x][y] = true;
                west[x][y]  = true;
            }
        }
    }


    private void generate(int x, int y) {
        visited[x][y] = true;

        while (!visited[x][y+1] || !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y]) {

            while (true) {
                double r = new Random().nextInt(4);
                if (r == 0 && !visited[x][y+1]) { 
                    north[x][y] = false;
                    south[x][y+1] = false;
                    generate(x, y + 1);
                    break;
                }else if (r == 1 && !visited[x+1][y]) {
                    east[x][y] = false;
                    west[x+1][y] = false;
                    generate(x+1, y);
                    break;
                }else if (r == 2 && !visited[x][y-1]) {
                    south[x][y] = false;
                    north[x][y-1] = false;
                    generate(x, y-1);
                    break;
                }else if (r == 3 && !visited[x-1][y]) { 
                    west[x][y] = false;
                    east[x-1][y] = false;
                    generate(x-1, y);
                    break;
                }
            }
        }
    }

    private void generate() {
        generate(1, 1);
    }


    public void findSolution() {
        for (int x = 1; x <= n; x++)
            for (int y = 1; y <= n; y++)
                visited[x][y] = false;
        done = false;
        findSolution(1, n);
    }


    private void findSolution(int x, int y) {
        if (done || visited[x][y]) return; 

        visited[x][y] = true;

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();


        if (x == n && y == 1) done = true;

        if (!south[x][y]) findSolution(x, y - 1);
        if (!east[x][y])  findSolution(x + 1, y);
        if (!north[x][y]) findSolution(x, y + 1);
        if (!west[x][y])  findSolution(x - 1, y);

        if (done) return;

        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
    }

    public void drawLabirynth(){
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledCircle(1 + 0.5, n + 0.5, 0.375);
        StdDraw.filledCircle(n + 0.5, 1.5, 0.375);

        StdDraw.setPenColor(StdDraw.BLACK);
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                if (south[x][y]) StdDraw.line(x, y, x+1, y);
                if (north[x][y]) StdDraw.line(x, y+1, x+1, y+1);
                if (west[x][y])  StdDraw.line(x, y, x, y+1);
                if (east[x][y])  StdDraw.line(x+1, y, x+1, y+1);
            }
        }
        StdDraw.show();
        StdDraw.pause(50);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Maze maze = new Maze(n);
        StdDraw.enableDoubleBuffering();
        maze.drawLabirynth();
        maze.findSolution();
    }
}
