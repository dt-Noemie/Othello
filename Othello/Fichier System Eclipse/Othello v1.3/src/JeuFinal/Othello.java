package JeuFinal;

import java.io.*;
import interfaces.*;

public class Othello implements ModeleOthello, Sauvegarde, Ordinateur, Historique, Serializable
{		
		// statut des cases
	protected int noir = 1;
	protected int blanc = 2;
	protected int prisParBlanc = 3;
	protected int prisParNoir = 4;
	protected int libre = 0;
	protected int horsGrille = -1;
	
		// plateau de 10x10
	protected int casesPlateau[][] = new int[10][10];
	
		// mémorise les coups possibles à jouer
	protected boolean coupPossibles[][] = new boolean[10][10];
	
		// mémorise le numéro des cases graphiques par rapport aux coordonnées
	protected int numeroCases[][] = new int[10][10];
	
		// mémorise  les coordonnées des cases graphiques par rapport à leurs  numéro 
	protected int ligneCases[] = new int[64];
	protected int colonneCases[] = new int[64];
	protected int sauvegardeChoixIA[] = new int[64];
	
	protected int caseChoisie;  		// récupère grâce au clic du joueur sur une case
	protected int caseJouer;
	
		// compteurs des pions présents sur le plateau
	protected int compteurNoir = 0;
	protected int compteurBlanc = 0;
	
		// mémorise les coordonnées des cases prises
	protected int lignePris[]= new int[32];
	protected int colonnePris[] = new int[32];
	protected int lignePrisTest[]= new int[32];
	protected int colonnePrisTest[] = new int[32];
	
		// type de jeu : solo  = 0  ou multi = 1
	protected int typePartie = 0;
	
		// 1 : Joueur noir  ;  2 : Joueur blanc
	protected int joueurEnCours = 1; 
	protected int joueurEnCoursChoisi = 1;
	
	
		// niveau de jeu
	protected int niveauJeu = 1;
	boolean trouve;
	boolean enTest = false;
	
		/** Instances pour l'historique des coups */	

		// liste des modèles
	protected Othello listeModele[] = new Othello[64];  
	protected int numeroModele = 0;
	
		// statut du plateau en cours
	protected int statutJeu[][] = new int[10][10];

		// points des différents case pour le choix de la case par l'IA
	protected int[][] pointCase;
	
		// instance de la sauvegarde
	protected String nomFichier = "";
	boolean pasDefichier;
	
	
		/** CONSTANTE  EVALUATION  MIN-MAX   */
	final int MINEVAL= -100000;
	final int MAXEVAL= 100000;
	
	
		// constructeurs
	public Othello()
	{
		initialiseJeu();
	}
	

			/** PREPARE  LES  INSTANCES  DU  JEU */
	public void prepareInstance()
	{
		niveauJeu = 1;
			// met à false le tableau coupsPossibles
		remiseAZeroCoupPossibles();
		
			// met à 0 la liste des modèles, le joueur en cours et met à 0 le compteur de modèle
		for (int i = 0; i < 61; i++)
			listeModele[i]= null;
		joueurEnCours = joueurEnCoursChoisi;
		numeroModele = 0;
		
			// enregistre les numéros des cases graphique par rapport aux coordonnées
		int cpt = 0;
		for (int i = 1; i < 9; i++)
			for (int j = 1; j < 9; j++)
				{
					numeroCases[i][j] = cpt;
					cpt++;
				}
		
			// enregistre les lignes et colonnes par rapport aux  coordonnées des cases.
		int ligne = 1;
		for (int i = 0; i < 64; i++)
		{
			if (i > 7 ) ligne = 2;
			if (i > 15) ligne = 3;
			if (i > 23) ligne = 4;
			if (i > 31) ligne = 5;
			if (i > 39) ligne = 6;
			if (i > 47) ligne = 7;
			if (i > 55) ligne = 8;
			ligneCases[i]= ligne;		// un décalage de 1 par rapport à l'interface graphique

		}
		
		int colonne = 1;
		for (int i = 0; i < 64; i++)
		{
			if (colonne > 8) colonne = 1;
			colonneCases[i] = colonne;
			colonne++;
		}
			
			// initialise à zéro le tableau des cases pris.
		for (int i = 0; i < 32; i++)
		{
			lignePris[i] = 0;
			colonnePris[i] = -0;
		}
		initialiseTableauPoint();
		
	}	
	
