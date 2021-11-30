<template>
  <div id="hour_edit">
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>
    <div class="msg">
      <h3>Edit Library Hours</h3>
    </div>
    <div class="table">
      <table>
        <tr>
          <th>Day</th>
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
    <div class="choose">
      <label for="select">Select day to update:</label><br>
      <select v-model="select" name="select" id="select">
        <option v-for="hour in hours" :value="hour.hourId">{{hour.weekday}}</option>
      </select>
    </div>
    <div class="update_fields">
      <label for="start">Enter new start time:</label>
      <input type="text" v-model="start" class="form-control" id="start" required><br>
      <label for="endTime">Enter new end time (later than start time):</label>
      <input type="text" v-model="endTime" class="form-control" id="endTime">
    </div>
    <div class="buttons">
      <button class="btn btn-danger" @click="cancel">Cancel</button>
      <button class="btn btn-success" @click="confirm(start, endTime)">Confirm</button>
    </div>
    <div class="error_msg">
      <p>
        <span v-if="error" style="color: red">{{error}}</span>
      </p>
    </div>
  </div>
</template>

<script src="./system_hour_edit_script.js">
export default {
  name: "system_hour_edit"
}
</script>

<style scoped>
table {
  margin-left: auto;
  margin-right: auto;
}
.msg {
  padding-bottom: 3vh;
}

.update_fields {
  align-content: center;
  padding-left: 40%;
  padding-right: 40%;
}

.choose {
  padding-bottom: 3vh;
}

.buttons {
  padding-top: 3vh;
}
</style>
