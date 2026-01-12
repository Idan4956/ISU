//Author: Idan Sheranosher
//File Name: Main.java
//Project Name: ISU
//Creation Date: December 9th, 2025
//Modified Date: 
//Description: 

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;

import com.engine.core.*;
import com.engine.core.gfx.*;

import java.util.ArrayList;

public class Main extends AbstractGame {
    //Required Basic Game Functional Data
    private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private static int screenWidth = device.getDisplayMode().getWidth();
    private static int screenHeight = device.getDisplayMode().getHeight();

    //Store how many milliseconds are in one second
    private static final float SECOND = 1000f;

    //Game States - Add/Remove/Modify as needed
    //These are the most common game states, but modify as needed
    //You will ALSO need to modify the two switch statements in Update and Draw
    private static final int MENU = 0;
    private static final int SETTINGS = 1;
    private static final int INSTRUCTIONS = 2;
    private static final int GAMEPLAY = 3;
    private static final int PAUSE = 4;
    private static final int ENDGAME = 5;

    //Required Basic Game Visual data used in main below
    private static String gameName = "Tile Democracy";
    private static int windowWidth = 1920;    //For fullscreen mode set these next two to screenWidth and screenHeight
    private static int windowHeight = 1080;
    private static int fps = 60;

    //Store and set the initial game state, typically MENU to start
    private int gameState = MENU;

    /////////////////////////////////////////////////////////////////////////////////////
    // Define your Global variables and constants here (They do NOT need to be static) //
    /// //////////////////////////////////////////////////////////////////////////////////

    int[] ylevel = new int[]{200, 290, 290, 335, 380};
    int[] xlevel = new int[]{480, 505, 630, 755, 880, 1005, 1130, 1255};

    int[] tileControled = new int[]{50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};


    float fourtyFiveSec = 45000;
    float buttonPress = 0;
    //tiles are listed from left to right, top to bottom

    String player1Points = "0";
    String player2Points = "0";

    Boolean player1turn = true;
    Boolean player2turn = false;

    SpriteSheet player1Icon;
    SpriteSheet player2Icon;
    SpriteSheet oceanBg;
    SpriteSheet village;
    SpriteSheet mountains;
    SpriteSheet desert;
    SpriteSheet field;
    SpriteSheet forest;
    SpriteSheet oilField;
    SpriteSheet village2;
    SpriteSheet mountains2;
    SpriteSheet desert2;
    SpriteSheet field2;
    SpriteSheet forest2;
    SpriteSheet oilField2;
    SpriteSheet village3;
    SpriteSheet mountains3;
    SpriteSheet desert3;
    SpriteSheet field3;
    SpriteSheet forest3;
    SpriteSheet oilField3;
    SpriteSheet village4;
    SpriteSheet mountains4;
    SpriteSheet desert4;
    SpriteSheet field4;
    SpriteSheet forest4;
    SpriteSheet oilField4;
    SpriteSheet village5;
    SpriteSheet mountains5;
    SpriteSheet desert5;
    SpriteSheet field5;
    SpriteSheet forest5;
    SpriteSheet oilField5;
    SpriteSheet playButton;
    SpriteSheet lumberMillButton;
    SpriteSheet pressedLumber;
    SpriteSheet mineButton;
    SpriteSheet pressedMine;
    SpriteSheet farmButton;
    SpriteSheet pressedfarm;
    SpriteSheet pressedHouse;
    SpriteSheet HouseButton;
    SpriteSheet GlassFurnaceButton;
    SpriteSheet pressedGlassFurnace;
    SpriteSheet oilFieldButton;
    SpriteSheet pressedOilField;

    PlayerResources player1Resources = new PlayerResources();
    PlayerResources player2Resources = new PlayerResources();

    Font titleFont = new Font("Arial", Font.BOLD + Font.ITALIC, 40);
    Vector2F mousePos = new Vector2F(0f, 0f);

    // List of clickable tiles (populated in LoadContent in draw order)
    private final ArrayList<SpriteSheet> tiles = new ArrayList<>();
    // Metadata objects for each tile (same order as 'tiles')
    private final ArrayList<GameTile> gameTiles = new ArrayList<>();
    // Persistently selected tile index (-1 when none selected)
    private int selectedTileIndex = -1;

    public static void main(String[] args) {
        GameContainer gameContainer = new GameContainer(new Main(), gameName, windowWidth, windowHeight, fps);
        gameContainer.Start();
    }


