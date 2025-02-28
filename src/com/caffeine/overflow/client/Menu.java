package com.caffeine.overflow.client;

import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This class consists of a constructor and some methods to allow the user to
 * have a menu with the action he can perform
 *
 * @author Giacomo Minello
 * @author Matteo Tramontano
 * @author Davide Menetto
 * @version 1.0
 */
public class Menu {

	private VerticalPanel verticalPanel = null;
	private int type = -1;

	/**
	 * Constructor for class Menu
	 * 
	 * @param verticalPanel
	 * @param type
	 * @see VerticalPanel
	 * @since 1.0
	 */
	public Menu(VerticalPanel verticalPanel, int type) {
		this.verticalPanel = verticalPanel;
		this.type = type;
	}

	/**
	 * Getter method for the parameter Type
	 * 
	 * @return the Type of user
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * This is the entry point method.
	 * 
	 * @see VerticalPanel
	 * @see RootPanel
	 * @see Comando
	 * @see MenuBar
	 * @since 1.0
	 */
	public void onModuleLoad() {
		final Comando cmd = new Comando(this.verticalPanel);
		final MenuBar menu = new MenuBar();
		if (this.type == 0) {
			final MenuBar menu1 = new MenuBar(true);
			menu1.addItem("LogIn", cmd.comando("0"));
			menu1.addItem("SignUp", cmd.comando("1"));
			menu1.addItem("Visualizza domande", cmd.comando("4"));
			menu.addItem("Menu", menu1);
		}
		if ((this.type == 1) || (this.type == 2)) {
			final MenuBar menu1 = new MenuBar(true);
			menu1.addItem("Profilo", cmd.comando("10"));
			menu1.addItem("Logout", cmd.comando("4"));
			if (this.type == 1) {
				menu.addItem("GIUDICE", menu1);
			} else {
				menu.addItem("ADMIN", menu1);
			}
			menu.addItem("Inserisci domanda", cmd.comando("2"));
			menu.addItem("Inserisci risposta", cmd.comando("7"));
			menu.addItem("Visualizza domande", cmd.comando("3"));
			menu.addItem("Elimina risposte", cmd.comando("9"));
			if (this.type == 2) {
				menu.addItem("Elimina domande", cmd.comando("8"));
				menu.addItem("Categorie", cmd.comando("6"));
				menu.addItem("Nomina giudici", cmd.comando("5"));
			}
		}
		if (this.type == 3) {
			final MenuBar menu1 = new MenuBar(true);
			menu1.addItem("Profilo", cmd.comando("10"));
			menu1.addItem("LogOut", cmd.comando("4"));
			menu.addItem("Menu", menu1);
			menu.addItem("Inserisci domanda", cmd.comando("2"));
			menu.addItem("Inserisci risposta", cmd.comando("7"));
			menu.addItem("Visualizza domande", cmd.comando("3"));
		}
		RootPanel.get().add(menu, 0, 0);
	}

	/**
	 * setter method for the parameter type
	 * 
	 * @param type, the type of user
	 */
	public void setType(int type) {
		this.type = type;
	}
}