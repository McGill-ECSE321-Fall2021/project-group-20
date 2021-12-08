<template>
  <div id="event_create">
    <b-navbar toggleable="lg" variant="faded" type="light" >
      <b-navbar-brand href="/">Menu</b-navbar-brand>
    </b-navbar>
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>
    <hr>
    <h2>Add an Event to our Glorious System</h2>
    <hr>
    <b-container class="input">
      <b-row>
        <b-col>
          <label for="eventname">Event name:</label>
          <input type="text" v-model="eventname" id="eventname" class="form-control" required>
        </b-col>
        <b-col>
          <label for="eventdate">Event Date (MM/DD/YYYY):</label>
          <input type="text" v-model="eventdate" id="eventdate" class="form-control" required>
        </b-col>
        <b-col>
          <label for="eventday">Weekday:</label>
          <input type="text" v-model="eventday" id="eventday" class="form-control" required>
        </b-col>
      </b-row><br>
      <b-row>
        <b-col>
          <label for="starTime">Start Time (HH:MM:SS):</label>
          <input type="text" v-model="starTime" id="starTime" class="form-control" required>
        </b-col>
        <b-col>
          <label for="endTime">End Time (HH:MM:SS):</label>
          <input type="text" v-model="endTime" id="endTime" class="form-control" required>
        </b-col>
        <b-col>
          <label for="employeeUsername">Employee Username:</label>
          <input type="text" v-model="employeeUsername" id="employeeUsername" class="form-control" required>
        </b-col>
      </b-row><br>
      <b-row>
        <b-col>

        </b-col>
        <b-col>

        </b-col>
        <b-col>

        </b-col>

      </b-row>
    </b-container>

    <div class="error">
      <p>
        <span v-if="error" style="color: red">Error: {{error}}</span>
      </p>
    </div>

    <div class="buttons">
      <button class="btn" @click="createeventbyEmployee(eventname, eventdate, eventday, starTime, endTime, employeeUsername)">Create Event</button>
    </div>

  </div>
</template>

<script src="./Event_create_script.js">
export default {
  name: "Event_create_script"
}
</script>

<style scoped>

#event_create{
  background-color: #4f4b57;
  color: #F5B7B1;
  position: fixed;
  overflow: hidden;
}
.msg {
  padding-top: 2vh;
  padding-bottom: 5vh;
}

.input {
  padding-bottom: 5vh;
  text-align: left;
}

.buttons {

  padding-bottom: 100%;
}
.btn{
  background-color: #333333;
  color: #F5B7B1;
  border-color: #F5B7B1;


}
.btn:active{
  background-color: #333333;
  box-shadow: 0 5px #F5B7B1;
  transform: translateY(4px);

}
.error {
  padding-bottom: 5vh;
}
</style>
