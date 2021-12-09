<template>
<div id="Management" >
  <b-navbar toggleable="lg" variant="faded" type="light" >
    <b-navbar-brand href="/">Menu</b-navbar-brand>
  </b-navbar>
  <div class="header_img">
    <img src="../assets/LS.png" style="width:10%; height:auto; padding-bottom: 2vh">
  </div>



<!--  <div class="mainFrame">-->

    <div><h2><b>Add Item to Library Inventory</b></h2></div>
    <br>

<!--    <b-tabs content-class="mt-3" align="center">-->

      <div class="mainFrame">

        <!-- THIS IS THE TABS TO CHOOSE WHICH ITEM TO ADD -->
        <b-tabs content-class="mt-3" align="center">
          <div class="tabFrame">


            <div class="authorTable">
              <!--          <div><b>Authors selected: {{selected}}</b></div>-->
              <table class="table table-striped table-hover" id="formatted_author_table">
                <thead>
                <tr>
                  <th>
                    <label class="form-checkbox">
                      <input type="checkbox" v-model="selectAll" @click="selectAuthor">
                      <i class="form-icon"></i>
                    </label>
                  </th>
                  <th>First Name</th>
                  <th>Last Name</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="author in authors">
                  <td>
                    <label class="form-checkbox">
                      <input type="checkbox" :value="author.authorID" v-model="selectedAuthors" >
                      <i class="form-icon"></i>
                    </label>
                  </td>
                  <td>{{author.firstName}}</td>
                  <td>{{author.lastName}}</td>
                </tr>
                </tbody>
              </table>
            </div>


            <div class="titleFields">
              <div class="titleName">
                <label for="titleName">Item Name</label>
                <input type="text" v-model="titleName" id="titleName" class="form-control" placeholder="Name" required>
              </div>

              <div class="titlePubDate">
                <label for="titlePubDate">Item Publish Date</label>
                <input type="text" v-model="titlePubDate" id="titlePubDate" class="form-control" placeholder="mm/dd/yyyy"  required>
              </div>

              <div class="titleAuthors">
                <label>Item Authors</label>
                <div class="selectedAuthorID">{{selectedAuthors}}</div>
              </div>
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
                <button class="btn-success-acc" @click="createArchive(titleName, titlePubDate)">Create Archive
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
                <button class="btn-success-acc" @click="createBook(titleName,titlePubDate, isbncode, numberpages)">Create Book
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
                <button class="btn-success-acc" @click="createMovie(titleName, titlePubDate, lengthmovie)">Create Movie
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
                <button class="btn-success-acc" @click="createMusicAlbum(titleName, titlePubDate, lengthmusic)">Create Music Album
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
                <button class="btn-success-acc" @click="createNewsPaper(titleName, titlePubDate)">Create Newspaper
                </button>
              </div>
            </b-tab>
            <br>
            <br>
            <div class="buttonUpdateItem">
              <button class="btn-success-new-page" @click="openUpdateItemPage">Update an item</button>
              <button class="btn-success-new-page" @click="openCreateAuthorPage">Create new Author</button>
<!--              <button class="btn-success-new-page" @click="openCreateTitlePage">Create new title</button>-->
            </div>

<!--            <span v-if="error" style="color: red">Error: {{error}}</span>-->
<!--            <span v-if="success" style="color: green">Added New Item: {{success}}</span>-->
          </div>

        </b-tabs>



      </div>    <!-- main frame div -->


  </div>

</template>

<script src="./management_view_script.js">
export default {
  name: "Management"
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

#Management {
  background-image: linear-gradient(to bottom right, #3eadcf, #abe9cd);
  padding-bottom: 50vh;
}

.inputFrame {
  width: 24.75%;
  position: relative;
  margin: auto;
  display: block;
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

.btn-success-new-page {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #0cab11 ;
}


#formatted_author_table {
  margin-left: auto;
  margin-right: auto;
  padding-top: 1vh;
  max-width: 95%
}

.titleFields {
  width: 50%;
  margin: auto;
  display: block;
  position: center;
  padding-top: 1vh;
}

.titlePubDate {
  padding-top: 1vh;
}

.titleAuthors {
  padding-top: 1vh;
}
</style>
