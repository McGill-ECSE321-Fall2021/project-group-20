<template >
  <div class="background-template">
  <div id="Update_Item" >
    <img src="../assets/library_source.jpg" style="width:80vh; height:auto; padding-bottom: 2vh">
    <h2><b>Update Item</b></h2>
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
    <br>
    <input type="text" v-model="status" id="status" class="form-control" required placeholder="Status">
    <br>
    <button class="btn btn-primary" @click="update(itemBarcode,status)">Update</button>
    <button class="btn btn-danger" @click="delet(itemBarcode)">Delete</button>
    <button class="btn btn-primary" @click="back()">Back</button>
  </div>
  </div>
</template>
<!--  <v-data-table-->
<!--    :headers="heads"-->
<!--    :items="items"-->
<!--    hide-actions-->
<!--    class="elevation-1"-->
<!--  >-->
<!--    <template slot="items" slot-scope="props">-->
<!--      <td>{{ props.item.name }}</td>-->
<!--      <td class="text-xs-right">{{ props.item.calories }}</td>-->
<!--      <td class="text-xs-right">{{ props.item.fat }}</td>-->
<!--      <td class="text-xs-right">{{ props.item.carbs }}</td>-->
<!--      <td class="text-xs-right">{{ props.item.protein }}</td>-->
<!--      <td class="text-xs-right">{{ props.item.iron }}</td>-->
<!--    </template>-->
<!--  </v-data-table>-->


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
  width: 44.75%;
  min-width: 400px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 10vh;
  padding-bottom: 20vh;
}
</style>