    @Override
    public void LoadContent(GameContainer gc) {
        //TODO: This subprogram automatically happens only once, just before the actual game loop starts.
        //It should never be manually called, the Engine will call it for you.
        //Load images, sounds and set up any data
        // NOTE: Engine likely loads from classpath; with 'res' marked as resources root, use paths starting at 'images/...'
        oceanBg = new SpriteSheet(LoadImage.FromFile("images/backgrounds/waterbganimation.png"),5,5, 0, 21, 5);
        oceanBg.destRec = new Rectangle(0, 0, windowWidth, windowHeight);
        oceanBg.StartAnimation();
        lumberMillButton = new SpriteSheet(LoadImage.FromFile("images/sprites/lumberMillButton.png"));
        lumberMillButton.destRec = new Rectangle(0, 0, (int) (lumberMillButton.GetFrameWidth() * 0.2f), (int) (lumberMillButton.GetFrameHeight() * 0.2f));
        pressedLumber = new SpriteSheet(LoadImage.FromFile("images/sprites/pressedLumber.png"));
        pressedLumber.destRec = new Rectangle(0, 0, (int) (pressedLumber.GetFrameWidth() * 0.08f), (int) (pressedLumber.GetFrameHeight() * 0.08f));
        mineButton = new SpriteSheet(LoadImage.FromFile("images/sprites/MineButton.png"));
        mineButton.destRec = new Rectangle(0, 0, (int) (mineButton.GetFrameWidth() * 0.2f), (int) (mineButton.GetFrameHeight() * 0.2f));
        pressedMine = new SpriteSheet(LoadImage.FromFile("images/sprites/PressedMine.png"));
        pressedMine.destRec = new Rectangle(0, 0, (int) (pressedMine.GetFrameWidth() * 0.2f), (int) (pressedMine.GetFrameHeight() * 0.2f));
        farmButton = new SpriteSheet(LoadImage.FromFile("images/sprites/FarmButton.png"));
        farmButton.destRec = new Rectangle(0, 0, (int) (farmButton.GetFrameWidth() * 0.2f), (int) (farmButton.GetFrameHeight() * 0.2f));
        pressedfarm = new SpriteSheet(LoadImage.FromFile("images/sprites/PressedFarm.png"));
        pressedfarm.destRec = new Rectangle(0, 0, (int) (pressedfarm.GetFrameWidth() * 0.2f), (int) (pressedfarm.GetFrameHeight() * 0.2f));
        pressedHouse = new SpriteSheet(LoadImage.FromFile("images/sprites/PressedVillage.png"));
        pressedHouse.destRec = new Rectangle(0, 0, (int) (pressedHouse.GetFrameWidth() * 0.2f), (int) (pressedHouse.GetFrameHeight() * 0.2f));
        GlassFurnaceButton = new SpriteSheet(LoadImage.FromFile("images/sprites/GlassFurnaceButton.png"));
        GlassFurnaceButton.destRec = new Rectangle(0, 0, (int) (GlassFurnaceButton.GetFrameWidth() * 0.2f), (int) (GlassFurnaceButton.GetFrameHeight() * 0.2f));
        pressedGlassFurnace = new SpriteSheet(LoadImage.FromFile("images/sprites/PressedGlassFurnace.png"));
        pressedGlassFurnace.destRec = new Rectangle(0, 0, (int) (pressedGlassFurnace.GetFrameWidth() * 0.2f), (int) (pressedGlassFurnace.GetFrameHeight() * 0.2f));
        oilFieldButton = new SpriteSheet(LoadImage.FromFile("images/sprites/OilFieldButton.png"));
        oilFieldButton.destRec = new Rectangle(0, 0, (int) (oilFieldButton.GetFrameWidth() * 0.2f), (int) (oilFieldButton.GetFrameHeight() * 0.2f));
        pressedOilField = new SpriteSheet(LoadImage.FromFile("images/sprites/PressedOilField.png"));
        pressedOilField.destRec = new Rectangle(0, 0, (int) (pressedOilField.GetFrameWidth() * 0.2f), (int) (pressedOilField.GetFrameHeight() * 0.2f));
        HouseButton = new SpriteSheet(LoadImage.FromFile("images/sprites/VillageButton.png"));
        HouseButton.destRec = new Rectangle(0, 0, (int) (HouseButton.GetFrameWidth() * 0.2f), (int) (HouseButton.GetFrameHeight() * 0.2f));
        village = new SpriteSheet(LoadImage.FromFile("images/sprites/village.png"));
        village.destRec = new Rectangle(xlevel[4], ylevel[0], village.GetFrameWidth() * 2, village.GetFrameHeight() * 2);
        mountains = new SpriteSheet(LoadImage.FromFile("images/sprites/mountains.png"));
        mountains.destRec = new Rectangle(xlevel[5], ylevel[2] - 15, mountains.GetFrameWidth() * 2, mountains.GetFrameHeight() * 2);
        desert = new SpriteSheet(LoadImage.FromFile("images/sprites/Desert.png"));
        desert.destRec = new Rectangle(xlevel[6] + 12, ylevel[3] + 30, desert.GetFrameWidth() * 2, desert.GetFrameHeight() * 2);
        //blueDesert = new  SpriteSheet(LoadImage.FromFile("images/sprites/blueDesert.png"));
        //blueDesert.destRec = new Rectangle (xlevel[6] + 12, ylevel[3] + 30, blueDesert.GetFrameWidth() * 2, blueDesert.GetFrameHeight() * 2);
        field = new SpriteSheet(LoadImage.FromFile("images/sprites/Field.png"));
        field.destRec = new Rectangle(xlevel[4], 380, field.GetFrameWidth() * 2, field.GetFrameHeight() * 2);
        oilField = new SpriteSheet(LoadImage.FromFile("images/sprites/Oil Field.png"));
        oilField.destRec = new Rectangle(xlevel[4] + 5, 525, oilField.GetFrameWidth() * 2, oilField.GetFrameHeight() * 2);
        forest = new SpriteSheet(LoadImage.FromFile("images/sprites/Forest.png"));
        forest.destRec = new Rectangle(xlevel[4] + 5, 675, forest.GetFrameWidth() * 2, forest.GetFrameHeight() * 2);
        village2 = new SpriteSheet(LoadImage.FromFile("images/sprites/village.png"));
        village2.destRec = new Rectangle(xlevel[6] + 17, 495, village2.GetFrameWidth() * 2, village2.GetFrameHeight() * 2);
        mountains2 = new SpriteSheet(LoadImage.FromFile("images/sprites/mountains.png"));
        mountains2.destRec = new Rectangle(xlevel[5], 425, mountains2.GetFrameWidth() * 2, mountains2.GetFrameHeight() * 2);
        desert2 = new SpriteSheet(LoadImage.FromFile("images/sprites/Desert.png"));
        desert2.destRec = new Rectangle(xlevel[5] + 10, 595, desert2.GetFrameWidth() * 2, desert2.GetFrameHeight() * 2);
        field2 = new SpriteSheet(LoadImage.FromFile("images/sprites/Field.png"));
        field2.destRec = new Rectangle(xlevel[4], 75, field2.GetFrameWidth() * 2, field2.GetFrameHeight() * 2);
        oilField2 = new SpriteSheet(LoadImage.FromFile("images/sprites/Oil Field.png"));
        oilField2.destRec = new Rectangle(xlevel[4] + 7, 831, oilField2.GetFrameWidth() * 2, oilField2.GetFrameHeight() * 2);
        forest2 = new SpriteSheet(LoadImage.FromFile("images/sprites/Forest.png"));
        forest2.destRec = new Rectangle(xlevel[5] + 5, 137, forest2.GetFrameWidth() * 2, forest2.GetFrameHeight() * 2);
        village3 = new SpriteSheet(LoadImage.FromFile("images/sprites/village.png"));
        village3.destRec = new Rectangle(xlevel[7] + 22, 418, village2.GetFrameWidth() * 2, village2.GetFrameHeight() * 2);
        mountains3 = new SpriteSheet(LoadImage.FromFile("images/sprites/mountains.png"));
        mountains3.destRec = new Rectangle(xlevel[6] + 10, 652, mountains2.GetFrameWidth() * 2, mountains2.GetFrameHeight() * 2);
        desert3 = new SpriteSheet(LoadImage.FromFile("images/sprites/Desert.png"));
        desert3.destRec = new Rectangle(xlevel[7] + 20, 588, desert2.GetFrameWidth() * 2, desert2.GetFrameHeight() * 2);
        field3 = new SpriteSheet(LoadImage.FromFile("images/sprites/Field.png"));
        field3.destRec = new Rectangle(xlevel[5] + 11, 756, field2.GetFrameWidth() * 2, field2.GetFrameHeight() * 2);
        oilField3 = new SpriteSheet(LoadImage.FromFile("images/sprites/Oil Field.png"));
        oilField3.destRec = new Rectangle(xlevel[6] + 13, 215, oilField2.GetFrameWidth() * 2, oilField2.GetFrameHeight() * 2);
        forest3 = new SpriteSheet(LoadImage.FromFile("images/sprites/Forest.png"));
        forest3.destRec = new Rectangle(xlevel[7] + 19, 283, forest2.GetFrameWidth() * 2, forest2.GetFrameHeight() * 2);
        village4 = new SpriteSheet(LoadImage.FromFile("images/sprites/village.png"));
        village4.destRec = new Rectangle(xlevel[1] - 16, 430, village4.GetFrameWidth() * 2, village4.GetFrameHeight() * 2);
        mountains4 = new SpriteSheet(LoadImage.FromFile("images/sprites/mountains.png"));
        mountains4.destRec = new Rectangle(xlevel[3] - 12, 123, mountains4.GetFrameWidth() * 2, mountains4.GetFrameHeight() * 2);
        desert4 = new SpriteSheet(LoadImage.FromFile("images/sprites/Desert.png"));
        desert4.destRec = new Rectangle(xlevel[3] - 5, 292, desert4.GetFrameWidth() * 2, desert4.GetFrameHeight() * 2);
        field4 = new SpriteSheet(LoadImage.FromFile("images/sprites/Field.png"));
        field4.destRec = new Rectangle(xlevel[2] - 11, 377, field4.GetFrameWidth() * 2, field4.GetFrameHeight() * 2);
        oilField4 = new SpriteSheet(LoadImage.FromFile("images/sprites/Oil Field.png"));
        oilField4.destRec = new Rectangle(xlevel[3] - 2, 602, oilField4.GetFrameWidth() * 2, oilField4.GetFrameHeight() * 2);
        forest4 = new SpriteSheet(LoadImage.FromFile("images/sprites/Forest.png"));
        forest4.destRec = new Rectangle(xlevel[3] - 5, 445, forest4.GetFrameWidth() * 2, forest4.GetFrameHeight() * 2);
        village5 = new SpriteSheet(LoadImage.FromFile("images/sprites/village.png"));
        village5.destRec = new Rectangle(xlevel[2] - 11, 198, village5.GetFrameWidth() * 2, village5.GetFrameHeight() * 2);
        mountains5 = new SpriteSheet(LoadImage.FromFile("images/sprites/mountains.png"));
        mountains5.destRec = new Rectangle(xlevel[2] - 18, 510, mountains5.GetFrameWidth() * 2, mountains5.GetFrameHeight() * 2);
        desert5 = new SpriteSheet(LoadImage.FromFile("images/sprites/Desert.png"));
        desert5.destRec = new Rectangle(xlevel[1] - 20, 602, desert5.GetFrameWidth() * 2, desert5.GetFrameHeight() * 2);
        field5 = new SpriteSheet(LoadImage.FromFile("images/sprites/Field.png"));
        field5.destRec = new Rectangle(xlevel[3] - 2, 762, field5.GetFrameWidth() * 2, field5.GetFrameHeight() * 2);
        oilField5 = new SpriteSheet(LoadImage.FromFile("images/sprites/Oil Field.png"));
        oilField5.destRec = new Rectangle(xlevel[1] - 15, 296, oilField5.GetFrameWidth() * 2, oilField5.GetFrameHeight() * 2);
        forest5 = new SpriteSheet(LoadImage.FromFile("images/sprites/Forest.png"));
        forest5.destRec = new Rectangle(xlevel[2] - 11, 677, forest5.GetFrameWidth() * 2, forest5.GetFrameHeight() * 2);
        playButton = new SpriteSheet(LoadImage.FromFile("images/sprites/PlayButton.png"));
        playButton.destRec = new Rectangle(windowWidth / 2 - playButton.GetFrameWidth() / 2, windowHeight / 2 - playButton.GetFrameHeight() / 2 + 120, playButton.GetFrameWidth(), playButton.GetFrameHeight());
        player1Icon = new SpriteSheet(LoadImage.FromFile("images/sprites/Player1icon.png"));
        player1Icon.destRec = new Rectangle(0, 0, player1Icon.GetFrameWidth() / 3, player1Icon.GetFrameHeight() / 3);
        player2Icon = new SpriteSheet(LoadImage.FromFile("images/sprites/Player2icon.png"));
        player2Icon.destRec = new Rectangle(windowWidth - player2Icon.GetFrameWidth() - 80, -8, (int) (player2Icon.GetFrameWidth() / 2.5f), (int) (player2Icon.GetFrameHeight() / 2.5f));

        // Build the clickable tiles list once all tiles are positioned
        buildTileList();
    }

