document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('signupForm');
    const tipoUsuario = document.getElementById('tipoUsuario');
    const clienteFields = document.getElementById('clienteFields');
    const aerolineaFields = document.getElementById('aerolineaFields');
    const password = document.getElementById('password');
    const passwordConfirm = document.getElementById('passwordConfirm');
    
    function toggleFields() {
        if (tipoUsuario.value === 'cliente') {
            clienteFields.style.display = 'block';
            aerolineaFields.style.display = 'none';

            document.getElementById('apellido').setAttribute('required', true);
            document.getElementById('fechaNacimiento').setAttribute('required', true);
            document.getElementById('tipoDocumento').setAttribute('required', true);
            document.getElementById('nroDocumento').setAttribute('required', true);
            document.getElementById('nacionalidad').setAttribute('required', true);
            document.getElementById('descripcion').removeAttribute('required');
            document.getElementById('paginaWeb').removeAttribute('required');
            document.getElementById('paginaWeb').setAttribute('type', 'text');
        } else if (tipoUsuario.value === 'aerolinea') {
            clienteFields.style.display = 'none';
            aerolineaFields.style.display = 'block';

            document.getElementById('descripcion').setAttribute('required', true);
            document.getElementById('paginaWeb').setAttribute('required', true);
            document.getElementById('paginaWeb').setAttribute('type', 'url');
            document.getElementById('apellido').removeAttribute('required');
            document.getElementById('fechaNacimiento').removeAttribute('required');
            document.getElementById('tipoDocumento').removeAttribute('required');
            document.getElementById('nroDocumento').removeAttribute('required');
            document.getElementById('nacionalidad').removeAttribute('required');
        }
    }

    toggleFields();

    tipoUsuario.addEventListener('change', toggleFields);

    form.addEventListener('submit', function(event) {
        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }

        if (password.value !== passwordConfirm.value) {
            passwordConfirm.setCustomValidity('Las contraseñas no coinciden');
        } else {
            passwordConfirm.setCustomValidity('');
        }

        if (password.value.length < 8) {
            password.setCustomValidity('La contraseña debe tener al menos 8 caracteres');
        } else {
            password.setCustomValidity('');
        }

        form.classList.add('was-validated');
    }, false);

    passwordConfirm.addEventListener('input', function() {
        if (password.value !== passwordConfirm.value) {
            passwordConfirm.setCustomValidity('Las contraseñas no coinciden');
        } else {
            passwordConfirm.setCustomValidity('');
        }
    });

    password.addEventListener('input', function() {
        if (password.value.length < 8) {
            password.setCustomValidity('La contraseña debe tener al menos 8 caracteres');
        } else {
            password.setCustomValidity('');
        }
    });
});
