package interfaces;

public interface Ordinateur {
	
	
		/** fait le choix pour l'IA */
	public int IAchoix();
	
	
		/**  GESTION  NIVEAU 1  : Joue aléatoirement */
	public int niveau1();
	
	
		/** analyse niveau 2 : 
		 GESTION NIVEAU 2 : Suit une règle 
		 commence par les extrémité pour arriver au centre */
	public int niveau2();
	
	
		/** analyse niveau 3 : calcul nombre de points gagnés après un coup et prend le meilleur */
	public int niveau3();
	
	
	
	
	/**  PARTIE   MINMAX    */
	
		// lance l'analyse en profondeur
	public int calcIA(int prof);

    	//calcul le score à chaque coup
    public int calcMin(int prof);
    public  int calcMax(int prof);
    
    	//Fonction qui évalue le score obtenu en fin de parcours de l'arbre
    public int evalue();
	

}
