import axios from "axios";

const BookTest = async () => {
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
    Test 1: Create Book
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

        response = await axios.post("http://localhost:8080/Books/create?status=Available&titleId=" + titleid + "&isbn=10&pages=160");

        resultData = response.data;
        resultStatus = response.status;
        let isbn = response.data.isbn
        let numPages = response.data.numPages
        if (resultStatus === 200 && resultData.status.toString().includes("Available") && resultData.title.titleID === titleid && isbn.includes("10") && numPages.includes("160")) {
            id = resultData.itemBarcode;
            remainingTests--;
        }
        else {
            console.log("Failed Test 1: Create Book");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 1: Create Book");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }


    /*
Test3 update Book
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
        response = await axios.put("http://localhost:8080/Books/uptitle?itemBarcode=" + id + "&titleId=" + titleid);
        resultData = response.data;
        resultStatus = response.status;
        let isbn = response.data.isbn
        let numPages = response.data.numPages
        if (resultStatus === 200 && resultData.itemBarcode === id && resultData.status === "Available" && resultData.title.titleID === titleid && pubdate === "12/10/2020"&&isbn.includes("10") && numPages.includes("160") ) remainingTests--;
    } catch (errorMsg){
        console.log("Failed Test 3: Update Book");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
// Test4 update Book
// */
    try {

        let response = await axios.put("http://localhost:8080/Books/upstatus?itemBarcode=" +id+ "&status=Damaged");
        resultData = response.data;
        resultStatus = response.status;
        let isbn = response.data.isbn
        let numPages = response.data.numPages
        if (resultStatus === 200 && resultData.itemBarcode === id && resultData.status === "Damaged"&& isbn.includes("10") && numPages.includes("160")) remainingTests--;
    } catch (errorMsg){
        console.log("Failed Test 4: Update Book");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }



    /*
Test5 update Book
*/
    try {

        let response = await axios.put("http://localhost:8080/Books/upisbn?itemBarcode=" +id+ "&isbn=30");
        resultData = response.data;
        resultStatus = response.status;
        let isbn = response.data.isbn
        let numPages = response.data.numPages
        let titleid2 = response.data.title.titleID;
        if (resultStatus === 200 && resultData.itemBarcode === id && titleid2.includes(titleid) && isbn.includes("30") && numPages.includes("160") ) remainingTests--;
    } catch (errorMsg){
        console.log("Failed Test 5: Update Book");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
Test6 update Book
*/
    try {

        let response = await axios.put("http://localhost:8080/Books/upage?itemBarcode=" +id+ "&page=90");
        resultData = response.data;
        resultStatus = response.status;
        let isbn = response.data.isbn
        let numPages = response.data.numPages
        let titleid2 = response.data.title.titleID;
        if (resultStatus === 200 && resultData.itemBarcode === id && titleid2.includes(titleid) && isbn.includes("30") && numPages.includes("90") ) remainingTests--;
    } catch (errorMsg){
        console.log("Failed Test 6: Update Book");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
     Test7 delet Book
    */
    try {
        let response = await axios.delete("http://localhost:8080/Books/delitem?itemBarcode=" + id)
        resultData = response.data
        resultStatus = response.status;

        if (resultStatus !== 200) {
            console.log("Failed test 7: Delete Book");
            console.log("Error: " + resultData);
            console.log("");
        }
        else if (resultData.toString().includes("Item deleted on ")) {
            remainingTests--;
        }

    }   catch (errorMsg){
        console.log("Failed Test 7: deleat Book");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    * Test8
    */
    try {
        let response = await axios.delete("http://localhost:8080/Books/delitemstat?status=Available")
        resultData = response.data
        resultStatus = response.status;

        if (resultStatus !== 200) {
            console.log("Failed test 8: Delete Book");
            console.log("Error: " + resultData);
            console.log("");
        }
        else if (resultData.toString().includes("Item deleted on ")) {
            remainingTests--;
        }

    }   catch (errorMsg){
        console.log("Failed Test 8: deleat Book");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
Compile
 */
    if (remainingTests === 0) console.log("Passed all Books Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("");
}





export default BookTest;