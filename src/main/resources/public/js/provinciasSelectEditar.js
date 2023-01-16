document.getElementById("provinciaSel").addEventListener('change', (event) => {
    var municipioSelect = document.getElementById('muniSel');
    var provSelect = document.getElementById("provinciaSel").selectedIndex;

    let options = municipioSelect.getElementsByTagName('option');

    for (var i=options.length; i--;) {
        municipioSelect.removeChild(options[i]);
    }

    $.ajax({
        url: "/api/provincias/" + provSelect,
        type: "get", //send it through get method
        dataType : "json",
        success : function(resultado){
                        for(r of resultado){
                            var myOption = document.createElement("option");
                            myOption.text = r;
                            myOption.value = r;
                            municipioSelect.add(myOption);
                        }
                  }
    });
})


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

function quitarSector(idSector){
    document.getElementById("idSector").value = idSector;
    document.getElementById("modalAceptar").style.display = 'block';
}

function eliminarSector(){
    var idSector = document.getElementById("idSector").value;
    $.ajax({
        type: "POST",
        url: "editar/"+idSector,
        success: function(result){
            location.reload(true);
        }
    });
}

function cerrarModal(){
    document.getElementsByClassName("modal")[0].style.display = 'none';
}

function confirmarSector(){
    var nombreSector = document.getElementById("inputSector").value;
    $.ajax({
        type: "POST",
        url: "editar/"+nombreSector,
        success: function(result){
            location.reload(true);
        }
    });
}

function agregarSector(){
    document.getElementById("modalAgregar").style.display = 'block';
}