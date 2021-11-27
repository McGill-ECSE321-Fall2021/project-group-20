<template>
  <div id="event_view">

    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>





    <div class="jumbotron jumbotron-fluid">
      <div class="container">
        <h1 class="display-4">At the service of our commmunity</h1>
        <p class="lead">This is the event page!! See all the fun activities going on!</p>
      </div>
    </div>

    <div class="event_buttons">

      <button class="btn btn-primary" @click="updateEvent">Update an Event</button>
      <button class="btn btn-primary" @click="CreateEvent">Create an Event</button>

    </div>


    <hr>
    <h2>All of our events</h2>
    <table>
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
    <span v-if="errorEvent" style="color:red">Error: {{errorEvent}} </span>
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
  border-spacing: 50px 0;
  background-color: #73e2f5;
  padding-bottom: 100%;

}

td {
  padding: 10px 0;
}


.header_img{
  position: center;
}


</style>
