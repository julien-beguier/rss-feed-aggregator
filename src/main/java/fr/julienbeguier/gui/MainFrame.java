package fr.julienbeguier.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;

import fr.julienbeguier.configuration.Configuration;
import fr.julienbeguier.controller.Controller;
import fr.julienbeguier.data.RssCategory;
import fr.julienbeguier.data.RssData;
import fr.julienbeguier.gui.action.Action;
import fr.julienbeguier.nodes.AbstractNode;
import fr.julienbeguier.nodes.AbstractNode.NodeType;
import fr.julienbeguier.nodes.CategoryNode;
import fr.julienbeguier.nodes.EmptyNode;
import fr.julienbeguier.nodes.FeedNode;
import fr.julienbeguier.nodes.RootNode;
import fr.julienbeguier.observer.Notification;
import fr.julienbeguier.observer.Observer;
import fr.julienbeguier.utils.Constants;
import fr.valentinpinilla.library.FluxRss;

public class MainFrame extends JFrame implements Observer {

	// GUI
	private Dimension				screenDimension;
	private Dimension				ratioDimension;
	private ImageIcon				imageIcon;
	private ImageIcon				aboutImageIcon;

	// GUI MENU
	private JMenuBar				menuBar;
	private JMenu					rssMenu;
	private JMenu					settingsMenu;
	private JMenu					aboutMenu;
	private JMenuItem				addFeedMenuItem;
	private JMenuItem				addCategoryMenuItem;
	private JMenuItem				removeFeedMenuItem;
	private JMenuItem				removeCategoryMenuItem;
	private JMenuItem				quitMenuItem;
	private JMenuItem				saveSettingsMenuItem;
	private JMenuItem				aboutMenuItem;
	private AddNodeFrame			anf;
	private RemoveNodeFrame			rnf;

	// GUI MAIN
	JEditorPane						jep;

	// GUI LEFT MENU (TREE)
	private JScrollPane				treeScrollPanel;
	private JTree					treeFeeds;
	private DefaultMutableTreeNode	rootNode;

	// RSS
	private JScrollPane				webScrollPanel;

	// OTHER
	private Controller				controller;
	private RssData					rssData;

	/**
	 * Main frame for the rss feed aggregator
	 */
	private static final long serialVersionUID = -7343589863306023739L;

	public enum Type {
		Feed,
		Category;
	}

	public enum Operation {
		Add,
		Delete;
	}

