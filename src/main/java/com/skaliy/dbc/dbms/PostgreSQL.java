package com.skaliy.dbc.dbms;

import com.skaliy.dbc.Connector;

public class PostgreSQL extends Connector{

    public PostgreSQL(String url, String user, String password) {
        super("org.postgresql.Driver", "postgresql", url, user, password);
    }
}
