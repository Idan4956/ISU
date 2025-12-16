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

public class Main extends AbstractGame
{
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
	private static int windowWidth = 1920;	//For fullscreen mode set these next two to screenWidth and screenHeight
	private static int windowHeight = 1080;
	private static int fps = 60;

	//Store and set the initial game state, typically MENU to start
	private int gameState = MENU;

	/////////////////////////////////////////////////////////////////////////////////////
	// Define your Global variables and constants here (They do NOT need to be static) //
	/////////////////////////////////////////////////////////////////////////////////////

    int [] ylevel = new int[] {200, 290, 290, 335, 380};
    int [] xlevel = new int[] {480, 505, 630, 755, 880, 1005, 1130, 1255};

    int [] tileControled = new int[] {50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50};

    float fourtyFiveSec = 45000;
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

    Font titleFont = new Font("Arial", Font.BOLD + Font.ITALIC, 40);
    Vector2F mousePos = new Vector2F(0f,0f);

    // List of clickable tiles (populated in LoadContent in draw order)
    private final ArrayList<SpriteSheet> tiles = new ArrayList<>();
    // Metadata objects for each tile (same order as 'tiles')
    private final ArrayList<GameTile> gameTiles = new ArrayList<>();
    // Persistently selected tile index (-1 when none selected)
    private int selectedTileIndex = -1;

	public static void main(String[] args)
	{
		GameContainer gameContainer = new GameContainer(new Main(), gameName, windowWidth, windowHeight, fps);
		gameContainer.Start();
	}


