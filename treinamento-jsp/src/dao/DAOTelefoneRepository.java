package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelTelefone;

public class DAOTelefoneRepository {
	
	private Connection connection; 
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	public DAOTelefoneRepository() {
		connection = SingleConnectionBanco.getConnection();
		
	}
	
	
	//Método para carregar a lista de Telefones do usuáro: (001)
	public List<ModelTelefone> listFoneUser(Long idUserPai) throws Exception {
		
		List<ModelTelefone> retornaFoneUser = new ArrayList<ModelTelefone>();
		
		String sql = "select * from telefone where usuario_pai_id = ? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, idUserPai);
		
		ResultSet retornaFone = preparedStatement.executeQuery();
		
		while (retornaFone.next()) {
			
			ModelTelefone modelTelefone = new ModelTelefone();
			
			modelTelefone.setId(retornaFone.getLong("id"));
			modelTelefone.setNumero(retornaFone.getString("numero"));
			modelTelefone.setUsuario_cad_id(daoUsuarioRepository.consultarUsuarioID(retornaFone.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultarUsuarioID(retornaFone.getLong("usuario_pai_id")));
			
			retornaFoneUser.add(modelTelefone);
			
			}
		
			return retornaFoneUser;
		
		}
	
	
	//Método para gravar um número de telefone no banco de dados
	public void gravarTelefone(ModelTelefone modelTelefone) throws Exception {
		
		String sql = "Insert into telefone (numero, usuario_pai_id, usuario_cad_id) values (?, ?, ?)";		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setString(1, modelTelefone.getNumero());
		preparedStatement.setLong(2, modelTelefone.getUsuario_pai_id().getId());
		preparedStatement.setLong(3, modelTelefone.getUsuario_cad_id().getId());
		
		preparedStatement.execute();
		connection.commit();
		
	} 
	
	//Método para deletar um número de telefone gravado no banco de dados
	public void deletarTelefone(Long id) throws Exception {
		
		String sql = "delete from telefone where id = ? ";
		PreparedStatement apagarTelefone = connection.prepareStatement(sql);
		
		apagarTelefone.setLong(1, id);
		
		apagarTelefone.executeUpdate();
		connection.commit();		
		
	}
	
	//Método para não deixar gravar um telefone duplicado para o mesmo usuário:
	public boolean existeFone(String fone, Long idUser) throws Exception {
		
		//Se existir telefone para esse usuário vai retornar maior que 0 retorna o número 1,2,3...
		String sql = "select count(1) > 0 as existeFoneDuplicado from telefone where usuario_pai_id =? and numero =? ";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, idUser);
		preparedStatement.setString(2, fone);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		resultSet.next();
		
		return resultSet.getBoolean("existeFoneDuplicado");
		
	}

}
