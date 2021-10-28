<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

  <body>
    
<jsp:include page="theme-loader.jsp"></jsp:include>



  <!-- Pre-loader end -->
  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
      
<jsp:include page="navbar.jsp"></jsp:include>
         
          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">
              
<jsp:include page="navbar-main-menu.jsp"></jsp:include>
                  
                  <div class="pcoded-content">
                      <!-- Page-header start -->
                      
<jsp:include page="page-header.jsp"></jsp:include>                   
                      
                      <!-- Page-header end -->
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
                                    <div class="page-body">                                        
                                        
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <!-- Basic Form Inputs card start -->
                                                <div class="card">                                                    
                                                    <div class="card-block">
                                                        <h4 class="sub-title">Cadastro de usu�rios no Sistema</h4>                               
                                        
                                        				<form class="form-material" enctype="multipart/form-data" action="<%= request.getContextPath() %>/ServletUsuarioController" method="post" id="formUser">
                                        				
                                        					<input type="hidden" name="acao" id="acao" value="">
                                        				
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id" id="id" class="form-control" readonly="readonly" value="${mantemDadosTela.id}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">ID</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default input-group mb-4">                                                            	
                                                            	<div class="input-gro-prepend">
                                                            	
                                                            		<c:if test="${mantemDadosTela.fotouser != '' && mantemDadosTela.fotouser != null}">
                                                            		   <a href="<%= request.getContextPath()%>/ServletUsuarioController?acao=downloadFoto&id=${mantemDadosTela.id}">                                                            			
                                                            			  <img alt="Imagem User" id="fotoembase64" src="${mantemDadosTela.fotouser}" width="70px">
                                                            			  
																	   </a>
                                                            		</c:if>
                                                            		
                                                            		<c:if test="${mantemDadosTela.fotouser == '' || mantemDadosTela.fotouser == null}">
                                                            			<img alt="Imagem User" id="fotoembase64" src="assets/images/user.png" width="70px">
                                                            		</c:if>
                                                            		
                                                            	</div>
                                                            	
                                                            		<input type="file" id="fileFoto" name="fileFoto" accept="image/*" onchange="visualizarImg('fotoembase64','fileFoto');" class="form-control-file" style="margin-top: 37px; margin-left: 5px;">
                                                           
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="nome" id="nome" class="form-control" required="required" value="${mantemDadosTela.nome}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome</label>                                                                
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="rendaMensal" id="rendaMensal" class="form-control" required="required" value="${mantemDadosTela.rendaMensal}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Renda Mensal</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="dataNascimento" id="dataNascimento" class="form-control" required="required" value="${mantemDadosTela.dataNascimento}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Data Nascimento</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="email" name="email" id="email" class="form-control" required="required" autocomplete="off" value="${mantemDadosTela.email}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">E-mail</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
	                                                            <select class="form-control" aria-label="Default select example" name="perfil">
																    <option disabled="disabled" selected="selected">Selecione o Perfil</option>
																    
																	<option value="ADMIN" <% ModelLogin modelLogin = (ModelLogin) request.getAttribute("mantemDadosTela");
																		if (modelLogin != null && modelLogin.getPerfil().equals("ADMIN")) {
																			out.print(" ");
																				out.print("selected=\"selected\"");
																			out.print(" ");
																		} %>>Admin</option> 											
																	
																	
																	<option value="SECRETARIA" <% modelLogin = (ModelLogin) request.getAttribute("mantemDadosTela"); 	
																		if (modelLogin != null && modelLogin.getPerfil().equals("SECRETARIA")) {
																			out.print(" ");
																				out.print("selected=\"selected\"");
																			out.print(" ");
																		} %>>Secret�ria</option>
																	
																	<option value="AUXILIAR" <% modelLogin = (ModelLogin) request.getAttribute("mantemDadosTela");														
																		if (modelLogin != null && modelLogin.getPerfil().equals("AUXILIAR")) {
																			out.print(" ");
																				out.print("selected=\"selected\"");
																			out.print(" ");
																		} %>>Auxiliar</option>
																	
																</select>
																	<span class="form-bar"></span>
                                                                	<label class="float-label">Perfil</label>
															</div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input onblur="pesquisarCep();" type="text" name="cep" id="cep" class="form-control" required="required" autocomplete="off" value="${mantemDadosTela.cep}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Cep</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="logradouro" id="logradouro" class="form-control" required="required" autocomplete="off" value="${mantemDadosTela.logradouro}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Logradouro</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="bairro" id="bairro" class="form-control" required="required" autocomplete="off" value="${mantemDadosTela.bairro}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Bairro</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="localidade" id="localidade" class="form-control" required="required" autocomplete="off" value="${mantemDadosTela.localidade}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Localidade</label>
                                                            </div>															                                                            
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="uf" id="uf" class="form-control" required="required" autocomplete="off" value="${mantemDadosTela.uf}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Estado</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="numero" id="numero" class="form-control" required="required" autocomplete="off" value="${mantemDadosTela.numero}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">N�mero</label>
                                                            </div>
                                                            
                                                             <div class="form-group form-default form-static-label">
                                                                <input type="text" name="login" id="login" class="form-control" required="required" autocomplete="off" value="${mantemDadosTela.login}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Login</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="password" name="senha" id="senha" class="form-control" required="required" autocomplete="off" value="${mantemDadosTela.senha}">
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Senha</label>
                                                            </div> 
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                            
																 <input type="radio" name="sexo" checked="checked" value="MASCULINO" <% 																	 
																 	modelLogin = (ModelLogin) request.getAttribute("mantemDadosTela");							 
															 			if (modelLogin != null && modelLogin.getSexo().equals("MASCULINO")){
															 				out.print(" ");
															 					out.print("checked=\"checked\"");
															 				out.print(" ");
															 		    }  %>> Masculino </input>
																 
																 
																 <input type="radio" name="sexo" value="FEMININO" <% 
																 	modelLogin = (ModelLogin) request.getAttribute("mantemDadosTela");							 
																 		if (modelLogin != null && modelLogin.getSexo().equals("FEMININO")){
																 			out.print(" ");
																 				out.print("checked=\"checked\"");
																 			out.print(" ");
																 		}  %>> Feminino </input>
																 </div>
                                                            
                                                            <button type="button" class="btn btn-primary btn-round waves-effect waves-light" onclick="limparForm();">Novo</button>
                                                            <button class="btn btn-success btn-round waves-effect waves-light">Salvar</button>                                                            
												            <button type="button" class="btn btn-danger btn-round waves-effect waves-light" onclick="criarDeleteComAjax();">Excluir</button>
															
															<!-- No bot�o abaixo foi usao <a><\a> pois vai ser feito um link no bot�o Telefone -->
															<!-- <a><\a> Dispara um comando get para a nossa ServletTelefone -->
															
															
															<c:if test="${mantemDadosTela.id > 0}">
																<a href=" <%= request.getContextPath() %>/ServletTelefone?idUser=${mantemDadosTela.id}" type="button" class="btn btn-primary btn-round waves-effect waves-light">Telefone</a>
												            </c:if>
												            <button type="button" class="btn btn-inverse btn-round waves-effect waves-light" data-toggle="modal" data-target="#exampleModalUsuario">Pesquisar</button>
                                                            
                                        				</form>

		                                            </div>
		                         	       	   </div>
		                                    </div>
		                                </div> 
		                                
		                                <!-- Mensagem passada por parametros -->	
		                                <!-- <span>${msg}</span> -->	
		                                
		                                <!-- Mensagem passada por Ajax -->
		                                <span id="msg">${msg}</span>
		                                
		                                 <div style="height:300px; overflow: scroll;">			 		  
											<table class="table" id="tabelaResultadoPesquisaView">
						  						<thead>
						    						<tr>
						      							<th scope="col">ID</th>
												        <th scope="col">Nome</th>
												        <th scope="col">E-mail</th>
												        <th scope="col">Editar</th>
												        <th scope="col">Excluir</th>
						 						    </tr>
						  						</thead>
						  						<tbody>						  						
						  							<c:forEach items="${carregaUserJSTL}" var="jstl">
						  								<tr>
						  									<td><c:out value="${jstl.id}"></c:out></td>	
						  									<td><c:out value="${jstl.nome}"></c:out></td>
						  									<td><c:out value="${jstl.email}"></c:out></td>
						  									<td><a class="btn btn-outline-dark" href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${jstl.id}">Editar</a></td>	
						  									<td><a class="btn btn-outline-danger" href="<%= request.getContextPath() %>/ServletUsuarioController?acao=deletar&id=${jstl.id}">Excluir</a></td>
						  								</tr>				  							
						  							</c:forEach>
						  						</tbody>
											</table>																					
					 					 </div>		
					 					 
					 					 <!-- M�todo abaixo � para fazer a pagina��o de usu�rios da tela de cadastro -->
					 					 <nav aria-label="Page navigation example">
											  <ul class="pagination">
											  	  
											  	  <%
											  	  	int totalPagina = (int) request.getAttribute("totalPagina");
											  	  
											  	  	for (int p = 0; p < totalPagina; p++) {
											  	  		
											  	  		String url = request.getContextPath() + "/ServletUsuarioController?acao=paginar&pagina=" + (p * 5);
											  	  		
											  	  		out.println("<li class=\"page-item\"><a class=\"page-link\" href=\""+ url +"\">"+(p + 1)+"</a></li>");
											  	  		
											  	  	}											  	  
											  	  
											  	  %>												  
							 				  </ul>
										</nav>			 					 			 					
		                                                          
                                    </div>
                                    <!-- Page-body end -->
                                </div>
                                	<div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
   
