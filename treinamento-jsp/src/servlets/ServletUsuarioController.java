package servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import beandto.BeanDtoGraficoSalarioUser;
import dao.DAOUsuarioRepository;
import model.ModelLogin;
import util.ReportUtil;


@MultipartConfig
@WebServlet( urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
	//Iniciando e instanciando a classe DAOUsuarioRepository feito isso, 
	//nessa mesma classe antes se setar os dados, escreve o daoUsuarioRepository 
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();	
       
    public ServletUsuarioController() {
        super();

    }
	
    //Método deletar com redirecionamento de formulários, sem o uso do Ajax
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	try {
    	
    	String acao = request.getParameter("acao");
    	
    	if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
    		
    		String idUser = request.getParameter("id");    		
    		
    		daoUsuarioRepository.deletarUser(idUser); 
    		
    		//Método para manter os cadastros (usuários) na tela debaixo depois da pesquisa feita
			List<ModelLogin> carregaUserJSTL = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request)); 
			
			request.setAttribute("carregaUserJSTL", carregaUserJSTL);
    		
    		request.setAttribute("msg", "Cadastro excluído com sucesso !!!");
    		
    		//Antes de retornar para a tela de cadastro de usuário, 
    		//é necessário escrever o método abaixo para que seja montada a paginação.
    		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
    		
    		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
    		
    	//Método deletar sem redirecionamento de formulários, utilizando - Ajax	
    	} else 
    		
    		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
        		
        		String idUser = request.getParameter("id");    	
        		
        		daoUsuarioRepository.deletarUser(idUser);   
        		
        		response.getWriter().write("Excluido com sucesso !");
        		
        	} else 
        		
        		//Método para pesquisar e carregar usuário cadastrado utilizando - Ajax	
        		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
            		
            		String nomeBusca = request.getParameter("nomeBusca"); 

            		List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
            		
            		ObjectMapper mapper = new ObjectMapper();
            		
            		String json = mapper.writeValueAsString(dadosJsonUser);
            		
            		response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultaUsuarioListTotalPaginacaoAjax(nomeBusca, super.getUserLogado(request)));
            		response.getWriter().write(json);
    		
        	} else 
        		
        		//Método para pesquisar e carregar usuário cadastrado utilizando - Ajax	no botão Pesquisar tela central
        		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
            		
            		String nomeBusca = request.getParameter("nomeBusca");  
            		String pagina = request.getParameter("pagina");

            		List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));
            		
            		ObjectMapper mapper = new ObjectMapper();
            		
            		String json = mapper.writeValueAsString(dadosJsonUser);
            		
            		response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultaUsuarioListTotalPaginacaoAjax(nomeBusca, super.getUserLogado(request)));
            		response.getWriter().write(json);
    		
        	
        		} else 
        		
        		//Método para buscar e carregar usuário na tela para edição utilizando - Ajax
        		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
        			
        			String id = request.getParameter("id");
        			
        			ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(id, super.getUserLogado(request));
        			
        			//Método para manter os cadastros (usuários) na tela debaixo depois da pesquisa feita
        			List<ModelLogin> carregaUserJSTL = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request)); 
        			request.setAttribute("carregaUserJSTL", carregaUserJSTL);
        			
        			request.setAttribute("msg", "Usuário em edição");		
        			request.setAttribute("mantemDadosTela", modelLogin);
        			
        			//Antes de retornar para a tela de cadastro de usuário, 
            		//é necessário escrever o método abaixo para que seja montada a paginação.
            		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
        			
        			RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
        			redireciona.forward(request, response);       		
        			
       		} else 
        		
        		//Método para pesquisar e carregar na tela usuário cadastrado utilizando - JSTL	
            	if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("ListarUser")) {
            			
            		List<ModelLogin> carregaUserJSTL = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request)); 
            	
            		request.setAttribute("msg", "Usuário carregados para edição");		
        			request.setAttribute("carregaUserJSTL", carregaUserJSTL);
        			
        			//Antes de retornar para a tela de cadastro de usuário, 
            		//é necessário escrever o método abaixo para que seja montada a paginação.
            		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
        			
        			RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
        			redireciona.forward(request, response);
        		
        	} else 
        		
        		//Método para fazer download da imagem do usuário	
        		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
        		
        		String idUser = request.getParameter("id"); 
        		
        		ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(idUser, super.getUserLogado(request));
        		
        		if (modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
        			
        			response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaofotouser());
        			response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1]));
        			
        		}
        		
        	} else 
        		
        		//Método para fazer a paginação de pesquisa de usuário	
        		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
        			
        			Integer offset = Integer.parseInt(request.getParameter("pagina"));
        			
        			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioListPaginada(this.getUserLogado(request), offset);
        			
        			request.setAttribute("carregaUserJSTL", modelLogins);
        			
        			//Antes de retornar para a tela de cadastro de usuário, 
            		//é necessário escrever o método abaixo para que seja montada a paginação.
            		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));        		
            		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);  		
        			
        			
        		} else 
            		
            		//Método para pesquisar intervalos dataInicial e dataFinal no relatório:	
            		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {
            			
            			String dataInicial = request.getParameter("dataInicial");
            			String dataFinal = request.getParameter("dataFinal");
            			
            			//Condição para imprimir todos os usuários cadastrados, de uma só vez:
            			if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
            				
            				request.setAttribute("listarUser", daoUsuarioRepository.consultaUsuarioListRelatorio(super.getUserLogado(request)));
            				
            			} else {
            				request.setAttribute("listarUser", daoUsuarioRepository.consultaUsuarioListRelatorio
            						(super.getUserLogado(request), dataInicial, dataFinal));
            				
            			}
            			
            			//Mantém as datas inicial e final setadas no campo:
            			request.setAttribute("dataInicial", dataInicial);
            			request.setAttribute("dataFinal", dataFinal);
            			
            			//Retorna para a página principal de Relatório:
            			request.getRequestDispatcher("principal/reluser.jsp").forward(request, response); 
            			
            		}  else    
    	
    					//Método para pesquisar intervalos dataInicial e dataFinal no relatório:	
            			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioPDF")) {
            				
            				String dataInicial = request.getParameter("dataInicial");
                			String dataFinal = request.getParameter("dataFinal");
                			
                			List<ModelLogin> modelLogins = null;
                			
                			if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
                				
                				modelLogins = daoUsuarioRepository.consultaUsuarioListRelatorio(super.getUserLogado(request));
                				
                			} else {
                				
                				modelLogins = daoUsuarioRepository.consultaUsuarioListRelatorio
                						(super.getUserLogado(request), dataInicial, dataFinal);

                			}
                			
                				//Método para gerar um sub-relatório: 
                				HashMap<String, Object> params = new HashMap<String, Object>();
                				params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorios") + File.separator);
                			
                			
                				byte[] relatorio = new ReportUtil().gerarRelatorioPDF(modelLogins, "rel-user-jsp", params, request.getServletContext());
            				
                				response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
                				response.getOutputStream().write(relatorio);
							
                		//Método para exibir a média salarial sem informar uma data inicial e final: 		
						} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("graficoSalario")) {
							
							String dataInicial = request.getParameter("datainicial");
							String dataFinal = request.getParameter("dataFinal");
							
							if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
								
								BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = 
										daoUsuarioRepository.montarGraficoMediaSalario(super.getUserLogado(request));
								
									ObjectMapper mapper = new ObjectMapper();				            		
									String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);				            		
									response.getWriter().write(json);
								
								//Método para exibir a média salarial infromando data inicial e data final:	
								} else {
									
									BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = 
											daoUsuarioRepository.montarGraficoMediaSalario(super.getUserLogado(request), dataInicial, dataFinal);
									
										ObjectMapper mapper = new ObjectMapper();				            		
										String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);				            		
										response.getWriter().write(json);

                			}
							
						} else 
						
					{ 		
        		//Método para manter os cadastros (usuários) na tela debaixo depois da pesquisa feita
    			List<ModelLogin> carregaUserJSTL = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request)); 
    			request.setAttribute("carregaUserJSTL", carregaUserJSTL);
    			
    			//Antes de retornar para a tela de cadastro de usuário, 
        		//é necessário escrever o método abaixo para que seja montada a paginação.
        		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
    		
        		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);  		
        	}
    	
    	} catch (Exception e) {
    		e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
    	}    	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
		
		//Método para gravar um novo usuário no banco de dados
		String msg = "Operação realizada com sucesso"; 
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String perfil = request.getParameter("perfil");
		String sexo = request.getParameter("sexo");	
		
		String cep = request.getParameter("cep");
		String logradouro = request.getParameter("logradouro");
		String bairro = request.getParameter("bairro");
		String localidade = request.getParameter("localidade");
		String uf = request.getParameter("uf");
		String numero = request.getParameter("numero");
		String dataNascimento = request.getParameter("dataNascimento");
		String rendaMensal = request.getParameter("rendaMensal");
		
		//Duas maneiras para eliminar o R$ os pontos e a vírgula para salvar no banco:
		//A que funcionou foi o método com o comando Split
		
		//rendaMensal = rendaMensal.replaceAll("\\R", "").replaceAll("\\$", "").replaceAll("\\ ", "").replaceAll("\\,", "");
		rendaMensal = rendaMensal.split("\\ ")[1].replaceAll("\\.", "").replaceAll("\\,", ".");
		
		
		ModelLogin modelLogin = new ModelLogin();
		
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setLogin(login);
		modelLogin.setSenha(senha);
		modelLogin.setPerfil(perfil);
		modelLogin.setSexo(sexo);
		
		modelLogin.setCep(cep);
		modelLogin.setLogradouro(logradouro);
		modelLogin.setBairro(bairro);
		modelLogin.setLocalidade(localidade);
		modelLogin.setUf(uf);
		modelLogin.setNumero(numero);	
		//Tratando a variável dataNascimento para a gravação no banco de dados:
		modelLogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
		//Tratando a variável rendaMensal para a gravação no banco e dados:
		modelLogin.setRendaMensal(Double.valueOf(rendaMensal));
		
		
		//Métoda para salvar imagem no banco de dados
		if (ServletFileUpload.isMultipartContent(request)) {	
			
			Part part = request.getPart("fileFoto");  //Esse comando pega a foto da tela.
			
			if (part.getSize() > 0) {
			
			byte[] foto = IOUtils.toByteArray(part.getInputStream());  //Esse método converte imagem para byte
			
			//Método para preparar a imagem antes de gravar no banco de dados			
			String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(foto);
			
			modelLogin.setFotouser(imagemBase64);
			modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);
			
			}
			
		}		
		
		//Métodp para verificar se existe usuário sendo cadastrado com o mesmo login
		//Caso exista algum usuário cadastrado com o mesmo login é dada a mensagem, abaixo 
		//Caso contrário é gravado um novo usuário no banco de dados
		if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
			msg = "Já existe usuário com o mesmo login, informe um novo login !";
			
		} else {
			
			if(modelLogin.isNovo()) {
				msg = "Gravado com sucesso !";				
			} else {
				msg = "Atualizado com sucesso";
			}
			
			modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
		}
		
		//Método para manter os cadastros (usuários) na tela debaixo depois da pesquisa feita
		List<ModelLogin> carregaUserJSTL = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request)); 
		request.setAttribute("carregaUserJSTL", carregaUserJSTL);
		
		request.setAttribute("msg", msg);		
		
		//Com esse método setAttribute quando a tela de cadastro é recarregada as informações 
		//que foram preenchidas continuam na tela de cadastro, elas não são apagadas
		request.setAttribute("mantemDadosTela", modelLogin);
		
		//Antes de retornar para a tela de cadastro de usuário, 
		//é necessário escrever o método abaixo para que seja montada a paginação.
		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
		
		RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
		redireciona.forward(request, response);
		
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
		
	}

}
