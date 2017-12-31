package autoloader.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextPane txtPanel;

	/**
	 * Create the frame.
	 */
	public View() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		txtPanel = new JTextPane();
		txtPanel.setText("File downloader");
		JScrollPane scroll = new JScrollPane(txtPanel);
		contentPane.add(scroll, BorderLayout.CENTER);
		
		setVisible();
	}
	
	public void setVisible() {
		this.setVisible(true);
	}

	public void addMessage(String text) {
		String previousMessages = txtPanel.getText();
		previousMessages += "\n";
		previousMessages += text;

		txtPanel.setText(previousMessages);
		
		System.out.println(text);
	}

}