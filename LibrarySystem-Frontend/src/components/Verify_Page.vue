<template>
  <div id="verify_page">
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>
    <div class="msg">
      <h2><b>Verify Accounts</b></h2>
    </div>
    <img src="../assets/cyber-security.jpg" style="width:45vh; height:30vh;">
    <div class="table">
      <br>
      <h5><b>List of un-verified accounts:</b></h5>
      <table>
        <tr>
          <th>ID</th>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Username</th>
          <th>Email</th>
          <th>Fees</th>
        </tr>
        <tr v-for="account in accounts">
          <td>{{account.libraryCardID}}</td>
          <td>{{account.firstName}}</td>
          <td>{{account.lastName}}</td>
          <td>{{account.username}}</td>
          <td>{{account.email}}</td>
          <td>{{account.outstandingBalance}}</td>
        </tr>
      </table>
    </div>
    <div class="choose">
      <label for="select">Select account to verify:</label><br>
      <select v-model="select" name="select" id="select">
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
      <button class="btn btn-success" @click="verify">Verify Account</button>
    </div>
  </div>
</template>

<script src="./verify_page_script.js">
export default {
  name: "Verify_Page"
}
</script>

<style scoped>
.msg {
  padding-bottom: 5vh;
}

table {
  margin-left: auto;
  margin-right: auto;
  padding-bottom: 5vh;
}

.choose {
  align-content: center;
}

#verify_page {
  background-image: linear-gradient(to bottom right, #3eadcf, #abe9cd);
  padding-bottom: 50vh;
}

.btn-success {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #03a634;
}

.btn-danger {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #ab0303;
}
</style>
