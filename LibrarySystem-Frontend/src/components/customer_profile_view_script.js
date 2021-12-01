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
  name: 'customer_profile_vue_script',
  data() {
    return {
      error: '',
      response: [],
      username: '',
      libraryCardID: 0,
      email: '',
      firstname: '',
      lastname: '',
      civicNumber: '',
      street: '',
      country: '',
      province: '',
      postalCode: '',
      civic: '',
      city: '',
      demeritPts: '',
      balance: ''
    }
  },

  methods: {
    /* Calls updateOnlineInfo */
    customerUpdateOnlineInfo: function (userName, confirm, newPassword, email) {
      if (confirm !== newPassword) {
        this.error = "Please enter the new password twice"
      }
      else if (newPassword == null) {
        AXIOS.put(backendUrl + '/customer/updateNoPass/' + this.libraryCardID + '?username=' + userName + '&email=' + email).then(response => {
          this.response = response.data
          this.error = ''
          console.log(response)
          if (this.response != '') {
            this.$router.push('/')
          }
        }).catch(msg => {
          console.log(msg.response.data)
          console.log(msg.response.status)
          this.error = msg.response.data;
        })
      }
      else {
        AXIOS.put(backendUrl + '/customer/updateOnline/' + this.libraryCardID + '?username=' + userName + '&password=' + newPassword +
          '&email=' + email).then(response => {
          this.response = response.data
          this.error = ''
          console.log(response)
          if (this.response != '') {
            this.$router.push('/')
          }
        }).catch(msg => {
          console.log(msg.response.data)
          console.log(msg.response.status)
          this.error = msg.response.data;
        })
      }
    },

    /* Calls updateInfo */
    customerUpdateInfo: function (firstname, lastname, civic, street, city, postalCode, province, country) {
      AXIOS.put(backendUrl + '/customer/update/' + this.libraryCardID + '?firstName=' + firstname + '&lastName=' + lastname + '&civic=' +
        civic + '&street=' + street + '&city=' + city + '&postalCode=' + postalCode + '&province=' + province +
        '&country=' + country).then(response => {
        this.response = response.data
        this.error = ''
        console.log(response)
        if (this.response != '') {
          this.$router.push('/')
        }
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })
    },

    cancel: function () {
      //TODO
      this.$router.push('/')
    },

    deleteAcc: function() {
      AXIOS.delete(backendUrl + '/customer/' + this.libraryCardID).then(response => {
        this.response = response.data
        this.error = ''
        console.log(response)
        if (this.response != '') {
          document.cookie = "libraryCardID=;Max-Age=0";
          document.cookie = "usertype=;Max-Age=0"
          this.error = this.response
          this.$router.push('/')
        }
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
      })
    }
  },
  beforeMount() {
  //   if (document.cookie.indexOf('usertype=') !== -1) {
  //     let splits = document.cookie.split(';');
  //     let type = splits[1].split('=');
  //     if (type[1] === 'customer') {
  //       this.$router.push('home');
  //     }
  //   }
  //   else {
  //     this.$router.push('/');
  //   }
    let split = document.cookie.split(';')
    let id = split[0].split('=');
    AXIOS.get(backendUrl + '/customer/' + id[1]).then(response => {
      this.response = response.data;
      this.username = this.response.username;
      this.libraryCardID = this.response.libraryCardID;
      this.email = this.response.email;
      this.firstname = this.response.firstName;
      this.lastname = this.response.lastName;
      this.civicNumber = this.response.address.civicNumber;
      this.street = this.response.address.street;
      this.city = this.response.address.city;
      this.province = this.response.address.province;
      this.country = this.response.address.country;
      this.postalCode = this.response.address.postalCode;
      this.demeritPts = this.response.demeritPts;
      this.balance = this.response.outstandingBalance;
    }).catch(msg => {
      this.error = msg.response.data;
    })
  }
}
