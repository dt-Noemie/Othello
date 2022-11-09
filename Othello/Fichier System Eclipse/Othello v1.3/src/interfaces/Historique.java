package interfaces;

public interface Historique {
	
		/** vérifie si le joueur a effectuer un coup lors 
		     de la navigation dans l'historique   */
	public boolean aRejouerDansHistorique();
	
		/** réinitialise listeModele[indice] à null pour indice > numeroModele +1 */
	public void reinitialiseListeModele(int indice);

}