	@Override
 public void LoadContent(GameContainer gc)
 {
		//TODO: This subprogram automatically happens only once, just before the actual game loop starts.
		//It should never be manually called, the Engine will call it for you.
  //Load images, sounds and set up any data
  // NOTE: Engine likely loads from classpath; with 'res' marked as resources root, use paths starting at 'images/...'
  oceanBg = new SpriteSheet(LoadImage.FromFile("images/backgrounds/OIP.png"));
  oceanBg.destRec = new Rectangle(0,0, windowWidth, windowHeight);
  village = new SpriteSheet(LoadImage.FromFile("images/sprites/village.png"));
  village.destRec = new Rectangle(xlevel[4],ylevel[0], village.GetFrameWidth() * 2, village.GetFrameHeight() * 2);
  mountains = new SpriteSheet(LoadImage.FromFile("images/sprites/mountains.png"));
  mountains.destRec = new Rectangle (xlevel[5], ylevel[2]-15, mountains.GetFrameWidth() * 2, mountains.GetFrameHeight() * 2);
  desert = new SpriteSheet(LoadImage.FromFile("images/sprites/Desert.png"));
  desert.destRec = new Rectangle (xlevel[6] + 12, ylevel[3] + 30, desert.GetFrameWidth() * 2, desert.GetFrameHeight() * 2);
  field = new SpriteSheet(LoadImage.FromFile("images/sprites/Field.png"));
  field.destRec = new Rectangle (xlevel[4], 380, field.GetFrameWidth() * 2, field.GetFrameHeight() * 2);
  oilField = new SpriteSheet(LoadImage.FromFile("images/sprites/Oil Field.png"));
  oilField.destRec = new Rectangle (xlevel[4] + 5, 525, oilField.GetFrameWidth() * 2, oilField.GetFrameHeight() * 2);
  forest = new SpriteSheet(LoadImage.FromFile("images/sprites/Forest.png"));
  forest.destRec = new Rectangle (xlevel[4] + 5, 675, forest.GetFrameWidth() * 2, forest.GetFrameHeight() * 2);
  village2 = new SpriteSheet(LoadImage.FromFile("images/sprites/village.png"));
  village2.destRec = new Rectangle(xlevel[6] + 17,495, village2.GetFrameWidth() * 2, village2.GetFrameHeight() * 2);
  mountains2 = new SpriteSheet(LoadImage.FromFile("images/sprites/mountains.png"));
  mountains2.destRec = new Rectangle (xlevel[5], 425, mountains2.GetFrameWidth() * 2, mountains2.GetFrameHeight() * 2);
  desert2 = new SpriteSheet(LoadImage.FromFile("images/sprites/Desert.png"));
  desert2.destRec = new Rectangle (xlevel[5] + 10, 595, desert2.GetFrameWidth() * 2, desert2.GetFrameHeight() * 2);
  field2 = new SpriteSheet(LoadImage.FromFile("images/sprites/Field.png"));
  field2.destRec = new Rectangle (xlevel[4], 75, field2.GetFrameWidth() * 2, field2.GetFrameHeight() * 2);
  oilField2 = new SpriteSheet(LoadImage.FromFile("images/sprites/Oil Field.png"));
  oilField2.destRec = new Rectangle (xlevel	[4] + 7, 831, oilField2.GetFrameWidth() * 2, oilField2.GetFrameHeight() * 2);
  forest2 = new SpriteSheet(LoadImage.FromFile("images/sprites/Forest.png"));
  forest2.destRec = new Rectangle (xlevel[5] + 5, 137, forest2.GetFrameWidth() * 2, forest2.GetFrameHeight() * 2);
  village3 = new SpriteSheet(LoadImage.FromFile("images/sprites/village.png"));
  village3.destRec = new Rectangle(xlevel[7] + 22,418, village2.GetFrameWidth() * 2, village2.GetFrameHeight() * 2);
  mountains3 = new SpriteSheet(LoadImage.FromFile("images/sprites/mountains.png"));
  mountains3.destRec = new Rectangle (xlevel[6] + 10, 652, mountains2.GetFrameWidth() * 2, mountains2.GetFrameHeight() * 2);
  desert3 = new SpriteSheet(LoadImage.FromFile("images/sprites/Desert.png"));
  desert3.destRec = new Rectangle (xlevel[7] + 20, 588, desert2.GetFrameWidth() * 2, desert2.GetFrameHeight() * 2);
  field3 = new SpriteSheet(LoadImage.FromFile("images/sprites/Field.png"));
  field3.destRec = new Rectangle (xlevel[5] + 11, 756, field2.GetFrameWidth() * 2, field2.GetFrameHeight() * 2);
  oilField3 = new SpriteSheet(LoadImage.FromFile("images/sprites/Oil Field.png"));
  oilField3.destRec = new Rectangle (xlevel[6] + 13, 215, oilField2.GetFrameWidth() * 2, oilField2.GetFrameHeight() * 2);
  forest3 = new SpriteSheet(LoadImage.FromFile("images/sprites/Forest.png"));
  forest3.destRec = new Rectangle (xlevel[7] + 19, 283, forest2.GetFrameWidth() * 2, forest2.GetFrameHeight() * 2);
  village4 = new SpriteSheet(LoadImage.FromFile("images/sprites/village.png"));
  village4.destRec = new Rectangle(xlevel[1] - 16,430, village4.GetFrameWidth() * 2, village4.GetFrameHeight() * 2);
  mountains4 = new SpriteSheet(LoadImage.FromFile("images/sprites/mountains.png"));
  mountains4.destRec = new Rectangle (xlevel[3] - 12, 123, mountains4.GetFrameWidth() * 2, mountains4.GetFrameHeight() * 2);
  desert4 = new SpriteSheet(LoadImage.FromFile("images/sprites/Desert.png"));
  desert4.destRec = new Rectangle (xlevel[3] - 5, 292, desert4.GetFrameWidth() * 2, desert4.GetFrameHeight() * 2);
  field4 = new SpriteSheet(LoadImage.FromFile("images/sprites/Field.png"));
  field4.destRec = new Rectangle (xlevel[2] - 11, 377, field4.GetFrameWidth() * 2, field4.GetFrameHeight() * 2);
  oilField4 = new SpriteSheet(LoadImage.FromFile("images/sprites/Oil Field.png"));
  oilField4.destRec = new Rectangle (xlevel[3] - 2, 602, oilField4.GetFrameWidth() * 2, oilField4.GetFrameHeight() * 2);
  forest4 = new SpriteSheet(LoadImage.FromFile("images/sprites/Forest.png"));
  forest4.destRec = new Rectangle (xlevel[3] - 5, 445, forest4.GetFrameWidth() * 2, forest4.GetFrameHeight() * 2);
  village5 = new SpriteSheet(LoadImage.FromFile("images/sprites/village.png"));
  village5.destRec = new Rectangle(xlevel[2] - 11,198, village5.GetFrameWidth() * 2, village5.GetFrameHeight() * 2);
  mountains5 = new SpriteSheet(LoadImage.FromFile("images/sprites/mountains.png"));
  mountains5.destRec = new Rectangle (xlevel[2] - 18, 510, mountains5.GetFrameWidth() * 2, mountains5.GetFrameHeight() * 2);
  desert5 = new SpriteSheet(LoadImage.FromFile("images/sprites/Desert.png"));
  desert5.destRec = new Rectangle (xlevel[1] - 20, 602, desert5.GetFrameWidth() * 2, desert5.GetFrameHeight() * 2);
  field5 = new SpriteSheet(LoadImage.FromFile("images/sprites/Field.png"));
  field5.destRec = new Rectangle (xlevel[3] - 2, 762, field5.GetFrameWidth() * 2, field5.GetFrameHeight() * 2);
  oilField5 = new SpriteSheet(LoadImage.FromFile("images/sprites/Oil Field.png"));
  oilField5.destRec = new Rectangle (xlevel[1] - 15, 296, oilField5.GetFrameWidth() * 2, oilField5.GetFrameHeight() * 2);
  forest5 = new SpriteSheet(LoadImage.FromFile("images/sprites/Forest.png"));
  forest5.destRec = new Rectangle (xlevel[2] - 11, 677, forest5.GetFrameWidth() * 2, forest5.GetFrameHeight() * 2);
  playButton = new SpriteSheet(LoadImage.FromFile("images/sprites/PlayButton.png"));
  playButton.destRec = new Rectangle (windowWidth / 2 - playButton.GetFrameWidth() / 2, windowHeight / 2 - playButton.GetFrameHeight() / 2 + 120, playButton.GetFrameWidth(), playButton.GetFrameHeight());
  player1Icon = new SpriteSheet(LoadImage.FromFile("images/sprites/Player1icon.png"));
  player1Icon.destRec = new Rectangle (0,0, player1Icon.GetFrameWidth()/3, player1Icon.GetFrameHeight()/3);
  player2Icon = new SpriteSheet(LoadImage.FromFile("images/sprites/Player2icon.png"));
  player2Icon.destRec = new Rectangle (windowWidth - player2Icon.GetFrameWidth() - 80, -8, (int)(player2Icon.GetFrameWidth() /2.5f), (int) (player2Icon.GetFrameHeight() /2.5f));

  // Build the clickable tiles list once all tiles are positioned
  buildTileList();
    }

