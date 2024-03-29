package dungContent;

import dungEntity.EntityController;
import dungEntity.AnimationType;
import dungEntity.EntitySpatialKnowledge;
import dungMain.DungeonGame;
import static dungMain.DungeonGame.handleEntity;
import dungUserInterface.GameActions;
import dungUserInterface.GameInput;

/**
 * ControllerPlayer:
 * A class that converts the input information from the dungUserInterface package to entity movement.
 * This class is designed to be compatible with the player-centric nature of the GameGraphics class - 
 * (hence the static variables).
 * WARNING: This class should only be the controller for one entity.
 */
public class ControllerPlayer extends EntityController{

	public static int iPlayerEntityID; //A variable that tells other classes which entity is the player's entity.
	
	
	private static EntitySpatialKnowledge spkKnowledge; //The "knowledge" of the player - what the player sees, has seen, and has not seen.
	private int currentX; //For updating EntitySpatialKnowledge in a conservative matter (i.e., not every frame, but only when the tile changes.)
	private int currentY;
	
	
	public ControllerPlayer(){
		super();
		spkKnowledge = new EntitySpatialKnowledge(iPlayerEntityID, 5); //Initializes the "knowledge" variables
		currentX = -1;
		currentY = -1;
	}
	
	@Override
	public boolean isEntityDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEntityID(int entityID) {
		// TODO Auto-generated method stub
		super.setEntityID(entityID);
		iPlayerEntityID = entityID;
	}
	
	
	public void doNextAction() {
		
		handleEntity(iEntityID).dHeading = GameInput.getHeading(); 	//Sets the Player entity's heading relative to the mouse.
		handleEntity(iEntityID).bEntityMoving = false;				//Assumes the player is not moving.
		
		
		//CODE BLOCK:
		//Updating the Entity's knowledge
		if (currentX != (int)handleEntity(iEntityID).getXPos() || currentY != (int)handleEntity(iEntityID).getYPos()){ 	//If the entity has changed position (block-wise)
			currentX = (int)handleEntity(iEntityID).getXPos();	//Change his current position in X...
			currentY = (int)handleEntity(iEntityID).getYPos();	//And in Y...
			spkKnowledge.updateKnowledge();						//Then tell the knowledge to update his knowledge.
		}
		//END OF CODE BLOCK
		
		//CODE BLOCK:
		//Handling movement input
		if (GameInput.baActions[GameActions.MOVE_UP] && GameInput.baActions[GameActions.MOVE_LEFT]){ //Moves Up Left
			handleEntity(iEntityID).bEntityMoving = true;					//Assumes the player is moving
			handleEntity(iEntityID).setMovementDirection(Math.PI / (-4));	//and sets their velocity direction accordingly.
		} else if (GameInput.baActions[GameActions.MOVE_UP] && GameInput.baActions[GameActions.MOVE_RIGHT]) { //Moves Up Right
			handleEntity(iEntityID).bEntityMoving = true;
			handleEntity(iEntityID).setMovementDirection(Math.PI / (4));
		} else if (GameInput.baActions[GameActions.MOVE_DOWN] && GameInput.baActions[GameActions.MOVE_LEFT]) { //Moves Down Left
			handleEntity(iEntityID).bEntityMoving = true;
			handleEntity(iEntityID).setMovementDirection( 3 * Math.PI / (-4));
		} else if (GameInput.baActions[GameActions.MOVE_DOWN] && GameInput.baActions[GameActions.MOVE_RIGHT]) { //Moves Down Right
			handleEntity(iEntityID).bEntityMoving = true;
			handleEntity(iEntityID).setMovementDirection( 3 * Math.PI / (4));
		} else if (GameInput.baActions[GameActions.MOVE_UP]){ //Moves Up
			handleEntity(iEntityID).bEntityMoving = true;
			handleEntity(iEntityID).setMovementDirection(0);
		} else if (GameInput.baActions[GameActions.MOVE_LEFT]){ //Moves Left
			handleEntity(iEntityID).bEntityMoving = true;
			handleEntity(iEntityID).setMovementDirection(Math.PI / -2);
		} else if (GameInput.baActions[GameActions.MOVE_DOWN]) { //Moves Down
			handleEntity(iEntityID).bEntityMoving = true;
			handleEntity(iEntityID).setMovementDirection(Math.PI);
		} else if (GameInput.baActions[GameActions.MOVE_RIGHT]){ //Moves Right
			handleEntity(iEntityID).bEntityMoving = true;
			handleEntity(iEntityID).setMovementDirection(Math.PI / 2);
		} else if (GameInput.baActions[GameActions.MOVE_FORWARD]){ //Moves Forward
			handleEntity(iEntityID).bEntityMoving = true;
			handleEntity(iEntityID).setMovementDirection(handleEntity(iEntityID).dHeading); //Moves player towards where they are facing
		} else if (GameInput.baActions[GameActions.MOVE_BACKWARD]){ //Moves Backward
			handleEntity(iEntityID).bEntityMoving = true;
			handleEntity(iEntityID).setMovementDirection(handleEntity(iEntityID).dHeading + Math.PI); //Moves player backwards where they are facing
		}
		//END OF CODE BLOCK
		
		
		//Glitch : the speed modified will continue even if shift is not pressed. This is done by holding shift, then pressing and holding down any WASD keys, then releasing the shift key.
		//That is a feature, not a glitch. Instead of being a push-to-sprint, the game can also be a toggle-to-sprint. This is controllable in GameSettings with the boolean bModifiersAreToggled.
		//Why did I make this feature? Key ghosting. My keyboard can't sprint up-right, because it doesnt allow those three keys to be pressed at the same time.
		
		//CODE BLOCK:
		//Handling sprinting
		if(GameInput.baActions[GameActions.SPEED_MODIFIER]){ 	//If Sprinting is requested...
			handleEntity(iEntityID).dMovementMagnitude = handleEntity(iEntityID).dNormalSpeed * 6; 	//Change the entity's speed.
		} else {												//But if not...
			handleEntity(iEntityID).dMovementMagnitude = handleEntity(iEntityID).dNormalSpeed;		//Set it back to normal. 
		}
		//END OF CODE BLOCK
		
		
		//CODE BLOCK:
		//Handling movement animations
		if (handleEntity(iEntityID).bEntityMoving){ 		//If the player is moving,
			handleEntity(iEntityID).lEntityMovingTime ++; 	//increment how long he has been moving
			DungeonGame.moveEntity(iEntityID); 				//then move him accordingly
		} else {											//But if not
			handleEntity(iEntityID).lEntityMovingTime = 0;	//Then he has been moving for 0 frames. 
		}
		
		handleEntity(iEntityID).ensSkeleton.doAnimation( AnimationType.MOVE , handleEntity(iEntityID).lEntityMovingTime); //Do the movement animation.
		//END OF CODE BLOCK
		
		
		//CODE BLOCK
		//Handling special animations
		if (handleEntity(iEntityID).lEntityActionTime <= 0){ //If the special animation is done,
			handleEntity(iEntityID).entityAction = AnimationType.IDLE; //then set them to be idle.
			handleEntity(iEntityID).lEntityActionTime = 0;
		}
		
		//SUB-CODE-BLOCK
		//Handling actions requested
		if (handleEntity(iEntityID).entityAction == AnimationType.IDLE){ //If the entity is idle, they are eligible to do a special action/animation
			if (GameInput.baActions[GameActions.ATTACK_USE_PRIMARY]){ //such as swing their fist
					handleEntity(iEntityID).lEntityActionTime = 45;
					handleEntity(iEntityID).entityAction = AnimationType.ATTACK_SPEAR_RIGHTHAND;
			}
			if (GameInput.baActions[GameActions.ATTACK_USE_SECONDARY]){ //and swing their other fist
				handleEntity(iEntityID).lEntityActionTime = 45;
				handleEntity(iEntityID).entityAction = AnimationType.ATTACK_SPEAR_LEFTHAND;
		}
		}
		
		handleEntity(iEntityID).ensSkeleton.doAnimation(handleEntity(iEntityID).entityAction, handleEntity(iEntityID).lEntityActionTime); //Tells the skeleton to move the entity accordingly.
			
		
		
		
		handleEntity(iEntityID).lEntityActionTime -= 1;
		//END OF CODE BLOCKS
		
		
		
	}

	@Override
	public void doIntersectionAction() {
		// TODO Auto-generated method stub
		
	}
	
	public static EntitySpatialKnowledge getKnowledge(){
		return spkKnowledge;
	}
	
	
}