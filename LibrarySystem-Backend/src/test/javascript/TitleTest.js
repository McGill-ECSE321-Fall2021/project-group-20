import axios from "axios";

const titleTest = async () => {
    let remainingTests = 12;
    let resultData;
    let resultStatus;
    let id;
    let titleid;
    let aId;
    let a2;
    let a3;

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
    Test 1: Create Title
     */
    try {
        let response = await axios.post("http://localhost:8080/author/create?firstname=John&lastname=Doe");

        if (response.status === 200) id = response.data.authorID;

        else {
            console.log("Failed Test 1: Could not create author for test");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.post("http://localhost:8080/title/create?name=Book&pubDate=11/10/2020&authors=" + id);

        if (response.status === 200) titleid = response.data.titleID;
        else {
            console.log("Failed Test 1: Create Title");
            console.log("Error: " + response.data);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 1: Create Title");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 2: Update Title name
     */
    try {
        let response = await axios.put("http://localhost:8080/title/" + titleid + "/updateName?name=UpdatedBook");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Updated name successfully")) remainingTests--;
        else {
            console.log("Failed Test 2: Update Title Name");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 2: Update Title Name");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 3: Update Title pubDate
     */
    try {
        let response = await axios.put("http://localhost:8080/title/" + titleid + "/updatePubDate?pubDate=01/01/2001");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Updated pubDate successfully")) remainingTests--;
        else {
            console.log("Failed Test 3: Update Title pubDate");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 3: Update Title pubDate");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 4: Update Title name & pubDate
     */
    try {
        let response = await axios.put("http://localhost:8080/title/" + titleid + "/updateNameAndPubDate?name=Up2&pubDate=01/01/2005");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Updated name & pubDate successfully")) remainingTests--;
        else {
            console.log("Failed Test 4: Update Title name and pubdate");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 4: Update Title name and pubDate");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 5: Add author to Title
     */
    try {
        let response = await axios.post("http://localhost:8080/author/create?firstname=John&lastname=Duet");

        if (response.status === 200) id = response.data.authorID;

        else {
            console.log("Failed Test 5: Could not create author for test");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.put("http://localhost:8080/title/" + titleid + "/addAuthorToTitle?author=" + id);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Added author successfully")) remainingTests--;
        else {
            console.log("Failed Test 5: Add author to title");
            console.log("Error: " + resultData);
            console.log("");
        }

    } catch (errorMsg) {
        console.log("Failed Test 5: Add author to title");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 6: Add authors to Title
     */
    try {
        let response = await axios.post("http://localhost:8080/author/create?firstname=Johnny&lastname=Duet");

        if (response.status === 200) aId = response.data.authorID;

        else {
            console.log("Failed Test 6: Could not create author for test");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.post("http://localhost:8080/author/create?firstname=Johnny&lastname=Duet");

        if (response.status === 200) a2 = response.data.authorID;

        else {
            console.log("Failed Test 6: Could not create author for test");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.post("http://localhost:8080/author/create?firstname=Johnny&lastname=Duetty");

        if (response.status === 200) a3 = response.data.authorID;

        else {
            console.log("Failed Test 6: Could not create author for test");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.put("http://localhost:8080/title/" + titleid + "/addAuthorsToTitle?authors=" + aId + "," + a2 + "," + a3);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Added authors successfully")) remainingTests--;
        else {
            console.log("Failed Test 6: Add authors to title");
            console.log("Error: " + resultData);
            console.log("");
        }

    } catch (errorMsg) {
        console.log("Failed Test 6: Add authors to title");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 7: Remove author from Title
     */
    try {
        let response = await axios.put("http://localhost:8080/title/" + titleid + "/removeAuthorFromTitle?author=" + id);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Removed author successfully")) remainingTests--;
        else {
            console.log("Failed Test 7: Remove author to title");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 7: Remove author to title");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 8: Remove authors from Title
     */
    try {
        let response = await axios.put("http://localhost:8080/title/" + titleid + "/removeAuthorsFromTitle?authors=" + a2 + "," + a3);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Removed authors successfully")) remainingTests--;
        else {
            console.log("Failed Test 8: Remove authors to title");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 8: Remove authors to title");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 9: Delete Title by ID
     */
    try {
        let response = await axios.delete("http://localhost:8080/title/" + titleid);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Title has been deleted")) remainingTests--;
        else {
            console.log("Failed Test 9: Delete Title by ID");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 9: Delete Title by ID");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 10: Delete Title by Name
     */
    try {
        let response = await axios.post("http://localhost:8080/title/create?name=Booker&pubDate=11/10/2020&authors=" + id);

        if (response.status === 200) titleid = response.data.titleID;
        else {
            console.log("Failed Test 10: Delete Title");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.delete("http://localhost:8080/title/deleteByName?name=Booker");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Titles have been deleted")) remainingTests--;
        else {
            console.log("Failed Test 10: Delete Title by name");
            console.log("Error: " + resultData);
            console.log("");
        }

    } catch (errorMsg) {
        console.log("Failed Test 10: Delete Title by name");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 11: Delete Titles by date
     */
    try {
        let response = await axios.post("http://localhost:8080/title/create?name=Bookers&pubDate=11/10/2020&authors=" + id);

        if (response.status === 200) titleid = response.data.titleID;
        else {
            console.log("Failed Test 11: Delete Titles");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.delete("http://localhost:8080/title/deleteByPubDate?pubDate=11/10/2020");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Titles have been deleted")) remainingTests--;
        else {
            console.log("Failed Test 11: Delete Titles by date");
            console.log("Error: " + resultData);
            console.log("");
        }

    } catch (errorMsg) {
        console.log("Failed Test 11: Delete Titles by date");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Compile
     */
    if (remainingTests === 0) console.log("Passed all Title Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("");
};

export default titleTest;