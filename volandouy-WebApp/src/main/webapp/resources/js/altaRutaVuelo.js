actualizar(true);
actualizar(false);


function actualizar(origen){

    if(origen){
        pais = document.getElementById("paisOrigen").value;
        ciudades = document.getElementById("ciudadOrigen");
    }
    else{
        pais = document.getElementById("paisDestino").value;
        ciudades = document.getElementById("ciudadDestino");
    }
    
    for(let option of ciudades.options) {
        if(option.getAttribute("pais") != pais){
            option.setAttribute("hidden", "hidden");
        }
        else{
            option.removeAttribute("hidden");
        }
    }
    for (var i = 0; i < ciudades.options.length; i++) {
        var option = ciudades.options[i];
        if (option.getAttribute("hidden") == null) {
            ciudades.selectedIndex = i;
            return;
        }
    }
    ciudades.selectedIndex = -1;
}

document.getElementById('altaRutaForm').addEventListener('submit', function(event) {
	const paisOrigenInput = document.getElementById('paisOrigen');
	const ciudadOrigenInput = document.getElementById('ciudadOrigen');
	const paisDestinoInput = document.getElementById('paisDestino');
	const ciudadDestinoInput = document.getElementById('ciudadDestino');
	
	if (paisOrigenInput.value === paisDestinoInput.value && ciudadOrigenInput.value === ciudadDestinoInput.value) {
		event.preventDefault();
		ciudadOrigenInput.classList.add('is-invalid');
		ciudadDestinoInput.classList.add('is-invalid');
	} else {
		ciudadOrigenInput.classList.remove('is-invalid');
		ciudadDestinoInput.classList.remove('is-invalid');
	}
});
