import axios from "axios";

const calendarTest = async () => {
    let remainingTests = 3;
    let resultData;
    let resultStatus;
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
    Test 1: Create calendar
     */
    try {
        let response = await axios.post("http://localhost:8080/calendar/create");

        if (response.status === 200 && response.data !== null) {
            remainingTests--;
            id = response.data.calendarID;
        }
        else {
            console.log("Failed test 1: Create calendar");
            console.log("Error: " + response.data);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed test 1: Create calendar");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 2: Delete calendar
     */
    try {
        let response = await axios.delete("http://localhost:8080/calendar/" + id);

        if (response.status === 200 && response.data.toString().includes("Deleted calendar")) remainingTests--;
        else {
            console.log("Failed test 2: Delete calendar");
            console.log("Error: " + response.data);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed test 2: Delete calendar");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Compile
     */
    if (remainingTests === 0) console.log("Passed all Calendar Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("");
};
export default calendarTest;