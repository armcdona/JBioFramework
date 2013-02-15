package one.d.electrophoresis;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class FileReadingDemo extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileReadingDemo frame = new FileReadingDemo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FileReadingDemo() {

		//create the main pale
		contentPane = new JPanel();
		setContentPane(contentPane);
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		//create the text area
		final JTextArea textArea = new JTextArea(10, 25);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		// textArea.setFont(new Font("Serif", Font.BOLD, 12));
		textArea.setWrapStyleWord(true);
		// scrolling 
		final JScrollPane jsp = new JScrollPane(textArea);
		
		panel.add(jsp, BorderLayout.CENTER);
		contentPane.add(panel, BorderLayout.NORTH);

		 
		
		JButton btnChooseFile = new JButton("get file");
		// handle button event                                                                                                                                  
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// get the user directory 
				String cwd = System.getProperty("user.dir");
				final JFileChooser jfc = new JFileChooser(cwd);
				if (jfc.showOpenDialog(getParent()) != JFileChooser.APPROVE_OPTION)
					return;

				//get selected  file 
				File file = jfc.getSelectedFile();

				// process file
				readin(file, textArea);

			}

		});

		JPanel btnPanel = new JPanel();

		btnPanel.add(btnChooseFile);
		JButton getInfo = new JButton("get info");
		btnPanel.add(getInfo);

		getInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] lines = textArea.getText().split("\n");

				String header = lines[0];
				String seq = "";
				StringBuffer strBuf = new StringBuffer();

				for (int i = 1; i < lines.length; i++) {
					strBuf.append(lines[i]);
				}

				seq=strBuf.toString();
				char arr[] = seq.toCharArray(); // convert the String object to
												// array of char

				int counter = 0;
				// iterate over the array using the for-each loop.
				for (char c : arr) {
					counter++;
					
					
					checkAA(c);
				}

				JTextArea jtx = null;

				JOptionPane.showMessageDialog(null, header+"conter "+counter);

			}

			private void checkAA(char c) {

				
			}
		});

		panel.add(btnPanel, BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 450, 300);
		pack();

	}

	// read the file into the pane

	static void readin(File f, JTextComponent pane) {
		try {
			FileReader fr = new FileReader(f);
			pane.read(fr, null);
			fr.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}




static void calc(){
	
}
}