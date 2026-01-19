import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.Point;

import com.engine.core.Helper;
import com.engine.core.gfx.Draw;
import com.engine.core.gfx.SpriteSheet;


public class GameTile {


    private String title;

    private TileType tileType;

    private SpriteSheet icon;

    private int bluePoints;
    private int redPoints;

    // Resources provided by this tile
    private final List<String> resources = new ArrayList<>();

    // The tile location
    private Point location;

    private boolean isBuildingExists;

    //information for tile logic
    public GameTile(SpriteSheet icon, int bluePoints, int redPoints, List<String> resources) {
        this.icon = icon;
        this.bluePoints = bluePoints;
        this.redPoints = redPoints;
        if (resources != null) {
            this.resources.addAll(resources);
        }
        this.isBuildingExists = false;
    }
//getters and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public SpriteSheet getIcon() {
        return icon;
    }

    public void setIcon(SpriteSheet icon) {
        this.icon = icon;
    }

    public int getBluePoints() {
        return bluePoints;
    }

    public void setBluePoints(int bluePoints) {
        this.bluePoints = bluePoints;
    }

    public int getRedPoints() {
        return redPoints;
    }

    public void setRedPoints(int redPoints) {
        this.redPoints = redPoints;
    }

    //Check which resource belongs to tile and how many should the player receive based on build status
    public String getResources() {
        String resource = "";
        int howMany = 0;

        if (tileType == TileType.Village) {
            resource = "People";
            howMany += 1;
        } else if (tileType == TileType.Mountains) {
            resource = "Iron";
        } else if (tileType == TileType.Forest) {
            resource = "Wood";
        } else if (tileType == TileType.OilField) {
            resource = "Oil";
        } else if (tileType == TileType.Field) {
            resource = "Food";
        } else if (tileType == TileType.Desert) {
            resource = "Glass";
        }

        if (this.isBuildingExists) {
            howMany += 1;
        }

        return resource + ", " + Integer.toString(howMany);
    }

    //remove 1 resource every 3 turns for some buildings
    public String removeResource() {
        String resource = "";
        int howMany = 0;

        if (this.isBuildingExists) {
            if (tileType == TileType.Mountains) {
                resource = "Food";
            } else if (tileType == TileType.Forest) {
                resource = "Oil";
            } else if (tileType == TileType.OilField) {
                resource = "Food";
            } else if (tileType == TileType.Desert) {
                resource = "Oil";
            }
            howMany = 1;
        }

        return resource + ", " + Integer.toString(howMany);
    }

    //find the available buildings to be constructed by the tiletype
    public BuildingType getAvailableBuilding() {
        switch (tileType)
        {
            case Desert:
                return BuildingType.GlassFurnace;
            case Field:
                return BuildingType.Farm;
            case OilField:
                return BuildingType.OilDrill;
            case Forest:
                return BuildingType.LumberMill;
            case Village:
                return BuildingType.House;
            case Mountains:
                return BuildingType.Mine;
            default:
				return null;
        }
    }

    //to check if there is already a building on the tile
    public boolean isBuildingBuilt() {
        return this.isBuildingExists;
    }

    //build the building
    public void buildBuilding(boolean isBluePlayerTurn) {
        this.isBuildingExists = true;
        if (isBluePlayerTurn) {
            this.bluePoints += 15;
            this.redPoints = 100 - this.bluePoints;
        } else {
            this.redPoints += 15;
            this.bluePoints = 100 - this.redPoints;
        }
    }

    public void invokePropaganda(boolean isBluePlayerTurn) {
        if (isBluePlayerTurn) {
            this.bluePoints += 5;
            this.redPoints = 100 - this.bluePoints;
        } else {
            this.redPoints += 5;
            this.bluePoints = 100 - this.redPoints;
        }
    }
}