<jsp:include page="java-script-file.jsp"></jsp:include>

			<!-- Modal -->
			<div class="modal fade" id="exampleModalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="exampleModalLabel">Pesquisa de usu�rios</h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
			      <div class="modal-body">
								       
			    	  <div class="input-group mb-3">
  						<input type="text" class="form-control" placeholder="Nome" aria-label="nome" id="nomeBusca" aria-describedby="basic-addon2">
  						<div class="input-group-append">  						
    						<button class="btn btn-outline-success" type="button" onclick="buscarUsuario();">Buscar</button>
  						</div>
			 		  </div>
			 		  
			 		  <div style="height:300px; overflow: scroll;">
						<table class="table" id="tabelaResultadoPesquisa">
						  <thead>
						    <tr>
						      <th scope="col">ID</th>
						      <th scope="col">Nome</th>
						      <th scope="col">E-mail</th>
						      <th scope="col">Ver</th>
						    </tr>
						  </thead>
						  <tbody>
						    
						  </tbody>
						</table>											
					 </div>	
					 
					 
					 <!-- M�todo para pagina��o utilizando Ajax - Tela pequena onde tem o bot�o Pesquisar -->
					 <nav aria-label="Page navigation example">
							<ul class="pagination" id="ulPaginacaoUserAjax"></ul>
							
							
					 </nav>										  
						<span id='totalResultados'></span>
			      </div>
			      
			      <div class="modal-footer">			     
			        <button type="button" class="btn btn-primary btn-round waves-effect waves-light" data-dismiss="modal">Fechar</button>			      		
			      </div>
			    </div>			    
			  </div>
			</div>
			
			
			