    // Returns the index (in draw order) of the tile under the mouse when the left mouse button is released.
    private int getClickedTileIndexOnRelease() {
        if (!Input.IsMouseButtonReleased(Input.MOUSE_LEFT)) {
            return -1;
        }

        Vector2F mousePos = Input.GetMousePos();

        for (int i = tiles.size() - 1; i >= 0; i--) {
            SpriteSheet tile = tiles.get(i);
            if (tile != null && tile.destRec != null && Helper.Intersects(tile.destRec, mousePos)) {
                return i;
            }
        }
        return -1;
    }


    private SpriteSheet getClickedTileOnRelease() {
        int idx = getClickedTileIndexOnRelease();
        return (idx >= 0 && idx < tiles.size()) ? tiles.get(idx) : null;
    }


    private GameTile getClickedGameTileOnRelease() {
        int idx = getClickedTileIndexOnRelease();
        return (idx >= 0 && idx < gameTiles.size()) ? gameTiles.get(idx) : null;
    }


    // Returns the index (in draw order from back to front) of the tile currently under the mouse.
    // Does NOT require any mouse button state.
    private int getTileIndexUnderMouse() {
        Vector2F mouse = Input.GetMousePos();
        for (int i = tiles.size() - 1; i >= 0; i--) {
            SpriteSheet tile = tiles.get(i);
            if (tile != null && tile.destRec != null && Helper.Intersects(tile.destRec, mouse)) {
                return i;
            }
        }
        return -1;
    }



