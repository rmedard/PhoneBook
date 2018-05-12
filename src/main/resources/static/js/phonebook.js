$(document).ready(function () {
    $("#search-box").keyup(function (event) {
        event.preventDefault();
        search();
    });
});

function search() {
    var token = $('#search-box').val();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/phones/search",
        data: token,
        dataType: 'json',
        cache: false,
        success: function (data) {
            console.log("Success: ", data);
            var output = '';
            for (var i = 0; i < data.length; i++) {
                var phoneEntry = data[i];
                output += "<tr>" +
                    "<td>" + (i + 1) + "</td> " +
                    "<td>" + phoneEntry['firstName'] + "</td> " +
                    "<td>" + phoneEntry['lastName'] + "</td> " +
                    "<td>" + phoneEntry['phoneNumber'] + "</td> " +
                    "<td> " +
                    "<a class='btn btn-outline-danger btn-sm' href='/phones/create-update/?id=" + phoneEntry['id'] + "'>Edit phone</a> " +
                    "</td> </tr>"
            }
            $('#phoneEntriesTable > tbody').html(output);
        }, error: function (e) {
            console.log("Error: ", e);
        }
    });
}