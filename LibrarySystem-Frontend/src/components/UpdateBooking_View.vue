<template>
  <div id="UpdateBooking_View">
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>





    <div class="jumbotron jumbotron-fluid">
      <div class="container">
        <h1 class="display-4">At the service of our commmunity</h1>
        <p class="lead">This is your personal Booking Space!!Keep track of all your items!!!</p>
      </div>
    </div>




    <hr>
    <h2>All of your bookings</h2>
    <div class="HI">

      <table style="margin-left:auto; margin-right:auto">

        <tr>
          <th>Booking ID</th>
          <th>Start Date</th>
          <th> End Date </th>
          <th>Booking Type</th>
          <th>Item</th>
          <th>User</th>

          <!--<th>Edit</th>-->
        </tr>
        <tr v-for="booking in bookings">
          <td>{{booking.bookingID}}</td>
          <td>{{booking.startDate}}</td>
          <td>{{booking.endDate}}</td>
          <td>{{booking.type}}</td>
          <td>Title:{{booking.item.title.name}}   BarCode:{{booking.item.itemBarcode}} </td>
          <td>{{booking.user.firstName }} {{booking.user.lastName}}</td>







          <!--<td>
            <button v-on:click="updateEvent(event.name)">Edit</button>
          </td>-->
        </tr>
      </table>
      <span v-if="error" style="color:red">Error: {{error}} </span>



    </div>

    <hr>







  </div>
</template>
<script src="./updatebooking_view_script.js">
export default {
  name: "updatebooking_view_script"
}
</script>

<style scoped>
#UpdateBooking_View {
  background-color:  #fec8cd;

;
  color: #333333;
}
.jumbotron-fluid{

  background-color: #7a6e6e;
;

}
table {
  border-collapse: separate;
  border-spacing: 50px 0;
  padding-bottom: 100%;
  background-color:  #fec8cd;

}

td {
  padding: 10px 0;
}


</style>
