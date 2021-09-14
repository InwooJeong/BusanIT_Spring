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
            <h5>${id }님 비밀번호 변경</h5>

            <hr class="featurette-divider">

            <form action="/member/pmodi" method="POST">
            	<input type="hidden" value="${id }" name="id">

              <div class="form-group">
                <label for="password">
                  <i class="material-icons align-middle">lock</i>
                  <span class="align-middle">현재 비밀번호</span>
                </label>
                <input type="password" class="form-control" id="password" aria-describedby="pwdHelp" name="passwd" required>
              </div>
              
              <hr><span>비밀번호는 영문자와 특수문자를 포함, 6~12글자까지 가능합니다!</span><hr>
              
              <div class="form-group">
                <label for="password2">
                  <i class="material-icons align-middle">check</i>
                  <span class="align-middle">수정할 비밀번호</span>
                </label>
                <input type="password" class="form-control" id="pmodi" name="pmodi" required>
              </div>
              
              <div class="form-group">
                <label for="password2">
                  <i class="material-icons align-middle">check</i>
                  <span class="align-middle">수정할 비밀번호 확인</span>
                </label>
                <input type="password" class="form-control" id="pmodick" name="pmodick" placeholder="수정할 비밀번호를 한 번더 입력하세요." required>
              </div>
              
              <p id="pwValid" style="color : black; margin : 0;">유효성 결과</p>
              <p id="pwCheckR" style="color : black; margin : 0;">일치 결과</p>
              
              <div class="my-3 text-center">
                <button type="submit" class="btn btn-primary" id="btn" disabled>비밀번호 수정</button>
                <button type="reset" class="btn btn-primary ml-3">초기화</button>
                <button type="button" class="btn btn-primary ml-3" onclick="history.back()">취소</button>
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
	
	<script type="text/javascript">
		let chk = 0;
	
		$(document).ready(function(){
			
			$('#pmodi').keyup(function(){
				$('#pwCheckR').text('');
			});
			
			$('#pmodick').keyup(function(){
				if($('#pmodi').val()!=$('#pmodick').val()){
					$('#pwCheckR').text('');
					$('#pwCheckR').html("<font color='red'>비밀번호가 일치하지 않습니다!!</font>");
				}else{
					$('#pwCheckR').text('');
					$('#pwCheckR').html("<font color='green'>비밀번호가 일치합니다!!</font>");
					chk++;
					console.log(chk);
					//$('#btn').prop('disabled', false);
				}
				
				if(chk==2){
					$('#btn').prop('disabled', false);
				}
				
			});
			
			$('input#pmodi').on('focusout', function(){
				let pmodi = $(this).val();
				if(pmodi.length == 0){
					return;
				}
				
				$.ajax({
					url : '/member/pwCheck/' + pmodi+'.json',
					method : 'GET',
					success : function(data){
						console.log(typeof data); //boolean
						console.log(data); // true / false
						
						if(data){
							$('#pwValid').text('');
							$('#pwValid').html("<font color='green'>비밀번호가 유효합니다!!</font>");
							chk++;
							console.log(chk);
						}else{
							$('#pwValid').text('');
							$('#pwValid').html("<font color='red'>비밀번호가 유효하지 않습니다!!</font>");
						}
						
					},
					error : function(request, status, error){
						alert('code : ' + request.status + '\n message : ' + request.responseText + '\n error : '+error);
					}
				});
			});
			
		});
	</script>
		
</body>
</html>