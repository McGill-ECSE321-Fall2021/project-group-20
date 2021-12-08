<template >
  <div class="background-template">
    <b-navbar toggleable="lg" variant="faded" type="light" >
      <b-navbar-brand href="/">Menu</b-navbar-brand>
    </b-navbar>
    <div id="EmoloyeeBooking_View" >
      <img src="../assets/library_source.jpg" style="width:100%; height:auto; padding-bottom: 2vh">
      <h2><b>Book Item</b></h2>
      <template >
        <input type="input" v-model="input" id="input" class="form-control" required placeholder="User ID">
        <br>
        <button class="btn btn-primary" @click="get(input)">Search</button>
      </template>
      <div class="table">
        <table>
          <tr>
            <th>Booking ID</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Type</th>
            <th>Item</th>
            <th>User</th>
          </tr>
          <tr v-for="item in items">
            <td>{{item.bookingID}}</td>
            <td>{{item.startDate}}</td>
            <td>{{item.endDate}}</td>
            <td>{{item.type}}</td>
            <td>{{item.item.title.name}} (Barcode: {{item.item.itemBarcode}})</td>
            <td>{{item.user.firstName}} {{item.user.lastName}} (ID: {{item.user.libraryCardID}})</td>
          </tr>
        </table>
      </div>
      <input type="text" v-model="itemBarcode" id="itemBarcode" class="form-control" required placeholder="ItemBarcode">
      </div>
    <div class="buttons">
      <button class="btn btn-primary" @click="Return(itemBarcode)">Return</button>
      <button class="btn btn-primary" @click="backup()">Back</button>
      <p>
        <br><span v-if="error" style="color: red">Error: {{error}}</span>
      </p>
    </div>
  </div>
</template>

<script src="./HeadLibrarianBookibng_View_script.js">
export default {
  name: "hdbooking_view_script"
}

</script>

<style scoped>

.background-template {
  background-image: linear-gradient(to bottom right, #3eadcf, #abe9cd);
  padding-bottom: 25vh;
}

#EmoloyeeBooking_View {
  position: center;
}
#input {
  width: 50%;
  height: auto;
  position: center;
  margin: auto;
  display: block;
}

#itemBarcode {
  width: 50%;
  height: auto;
  margin: auto;
  display: block;
}

table {
  margin-left: auto;
  margin-right: auto;
}

.table {
  padding-top: 3vh;
  padding-bottom: 2vh;
}

.buttons {
  padding-top: 5vh;
}

</style>
