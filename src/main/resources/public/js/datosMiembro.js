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

document.getElementById("organizacionSel").addEventListener('change', (event) => {
    var sectorSelect = document.getElementById('sectorSel');
    var organizacionVal = document.getElementById("organizacionSel").value;

    var index = organizacionVal.selectedIndex;
    sectores = [];

    let options = sectorSelect.getElementsByTagName('option');

    for (var i=options.length; i--;) {
        sectorSelect.removeChild(options[i]);
    }

    $.ajax({
        url: "/api/organizaciones/" + organizacionVal,
        type: "get", //send it through get method
        dataType : "json",
        success : function(resultado){
                        for(r of resultado){
                            var myOption = document.createElement("option");
                            myOption.text = r;
                            myOption.value = r;
                            sectorSelect.add(myOption);
                        }
                  }
    });
})

document.getElementById("misOrganizacionesSel").addEventListener('change', (event) => {
    var orgSelect = document.getElementById('misOrganizacionesSel').value;
    var irOrgBtn = document.getElementById('verOrg');
    var nombreUsuario = document.getElementById('userId').value;
    var strHref = "/miembros/" + nombreUsuario + "/org/" + orgSelect;
    irOrgBtn.setAttribute("href", strHref);
})

document.getElementById("misOrganizacionesSel").selectedIndex = 0;
document.getElementById("misOrganizacionesSel").dispatchEvent(new Event('change'));

