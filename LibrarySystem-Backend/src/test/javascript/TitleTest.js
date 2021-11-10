import axios from "axios";

const titleTest = async () => {
    let remainingTests = 0;
    let resultData;
    let resultStatus;
    let id;
    let titleid;

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
};

export default titleTest;