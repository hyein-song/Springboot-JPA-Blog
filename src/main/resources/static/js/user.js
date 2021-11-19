let index = {
    init:function (){
        $("#btn-save").on("click",()=>{ // function(){} 를 ()=> {} 로 사용하는건 this를 바인딩하기 위해서
            this.save();
        });
        $("#btn-login").on("click",()=>{ // function(){} 를 ()=> {} 로 사용하는건 this를 바인딩하기 위해서
            this.login();
        });
    },

    save:function (){
        // alert('user의 save 함수 호출됨');
        let data = {
            username : $("#username").val(),
            password : $("#password").val(),
            email : $("#email").val()
        }
        // console.log(data); // 자바스크리브 오브젝트

        // ajax 호출 시 default가 비동기 호출
        // ajax가 통신을 성골하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해줌
        $.ajax({
            // 회원가입 수행 요청
            type :"POST",
            url:"/blog/api/user",
            data:JSON.stringify(data), // data를 json 문자열로 변환
            contentType:"application/json; charset=utf-8", // body data가 어떤 타입인지 (MIME)
            dataType : "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든것이 String임 (생긴게 json이라면 자바스크립트 오브젝트로 변경해줌)
        }).done(function (resp){
            alert("회원가입이 완료 되었습니다.");
            // console.log(resp);
            location.href="/blog";
        }).fail(function (error){
            alert(JSON.stringify(error));
        }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
        //ajax 쓰는 두 가지 이유
        // 1. 웹프로그램을 사용하든지 앱 프로그램을 사용하든지 서버를 하나만 쓰기 위해
        // 2. 비동기 통신을 하기 위해서

    },

    login:function (){
        let data = {
            username : $("#username").val(),
            password : $("#password").val(),
        }

        $.ajax({
            type :"POST",
            url:"/blog/api/user/login",
            data:JSON.stringify(data), // data를 json 문자열로 변환
            contentType:"application/json; charset=utf-8", // body data가 어떤 타입인지 (MIME)
            dataType : "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든것이 String임 (생긴게 json이라면 자바스크립트 오브젝트로 변경해줌)
        }).done(function (resp){
            alert("로그인이 완료 되었습니다.");
            location.href="/blog";
        }).fail(function (error){
            alert(JSON.stringify(error));
        }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
        //ajax 쓰는 두 가지 이유
        // 1. 웹프로그램을 사용하든지 앱 프로그램을 사용하든지 서버를 하나만 쓰기 위해
        // 2. 비동기 통신을 하기 위해서

    }

}

index.init();