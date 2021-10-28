package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOLoginRepository {

	// Primeiro - Criar um objeto de conex�o
	private Connection connection;

	// Segundo - Criar um construtor
	public DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();

	}

	// Terceiro - Fazer um m�todo para validar nosso login,
	// Nesse parametro � passado o objeto inteiro ModelLogin
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		
		String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?) ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		ResultSet resultSet = statement.executeQuery();
		
		//Usu�rio autenticado
		if (resultSet.next()) {
			return true;
			
		}
		//Usu�rio n�o autenticado
		return false;

	}
	
	//Depois do c�digo acima escrito � necess�rio ir na ServletLogin, e
	//fazer a declara��o do DAOLoginRepository dentro da ServletLogin

}
