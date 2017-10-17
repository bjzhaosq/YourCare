function fAddAttach() {
    var count_div = $("#dvReadAttach").children("div").length;

    if(count_div > 0) {
        for(var i = 0; i < count_div; i++) {
            var input_value = $("#files" + i).val();
            if(input_value.trim() == "") {
               // alert("第 " + (i+1) + " 不能为空");
                return;
            }
        }
    }

    if(count_div >= 10) {
        alert("最多只能添加10个");
    } else {
       // var gAttchHTML = '<div class="qrle text2"></div><div class="le" style="float:left;position:relative;"><input type="file" id="files" name="file"  class="bot3 up_btn" ></div><div class="le upfun_btn"><input type="button" value=" 删除 " class="bot2" id="btnDeleteReadAttach"  /></div><div class="le upfun_btn"><input type="button" value=" 预览 " class="bot2" /></div><span></span>';
        var gAttchHTML = '<div class="qrle text2"></div><div class="le" style="float:left;position:relative;"><input type="file" id="files" name="file"  class="bot3 up_btn" ></div><div class="le upfun_btn"><input type="button" value=" 删除 " class="bot2" id="btnDeleteReadAttach"  /></div><div class="le upfun_btn"></div><span></span>';
        var Attach = document.getElementById("dvReadAttach");
        var spnList = Attach.getElementsByTagName("SPAN");
        var spn = document.createElement("DIV");
        spn.className = "qrc4";
        spn.style.width = "720px";
        spn.style.minHeight = "50px";
        spn.style.merginTop = "20px";
        spn.innerHTML = gAttchHTML;
        spn.childNodes[1].childNodes[0].id = "files" + spnList.length;
        //spn.childNodes[1].childNodes[0].name = "attachfile_" + spnList.length;
        spn.childNodes[1].childNodes[0].name = "file";
        Attach.appendChild(spn);
        fGetObjInputById(spn, "btnDeleteReadAttach").onclick = function() {
            fDeleteAttach(this);
        };
        document.getElementById("aAddAttach").innerHTML = "添加文件";
        Attach.style.display = "";
        if(spnList.length > 1) {
            spn.childNodes[0].innerHTML = "   ";
        }
    }

}

function fGetObjInputById(obj, id) {
    var inputList = obj.getElementsByTagName("INPUT");
    for(var i = 0; i < inputList.length; i++) {
        if(inputList[i].id == id) {
            return inputList[i];
        }
    }
    return null;
}

function fDeleteAttach(obj) {
    try {
        obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);
        var Attach = $("dvReadAttach");
        var spnList = Attach.getElementsByTagName("SPAN");
        if(spnList.length == 0) {
            document.getElementById("aAddAttach").innerHTML = "添加附件";
            Attach.style.display = "none";
        } else {
            document.getElementById("aAddAttach").innerHTML = "继续添加附件";
        }
    } catch(exp) {
        //fDebug("fDeleteAttach",exp.description);
    }
}