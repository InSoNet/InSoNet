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
        p2.setCustomValidity('Password incorrect');
    } else {
        p2.setCustomValidity('');
    }
}