package trigtrot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui extends JFrame implements ActionListener {
	JPanel jp = new JPanel();
	JLabel jl = new JLabel();
	JLabel jl1 = new JLabel();
	JTextField jt = new JTextField(30);
	public static String input = "";
	public static TrigTrot trigtrot;
	public static Orange orange;
	public static Bullet bullet;

    public Gui() {

        setTitle("Pick");
        setVisible(true);
        setSize(TrigTrot.WIDTH, TrigTrot.HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        jl1.setText("There are 3 different modes for this game: paint, orange and bullet. Please type on in the box, and then hit enter(case sensitive)");
        jp.add(jl1);
        jp.add(jt);
        
        jt.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				input = jt.getText();
				jl.setText("Style chosen: " + input);
				if(input.equals("paint")) trigtrot = new TrigTrot();
		        if(input.equals("orange")) orange = new Orange();
		        if(input.equals("bullet")) bullet = new Bullet();
			}
        	
        });
        

        jp.add(jl);
        add(jp);

        
        
    }


    public static void main(String[] args) {
        new Gui();
    }


	public void actionPerformed(ActionEvent e) {
		
	}



}
