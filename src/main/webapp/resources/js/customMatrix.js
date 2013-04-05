/**
 * Contains custom JavaScript code
 */
var urlHolder = new Object();

function loadTable() {
	
	toggleForms('hide');

	$.get(urlHolder.records, function(response) {

 		$('#tableMatrix').find('tbody').remove();
 		
 		for (var i=0; i<response.matrices.length; i++) {
			var row = '<tr>';
			// row += '<td><input type="radio" name="index" id="index" value="'+i+'"></td>';
			row += '<td>' + response.matrices[i].id + '</td>';
			row += '<td>' + matrixPrettyPrint(response.matrices[i].knowledgeBase) + '</div></td>';
			row += '<td>' + matrixPrettyPrint(response.matrices[i].inferred) + '</td>';
			row += '<td>' + response.matrices[i].steps + '</td>';
			row += '<td>' + response.matrices[i].alpha + '</td>';
			row += '<td>' + response.matrices[i].beta + '</td>';
			row += '<td>' + response.matrices[i].k + '</td>';
			row += '</tr>';
	 		$('#tableMatrix').append(row);
 		}
 		
 		$('#tableMatrix').data('model', response.matrices);;
 	});
}

function submitNewRecord() {
	// var kb = matrixRequest();
	// console.log(kb);
	$.post(urlHolder.add, {
			name: $('#newMatrix').val(),
			// knowledgeBase: kb,
			steps: $('#newSteps').val(),
			alpha: $('#newAlpha').val(),
			beta: $('#newBeta').val(),
			k: $('#newK').val()
		}, 
		function(response) {
			if (response != null) {
				loadTable();
				toggleForms('hide'); ;
				toggleCrudButtons('show');
				alert('Success! Matrix has been added.');
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);	
}

function submitDeleteRecord() {
	var selected = $('input:radio[name=index]:checked').val();
	
	$.post(urlHolder.del, {
			matrix: $('#tableMatrix').data('model')[selected].matrix
		}, 
		function(response) {
			if (response == true) {
				loadTable(urlHolder.records);
				alert('Success! Record has been deleted.');
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);
}

function submitUpdateRecord() {
	$.post(urlHolder.edit, {
			matrix: $('#editMatrix').val(),
			alpha: $('#editAlpha').val(),
			beta: $('#editBeta').val(),
			k: $('#editK').val()
		}, 
		function(response) {
			if (response != null) {
				loadTable();
				toggleForms('hide'); ;
				toggleCrudButtons('show');
				alert('Success! Record has been edited.');
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);
}

function hasSelected() {
	var selected = $('input:radio[name=index]:checked').val();
	if (selected == undefined) { 
		alert('Select a record first!');
		return false;
	}
	
	return true;
}

function fillEditForm() {
	var selected = $('input:radio[name=index]:checked').val();
	$('#editMatrix').val( $('#tableMatrix').data('model')[selected].matrix );
	$('#editAlpha').val( $('#tableMatrix').data('model')[selected].alpha );
	$('#editBeta').val( $('#tableMatrix').data('model')[selected].beta );
	$('#editK').val( $('#tableMatrix').data('model')[selected].k.k );
}

function resetNewForm() {
	$('#newMatrix').val('');
	$('#newSteps').val('');
	$('#newAlpha').val('');
	$('#newBeta').val('');
	$('#newK').val('2');
}

function resetEditForm() {
	$('#editAlpha').val('');
	$('#editBeta').val('');
	$('#editK').val('2');
}

function toggleForms(id) {
	if (id == 'hide') {
		$('#createNewTitle').hide();
		$('#dimForm').hide();
		$('#newForm').hide();
		$('#editForm').hide();
		
	} else if (id == 'new') {
 		resetNewForm();
 		$('#newForm').show();
 		$('#editForm').hide();
 		
	} else if (id == 'edit') {
 		resetEditForm();
 		$('#newForm').hide();
 		$('#editForm').show();
	}
}

function toggleCrudButtons(id) {
	if (id == 'show') {
		$('#newBtn').removeAttr('disabled');
		$('#editBtn').removeAttr('disabled');
		$('#deleteBtn').removeAttr('disabled');
		$('#reloadBtn').removeAttr('disabled');
		
	} else if (id == 'hide'){
		$('#newBtn').attr('disabled', 'disabled');
		$('#editBtn').attr('disabled', 'disabled');
		$('#deleteBtn').attr('disabled', 'disabled');
		$('#reloadBtn').attr('disabled', 'disabled');
	}
}

function matrixForm(x,y) {
	var ret = "<table class='matrixInput'>";
	for (var i=0; i<y; i++) {
		var row = "<tr class='matrixInput'>";
		for (var j=0;j<x;j++) {
			row += "<td class='matrixInput'><input class='matrixInput' type='number' id='"
			row += i + '_' + j;
			row += "'/><br/></td>"
		}
	ret += row;
	}
	ret += "</table><input type='button' value='Back' id='back2Matrix'/>";
	ret += "<input type='button' value='Next' id='matrixMeta'/></form>";

	return ret;
}

function matrixPrettyPrint(matrix) {

	var ret = "<table>";
	for (var i=0; i<matrix.length; i++) {
		var row = '<tr>';
		for (var j=0;j<matrix[0].length;j++) {
			row += '<td>' + matrix[i][j].toFixed(1) + '</td>';
		}
	row += '</tr>';
	ret += row;
	}
	ret += '</table>';
	return ret;

}

function matrixRequest() {
	var ret = [];
	var tmp = "";
	console.log($('#xDim').val());
	for (var i=0; i < $('#xDim').val(); i++) {
		var row = [];
		for (var j=0;j < $('#yDim').val();j++) {
			tmp = '#' + i + '_' + j;
			row.push(Number($(tmp).val()));
		}
		ret.push(row);
	}
	return JSON.stringify(ret);

}