    private int BuildingCost(BuildingType bt, PlayerResources playerResources )
    {
    	
        if (bt == BuildingType.GlassFurnace)
        {
            if (playerResources.getIron() >= 1 && playerResources.getPeople() >= 1)
            {
                playerResources.setIron(playerResources.getIron() - 1);
                playerResources.setPeople(playerResources.getPeople() - 1);
                System.out.println("Build Glass Furnace");
            }
            else
            {
                return 0;
            }

        }

		return 1;
    }

    // Returns the SpriteSheet of the tile currently under the mouse, or null if none.
    private SpriteSheet getTileUnderMouse() {
        int idx = getTileIndexUnderMouse();
        return (idx >= 0 && idx < tiles.size()) ? tiles.get(idx) : null;
    }

    // Returns the GameTile metadata of the tile currently under the mouse, or null if none.
    private GameTile getGameTileUnderMouse() {
        int idx = getTileIndexUnderMouse();
        return (idx >= 0 && idx < gameTiles.size()) ? gameTiles.get(idx) : null;
    }

    private SpriteSheet getSelectedTile() {
        return (selectedTileIndex >= 0 && selectedTileIndex < tiles.size()) ? tiles.get(selectedTileIndex) : null;
    }

    private GameTile getSelectedGameTile() {
        return (selectedTileIndex >= 0 && selectedTileIndex < gameTiles.size()) ? gameTiles.get(selectedTileIndex) : null;
    }

