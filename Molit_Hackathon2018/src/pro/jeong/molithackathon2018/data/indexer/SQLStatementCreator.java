package pro.jeong.molithackathon2018.data.indexer;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class SQLStatementCreator {
    //F:\\PUBLIC\\Project\\Molit_Hackathon2018\\Molit_Hackathon2018\\Index
    public static String createTupleTableForABus(String tableName, String column_1_Name, String column_1_Type, String column_2_Name, String column_2_Type) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS " + tableName + " (\n");
        builder.append(column_1_Name + " " + column_1_Type + " PRIMARY KEY,\n");
        builder.append(column_2_Name + " " + column_2_Type + " NOT NULL\n");
        builder.append(");");

        return builder.toString();
    }
}
