import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.Point;
import com.engine.core.gfx.SpriteSheet;
/**
 * Represents a single game tile with its visual and gameplay properties.
 */
public class GameTile {

    // Human-readable title/name of the tile
    private String title;

    // Icon to show on the tile
    private SpriteSheet icon;

    // Point values for the two teams
    private int bluePoints;
    private int redPoints;

    // Resources provided by this tile
    private final List<String> resources = new ArrayList<>();

    // The color tint or identifying color of the tile
    private Color tileColor;

    // The grid/world location of this tile (e.g., pixel or grid coordinates)
    private Point location;

    public GameTile() {
    }

    public GameTile(SpriteSheet icon, int bluePoints, int redPoints, List<String> resources, Color tileColor) {
        this.icon = icon;
        this.bluePoints = bluePoints;
        this.redPoints = redPoints;
        if (resources != null) {
            this.resources.addAll(resources);
        }
        this.tileColor = tileColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GameTile(SpriteSheet icon, int bluePoints, int redPoints, List<String> resources, Color tileColor, Point location) {
        this(icon, bluePoints, redPoints, resources, tileColor);
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

    public Color getTileColor() {
        return tileColor;
    }

    public void setTileColor(Color tileColor) {
        this.tileColor = tileColor;
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
}
