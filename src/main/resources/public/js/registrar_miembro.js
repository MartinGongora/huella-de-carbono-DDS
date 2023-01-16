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

function verificarUsuario(){
    var usuario = document.getElementById('usuario').value;
    var usuarioNoExiste = true;

    $.ajax({
        url: "/api/usuarios",
        type: "get",
        dataType : "json",
        success : function(resultado){
                        for(r of resultado){
                            if (r === usuario){
                                document.getElementById("modalUsuario").style.display = 'block';
                                document.getElementById('usuario').value = "";
                                usuarioNoExiste = false;
                                break;
                            }
                        }
                  }
    })


}

function cerrarModal(){
    document.getElementsByClassName("modal")[0].style.display = 'none';
}

