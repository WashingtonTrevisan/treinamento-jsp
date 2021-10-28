package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOLoginRepository;
import dao.DAOUsuarioRepository;
import model.ModelLogin;

// As Servlets muitas vezes tamb�m s�o chamadas de Controller
// Mapeamento que vem da tela atrav�s da "URL"
@WebServlet(urlPatterns = { "/principal/ServletLogin", "/ServletLogin" })
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public ServletLogin() {
		super();

	}

	// Recebe os dados pela URL atrav�s de par�metros
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		
		if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			request.getSession().invalidate();  //Inval�da a sess�o
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
			
		} else {
			doPost(request, response);
			
		}
	}

	// Recebe os dados pela URL atrav�s de formul�rios
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");

		try {
		
					if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
		
						ModelLogin modelLogin = new ModelLogin();
						modelLogin.setLogin(login);
						modelLogin.setSenha(senha);
		
						// Simulando um login
						if (daoLoginRepository.validarAutenticacao(modelLogin)) {
							
							modelLogin = daoUsuarioRepository.consultarUsuarioLogado(login);
							
							////M�todo para carregar usu�rio no sistema
							request.getSession().setAttribute("usuario", modelLogin.getLogin());
							
							//M�todo para carregar o perfil do usu�rio no sistema
							request.getSession().setAttribute("perfil", modelLogin.getPerfil());
							
							//M�todo para carregar a foto do usu�rio no sistema
							request.getSession().setAttribute("imagemUser", modelLogin.getFotouser());
		
							if (url == null || url.equals("null")) {
								url = "principal/principal.jsp";
							}
		
							RequestDispatcher redirecionar = request.getRequestDispatcher(url);
							redirecionar.forward(request, response);
		
						} else {
		
							RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
							request.setAttribute("msg", "Informe o login e senha corretamente...");
							redirecionar.forward(request, response);
						}
		
					} else {
		
						RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
						request.setAttribute("msg", "Informe o login e senha corretamente...");
						redirecionar.forward(request, response);
		
					}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}
}
