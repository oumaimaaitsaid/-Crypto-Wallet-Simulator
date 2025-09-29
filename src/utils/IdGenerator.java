package utils;

import java.util.UUID;

public class IdGenerator {

	/**
	 * génère un nouvel identifiant unique
	 */
	public static UUID generateUUID() {
		return UUID.randomUUID();
	}
}
