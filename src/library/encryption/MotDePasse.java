package library.encryption;

public class MotDePasse {
	public String motDePasse;
	public String sel;
	public String hash;
	
	public MotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	public String getMotDePasse() {
		return this.motDePasse;
	}
	
	public String getSel() {
		return this.sel;
	}
	
	public String getHash() {
		return this.hash;
	}
	
	public void settMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	public void settSel(String sel) {
		this.sel = sel;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String crypter_le_mot_de_passe() {
		byte[] sel = Salt.generer_le_sel();
		this.sel = sel.toString();
		String motDePasseSale = this.sel+this.motDePasse;
		String motDePasseSaleEtHashe = BCrypt.hashpw(motDePasseSale, BCrypt.gensalt());
		return motDePasseSaleEtHashe;
	}
	
	public boolean comparer_les_mots_de_passes(String motDePasseSaleEtHasheDeLaBaseDeDonnees, String sel) {
		boolean resultatDeVerification = false;
		String motDePasseSale = sel+this.motDePasse;
		String motDePasseSaleEtHasheDeVerification = BCrypt.hashpw(motDePasseSale, BCrypt.gensalt());
		
		if(motDePasseSaleEtHasheDeVerification == motDePasseSaleEtHasheDeLaBaseDeDonnees) {
			resultatDeVerification = true;
		}
		
		return resultatDeVerification;
	}
}
