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
  name: 'employee_view_script',
  data() {
    return {
      error: '',
      response: [],
      slide: 0,
      sliding: null
    }
  },

  methods: {

    onSlideStart(slide) {
      this.sliding = true
    },
    onSlideEnd(slide) {
      this.sliding = false
    },
    openCreate() {
      this.$router.push('/createLocalAccount')
    },
    openFees() {
      this.$router.push('/fees')
    },
    openVerify() {
      this.$router.push('/verify')
    },
    openBooking(){
      this.$router.push('/EmployeePage/Booking')
    },
    openManagement(){
      this.$router.push('/EmployeePage/Management')
    },
    openEvent(){
      this.$router.push('/EmployeePage/Event')
    },
    openSchedule(){
      this.$router.push('/EmployeePage/Schedule')
    },
    openProfile(){
      this.$router.push('Employee/Profile')
    },
    signout() {
      let id = document.cookie.split('=');
      AXIOS.put(backendUrl + "/employee/logout/" + id[1]).then(response => {
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
    }
  },
  // beforeMount() {
  //   if (document.cookie.indexOf('usertype=') !== -1) {
  //     let splits = document.cookie.split(';');
  //     let type = splits[1].split('=');
  //     if (type[1] === 'customer') {
  //       this.$router.push('home');
  //     }
  //     else if (type[1] === 'Head Librarian') {
  //       this.$router.push('HeadLibrarian');
  //     }
  //   }
  //   else {
  //     this.$router.push('/');
  //   }
  // }
}
