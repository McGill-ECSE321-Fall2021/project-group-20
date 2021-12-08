<template>
  <div id="fire">
    <b-navbar toggleable="lg" variant="faded" type="light" >
      <b-navbar-brand href="/">Menu</b-navbar-brand>
    </b-navbar>
    <div class="header_img">
      <img src="../assets/LS.png" style="width:30%; height:auto; padding-bottom: 4vh">
    </div>
    <div class="msg">
      <h2><b>Fire Librarian</b></h2>
    </div>
    <div class="table">
      <h5><b>List of current librarians:</b></h5>
      <table>
        <tr>
          <th>ID</th>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Username</th>
          <th>Role</th>
        </tr>
        <tr v-for="account in accounts">
          <td>{{account.libraryCardID}}</td>
          <td>{{account.firstName}}</td>
          <td>{{account.lastName}}</td>
          <td>{{account.username}}</td>
          <td>{{account.role}}</td>
        </tr>
      </table>
    </div>
    <div class="choose">
      <label for="select"><b>Select librarian to fire in system:</b></label><br>
      <select v-model="select" name="select" id="select">
        <option v-for="account in accounts" :value="account.libraryCardID">{{account.username}}</option>
      </select>
    </div>
    <div class="confirm">
      <label for="confirmSel"><b>Confirm librarian to fire:</b></label><br>
      <select v-model="confirmSel" name="confirmSel" id="confirmSel">
        <option v-for="account in accounts" :value="account.libraryCardID">{{account.username}}</option>
      </select>
    </div>
    <div class="error_msg">
      <p>
        <span v-if="error" style="color: red">{{error}}</span>
      </p>
    </div>
    <div class="verify_button">
      <button class="btn btn-danger" @click="cancel">Cancel</button>&nbsp
      <button class="btn btn-success" @click="fireL">Fire Librarian</button>
    </div>
  </div>
</template>

<script src="./fire_script.js">
export default {
  name: "fire"
}
</script>

<style scoped>

#fire {

  background-image: linear-gradient(to bottom right, #11998E, #38EF7D);
  padding-bottom: 50vh;

}


.msg {
  padding-bottom: 3vh;
}

table {
  margin-left: auto;
  margin-right: auto;
  max-width: 95%;
}

.table {
  padding-bottom: 3vh;

}

.confirm {
  padding-bottom: 2vh;
}

.verify_button {
  padding-top: 3vh;
}

.btn-danger {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #ab0303;
}

.btn-success{
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #14820c;
}
</style>
