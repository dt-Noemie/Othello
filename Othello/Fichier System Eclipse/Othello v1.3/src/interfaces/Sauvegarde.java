package interfaces;

public interface Sauvegarde {
	
		/** sauvegarde une partie sur le fichier mis en param�tre */
	public void sauvegardeJeux(String nomSauvegarde);
	
		/** charge une partie du nom en param�tre */
	public void chargerUnePartie(String nomFichier);

}
