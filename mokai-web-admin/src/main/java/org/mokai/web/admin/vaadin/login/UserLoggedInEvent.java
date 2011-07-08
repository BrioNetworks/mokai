package org.mokai.web.admin.vaadin.login;

import com.github.peholmst.mvp4vaadin.View;
import com.github.peholmst.mvp4vaadin.ViewEvent;

public class UserLoggedInEvent extends ViewEvent {

	private static final long serialVersionUID = -1L;
	
	private final String username;

	public UserLoggedInEvent(View source, String username) {
		super(source);
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

}
