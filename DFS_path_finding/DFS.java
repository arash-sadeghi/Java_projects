import java.util.*;
class DFS implements GraphSearch {

    @Override
    public List<MazeNode> search(Maze maze) {
        Stack<MazeNode> stack = new Stack<>();
        List<MazeNode> path = new ArrayList<>();
        MazeNode entrance = maze.getEntrance();
        MazeNode exit = maze.getExit();

        stack.push(entrance);
        entrance.setVisited(true);

        while (!stack.isEmpty()) {
            MazeNode current = stack.pop();
            path.add(current);

            if (current == exit) {
                break;
            }

            for (MazeNode neighbor : getUnvisitedNeighbors(current, maze)) {
                stack.push(neighbor);
                neighbor.setVisited(true);
            }
        }
        return path;
    }

    private List<MazeNode> getUnvisitedNeighbors(MazeNode node, Maze maze) {
        List<MazeNode> unvisitedNeighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] direction : directions) {
            int newX = node.getX() + direction[0];
            int newY = node.getY() + direction[1];

            if (isValidCoordinate(newX, newY, maze) && !maze.getNode(newX, newY).isWall()
                    && !maze.getNode(newX, newY).isVisited()) {
                unvisitedNeighbors.add(maze.getNode(newX, newY));
            }
        }
        return unvisitedNeighbors;
    }

    private boolean isValidCoordinate(int x, int y, Maze maze) {
        return x >= 0 && x < maze.getWidth() && y >= 0 && y < maze.getHeight();
    }
}
