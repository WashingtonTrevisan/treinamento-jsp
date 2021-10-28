package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;
import model.ModelLogin;
import model.ModelTelefone;

@WebServlet("/ServletTelefone")
public class ServletTelefone extends ServletGenericUtil {
	
	private static final long serialVersionUID = 1L;
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	private DAOTelefoneRepository daoTelefoneRepository = new DAOTelefoneRepository();
       
    public ServletTelefone() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
			//Método para excluir um número de telefone cadastrado no cliente: 
			String acao = request.getParameter("acao");
			
				if (acao != null && !acao.isEmpty() && acao.equals("excluir")) {
					
					//Primeiro pega o telefone por parametro e deleta:
					String idFone = request.getParameter("id");				
					
					//Depois de pegar o telefone por parâmetro o mesmo é deletado:
					daoTelefoneRepository.deletarTelefone(Long.parseLong(idFone));
					
					//Depois pega-se o userPai
					String userPai = request.getParameter("userPai");
					
					//Consulta o objeto para retornar para a tela:
					ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(Long.parseLong(userPai));
					
					//Consulta os telefones que estão cadastrados para o usuário: 
					//Este métodpo trás para a tela todos os telefones menos o que foi deletado:
					List<ModelTelefone> carregaListaTelefone = daoTelefoneRepository.listFoneUser(modelLogin.getId());
					request.setAttribute("carregaListaTelefone", carregaListaTelefone);
					
					//Retorna o objeto pai na tela, pronto para se fazer um novo cadastro:
					request.setAttribute("msg", "Telefone Excluido com Sucesso");
					request.setAttribute("modelLogin", modelLogin);	
					request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
					
					return;
					
				}
			
			
		String idUser = request.getParameter("idUser");

		if (idUser != null && !idUser.isEmpty()) {		
	
			//Esse método carrega o usuário na tela pelo ID:
			ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(Long.parseLong(idUser));	
			
			//Carrega a lista de usuários:
			List<ModelTelefone> carregaListaTelefone = daoTelefoneRepository.listFoneUser(modelLogin.getId());
			request.setAttribute("carregaListaTelefone", carregaListaTelefone);
			
			request.setAttribute("modelLogin", modelLogin);	
			
			//Retorno para a tela do usuário:
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
		
		} else {
			
			List<ModelLogin> carregaUserJSTL = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request)); 
			request.setAttribute("carregaUserJSTL", carregaUserJSTL);
    		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
    		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);  	
    		
			
		} 
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			String usuario_pai_id = request.getParameter("id");
			String numero = request.getParameter("numero");
			
			//Gravando ou não o telefone vamos voltar para a tela os seguintes métodos:
			//A lista de telefones: carregaListaTelefone e o Objeto:  modelLogin   
			
			if (!daoTelefoneRepository.existeFone(numero, Long.valueOf(usuario_pai_id))) {
			
					ModelTelefone modelTelefone = new ModelTelefone();
					
					modelTelefone.setNumero(numero);
					modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultarUsuarioID(Long.parseLong(usuario_pai_id)));
					modelTelefone.setUsuario_cad_id(super.getUserLogadoObjt(request));
					
					daoTelefoneRepository.gravarTelefone(modelTelefone);
						
					//Exibe mensagem "Salvo com Sucesso"
					request.setAttribute("msg", "Salvo com Sucesso...");		
			
			} else {
				
				request.setAttribute("msg", "Telefone já existe para esse usuário...");	
				
			}
			
					//Carrega uma lista 			
					List<ModelTelefone> carregaListaTelefone = daoTelefoneRepository.listFoneUser(Long.parseLong(usuario_pai_id));
					
					ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(Long.parseLong(usuario_pai_id));
					
					// ( Primeiro -->> "carregaListaTelefone" São os valores pegos na tela ) 
					// ( Segundo -->> carregaListaTelefone São os valores que foram carregados na tela )
					request.setAttribute("modelLogin", modelLogin);
					
					//O método abaixo retorna o usuário Pai para a tela de cadastro de Telefone:
					request.setAttribute("carregaListaTelefone", carregaListaTelefone);
					
					//Retorna para a página de cadastro de telefones:
					request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