    // Returns the appropriate build button sprite based on building type and state
    private SpriteSheet getBuildButton(BuildingType b, boolean pressed) {
        if (b == null) return null;
        switch (b) {
            case LumberMill:
                return pressed ? pressedLumber : lumberMillButton;
            case Mine:
                return pressed ? pressedMine : mineButton;
            case Farm:
                return pressed ? pressedfarm : farmButton;
            case House:
                return pressed ? pressedHouse : HouseButton;
            case GlassFurnace:
                return pressed ? pressedGlassFurnace : GlassFurnaceButton;
            case OilDrill:
                return pressed ? pressedOilField : oilFieldButton;
            default:
                return null;
        }
    }

    private void drawTileInfoPanel(Graphics2D gfx, SpriteSheet tile, GameTile meta) {
        if (tile == null || tile.destRec == null) return;

        // Slightly smaller font for tile names
        Font smallTitleFont = titleFont.deriveFont((float) Math.max(12, (int)(titleFont.getSize() * 0.8f)));

        // Title text and measurement
        String title = (meta != null && meta.getTitle() != null) ? meta.getTitle() : "";
        java.awt.FontMetrics fm = gfx.getFontMetrics(smallTitleFont);
        int lineH = fm.getHeight();
        int textAscent = fm.getAscent();

        // Determine if a build button is available and get its size
        int btnW = 0, btnH = 0;
        BuildingType bt = (meta != null) ? meta.getAvailableBuilding() : null;
        SpriteSheet btn = getBuildButton(bt, false);
        if (btn != null && btn.destRec != null) {
            btnW = Math.max(0, btn.destRec.width);
            btnH = Math.max(0, btn.destRec.height);
        }

        // Panel sizing to fit full title
        int padX = 16;
        int padY = 12;
        int minW = Math.max(220, btnW + padX * 2);
        int maxW = Math.max(minW, Math.min(520, windowWidth - 40));

        // Word-wrap the title to fit within maxW
        java.util.List<String> lines = new java.util.ArrayList<>();
        if (!title.isEmpty()) {
            String[] words = title.split(" ");
            StringBuilder current = new StringBuilder();
            for (String w : words) {
                String trial = current.length() == 0 ? w : current + " " + w;
                int trialW = fm.stringWidth(trial);
                if (trialW + padX * 2 <= maxW) {
                    current.setLength(0);
                    current.append(trial);
                } else {
                    if (current.length() > 0) {
                        lines.add(current.toString());
                        current.setLength(0);
                        current.append(w);
                    } else {
                        // Single long word: hard cut
                        lines.add(w);
                        current.setLength(0);
                    }
                }
            }
            if (current.length() > 0) lines.add(current.toString());
        } else {
            lines.add("");
        }

        // Panel width based on the longest wrapped line
        int longest = 0;
        for (String ln : lines) longest = Math.max(longest, fm.stringWidth(ln));
        int panelW = Math.max(minW, Math.min(maxW, longest + padX * 2));

        // Height: all lines + optional spacing + button area + padding
        int spacing = (btn != null) ? 12 : 0;
        int titleBlockH = lines.size() * lineH;
        int contentH = titleBlockH + spacing + ((btn != null) ? btnH : 0);
        int panelH = Math.max(80, contentH + padY * 2);

        // Base position to the right of the tile
        int baseX = tile.destRec.x + tile.destRec.width + 20;

        // Orientation: show above if tile is in the lower half of the screen
        boolean showAbove = (tile.destRec.y + tile.destRec.height / 2) > (windowHeight / 2);
        int baseY = showAbove ? (tile.destRec.y - panelH - 20) : (tile.destRec.y + 20);

        // Clamp inside the window bounds
        int panelX = Math.max(10, Math.min(baseX, windowWidth - panelW - 10));
        int panelY = Math.max(10, Math.min(baseY, windowHeight - panelH - 10));

        // Draw panel background
        Draw.FillRect(gfx, panelX, panelY, panelW, panelH, Helper.BLACK, 0.6f);

        // Draw wrapped title lines (left-top inside panel)
        int textX = panelX + padX;
        int textY = panelY + padY + textAscent; // baseline position for first line
        for (int i = 0; i < lines.size(); i++) {
            String ln = lines.get(i);
            Draw.Text(gfx, ln, textX, textY + (i * lineH), smallTitleFont, Helper.WHITE, 1f);
        }

        // Draw button below the title (inside the panel)
        if (btn != null) {
            int btnX = panelX + padX;
            int btnY = panelY + padY + titleBlockH + spacing;
            btn.destRec.x = btnX;
            btn.destRec.y = btnY;
            Draw.Sprite(gfx, btn);

            // Click handling
            if (Input.IsMouseButtonReleased(Input.MOUSE_LEFT) && Helper.Intersects(btn.destRec, Input.GetMousePos())) {
                buttonPress = 1000;
                int didBuildingSuccess = BuildingCost(bt, player1turn ? player1Resources :  player2Resources);
                if (didBuildingSuccess == 1) {
                    meta.buildBuilding();
                } else {
                    Draw.FillRect(gfx, windowWidth /2 - 100, windowHeight / 2 - 50, windowWidth /2 + 100, windowHeight / 2 + 50, Helper.BLACK, 1F);
                    Draw.Text(gfx, "Not enough resources to build " + bt.toString(), windowWidth /2 -80 , windowHeight / 2, titleFont, Helper.WHITE, 1f);
                }
            }
            if (buttonPress > 0f) {
                buttonPress -= 16.666666666666f;
                SpriteSheet pressedBtn = getBuildButton(bt, true);
                //BuildingCost();
                if (pressedBtn != null && pressedBtn.destRec != null) {
                    pressedBtn.destRec.x = btn.destRec.x;
                    pressedBtn.destRec.y = btn.destRec.y;
                    Draw.Sprite(gfx, pressedBtn);
                }
            }
        }
    }


