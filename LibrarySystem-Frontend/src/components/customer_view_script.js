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
  name: 'customer_view_script',
  data() {
    return {
      error: '',
      response: [],
      slide: 0,
      sliding: null,
      hours: [],
      civic: '',
      street: '',
      city: '',
      postalCode: '',
      province: '',
      country: '',
      shiftError: '',
      eventError: '',
      info: [],
      bookings: []
    }
  },

  methods: {
    signout() {
      let splits = document.cookie.split(';');
      let id = splits[0].split('=');
      AXIOS.put(backendUrl + "/customer/logout/" + id[1]).then(response => {
        this.response = response.data
        this.error = ''
        console.log(response)
        if (this.response === "Logged out") {
          document.cookie = "libraryCardID=;Max-Age=0";
          document.cookie = "usertype=;Max-Age=0"
          this.$router.push('/');
        }
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.status)
        this.error = msg.response.data;
      })
    },
    openProfile(){
      this.$router.push('/home/Profile')
    },
    openBooking(){
      this.$router.push('/home/Booking');
    }
  },

  beforeMount() {
    let splits = document.cookie.split(';');
    let id = splits[0].split('=');
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
    }),

      AXIOS.get(backendUrl + '/hours/system').then(response => {
        this.hours = response.data
      }).catch(msg => {
        this.error = msg.response.data
        console.log(this.error)
      }),

      AXIOS.get(backendUrl + '/booking/user/?libraryId=' + id[1]).then(response => {
        this.bookings = response.data;
      }).catch(e => {
        this.error = e
      }),

      AXIOS.get(backendUrl + '/customer/' + id[1]).then(response => {
        this.info = response.data;
      }).catch(e => {
        this.error = e
      })
  }
}
