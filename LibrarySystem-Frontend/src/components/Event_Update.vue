<template>
  <div id="event_update">
    <b-navbar toggleable="lg" variant="faded" type="light" >
      <b-navbar-brand href="/">Menu</b-navbar-brand>
    </b-navbar>
    <div class="header_img">
      <img src="../assets/LS.png" style="width:50vh; height:50vh;">
    </div>

    <div class="mainFrame">
      <b-tabs content-class="mt-3" align="center">


        <b-tab title="Delete Event" class="mytabs">
          <div class="inputFrame">

            <div class="DeleteEvent inputPersLabel">
              <label for="dayToDelete" class="InputLabel"><b>The Date of the Event You Want To Delete (MM/DD/YYYY)</b></label>
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
    <div class="cancel">
      <button class="btn-success" @click="cancel">Cancel</button>
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
  background-image: linear-gradient(to bottom right, #3eadcf, #abe9cd);
  padding-bottom: 40vh;
}

.inputFrame {
  /*display: grid;*/
  /*grid-template-columns: repeat(2, 1fr);*/
  /*grid-template-rows: repeat(4, 1fr);*/
  /*width: 25%;*/
  /*min-width: 500px;*/
  /*position: relative;*/
  /*left: 50%;*/
  /*transform: translateX(-50%);*/
  /*padding-top: 2vh;*/
  /*padding-bottom: 0;*/

  width: 24.75%;
  min-width: 400px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 2vh;
}

.InputLabel {

  padding-bottom: 25px;

}

input[type=text] {
  /*padding: 12px 20px;*/
  /*margin: 8px 0;*/
  box-sizing: border-box;
  background-color: #fec8cd;
  color: #a30000;
  width: 24.75%;
  min-width: 400px;
  position: relative;
  left: 10%;
  /*border: 3px solid #333333;*/
  /*-webkit-transition: 0.5s;*/
  /*transition: 0.5s;*/
  outline: none;
}

#dayToDelete {
  padding-bottom: 2vh;
}

.btn-cancel-pers {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #ab0303;
}

.cancel {
  padding-top: 3vh;
}

</style>
