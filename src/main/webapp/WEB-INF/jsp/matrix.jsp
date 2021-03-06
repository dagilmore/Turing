
<!DOCTYPE html>

<html lang="en">
<head>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:url value="/matrix/records" var="recordsUrl"/>
<c:url value="/matrix/create" var="addUrl"/>
<c:url value="/matrix/update" var="editUrl"/>
<c:url value="/matrix/delete" var="deleteUrl"/>

<script type='text/javascript' src='<c:url value="/resources/js/customMatrix.js"/>'></script>

	<title>Matrix Factorization</title>
	
	<script type='text/javascript'>
	$(function() {
		// init
		urlHolder.records = '${recordsUrl}';
		urlHolder.add = '${addUrl}';
		urlHolder.edit = '${editUrl}';
		urlHolder.del = '${deleteUrl}';
		loadTable();
		
		$('#newBtn').click(function() { 
			// toggleForms('new');
			$('#createNewTitle').show();
	 		$('#dimForm').show();
			toggleCrudButtons('hide');
		});
		
		$('#editBtn').click(function() { 
			if (hasSelected()) {
				toggleForms('edit');
				toggleCrudButtons('hide');
				fillEditForm();
			}
		});
		
		$('#reloadBtn').click(function() { 
			loadTable();
		});

		$('#deleteBtn').click(function() {
			if (hasSelected()) { 
				submitDeleteRecord();
			}
		});
		
		$('#newForm').submit(function(e) {
			e.preventDefault();
			submitNewRecord();
		});
		
		$('#editForm').submit(function(e) {
			e.preventDefault();
			submitUpdateRecord();
		});

		$('#closeNewForm').click(function() { 
			toggleForms('hide'); 
			toggleCrudButtons('show');
		});
		
		$('#closeEditForm').click(function() { 
			toggleForms('hide'); 
			toggleCrudButtons('show');
		});

		$('#createDims').click(function() { 
			$('#dimForm').hide();
	 		$('#matrixForm').html(matrixForm($('#xDim').val(),$('#yDim').val())); 
		});

		$('#back2Matrix').live("click",function() { 
			$('#matrixForm').hide();
	 		$('#dimForm').show(); 
		});

		$('#matrixMeta').live("click",function() { 
			$('#matrixForm').hide();
	 		$('#newForm').show(); 
		});

	});
	</script>
</head>

<body>
	<div class="container">
	<h1 id='banner'>Matrix Factorization</h1>
	<hr/>
	
	<table id='tableMatrix'>
		<caption></caption>
		<thead>
			<tr>
				<!-- <th></th> -->
				<th>Matrix</th>
				<th>Knowledge Base</th>
				<th>Inferred</th>
				<th>Steps</th>
				<th>Alpha</th>
				<th>Beta</th>
				<th>K</th>
			</tr>
		</thead>
	</table>
	
	<div id='controlBar'>
		<input type='button' value='New' id='newBtn' />
<!-- 		<input type='button' value='Delete' id='deleteBtn' />
		<input type='button' value='Edit' id='editBtn' />
		<input type='button' value='Reload' id='reloadBtn' /> -->
	</div>
	<div id="createNewTitle">
		<legend>Create New Matrix</legend>
	</div>
	<div id='dimForm'>
		<form>
  			<fieldset>
				<label for='xDim'>X Dim</label><input type='number' id='xDim'/><br/>
				<label for='yDim'>Y Dim</label><input type='number' id='yDim'/><br/>
  			</fieldset>
			<input type='button' value='Next' id='createDims'/>
		</form>
	</div>
	<div id="matrixForm">
	</div>
	<div id='newForm'>
		<form>
  			<fieldset>
				<label for='newMatrix'>Name</label><input type='text' id='newMatrix'/><br/>
				<label for='newSteps'>Steps</label><input type='number' id='newSteps'/><br/>
				<label for='newAlpha'>Alpha</label><input type='number' id='newAlpha'/><br/>
				<label for='newBeta'>Beta</label><input type='number' id='newBeta'/><br/>
				<label for='newK'>K</label><input type='number' id='newK'/><br/>
  			</fieldset>
			<input type='button' value='Close' id='closeNewForm' />
			<input type='submit' value='Submit'/>
		</form>
	</div>
	
	<div id='editForm'>
		<form>
  			<fieldset>
				<legend>Edit Record</legend>
				<input type='hidden' id='editMatrix'/>
				<label for='editAlpha'>Alpha</label><input type='text' id='editAlpha'/><br/>
				<label for='editBeta'>Beta</label><input type='text' id='editBeta'/><br/>
				<label for='editK'>K</label><input type='text' id='editK'/><br/>
			</fieldset>
			<input type='button' value='Close' id='closeEditForm' />
			<input type='submit' value='Submit'/>
		</form>
	</div>
	</div>
</body>
</html>