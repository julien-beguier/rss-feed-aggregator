package fr.julienbeguier.controller;

import java.util.Map;

import fr.julienbeguier.gui.action.Action;
import fr.julienbeguier.model.AbstractModel;
import fr.julienbeguier.observer.Notification;
import fr.julienbeguier.utils.Constants;

public class Controller extends AbstractController {

	public Controller(AbstractModel model) {
		super(model);
	}

	@Override
	public void control(Action a) {
		Map<String, Object> params = a.getParams();

		System.out.print("CONTROLLING ... ");

		if (a.getActionType().equals(Action.ACTION_LOGIN)) {
			System.out.println("LOGIN");
			if (this.model.tryLogin((String) params.get(Constants.VALUE_LOGIN), (String) params.get(Constants.VALUE_PASSWORD)) == true) {
				this.model.notifyObserver(new Notification(Action.ACTION_LOGIN_SUCCESS));
			}else {
				this.model.notifyObserver(new Notification(Action.ACTION_LOGIN_FAILURE));
			}
			return;
		}else if (a.getActionType().equals(Action.ACTION_REGISTER)) {
			System.out.println("REGISTER");
			if (this.model.tryRegister((String) params.get(Constants.VALUE_LOGIN), (String) params.get(Constants.VALUE_PASSWORD), (String) params.get(Constants.VALUE_EMAIL)) == true) {
				params.remove(Constants.VALUE_EMAIL);
				this.model.notifyObserver(new Notification(Action.ACTION_REGISTER_SUCCESS, params));
			}else {
				this.model.notifyObserver(new Notification(Action.ACTION_REGISTER_FAILURE));
			}
			return;
		}else if (a.getActionType().equals(Action.ACTION_ADD_FEED)) {
			System.out.println("ADD_FEED");
			if (this.model.tryAddFeed((String) params.get(Constants.VALUE_FEED), (String) params.get(Constants.VALUE_CATEGORY), (String) params.get(Constants.VALUE_URL)) == true) {
				this.model.notifyObserver(new Notification(Action.ACTION_ADD_FEED_SUCCESS, params));				
			}else {
				this.model.notifyObserver(new Notification(Action.ACTION_ADD_FEED_FAILURE, params));
			}
			return;
		}else if (a.getActionType().equals(Action.ACTION_ADD_CATEGORY)) {
			System.out.println("ADD_CATEGORY");
			if (this.model.tryAddCategory((String) params.get(Constants.VALUE_CATEGORY)) == true) {
				this.model.notifyObserver(new Notification(Action.ACTION_ADD_CATEGORY_SUCCESS, params));				
			}else {
				this.model.notifyObserver(new Notification(Action.ACTION_ADD_CATEGORY_FAILURE, params));
			}
			return;
		}else if (a.getActionType().equals(Action.ACTION_REMOVE_FEED)) {
			System.out.println("REMOVE_FEED");
			if (this.model.tryRemoveFeed((String) params.get(Constants.VALUE_FEED), (String) params.get(Constants.VALUE_CATEGORY)) == true) {
				this.model.notifyObserver(new Notification(Action.ACTION_REMOVE_FEED_SUCCESS, params));
			}else {
				this.model.notifyObserver(new Notification(Action.ACTION_REMOVE_FEED_FAILURE));
			}
			return;
		}else if (a.getActionType().equals(Action.ACTION_REMOVE_CATEGORY)) {
			System.out.println("REMOVE_CATEGORY");
			if (this.model.tryRemoveCategory((String) params.get(Constants.VALUE_CATEGORY)) == true) {
				this.model.notifyObserver(new Notification(Action.ACTION_REMOVE_CATEGORY_SUCCESS, params));
			}else {
				this.model.notifyObserver(new Notification(Action.ACTION_REMOVE_CATEGORY_FAILURE));
			}
			return;
		}else if (a.getActionType().equals(Action.ACTION_VIEW_FEED)) {
			System.out.println("VIEW_FEED");
			this.model.tryProcessFlux(params);
			this.model.notifyObserver(new Notification(Action.ACTION_VIEW_FEED, params));
			return;
		}
	}
}
