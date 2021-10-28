package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {

	// Primeiro - Criar um objeto de conexão
	private Connection connection;

	// Segundo - Criar um construtor
	public DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();

	}

	// Terceiro - Fazer um método para validar nosso login,
	// Nesse parametro é passado o objeto inteiro ModelLogin
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		
		String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?) ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		ResultSet resultSet = statement.executeQuery();
		
		//Usuário autenticado
		if (resultSet.next()) {
			return true;
			
		}
		//Usuário não autenticado
		return false;

	}
	
	//Depois do código acima escrito é necessário ir na ServletLogin, e
	//fazer a declaração do DAOLoginRepository dentro da ServletLogin

}
