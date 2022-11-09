package interfaces;

import JeuFinal.Othello;

public interface ModeleOthello {
	
	
		/** permet de sauvegarde un mod�le dans une liste */
	public void sauvegarderModele();
	
		/** permet de charger le mod�le qui pr�c�de un coup*/
	public void chargerModelePrecedent();
	
		/** restitu le mod�le d'avant le chargement du mod�le pr�cedent */
	public void chargerModeleSuivant();
	
		/** charge le mod�le en param�tre */
	public void chargerUnModele(int numeroModele);
	
		/** charge un modele quelconque */
	public void chargeModeleQuelconque(Othello modele);
	

}
