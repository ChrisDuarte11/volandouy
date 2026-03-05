document.addEventListener('DOMContentLoaded', (event) => {
    const htmlElement = document.documentElement;
    const switchElement = document.getElementById('darkModeSwitch');
    const navbarElement = document.getElementById('navbar');

    // Set the default theme to dark if no setting is found in local storage
    const currentTheme = localStorage.getItem('bsTheme') || 'dark';
    htmlElement.setAttribute('data-bs-theme', currentTheme);
    switchElement.checked = currentTheme === 'dark';

    switchElement.addEventListener('change', function () {
        if (this.checked) {
            htmlElement.setAttribute('data-bs-theme', 'dark');
            localStorage.setItem('bsTheme', 'dark');
            navbarElement.classList.remove('navbar-light', 'bg-light');
            navbarElement.classList.add('navbar-dark', 'bg-dark');
        } else {
            htmlElement.setAttribute('data-bs-theme', 'light');
            localStorage.setItem('bsTheme', 'light');
            navbarElement.classList.remove('navbar-dark', 'bg-dark');
            navbarElement.classList.add('navbar-light', 'bg-light');
        }
    });
});