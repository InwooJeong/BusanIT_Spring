<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>          
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
            <h5>게시판 글 수정</h5>

            <hr class="featurette-divider">

            <form action="/board/modify" method="POST" enctype="multipart/form-data">
            	<input type="hidden" name="pageNum" value="${pageNum }">
            	<input type="hidden" name="num"  value="${board.num }">
            
              <div class="form-group">
                <label for="id">아이디</label>
                <input type="text" class="form-control" id="id" aria-describedby="idHelp" name="mid" value="${sessionScope.id }" readonly>
              </div>

              <div class="form-group">
                <label for="subject">제목</label>
                <input type="text" class="form-control" id="subject" name="subject" value="${board.subject }" autofocus>
              </div>

              <div class="form-group">
                <label for="content">내용</label>
                <textarea class="form-control" id="content" rows="10" name="content">${board.content }</textarea>
              </div>
			  
			  <button type="button" class="btn btn-primary my-3" id="btnAddFile">파일 추가</button>
			  
			  <div><span>첨부 파일</span></div>
			  
			  <!-- 기존 첨부 파일 영역 : 삭제할 파일 정보 표현용 -->
			  <div id="oldFileBox">
			  <c:forEach var="attach" items="${attachList }">
			  	<!-- delete-oldfile 삭제버튼 클릭 시 hidden input 요소 name 속성을 oldfile → delfile -->
			  	<!-- 서버에서는 oldfile 찾지 않고 delfile만 찾아서 삭제처리 -->
			  	<input type="hidden" name="oldfile" value="${attach.uuid }">
			  	<div>
			  		<span>${attach.filename }</span>
			  		<button type="button" class="btn btn-primary btn-sm delete-oldfile">
           	  			<i class="material-icons">clear</i>
           	  			<span class="align-middle">삭제</span>
           	  		</button>
			  	</div>
			  </c:forEach>
			  </div>
			  
			  <!-- 신규 파일 영역 : 새로 첨부될 파일 업로드용 -->
			  <div id="newFileBox">
			  
			  </div>
				
              <div class="my-4 text-center">
                <button type="submit" class="btn btn-primary">
                  <i class="material-icons align-middle">create</i>
                  <span class="align-middle">글수정</span>
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
		
		const MAX_FILE_COUNT = 5;
		let fileCount = ${ fn:length(attachList) }; //화면에 보이는 file 입력 상자 개수
	
		$('button.delete-oldfile').on('click', function(event){
			
			//현재 클릭 발생한 버튼(this)을 기준,
			//직계부모 div 요소의 이전(prev)요소인 hidden input 요소를 참조
			//name 속성값을 기존 oldfile에서 delfile로 변경
			$(this).parent().prev().prop('name','delfile');
			
			$(this).parent().remove(); //현재 클릭한 버튼의 직계부모 div 요소를 삭제
			fileCount--;
			
		});
	
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
           	  		<button type="button" class="btn btn-primary btn-sm delete-addfile">
           	  			<i class="material-icons">clear</i>
           	  			<span class="align-middle">삭제</span>
           	  		</button>
           		</div>
           	`;
           	
           	$('#newFileBox').append(str);
           	
           	fileCount++;
		});
		
		/* $('button.delete-file').on('click',function(){
			//alert('삭제 버튼 클릭');
		}); */
		
		//동적 이벤트 연결 - 이벤트 등록을 이미 존재하는 요소에게 위임
		$('div#newFileBox').on('click','button.delete-addfile',function(event){
			
			//alert('삭제 버튼 클릭2');
			//event.target; //실제 이벤트 발생한 오브젝트
			
			$(this).closest('div').remove();
			//$(this).parent().remove();
			
			fileCount--;
			
		});
		
	</script>

</body>
</html>