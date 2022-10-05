const app = Vue.createApp({
    data() {
        return {
            cardType: "",
            cardColor: "",
            client: [],
            cards:[],
            gearRotated: false,
        }
    },
    created() {
        this.getClient()
    },
    methods: {
        getClient(){
            axios.get("/api/clients/current").then(response =>{
                this.client = response.data
                this.getCards();
            })
        },
        getCards(){
            axios.get("/api/clients/current").then(response=>{
                this.cards=response.data.cards
            })
        },
        createCard() {
            axios.post('/api/clients/current/cards', "cardType=" + this.cardType + "&cardColor=" + this.cardColor)
                .then(response => {
                    Swal.fire({
                        icon: 'success',
                        title: 'Tarjeta creada',
                        text: 'Tarjeta creada con exito',
                    })
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Algo ha salido mal',
                        text: error.response.data,
                        footer: '<a href="">Why do I have this issue?</a>'
                    })
                })
        },
        logout() {
            axios.post('/api/logout').then(response => window.location.href = "/index.html")
        },
        redirect(url) {
            window.location.href = url;
        }
    }
}).mount('#app')