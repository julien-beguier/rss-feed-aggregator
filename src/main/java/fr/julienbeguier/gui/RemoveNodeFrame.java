package fr.julienbeguier.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.uncommons.swing.SpringUtilities;

import fr.julienbeguier.controller.Controller;
import fr.julienbeguier.data.RssCategory;
import fr.julienbeguier.data.RssData;
import fr.julienbeguier.gui.action.Action;
import fr.julienbeguier.utils.Constants;

public class RemoveNodeFrame extends JFrame {

	//GUI
	private Dimension							dimension;
	private ImageIcon							imageIcon;
	private JComboBox<Object>					categoryComboBox;
	private JLabel								categoryLabel;
	private JComboBox<Object>					feedComboBox;
	private JLabel								feedLabel;
	private JButton								cancelButton;
	private JButton								okButton;

	// OTHER
	private Controller							controller;
	private fr.julienbeguier.gui.MainFrame.Type	type;
	private RssData								rssData;

	/**
	 * For both removing a category or a feed
	 */
	private static final long serialVersionUID = -4667515097650784277L;

	public RemoveNodeFrame(Controller controller, fr.julienbeguier.gui.MainFrame.Type type, RssData rssData) {
		super(Constants.REMOVENODE_TITLE(type));

		this.controller = controller;
		this.type = type;
		this.rssData = rssData;
		this.dimension = new Dimension(Constants.REMOVENODE_WIDTH, Constants.REMOVENODE_HEIGHT(type));

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

		int numPairs = 0;

		if (this.type == fr.julienbeguier.gui.MainFrame.Type.Feed) {
			// Feed

			for (Map.Entry<String, RssCategory> entry : this.rssData.getRssCategories().entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			this.categoryComboBox = new JComboBox<Object>(this.rssData.getCategories().toArray());
			this.categoryComboBox.setSelectedIndex(0);
			this.categoryComboBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent event) {
					if (event.getStateChange() == ItemEvent.SELECTED) {
						String item = (String) event.getItem();
						if (!rssData.getCategories().contains(item)) {
							return;
						}
						feedComboBox.setModel(new DefaultComboBoxModel<Object>(rssData.getRssCategories().get(item).getFeeds().toArray()));
					}
				}
			});
			this.categoryLabel = new JLabel(Constants.REMOVENODE_LABEL_CATEGORY);
			this.feedComboBox = new JComboBox<Object>(this.rssData.getRssCategories().get(this.rssData.getCategories().get(0).toString()).getFeeds().toArray());
			this.feedLabel = new JLabel(Constants.REMOVENODE_LABEL_FEED);
			numPairs = 2;

			p.add(this.categoryLabel);
			this.categoryLabel.setLabelFor(this.categoryComboBox);
			p.add(this.categoryComboBox);
			p.add(this.categoryLabel);
			this.categoryLabel.setLabelFor(this.categoryComboBox);
			p.add(this.categoryComboBox);
			p.add(this.feedLabel);
			this.feedLabel.setLabelFor(this.feedComboBox);
			p.add(this.feedComboBox);

		}else {
			// Category
			this.categoryComboBox = new JComboBox<Object>(this.rssData.getCategories().toArray());
			this.categoryComboBox.setSelectedIndex(0);
			this.categoryLabel = new JLabel(Constants.REMOVENODE_LABEL_CATEGORY);
			numPairs = 1;

			p.add(this.categoryLabel);
			this.categoryLabel.setLabelFor(this.categoryComboBox);
			p.add(this.categoryComboBox);

		}

		SpringUtilities.makeCompactGrid(p, numPairs, 2, 6, 6, 6, 6);
		this.getContentPane().add(p);

		this.cancelButton = new JButton(Constants.REMOVENODE_BUTTON_CANCEL);
		this.cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		this.okButton = new JButton(Constants.REMOVENODE_BUTTON_OK);
		this.okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String category = null;
				String feed = null;

				Map<String, Object> params = new HashMap<String, Object>();
				Action action = null;
				if (type == fr.julienbeguier.gui.MainFrame.Type.Feed) {
					feed = (String) feedComboBox.getSelectedItem();
					category = (String) categoryComboBox.getSelectedItem();

					params.put(Constants.VALUE_FEED, feed);
					params.put(Constants.VALUE_CATEGORY, category);
					action = new Action(Action.ACTION_REMOVE_FEED);
					action.setParams(params);
				}else { // Category
					category = (String) categoryComboBox.getSelectedItem();

					params.put(Constants.VALUE_CATEGORY, category);
					action = new Action(Action.ACTION_REMOVE_CATEGORY);
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
