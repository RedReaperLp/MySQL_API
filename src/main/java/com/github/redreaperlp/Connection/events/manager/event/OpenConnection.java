package com.github.redreaperlp.Connection.events.manager.event;

import com.github.redreaperlp.Connection.events.manager.objects.Event;
import com.github.redreaperlp.Connection.objects.ConnectionSettings;

public class OpenConnection extends Event {
    ConnectionSettings settings;
    boolean cancelled = false;

    public OpenConnection(boolean cancelled, ConnectionSettings settings) {
        super(OpenConnection.class);
        this.cancelled = cancelled;
        this.settings = settings;
    }

    /**
     * @return If Connection was Cancelled by a Listener
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * This Method can be used to cancel the Connection
     *
     * @param cancelled
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * @return The Host like localhost or an IP
     */
    public String getHost() {
        return settings.getHost();
    }

    /**
     * Set the Host
     *
     * @param host
     */
    public void setHost(String host) {
        settings.setHost(host);
    }

    /**
     * @return The Port, default is 3306
     */
    public int getPort() {
        return settings.getPort();
    }

    /**
     * Set the Port
     *
     * @param port
     */
    public void setPort(int port) {
        settings.setPort(port);
    }

    /**
     * @return Name of the Database
     */
    public String getDatabase() {
        return settings.getDatabase();
    }

    /**
     * Set the Name of the Database
     *
     * @param database
     */
    public void setDatabase(String database) {
        this.settings.setDatabase(database);
    }

    /**
     * @return The User of the Database
     */
    public String getUser() {
        return settings.getUser();
    }

    /**
     * Set the User of the Database
     *
     * @param user
     */
    public void setUser(String user) {
        this.settings.setUser(user);
    }

    /**
     * @return The Password of the Database
     */
    public String getPassword() {
        return settings.getPassword();
    }

    /**
     * Set the Password of the Database
     *
     * @param password
     */
    public void setPassword(String password) {
        this.settings.setPassword(password);
    }

    /**
     * @return The ConnectionSettings
     */
    public ConnectionSettings getSettings() {
        return settings;
    }

    /**
     * Set the ConnectionSettings
     *
     * @param settings
     */

    public void setSettings(ConnectionSettings settings) {
        this.settings = settings;
    }
}
