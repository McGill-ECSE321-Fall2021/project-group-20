<template>
<div id="Management" >
  <b-navbar toggleable="lg" variant="faded" type="light" >
    <b-navbar-brand href="/">Menu</b-navbar-brand>
  </b-navbar>
  <div class="header_img">
    <img src="../assets/LS.png" style="width:10%; height:auto; padding-bottom: 2vh">
  </div>



<!--  <div class="authorTableFrame">-->
<!--    <table class="table table-striped table-hover">-->
<!--      <thead>-->
<!--        <tr>-->
<!--          <th>AuthorID</th>-->
<!--          <th>First Name</th>-->
<!--          <th>Last Name</th>-->
<!--        </tr>-->
<!--      </thead>-->

<!--      <tbody>-->
<!--        <tr v-for="author in authors">-->
<!--          <td>-->
<!--            <label class="form-checkbox">-->
<!--              <input type="checkbox" :value="author.authorID" v-model="selectedAuthor">-->
<!--              <i class="form-icon"></i>-->
<!--            </label>-->
<!--          </td>-->
<!--          <th>{{author.authorID}}</th>-->
<!--          <th>{{author.firstname}}</th>-->
<!--          <th>{{author.lastname}}</th>-->
<!--        </tr>-->
<!--      </tbody>-->
<!--    </table>-->
<!--  </div>-->




<!--  <div class="titleTableFrame">-->
<!--  </div>-->



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
                  <th>AuthorID</th>
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
                  <td>{{author.authorID}}</td>
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





<!--    </b-tabs>-->



<!--        <h4><b>Title ID: </b></h4>-->
<!--        <br>-->
<!--        <input type="text" v-model="titleid" id="titleid" placeholder="TitleID" class="form-control" required>-->
<!--        <br>-->


        <!-- NOT USING THIS ANYMORE -->
<!--        <div class="titleTable">-->
<!--&lt;!&ndash;          <div><b>Title selected: {{selected}}</b></div>&ndash;&gt;-->
<!--          <table class="table table-striped table-hover" id="formatted_title_table">-->
<!--            <thead>-->
<!--            <tr>-->
<!--              <th>-->
<!--&lt;!&ndash;                <label class="form-checkbox">&ndash;&gt;-->
<!--&lt;!&ndash;                  <input type="checkbox" v-model="selectTitles" @click="selectTitle">&ndash;&gt;-->
<!--&lt;!&ndash;                  <i class="form-icon"></i>&ndash;&gt;-->
<!--&lt;!&ndash;                </label>&ndash;&gt;-->
<!--              </th>-->
<!--              <th>TitleID</th>-->
<!--              <th>TitleName</th>-->
<!--              <th>PublishDate</th>-->
<!--            </tr>-->
<!--            </thead>-->
<!--            <tbody>-->
<!--            <tr v-for="title in titles">-->
<!--              <td>-->
<!--                <label class="form-checkbox">-->
<!--                  <input type="checkbox" :value="title.titleID" v-model="selectedTitles" @click="selectTitle">-->
<!--                  <i class="form-icon"></i>-->
<!--                </label>-->
<!--              </td>-->
<!--              <td>{{title.titleID}}</td>-->
<!--              <td>{{title.name}}</td>-->
<!--              <td>{{title.pubDate}}</td>-->
<!--            </tr>-->
<!--            </tbody>-->
<!--          </table>-->
<!--        </div>-->




<!--        <div class="itemType">-->
<!--          <div class="choose">-->
<!--            <label for="select">Select item type</label><br>-->
<!--            <select v-model="selectedType" name="select" id="select">-->
<!--              <option </option>-->
<!--            </select>-->
<!--          </div>-->
<!--        </div>-->


      </div>    <!-- main frame div -->

<!--        </div>-->

