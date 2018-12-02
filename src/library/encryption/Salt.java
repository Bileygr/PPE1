package library.encryption;

import java.security.SecureRandom;

public class Salt {
	public static byte[] generer_le_sel() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }
	
	public static boolean verifier_un_mot_de_passe(String motDePasse, int sel, String hash) {
		boolean verification = false;
		return verification;
	}
}
