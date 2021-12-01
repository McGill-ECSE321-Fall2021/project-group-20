import axios from 'axios'
import JQuery from 'jquery'



let $ = JQuery
var config = require('../../config')

var frontendUrl = 'http://' + config.build.host
var backendUrl = 'http://' + config.build.backendHost

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
export default {
  name: 'event_view_script',
  data() {
    return {

      error: '',
      events: [],
      response: []

    }
  },

  methods: {
    cancel() {
      this.$router.push('/')
    },
    updateEvent: function () {
      this.$router.push('/EmployeePage/Event/Update')
    },
    CreateEvent: function () {
      this.$router.push('/EmployeePage/Event/Create')
    },
  },
  beforeMount() {
    AXIOS.get('/events').then(response => {
      this.events = response.data
    }).catch(e => {
      this.errorEvent = e
    });
  },



}


