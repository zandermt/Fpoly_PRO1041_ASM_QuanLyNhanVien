/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UTILS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class JDBCHelper {
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String dbUrl = "jdbc:sqlserver://13.71.106.166:1433;databaseName=SD19301_Team5;encrypt=true;trustServerCertificate=true;";
    static String admin ="usr01_team5";
    static String pass ="qlnhanvien";
    static {
        try{
            Class.forName(driver);
            Connection a = DriverManager.getConnection(dbUrl,admin,pass);
            System.out.print("success");
        }
        catch (Exception e){
            System.out.print("fail");
            e.printStackTrace();
            
        }
    }
    public static PreparedStatement getStmt (String sql, Object... args) throws SQLException{
        Connection conn = DriverManager.getConnection(dbUrl, admin, pass);
        PreparedStatement stmt;
        if(sql.trim().startsWith("{")){
            stmt=conn.prepareCall(sql);
        }
        else {
            stmt=conn.prepareStatement(sql);
        }
        for(int i=0;i<args.length;i++){
            stmt.setObject(i+1,args[i]);
        }
        return stmt;
    }
    public static int update(String sql, Object... args) throws SQLException{
        try{
            PreparedStatement stmt = JDBCHelper.getStmt(sql, args);
            try{
                return stmt.executeUpdate();
            }
            finally{
                stmt.getConnection().close();
            }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static ResultSet query(String sql, Object... args) throws SQLException{
        PreparedStatement stmt=JDBCHelper.getStmt(sql, args);
        return stmt.executeQuery();
    }
    public static Object value(String sql, Object... args){
        try{
            ResultSet rs = JDBCHelper.query(sql, args);
            if(rs.next()){
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
            return null;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
