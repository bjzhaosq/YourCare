/**
 * Created by lhf on 2017/7/12.
 */

$(document).ready(function(){
	
	 $('.left_menu>a').click(function(){
	        if($(this).next('.submenu').is(":hidden")){
	              $(this).next('.submenu').slideDown("fast");
	              $(this).children('b').addClass('rot');
	         }else{
	               $(this).next('.submenu').slideUp("fast");
	               $(this).children('b').removeClass('rot');
	         }
	    });
	
	 $('.submenu span').click(function(){
	        $('.submenu span:not(this)').css('color','#fff');
	        $(this).css('color','#dfd9d9');
	    })
	
    var h=$('.left_menu').height();
    $('.main_content').css("minHeight",h+245);
    $('.main_content_box').css("minHeight",h+176);
})
