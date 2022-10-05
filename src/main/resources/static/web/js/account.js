const app = Vue.createApp({
    data() {
        return {
            client: [],
            account: [],
            transactions: [],
            dates:[],
            transactionsSorted: [],
            gearRotated: false,
            numberAccount: "",
        };
    },
    created() {
        const urlParams = new URLSearchParams(location.search);
        const myParam = urlParams.get('id');
        this.getAccount(myParam);
        this.getClient();
    },
    methods: {
        getClient() {
            axios.get('/api/clients/current')
                .then(response => {
                    this.client = response.data
                })
        },
        getAccount(id) {
            axios.get("/api/accounts/" + id)
                .then(response => {
                    this.account = response.data
                    this.numberAccount = this.account.numberAccount
                    this.transactions = this.account.transactions
                    this.sortTransactions();
                    console.log(this.transactions);
                });
        },
        sortTransactions() {
            this.transactions = [...this.transactions].sort((a, b) => new Date(b.date) - new Date(a.date))
        },
        logout() {
            axios.post('/api/logout').then(response => window.localStorage.href = "/web/index.html")
                .catch(error => alert("NO PUDISTE SALIR"));
        },
    },
}).mount("#app");