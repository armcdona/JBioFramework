/**
 *  - /Tutorial/  - @aidanSawyer[aks5238]
 *  # description: rightPanel (rP) of Welcome page. should house 
 *  # calls: -none-
 *  # called by: /Welcome/
 *  # envelop: /Welcome/
 *  # package: /Welcome/
 *  ! needs to be written/populated. 
 *  ! need to take commands from /Welcome/ lP
 */

import javax.swing.*;

public class Tutorial extends JPanel{
	/*instance variables*/
	private JLabel message;
	
	
	public Tutorial(){
		message = new JLabel("tutorial class");
		this.add(message);
	}
}
