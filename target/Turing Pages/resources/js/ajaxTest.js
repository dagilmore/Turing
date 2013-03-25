$(document).ready(function(){

  $('#matrixForm').submit(function(e) {
    e.preventDefault();

        var matrixArray = [ 
            [1,2,3],
            [3,4,5],
            [5,6,7]
        ];

    // var matrixArray = {"test" : "this is a test"};

    // var temp = JSON.stringify(matrixArray);
    // console.log($.parseJSON(temp));
    $.ajaxSetup({
        contentType: "application/json; charset=utf-8"
    });
    $.ajax({  
        type: "POST",  
        data: JSON.stringify(matrixArray),  
        url: "/turingpages/matrix/create",
        success: function(data) {

        }  
    });   

    }); 
});