	public  void initialiseTableauPoint()
	{
			// mémorise le nombre de points de chaque case.
		
		pointCase = new int[][]
		            {
						{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
				
						{-1, 1000, 100, 700, 400, 400, 700, 100, 1000, -1},
						
						{-1, 100, 0, 310, 310, 310, 310, 0, 100, -1},
						
						{-1, 700, 310, 350, 325, 325, 350, 310, 700, -1},
						
						{-1, 400, 310, 325, 500, 500, 325, 310, 400, -1},
						
						{-1, 400, 310, 325, 500, 500, 325, 310, 400, -1},
						
						{-1, 700, 310, 350, 325, 325, 350, 310, 700, -1},
						
						{-1, 100, 0, 310, 310, 310, 310, 0, 100, -1},
						
						{-1, 1000, 100, 700, 400, 400, 700, 100, 1000, -1},
						
						{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
		            };
	}
	
		// si on possède les coins, on change la valeur des cases qui se 
		// trouve à proximité car elles deviennent favorables.
	public void modifiePoint()
	{
		if (joueurEnCours() == 1)
		{
			if (casesPlateau[1][1] == noir)
			{
				pointCase[1][2] = 1000;
				pointCase[2][1] = 1000;
				pointCase[2][2] = 1000;
			}
			if (casesPlateau[1][8] == noir)
			{
				pointCase[1][7] = 1000;
				pointCase[2][8] = 1000;
				pointCase[2][7] = 1000;
			}
			if (casesPlateau[8][1] == noir)
			{
				pointCase[7][1] = 1000;
				pointCase[7][2] = 1000;
				pointCase[8][2] = 1000;
			}
			if (casesPlateau[8][8] == noir)
			{
				pointCase[7][8] = 1000;
				pointCase[7][7] = 1000;
				pointCase[8][7] = 1000;
			}
		}
		else if (joueurEnCours() == 2)
		{
			if (casesPlateau[1][1] == blanc)
			{
				pointCase[1][2] = 1000;
				pointCase[2][1] = 1000;
				pointCase[2][2] = 1000;
			}
			if (casesPlateau[1][8] == blanc)
			{
				pointCase[1][7] = 1000;
				pointCase[2][8] = 1000;
				pointCase[2][7] = 1000;
			}
			if (casesPlateau[8][1] == blanc)
			{
				pointCase[7][1] = 1000;
				pointCase[7][2] = 1000;
				pointCase[8][2] = 1000;
			}
			if (casesPlateau[8][8] == blanc)
			{
				pointCase[7][8] = 1000;
				pointCase[7][7] = 1000;
				pointCase[8][7] = 1000;
			}
		}
	}
	
	
	
		/** INITIALISE  UNE  PARTIE **/
	
	public void initialiseJeu()
	{
			// déclare les cases qui se trouve hors du plateau de jeu
		for (int i = 0; i < 10; i++)
		{	
				casesPlateau[i][0] = horsGrille;
				casesPlateau[i][9] = horsGrille;
				casesPlateau[0][i] = horsGrille;
				casesPlateau[9][i] = horsGrille;
		}		
		
			// déclare toutes les cases en statut libre
		
		for (int i = 1; i < 9; i++)
			for (int j = 1; j < 9; j++)
				casesPlateau[i][j] = libre;
		
			// positionne les 4 pions de départ sur les case 27, 28, 35 et 36
		casesPlateau[4][4] = blanc;
		casesPlateau[4][5] = noir;
		casesPlateau[5][4] = noir;
		casesPlateau[5][5] = blanc;
				
			// initialise les compteurs de pions.
		compteurBlanc = 2;
		compteurNoir = 2;

	}

				/** *************************************************   
				 * 													*
				 * 						ACCESSEURS	 				*
				 * 													*	
				 * *********************************************** **/


		// retourne le joueur en cours
	public int joueurEnCours()
	{
		return joueurEnCours;
	}
	
		// retourne la ligne d'une case en paramètre
	public int getLigne(int uneCase)
	{
		return ligneCases[uneCase];
	}
	
		// retourne la colonne d'une case en paramètre
	public int getColonne(int uneCase)
	{
		return colonneCases[uneCase];
	}
	
		// retourne la ligne du pion pris
	public int getLignePris(int unIndice)
	{
		return lignePris[unIndice];
	
	}
	
		// retourne la colonne  du pion pris
	public  int getColonnePris(int unIndice)
	{
		return colonnePris[unIndice];
	}
	
		// retourne le numéro de la case grâce aux coordonnées
	public int getNumeroCase(int ligne, int colonne)
	{
		return numeroCases[ligne][colonne];
	}
	
		// retourne le score des joueurs.
	public int getScore(int unJoueur)
	{
		if (unJoueur == 1) return compteurNoir;
		else return compteurBlanc;
	}

		// retourne le type de partie.
	public int typePartie()
	{
		return typePartie;
	}
		
		// retourne la couleur de la case
	public int getCouleurCase(int ligne, int colonne)
	{
		return casesPlateau[ligne][colonne];
	}

		// retourne la case sur laquelle le joueur aura cliqué
	public int choixCase()
	{
	return caseChoisie;
	}
	
	
					/** *************************************************    
					 * 													*
					 * 				DEROULEMENT DU JEU	 				*
					 * 													*	
					 * *********************************************** **/
	
			// véfirie si le placement est correcte
	public boolean placementCorrect(int ligne, int colonne, int couleur, int autreCouleur, boolean joueLeCoup)
	{	
		int i, j;
		int nbEtape;
		boolean correct = false;
		
			// si la case est libre
		if (casesPlateau[ligne][colonne] == libre)
		{
			int indice = 0;
			for (int a = -1; a < 2; a++)		// permet de savoir dans qu'elle direction on va
				for (int b = -1; b < 2; b++)	// par rapport à la case choisie
				{
					nbEtape = 0;				// indique le nombre de pions adverses trouvés
					do
					{		
						nbEtape++;
						i = ligne + nbEtape*a;
						j = colonne + nbEtape*b;
											} 
					// permet de voir s'il y a un autre pion
					// d'une autre couleur
					while ( (i > 0 ) && (i < 9) && (j > 0) && (j < 9) &&
							(casesPlateau[i][j] == autreCouleur));  
					
						// si au moins un pion d'une autre couleur est trouvé 
						// et qu'on se trouve dans le plateau 
						// et qu'un pion de même couleur est trouvé 
					if (( i > 0 ) && (i < 9)  && (j > 0) && (j < 9) && (nbEtape > 1)
							&& (casesPlateau[i][j] == couleur))
					{
						correct = true;
					
						// mémorise les coordonnées des pions pris
						for (int k = 1; k < nbEtape; k++)
						{
							lignePrisTest[indice]= ligne + a*k;
							colonnePrisTest[indice] = colonne + b*k;
							indice++;
						}
						
							// si le coup est vraiment joué, on exécute cette boucle
						if (joueLeCoup)
						{	
							for (int m = 1; m < nbEtape; m++)
								{
										// enregistre les cases prises
									lignePris[indice]= ligne + a*m;
									colonnePris[indice] = colonne + b*m;
									indice++;
										// modifie la couleur des pions pris
									if (couleur == noir)
										casesPlateau[ligne + a*m][colonne + b*m] = prisParNoir;
									else if ( couleur == blanc ) 
										casesPlateau[ligne + a*m][colonne + b*m] = prisParBlanc;
								}
								// la case choisie prend la couleur du joueur en cours
							casesPlateau[ligne][colonne] = couleur;		
						}
					}
				}
		}
		return correct;
	}
	
		// change le joueur en cours
	public void changeTourJoueur()
	{
		if (joueurEnCours == 1) joueurEnCours = 2;
		else joueurEnCours = 1;
	}
	
		// remet à 0 le tableau des cases prises
	public void reinitialiseCasesPrises()
	{
		for (int i = 0; i < 32; i++)
		{
			lignePris[i] = 0;
			colonnePris[i] = 0;
		}	
	}
	
		// vérifie si la partie est terminée
	public boolean partieEstFinie()
	{
		boolean partieFinie = true;
		
		for (int y = 1; y < 9; y++ )
			for (int z = 1; z < 9; z++ )
					if ( (placementCorrect(y, z, blanc, noir, false) ) || 
						(placementCorrect(y, z, noir, blanc, false)) )
							partieFinie = false;
		
		return partieFinie;
	}
	
		// vérifie si le joueur blanc ne peut plus jouer
	public boolean jbPeutPlusJouer()
	{
		boolean jbPeutPlusJouer = true;
		
		for (int y = 1; y < 9; y++ )
			for (int z = 1; z < 9; z++ )		
					if (placementCorrect(y, z, blanc, noir, false))
						jbPeutPlusJouer = false;
					
		return jbPeutPlusJouer;
	}
	
		// vérifie si le joueur noir ne peut plus jouer
	public boolean jnPeutPlusJouer()
	{
		boolean jnPeutPlusJouer = true;
		
		for (int y = 1; y < 9; y++ )
			for (int z = 1; z < 9; z++ )		
					if (placementCorrect(y, z, noir, blanc, false))
						jnPeutPlusJouer = false;
					
		return jnPeutPlusJouer;
	}
	
		// permet de montrer les coups possibles à jouer
	public void coupsPossibles()
	{
			// pour le joueur noir
		if (joueurEnCours() == 1)
		{
			for (int i = 1 ; i < 9; i++)
				for (int j = 1; j < 9 ; j++)
					if (placementCorrect(i, j, noir, blanc, false))
						coupPossibles[i][j] = true;
		}
		
			// pour le joueur blanc
		else if (joueurEnCours() == 2)
		{
			for (int m = 1 ; m < 9; m++)
				for (int n = 1; n < 9 ; n++)
					if (placementCorrect(m, n, blanc, noir, false))
						coupPossibles[m][n] = true;
		}
	}
	
		// remise à 0 tableau coupPossibles
	public void remiseAZeroCoupPossibles()
	{
		for (int i = 1 ; i < 9; i++)
			for (int j = 1; j < 9 ; j++)
				coupPossibles[i][j] = false;
	}
	
	
	// calcul du nombre de pions de chaque joueur
	public void calculScore()
	{
		compteurBlanc = 0;
		compteurNoir = 0;
		
		for (int d = 1; d < 9; d++)
			for (int f = 1; f < 9; f++)
			{
				if (casesPlateau[d][f] == noir || casesPlateau[d][f] == prisParNoir)
					compteurNoir++;
				if (casesPlateau[d][f] == blanc || casesPlateau[d][f] == prisParBlanc)
					compteurBlanc++;
			}
	}
	
	

		// créer une copie du jeu 
	public Othello copier()
	{
			// crée un nouveau modèle
		Othello mod = new Othello();
		
			// récupère le statut des  cases
		for (int i = 1; i < 9; i++)
			for (int j = 1; j < 9; j++)
				mod.statutJeu[i][j] = casesPlateau[i][j];				
			// récupère le joueurs en cours
		mod.joueurEnCours = joueurEnCours();
			// récupère les points
		mod.compteurBlanc = compteurBlanc;
		mod.compteurNoir = compteurNoir;
			// récupère la case joué
		mod.caseJouer = caseJouer;
		
		return mod;
	}
	
	
	
				/**   GESTION    DE  L'HISTORIQUE      */
	
	
	public void sauvegarderModele()
	{
		listeModele[numeroModele] = copier();
	}
	
	public void chargerModelePrecedent()
	{
		numeroModele--;
		
			// récupère les statuts des  cases
		for (int i = 1; i < 9; i++)
			for (int j = 1; j < 9; j++)
				casesPlateau[i][j] = listeModele[numeroModele].statutJeu[i][j];
			// récupère le joueurs en cours
		joueurEnCours = listeModele[numeroModele].joueurEnCours;
			// récupère les points
		compteurBlanc = listeModele[numeroModele].compteurBlanc;
		compteurNoir = listeModele[numeroModele].compteurNoir;
			// récupère la case joué
		caseJouer = listeModele[numeroModele].caseJouer;
	}
	
	public void chargerModeleSuivant()
	{
		numeroModele++;
			
			// récupère les statuts des  cases
		for (int i = 1; i < 9; i++)
			for (int j = 1; j < 9; j++)
				casesPlateau[i][j] = listeModele[numeroModele].statutJeu[i][j];
			// récupère le joueurs en cours
		joueurEnCours = listeModele[numeroModele].joueurEnCours;
			// récupère les points
		compteurBlanc = listeModele[numeroModele].compteurBlanc;
		compteurNoir = listeModele[numeroModele].compteurNoir;
			// récupère la case joué
		caseJouer = listeModele[numeroModele].caseJouer;
	}
	
	public void chargerUnModele(int unModele)
	{
		// récupère les statuts des  cases
		for (int i = 1; i < 9; i++)
			for (int j = 1; j < 9; j++)
				casesPlateau[i][j] = listeModele[unModele].statutJeu[i][j];
			// récupère le joueurs en cours
		joueurEnCours = listeModele[unModele].joueurEnCours;
			// récupère les points
		compteurBlanc = listeModele[unModele].compteurBlanc;
		compteurNoir = listeModele[unModele].compteurNoir;
			// récupère la case joué
		caseJouer = listeModele[unModele].caseJouer;
	}

	public void chargeModeleQuelconque(Othello modele)
	{
		// récupère les statuts des  cases
		for (int i = 1; i < 9; i++)
			for (int j = 1; j < 9; j++)
				casesPlateau[i][j] = modele.statutJeu[i][j];
			// récupère le joueurs en cours
		joueurEnCours = modele.joueurEnCours;
			// récupère les points
		compteurBlanc = modele.compteurBlanc;
		compteurNoir = modele.compteurNoir;
			// récupère la case joué
		caseJouer = modele.caseJouer;
	}
	
	public boolean aRejouerDansHistorique()
	{
		boolean aRejouer = false;
		if (listeModele[numeroModele+1] != null)
			aRejouer = true;
		
		return aRejouer;
	}

	public void reinitialiseListeModele(int indice)
	{
		for (int  i = indice + 1; i < 61; i++)
					listeModele[i] = null;
	}
	
	
						/*   FIN    GESTION    DE  L'HISTORIQUE      */
	
	/*  ============================================================================= */
		
	
						/* 	  	GESTION    DE    LA   SAUVEGARDE     */	
	
	public void sauvegardeJeux(String nomSauvegarde) 
	{
		int compteur = 0;
		try 
		{
			// ouverture d'un flux de sortie vers un fichier
			FileOutputStream fluxSortieFichier = new FileOutputStream("sauvegarde/"+nomSauvegarde+".sav");
			// création d'un "flux objet" avec le flux fichier
			ObjectOutputStream fluxSortieObjet= new ObjectOutputStream(fluxSortieFichier);
			try {
				while (compteur < 61)
				{
						// sérialisation : écriture de l'objet dans le flux de sortie
					fluxSortieObjet.writeObject(listeModele[compteur]); 
					compteur++;
				}
				fluxSortieObjet.write(numeroModele);
				fluxSortieObjet.write(typePartie);
					// on vide le tampon
				fluxSortieObjet.flush();
				
			} finally {
					//fermeture des flux
				try {
					fluxSortieObjet.close();
				} finally {
					fluxSortieFichier.close();
				}
			}
		} 
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	public void chargerUnePartie(String nomFichier)
	{
		int compteur = 0;
		try 
		{
				// ouverture d'un flux d'entrée depuis le fichier "personne.serial"
			FileInputStream fluxEntreeFichier = new FileInputStream("sauvegarde/"+nomFichier+".sav");
			
				// création d'un "flux objet" avec le flux fichier
			ObjectInputStream fluxEntreeObjet= new ObjectInputStream(fluxEntreeFichier);
			try 
			{		
					// désérialisation : lecture de l'objet depuis le flux d'entrée
				while (compteur < 61)
				{	listeModele[compteur] = (Othello) fluxEntreeObjet.readObject(); 
					compteur++;
				}
				numeroModele = fluxEntreeObjet.read();
				typePartie = fluxEntreeObjet.read();
			} finally 
				{
						// on ferme les flux
					try {
						fluxEntreeObjet.close();
					} finally {
						fluxEntreeFichier.close();
					}
				}
		} catch(IOException ioe) 
			{
				pasDefichier = true;
			} catch(ClassNotFoundException cnfe)
				{
					cnfe.printStackTrace();
				}
	}	
	
				/* 		FIN     GESTION    DE LA  SAUVEGARDE     */
	
	
	
			/** *************************************************************************
						Début de la partie gérant les coups de l'IA
			 ************************************************************************* **/
	
	public int IAchoix()
	{
		trouve = false;
		int caseChoisie = -1;

				
		if (niveauJeu == 1)
			caseChoisie = niveau1();	
			
		if (niveauJeu == 2)
			caseChoisie = niveau2();
					
		if (niveauJeu == 3)
			caseChoisie = niveau3();
		
		if (niveauJeu == 4)
			caseChoisie = calcIA(3);
		
		if (niveauJeu == 5)
			caseChoisie = calcIA(5);
		
		if (niveauJeu == 6)
			caseChoisie = calcIA(7);
		
			return caseChoisie;		
	}

	public int niveau1()
	{
		int aleatoire = -1;
		int choixCase = -1;
		
		
		// selon la valeur, la recherche commencera d'un coté ou d'un autre
		while ( (aleatoire < 1 ) || (aleatoire >= 5) )
				aleatoire = (int) (Math.random()*10) + 1;
		
		if (aleatoire == 1) {
			while (choixCase == -1)
				for (int k = 1; k < 9; k++)
					for (int l = 1; l < 9; l++)
						if (placementCorrect(k, l, blanc, noir, false))
							choixCase = getNumeroCase(k, l); }
		
		else if (aleatoire == 2) {
			while (choixCase == -1)
				for (int k = 1; k < 9; k++)
					for (int l = 8; l > 0; l--)
						if (placementCorrect(k, l, blanc, noir, false))
							choixCase = getNumeroCase(k, l); }
		

		else if (aleatoire == 3) {
			while (choixCase == -1)
				for (int k = 8; k > 0; k--)
					for (int l = 1; l < 9; l++)
						if (placementCorrect(k, l, blanc, noir, false))
							choixCase = getNumeroCase(k, l); }
		

		else if (aleatoire == 4) {
			while (choixCase == -1)
				for (int k = 8; k > 0; k--)
					for (int l = 8; l > 0; l--)
						if (placementCorrect(k, l, blanc, noir, false))
							choixCase = getNumeroCase(k, l); }
		
		return choixCase;
	}
	
	public int niveau2()
	{
		int i, j;
		int choixCase = -1;
		
		if (placementCorrect(1, 1, blanc, noir, false))
		{
			choixCase = getNumeroCase(1, 1);
			trouve = true;
		}
		if (placementCorrect(8, 8, blanc, noir, false))
		{
			choixCase = getNumeroCase(8, 8);
			trouve = true;
		}
		if (placementCorrect(1, 8, blanc, noir, false))
		{
			choixCase = getNumeroCase(1, 8);
			trouve = true;
		}
		if (placementCorrect(8, 1, blanc, noir, false))
		{
			choixCase = getNumeroCase(8, 1);
			trouve = true;
		}
			
				//	Bords
		i = 3;
		while (!(trouve) && (i < 7))
		{
			if (placementCorrect(1, i, blanc, noir, false))
			{
				choixCase = getNumeroCase(1, i);
				trouve = true;
			}
			if (placementCorrect(8, i, blanc, noir, false))
			{
				choixCase = getNumeroCase(8, i);
				trouve = true;
			}
			if (placementCorrect(i,1 , blanc, noir, false))
			{
				choixCase = getNumeroCase(i,1);
				trouve = true;
			}
			if (placementCorrect(i, 8, blanc,noir, false))
			{
				choixCase = getNumeroCase(i, 8);
				trouve = true;
			}
			i++;
		}
		
		i = 3;
		while (!(trouve) && (i < 7))
		{
			if (placementCorrect(2, i, blanc, noir, false))
			{
				choixCase = getNumeroCase(2, i);
				trouve = true;
			}
			if (placementCorrect(7, i, blanc, noir, false))
			{
				choixCase = getNumeroCase(7, i);
				trouve = true;
			}
			if (placementCorrect(i,2 , blanc, noir, false))
			{
				choixCase = getNumeroCase(i,2);
				trouve = true;
			}
			if (placementCorrect(i, 7, blanc, noir, false))
			{
				choixCase = getNumeroCase(i, 7);
				trouve = true;
			}
			i++;
		}
		
		// centre 
		i = 3;
		while (!(trouve) && (i < 7))
		{
			j = 3;
			while ( !(trouve) && (j < 7))
			{
				if (placementCorrect(i, j, blanc, noir, false))
				{
					choixCase = getNumeroCase(i, j);
					trouve = true;
				}
				j++;
			}
			i++;
		}
		
		// Si rien ne correspond au dessus, on prend ma première case
		
		i=1;
		while ( !(trouve) && (i < 9))
		{
			j=1;
			while ( !(trouve) && (j < 9))
			{
				if ( placementCorrect(i, j, blanc, noir, false))
				{
					choixCase = getNumeroCase(i, j);
					trouve = true;
				}
				j++;
			}
			i++;
		}
		
		return choixCase;
	}
		
	public int niveau3()
	{
		int meilleurCase = -1;	
		int couleur = 0;
		int autreCouleur = 0;
		
		if (joueurEnCours() == 1)
		{
			couleur = noir;
			autreCouleur = blanc;
		}
		else if (joueurEnCours() == 2)
		{
			couleur = blanc;
			autreCouleur = noir;
		}
		
		modifiePoint();
			// coins 
		if (placementCorrect(1, 1, couleur, autreCouleur, false))
			meilleurCase = getNumeroCase(1, 1);

		else if (placementCorrect(8, 8, couleur, autreCouleur, false))
			meilleurCase = getNumeroCase(8, 8);

		else if (placementCorrect(1, 8, couleur, autreCouleur, false))
			meilleurCase = getNumeroCase(1, 8);
		
		else if (placementCorrect(8, 1, couleur, autreCouleur, false))
			meilleurCase = getNumeroCase(8, 1);
		
		else 
		{
				// recherche de la meilleur position
			int meilleurChoix = -1;
			int meilleurLigne = 0;
			int meilleurColonne = 0;
			int scoreCoupJoue;
			
				// boucles vérifiant toutes les cases du plateau 
			for (int g = 1; g < 9; g++)
				for (int h = 1; h < 9; h++)
				{					
					scoreCoupJoue = 0;
					
						// efface les pions pris
					for (int k = 0; k < 32; k++)
					{
						lignePrisTest[k]= 0;
						colonnePrisTest[k] = 0;
					}
					
					if (placementCorrect(g, h, couleur, autreCouleur, false))
					{
						for (int n = 0; n < 32; n++)
						{
							if ((lignePrisTest[n] > 0) && (lignePrisTest[n] < 9)
								&& (colonnePrisTest[n] > 0) && (colonnePrisTest[n] < 9))
										// score coup joué = score des cases pris + score de la case choisie
									scoreCoupJoue = scoreCoupJoue + pointCase[lignePrisTest[n]][colonnePrisTest[n]];	                                           
								// rajoute la valeur de la case choisie
							scoreCoupJoue = scoreCoupJoue + pointCase[g][h];
			
						} 
					}
						// récupère la meilleur position
					if ((scoreCoupJoue > meilleurChoix))
					{
						meilleurChoix = scoreCoupJoue;
						meilleurLigne = g;
						meilleurColonne = h;
					} 				
						// par sécurité on efface les pions pris
					for (int k = 0; k < 32; k++)
					{
						lignePrisTest[k]= 0;
						colonnePrisTest[k] = 0;
					}
				} // fin 2è boucle for	
			
			initialiseTableauPoint();
			
			meilleurCase = getNumeroCase(meilleurLigne, meilleurColonne);
		}	// fin boucle else
	
		return meilleurCase;
	}
		
	public void jouerUnCoup(int unCase)
	{			
		int ligne = getLigne(unCase);		// lit la ligne de la case choisie
		int colonne = getColonne(unCase);	// lit la colonne  de la case choisie
		int couleur = 0;							// couleur du joueur en cours
		int autreCouleur = 0;	

			// récupère la couleur du joueur en cours et celle de l'autre joueur
		if ( joueurEnCours() == 1) 
		{	couleur = noir;
			autreCouleur = blanc;
		}
		else if ( joueurEnCours() == 2) 
		{ 	couleur= blanc;
			autreCouleur = noir; 
		}

			// si le coup est correct
		if (placementCorrect(ligne, colonne, couleur, autreCouleur, true))
		{		
				// fin du tour du joueur : changement de joueur
			changeTourJoueur();
				// remet à zéro le tableau des cases pris.
			reinitialiseCasesPrises();
				// calcul du score 
			calculScore();
				// si un des 2 joueurs ne peut jouer on passe le tour 
			if ((compteurBlanc + compteurNoir) < 64) 
			{
				if (jbPeutPlusJouer() == true) joueurEnCours = 1;
				if (jnPeutPlusJouer() == true) joueurEnCours = 2;
			} 
		}
	}

	
	
	/** Gestion  recherche  MINMAX  */
	int compteur;
	
	public int calcIA(int prof) 
	{
		
		compteur = 0;
		int i,j,tmp;
	    int max = MINEVAL;
	    int maxi=-1, maxj=-1;
	    int couleur = 0;
		int autreCouleur = 0;
		
		if (joueurEnCours() == 1)
		{
			couleur = noir;
			autreCouleur = blanc;
		}
		else if (joueurEnCours() == 2)
		{
			couleur = blanc;
			autreCouleur = noir;
		}
		
			// teste si une des cases en coins est jouable
			// si c'est le cas, on le joue
		if (placementCorrect(1, 1, blanc, noir, false))
			return getNumeroCase(1, 1);

		if (placementCorrect(8, 8, blanc, noir, false))
			return getNumeroCase(8, 8);

		if (placementCorrect(1, 8, blanc, noir, false))
			return getNumeroCase(1, 8);

		if (placementCorrect(8, 1, blanc, noir, false))
			return getNumeroCase(8, 1);

		

	    	//Si la profondeur est nulle ou la partie est finie,
	    	//on ne fait pas le calcul
	    if((prof > 0) && (compteurBlanc + compteurNoir) < 64)
	        {
	        //On parcourt les cases du morpion
	        for(i=1; i<9; i++)
	            for(j=1; j<9;j++)
	                {
	                //Si la case est vide
	                if (placementCorrect(i, j, couleur, autreCouleur, false))
	                {
	                	Othello modeleTmp = copier();
	                	
	                		//On simule qu'on joue cette case
	                    jouerUnCoup(getNumeroCase(i, j));
	                    	//On appelle la fonction calcMin pour lancer l'IA
	                    tmp = calcMin(prof-1);
	                    	//Si ce score est plus grand
	                    if (tmp >= max)
	                        {
	                        //On le choisit
	                        max = tmp;
	                        maxi = i;
	                        maxj = j;
	                        }
	                    //On annule le coup
	                    chargeModeleQuelconque(modeleTmp);
	                    }
	                }
	        }
	    	//on retourne la meilleure case
	    return getNumeroCase(maxi, maxj);
		
	}
	
	public int calcMax(int prof) 
	{
		compteur++;
	    int i,j,tmp;
	    int max = MINEVAL;
	    int couleur = 0;
		int autreCouleur = 0;
		boolean nePeutPasJouer = true;
		System.out.println("nbCoup : "+compteur);
		
		if (joueurEnCours() == 1)
		{
			couleur = noir;
			autreCouleur = blanc;
		}
		else if (joueurEnCours() == 2)
		{
			couleur = blanc;
			autreCouleur = noir;
		}

	    //Si on est à la profondeur voulue ou que la partie est finie, on retourne l'évaluation
	    if((prof==0) || (compteurBlanc + compteurNoir) >= 64)
	        return evalue();

	    	//On parcourt le plateau de jeu et on le joue si la case est vide
	    for(i = 1; i < 9; i++)
	        for(j = 1; j < 9; j++)
	        {
	            if(placementCorrect(i, j, couleur, autreCouleur, false))
	            {
	            	Othello modeleTmp1 = copier();
	                //On joue le coup
	                jouerUnCoup(getNumeroCase(i, j));
	                tmp = calcMin(prof-1);
	                if(tmp > max)
	                {
	                    max = tmp;
	                }
	                
	                	//On annule le coup
	               chargeModeleQuelconque(modeleTmp1);
	               nePeutPasJouer = false;
	             }
	        }
	    if  (nePeutPasJouer) changeTourJoueur();
	    
	    return max;
	}

	public int calcMin(int prof) 
	{
		compteur++;
		int i,j,tmp;
	    int min = MAXEVAL;
	    int couleur = 0;
		int autreCouleur = 0;
		boolean nePeutPasJouer = true;
		System.out.println("nbCoup : "+compteur);
		
		if (joueurEnCours() == 1)
		{
			couleur = noir;
			autreCouleur = blanc;
		}
		else if (joueurEnCours() == 2)
		{
			couleur = blanc;
			autreCouleur = noir;
		}

		 	//Si on est à la profondeur voulue ou que la partie est finie, on retourne l'évaluation
	    if((prof==0) || (compteurBlanc + compteurNoir) >= 64)
	        return evalue();

	    	//On parcourt le plateau de jeu et on le joue si la case est vide
	    for(i = 1; i < 9; i++)
	        for(j = 1; j < 9; j++)
	        {
	            if(placementCorrect(i, j, couleur, autreCouleur, false))
	            {
	            	Othello modeleTmp2 = copier();
	            		//On joue le coup
	                jouerUnCoup(getNumeroCase(i, j));
	                tmp = calcMax(prof-1);
	                if(tmp < min)
	                {
	                    min = tmp;
	                }
	                	//On annule le coup
	                chargeModeleQuelconque(modeleTmp2);
	                nePeutPasJouer = false;
	              }
	          }
	    if  (nePeutPasJouer) changeTourJoueur();
	    
	    return min;
	}
	
	

	public int evalue() 
	{
		int score = 0;
		calculScore();
		
			// si la partie est finie
		if ((compteurBlanc + compteurNoir) >= 64)
		{
			if (compteurBlanc > compteurNoir ) return score =  100000 - (compteurBlanc - compteurNoir);
			else if (compteurBlanc < compteurNoir) return score =  -100000 + (compteurNoir - compteurBlanc);
			else  return score = 0;
		}
		
			// sinon on met à jour le tableau des points puis on fait le compte
		modifiePoint();		
		for (int i = 1; i < 9; i++)
			for (int j = 1; j < 9; j++)
			{
				if (casesPlateau[i][j] == noir || casesPlateau[i][j] == prisParNoir)
					score -= pointCase[i][j];
				else if (casesPlateau[i][j] == blanc || casesPlateau[i][j] == prisParBlanc)
					score += pointCase[i][j];
			}
		
			// si blanc possède les cases du bord
		for (int i = 1; i < 9; i++)
			if (casesPlateau[i][1] == blanc ||  casesPlateau[i][1] == prisParBlanc)
				score += 1000;
		for (int i = 1; i < 9; i++)
			if (casesPlateau[i][8] == blanc ||  casesPlateau[i][1] == prisParBlanc)
				score += 1000;
		for (int i = 1; i < 9; i++)
			if (casesPlateau[1][i] == blanc ||  casesPlateau[i][1] == prisParBlanc)
				score += 1000;
		for (int i = 1; i < 9; i++)
			if (casesPlateau[8][i] == blanc ||  casesPlateau[i][1] == prisParBlanc)
				score += 1000;
		
		/**
			// 3ème et 6ème ligne et 3ème et 6ème colonne
		for (int i = 3; i < 7; i++)
			if (casesPlateau[i][1] == blanc ||  casesPlateau[i][1] == prisParBlanc)
				score += 1000;
		for (int i = 3; i < 7; i++)
			if (casesPlateau[i][8] == blanc ||  casesPlateau[i][1] == prisParBlanc)
				score += 1000;
		for (int i = 3; i < 7; i++)
			if (casesPlateau[1][i] == blanc ||  casesPlateau[i][1] == prisParBlanc)
				score += 1000;
		for (int i = 3; i < 7; i++)
			if (casesPlateau[8][i] == blanc ||  casesPlateau[i][1] == prisParBlanc)
				score += 1000;
		*/
		
			
			// si noir a des coins on diminu le score
		if (casesPlateau[1][1] == noir)
			score -= 5000;
		if (casesPlateau[1][8] == noir)
			score -= 5000;
		if (casesPlateau[8][1] == noir)
			score -= 5000;
		if (casesPlateau[8][8] == noir)
			score -= 5000;
		
			// si c'est lIA qui les a on augemente le score
		if (casesPlateau[1][1] == blanc)
			score += 5000;
		if (casesPlateau[1][8] == blanc)
			score += 5000;
		if (casesPlateau[8][1] == blanc)
			score += 5000;
		if (casesPlateau[8][8] == blanc)
			score += 5000;
		
		
			// si noir possède les coins au centre
		if (casesPlateau[3][3] == noir)
			score -= 2500;
		if (casesPlateau[3][6] == noir)
			score -= 2500;
		if (casesPlateau[6][3] == noir)
			score -= 2500;
		if (casesPlateau[6][6] == noir)
			score -= 2500;
		
		// si blanc possède les coins au centre
		if (casesPlateau[3][3] == blanc)
			score += 2500;
		if (casesPlateau[3][6] == blanc)
			score += 2500;
		if (casesPlateau[6][3] == blanc)
			score += 2500;
		if (casesPlateau[6][6] == blanc)
			score += 2500;
		
		
		
		
			// si  noir possède les 3 cases en coins
		if (pointCase[1][1] == noir && ( pointCase[1][2] == noir || pointCase[1][2] == prisParNoir) 	
				&& (pointCase[2][1] == noir || pointCase[2][1] == prisParNoir ))
			score -= 10000;
		
		if (casesPlateau[1][8] == noir && (pointCase[1][7] == noir || pointCase[1][7] == prisParNoir) 
				&& (pointCase[2][8] == noir || pointCase[2][8] == prisParNoir));
			score -= 10000;
			
		if (casesPlateau[8][1] == noir && (pointCase[7][1] == noir || pointCase[7][1] == prisParNoir )
				&& (pointCase[7][2] == noir || pointCase[7][2] == prisParNoir))
			score -= 10000;
		
		if (casesPlateau[8][8] == noir && (pointCase[7][8] == noir || pointCase[7][8] == prisParNoir) 
				&& (pointCase[7][7] == noir || pointCase[7][7] == prisParNoir ));
			score -= 10000;
			
			
			
			
			// si noir les 4 cases de coins 
		if (pointCase[1][1] == noir && ( pointCase[1][2] == noir || pointCase[1][2] == prisParNoir) 	
				&& (pointCase[2][1] == noir || pointCase[2][1] == prisParNoir )
				&& (pointCase[2][2] == noir || pointCase[2][2] == prisParNoir))
			score -= 15000;
		
		if (casesPlateau[1][8] == noir && (pointCase[1][7] == noir || pointCase[1][7] == prisParNoir) 
				&& (pointCase[2][8] == noir || pointCase[2][8] == prisParNoir)
				&& (pointCase[2][7] == noir || pointCase[2][7] == prisParNoir));
			score -= 15000;
			
		if (casesPlateau[8][1] == noir && (pointCase[7][1] == noir || pointCase[7][1] == prisParNoir )
				&& (pointCase[7][2] == noir || pointCase[7][2] == prisParNoir)
				&& (pointCase[7][2] == noir || pointCase[7][2] == prisParNoir) )
			score -= 15000;
		
		if (casesPlateau[8][8] == noir && (pointCase[7][8] == noir || pointCase[7][8] == prisParNoir) 
				&& (pointCase[7][7] == noir || pointCase[7][7] == prisParNoir ) 
				&& (pointCase[7][7] == noir || pointCase[7][7] == prisParNoir));
			score -= 15000;
			
			
			
			// si  blanc possède les 3 cases en coins
		if (pointCase[1][1] == blanc && ( pointCase[1][2] == blanc || pointCase[1][2] == prisParBlanc) 	
				&& (pointCase[2][1] == blanc || pointCase[2][1] == prisParBlanc ))
			score += 10000;
		
		if (casesPlateau[1][8] == blanc && (pointCase[1][7] == blanc || pointCase[1][7] == prisParBlanc) 
				&& (pointCase[2][8] == blanc || pointCase[2][8] == prisParBlanc));
			score += 10000;
			
		if (casesPlateau[8][1] == blanc && (pointCase[7][1] == blanc || pointCase[7][1] == prisParBlanc )
				&& (pointCase[7][2] == blanc || pointCase[7][2] == prisParBlanc) )
			score += 10000;
		
		if (casesPlateau[8][8] == blanc && (pointCase[7][8] == blanc || pointCase[7][8] == prisParBlanc) 
				&& (pointCase[7][7] == blanc || pointCase[7][7] == prisParBlanc ));
			score += 10000;
				
				
			// si blanc les 4 cases de coins 
		if (pointCase[1][1] == blanc && ( pointCase[1][2] == blanc || pointCase[1][2] == prisParBlanc) 	
				&& (pointCase[2][1] == blanc || pointCase[2][1] == prisParBlanc )
				&& (pointCase[2][2] == blanc || pointCase[2][2] == prisParBlanc))
			score += 15000;
		
		if (casesPlateau[1][8] == blanc && (pointCase[1][7] == blanc || pointCase[1][7] == prisParBlanc) 
				&& (pointCase[2][8] == blanc || pointCase[2][8] == prisParBlanc)
				&& (pointCase[2][7] == blanc || pointCase[2][7] == prisParBlanc));
			score += 15000;
			
		if (casesPlateau[8][1] == blanc && (pointCase[7][1] == blanc || pointCase[7][1] == prisParBlanc )
				&& (pointCase[7][2] == blanc || pointCase[7][2] == prisParBlanc)
				&& (pointCase[7][2] == blanc || pointCase[7][2] == prisParBlanc) )
			score += 15000;
		
		if (casesPlateau[8][8] == blanc && (pointCase[7][8] == blanc || pointCase[7][8] == prisParBlanc) 
				&& (pointCase[7][7] == blanc || pointCase[7][7] == prisParBlanc ) 
				&& (pointCase[7][7] == blanc || pointCase[7][7] == prisParBlanc));
			score += 15000;

		
			// réinitialise le tableau des points
		initialiseTableauPoint();
		
		
		
		return score;
	}	
	
	
	/** *************************************************************************
						Fin de la gestion de l'IA
	 ************************************************************************* **/   
		
	
	
}