    // Returns the index (in draw order) of the tile under the mouse when the left mouse button is released.
    private int getClickedTileIndexOnRelease()
    {
        if (!Input.IsMouseButtonReleased(Input.MOUSE_LEFT))
        {
            return -1;
        }

        Vector2F mousePos = Input.GetMousePos();

        for (int i = tiles.size() - 1; i >= 0; i--)
        {
            SpriteSheet tile = tiles.get(i);
            if (tile != null && tile.destRec != null && Helper.Intersects(tile.destRec, mousePos))
            {
                return i;
            }
        }
        return -1;
    }


    private SpriteSheet getClickedTileOnRelease()
    {
        int idx = getClickedTileIndexOnRelease();
        return (idx >= 0 && idx < tiles.size()) ? tiles.get(idx) : null;
    }


    private GameTile getClickedGameTileOnRelease()
    {
        int idx = getClickedTileIndexOnRelease();
        return (idx >= 0 && idx < gameTiles.size()) ? gameTiles.get(idx) : null;
    }


    // Returns the index (in draw order from back to front) of the tile currently under the mouse.
    // Does NOT require any mouse button state.
    private int getTileIndexUnderMouse()
    {
        Vector2F mouse = Input.GetMousePos();
        for (int i = tiles.size() - 1; i >= 0; i--)
        {
            SpriteSheet tile = tiles.get(i);
            if (tile != null && tile.destRec != null && Helper.Intersects(tile.destRec, mouse))
            {
                return i;
            }
        }
        return -1;
    }

