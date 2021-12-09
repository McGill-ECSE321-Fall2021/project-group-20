<template>
    <div id="Create_Author">
      <b-navbar toggleable="lg" variant="faded" type="light" >
        <b-navbar-brand href="/">Menu</b-navbar-brand>
      </b-navbar>
      <div class="header_img">
        <br>
        <img src="../assets/author.jpg" style="width:70%; height:auto; padding-bottom: 1vh">
      </div>
      <div class="mainFrame">

        <div><h2><b>Add Author To Library</b></h2></div>
        <br>


          <div class="names">
            <label><b>Author Info: </b></label>
            <br>
            <br>
            <input type="text" v-model="firstname" id="firstname" placeholder="First Name" class="form-control" required>
            <br>
            <br>
            <input type="text" v-model="lastname" id="lastname" placeholder="Last Name" class="form-control" required>
            <br>
            <br>
            <br>
          </div>

        <div class="buttonPersonalFrame inputPersLabel">
          <button class="btn-cancel" @click="cancel">Back
          </button>
          <button class="btn-success" @click="addAuthor(firstname, lastname)">Create Author
          </button>
        </div>


        <span v-if="error" style="color: red">Error: {{error}}</span>
        <span v-if="success" style="color: green">Added New Author: {{success}}</span>
      </div>
    </div>
</template>

<script src="./create_author_script.js">
export default {
  name: "Create_Author"
}
</script>

<style scoped>

#Create_Author {
  background-image: linear-gradient(to bottom right, #3eadcf, #abe9cd);
  padding-bottom: 80px;
}

.names {
  width: 50%;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 2vh;
}

.btn-success {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #03a634;
}

.btn-cancel {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #ab0303;
}

</style>
