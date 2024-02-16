import java.util.Random;
import java.util.Stack;
import java.util.List;

public class Main{

    public static void main(String []args){
        Maze maze = new Maze();
        maze.generateMaze(10, 12);
        maze.printMaze();


        GraphSearch graphSearch = new DFS();
        List<MazeNode> path = graphSearch.search(maze);
        System.out.println("\nPath Found:");
        for (MazeNode node : path) {
            System.out.print("(" + node.getX() + "," + node.getY() + ") ");
        }
    }
}

class Maze {
    private MazeNode[][] maze;
    private int height;
    private int width;
    private Random random;
    private MazeNode entrance;
    private MazeNode exit;

    public Maze() {
        this.random = new Random();
    }

    public MazeNode getEntrance() {
        return this.entrance;
    }

    public MazeNode getExit() {
        return this.exit;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public MazeNode getNode(int x, int y) {
        return maze[y][x];
    }

    public void generateMaze(int h, int w) {
        this.height = h;
        this.width = w;
        this.maze = new MazeNode[height][width];

        // Initialize the maze with walls
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maze[y][x] = new MazeNode(x, y, true);
            }
        }

        // Define the entrance
        entrance = maze[0][0];
        entrance.setWall(false);
        entrance.setVisited(true);

        // Start carving paths from the entrance
        Stack<MazeNode> stack = new Stack<>();
        stack.push(entrance);
        carvePaths(stack);

        // Define the exit as one of the border nodes that has been visited
        defineExit();

        clearVisited();

        this.maze = maze; //! why?
    }

    private void clearVisited() {
        for(MazeNode[] row: this.maze) {
            for(MazeNode node: row) {
                node.setVisited(false);
            }
        }
    }

    private void carvePaths(Stack<MazeNode> stack) {
        while (!stack.isEmpty()) {
            MazeNode current = stack.pop();
            MazeNode next = getRandomUnvisitedNeighbor(current);

            if (next != null) {
                stack.push(current);
                removeWallBetween(current, next);
                next.setVisited(true);
                stack.push(next);
            }
        }
    }

    private MazeNode getRandomUnvisitedNeighbor(MazeNode node) {
        int x = node.getX();
        int y = node.getY();

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        shuffleArray(directions);

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];

            int newX = x + dx;
            int newY = y + dy;

            if (newX >= 0 && newX < width && newY >= 0 && newY < height && !maze[newY][newX].isVisited()) {
                return maze[newY][newX];
            }
        }

        return null;
    }

    private void removeWallBetween(MazeNode a, MazeNode b) {
        int x = (a.getX() + b.getX()) / 2;
        int y = (a.getY() + b.getY()) / 2;
        maze[y][x].setWall(false);
    }

    private void defineExit() {
        // Try to find an exit on the opposite border of the entrance
        for (int x = 0; x < width; x++) {
            if (maze[height - 1][x].isVisited() && !maze[height - 1][x].isWall()) {
                exit = maze[height - 1][x];
                return;
            }
        }

        // If no exit is found on the opposite border, find the first visited border node
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if ((x == 0 || x == width - 1 || y == 0 || y == height - 1) && maze[y][x].isVisited() && !maze[y][x].isWall()) {
                    exit = maze[y][x];
                    return;
                }
            }
        }
    }

    // Helper method to shuffle an array
    private void shuffleArray(int[][] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int[] temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    // Method to print the maze
    public void printMaze() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(maze[y][x].isWall())
                    System.out.print("#");
                else if(this.entrance.getX() == x && this.entrance.getY() == y)
                    System.out.print("E");
                else if(this.exit.getX() == x && this.exit.getY() == y)
                    System.out.print("X");
                else
                    System.out.print(".");
            }
            System.out.println("|");
        }
    }
}

class MazeNode {
    private int x;
    private int y;
    private boolean isWall;
    private boolean visited = false;

    public MazeNode(int x, int y, boolean isWall) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        this.isWall = wall;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}


