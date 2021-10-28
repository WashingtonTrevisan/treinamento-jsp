package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

	private static String banco = "jdbc:postgresql://localhost:5432/treinamento-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String password = "admin";
	private static Connection connection = null;

	// Quarto: Criar um método para retornar a conexão existente
	public static Connection getConnection() {
		return connection;
	}

	// Terceiro: Esse static chama o método conectar de qualquer parte do Sistema
	static {
		conectar();
	}

	// Segundo: Criar um construtor.
	public SingleConnectionBanco() {
		conectar();
	}

	// Primeiro: é criado o método conectar, para fazer a conexão com o banco.
	private static void conectar() {

		try {

			if (connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, password);
				connection.setAutoCommit(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			//Mostra qualquer erro no momento de conectasr com o banco 
		}
		
		// Depois de todos os passos feitos acima é necessário fazer a declaração no FilterAutenticação
	}
}
