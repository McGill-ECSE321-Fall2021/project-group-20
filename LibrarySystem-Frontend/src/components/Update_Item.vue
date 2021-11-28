<template>
  <div id="Update_Item">
    <template>
      <b-input-group prepend="Search" v-model="name" id="name" class="mt-3">
        <b-form-input></b-form-input>
        <b-input-group-append>
          <b-button variant="info" @click="get">Button</b-button>
        </b-input-group-append>
      </b-input-group>
    </template>
    <template>
      <div>
        <b-table striped hover :items="items"></b-table>
      </div>
    </template>
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
