import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.Point;
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

    public GameTile(SpriteSheet icon, int bluePoints, int redPoints, List<String> resources) {
        this.icon = icon;
        this.bluePoints = bluePoints;
        this.redPoints = redPoints;
        if (resources != null) {
            this.resources.addAll(resources);
        }
        this.isBuildingExists = false;
    }

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

    public GameTile(SpriteSheet icon, int bluePoints, int redPoints, List<String> resources, Point location) {
        this(icon, bluePoints, redPoints, resources);
        setLocation(location);
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

    public List<String> getResources() {
        return Collections.unmodifiableList(resources);
    }

    public void addResource(String resource) {
        if (resource != null && !resource.isEmpty()) {
            resources.add(resource);
        }
    }

    public boolean removeResource(String resource) {
        return resources.remove(resource);
    }



    public Point getLocation() {
        return location == null ? null : new Point(location);
    }

    public void setLocation(Point location) {
        this.location = (location == null) ? null : new Point(location);
    }

    public void setLocation(int x, int y) {
        if (this.location == null) {
            this.location = new Point(x, y);
        } else {
            this.location.setLocation(x, y);
        }
    }

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

    public void buildBuilding() {
        this.isBuildingExists = true;
    }
}
