const app = Vue.createApp({
    data() {
        return {
            amount: 0,
            desc: "",
            originAccInt: "",
            targetAccInt: "",
            originAccExt: "",
            targetAccExt: "",
            accounts: [],
            client: [],
            gearRotated: false,
        };
    },
    created() {
        this.getAccounts()
    },
    methods: {
        getAccounts(){
            axios.get('/api/clients/current').then(response => {
                this.client = response.data
                this.accounts = this.client.accounts
                console.log(this.accounts);
            })
        },
        createTransfer(){
            this.originAccExt = this.originAccInt
            this.targetAccExt = this.targetAccInt

            axios.post('/api/transactions', "amount="+this.amount+"&desc="+this.desc+"&origin="+this.originAccExt+"&target="+this.targetAccExt).then(response => Swal.fire({
                icon: 'success',
                title: 'Transferencia realizada',
                text: '¡Tu transferencia fue realizada con exito!',
                timer: 3000
              }))
              .catch(error => Swal.fire({
                icon: 'error',
                title: 'Ocurrió un error',
                text: error.response.data,
                timer: 3000,
            }))
        },
        redirect(url){
            window.location.href=url;
        },
        logout(){
            axios.post('/api/logout').then(response => setTimeout(window.location.href="/web/index.html",1000));
        }
    },
}).mount("#app");