<script type="text/javascript">

//<!-- Criando fun��o para que o campo aceite somente N�meros -->

$("#numero").keypress(function (event){
	return /\d/.test(String.fromCharCode(event.keyCode));
	
});

$("#cep").keypress(function (event){
	return /\d/.test(String.fromCharCode(event.keyCode));
	
});



//<!-- Fun��o para que o campo rendaMensal aceite somente N�meros e o s�mbolo de R$ no campo de entrada-->
$("#rendaMensal").maskMoney({showSymbol:true, symbol:"R$ ", decimal:",", thousands:"."});



//Criando uma constante de formata��o monet�ria, em Java Script
const formatter = new Intl.NumberFormat('pt-BR', {
	currency : 'BRL',
	minimumFractionDigits : 2
});

$("#rendaMensal").val(formatter.format($("#rendaMensal").val()));

$("#rendaMensal").focus();



//M�todo para Data de Nascimento ser carregada na tela no formato ( dd / mm / yyyy)
var dataNascimento = $("#dataNascimento").val();

	if (dataNascimento != null && dataNascimento != '') {
		
		var dateFormat = new Date(dataNascimento);
		
		$("#dataNascimento").val(dateFormat.toLocaleDateString('pt-BR',{timeZone: 'UTC'}));
		
	}

