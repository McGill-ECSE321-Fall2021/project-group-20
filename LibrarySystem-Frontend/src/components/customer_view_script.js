import axios from 'axios'
import JQuery from 'jquery'

let $ = JQuery
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

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
      sliding: null
    }
  },

  methods: {
    // signout() {
    //   let id = document.cookie.split('=');
    //   AXIOS.put(backendUrl + "/customer/logout/" + id[1]).then(response => {
    //     this.response = response.data
    //     this.error = ''
    //     console.log(response)
    //     if (this.response === "Logged out") {
    //       document.cookie = "libraryCardID=;Max-Age=0";
    //       document.cookie = "usertype=;Max-Age=0"
    //       this.$router.push('/');
    //     }
    //   }).catch(msg => {
    //     console.log(msg.response.data)
    //     console.log(msg.status)
    //     this.error = msg.response.data;
    //   })
    // },
    openProfile(){
      this.$router.push('/home/Profile')
    },
    openBooking(){
      this.$router.push('/home/Booking');
    }
  }
}
