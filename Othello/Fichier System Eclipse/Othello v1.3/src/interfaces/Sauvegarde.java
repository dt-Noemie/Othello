package interfaces;

public interface Sauvegarde {
	
		/** sauvegarde une partie sur le fichier mis en paramètre */
	public void sauvegardeJeux(String nomSauvegarde);
	
		/** charge une partie du nom en paramètre */
	public void chargerUnePartie(String nomFichier);

}
