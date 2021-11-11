import axios from "axios";

let authorTest = async () => {
    let remainingTests = 10;
    let resultData;
    let resultStatus;
    let id;
    let titleId;

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
    Test 1: Create Author
     */
    try {
        let response = await axios.post("http://localhost:8080/author/create?firstname=John&lastname=Doe")

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.firstName === "John" && resultData.lastName === "Doe") {
            remainingTests--;
            id = resultData.authorID;
        }
        else {
            console.log("Failed Test 1: Create Author");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 1: Create Author");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 2: Update Author firstname
     */
    try {
        let response = await axios.put("http://localhost:8080/author/" + id + "/updateFirstName?firstname=Alex");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Firstname has been updated")) remainingTests--;
        else {
            console.log("Failed Test 2: Update Author firstname");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 2: Update Author firstname");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 3: Update Author lastname
     */
    try {
        let response = await axios.put("http://localhost:8080/author/" + id + "/updateLastName?lastname=John");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Lastname has been updated")) remainingTests--;
        else {
            console.log("Failed Test 3: Update Author lastname");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 3: Update Author lastname");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 4: Update author name
     */
    try {
        let response = await axios.put("http://localhost:8080/author/" + id + "/updateFullName?firstname=First&lastname=Last");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Name has been updated")) remainingTests--;
        else {
            console.log("Failed Test 4: Update Author name");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 4: Update Author name");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 5: Delete Author by ID
     */
    try {
        let response = await axios.delete("http://localhost:8080/author/" + id);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Author has been deleted")) remainingTests--;
        else {
            console.log("Failed Test 5: Delete author by ID");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 5: Delete author by ID");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 6: Delete Author by firstname
     */
    try {
        try {
            let response = await axios.post("http://localhost:8080/author/create?firstname=John&lastname=Doe")

            resultData = response.data;
            resultStatus = response.status;

            if (resultStatus === 200 && resultData.firstName === "John" && resultData.lastName === "Doe") {
                id = resultData.authorID;
            }
            else {
                console.log("Failed Test 6: Could not re-create Author for deletion testing");
                console.log("Error: " + resultData);
                console.log("");
            }
        } catch (errorMsg) {
            console.log("Failed Test 6: Could not re-create Author for deletion testing");
            console.log("Error: " + errorMsg.response.data);
            console.log("");
        }

        let response = await axios.delete("http://localhost:8080/author/deleteAuthorsByFirstName?firstname=John");
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Authors have been deleted")) remainingTests--;
        else {
            console.log("Failed Test 6: Could not delete Author by firstname");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 6: Could not delete Author by firstname");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 7: Delete Author by lastname
     */
    try {
        try {
            let response = await axios.post("http://localhost:8080/author/create?firstname=John&lastname=Doe")

            resultData = response.data;
            resultStatus = response.status;

            if (resultStatus === 200 && resultData.firstName === "John" && resultData.lastName === "Doe") {
                id = resultData.authorID;
            }
            else {
                console.log("Failed Test 7: Could not re-create Author for deletion testing");
                console.log("Error: " + resultData);
                console.log("");
            }
        } catch (errorMsg) {
            console.log("Failed Test 7: Could not re-create Author for deletion testing");
            console.log("Error: " + errorMsg.response.data);
            console.log("");
        }

        let response = await axios.delete("http://localhost:8080/author/deleteAuthorsByLastName?lastname=Doe");
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Authors have been deleted")) remainingTests--;
        else {
            console.log("Failed Test 7: Could not delete Author by lastname");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 7: Could not delete Author by lastname");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 8: Delete Author by name
     */
    try {
        try {
            let response = await axios.post("http://localhost:8080/author/create?firstname=John&lastname=Doe")

            resultData = response.data;
            resultStatus = response.status;

            if (resultStatus === 200 && resultData.firstName === "John" && resultData.lastName === "Doe") {
                id = resultData.authorID;
            }
            else {
                console.log("Failed Test 8: Could not re-create Author for deletion testing");
                console.log("Error: " + resultData);
                console.log("");
            }
        } catch (errorMsg) {
            console.log("Failed Test 8: Could not re-create Author for deletion testing");
            console.log("Error: " + errorMsg.response.data);
            console.log("");
        }

        let response = await axios.delete("http://localhost:8080/author/deleteAuthorsByFirstNameAndLastName?firstname=John&lastname=Doe");
        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Authors have been deleted")) remainingTests--;
        else {
            console.log("Failed Test 8: Could not delete Author by name");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed Test 8: Could not delete Author by name");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 9: Delete Author by titles
     */
    try {
        let response = await axios.post("http://localhost:8080/author/create?firstname=John&lastname=Doe");

        if (response.status === 200) id = response.data.authorID;

        else {
            console.log("Failed Test 9: Could not create author for test");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.post("http://localhost:8080/title/create?name=Book&pubDate=11/10/2020&authors=" + id);

        if (response.status === 200) titleId = response.data.titleID;
        else {
            console.log("Failed Test 9: Could not create title for test");
            console.log("Error: " + response.data);
            console.log("");
        }

        response = await axios.delete("http://localhost:8080/author/deleteAuthorsByTitles?titles=" + titleId);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Authors have been deleted")) remainingTests--;
        else {
            console.log("Failed Test 9: Could not delete Author by titles");
            console.log("Error: " + resultData);
            console.log("");
        }

    } catch (errorMsg) {
        console.log("Failed Test 9: Could not delete Author by titles");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }


    /*
    Compile
     */
    if (remainingTests === 0) console.log("Passed all Author Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("");
};

export default authorTest;