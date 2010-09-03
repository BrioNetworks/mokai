package org.mokai.web.admin.vaadin;

import org.mokai.RoutingEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.henrik.refresher.Refresher;

import com.vaadin.Application;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class WebAdminApplication extends Application {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RoutingEngine routingEngine;

	@Override
	public void init() {
		Window mainWindow = new Window();
		
		mainWindow.addComponent(new Label("<h2>Processors</h2>", Label.CONTENT_XHTML));
		
		final ProcessorsTable processorsTable = new ProcessorsTable(routingEngine);
		processorsTable.setPageLength(0);
		processorsTable.setHeight(null);
		processorsTable.loadData();
		
		mainWindow.addComponent(processorsTable);
		
		mainWindow.addComponent(new Label("<h2>Receivers</h2>", Label.CONTENT_XHTML));
		
		final ReceiversTable receiversTable = new ReceiversTable(routingEngine);
		receiversTable.setPageLength(0);
		receiversTable.setHeight(null);
		receiversTable.loadData();
		
		mainWindow.addComponent(receiversTable);
		
		Refresher refresher = new Refresher();
		refresher.setRefreshInterval(5000);
		refresher.addListener(new Refresher.RefreshListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void refresh(Refresher source) {
				processorsTable.loadData();
				receiversTable.loadData();
			}
			
		});
		
		mainWindow.addComponent(refresher);
		
		setTheme("mokai");
		
		setMainWindow(mainWindow);
	}
	

}