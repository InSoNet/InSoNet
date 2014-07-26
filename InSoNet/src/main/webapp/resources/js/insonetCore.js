var files;
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

function getURL() {
	var path = location.pathname;
	var protocol = location.protocol;
	var hostname = location.host;
	var url = "";
	
	url = protocol + "//" + hostname;
	var pos = path.indexOf("InSoNet");
	if(pos !== -1) {
		url = url + "/InSoNet"; 
	} 
	
	return url;
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

function addComment(net, text, post) {
	//var url = "http://localhost:8080/InSoNet/" + net + "/comment/add";
	var url = getURL() + "/facebook/" + net + "/comment/add";  
	var response;
	
	$.ajax({
		type: "GET",
		url: url,
		data: "m=" + text + "&p=" + post,
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

function uploadFiles(){
	//event.stopPropagation();
	//event.preventDefault();
	var aux;
	var data = new FormData();
	if(files !== undefined) {
	$.each(files, function(key, value)	{
		data.append("filePhoto", value);//si queremos mas de una foto reemplazar "filePhoto" por key
	});
	
	
	$.ajax({
		url: getURL() + '/facebook/post/file',
		type: 'POST',
		data: data,
		async: false,
		cache: false,
		dataType: 'json',
		processData: false, // Don't process the files
		contentType: false, // Set content type to false as jQuery will tell the server its a query string request
		success: function(data, textStatus, jqXHR) {
			if(data.result == "ok") {
				aux = postForm(data);
			} else {
				console.log('ERRORS: ' + data);
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			
			console.log('ERRORS: ' + textStatus);
			
		}
	});
	} else {
		var j = { result : "ok", files : "" };
		aux = postForm(j);
	}
	
	return aux;
}

function postForm(data) {
	var url = getURL() + "/facebook/post/submit";
	var formData = $("#formMessage").serialize();
	//Para varias fotos
	/*$.each(data.files, function(key, value)	{
		formData = formData + '&fileName[]=' + value;
	});*/
	formData = formData + '&fileName=' + data.files;
	var response;
	$.ajax({
		type: "POST",
		url: url,
		data: formData,
		cache: false,
		async: false,
		dataType: 'json',
		success: function (r) {
            response = r.result;
        },
        error: function (datosError) {
            console.log(datosError.responseText);
        }
	});
	return response;
	//$("#formMessage").submit();
}

function publishing() {
	var forNet = "";
	var escaped = escape($('#messageTxt').val());
	var response = false;
	var aux;
	var data;
	var noticeAlert = "No se pudo publicar su mensaje en ";
	if ($("input[name='publishingIn']")[0].checked) {
		//var aux = addPost("facebook", escaped);
		//if(files != null) {
		aux = uploadFiles();
		/*} else {
			data = $("#formMessage").serialize();
			aux = postForm(data);
		}*/
				
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

$(document).ready(function() {
	
	
	 
	$('input[type=file]').on('change', prepareUpload);
	 
	function prepareUpload(event)
	{
		files = event.target.files;
	}
	
	var hiddenBox = $('#noticeMessage');
	var audio = $('#noticeAudio');
	if(window.FormData !== undefined) {
		$('#publishingButton').on( "click", function( event ) {
			
			validFormJson = validForm("#formMessage");
			publishingJson = {};
				
				
				
				if(validFormJson.result === true) {
					
						event.stopPropagation(); // Stop stuff happening
						event.preventDefault(); // Totally stop stuff happening
						publishingJson = publishing();
					
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
								
				} else {
					hiddenBox.addClass("alert alert-success");
					//hiddenBox.show();
					hiddenBox.html(validFormJson.message);
				}
			
		});
	} 
	
	/*$('#formMessage').on( "submit", function( event ) {
		event.stopPropagation(); // Stop stuff happening
		event.preventDefault(); // Totally stop stuff happening
		validFormJson = validForm("#formMessage");
		publishingJson = {};
		if(validFormJson.result === true) {
			publishingJson = publishing(event);
			
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
		
			
	});*/
	
	
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
	
	$("[id^='comment-']").keypress(function(event){
		if(event.keyCode == 13) {
			var commentsId = this.id.split("-");
			var netId = $("#commentHidden-" + commentsId[1]).val();
		    var formId = "#commentForm-" + commentsId[1];
		    validFormJson = validForm(formId);
		    if(validFormJson.result === true){
		    	response = addComment(netId, this.value, commentsId[1]);
		    	if(response == "ok") {
		    		alert("Comentario enviado con exito");
		    	}
		    } else {
		    	alert("Escriba algo");
		    }
		}
				
	});
	
	$('#adjuntar').on("click", function(event){
		$('#filePhoto').click();
	});
});



