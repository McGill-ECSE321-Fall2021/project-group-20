<template>
  <div id="event_update">

    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>

    <div class="mainFrame">
      <b-tabs content-class="mt-3" align="center">


        <b-tab title="Delete the infidels from the system" class="mytabs">
          <div class="inputFrame">

            <div class="DeleteEvent inputPersLabel">
              <label for="dayToDelete">The Date of the Event You Want To Delete (MM/DD/YYYY)</label>
              <input type="text" v-model="dayToDelete" id="dayToDelete" class="form-control" required>
            </div>



          </div>

          <div class="buttonPersonalFrame inputPersLabel">
            <button class="btn-cancel-pers" @click="eventTodelete(dayToDelete)">Delete
            </button>

          </div>

        </b-tab>
      </b-tabs>

    </div>

  </div>
</template>

<script src=./Event_Update_script.js>
export default {
  name: "Event_Update_script"
}
</script>

<style scoped>
#event_update{
  background-color:  #fec8cd;
  //color: #f5b1cc;
  color:  #a30000;
  caret-color: #fec8cd;
  colorful-error-color:#2e7dde;
  colorful-error-extra-color: #2e7dde;
}

.inputFrame {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: repeat(4, 1fr);
  width: 25%;
  min-width: 500px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 2vh;
}

input[type=text] {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  background-color: #fec8cd;
  color: #a30000;
  border: 3px solid #333333;
  -webkit-transition: 0.5s;
  transition: 0.5s;
  outline: none;
}


</style>
