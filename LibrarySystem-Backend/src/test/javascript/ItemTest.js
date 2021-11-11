import axios from "axios";

const itemTest = async () => {
  let remainingTests = 0;
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
            remainingTests--;
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
            remainingTests--;
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
};

export default itemTest;