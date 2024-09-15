/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.TablePojo;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author AnhDoan
 */
public class TableDao {
    public ArrayList<TablePojo> layDSTable(){
        ArrayList<TablePojo>dsTableName = new ArrayList<>();
        String query = "SELECT table_name FROM all_tables WHERE owner = 'QLDATA'";
        ResultSet rs = Database.excuteQuery(query);
        try {
             while (rs.next()) {
                TablePojo tableName = new TablePojo();
                tableName.setTableName(rs.getString("table_name"));
                dsTableName.add(tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return dsTableName;
    }
}
