package util;

import Space.SpaceObjects;
import main.GameFrame;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private final int gridSizeX;
    private final int gridSizeY;
    private final int cellSize;
    private List<SpaceObjects>[][] grid;

    public Grid(int gridSizeX, int gridSizeY, int cellSize) {

        this.cellSize = cellSize;
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;

        grid = new ArrayList[gridSizeX][gridSizeY];

        for (int x = 0; x < gridSizeX; x++) {

            for (int y =  0; y < gridSizeY; y ++) {
                grid[x][y] = new ArrayList<>();
            }
        }
    }

    public void addObject(SpaceObjects object) {

        int x = object.getX() / cellSize;
        int y = object.getY() / cellSize;

        // Check bounds to ensure x and y are within the grid's dimensions
        if (x >= 0 && x < gridSizeX && y >= 0 && y < gridSizeY) {
            grid[x][y].add(object);
        }
    }

    public void objectCollision() {

        for (int x = 0; x < gridSizeX; x++) {
            for (int y = 0; y < gridSizeY; y++) {
                List<SpaceObjects> objects = grid[x][y];
                for (int i = 0; i < objects.size(); i++) {
                    SpaceObjects firstObject = objects.get(i);
                    for (int j = 0; j < objects.size(); j++) {
                        SpaceObjects secondObject = objects.get(j);
                        if (firstObject.intersects(secondObject)) {
                            firstObject.takeDamage(secondObject.getDamage());
                            secondObject.takeDamage(firstObject.getDamage());
                        }
                    }
                }
            }
        }
    }

    public void clearGrid() {

        for (int x = 0; x < gridSizeX; x++) {
            for (int y = 0; y< gridSizeY; y++) {
                grid[x][y].clear();
            }
        }
    }
}
