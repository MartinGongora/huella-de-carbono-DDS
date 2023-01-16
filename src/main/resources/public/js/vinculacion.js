/*
function aceptarSolicitud(idSolicitud, idOrganizacion){
    $.ajax({
        url: "/organizaciones/"+ idOrganizacion + "/solicitudes/" + idSolicitud + "/aceptado",
        type: "get"
    });
}*/

function includeHTML() {
    var z, i, elmnt, file, xhttp;
    /* Loop through a collection of all HTML elements: */
    z = document.getElementsByTagName("*");
    for (i = 0; i < z.length; i++) {
        elmnt = z[i];
        /*search for elements with a certain atrribute:*/
        file = elmnt.getAttribute("w3-include-html");
        if (file) {
            /* Make an HTTP request using the attribute value as the file name: */
            xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4) {
                    if (this.status == 200) {elmnt.innerHTML = this.responseText;}
                    if (this.status == 404) {elmnt.innerHTML = "Page not found.";}
                    /* Remove the attribute, and call this function once more: */
                    elmnt.removeAttribute("w3-include-html");
                    includeHTML();
                }
            }
            xhttp.open("GET", file, true);
            xhttp.send();
            /* Exit the function: */
            return;
        }
    }
}

$(document).ready(function(){
    $("#inputUsuario").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#tabla tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});

function confirmarVinculacion(idSolicitud){
    document.getElementById("idSolicitud").value = idSolicitud;
    document.getElementById("modalAceptar").style.display = 'block';
}

function aceptarVinculacion(){
    var idSolicitud = document.getElementById("idSolicitud").value;
    $.ajax({
        type: "POST",
        url: "solicitudes/"+idSolicitud+"/aceptada",
        success: function(result){
            location.reload(true);
        }
    });
}

function cerrarModal(){
    document.getElementsByClassName("modal")[0].style.display = 'none';
}

function rechazarVinculacion(){
    var idSolicitud = document.getElementById("idSolicitud").value;
    $.ajax({
        type: "POST",
        url: "solicitudes/"+idSolicitud+"/rechazada",
        success: function(result){
            location.reload(true);
        }
    });
}

function confirmarRechazoVinculacion(idSolicitud){
    document.getElementById("idSolicitud").value = idSolicitud;
    document.getElementById("modalRechazar").style.display = 'block';
}