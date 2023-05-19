$(function(){

    const appendEntry = function(data){
        var entryCode = '<br><a href="#" class="entry-link" data-id="' +
            data.id + '">' + data.toDo + '<button class="update-button">Редактировать</button>' +
             '<button class="delete-button">Удалить запись</button>' + '</a><br>';
        $('#entry-list')
            .append('<div class="entry">' + entryCode + '</div>');
    };

    //Loading entries on load page
    $.get('/entries/', function(response)
    {
        for(i in response) {
            appendEntry(response[i]);
        }
    });

//    //Show adding entry form
//    $('#show-add-entry-form').click(function(){
//        $('#entry-form').css('display', 'flex');
//    });

    //Closing adding entry form
    $('#entry-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Getting entry
    $(document).on('click', '.entry-link', function(){
        var link = $(this);
        var entryId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/entries/' + entryId,
            success: function(response)
            {
                var code = '<br><span>Исполнитель:' + response.executor + '</span>';
                link.parent().append(code);
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Запись не найдена!');
                }
            }
        });
        return false;
    });

    //Adding entry
     $(document).on('click', '#show-add-entry-form', function(){
        $('#entry-form').css('display', 'flex');
        $('#save-entry').click(function()
        {
            var data = $('#entry-form form').serialize();
            $.ajax({
                method: "POST",
                url: '/entries/',
                data: data,
                success: function(response)
                {
                    $('#entry-form').css('display', 'none');
                    var entry = {};
                    entry.id = response;
                    var dataArray = $('#entry-form form').serializeArray();
                    for(i in dataArray) {
                        entry[dataArray[i]['name']] = dataArray[i]['value'];
                    }
                    appendEntry(entry);
                }
            });
            return true;
        });
    });

   //Delete entry
   $(document).on('click', '.delete-button', function(){
        var link = $(this);
        var entryId = link.parent().data('id');
        $.ajax({
            method: "DELETE",
            url: '/entries/' + entryId,
            success: function(response)
            {
                link.parent().parent().remove();
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Запись не найдена!');
                }
            }
        });
        return false;
    });
   //Delete all entries
   $(document).on('click', '.delete-all-button', function(){
       var items = $('.entry');
       $.ajax({
           method: "DELETE",
           url: '/entries/',
           success: function(response)
           {
               items.remove();
           },
           error: function(response)
           {
               if(response.status == 404) {
                   alert('Список дел пуст!');
               }
           }
       });
       return false;
    });
   //Update entry
    $(document).on('click', '.update-button', function(){
       var link = $(this);
       $('#entry-form').css('display', 'flex');
       var inputExecutor = document.querySelector("input[name~='executor']");
       var inputToDo = document.querySelector("input[name~='toDo']");
       var entryId = link.parent().data('id');
       $.ajax({
           method: "GET",
           url: '/entries/' + entryId,
           success: function(response)
           {
               inputExecutor.value = response.executor;
               inputToDo.value = response.toDo;
           },
           error: function(response)
           {
               if(response.status == 404) {
                   alert('Запись не найдена!');
               }
           }
       });
       $('#save-entry').click(function()
       {
           var data = $('#entry-form form').serialize();
           var entryId = link.parent().data('id');
           $.ajax({
               method: "PUT",
               url: '/entries/' + entryId,
               data: data,
               success: function(response)
               {
                   $('#entry-form').css('display', 'none');
                   var entry = {};
                   entry.id = response;
                   var dataArray = $('#entry-form form').serializeArray();
                   for(i in dataArray) {
                       entry[dataArray[i]['name']] = dataArray[i]['value'];
                   }
                   $(link.parent()).html(entry);
               },
           });
           return true;
       });
       return false;
    });
});