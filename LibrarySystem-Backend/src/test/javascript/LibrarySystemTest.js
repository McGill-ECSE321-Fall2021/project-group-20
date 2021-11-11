import axios from "axios";

const librarySystemTest = async () => {
    let remainingTests = 4;
    let resultData;
    let resultStatus;
    let lsID;

    /*
    Clean database for testing (Test 1)
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
    Test 2: Create librarySystem
     */
    try {
        let response = await axios.post("http://localhost:8080/librarySystem/create?civicNumber=1&street=University&city=Montreal&postalCode=H3D4R5&province=QC&country=Canada");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.businessaddress.street === "University") {
            remainingTests--;
            lsID = resultData.systemID;
        }
        else {
            console.log("Failed test 2: Create librarySystem");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed test 2: Create librarySystem");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 3: Update librarySystem
     */
    try {
        let response = await axios.put("http://localhost:8080/librarySystem/updateAddress/" + lsID +
        "?civicNumber=2&street=Sherbrooke&city=Laval&postalCode=W2Q3E4&province=QC&country=Canada");

        resultData = response.data;
        resultStatus = response.status;

        if (resultStatus === 200 && resultData.systemID === lsID && resultData.businessaddress.civicNumber === "2" && resultData.businessaddress.city === "Laval") {
            remainingTests--;
        }
        else {
            console.log("Failed test 3: Update librarySystem");
            console.log("Error: " + resultData);
            console.log("");
        }
    } catch (errorMsg) {
        console.log("Failed test 3: Update librarySystem");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Test 4: Delete librarySystem
     */
    try {
        let response = await axios.delete("http://localhost:8080/librarySystem/delete/" + lsID);

        if (response.status === 200 && response.data.toString().includes("Deleted LibrarySystem")) remainingTests--;
        else {
            console.log("Failed test 4: Delete librarySystem");
            console.log("Error: " + resultData);
            console.log("");
        }

    } catch (errorMsg) {
        console.log("Failed test 4: Delete librarySystem");
        console.log("Error: " + errorMsg.response.data);
        console.log("");
    }

    /*
    Compile
     */
    if (remainingTests === 0) console.log("Passed all LibrarySystem Tests :)");
    else console.log("Failed " + remainingTests + " :(");
    console.log("");
};
export default librarySystemTest;