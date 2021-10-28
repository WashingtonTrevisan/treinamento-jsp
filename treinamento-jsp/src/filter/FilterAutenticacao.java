package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.SingleConnectionBanco;
import dao.DaoVersionadorBanco;

// Intercepta todas as requisi��es que vierem do projeto ou mapeamento
@WebFilter(urlPatterns = { "/principal/*" })
public class FilterAutenticacao implements Filter {

	//Essa declara��o vem do pacote SingleConnection - Connection SQL
	//Obs: Fazer a declara��o dentro do Init tambem (abaixo)
	private static Connection connection;

	public FilterAutenticacao() {

	}

	// Encerra os processos quando o servidor � parado.
	// Mata os processos de conex�o com o Banco de Dados
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Intercepta todas as requisi��es e d� as respostas no Sistema
	// Tudo que for feito no Sistema passa por aki no doFilter
	// Validar uma autentica��o
	// Dar commit e RolBack de transa��es no banco
	// Validar e fazer redirecionamento de p�ginas
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {

				HttpServletRequest requisicao = (HttpServletRequest) request;
				HttpSession session = requisicao.getSession();
	
				String usuarioLogado = (String) session.getAttribute("usuario");
	
				// Url que est� sendo acessada:
				String urlParaAutenticar = requisicao.getServletPath();
	
				// Validar para ver se o usu�rio est� logado sen�o volta para a tela de login:
				if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {
	
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
					request.setAttribute("msg", "Favor realize o login...");
					redirecionar.forward(request, response);
					return;
	
				} else {
					chain.doFilter(request, response);					
				}
				
				//Deu tudo certo, comita as altera��es no banco de dados. 
				connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	// Inicia os processos quando o servidor sobe os Projetos.
	// Inicia a conex�o com o Banco de Dados quando inicia o projeto
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();
		
		DaoVersionadorBanco daoVersionadorBanco = new DaoVersionadorBanco();
		
		String caminhoPastaSQL = fConfig.getServletContext().getRealPath("versionadorbancosql") + File.separator;
		
		File[] filesSql = new File(caminhoPastaSQL).listFiles();
		
		
		try {
			
			for (File file : filesSql) {
				
				boolean arquivoJaRodade = daoVersionadorBanco.arquivoSqlRodado(file.getName());
				
				if (!arquivoJaRodade) {
					
					FileInputStream entradaArquivo = new FileInputStream(file);
					
					Scanner lerArquivo = new Scanner(entradaArquivo, "UTF-8");
					
					StringBuilder sql = new StringBuilder();
					
					while (lerArquivo.hasNext()) {
						
						sql.append(lerArquivo.nextLine());
						sql.append("\n");
						
					}
					
						connection.prepareStatement(sql.toString()).execute();
						daoVersionadorBanco.gravaArquivoSqlRodado(file.getName());
						
						connection.commit();						
						lerArquivo.close();
				}
			}
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
