package com.github.redreaperlp;

import com.github.redreaperlp.Connection.DatabaseCreator;
import com.github.redreaperlp.Connection.objects.ConnectionSettings;

public class MySQL_API {
    public static boolean wasConnected = false;

    public static void main(String[] args) {
        DatabaseCreator.createDataBase(new ConnectionSettings("localhost", 3306, "root", "root", "", 10), false);
    }
}
