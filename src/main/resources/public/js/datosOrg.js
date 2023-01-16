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


