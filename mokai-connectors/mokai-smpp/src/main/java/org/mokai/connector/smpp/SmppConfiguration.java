package org.mokai.connector.smpp;

import org.mokai.ui.annotation.Label;
import org.mokai.ui.annotation.Required;

/**
 * Holds the information used to configure a {@link SyncSmppConnector} instance.
 * 
 * @author German Escobar
 */
public class SmppConfiguration {
	
	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 5020;
	private static final int DEFAULT_ENQUIRELINK_TIMER = 9000;
	private static final long DEFAULT_INITIAL_RECONNECT_DELAY = 5000;
	private static final long DEFAULT_RECONNECT_DELAY = 5000;
	private static final int DEFAULT_DATA_CODING = 0;
	
	/**
	 * Tells whether the connection will be in transmitter, receiver or 
	 * transciever mode.
	 * 
	 * @author German Escobar
	 */
	public enum BindType {
		
		TRANSMITTER, TRANSCIEVER, RECEIVER;
		
		public static BindType convert(String strValue) {
			if (strValue == null) {
				throw new IllegalArgumentException("value not provided");
			}
			
			if (strValue.equals("t")) {
				return TRANSMITTER;
			} else if (strValue.equals("r")) {
				return RECEIVER;
			} else if (strValue.equals("tr")) {
				return TRANSCIEVER;
			}
			
			throw new IllegalArgumentException("value not supported");
		}
	}

	@Required
	@Label("Host")
	private String host = DEFAULT_HOST;
	
	@Required
	@Label("Port")
	private int port = DEFAULT_PORT;
	
	@Required
	@Label("System ID")
	private String systemId;
	
	@Required
	@Label("Password")
	private String password;
	
	@Required
	@Label("System Type")
	private String systemType;
	
	@Label("Enquire Link Timer")
	private int enquireLinkTimer = DEFAULT_ENQUIRELINK_TIMER;
	
	@Label("Bind Type")
	private BindType bindType = BindType.TRANSCIEVER;
	
	@Label("Bind NPI")
	private String bindNPI;
	
	@Label("Bind TON")
	private String bindTON;
	
	@Label("Source NPI")
	private String sourceNPI;
	
	@Label("Source TON")
	private String sourceTON;
	
	@Label("Destination NPI")
	private String destNPI;
	
	@Label("Destination TON")
	private String destTON;
	
	/**
	 * Sets the flag to request the delivery receipts from the SMSC.
	 */
	@Label("Request DLR")
	private boolean requestDeliveryReceipts = true;
	
	@Label("Initial Reconnect Delay")
	private long initialReconnectDelay = DEFAULT_INITIAL_RECONNECT_DELAY;
	
	@Label("Reconnect Delay")
    private long reconnectDelay = DEFAULT_RECONNECT_DELAY;
	
	@Label("Data Coding")
	private int dataCoding = DEFAULT_DATA_CODING;
	
	/**
	 * If true, it will generate
	 */
	@Label("Route Delivery Receipts")
	private boolean routeDeliveryReceipts = false;
	
	public final String getHost() {
		return host;
	}

	public final void setHost(String host) {
		this.host = host;
	}

	public final int getPort() {
		return port;
	}

	public final void setPort(int port) {
		this.port = port;
	}

	public final String getSystemId() {
		return systemId;
	}

	public final void setSystemId(String user) {
		this.systemId = user;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final String getSystemType() {
		return systemType;
	}

	public final void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public final int getEnquireLinkTimer() {
		return enquireLinkTimer;
	}

	public final void setEnquireLinkTimer(int enquireLinkTimer) {
		this.enquireLinkTimer = enquireLinkTimer;
	}

	public final BindType getBindType() {
		return bindType;
	}

	public final void setBindType(BindType bindType) {
		this.bindType = bindType;
	}

	public final String getBindNPI() {
		return bindNPI;
	}

	public final void setBindNPI(String bindNPI) {
		this.bindNPI = bindNPI;
	}

	public final String getBindTON() {
		return bindTON;
	}

	public final void setBindTON(String bindTON) {
		this.bindTON = bindTON;
	}

	public final String getSourceNPI() {
		return sourceNPI;
	}

	public final void setSourceNPI(String sourceNPI) {
		this.sourceNPI = sourceNPI;
	}

	public final String getSourceTON() {
		return sourceTON;
	}

	public final void setSourceTON(String sourceTON) {
		this.sourceTON = sourceTON;
	}

	public final String getDestNPI() {
		return destNPI;
	}

	public final void setDestNPI(String destNPI) {
		this.destNPI = destNPI;
	}

	public final String getDestTON() {
		return destTON;
	}

	public final void setDestTON(String destTON) {
		this.destTON = destTON;
	}

	public boolean isRequestDeliveryReceipts() {
		return requestDeliveryReceipts;
	}

	public void setRequestDeliveryReceipts(boolean requestDeliveryReceipts) {
		this.requestDeliveryReceipts = requestDeliveryReceipts;
	}

	public final long getInitialReconnectDelay() {
		return initialReconnectDelay;
	}

	public final void setInitialReconnectDelay(long initialReconnectDelay) {
		this.initialReconnectDelay = initialReconnectDelay;
	}

	public final long getReconnectDelay() {
		return reconnectDelay;
	}

	public final void setReconnectDelay(long reconnectDelay) {
		this.reconnectDelay = reconnectDelay;
	}

	public int getDataCoding() {
		return dataCoding;
	}

	public void setDataCoding(int dataCoding) {
		this.dataCoding = dataCoding;
	}

	public boolean isRouteDeliveryReceipts() {
		return routeDeliveryReceipts;
	}

	public void setRouteDeliveryReceipts(boolean routeDeliveryReceipts) {
		this.routeDeliveryReceipts = routeDeliveryReceipts;
	}
	
}
