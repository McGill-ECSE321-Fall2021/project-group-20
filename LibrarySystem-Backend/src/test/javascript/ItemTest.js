import axios from "axios";

const itemTest = async () => {
  let remainingTests = 7;
  let resultData;
  let resultStatus;
  let titleid;
  let authorid;
  let id;

    /*
      Clean database for testing (Test 0)
       */
    try {
        let response = await axios.delete("http://localhost:8080/librarySystem/clear?confirmbool=true");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Database has been wiped")) remainingTests--;
        else {
            console.log("Failed DB Clear");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed DB Clear");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 1: Create Item
     */
    try {
        let response = await axios.post("http://localhost:8080/author/create?firstname=John&lastname=Doe");

        if (response.status === 200) authorid = response.data.authorID;

        else {
            console.log("Failed Test 1: Could not create author for test");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.post("http://localhost:8080/title/create?name=Book&pubDate=11/10/2020&authors=" + authorid);

        if (response.status === 200)  {
            titleid = response.data.titleID;
        }
        else {
            console.log("Failed Test 1: Create Title for test");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.post("http://localhost:8080/items/create?status=Available&titleId=" + titleid);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.status.toString().includes("Available") && resultData.title.titleID === titleid) {
            id = resultData.itemBarcode;
            remainingTests--;

        }
        else {
            console.log("Failed Test 1: Create Item");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 1: Create Item");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 2: Update item
     */
    try {
        let response = await axios.post("http://localhost:8080/title/create?name=Booker&pubDate=11/10/2020&authors=" + authorid);

        if (response.status === 200)  {
            titleid = response.data.titleID;
        }
        else {
            console.log("Failed Test 2: Create Title for test");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.put("http://localhost:8080/items/updateall?itemBarcode=" + id + "&status=Reserved&titleId=" + titleid);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.itemBarcode === id && resultData.status === "Reserved" && resultData.title.titleID === titleid) remainingTests--;
        else {
            console.log("Failed Test 2: Update item");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 2: Update item");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
Test3 update item
 */

    try {
        let pubdate;
        let response = await axios.post("http://localhost:8080/title/create?name=Booker&pubDate=12/10/2020&authors=" + authorid);
        if (response.status === 200)  {
            titleid = response.data.titleID;
            pubdate = response.data.pubDate;
        } else {
            console.log("Failed Test 3: Create Title for test");
            console.log("Error: " + response.data);
            console.log("");
        }
        response = await axios.put("http://localhost:8080/items/uptitle?itemBarcode=" + id + "&titleId=" + titleid);
        resultData = response.data;
        resultStatus = response.status;
        if (resultStatus === 200 && resultData.itemBarcode === id && resultData.status === "Reserved" && resultData.title.titleID === titleid && pubdate === "12/10/2020") remainingTests--;
    } catch (errorMsg){
        console.log("Failed Test 3: Update item");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
Test4 update item
*/
    try {

        let response = await axios.put("http://localhost:8080/items/upstatus?itemBarcode=" +id+ "&status=Damaged");
        resultData = response.data;
        resultStatus = response.status;
        if (resultStatus === 200 && resultData.itemBarcode === id && resultData.status === "Damaged") remainingTests--;
    } catch (errorMsg){
        console.log("Failed Test 4: Update item");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
     Test5 delet item
    */
    try {
        let response = await axios.delete("http://localhost:8080/items/delitem?itemBarcode=" + id)
        resultData = response.data
        resultStatus = response.status;

        if (resultStatus !== 200) {
            console.log("Failed test 5: Delete Item");
            console.log("Error: " + resultData);
            console.log("");
        }
        else if (resultData.toString().includes("Item deleted on ")) {
            remainingTests--;
        }

    }   catch (errorMsg){
        console.log("Failed Test 5: deleat item");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    * Test6
    */
    try {
        let response = await axios.delete("http://localhost:8080/items/delitemstat?status=Available")
        resultData = response.data
        resultStatus = response.status;

        if (resultStatus !== 200) {
            console.log("Failed test 6: Delete Item");
            console.log("Error: " + resultData);
            console.log("");
        }
        else if (resultData.toString().includes("Item deleted on ")) {
            remainingTests--;
        }

    }   catch (errorMsg){
        console.log("Failed Test 6: deleat item");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
Compile
 */
    if (remainingTests === 0) console.log("Passed all Items Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("");
}





export default itemTest;