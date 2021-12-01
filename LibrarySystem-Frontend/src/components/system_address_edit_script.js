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
  name: 'system_address_edit_script',
  data() {
    return {
      error: '',
      response: [],
      systemID: '',
      street: '',
      country: '',
      province: '',
      postalCode: '',
      civic: '',
      city: ''
    }
  },

  methods: {
    cancel() {
      this.$router.push('/Library/edit')
    },
    update: function (civicU, streetU, cityU, postalCodeU, systemIDU) {
      if (civicU === '') {
        this.error = "Please enter a valid Civic Number"
      }
      else if (streetU === '') {
        this.error = "Please enter a valid Street name"
      }
      else if (cityU === '') {
        this.error = "Please enter a valid City name"
      }
      else if (postalCodeU === '') {
        this.error = "Please enter a valid Postal Code"
      }
      else if (civicU === civic && streetU === street && cityU === city && postalCodeU === postalCode) {
        location.reload()
      }
      else {
        AXIOS.put(backendUrl + '/librarySystem/updateAddress/' + systemIDU + '?civicNumber=' + civicU + '&street=' + streetU + '&city=' + cityU + '&postalCode=' + postalCodeU +
        '&province=' + document.getElementById('province').value + '&country=' + document.getElementById('country').value).then(response => {
          this.response = response.data;
          location.reload();
        }).catch(msg => {
          console.log(msg.response.data);
        })
      }
    }
  },

  beforeMount() {
    AXIOS.get(backendUrl + '/librarySystem').then(response => {
      this.response = response.data[0];
      this.systemID = this.response.systemID;
      this.civic = this.response.businessaddress.civicNumber;
      this.street = this.response.businessaddress.street;
      this.city = this.response.businessaddress.city;
      this.postalCode = this.response.businessaddress.postalCode;
      this.province = this.response.businessaddress.province;
      this.country = this.response.businessaddress.country;
    }).catch(msg => {
      this.error = msg.response;
      console.log(this.error);
    })
  }
}
