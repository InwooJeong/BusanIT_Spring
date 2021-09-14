<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%-- include head.jsp --%>
    <jsp:include page="/WEB-INF/views/include/head.jsp"/>
</head>
<body>
    <%-- include topNavbar.jsp --%>
    <jsp:include page="/WEB-INF/views/include/topNavbar.jsp"/>
    
    <!-- middle container -->
    <div class="container mt-4">
      <div class="row">

        <!-- Left Menu -->
        <div class="col-sm-3">

          <!-- Vertical Nav -->
          <ul class="nav flex-column nav-pills">
            <li class="nav-item">
              <a class="nav-link active" href="#">Active</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item">
              <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
            </li>
          </ul>
          <!-- end of Vertical Nav -->
        </div>
        <!-- end of Left Menu  -->


        <!-- Right area -->
        <div class="col-sm-9">
          
          <!-- Contents area -->
          <div class="border border-info p-4 rounded">
            <h5>게시판 새글쓰기</h5>

            <hr class="featurette-divider">

            <form action="/board/reply" method="POST" enctype="multipart/form-data">
            	<input type="hidden" name="pageNum" value="${pageNum }">
            	<input type="hidden" name="reRef" value="${reRef }">
            	<input type="hidden" name="reLev" value="${reLev }">
            	<input type="hidden" name="reSeq" value="${reSeq }">
            
              <div class="form-group">
                <label for="id">아이디</label>
                <input type="text" class="form-control" id="id" aria-describedby="idHelp" name="mid" value="${sessionScope.id }" readonly>
              </div>

              <div class="form-group">
                <label for="subject">제목</label>
                <input type="text" class="form-control" id="subject" name="subject" autofocus>
              </div>

              <div class="form-group">
                <label for="content">내용</label>
                <textarea class="form-control" id="content" rows="10" name="content"></textarea>
              </div>
			  
			  <button type="button" class="btn btn-primary my-3" id="btnAddFile">파일 추가</button>
			  
			  <div><span>첨부 파일</span></div>
			  <div id="fileBox">
            	<div class="my-2">
              	  <input type="file" name="files" multiple>
              	  <button type="button" class="btn btn-primary btn-sm delete-file">
              	  	<i class="material-icons">clear</i>
              	  	<span class="align-middle">삭제</span>
              	  </button>
             	</div>
			  </div>
				
              <div class="my-4 text-center">
                <button type="submit" class="btn btn-primary">
                  <i class="material-icons align-middle">create</i>
                  <span class="align-middle">답글등록</span>
                </button>
                <button type="reset" class="btn btn-primary ml-3">
                  <i class="material-icons align-middle">clear</i>
                  <span class="align-middle">초기화</span>
                </button>
                <button type="button" class="btn btn-primary ml-3" onclick="location.href = '/board/boardList?pageNum=${pageNum}';">
                  <i class="material-icons align-middle">list</i>
                  <span class="align-middle">글목록</span>
                </button>
              </div>
            </form>

          </div>
          <!-- end of Contents area -->
        </div>
        <!-- end of Right area -->
      </div>
    </div>
    <!-- end of middle container -->



    <%-- include bottomFooter.jsp --%>
    <jsp:include page="/WEB-INF/views/include/bottomFooter.jsp"/>
    
	<%-- include javascript.jsp --%>
	<jsp:include page="/WEB-INF/views/include/javascript.jsp"/>
	
	<script>
	
		/* document.querySelector('#btnAddFile').addEventListener('click', function(event){
			
			//alert('aaa');
			
			var str = `
				<div class="my-2">
           	  		<input type="file">
           	  		<button type="button" class="btn btn-primary btn-sm delete-file">
           	  			<i class="material-icons">clear</i>
           	  			<span class="align-middle">삭제</span>
           	  		</button>
           		</div>
           	`;
			
			document.querySelector('#fileBox').innerHTML += str;
			
		}); */
		
		const MAX_FILE_COUNT = 5;
		let fileCount = 1; //화면에 보이는 file 입력 상자 개수
		
		//jQuery 방식 이벤트 처리
		//정적 이벤트 연결
		$('#btnAddFile').on('click',function(event){
			//alert('JQUERY~');
			
			if(fileCount >= MAX_FILE_COUNT){
				alert('첨부파일은 최대 5개 까지만 첨부할 수 있습니다~');
				return;
			}
			
			var str = `
				<div class="my-2">
           	  		<input type="file" name="files" multiple>
           	  		<button type="button" class="btn btn-primary btn-sm delete-file">
           	  			<i class="material-icons">clear</i>
           	  			<span class="align-middle">삭제</span>
           	  		</button>
           		</div>
           	`;
           	
           	$('#fileBox').append(str);
           	
           	fileCount++;
		});
		
		/* $('button.delete-file').on('click',function(){
			//alert('삭제 버튼 클릭');
		}); */
		
		//동적 이벤트 연결 - 이벤트 등록을 이미 존재하는 요소에게 위임
		$('div#fileBox').on('click','button.delete-file',function(event){
			
			//alert('삭제 버튼 클릭2');
			//event.target; //실제 이벤트 발생한 오브젝트
			
			$(this).closest('div').remove();
			//$(this).parent().remove();
			
			fileCount--;
			
		});
		
	</script>

</body>
</html>