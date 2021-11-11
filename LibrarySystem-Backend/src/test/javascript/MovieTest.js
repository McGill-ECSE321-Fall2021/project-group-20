import axios from "axios";

const MovieTest = async () => {
    let remainingTests = 8;
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
    Test 1: Create Movie
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

        response = await axios.post("http://localhost:8080/Movies/create?status=Available&titleId=" + titleid + "&length=10");

        resultData = response.data;
        resultStatus = response.status;
        let duration = response.data.length
        if (resultStatus === 200 && resultData.status.toString().includes("Available") && resultData.title.titleID === titleid && duration===10) {
            id = resultData.itemBarcode;
            remainingTests--;
        }
        else {
            console.log("Failed Test 1: Create Movie");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 1: Create Movie");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 2: Update Movie
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

        response = await axios.put("http://localhost:8080/Movies/updateall?ItemBarcode=" + id + "&status=Reserved&titleId="  + titleid + "&length=10") ;

        resultData = response.data;
        let duration = response.data.length
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.itemBarcode === id && resultData.status.includes("Reserved") && resultData.title.titleID === titleid && duration===10) remainingTests--;
        else {
            console.log("Failed Test 2: Update Movie");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 2: Update Movie");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
Test3 update Movie
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
        response = await axios.put("http://localhost:8080/Movies/uptitle?itemBarcode=" + id + "&titleId=" + titleid);
        resultData = response.data;
        resultStatus = response.status;
        let duration = response.data.length
        if (resultStatus === 200 && resultData.itemBarcode === id && resultData.status === "Reserved" && resultData.title.titleID === titleid && pubdate === "12/10/2020"&& duration===10) remainingTests--;
    } catch (errorMsg){
        console.log("Failed Test 3: Update Movie");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
Test4 update Movie
*/
    try {

        let response = await axios.put("http://localhost:8080/Movies/upstatus?itemBarcode=" +id+ "&status=Damaged");
        resultData = response.data;
        resultStatus = response.status;
        if (resultStatus === 200 && resultData.itemBarcode === id && resultData.status === "Damaged") remainingTests--;
    } catch (errorMsg){
        console.log("Failed Test 4: Update Movie");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }



    /*
Test5 update Movie
*/
    try {

        let response = await axios.put("http://localhost:8080/Movies/uplength?itemBarcode=" +id+ "&length=30");
        resultData = response.data;
        resultStatus = response.status;
        let duration = response.data.length
        if (resultStatus === 200 && resultData.itemBarcode === id && duration===30) remainingTests--;
    } catch (errorMsg){
        console.log("Failed Test 5: Update Movie");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
     Test6 delet Movie
    */
    try {
        let response = await axios.delete("http://localhost:8080/Movies/delitem?itemBarcode=" + id)
        resultData = response.data
        resultStatus = response.status;

        if (resultStatus !== 200) {
            console.log("Failed test 5: Delete Movie");
            console.log("Error: " + resultData);
            console.log("");
        }
        else if (resultData.toString().includes("Item deleted on ")) {
            remainingTests--;
        }

    }   catch (errorMsg){
        console.log("Failed Test 5: deleat Movie");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    * Test7
    */
    try {
        let response = await axios.delete("http://localhost:8080/Movies/delitemstat?status=Available")
        resultData = response.data
        resultStatus = response.status;

        if (resultStatus !== 200) {
            console.log("Failed test 6: Delete Movie");
            console.log("Error: " + resultData);
            console.log("");
        }
        else if (resultData.toString().includes("Item deleted on ")) {
            remainingTests--;
        }

    }   catch (errorMsg){
        console.log("Failed Test 6: deleat Movie");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
Compile
 */
    if (remainingTests === 0) console.log("Passed all Movie Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("");
}





export default MovieTest;