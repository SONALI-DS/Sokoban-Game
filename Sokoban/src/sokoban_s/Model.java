package sokoban_s;

import java.awt.Point;
import sokoban_s.LevelLoader.Level;


public class Model {

    private String level = ""
            + "HHHHHHHHH\n"
            + "H@HBD@@@H\n"
            + "HAD@@@@HH\n"
            + "H@H@@@@@H\n"
            + "HH@@FH@HH\n"
            + "HHH@@@BHH\n"
            + "HHHHHHHHH\n";
    
    private char[][] map = new char[9][7];
    private Point p = new Point();

    public Model() {
        init();
    }
    
    public Model(Level level) {
        this.level = level.info;
        map = new char[level.width][level.height];
        init();
    }
    
    public char[][] getMap() {
        return map;
    }

    public Point getP() {
        return p;
    }

    private void init() {
        String[] lines = level.split("\\n");
        int y = 0;
        for (String line : lines) {
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if ((c & 1) == 1) {
                    p.x = x;
                    p.y = y;
                    c = (char) (c & 78);
                }
                map[x][y] = c;
            }
            y++;
        }
    }

    // 37-esq 38-cima 39-dir 40-baixo
    public void move(int d) {
        try {
            int dx = d == 37 ? -1 : d == 39 ? 1 : 0;
            int dy = d == 38 ? -1 : d == 40 ? 1 : 0;
            int t1 = map[p.x + dx][p.y + dy];
            int t2 = map[p.x + 2 * dx][p.y + 2 * dy];
            if ((t1 & 4) == 4 && (t2 & 12) == 0) {
                map[p.x + dx][p.y + dy] = (char) (t1 & 75);
                map[p.x + 2 * dx][p.y + 2 * dy] = (char) (t2 | 4);
            }
            if ((map[p.x + dx][p.y + dy] & 12) == 0) {
                p.x += dx;
                p.y += dy;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException e) { }
    }

    public boolean isCompleted() {
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                int t = map[x][y] & 6;
                if (t > 0 && t != 6) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                out.append(p.x == x && p.y == y ? 'A' : map[x][y]);
            }
            out.append("\n");
        }
        return out.toString();
    }
    
}
