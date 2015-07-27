package br.com.setoronline.JFinance.v1_0_0.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String SERVER = "192.168.56.199";
	private static final String PORT = "3306";
	private static final String DATABASE = "JFINANCE";
	
	private static final String CONN = "jdbc:mysql://"+SERVER+":"+PORT+"/"+DATABASE;
	private static final String USER = "root";
	private static final String PASS = "root";
	
	public Connection getConn() throws Exception{
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(CONN, USER, PASS);
			System.out.println("(ConnectionManager): Obtendo Conexão");
			return conn;
		} catch (ClassNotFoundException e) {
			String errorMsg = "Erro: Drive jdbc não encontrado ("+DRIVER+")";
			throw new Exception(errorMsg, e);
		} catch (SQLException e) {
			String errorMsg = "Erro ao obter conexão ("+CONN+")";
			throw new Exception(errorMsg, e);
		}
	} //end try-catch
	
} // end class ConectaBanco
