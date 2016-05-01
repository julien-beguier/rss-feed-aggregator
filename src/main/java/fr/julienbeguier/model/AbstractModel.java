package fr.julienbeguier.model;

import java.util.ArrayList;
import java.util.Map;

import fr.julienbeguier.observer.Notification;
import fr.julienbeguier.observer.Observable;
import fr.julienbeguier.observer.Observer;

public abstract class AbstractModel implements Observable {

	protected ArrayList<Observer> listObserver = new ArrayList<Observer>();

	public void addObserver(Observer obs) {
		this.listObserver.add(obs);
	}

	public void removeObservers() {
		this.listObserver = new ArrayList<Observer>();
	}

	public abstract void notifyObserver(Notification notification);
	
	public abstract boolean tryLogin(String login, String password);
	public abstract boolean tryLogout();
	public abstract boolean tryRegister(String login, String password, String email);
	public abstract boolean tryAddFeed(String feedName, String feedCategory, String feedUrl);
	public abstract boolean tryAddCategory(String categoryName);
	public abstract boolean tryRemoveFeed(String feedName, String feedCategory);
	public abstract boolean tryRemoveCategory(String categoryName);
	public abstract boolean tryProcessFlux(Map<String, Object> params);
}
