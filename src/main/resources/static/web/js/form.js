const localButton = document.getElementById('local-transfer');
const externalButton = document.getElementById('external-transfer');
const container = document.getElementById('container');

localButton.addEventListener('click', () => {
	container.classList.add("right-panel-active");
});

externalButton.addEventListener('click', () => {
	container.classList.remove("right-panel-active");
});