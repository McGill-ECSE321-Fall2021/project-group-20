<template>
<div id="Management" >
  <div class="header_img">
    <img src="../assets/LS.png" style="width:20vh; height:auto; padding-bottom: 2vh">
  </div>

  <div class="mainFrame">

    <div><h2><b>Add Item to Library Inventory</b></h2></div>
    <br>
    <b-tabs content-class="mt-3" align="center">

      <div class="tables">

<!--      <div class="global">-->

        <label><b>List of Authors: </b></label>
        <br>

<!--        <table class="table">-->
<!--          <thead>-->
<!--          <tr>-->
<!--            <th scope="col">-->
<!--              <div class="form-check">-->
<!--                <input-->
<!--                  class="form-check-input"-->
<!--                  type="checkbox"-->
<!--                  value=""-->
<!--                  id="flexCheckDefault"-->
<!--                />-->
<!--              </div>-->
<!--            </th>-->
<!--            <th scope="col">Lorem</th>-->
<!--            <th scope="col">Ipsum</th>-->
<!--            <th scope="col">Dolor</th>-->
<!--          </tr>-->
<!--          </thead>-->
<!--          <tbody>-->
<!--          <tr>-->
<!--            <th scope="row">-->
<!--              <div class="form-check">-->
<!--                <input-->
<!--                  class="form-check-input"-->
<!--                  type="checkbox"-->
<!--                  value=""-->
<!--                  id="flexCheckDefault"-->
<!--                />-->
<!--              </div>-->
<!--            </th>-->
<!--            <td>Sit</td>-->
<!--            <td>Amet</td>-->
<!--            <td>Consectetur</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <th scope="row">-->
<!--              <div class="form-check">-->
<!--                <input-->
<!--                  class="form-check-input"-->
<!--                  type="checkbox"-->
<!--                  value=""-->
<!--                  id="flexCheckDefault"-->
<!--                />-->
<!--              </div>-->
<!--            </th>-->
<!--            <td>Adipisicing</td>-->
<!--            <td>Elit</td>-->
<!--            <td>Sint</td>-->
<!--          </tr>-->
<!--          <tr>-->
<!--            <th scope="row">-->
<!--              <div class="form-check">-->
<!--                <input-->
<!--                  class="form-check-input"-->
<!--                  type="checkbox"-->
<!--                  value=""-->
<!--                  id="flexCheckDefault"-->
<!--                />-->
<!--              </div>-->
<!--            </th>-->
<!--            <td>Hic</td>-->
<!--            <td>Fugiat</td>-->
<!--            <td>Temporibus</td>-->
<!--          </tr>-->
<!--          </tbody>-->
<!--        </table>-->


        <label><b>List of Titles: </b></label>

<!--        <label><b>Author Info: </b></label>-->
<!--        <input value="response.firstname" type="text" v-model="firstname" id="firstname" placeholder="First Name" class="form-control" required>-->
<!--        <input value="response.lastname" type="text" v-model="lastname" id="lastname" placeholder="Last Name" class="form-control" required>-->
<!--        <br>-->
<!--        <label><b>Title Info: </b></label>-->
<!--        <input v-model="titlename" id="titlename" placeholder="Title Name" class="form-control" required>-->
<!--        <input v-model="publishdate" id="publishdate" placeholder="Publish Date (mm/dd/yyyy)" class="form-control" required>-->
<!--        <br>-->
      </div>

      <b-tab title="Archive" active>
        <div class="inputFrame">
          <label><b>Item Status</b></label>
          <select name="item_status" id="archive_item_status">
            <option selected value="Available">Available</option>
            <option value="Damaged">Damaged</option>
          </select>
        </div>
        <br>
        <div class="buttonArchiveFrame inputLabel">
          <button class="btn-success-acc" @click="createArchive(firstname, lastname, titlename, publishdate, archive_item_status)">Create Archive
          </button>
        </div>
      </b-tab>

      <b-tab title="Book">
        <div class="inputFrame">
          <label><b>ISBN code:</b></label>
          <input v-model="isbncode" id="isbncode" placeholder="ISBN" class="form-control" required>
          <br>
          <label><b>Number of Pages: </b></label>
          <input v-model="numberpages" id="numberpages" placeholder="Number of pages" class="form-control" required>
          <br>
          <label><b>Item Status</b></label>
          <select name="item_status" id="book_item_status">
            <option selected value="Available">Available</option>
            <option value="Reserved">Reserved</option>
            <option value="Borrowed">Borrowed</option>
            <option value="Damaged">Damaged</option>
          </select>
        </div>
        <br>
        <div class="buttonBookFrame inputPersLabel">
          <button class="btn-success-acc" @click="createBook(firstname, lastname, titlename, publishdate, book_item_status, isbncode, numberpages)">Create Book
          </button>
        </div>

      </b-tab>
      <b-tab title="Movie" active>
        <div class="inputFrame">
          <label><b>Movie Length: </b></label>
          <input v-model="lengthmovie" id="lengthmovie" placeholder="Length in minutes" class="form-control" required>
          <br>
          <label><b>Item Status</b></label>
          <select name="movie_item_status" id="movie_item_status">
            <option selected value="Available">Available</option>
            <option value="Reserved">Reserved</option>
            <option value="Borrowed">Borrowed</option>
            <option value="Damaged">Damaged</option>
          </select>
        </div>
        <br>
        <div class="buttonMovieFrame inputLabel">
          <button class="btn-success-acc" @click="createMovie(firstname, lastname, titlename, publishdate, movie_item_status, lengthmovie)">Create Movie
          </button>
        </div>

      </b-tab>

      <b-tab title="Music Album" active>
        <div class="inputFrame">
          <label><b>Music Album Length: </b></label>
          <input v-model="lengthmusic" id="lengthmusic" placeholder="Length in minutes" class="form-control" required>
          <br>
          <label><b>Item Status</b></label>
          <select name="music_item_status" id="music_item_status">
            <option selected value="Available">Available</option>
            <option value="Reserved">Reserved</option>
            <option value="Borrowed">Borrowed</option>
            <option value="Damaged">Damaged</option>
          </select>
        </div>
        <br>
        <div class="buttonMusicAlbumFrame inputLabel">
          <button class="btn-success-acc" @click="createMusicAlbum(firstname, lastname, titlename, publishdate, music_item_status, lengthmusic)">Create Music Album
          </button>
        </div>

      </b-tab>

      <b-tab title="Newspaper" active>
        <div class="inputFrame">
          <label><b>Item Status</b></label>
          <select name="newspaper_item_status" id="newspaper_item_status">
            <option selected value="Available">Available</option>
            <option value="Damaged">Damaged</option>
          </select>
        </div>
        <br>
        <div class="buttonNewspaperFrame inputLabel">
          <button class="btn-success-acc" @click="createNewsPaper(firstname, lastname, titlename, publishdate, newspaper_item_status)">Create Newspaper
          </button>
        </div>
      </b-tab>
    </b-tabs>
    <span v-if="error" style="color: red">Error: {{error}}</span>
  </div>

</div>

</template>

<script>
export default {
  name: "management_view_script"
}
</script>

<style scoped>

/*.global {*/
/*  width: 24.75%;*/
/*  min-width: 400px;*/
/*  position: relative;*/
/*  left: 50%;*/
/*  transform: translateX(-50%);*/
/*  padding-top: 2vh;*/
/*}*/

.inputFrame {
  width: 24.75%;
  min-width: 400px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 1vh;
}

.btn-success-acc {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #a20000 ;
}



</style>
