$('document').ready(function (){
    $('table #editButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        console.log(href);
        $.get(href, function (category, status) {
            console.log("Category : " + category.id);
            $('#idEdit').val(category.id);
            $('#nameEdit').val(category.name);

        });

        //$('#editModal').modal();
    });
});
