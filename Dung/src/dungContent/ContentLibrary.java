package dungContent;

import java.awt.Color;
import dungEntity.Entity;

/**
 * ContentLibrary:
 * A class to store the content of entities,
 * such as original color schemes, and EntityBlueprint objects.
 * All objects here are declared public, static, and final. 
 */
public class ContentLibrary {

	public static final Entity nullEntity = new Entity();
	
	public static final Color[] PLAYER_COLORS = new Color[]{
		ColorList.KHAKI,
		ColorList.KHAKI,
		ColorList.WHITE_SHIRT,
		ColorList.WHITE_SHIRT,
		ColorList.WHITE_SHIRT,
		ColorList.BROWN_SKIN,
		ColorList.BROWN_SKIN,
		ColorList.BROWN_SKIN
	};
	
	public static final Color[] DIRTY_BUBBLE_COLORS = new Color[]{
		ColorList.TRANSPARENT_DIRTY,
	};
	
	public static final Color[] CREATURE_COLORS = new Color[]{
		Color.YELLOW,
		Color.CYAN,
		Color.BLUE,
		Color.MAGENTA,
		Color.ORANGE,
		Color.RED,
		ColorList.WINDOWS_95_TEAL
	};
	
	public static final EntityBlueprint PLAYER_BLUEPRINT = new EntityBlueprint(0.15, 0.05, 0, true, true);
	public static final EntityBlueprint RAT_BLUEPRINT = new EntityBlueprint(0.20, 0.05, 1, true, true);
	public static final EntityBlueprint DIRTY_BUBBLE_BLUEPRINT = new EntityBlueprint(0.55, 0.025, 1, false, true);
	
}
