/*
       document : queue-checkoff-and-delete.js
     created on : 2019 december 2, 18:23 pm (monday)
         author : audrey bongalon
      USC email : bongalon@usc.edu
         USC ID : 9152272619
    description : csci201 final project - js for checking someone off the queue
                                          or deleting an entire queue


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
    let queueToDelete;

    /* DONT USE ARROW FUNCTIONS, or "this" wont bind properly!*/

    $(".remove-user-button").click(function() {
        const $button = $(this);

        // prevent from multiple clicks
        if ($button.attr("data-removing")) return;

        const queueName = $button.attr("data-queue-name");
        const userToRemove = $button.attr("data-username");
        console.log(`removing "${userToRemove}" from "${queueName}"`);

        $button.html("Removing...");
        $button.attr("data-removing", "in-progress");

        $.ajax({
            type: "POST",
            url: "CheckoffUserQueue",
            data: {
                queueName: queueName,
                userToRemove: userToRemove
            },
            success: function(result) {
                console.log(`successful deletion of ${userToRemove}`);
                $button.parent().addClass("remove-me");
                $(".remove-me").fadeOut(500, function() {
                    $button.remove();
                });
                // location.reload();
            },
            error: function(result) {
                alert("error. see console log");
                console.log("error");
                console.log(result);
            }
        });
    });


    $(".delete-queue-open-modal").click(function() {
        // when user clicks button to open modal, put queue name in header
        queueToDelete = $(this).attr("data-queue-name");
        $("#delete-modal-queue-name").html(queueToDelete);
    });

    $("#delete-queue-button").click(function() {
        console.log(`deleting queue ${queueToDelete}`)
        $.ajax({
            type: "POST",
            url: "DeleteQueue",
            data: {
                queueToDelete: queueToDelete,
            },
            success: function(result) {
                console.log(`successful deletion of ${queueToDelete}`);
                location.reload();
            },
            error: function(result) {
                alert("error. see console log");
                console.log("error");
                console.log(result);
            }
        });
    });
});

