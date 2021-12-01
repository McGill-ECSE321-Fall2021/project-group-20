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
  name: 'headlibrarian_view_script',
  data() {
    return {
      error: '',
      response: [],
      slide: 0,
      sliding: null,
      shifts: [],
      hours: [],
      civic: '',
      street: '',
      city: '',
      postalCode: '',
      province: '',
      country: '',
      shiftError: '',
      eventError: '',
      events: []
    }
  },

  methods: {

    onSlideStart(slide) {
      this.sliding = true
    },
    onSlideEnd(slide) {
      this.sliding = false
    },
    openBooking() {
      this.$router.push('/EmployeePage/Booking')
    },
    openLibrary() {
      this.$router.push('/library/edit')
    },
    openCreate() {
      this.$router.push('/createLocalAccount')
    },
    openVerify() {
      this.$router.push('/verify')
    },
    openFees() {
      this.$router.push('/fees')
    },
    openHire() {
      this.$router.push('/HeadLibrarian/hire')
    },
    openFire() {
      this.$router.push('/HeadLibrarian/fire')
    },
    openManagement() {
      this.$router.push('/EmployeePage/Management')
    },
    openEvent() {
      this.$router.push('/EmployeePage/Event')
    },
    openSchedule() {
      this.$router.push('/Schedules')
    },
    openProfile() {
      this.$router.push('Employee/Profile')
    },
    openOrganize() {
      this.$router.push('HeadLibrarian/Organize')
    },
    openHour() {
      this.$router.push('HeadLibrarian/Hour')
    },
    signout() {
      let id = document.cookie.split('=');
      AXIOS.put(backendUrl + "/employee/logout/" + id[1]).then(response => {
        this.response = response.data
        this.error = ''
        console.log(response)
        document.cookie = 'libraryCardID=;Max-Age=0';
        document.cookie = 'usertype=;Max-Age=0';
        this.$router.push('/');
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
  //     } else if (type[1] === 'Librarian') {
  //       this.$router.push('EmployeePage');
  //     }
  //   } else {
  //     this.$router.push('/');
  //   }
  // }


  beforeMount() {

    let splits = document.cookie.split(';');
    let type = splits[0].split('=');
    AXIOS.get(backendUrl + '/hours/shifts/' + type[1]).then(response => {
      this.shifts = response.data;
    }).catch(msg => {
      this.shiftError = msg.response.data
      console.log(this.shiftError)
    }),

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
      });
  }
}