$("#nome").focus();



//<!-- Criando fun��o com JQuery para o campo DataNascimento -->
$( function() {
	  
	  $("#dataNascimento").datepicker({
		    dateFormat: 'dd/mm/yy',
		    dayNames: ['Domingo','Segunda','Ter�a','Quarta','Quinta','Sexta','S�bado'],
		    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
		    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S�b','Dom'],
		    monthNames: ['Janeiro','Fevereiro','Mar�o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
		    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
		    nextText: 'Pr�ximo',
		    prevText: 'Anterior'
		});
} );





//<!-- Criando fun��o para pesquisar Cep -->
function pesquisarCep() {
	
	//Para a pesquisa de Cep primeiramente � feita a declara��o abaixo:
	var cep = $('#cep').val();
	 
	//Depois o c�digo fonte abaixo foi copiado do site viacep.com.br
	$.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {	
		
		 if (!("erro" in dados)) {
			 
			//Atualiza os campos com os valores da consulta.
			 $("#cep").val(dados.cep);
             $("#logradouro").val(dados.logradouro);
             $("#bairro").val(dados.bairro);
             $("#localidade").val(dados.localidade);
             $("#uf").val(dados.uf);
			 
		 }		
	 });	
 }




//<!-- Criando fun��o visualizarImg para carregar imagem do usu�rio na tela -->
function visualizarImg(fotoembase64, fileFoto) {
	
	//Campo da imagem que ser� mostrada - IMG do html
	var preview = document.getElementById(fotoembase64);	
	//Arquivo com a imagem que vai ser carregada para o usu�rio escolher
	var fileUser = document.getElementById(fileFoto).files[0];	
	//M�toda para se trabalhar com imagens na tela
	var reader = new FileReader();
	
	reader.onloadend = function() {
		preview.src = reader.result;  //Esse m�todo carrega a imagem na tela
	}; 
		if (fileUser) {
			reader.readAsDataURL(fileUser); // D� um preview na imagem na tela
			
		} else {
			preview.src = '';  //Se n�o houver imagem carrega vazio
		}
	
	}




//  <!-- Criando fun��o verEditar para carregar o usu�rio na tela usando Ajax -->
	function verEditar(id) {
		
		var urlAction = document.getElementById('formUser').action;
		
		window.location.href = urlAction + '?acao=buscarEditar&id='+id;
		
	}

	
	
