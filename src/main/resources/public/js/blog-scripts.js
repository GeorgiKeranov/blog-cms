$("textarea").keyup(function () { //Script for autosizing textarea.
   $(this).css({'height' : 'auto', 'overflow-y' : 'hidden'}).height(this.scrollHeight);
});

function readURL(input) {

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#image').attr('src', e.target.result);
        };

        reader.readAsDataURL(input.files[0]);
    }
}

$("#picture").change(function(){
    readURL(this);
});