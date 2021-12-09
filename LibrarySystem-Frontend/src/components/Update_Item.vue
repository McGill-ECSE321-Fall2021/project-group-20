<template >
  <div class="background-template">
  <div id="Update_Item" >
    <img src="../assets/library_source.jpg" style="width:100%; height:auto; padding-bottom: 2vh">
    <h2><b>Update Item</b></h2>
    <template >
      <input type="input" v-model="input" id="input" class="form-control" required placeholder="Search">
      <br>
      <button class="btn btn-primary" @click="get(input)">Search</button>
    </template>
    <div class="table">
      <table>
        <tr>
          <th>Status</th>
          <th>Barcode</th>
          <th>Title</th>
          <th>Booking</th>
        </tr>
        <tr v-for="item in items">
          <td>{{item.status}}</td>
          <td>{{item.itemBarcode}}</td>
          <td>{{item.title.name}}</td>
          <td>{{item.booking}}</td>
        </tr>
      </table>
    </div>
    <input type="text" v-model="itemBarcode" id="itemBarcode" class="form-control" required placeholder="ItemBarcode">
    <br>
    <input type="text" v-model="status" id="status" class="form-control" required placeholder="Status">
    <br>
    <button class="btn btn-primary" @click="update(itemBarcode,status)">Update</button>
    <button class="btn btn-danger" @click="delet(itemBarcode)">Delete</button>
    <button class="btn btn-primary" @click="back()">Back</button>
    <p>
      <span v-if="uperror" style="color: red">Error: {{uperror}}</span>
    </p>
  </div>
  </div>
</template>

<script src="./update_item_view.js">
export default {
  name: "Update_Item"
}
</script>

<style scoped>

.background-template {
  background-image: linear-gradient(to bottom right, #3eadcf, #abe9cd);
  padding-bottom: 80px;
}

#Update_Item {
  width: 60%;
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
  padding-top: 2vh;
}
</style>
