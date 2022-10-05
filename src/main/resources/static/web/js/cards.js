
const app = Vue.createApp({
  data() {
    return {
      client: [],
      cards: [],
      gearRotated: false,
    }
  },
  created() {
    this.getCards()
    console.log(new Date());
  },
  methods: {
    getCards() {
      axios.get("/api/clients/current").then(response => {
        this.client = response.data
        this.cards = this.client.cards
      })
    },
    disabledCard(id) {
      axios.delete("/api/cards/" + id).then(response => {
        this.getCards()
      })
      .catch(error => console.log(error));
    },
    logout() {
      axios.post('/api/logout').then(response => window.location.href = "/index.html")
    },
    redirect(url) {
      window.location.href = url;
    }
  },
  computed: {
  }
}).mount('#app');