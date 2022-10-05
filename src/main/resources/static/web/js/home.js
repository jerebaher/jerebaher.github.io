const nav = document.querySelector("nav");
const btnToggleNav = document.querySelector(".hamburger-menu");
const form = document.querySelector(".hero-gauche form");


// La navbar responsive
btnToggleNav.addEventListener('click', () => {

    nav.classList.toggle("active");
    btnToggleNav.classList.toggle("active");

})

// Le prevent default sur le formulaire
form.addEventListener("submit", (e) => {

    e.preventDefault();

})