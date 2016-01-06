package codecool.study.db;

import codecool.study.db.database.DatabaseConnection;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println(DatabaseConnection.getDbConnection().getClientInfo().toString());

    }
}
