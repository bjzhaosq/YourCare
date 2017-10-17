$(document).ready(function(){
    $('.nav-list li').click(function(){
        $('.nav-list li:not(this)').removeClass('click-color');
        $(this).addClass('click-color');
    })

    $('.nav-list>li').mouseover(function(){
        var innerH= $(this).find('ul').height();
        $(this).find('ul').css('bottom',-innerH);
    })
})

