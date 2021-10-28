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
														<h4 class="sub-title">Relat�rio de usu�rios cadastrados no Sistema</h4>
															

														<form
															action="<%=request.getContextPath()%>/ServletUsuarioController" method="get" id="formUser">
																

															<!-- O m�todo abaixo est� sendo passado por input pois n�o est� dando certo com a fun��o action -->
															<!-- Por isso est� sendo passado de dentro do Formul�rio -->
															<input type="hidden" id="acaoImprimirRelatorioTipo" name="acao" value="imprimirRelatorioUser">

															<div class="form-row align-items-center">
																<div class="col-sm-3 my-1">
																	<label class="sr-only" for="dataInicial">Data
																		Inicial</label> <input value="${dataInicial}" type="text"
																		class="form-control mb-2" id="dataInicial"
																		name="dataInicial">
																</div>

																<div class="col-sm-3 my-1">
																	<label class="sr-only" for="dataFinal">Data Final</label>
																	<div class="input-group mb-2">
																		<input value="${dataFinal}" type="text"class="form-control" id="dataFinal" name="dataFinal">
																	</div>
																</div>

																<div class="col-auto">
																	<button type="button" onclick="imprimirHtml();" class="btn btn-primary mb-2">Imprimir Relat�rio</button>
																	<button type="button" onclick="imprimirPdf();" class="btn btn-primary mb-2">Imprimir PDF</button>
																</div>
																
																
																
															</div>

														</form>

														<div style="height: 300px; overflow: scroll;">
															<table class="table" id="tabelaResultadoPesquisaView">
																<thead>
																	<tr>
																		<th scope="col">ID</th>
																		<th scope="col">Nome</th>																		
																	</tr>
																</thead>
																<tbody>
																	<!-- M�todo que carrega o ID e NOME do usu�rio -->
																	<c:forEach items="${listarUser}" var="jstl">
																		<tr>
																			<td><c:out value="${jstl.id}"></c:out></td>
																			<td><c:out value="${jstl.nome}"></c:out></td>
																		</tr>
																		
																		<!-- M�todo carrega o ID - NOME - TELEFONE do usu�rio -->
																		<c:forEach items="${jstl.telefones}" var="fone">
																			<tr>
																				<td/>
																					<td style="font-size: 11px">
																						<c:out value="${fone.numero}"></c:out>
																					</td>
																			</tr>
																		</c:forEach>
																	</c:forEach>
																</tbody>
															</table>
														</div>

													</div>
												</div>
											</div>
										</div>

									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="java-script-file.jsp"></jsp:include>


	<script type="text/javascript">
	
	
//  <!-- Fun��o para imprimir relat�rio na tela -->		
function imprimirHtml() {
	document.getElementById("acaoImprimirRelatorioTipo").value = 'imprimirRelatorioUser';
	$("#formUser").submit();
	
}

//<!-- Fun��o para imprimir relat�rio em PDF -->
function imprimirPdf() {
	document.getElementById("acaoImprimirRelatorioTipo").value = 'imprimirRelatorioPDF';
	$("#formUser").submit();
	return false
	
}

	
	
$(function() {

$("#dataInicial").datepicker(
			
					{
						dateFormat : 'dd/mm/yy',
						dayNames : [ 'Domingo', 'Segunda', 'Ter�a',
								'Quarta', 'Quinta', 'Sexta', 'S�bado' ],
						dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S',
								'S', 'D' ],
						dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua',
								'Qui', 'Sex', 'S�b', 'Dom' ],
						monthNames : [ 'Janeiro', 'Fevereiro', 'Mar�o',
								'Abril', 'Maio', 'Junho', 'Julho',
								'Agosto', 'Setembro', 'Outubro',
								'Novembro', 'Dezembro' ],
						monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr',
								'Mai', 'Jun', 'Jul', 'Ago', 'Set',
								'Out', 'Nov', 'Dez' ],
						nextText : 'Pr�ximo',
						prevText : 'Anterior'
					});
});


//<!-- Criando fun��o com JQuery para o campo  dataFinal do relat�rio de Usu�rios -->
$(function() {

	$("#dataFinal")
			.datepicker(
					{
						dateFormat : 'dd/mm/yy',
						dayNames : [ 'Domingo', 'Segunda', 'Ter�a',
								'Quarta', 'Quinta', 'Sexta', 'S�bado' ],
						dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S',
								'S', 'D' ],
						dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua',
								'Qui', 'Sex', 'S�b', 'Dom' ],
						monthNames : [ 'Janeiro', 'Fevereiro', 'Mar�o',
								'Abril', 'Maio', 'Junho', 'Julho',
								'Agosto', 'Setembro', 'Outubro',
								'Novembro', 'Dezembro' ],
						monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr',
								'Mai', 'Jun', 'Jul', 'Ago', 'Set',
								'Out', 'Nov', 'Dez' ],
						nextText : 'Pr�ximo',
						prevText : 'Anterior'
					});
});
</script>


</body>

</html>