<!--&lt;!&ndash;        <div id="Author-List-Search">&ndash;&gt;-->
<!--&lt;!&ndash;          <template>&ndash;&gt;-->
<!--&lt;!&ndash;            <input type="input" v-model="input" id="authorInput" class="form-control" required placeholder="Search">&ndash;&gt;-->
<!--&lt;!&ndash;            <button class="btn btn-primary" @click="get(authorInput)">Search</button>&ndash;&gt;-->
<!--&lt;!&ndash;            <button class="btn btn-danger" @click="addNewAuthor(itemBarcode,status)">Add new Author</button>&ndash;&gt;-->
<!--&lt;!&ndash;          </template>&ndash;&gt;-->
<!--&lt;!&ndash;          <template>&ndash;&gt;-->
<!--&lt;!&ndash;            <div>&ndash;&gt;-->
<!--&lt;!&ndash;              <b-table :items="items" :fields="fields" striped responsive="sm">&ndash;&gt;-->
<!--&lt;!&ndash;                <template #cell(show_details)="row">&ndash;&gt;-->
<!--&lt;!&ndash;                  &lt;!&ndash; We call the toggleDetails function on @change &ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;                  <b-form-checkbox v-model="row.detailsShowing" @change="row.toggleDetails"></b-form-checkbox>&ndash;&gt;-->
<!--&lt;!&ndash;                </template>&ndash;&gt;-->
<!--&lt;!&ndash;                <template #row-details="row">&ndash;&gt;-->
<!--&lt;!&ndash;                  <b-card>&ndash;&gt;-->
<!--&lt;!&ndash;                    <b-row class="mb-2">&ndash;&gt;-->
<!--&lt;!&ndash;                      <b-col sm="3" class="text-sm-right"><b>Works:</b></b-col>&ndash;&gt;-->
<!--&lt;!&ndash;                      <b-col>{{ row.item.title }}</b-col>&ndash;&gt;-->
<!--&lt;!&ndash;                    </b-row>&ndash;&gt;-->
<!--&lt;!&ndash;                    <b-button size="sm" @click="row.toggleDetails">Hide Details</b-button>&ndash;&gt;-->
<!--&lt;!&ndash;                  </b-card>&ndash;&gt;-->
<!--&lt;!&ndash;                </template>&ndash;&gt;-->
<!--&lt;!&ndash;              </b-table>&ndash;&gt;-->
<!--&lt;!&ndash;            </div>&ndash;&gt;-->
<!--&lt;!&ndash;            <br>&ndash;&gt;-->

<!--&lt;!&ndash;          </template>&ndash;&gt;-->
<!--&lt;!&ndash;        </div>&ndash;&gt;-->

<!--&lt;!&ndash;        <label><b>List of Titles: </b></label>&ndash;&gt;-->

<!--&lt;!&ndash;        <br>&ndash;&gt;-->
<!--&lt;!&ndash;        <div id="Title-List-Search">&ndash;&gt;-->
<!--&lt;!&ndash;          <template>&ndash;&gt;-->
<!--&lt;!&ndash;            <input type="input" v-model="input" id="titleInput" class="form-control" required placeholder="Search">&ndash;&gt;-->
<!--&lt;!&ndash;            <button class="btn btn-primary" @click="get(authorInput)">Search</button>&ndash;&gt;-->
<!--&lt;!&ndash;            <button class="btn btn-danger" @click="addNewTitle(itemBarcode,status)">Add new Title</button>&ndash;&gt;-->
<!--&lt;!&ndash;          </template>&ndash;&gt;-->
<!--&lt;!&ndash;          <template>&ndash;&gt;-->
<!--&lt;!&ndash;            <div>&ndash;&gt;-->
<!--&lt;!&ndash;              <b-table :items="items" :fields="fields" striped responsive="sm">&ndash;&gt;-->
<!--&lt;!&ndash;                <template #cell(show_details)="row">&ndash;&gt;-->
<!--&lt;!&ndash;                  &lt;!&ndash; We call the toggleDetails function on @change &ndash;&gt;&ndash;&gt;-->
<!--&lt;!&ndash;                  <b-form-checkbox v-model="row.detailsShowing" @change="row.toggleDetails"></b-form-checkbox>&ndash;&gt;-->
<!--&lt;!&ndash;                </template>&ndash;&gt;-->
<!--&lt;!&ndash;                <template #row-details="row">&ndash;&gt;-->
<!--&lt;!&ndash;                  <b-card>&ndash;&gt;-->
<!--&lt;!&ndash;                    <b-row class="mb-2">&ndash;&gt;-->
<!--&lt;!&ndash;                      <b-col sm="3" class="text-sm-right"><b>Works:</b></b-col>&ndash;&gt;-->
<!--&lt;!&ndash;                      <b-col>{{ row.item.title }}</b-col>&ndash;&gt;-->
<!--&lt;!&ndash;                    </b-row>&ndash;&gt;-->
<!--&lt;!&ndash;                    <b-button size="sm" @click="row.toggleDetails">Hide Details</b-button>&ndash;&gt;-->
<!--&lt;!&ndash;                  </b-card>&ndash;&gt;-->
<!--&lt;!&ndash;                </template>&ndash;&gt;-->
<!--&lt;!&ndash;              </b-table>&ndash;&gt;-->
<!--&lt;!&ndash;            </div>&ndash;&gt;-->
<!--&lt;!&ndash;            <br>&ndash;&gt;-->
<!--&lt;!&ndash;          </template>&ndash;&gt;-->
<!--&lt;!&ndash;        </div>&ndash;&gt;-->
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

.tabFrame {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
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
