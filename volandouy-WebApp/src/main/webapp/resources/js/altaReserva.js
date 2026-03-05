actualizarRutas();
actualizarVuelos();
let cantPasajeros = 0;
let pasajeros = [];

function actualizarRutas(){
    aero = document.getElementById("aerolineasSelect").value;
    for(let option of document.getElementById("rutasSelect").options) {
        if(option.className != aero){
            option.setAttribute("hidden", "hidden");
        }
        else{
            option.removeAttribute("hidden");
        }
    }
    for (var i = 0; i < document.getElementById("rutasSelect").options.length; i++) {
        var option = document.getElementById("rutasSelect").options[i];
        if (option.getAttribute("hidden") == null) {
            document.getElementById("rutasSelect").selectedIndex = i;
            actualizarVuelos();
            return;
        }
    }
    document.getElementById("rutasSelect").selectedIndex = -1;
    actualizarVuelos();
}

function actualizarVuelos(){
    ruta = document.getElementById("rutasSelect").value;
    for(let option of document.getElementById("vuelosSelect").options) {
        if(option.className != ruta){
            option.setAttribute("hidden", "hidden");
        }
        else{
            option.removeAttribute("hidden");
        }
    }
    for (var i = 0; i < document.getElementById("vuelosSelect").options.length; i++) {
        var option = document.getElementById("vuelosSelect").options[i];
        if (option.getAttribute("hidden") == null) {
            document.getElementById("vuelosSelect").selectedIndex = i;
            return;
        }
    }
    document.getElementById("vuelosSelect").selectedIndex = -1;
}

function agregarPasajero(){
    if(cantPasajeros+1 < parseInt(document.getElementById("pasajesInput").value)){
        cantPasajeros++;
        pasajero = document.getElementById("pasajerosInput").value;
        pasajeros.push(pasajero);
        document.getElementById("pasajerosInput").value = "";
        alert("Pasajero agregado");
        document.getElementById("pasajerosAgregados").value += pasajero + ", ";
    }
    else{
        alert("No se pueden agregar mas pasajeros, aumente la cantidad de pasajeros");
    }
}

function actualizarCosto() {
    const tipoAsiento = document.getElementById("opciones").value;
    const cantidadPasajes = parseInt(document.getElementById("pasajesInput").value);
    const equipajeExtra = parseInt(document.getElementById("equipajeExtra").value);

    const ruta = document.getElementById("rutasSelect");
    const rutaOption = ruta.options[ruta.selectedIndex];
    const costoTurista = parseFloat(rutaOption.getAttribute("costo-turista"));
    const costoEjecutivo = parseFloat(rutaOption.getAttribute("costo-ejecutivo"));
    const costoEquipaje = parseFloat(rutaOption.getAttribute("costo-equipaje"));

    let costoTotal = costoEquipaje * equipajeExtra;
    if (tipoAsiento === "turista") {
        costoTotal += costoTurista * cantidadPasajes;
    } else {
        costoTotal += costoEjecutivo * cantidadPasajes;
    }

    document.getElementById("costo").value = costoTotal.toFixed(2);
}


function limpiar(){
    cantPasajeros = 0;
    pasajeros = [];
    document.getElementById("pasajerosAgregados").value = "";
}

document.getElementById('altaReservaForm').addEventListener('submit', function(event) {
	const rutaInput = document.getElementById('rutasSelect');
	const vueloInput = document.getElementById('vuelosSelect');
	
	if (rutaInput.selectedIndex === -1) {
		event.preventDefault();
		rutaInput.classList.add('is-invalid');
	} else {
		rutaInput.classList.remove('is-invalid');
	}

	if (vueloInput.selectedIndex === -1) {
		event.preventDefault();
		vueloInput.classList.add('is-invalid');
	} else {
		vueloInput.classList.remove('is-invalid');
	}
});
