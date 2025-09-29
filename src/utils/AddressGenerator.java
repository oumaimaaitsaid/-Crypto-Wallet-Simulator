package utils;

import java.util.Random;

import model.WalletType;
public class AddressGenerator {

	
    private static final String CHARS = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    /**
     * Génére une address pour un type de wallet
     */
    public static String generateAddress(WalletType type) {
        switch(type) {
            case BITCOIN:
                return "BTC-" + randomString(30);
            case ETHEREUM:
                return "ETH-" + randomString(30);
            default:
                throw new IllegalArgumentException("Type de wallet inconnu : " + type);
        }
    }
    
    private static String randomString(int length) {
    	Random random =new Random();
    	StringBuilder sb =new StringBuilder();
    	
    	for(int i = 0; i<length; i++) {
    		sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
    	}
    	return sb.toString();
    	}
}