    // Returns the SpriteSheet of the tile currently under the mouse, or null if none.
    private SpriteSheet getTileUnderMouse()
    {
        int idx = getTileIndexUnderMouse();
        return (idx >= 0 && idx < tiles.size()) ? tiles.get(idx) : null;
    }

    // Returns the GameTile metadata of the tile currently under the mouse, or null if none.
    private GameTile getGameTileUnderMouse()
    {
        int idx = getTileIndexUnderMouse();
        return (idx >= 0 && idx < gameTiles.size()) ? gameTiles.get(idx) : null;
    }

    private SpriteSheet getSelectedTile()
    {
        return (selectedTileIndex >= 0 && selectedTileIndex < tiles.size()) ? tiles.get(selectedTileIndex) : null;
    }

    private GameTile getSelectedGameTile()
    {
        return (selectedTileIndex >= 0 && selectedTileIndex < gameTiles.size()) ? gameTiles.get(selectedTileIndex) : null;
    }

    private void drawTileInfoPanel(Graphics2D gfx, SpriteSheet tile, GameTile meta)
    {
        if (tile == null || tile.destRec == null) return;
        Draw.FillRect(gfx, tile.destRec.x + 200, tile.destRec.y + 100, 220, 350, Helper.BLACK, 0.6f);
        if (meta != null && meta.getTitle() != null)
        {
            Draw.Text(gfx, meta.getTitle(), tile.destRec.x + 210, tile.destRec.y + 140, titleFont, Helper.WHITE, 1f);
        }
    }


    private void buildTileList()
    {
        tiles.clear();
        gameTiles.clear();

        addTileWithMeta(desert,    "Desert");
        addTileWithMeta(field,     "Field");
        addTileWithMeta(oilField,  "Oil Field");
        addTileWithMeta(forest,    "Forest");
        addTileWithMeta(desert2,   "Desert 2");
        addTileWithMeta(field2,    "Field 2");
        addTileWithMeta(oilField2, "Oil Field 2");
        addTileWithMeta(forest2,   "Forest 2");
        addTileWithMeta(desert3,   "Desert 3");
        addTileWithMeta(field3,    "Field 3");
        addTileWithMeta(oilField3, "Oil Field 3");
        addTileWithMeta(forest3,   "Forest 3");
        addTileWithMeta(desert4,   "Desert 4");
        addTileWithMeta(field4,    "Field 4");
        addTileWithMeta(oilField4, "Oil Field 4");
        addTileWithMeta(forest4,   "Forest 4");
        addTileWithMeta(desert5,   "Desert 5");
        addTileWithMeta(field5,    "Field 5");
        addTileWithMeta(oilField5, "Oil Field 5");
        addTileWithMeta(forest5,   "Forest 5");
        addTileWithMeta(village,   "Village");
        addTileWithMeta(village2,  "Village 2");
        addTileWithMeta(village3,  "Village 3");
        addTileWithMeta(village4,  "Village 4");
        addTileWithMeta(village5,  "Village 5");
        addTileWithMeta(mountains,  "Mountains");
        addTileWithMeta(mountains2, "Mountains 2");
        addTileWithMeta(mountains3, "Mountains 3");
        addTileWithMeta(mountains4, "Mountains 4");
        addTileWithMeta(mountains5, "Mountains 5");
    }


    private void addTileWithMeta(SpriteSheet sheet, String title)
    {
        if (sheet != null)
        {
            tiles.add(sheet);
            GameTile gt = new GameTile();
            gt.setTitle(title);
            gameTiles.add(gt);
        }
    }

