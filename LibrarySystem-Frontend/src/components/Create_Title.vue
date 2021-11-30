<template >
  <div id="Create_Title">

    <div class="header_img">
      <br>
      <img src="../assets/books_management.jpg" style="width:70vh; height:auto; padding-bottom: 2vh">
    </div>

    <div class="mainFrame">
      <div><h2><b>Add Title To Library</b></h2></div>
      <br>

      <div class="names">
        <label><b>Title Info: </b></label>
        <br>
        <br>
        <input type="text" v-model="titlename" id="titlename" placeholder="Title Name" class="form-control" required>
        <br>
        <br>
        <input type="text" v-model="pubdate" id="pubdate" placeholder="Publish Date" class="form-control" required>
        <br>
        <br>
        <input type="text" v-model="authorids" id="authorids" placeholder="AuthorIDs" class="form-control" required>
        <br>
      </div>

      <div class="buttonPersonalFrame inputPersLabel">
        <button class="btn-cancel" @click="cancel">Back
        </button>
        <button class="btn-success" @click="addTitle(titlename, pubdate, authorids)">Create Title
        </button>
      </div>
      <span v-if="error" style="color: red">Error: {{error}}</span>
      <span v-if="success" style="color: green">Added New Title: {{success}}</span>
    </div>
  </div>
</template>

<script src="./create_title_script.js">
export default {
  name: "Create_Title"
}
</script>

<style scoped>

#Create_Title {
  background-image: linear-gradient(to bottom right, #3eadcf, #abe9cd);
  padding-bottom: 80px;
}

.names {
  width: 24.75%;
  min-width: 400px;
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
