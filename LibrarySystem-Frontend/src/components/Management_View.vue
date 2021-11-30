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
<!--          <div><b>Author selected: {{selected}}</b></div>-->

<!--          <table class="table table-striped table-hover" id="formatted_author_table">-->
<!--            <thead>-->
<!--            <tr>-->
<!--              <th>-->
<!--                <label class="form-checkbox">-->
<!--                  <input type="checkbox" v-model="selectAllAuthors" @click="selectAuthor">-->
<!--                  <i class="form-icon"></i>-->
<!--                </label>-->
<!--              </th>-->
<!--              <th>AuthorID</th>-->
<!--              <th>firstname</th>-->
<!--              <th>lastname</th>-->
<!--            </tr>-->
<!--            </thead>-->
<!--            <tbody>-->
<!--            <tr v-for="i in items">-->
<!--              <td>-->
<!--                <label class="form-checkbox">-->
<!--                  <input type="checkbox" :value="i.AuthorID" v-model="selected">-->
<!--                  <i class="form-icon"></i>-->
<!--                </label>-->
<!--              </td>-->
<!--              <td>{{i.AuthorID}}</td>-->
<!--              <td>{{i.firstname}}</td>-->
<!--              <td>{{i.lastname}}</td>-->
<!--            </tr>-->
<!--            </tbody>-->
<!--          </table>-->


        <h4><b>Title ID: </b></h4>
        <br>
        <input type="text" v-model="titleid" id="titleid" placeholder="TitleID" class="form-control" required>
        <br>

<!--        <div><b>Title selected: {{selected}}</b></div>-->
<!--        <table class="table table-striped table-hover" id="formatted_title_table">-->
<!--          <thead>-->
<!--          <tr>-->
<!--            <th>-->
<!--              <label class="form-checkbox">-->
<!--                <input type="checkbox" v-model="selectAllTitles" @click="selectTitle">-->
<!--                <i class="form-icon"></i>-->
<!--              </label>-->
<!--            </th>-->
<!--            <th>TitleID</th>-->
<!--            <th>TitleName</th>-->
<!--            <th>PublishDate</th>-->
<!--          </tr>-->
<!--          </thead>-->
<!--          <tbody>-->
<!--          <tr v-for="i in items">-->
<!--            <td>-->
<!--              <label class="form-checkbox">-->
<!--                <input type="checkbox" :value="i.TitleID" v-model="selected">-->
<!--                <i class="form-icon"></i>-->
<!--              </label>-->
<!--            </td>-->
<!--            <td>{{i.TitleID}}</td>-->
<!--            <td>{{i.TitleName}}</td>-->
<!--            <td>{{i.PublishDate}}</td>-->
<!--          </tr>-->
<!--          </tbody>-->
<!--        </table>-->

        </div>

<!--        <div id="Author-List-Search">-->
<!--          <template>-->
<!--            <input type="input" v-model="input" id="authorInput" class="form-control" required placeholder="Search">-->
<!--            <button class="btn btn-primary" @click="get(authorInput)">Search</button>-->
<!--            <button class="btn btn-danger" @click="addNewAuthor(itemBarcode,status)">Add new Author</button>-->
<!--          </template>-->
<!--          <template>-->
<!--            <div>-->
<!--              <b-table :items="items" :fields="fields" striped responsive="sm">-->
<!--                <template #cell(show_details)="row">-->
<!--                  &lt;!&ndash; We call the toggleDetails function on @change &ndash;&gt;-->
<!--                  <b-form-checkbox v-model="row.detailsShowing" @change="row.toggleDetails"></b-form-checkbox>-->
<!--                </template>-->
<!--                <template #row-details="row">-->
<!--                  <b-card>-->
<!--                    <b-row class="mb-2">-->
<!--                      <b-col sm="3" class="text-sm-right"><b>Works:</b></b-col>-->
<!--                      <b-col>{{ row.item.title }}</b-col>-->
<!--                    </b-row>-->
<!--                    <b-button size="sm" @click="row.toggleDetails">Hide Details</b-button>-->
<!--                  </b-card>-->
<!--                </template>-->
<!--              </b-table>-->
<!--            </div>-->
<!--            <br>-->

