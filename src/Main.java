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
    //levels to help but tile locations on grid
    int[] ylevel = new int[]{200, 290, 290, 335, 380};
    int[] xlevel = new int[]{480, 505, 630, 755, 880, 1005, 1130, 1255};

    //Turn timer
    float fourtyFiveSec = 45000;
    //button cooldown
    float buttonPress = 0;

    //player points
    String player1Points = "0";
    String player2Points = "0";

    //player turn
    Boolean player1turn = true;
    Boolean player2turn = false;

    //player Icons
    SpriteSheet player1Icon;
    SpriteSheet player2Icon;

    //BG
    SpriteSheet oceanBg;

    //Tiles
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


    // building buttons
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

    //Game title/logo
    SpriteSheet gameTitle;

    //non gameplay buttons
    SpriteSheet resumeButton;
    SpriteSheet quitButton;
    SpriteSheet playButton;

    //player resources
    PlayerResources player1Resources = new PlayerResources();
    PlayerResources player2Resources = new PlayerResources();

    //fonts
    Font titleFont = new Font("Arial", Font.BOLD + Font.ITALIC, 40);
    Font victoryFont = new Font("Arial", Font.BOLD, 200);

    //mousepos
    Vector2F mousePos = new Vector2F(0f, 0f);

    //arrays
    private final ArrayList<SpriteSheet> tiles = new ArrayList<>();
    private final ArrayList<GameTile> gameTiles = new ArrayList<>();
    private int selectedTileIndex = -1;

    //building bug fixer
    boolean isBuildingJustBuilt = false;


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
        //BG
        oceanBg = new SpriteSheet(LoadImage.FromFile("images/backgrounds/waterbganimation.png"),5,5, 0, 21, 5);
        oceanBg.destRec = new Rectangle(0, 0, windowWidth, windowHeight);
        oceanBg.StartAnimation();
        //Buttons
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
        //Tiles
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
        village3.destRec = new Rectangle(xlevel[7] + 22, 418, village3.GetFrameWidth() * 2, village3.GetFrameHeight() * 2);
        mountains3 = new SpriteSheet(LoadImage.FromFile("images/sprites/mountains.png"));
        mountains3.destRec = new Rectangle(xlevel[6] + 10, 652, mountains3.GetFrameWidth() * 2, mountains3.GetFrameHeight() * 2);
        desert3 = new SpriteSheet(LoadImage.FromFile("images/sprites/Desert.png"));
        desert3.destRec = new Rectangle(xlevel[7] + 20, 588, desert3.GetFrameWidth() * 2, desert3.GetFrameHeight() * 2);
        field3 = new SpriteSheet(LoadImage.FromFile("images/sprites/Field.png"));
        field3.destRec = new Rectangle(xlevel[5] + 11, 756, field3.GetFrameWidth() * 2, field3.GetFrameHeight() * 2);
        oilField3 = new SpriteSheet(LoadImage.FromFile("images/sprites/Oil Field.png"));
        oilField3.destRec = new Rectangle(xlevel[6] + 13, 215, oilField3.GetFrameWidth() * 2, oilField3.GetFrameHeight() * 2);
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

        //non tile images
        playButton = new SpriteSheet(LoadImage.FromFile("images/sprites/PlayButton.png"));
        playButton.destRec = new Rectangle(windowWidth / 2 - playButton.GetFrameWidth() / 2, windowHeight / 2 - playButton.GetFrameHeight() / 2 + 320, playButton.GetFrameWidth(), playButton.GetFrameHeight());
        player1Icon = new SpriteSheet(LoadImage.FromFile("images/sprites/Player1icon.png"));
        player1Icon.destRec = new Rectangle(0, 0, player1Icon.GetFrameWidth() / 3, player1Icon.GetFrameHeight() / 3);
        player2Icon = new SpriteSheet(LoadImage.FromFile("images/sprites/Player2icon.png"));
        player2Icon.destRec = new Rectangle(windowWidth - player2Icon.GetFrameWidth() - 80, -8, (int) (player2Icon.GetFrameWidth() / 2.5f), (int) (player2Icon.GetFrameHeight() / 2.5f));
        gameTitle = new SpriteSheet(LoadImage.FromFile("images/sprites/Game Title.png"));
        gameTitle.destRec = new Rectangle(windowWidth/2 - gameTitle.GetFrameWidth(), windowHeight/2 - 600, gameTitle.GetFrameWidth() * 2, gameTitle.GetFrameHeight() * 2);
        resumeButton = new SpriteSheet(LoadImage.FromFile("images/sprites/ResumeButton.png"));
        resumeButton.destRec = new Rectangle(windowWidth/2 - resumeButton.GetFrameWidth()/2, windowHeight/2 -350, resumeButton.GetFrameWidth(), resumeButton.GetFrameHeight());
        quitButton = new SpriteSheet(LoadImage.FromFile("images/sprites/QuitButton.png"));
        quitButton.destRec = new Rectangle(windowWidth/2 - quitButton.GetFrameWidth()/2, windowHeight/2, quitButton.GetFrameWidth(), quitButton.GetFrameHeight());


        buildTileList();
    }
    //gametile locator w/ mini function
    private GameTile findGameTile(ArrayList<GameTile> gameTiles, String tileTitle) {
        return gameTiles.stream().filter(item -> item.getTitle() == tileTitle).findFirst().orElse(null);
    }

    //check which tile is below mouse when left click released
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

    //store tile clicked
    private SpriteSheet getClickedTileOnRelease() {
        int idx = getClickedTileIndexOnRelease();
        return (idx >= 0 && idx < tiles.size()) ? tiles.get(idx) : null;
    }

    //get hovered tile
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


    //does the player have enough resources to build the respective building
    private boolean isPlayerHaveResourcesToBuild(BuildingType bt, PlayerResources playerResources) {
        if (bt == BuildingType.GlassFurnace) {
            return (playerResources.getIron() >= 1 && playerResources.getPeople() >= 1);
        }
        else if (bt == BuildingType.Farm) {
            return (playerResources.getPeople() >= 1);
        } 
        else if (bt == BuildingType.LumberMill) {
            return (playerResources.getIron() >= 1 && playerResources.getPeople() >= 1);
        } 
        else if (bt == BuildingType.OilDrill) {
            return (playerResources.getIron() >= 1 && playerResources.getPeople() >= 1);
        } 
        else if (bt == BuildingType.Mine) {
            return (playerResources.getWood() >= 1 && playerResources.getPeople() >= 1);
        } 
        else if (bt == BuildingType.House) {
            return (playerResources.getFood() >= 1 && playerResources.getWood() >= 1 && playerResources.getIron() >= 1 && playerResources.getGlass() >= 1);
        } else {
            return false;
        }
    }

    //UI to show user item costs
    private String BuildingCostUI(BuildingType bt) {
        if (bt == BuildingType.GlassFurnace) {
            return "Cost:, 1 Iron, 1 Person, 1 Oil for upkeep every 3 turns";
        }
        else if (bt == BuildingType.Farm) {
            return "Cost:, 1 person";
        }
        else if (bt == BuildingType.LumberMill) {
            return "Cost:, 1 person, 1 Iron, 1 Oil for upkeep every 3 turns";
        }
        else if (bt == BuildingType.OilDrill) {
            return "Cost:, 1 Iron, 1 Person, 1 Food for  upkeep every 3 turns";
        }
        else if (bt == BuildingType.Mine) {
            return "Cost:, 1 Wood, 1 Person, 1 Food for upkeep every 3 turns";
        }
        else if (bt == BuildingType.House) {
            return "Cost:, 1 Food, 1 Iron, 1 Wood, 1 Glass";
        } else {
            return "No info available";
        }
    }

    //get tile to show for hovering
    private SpriteSheet getTileUnderMouse() {
        int idx = getTileIndexUnderMouse();
        return (idx >= 0 && idx < tiles.size()) ? tiles.get(idx) : null;
    }
    //get information for hovering
    private GameTile getGameTileUnderMouse() {
        int idx = getTileIndexUnderMouse();
        return (idx >= 0 && idx < gameTiles.size()) ? gameTiles.get(idx) : null;
    }

    //get selected tile for clicked
    private SpriteSheet getSelectedTile() {
        return (selectedTileIndex >= 0 && selectedTileIndex < tiles.size()) ? tiles.get(selectedTileIndex) : null;
    }

    //get selected tile info
    private GameTile getSelectedGameTile() {
        return (selectedTileIndex >= 0 && selectedTileIndex < gameTiles.size()) ? gameTiles.get(selectedTileIndex) : null;
    }

    //gather which building button to show for each panel and what to change it to when clicked
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

    //draw the tile information, name, button, and cost
    private void drawTileInfoPanel(Graphics2D gfx, SpriteSheet tile, GameTile meta, boolean isTileInfoPanelSelected) {
        if (tile == null || tile.destRec == null) return;

        //panel fonts
        Font smallTitleFont = titleFont.deriveFont((float) Math.max(12, (int)(titleFont.getSize() * 0.8f)));
        Font costFont = titleFont.deriveFont((float) Math.max(12, (int)(titleFont.getSize() * 0.5f)));

        //show name
        String title = (meta != null && meta.getTitle() != null) ? meta.getTitle() : "";
        java.awt.FontMetrics fm = gfx.getFontMetrics(smallTitleFont);
        int lineH = fm.getHeight();
        int textAscent = fm.getAscent();

        int btnW = 0, btnH = 0;
        BuildingType bt = (meta != null) ? meta.getAvailableBuilding() : null;

        SpriteSheet btn = getBuildButton(bt, false);
        if (btn != null && btn.destRec != null) {
            btnW = Math.max(0, btn.destRec.width);
            btnH = Math.max(0, btn.destRec.height);
        }

        int padX = 16;
        int padY = 12;
        int minW = Math.max(350, btnW + padX * 2);
        int maxW = Math.max(minW, Math.min(520, windowWidth - 40));

       
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
                        lines.add(w);
                        current.setLength(0);
                    }
                }
            }
            if (current.length() > 0) lines.add(current.toString());
        } else {
            lines.add("");
        }

        int longest = 0;
        for (String ln : lines) longest = Math.max(longest, fm.stringWidth(ln));
        int panelW = Math.max(minW, Math.min(maxW, longest + padX * 2));

        int spacing = (btn != null) ? 12 : 0;
        int titleBlockH = lines.size() * lineH;
        int contentH = titleBlockH + spacing + ((btn != null) ? btnH : 0);
        int panelH = Math.max(250, contentH + padY * 2);

        int baseX = tile.destRec.x + tile.destRec.width + 20;

        int baseY = tile.destRec.y + 20;

        int panelX = Math.max(10, Math.min(baseX, windowWidth - panelW - 10));
        int panelY = Math.max(10, Math.min(baseY, windowHeight - panelH - 10));

        //draw background rectangle
        Draw.FillRect(gfx, panelX, panelY, panelW, panelH, Helper.BLACK, 0.6f);
        String[] buildingCostParts = BuildingCostUI(bt).split(", ");
        for (int i = 0; i < buildingCostParts.length; i++) {
            String part = buildingCostParts[i];
            Draw.Text(gfx, part, panelX + costFont.getSize(), panelY + 150 + i * (costFont.getSize() + 5), costFont, Helper.WHITE, 1f);
        }

        int textX = panelX + padX;
        int textY = panelY + padY + textAscent;
        for (int i = 0; i < lines.size(); i++) {
            String ln = lines.get(i);
            Draw.Text(gfx, ln, textX, textY + (i * lineH), smallTitleFont, Helper.WHITE, 1f);
        }

        //check if tile is selected to choose what to show
        if (btn != null && isTileInfoPanelSelected) {
            int btnX = panelX + padX;
            int btnY = panelY + padY + titleBlockH + spacing;
            btn.destRec.x = btnX;
            btn.destRec.y = btnY;
            Draw.Sprite(gfx, btn);

            //check if a building is already on tile where button is clicked
            boolean isBuildingAlreadyBuilt = meta.isBuildingBuilt();
            PlayerResources activePlayerResources = player1turn ? player1Resources : player2Resources;
            boolean isEnoughResourcesToBuild = isPlayerHaveResourcesToBuild(bt, activePlayerResources);
            if (Input.IsMouseButtonReleased(Input.MOUSE_LEFT) && Helper.Intersects(btn.destRec, Input.GetMousePos())) {
                buttonPress = 1000;

                if (!isBuildingAlreadyBuilt) {
                    if (isEnoughResourcesToBuild) {
                        meta.buildBuilding(player1turn);
                        isBuildingJustBuilt = true;
                        isEnoughResourcesToBuild = activePlayerResources.useResourcesForBuilding(bt);
                    }
                }
            }
            //button ui
            if (buttonPress > 0f) {
                buttonPress -= 16.666666666666f;
                if (isTileInfoPanelSelected) {
                    SpriteSheet pressedBtn = getBuildButton(bt, true);
                    if (pressedBtn != null && pressedBtn.destRec != null) {
                        pressedBtn.destRec.x = btn.destRec.x;
                        pressedBtn.destRec.y = btn.destRec.y;
                        Draw.Sprite(gfx, pressedBtn);
                    }
                    if (isBuildingAlreadyBuilt && !isBuildingJustBuilt) {
                        Draw.FillRect(gfx, windowWidth / 2 - 250, windowHeight / 2 - 50, 500, 100, Helper.BLACK, 1F);
                        Draw.Text(gfx, "Building already exists", windowWidth / 2 - 230, windowHeight / 2, titleFont, Helper.WHITE, 1f);
                    }
                    if (!isEnoughResourcesToBuild) {
                        Draw.FillRect(gfx, windowWidth / 2 - 450, windowHeight / 2 - 50, 900, 100, Helper.BLACK, 1F);
                        Draw.Text(gfx, "Not enough resources to build " + bt.toString(), windowWidth / 2 - 430, windowHeight / 2, titleFont, Helper.WHITE, 1f);
                    }
                }
            } else if (buttonPress < 30f) {
                isBuildingJustBuilt = false;
            }
        }
    }

    //who controls tile
    public void tint(GameTile tile, Graphics2D gfx) {
    	Color color;
    	if (tile.getRedPoints() == tile.getBluePoints())
    	{
    		color = Helper.GRAY;
    	}
    	else if (tile.getRedPoints() >= tile.getBluePoints())
    	{
    		color = Helper.RED;
    	}
    	else
    	{
    		color = Helper.BLUE;
    	}
        Draw.Rect(gfx, tile.getIcon().destRec.x + tile.getIcon().GetFrameWidth()/2, tile.getIcon().destRec.y + tile.getIcon().GetFrameHeight()/2, tile.getIcon().GetFrameWidth(), tile.getIcon().GetFrameHeight(), 5, color, 1f);
    }
    //System to control tiles and who controls them
    private void buildTileList() {
        tiles.clear();
        gameTiles.clear();

        // Deserts
        addTileWithMeta(desert, "Sahara Desert", TileType.Desert, 50, 50);
        addTileWithMeta(desert2, "Gobi Desert", TileType.Desert, 50, 50);
        addTileWithMeta(desert3, "Kalahari Desert", TileType.Desert, 50, 50);
        addTileWithMeta(desert4, "Atacama Desert", TileType.Desert, 50, 50);
        addTileWithMeta(desert5, "Arabian Desert", TileType.Desert, 50, 50);

        // Fields
        addTileWithMeta(field, "Great Plains", TileType.Field, 50, 50);
        addTileWithMeta(field2, "Pampas", TileType.Field, 50, 50);
        addTileWithMeta(field3, "Canterbury Plains", TileType.Field, 50, 50);
        addTileWithMeta(field4, "North China Plain", TileType.Field, 50, 50);
        addTileWithMeta(field5, "Black Earth Fields", TileType.Field, 50, 50);

        // Oil fields
        addTileWithMeta(oilField, "Ghawar Oil Field", TileType.OilField, 50, 50);
        addTileWithMeta(oilField2, "Permian Basin", TileType.OilField, 50, 50);
        addTileWithMeta(oilField3, "Burgan Oil Field", TileType.OilField, 50, 50);
        addTileWithMeta(oilField4, "Kashagan Field", TileType.OilField, 50, 50);
        addTileWithMeta(oilField5, "Brent Oilfield", TileType.OilField, 50, 50);

        // Forests
        addTileWithMeta(forest, "Boreal Forest", TileType.Forest, 50, 50);
        addTileWithMeta(forest2, "Amazon Rainforest", TileType.Forest, 50, 50);
        addTileWithMeta(forest3, "Black Forest", TileType.Forest, 50, 50);
        addTileWithMeta(forest4, "Sherwood Forest", TileType.Forest, 50, 50);
        addTileWithMeta(forest5, "Daintree Rainforest", TileType.Forest, 50, 50);

        // Villages
        addTileWithMeta(village, "Hallstatt", TileType.Village, 50, 50);
        addTileWithMeta(village2, "Giethoorn", TileType.Village, 50, 50);
        addTileWithMeta(village3, "Shirakawa-go", TileType.Village, 100, 0);
        addTileWithMeta(village4, "Oia", TileType.Village, 0, 100);
        addTileWithMeta(village5, "Reine", TileType.Village, 50, 50);

        // Mountain ranges
        addTileWithMeta(mountains, "Himalayas", TileType.Mountains, 50, 50);
        addTileWithMeta(mountains2, "Andes", TileType.Mountains, 50, 50);
        addTileWithMeta(mountains3, "Alps", TileType.Mountains, 50, 50);
        addTileWithMeta(mountains4, "Rocky Mountains", TileType.Mountains, 50, 50);
        addTileWithMeta(mountains5, "Ural Mountains", TileType.Mountains, 50, 50);
    }

    //set up the gametile for logic
    private void addTileWithMeta(SpriteSheet sheet, String title, TileType tileType, int redPoints, int bluePoints) {
        if (sheet != null) {
            tiles.add(sheet);
            GameTile gt = new GameTile(sheet, title == "Hallstatt" ? 1 : 0, title == "Oia" ? 1 : 0, null);
            gt.setTitle(title);
            gt.setTileType(tileType);
            gameTiles.add(gt);
            gt.setRedPoints(redPoints);
            gt.setBluePoints(bluePoints);
        }
    }
    //switch player turns
    private void changeTurns() {
        if (fourtyFiveSec <= 0) {
            fourtyFiveSec = 45000;
            selectedTileIndex = -1;
            updatePlayerResources();
            if (player1turn) {
                player1turn = false;
                player2turn = true;
            } else if (player2turn) {
                player2turn = false;
                player1turn = true;
            }
        }
    }

    //calculate player points based on tile control
    private void updatePlayerPoints() {
        int player1Count = 0;
        int player2Count = 0;

        for (GameTile tile : gameTiles) {
            if (tile.getBluePoints() > 50) {
                player1Count++;
            } else if (tile.getRedPoints() > 50) {
                player2Count++;
            }
        }

        player1Points = Integer.toString(player1Count);
        player2Points = Integer.toString(player2Count);
    }

    // Updating player resources based on tile control and buildings
    private void updatePlayerResources() {
        int player1Count = 0;
        int player2Count = 0;

        for (GameTile tile : gameTiles) {
            if (player1turn) {
                if (tile.getBluePoints() > 50) {
                    player1Resources.assignResources(tile.getResources());
                }
            }
        }

        player1Points = Integer.toString(player1Count);
        player2Points = Integer.toString(player2Count);
    }

    @Override
    public void Update(GameContainer gc, float deltaTime) {
        Vector2F mousePos = Input.GetMousePos();
        switch (gameState) {
            case MENU:
                fourtyFiveSec = 15000;
                //begin game
                if (Helper.Intersects(playButton.destRec, mousePos)) {
                    if (Input.IsMouseButtonReleased(Input.MOUSE_LEFT)) {
                        gameState = GAMEPLAY;
                    }

                }
                break;
            case SETTINGS:
                //send player to menu
                if(Helper.Intersects(quitButton.destRec, Input.GetMousePos()))
                {
                    if (Input.IsMouseButtonReleased(Input.MOUSE_LEFT))
                    {
                        gameState = MENU;
                    }
                }
                //go back to gameplay
                if(Helper.Intersects(resumeButton.destRec, Input.GetMousePos()))
                {
                    if (Input.IsMouseButtonReleased(Input.MOUSE_LEFT))
                    {
                        gameState = GAMEPLAY;
                    }
                }
                break;
            case INSTRUCTIONS:

                break;
            case GAMEPLAY:
                //Implement standard game logic (input, update game objects, apply physics,
                //collision detection, update HUD elements, etc.)
                fourtyFiveSec -= deltaTime;
                changeTurns();
                updatePlayerPoints();
                //go to settings
                if (Input.IsKeyPressed(KeyEvent.VK_ESCAPE)) {
                    gameState = SETTINGS;
                }
                // Selection logic: on mouse release, update selection unless the click was on UI (panel or its button)
                if (Input.IsMouseButtonReleased(Input.MOUSE_LEFT)) {
                    boolean clickedUI = false;

                    //get tile
                    SpriteSheet st = getSelectedTile();
                    if (st != null && st.destRec != null) {
                        Rectangle panelRect = new Rectangle(st.destRec.x + 200, st.destRec.y + 100, 220, 350);
                        //tile logic

                        GameTile selectedMeta = getSelectedGameTile();
                        BuildingType bt = (selectedMeta != null) ? selectedMeta.getAvailableBuilding() : null;
                        SpriteSheet btn = getBuildButton(bt, false);
                        if (btn != null) {
                            btn.destRec.x = st.destRec.x + 210;
                            btn.destRec.y = st.destRec.y + 140;
                        }
                        //is player using panel
                        Vector2F mouse = Input.GetMousePos();
                        if (Helper.Intersects(panelRect, mouse) || (btn != null && Helper.Intersects(btn.destRec, mouse))) {
                            clickedUI = true;
                        }
                    }
                    //testing output
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
                //victory
                if (player1Points.equals("20")) {
                    gameState = ENDGAME;
                }
                if (player2Points.equals("20")) {
                    gameState = ENDGAME;
                }


                break;
            case PAUSE:
                //Get user input to unpause the game
                break;
            case ENDGAME:
                //Wait for final input based on end of game options (end, restart, etc.)
                //quitgame
                if(Helper.Intersects(quitButton.destRec, Input.GetMousePos()))
                {
                    if (Input.IsMouseButtonReleased(Input.MOUSE_LEFT))
                    {
                        gameState = MENU;
                    }
                }
                break;
        }
    }

    @Override
    public void Draw(GameContainer gc, Graphics2D gfx) {
        //TODO: Add your draw logic here
        //The only other logic here should be selection logic (everything else should be in Update)

        switch (gameState) {
            case MENU:
                //Get and implement menu interactions
                Draw.Sprite(gfx, oceanBg);
                Draw.Sprite(gfx, playButton);
                Draw.Sprite(gfx, gameTitle);
                break;
            case SETTINGS:
                //Get and apply changes to game settings
                //BG
                Draw.Sprite(gfx, oceanBg);
                //tiles
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

                //actions & cover
                Draw.FillRect(gfx, 0, 0, windowWidth, windowHeight, Helper.BLACK, 0.7f);
                Draw.Sprite(gfx, resumeButton);
                Draw.Sprite(gfx, quitButton);
                break;
            case INSTRUCTIONS:
                //Get user input to return to MENU
                break;
            case GAMEPLAY:
                //Implement standard game logic (input, update game objects, apply physics,
                //collision detection, update HUD elements, etc.

                // background
                Draw.Sprite(gfx, oceanBg);

                // tiles
                GameTile currentTile;
                currentTile = findGameTile(gameTiles, "Sahara Desert");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Great Plains");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Ghawar Oil Field");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Boreal Forest");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Gobi Desert");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Pampas");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Permian Basin");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Amazon Rainforest");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Kalahari Desert");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Canterbury Plains");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Burgan Oil Field");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Black Forest");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Atacama Desert");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Atacama Desert");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "North China Plain");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Kashagan Field");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Sherwood Forest");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Arabian Desert");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Black Earth Fields");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Brent Oilfield");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Daintree Rainforest");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Hallstatt");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Giethoorn");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Shirakawa-go");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Oia");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Reine");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Himalayas");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Andes");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Alps");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Rocky Mountains");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);

                currentTile = findGameTile(gameTiles, "Ural Mountains");
                Draw.Sprite(gfx, currentTile.getIcon());
                tint(currentTile, gfx);
                
                //Draw.Sprite(gfx, blueDesert);

                //point UI
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
                //player Icons and hovering
                Draw.Sprite(gfx, player1Icon);
                Draw.Sprite(gfx, player2Icon);

                if (Helper.Intersects(player1Icon.destRec, Input.GetMousePos())) {
                    Draw.FillRect(gfx, 50, 100, 220, 350, Helper.BLACK, 1f);
                    Draw.Text(gfx, "Iron: ", 60, 130, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player1Resources.getIron()), 160, 130, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Oil: ", 60, 190, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player1Resources.getOil()), 140, 190, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "People: ", 60, 240, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player1Resources.getPeople()), 210, 240, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Food: ", 60, 300, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player1Resources.getFood()), 180, 300, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Glass: ", 60, 360, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player1Resources.getGlass()), 185, 360, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Wood: ", 60, 420, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player1Resources.getWood()), 190, 420, titleFont, Helper.WHITE, 1f);
                }
                if (Helper.Intersects(player2Icon.destRec, Input.GetMousePos())) {
                    Draw.FillRect(gfx, 1610, 100, 220, 350, Helper.BLACK, 1f);
                    Draw.Text(gfx, "Iron: ", 1620, 130, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player2Resources.getIron()), 1720, 130, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Oil: ", 1620, 190, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player2Resources.getOil()), 1700, 190, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "People: ", 1620, 240, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player2Resources.getPeople()), 1770, 240, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Food: ", 1620, 300, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player2Resources.getFood()), 1740, 300, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Glass: ", 1620, 360, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player2Resources.getGlass()), 1745, 360, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Wood: ", 1620, 420, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, Integer.toString(player2Resources.getWood()), 1750, 420, titleFont, Helper.WHITE, 1f);
                }
                //pull player actions
                SpriteSheet selectedTile = getSelectedTile();
                GameTile selectedMeta = getSelectedGameTile();
                if (selectedTile != null) {
                    drawTileInfoPanel(gfx, selectedTile, selectedMeta, true);
                } else {
                    SpriteSheet hoveredTile = getTileUnderMouse();
                    GameTile hoveredMeta = getGameTileUnderMouse();
                    if (hoveredTile != null) {
                        drawTileInfoPanel(gfx, hoveredTile, hoveredMeta, false);
                    }
                }


                break;
            case PAUSE:
                //Get user input to unpause the game
                break;
            case ENDGAME:
                //Wait for final input based on end of game options (end, restart, etc.)
                //bg
                Draw.Sprite(gfx, oceanBg);
                //tiles
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
                //victory screen and quit button
                Draw.FillRect(gfx, 0, 0, windowWidth, windowHeight, Helper.BLACK, 0.7f);
                Draw.Sprite(gfx, quitButton);
                Draw.Text(gfx, "VICTORY", 550, windowHeight/2 - 50, victoryFont, Helper.YELLOW, 1f);
                break;
        }
    }
}
