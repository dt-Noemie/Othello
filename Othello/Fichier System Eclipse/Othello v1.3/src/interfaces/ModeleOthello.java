package interfaces;

import JeuFinal.Othello;

public interface ModeleOthello {
	
	
		/** permet de sauvegarde un modèle dans une liste */
	public void sauvegarderModele();
	
		/** permet de charger le modèle qui précède un coup*/
	public void chargerModelePrecedent();
	
		/** restitu le modèle d'avant le chargement du modèle précedent */
	public void chargerModeleSuivant();
	
		/** charge le modèle en paramètre */
	public void chargerUnModele(int numeroModele);
	
		/** charge un modele quelconque */
	public void chargeModeleQuelconque(Othello modele);
	

}