<!--          </template>-->
<!--        </div>-->

<!--        <label><b>List of Titles: </b></label>-->

<!--        <br>-->
<!--        <div id="Title-List-Search">-->
<!--          <template>-->
<!--            <input type="input" v-model="input" id="titleInput" class="form-control" required placeholder="Search">-->
<!--            <button class="btn btn-primary" @click="get(authorInput)">Search</button>-->
<!--            <button class="btn btn-danger" @click="addNewTitle(itemBarcode,status)">Add new Title</button>-->
<!--          </template>-->
<!--          <template>-->
<!--            <div>-->
<!--              <b-table :items="items" :fields="fields" striped responsive="sm">-->
<!--                <template #cell(show_details)="row">-->
<!--                  &lt;!&ndash; We call the toggleDetails function on @change &ndash;&gt;-->
<!--                  <b-form-checkbox v-model="row.detailsShowing" @change="row.toggleDetails"></b-form-checkbox>-->
<!--                </template>-->
<!--                <template #row-details="row">-->
<!--                  <b-card>-->
<!--                    <b-row class="mb-2">-->
<!--                      <b-col sm="3" class="text-sm-right"><b>Works:</b></b-col>-->
<!--                      <b-col>{{ row.item.title }}</b-col>-->
<!--                    </b-row>-->
<!--                    <b-button size="sm" @click="row.toggleDetails">Hide Details</b-button>-->
<!--                  </b-card>-->
<!--                </template>-->
<!--              </b-table>-->
<!--            </div>-->
<!--            <br>-->
<!--          </template>-->
<!--        </div>-->

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
          <button class="btn-success-acc" @click="createArchive(titleid)">Create Archive
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
          <button class="btn-success-acc" @click="createBook(titleid,isbncode, numberpages)">Create Book
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
          <button class="btn-success-acc" @click="createMovie(titleid, lengthmovie)">Create Movie
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
          <button class="btn-success-acc" @click="createMusicAlbum(titleid,lengthmusic)">Create Music Album
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
          <button class="btn-success-acc" @click="createNewsPaper(titleid)">Create Newspaper
          </button>
        </div>
      </b-tab>
      <br>
      <br>
      <div class="buttonUpdateItem">
        <button class="btn-success-new-page" @click="openUpdateItemPage">Update an item</button>
        <button class="btn-success-new-page" @click="openCreateAuthorPage">Create new Author</button>
        <button class="btn-success-new-page" @click="openCreateTitlePage">Create new title</button>
      </div>
    </b-tabs>
    <span v-if="error" style="color: red">Error: {{error}}</span>
    <span v-if="success" style="color: green">Added New Item: {{success}}</span>
  </div>
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

.btn-success-new-page {
  border-radius: 60px;
  border: None;
  width: 150px;
  height: 55px;
  color: #FDEDEC;
  background-color: #0cab11 ;
}

#titleid {
  width: 24.75%;
  min-width: 400px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 1vh;
}

#Author-List-Search {
  width: 24.75%;
  min-width: 400px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 1vh;
}

#Title-List-Search {
  width: 24.75%;
  min-width: 400px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 1vh;
}

#global_portion {
  width: 24.75%;
  min-width: 400px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 1vh;
}

.chooseAuthor {
  width: 30.75%;
  min-width: 400px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 1vh;
}

#formatted_author_table {
  width: 24.75%;
  min-width: 600px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 1vh;
}

#formatted_title_table {
  width: 24.75%;
  min-width: 600px;
  position: relative;
  left: 50%;
  transform: translateX(-50%);
  padding-top: 1vh;
}

</style>
