const app = Vue.createApp({
    data() {
        return {
            client: [],
            accounts: [],
            loans: [],
            cuotas: [],
            loan: {
                id: 0,
                amount: 0,
                payments: 0,
                targetAccount: ""
            },
            gearRotated: false
        }
    },
    created() {
        this.getLoan()
        this.getClient()
    },
    methods: {
        getClient(){
            axios.get('/api/clients/current').then(res =>{
                this.client = res.data
                this.accounts = this.client.accounts
            })
        },
        getLoan() {
            axios.get('/api/loans').then(response => {
                this.loans = response.data
                console.log(this.loans);
            })
        },
        createLoan() {
            axios.post("/api/loans", {
                "id": this.loan.id,
                "amount": this.loan.amount,
                "payments": this.loan.payments,
                "targetAccount": this.loan.targetAccount,
            }, { headers: { 'content-type': 'application/json', 'application/x-www-form-urlencoded' } })
                .then(response => Swal.fire({
                    icon: 'success',
                    title: 'Prestamo entregado con exito',
                    text: 'Haz accedido al prestamo exitosamente',
                  }).then((result) =>{
                    if (result.isConfirmed) {
                        window.location.href="/web/html/accounts.html";
                    }
                  }))
                .catch(error =>{
                    Swal.fire({
                        icon: 'error',
                        title: 'Ocurrió un error',
                        text: error.response.data,
                    });
                });
        },
        redirect(url){
            window.location.href=url;
        },
        logout(){
            axios.post('/api/logout').then(response => setTimeout(window.location.href="/web/index.html",1000));
        },
        currency(number){
            return new Intl.NumberFormat('en-US', {style: 'currency',currency: 'USD', minimumFractionDigits: 2}).format(number);
        }    
    },
    computed: {
        getPayments(){
            if(this.loan.id != 0){
                let loanSelected = this.loans.filter(loan => loan.id == this.loan.id);
                this.cuotas = loanSelected[0].payments
                console.log(this.cuotas);
            }
        },
    },
}).mount("#app")