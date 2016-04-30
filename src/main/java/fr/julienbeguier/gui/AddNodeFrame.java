package fr.julienbeguier.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.uncommons.swing.SpringUtilities;

import fr.julienbeguier.controller.Controller;
import fr.julienbeguier.gui.action.Action;
import fr.julienbeguier.utils.Constants;

public class AddNodeFrame extends JFrame {

	// GUI
	private Dimension							dimension;
	private ImageIcon							imageIcon;
	private JTextField							textField;
	private JLabel								textLabel;
	private JComboBox<Object>					categoryComboBox;
	private JLabel								categoryLabel;
	private JTextField							urlField;
	private JLabel								urlLabel;
	private JButton								cancelButton;
	private JButton								okButton;
	
	// OTHER
	private fr.julienbeguier.gui.MainFrame.Type	type;
	private Controller							controller;
	private List<String>						categories;

	/**
	 * For both adding a category or a feed
	 */
	private static final long serialVersionUID = 7200785460735259827L;

	public AddNodeFrame(Controller controller, fr.julienbeguier.gui.MainFrame.Type type, List<String> categories) {
		super(Constants.ADDNODE_TITLE(type));

		this.controller = controller;
		this.type = type;
		this.categories = categories;
		this.dimension = new Dimension(Constants.ADDNODE_WIDTH, Constants.ADDNODE_HEIGHT(type));

		this.imageIcon = new ImageIcon(this.getClass().getResource(Constants.PATH_ICON_PROGRAM));
		this.setIconImage(this.imageIcon.getImage());
		this.setSize(this.dimension);
		this.setPreferredSize(this.dimension);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
	}

	public void initGUI() {
		JPanel p = new JPanel(new SpringLayout());
		
		this.textField = new JTextField(20);
		this.textLabel = new JLabel(Constants.ADDNODE_LABEL_TEXT(this.type), JLabel.TRAILING);
		int numPairs = 0;
		
		if (this.type == fr.julienbeguier.gui.MainFrame.Type.Feed) {
			// Feed
			this.categoryComboBox = new JComboBox<Object>(this.categories.toArray());
			this.categoryLabel = new JLabel(Constants.ADDNODE_LABEL_CATEGORY);
			this.urlField = new JTextField(20);
			this.urlLabel = new JLabel(Constants.ADDNODE_LABEL_URL);
			numPairs = 3;
			
			p.add(this.textLabel);
			this.textLabel.setLabelFor(this.textField);
			p.add(this.textField);
			p.add(this.categoryLabel);
			this.categoryLabel.setLabelFor(this.categoryComboBox);
			p.add(this.categoryComboBox);
			p.add(this.urlLabel);
			this.urlLabel.setLabelFor(this.urlField);
			p.add(this.urlField);

		}else {
			// Category
			numPairs = 1;
			
			p.add(this.textLabel);
			this.textLabel.setLabelFor(this.textField);
			p.add(this.textField);

		}

		SpringUtilities.makeCompactGrid(p, numPairs, 2, 6, 6, 6, 6);
		this.getContentPane().add(p);

		this.cancelButton = new JButton(Constants.ADDNODE_BUTTON_CANCEL);
		this.cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		this.okButton = new JButton(Constants.ADDNODE_BUTTON_OK);
		this.okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = null;
				String category = null;
				String url = null;
				
				Map<String, Object> params = new HashMap<String, Object>();
				Action action = null;
				if (type == fr.julienbeguier.gui.MainFrame.Type.Feed) {
					text = textField.getText();
					category = (String) categoryComboBox.getSelectedItem();
					url = urlField.getText();

					if (text.isEmpty() || category.isEmpty() || url.isEmpty()) {
						return;
					}
					
					try {
						new URL(url);
					} catch (MalformedURLException e1) {
						JOptionPane.showMessageDialog(null, Constants.ADDNODE_WARNING_URL_MALFORMED_POPUP_MESSAGE, Constants.ADDNODE_WARNING_URL_MALFORMED_POPUP_TITLE, JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					params.put(Constants.VALUE_FEED, text);
					params.put(Constants.VALUE_CATEGORY, category);
					params.put(Constants.VALUE_URL, url);
					action = new Action(Action.ACTION_ADD_FEED);
					action.setParams(params);
				}else { // Category
					text = textField.getText();

					if (text.isEmpty()) {
						return;
					}
					
					params.put(Constants.VALUE_CATEGORY, text);
					action = new Action(Action.ACTION_ADD_CATEGORY);
					action.setParams(params);
				}
				
				controller.control(action);
			}
		});

		Box hBox = Box.createHorizontalBox();
		hBox.add(this.cancelButton);
		hBox.add(Box.createRigidArea(new Dimension(15, 0)));
		hBox.add(this.okButton);
		this.getContentPane().add(hBox);
		this.getContentPane().add(Box.createRigidArea(new Dimension(0, 3)));
	}
}
