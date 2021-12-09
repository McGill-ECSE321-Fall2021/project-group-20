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
      <button class="btn btn-primary" @click="Return(itemBarcode)">Return</button>
      <button class="btn btn-primary" @click="back()">Back</button>
      <p>
        <br><span v-if="uperror" style="color: red">Error: {{uperror}}</span>
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
  width: 50%;
  min-width: 400px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 10vh;
  padding-bottom: 20vh;
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
  padding-top: 2vh;
}
</style>