	@Override
	public void Update(GameContainer gc, float deltaTime)
	{
        SpriteSheet activeTile = null;
		//TODO: Add your update logic here, including user input, movement, physics, collision, ai, sound, etc.
        Vector2F mousePos = Input.GetMousePos();
        int points = 0;
		switch(gameState)
	    {
	    	case MENU:
	    		//Get and implement menu interaction
                if (Helper.Intersects(playButton.destRec, mousePos))
                {
                    if (Input.IsMouseButtonReleased(Input.MOUSE_LEFT))
                    {
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

                if (Input.IsKeyPressed(KeyEvent.VK_ESCAPE))
                {
                    gameState = PAUSE;
                }
                // Selection logic: on mouse release, lock selection to tile under mouse; clicking water clears it
                if (Input.IsMouseButtonReleased(Input.MOUSE_LEFT))
                {
                    int idx = getTileIndexUnderMouse();
                    selectedTileIndex = (idx >= 0) ? idx : -1;
                    if (selectedTileIndex >= 0)
                    {
                        GameTile meta = gameTiles.get(selectedTileIndex);
                        if (meta != null)
                        {
                            System.out.println("Selected tile: " + meta.getTitle() + " (index " + selectedTileIndex + ")");
                        }
                    }
                    else
                    {
                        System.out.println("Selection cleared");
                    }
                }

                    player1Points = Integer.toString(points);
                if (player1turn == true && fourtyFiveSec <= 0)
                {
                    player1turn = false;
                    player2turn = true;
                    fourtyFiveSec = 45000;
                }
                else if (player2turn == true && fourtyFiveSec <= 0)
                {
                    player2turn = false;
                    player1turn = true;
                    fourtyFiveSec = 45000;
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

	@Override
	public void Draw(GameContainer gc, Graphics2D gfx)
	{
		//TODO: Add your draw logic here
		//The only other logic here should be selection logic (everything else should be in Update)

		switch(gameState) {
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


                Draw.FillRect(gfx, 0, 0, windowWidth, 80, Helper.BLACK, 0.6F);
                Draw.Text(gfx, "Points: ", 85, 45, titleFont, Helper.BLUE, 1f);
                Draw.Text(gfx, player1Points, 225, 45, titleFont, Helper.WHITE, 1f);
                Draw.Text(gfx, "Points: ", 1700, 45, titleFont, Helper.RED, 1f);
                Draw.Text(gfx, player2Points, 1850, 45, titleFont, Helper.WHITE, 1f);
                Draw.Text(gfx, "Turn: ", 900, 45, titleFont, Helper.WHITE, 1f);
                if (player1turn == true)
                {
                    Draw.Text(gfx, String.format("%.2f", fourtyFiveSec / 1000), 1020, 45, titleFont, Helper.BLUE, 1f);
                }
                else if (player2turn == true)
                {
                    Draw.Text(gfx, String.format("%.2f", fourtyFiveSec / 1000), 1020, 45, titleFont, Helper.RED, 1f);
                }

                Draw.Sprite(gfx, player1Icon);
                Draw.Sprite(gfx, player2Icon);



                if (Helper.Intersects(player1Icon.destRec, Input.GetMousePos()))
                {
                    Draw.FillRect(gfx, 50, 100, 220, 350, Helper.BLACK, 1f);
                    Draw.Text(gfx, "Iron: ", 60, 130, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Oil: ", 60, 190, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "People: ", 60, 240, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Food: ", 60, 300, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Glass: ", 60, 360, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Wood: ", 60, 420, titleFont, Helper.WHITE, 1f);
                }
                if (Helper.Intersects(player2Icon.destRec, Input.GetMousePos()))
                {
                    Draw.FillRect(gfx, 1610, 100, 220, 350, Helper.BLACK, 1f);
                    Draw.Text(gfx, "Iron: ", 1620, 130, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Oil: ", 1620, 190, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "People: ", 1620, 240, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Food: ", 1620, 300, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Glass: ", 1620, 360, titleFont, Helper.WHITE, 1f);
                    Draw.Text(gfx, "Wood: ", 1620, 420, titleFont, Helper.WHITE, 1f);
                }

                SpriteSheet selectedTile = getSelectedTile();
                GameTile selectedMeta = getSelectedGameTile();
                if (selectedTile != null)
                {
                    drawTileInfoPanel(gfx, selectedTile, selectedMeta);
                }
                else
                {
                    // No selection yet — show a hover preview panel
                    SpriteSheet hoveredTile = getTileUnderMouse();
                    GameTile hoveredMeta = getGameTileUnderMouse();
                    if (hoveredTile != null)
                    {
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
