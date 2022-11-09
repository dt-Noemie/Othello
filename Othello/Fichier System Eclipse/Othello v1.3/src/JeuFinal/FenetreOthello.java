			/** *************************************************************************
			 * 																			*
			 * 					Othello  Solo et Multi (v1.3)							*
			 *																			* 
			 * 					MANDIN   Alexis		// 2008								*
			 * 																			*	
			 * 																			*	
			 ************************************************************************* **/


package JeuFinal;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class FenetreOthello extends javax.swing.JFrame  implements ActionListener
{
	private Othello oth = new Othello();
		
	
		// charge les différentes images
	Icon imageJoueurN = new ImageIcon("images/scoreJN.gif");
	Icon imageJoueurB = new ImageIcon("images/scoreJB.gif");
	Icon imagePionNoir = new ImageIcon("images/pionNoir.gif");
	Icon imagePionBlanc = new ImageIcon("images/pionBlanc.gif");
	Icon imageDebut = new ImageIcon("images/couleurDebut.gif");
	Icon imagePionBlancPose = new ImageIcon("images/pionBlancPose.gif");
	Icon imagePionNoirPose = new ImageIcon("images/pionNoirPose.gif");
	Icon imagePionBlancPris = new ImageIcon("images/pionBlancPris.gif");
	Icon imagePionNoirPris = new ImageIcon("images/pionNoirPris.gif");
	Icon imageCharger = new ImageIcon("images/ouverture.gif");
	Icon imageEnregistrer = new ImageIcon("images/enregistrer.gif");
	Icon imageAide = new ImageIcon("images/aide.gif");
	Icon imageCoupPossible = new ImageIcon("images/coupPossible.gif");
	
		// elements de la barre menu
	private JMenuBar menuJeu;
	private JMenu iconeMenuJeu;
	private JMenu iconeNiveau = new JMenu("* Niveau ");
	private JMenu iconeAide;
	private JMenu iconeCouleurPlateau = new JMenu("* Couleur plateau");
	private JMenu iconePremierJoueur = new JMenu("* Choix 1er joueur :   <Noir>");
	private JMenu espace = new JMenu();
	private JMenu espace1 = new JMenu();
	private JSeparator separateur;
	
	private JMenuItem iconeSolo1 = new JMenuItem("* Solo : niveau 1");
	private JMenuItem iconeSolo2 = new JMenuItem("* Solo : niveau 2");
	private JMenuItem iconeSolo3 = new JMenuItem("* Solo : niveau 3");
	private JMenuItem iconeSolo4 = new JMenuItem("* Solo : coup + 3");
	private JMenuItem iconeSolo5 = new JMenuItem("* Solo : coup + 5");
	private JMenuItem iconeSolo6 = new JMenuItem("* Solo : coup + 7");
	private JMenuItem iconeMulti = new JMenuItem("* Multi - Joueur");
	private JMenuItem enregistrer = new JMenuItem("* Enregistrer Partie", imageEnregistrer);
	private JMenuItem charger = new JMenuItem("* Charger une Partie", imageCharger);
	private JMenuItem iconeChangerCouleurPlateau = new JMenuItem("* Changer la  couleur du plateau");
	private JMenuItem aideInterface = new JMenuItem("* Interface Graphique", imageAide);
	private JMenuItem aideRegles = new JMenuItem("* Les règles de jeu", imageAide);
	private JMenuItem blanc = new JMenuItem("* Blanc");
	private JMenuItem noir = new JMenuItem("* Noir ");
		
	
		// différents JPanel de la fenêtre
	private JPanel fondPrincipale;
	private JPanel panelHaut;
	private JPanel panelDroit;
	private JPanel panelGauche;
	private JPanel panelGaucheHaut;
	private JPanel panelGaucheBas;
	private JPanel panelBas;
	private JPanel grilleCases;
	
	
		// Message en bas d'écran
	private JLabel infoTypePartie ;
	private JLabel infoMessage;	
	
		// cases du plateau
	private JButton casesPlateau[] = new JButton[64]; 
	
		// permet d'afficher le score sur le coté droit.
	private JLabel pionNoir =  new JLabel();
	private JLabel pionBlanc = new JLabel();
	private JLabel scoreNoir = new JLabel();
	private JLabel scoreBlanc = new JLabel();
	
		// Element sur le côté gauche
	private JLabel coupJoue;
	private JLabel coordCoupJoue;
	private JLabel gereCoup;
	 
		// boutons pour revenir en arrière
	private JButton coupPrecedent = new JButton();
	private JButton coupSuivant = new JButton();
	

	private String txt;		// servira à stocker le texte 'blanc' ou 'noir' en fonction du joueur
	
		// permettra de changer la couleur du plateau
	private int valeurRouge = 0;
	private int valeurVert= 220;
	private int valeurBleu = 20;
		
	
		// constructeur
	public FenetreOthello() 
	{
		super();
		initGUI();
	}
	
	
/**  ***************************************************************************
 					  CONSTRUCTION   DE LE  FENETRE					
 **************************************************************************** **/	

	private void initGUI() 
	{		
			// initialise les instances d'Othello
		oth.prepareInstance();
		oth.initialiseJeu();
		
			// ajout les actions aux différents boutons du menu.
		iconeSolo1.addActionListener(this);
		iconeSolo2.addActionListener(this);
		iconeSolo3.addActionListener(this);
		iconeSolo4.addActionListener(this);
		iconeSolo5.addActionListener(this);
		iconeSolo6.addActionListener(this);
		iconeMulti.addActionListener(this);
		enregistrer.addActionListener(this);
		iconeChangerCouleurPlateau.addActionListener(this);
		charger.addActionListener(this);
		coupPrecedent.addActionListener(this);
		coupSuivant.addActionListener(this);
		aideInterface.addActionListener(this);
		aideRegles.addActionListener(this);
		blanc.addActionListener(this);
		noir.addActionListener(this);

		try 
		{
				// ferme le programme grâce à la croix
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				menuJeu = new JMenuBar();
				fondPrincipale = new JPanel();	// Contient les 3 panel d'après
				panelHaut = new JPanel();
				panelDroit = new JPanel();			// panel de droite
				panelGauche = new JPanel();			// panel gauche
					// disposition 
				BoxLayout panelGaucheLayout = new BoxLayout(panelGauche, javax.swing.BoxLayout.Y_AXIS);
				panelGauche.setLayout(panelGaucheLayout);
				
				panelBas = new JPanel();			// panel du bas
				grilleCases = new JPanel(); 		// accueille les boutons de la grille
				infoTypePartie = new JLabel(" * Type de partie :  Solo 1.");	// indique des infos en bas de fenetre
				infoMessage = new JLabel("* Joueur Noir commence !");

				
					// créer la barre menu
				espace.setEnabled(false);
				espace.setPreferredSize(new java.awt.Dimension(5, 27));
				espace1.setEnabled(false);
				espace1.setPreferredSize(new java.awt.Dimension(5, 27));
				
				menuJeu.setBackground(Color.getHSBColor(15, 76, 56));
				setJMenuBar(menuJeu);
				{
					iconeMenuJeu = new JMenu(" Gestion Jeu ");
					iconeAide = new JMenu(" Aide ");
					menuJeu.add(iconeMenuJeu);
					
						// Bouton menu Gestion Jeu
					iconeMenuJeu.setBorder(BorderFactory.createTitledBorder(""));
					iconeMenuJeu.setFont(new java.awt.Font("Comic Sans MS",1,12));
					iconeMenuJeu.setPreferredSize(new java.awt.Dimension(110, 27));
					{
						iconeMenuJeu.add(enregistrer);
						separateur = new JSeparator();
						iconeMenuJeu.add(separateur);
						iconeMenuJeu.add(charger);
					}
					
						// bouton menu niveau
					menuJeu.add(iconeNiveau);
					iconeNiveau.setBorder(BorderFactory.createTitledBorder(""));
					iconeNiveau.setFont(new java.awt.Font("Comic Sans MS",1,12));
					{
						iconeNiveau.add(iconeSolo1);
						
						separateur = new JSeparator();
						iconeNiveau.add(separateur);
						
						iconeNiveau.add(iconeSolo2);
						
						separateur = new JSeparator();
						iconeNiveau.add(separateur);
						
						iconeNiveau.add(iconeSolo3);
						
						separateur = new JSeparator();
						iconeNiveau.add(separateur);
						
						iconeNiveau.add(iconeSolo4);
						
						separateur = new JSeparator();
						iconeNiveau.add(separateur);
						
						iconeNiveau.add(iconeSolo5);
						
						separateur = new JSeparator();
						iconeNiveau.add(separateur);
						
						iconeNiveau.add(iconeSolo6);
						
						separateur = new JSeparator();
						iconeNiveau.add(separateur);
						separateur = new JSeparator();
						iconeNiveau.add(separateur);
						
						iconeNiveau.add(iconeMulti);
					}
					
						// un espace 
					menuJeu.add(espace1);
					
						// bouton pour dire quel joueur commence
					menuJeu.add(iconePremierJoueur);
					iconePremierJoueur.setBorder(BorderFactory.createTitledBorder(""));
					iconePremierJoueur.setForeground(Color.BLUE);
					iconePremierJoueur.setFont(new java.awt.Font("Comic Sans MS",1,12));		
					{					
						separateur = new JSeparator();
						iconePremierJoueur.add(noir);
						iconePremierJoueur.add(separateur);
						iconePremierJoueur.add(blanc);
					}
					
						// bouton pour changer la couleur du plateau
					menuJeu.add(iconeCouleurPlateau);
					iconeCouleurPlateau.setFont(new java.awt.Font("Comic Sans MS",1,12));
					iconeCouleurPlateau.setBorder(BorderFactory.createTitledBorder(""));
					{
						iconeCouleurPlateau.add(iconeChangerCouleurPlateau);
					}
					
					menuJeu.add(espace);
					
						// boutons aide
					menuJeu.add(iconeAide);
					iconeAide.setFont(new java.awt.Font("Comic Sans MS",1,12));
					iconeAide.setBorder(BorderFactory.createTitledBorder(""));
					{
						iconeAide.add(aideInterface);
						separateur = new JSeparator();
						iconeAide.add(separateur);
						iconeAide.add(aideRegles);
					}
				}
				
				// ajout les différents panels au panel principale	
				
					// crée une disposition et on l'applique au panel fondPrincipale
				BorderLayout jPanel1Layout = new BorderLayout();
				fondPrincipale.setLayout(jPanel1Layout);
				
				getContentPane().add(fondPrincipale, BorderLayout.CENTER);
				{
					fondPrincipale.add(panelGauche, BorderLayout.WEST);
					panelGauche.setPreferredSize(new java.awt.Dimension(105, 188));
					{
							// panel de gauche ( haut ) avec la disposition  GridBagLayout
						panelGaucheHaut = new JPanel();
						GridBagLayout panelGaucheHautLayout = new GridBagLayout();
						panelGauche.add(panelGaucheHaut);
						panelGauche.setBackground(Color.lightGray);
						panelGaucheHaut.setBackground(Color.lightGray);
						panelGaucheHaut.setPreferredSize(new java.awt.Dimension(105, 202));
						panelGaucheHautLayout.rowWeights = new double[] {0.0, 0.0, 0.1, 0.0, 0.0 };
						panelGaucheHautLayout.rowHeights = new int[] {40, 40, 20, 20, 30};
						panelGaucheHautLayout.columnWeights = new double[] {0.1};
						panelGaucheHautLayout.columnWidths = new int[] {7};
						panelGaucheHaut.setLayout(panelGaucheHautLayout);
						{
							coupJoue = new JLabel();
							panelGaucheHaut.add(coupJoue, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							coupJoue.setBorder(BorderFactory.createTitledBorder(""));
							coupJoue.setForeground(Color.BLUE);
							coupJoue.setText(" Coup Joué :  ");
						}
						{
							coordCoupJoue = new JLabel();
							panelGaucheHaut.add(coordCoupJoue, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							coordCoupJoue.setForeground(Color.red);
							coordCoupJoue.setText("L: ?  ||  C: ? ");	
						}
						{
							gereCoup = new JLabel();
							panelGaucheHaut.add(gereCoup, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							gereCoup.setFont(new java.awt.Font("Comic Sans MS",1,12));
							gereCoup.setForeground(Color.red);
							gereCoup.setText(" * Gestion Coup : ");
						}
						
							// panel de gauche (bas) avec la disposition GridBagLayout
						panelGaucheBas = new JPanel();
						GridBagLayout panelGaucheBasLayout = new GridBagLayout();
						panelGauche.add(panelGaucheBas);
						panelGaucheBas.setBackground(Color.lightGray);
						panelGaucheBas.setPreferredSize(new java.awt.Dimension(105, 194));
						panelGaucheBasLayout.rowWeights = new double[] {0.1, 0.1, 0.1};
						panelGaucheBasLayout.rowHeights = new int[] {20, 20, 7};
						panelGaucheBasLayout.columnWeights = new double[] {0.1, 0.1};
						panelGaucheBasLayout.columnWidths = new int[] {5, 7};
						panelGaucheBas.setLayout(panelGaucheBasLayout);
						{
							panelGaucheBas.add(coupPrecedent, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							coupPrecedent.setText("<");
							coupPrecedent.setEnabled(false);
						}
						{
							panelGaucheBas.add(coupSuivant, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							coupSuivant.setText(">");
							coupSuivant.setEnabled(false);
						}
					}
				}
				{
						// ajout les différents panel Haut, droit, bas
					fondPrincipale.add(panelHaut, BorderLayout.NORTH);
					panelHaut.setBackground(Color.lightGray);
					panelHaut.setPreferredSize(new java.awt.Dimension(560, 20));
					
					fondPrincipale.add(panelDroit, BorderLayout.EAST);
					panelDroit.setBackground(Color.lightGray);
					panelDroit.setPreferredSize(new java.awt.Dimension(55, 188));
					GridBagLayout panelDroitLayout = new GridBagLayout();
					panelDroitLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
					panelDroitLayout.rowHeights = new int[] {75, 35, 35, 35, 37, 35, 7};
					panelDroitLayout.columnWeights = new double[] {0.1};
					panelDroitLayout.columnWidths = new int[] {7};
					panelDroit.setLayout(panelDroitLayout);
					{
						panelDroit.add(pionNoir, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						pionNoir.setText("  ");
						pionNoir.setBackground(Color.black);
						pionNoir.setIcon(imageJoueurN);
						
					}
					{
						panelDroit.add(pionBlanc, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						pionBlanc.setText("  ");
						pionBlanc.setBackground(Color.white);
						pionBlanc.setIcon(imageJoueurB);
					}
					{
						scoreNoir = new JLabel();
						panelDroit.add(scoreNoir, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						scoreNoir.setText(""+oth.compteurNoir);
					}
					{
						scoreBlanc = new JLabel();
						panelDroit.add(scoreBlanc, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
						scoreBlanc.setText(""+oth.compteurBlanc);
					}
				}

				{
					panelBas.setBackground(Color.lightGray);
					panelBas.setLayout(new GridLayout(1,4));
					fondPrincipale.add(panelBas, BorderLayout.SOUTH);
					panelBas.setPreferredSize(new java.awt.Dimension(497, 47));
					{
						{
							panelBas.add(infoTypePartie);
							panelBas.add(infoMessage);
							infoTypePartie.setPreferredSize(new java.awt.Dimension(103, 35));
							infoTypePartie.setForeground(new Color(0,0,0));
							infoMessage.setForeground(new Color(251,2,2));
						}
					}
				}
				{
						// grilles des cases au centre avecun GridLayout de 8 * 8
					grilleCases.setLayout(new GridLayout(8,8));
					fondPrincipale.add(grilleCases, BorderLayout.CENTER);
					grilleCases.setPreferredSize(new java.awt.Dimension(325, 188));	
						// créations des cases
					{
						for (int i = 0; i < 64; i++)	
						{
							casesPlateau[i] = new JButton();
							casesPlateau[i].setBackground(new Color(10,230,65));
							casesPlateau[i].setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
							
							// ajout les actions aux Cases
							casesPlateau[i].addActionListener(this);
							grilleCases.add(casesPlateau[i]);
						}
					}	
				}
					// applique les couleurs initiale
				couleurDepart();
			}
			
			pack();
			setTitle("Othello Solo et Multi ( v1.3 )   -   A.MANDIN");
			setResizable(false);
			setSize(575, 475);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** **********************************************************
	  			FIN  DE   LA  CREATION   DE  LA  FENETRE    
	 *************************************************************/
	
	
		// Affiche les pions au départ d'une nouvelle partie
	public void couleurDepart()
	{		
		for (int i = 0; i < 64; i++)	
		{
			casesPlateau[i].setIcon(imageDebut);
			casesPlateau[i].setBackground(new Color(0,220,20));
		}
		casesPlateau[27].setIcon(imagePionBlanc);
		casesPlateau[28].setIcon(imagePionNoir);
		casesPlateau[35].setIcon(imagePionNoir);
		casesPlateau[36].setIcon(imagePionBlanc);
		
		
		
			// récupère et affiche les coups possibles
		oth.coupsPossibles();
		
		for (int j = 1; j <  9; j++)
			for (int k = 1; k < 9; k++)
				if (oth.coupPossibles[j][k] == true)
					casesPlateau[oth.getNumeroCase(j,k)].setIcon(imageCoupPossible);
		
	}
	
		//affiche les coups possibles par le biais de croix rouges
	public void afficheCoupPossible()
	{
		oth.coupsPossibles();
		
		for (int j = 1; j <  9; j++)
			for (int k = 1; k < 9; k++)
				if (oth.coupPossibles[j][k] == true)
					casesPlateau[oth.getNumeroCase(j,k)].setIcon(imageCoupPossible);
	}

		// récupère le joueur en cours pour connaitre la couleur
	public void afficheJoueurEnCours()
	{
		if (oth.joueurEnCours() == 1) txt = "NOIR";
		else txt = "BLANC";
			infoMessage.setText("*Joueur  " +txt +"   à vous de  jouer !");
	}
	
		// affiche le score sur l'interface graphique
	public void afficheScore()
	{
		scoreNoir.setText(""+oth.compteurNoir);
		scoreBlanc.setText(""+oth.compteurBlanc);
	}
	
		// affiche les coordonnées de la case joué sur l'interface graphique
	public void afficheCoodCaseJouer()
	{
		if (oth.numeroModele <= 0)
			coordCoupJoue.setText("L: ?  ||  C: ?");
		
		else if ( ((oth.joueurEnCours() == 1 ) || (oth.joueurEnCours() == 2 ))  
				&& (oth.typePartie() == 1))
			coordCoupJoue.setText("L: " +oth.getLigne(oth.listeModele[oth.numeroModele-1].caseJouer) 
					+ "  ||  C: " +oth.getColonne(oth.listeModele[oth.numeroModele-1].caseJouer));
		
		else if ((oth.joueurEnCours() == 1 ) &&  (oth.typePartie() == 0))
			coordCoupJoue.setText("L: " +oth.getLigne(oth.sauvegardeChoixIA[oth.numeroModele]) 
					+ "  ||  C: " +oth.getColonne(oth.sauvegardeChoixIA[oth.numeroModele]) );
	}
	
		// permet d'activer ou de désactiver les boutons précédent et suivant
	public void activerBoutons()
	{
			// précedent
		if ( (oth.numeroModele <= 0) || oth.partieEstFinie())
			coupPrecedent.setEnabled(false);
		else 
			coupPrecedent.setEnabled(true);
		
			// suivant
		if ((oth.listeModele[oth.numeroModele+1] == null) || oth.partieEstFinie())
			coupSuivant.setEnabled(false);
		else coupSuivant.setEnabled(true);
	}
	
		// permet de changer la couleur du plateau
		// avec les valeurs données par le joueur
	public void changerCOuleurPlateau(int rouge, int vert, int bleu)
	{
		for (int i = 0; i < 64; i++)	
		{
			casesPlateau[i].setBackground(new Color(rouge, vert, bleu));
		}
	}
	
	
	/* *************************************************************************
	  		Methode pour exécuter le coup demandé et l'afficher à l'écran  
	 ************************************************************************* */

	
	public void jouerUnCoup()
	{			
		if (oth.joueurEnCours() == 1)
			oth.caseJouer= oth.choixCase();
		
			// si le 2è joueur est un humain
		if ((oth.joueurEnCours() == 2 ) && (oth.typePartie() == 1) && (oth.enTest == false))
			oth.caseJouer = oth.choixCase();
		
			// si le 2è joueur est le PC
		if ((oth.joueurEnCours() == 2) && (oth.typePartie() == 0))
			oth.caseJouer = oth.IAchoix();
			
		int ligne = oth.getLigne(oth.caseJouer);		// lit la ligne de la case choisie
		int colonne = oth.getColonne(oth.caseJouer);	// lit la colonne  de la case choisie
		int couleur;							// couleur du joueur en cours
		int autreCouleur;	
			
		
			// récupère la couleur et le texte du joueur en cours et celle de l'autre joueur
		if ( oth.joueurEnCours() == 1) 
			{	couleur = oth.noir;
				autreCouleur = oth.blanc;
				txt = "NOIR";}
		else 
			{ 	couleur= oth.blanc;
				autreCouleur = oth.noir;
				txt = "BLANC";}
		
			// sauvegarde le plateau
			// si 2 joueurs humains alors on sauvegarde tous les coups, sinon sauvegarde 
			// que les coups humains
		if ( ((oth.joueurEnCours() == 1 ) || (oth.joueurEnCours() == 2 ))  
				&& (oth.typePartie() == 1))
			oth.sauvegarderModele();
		
		else if ((oth.joueurEnCours() == 1 ) &&  (oth.typePartie() == 0))
			oth.sauvegarderModele();
		
			// si le coup est correct
		if (oth.placementCorrect(ligne, colonne, couleur, autreCouleur, true) == true)
		{		
				// sauvegarde le coup du PC pour affiche en interface
			if ((oth.joueurEnCours() == 2) && (oth.typePartie() == 0))
				oth.sauvegardeChoixIA[oth.numeroModele] = oth.caseJouer;
			
				// incrémente numeroModèle
			if ( ((oth.joueurEnCours() == 1 ) || (oth.joueurEnCours() == 2 ))  
					&& (oth.typePartie() == 1))
				oth.numeroModele++;
			else if ((oth.joueurEnCours() == 1 ) &&  (oth.typePartie() == 0))
				oth.numeroModele++;
				
				// change les couleurs en interface	graphique
			rechangeCouleurPion();
			if (oth.joueurEnCours() == 1) casesPlateau[oth.caseJouer].setIcon(imagePionNoirPose);
			if (oth.joueurEnCours() == 2) casesPlateau[oth.caseJouer].setIcon(imagePionBlancPose);
	
				// fin du tour du joueur : changement de joueur
			oth.changeTourJoueur();
	
				// remet à zéro le tableau des cases pris.
			oth.reinitialiseCasesPrises();
			
				// calcul du score 
			oth.calculScore();
			
				// affiche le score
			afficheScore();
			
				// si un des 2 joueurs ne peut jouer on passe son tour 
			if ((oth.compteurBlanc + oth.compteurNoir) < 64) 
			{
				if (oth.jbPeutPlusJouer() == true) oth.joueurEnCours = 1;
				if (oth.jnPeutPlusJouer() == true) oth.joueurEnCours = 2;
			}

				// réinitialise le tableau des coups possibles
			oth.remiseAZeroCoupPossibles();
			
				// récupère les cases possibles à jouer
			oth.coupsPossibles();
			
				// affiche les cases jouables sur l'interface
			afficheCoupPossible();
			
				// affiche en bas de l'écran le joueur en cours
			afficheJoueurEnCours();
			
			// termine la partie si l'une des conditions est vraie.
			if ((oth.partieEstFinie()== true) || (oth.compteurBlanc + oth.compteurNoir == 64) ||
					(oth.compteurBlanc == 0) || (oth.compteurNoir == 0)) 
				finPartie();
			
				// si le joueur blanc est l'IA, on le fait jouer
			else if ((oth.joueurEnCours() == 2) && (oth.typePartie() == 0) && 
					(oth.compteurBlanc + oth.compteurNoir) < 64)
				IAJoue();
		
				// sauvegarde le coup en cours afin de pouvoir annuler l'annulation
			oth.sauvegarderModele();	
			
			// test si le joueur à rejouer pendant la navigation dans l'historique
			// si c'est le cas on met à null le reste du tableau des historiques à partir 
			// du coup qui vient d'être joué
			// cela permettra de désactiver le bouton suivant
			if (oth.aRejouerDansHistorique())
				oth.reinitialiseListeModele(oth.numeroModele);
			
				// affiche les coordonnées de la case joué sur l'interface
			afficheCoodCaseJouer();
			
				// active ou desactive boutons précédent et suivant
			activerBoutons();
			
		}
			// si mauvaise case choisie : demande au joueurs de recommencer
		else 
		{	if ((oth.compteurBlanc + oth.compteurNoir) < 64) infoMessage.setText("INTERDIT ! Joueur  " +txt +"  recommencez !");
			else JOptionPane.showMessageDialog(this, " Veuillez démarrer une nouvelle partie ! ","Erreur", JOptionPane.ERROR_MESSAGE);

			coordCoupJoue.setText("L: ?  ||  C: ? ");
			
			if ((oth.joueurEnCours() == 2) && (oth.typePartie() == 0) 
					&& ((oth.compteurBlanc + oth.compteurNoir) < 64))
				IAJoue();
		}
	}

	/** *************************************************************************
			 				Fin de la méthode  jouerUnCoup()
	 ************************************************************************* **/
	
	
	
	/** *************************************************************************
				PARTIE  CONCERNANT  LE CHANGEMENT DES  IMAGES  DES CASES
	 ************************************************************************* **/

		// RECHANGE LA COULEUR  DES  PIONS  PRIS  AU TOUR  PRECEDENT
	public void rechangeCouleurPion()
	{
		int uneCase = 0;
		for (int i = 1; i < 9; i++)	
			for (int j = 1; j < 9; j++)
			{		// remet les cases en couleur normale
				if (oth.getCouleurCase(i,j) == oth.blanc) 
						casesPlateau[uneCase].setIcon(imagePionBlanc);
					
				else if (oth.getCouleurCase(i,j) == oth.noir) 
						casesPlateau[uneCase].setIcon(imagePionNoir);
				
				else if (oth.getCouleurCase(i, j) == oth.prisParNoir)
					{
						casesPlateau[uneCase].setIcon(imagePionBlancPris);
						oth.casesPlateau[i][j] = oth.noir;
					}
				else if (oth.getCouleurCase(i,j) == oth.prisParBlanc)
					{
						casesPlateau[uneCase].setIcon(imagePionNoirPris);
						oth.casesPlateau[i][j] = oth.blanc;
					}	
				else if ( oth .getCouleurCase(i, j) == oth.libre)
					casesPlateau[uneCase].setIcon(imageDebut);
				
				uneCase++;
			}
	}
	
	
	/** *************************************************************************
						Fin de la partie gérant les images des cases
	 ************************************************************************* **/
	
	
		// gère le coup de l'IA
	public void IAJoue() 
	{	
		jouerUnCoup();
	}
	
			
	
	/** *************************************************************************
	 					Début de la gestion des clics 
	 *************************************************************************** **/
	
	
			// GERE   LES  ACTIONS  DES  BOUTONS
	public void actionPerformed(ActionEvent e) 
	{
			// récupère la source du bouton cliqué
		Object sourceDuClic = e.getSource();

			// gère les boutons du menu
		if (sourceDuClic == iconeSolo1) 
		{	
				oth.typePartie = 0;
				nouvellePartie();
				oth.niveauJeu = 1;
				infoTypePartie.setText(" * Type de partie :  Solo 1.");
				if (oth.joueurEnCoursChoisi == 2) 
				{
					oth.joueurEnCours = 2;
					IAJoue();
				}	
		}
		if (sourceDuClic == iconeSolo2) 
		{	
				oth.typePartie = 0;
				nouvellePartie();
				oth.niveauJeu = 2;
				infoTypePartie.setText(" * Type de partie :  Solo 2.");
				if (oth.joueurEnCoursChoisi == 2) 
				{
					oth.joueurEnCours = 2;
					IAJoue();
				}
		}	
		if (sourceDuClic == iconeSolo3) 
		{	
				oth.typePartie = 0;
				nouvellePartie();
				oth.niveauJeu = 3;
				infoTypePartie.setText(" * Type de partie :  Solo 3.");
				if (oth.joueurEnCoursChoisi == 2) 
				{
					oth.joueurEnCours = 2;
					IAJoue();
				}
		}
		if (sourceDuClic == iconeSolo4) 
		{	
				oth.typePartie = 0;
				nouvellePartie();
				oth.niveauJeu = 4;
				infoTypePartie.setText(" * Type de partie :  Solo 4");
				if (oth.joueurEnCoursChoisi == 2) 
				{
					oth.joueurEnCours = 2;
					IAJoue();
				}
		}		
		if (sourceDuClic == iconeSolo5) 
		{	
				oth.typePartie = 0;
				nouvellePartie();
				oth.niveauJeu = 5;
				infoTypePartie.setText(" * Type de partie :  Solo 5.");
				if (oth.joueurEnCoursChoisi == 2) 
				{
					oth.joueurEnCours = 2;
					IAJoue();
				}
		}
		if (sourceDuClic == iconeSolo6) 
		{	
				oth.typePartie = 0;
				nouvellePartie();
				oth.niveauJeu = 6;
				infoTypePartie.setText(" * Type de partie :  Solo 6.");
				if (oth.joueurEnCoursChoisi == 2) 
				{
					oth.joueurEnCours = 2;
					IAJoue();
				}
		}
		if (sourceDuClic == iconeMulti)
			{
				oth.typePartie = 1;	
				infoTypePartie.setText(" * Type de partie :  Multi." );
				if (oth.joueurEnCoursChoisi == 2) 
					oth.joueurEnCours = 2;
				nouvellePartie();
			}
		
		if (sourceDuClic == enregistrer)
		{
			System.out.println(oth.nomFichier);
			oth.nomFichier = JOptionPane.showInputDialog(this, "Entrez le nom du fichier (au moins 5 caractères) : ", "maPartie");
			if (oth.nomFichier.length() > 4 )
			{
				oth.sauvegardeJeux(oth.nomFichier);
				JOptionPane.showMessageDialog(this, "Enregistrement de la partie : "+oth.nomFichier +" réussi !", "Enregistrement", JOptionPane.INFORMATION_MESSAGE) ;
				oth.nomFichier = null;
			}
			else 
				JOptionPane.showMessageDialog(this, " 'Erreur du nom'   ou   'Annulation de l'enregistrement' ","Erreur", JOptionPane.ERROR_MESSAGE);
		}
		
		if (sourceDuClic == charger)
		{
			oth.nomFichier = JOptionPane.showInputDialog(this, "Entrez le nom du fichier : ", "maPartie");
			oth.chargerUnePartie(oth.nomFichier);
			if (oth.pasDefichier == false)
			{	oth.chargerUnModele(oth.numeroModele);
				rechangeCouleurPion();
				afficheJoueurEnCours();
				afficheScore();
				afficheCoodCaseJouer();
				activerBoutons();
				oth.remiseAZeroCoupPossibles();
				oth.coupsPossibles();
				afficheCoupPossible();
				JOptionPane.showMessageDialog(this, "Chargement de la partie : <"+oth.nomFichier +"  réussi !") ;
			}
			else 
			{
				JOptionPane.showMessageDialog(this, "La partie :    "+oth.nomFichier +"    n'existe pas !","Erreur", JOptionPane.ERROR_MESSAGE) ;
				oth.pasDefichier = false;
			}
		}
			
			// boutons pour changer le premier joueur
		if (sourceDuClic == noir)
		{
			oth.joueurEnCoursChoisi = 1;
			iconePremierJoueur.setText("* Choix 1er joueur :   <Noir>");
		}
		if (sourceDuClic == blanc)
		{
			oth.joueurEnCoursChoisi = 2;
			iconePremierJoueur.setText("* Choix 1er joueur :  <Blanc>");
		}
		
			// gère les clics des boutons suivant et précédent
		if (sourceDuClic == coupPrecedent) 
		{
			oth.chargerModelePrecedent();
			rechangeCouleurPion();
			afficheJoueurEnCours();
			afficheScore();
			afficheCoodCaseJouer();
			activerBoutons();
			oth.remiseAZeroCoupPossibles();
			oth.coupsPossibles();
			afficheCoupPossible();
		}
		if (sourceDuClic == coupSuivant)
		{
			oth.chargerModeleSuivant();
			rechangeCouleurPion();
			afficheJoueurEnCours();
			afficheScore();
			afficheCoodCaseJouer();
			activerBoutons();
			oth.remiseAZeroCoupPossibles();
			oth.coupsPossibles();
			afficheCoupPossible();
		}
		
			// bouton changer couleur du plateau
		if (sourceDuClic == iconeChangerCouleurPlateau)
		{
			String rouge = JOptionPane.showInputDialog(this, "Entrez le degré de rouge (de 0 à 255)", "0");
			String vert = JOptionPane.showInputDialog(this, "Entrez le degré de vert (de 0 à 255)", "220");
			String bleu = JOptionPane.showInputDialog(this, "Entrez le degré de bleu (de 0 à 255)", "20");
			
			if (rouge != null) valeurRouge = Integer.valueOf(rouge).intValue();
			if (vert != null)valeurVert = Integer.valueOf(vert).intValue();
			if (bleu != null)valeurBleu = Integer.valueOf(bleu).intValue();
			
			if ((valeurRouge < 0 || valeurRouge > 255) || (valeurVert < 0 || valeurVert > 255) || (valeurBleu < 0 || valeurBleu > 255))
				JOptionPane.showMessageDialog(this, " Valeur(s) entrée(s) incorrecte(s)","Erreur", JOptionPane.ERROR_MESSAGE);
			
			else changerCOuleurPlateau(valeurRouge, valeurVert, valeurBleu);	
		}
			
			// gère les boutons aide.
		if (sourceDuClic == aideInterface)
			{
				JFrame frame = new JFrame();
				FenetresAide fenetre = new FenetresAide(frame);
				fenetre.typeAide = 1;
				fenetre.setTitle("Interface Graphique");
				fenetre.afficheImage();
			}
		if (sourceDuClic == aideRegles)
			{
				JFrame frame = new JFrame();
				FenetresAide fenetre = new FenetresAide(frame);
				fenetre.typeAide = 2;
				fenetre.setTitle("Règles du jeu");
				fenetre.afficheImage();	
			}
		

				// gère les cases du plateau
		if (sourceDuClic == casesPlateau[0]) 
		{oth.caseChoisie = 0; 
			jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[1]) 
			{oth.caseChoisie = 1; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[2]) 
			{oth.caseChoisie = 2;
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[3]) 
			{oth.caseChoisie = 3; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[4]) 
			{oth.caseChoisie = 4; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[5]) 
			{oth.caseChoisie = 5; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[6]) 
			{oth.caseChoisie = 6; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[7]) 
			{oth.caseChoisie =7 ; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[8]) 
			{oth.caseChoisie = 8; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[9]) 
			{oth.caseChoisie = 9; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[10]) 
			{oth.caseChoisie = 10; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[11]) 
			{oth.caseChoisie = 11 ; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[12]) 
			{oth.caseChoisie = 12; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[13]) 
			{oth.caseChoisie = 13; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[14]) 
			{oth.caseChoisie = 14; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[15]) 
			{oth.caseChoisie = 15; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[16]) 
			{oth.caseChoisie = 16; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[17]) 
			{oth.caseChoisie = 17; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[18]) 
			{oth.caseChoisie = 18; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[19]) 
			{oth.caseChoisie = 19; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[20]) 
			{oth.caseChoisie = 20; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[21]) 
			{oth.caseChoisie = 21; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[22]) 
			{oth.caseChoisie = 22; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[23]) 
			{oth.caseChoisie = 23; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[24]) 
			{oth.caseChoisie = 24; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[25]) 
			{oth.caseChoisie = 25; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[26]) 
			{oth.caseChoisie = 26; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[27]) 
			{oth.caseChoisie = 27; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[28]) 
			{oth.caseChoisie = 28; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[29]) 
			{oth.caseChoisie = 29; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[30]) 
			{oth.caseChoisie = 30; 
				jouerUnCoup();}	
		else if (sourceDuClic == casesPlateau[31]) 
			{oth.caseChoisie = 31; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[32]) 
			{oth.caseChoisie = 32; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[33]) 
			{oth.caseChoisie = 33; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[34]) 
			{oth.caseChoisie = 34; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[35]) 
			{oth.caseChoisie = 35; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[36]) 
			{oth.caseChoisie = 36; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[37]) 
			{oth.caseChoisie = 37; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[38]) 
			{oth.caseChoisie = 38; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[39]) 
			{oth.caseChoisie = 39; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[40]) 
			{oth.caseChoisie = 40; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[41]) 
			{oth.caseChoisie = 41; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[42]) 
			{oth.caseChoisie = 42; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[43]) 
			{oth.caseChoisie =43 ; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[44]) 
			{oth.caseChoisie = 44; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[45]) 
			{oth.caseChoisie = 45; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[46]) 
			{oth.caseChoisie = 46; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[47]) 
			{oth.caseChoisie = 47; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[48]) 
			{oth.caseChoisie = 48; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[49]) 
			{oth.caseChoisie = 49; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[50]) 
			{oth.caseChoisie = 50; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[51]) 
			{oth.caseChoisie = 51; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[52]) 
			{oth.caseChoisie = 52; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[53]) 
			{oth.caseChoisie = 53; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[54]) 
			{oth.caseChoisie = 54; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[55]) 
			{oth.caseChoisie = 55; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[56]) 
			{oth.caseChoisie = 56; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[57]) 
			{oth.caseChoisie = 57; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[58]) 
			{oth.caseChoisie = 58; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[59]) 
			{oth.caseChoisie = 59; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[60]) 
			{oth.caseChoisie = 60; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[61]) 
			{oth.caseChoisie = 61; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[62]) 
			{oth.caseChoisie = 62; 
				jouerUnCoup();}
		else if (sourceDuClic == casesPlateau[63]) 
			{oth.caseChoisie = 63; 
				jouerUnCoup();}
	}
	
	/** *************************************************************************
						Fin de la gestion des clics
	 ************************************************************************* **/
	
	
		//  INITIALISE  UNE  NOUVELLE PARTIE
	public void nouvellePartie()
	{
		oth.prepareInstance();
		oth.initialiseJeu();
		couleurDepart();
		changerCOuleurPlateau(valeurRouge, valeurVert, valeurBleu);
		scoreNoir.setText(""+oth.compteurNoir);
		scoreBlanc.setText(""+oth.compteurBlanc);
		activerBoutons();
		
		if (oth.joueurEnCours == 1)
			infoMessage.setText("* Joueur Noir commence !");
		else if (oth.joueurEnCours == 2)
			infoMessage.setText("* Joueur Blanc commence !");
	}
	
		// FIN  D'UNE  PARTIE 
	public void finPartie()
	{
		if (oth.compteurBlanc > oth.compteurNoir)
		{
			infoMessage.setText("* Partie Finie !  Blanc gagne !");
			JOptionPane.showMessageDialog(this,"* Partie Finie : Blanc gagne avec " +oth.compteurBlanc +" Pions !"); 
		}
		
		else if (oth.compteurBlanc < oth.compteurNoir)
		{
			infoMessage.setText("* Partie Finie !  Noir gagne !");
			JOptionPane.showMessageDialog(this,"* Partie Finie : Noir gagne avec " +oth.compteurNoir +" Pions !");
		}
		
		else if (oth.compteurBlanc == oth.compteurNoir)
		{
			infoMessage.setText("* Partie Finie !"); 
			JOptionPane.showMessageDialog(this,"* Partie Finie : Egalité !");
		}
	}
	
	/** *************************************************************************
		
	 ************************************************************************* **/

		// FONCTION PRINCIPALE POUR DEMARRER   LE  JEU
	public static void main(String[] args) 
	{	
				FenetreOthello fe = new FenetreOthello();	
				fe.setLocationRelativeTo(null);
				fe.setVisible(true);

	}

}
