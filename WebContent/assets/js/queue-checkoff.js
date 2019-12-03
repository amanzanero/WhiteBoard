/*
       document : queue-checkoff.js
     created on : 2019 december 2, 18:23 pm (monday)
         author : audrey bongalon
      USC email : bongalon@usc.edu
         USC ID : 9152272619
    description : csci201 final project - js for checking someone off the queue


                                      88
                                      88
                                      88
    ,adPPYYba,  88       88   ,adPPYb,88  8b,dPPYba,   ,adPPYba,  8b       d8
    ""     `Y8  88       88  a8"    `Y88  88P'   "Y8  a8P,,,,,88  `8b     d8'
    ,adPPPPP88  88       88  8b      :88  88          8PP"""""""   `8b   d8'
    88,    ,88  "8a,   ,a88  "8a,   ,d88  88          "8b,   ,aa    `8b,d8'
    `"8bbdP"Y8   `"YbbdP'Y8   `"8bbdP"Y8  88           `"Ybbd8"'      Y88'
                                                                     d8'
                                                                    d8'
*/


$(document).ready(() => {
    createHiddenForm();
    connectButtonToForm();
});




// creates a form not visible to user that will be submitted upon clicking
// the logout button
function createHiddenForm() {
    $("body").append(
            $("<form>").attr({
                id: "logout-form",
                method: "post",
                action: "Logout"
            }).css("display", "none").append([
                $("<input>").attr({
                    type: "text",
                    name: "logout",
                    value: true
                })
            ])
    );
}




// makes it so that the form submits when you click the button
function connectButtonToForm() {
    $("#logout-button").click(() => {
        $("#logout-form").submit();
    });
}

