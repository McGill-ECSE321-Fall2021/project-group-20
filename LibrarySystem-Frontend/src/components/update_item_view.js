import axios, {Axios} from 'axios'
import JQuery from 'jquery'

let $ = JQuery
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
let marepo;

AXIOS.get('http://localhost:8080/items/titleId/?titleId=8a8180b37d63becc017d63c0878b0045').then(response => {
  this.response = response.data
  marepo=response.data
  this.error = ''
  console.log(response)
}).catch(msg => {
  console.log(msg.response.data)
  console.log(msg.response.status)
  this.error = msg.response.data;
})
export default {
  name: 'update_item_view',
  data() {

    return {
      error: '',
      response: [],
      items:[{hd:marepo=response.ItemBarcode,tl:'response'}],
      heads:{

      },
      slide: 0,
      sliding: null
    }
  },

  methods: {
    get(){
      AXIOS.get('http://localhost:8080/items/titleId/?titleId=4028571b7d657dc0017d657f80490001').then(response => {
        this.response = response.data
        marepo=response.data
        this.error = ''
        console.log(response)
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })

    }

  },
  mounted(){
    this.get();
  }
}
