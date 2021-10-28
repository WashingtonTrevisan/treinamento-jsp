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
														<h4 class="sub-title">Gr�fico de Sal�rios</h4>
															

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
																
																<!-- Fun��o Java Script para gerar o Gr�fico -->
																<div class="col-auto">
																	<button type="button" onclick="gerarGrafico();" class="btn btn-primary mb-2">Gerar Gr�fico</button>
																</div>
																
																
																
															</div>

														</form>

														<div style="height: 500px; overflow: scroll;">
															<div>
															  <canvas id="myChart"></canvas>
															</div>														
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
	
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

	<script type="text/javascript">
	
	
//Criando uma vari�vel global para criar o gr�fico:	
var myChart = new Chart(document.getElementById('myChart'));	
	

//<!-- Fun��o JS para gerar gr�fico de sal�rios na tela -->	
//<!-- Criando fun��o Gerar Gr�ficos com Ajax -->
//<!-- Obs: usando o Ajax n�o existe redirecionamento para p�ginas e formul�rios -->	
function gerarGrafico() {	

var urlAction = document.getElementById('formUser').action;
var dataInicial = document.getElementById('dataInicial').value;
var dataFinal = document.getElementById('dataFinal').value;
	
	$.ajax({
		
		method: "get",
		url: urlAction,
		data: "dataInicial=" + dataInicial + '&dataFinal=' + dataFinal + '&acao=graficoSalario',				
		success: function (response) {
			
		var json = JSON.parse(response);
		
		myChart.destroy();
			
		myChart = new Chart(
			   document.getElementById('myChart'),
			  	 {
				  type: 'line',
				  data: {
				  labels: json.perfils,
					
				  datasets: [{
				   	label: 'Gr�fico de m�dia salarial por cargo',
				   	backgroundColor: 'rgb(255, 99, 132)',
				   	borderColor: 'rgb(255, 99, 132)',
				   	data: json.salarios,
						}]
					},
			  options: {}
			}
		 );			
	}				
										
	}).fail(function(xhr, status, errorThrown){
		alert('Erro ao buscar dados para gerar o gr�fico' + xhr.responseText);
	}); 			
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
