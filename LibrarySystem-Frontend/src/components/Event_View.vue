<template>
  <div id="event_view">
    <b-navbar toggleable="lg" variant="faded" type="light" >
      <b-navbar-brand href="/">Menu</b-navbar-brand>
    </b-navbar>
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>





    <h1>At the service of our commmunity</h1><br>

    <div class="event_buttons">

      <button class="btn btn-primary" @click="updateEvent">Delete an Event</button>
      <button class="btn btn-primary" @click="CreateEvent">Create an Event</button>

    </div>


    <hr>
    <h2>All of our events</h2>
    <div class="HI">

      <table style="margin-left:auto; margin-right:auto">

        <tr>
          <th>Event Name</th>
          <th>Date</th>
          <th> Weekday </th>
          <th>Start</th>
          <th>End</th>
          <!--<th>Edit</th>-->
        </tr>
        <tr v-for="event in events">
          <td>{{event.name}}</td>
          <td>{{event.eventDate}}</td>
          <td>{{event.eventhour.weekday}}</td>
          <td>{{event.eventhour.startTime}}</td>
          <td>{{event.eventhour.endTime}}</td>





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

<script src="./event_view_script.js">
export default {
  name: "event_script_view"
}
</script>

<style scoped>
#event_view {
  background-color: #73e2f5

;
  color: #333333;
}
.jumbotron-fluid{

  background: #73e2f5
;

}
.event_buttons{
  position: relative;
  align-content: center;
  padding-bottom: 3vh;

}
.btn-primary{
  background: #241a4e

;


}
table {
  border-collapse: separate;
  border-spacing: 20px 0;
  background-color: #73e2f5;
  padding-bottom: 100%;
  max-width: 100%;

}

td {
  padding: 10px 0;
}


.header_img{
  position: center;
}


</style>