//  <!-- Criando fun��o para buscar usu�rio, e montando pagina��o usando Ajax no bot�o Pesquisa 
	function buscaUserPaginacaoAjax(url) {
	
		var urlAction = document.getElementById('formUser').action;
		var nomeBusca = document.getElementById('nomeBusca').value;
		
		$.ajax({
			
			method: "get",
			url: urlAction,
			data: url,
			//data: "nomeBusca=" + nomeBusca + '&acao=buscarUserAjax',
			success: function (response, textStatus, xhr) {
				
			//Fun��o JQuery --> carrega os dados do usu�rio na tabela
			var json = JSON.parse(response);					
				
			//M�todo para n�o duplicar a pagina��o ap�s a exibi��o dos res. e nova busca	
			$('#tabelaResultadoPesquisa > tbody > tr').remove();
				
			//M�todo para n�o duplicar a pagina��o ap�s a exibi��o dos res. e nova busca (Bot�o pesquisar)
			$("#ulPaginacaoUserAjax > li").remove();
				
					for (var p = 0; p < json.length; p++) {
						$('#tabelaResultadoPesquisa > tbody').append('<tr><td>'+ json[p].id +'</td>   <td>' + json[p].nome + '</td>   <td>'+ json[p].email + '</td>   <td><button onclick="verEditar('+ json[p].id +')" type="button" class="btn btn-outline-info">Ver</button></td></tr>');
					}
						
						document.getElementById('totalResultados').textContent = 'Total de registros encontrados: ' + json.length;
						
						
					//M�todo para pagina��o onde tem o bot�o pesquisar
					//var totalPagina = new Number(xhr.getResponseHeader("totalPagina"));
					var totalPagina = xhr.getResponseHeader("totalPagina");
					
						for (var p = 0; p < totalPagina; p++) {
						
						var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina=' + (p * 5);
								
						$("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPaginacaoAjax(\''+ url +'\')">' + (p + 1) + '</a></li>');
					
				}			
						
			}				
											
		}).fail(function(xhr, status, errorThrown){
			alert('Erro ao pesquisar usu�rio por nome' + xhr.responseText);
		});
	}

	
	
	
	

//  <!-- Criando fun��o para buscar usu�rio usando Ajax -->
	function buscarUsuario() {
		
		var nomeBusca = document.getElementById('nomeBusca').value;
		
		if (nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != '') {
			
			/* Validando que tem que ter um valor para buscar no banco */
			var urlAction = document.getElementById('formUser').action;
			
			$.ajax({
				
				method: "get",
				url: urlAction,
				data: "nomeBusca=" + nomeBusca + '&acao=buscarUserAjax',				
				success: function (response, textStatus, xhr) {
					
				//Fun��o JQuery --> carrega os dados do usu�rio na tabela
				var json = JSON.parse(response);					
					
				//M�todo para n�o duplicar a pagina��o ap�s a exibi��o dos res. e nova busca	
				$('#tabelaResultadoPesquisa > tbody > tr').remove();
					
				//M�todo para n�o duplicar a pagina��o ap�s a exibi��o dos res. e nova busca (Bot�o pesquisar)
				$('#ulPaginacaoUserAjax > li').remove();
					
						for (var p = 0; p < json.length; p++) {
							$('#tabelaResultadoPesquisa > tbody').append('<tr><td>'+ json[p].id +'</td>   <td>' + json[p].nome + '</td>   <td>'+ json[p].email + '</td>   <td><button onclick="verEditar('+ json[p].id +')" type="button" class="btn btn-outline-info">Ver</button></td></tr>');
						}
							
							document.getElementById('totalResultados').textContent = 'Total de registros encontrados: ' + json.length;
							
							
						//M�todo para pagina��o onde tem o bot�o pesquisar
						//var totalPagina = new Number(xhr.getResponseHeader("totalPagina"));
						var totalPagina = xhr.getResponseHeader("totalPagina");
							
							for (var p = 0; p < totalPagina; p++) {
									
							var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina=' + (p * 5);
									
							$("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPaginacaoAjax(\''+url+'\')">'+ (p + 1) +'</a></li>');
							
					}			
							
				}				
												
			}).fail(function(xhr, status, errorThrown){
				alert('Erro ao pesquisar usu�rio por nome' + xhr.responseText);
			});			
		}		
	}
	
	
	
	
	

//  <!-- Criando fun��o Delete com Ajax -->
//  <!-- Usando o Ajax n�o existe redirecionamento de formul�rios -->
	function criarDeleteComAjax() {
		
		if (confirm('Deseja realmente excluir este usu�rio - Ajax ?')) {
						
			var urlAction = document.getElementById('formUser').action;
			var idUser = document.getElementById('id').value;
			
			$.ajax({
				
				method: "get",
				url: urlAction,
				data: "id=" + idUser + '&acao=deletarAjax',				
				success: function (response) {
					
					limparForm();
					document.getElementById('msg').textContent = response;
				
				}				
												
			}).fail(function(xhr, status, errorThrown){
				alert('Erro ao deletar usu�rio por ID' + xhr.responseText);
			}); 				
		}		
	}


	
	
	
//  <!-- Criando fun��o Delete -->	
//  <!-- Neste m�todo � necess�rio fazer o redirecionamento para um formul�rios -->
	function criarDelete() {
		
		if (confirm('Deseja realmente excluir este usu�rio ?')) {
			
			document.getElementById("formUser").method = 'get';
			document.getElementById("acao").value = 'deletar';
			document.getElementById("formUser").submit();	
			
		}
	}	
	

	function limparForm() {
		
		var elementos = document.getElementById("formUser").elements;
		
		for (p = 0; p < elementos.length; p ++) {
			elementos[p].value = '';
		}		
	}
	
	

</script>
 
</body>

</html>
    