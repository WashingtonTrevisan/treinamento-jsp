package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

	//Essa conexão comentada é para acessar o servidor TomCat servidor local instalado no notebook
	//private static String banco = "jdbc:postgresql://localhost:5432/treinamento-jsp?autoReconnect=true";
	//private static String user = "postgres";
	//private static String password = "admin";
	//private static Connection connection = null;
	
	//Essa configuração é feita para colocar a aplicação online usando o servidor Heroku (Primeira Tentativa)
	//private static String banco = "jdbc:postgresql://ec2-52-204-213-254.compute-1.amazonaws.com:5432/ddfvlb5sl1ds35?sslmode=require&autoReconnect=true";
	//private static String user = "ytpwwwxbrhclrj";
	//private static String password = "9756dbafe8ded14d27818a84c26d6dda356ccd1d35bd02431df087a2e9a1b5c9";
	//private static Connection connection = null;
	
	
	private static String banco = "jdbc:postgresql://ec2-3-92-119-83.compute-1.amazonaws.com:5432/d17qof75kt2nvg?sslmode=require&autoReconnect=true";
	private static String user = "lpsvvweiwaqwwu";
	private static String password = "71421982ed1216321fd87c0aea704261242f55ddd838624d7531589fb0258b3f";
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
