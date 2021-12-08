<template >
  <div class="background-template">
    <b-navbar toggleable="lg" variant="faded" type="light" >
      <b-navbar-brand href="/">Menu</b-navbar-brand>
    </b-navbar>
    <div id="Update_Item" >
      <img src="../assets/library_source.jpg" style="width:100%; height:auto; padding-bottom: 1vh">
      <h2><b>Book Item</b></h2>
      <template >
        <input type="input" v-model="input" id="input" class="form-control" required placeholder="Search">
        <br>
        <div class="search">
          <button class="btn btn-primary" @click="get(input)">Search</button>
        </div>
      </template>
      <template>
        <div class="table">
          <table>
            <tr>
              <th>Status</th>
              <th>Item Barcode</th>
              <th>Title</th>
              <th>Main Author</th>
            </tr>
            <tr v-for="item in items">
              <td>{{item.status}}</td>
              <td>{{item.itemBarcode}}</td>
              <td>{{item.title.name}}</td>
              <td>{{item.title.author[0].firstName}} {{item.title.author[0].lastName}}</td>
            </tr>
          </table>
        </div>
      </template>
      <input type="text" v-model="itemBarcode" id="itemBarcode" class="form-control" required placeholder="ItemBarcode">
      <input type="text" v-model="Sdate" id="Sdate" class="form-control" required placeholder="Start (mm/dd/yyyy)">
      <input type="text" v-model="Edate" id="Edate" class="form-control" required placeholder="End (mm/dd/yyyy)">
      <label for="Type">Type:</label><br>
      <select v-model="Reservation" name="Type" id="Type">
        <option selected value="Borrow">Borrow</option>
        <option value="Reservation">Reservation</option>
      </select><br><br>
      <div class="error">
        <p>
          <span v-if="uperror" style="color: red">Error: {{uperror}}</span>
        </p>
      </div>
      <button class="btn btn-primary" @click="Book(Sdate,Edate,Reservation,itemBarcode)">Book</button>
      <button class="btn btn-primary" @click="back()">Back</button>
      <!--<button class="btn btn-primary" @click="next()">Browse</button>-->
    </div>
  </div>
</template>

<script src="./booking_view_script.js">
export default {
  name: "booking_view_script"
}
</script>

<style scoped>

.background-template {
  background-image: linear-gradient(to bottom right, #3eadcf, #abe9cd);
  padding-bottom: 80px;
}

#Update_Item {
  position: center;
  margin-left: auto;
  margin-right: auto;
  padding-top: 10vh;
  padding-bottom: 20vh;
}

table {
  margin-left: auto;
  margin-right: auto;
  max-width: 95%;
}

#itemBarcode, #Sdate, #Edate, #input {
  max-width: 85%;
  align-content: center;
  margin: auto;
  display: block;
}
</style>
