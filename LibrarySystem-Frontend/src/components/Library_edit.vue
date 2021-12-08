<template>
  <div id="library_view">
    <div class="bar">
      <b-navbar toggleable="lg" variant="faded" type="light" >
        <b-navbar-brand href="/">Menu</b-navbar-brand>
      </b-navbar>
    </div>
    <div class="head">
      <h2><b>Library Information</b></h2>
      <br>
    </div>
    <div>
      <img src="../assets/Public_Library.jpg" style="width:100%; height:70%;">
    </div>
    <div class="block">
      <div class="inline_left">
        <div class="address">
          <p><b>Your library is located at:</b></p>
          <div class="centered">
            <p>{{civic}} {{street}}, {{city}}, {{province}}, {{postalCode}}, {{country}}</p>
          </div>
        </div>
      </div>
      <div class="inline_right">
        <div class="split openingHours">
          <p><br><b>Your library is open on:</b></p>
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
    <div class="edit">
      <button class="btn btn-success" @click="openAddressEdit">Edit Address</button>
      <button class="btn btn-success" @click="openHoursEdit">Edit Opening Hours</button>
    </div>
  </div>
</template>

<script src="./library_edit_script.js">
export default {
  name: "library_edit"
}
</script>

<style scoped>

#library_view {
  font-weight: bold;
  background-image: linear-gradient(to bottom right, #93A5CF, #E4EfE9);
  background-size: cover;
  overflow: hidden;
  position: fixed;
  padding-bottom: 30vh;

}

.block {
  width: 100%;
  display: block;
  padding-top: 1vh;
}

.address {
  left: 0;
  text-align: center;
}

.openingHours {
  right: 0;
}

.edit {
  padding-top: 5vh;
}

table {
  margin-left: auto;
  margin-right: auto;
  width: 20px;
}

.btn-success {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #03a634;}
</style>
