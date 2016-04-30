package fr.julienbeguier.observer;

public interface Observable {

	public void addObserver(Observer obs);
	public void removeObservers();
	public void notifyObserver(Notification notification);
}