    private void buildTileList() {
        tiles.clear();
        gameTiles.clear();

        // Deserts
        addTileWithMeta(desert, "Sahara Desert", TileType.Desert);
        addTileWithMeta(desert2, "Gobi Desert", TileType.Desert);
        addTileWithMeta(desert3, "Kalahari Desert", TileType.Desert);
        addTileWithMeta(desert4, "Atacama Desert", TileType.Desert);
        addTileWithMeta(desert5, "Arabian Desert", TileType.Desert);

        // Fields / Plains (arable regions)
        addTileWithMeta(field, "Great Plains", TileType.Field);
        addTileWithMeta(field2, "Pampas", TileType.Field);
        addTileWithMeta(field3, "Canterbury Plains", TileType.Field);
        addTileWithMeta(field4, "North China Plain", TileType.Field);
        addTileWithMeta(field5, "Black Earth Fields", TileType.Field);

        // Oil fields
        addTileWithMeta(oilField, "Ghawar Oil Field", TileType.OilField);
        addTileWithMeta(oilField2, "Permian Basin", TileType.OilField);
        addTileWithMeta(oilField3, "Burgan Oil Field", TileType.OilField);
        addTileWithMeta(oilField4, "Kashagan Field", TileType.OilField);
        addTileWithMeta(oilField5, "Brent Oilfield", TileType.OilField);

        // Forests
        addTileWithMeta(forest, "Boreal Forest", TileType.Forest);
        addTileWithMeta(forest2, "Amazon Rainforest", TileType.Forest);
        addTileWithMeta(forest3, "Black Forest", TileType.Forest);
        addTileWithMeta(forest4, "Sherwood Forest", TileType.Forest);
        addTileWithMeta(forest5, "Daintree Rainforest", TileType.Forest);

        // Villages (real notable villages)
        addTileWithMeta(village, "Hallstatt", TileType.Village);
        addTileWithMeta(village2, "Giethoorn", TileType.Village);
        addTileWithMeta(village3, "Shirakawa-go", TileType.Village);
        addTileWithMeta(village4, "Oia", TileType.Village);
        addTileWithMeta(village5, "Reine", TileType.Village);

        // Mountain ranges
        addTileWithMeta(mountains, "Himalayas", TileType.Mountains);
        addTileWithMeta(mountains2, "Andes", TileType.Mountains);
        addTileWithMeta(mountains3, "Alps", TileType.Mountains);
        addTileWithMeta(mountains4, "Rocky Mountains", TileType.Mountains);
        addTileWithMeta(mountains5, "Ural Mountains", TileType.Mountains);
    }


