document.getElementById("provinciaOrigenSel").addEventListener('change', (event) => {
    var municipioSelect = document.getElementById('muniOrigenSel');
    var provSelect = document.getElementById("provinciaOrigenSel").selectedIndex;

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


document.getElementById("provinciaDestinoSel").addEventListener('change', (event) => {
    var municipioSelect = document.getElementById('muniDestinoSel');
    var provSelect = document.getElementById("provinciaDestinoSel").selectedIndex;

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

document.getElementById("medioOrigen").addEventListener('change', (event) => {
    var select = document.getElementById("medioOrigen").selectedIndex;
    var selectTipo = document.getElementById("medioOrigenSel").selectedIndex;

    if (select == 0){
        if (selectTipo == 0){
            document.getElementById("direccionOrigen").disabled = false;
            document.getElementById("alturaOrigen").disabled = false;
            document.getElementById("provinciaOrigenSel").disabled = false;
            document.getElementById("muniOrigenSel").disabled = false;
            document.getElementById("vehiculoSelect_1").disabled = true;
            document.getElementById("vehiculoTipoSelect_1").disabled = true;
            document.getElementById("servicioSelect_1").disabled = true;
            document.getElementById("transporteSelect_1").disabled = true;
            document.getElementById("lineaSelect_1").disabled = true;
            document.getElementById("estacionSelect_1").disabled = true;
            document.getElementById("medioDestino").disabled = false;
            document.getElementById("medioDestino").selectedIndex = 0;
            document.getElementById("medioDestino").dispatchEvent(new Event('change'));
            document.getElementById("medioOrigenSel").disabled = false;
        } else {
            document.getElementById("direccionOrigen").disabled = true;
            document.getElementById("alturaOrigen").disabled = true;
            document.getElementById("provinciaOrigenSel").disabled = true;
            document.getElementById("muniOrigenSel").disabled = true;
            document.getElementById("vehiculoSelect_1").disabled = true;
            document.getElementById("vehiculoTipoSelect_1").disabled = true;
            document.getElementById("servicioSelect_1").disabled = true;
            document.getElementById("transporteSelect_1").disabled = false;
            document.getElementById("lineaSelect_1").disabled = false;
            document.getElementById("estacionSelect_1").disabled = false;
            document.getElementById("medioDestino").disabled = false;
            document.getElementById("medioDestino").selectedIndex = 0;
            document.getElementById("medioDestino").dispatchEvent(new Event('change'));
            document.getElementById("medioOrigenSel").disabled = false;
        }

    } else if (select == 1){
        document.getElementById("direccionOrigen").disabled = true;
        document.getElementById("alturaOrigen").disabled = true;
        document.getElementById("provinciaOrigenSel").disabled = true;
        document.getElementById("muniOrigenSel").disabled = true;
        document.getElementById("vehiculoSelect_1").disabled = true;
        document.getElementById("vehiculoTipoSelect_1").disabled = true;
        document.getElementById("servicioSelect_1").disabled = true;
        document.getElementById("transporteSelect_1").disabled = false;
        document.getElementById("lineaSelect_1").disabled = false;
        document.getElementById("estacionSelect_1").disabled = false;
        document.getElementById("medioDestino").disabled = true;
        document.getElementById("medioDestino").selectedIndex = 1;
        document.getElementById("medioDestino").dispatchEvent(new Event('change'));
        document.getElementById("medioOrigenSel").disabled = true;
        document.getElementById("medioOrigenSel").selectedIndex = 1;
    } else if (select == 2){
        document.getElementById("direccionOrigen").disabled = false;
        document.getElementById("alturaOrigen").disabled = false;
        document.getElementById("provinciaOrigenSel").disabled = false;
        document.getElementById("muniOrigenSel").disabled = false;
        document.getElementById("vehiculoSelect_1").disabled = false;
        document.getElementById("vehiculoTipoSelect_1").disabled = false;
        document.getElementById("servicioSelect_1").disabled = true;
        document.getElementById("transporteSelect_1").disabled = true;
        document.getElementById("lineaSelect_1").disabled = true;
        document.getElementById("estacionSelect_1").disabled = true;
        document.getElementById("medioDestino").disabled = false;
        document.getElementById("medioDestino").selectedIndex = 0;
        document.getElementById("medioDestino").dispatchEvent(new Event('change'));
        document.getElementById("medioOrigenSel").disabled = false;
        document.getElementById("medioOrigenSel").selectedIndex = 0;
        document.getElementById("medioOrigenSel").dispatchEvent(new Event('change'));
    } else {
        document.getElementById("direccionOrigen").disabled = false;
        document.getElementById("alturaOrigen").disabled = false;
        document.getElementById("provinciaOrigenSel").disabled = false;
        document.getElementById("muniOrigenSel").disabled = false;
        document.getElementById("vehiculoSelect_1").disabled = true;
        document.getElementById("vehiculoTipoSelect_1").disabled = true;
        document.getElementById("servicioSelect_1").disabled = false;
        document.getElementById("transporteSelect_1").disabled = true;
        document.getElementById("lineaSelect_1").disabled = true;
        document.getElementById("estacionSelect_1").disabled = true;
        document.getElementById("medioDestino").disabled = false;
        document.getElementById("medioDestino").selectedIndex = 0;
        document.getElementById("medioDestino").dispatchEvent(new Event('change'));
        document.getElementById("medioOrigenSel").disabled = false;
        document.getElementById("medioOrigenSel").selectedIndex = 0;
        document.getElementById("medioOrigenSel").dispatchEvent(new Event('change'));
    }

})

document.getElementById("medioOrigenSel").addEventListener('change', (event) => {
    var selectTipo = document.getElementById("medioOrigenSel").selectedIndex;

    if (selectTipo == 0){
        document.getElementById("direccionOrigen").disabled = false;
        document.getElementById("alturaOrigen").disabled = false;
        document.getElementById("provinciaOrigenSel").disabled = false;
        document.getElementById("muniOrigenSel").disabled = false;
        document.getElementById("transporteSelect_1").disabled = true;
        document.getElementById("lineaSelect_1").disabled = true;
        document.getElementById("estacionSelect_1").disabled = true;
        document.getElementById("medioDestino").disabled = false;
        document.getElementById("medioDestino").selectedIndex = 0;
        document.getElementById("medioDestino").dispatchEvent(new Event('change'));
    } else {
        document.getElementById("direccionOrigen").disabled = true;
        document.getElementById("alturaOrigen").disabled = true;
        document.getElementById("provinciaOrigenSel").disabled = true;
        document.getElementById("muniOrigenSel").disabled = true;
        document.getElementById("transporteSelect_1").disabled = false;
        document.getElementById("lineaSelect_1").disabled = false;
        document.getElementById("estacionSelect_1").disabled = false;
        document.getElementById("medioDestino").disabled = false;
        document.getElementById("medioDestino").selectedIndex = 0;
        document.getElementById("medioDestino").dispatchEvent(new Event('change'));
    }
})

document.getElementById("transporteSelect_1").addEventListener('change', (event) => {
    var select = document.getElementById('lineaSelect_1');
    var str = document.getElementById("transporteSelect_1").value;

    let options = select.getElementsByTagName('option');

    for (var i=options.length; i--;) {
        select.removeChild(options[i]);
    }

    var newOption = document.createElement("option");
    newOption.text = "Seleccionar";
    newOption.value = "";
    select.add(newOption);

    $.ajax({
        url: "/api/" + str,
        type: "get", //send it through get method
        dataType : "json",
        success : function(resultado){
                        for(r of resultado){
                            var myOption = document.createElement("option");
                            myOption.text = r;
                            myOption.value = r;
                            select.add(myOption);
                        }
                  }
    });
})


document.getElementById("lineaSelect_1").addEventListener('change', (event) => {
    var select = document.getElementById('estacionSelect_1');
    var medio = document.getElementById("transporteSelect_1").value;
    var linea = document.getElementById("lineaSelect_1").value;

    let options = select.getElementsByTagName('option');

    for (var i=options.length; i--;) {
        select.removeChild(options[i]);
    }

    var newOption = document.createElement("option");
    newOption.text = "Seleccionar";
    newOption.value = "";
    select.add(newOption);

    if(linea != ""){
        $.ajax({
            url: "/api/" + medio + "/" + linea,
            type: "get", //send it through get method
            dataType : "json",
            success : function(resultado){
                            for(r of resultado){
                                var myOption = document.createElement("option");
                                myOption.text = r;
                                myOption.value = r;
                                select.add(myOption);
                            }
                      }
        });
    }
})

///DESTINOOOOO


document.getElementById("medioDestino").addEventListener('change', (event) => {
    var select = document.getElementById("medioDestino").selectedIndex;

    if (select == 0){
        document.getElementById("direccionDestino").disabled = false;
        document.getElementById("alturaDestino").disabled = false;
        document.getElementById("provinciaDestinoSel").disabled = false;
        document.getElementById("muniDestinoSel").disabled = false;
        document.getElementById("transporteSelect_2").disabled = true;
        document.getElementById("lineaSelect_2").disabled = true;
        document.getElementById("estacionSelect_2").disabled = true;
    } else {
        document.getElementById("direccionDestino").disabled = true;
        document.getElementById("alturaDestino").disabled = true;
        document.getElementById("provinciaDestinoSel").disabled = true;
        document.getElementById("muniDestinoSel").disabled = true;
        document.getElementById("transporteSelect_2").disabled = false;
        document.getElementById("lineaSelect_2").disabled = false;
        document.getElementById("estacionSelect_2").disabled = false;
    }

})

document.getElementById("transporteSelect_2").addEventListener('change', (event) => {
    var select = document.getElementById('lineaSelect_2');
    var str = document.getElementById("transporteSelect_2").value;

    let options = select.getElementsByTagName('option');

    for (var i=options.length; i--;) {
        select.removeChild(options[i]);
    }

    var newOption = document.createElement("option");
    newOption.text = "Seleccionar";
    newOption.value = "";
    select.add(newOption);

    $.ajax({
        url: "/api/" + str,
        type: "get", //send it through get method
        dataType : "json",
        success : function(resultado){
                        for(r of resultado){
                            var myOption = document.createElement("option");
                            myOption.text = r;
                            myOption.value = r;
                            select.add(myOption);
                        }
                  }
    });
})


document.getElementById("lineaSelect_2").addEventListener('change', (event) => {
    var select = document.getElementById('estacionSelect_2');
    var medio = document.getElementById("transporteSelect_2").value;
    var linea = document.getElementById("lineaSelect_2").value;

    let options = select.getElementsByTagName('option');

    for (var i=options.length; i--;) {
        select.removeChild(options[i]);
    }

    var newOption = document.createElement("option");
    newOption.text = "Seleccionar";
    newOption.value = "";
    select.add(newOption);

    if(linea != ""){
        $.ajax({
            url: "/api/" + medio + "/" + linea,
            type: "get", //send it through get method
            dataType : "json",
            success : function(resultado){
                            for(r of resultado){
                                var myOption = document.createElement("option");
                                myOption.text = r;
                                myOption.value = r;
                                select.add(myOption);
                            }
                      }
        });
    }
})