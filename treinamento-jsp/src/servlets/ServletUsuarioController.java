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
	
    //M�todo deletar com redirecionamento de formul�rios, sem o uso do Ajax
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	try {
    	
    	String acao = request.getParameter("acao");
    	
    	if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
    		
    		String idUser = request.getParameter("id");    		
    		
    		daoUsuarioRepository.deletarUser(idUser); 
    		
    		//M�todo para manter os cadastros (usu�rios) na tela debaixo depois da pesquisa feita
			List<ModelLogin> carregaUserJSTL = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request)); 
			
			request.setAttribute("carregaUserJSTL", carregaUserJSTL);
    		
    		request.setAttribute("msg", "Cadastro exclu�do com sucesso !!!");
    		
    		//Antes de retornar para a tela de cadastro de usu�rio, 
    		//� necess�rio escrever o m�todo abaixo para que seja montada a pagina��o.
    		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
    		
    		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
    		
    	//M�todo deletar sem redirecionamento de formul�rios, utilizando - Ajax	
    	} else 
    		
    		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarAjax")) {
        		
        		String idUser = request.getParameter("id");    	
        		
        		daoUsuarioRepository.deletarUser(idUser);   
        		
        		response.getWriter().write("Excluido com sucesso !");
        		
        	} else 
        		
        		//M�todo para pesquisar e carregar usu�rio cadastrado utilizando - Ajax	
        		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
            		
            		String nomeBusca = request.getParameter("nomeBusca"); 

            		List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
            		
            		ObjectMapper mapper = new ObjectMapper();
            		
            		String json = mapper.writeValueAsString(dadosJsonUser);
            		
            		response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultaUsuarioListTotalPaginacaoAjax(nomeBusca, super.getUserLogado(request)));
            		response.getWriter().write(json);
    		
        	} else 
        		
        		//M�todo para pesquisar e carregar usu�rio cadastrado utilizando - Ajax	no bot�o Pesquisar tela central
        		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
            		
            		String nomeBusca = request.getParameter("nomeBusca");  
            		String pagina = request.getParameter("pagina");

            		List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));
            		
            		ObjectMapper mapper = new ObjectMapper();
            		
            		String json = mapper.writeValueAsString(dadosJsonUser);
            		
            		response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultaUsuarioListTotalPaginacaoAjax(nomeBusca, super.getUserLogado(request)));
            		response.getWriter().write(json);
    		
        	
        		} else 
        		
        		//M�todo para buscar e carregar usu�rio na tela para edi��o utilizando - Ajax
        		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
        			
        			String id = request.getParameter("id");
        			
        			ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(id, super.getUserLogado(request));
        			
        			//M�todo para manter os cadastros (usu�rios) na tela debaixo depois da pesquisa feita
        			List<ModelLogin> carregaUserJSTL = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request)); 
        			request.setAttribute("carregaUserJSTL", carregaUserJSTL);
        			
        			request.setAttribute("msg", "Usu�rio em edi��o");		
        			request.setAttribute("mantemDadosTela", modelLogin);
        			
        			//Antes de retornar para a tela de cadastro de usu�rio, 
            		//� necess�rio escrever o m�todo abaixo para que seja montada a pagina��o.
            		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
        			
        			RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
        			redireciona.forward(request, response);       		
        			
       		} else 
        		
        		//M�todo para pesquisar e carregar na tela usu�rio cadastrado utilizando - JSTL	
            	if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("ListarUser")) {
            			
            		List<ModelLogin> carregaUserJSTL = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request)); 
            	
            		request.setAttribute("msg", "Usu�rio carregados para edi��o");		
        			request.setAttribute("carregaUserJSTL", carregaUserJSTL);
        			
        			//Antes de retornar para a tela de cadastro de usu�rio, 
            		//� necess�rio escrever o m�todo abaixo para que seja montada a pagina��o.
            		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
        			
        			RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
        			redireciona.forward(request, response);
        		
        	} else 
        		
        		//M�todo para fazer download da imagem do usu�rio	
        		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
        		
        		String idUser = request.getParameter("id"); 
        		
        		ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(idUser, super.getUserLogado(request));
        		
        		if (modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
        			
        			response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaofotouser());
        			response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1]));
        			
        		}
        		
        	} else 
        		
        		//M�todo para fazer a pagina��o de pesquisa de usu�rio	
        		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
        			
        			Integer offset = Integer.parseInt(request.getParameter("pagina"));
        			
        			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioListPaginada(this.getUserLogado(request), offset);
        			
        			request.setAttribute("carregaUserJSTL", modelLogins);
        			
        			//Antes de retornar para a tela de cadastro de usu�rio, 
            		//� necess�rio escrever o m�todo abaixo para que seja montada a pagina��o.
            		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));        		
            		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);  		
        			
        			
        		} else 
            		
            		//M�todo para pesquisar intervalos dataInicial e dataFinal no relat�rio:	
            		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {
            			
            			String dataInicial = request.getParameter("dataInicial");
            			String dataFinal = request.getParameter("dataFinal");
            			
            			//Condi��o para imprimir todos os usu�rios cadastrados, de uma s� vez:
            			if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
            				
            				request.setAttribute("listarUser", daoUsuarioRepository.consultaUsuarioListRelatorio(super.getUserLogado(request)));
            				
            			} else {
            				request.setAttribute("listarUser", daoUsuarioRepository.consultaUsuarioListRelatorio
            						(super.getUserLogado(request), dataInicial, dataFinal));
            				
            			}
            			
            			//Mant�m as datas inicial e final setadas no campo:
            			request.setAttribute("dataInicial", dataInicial);
            			request.setAttribute("dataFinal", dataFinal);
            			
            			//Retorna para a p�gina principal de Relat�rio:
            			request.getRequestDispatcher("principal/reluser.jsp").forward(request, response); 
            			
            		}  else    
    	
    					//M�todo para pesquisar intervalos dataInicial e dataFinal no relat�rio:	
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
                			
                				//M�todo para gerar um sub-relat�rio: 
                				HashMap<String, Object> params = new HashMap<String, Object>();
                				params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorios") + File.separator);
                			
                			
                				byte[] relatorio = new ReportUtil().gerarRelatorioPDF(modelLogins, "rel-user-jsp", params, request.getServletContext());
            				
                				response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
                				response.getOutputStream().write(relatorio);
							
                		//M�todo para exibir a m�dia salarial sem informar uma data inicial e final: 		
						} else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("graficoSalario")) {
							
							String dataInicial = request.getParameter("datainicial");
							String dataFinal = request.getParameter("dataFinal");
							
							if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
								
								BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = 
										daoUsuarioRepository.montarGraficoMediaSalario(super.getUserLogado(request));
								
									ObjectMapper mapper = new ObjectMapper();				            		
									String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);				            		
									response.getWriter().write(json);
								
								//M�todo para exibir a m�dia salarial infromando data inicial e data final:	
								} else {
									
									BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = 
											daoUsuarioRepository.montarGraficoMediaSalario(super.getUserLogado(request), dataInicial, dataFinal);
									
										ObjectMapper mapper = new ObjectMapper();				            		
										String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);				            		
										response.getWriter().write(json);

                			}
							
						} else 
						
					{ 		
        		//M�todo para manter os cadastros (usu�rios) na tela debaixo depois da pesquisa feita
    			List<ModelLogin> carregaUserJSTL = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request)); 
    			request.setAttribute("carregaUserJSTL", carregaUserJSTL);
    			
    			//Antes de retornar para a tela de cadastro de usu�rio, 
        		//� necess�rio escrever o m�todo abaixo para que seja montada a pagina��o.
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
		
		//M�todo para gravar um novo usu�rio no banco de dados
		String msg = "Opera��o realizada com sucesso"; 
		
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
		
		//Duas maneiras para eliminar o R$ os pontos e a v�rgula para salvar no banco:
		//A que funcionou foi o m�todo com o comando Split
		
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
		//Tratando a vari�vel dataNascimento para a grava��o no banco de dados:
		modelLogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
		//Tratando a vari�vel rendaMensal para a grava��o no banco e dados:
		modelLogin.setRendaMensal(Double.valueOf(rendaMensal));
		
		
		//M�toda para salvar imagem no banco de dados
		if (ServletFileUpload.isMultipartContent(request)) {	
			
			Part part = request.getPart("fileFoto");  //Esse comando pega a foto da tela.
			
			if (part.getSize() > 0) {
			
			byte[] foto = IOUtils.toByteArray(part.getInputStream());  //Esse m�todo converte imagem para byte
			
			//M�todo para preparar a imagem antes de gravar no banco de dados			
			String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(foto);
			
			modelLogin.setFotouser(imagemBase64);
			modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);
			
			}
			
		}		
		
		//M�todp para verificar se existe usu�rio sendo cadastrado com o mesmo login
		//Caso exista algum usu�rio cadastrado com o mesmo login � dada a mensagem, abaixo 
		//Caso contr�rio � gravado um novo usu�rio no banco de dados
		if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
			msg = "J� existe usu�rio com o mesmo login, informe um novo login !";
			
		} else {
			
			if(modelLogin.isNovo()) {
				msg = "Gravado com sucesso !";				
			} else {
				msg = "Atualizado com sucesso";
			}
			
			modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
		}
		
		//M�todo para manter os cadastros (usu�rios) na tela debaixo depois da pesquisa feita
		List<ModelLogin> carregaUserJSTL = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request)); 
		request.setAttribute("carregaUserJSTL", carregaUserJSTL);
		
		request.setAttribute("msg", msg);		
		
		//Com esse m�todo setAttribute quando a tela de cadastro � recarregada as informa��es 
		//que foram preenchidas continuam na tela de cadastro, elas n�o s�o apagadas
		request.setAttribute("mantemDadosTela", modelLogin);
		
		//Antes de retornar para a tela de cadastro de usu�rio, 
		//� necess�rio escrever o m�todo abaixo para que seja montada a pagina��o.
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
