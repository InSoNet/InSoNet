var numCol=0;
var maxNumCol=1;
function isKeyPressed(event) {
    if(event.shiftKey) {
        switch(event.keyCode) {
            case 13:
                $('#col'+numCol).focus();
                event.preventDefault();
                numCol+=1;
                if(numCol>maxNumCol) {
                    numCol=0;
                }                        
                break;
        }
        //alert("The shift key was pressed!"+event.keyCode+numCol);
    }    
}

function validatePass(p1, p2) {
    if (p1.value != p2.value || p1.value == '' || p2.value == '') {
        p2.setCustomValidity('Contraseña incorrecta');
    } else {
        p2.setCustomValidity('');
    }
}

function sendEmailConfirm() {
	$.ajax({
		type: "GET",
		url: "http://localhost:8080/InSoNet/profile/sendEmailConfirm"
		}).done(function( result ) {
			if (result == "ok") {
	            $('#result').addClass("alert alert-success")
	            $('#result').html("Se ha vuelto a enviar el correo de confirmación.");
	        } else {
	        	$('#result').addClass("alert alert-success")
	        	$('#result').html("No se pudo volver a enviar el correo de confirmación.");
	        }
		}); 
	
} 