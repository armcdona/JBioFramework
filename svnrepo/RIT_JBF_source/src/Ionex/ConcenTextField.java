package Ionex;

import javax.swing.JTextField;
import java.awt.Event;

class ConcenTextField extends JTextField
{
	String lastValue;

	public ConcenTextField( String string)
	{
		super( string);
	}

	public boolean keyUp( Event evt, int key)
	{
		//if the field is now empty, it's OK
		String strText = getText().trim();
		if( strText.equals( "")){
			lastValue = getText();
			return true;
		}

		//if the key is the return or tab or an action key, don't handle it
		if(( key == 9) || (key == 10) || (evt.id == Event.KEY_ACTION_RELEASE)){
			return false;
		}

		//if the key pressed isn't a digit or a period or backspace, don't keep it
		if((( key >= 48 ) && ( key <= 57)) || 
			( key == 46) || (key == 8) || (key == 127)){

			try{
				float fValue = Float.valueOf( getText().trim()).floatValue();
				if(( fValue >= 0.0 ) && (fValue <= 1.0)){
					lastValue = getText();
					return true;
				}
			}
			catch( NumberFormatException e){
				setText( lastValue);
	//				Toolkit.getDefaultToolkit().beep();
			}
		}

		//invalid value entered
		setText( lastValue);
//		Toolkit.getDefaultToolkit().beep();

		return true;
	}
}
