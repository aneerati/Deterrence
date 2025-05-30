package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        loadTiles();
        loadMap("/maps/world01.txt");
    }

    // load all tiles on startup
    public void loadTiles() {
        setupTiles(0, "grass", false);
        setupTiles(1, "wall", true);
        setupTiles(2, "water", true);
        setupTiles(3, "earth", false);
        setupTiles(4, "tree", true);
        setupTiles(5, "sand", false);
    }

    public void setupTiles(int index, String tileName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + tileName + ".png"));
            tiles[index].image = uTool.scaleImage(tiles[index].image, gp.tileSize, gp.tileSize);
            tiles[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Math to make world rendering work
    public void loadMap(String mapFile) {
        try {
            InputStream is = getClass().getResourceAsStream(mapFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {

        }
    }

    // Draw method for world building
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            // allows player to always be in center
            // Again, no clue how this works
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if ((worldX + gp.tileSize > gp.player.worldX - gp.player.screenX)
                    && (worldX - gp.tileSize < gp.player.worldX + gp.player.screenX)
                    && (worldY + gp.tileSize > gp.player.worldY - gp.player.screenY)
                    && (worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)) {

                g2.drawImage(tiles[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                // x = 0;
                worldRow++;
            }
        }
    }
}
