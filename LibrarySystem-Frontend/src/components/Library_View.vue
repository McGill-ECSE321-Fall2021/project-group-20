<template>
  <div id="library_view">
    <div class="bar">
      <b-navbar toggleable="lg" variant="faded" type="light" >
        <b-navbar-brand href="/">Menu</b-navbar-brand>
      </b-navbar>
    </div>
    <div class="head">
      <br>
      <br>
      <h1>Library Information</h1>
      <br>
      <br>
    </div>

    <div>
      <img src="../assets/Public_Library2.jpg" style="width:60vh; height:auto;">
    </div>
    <div class="block">
      <div class="inline_left">
        <div class="address">
          <p><b>Your library is located at:</b></p>
          <div class="centered">
            <p><br>{{civic}} {{street}}, {{city}}, {{province}}, {{postalCode}}, {{country}}</p><br><br>
            <p><b>Upcoming Events:</b></p>
            <table style="margin-left:auto; margin-right:auto">
              <tr>
                <th>Name</th>
                <th>Date</th>
                <th>Weekday</th>
                <th>Start</th>
                <th>End</th>

              </tr>
              <tr v-for="event in events">
                <td>{{event.name}}</td>
                <td>{{event.eventDate}}</td>
                <td>{{event.eventhour.weekday}}</td>
                <td>{{event.eventhour.startTime}}</td>
                <td>{{event.eventhour.endTime}}</td>

              </tr>
            </table>
          </div>
        </div>
      </div>
      <div class="inline_right">
        <div class="split openingHours">
          <p><b>Your library is open on:</b></p><br>
          <table>
            <tr>
              <th>Day</th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th>Open</th>
              <th></th>
              <th></th>
              <th></th>
              <th></th>
              <th>Close</th>
            </tr>
            <tr v-for="hour in hours">
              <td>{{hour.weekday}}</td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td>{{hour.startTime}}</td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td>{{hour.endTime}}</td>
            </tr>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./library_view_script.js">
export default {
  name: "library_view"
}
</script>

<style scoped>

#library_view {
  font-weight: bold;
  background-image: linear-gradient(to bottom right, #93A5CF, #E4EfE9);
  padding-bottom: 40vh;
}


.block {
  width: 100%;
  display: block;
  padding-top: 5vh;
}

.inline_left {
  width: 50%;
  float: left;
  display: inline-block;
}

.inline_right {
  width: 50%;
  float: right;
  display: inline-block;
}

.address {
  left: 0;
  text-align: center;
}

.openingHours {
  right: 0;
}

table {
  margin-left: auto;
  margin-right: auto;
  width: 20px;
  border-collapse: separate;
  border-spacing: 30px 0;
  position: relative;
}
</style>
