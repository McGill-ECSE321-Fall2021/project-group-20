<template >
  <div class="background-template">
    <div id="EmoloyeeBooking_View" >
      <img src="../assets/library_source.jpg" style="width:80vh; height:auto; padding-bottom: 2vh">
      <h2><b>Book Item</b></h2>
      <template >
        <input type="input" v-model="input" id="input" class="form-control" required placeholder="Search">
        <br>
        <button class="btn btn-primary" @click="get(input)">Search</button>
      </template>
      <template>
        <div>
          <b-table striped hover :items="items"></b-table>
        </div>
      </template>
      <input type="text" v-model="itemBarcode" id="itemBarcode" class="form-control" required placeholder="ItemBarcode">
      <button class="btn btn-primary" @click="Return(itemBarcode)">Return</button>
      <button class="btn btn-primary" @click="back()">Back</button>
      <p>
        <span v-if="error" style="color: red">Error: {{error}}</span>
      </p>
    </div>
  </div>
</template>

<script src="./EmployeeBooking_script.js">
export default {
  name: "booking_view_script"
}
</script>

<style scoped>

.background-template {
  background-image: linear-gradient(to bottom right, #3eadcf, #abe9cd);
  padding-bottom: 219px;
}

#EmoloyeeBooking_View {
  width: 44.75%;
  min-width: 400px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 10vh;
  padding-bottom: 20vh;
}
</style>