    private void addTileWithMeta(SpriteSheet sheet, String title, TileType tileType) {
        if (sheet != null) {
            tiles.add(sheet);
            GameTile gt = new GameTile(sheet, title == "Hallstatt" ? 1 : 0, title == "Oia" ? 1 : 0 );
            gt.setTitle(title);
            gt.setTileType(tileType);
            gameTiles.add(gt);
        }
    }

    private void changeTurns() {
        if (fourtyFiveSec <= 0) {
            fourtyFiveSec = 45000;
            selectedTileIndex = -1;
            //hoveredTile = null;
            if (player1turn) {
                player1turn = false;
                player2turn = true;
            } else if (player2turn) {
                player2turn = false;
                player1turn = true;
            }
        }
    }

    @Override
    public void Update(GameContainer gc, float deltaTime) {
        SpriteSheet activeTile = null;
        //TODO: Add your update logic here, including user input, movement, physics, collision, ai, sound, etc.
        Vector2F mousePos = Input.GetMousePos();
        int points = 0;
        switch (gameState) {
            case MENU:
                //Get and implement menu interaction
                if (Helper.Intersects(playButton.destRec, mousePos)) {
                    if (Input.IsMouseButtonReleased(Input.MOUSE_LEFT)) {
                        gameState = GAMEPLAY;
                        tileControled[1] += 1;
                        tileControled[28] -= 1;
                    }

                }
                break;
            case SETTINGS:
                //Get and apply changes to game settings
                break;
            case INSTRUCTIONS:
                //Get user input to return to MENU
                break;
            case GAMEPLAY:
                //Implement standard game logic (input, update game objects, apply physics,
                //collision detection, update HUD elements, etc.)
                fourtyFiveSec -= deltaTime;
                changeTurns();

                if (Input.IsKeyPressed(KeyEvent.VK_ESCAPE)) {
                    gameState = PAUSE;
                }
                // Selection logic: on mouse release, update selection unless the click was on UI (panel or its button)
                if (Input.IsMouseButtonReleased(Input.MOUSE_LEFT)) {
                    boolean clickedUI = false;

                    // If there is a selected tile, treat clicks on its info panel or button as UI clicks
                    SpriteSheet st = getSelectedTile();
                    if (st != null && st.destRec != null) {
                        // Compute the panel rect exactly as drawn in drawTileInfoPanel
                        Rectangle panelRect = new Rectangle(st.destRec.x + 200, st.destRec.y + 100, 220, 350);

                        // Position the button for accurate hit testing (same as draw), using the correct type
                        GameTile selectedMeta = getSelectedGameTile();
                        BuildingType bt = (selectedMeta != null) ? selectedMeta.getAvailableBuilding() : null;
                        SpriteSheet btn = getBuildButton(bt, false);
                        if (btn != null) {
                            btn.destRec.x = st.destRec.x + 210;
                            btn.destRec.y = st.destRec.y + 140;
                        }

                        Vector2F mouse = Input.GetMousePos();
                        if (Helper.Intersects(panelRect, mouse) || (btn != null && Helper.Intersects(btn.destRec, mouse))) {
                            // Consume the click so selection does not change and the panel stays open
                            clickedUI = true;
                        }
                    }

                    if (!clickedUI) {
                        int idx = getTileIndexUnderMouse();
                        selectedTileIndex = (idx >= 0) ? idx : -1;
                        if (selectedTileIndex >= 0) {
                            GameTile meta = gameTiles.get(selectedTileIndex);
                            if (meta != null) {
                                System.out.println("Selected tile: " + meta.getTitle() + " (index " + selectedTileIndex + ")");
                            }
                        } else {
                            System.out.println("Selection cleared");
                        }
                    }

                }

                player1Points = Integer.toString(points);


                break;
            case PAUSE:
                //Get user input to unpause the game
                break;
            case ENDGAME:
                //Wait for final input based on end of game options (end, restart, etc.)
                break;
        }
    }

