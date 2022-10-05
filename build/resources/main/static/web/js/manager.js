const app = Vue.createApp({
    data() {
        return {
            clients: [],
            clientsNames:"",
            clientsLastNames: "",
            clientsEmails: "",
        };
    },
    created() {
        this.getData();
    },
    methods: {
        getData(){
            anpxios.get("/api/clients").then(response => {
                this.clients = response.data  
            });
        },
        createNewClient(){
            axios.post("/rest/clients", {
                name: this.clientsNames,
                lastName: this.clientsLastNames,
                email: this.clientsEmails,
            }).then(response => this.getData())
            .catch(error => console.log(error))
        },
    },
}).mount("#app");