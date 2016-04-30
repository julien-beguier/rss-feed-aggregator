package fr.julienbeguier.controller;

import fr.julienbeguier.gui.action.Action;
import fr.julienbeguier.model.AbstractModel;


public abstract class AbstractController {

	protected AbstractModel	model;

	public AbstractController(AbstractModel model) {
		this.model = model;
	}

	abstract void control(Action a);
}
