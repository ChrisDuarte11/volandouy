document.getElementById('altaVueloForm').addEventListener('submit', function(event) {
	const dateInput = document.getElementById('fechaVuelo');
	const hoy = new Date().toISOString().split('T')[0];

	const cantidadTuristaInput = document.getElementById('cantidadTurista');
	const cantidadTurista = parseInt(cantidadTuristaInput.value, 10);
	const cantidadEjecutivoInput = document.getElementById('cantidadEjecutivo');
	const cantidadEjecutivo = parseInt(cantidadEjecutivoInput.value, 10);
	
	const duracionHorasInput = document.getElementById('duracionVueloHoras');
	const duracionHoras = parseInt(duracionHorasInput.value, 10);
	const duracionMinutosInput = document.getElementById('duracionVueloMinutos');
	const duracionMinutos = parseInt(duracionMinutosInput.value, 10);
	
	if (new Date(dateInput.value) < new Date(hoy)) {
		event.preventDefault();
		dateInput.classList.add('is-invalid');
	} else {
		dateInput.classList.remove('is-invalid');
	}

	if (cantidadTurista === 0 && cantidadEjecutivo === 0) {
		event.preventDefault();
		cantidadTuristaInput.classList.add('is-invalid');
		cantidadEjecutivoInput.classList.add('is-invalid');
	} else {
		cantidadTuristaInput.classList.remove('is-invalid');
		cantidadEjecutivoInput.classList.remove('is-invalid');
	}

	if (duracionHoras === 0 && duracionMinutos === 0) {
		event.preventDefault();
		duracionHorasInput.classList.add('is-invalid');
		duracionMinutosInput.classList.add('is-invalid');
	} else {
		duracionHorasInput.classList.remove('is-invalid');
		duracionMinutosInput.classList.remove('is-invalid');
	}
});

document.addEventListener('DOMContentLoaded', function() {
	const dateInput = document.getElementById('fechaVuelo');
	const hoy = new Date().toISOString().split('T')[0];
	dateInput.setAttribute('min', hoy);
});		

/*
document.getElementById('cancelar').addEventListener('click', function(event) {
	document.getElementById('rutaVuelo').removeAttribute('required');
	document.getElementById('nombreVuelo').removeAttribute('required');
	document.getElementById('cantidadTurista').removeAttribute('required');
	document.getElementById('cantidadEjecutivo').removeAttribute('required');
	document.getElementById('fechaVuelo').removeAttribute('required');
	document.getElementById('duracionVueloHoras').removeAttribute('required');
	document.getElementById('duracionVueloMinutos').removeAttribute('required');
});
*/
