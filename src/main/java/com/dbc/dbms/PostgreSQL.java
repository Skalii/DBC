package com.dbc.dbms;

import com.dbc.Connector;

public class PostgreSQL extends Connector{

    public PostgreSQL(String url, String user, String password) {
        super("org.postgresql.Driver", "postgresql", url, user, password);
    }
}
