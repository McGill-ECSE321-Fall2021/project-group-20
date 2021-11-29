<template>
  <div id="fire">
    <div class="header_img">
      <img src="../assets/LS.png" style="width:20vh; height:auto;">
    </div>
    <div class="msg">
      <h2>Fire Librarian</h2>
    </div>
    <div class="table">
      <p>List of current librarians:</p>
      <table>
        <tr>
          <th>ID</th>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Username</th>
          <th>Email</th>
          <th>Role</th>
        </tr>
        <tr v-for="account in accounts">
          <td>{{account.libraryCardID}}</td>
          <td>{{account.firstName}}</td>
          <td>{{account.lastName}}</td>
          <td>{{account.username}}</td>
          <td>{{account.email}}</td>
          <td>{{account.role}}</td>
        </tr>
      </table>
    </div>
    <div class="choose">
      <label for="select">Select librarian to fire in system:</label><br>
      <select v-model="select" name="select" id="select">
        <option v-for="account in accounts" :value="account.libraryCardID">{{account.username}}</option>
      </select>
    </div>
    <div class="confirm">
      <label for="confirmSel">Confirm librarian to fire:</label><br>
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
.msg {
  padding-bottom: 3vh;
}

table {
  margin-left: auto;
  margin-right: auto;
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
</style>
