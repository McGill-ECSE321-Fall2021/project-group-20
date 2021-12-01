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


export default {
  name: 'hdbooking_view_script',
  methods: {
    get(input){
      AXIOS.get(backendUrl+'/booking/user/?libraryId='+input).then(response => {
        this.response = response.data
        this.items = response.data
        marepo=response.data
        this.error = ''
        console.log(response)

      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.error = msg.response.data;
      })

    },
    // await axios.post("http://localhost:8080/booking/create?startDate=12/12/2021&endDate=12/25/2021&type=Reservation&barcode=" + iid + "&LibraryId=" + cid);
    Book(sdate,edate,Reservation,id){

      AXIOS.post(backendUrl+'/booking/create?startDate='+sdate+'&endDate='+edate+'&type='+Reservation+'&barcode='+id+'&LibraryId='+'1').then(response => {
        this.uperror= ''

      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.uperror = msg.response.data;
      })

    },
    Return(id){
      AXIOS.put(backendUrl+'/booking/return/item?barcode='+id).then(response => {
        this.uperror=''
      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.uperror = msg.response.data;
      })
    },

    backup(){
      this.$router.push('/HeadLibrarian')
    },
    next(){
      this.$router.push('UpdateBooking')
    }
    ,
    beforeMount() {
      let split = document.cookie.split(';')
      let id = split[0].split('=');
      this.myid= id
    }

  },
  data() {

    return {
      myid:'',
      error: '',
      response: [],
      items:[],
      uperror: '',
      heads:{

      },
      slide: 0,
      sliding: null
    }
  },

  mounted(){
    this.get();
  }



}
