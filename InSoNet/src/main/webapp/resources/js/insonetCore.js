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

function closeColumn(idCol) {
	var auxReal = idCol.split("-");
	var idColReal = auxReal[0] + "-" + auxReal[1]
	$.ajax({
		type: "GET",
		url: "http://localhost:8080/InSoNet/hidecol/" + auxReal[1],
		success: function (result) {
			if (result == "ok") {
				$("#" + idColReal).hide();
	        }
	    },
	    error: function (datosError) {
	        console.log(datosError.responseText);
	    }	
	});	
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
	            $('#result').addClass("alert alert-success");
	            $('#result').html("Se ha vuelto a enviar el correo de confirmación.");
	        } else {
	        	$('#result').addClass("alert alert-success");
	        	$('#result').html("No se pudo volver a enviar el correo de confirmación.");
	        }
		}); 
	
}

function validForm(nameform) {
	var form = $( nameform );
	form.validate();
	//alert( "Valid: " + form.valid() );
	var isValid = form.valid();
	if(isValid) {
		return { result : isValid, message : "Exito" };
	} else {
		return { result : isValid, message : "Debe escribir algo." };
	}
	
}

function addPost(net, text) {
	var url = "http://localhost:8080/InSoNet/" + net + "/post/add";
	var response;
	$.ajax({
		type: "GET",
		url: url,
		data: "messageTxt="+text,
		async: false,
		success: function (result) {
            response = result;
        },
        error: function (datosError) {
            console.log(datosError.responseText);
        }
	});
	return response;
}

function publishing() {
	var forNet = "";
	var escaped = escape($('#messageTxt').val());
	var response = false;
	var noticeAlert = "No se pudo publicar su mensaje en ";
	if ($("input[name='publishingIn']")[0].checked) {
		var aux = addPost("facebook", escaped);
		if (aux == "ok") {
			forNet = forNet + "Facebook";
			response = true;
			noticeAlert = "Mensaje publicado en ";
		}		 
	}
	if ($("input[name='publishingIn']")[1].checked) {
		forNet = forNet + "Twitter"; 
	}
	if ($("input[name='publishingIn']")[2].checked || ($("input[name='publishingIn']")[0].checked && $("input[name='publishingIn']")[1].checked)) {
		forNet = "Facebook y Twitter"; 
	}
	
	return { result : response, message : noticeAlert + forNet };
}

function deleteFBCookie() {
	   var name="c_user";
	   var domain=".facebook.com";
	   var path="/";
	   var d = new Date();
	   document.cookie = name + "=" + ( ( path ) ? ";path=" + path : "") + ( ( domain ) ? ";domain=" + domain : "" ) + ";expires=" + d.toGMTString();
}

$(document).ready(function() {
	var hiddenBox = $('#noticeMessage');
	var audio = $('#noticeAudio');
	$('#publishingButton').on( "click", function( event ) {
		validFormJson = validForm("#formMessage");
		publishingJson = {};
		if(validFormJson.result === true) {
			publishingJson = publishing();
			
		} else {
			hiddenBox.addClass("alert alert-success");
			//hiddenBox.show();
			hiddenBox.html(validFormJson.message);
		}
		
		if(publishingJson.result === true) {
			hiddenBox.addClass("alert alert-success");
			//hiddenBox.show();
			hiddenBox.html(publishingJson.message);
		} else {
			//audio.attr("src", "/InSoNet/resources/audio/no-se-pudo-publicar-mensaje.wma");
			document.getElementById("noticeAudio").load();
			document.getElementById("noticeAudio").play();
			//audio.load();
			//audio.play;
		}
		
			
	});
	
	
});

$(document).ready(function(){
	var searchForm = $('#searchForm');
	var alertSearch = $('#alertSearch');
	$('#searchButton').on("click", function(event){
		validFormJson = validForm("#searchForm");
		publishingJson = {};
		if(validFormJson.result === true) {
			searchForm.submit();			
		} else {
			alertSearch.addClass("alert alert-success");
			alertSearch.html(validFormJson.message);
		}
		
		if(publishingJson.result === true) {
			alertSearch.addClass("alert alert-success");
			alertSearch.html(publishingJson.message);
		} else {
			document.getElementById("noticeAudio").load();
			document.getElementById("noticeAudio").play();
		}
	});	
	
});

$(document).ready(function(){
	$("[id^='addFriendButton']").on("click", function(event){
		var userId = this.id.split("-");
		var url = "http://localhost:8080/InSoNet/friend/facebook/add/" + userId[1];
		var response;
		$.ajax({
			type: "GET",
			url: url,
			async: false,
			success: function(result) {
	            response = result;
	        },
	        error: function (datosError) {
	            console.log(datosError.responseText);
	        }
		});
		
		if(response == "ok") {
			$("#" + this.id).html("Es tu Amigo");
			$("#" + this.id).addClass("disabled");
		}
	});	
	
});

