<template>
<div id="Management" >
  <br>
  <br>
  <div class="Management">
    <h2> <b>Library System Inventory</b></h2>
  </div>
  <br>
  <br>
  <br>
  <div>

    <div id="main_picture_container">
      <img src="../assets/books_management.jpg" width="304" height="236">
      <img src="../assets/film_management.jpg" width="304" height="236">
      <img src="../assets/music_management.jpg" width="304" height="236">
    </div>

    <br>
    <br>

    <div class="seb">
      <form>
        <label><b>Author:</b></label>
        <input type="text" v-model="firstname" id="firstname" class="form-control" placeholder="First Name" required>
        <br>
        <input type="text" v-model="lastname" id="lastname" class="form-control" placeholder="Last Name" required>
        <br>
        <br>
<!--        <br>-->
<!--        <br>-->
<!--        <label><b>Item:</b></label>-->
<!--&lt;!&ndash;        <input type="text" v-model="itemstatus" id="itemstatus" class="form-control" placeholder="Item Status" required>&ndash;&gt;-->
<!--        <select class="form-select" aria-label="Default select example">-->
<!--          <option selected>Open this select menu</option>-->
<!--          <option value="Available">Available</option>-->
<!--          <option value="Reserved">Reserved</option>-->
<!--          <option value="Borrowed">Borrowed</option>-->
<!--          <option value="Damaged">Damaged</option>-->
<!--        </select>-->
<!--        <br>-->
<!--        <br>-->
<!--        <button class="btn btn-danger" id="create-button" @click="create">Create</button>-->
        <br>
      </form>
      <form>
        <br>
        <br>
                <label><b>Item:</b></label>
                <select class="form-select" aria-label="Default select example">
                  <option selected>Open this select menu</option>
                  <option value="Available">Available</option>
                  <option value="Reserved">Reserved</option>
                  <option value="Borrowed">Borrowed</option>
                  <option value="Damaged">Damaged</option>
                </select>

      </form>
      <form>
        <label><b>Title:</b></label>
        <input type="text" v-model="titlename" id="titlename" class="form-control" placeholder="Title Name" required>
        <br>
        <input type="text" v-model="publishdate" id="publishdate" class="form-control" placeholder="Publish Date" required>
      </form>
    </div>

    <div>
      <button class="btn btn-danger" id="create-button" @click="create">Create</button>
    </div>




<!--    <b-container class="input">-->
<!--      <b-row>-->
<!--        <b-col>-->
<!--          <label>Firstname:</label>-->
<!--          <input type="text" v-model="firstname" id="firstname" class="form-control" required>-->
<!--        </b-col>-->
<!--        <b-col>-->
<!--          <label>Lastname:</label>-->
<!--          <input type="text" v-model="lastname" id="lastname" class="form-control" required>-->
<!--        </b-col>-->
<!--      </b-row>-->
<!--      <br>-->
<!--      <b-row>-->
<!--        <b-col>-->
<!--          <label for="civic">Civic Number:</label>-->
<!--          <input type="text" v-model="civic" id="civic" class="form-control" required>-->
<!--        </b-col>-->
<!--        <b-col>-->
<!--          <label for="street">Street:</label>-->
<!--          <input type="text" v-model="street" id="street" class="form-control" required>-->
<!--        </b-col>-->
<!--        <b-col>-->
<!--          <label for="city">City:</label>-->
<!--          <input type="text" v-model="city" id="city" class="form-control" required>-->
<!--        </b-col>-->
<!--      </b-row>-->
<!--      <br>-->
<!--      <b-row>-->
<!--        <b-col>-->
<!--          <label for="username">Username:</label>-->
<!--          <input type="text" v-model="username" id="username" class="form-control" required>-->
<!--        </b-col>-->
<!--        <b-col>-->
<!--          <label for="password">Password:</label>-->
<!--          <input type="password" v-model="password" id="password" class="form-control" required>-->
<!--        </b-col>-->
<!--      </b-row>-->
<!--    </b-container>-->



<!--    <b-navbar toggleable="lg" type="dark" variant="info" >-->

<!--        <b-navbar-toggle target="nav-collapse">-->
<!--        </b-navbar-toggle>-->

<!--        <b-collapse id="nav-collapse" is-nav>-->
<!--          <b-navbar-nav class="mx-auto">-->
<!--            <b-nav-item-dropdown text="Lang" left>-->
<!--              <b-dropdown-item href="#">Author</b-dropdown-item>-->
<!--              <b-dropdown-item href="#">Item</b-dropdown-item>-->
<!--              <b-dropdown-item href="#">RU</b-dropdown-item>-->
<!--              <b-dropdown-item href="#">FA</b-dropdown-item>-->
<!--            </b-nav-item-dropdown>-->
<!--            <b-nav-form >-->
<!--              <b-form-input size="sm" class="mr-sm-2 w-100%" placeholder="Search"></b-form-input>-->
<!--              <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>-->
<!--            </b-nav-form>-->
<!--          </b-navbar-nav>-->

<!--          &lt;!&ndash; Right aligned nav items &ndash;&gt;-->

<!--        </b-collapse>-->
<!--      </b-navbar>-->

<!--  <b-container >-->
<!--    <b-row>-->
<!--&lt;!&ndash;      <b-col align-h="center" class="input_columns">&ndash;&gt;-->
<!--&lt;!&ndash;        <select v-model="selected" class="select">&ndash;&gt;-->
<!--&lt;!&ndash;          <option disabled value=""></option>&ndash;&gt;-->
<!--&lt;!&ndash;          <option>Archive</option>&ndash;&gt;-->
<!--&lt;!&ndash;          <option>Author</option>&ndash;&gt;-->
<!--&lt;!&ndash;          <option>Book</option>&ndash;&gt;-->
<!--&lt;!&ndash;          <option>Item</option>&ndash;&gt;-->
<!--&lt;!&ndash;          <option>Movie</option>&ndash;&gt;-->
<!--&lt;!&ndash;          <option>Music Album</option>&ndash;&gt;-->
<!--&lt;!&ndash;          <option>Newspaper</option>&ndash;&gt;-->
<!--&lt;!&ndash;          <option>Title</option>&ndash;&gt;-->
<!--&lt;!&ndash;        </select>&ndash;&gt;-->
<!--&lt;!&ndash;        <span>{{ selected }}</span>&ndash;&gt;-->
<!--&lt;!&ndash;      </b-col>&ndash;&gt;-->
<!--      <b-col align-h="center" class="input_columns" md="auto">-->
<!--        <input type="text" v-model="input" id="search" class="form-control" required>-->
<!--      </b-col>-->
<!--      <b-col align-h="center" class="input_columns" md="auto">-->
<!--        <button></button>-->
<!--        </b-col>-->
<!--    </b-row>-->
<!--    <br>-->
<!--  </b-container>-->
  </div>

</div>

</template>

<script>
export default {
  name: "management_view_script"
}
</script>

<style scoped>
.select {
  padding-top: 2vh;
}

.input_columns {
  padding-right: 0;
  padding-left: 0;
  width : 100px;
  display: inline;
}

.padding-0 {
  padding-right: 0;
  padding-left: 0;
}

#Management {
  background-color: rgb(255, 216, 167);
}

#main_picture_container {
  display:flex;
  justify-content: space-evenly;
}

.seb {
  display:flex;
  justify-content: space-evenly;
  width:100em;
}

#create-button {
  border-radius: 25%;
}

</style>
