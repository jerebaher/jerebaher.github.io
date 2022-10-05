document.addEventListener("DOMContentLoaded", ()=>{
    const loginForm = document.querySelector("#login");
    const registerForm = document.querySelector("#register");

    document.querySelector("#link-register").addEventListener("click", ()=>{
        loginForm.classList.add("hidden")
        registerForm.classList.remove("hidden")
    });

    document.querySelector("#link-login").addEventListener("click", ()=>{
        registerForm.classList.add("hidden")
        loginForm.classList.remove("hidden")
    });
});

const app = Vue.createApp({
    data() {
        return {
            addressRegister:"",
            passwordRegister:"",
            firstName:"",
            lastName: "",
            address: "",
            password: "",
            good: false,
        };
    },
    created() {},
    methods: {
        login(){
            axios.post('/api/login',"email=" + this.address + "&password=" + this.password,
            {headers:{'accept':'application/xml'}})
            .then(response => setTimeout(()=> window.location.href="/web/html/accounts.html", 500))
            .catch(error => Swal.fire({
                icon: 'error',
                title: 'Ocurrió un error',
                text: error.response.data,
            }));
        },
        logout(){
            axios.post('/api/logout').then(response => window.localStorage.href="/web/index.html");
        },
        signup(){
            axios.post('/api/clients',"name="+this.firstName+"&lastName="+this.lastName+"&email="+this.addressRegister+"&password="+this.passwordRegister,
            {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response =>{
                this.address = this.addressRegister
                this.password = this.passwordRegister
                this.login()
            })
            .catch(error => Swal.fire({
                icon: 'error',
                title: 'Ocurrió un error',
                text: error.response.data,
                footer: '<a href="#">¿Por qué tengo este error?</a>'
            }))
        },
    },
}).mount("#app");