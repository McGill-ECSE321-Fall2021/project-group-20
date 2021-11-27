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
  name: 'setup_script',
  data() {
    return {
      error: '',
      response: []
    }
  },

  methods : {
    create: function (firstname, lastname, email, civic, street, city, postalCode) {
      if (document.getElementById('civic').value === '') {
        this.error = "Please enter a valid civic number"
      }

      else if (document.getElementById('street').value === '') {
        this.error = "Please enter a valid street"
      }

      else if (document.getElementById('city').value === '') {
        this.error = "Please enter a valid city"
      }

      else if (document.getElementById('postal').value === '') {
        this.error = "Please enter a valid postal code"
      }

      else if (document.getElementById('firstname').value === '') {
        this.error = "Please enter a valid first name"
      }

      else if (document.getElementById('lastname').value === '') {
        this.error = "Please enter a valid last name"
      }

      else if (document.getElementById('email').value === '') {
        this.error = "Please enter a valid email"
      }

      else {
        AXIOS.post(backendUrl + '/librarySystem/create?civicNumber=' + civic + '&street=' + street +
        '&city=' + city + '&postalCode=' + postalCode + '&province=' + document.getElementById('province').value +
        '&country=' + document.getElementById('country').value).then(response => {
          this.response = response.data
          this.error = ''
          console.log(response)
          if (this.response.calendar !== null && this.response.systemID !== null && this.response.businessaddress !== null) {
            if (this.response.businessaddress.civicNumber === civic && this.response.businessaddress.city === city &&
              this.response.businessaddress.street === street && this.response.businessaddress.postalCode === postalCode &&
              this.response.businessaddress.province === document.getElementById('province').value &&
            this.response.businessaddress.country === document.getElementById('country').value) {
              AXIOS.post(backendUrl + '/employee/create?firstName=' + firstname + '&lastName=' + lastname + '&civic=' + civic + '&street=' + street +
                '&city=' + city + '&postalCode=' + postalCode + '&province=' + document.getElementById('province').value +
                '&country=' + document.getElementById('country').value + '&email=' + email + '&username=headlibrarian&password=headlibrarian&role=HeadLibrarian').then(response => {
                this.response = response.data
                this.error = ''
                console.log(response)
                if (this.response.libraryCardID !== null) {
                  this.error = 'You may now log in!';
                  this.$router.push('home');
                }
              }).catch(msg => {
                console.log(msg.response.data)
                console.log(msg.response.status)
                this.error = msg.response.data;
              })
            }
          }
        }).catch(msg => {
          console.log(msg.response.data)
          console.log(msg.response.status)
          this.error = msg.response.data;
        })
      }
    }
  },

  beforeMount() {
    if (document.cookie.indexOf('libraryCardID=') !== -1) {
      let splits = document.cookie.split(';');
      let type = splits[1].split('=');
      if (type[1] === 'Librarian') {
        this.$router.push('EmployeePage');
      }
      else if (type[1] === 'Head Librarian') {
        this.$router.push('HeadLibrarian');
      }
      else {
        this.$router.push('home');
      }
    }
    else {
      AXIOS.get(backendUrl + '/librarySystem').then(response => {
        this.response = response.data
        this.error = ''
        this.$router.push('/')
      }).catch(msg => {
        this.error = msg.response.data
      })
    }
  }
}
