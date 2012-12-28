package dungEntity;

public class Entity {
	
	private int iEntityID;
	private int iEntityIntegrity; //This will be health and stuff (40% health remaining and stuff...) The reason why I named this EntityStatus because entities like arrows can be like 50% used (this means arrow is broken and done with) or 50% unused (you haven't shot this arrow yet)
	//The reason why I renamed it to EntityIntegrity is because it can be applied to any object - how integritous(?) is the arrow? the human? the laser bullet?
	//Or maybe we should just call it health?
	private int iEntityAction; //The entity's current action. Try to use the values from GameActions from here.
	private long lEntityActionTimeLeft; //Time left in the Entity's current action
	//Should we have a variable called EntityCurrAction? and use a coding system (like used in EntityID) to make specific actions like Attack, Heal, Defend, Move, Use Item/Potion, Call Chuck Norris, etc...
	
	private double dRadius;
	private double dXPos;
	private double dYPos;
	private double dHeading;
	
	EntityController encController;
	EntitySkeleton ensSkeleton;
	public Entity(int entityID, Entity baseEntity, double xPos, double yPos, double heading){
		
	}
	
	public Entity(int entityID, double radius, EntityController controller){
		
	}
	
}