/**
 * Represents all of the "rooms" available for a simple adventure game. Also an
 * example of a more complex enum type.
 *
 * @see AdventureServer
 * @see AdventureServlet
 * @see Direction
 */
public enum AdventureRoom {
	/*
	 * +-----------+-----------+-----------+
	 * | START (0) | CHAIR (1) | GROWL (2) |
	 * +-----------+-----------+-----------+
	 * | BLACK (3) | CHILL (4) | TIGER (5) |
	 * +-----------+-----------+-----------+
	 * | YUMMY (6) | SMELL (7) | NOISE (8) |
	 * +-----------+-----------+-----------+
	 */

	/** The first room the player enters. */
	START_ROOM("You are starving, and know food is in here somewhere.", false),

	/** A room with a comfortable chair. */
	CHAIR_ROOM("You find a comfortable chair.", false),

	/** A room with growling noises. */
	GROWL_ROOM("You hear a faint growling noise.", false),

	/** A room with a black hole that ends the game. */
	BLACK_ROOM("Oh no! You have fallen through a black hole!", true),

	/** A room with a slight chill. */
	CHILL_ROOM("You feel a slight chill.", false),

	/** A room with a tiger that ends the game. */
	TIGER_ROOM("Oh no! You have been eaten by a tiger.", true),

	/** A room with a delicious meal that ends the game (success). */
	YUMMY_ROOM("Congratulations! You found the meal, and it looks delicious!", true),

	/** A room with a pleasant smell. */
	SMELL_ROOM("You smell something pleasant.", false),

	/** A room with a faint noise. */
	NOISE_ROOM("You hear a faint noise, almost like purring.", false);

	/** The clue given to the player when entering the room. */
	private String clue;

	/** Whether the room ends the game. */
	private boolean done;

	/**
	 * Initializes a new adventure game room.
	 *
	 * @param clue the clue given to the player
	 * @param done whether the room ends the game
	 */
	private AdventureRoom(String clue, boolean done) {
		this.clue = clue;
		this.done = done;
	}

	@Override
	public String toString() {
		return clue;
	}

	/**
	 * Returns whether the room ends the game.
	 *
	 * @return whether the room ends the game
	 */
	public boolean done() {
		return done;
	}

	/**
	 * Tests whether we can move in the specified direction from the current room,
	 * based on the room's ordinal value.
	 *
	 * @param direction the direction of movement
	 * @return true if able to move in the provided direction
	 */
	public boolean canMove(Direction direction) {
		boolean move = false;

		switch (direction) {
			case WEST:
				move = this.ordinal() % 3 != 0;
				break;
			case EAST:
				move = this.ordinal() % 3 != 2;
				break;
			case NORTH:
				move = this.ordinal() > 2;
				break;
			case SOUTH:
				move = this.ordinal() < 6;
				break;
			default:
				assert false;
		}

		return move;
	}

	/**
	 * Will move to the room in the specified direction if possible.
	 *
	 * @param direction the direction of movement
	 * @return the new room after moving
	 */
	public AdventureRoom moveRoom(Direction direction) {
		AdventureRoom room = this;

		if (this.canMove(direction)) {
			switch (direction) {
				case WEST:
					room = AdventureRoom.values()[this.ordinal() - 1];
					break;
				case EAST:
					room = AdventureRoom.values()[this.ordinal() + 1];
					break;
				case NORTH:
					room = AdventureRoom.values()[this.ordinal() - 3];
					break;
				case SOUTH:
					room = AdventureRoom.values()[this.ordinal() + 3];
					break;
				default:
					assert false;
			}
		}

		return room;
	}
}
