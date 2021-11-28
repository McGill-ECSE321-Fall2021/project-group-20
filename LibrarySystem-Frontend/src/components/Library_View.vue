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
      <p>{{civic}} {{street}}</p>
      <p>{{city}}, {{province}}</p>
      <p>{{postalCode}}</p>
      <p>{{country}}</p>
    </div>
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
  padding-top: 15vh;
  padding-left: 8vw;
  padding-right: 81vw;
}

.centered {
  padding-top: 2vh;
  text-align: center;
}
</style>
