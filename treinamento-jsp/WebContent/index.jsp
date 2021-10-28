<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
	
	<title>Treinamento - JSP</title>
	
	<style type="text/css">
		form {
			position: absolute;
			top: 40%;
			left: 33%;
			right: 33%;			
		}
		
		h3 {
			position: absolute;
			top: 30%;
			left: 33%;					
		}	
		
		h5 {
			position: absolute;
			top: 10%;
			left: 33%;
			right: 33%;		
			font-size: 20px;
			color: red;
		}	
	
	</style>	
	
</head>
<body>

	<h3>Bem Vindo ao Treinamento - JSP</h3>	
	
<form action="<%=request.getContextPath() %>/ServletLogin" method="post" class="row g-3 needs-validation" novalidate>	

<input type="hidden" value="<%= request.getParameter("url") %>" name="url">

	 <div class="mb-3">	
		<label class="form-label">Login: </label>
		<input class="form-control" name="login" type="text" required="required">
		<div class="invalid-feedback"> Campo obrigatório </div>
		<div class="valid-feedback"> Ok </div>	
	</div>
	
	<div class="mb-3">	
		<label class="form-label">Senha: </label>			
		<input class="form-control" name="senha" type="password" required="required">	
		<div class="invalid-feedback"> Campo obrigatório </div>
		<div class="valid-feedback"> Ok </div>
	</div>		
	
	<div>
		<input type="submit" value="Acessar" class="btn btn-primary">
	</div>
			
	

</form>

<h5>${msg}</h5>

	<!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>

	<script type="text/javascript">
	
	// Example starter JavaScript for disabling form submissions if there are invalid fields
	(function () {
	  'use strict'

	  // Fetch all the forms we want to apply custom Bootstrap validation styles to
	  var forms = document.querySelectorAll('.needs-validation')

	  // Loop over them and prevent submission
	  Array.prototype.slice.call(forms)
	    .forEach(function (form) {
	      form.addEventListener('submit', function (event) {
	        if (!form.checkValidity()) {
	          event.preventDefault()
	          event.stopPropagation()
	        }

	        form.classList.add('was-validated')
	      }, false)
	    })
	})()
	
	</script>

</body>
</html>