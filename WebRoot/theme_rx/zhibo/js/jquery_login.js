$(document).ready(function(){
    $('#tishi').hide();
    $(".submit").click(function(){

        var username = $("#username").val();
        var password = $("#password").val();
        if(username == ''||password == ''){
            $('#tishi').html("账号密码不可以为空");
            $('#tishi').show();
        }else{
            $.ajax({
                type : 'POST',
                url : "/login/goLogin.html",
                data :"username="+username+"&password="+password,
                dataType : 'json',
                cache : false,
                error : function(XMLHttpRequest, textStatus, errorThrown){
                    alert("登陆失败，联系管理员");
                },
                success : function(date) {
                    var code = date.code;
                    if(code == 1){
                        $('#tishi').html(date.msg);
                        $('#tishi').show();
                    }else{
                        $('#ycare').text("welcome to Ycare");
                        $(".b").css("transform","translate(0px,-260px)");
                        $(".b").css("opacity","0");
                        $("h2").css("transform","translate(0px,130px)");
                        $("h2 span").css("opacity","1");
                        $("h2 span i").css("animation-play-state","running");
                        window.setTimeout(function(){$('#ycare').text("登陆成功"); }, 2000);
                        window.setTimeout(function(){ location.href="/zhibo/index.html";}, 3000);
                        // window.location.href = "/index.html";
                    }
                }
            });
        }


        //window.setTimeout(function(){ location.href="https://www.baidu.com/";}, 5000);
    });
    $(".input").change(function(){
        $('#tishi').html("");
        $('#tishi').hide();
    });

    document.onkeydown=keyListener;
    function keyListener(e){
        e = e ? e : event;// 兼容FF
        if(e.keyCode == 13){
            $(".submit").click();
        }
    }
});