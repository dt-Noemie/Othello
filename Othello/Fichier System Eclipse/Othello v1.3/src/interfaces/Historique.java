package interfaces;

public interface Historique {
	
		/** v�rifie si le joueur a effectuer un coup lors 
		     de la navigation dans l'historique   */
	public boolean aRejouerDansHistorique();
	
		/** r�initialise listeModele[indice] � null pour indice > numeroModele +1 */
	public void reinitialiseListeModele(int indice);

}
