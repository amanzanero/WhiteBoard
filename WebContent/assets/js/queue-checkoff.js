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
            id: "checkoff-form",
            method: "post",
            action: "CheckoffUserQueue"
        }).css("display", "none").append([
            $("<input>").attr({
                id: "queue-name-form-input",
                type: "text",
                name: "queueName",
                value: null
            }),
            $("<input>").attr({
                id: "username-form-input",
                type: "text",
                name: "userToRemove",
                value: null
            })
        ])
    );
}




// makes it so that the form submits when you click the button
function connectButtonToForm() {
    // DONT USE ARROW FUNCTIONS, or "this" wont bind properly!
    $(".remove-user-button").click(function() {
        const queueName = $(this).attr("data-queue-name");
        const userToRemove = $(this).attr("data-username");
        console.log(`removing "${userToRemove}" from "${queueName}"`);

        $("#queue-name-form-input").attr("queueName", queueName);
        $("#username-form-input").attr("userToRemove", userToRemove);
        $("#checkoff-form").submit();
    });
}