	public MainFrame(Controller controller, Dimension dimension, RssData rssData) {
		super(Constants.MAINFRAME_TITLE);

		this.controller = controller;
		this.screenDimension = dimension;
		this.rssData = rssData;
		this.ratioDimension = new Dimension((int) (this.screenDimension.getWidth() / Constants.RATIO), (int) (this.screenDimension.getHeight() / Constants.RATIO));

		this.imageIcon = new ImageIcon(this.getClass().getResource(Constants.PATH_ICON_PROGRAM));
		this.aboutImageIcon = new ImageIcon(this.getClass().getResource(Constants.PATH_ICON_ABOUTUS));
		this.setIconImage(this.imageIcon.getImage());
		this.setSize(this.ratioDimension);
		this.setPreferredSize(this.ratioDimension);
		this.setLocation(10, 10);
		this.setLayout(new BorderLayout());

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				Configuration.getInstance().saveConfiguration();
				System.exit(0);
			}
		});
	}

	public void initGUI() {
		// MENU
		this.menuBar = new JMenuBar();
		this.rssMenu = new JMenu(Constants.JMENU_RSSMENU_TITLE);
		this.settingsMenu = new JMenu(Constants.JMENU_SETTINGS_TILTE);
		this.aboutMenu = new JMenu(Constants.JMENU_ABOUTMENU_TITLE);
		this.addFeedMenuItem = new JMenuItem(Constants.JMENUITEM_ADD_FEED_TITLE);
		this.addCategoryMenuItem = new JMenuItem(Constants.JMENUITEM_ADD_CATEGORY_TITLE);
		this.removeFeedMenuItem = new JMenuItem(Constants.JMENUITEM_REMOVE_FEED_TITLE);
		this.removeCategoryMenuItem = new JMenuItem(Constants.JMENUITEM_REMOVE_CATEGORY_TITLE);
		this.quitMenuItem = new JMenuItem(Constants.JMENUITEM_QUIT_TITLE);
		this.saveSettingsMenuItem = new JMenuItem(Constants.JMENUITEM_SAVE_CONFIGURATION_TITLE);
		this.aboutMenuItem = new JMenuItem(Constants.JMENUITEM_ABOUT_TITLE);

		this.addFeedMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				anf = new AddNodeFrame(controller, Type.Feed, rssData.getCategories());
				anf.initGUI();
				anf.setVisible(true);
			}
		});
		this.addCategoryMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				anf = new AddNodeFrame(controller, Type.Category, rssData.getCategories());
				anf.initGUI();
				anf.setVisible(true);
			}
		});
		this.removeFeedMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				rnf = new RemoveNodeFrame(controller, Type.Feed, rssData);
				rnf.initGUI();
				rnf.setVisible(true);
			}
		});
		this.removeCategoryMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				rnf = new RemoveNodeFrame(controller, Type.Category, rssData);
				rnf.initGUI();
				rnf.setVisible(true);
			}
		});
		this.quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
		this.quitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Configuration.getInstance().saveConfiguration();
				System.exit(0);
			}
		});
		this.saveSettingsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Configuration.getInstance().saveConfiguration();
			}
		});
		this.aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null, Constants.ABOUT_MESSAGE, Constants.ABOUT_TITLE, JOptionPane.INFORMATION_MESSAGE, aboutImageIcon);
			}
		});	
		this.rssMenu.add(this.addFeedMenuItem);
		this.rssMenu.add(this.addCategoryMenuItem);
		this.rssMenu.add(this.removeFeedMenuItem);
		this.rssMenu.add(this.removeCategoryMenuItem);
		this.rssMenu.add(this.quitMenuItem);
		this.settingsMenu.add(this.saveSettingsMenuItem);
		this.aboutMenu.add(this.aboutMenuItem);
		this.menuBar.add(this.rssMenu);
		this.menuBar.add(settingsMenu);
		this.menuBar.add(this.aboutMenu);
		this.setJMenuBar(this.menuBar);

		// LEFT
		this.rootNode = new DefaultMutableTreeNode(new RootNode());
		this.treeFeeds = new JTree(this.rootNode);
		this.treeFeeds.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeFeeds.getLastSelectedPathComponent();

				if (node == null) {
					return;
				}

				AbstractNode an = (AbstractNode) node.getUserObject();
				if (an.getNodeType() == NodeType.Feed) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put(Constants.VALUE_URL, an.getFeedUrl());
					Action action = new Action(Action.ACTION_VIEW_FEED, params);
					controller.control(action);
				}
			}
		});
		this.treeFeeds.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.treeScrollPanel = new JScrollPane(this.treeFeeds);
		this.treeScrollPanel.setPreferredSize(new Dimension((int) (this.ratioDimension.getWidth() / 5), (int) (this.ratioDimension.getHeight())));

		// MAIN
		this.jep = new JEditorPane();
		this.jep.setEditable(false);
		this.jep.setText("");
		this.jep.setContentType("text/html");
		this.webScrollPanel = new JScrollPane(this.jep);

		this.getContentPane().add(this.treeScrollPanel, BorderLayout.WEST);
		this.getContentPane().add(this.webScrollPanel, BorderLayout.CENTER);
	}

	public void initRSS() {
		JSONArray categories = Configuration.getInstance().getFeeds().getJSONArray(Configuration.getInstance().getKeyFeedsCategories());
		JSONArray feeds = Configuration.getInstance().getFeeds().getJSONArray(Configuration.getInstance().getKeyFeedsRssFeeds());

		Iterator<Object> itCat = categories.iterator();
		while (itCat.hasNext()) {
			JSONObject cat = (JSONObject) itCat.next();

			createCategoryNode(new DefaultMutableTreeNode((AbstractNode) new CategoryNode(cat.getString(Configuration.getInstance().getKeyFeedsCategoriesName()))));
		}
		Iterator<Object> itFeeds = feeds.iterator();
		while (itFeeds.hasNext()) {
			JSONObject feed = (JSONObject) itFeeds.next();

			try {
				createFeedNode(new DefaultMutableTreeNode((AbstractNode) new FeedNode(feed.getString(Configuration.getInstance().getKeyFeedsCategoriesName()), feed.getString(Configuration.getInstance().getKeyFeedsRssFeedsCategory()), new URL(feed.getString(Configuration.getInstance().getKeyFeedsRssFeedsUrl())))));
			} catch (JSONException | MalformedURLException e) {
				e.printStackTrace();
			}
		}
		this.opentree();
	}

	private void createFeedNode(DefaultMutableTreeNode node) {
		FeedNode feed = (FeedNode) node.getUserObject();

		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = this.rootNode.preorderEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode n = e.nextElement();
			AbstractNode an = (AbstractNode) n.getUserObject();

			if (an.getNodeType() == NodeType.Category) {
				if (an.getName() != null && an.getName().equals(feed.getCategory())) {
					if (n.getChildCount() == 1 && ((DefaultMutableTreeNode) n.getChildAt(0)).getUserObject().toString().equals(Constants.NODE_EMPTY)) {
						// first remove the "empty" child
						n.removeAllChildren();
					}					
					DefaultTreeModel model = (DefaultTreeModel) treeFeeds.getModel();
					n.add(node);
					model.reload(n);

					// fill the data list
					this.updateDataLists(Operation.Add, Type.Feed, feed.getName(), feed.getCategory());

					return;
				}
			}
		}

		// category doesn't exist
		JOptionPane.showMessageDialog(null, Constants.MAINFRAME_RSSTREE_CATEGORY_NOT_FOUND_TITLE, Constants.MAINFRAME_RSSTREE_CATEGORY_NOT_FOUND_MESSAGE(feed.getCategory()), JOptionPane.WARNING_MESSAGE);
	}

	private void createCategoryNode(DefaultMutableTreeNode node) {
		CategoryNode category = (CategoryNode) node.getUserObject();

		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = this.rootNode.preorderEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode n = e.nextElement();
			AbstractNode an = (AbstractNode) n.getUserObject();

			if (an.getNodeType() == NodeType.Category) {
				if (an.getName() != null && an.getName().equals(category.getName())) {
					// category already exists so do nothing (popup warning ?)
					return;
				}
			}
		}

		// otherwise, fill the new category with the "empty' object and add it to the root node
		node.add(new DefaultMutableTreeNode(new EmptyNode()));
		DefaultTreeModel model = (DefaultTreeModel) treeFeeds.getModel();
		this.rootNode.add(node);
		model.reload(this.rootNode);

		// fill the data list
		this.updateDataLists(Operation.Add, Type.Category, category.getName());
	}

	private void destroyFeedNode(String feedName, String categoryName) {
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> categories = this.rootNode.preorderEnumeration();
		while (categories.hasMoreElements()) {
			DefaultMutableTreeNode category = categories.nextElement();
			AbstractNode anCategory = (AbstractNode) category.getUserObject();

			if (anCategory.getNodeType() == NodeType.Category) {
				if (anCategory.getName() != null && anCategory.getName().equals(categoryName)) {

					@SuppressWarnings("unchecked")
					Enumeration<DefaultMutableTreeNode> feeds = category.preorderEnumeration();
					while (feeds.hasMoreElements()) {
						DefaultMutableTreeNode feed = feeds.nextElement();
						AbstractNode anFeed = (AbstractNode) feed.getUserObject();

						if (anFeed.getNodeType() == NodeType.Feed) {
							if (anFeed.getName() != null && anFeed.getName().equals(feedName)) {

								category.remove(feed);
								DefaultTreeModel model = (DefaultTreeModel) treeFeeds.getModel();
								model.reload(category);

								// update the data list
								this.updateDataLists(Operation.Delete, Type.Feed, feedName, categoryName);
								return;
							}
						}
					}
				}
			}
		}
	}

	private void destroyCategoryNode(String categoryName) {
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = this.rootNode.preorderEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode n = e.nextElement();
			AbstractNode an = (AbstractNode) n.getUserObject();

			if (an.getNodeType() == NodeType.Category) {
				if (an.getName() != null && an.getName().equals(categoryName)) {
					this.rootNode.remove(n);
					DefaultTreeModel model = (DefaultTreeModel) this.treeFeeds.getModel();
					model.reload(this.rootNode);

					// update the data list
					this.updateDataLists(Operation.Delete, Type.Category, categoryName);
					return;
				}
			}
		}
	}

	private void opentree() {
		for (int i = 0; i < this.treeFeeds.getRowCount(); i++) {
			this.treeFeeds.expandRow(i);
		}
	}

	private void updateDataLists(Operation operation, Type type, String ... vars) {
		if (operation == Operation.Add) {
			// OPERATION ADD
			if (type == Type.Feed) {
				String feedName = vars[0];
				String feedCategory = vars[1];

				// filling RssCategory
				Map<String, RssCategory> rssCategories = this.rssData.getRssCategories();
				RssCategory rssCategory;
				if (rssCategories.get(feedCategory) == null) {
					rssCategory = new RssCategory(feedCategory);
					rssCategories.put(feedCategory, rssCategory);
				}
				rssCategory = rssCategories.get(feedCategory);

				// adding feed in the feed list & rsscategoories map
				rssCategory.getFeeds().add(feedName);
				this.rssData.getFeeds().add(feedName);
			}else {
				String categoryName = vars[0];

				// adding category in the category list
				this.rssData.getCategories().add(categoryName);
			}
		}else {
			// OPERATION DELETE
			// TODO
			if (type == Type.Feed) {
				String feedName = vars[0];
				String feedCategory = vars[1];

				if (this.rssData.getFeeds().contains(feedName)) {
					// removing feed from the feed list & rsscategories map
					this.rssData.getFeeds().remove(feedName);
					this.rssData.getRssCategories().get(feedCategory).getFeeds().remove(feedCategory);
				}
			}else {
				String categoryName = vars[0];

				if (this.rssData.getCategories().contains(categoryName)) {
					// removing category from the category list & from the rsscategories map
					this.rssData.getCategories().remove(categoryName);
					this.rssData.getRssCategories().remove(categoryName);
				}
			}
		}
	}

	@Override
	public void update(Notification notification) {
		Map<String, Object> params = notification.getParams();

		if (notification.getNotification().equals(Action.ACTION_ADD_FEED_SUCCESS)) {
			anf.setVisible(false);
			try {
				createFeedNode(new DefaultMutableTreeNode((AbstractNode) new FeedNode((String) params.get(Constants.VALUE_FEED), (String) params.get(Constants.VALUE_CATEGORY), new URL((String) params.get(Constants.VALUE_URL)))));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}else if (notification.getNotification().equals(Action.ACTION_ADD_FEED_FAILURE)) {
			anf.setVisible(false);
			// TODO JOptionPane
		}else if (notification.getNotification().equals(Action.ACTION_ADD_CATEGORY_SUCCESS)) {
			anf.setVisible(false);
			createCategoryNode(new DefaultMutableTreeNode((AbstractNode) new CategoryNode((String) params.get(Constants.VALUE_CATEGORY))));
		}else if (notification.getNotification().equals(Action.ACTION_ADD_CATEGORY_FAILURE)) {
			anf.setVisible(false);
			// TODO JOPTIONPANE
		}else if (notification.getNotification().equals(Action.ACTION_REMOVE_FEED_SUCCESS)) {
			rnf.setVisible(false);
			this.destroyFeedNode((String) params.get(Constants.VALUE_FEED), (String) params.get(Constants.VALUE_CATEGORY));
		}else if (notification.getNotification().equals(Action.ACTION_REMOVE_FEED_FAILURE)) {
			rnf.setVisible(false);
			// TODO REMOVE FEED FAILURE
			// TODO JOPTIONPANE
		}else if (notification.getNotification().equals(Action.ACTION_REMOVE_CATEGORY_SUCCESS)) {
			rnf.setVisible(false);
			this.destroyCategoryNode((String) params.get(Constants.VALUE_CATEGORY));
		}else if (notification.getNotification().equals(Action.ACTION_REMOVE_CATEGORY_FAILURE)) {
			rnf.setVisible(false);
			// TODO REMOVE CATEGORY FAILURE
			// TODO JOPTIONPANE
		}else if (notification.getNotification().equals(Action.ACTION_VIEW_FEED)) {
			String url = params.get(Constants.VALUE_URL).toString();
			StringBuilder sb = new StringBuilder();
			
			System.out.println("URL : " + url);

			FluxRss fr = new FluxRss(params.get(Constants.VALUE_URL).toString());
			Iterator<?> entryIter = fr.getFeed().getEntries().iterator();
			while (entryIter.hasNext()) {
				SyndEntry syndEntry = (SyndEntry) entryIter.next();

				if (syndEntry.getContents() != null) {
					Iterator<?> it = syndEntry.getContents().iterator();
					while (it.hasNext()) {
						SyndContent syndContent = (SyndContent) it.next();

						sb.append(syndContent.getValue());
					}
				}
			}
			this.jep.setText(sb.toString());
			this.jep.setCaretPosition(0);
		}
	}
}