    @Override
    public void Draw(GameContainer gc, Graphics2D gfx) {
        //TODO: Add your draw logic here
        //The only other logic here should be selection logic (everything else should be in Update)

        switch (gameState) {
            case MENU:
                //Get and implement menu interaction
                Draw.Sprite(gfx, oceanBg);
                Draw.Sprite(gfx, playButton);
                break;
            case SETTINGS:
                //Get and apply changes to game settings
                break;
            case INSTRUCTIONS:
                //Get user input to return to MENU
                break;
            case GAMEPLAY:
                //Implement standard game logic (input, update game objects, apply physics,
                //collision detection, update HUD elements, etc.
                Draw.Sprite(gfx, oceanBg);
                Draw.Sprite(gfx, desert);
                Draw.Sprite(gfx, field);
                Draw.Sprite(gfx, oilField);
                Draw.Sprite(gfx, forest);
                Draw.Sprite(gfx, desert2);
                Draw.Sprite(gfx, field2);
                Draw.Sprite(gfx, oilField2);
                Draw.Sprite(gfx, forest2);
                Draw.Sprite(gfx, desert3);
                Draw.Sprite(gfx, field3);
                Draw.Sprite(gfx, oilField3);
                Draw.Sprite(gfx, forest3);
                Draw.Sprite(gfx, desert4);
                Draw.Sprite(gfx, field4);
                Draw.Sprite(gfx, oilField4);
                Draw.Sprite(gfx, forest4);
                Draw.Sprite(gfx, desert5);
                Draw.Sprite(gfx, field5);
                Draw.Sprite(gfx, oilField5);
                Draw.Sprite(gfx, forest5);
                Draw.Sprite(gfx, village);
                Draw.Sprite(gfx, village2);
                Draw.Sprite(gfx, village3);
                Draw.Sprite(gfx, village4);
                Draw.Sprite(gfx, village5);
                Draw.Sprite(gfx, mountains);
                Draw.Sprite(gfx, mountains2);
                Draw.Sprite(gfx, mountains3);
                Draw.Sprite(gfx, mountains4);
                Draw.Sprite(gfx, mountains5);
                //Draw.Sprite(gfx, blueDesert);


                Draw.FillRect(gfx, 0, 0, windowWidth, 80, Helper.BLACK, 0.6F);
                Draw.Text(gfx, "Points: ", 85, 45, titleFont, Helper.BLUE, 1f);
                Draw.Text(gfx, player1Points, 225, 45, titleFont, Helper.WHITE, 1f);
                Draw.Text(gfx, "Points: ", 1700, 45, titleFont, Helper.RED, 1f);
                Draw.Text(gfx, player2Points, 1850, 45, titleFont, Helper.WHITE, 1f);
                Draw.Text(gfx, "Turn: ", 900, 45, titleFont, Helper.WHITE, 1f);
                if (player1turn == true) {
                    Draw.Text(gfx, String.format("%.2f", fourtyFiveSec / 1000), 1020, 45, titleFont, Helper.BLUE, 1f);
                } else if (player2turn == true) {
                    Draw.Text(gfx, String.format("%.2f", fourtyFiveSec / 1000), 1020, 45, titleFont, Helper.RED, 1f);
                }

                Draw.Sprite(gfx, player1Icon);
                Draw.Sprite(gfx, player2Icon);


                if (Helper.Intersects(player1Icon.destRec, Input.GetMousePos())) {
                    Draw.FillRect(gfx, 50, 100, 220, 350, Helper.BLACK, 1f);
                    Draw.Text(gfx, "Iron: ", 60, 130, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player1Iron), 160, 130, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Oil: ", 60, 190, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player1Oil), 140, 190, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "People: ", 60, 240, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player1People), 210, 240, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Food: ", 60, 300, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player1Food), 180, 300, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Glass: ", 60, 360, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player1Glass), 185, 360, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Wood: ", 60, 420, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player1Wood), 190, 420, titleFont, Helper.WHITE, 1f);
                }
                if (Helper.Intersects(player2Icon.destRec, Input.GetMousePos())) {
                    Draw.FillRect(gfx, 1610, 100, 220, 350, Helper.BLACK, 1f);
                    Draw.Text(gfx, "Iron: ", 1620, 130, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player2Iron), 1720, 130, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Oil: ", 1620, 190, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player2Oil), 1700, 190, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "People: ", 1620, 240, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player2People), 1770, 240, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Food: ", 1620, 300, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player2Food), 1740, 300, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Glass: ", 1620, 360, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player2Glass), 1745, 360, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Wood: ", 1620, 420, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player2Wood), 1750, 420, titleFont, Helper.WHITE, 1f);
                }

                SpriteSheet selectedTile = getSelectedTile();
                GameTile selectedMeta = getSelectedGameTile();
                if (selectedTile != null) {
                    drawTileInfoPanel(gfx, selectedTile, selectedMeta);
                } else {
                    // No selection yet — show a hover preview panel
                    SpriteSheet hoveredTile = getTileUnderMouse();
                    GameTile hoveredMeta = getGameTileUnderMouse();
                    if (hoveredTile != null) {
                        drawTileInfoPanel(gfx, hoveredTile, hoveredMeta);
                    }
                }


                break;
            case PAUSE:
                //Get user input to unpause the game
                break;
            case ENDGAME:
                //Wait for final input based on end of game options (end, restart, etc.)
                break;
        }
    }
}
