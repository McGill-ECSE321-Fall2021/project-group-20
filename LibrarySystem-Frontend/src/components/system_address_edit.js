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
  name: 'library_edit_script',
  data() {
    return {
      error: '',
      response: [],
      civic: '',
      street: '',
      city: '',
      postalCode: '',
      province: '',
      country: ''
    }
  },

  methods: {

  },

  beforeMount() {
    AXIOS.get(backendUrl + '/librarySystem').then(response => {
      this.response = response.data[0].businessaddress;
      this.civic = this.response.civicNumber;
      this.street = this.response.street;
      this.city = this.response.city;
      this.postalCode = this.response.postalCode;
      this.province = this.response.province;
      this.country = this.response.country;
    }).catch(msg => {
      this.error = msg.response.data;
      console.log(this.error)
    })
  }
}
