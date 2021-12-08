import axios, {Axios} from 'axios'
import JQuery from 'jquery'

let $ = JQuery
var config = require('../../config')

var frontendUrl = 'http://' + config.build.host
var backendUrl = 'http://' + config.build.backendHost

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
let marepo;


export default {
  name: 'booking_view',
  methods: {
    get(input){
      AXIOS.get(backendUrl+'/items/title/?titlename='+input).then(response => {
        this.response = response.data
        this.items = response.data
        marepo=response.data
        this.uperror = ''
        console.log(response)
        console.log(response.data[0].itemBarcode)

      }).catch(msg => {
        console.log(msg.response.data)
        console.log(msg.response.status)
        this.uperror = msg.response.data;
      })

    },
    // await axios.post("http://localhost:8080/booking/create?startDate=12/12/2021&endDate=12/25/2021&type=Reservation&barcode=" + iid + "&LibraryId=" + cid);
    Book(sdate,edate,Reservation,id){
      if (this.items.status !== "Available") {
        this.uperror = "Cannot book an unavailable item!"
      }
      else {
        AXIOS.post(backendUrl + '/booking/create?startDate=' + sdate + '&endDate=' + edate + '&type=' + Reservation + '&barcode=' + id + '&LibraryId=' + this.myid).then(response => {
          this.uperror = ''
          AXIOS.get(backendUrl + '/items/id?itemId=' + id).then(response => {
            this.response = response.data
            this.item = response.data
            this.uperror = ''
          }).catch(msg => {
            console.log(msg.response.data)
            console.log(msg.response.status)
            this.uperror = msg.response.data;
          })
          if (this.item.status !== 'Available') this.uperror = 'Cannot book this item!'
          else {
            AXIOS.post(backendUrl + '/booking/create?startDate=' + sdate + '&endDate=' + edate + '&type=' + Reservation + '&barcode=' + id + '&LibraryId=' + this.myid).then(response => {
              this.uperror = ''
              this.error = this.uperror
              this.$router.push('/')
            }).catch(msg => {
              console.log(msg.response.data)
              console.log(msg.response.status)
              this.uperror = msg.response.data;
            })
          }
        })
      }
    },
    back(){
      this.$router.push('/')
    },
    next(){
      this.$router.push('/home/Booking/UpdateBooking')
    }
  },
  data() {

    return {
      myid:'',
      error: '',
      response: [],
      items:[],
      uperror: '',
      item: [],
      heads:{

      },
      slide: 0,
      sliding: null
    }
  },

  mounted(){
    this.get();
  },

  beforeMount() {
    let split = document.cookie.split(';')
    let id = split[0].split('=');
    this.myid= id[1]
  }

}
