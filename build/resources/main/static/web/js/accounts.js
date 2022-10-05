const app = Vue.createApp({
  data() {
    return {
      client: [],
      accounts: [],
      balances: [],
      loans: [],
      amounts: [],
      totalBalance: 0,
      totalLoan: 0,
      gearRotated: false,
      arrowRotated: false,
    };
  },
  created() {
    this.getAccounts();
    this.getLoans();
  },
  methods: {
    getAccounts(){
      axios.get("/api/clients/current",{headers:{'content-type':'application/x-www-form-urlencoded'}})
      .then((response)=> {
        this.client = response.data;
        this.accounts = this.client.accounts; //cuentas del cliente autenticado
        this.getTotalBalance();
        this.sortAccounts();
      });
    },
    getTotalBalance() {
      axios.get("/api/clients/current",{headers:{'content-type':'application/x-www-form-urlencoded'}})
      .then(response => {
        this.accounts = response.data.accounts
        this.totalBalance = 0
        this.accounts.forEach(account => this.totalBalance += account.balance)//agrega el balance de cada cuenta a un balance total
      })
    },
    sortAccounts() {
      axios.get("/api/clients/current",{headers:{'content-type':'application/x-www-form-urlencoded'}})
      .then((response) => {
        this.accounts = response.data.accounts;
        this.accounts = [...this.accounts].sort((a, b) => a.id - b.id); //ordena las cuentas por su id
      })
    },
    getLoans() {
      axios.get("/api/clients/current",{headers:{'content-type':'application/x-www-form-urlencoded'}})
      .then(response => {
        this.client = response.data;
        this.loans = this.client.loans;
      })
    },
    logout() {
      axios.post('/api/logout').then(response => window.location.href = "/index.html")
        .catch(error => alert("NO PUDISTE SALIR"));
    },
    createAccount() {
      Swal.fire({
        title: '¿Estás seguro?',
        text: "¿Estás seguro que desea crear una nueva cuenta?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Aceptar',
        cancelButtonText: 'Cancelar',
        reverseButtons: true
      }).then((result) => {
        if (result.isConfirmed) {
          axios.post('/api/clients/current/accounts')
          .then(response => this.getAccounts())
          .catch(error => {
            swalWithBootstrapButtons.fire({
              title: "Hemos detectado un error",
              text: error.response.data,
              icon: "error",
              showConfirmButton: false,
              showCancelButton: true,
              cancelButtonText: 'Aceptar',
            })
          })
        }
      })
    },
    /* deteleAccount(){
      Swal.fire({
        title: '¿Estás seguro que desea eliminar esta cuenta?',
        text: "Al hacer esto, los datos de su cuenta eliminada serán transferidos a la cuenta de destino.",
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Aceptar',
        cancelButtonText: 'Cancelar',
        reverseButtons: true
      }).then((result) => {
        if(result.isConfirmed){
          Swal.fire({
            input:'select',
            inputOptions: {
              'Accounts': {
                account1: 'Apples',
                bananas: 'Bananas',
                grapes: 'Grapes',
                oranges: 'Oranges'
              }, 
            showCancelButton: true,
            confirmButtonText: 'Aceptar',
            cancelButtonText: 'Cancelar',
            reverseButtons: true
          })
        }
      })
    }, */
    redirect(url) {
      window.location.href = url;
    },
    currency(number) {
      return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 2 }).format(number);
    }
  },
  mounted() { }
}).mount('#app');
