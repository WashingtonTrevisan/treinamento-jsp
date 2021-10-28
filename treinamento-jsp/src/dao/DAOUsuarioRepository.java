package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beandto.BeanDtoGraficoSalarioUser;
import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

public class DAOUsuarioRepository {
	
	//Segundo passo criar o connection
	private Connection connection;
	
	//primeira passo a ser feito é criar um contrutor
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();		
	
	}
	
	
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario(Long userLogado, String dataInicial, String dataFinal) throws Exception {
		
		String sql = "select avg (rendamensal) as media_salarial, perfil from model_login where usuario_id = ? and datanascimento >= ? and datanascimento <= ? group by perfil";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		preparedStatement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		preparedStatement.setDate(3, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while (resultSet.next()) {
			
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");	
			
			perfils.add(perfil);
			salarios.add(media_salarial);
			
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
		
	}
	
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario (Long userLogado) throws Exception {
		
		String sql = "select avg (rendamensal) as media_salarial, perfil from model_login where usuario_id = ? group by perfil";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while (resultSet.next()) {
			
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");	
			
			perfils.add(perfil);
			salarios.add(media_salarial);
			
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
		
	}
	
	
	public ModelLogin gravarUsuario(ModelLogin gravarObjeto, Long userLogado) throws Exception {	
		
		//Grava um novo objeto no banco
		if (gravarObjeto.isNovo()) {
			
			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero, datanascimento, rendamensal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement preparaSql = connection.prepareStatement(sql);
			
			preparaSql.setString(1, gravarObjeto.getLogin());
			preparaSql.setString(2, gravarObjeto.getSenha());
			preparaSql.setString(3, gravarObjeto.getNome());
			preparaSql.setString(4, gravarObjeto.getEmail());
			preparaSql.setLong(5, userLogado);		
			preparaSql.setString(6, gravarObjeto.getPerfil());
			preparaSql.setString(7, gravarObjeto.getSexo());			
			
			preparaSql.setString(8, gravarObjeto.getCep());
			preparaSql.setString(9, gravarObjeto.getLogradouro());
			preparaSql.setString(10, gravarObjeto.getBairro());
			preparaSql.setString(11, gravarObjeto.getLocalidade());
			preparaSql.setString(12, gravarObjeto.getUf());
			preparaSql.setString(13, gravarObjeto.getNumero());
			preparaSql.setDate(14, gravarObjeto.getDataNascimento());
			preparaSql.setDouble(15, gravarObjeto.getRendaMensal());
			
			preparaSql.execute();
			connection.commit();
			
			//Método para gravar a imagem do usuário cadastrado no banco de dados:
			if (gravarObjeto.getFotouser() != null && !gravarObjeto.getFotouser().isEmpty()) {
				sql = "update model_login set fotouser=?, extensaofotouser=? where login =?";
				
				preparaSql = connection.prepareStatement(sql);
				
				preparaSql.setString(1, gravarObjeto.getFotouser());
				preparaSql.setString(2, gravarObjeto.getExtensaofotouser());
				preparaSql.setString(3, gravarObjeto.getLogin());
				
				preparaSql.execute();
				connection.commit();
				
			}
			
		} else {
			
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=?, datanascimento=?, rendamensal=? WHERE id = " + gravarObjeto.getId() + ";"; 
			PreparedStatement preparaUpdateSql = connection.prepareStatement(sql);		
			
			preparaUpdateSql.setString(1, gravarObjeto.getLogin());
			preparaUpdateSql.setString(2, gravarObjeto.getSenha());
			preparaUpdateSql.setString(3, gravarObjeto.getNome());
			preparaUpdateSql.setString(4, gravarObjeto.getEmail());
			preparaUpdateSql.setString(5, gravarObjeto.getPerfil());
			preparaUpdateSql.setString(6, gravarObjeto.getSexo());
			
			preparaUpdateSql.setString(7, gravarObjeto.getCep());
			preparaUpdateSql.setString(8, gravarObjeto.getLogradouro());
			preparaUpdateSql.setString(9, gravarObjeto.getBairro());
			preparaUpdateSql.setString(10, gravarObjeto.getLocalidade());
			preparaUpdateSql.setString(11, gravarObjeto.getUf());
			preparaUpdateSql.setString(12, gravarObjeto.getNumero());
			preparaUpdateSql.setDate(13, gravarObjeto.getDataNascimento());
			preparaUpdateSql.setDouble(14, gravarObjeto.getRendaMensal());
			
			preparaUpdateSql.executeUpdate();
			connection.commit();
			
			
			//Método para fazer o atualização de uma imagem no banco de dados
			if (gravarObjeto.getFotouser() != null && !gravarObjeto.getFotouser().isEmpty()) {
				sql = "update model_login set fotouser =?, extensaofotouser =? where id =? ";
				
				preparaUpdateSql = connection.prepareStatement(sql);
				
				preparaUpdateSql.setString(1, gravarObjeto.getFotouser());
				preparaUpdateSql.setString(2, gravarObjeto.getExtensaofotouser());
				preparaUpdateSql.setLong(3, gravarObjeto.getId());
				
				preparaUpdateSql.execute();
				connection.commit();
								
			}
		}
			
			return this.consultarUsuario(gravarObjeto.getLogin(), userLogado);			
			//Terminado esse bloco, é necessário ir até a ServletUsuarioController é obrigatório
			//iniciar um DAOusuarioRepository na declaração
		
	}  
	
	//Método para retornar uma lista de usuários cadastrados em forma páginada:
	//Será mostrado apenas 5 usuários cadastrados de cada vez, comando usado (offset limit 5)
	public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws Exception {
		
		List<ModelLogin> retornaNomeUser = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + " order by nome offset " + offset + " limit 5 ";		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();  
		
		//Varre as linhas de resultado do SQL enquanto existir resultados, vai "separando"  
		while (resultado.next()) {
			
			ModelLogin modelLogin =  new ModelLogin();
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retornaNomeUser.add(modelLogin);
			
		}
		
		return retornaNomeUser;
		
	}
	
	//Método para montar o SQL da paginação (de 5 em 5 registros) que será exibido ao usuário.
	//Na ServletUsuarioController existe um método escrito para fazer a paginação.
	public int totalPagina(Long userLogado) throws Exception {
		
		String sql = "select count(1) as total from model_login where usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);	
		
		ResultSet resultado = statement.executeQuery(); 	
		
		resultado.next();
				
		Double cadastros = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double restodivisao = pagina % 2;
		
		if (restodivisao > 0) {
			pagina ++;			
			
		}
		
		return pagina.intValue();
	}
	
	
	//Método que retorna uma lista ("Relatório") de todos os usuários cadastrados no Sistema
		public List<ModelLogin> consultaUsuarioListRelatorio(Long userLogado) throws Exception {
				
				List<ModelLogin> retornaNomeUser = new ArrayList<ModelLogin>();
				
				String sql = "select * from model_login where useradmin = false and usuario_id = " + userLogado ;		
				PreparedStatement statement = connection.prepareStatement(sql);
				
				ResultSet resultado = statement.executeQuery();  
				
				//Varre as linhas de resultado do SQL enquanto existir resultados, vai "separando"  
				while (resultado.next()) {
					
					ModelLogin modelLogin =  new ModelLogin();
					
					modelLogin.setId(resultado.getLong("id"));
					modelLogin.setNome(resultado.getString("nome"));
					modelLogin.setEmail(resultado.getString("email"));
					modelLogin.setLogin(resultado.getString("login"));
					//modelLogin.setSenha(resultado.getString("senha"));
					modelLogin.setPerfil(resultado.getString("perfil"));
					modelLogin.setSexo(resultado.getString("sexo"));
					modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
					
					modelLogin.setTelefones(this.listFoneUser(modelLogin.getId()));
					
					retornaNomeUser.add(modelLogin);
					
				}
				
				return retornaNomeUser;
				
			}
		
		//Método que retorna uma lista ("Relatório") de todos os usuários cadastrados no Sistema
		public List<ModelLogin> consultaUsuarioListRelatorio(Long userLogado, String dataInicial, String dataFinal) throws Exception {
						
				List<ModelLogin> retornaNomeUser = new ArrayList<ModelLogin>();
						
				String sql = "select * from model_login where useradmin = false and usuario_id = " + userLogado + " and datanascimento >= ? and datanascimento <= ? " ;		
				PreparedStatement statement = connection.prepareStatement(sql);
				//Data Inicial e data Final tem que setar no PreparedStatement (abaixo)
				statement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
				statement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
						
				ResultSet resultado = statement.executeQuery();  
						
				//Varre as linhas de resultado do SQL enquanto existir resultados, vai "separando"  
				while (resultado.next()) {
							
					ModelLogin modelLogin =  new ModelLogin();
							
					modelLogin.setId(resultado.getLong("id"));
					modelLogin.setNome(resultado.getString("nome"));
					modelLogin.setEmail(resultado.getString("email"));
					modelLogin.setLogin(resultado.getString("login"));
					//modelLogin.setSenha(resultado.getString("senha"));
					modelLogin.setPerfil(resultado.getString("perfil"));
					modelLogin.setSexo(resultado.getString("sexo"));
					modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
						
					modelLogin.setTelefones(this.listFoneUser(modelLogin.getId()));
						
					retornaNomeUser.add(modelLogin);
							
				}
						
				return retornaNomeUser;
						
			}
	
	
	//Método que retorna uma lista de usuários cadastrados no Sistema
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {
			
			List<ModelLogin> retornaNomeUser = new ArrayList<ModelLogin>();
			
			String sql = "select * from model_login where useradmin = false and usuario_id = " + userLogado + " limit 5";		
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet resultado = statement.executeQuery();  
			
			//Varre as linhas de resultado do SQL enquanto existir resultados, vai "separando"  
			while (resultado.next()) {
				
				ModelLogin modelLogin =  new ModelLogin();
				
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setNome(resultado.getString("nome"));
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setLogin(resultado.getString("login"));
				//modelLogin.setSenha(resultado.getString("senha"));
				modelLogin.setPerfil(resultado.getString("perfil"));
				modelLogin.setSexo(resultado.getString("sexo"));
				
				retornaNomeUser.add(modelLogin);
				
			}
			
			return retornaNomeUser;
			
		}
	
	//Método para listar os usuários utilizando Ajax tela menor do Sistema
	public int consultaUsuarioListTotalPaginacaoAjax(String nome, Long userLogado) throws Exception {
		
		String sql = "select count(1) as total from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? ";		
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, + userLogado);
		
		ResultSet resultado = statement.executeQuery();  
		
		//Varre as linhas de resultado do SQL enquanto existir resultados, vai "separando"  
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double restodivisao = pagina % 2;
		
		if (restodivisao > 0) {
			pagina ++;			
			
		}
		
		return pagina.intValue();

	}
	
	
	//Método para listar os usuários utilizando Ajax tela menor do Sistema utilizando o OffSet
	public List<ModelLogin> consultaUsuarioListOffSet(String nome, Long userLogado, int offset) throws Exception {
		
		List<ModelLogin> retornaNomeUser = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+ offset +" limit 5";		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, + userLogado);
		
		ResultSet resultado = statement.executeQuery();  
		
		//Varre as linhas de resultado do SQL enquanto existir resultados, vai "separando"  
		while (resultado.next()) {
			
			ModelLogin modelLogin =  new ModelLogin();
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retornaNomeUser.add(modelLogin);
			
		}
		
		return retornaNomeUser;
		
	}
	
	
	
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception {
		
		List<ModelLogin> retornaNomeUser = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, + userLogado);
		
		ResultSet resultado = statement.executeQuery();  
		
		//Varre as linhas de resultado do SQL enquanto existir resultados, vai "separando"  
		while (resultado.next()) {
			
			ModelLogin modelLogin =  new ModelLogin();
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retornaNomeUser.add(modelLogin);
			
		}
		
		return retornaNomeUser;
		
	}
	
		public ModelLogin consultarUsuarioLogado(String login) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+ login +"') ";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		//Se tem resultado
		while (resultado.next()) {
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));	
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));
			
		}
		
		return modelLogin;			
	}
	
	public ModelLogin consultarUsuario(String login) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+ login +"') and useradmin is false ";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		//Se tem resultado
		if (resultado.next()) {
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));	
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));
			
		}
		
		return modelLogin;			
	}
	
	public ModelLogin consultarUsuario(String login, Long userlogado) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+ login +"') and useradmin is false and usuario_id = " + userlogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		//Se tem resultado
		if (resultado.next()) {
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));
			
			
		}
		
		return modelLogin;			
	}
	
	
		//Método para carregar o usuário no DAOTelefoneRepository: (001)
		public ModelLogin consultarUsuarioID(Long id) throws Exception {
				
				ModelLogin modelLogin = new ModelLogin();
				
				String sql = "select * from model_login where id = ? and useradmin is false";
				
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setLong(1, id);
				
				ResultSet resultado = statement.executeQuery();
				
				//Se tem resultado
				while (resultado.next()) {
					
					modelLogin.setId(resultado.getLong("id"));
					modelLogin.setNome(resultado.getString("nome"));
					modelLogin.setEmail(resultado.getString("email"));
					modelLogin.setLogin(resultado.getString("login"));
					modelLogin.setSenha(resultado.getString("senha"));
					modelLogin.setPerfil(resultado.getString("perfil"));
					modelLogin.setSexo(resultado.getString("sexo"));
					modelLogin.setFotouser(resultado.getString("fotouser"));
					modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));					
					modelLogin.setCep(resultado.getString("cep"));
					modelLogin.setLogradouro(resultado.getString("logradouro"));
					modelLogin.setBairro(resultado.getString("bairro"));
					modelLogin.setLocalidade(resultado.getString("localidade"));
					modelLogin.setUf(resultado.getString("uf"));
					modelLogin.setNumero(resultado.getString("numero"));
					modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
					modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));
					
				}
				
				return modelLogin;			
			}

		
	//Consultar usuário gravado no banco de dados para edição.
	public ModelLogin consultarUsuarioID(String id, Long userLogado) throws Exception {
			
			ModelLogin modelLogin = new ModelLogin();
			
			String sql = "select * from model_login where id = ? and useradmin is false and usuario_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, Long.parseLong(id));
			statement.setLong(2, userLogado);
			
			ResultSet resultado = statement.executeQuery();
			
			//Se tem resultado
			while (resultado.next()) {
				
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setNome(resultado.getString("nome"));
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setSenha(resultado.getString("senha"));
				modelLogin.setPerfil(resultado.getString("perfil"));
				modelLogin.setSexo(resultado.getString("sexo"));
				modelLogin.setFotouser(resultado.getString("fotouser"));
				modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));				
				modelLogin.setCep(resultado.getString("cep"));
				modelLogin.setLogradouro(resultado.getString("logradouro"));
				modelLogin.setBairro(resultado.getString("bairro"));
				modelLogin.setLocalidade(resultado.getString("localidade"));
				modelLogin.setUf(resultado.getString("uf"));
				modelLogin.setNumero(resultado.getString("numero"));
				modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
				modelLogin.setRendaMensal(resultado.getDouble("rendamensal"));
				
			}
			
			return modelLogin;			
		}
		
	//Método para validar um login já existente
	public boolean validarLogin(String login) throws Exception {
		
		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('"+ login +"');";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		if(resultado.next()) {
			return resultado.getBoolean("existe");			
		}		
		return false;
	}
	
	//Método para deletar usuário cadastrado
	public void deletarUser(String idUser) throws Exception {
		
		String sql = "delete from model_login where id = ? and useradmin is false;";
		
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		
		prepareSql.setLong(1, Long.parseLong(idUser));
		
		prepareSql.executeUpdate();
		
		connection.commit();

	}
	
	//Método para carregar a lista de Telefones do usuáro:
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
			modelTelefone.setUsuario_cad_id(this.consultarUsuarioID(retornaFone.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(this.consultarUsuarioID(retornaFone.getLong("usuario_pai_id")));
				
			retornaFoneUser.add(modelTelefone);
					
			}
				
			return retornaFoneUser;
				
		}
	
	}
