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
  </div>
  <div class="address">
    <p><b>Our library is located at:</b></p>
    <div class="centered">
      <p><br>{{civic}} {{street}}, {{city}}, {{province}}, {{postalCode}}, {{country}}</p>
    </div>
  </div>
  <div class="openingHours">
    <p><b>Our library is open on:</b></p><br>
    <table>
      <tr>
        <th>Weekday</th>
        <th>Open</th>
        <th>Close</th>
      </tr>
      <tr v-for="hour in hours">
        <td>{{hour.weekday}}</td>
        <td>{{hour.startTime}}</td>
        <td>{{hour.endTime}}</td>
      </tr>
    </table>
  </div>
</div>
</template>

<script src="./library_view_script.js">
export default {
  name: "library_View"
}
</script>

<style scoped>
.address {
  text-align: center;
  padding-top: 5vh;
  padding-bottom: 10vh;
}

table {
  margin-left: auto;
  margin-right: auto;
}
</style>
