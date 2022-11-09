package interfaces;

public interface Ordinateur {
	
	
		/** fait le choix pour l'IA */
	public int IAchoix();
	
	
		/**  GESTION  NIVEAU 1  : Joue al�atoirement */
	public int niveau1();
	
	
		/** analyse niveau 2 : 
		 GESTION NIVEAU 2 : Suit une r�gle 
		 commence par les extr�mit� pour arriver au centre */
	public int niveau2();
	
	
		/** analyse niveau 3 : calcul nombre de points gagn�s apr�s un coup et prend le meilleur */
	public int niveau3();
	
	
	
	
	/**  PARTIE   MINMAX    */
	
		// lance l'analyse en profondeur
	public int calcIA(int prof);

    	//calcul le score � chaque coup
    public int calcMin(int prof);
    public  int calcMax(int prof);
    
    	//Fonction qui �value le score obtenu en fin de parcours de l'arbre
    public int evalue();
	

}
