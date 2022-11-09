/** permet d'afficher une nouvelle fenêtre donnant des renseignements sur le jeu **/


package JeuFinal;
import java.awt.BorderLayout;
import javax.swing.JButton;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class FenetresAide extends javax.swing.JDialog 
{

	private JButton imageAide;
	private Icon aideInterface = new ImageIcon("images/interface.gif");
	private Icon aideRegles = new ImageIcon("images/regles.gif");
	
	// definit le type d'aide 1 : Interface ;  2 : Règles
	protected int typeAide = 0;

	
	public FenetresAide(JFrame frame) {
		super(frame);
		initGUI();
	}
	
	private void initGUI() {
		try 
		{
			{
				imageAide = new JButton();
				imageAide.setSize(500,350);
				getContentPane().add(imageAide, BorderLayout.CENTER);
			}
			
			setSize(510, 390);
			setLocationRelativeTo(null);
			
			setAlwaysOnTop(true);
			setVisible(true);
		} catch (Exception e) 
		  {
			e.printStackTrace();
		  }
	}
		
		// affiche l'image correspond au choix de l'utilisateur
	public void afficheImage()
	{
		if (typeAide == 1) imageAide.setIcon(aideInterface);
		if (typeAide == 2) imageAide.setIcon(aideRegles);
	}

}
