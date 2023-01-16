$("#agregar").click(function(){

    var elemento = '<div><input class = "text_input_reg" type="text" name="sector" value="" placeholder="AREA"></div>';

    $("#sector").append(elemento);

});

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
                                document.getElementById("registrarBtn").disabled = true;
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

