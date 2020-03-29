$(document).ready(function () {
    listItems($('.show').prop('checked'), showItemsList);
    changeShowed();
    saveChanged();
    createItem();
});

function listItems(showAll, callback) {
    $('#msg').remove();
    $.ajax({
        url      : '/view',
        data     : {'show' : showAll},
        complete : function (resp) {
            if (resp.status === 200) {
                var text = JSON.parse(resp.responseText);
                callback(text);
            } else {
                $('table').after('<div id="msg"><h3>Nothing to show!</h3></div>');
            }
        }
    })
}

function saveChanged() {
    $(document).on('change', '.done', function () {
        var itemData = $(this).closest('.row').find('td').first().text();
        $.ajax({
            url: '/edit',
            method: 'post',
            data: {'id' : itemData, 'done' : $(this).prop('checked')},
            complete: function () {
                document.location.reload(true);
            }
        });
    })
}

function changeShowed() {
    $('#show').on('change', function () {
        clearTable();
        listItems($(this).prop('checked'), showItemsList);
    })

}

function createItem() {
    $('[type=button]').on('click', function () {
        var formData = $('form').serializeArray();
        if (formData[0].value === '') {
            console.log('field empty');
        }
        $.ajax({
            url: '/create',
            method:'post',
            data : formData,
            complete : function () {
                document.location.reload(true);
            }
        });
    })
}

function showItemsList(list) {
    list.forEach(function (item) {
        var row = "<tr class='row'><td>" + item.id +"</td><td>" + item.description
            + "</td><td>" + item.created + "</td>";
        if (item.done) {
            row += "<td><input type='checkbox' class='done' checked disabled/></td></tr>";
        } else {
            row += "<td><input type='checkbox' class='done'/></td></tr>";
        }
        $('table').append(row);

    });
}

function clearTable() {
    $('.row').remove();
}
