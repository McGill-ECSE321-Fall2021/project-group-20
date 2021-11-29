<template>
  <div id="Update_Item">
    <template>
      <input type="input" v-model="input" id="input" class="form-control" required placeholder="Search">
      <button class="btn btn-primary" @click="get(input)">Search</button>
    </template>
    <template>
      <div>
        <b-table striped hover :items="items"></b-table>
      </div>
    </template>
    <input type="text" v-model="itemBarcode" id="itemBarcode" class="form-control" required placeholder="ItemBarcode">
    <input type="text" v-model="status" id="status" class="form-control" required placeholder="Status">
    <button class="btn btn-primary" @click="update(itemBarcode,status)">Update</button>
    <button class="btn btn-danger" @click="delet(itemBarcode)">Delete</button>
    <button class="btn btn-primary" @click="back()">Back</button>

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
  name: "management_view_script"
}
</script>

<style scoped>


</style>
