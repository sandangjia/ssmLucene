function refresh(){
    $.ajax({
        type:'POST',
        url:'/createAllIndex',
        success:function(data){
            if(data.result ==1){
                alert("生成索引成功！");
            }else {
                alert(data.msg) ;
            }
        }
    });
}