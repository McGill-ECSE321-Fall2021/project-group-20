import axios from "axios";

const addressTest = async () => {
    let remainingTests = 4;
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
    Test 1: Create Address
     */
    try {
        let payload = "civicNumber=1&street=University&city=Montreal&postalCode=H2X3E2&province=QC&country=Canada"
        let response = await axios.post("http://localhost:8080/address/create?" + payload);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.civicNumber === "1" && resultData.street === "University") {
            remainingTests--;
            id = resultData.addressID;
        }
        else {
            console.log("Failed test 1: Create Address");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed test 1: Create Address");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 2: Modify Address
     */
    try {
        let payload = "?civicNumber=10&street=Uni&city=Montreal&postalCode=H2X3E5&province=QC&country=CA"
        let response = await axios.put("http://localhost:8080/address/update/" + id + payload);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.addressID === id && resultData.civicNumber === "10" && resultData.street === "Uni" && resultData.postalCode === "H2X3E5") remainingTests--;
        else {
            console.log("Failed test 2: Modify Address");
            console.log("Error: " + resultData);
            console.log("");
        }

    } catch (errorMsg) {
        console.log("Failed test 2: Modify Address");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 3: Delete Address
     */
    try {
        let response = await axios.delete("http://localhost:8080/address/" + id);

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.toString().includes("Address deleted")) remainingTests--;
        else {
            console.log("Failed test 3: Delete Address");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed test 3: Delete Address");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Compile
     */
    if (remainingTests === 0) console.log("Passed all Address Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("")

};

export default addressTest;